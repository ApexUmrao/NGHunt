
/**
 * InqSBKCustomerCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:22:40 CEST)
 */

    package com.newgen.stub;

    /**
     *  InqSBKCustomerCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class InqSBKCustomerCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public InqSBKCustomerCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public InqSBKCustomerCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getChqLeavesIssueAccts_Oper method
            * override this method for handling normal response from getChqLeavesIssueAccts_Oper operation
            */
           public void receiveResultgetChqLeavesIssueAccts_Oper(
                    com.newgen.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getChqLeavesIssueAccts_Oper operation
           */
            public void receiveErrorgetChqLeavesIssueAccts_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getKioskCustEIDAInfo_Oper method
            * override this method for handling normal response from getKioskCustEIDAInfo_Oper operation
            */
           public void receiveResultgetKioskCustEIDAInfo_Oper(
                    com.newgen.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getKioskCustEIDAInfo_Oper operation
           */
            public void receiveErrorgetKioskCustEIDAInfo_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getCustomerInfo_Oper method
            * override this method for handling normal response from getCustomerInfo_Oper operation
            */
           public void receiveResultgetCustomerInfo_Oper(
                    com.newgen.stub.InqSBKCustomerStub.GetCustomerInfoResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getCustomerInfo_Oper operation
           */
            public void receiveErrorgetCustomerInfo_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getKioskCustActivitiesDtl_Oper method
            * override this method for handling normal response from getKioskCustActivitiesDtl_Oper operation
            */
           public void receiveResultgetKioskCustActivitiesDtl_Oper(
                    com.newgen.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getKioskCustActivitiesDtl_Oper operation
           */
            public void receiveErrorgetKioskCustActivitiesDtl_Oper(java.lang.Exception e) {
            }
                


    }
    