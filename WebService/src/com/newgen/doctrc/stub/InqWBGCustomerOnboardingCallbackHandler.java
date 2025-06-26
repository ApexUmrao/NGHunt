
/**
 * InqWBGCustomerOnboardingCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.doctrc.stub;

    /**
     *  InqWBGCustomerOnboardingCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class InqWBGCustomerOnboardingCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public InqWBGCustomerOnboardingCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public InqWBGCustomerOnboardingCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for fetchDocumentTrackerDtls_Oper method
            * override this method for handling normal response from fetchDocumentTrackerDtls_Oper operation
            */
           public void receiveResultfetchDocumentTrackerDtls_Oper(
                    com.newgen.doctrc.stub.InqWBGCustomerOnboardingStub.FetchDocumentTrackerDtlsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchDocumentTrackerDtls_Oper operation
           */
            public void receiveErrorfetchDocumentTrackerDtls_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchWBGLeadDetails_Oper method
            * override this method for handling normal response from fetchWBGLeadDetails_Oper operation
            */
           public void receiveResultfetchWBGLeadDetails_Oper(
                    com.newgen.doctrc.stub.InqWBGCustomerOnboardingStub.FetchWBGLeadDetailsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchWBGLeadDetails_Oper operation
           */
            public void receiveErrorfetchWBGLeadDetails_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for validateWBGLeadRefNum_Oper method
            * override this method for handling normal response from validateWBGLeadRefNum_Oper operation
            */
           public void receiveResultvalidateWBGLeadRefNum_Oper(
                    com.newgen.doctrc.stub.InqWBGCustomerOnboardingStub.ValidateWBGLeadRefNumResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from validateWBGLeadRefNum_Oper operation
           */
            public void receiveErrorvalidateWBGLeadRefNum_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchWBGApplicationStatus_Oper method
            * override this method for handling normal response from fetchWBGApplicationStatus_Oper operation
            */
           public void receiveResultfetchWBGApplicationStatus_Oper(
                    com.newgen.doctrc.stub.InqWBGCustomerOnboardingStub.FetchWBGApplicationStatusResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchWBGApplicationStatus_Oper operation
           */
            public void receiveErrorfetchWBGApplicationStatus_Oper(java.lang.Exception e) {
            }
                


    }
    