
/**
 * AddDigiCompanyDtlsCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.dscop.stub;

    /**
     *  AddDigiCompanyDtlsCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class AddDigiCompanyDtlsCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public AddDigiCompanyDtlsCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public AddDigiCompanyDtlsCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for addAuthorizedSignatoryDetails_Oper method
            * override this method for handling normal response from addAuthorizedSignatoryDetails_Oper operation
            */
           public void receiveResultaddAuthorizedSignatoryDetails_Oper(
                    com.newgen.dscop.stub.AddDigiCompanyDtlsStub.AddAuthorizedSignatoryDetailsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addAuthorizedSignatoryDetails_Oper operation
           */
            public void receiveErroraddAuthorizedSignatoryDetails_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addCompanyDetails_Oper method
            * override this method for handling normal response from addCompanyDetails_Oper operation
            */
           public void receiveResultaddCompanyDetails_Oper(
                    com.newgen.dscop.stub.AddDigiCompanyDtlsStub.AddCompanyDetailsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addCompanyDetails_Oper operation
           */
            public void receiveErroraddCompanyDetails_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addCompanyLendingDetails_Oper method
            * override this method for handling normal response from addCompanyLendingDetails_Oper operation
            */
           public void receiveResultaddCompanyLendingDetails_Oper(
                    com.newgen.dscop.stub.AddDigiCompanyDtlsStub.AddCompanyLendingDetailsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addCompanyLendingDetails_Oper operation
           */
            public void receiveErroraddCompanyLendingDetails_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addOldCompanyDetails_Oper method
            * override this method for handling normal response from addOldCompanyDetails_Oper operation
            */
           public void receiveResultaddOldCompanyDetails_Oper(
                    com.newgen.dscop.stub.AddDigiCompanyDtlsStub.AddOldCompanyDetailsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addOldCompanyDetails_Oper operation
           */
            public void receiveErroraddOldCompanyDetails_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addCompanyDocumentDetails_Oper method
            * override this method for handling normal response from addCompanyDocumentDetails_Oper operation
            */
           public void receiveResultaddCompanyDocumentDetails_Oper(
                    com.newgen.dscop.stub.AddDigiCompanyDtlsStub.AddCompanyDocumentDetailsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addCompanyDocumentDetails_Oper operation
           */
            public void receiveErroraddCompanyDocumentDetails_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createCompanyNotification_Oper method
            * override this method for handling normal response from createCompanyNotification_Oper operation
            */
           public void receiveResultcreateCompanyNotification_Oper(
                    com.newgen.dscop.stub.AddDigiCompanyDtlsStub.CreateCompanyNotificationResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createCompanyNotification_Oper operation
           */
            public void receiveErrorcreateCompanyNotification_Oper(java.lang.Exception e) {
            }
                


    }
    