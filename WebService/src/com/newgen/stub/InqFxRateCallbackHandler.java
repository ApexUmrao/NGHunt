
/**
 * InqFxRateCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */

    package com.newgen.stub;

    /**
     *  InqFxRateCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class InqFxRateCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public InqFxRateCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public InqFxRateCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for inqFxRate_Oper method
            * override this method for handling normal response from inqFxRate_Oper operation
            */
           public void receiveResultinqFxRate_Oper(
                    com.newgen.stub.InqFxRateStub.GetFxRatesResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from inqFxRate_Oper operation
           */
            public void receiveErrorinqFxRate_Oper(java.lang.Exception e) {
            }
                


    }
    