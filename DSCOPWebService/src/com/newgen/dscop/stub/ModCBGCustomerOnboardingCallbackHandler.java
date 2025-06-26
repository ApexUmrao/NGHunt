
/**
 * ModCBGCustomerOnboardingCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.dscop.stub;

    /**
     *  ModCBGCustomerOnboardingCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ModCBGCustomerOnboardingCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ModCBGCustomerOnboardingCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ModCBGCustomerOnboardingCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for addDigitalLendingApplication_Oper method
            * override this method for handling normal response from addDigitalLendingApplication_Oper operation
            */
           public void receiveResultaddDigitalLendingApplication_Oper(
                    com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.AddDigitalLendingApplicationResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addDigitalLendingApplication_Oper operation
           */
            public void receiveErroraddDigitalLendingApplication_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateWorkItemExpiryDtls_Oper method
            * override this method for handling normal response from updateWorkItemExpiryDtls_Oper operation
            */
           public void receiveResultupdateWorkItemExpiryDtls_Oper(
                    com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.UpdateWorkItemExpiryDtlsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateWorkItemExpiryDtls_Oper operation
           */
            public void receiveErrorupdateWorkItemExpiryDtls_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addOnlineLead_Oper method
            * override this method for handling normal response from addOnlineLead_Oper operation
            */
           public void receiveResultaddOnlineLead_Oper(
                    com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.AddOnlineLeadResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addOnlineLead_Oper operation
           */
            public void receiveErroraddOnlineLead_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addCBGAppRegistration_Oper method
            * override this method for handling normal response from addCBGAppRegistration_Oper operation
            */
           public void receiveResultaddCBGAppRegistration_Oper(
                    com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.AddCBGAppRegistrationResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addCBGAppRegistration_Oper operation
           */
            public void receiveErroraddCBGAppRegistration_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for removeCardPrintHold_Oper method
            * override this method for handling normal response from removeCardPrintHold_Oper operation
            */
           public void receiveResultremoveCardPrintHold_Oper(
                    com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.RemoveCardPrintHoldResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from removeCardPrintHold_Oper operation
           */
            public void receiveErrorremoveCardPrintHold_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateDeliveryStatus_Oper method
            * override this method for handling normal response from updateDeliveryStatus_Oper operation
            */
           public void receiveResultupdateDeliveryStatus_Oper(
                    com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.UpdateDeliveryStatusResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateDeliveryStatus_Oper operation
           */
            public void receiveErrorupdateDeliveryStatus_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addUserDetails_Oper method
            * override this method for handling normal response from addUserDetails_Oper operation
            */
           public void receiveResultaddUserDetails_Oper(
                    com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.AddUserDetailsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addUserDetails_Oper operation
           */
            public void receiveErroraddUserDetails_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addDeliveryDetails_Oper method
            * override this method for handling normal response from addDeliveryDetails_Oper operation
            */
           public void receiveResultaddDeliveryDetails_Oper(
                    com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.AddDeliveryDetailsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addDeliveryDetails_Oper operation
           */
            public void receiveErroraddDeliveryDetails_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateCardLimit_Oper method
            * override this method for handling normal response from updateCardLimit_Oper operation
            */
           public void receiveResultupdateCardLimit_Oper(
                    com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.UpdateCardLimitResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateCardLimit_Oper operation
           */
            public void receiveErrorupdateCardLimit_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateBPMWorkItemDetails_Oper method
            * override this method for handling normal response from updateBPMWorkItemDetails_Oper operation
            */
           public void receiveResultupdateBPMWorkItemDetails_Oper(
                    com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.UpdateBPMWorkItemDetailsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateBPMWorkItemDetails_Oper operation
           */
            public void receiveErrorupdateBPMWorkItemDetails_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addCardCCPSApplication_Oper method
            * override this method for handling normal response from addCardCCPSApplication_Oper operation
            */
           public void receiveResultaddCardCCPSApplication_Oper(
                    com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.AddCardCCPSApplicationResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addCardCCPSApplication_Oper operation
           */
            public void receiveErroraddCardCCPSApplication_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchDeliveryStatus_Oper method
            * override this method for handling normal response from fetchDeliveryStatus_Oper operation
            */
           public void receiveResultfetchDeliveryStatus_Oper(
                    com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.FetchDeliveryStatusResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchDeliveryStatus_Oper operation
           */
            public void receiveErrorfetchDeliveryStatus_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateCustomerNPSDtls_Oper method
            * override this method for handling normal response from updateCustomerNPSDtls_Oper operation
            */
           public void receiveResultupdateCustomerNPSDtls_Oper(
                    com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.UpdateCustomerNPSDtlsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateCustomerNPSDtls_Oper operation
           */
            public void receiveErrorupdateCustomerNPSDtls_Oper(java.lang.Exception e) {
            }
                


    }
    