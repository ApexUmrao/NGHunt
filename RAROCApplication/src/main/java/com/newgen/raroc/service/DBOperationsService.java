package com.newgen.raroc.service;

import com.newgen.omni.wf.util.excp.NGException;
import com.newgen.raroc.entities.InsertDataRequest;
import com.newgen.raroc.entities.UpdateDataRequest;
import org.springframework.stereotype.Service;

@Service
public interface DBOperationsService {

    public String selectApi(String query)  throws NGException;

    public String updateApi(UpdateDataRequest updateDataRequest)  throws NGException;

    public String insertApi(InsertDataRequest insertDataRequest)  throws NGException;


}
