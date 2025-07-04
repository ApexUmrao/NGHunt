
/**
 * ModCCOnlineOpsCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.dscop.stub;

    /**
     *  ModCCOnlineOpsCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ModCCOnlineOpsCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ModCCOnlineOpsCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ModCCOnlineOpsCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for updateCCBlockStatus_Oper method
            * override this method for handling normal response from updateCCBlockStatus_Oper operation
            */
           public void receiveResultupdateCCBlockStatus_Oper(
                    com.newgen.dscop.stub.ModCCOnlineOpsStub.UpdateCCBlockStatusResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateCCBlockStatus_Oper operation
           */
            public void receiveErrorupdateCCBlockStatus_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateCCTempLimit_Oper method
            * override this method for handling normal response from updateCCTempLimit_Oper operation
            */
           public void receiveResultupdateCCTempLimit_Oper(
                    com.newgen.dscop.stub.ModCCOnlineOpsStub.UpdateCCTempLimitResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateCCTempLimit_Oper operation
           */
            public void receiveErrorupdateCCTempLimit_Oper(java.lang.Exception e) {
            }
                


    }
    