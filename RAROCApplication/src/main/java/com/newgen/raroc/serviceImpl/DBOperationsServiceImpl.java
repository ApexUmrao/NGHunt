package com.newgen.raroc.serviceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.newgen.omni.wf.util.excp.NGException;
import com.newgen.raroc.entities.InsertDataRequest;
import com.newgen.raroc.entities.UpdateDataRequest;
import com.newgen.raroc.service.DBOperationsService;
import com.newgen.raroc.util.APCallCreateXML;

@Component
public class DBOperationsServiceImpl implements DBOperationsService {


    @Value("${NgEjbClientIp}")
    private String IP;

    @Value("${NgEjbClientPort}")
    private String port;

    @Value("${NgEjbClientappName}")
    private String appName;

    @Value("${cabinetName}")
    private String cabinetName;

    @Override
    public String selectApi(String query) throws NGException {
        APCallCreateXML.init(IP,port,appName);
        return APCallCreateXML.APSelect(query, cabinetName);
    }

    @Override
    public String updateApi(UpdateDataRequest updateDataRequest) throws NGException {
        APCallCreateXML.init(IP,port,appName);
        return APCallCreateXML.APUpdate(updateDataRequest.getTableName(),updateDataRequest.getColumnName(),
                updateDataRequest.getStrValues(),updateDataRequest.getWhereClause(),updateDataRequest.getSessionId(), cabinetName);
    }

    @Override
    public String insertApi(InsertDataRequest insertDataRequest)  throws NGException {
        APCallCreateXML.init(IP,port,appName);
        return APCallCreateXML.APInsert(insertDataRequest.getTableName(),insertDataRequest.getColumnName(),
                insertDataRequest.getStrValues(),insertDataRequest.getSessionId(), cabinetName);
    }


}
