
/**
 * InqCustApplicationDtlCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.dscop.stub;

    /**
     *  InqCustApplicationDtlCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class InqCustApplicationDtlCallbackHandler{
    	
    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public InqCustApplicationDtlCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public InqCustApplicationDtlCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for inqCustApplicationDtl_Oper method
            * override this method for handling normal response from inqCustApplicationDtl_Oper operation
            */
           public void receiveResultinqCustApplicationDtl_Oper(
                    com.newgen.dscop.stub.InqCustApplicationDtlStub.GetPendingApplicationDetailsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from inqCustApplicationDtl_Oper operation
           */
            public void receiveErrorinqCustApplicationDtl_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchApplicationData_Oper method
            * override this method for handling normal response from fetchApplicationData_Oper operation
            */
           public void receiveResultfetchApplicationData_Oper(
                    com.newgen.dscop.stub.InqCustApplicationDtlStub.FetchApplicationDataResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchApplicationData_Oper operation
           */
            public void receiveErrorfetchApplicationData_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createCCPSApplication_Oper method
            * override this method for handling normal response from createCCPSApplication_Oper operation
            */
           public void receiveResultcreateCCPSApplication_Oper(
                    com.newgen.dscop.stub.InqCustApplicationDtlStub.CreateCCPSApplicationResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createCCPSApplication_Oper operation
           */
            public void receiveErrorcreateCCPSApplication_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateCCPSAppStatus_Oper method
            * override this method for handling normal response from updateCCPSAppStatus_Oper operation
            */
           public void receiveResultupdateCCPSAppStatus_Oper(
                    com.newgen.dscop.stub.InqCustApplicationDtlStub.UpdateCCPSAppStatusResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateCCPSAppStatus_Oper operation
           */
            public void receiveErrorupdateCCPSAppStatus_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchAdditionalAppDetails_Oper method
            * override this method for handling normal response from fetchAdditionalAppDetails_Oper operation
            */
           public void receiveResultfetchAdditionalAppDetails_Oper(
                    com.newgen.dscop.stub.InqCustApplicationDtlStub.FetchAdditionalAppDetailsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchAdditionalAppDetails_Oper operation
           */
            public void receiveErrorfetchAdditionalAppDetails_Oper(java.lang.Exception e) {
            }
                


    }
    