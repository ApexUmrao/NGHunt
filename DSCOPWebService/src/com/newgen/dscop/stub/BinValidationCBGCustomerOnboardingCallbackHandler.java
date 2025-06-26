
/**
 * InqCBGCustomerOnboardingCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.dscop.stub;

    /**
     *  InqCBGCustomerOnboardingCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class BinValidationCBGCustomerOnboardingCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public BinValidationCBGCustomerOnboardingCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public BinValidationCBGCustomerOnboardingCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for fetchIslamicCommodityDtls_Oper method
            * override this method for handling normal response from fetchIslamicCommodityDtls_Oper operation
            */
           public void receiveResultfetchIslamicCommodityDtls_Oper(
                    com.newgen.dscop.stub.BinValidationInqCBGCustomerOnboardingStub.FetchIslamicCommodityDtlsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchIslamicCommodityDtls_Oper operation
           */
            public void receiveErrorfetchIslamicCommodityDtls_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchUserDetails_Oper method
            * override this method for handling normal response from fetchUserDetails_Oper operation
            */
           public void receiveResultfetchUserDetails_Oper(
                    com.newgen.dscop.stub.BinValidationInqCBGCustomerOnboardingStub.FetchUserDetailsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchUserDetails_Oper operation
           */
            public void receiveErrorfetchUserDetails_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchCustomerScore_Oper method
            * override this method for handling normal response from fetchCustomerScore_Oper operation
            */
           public void receiveResultfetchCustomerScore_Oper(
                    com.newgen.dscop.stub.BinValidationInqCBGCustomerOnboardingStub.FetchCustomerScoreResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchCustomerScore_Oper operation
           */
            public void receiveErrorfetchCustomerScore_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for validateEmiratesID_Oper method
            * override this method for handling normal response from validateEmiratesID_Oper operation
            */
           public void receiveResultvalidateEmiratesID_Oper(
                    com.newgen.dscop.stub.BinValidationInqCBGCustomerOnboardingStub.ValidateEmiratesIDResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from validateEmiratesID_Oper operation
           */
            public void receiveErrorvalidateEmiratesID_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchBPMWorkItemDetails_Oper method
            * override this method for handling normal response from fetchBPMWorkItemDetails_Oper operation
            */
           public void receiveResultfetchBPMWorkItemDetails_Oper(
                    com.newgen.dscop.stub.BinValidationInqCBGCustomerOnboardingStub.FetchBPMWorkItemDetailsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchBPMWorkItemDetails_Oper operation
           */
            public void receiveErrorfetchBPMWorkItemDetails_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for validateUAEDebitCardBIN_Oper method
            * override this method for handling normal response from validateUAEDebitCardBIN_Oper operation
            */
           public void receiveResultvalidateUAEDebitCardBIN_Oper(
                    com.newgen.dscop.stub.BinValidationInqCBGCustomerOnboardingStub.ValidateUAEDebitCardBINResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from validateUAEDebitCardBIN_Oper operation
           */
            public void receiveErrorvalidateUAEDebitCardBIN_Oper(java.lang.Exception e) {
            }
                


    }
    