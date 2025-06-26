
/**
 * AddCSAcctAmountHoldCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.dscop.stub;

    /**
     *  AddCSAcctAmountHoldCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class AddCSAcctAmountHoldCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public AddCSAcctAmountHoldCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public AddCSAcctAmountHoldCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for addCSAcctAmountUnHold_Oper method
            * override this method for handling normal response from addCSAcctAmountUnHold_Oper operation
            */
           public void receiveResultaddCSAcctAmountUnHold_Oper(
                    com.newgen.dscop.stub.AddCSAcctAmountHoldStub.AddCSAcctAmountUnHoldResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addCSAcctAmountUnHold_Oper operation
           */
            public void receiveErroraddCSAcctAmountUnHold_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addCSAcctAmountHold_Oper method
            * override this method for handling normal response from addCSAcctAmountHold_Oper operation
            */
           public void receiveResultaddCSAcctAmountHold_Oper(
                    com.newgen.dscop.stub.AddCSAcctAmountHoldStub.AddCSAcctAmountHoldResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addCSAcctAmountHold_Oper operation
           */
            public void receiveErroraddCSAcctAmountHold_Oper(java.lang.Exception e) {
            }
                


    }
    