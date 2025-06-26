
/**
 * ModCustEmiratesDigitalWalletCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.dscop.stub;

    /**
     *  ModCustEmiratesDigitalWalletCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ModCustEmiratesDigitalWalletCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ModCustEmiratesDigitalWalletCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ModCustEmiratesDigitalWalletCallbackHandler(){
        this.clientData  = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for registerCustEmiratesDigitalWallet_Oper method
            * override this method for handling normal response from registerCustEmiratesDigitalWallet_Oper operation
            */
           public void receiveResultregisterCustEmiratesDigitalWallet_Oper(
                   com.newgen.dscop.stub.ModCustEmiratesDigitalWalletStub.RegisterCustEmiratesDigitalWalletResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from registerCustEmiratesDigitalWallet_Oper operation
           */
            public void receiveErrorregisterCustEmiratesDigitalWallet_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addMerchantEmiratesDigitalWalletCashOut_Oper method
            * override this method for handling normal response from addMerchantEmiratesDigitalWalletCashOut_Oper operation
            */
           public void receiveResultaddMerchantEmiratesDigitalWalletCashOut_Oper(
                   com.newgen.dscop.stub.ModCustEmiratesDigitalWalletStub.AddMerchantEmiratesDigitalWalletCashOutResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addMerchantEmiratesDigitalWalletCashOut_Oper operation
           */
            public void receiveErroraddMerchantEmiratesDigitalWalletCashOut_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modifyCustEmiratesDigitalWalletStatus_Oper method
            * override this method for handling normal response from modifyCustEmiratesDigitalWalletStatus_Oper operation
            */
           public void receiveResultmodifyCustEmiratesDigitalWalletStatus_Oper(
                   com.newgen.dscop.stub.ModCustEmiratesDigitalWalletStub.ModifyCustEmiratesDigitalWalletStatusResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modifyCustEmiratesDigitalWalletStatus_Oper operation
           */
            public void receiveErrormodifyCustEmiratesDigitalWalletStatus_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addCustEmiratesDigitalWalletTopup_Oper method
            * override this method for handling normal response from addCustEmiratesDigitalWalletTopup_Oper operation
            */
           public void receiveResultaddCustEmiratesDigitalWalletTopup_Oper(
                   com.newgen.dscop.stub.ModCustEmiratesDigitalWalletStub.AddCustEmiratesDigitalWalletTopupResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addCustEmiratesDigitalWalletTopup_Oper operation
           */
            public void receiveErroraddCustEmiratesDigitalWalletTopup_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modifyCustEmiratesDigitalWalletDeviceDtls_Oper method
            * override this method for handling normal response from modifyCustEmiratesDigitalWalletDeviceDtls_Oper operation
            */
           public void receiveResultmodifyCustEmiratesDigitalWalletDeviceDtls_Oper(
                   com.newgen.dscop.stub.ModCustEmiratesDigitalWalletStub.ModifyCustEmiratesDigitalWalletDeviceDtlsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modifyCustEmiratesDigitalWalletDeviceDtls_Oper operation
           */
            public void receiveErrormodifyCustEmiratesDigitalWalletDeviceDtls_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addCustEmiratesDigitalWalletCashOut_Oper method
            * override this method for handling normal response from addCustEmiratesDigitalWalletCashOut_Oper operation
            */
           public void receiveResultaddCustEmiratesDigitalWalletCashOut_Oper(
                   com.newgen.dscop.stub.ModCustEmiratesDigitalWalletStub.AddCustEmiratesDigitalWalletCashOutResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addCustEmiratesDigitalWalletCashOut_Oper operation
           */
            public void receiveErroraddCustEmiratesDigitalWalletCashOut_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modifyCustEmiratesDigitalWalletBenefDtls_Oper method
            * override this method for handling normal response from modifyCustEmiratesDigitalWalletBenefDtls_Oper operation
            */
           public void receiveResultmodifyCustEmiratesDigitalWalletBenefDtls_Oper(
                   com.newgen.dscop.stub.ModCustEmiratesDigitalWalletStub.ModifyCustEmiratesDigitalWalletBenefDtlsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modifyCustEmiratesDigitalWalletBenefDtls_Oper operation
           */
            public void receiveErrormodifyCustEmiratesDigitalWalletBenefDtls_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addEDWWalletPayment_Oper method
            * override this method for handling normal response from addEDWWalletPayment_Oper operation
            */
           public void receiveResultaddEDWWalletPayment_Oper(
                   com.newgen.dscop.stub.ModCustEmiratesDigitalWalletStub.AddEDWWalletPaymentResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addEDWWalletPayment_Oper operation
           */
            public void receiveErroraddEDWWalletPayment_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modifyCustEmiratesDigitalWalletDtls_Oper method
            * override this method for handling normal response from modifyCustEmiratesDigitalWalletDtls_Oper operation
            */
           public void receiveResultmodifyCustEmiratesDigitalWalletDtls_Oper(
                   com.newgen.dscop.stub.ModCustEmiratesDigitalWalletStub.ModifyCustEmiratesDigitalWalletDtlsResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modifyCustEmiratesDigitalWalletDtls_Oper operation
           */
            public void receiveErrormodifyCustEmiratesDigitalWalletDtls_Oper(java.lang.Exception e) {
            }
                


    }
    