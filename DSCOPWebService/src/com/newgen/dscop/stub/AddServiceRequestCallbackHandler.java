
/**
 * AddServiceRequestCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.dscop.stub;

    /**
     *  AddServiceRequestCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class AddServiceRequestCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public AddServiceRequestCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public AddServiceRequestCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for removeOffers_Oper method
            * override this method for handling normal response from removeOffers_Oper operation
            */
           public void receiveResultremoveOffers_Oper(
                    com.newgen.dscop.stub.AddServiceRequestStub.RemoveOffersResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from removeOffers_Oper operation
           */
            public void receiveErrorremoveOffers_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for executeSRAutomation_Oper method
            * override this method for handling normal response from executeSRAutomation_Oper operation
            */
           public void receiveResultexecuteSRAutomation_Oper(
                    com.newgen.dscop.stub.AddServiceRequestStub.ExecuteSRAutomationResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from executeSRAutomation_Oper operation
           */
            public void receiveErrorexecuteSRAutomation_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addServiceRequest2_Oper method
            * override this method for handling normal response from addServiceRequest2_Oper operation
            */
           public void receiveResultaddServiceRequest2_Oper(
                    com.newgen.dscop.stub.AddServiceRequestStub.AddServiceRequest2ResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addServiceRequest2_Oper operation
           */
            public void receiveErroraddServiceRequest2_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addServiceRequest_Oper method
            * override this method for handling normal response from addServiceRequest_Oper operation
            */
           public void receiveResultaddServiceRequest_Oper(
                    com.newgen.dscop.stub.AddServiceRequestStub.AddServiceRequestResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addServiceRequest_Oper operation
           */
            public void receiveErroraddServiceRequest_Oper(java.lang.Exception e) {
            }
                


    }
    