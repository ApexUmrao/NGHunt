
/**
 * AddOnlineAppDataCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.dscop.stub;

    /**
     *  AddOnlineAppDataCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class AddOnlineAppDataCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public AddOnlineAppDataCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public AddOnlineAppDataCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for addOnlineCardAppData_Oper method
            * override this method for handling normal response from addOnlineCardAppData_Oper operation
            */
           public void receiveResultaddOnlineCardAppData_Oper(
                    com.newgen.dscop.stub.AddOnlineAppDataStub.AddOnlineCardAppDataResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addOnlineCardAppData_Oper operation
           */
            public void receiveErroraddOnlineCardAppData_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addOnlineLendingApplicationDtls_Oper method
            * override this method for handling normal response from addOnlineLendingApplicationDtls_Oper operation
            */
           public void receiveResultaddOnlineLendingApplicationDtls_Oper(
                    com.newgen.dscop.stub.AddOnlineAppDataStub.AddOnlineLendingApplicationDtlsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addOnlineLendingApplicationDtls_Oper operation
           */
            public void receiveErroraddOnlineLendingApplicationDtls_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addOnlineAppData_Oper method
            * override this method for handling normal response from addOnlineAppData_Oper operation
            */
           public void receiveResultaddOnlineAppData_Oper(
                    com.newgen.dscop.stub.AddOnlineAppDataStub.AddOnlineAppDataResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addOnlineAppData_Oper operation
           */
            public void receiveErroraddOnlineAppData_Oper(java.lang.Exception e) {
            }
                


    }
    