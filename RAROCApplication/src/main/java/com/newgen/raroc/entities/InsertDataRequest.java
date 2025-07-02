package com.newgen.raroc.entities;

public class InsertDataRequest {
    public String tableName;
    public String columnName;
    public String strValues;
    public String sessionId;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getStrValues() {
        return strValues;
    }

    public void setStrValues(String strValues) {
        this.strValues = strValues;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
