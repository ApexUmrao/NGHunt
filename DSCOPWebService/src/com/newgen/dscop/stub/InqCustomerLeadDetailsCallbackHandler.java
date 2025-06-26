
/**
 * InqCustomerLeadDetailsCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.dscop.stub;

    /**
     *  InqCustomerLeadDetailsCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class InqCustomerLeadDetailsCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public InqCustomerLeadDetailsCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public InqCustomerLeadDetailsCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for fetchLeadDetails_Oper method
            * override this method for handling normal response from fetchLeadDetails_Oper operation
            */
           public void receiveResultfetchLeadDetails_Oper(
                    com.newgen.dscop.stub.InqCustomerLeadDetailsStub.FetchLeadDetailsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchLeadDetails_Oper operation
           */
            public void receiveErrorfetchLeadDetails_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchMGMOffersList_Oper method
            * override this method for handling normal response from fetchMGMOffersList_Oper operation
            */
           public void receiveResultfetchMGMOffersList_Oper(
                    com.newgen.dscop.stub.InqCustomerLeadDetailsStub.FetchMGMOffersListResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchMGMOffersList_Oper operation
           */
            public void receiveErrorfetchMGMOffersList_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fetchCustMGMOffers_Oper method
            * override this method for handling normal response from fetchCustMGMOffers_Oper operation
            */
           public void receiveResultfetchCustMGMOffers_Oper(
                    com.newgen.dscop.stub.InqCustomerLeadDetailsStub.FetchCustMGMOffersResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fetchCustMGMOffers_Oper operation
           */
            public void receiveErrorfetchCustMGMOffers_Oper(java.lang.Exception e) {
            }
                


    }
    