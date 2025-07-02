package com.newgen.raroc.service;

import org.springframework.stereotype.Service;

import com.newgen.omni.wf.util.app.NGEjbClient;
//import com.newgen.raroc.util.LapsModifyLogger;
//
//import com.newgen.raroc.util.SingleUserConnection;
import com.newgen.raroc.entities.RarocApplicationRequest;
import com.newgen.raroc.entities.RarocApplicationResponse;

@Service
public class RarocApplicationService {
    String sessionId;
    RarocApplicationResponse response = new RarocApplicationResponse();

    public RarocApplicationResponse createWorkitem(RarocApplicationRequest req) {
        String outputXML = "";
        try {


            NGEjbClient objNGEjbClient = null;
            String IP = "10.101.109.178";
            String port = "2222";
            String appName = "WebSphere";


            if (objNGEjbClient == null) {
                objNGEjbClient = NGEjbClient.getSharedInstance();
                objNGEjbClient.initialize(IP, port, appName);
            }


            System.out.print("RAROC APP");

            String sInputXML = "<WFUploadWorkItem_Input>"
                    + "<Option>WFUploadWorkItem</Option>"
                    + "<SessionId>" + req.getSessionID() + "</SessionId>"
                    + "<EngineName>WMSUPGUAT</EngineName>"
                    + "<ProcessDefId>9</ProcessDefId"
                    + "<InitiateAlso>N</InitiateAlso>"
                    + "<ValidationRequired></ValidationRequired>"
                    + "<DataDefName></DataDefName>"
                    + "<InitiateFromActivityId>1</InitiateFromActivityId>"
                    + "<InitiateFromActivityName>INITIATE_APPLICATION</InitiateFromActivityName>"
                    + "<Attributes></Attributes>"
                    + "</WFUploadWorkItem_Input>";
            System.out.print("RAROC InputXML" + sInputXML);


            outputXML = objNGEjbClient.makeCall(sInputXML);
            System.out.println("Final OutputXML in SPringBoot 04032024" + outputXML);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in Springboot 04032024" + e.getMessage());

        } finally {
        }
        return response;
    }
}
