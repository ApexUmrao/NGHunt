
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
    public abstract class InqCBGCustomerOnboardingCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public InqCBGCustomerOnboardingCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public InqCBGCustomerOnboardingCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for fetchCustomerScore_Oper method
            * override this method for handling normal response from fetchCustomerScore_Oper operation
            */
           public void receiveResultfetchCustomerScore_Oper(
                    com.newgen.dscop.stub.InqCBGCustomerOnboardingStub.FetchCustomerScoreResMsg result
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
                    com.newgen.dscop.stub.InqCBGCustomerOnboardingStub.ValidateEmiratesIDResMsg result
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
                    com.newgen.dscop.stub.InqCBGCustomerOnboardingStub.FetchBPMWorkItemDetailsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchBPMWorkItemDetails_Oper operation
           */
            public void receiveErrorfetchBPMWorkItemDetails_Oper(java.lang.Exception e) {
            }
                


    }
    