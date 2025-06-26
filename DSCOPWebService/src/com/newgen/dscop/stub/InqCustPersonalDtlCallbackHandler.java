
/**
 * InqCustPersonalDtlCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.dscop.stub;

    /**
     *  InqCustPersonalDtlCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class InqCustPersonalDtlCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public InqCustPersonalDtlCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public InqCustPersonalDtlCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for inqCustPersonalDtl_Oper method
            * override this method for handling normal response from inqCustPersonalDtl_Oper operation
            */
           public void receiveResultinqCustPersonalDtl_Oper(
                    com.newgen.dscop.stub.InqCustPersonalDtlStub.GetCustPersonalDetailResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from inqCustPersonalDtl_Oper operation
           */
            public void receiveErrorinqCustPersonalDtl_Oper(java.lang.Exception e) {
            }
                


    }
    