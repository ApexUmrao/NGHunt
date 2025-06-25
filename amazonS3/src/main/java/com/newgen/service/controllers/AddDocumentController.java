package com.newgen.service.controllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newgen.service.AddDocumentServiceApplication;
import com.newgen.service.entities.AddDocumentResponse;
import com.newgen.service.entities.FinalAddDocumentRequest;

@RestController
//@RequestMapping("/document")
public class AddDocumentController {
	
	private static final Logger logger = LogManager.getLogger(AddDocumentServiceApplication.class);
	
	@GetMapping("/test")
	public String hello() {
		return "Hello, this is a test Rest API Using Spring Boot Application For Add Document Service";
	}
	
	//Creating add document endpoints
//	@PostMapping("/adddoc")
//	public ResponseEntity<AddDocumentResponse> addDocument(@RequestBody Document docs) {
//		/*return "Hello, this is a test api, doc_id= " + docs.getDoc_id() + " doc_name= " + docs.getDoc_name()
//				+ "doc_type= " + docs.getDoc_type() + "doc_size= " + docs.getDoc_size();*/
//		//return new ResponseEntity<>(docs.getDoc_name()+" Added Successfully",HttpStatus.OK);
//		AddDocumentResponse resp=new AddDocumentResponse();
//		resp.setDescription("Document Added Successfully!!!");
//		resp.setStatus("200");
//		resp.setMessage("success");
//		return ResponseEntity.status(HttpStatus.OK).body(resp);
//	}
	
	//Test endpoints
	@PostMapping("/UploadDocument")
	public ResponseEntity<AddDocumentResponse> adddoc(@RequestBody FinalAddDocumentRequest docs) {
		logger.info("Endpoints: UploadDocument Inside adddoc method!!!");
		/*return "Hello, this is a test api, doc_id= " + docs.getDoc_id() + " doc_name= " + docs.getDoc_name()
				+ "doc_type= " + docs.getDoc_type() + "doc_size= " + docs.getDoc_size();*/
		//return new ResponseEntity<>(docs.getDoc_name()+" Added Successfully",HttpStatus.OK);
		ObjectMapper op=new ObjectMapper();
		AddDocumentResponse resp=new AddDocumentResponse();
		try {
			String string_docs=op.writeValueAsString(docs);
			String Document = docs.getAddDocumentReq().getDocuments().getDocType();
			System.out.println("Request>>>>>> "+string_docs);
			logger.info("Request>>>>>> "+string_docs);
			resp.setDescription(Document+" Added Successfully!!!");
			resp.setStatus("100");
			resp.setMessage("Success");
			resp.setDocIndex("43467");
			logger.info("Response>>>>>> "+op.writeValueAsString(resp));
		} catch (JsonProcessingException e) {
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}

}


//localhost:8080/document/hello