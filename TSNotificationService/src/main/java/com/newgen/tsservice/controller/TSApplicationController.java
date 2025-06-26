package com.newgen.tsservice.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newgen.omni.wf.util.excp.NGException;
import com.newgen.tsservice.entities.ErrorResponse;
import com.newgen.tsservice.entities.RequestData;
import com.newgen.tsservice.entities.RequestWrapper;
import com.newgen.tsservice.entities.ResponseData;
import com.newgen.tsservice.entities.ResponseWrapper;
import com.newgen.tsservice.util.APCallCreateXML;
import com.newgen.tsservice.util.LapsConfigurations;
import com.newgen.tsservice.util.SingleUserConnection;

@RestController
public class TSApplicationController {

    private static final Logger logger = LogManager.getLogger(TSApplicationController.class);
    private static final String ERROR_CODE_BAD_REQUEST = "API400";
    private static final String ERROR_CODE_INTERNAL_SERVER = "API500";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String  jsonRequestData; 
    private static String sessionid;

    @GetMapping("/test")
    public String hello() {
        return "Hello, this is a test Rest API Using Spring Boot";
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateRequest(@RequestBody RequestWrapper requestWrapper) {
        try {
        	 jsonRequestData =objectMapper.writeValueAsString(requestWrapper);
        	 logger.info("Received complete request: {}", jsonRequestData);
             initializeSession();
            
            if (requestWrapper == null || requestWrapper.getData() == null) {
                return buildErrorResponse(ERROR_CODE_BAD_REQUEST, "Request body or Data is missing");
            }

            RequestData requestData = requestWrapper.getData();
            logRequestData(requestData);

            if (!isRequestDataValid(requestData)) {
                return handleInvalidRequest(requestData);
            }

            saveIntegrationCall(requestData);
            updateTransactionStatus(requestData);
            saveDecisionHistory(requestData);

		
		  if ("Authorised".equalsIgnoreCase(requestData.getTransactionStatus()) || "DISCARD".equalsIgnoreCase(requestData.getTransactionStatus())
			  || "Verified".equalsIgnoreCase(requestData.getTransactionStatus())) {
		     generateEventTransaction(requestData.getClientReferenceID());
		   }
		 

            return buildSuccessResponse();

        } catch (Exception e) {
            logger.error("Internal server error occurred  {}", e);
            return buildErrorResponse(ERROR_CODE_INTERNAL_SERVER, "WMS Error Message Technical Error. Contact ADCB IT helpdesk: " + e.getMessage());
        }
    }

    private void initializeSession() throws Exception {
        LapsConfigurations.getInstance().loadConfiguration();
        SingleUserConnection instance = SingleUserConnection.getInstance(1000);
        sessionid = instance.getSession(
                LapsConfigurations.getInstance().CabinetName,
                LapsConfigurations.getInstance().UserName,
                LapsConfigurations.getInstance().Password);
    }

    private void logRequestData(RequestData requestData) {
        logger.info("Received request: ClientReferenceID={}", requestData.getClientReferenceID());
        logger.info("Received request: LcNumber={}", requestData.getLcNumber());
        logger.info("Received request: ModifiedBy={}", requestData.getModifiedBy());
        logger.info("Received request: ModifiedDate={}", requestData.getModifiedDate());
        logger.info("Received request: TransactionID={}", requestData.getTransactionID());
        logger.info("Received request: TransactionStatus={}", requestData.getTransactionStatus());
        logger.info("Received request: TransactionType={}", requestData.getTransactionType());
    }

    private boolean isRequestDataValid(RequestData requestData) {
        return !(requestData.getModifiedBy() == null || requestData.getModifiedBy().trim().isEmpty() ||
                requestData.getModifiedDate() == null || requestData.getModifiedDate().trim().isEmpty() ||
                requestData.getTransactionID() == null || requestData.getTransactionID().trim().isEmpty() ||
                requestData.getTransactionStatus() == null || requestData.getTransactionStatus().trim().isEmpty() ||
                requestData.getTransactionType() == null || requestData.getTransactionType().trim().isEmpty() ||
                requestData.getClientReferenceID() == null || requestData.getClientReferenceID().trim().isEmpty());
    }

    private ResponseEntity<?> handleInvalidRequest(RequestData requestData) {
        logger.error("Validation failed for: {}", requestData);
        saveIntegrationCall(requestData);
        return buildErrorResponse(ERROR_CODE_BAD_REQUEST, "Bad or Missing Request Parameters");
    }


    private void updateTransactionStatus(RequestData requestData) {
        try {
			APCallCreateXML.APUpdate("EXT_TFO", "TSSTATUS", "'"+requestData.getTransactionStatus()+"'", " WI_NAME = '" + requestData.getClientReferenceID() + "'", sessionid);
		} catch (NGException e) {
		      logger.error("Error in updateTransactionStatus {}", e);
		}
    }

    private void saveDecisionHistory(RequestData requestData) {
        String valList = "'"+requestData.getClientReferenceID()+"','"+requestData.getModifiedBy().trim()+"',sysdate,'"+requestData.getModifiedBy().trim()+"','TSservice InquiryStatus',sysdate,'"+requestData.getTransactionStatus()+"'";
        try {
			APCallCreateXML.APInsert("TFO_DJ_DEC_HIST", "WI_NAME,USER_ID,CREATE_DATE,USERNAME,ACTION,TRANSACTION_DATE_TIME,REMARKS", valList, sessionid);
		} catch (NGException e) {
			 logger.error("Error in saveDecisionHistory {}", e);
		}
    }

    private void generateEventTransaction(String wiName) {
        String valList = "SYSDATE,'"+ wiName +"',SYSDATE,'N','TRAYDSTREAM','TRAYDSTREAM','C'";
        try {
			APCallCreateXML.APInsert("BPM_EVENTGEN_TXN_DATA", "INSERTIONDATETIME, WI_NAME, EXPIRY_DATE, STATUS_FLAG, PROCESS_NAME, SOURCING_CHANNEL, REQUESTMODE", valList, sessionid);
		} catch (NGException e) {
			 logger.error("Error in generateEventTransaction {}", e);
		}
    }

    private void saveIntegrationCall(RequestData requestData) {
    	String valList = "'"+ requestData.getClientReferenceID() +"','TSService InquiryStatus',sysdate, '"+  jsonRequestData +"'";
        try {
			APCallCreateXML.APInsert("TFO_DJ_INTEGRATION_CALLS_DTLS", "WI_NAME,CALL_NAME, REF_NUM, OUTPUT_XML", valList, sessionid);
		} catch (NGException e) {
			logger.error("Error in saveIntegrationCall {}", e);
		}
    }

    private ResponseEntity<ResponseWrapper> buildSuccessResponse() {
        ResponseData responseData = new ResponseData();
        responseData.setStatus("success");
        responseData.setMessage("Successfully Updated the Status.");

        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setData(responseData);

        logger.info("Request successfully processed: {}", responseWrapper);
        return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(String code, String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(code);
        errorResponse.setId(UUID.randomUUID().toString());
        errorResponse.setMessage(message);

        List<ErrorResponse.ErrorDetail> errors = new ArrayList<>();
        errors.add(new ErrorResponse.ErrorDetail("WMSErrorCode", "WMS Error Message Technical Error. Contact ADCB IT helpdesk", "", ""));
        errorResponse.setErrors(errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
