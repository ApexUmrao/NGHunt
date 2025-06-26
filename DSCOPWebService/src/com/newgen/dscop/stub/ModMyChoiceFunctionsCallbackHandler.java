
/**
 * ModMyChoiceFunctionsCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.dscop.stub;

    /**
     *  ModMyChoiceFunctionsCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ModMyChoiceFunctionsCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ModMyChoiceFunctionsCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ModMyChoiceFunctionsCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for addMyChoiceLead_Oper method
            * override this method for handling normal response from addMyChoiceLead_Oper operation
            */
           public void receiveResultaddMyChoiceLead_Oper(
                    com.newgen.dscop.stub.ModMyChoiceFunctionsStub.AddMyChoiceLeadResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addMyChoiceLead_Oper operation
           */
            public void receiveErroraddMyChoiceLead_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchPuzzleMaster_Oper method
            * override this method for handling normal response from fetchPuzzleMaster_Oper operation
            */
           public void receiveResultfetchPuzzleMaster_Oper(
                    com.newgen.dscop.stub.ModMyChoiceFunctionsStub.FetchPuzzleMasterResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchPuzzleMaster_Oper operation
           */
            public void receiveErrorfetchPuzzleMaster_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchProductMaster_Oper method
            * override this method for handling normal response from fetchProductMaster_Oper operation
            */
           public void receiveResultfetchProductMaster_Oper(
                    com.newgen.dscop.stub.ModMyChoiceFunctionsStub.FetchProductMasterResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchProductMaster_Oper operation
           */
            public void receiveErrorfetchProductMaster_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchBundles_Oper method
            * override this method for handling normal response from fetchBundles_Oper operation
            */
           public void receiveResultfetchBundles_Oper(
                    com.newgen.dscop.stub.ModMyChoiceFunctionsStub.FetchBundlesResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchBundles_Oper operation
           */
            public void receiveErrorfetchBundles_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchCustomerHoldings_Oper method
            * override this method for handling normal response from fetchCustomerHoldings_Oper operation
            */
           public void receiveResultfetchCustomerHoldings_Oper(
                    com.newgen.dscop.stub.ModMyChoiceFunctionsStub.FetchCustomerHoldingsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchCustomerHoldings_Oper operation
           */
            public void receiveErrorfetchCustomerHoldings_Oper(java.lang.Exception e) {
            }
                


    }
    