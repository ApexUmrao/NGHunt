
/**
 * ModEmiratesFaceRecognitionCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.newgen.dscop.stub;

    /**
     *  ModEmiratesFaceRecognitionCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ModEmiratesFaceRecognitionCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ModEmiratesFaceRecognitionCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ModEmiratesFaceRecognitionCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for verifyExistIdentity_Oper method
            * override this method for handling normal response from verifyExistIdentity_Oper operation
            */
           public void receiveResultverifyExistIdentity_Oper(
                    com.newgen.dscop.stub.ModEmiratesFaceRecognitionStub.VerifyExistIdentityResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from verifyExistIdentity_Oper operation
           */
            public void receiveErrorverifyExistIdentity_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for removeIdentity_Oper method
            * override this method for handling normal response from removeIdentity_Oper operation
            */
           public void receiveResultremoveIdentity_Oper(
                    com.newgen.dscop.stub.ModEmiratesFaceRecognitionStub.RemoveIdentityResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from removeIdentity_Oper operation
           */
            public void receiveErrorremoveIdentity_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for verifyLivenessCheckImage_Oper method
            * override this method for handling normal response from verifyLivenessCheckImage_Oper operation
            */
           public void receiveResultverifyLivenessCheckImage_Oper(
                    com.newgen.dscop.stub.ModEmiratesFaceRecognitionStub.VerifyLivenessCheckImageResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from verifyLivenessCheckImage_Oper operation
           */
            public void receiveErrorverifyLivenessCheckImage_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for verifyFaceIdentity_Oper method
            * override this method for handling normal response from verifyFaceIdentity_Oper operation
            */
           public void receiveResultverifyFaceIdentity_Oper(
                    com.newgen.dscop.stub.ModEmiratesFaceRecognitionStub.VerifyFaceIdentityResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from verifyFaceIdentity_Oper operation
           */
            public void receiveErrorverifyFaceIdentity_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for settleIdentity_Oper method
            * override this method for handling normal response from settleIdentity_Oper operation
            */
           public void receiveResultsettleIdentity_Oper(
                    com.newgen.dscop.stub.ModEmiratesFaceRecognitionStub.SettleIdentityResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from settleIdentity_Oper operation
           */
            public void receiveErrorsettleIdentity_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for confirmIdentity_Oper method
            * override this method for handling normal response from confirmIdentity_Oper operation
            */
           public void receiveResultconfirmIdentity_Oper(
                    com.newgen.dscop.stub.ModEmiratesFaceRecognitionStub.ConfirmIdentityResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from confirmIdentity_Oper operation
           */
            public void receiveErrorconfirmIdentity_Oper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getTempDynamicKey_Oper method
            * override this method for handling normal response from getTempDynamicKey_Oper operation
            */
           public void receiveResultgetTempDynamicKey_Oper(
                    com.newgen.dscop.stub.ModEmiratesFaceRecognitionStub.GetTempDynamicKeyResMsg result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getTempDynamicKey_Oper operation
           */
            public void receiveErrorgetTempDynamicKey_Oper(java.lang.Exception e) {
            }
                


    }
    