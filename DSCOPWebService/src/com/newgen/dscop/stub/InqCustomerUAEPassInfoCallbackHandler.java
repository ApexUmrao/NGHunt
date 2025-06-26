
/**
 * InqCustomerUAEPassInfoCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.dscop.stub;

    /**
     *  InqCustomerUAEPassInfoCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class InqCustomerUAEPassInfoCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public InqCustomerUAEPassInfoCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public InqCustomerUAEPassInfoCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for updateUAEPassDocumentDtls_Oper method
            * override this method for handling normal response from updateUAEPassDocumentDtls_Oper operation
            */
           public void receiveResultupdateUAEPassDocumentDtls_Oper(
                    com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.UpdateUAEPassDocumentDtlsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateUAEPassDocumentDtls_Oper operation
           */
            public void receiveErrorupdateUAEPassDocumentDtls_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchCustomerUAEPassInfo_Oper method
            * override this method for handling normal response from fetchCustomerUAEPassInfo_Oper operation
            */
           public void receiveResultfetchCustomerUAEPassInfo_Oper(
                    com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchCustomerUAEPassInfoResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchCustomerUAEPassInfo_Oper operation
           */
            public void receiveErrorfetchCustomerUAEPassInfo_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchUAEPassDocumentDtls_Oper method
            * override this method for handling normal response from fetchUAEPassDocumentDtls_Oper operation
            */
           public void receiveResultfetchUAEPassDocumentDtls_Oper(
                    com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchUAEPassDocumentDtlsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchUAEPassDocumentDtls_Oper operation
           */
            public void receiveErrorfetchUAEPassDocumentDtls_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for requestUAEPassDocuments_Oper method
            * override this method for handling normal response from requestUAEPassDocuments_Oper operation
            */
           public void receiveResultrequestUAEPassDocuments_Oper(
                    com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.RequestUARPassDocumentsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from requestUAEPassDocuments_Oper operation
           */
            public void receiveErrorrequestUAEPassDocuments_Oper(java.lang.Exception e) {
            }
                


    }
    