
/**
 * ModCustomerConsentCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.dscop.stub;

    /**
     *  ModCustomerConsentCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ModCustomerConsentCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ModCustomerConsentCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ModCustomerConsentCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for addCustomerKFSConsent_Oper method
            * override this method for handling normal response from addCustomerKFSConsent_Oper operation
            */
           public void receiveResultaddCustomerKFSConsent_Oper(
                    com.newgen.dscop.stub.ModCustomerConsentStub.AddCustomerKFSConsentResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addCustomerKFSConsent_Oper operation
           */
            public void receiveErroraddCustomerKFSConsent_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addCustomerConsent_Oper method
            * override this method for handling normal response from addCustomerConsent_Oper operation
            */
           public void receiveResultaddCustomerConsent_Oper(
                    com.newgen.dscop.stub.ModCustomerConsentStub.AddCustomerConsentResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addCustomerConsent_Oper operation
           */
            public void receiveErroraddCustomerConsent_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchCustomerConsent_Oper method
            * override this method for handling normal response from fetchCustomerConsent_Oper operation
            */
           public void receiveResultfetchCustomerConsent_Oper(
                    com.newgen.dscop.stub.ModCustomerConsentStub.FetchCustomerConsentResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchCustomerConsent_Oper operation
           */
            public void receiveErrorfetchCustomerConsent_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchCustomerConsentHistory_Oper method
            * override this method for handling normal response from fetchCustomerConsentHistory_Oper operation
            */
           public void receiveResultfetchCustomerConsentHistory_Oper(
                    com.newgen.dscop.stub.ModCustomerConsentStub.FetchCustomerConsentHistoryResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchCustomerConsentHistory_Oper operation
           */
            public void receiveErrorfetchCustomerConsentHistory_Oper(java.lang.Exception e) {
            }
                


    }
    