
/**
 * InqLoanOperationDtlCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.dscop.stub;

    /**
     *  InqLoanOperationDtlCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class InqLoanOperationDtlCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public InqLoanOperationDtlCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public InqLoanOperationDtlCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for inqLoanOperationDtl_Oper method
            * override this method for handling normal response from inqLoanOperationDtl_Oper operation
            */
           public void receiveResultinqLoanOperationDtl_Oper(
                    com.newgen.dscop.stub.InqLoanOperationDtlStub.GetLoanOperativeInfoResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from inqLoanOperationDtl_Oper operation
           */
            public void receiveErrorinqLoanOperationDtl_Oper(java.lang.Exception e) {
            }
                


    }
    