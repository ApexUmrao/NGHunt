
/**
 * InqECBApplicationDetailsCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.dscop.stub;

    /**
     *  InqECBApplicationDetailsCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class InqECBApplicationDetailsCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public InqECBApplicationDetailsCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public InqECBApplicationDetailsCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for fetchMonitoringDtls_Oper method
            * override this method for handling normal response from fetchMonitoringDtls_Oper operation
            */
           public void receiveResultfetchMonitoringDtls_Oper(
                    com.newgen.dscop.stub.InqECBApplicationDetailsStub.FetchMonitoringDtlsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchMonitoringDtls_Oper operation
           */
            public void receiveErrorfetchMonitoringDtls_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchApplicationUpdateDtls_Oper method
            * override this method for handling normal response from fetchApplicationUpdateDtls_Oper operation
            */
           public void receiveResultfetchApplicationUpdateDtls_Oper(
                    com.newgen.dscop.stub.InqECBApplicationDetailsStub.FetchApplicationUpdateDtlsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchApplicationUpdateDtls_Oper operation
           */
            public void receiveErrorfetchApplicationUpdateDtls_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchECBReportDtls_Oper method
            * override this method for handling normal response from fetchECBReportDtls_Oper operation
            */
           public void receiveResultfetchECBReportDtls_Oper(
                    com.newgen.dscop.stub.InqECBApplicationDetailsStub.FetchECBReportDtlsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchECBReportDtls_Oper operation
           */
            public void receiveErrorfetchECBReportDtls_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchAdditonalApplicationDtls_Oper method
            * override this method for handling normal response from fetchAdditonalApplicationDtls_Oper operation
            */
           public void receiveResultfetchAdditonalApplicationDtls_Oper(
                    com.newgen.dscop.stub.InqECBApplicationDetailsStub.FetchAdditonalApplicationDtlsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchAdditonalApplicationDtls_Oper operation
           */
            public void receiveErrorfetchAdditonalApplicationDtls_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchNewApplicationDtls_Oper method
            * override this method for handling normal response from fetchNewApplicationDtls_Oper operation
            */
           public void receiveResultfetchNewApplicationDtls_Oper(
                    com.newgen.dscop.stub.InqECBApplicationDetailsStub.FetchNewApplicationDtlsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchNewApplicationDtls_Oper operation
           */
            public void receiveErrorfetchNewApplicationDtls_Oper(java.lang.Exception e) {
            }
                


    }
    