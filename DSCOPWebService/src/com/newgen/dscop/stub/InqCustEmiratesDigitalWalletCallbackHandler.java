
/**
 * InqCustEmiratesDigitalWalletCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.dscop.stub;

    /**
     *  InqCustEmiratesDigitalWalletCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class InqCustEmiratesDigitalWalletCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public InqCustEmiratesDigitalWalletCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public InqCustEmiratesDigitalWalletCallbackHandler(){
        this.clientData  = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for fetchCustEmiratesDigitalWalletDtls_Oper method
            * override this method for handling normal response from fetchCustEmiratesDigitalWalletDtls_Oper operation
            */
           public void receiveResultfetchCustEmiratesDigitalWalletDtls_Oper(
                   com.newgen.dscop.stub.InqCustEmiratesDigitalWalletStub.FetchCustEmiratesDigitalWalletDtlsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchCustEmiratesDigitalWalletDtls_Oper operation
           */
            public void receiveErrorfetchCustEmiratesDigitalWalletDtls_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchCustEmiratesDigitalWalletDeviceDtls_Oper method
            * override this method for handling normal response from fetchCustEmiratesDigitalWalletDeviceDtls_Oper operation
            */
           public void receiveResultfetchCustEmiratesDigitalWalletDeviceDtls_Oper(
                   com.newgen.dscop.stub.InqCustEmiratesDigitalWalletStub.FetchCustEmiratesDigitalWalletDeviceDtlsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchCustEmiratesDigitalWalletDeviceDtls_Oper operation
           */
            public void receiveErrorfetchCustEmiratesDigitalWalletDeviceDtls_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchCustEmiratesDigitalWalletTxns_Oper method
            * override this method for handling normal response from fetchCustEmiratesDigitalWalletTxns_Oper operation
            */
           public void receiveResultfetchCustEmiratesDigitalWalletTxns_Oper(
                   com.newgen.dscop.stub.InqCustEmiratesDigitalWalletStub.FetchCustEmiratesDigitalWalletTxnsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchCustEmiratesDigitalWalletTxns_Oper operation
           */
            public void receiveErrorfetchCustEmiratesDigitalWalletTxns_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchReferenceDetails_Oper method
            * override this method for handling normal response from fetchReferenceDetails_Oper operation
            */
           public void receiveResultfetchReferenceDetails_Oper(
                   com.newgen.dscop.stub.InqCustEmiratesDigitalWalletStub.FetchReferenceDetailsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchReferenceDetails_Oper operation
           */
            public void receiveErrorfetchReferenceDetails_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchCustEmiratesDigitalWalletBenefDtls_Oper method
            * override this method for handling normal response from fetchCustEmiratesDigitalWalletBenefDtls_Oper operation
            */
           public void receiveResultfetchCustEmiratesDigitalWalletBenefDtls_Oper(
                   com.newgen.dscop.stub.InqCustEmiratesDigitalWalletStub.FetchCustEmiratesDigitalWalletBenefDtlsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchCustEmiratesDigitalWalletBenefDtls_Oper operation
           */
            public void receiveErrorfetchCustEmiratesDigitalWalletBenefDtls_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchCustEmiratesDigitalWalletBalance_Oper method
            * override this method for handling normal response from fetchCustEmiratesDigitalWalletBalance_Oper operation
            */
           public void receiveResultfetchCustEmiratesDigitalWalletBalance_Oper(
                   com.newgen.dscop.stub.InqCustEmiratesDigitalWalletStub.FetchCustEmiratesDigitalWalletBalanceResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchCustEmiratesDigitalWalletBalance_Oper operation
           */
            public void receiveErrorfetchCustEmiratesDigitalWalletBalance_Oper(java.lang.Exception e) {
            }
                


    }
    