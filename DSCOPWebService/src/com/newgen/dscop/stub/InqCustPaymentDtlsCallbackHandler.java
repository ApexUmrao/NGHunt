/**
 * InqCustPaymentDtlsCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.dscop.stub;

    /**
     *  InqCustPaymentDtlsCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class InqCustPaymentDtlsCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public InqCustPaymentDtlsCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public InqCustPaymentDtlsCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }
        
           /**
            * auto generated Axis2 call back method for fetchMessageDetails_Oper method
            * override this method for handling normal response from fetchMessageDetails_Oper operation
            */
           public void receiveResultfetchMessageDetails_Oper(
                    com.newgen.dscop.stub.InqCustPaymentDtlsStub.FetchMessageDetailsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchMessageDetails_Oper operation
           */
            public void receiveErrorfetchMessageDetails_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchDDSPaymentInfo_Oper method
            * override this method for handling normal response from fetchDDSPaymentInfo_Oper operation
            */
           public void receiveResultfetchDDSPaymentInfo_Oper(
                    com.newgen.dscop.stub.InqCustPaymentDtlsStub.FetchDDSPaymentInfoResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchDDSPaymentInfo_Oper operation
           */
            public void receiveErrorfetchDDSPaymentInfo_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchPaymentRefDtls_Oper method
            * override this method for handling normal response from fetchPaymentRefDtls_Oper operation
            */
           public void receiveResultfetchPaymentRefDtls_Oper(
                    com.newgen.dscop.stub.InqCustPaymentDtlsStub.FetchPaymentRefDtlsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchPaymentRefDtls_Oper operation
           */
            public void receiveErrorfetchPaymentRefDtls_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchChequeDtls_Oper method
            * override this method for handling normal response from fetchChequeDtls_Oper operation
            */
           public void receiveResultfetchChequeDtls_Oper(
                    com.newgen.dscop.stub.InqCustPaymentDtlsStub.FetchChequeDtlsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchChequeDtls_Oper operation
           */
            public void receiveErrorfetchChequeDtls_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchCorrespondenceDetails_Oper method
            * override this method for handling normal response from fetchCorrespondenceDetails_Oper operation
            */
           public void receiveResultfetchCorrespondenceDetails_Oper(
                    com.newgen.dscop.stub.InqCustPaymentDtlsStub.FetchCorrespondenceDetailsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchCorrespondenceDetails_Oper operation
           */
            public void receiveErrorfetchCorrespondenceDetails_Oper(java.lang.Exception e) {
            }
                


    }
    