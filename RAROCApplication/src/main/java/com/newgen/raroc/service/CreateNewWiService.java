package com.newgen.raroc.service;


import org.springframework.stereotype.Service;


@Service
public interface CreateNewWiService {
    public String createNewWorkItem(String processDefID, String sourceWI, String sessionID);//Added By Rishabh
}
