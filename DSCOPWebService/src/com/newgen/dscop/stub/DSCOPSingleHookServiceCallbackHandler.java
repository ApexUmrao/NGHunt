
/**
 * DSCOPSingleHookServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.dscop.stub;

    /**
     *  DSCOPSingleHookServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class DSCOPSingleHookServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public DSCOPSingleHookServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public DSCOPSingleHookServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for singleHook method
            * override this method for handling normal response from singleHook operation
            */
           public void receiveResultsingleHook(
                    com.newgen.dscop.stub.DSCOPSingleHookServiceStub.SingleHookResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from singleHook operation
           */
            public void receiveErrorsingleHook(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for bulkEnquiryBCA method
            * override this method for handling normal response from bulkEnquiryBCA operation
            */
           public void receiveResultbulkEnquiryBCA(
                    com.newgen.dscop.stub.DSCOPSingleHookServiceStub.BulkEnquiryBCAResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from bulkEnquiryBCA operation
           */
            public void receiveErrorbulkEnquiryBCA(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for bulkEnquiryAO method
            * override this method for handling normal response from bulkEnquiryAO operation
            */
           public void receiveResultbulkEnquiryAO(
                    com.newgen.dscop.stub.DSCOPSingleHookServiceStub.BulkEnquiryAOResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from bulkEnquiryAO operation
           */
            public void receiveErrorbulkEnquiryAO(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for bulkEnquiry method
            * override this method for handling normal response from bulkEnquiry operation
            */
           public void receiveResultbulkEnquiry(
                    com.newgen.dscop.stub.DSCOPSingleHookServiceStub.BulkEnquiryResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from bulkEnquiry operation
           */
            public void receiveErrorbulkEnquiry(java.lang.Exception e) {
            }
                


    }
    