
/**
 * ExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */

package com.newgen.stub;

public class ExceptionException extends java.lang.Exception{

    private static final long serialVersionUID = 1377612484405L;
    
    private com.newgen.stub.VersionStub.ExceptionE faultMessage;

    
        public ExceptionException() {
            super("ExceptionException");
        }

        public ExceptionException(java.lang.String s) {
           super(s);
        }

        public ExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public ExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.newgen.stub.VersionStub.ExceptionE msg){
       faultMessage = msg;
    }
    
    public com.newgen.stub.VersionStub.ExceptionE getFaultMessage(){
       return faultMessage;
    }
}
    