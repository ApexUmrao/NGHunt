
/**
 * InqCustomerDigitalLendingCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.dscop.stub;

    /**
     *  InqCustomerDigitalLendingCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class InqCustomerDigitalLendingCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public InqCustomerDigitalLendingCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public InqCustomerDigitalLendingCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for fetchCustSalaryAdditionalDtls_Oper method
            * override this method for handling normal response from fetchCustSalaryAdditionalDtls_Oper operation
            */
           public void receiveResultfetchCustSalaryAdditionalDtls_Oper(
                    com.newgen.dscop.stub.InqCustomerDigitalLendingStub.FetchCustSalaryAdditionalDtlsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchCustSalaryAdditionalDtls_Oper operation
           */
            public void receiveErrorfetchCustSalaryAdditionalDtls_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchWorkItemStatus_Oper method
            * override this method for handling normal response from fetchWorkItemStatus_Oper operation
            */
           public void receiveResultfetchWorkItemStatus_Oper(
                    com.newgen.dscop.stub.InqCustomerDigitalLendingStub.FetchWorkItemStatusResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchWorkItemStatus_Oper operation
           */
            public void receiveErrorfetchWorkItemStatus_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchCustSalaryDtls_Oper method
            * override this method for handling normal response from fetchCustSalaryDtls_Oper operation
            */
           public void receiveResultfetchCustSalaryDtls_Oper(
                    com.newgen.dscop.stub.InqCustomerDigitalLendingStub.FetchCustSalaryDtlsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchCustSalaryDtls_Oper operation
           */
            public void receiveErrorfetchCustSalaryDtls_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchCustBScore_Oper method
            * override this method for handling normal response from fetchCustBScore_Oper operation
            */
           public void receiveResultfetchCustBScore_Oper(
                    com.newgen.dscop.stub.InqCustomerDigitalLendingStub.FetchCustBScoreResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchCustBScore_Oper operation
           */
            public void receiveErrorfetchCustBScore_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchSupplCardHolderDtls_Oper method
            * override this method for handling normal response from fetchSupplCardHolderDtls_Oper operation
            */
           public void receiveResultfetchSupplCardHolderDtls_Oper(
                    com.newgen.dscop.stub.InqCustomerDigitalLendingStub.FetchSupplCardHoldeDtlsrResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchSupplCardHolderDtls_Oper operation
           */
            public void receiveErrorfetchSupplCardHolderDtls_Oper(java.lang.Exception e) {
            }
                


    }
    