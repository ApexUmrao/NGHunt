
/**
 * ModCampaignDtlsCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.dscop.stub;

    /**
     *  ModCampaignDtlsCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ModCampaignDtlsCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ModCampaignDtlsCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ModCampaignDtlsCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for fetchRMCampaignDtls_Oper method
            * override this method for handling normal response from fetchRMCampaignDtls_Oper operation
            */
           public void receiveResultfetchRMCampaignDtls_Oper(
                    com.newgen.dscop.stub.ModCampaignDtlsStub.FetchRMCampaignDtlsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchRMCampaignDtls_Oper operation
           */
            public void receiveErrorfetchRMCampaignDtls_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchCustCampaignDtls_Oper method
            * override this method for handling normal response from fetchCustCampaignDtls_Oper operation
            */
           public void receiveResultfetchCustCampaignDtls_Oper(
                    com.newgen.dscop.stub.ModCampaignDtlsStub.FetchCustCampaignDtlsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchCustCampaignDtls_Oper operation
           */
            public void receiveErrorfetchCustCampaignDtls_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addCampaignDtls_Oper method
            * override this method for handling normal response from addCampaignDtls_Oper operation
            */
           public void receiveResultaddCampaignDtls_Oper(
                    com.newgen.dscop.stub.ModCampaignDtlsStub.AddCampaignDtlsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addCampaignDtls_Oper operation
           */
            public void receiveErroraddCampaignDtls_Oper(java.lang.Exception e) {
            }
                


    }
    