
/**
 * InqSBKDebitCardsCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:22:40 CEST)
 */

    package com.newgen.stub;

    /**
     *  InqSBKDebitCardsCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class InqSBKDebitCardsCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public InqSBKDebitCardsCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public InqSBKDebitCardsCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getDebitCardPrintStatus_Oper method
            * override this method for handling normal response from getDebitCardPrintStatus_Oper operation
            */
           public void receiveResultgetDebitCardPrintStatus_Oper(
                    com.newgen.stub.InqSBKDebitCardsStub.GetDebitCardPrintStatusResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getDebitCardPrintStatus_Oper operation
           */
            public void receiveErrorgetDebitCardPrintStatus_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getKioskList_Oper method
            * override this method for handling normal response from getKioskList_Oper operation
            */
           public void receiveResultgetKioskList_Oper(
                    com.newgen.stub.InqSBKDebitCardsStub.GetKioskListResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getKioskList_Oper operation
           */
            public void receiveErrorgetKioskList_Oper(java.lang.Exception e) {
            }
                


    }
    