package com.newgen.raroc.rarocController;

import com.newgen.raroc.service.CreateNewWiService;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.newgen.raroc.entities.RarocApplicationResponse;
import com.newgen.raroc.service.RarocApplicationService;


@RestController
public class CreateController {
	
	private static final Logger logger = LogManager.getLogger(CreateController.class);

    @Autowired
    CreateNewWiService createNewWiService;

    @GetMapping("/createWI")
    public String alert() {
    	logger.info("Checking logger");
        return "Welcome to the RAROC Application";
    }

    RarocApplicationResponse response = new RarocApplicationResponse(); /// constructor for response handling

    @Autowired
    RarocApplicationService rarocapplicationservice;

    //Logger logger = LoggerFactory.getLogger(CreateController.class);



//    @PostMapping("/createWI")
//    public RarocApplicationResponse uploadDoc(@RequestBody RarocApplicationRequest request) {
////		request.getSourcingChannel();
//        System.out.print("request.getSourcingChannel()" + request.getSessionID());
//        response = rarocapplicationservice.createWorkitem(request);
//        return response;
//    }


    @PostMapping("/createNewWI")
    public String createNewWI(@RequestParam("sourceWI") String sourceWI, @RequestParam("processdefID") String processdefID
            , @RequestParam("sessionID") String sessionID) {
    	logger.info("attributes" + sourceWI);
       return createNewWiService.createNewWorkItem(processdefID, sourceWI, sessionID);
    }


}
