
/**
 * ModLoanDisbursementCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.dscop.stub;

    /**
     *  ModLoanDisbursementCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ModLoanDisbursementCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ModLoanDisbursementCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ModLoanDisbursementCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for addLoanRecord_Oper method
            * override this method for handling normal response from addLoanRecord_Oper operation
            */
           public void receiveResultaddLoanRecord_Oper(
                    com.newgen.dscop.stub.ModLoanDisbursementStub.AddLoanRecordResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addLoanRecord_Oper operation
           */
            public void receiveErroraddLoanRecord_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for cancelLoanRecord_Oper method
            * override this method for handling normal response from cancelLoanRecord_Oper operation
            */
           public void receiveResultcancelLoanRecord_Oper(
                    com.newgen.dscop.stub.ModLoanDisbursementStub.CancelLoanRecordResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from cancelLoanRecord_Oper operation
           */
            public void receiveErrorcancelLoanRecord_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getProductDetailsConv_Oper method
            * override this method for handling normal response from getProductDetailsConv_Oper operation
            */
           public void receiveResultgetProductDetailsConv_Oper(
                    com.newgen.dscop.stub.ModLoanDisbursementStub.GetProductDetailsConvResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getProductDetailsConv_Oper operation
           */
            public void receiveErrorgetProductDetailsConv_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addIslamicLoanDisb_Oper method
            * override this method for handling normal response from addIslamicLoanDisb_Oper operation
            */
           public void receiveResultaddIslamicLoanDisb_Oper(
                    com.newgen.dscop.stub.ModLoanDisbursementStub.AddIslamicLoanDisbResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addIslamicLoanDisb_Oper operation
           */
            public void receiveErroraddIslamicLoanDisb_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for cancelIslamicLoanRecord_Oper method
            * override this method for handling normal response from cancelIslamicLoanRecord_Oper operation
            */
           public void receiveResultcancelIslamicLoanRecord_Oper(
                    com.newgen.dscop.stub.ModLoanDisbursementStub.CancelIslamicLoanRecordResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from cancelIslamicLoanRecord_Oper operation
           */
            public void receiveErrorcancelIslamicLoanRecord_Oper(java.lang.Exception e) {
            }
                


    }
    