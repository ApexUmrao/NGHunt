
/**
 * AddCBRequestCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:22:40 CEST)
 */

    package com.newgen.dscop.stub;

    /**
     *  AddCBRequestCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class AddCBRequestCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public AddCBRequestCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public AddCBRequestCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for addCBRequest_Oper method
            * override this method for handling normal response from addCBRequest_Oper operation
            */
           public void receiveResultaddCBRequest_Oper(
                    com.newgen.dscop.stub.AddCBRequestStub.SRChequeBookResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addCBRequest_Oper operation
           */
            public void receiveErroraddCBRequest_Oper(java.lang.Exception e) {
            }
                


    }
    