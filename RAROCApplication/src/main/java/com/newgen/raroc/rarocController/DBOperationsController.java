package com.newgen.raroc.rarocController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newgen.omni.wf.util.excp.NGException;
import com.newgen.raroc.entities.InsertDataRequest;
import com.newgen.raroc.entities.UpdateDataRequest;
import com.newgen.raroc.service.DBOperationsService;

@RestController
@RequestMapping("/dbOperations")
public class DBOperationsController {

    @Autowired
    DBOperationsService dbOperationsService;

    @GetMapping("/select")
    public String selectAPI(@RequestParam("query") String query) {
        try {
            return dbOperationsService.selectApi(query);
        } catch (NGException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/update")
    public String updateAPI(@RequestBody UpdateDataRequest updateDataRequest) {
        try {
            return dbOperationsService.updateApi(updateDataRequest);
        } catch (NGException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/insert")
    public String insertAPI(@RequestBody InsertDataRequest insertDataRequest) {
        try {
            return dbOperationsService.insertApi(insertDataRequest);
        } catch (NGException e) {
            throw new RuntimeException(e);
        }
    }
}
