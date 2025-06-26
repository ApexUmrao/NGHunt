
/**
 * ModCBStatementCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.dscop.stub;

    /**
     *  ModCBStatementCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ModCBStatementCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ModCBStatementCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ModCBStatementCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for fetchMISInformationDetails_Oper method
            * override this method for handling normal response from fetchMISInformationDetails_Oper operation
            */
           public void receiveResultfetchMISInformationDetails_Oper(
                    com.newgen.dscop.stub.ModCBStatementStub.FetchMISInformationDetailsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchMISInformationDetails_Oper operation
           */
            public void receiveErrorfetchMISInformationDetails_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchCustStatementDetails_Oper method
            * override this method for handling normal response from fetchCustStatementDetails_Oper operation
            */
           public void receiveResultfetchCustStatementDetails_Oper(
                    com.newgen.dscop.stub.ModCBStatementStub.FetchCustStatementDetailsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchCustStatementDetails_Oper operation
           */
            public void receiveErrorfetchCustStatementDetails_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchMISInfromationSummary_Oper method
            * override this method for handling normal response from fetchMISInfromationSummary_Oper operation
            */
           public void receiveResultfetchMISInfromationSummary_Oper(
                    com.newgen.dscop.stub.ModCBStatementStub.FetchMISInfromationSummaryResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchMISInfromationSummary_Oper operation
           */
            public void receiveErrorfetchMISInfromationSummary_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addCustStatementRequest_Oper method
            * override this method for handling normal response from addCustStatementRequest_Oper operation
            */
           public void receiveResultaddCustStatementRequest_Oper(
                    com.newgen.dscop.stub.ModCBStatementStub.AddCustStatementResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addCustStatementRequest_Oper operation
           */
            public void receiveErroraddCustStatementRequest_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchCustStatementList_Oper method
            * override this method for handling normal response from fetchCustStatementList_Oper operation
            */
           public void receiveResultfetchCustStatementList_Oper(
                    com.newgen.dscop.stub.ModCBStatementStub.FetchCustStatementListResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchCustStatementList_Oper operation
           */
            public void receiveErrorfetchCustStatementList_Oper(java.lang.Exception e) {
            }
                


    }
    