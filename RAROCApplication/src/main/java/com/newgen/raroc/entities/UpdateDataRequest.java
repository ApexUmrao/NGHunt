package com.newgen.raroc.entities;

import lombok.Data;

@Data
public class UpdateDataRequest {
    public String tableName;
    public String columnName;
    public String strValues;
    public String whereClause;
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

    public String getWhereClause() {
        return whereClause;
    }

    public void setWhereClause(String whereClause) {
        this.whereClause = whereClause;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
