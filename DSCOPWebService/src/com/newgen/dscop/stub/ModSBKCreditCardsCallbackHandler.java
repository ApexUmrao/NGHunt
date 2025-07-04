
/**
 * ModSBKCreditCardsCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.dscop.stub;

    /**
     *  ModSBKCreditCardsCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ModSBKCreditCardsCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ModSBKCreditCardsCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ModSBKCreditCardsCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for addCreditCardInfo_Oper method
            * override this method for handling normal response from addCreditCardInfo_Oper operation
            */
           public void receiveResultaddCreditCardInfo_Oper(
                    com.newgen.dscop.stub.ModSBKCreditCardsStub.AddCreditCardInfoResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addCreditCardInfo_Oper operation
           */
            public void receiveErroraddCreditCardInfo_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modCreditCardPrintStatus_Oper method
            * override this method for handling normal response from modCreditCardPrintStatus_Oper operation
            */
           public void receiveResultmodCreditCardPrintStatus_Oper(
                    com.newgen.dscop.stub.ModSBKCreditCardsStub.ModCreditCardPrintStatusResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modCreditCardPrintStatus_Oper operation
           */
            public void receiveErrormodCreditCardPrintStatus_Oper(java.lang.Exception e) {
            }
                


    }
    