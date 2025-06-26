
/**
 * InqCustEmiratesIDAuthDtlsStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
        package com.newgen.dscop.stub;

        

        /*
        *  InqCustEmiratesIDAuthDtlsStub java implementation
        */

        
        public class InqCustEmiratesIDAuthDtlsStub extends org.apache.axis2.client.Stub
        {
        protected org.apache.axis2.description.AxisOperation[] _operations;

        //hashmaps to keep the fault mapping
        private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
        private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
        private java.util.HashMap faultMessageMap = new java.util.HashMap();

        private static int counter = 0;
        public String resCustEmiratesIDAuthDtls="";
        
        public String getInputXml(
    			com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.InqCustEmiratesIDAuthDtlsReqMsg CustEmiratesAuthDtlsReqMsg0)

    	throws java.rmi.RemoteException

    	{
    		org.apache.axis2.context.MessageContext _messageContext = null;
    		try {
    			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
    					.createClient(_operations[0].getName());
    			_operationClient
    					.getOptions()
    					.setAction(
    							"/Services/CustomerCommonServices/CustomerInqServices/Service/InqCustEmiratesIDAuthDtls.serviceagent/InqCustEmiratesIDAuthDtlsPortTypeEndpoint0/InqCustEmiratesIDAuthDtls_Oper");
    			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(
    					true);

    			addPropertyToOperationClient(
    					_operationClient,
    					org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
    					"&");

    			// create a message context
    			_messageContext = new org.apache.axis2.context.MessageContext();

    			// create SOAP envelope with that payload
    			org.apache.axiom.soap.SOAPEnvelope env = null;

    			env = toEnvelope(getFactory(_operationClient.getOptions()
    					.getSoapVersionURI()), CustEmiratesAuthDtlsReqMsg0,
    					optimizeContent(new javax.xml.namespace.QName(
    							"http://xmlns.example.com/1475522246254",
    							"inqCustEmiratesIDAuthDtls_Oper")),
    					new javax.xml.namespace.QName(
    							"http://xmlns.example.com/1475522246254",
    							"inqCustEmiratesIDAuthDtls_Oper"));

    			// adding SOAP soap_headers
    			_serviceClient.addHeadersToEnvelope(env);
    			// set the message context with that soap envelope
    			_messageContext.setEnvelope(env);
    			return env.toString();

    		} catch (org.apache.axis2.AxisFault f) {
    			return "";
    		}
    	}

        private static synchronized java.lang.String getUniqueSuffix(){
            // reset the counter if it is greater than 99999
            if (counter > 99999){
                counter = 0;
            }
            counter = counter + 1; 
            return java.lang.Long.toString(java.lang.System.currentTimeMillis()) + "_" + counter;
        }

    
    private void populateAxisService() throws org.apache.axis2.AxisFault {

     //creating the Service with a unique name
     _service = new org.apache.axis2.description.AxisService("InqCustEmiratesIDAuthDtls" + getUniqueSuffix());
     addAnonymousOperations();

        //creating the operations
        org.apache.axis2.description.AxisOperation __operation;

        _operations = new org.apache.axis2.description.AxisOperation[1];
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://xmlns.example.com/1475522246254", "inqCustEmiratesIDAuthDtls_Oper"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[0]=__operation;
            
        
        }

    //populates the faults
    private void populateFaults(){
         


    }

    /**
      *Constructor that takes in a configContext
      */

    public InqCustEmiratesIDAuthDtlsStub(org.apache.axis2.context.ConfigurationContext configurationContext,
       java.lang.String targetEndpoint)
       throws org.apache.axis2.AxisFault {
         this(configurationContext,targetEndpoint,false);
   }


   /**
     * Constructor that takes in a configContext  and useseperate listner
     */
   public InqCustEmiratesIDAuthDtlsStub(org.apache.axis2.context.ConfigurationContext configurationContext,
        java.lang.String targetEndpoint, boolean useSeparateListener)
        throws org.apache.axis2.AxisFault {
         //To populate AxisService
         populateAxisService();
         populateFaults();

        _serviceClient = new org.apache.axis2.client.ServiceClient(configurationContext,_service);
        
	
        _serviceClient.getOptions().setTo(new org.apache.axis2.addressing.EndpointReference(
                targetEndpoint));
        _serviceClient.getOptions().setUseSeparateListener(useSeparateListener);
        
    
    }

    /**
     * Default Constructor
     */
    public InqCustEmiratesIDAuthDtlsStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {
        
                    this(configurationContext,"http://10.146.163.71:5533/Services/CustomerCommonServices/CustomerInqServices/Service/InqCustEmiratesIDAuthDtls.serviceagent/InqCustEmiratesIDAuthDtlsPortTypeEndpoint0" );
                
    }

    /**
     * Default Constructor
     */
    public InqCustEmiratesIDAuthDtlsStub() throws org.apache.axis2.AxisFault {
        
                    this("http://10.146.163.71:5533/Services/CustomerCommonServices/CustomerInqServices/Service/InqCustEmiratesIDAuthDtls.serviceagent/InqCustEmiratesIDAuthDtlsPortTypeEndpoint0" );
                
    }

    /**
     * Constructor taking the target endpoint
     */
    public InqCustEmiratesIDAuthDtlsStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
        this(null,targetEndpoint);
    }



        
                    /**
                     * Auto generated method signature
                     * 
                     * @see com.newgen.dscop.stub.InqCustEmiratesIDAuthDtls#inqCustEmiratesIDAuthDtls_Oper
                     * @param inqCustEmiratesIDAuthDtlsReqMsg0
                    
                     */

                    

                            public  com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.InqCustEmiratesIDAuthDtlsResMsg inqCustEmiratesIDAuthDtls_Oper(

                            com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.InqCustEmiratesIDAuthDtlsReqMsg inqCustEmiratesIDAuthDtlsReqMsg0)
                        

                    throws java.rmi.RemoteException
                    
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
              _operationClient.getOptions().setAction("/Services/CustomerCommonServices/CustomerInqServices/Service/InqCustEmiratesIDAuthDtls.serviceagent/InqCustEmiratesIDAuthDtlsPortTypeEndpoint0/InqCustEmiratesIDAuthDtls_Oper");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    inqCustEmiratesIDAuthDtlsReqMsg0,
                                                    optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1475522246254",
                                                    "inqCustEmiratesIDAuthDtls_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1475522246254",
                                                    "inqCustEmiratesIDAuthDtls_Oper"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                resCustEmiratesIDAuthDtls =_returnEnv.toString();
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.InqCustEmiratesIDAuthDtlsResMsg.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.InqCustEmiratesIDAuthDtlsResMsg)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqCustEmiratesIDAuthDtls_Oper"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqCustEmiratesIDAuthDtls_Oper"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqCustEmiratesIDAuthDtls_Oper"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * 
                * @see com.newgen.dscop.stub.InqCustEmiratesIDAuthDtls#startinqCustEmiratesIDAuthDtls_Oper
                    * @param inqCustEmiratesIDAuthDtlsReqMsg0
                
                */
                public  void startinqCustEmiratesIDAuthDtls_Oper(

                 com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.InqCustEmiratesIDAuthDtlsReqMsg inqCustEmiratesIDAuthDtlsReqMsg0,

                  final com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
             _operationClient.getOptions().setAction("/Services/CustomerCommonServices/CustomerInqServices/Service/InqCustEmiratesIDAuthDtls.serviceagent/InqCustEmiratesIDAuthDtlsPortTypeEndpoint0/InqCustEmiratesIDAuthDtls_Oper");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    inqCustEmiratesIDAuthDtlsReqMsg0,
                                                    optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1475522246254",
                                                    "inqCustEmiratesIDAuthDtls_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1475522246254",
                                                    "inqCustEmiratesIDAuthDtls_Oper"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.InqCustEmiratesIDAuthDtlsResMsg.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultinqCustEmiratesIDAuthDtls_Oper(
                                        (com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.InqCustEmiratesIDAuthDtlsResMsg)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorinqCustEmiratesIDAuthDtls_Oper(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqCustEmiratesIDAuthDtls_Oper"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqCustEmiratesIDAuthDtls_Oper"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqCustEmiratesIDAuthDtls_Oper"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
					
										            callback.receiveErrorinqCustEmiratesIDAuthDtls_Oper(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinqCustEmiratesIDAuthDtls_Oper(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinqCustEmiratesIDAuthDtls_Oper(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinqCustEmiratesIDAuthDtls_Oper(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinqCustEmiratesIDAuthDtls_Oper(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinqCustEmiratesIDAuthDtls_Oper(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinqCustEmiratesIDAuthDtls_Oper(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinqCustEmiratesIDAuthDtls_Oper(f);
                                            }
									    } else {
										    callback.receiveErrorinqCustEmiratesIDAuthDtls_Oper(f);
									    }
									} else {
									    callback.receiveErrorinqCustEmiratesIDAuthDtls_Oper(f);
									}
								} else {
								    callback.receiveErrorinqCustEmiratesIDAuthDtls_Oper(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErrorinqCustEmiratesIDAuthDtls_Oper(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[0].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[0].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                


       /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
       private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
            org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
            returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
       return returnMap;
    }

    
    
    private javax.xml.namespace.QName[] opNameArray = null;
    private boolean optimizeContent(javax.xml.namespace.QName opName) {
        

        if (opNameArray == null) {
            return false;
        }
        for (int i = 0; i < opNameArray.length; i++) {
            if (opName.equals(opNameArray[i])) {
                return true;   
            }
        }
        return false;
    }
     //http://10.146.163.71:5533/Services/CustomerCommonServices/CustomerInqServices/Service/InqCustEmiratesIDAuthDtls.serviceagent/InqCustEmiratesIDAuthDtlsPortTypeEndpoint0
        public static class HeaderType
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = headerType
                Namespace URI = http://www.adcb.com/esb/common/header.xsd
                Namespace Prefix = ns2
                */
            

                        /**
                        * field for UsecaseID
                        */

                        
                                    protected java.lang.String localUsecaseID ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localUsecaseIDTracker = false ;

                           public boolean isUsecaseIDSpecified(){
                               return localUsecaseIDTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getUsecaseID(){
                               return localUsecaseID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param UsecaseID
                               */
                               public void setUsecaseID(java.lang.String param){
                            localUsecaseIDTracker = param != null;
                                   
                                            this.localUsecaseID=param;
                                    

                               }
                            

                        /**
                        * field for ServiceName
                        */

                        
                                    protected java.lang.String localServiceName ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getServiceName(){
                               return localServiceName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ServiceName
                               */
                               public void setServiceName(java.lang.String param){
                            
                                            this.localServiceName=param;
                                    

                               }
                            

                        /**
                        * field for VersionNo
                        */

                        
                                    protected java.lang.String localVersionNo ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getVersionNo(){
                               return localVersionNo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param VersionNo
                               */
                               public void setVersionNo(java.lang.String param){
                            
                                            this.localVersionNo=param;
                                    

                               }
                            

                        /**
                        * field for ServiceAction
                        */

                        
                                    protected java.lang.String localServiceAction ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localServiceActionTracker = false ;

                           public boolean isServiceActionSpecified(){
                               return localServiceActionTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getServiceAction(){
                               return localServiceAction;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ServiceAction
                               */
                               public void setServiceAction(java.lang.String param){
                            localServiceActionTracker = param != null;
                                   
                                            this.localServiceAction=param;
                                    

                               }
                            

                        /**
                        * field for CorrelationID
                        */

                        
                                    protected java.lang.String localCorrelationID ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCorrelationIDTracker = false ;

                           public boolean isCorrelationIDSpecified(){
                               return localCorrelationIDTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCorrelationID(){
                               return localCorrelationID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CorrelationID
                               */
                               public void setCorrelationID(java.lang.String param){
                            localCorrelationIDTracker = param != null;
                                   
                                            this.localCorrelationID=param;
                                    

                               }
                            

                        /**
                        * field for SysRefNumber
                        */

                        
                                    protected java.lang.String localSysRefNumber ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSysRefNumber(){
                               return localSysRefNumber;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SysRefNumber
                               */
                               public void setSysRefNumber(java.lang.String param){
                            
                                            this.localSysRefNumber=param;
                                    

                               }
                            

                        /**
                        * field for SenderID
                        */

                        
                                    protected java.lang.String localSenderID ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSenderID(){
                               return localSenderID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SenderID
                               */
                               public void setSenderID(java.lang.String param){
                            
                                            this.localSenderID=param;
                                    

                               }
                            

                        /**
                        * field for Consumer
                        */

                        
                                    protected java.lang.String localConsumer ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localConsumerTracker = false ;

                           public boolean isConsumerSpecified(){
                               return localConsumerTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getConsumer(){
                               return localConsumer;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Consumer
                               */
                               public void setConsumer(java.lang.String param){
                            localConsumerTracker = param != null;
                                   
                                            this.localConsumer=param;
                                    

                               }
                            

                        /**
                        * field for ReqTimeStamp
                        */

                        
                                    protected java.lang.String localReqTimeStamp ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getReqTimeStamp(){
                               return localReqTimeStamp;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ReqTimeStamp
                               */
                               public void setReqTimeStamp(java.lang.String param){
                            
                                            this.localReqTimeStamp=param;
                                    

                               }
                            

                        /**
                        * field for RepTimeStamp
                        */

                        
                                    protected java.lang.String localRepTimeStamp ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRepTimeStampTracker = false ;

                           public boolean isRepTimeStampSpecified(){
                               return localRepTimeStampTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getRepTimeStamp(){
                               return localRepTimeStamp;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RepTimeStamp
                               */
                               public void setRepTimeStamp(java.lang.String param){
                            localRepTimeStampTracker = param != null;
                                   
                                            this.localRepTimeStamp=param;
                                    

                               }
                            

                        /**
                        * field for Username
                        */

                        
                                    protected java.lang.String localUsername ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localUsernameTracker = false ;

                           public boolean isUsernameSpecified(){
                               return localUsernameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getUsername(){
                               return localUsername;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Username
                               */
                               public void setUsername(java.lang.String param){
                            localUsernameTracker = param != null;
                                   
                                            this.localUsername=param;
                                    

                               }
                            

                        /**
                        * field for Credentials
                        */

                        
                                    protected java.lang.String localCredentials ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCredentialsTracker = false ;

                           public boolean isCredentialsSpecified(){
                               return localCredentialsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCredentials(){
                               return localCredentials;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Credentials
                               */
                               public void setCredentials(java.lang.String param){
                            localCredentialsTracker = param != null;
                                   
                                            this.localCredentials=param;
                                    

                               }
                            

                        /**
                        * field for ReturnCode
                        */

                        
                                    protected java.lang.String localReturnCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localReturnCodeTracker = false ;

                           public boolean isReturnCodeSpecified(){
                               return localReturnCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getReturnCode(){
                               return localReturnCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ReturnCode
                               */
                               public void setReturnCode(java.lang.String param){
                            localReturnCodeTracker = param != null;
                                   
                                            this.localReturnCode=param;
                                    

                               }
                            

                        /**
                        * field for ErrorDescription
                        */

                        
                                    protected java.lang.String localErrorDescription ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localErrorDescriptionTracker = false ;

                           public boolean isErrorDescriptionSpecified(){
                               return localErrorDescriptionTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getErrorDescription(){
                               return localErrorDescription;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ErrorDescription
                               */
                               public void setErrorDescription(java.lang.String param){
                            localErrorDescriptionTracker = param != null;
                                   
                                            this.localErrorDescription=param;
                                    

                               }
                            

                        /**
                        * field for ErrorDetail
                        */

                        
                                    protected java.lang.String localErrorDetail ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localErrorDetailTracker = false ;

                           public boolean isErrorDetailSpecified(){
                               return localErrorDetailTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getErrorDetail(){
                               return localErrorDetail;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ErrorDetail
                               */
                               public void setErrorDetail(java.lang.String param){
                            localErrorDetailTracker = param != null;
                                   
                                            this.localErrorDetail=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/header.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":headerType",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "headerType",
                           xmlWriter);
                   }

               
                   }
                if (localUsecaseIDTracker){
                                    namespace = "http://www.adcb.com/esb/common/header.xsd";
                                    writeStartElement(null, namespace, "usecaseID", xmlWriter);
                             

                                          if (localUsecaseID==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("usecaseID cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localUsecaseID);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                                    namespace = "http://www.adcb.com/esb/common/header.xsd";
                                    writeStartElement(null, namespace, "serviceName", xmlWriter);
                             

                                          if (localServiceName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("serviceName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localServiceName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/common/header.xsd";
                                    writeStartElement(null, namespace, "versionNo", xmlWriter);
                             

                                          if (localVersionNo==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("versionNo cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localVersionNo);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localServiceActionTracker){
                                    namespace = "http://www.adcb.com/esb/common/header.xsd";
                                    writeStartElement(null, namespace, "serviceAction", xmlWriter);
                             

                                          if (localServiceAction==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("serviceAction cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localServiceAction);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCorrelationIDTracker){
                                    namespace = "http://www.adcb.com/esb/common/header.xsd";
                                    writeStartElement(null, namespace, "correlationID", xmlWriter);
                             

                                          if (localCorrelationID==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("correlationID cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCorrelationID);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                                    namespace = "http://www.adcb.com/esb/common/header.xsd";
                                    writeStartElement(null, namespace, "sysRefNumber", xmlWriter);
                             

                                          if (localSysRefNumber==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("sysRefNumber cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSysRefNumber);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/common/header.xsd";
                                    writeStartElement(null, namespace, "senderID", xmlWriter);
                             

                                          if (localSenderID==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("senderID cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSenderID);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localConsumerTracker){
                                    namespace = "http://www.adcb.com/esb/common/header.xsd";
                                    writeStartElement(null, namespace, "consumer", xmlWriter);
                             

                                          if (localConsumer==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("consumer cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localConsumer);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                                    namespace = "http://www.adcb.com/esb/common/header.xsd";
                                    writeStartElement(null, namespace, "reqTimeStamp", xmlWriter);
                             

                                          if (localReqTimeStamp==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("reqTimeStamp cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localReqTimeStamp);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localRepTimeStampTracker){
                                    namespace = "http://www.adcb.com/esb/common/header.xsd";
                                    writeStartElement(null, namespace, "repTimeStamp", xmlWriter);
                             

                                          if (localRepTimeStamp==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("repTimeStamp cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localRepTimeStamp);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localUsernameTracker){
                                    namespace = "http://www.adcb.com/esb/common/header.xsd";
                                    writeStartElement(null, namespace, "username", xmlWriter);
                             

                                          if (localUsername==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("username cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localUsername);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCredentialsTracker){
                                    namespace = "http://www.adcb.com/esb/common/header.xsd";
                                    writeStartElement(null, namespace, "credentials", xmlWriter);
                             

                                          if (localCredentials==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("credentials cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCredentials);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localReturnCodeTracker){
                                    namespace = "http://www.adcb.com/esb/common/header.xsd";
                                    writeStartElement(null, namespace, "returnCode", xmlWriter);
                             

                                          if (localReturnCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("returnCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localReturnCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localErrorDescriptionTracker){
                                    namespace = "http://www.adcb.com/esb/common/header.xsd";
                                    writeStartElement(null, namespace, "errorDescription", xmlWriter);
                             

                                          if (localErrorDescription==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("errorDescription cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localErrorDescription);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localErrorDetailTracker){
                                    namespace = "http://www.adcb.com/esb/common/header.xsd";
                                    writeStartElement(null, namespace, "errorDetail", xmlWriter);
                             

                                          if (localErrorDetail==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("errorDetail cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localErrorDetail);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/common/header.xsd")){
                return "ns2";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
                    if (uri == null || uri.length() == 0) {
                        break;
                    }
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localUsecaseIDTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd",
                                                                      "usecaseID"));
                                 
                                        if (localUsecaseID != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUsecaseID));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("usecaseID cannot be null!!");
                                        }
                                    }
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd",
                                                                      "serviceName"));
                                 
                                        if (localServiceName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localServiceName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("serviceName cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd",
                                                                      "versionNo"));
                                 
                                        if (localVersionNo != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localVersionNo));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("versionNo cannot be null!!");
                                        }
                                     if (localServiceActionTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd",
                                                                      "serviceAction"));
                                 
                                        if (localServiceAction != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localServiceAction));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("serviceAction cannot be null!!");
                                        }
                                    } if (localCorrelationIDTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd",
                                                                      "correlationID"));
                                 
                                        if (localCorrelationID != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCorrelationID));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("correlationID cannot be null!!");
                                        }
                                    }
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd",
                                                                      "sysRefNumber"));
                                 
                                        if (localSysRefNumber != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSysRefNumber));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("sysRefNumber cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd",
                                                                      "senderID"));
                                 
                                        if (localSenderID != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSenderID));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("senderID cannot be null!!");
                                        }
                                     if (localConsumerTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd",
                                                                      "consumer"));
                                 
                                        if (localConsumer != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localConsumer));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("consumer cannot be null!!");
                                        }
                                    }
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd",
                                                                      "reqTimeStamp"));
                                 
                                        if (localReqTimeStamp != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReqTimeStamp));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("reqTimeStamp cannot be null!!");
                                        }
                                     if (localRepTimeStampTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd",
                                                                      "repTimeStamp"));
                                 
                                        if (localRepTimeStamp != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRepTimeStamp));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("repTimeStamp cannot be null!!");
                                        }
                                    } if (localUsernameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd",
                                                                      "username"));
                                 
                                        if (localUsername != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUsername));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("username cannot be null!!");
                                        }
                                    } if (localCredentialsTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd",
                                                                      "credentials"));
                                 
                                        if (localCredentials != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCredentials));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("credentials cannot be null!!");
                                        }
                                    } if (localReturnCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd",
                                                                      "returnCode"));
                                 
                                        if (localReturnCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReturnCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("returnCode cannot be null!!");
                                        }
                                    } if (localErrorDescriptionTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd",
                                                                      "errorDescription"));
                                 
                                        if (localErrorDescription != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localErrorDescription));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("errorDescription cannot be null!!");
                                        }
                                    } if (localErrorDetailTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd",
                                                                      "errorDetail"));
                                 
                                        if (localErrorDetail != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localErrorDetail));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("errorDetail cannot be null!!");
                                        }
                                    }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static HeaderType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            HeaderType object =
                new HeaderType();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"headerType".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (HeaderType)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","usecaseID").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"usecaseID" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUsecaseID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","serviceName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"serviceName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setServiceName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","versionNo").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"versionNo" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setVersionNo(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","serviceAction").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"serviceAction" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setServiceAction(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","correlationID").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"correlationID" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCorrelationID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","sysRefNumber").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"sysRefNumber" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSysRefNumber(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","senderID").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"senderID" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSenderID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","consumer").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"consumer" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setConsumer(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","reqTimeStamp").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"reqTimeStamp" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setReqTimeStamp(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","repTimeStamp").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"repTimeStamp" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRepTimeStamp(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","username").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"username" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUsername(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","credentials").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"credentials" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCredentials(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","returnCode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"returnCode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setReturnCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","errorDescription").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"errorDescription" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setErrorDescription(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","errorDetail").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"errorDetail" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setErrorDetail(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    
        public static class InqCustEmiratesIDAuthDtlsReqMsg
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                "InqCustEmiratesIDAuthDtlsReqMsg",
                "ns10");

            

                        /**
                        * field for Header
                        */

                        
                                    protected HeaderType localHeader ;
                                

                           /**
                           * Auto generated getter method
                           * @return HeaderType
                           */
                           public  HeaderType getHeader(){
                               return localHeader;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Header
                               */
                               public void setHeader(HeaderType param){
                            
                                            this.localHeader=param;
                                    

                               }
                            

                        /**
                        * field for InqCustEmiratesIDAuthDtlsReq
                        */

                        
                                    protected InqCustEmiratesIDAuthDtlsReq_type0 localInqCustEmiratesIDAuthDtlsReq ;
                                

                           /**
                           * Auto generated getter method
                           * @return InqCustEmiratesIDAuthDtlsReq_type0
                           */
                           public  InqCustEmiratesIDAuthDtlsReq_type0 getInqCustEmiratesIDAuthDtlsReq(){
                               return localInqCustEmiratesIDAuthDtlsReq;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param InqCustEmiratesIDAuthDtlsReq
                               */
                               public void setInqCustEmiratesIDAuthDtlsReq(InqCustEmiratesIDAuthDtlsReq_type0 param){
                            
                                            this.localInqCustEmiratesIDAuthDtlsReq=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME);
               return factory.createOMElement(dataSource,MY_QNAME);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":InqCustEmiratesIDAuthDtlsReqMsg",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "InqCustEmiratesIDAuthDtlsReqMsg",
                           xmlWriter);
                   }

               
                   }
               
                                            if (localHeader==null){
                                                 throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
                                            }
                                           localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
                                               xmlWriter);
                                        
                                            if (localInqCustEmiratesIDAuthDtlsReq==null){
                                                 throw new org.apache.axis2.databinding.ADBException("InqCustEmiratesIDAuthDtlsReq cannot be null!!");
                                            }
                                           localInqCustEmiratesIDAuthDtlsReq.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","InqCustEmiratesIDAuthDtlsReq"),
                                               xmlWriter);
                                        
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd")){
                return "ns10";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
                    if (uri == null || uri.length() == 0) {
                        break;
                    }
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd",
                                                                      "header"));
                            
                            
                                    if (localHeader==null){
                                         throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
                                    }
                                    elementList.add(localHeader);
                                
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "InqCustEmiratesIDAuthDtlsReq"));
                            
                            
                                    if (localInqCustEmiratesIDAuthDtlsReq==null){
                                         throw new org.apache.axis2.databinding.ADBException("InqCustEmiratesIDAuthDtlsReq cannot be null!!");
                                    }
                                    elementList.add(localInqCustEmiratesIDAuthDtlsReq);
                                

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static InqCustEmiratesIDAuthDtlsReqMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            InqCustEmiratesIDAuthDtlsReqMsg object =
                new InqCustEmiratesIDAuthDtlsReqMsg();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"InqCustEmiratesIDAuthDtlsReqMsg".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (InqCustEmiratesIDAuthDtlsReqMsg)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header").equals(reader.getName())){
                                
                                                object.setHeader(HeaderType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","InqCustEmiratesIDAuthDtlsReq").equals(reader.getName())){
                                
                                                object.setInqCustEmiratesIDAuthDtlsReq(InqCustEmiratesIDAuthDtlsReq_type0.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                              
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    
        public static class InqCustEmiratesIDAuthDtlsResMsg
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                "InqCustEmiratesIDAuthDtlsResMsg",
                "ns10");

            

                        /**
                        * field for Header
                        */

                        
                                    protected HeaderType localHeader ;
                                

                           /**
                           * Auto generated getter method
                           * @return HeaderType
                           */
                           public  HeaderType getHeader(){
                               return localHeader;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Header
                               */
                               public void setHeader(HeaderType param){
                            
                                            this.localHeader=param;
                                    

                               }
                            

                        /**
                        * field for InqCustEmiratesIDAuthDtlsRes
                        */

                        
                                    protected InqCustEmiratesIDAuthDtlsRes_type0 localInqCustEmiratesIDAuthDtlsRes ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localInqCustEmiratesIDAuthDtlsResTracker = false ;

                           public boolean isInqCustEmiratesIDAuthDtlsResSpecified(){
                               return localInqCustEmiratesIDAuthDtlsResTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return InqCustEmiratesIDAuthDtlsRes_type0
                           */
                           public  InqCustEmiratesIDAuthDtlsRes_type0 getInqCustEmiratesIDAuthDtlsRes(){
                               return localInqCustEmiratesIDAuthDtlsRes;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param InqCustEmiratesIDAuthDtlsRes
                               */
                               public void setInqCustEmiratesIDAuthDtlsRes(InqCustEmiratesIDAuthDtlsRes_type0 param){
                            localInqCustEmiratesIDAuthDtlsResTracker = param != null;
                                   
                                            this.localInqCustEmiratesIDAuthDtlsRes=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME);
               return factory.createOMElement(dataSource,MY_QNAME);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":InqCustEmiratesIDAuthDtlsResMsg",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "InqCustEmiratesIDAuthDtlsResMsg",
                           xmlWriter);
                   }

               
                   }
               
                                            if (localHeader==null){
                                                 throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
                                            }
                                           localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
                                               xmlWriter);
                                         if (localInqCustEmiratesIDAuthDtlsResTracker){
                                            if (localInqCustEmiratesIDAuthDtlsRes==null){
                                                 throw new org.apache.axis2.databinding.ADBException("InqCustEmiratesIDAuthDtlsRes cannot be null!!");
                                            }
                                           localInqCustEmiratesIDAuthDtlsRes.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","InqCustEmiratesIDAuthDtlsRes"),
                                               xmlWriter);
                                        }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd")){
                return "ns10";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
                    if (uri == null || uri.length() == 0) {
                        break;
                    }
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd",
                                                                      "header"));
                            
                            
                                    if (localHeader==null){
                                         throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
                                    }
                                    elementList.add(localHeader);
                                 if (localInqCustEmiratesIDAuthDtlsResTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "InqCustEmiratesIDAuthDtlsRes"));
                            
                            
                                    if (localInqCustEmiratesIDAuthDtlsRes==null){
                                         throw new org.apache.axis2.databinding.ADBException("InqCustEmiratesIDAuthDtlsRes cannot be null!!");
                                    }
                                    elementList.add(localInqCustEmiratesIDAuthDtlsRes);
                                }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static InqCustEmiratesIDAuthDtlsResMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            InqCustEmiratesIDAuthDtlsResMsg object =
                new InqCustEmiratesIDAuthDtlsResMsg();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"InqCustEmiratesIDAuthDtlsResMsg".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (InqCustEmiratesIDAuthDtlsResMsg)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header").equals(reader.getName())){
                                
                                                object.setHeader(HeaderType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","InqCustEmiratesIDAuthDtlsRes").equals(reader.getName())){
                                
                                                object.setInqCustEmiratesIDAuthDtlsRes(InqCustEmiratesIDAuthDtlsRes_type0.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    
        public static class EidaApplicationDetails_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = eidaApplicationDetails_type0
                Namespace URI = http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd
                Namespace Prefix = ns10
                */
            

                        /**
                        * field for EidaUserID
                        */

                        
                                    protected java.lang.String localEidaUserID ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEidaUserID(){
                               return localEidaUserID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EidaUserID
                               */
                               public void setEidaUserID(java.lang.String param){
                            
                                            this.localEidaUserID=param;
                                    

                               }
                            

                        /**
                        * field for EidaUserName
                        */

                        
                                    protected java.lang.String localEidaUserName ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEidaUserName(){
                               return localEidaUserName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EidaUserName
                               */
                               public void setEidaUserName(java.lang.String param){
                            
                                            this.localEidaUserName=param;
                                    

                               }
                            

                        /**
                        * field for EidaIPAddress
                        */

                        
                                    protected java.lang.String localEidaIPAddress ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEidaIPAddress(){
                               return localEidaIPAddress;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EidaIPAddress
                               */
                               public void setEidaIPAddress(java.lang.String param){
                            
                                            this.localEidaIPAddress=param;
                                    

                               }
                            

                        /**
                        * field for EidaBranchCode
                        */

                        
                                    protected java.lang.String localEidaBranchCode ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEidaBranchCode(){
                               return localEidaBranchCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EidaBranchCode
                               */
                               public void setEidaBranchCode(java.lang.String param){
                            
                                            this.localEidaBranchCode=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":eidaApplicationDetails_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "eidaApplicationDetails_type0",
                           xmlWriter);
                   }

               
                   }
               
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "eidaUserID", xmlWriter);
                             

                                          if (localEidaUserID==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("eidaUserID cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEidaUserID);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "eidaUserName", xmlWriter);
                             

                                          if (localEidaUserName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("eidaUserName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEidaUserName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "eidaIPAddress", xmlWriter);
                             

                                          if (localEidaIPAddress==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("eidaIPAddress cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEidaIPAddress);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "eidaBranchCode", xmlWriter);
                             

                                          if (localEidaBranchCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("eidaBranchCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEidaBranchCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd")){
                return "ns10";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
                    if (uri == null || uri.length() == 0) {
                        break;
                    }
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "eidaUserID"));
                                 
                                        if (localEidaUserID != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEidaUserID));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("eidaUserID cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "eidaUserName"));
                                 
                                        if (localEidaUserName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEidaUserName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("eidaUserName cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "eidaIPAddress"));
                                 
                                        if (localEidaIPAddress != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEidaIPAddress));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("eidaIPAddress cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "eidaBranchCode"));
                                 
                                        if (localEidaBranchCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEidaBranchCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("eidaBranchCode cannot be null!!");
                                        }
                                    

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static EidaApplicationDetails_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            EidaApplicationDetails_type0 object =
                new EidaApplicationDetails_type0();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"eidaApplicationDetails_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (EidaApplicationDetails_type0)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","eidaUserID").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"eidaUserID" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEidaUserID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","eidaUserName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"eidaUserName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEidaUserName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","eidaIPAddress").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"eidaIPAddress" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEidaIPAddress(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","eidaBranchCode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"eidaBranchCode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEidaBranchCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                              
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    
        public static class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd".equals(namespaceURI) &&
                  "photoImage_type0".equals(typeName)){
                   
                            return  PhotoImage_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd".equals(namespaceURI) &&
                  "customerInformation_type0".equals(typeName)){
                   
                            return  CustomerInformation_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd".equals(namespaceURI) &&
                  "InqCustEmiratesIDAuthDtlsRes_type0".equals(typeName)){
                   
                            return  InqCustEmiratesIDAuthDtlsRes_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd".equals(namespaceURI) &&
                  "eidaApplicationDetails_type0".equals(typeName)){
                   
                            return  EidaApplicationDetails_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd".equals(namespaceURI) &&
                  "InqCustEmiratesIDAuthDtlsReq_type0".equals(typeName)){
                   
                            return  InqCustEmiratesIDAuthDtlsReq_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd".equals(namespaceURI) &&
                  "authenticationDetails_type0".equals(typeName)){
                   
                            return  AuthenticationDetails_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.adcb.com/esb/common/header.xsd".equals(namespaceURI) &&
                  "headerType".equals(typeName)){
                   
                            return  HeaderType.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    
        public static class InqCustEmiratesIDAuthDtlsRes_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = InqCustEmiratesIDAuthDtlsRes_type0
                Namespace URI = http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd
                Namespace Prefix = ns10
                */
            

                        /**
                        * field for ChannelId
                        */

                        
                                    protected java.lang.String localChannelId ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getChannelId(){
                               return localChannelId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ChannelId
                               */
                               public void setChannelId(java.lang.String param){
                            
                                            this.localChannelId=param;
                                    

                               }
                            

                        /**
                        * field for ChannelReferenceNumber
                        */

                        
                                    protected java.lang.String localChannelReferenceNumber ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getChannelReferenceNumber(){
                               return localChannelReferenceNumber;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ChannelReferenceNumber
                               */
                               public void setChannelReferenceNumber(java.lang.String param){
                            
                                            this.localChannelReferenceNumber=param;
                                    

                               }
                            

                        /**
                        * field for InquryType
                        */

                        
                                    protected java.lang.String localInquryType ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getInquryType(){
                               return localInquryType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param InquryType
                               */
                               public void setInquryType(java.lang.String param){
                            
                                            this.localInquryType=param;
                                    

                               }
                            

                        /**
                        * field for PhotoRequestFlag
                        */

                        
                                    protected java.lang.String localPhotoRequestFlag ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPhotoRequestFlag(){
                               return localPhotoRequestFlag;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PhotoRequestFlag
                               */
                               public void setPhotoRequestFlag(java.lang.String param){
                            
                                            this.localPhotoRequestFlag=param;
                                    

                               }
                            

                        /**
                        * field for AuthenticationDetails
                        */

                        
                                    protected AuthenticationDetails_type0 localAuthenticationDetails ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAuthenticationDetailsTracker = false ;

                           public boolean isAuthenticationDetailsSpecified(){
                               return localAuthenticationDetailsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return AuthenticationDetails_type0
                           */
                           public  AuthenticationDetails_type0 getAuthenticationDetails(){
                               return localAuthenticationDetails;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AuthenticationDetails
                               */
                               public void setAuthenticationDetails(AuthenticationDetails_type0 param){
                            localAuthenticationDetailsTracker = param != null;
                                   
                                            this.localAuthenticationDetails=param;
                                    

                               }
                            

                        /**
                        * field for CustomerInformation
                        */

                        
                                    protected CustomerInformation_type0 localCustomerInformation ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerInformationTracker = false ;

                           public boolean isCustomerInformationSpecified(){
                               return localCustomerInformationTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return CustomerInformation_type0
                           */
                           public  CustomerInformation_type0 getCustomerInformation(){
                               return localCustomerInformation;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerInformation
                               */
                               public void setCustomerInformation(CustomerInformation_type0 param){
                            localCustomerInformationTracker = param != null;
                                   
                                            this.localCustomerInformation=param;
                                    

                               }
                            

                        /**
                        * field for PhotoImage
                        */

                        
                                    protected PhotoImage_type0 localPhotoImage ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPhotoImageTracker = false ;

                           public boolean isPhotoImageSpecified(){
                               return localPhotoImageTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return PhotoImage_type0
                           */
                           public  PhotoImage_type0 getPhotoImage(){
                               return localPhotoImage;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PhotoImage
                               */
                               public void setPhotoImage(PhotoImage_type0 param){
                            localPhotoImageTracker = param != null;
                                   
                                            this.localPhotoImage=param;
                                    

                               }
                            

                        /**
                        * field for EidaApplicationDetails
                        */

                        
                                    protected EidaApplicationDetails_type0 localEidaApplicationDetails ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEidaApplicationDetailsTracker = false ;

                           public boolean isEidaApplicationDetailsSpecified(){
                               return localEidaApplicationDetailsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return EidaApplicationDetails_type0
                           */
                           public  EidaApplicationDetails_type0 getEidaApplicationDetails(){
                               return localEidaApplicationDetails;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EidaApplicationDetails
                               */
                               public void setEidaApplicationDetails(EidaApplicationDetails_type0 param){
                            localEidaApplicationDetailsTracker = param != null;
                                   
                                            this.localEidaApplicationDetails=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":InqCustEmiratesIDAuthDtlsRes_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "InqCustEmiratesIDAuthDtlsRes_type0",
                           xmlWriter);
                   }

               
                   }
               
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "channelId", xmlWriter);
                             

                                          if (localChannelId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("channelId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localChannelId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "channelReferenceNumber", xmlWriter);
                             

                                          if (localChannelReferenceNumber==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("channelReferenceNumber cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localChannelReferenceNumber);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "inquryType", xmlWriter);
                             

                                          if (localInquryType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("inquryType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localInquryType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "photoRequestFlag", xmlWriter);
                             

                                          if (localPhotoRequestFlag==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("photoRequestFlag cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPhotoRequestFlag);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localAuthenticationDetailsTracker){
                                            if (localAuthenticationDetails==null){
                                                 throw new org.apache.axis2.databinding.ADBException("authenticationDetails cannot be null!!");
                                            }
                                           localAuthenticationDetails.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","authenticationDetails"),
                                               xmlWriter);
                                        } if (localCustomerInformationTracker){
                                            if (localCustomerInformation==null){
                                                 throw new org.apache.axis2.databinding.ADBException("customerInformation cannot be null!!");
                                            }
                                           localCustomerInformation.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","customerInformation"),
                                               xmlWriter);
                                        } if (localPhotoImageTracker){
                                            if (localPhotoImage==null){
                                                 throw new org.apache.axis2.databinding.ADBException("photoImage cannot be null!!");
                                            }
                                           localPhotoImage.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","photoImage"),
                                               xmlWriter);
                                        } if (localEidaApplicationDetailsTracker){
                                            if (localEidaApplicationDetails==null){
                                                 throw new org.apache.axis2.databinding.ADBException("eidaApplicationDetails cannot be null!!");
                                            }
                                           localEidaApplicationDetails.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","eidaApplicationDetails"),
                                               xmlWriter);
                                        }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd")){
                return "ns10";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
                    if (uri == null || uri.length() == 0) {
                        break;
                    }
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "channelId"));
                                 
                                        if (localChannelId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localChannelId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("channelId cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "channelReferenceNumber"));
                                 
                                        if (localChannelReferenceNumber != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localChannelReferenceNumber));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("channelReferenceNumber cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "inquryType"));
                                 
                                        if (localInquryType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInquryType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("inquryType cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "photoRequestFlag"));
                                 
                                        if (localPhotoRequestFlag != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPhotoRequestFlag));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("photoRequestFlag cannot be null!!");
                                        }
                                     if (localAuthenticationDetailsTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "authenticationDetails"));
                            
                            
                                    if (localAuthenticationDetails==null){
                                         throw new org.apache.axis2.databinding.ADBException("authenticationDetails cannot be null!!");
                                    }
                                    elementList.add(localAuthenticationDetails);
                                } if (localCustomerInformationTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "customerInformation"));
                            
                            
                                    if (localCustomerInformation==null){
                                         throw new org.apache.axis2.databinding.ADBException("customerInformation cannot be null!!");
                                    }
                                    elementList.add(localCustomerInformation);
                                } if (localPhotoImageTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "photoImage"));
                            
                            
                                    if (localPhotoImage==null){
                                         throw new org.apache.axis2.databinding.ADBException("photoImage cannot be null!!");
                                    }
                                    elementList.add(localPhotoImage);
                                } if (localEidaApplicationDetailsTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "eidaApplicationDetails"));
                            
                            
                                    if (localEidaApplicationDetails==null){
                                         throw new org.apache.axis2.databinding.ADBException("eidaApplicationDetails cannot be null!!");
                                    }
                                    elementList.add(localEidaApplicationDetails);
                                }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static InqCustEmiratesIDAuthDtlsRes_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            InqCustEmiratesIDAuthDtlsRes_type0 object =
                new InqCustEmiratesIDAuthDtlsRes_type0();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"InqCustEmiratesIDAuthDtlsRes_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (InqCustEmiratesIDAuthDtlsRes_type0)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","channelId").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"channelId" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setChannelId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","channelReferenceNumber").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"channelReferenceNumber" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setChannelReferenceNumber(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","inquryType").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"inquryType" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setInquryType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","photoRequestFlag").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"photoRequestFlag" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPhotoRequestFlag(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","authenticationDetails").equals(reader.getName())){
                                
                                                object.setAuthenticationDetails(AuthenticationDetails_type0.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","customerInformation").equals(reader.getName())){
                                
                                                object.setCustomerInformation(CustomerInformation_type0.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","photoImage").equals(reader.getName())){
                                
                                                object.setPhotoImage(PhotoImage_type0.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","eidaApplicationDetails").equals(reader.getName())){
                                
                                                object.setEidaApplicationDetails(EidaApplicationDetails_type0.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    
        public static class InqCustEmiratesIDAuthDtlsReq_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = InqCustEmiratesIDAuthDtlsReq_type0
                Namespace URI = http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd
                Namespace Prefix = ns10
                */
            

                        /**
                        * field for ChannelId
                        */

                        
                                    protected java.lang.String localChannelId ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getChannelId(){
                               return localChannelId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ChannelId
                               */
                               public void setChannelId(java.lang.String param){
                            
                                            this.localChannelId=param;
                                    

                               }
                            

                        /**
                        * field for ChannelReferenceNumber
                        */

                        
                                    protected java.lang.String localChannelReferenceNumber ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getChannelReferenceNumber(){
                               return localChannelReferenceNumber;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ChannelReferenceNumber
                               */
                               public void setChannelReferenceNumber(java.lang.String param){
                            
                                            this.localChannelReferenceNumber=param;
                                    

                               }
                            

                        /**
                        * field for InquryType
                        */

                        
                                    protected java.lang.String localInquryType ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getInquryType(){
                               return localInquryType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param InquryType
                               */
                               public void setInquryType(java.lang.String param){
                            
                                            this.localInquryType=param;
                                    

                               }
                            

                        /**
                        * field for PhotoRequestFlag
                        */

                        
                                    protected java.lang.String localPhotoRequestFlag ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPhotoRequestFlag(){
                               return localPhotoRequestFlag;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PhotoRequestFlag
                               */
                               public void setPhotoRequestFlag(java.lang.String param){
                            
                                            this.localPhotoRequestFlag=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":InqCustEmiratesIDAuthDtlsReq_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "InqCustEmiratesIDAuthDtlsReq_type0",
                           xmlWriter);
                   }

               
                   }
               
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "channelId", xmlWriter);
                             

                                          if (localChannelId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("channelId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localChannelId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "channelReferenceNumber", xmlWriter);
                             

                                          if (localChannelReferenceNumber==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("channelReferenceNumber cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localChannelReferenceNumber);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "inquryType", xmlWriter);
                             

                                          if (localInquryType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("inquryType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localInquryType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "photoRequestFlag", xmlWriter);
                             

                                          if (localPhotoRequestFlag==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("photoRequestFlag cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPhotoRequestFlag);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd")){
                return "ns10";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
                    if (uri == null || uri.length() == 0) {
                        break;
                    }
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "channelId"));
                                 
                                        if (localChannelId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localChannelId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("channelId cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "channelReferenceNumber"));
                                 
                                        if (localChannelReferenceNumber != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localChannelReferenceNumber));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("channelReferenceNumber cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "inquryType"));
                                 
                                        if (localInquryType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInquryType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("inquryType cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "photoRequestFlag"));
                                 
                                        if (localPhotoRequestFlag != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPhotoRequestFlag));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("photoRequestFlag cannot be null!!");
                                        }
                                    

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static InqCustEmiratesIDAuthDtlsReq_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            InqCustEmiratesIDAuthDtlsReq_type0 object =
                new InqCustEmiratesIDAuthDtlsReq_type0();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"InqCustEmiratesIDAuthDtlsReq_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (InqCustEmiratesIDAuthDtlsReq_type0)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","channelId").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"channelId" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setChannelId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","channelReferenceNumber").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"channelReferenceNumber" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setChannelReferenceNumber(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","inquryType").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"inquryType" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setInquryType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","photoRequestFlag").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"photoRequestFlag" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPhotoRequestFlag(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                              
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    
        public static class CustomerInformation_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = customerInformation_type0
                Namespace URI = http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd
                Namespace Prefix = ns10
                */
            

                        /**
                        * field for IdType
                        */

                        
                                    protected java.lang.String localIdType ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getIdType(){
                               return localIdType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IdType
                               */
                               public void setIdType(java.lang.String param){
                            
                                            this.localIdType=param;
                                    

                               }
                            

                        /**
                        * field for CardSerialNumber
                        */

                        
                                    protected java.lang.String localCardSerialNumber ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCardSerialNumber(){
                               return localCardSerialNumber;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CardSerialNumber
                               */
                               public void setCardSerialNumber(java.lang.String param){
                            
                                            this.localCardSerialNumber=param;
                                    

                               }
                            

                        /**
                        * field for CardNumber
                        */

                        
                                    protected java.lang.String localCardNumber ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCardNumber(){
                               return localCardNumber;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CardNumber
                               */
                               public void setCardNumber(java.lang.String param){
                            
                                            this.localCardNumber=param;
                                    

                               }
                            

                        /**
                        * field for IssueDate
                        */

                        
                                    protected java.lang.String localIssueDate ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getIssueDate(){
                               return localIssueDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IssueDate
                               */
                               public void setIssueDate(java.lang.String param){
                            
                                            this.localIssueDate=param;
                                    

                               }
                            

                        /**
                        * field for ExpiryDate
                        */

                        
                                    protected java.lang.String localExpiryDate ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getExpiryDate(){
                               return localExpiryDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ExpiryDate
                               */
                               public void setExpiryDate(java.lang.String param){
                            
                                            this.localExpiryDate=param;
                                    

                               }
                            

                        /**
                        * field for Gender
                        */

                        
                                    protected java.lang.String localGender ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getGender(){
                               return localGender;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Gender
                               */
                               public void setGender(java.lang.String param){
                            
                                            this.localGender=param;
                                    

                               }
                            

                        /**
                        * field for DateOfBirth
                        */

                        
                                    protected java.lang.String localDateOfBirth ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDateOfBirth(){
                               return localDateOfBirth;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DateOfBirth
                               */
                               public void setDateOfBirth(java.lang.String param){
                            
                                            this.localDateOfBirth=param;
                                    

                               }
                            

                        /**
                        * field for MaritalStatus
                        */

                        
                                    protected java.lang.String localMaritalStatus ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMaritalStatusTracker = false ;

                           public boolean isMaritalStatusSpecified(){
                               return localMaritalStatusTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMaritalStatus(){
                               return localMaritalStatus;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MaritalStatus
                               */
                               public void setMaritalStatus(java.lang.String param){
                            localMaritalStatusTracker = param != null;
                                   
                                            this.localMaritalStatus=param;
                                    

                               }
                            

                        /**
                        * field for Occupation
                        */

                        
                                    protected java.lang.String localOccupation ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOccupationTracker = false ;

                           public boolean isOccupationSpecified(){
                               return localOccupationTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOccupation(){
                               return localOccupation;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Occupation
                               */
                               public void setOccupation(java.lang.String param){
                            localOccupationTracker = param != null;
                                   
                                            this.localOccupation=param;
                                    

                               }
                            

                        /**
                        * field for Title
                        */

                        
                                    protected java.lang.String localTitle ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTitleTracker = false ;

                           public boolean isTitleSpecified(){
                               return localTitleTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTitle(){
                               return localTitle;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Title
                               */
                               public void setTitle(java.lang.String param){
                            localTitleTracker = param != null;
                                   
                                            this.localTitle=param;
                                    

                               }
                            

                        /**
                        * field for Nationality
                        */

                        
                                    protected java.lang.String localNationality ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getNationality(){
                               return localNationality;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Nationality
                               */
                               public void setNationality(java.lang.String param){
                            
                                            this.localNationality=param;
                                    

                               }
                            

                        /**
                        * field for MotherFirstName
                        */

                        
                                    protected java.lang.String localMotherFirstName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMotherFirstNameTracker = false ;

                           public boolean isMotherFirstNameSpecified(){
                               return localMotherFirstNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMotherFirstName(){
                               return localMotherFirstName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MotherFirstName
                               */
                               public void setMotherFirstName(java.lang.String param){
                            localMotherFirstNameTracker = param != null;
                                   
                                            this.localMotherFirstName=param;
                                    

                               }
                            

                        /**
                        * field for FamilyId
                        */

                        
                                    protected java.lang.String localFamilyId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFamilyIdTracker = false ;

                           public boolean isFamilyIdSpecified(){
                               return localFamilyIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getFamilyId(){
                               return localFamilyId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param FamilyId
                               */
                               public void setFamilyId(java.lang.String param){
                            localFamilyIdTracker = param != null;
                                   
                                            this.localFamilyId=param;
                                    

                               }
                            

                        /**
                        * field for HusbandIdn
                        */

                        
                                    protected java.lang.String localHusbandIdn ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHusbandIdnTracker = false ;

                           public boolean isHusbandIdnSpecified(){
                               return localHusbandIdnTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getHusbandIdn(){
                               return localHusbandIdn;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HusbandIdn
                               */
                               public void setHusbandIdn(java.lang.String param){
                            localHusbandIdnTracker = param != null;
                                   
                                            this.localHusbandIdn=param;
                                    

                               }
                            

                        /**
                        * field for SponsorType
                        */

                        
                                    protected java.lang.String localSponsorType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSponsorTypeTracker = false ;

                           public boolean isSponsorTypeSpecified(){
                               return localSponsorTypeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSponsorType(){
                               return localSponsorType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SponsorType
                               */
                               public void setSponsorType(java.lang.String param){
                            localSponsorTypeTracker = param != null;
                                   
                                            this.localSponsorType=param;
                                    

                               }
                            

                        /**
                        * field for SponsorNumber
                        */

                        
                                    protected java.lang.String localSponsorNumber ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSponsorNumberTracker = false ;

                           public boolean isSponsorNumberSpecified(){
                               return localSponsorNumberTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSponsorNumber(){
                               return localSponsorNumber;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SponsorNumber
                               */
                               public void setSponsorNumber(java.lang.String param){
                            localSponsorNumberTracker = param != null;
                                   
                                            this.localSponsorNumber=param;
                                    

                               }
                            

                        /**
                        * field for SponsorName
                        */

                        
                                    protected java.lang.String localSponsorName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSponsorNameTracker = false ;

                           public boolean isSponsorNameSpecified(){
                               return localSponsorNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSponsorName(){
                               return localSponsorName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SponsorName
                               */
                               public void setSponsorName(java.lang.String param){
                            localSponsorNameTracker = param != null;
                                   
                                            this.localSponsorName=param;
                                    

                               }
                            

                        /**
                        * field for ResidencyType
                        */

                        
                                    protected java.lang.String localResidencyType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResidencyTypeTracker = false ;

                           public boolean isResidencyTypeSpecified(){
                               return localResidencyTypeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getResidencyType(){
                               return localResidencyType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ResidencyType
                               */
                               public void setResidencyType(java.lang.String param){
                            localResidencyTypeTracker = param != null;
                                   
                                            this.localResidencyType=param;
                                    

                               }
                            

                        /**
                        * field for ResidencyNumber
                        */

                        
                                    protected java.lang.String localResidencyNumber ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResidencyNumberTracker = false ;

                           public boolean isResidencyNumberSpecified(){
                               return localResidencyNumberTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getResidencyNumber(){
                               return localResidencyNumber;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ResidencyNumber
                               */
                               public void setResidencyNumber(java.lang.String param){
                            localResidencyNumberTracker = param != null;
                                   
                                            this.localResidencyNumber=param;
                                    

                               }
                            

                        /**
                        * field for ResidencyExpiryDate
                        */

                        
                                    protected java.lang.String localResidencyExpiryDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResidencyExpiryDateTracker = false ;

                           public boolean isResidencyExpiryDateSpecified(){
                               return localResidencyExpiryDateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getResidencyExpiryDate(){
                               return localResidencyExpiryDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ResidencyExpiryDate
                               */
                               public void setResidencyExpiryDate(java.lang.String param){
                            localResidencyExpiryDateTracker = param != null;
                                   
                                            this.localResidencyExpiryDate=param;
                                    

                               }
                            

                        /**
                        * field for PassportNumber
                        */

                        
                                    protected java.lang.String localPassportNumber ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPassportNumberTracker = false ;

                           public boolean isPassportNumberSpecified(){
                               return localPassportNumberTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPassportNumber(){
                               return localPassportNumber;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PassportNumber
                               */
                               public void setPassportNumber(java.lang.String param){
                            localPassportNumberTracker = param != null;
                                   
                                            this.localPassportNumber=param;
                                    

                               }
                            

                        /**
                        * field for PassportCountry
                        */

                        
                                    protected java.lang.String localPassportCountry ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPassportCountryTracker = false ;

                           public boolean isPassportCountrySpecified(){
                               return localPassportCountryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPassportCountry(){
                               return localPassportCountry;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PassportCountry
                               */
                               public void setPassportCountry(java.lang.String param){
                            localPassportCountryTracker = param != null;
                                   
                                            this.localPassportCountry=param;
                                    

                               }
                            

                        /**
                        * field for PassportCountryDesc
                        */

                        
                                    protected java.lang.String localPassportCountryDesc ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPassportCountryDescTracker = false ;

                           public boolean isPassportCountryDescSpecified(){
                               return localPassportCountryDescTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPassportCountryDesc(){
                               return localPassportCountryDesc;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PassportCountryDesc
                               */
                               public void setPassportCountryDesc(java.lang.String param){
                            localPassportCountryDescTracker = param != null;
                                   
                                            this.localPassportCountryDesc=param;
                                    

                               }
                            

                        /**
                        * field for PlaceOfBirth
                        */

                        
                                    protected java.lang.String localPlaceOfBirth ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPlaceOfBirthTracker = false ;

                           public boolean isPlaceOfBirthSpecified(){
                               return localPlaceOfBirthTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPlaceOfBirth(){
                               return localPlaceOfBirth;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PlaceOfBirth
                               */
                               public void setPlaceOfBirth(java.lang.String param){
                            localPlaceOfBirthTracker = param != null;
                                   
                                            this.localPlaceOfBirth=param;
                                    

                               }
                            

                        /**
                        * field for PassportType
                        */

                        
                                    protected java.lang.String localPassportType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPassportTypeTracker = false ;

                           public boolean isPassportTypeSpecified(){
                               return localPassportTypeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPassportType(){
                               return localPassportType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PassportType
                               */
                               public void setPassportType(java.lang.String param){
                            localPassportTypeTracker = param != null;
                                   
                                            this.localPassportType=param;
                                    

                               }
                            

                        /**
                        * field for PassportIssueDate
                        */

                        
                                    protected java.lang.String localPassportIssueDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPassportIssueDateTracker = false ;

                           public boolean isPassportIssueDateSpecified(){
                               return localPassportIssueDateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPassportIssueDate(){
                               return localPassportIssueDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PassportIssueDate
                               */
                               public void setPassportIssueDate(java.lang.String param){
                            localPassportIssueDateTracker = param != null;
                                   
                                            this.localPassportIssueDate=param;
                                    

                               }
                            

                        /**
                        * field for PassportExpiryDate
                        */

                        
                                    protected java.lang.String localPassportExpiryDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPassportExpiryDateTracker = false ;

                           public boolean isPassportExpiryDateSpecified(){
                               return localPassportExpiryDateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPassportExpiryDate(){
                               return localPassportExpiryDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PassportExpiryDate
                               */
                               public void setPassportExpiryDate(java.lang.String param){
                            localPassportExpiryDateTracker = param != null;
                                   
                                            this.localPassportExpiryDate=param;
                                    

                               }
                            

                        /**
                        * field for HomeAddressEmirateDesc
                        */

                        
                                    protected java.lang.String localHomeAddressEmirateDesc ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHomeAddressEmirateDescTracker = false ;

                           public boolean isHomeAddressEmirateDescSpecified(){
                               return localHomeAddressEmirateDescTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getHomeAddressEmirateDesc(){
                               return localHomeAddressEmirateDesc;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HomeAddressEmirateDesc
                               */
                               public void setHomeAddressEmirateDesc(java.lang.String param){
                            localHomeAddressEmirateDescTracker = param != null;
                                   
                                            this.localHomeAddressEmirateDesc=param;
                                    

                               }
                            

                        /**
                        * field for HomeAddressCityCode
                        */

                        
                                    protected java.lang.String localHomeAddressCityCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHomeAddressCityCodeTracker = false ;

                           public boolean isHomeAddressCityCodeSpecified(){
                               return localHomeAddressCityCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getHomeAddressCityCode(){
                               return localHomeAddressCityCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HomeAddressCityCode
                               */
                               public void setHomeAddressCityCode(java.lang.String param){
                            localHomeAddressCityCodeTracker = param != null;
                                   
                                            this.localHomeAddressCityCode=param;
                                    

                               }
                            

                        /**
                        * field for HomeAddressCityDesc
                        */

                        
                                    protected java.lang.String localHomeAddressCityDesc ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHomeAddressCityDescTracker = false ;

                           public boolean isHomeAddressCityDescSpecified(){
                               return localHomeAddressCityDescTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getHomeAddressCityDesc(){
                               return localHomeAddressCityDesc;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HomeAddressCityDesc
                               */
                               public void setHomeAddressCityDesc(java.lang.String param){
                            localHomeAddressCityDescTracker = param != null;
                                   
                                            this.localHomeAddressCityDesc=param;
                                    

                               }
                            

                        /**
                        * field for HomeAddressStreet
                        */

                        
                                    protected java.lang.String localHomeAddressStreet ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHomeAddressStreetTracker = false ;

                           public boolean isHomeAddressStreetSpecified(){
                               return localHomeAddressStreetTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getHomeAddressStreet(){
                               return localHomeAddressStreet;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HomeAddressStreet
                               */
                               public void setHomeAddressStreet(java.lang.String param){
                            localHomeAddressStreetTracker = param != null;
                                   
                                            this.localHomeAddressStreet=param;
                                    

                               }
                            

                        /**
                        * field for HomeAddressAreaDesc
                        */

                        
                                    protected java.lang.String localHomeAddressAreaDesc ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHomeAddressAreaDescTracker = false ;

                           public boolean isHomeAddressAreaDescSpecified(){
                               return localHomeAddressAreaDescTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getHomeAddressAreaDesc(){
                               return localHomeAddressAreaDesc;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HomeAddressAreaDesc
                               */
                               public void setHomeAddressAreaDesc(java.lang.String param){
                            localHomeAddressAreaDescTracker = param != null;
                                   
                                            this.localHomeAddressAreaDesc=param;
                                    

                               }
                            

                        /**
                        * field for HomeAddressBuilding
                        */

                        
                                    protected java.lang.String localHomeAddressBuilding ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHomeAddressBuildingTracker = false ;

                           public boolean isHomeAddressBuildingSpecified(){
                               return localHomeAddressBuildingTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getHomeAddressBuilding(){
                               return localHomeAddressBuilding;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HomeAddressBuilding
                               */
                               public void setHomeAddressBuilding(java.lang.String param){
                            localHomeAddressBuildingTracker = param != null;
                                   
                                            this.localHomeAddressBuilding=param;
                                    

                               }
                            

                        /**
                        * field for HomeAddressAreaCode
                        */

                        
                                    protected java.lang.String localHomeAddressAreaCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHomeAddressAreaCodeTracker = false ;

                           public boolean isHomeAddressAreaCodeSpecified(){
                               return localHomeAddressAreaCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getHomeAddressAreaCode(){
                               return localHomeAddressAreaCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HomeAddressAreaCode
                               */
                               public void setHomeAddressAreaCode(java.lang.String param){
                            localHomeAddressAreaCodeTracker = param != null;
                                   
                                            this.localHomeAddressAreaCode=param;
                                    

                               }
                            

                        /**
                        * field for HomeAddressFlatNumber
                        */

                        
                                    protected java.lang.String localHomeAddressFlatNumber ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHomeAddressFlatNumberTracker = false ;

                           public boolean isHomeAddressFlatNumberSpecified(){
                               return localHomeAddressFlatNumberTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getHomeAddressFlatNumber(){
                               return localHomeAddressFlatNumber;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HomeAddressFlatNumber
                               */
                               public void setHomeAddressFlatNumber(java.lang.String param){
                            localHomeAddressFlatNumberTracker = param != null;
                                   
                                            this.localHomeAddressFlatNumber=param;
                                    

                               }
                            

                        /**
                        * field for HomeAddressTypeCode
                        */

                        
                                    protected java.lang.String localHomeAddressTypeCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHomeAddressTypeCodeTracker = false ;

                           public boolean isHomeAddressTypeCodeSpecified(){
                               return localHomeAddressTypeCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getHomeAddressTypeCode(){
                               return localHomeAddressTypeCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HomeAddressTypeCode
                               */
                               public void setHomeAddressTypeCode(java.lang.String param){
                            localHomeAddressTypeCodeTracker = param != null;
                                   
                                            this.localHomeAddressTypeCode=param;
                                    

                               }
                            

                        /**
                        * field for HomeAddressLocationCode
                        */

                        
                                    protected java.lang.String localHomeAddressLocationCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHomeAddressLocationCodeTracker = false ;

                           public boolean isHomeAddressLocationCodeSpecified(){
                               return localHomeAddressLocationCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getHomeAddressLocationCode(){
                               return localHomeAddressLocationCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HomeAddressLocationCode
                               */
                               public void setHomeAddressLocationCode(java.lang.String param){
                            localHomeAddressLocationCodeTracker = param != null;
                                   
                                            this.localHomeAddressLocationCode=param;
                                    

                               }
                            

                        /**
                        * field for HomeAddressResidentPhoneNumber
                        */

                        
                                    protected java.lang.String localHomeAddressResidentPhoneNumber ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHomeAddressResidentPhoneNumberTracker = false ;

                           public boolean isHomeAddressResidentPhoneNumberSpecified(){
                               return localHomeAddressResidentPhoneNumberTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getHomeAddressResidentPhoneNumber(){
                               return localHomeAddressResidentPhoneNumber;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HomeAddressResidentPhoneNumber
                               */
                               public void setHomeAddressResidentPhoneNumber(java.lang.String param){
                            localHomeAddressResidentPhoneNumberTracker = param != null;
                                   
                                            this.localHomeAddressResidentPhoneNumber=param;
                                    

                               }
                            

                        /**
                        * field for HomeAddressMobilePhoneNumber
                        */

                        
                                    protected java.lang.String localHomeAddressMobilePhoneNumber ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHomeAddressMobilePhoneNumberTracker = false ;

                           public boolean isHomeAddressMobilePhoneNumberSpecified(){
                               return localHomeAddressMobilePhoneNumberTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getHomeAddressMobilePhoneNumber(){
                               return localHomeAddressMobilePhoneNumber;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HomeAddressMobilePhoneNumber
                               */
                               public void setHomeAddressMobilePhoneNumber(java.lang.String param){
                            localHomeAddressMobilePhoneNumberTracker = param != null;
                                   
                                            this.localHomeAddressMobilePhoneNumber=param;
                                    

                               }
                            

                        /**
                        * field for HomeAddressPoBox
                        */

                        
                                    protected java.lang.String localHomeAddressPoBox ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHomeAddressPoBoxTracker = false ;

                           public boolean isHomeAddressPoBoxSpecified(){
                               return localHomeAddressPoBoxTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getHomeAddressPoBox(){
                               return localHomeAddressPoBox;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HomeAddressPoBox
                               */
                               public void setHomeAddressPoBox(java.lang.String param){
                            localHomeAddressPoBoxTracker = param != null;
                                   
                                            this.localHomeAddressPoBox=param;
                                    

                               }
                            

                        /**
                        * field for HomeAddressEmail
                        */

                        
                                    protected java.lang.String localHomeAddressEmail ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHomeAddressEmailTracker = false ;

                           public boolean isHomeAddressEmailSpecified(){
                               return localHomeAddressEmailTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getHomeAddressEmail(){
                               return localHomeAddressEmail;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HomeAddressEmail
                               */
                               public void setHomeAddressEmail(java.lang.String param){
                            localHomeAddressEmailTracker = param != null;
                                   
                                            this.localHomeAddressEmail=param;
                                    

                               }
                            

                        /**
                        * field for HomeAddressEmirateCode
                        */

                        
                                    protected java.lang.String localHomeAddressEmirateCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHomeAddressEmirateCodeTracker = false ;

                           public boolean isHomeAddressEmirateCodeSpecified(){
                               return localHomeAddressEmirateCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getHomeAddressEmirateCode(){
                               return localHomeAddressEmirateCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HomeAddressEmirateCode
                               */
                               public void setHomeAddressEmirateCode(java.lang.String param){
                            localHomeAddressEmirateCodeTracker = param != null;
                                   
                                            this.localHomeAddressEmirateCode=param;
                                    

                               }
                            

                        /**
                        * field for WorkAddressCompanyName
                        */

                        
                                    protected java.lang.String localWorkAddressCompanyName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWorkAddressCompanyNameTracker = false ;

                           public boolean isWorkAddressCompanyNameSpecified(){
                               return localWorkAddressCompanyNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getWorkAddressCompanyName(){
                               return localWorkAddressCompanyName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WorkAddressCompanyName
                               */
                               public void setWorkAddressCompanyName(java.lang.String param){
                            localWorkAddressCompanyNameTracker = param != null;
                                   
                                            this.localWorkAddressCompanyName=param;
                                    

                               }
                            

                        /**
                        * field for WorkAddressEmirateDesc
                        */

                        
                                    protected java.lang.String localWorkAddressEmirateDesc ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWorkAddressEmirateDescTracker = false ;

                           public boolean isWorkAddressEmirateDescSpecified(){
                               return localWorkAddressEmirateDescTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getWorkAddressEmirateDesc(){
                               return localWorkAddressEmirateDesc;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WorkAddressEmirateDesc
                               */
                               public void setWorkAddressEmirateDesc(java.lang.String param){
                            localWorkAddressEmirateDescTracker = param != null;
                                   
                                            this.localWorkAddressEmirateDesc=param;
                                    

                               }
                            

                        /**
                        * field for WorkAddressCityCode
                        */

                        
                                    protected java.lang.String localWorkAddressCityCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWorkAddressCityCodeTracker = false ;

                           public boolean isWorkAddressCityCodeSpecified(){
                               return localWorkAddressCityCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getWorkAddressCityCode(){
                               return localWorkAddressCityCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WorkAddressCityCode
                               */
                               public void setWorkAddressCityCode(java.lang.String param){
                            localWorkAddressCityCodeTracker = param != null;
                                   
                                            this.localWorkAddressCityCode=param;
                                    

                               }
                            

                        /**
                        * field for WorkAddressCityDesc
                        */

                        
                                    protected java.lang.String localWorkAddressCityDesc ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWorkAddressCityDescTracker = false ;

                           public boolean isWorkAddressCityDescSpecified(){
                               return localWorkAddressCityDescTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getWorkAddressCityDesc(){
                               return localWorkAddressCityDesc;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WorkAddressCityDesc
                               */
                               public void setWorkAddressCityDesc(java.lang.String param){
                            localWorkAddressCityDescTracker = param != null;
                                   
                                            this.localWorkAddressCityDesc=param;
                                    

                               }
                            

                        /**
                        * field for WorkAddressStreet
                        */

                        
                                    protected java.lang.String localWorkAddressStreet ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWorkAddressStreetTracker = false ;

                           public boolean isWorkAddressStreetSpecified(){
                               return localWorkAddressStreetTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getWorkAddressStreet(){
                               return localWorkAddressStreet;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WorkAddressStreet
                               */
                               public void setWorkAddressStreet(java.lang.String param){
                            localWorkAddressStreetTracker = param != null;
                                   
                                            this.localWorkAddressStreet=param;
                                    

                               }
                            

                        /**
                        * field for WorkAddressAreaDesc
                        */

                        
                                    protected java.lang.String localWorkAddressAreaDesc ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWorkAddressAreaDescTracker = false ;

                           public boolean isWorkAddressAreaDescSpecified(){
                               return localWorkAddressAreaDescTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getWorkAddressAreaDesc(){
                               return localWorkAddressAreaDesc;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WorkAddressAreaDesc
                               */
                               public void setWorkAddressAreaDesc(java.lang.String param){
                            localWorkAddressAreaDescTracker = param != null;
                                   
                                            this.localWorkAddressAreaDesc=param;
                                    

                               }
                            

                        /**
                        * field for WorkAddressBuilding
                        */

                        
                                    protected java.lang.String localWorkAddressBuilding ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWorkAddressBuildingTracker = false ;

                           public boolean isWorkAddressBuildingSpecified(){
                               return localWorkAddressBuildingTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getWorkAddressBuilding(){
                               return localWorkAddressBuilding;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WorkAddressBuilding
                               */
                               public void setWorkAddressBuilding(java.lang.String param){
                            localWorkAddressBuildingTracker = param != null;
                                   
                                            this.localWorkAddressBuilding=param;
                                    

                               }
                            

                        /**
                        * field for WorkAddressAreaCode
                        */

                        
                                    protected java.lang.String localWorkAddressAreaCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWorkAddressAreaCodeTracker = false ;

                           public boolean isWorkAddressAreaCodeSpecified(){
                               return localWorkAddressAreaCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getWorkAddressAreaCode(){
                               return localWorkAddressAreaCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WorkAddressAreaCode
                               */
                               public void setWorkAddressAreaCode(java.lang.String param){
                            localWorkAddressAreaCodeTracker = param != null;
                                   
                                            this.localWorkAddressAreaCode=param;
                                    

                               }
                            

                        /**
                        * field for WorkAddressTypeCode
                        */

                        
                                    protected java.lang.String localWorkAddressTypeCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWorkAddressTypeCodeTracker = false ;

                           public boolean isWorkAddressTypeCodeSpecified(){
                               return localWorkAddressTypeCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getWorkAddressTypeCode(){
                               return localWorkAddressTypeCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WorkAddressTypeCode
                               */
                               public void setWorkAddressTypeCode(java.lang.String param){
                            localWorkAddressTypeCodeTracker = param != null;
                                   
                                            this.localWorkAddressTypeCode=param;
                                    

                               }
                            

                        /**
                        * field for WorkAddressLocationCode
                        */

                        
                                    protected java.lang.String localWorkAddressLocationCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWorkAddressLocationCodeTracker = false ;

                           public boolean isWorkAddressLocationCodeSpecified(){
                               return localWorkAddressLocationCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getWorkAddressLocationCode(){
                               return localWorkAddressLocationCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WorkAddressLocationCode
                               */
                               public void setWorkAddressLocationCode(java.lang.String param){
                            localWorkAddressLocationCodeTracker = param != null;
                                   
                                            this.localWorkAddressLocationCode=param;
                                    

                               }
                            

                        /**
                        * field for WorkAddressLandPhoneNumber
                        */

                        
                                    protected java.lang.String localWorkAddressLandPhoneNumber ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWorkAddressLandPhoneNumberTracker = false ;

                           public boolean isWorkAddressLandPhoneNumberSpecified(){
                               return localWorkAddressLandPhoneNumberTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getWorkAddressLandPhoneNumber(){
                               return localWorkAddressLandPhoneNumber;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WorkAddressLandPhoneNumber
                               */
                               public void setWorkAddressLandPhoneNumber(java.lang.String param){
                            localWorkAddressLandPhoneNumberTracker = param != null;
                                   
                                            this.localWorkAddressLandPhoneNumber=param;
                                    

                               }
                            

                        /**
                        * field for WorkAddressMobilePhoneNumber
                        */

                        
                                    protected java.lang.String localWorkAddressMobilePhoneNumber ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWorkAddressMobilePhoneNumberTracker = false ;

                           public boolean isWorkAddressMobilePhoneNumberSpecified(){
                               return localWorkAddressMobilePhoneNumberTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getWorkAddressMobilePhoneNumber(){
                               return localWorkAddressMobilePhoneNumber;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WorkAddressMobilePhoneNumber
                               */
                               public void setWorkAddressMobilePhoneNumber(java.lang.String param){
                            localWorkAddressMobilePhoneNumberTracker = param != null;
                                   
                                            this.localWorkAddressMobilePhoneNumber=param;
                                    

                               }
                            

                        /**
                        * field for WorkAddressPoBox
                        */

                        
                                    protected java.lang.String localWorkAddressPoBox ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWorkAddressPoBoxTracker = false ;

                           public boolean isWorkAddressPoBoxSpecified(){
                               return localWorkAddressPoBoxTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getWorkAddressPoBox(){
                               return localWorkAddressPoBox;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WorkAddressPoBox
                               */
                               public void setWorkAddressPoBox(java.lang.String param){
                            localWorkAddressPoBoxTracker = param != null;
                                   
                                            this.localWorkAddressPoBox=param;
                                    

                               }
                            

                        /**
                        * field for WorkAddressEmirateCode
                        */

                        
                                    protected java.lang.String localWorkAddressEmirateCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWorkAddressEmirateCodeTracker = false ;

                           public boolean isWorkAddressEmirateCodeSpecified(){
                               return localWorkAddressEmirateCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getWorkAddressEmirateCode(){
                               return localWorkAddressEmirateCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WorkAddressEmirateCode
                               */
                               public void setWorkAddressEmirateCode(java.lang.String param){
                            localWorkAddressEmirateCodeTracker = param != null;
                                   
                                            this.localWorkAddressEmirateCode=param;
                                    

                               }
                            

                        /**
                        * field for WorkAddressEmail
                        */

                        
                                    protected java.lang.String localWorkAddressEmail ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWorkAddressEmailTracker = false ;

                           public boolean isWorkAddressEmailSpecified(){
                               return localWorkAddressEmailTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getWorkAddressEmail(){
                               return localWorkAddressEmail;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WorkAddressEmail
                               */
                               public void setWorkAddressEmail(java.lang.String param){
                            localWorkAddressEmailTracker = param != null;
                                   
                                            this.localWorkAddressEmail=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":customerInformation_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "customerInformation_type0",
                           xmlWriter);
                   }

               
                   }
               
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "IdType", xmlWriter);
                             

                                          if (localIdType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("IdType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localIdType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "cardSerialNumber", xmlWriter);
                             

                                          if (localCardSerialNumber==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("cardSerialNumber cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCardSerialNumber);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "cardNumber", xmlWriter);
                             

                                          if (localCardNumber==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("cardNumber cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCardNumber);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "issueDate", xmlWriter);
                             

                                          if (localIssueDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("issueDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localIssueDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "expiryDate", xmlWriter);
                             

                                          if (localExpiryDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("expiryDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localExpiryDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "gender", xmlWriter);
                             

                                          if (localGender==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("gender cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localGender);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "dateOfBirth", xmlWriter);
                             

                                          if (localDateOfBirth==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("dateOfBirth cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDateOfBirth);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localMaritalStatusTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "maritalStatus", xmlWriter);
                             

                                          if (localMaritalStatus==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("maritalStatus cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMaritalStatus);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOccupationTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "occupation", xmlWriter);
                             

                                          if (localOccupation==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("occupation cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOccupation);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTitleTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "title", xmlWriter);
                             

                                          if (localTitle==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("title cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTitle);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "nationality", xmlWriter);
                             

                                          if (localNationality==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("nationality cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localNationality);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localMotherFirstNameTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "motherFirstName", xmlWriter);
                             

                                          if (localMotherFirstName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("motherFirstName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMotherFirstName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localFamilyIdTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "familyId", xmlWriter);
                             

                                          if (localFamilyId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("familyId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localFamilyId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHusbandIdnTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "husbandIdn", xmlWriter);
                             

                                          if (localHusbandIdn==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("husbandIdn cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localHusbandIdn);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSponsorTypeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "sponsorType", xmlWriter);
                             

                                          if (localSponsorType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("sponsorType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSponsorType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSponsorNumberTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "sponsorNumber", xmlWriter);
                             

                                          if (localSponsorNumber==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("sponsorNumber cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSponsorNumber);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSponsorNameTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "sponsorName", xmlWriter);
                             

                                          if (localSponsorName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("sponsorName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSponsorName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localResidencyTypeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "residencyType", xmlWriter);
                             

                                          if (localResidencyType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("residencyType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localResidencyType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localResidencyNumberTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "residencyNumber", xmlWriter);
                             

                                          if (localResidencyNumber==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("residencyNumber cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localResidencyNumber);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localResidencyExpiryDateTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "residencyExpiryDate", xmlWriter);
                             

                                          if (localResidencyExpiryDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("residencyExpiryDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localResidencyExpiryDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPassportNumberTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "passportNumber", xmlWriter);
                             

                                          if (localPassportNumber==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("passportNumber cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPassportNumber);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPassportCountryTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "passportCountry", xmlWriter);
                             

                                          if (localPassportCountry==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("passportCountry cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPassportCountry);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPassportCountryDescTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "passportCountryDesc", xmlWriter);
                             

                                          if (localPassportCountryDesc==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("passportCountryDesc cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPassportCountryDesc);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPlaceOfBirthTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "placeOfBirth", xmlWriter);
                             

                                          if (localPlaceOfBirth==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("placeOfBirth cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPlaceOfBirth);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPassportTypeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "passportType", xmlWriter);
                             

                                          if (localPassportType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("passportType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPassportType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPassportIssueDateTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "passportIssueDate", xmlWriter);
                             

                                          if (localPassportIssueDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("passportIssueDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPassportIssueDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPassportExpiryDateTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "passportExpiryDate", xmlWriter);
                             

                                          if (localPassportExpiryDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("passportExpiryDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPassportExpiryDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHomeAddressEmirateDescTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "homeAddressEmirateDesc", xmlWriter);
                             

                                          if (localHomeAddressEmirateDesc==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("homeAddressEmirateDesc cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localHomeAddressEmirateDesc);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHomeAddressCityCodeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "homeAddressCityCode", xmlWriter);
                             

                                          if (localHomeAddressCityCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("homeAddressCityCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localHomeAddressCityCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHomeAddressCityDescTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "homeAddressCityDesc", xmlWriter);
                             

                                          if (localHomeAddressCityDesc==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("homeAddressCityDesc cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localHomeAddressCityDesc);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHomeAddressStreetTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "homeAddressStreet", xmlWriter);
                             

                                          if (localHomeAddressStreet==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("homeAddressStreet cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localHomeAddressStreet);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHomeAddressAreaDescTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "homeAddressAreaDesc", xmlWriter);
                             

                                          if (localHomeAddressAreaDesc==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("homeAddressAreaDesc cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localHomeAddressAreaDesc);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHomeAddressBuildingTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "homeAddressBuilding", xmlWriter);
                             

                                          if (localHomeAddressBuilding==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("homeAddressBuilding cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localHomeAddressBuilding);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHomeAddressAreaCodeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "homeAddressAreaCode", xmlWriter);
                             

                                          if (localHomeAddressAreaCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("homeAddressAreaCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localHomeAddressAreaCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHomeAddressFlatNumberTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "homeAddressFlatNumber", xmlWriter);
                             

                                          if (localHomeAddressFlatNumber==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("homeAddressFlatNumber cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localHomeAddressFlatNumber);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHomeAddressTypeCodeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "homeAddressTypeCode", xmlWriter);
                             

                                          if (localHomeAddressTypeCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("homeAddressTypeCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localHomeAddressTypeCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHomeAddressLocationCodeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "homeAddressLocationCode", xmlWriter);
                             

                                          if (localHomeAddressLocationCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("homeAddressLocationCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localHomeAddressLocationCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHomeAddressResidentPhoneNumberTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "homeAddressResidentPhoneNumber", xmlWriter);
                             

                                          if (localHomeAddressResidentPhoneNumber==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("homeAddressResidentPhoneNumber cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localHomeAddressResidentPhoneNumber);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHomeAddressMobilePhoneNumberTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "homeAddressMobilePhoneNumber", xmlWriter);
                             

                                          if (localHomeAddressMobilePhoneNumber==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("homeAddressMobilePhoneNumber cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localHomeAddressMobilePhoneNumber);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHomeAddressPoBoxTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "homeAddressPoBox", xmlWriter);
                             

                                          if (localHomeAddressPoBox==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("homeAddressPoBox cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localHomeAddressPoBox);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHomeAddressEmailTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "homeAddressEmail", xmlWriter);
                             

                                          if (localHomeAddressEmail==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("homeAddressEmail cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localHomeAddressEmail);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHomeAddressEmirateCodeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "homeAddressEmirateCode", xmlWriter);
                             

                                          if (localHomeAddressEmirateCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("homeAddressEmirateCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localHomeAddressEmirateCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWorkAddressCompanyNameTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "workAddressCompanyName", xmlWriter);
                             

                                          if (localWorkAddressCompanyName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("workAddressCompanyName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localWorkAddressCompanyName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWorkAddressEmirateDescTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "workAddressEmirateDesc", xmlWriter);
                             

                                          if (localWorkAddressEmirateDesc==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("workAddressEmirateDesc cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localWorkAddressEmirateDesc);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWorkAddressCityCodeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "workAddressCityCode", xmlWriter);
                             

                                          if (localWorkAddressCityCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("workAddressCityCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localWorkAddressCityCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWorkAddressCityDescTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "workAddressCityDesc", xmlWriter);
                             

                                          if (localWorkAddressCityDesc==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("workAddressCityDesc cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localWorkAddressCityDesc);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWorkAddressStreetTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "workAddressStreet", xmlWriter);
                             

                                          if (localWorkAddressStreet==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("workAddressStreet cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localWorkAddressStreet);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWorkAddressAreaDescTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "workAddressAreaDesc", xmlWriter);
                             

                                          if (localWorkAddressAreaDesc==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("workAddressAreaDesc cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localWorkAddressAreaDesc);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWorkAddressBuildingTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "workAddressBuilding", xmlWriter);
                             

                                          if (localWorkAddressBuilding==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("workAddressBuilding cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localWorkAddressBuilding);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWorkAddressAreaCodeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "workAddressAreaCode", xmlWriter);
                             

                                          if (localWorkAddressAreaCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("workAddressAreaCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localWorkAddressAreaCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWorkAddressTypeCodeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "workAddressTypeCode", xmlWriter);
                             

                                          if (localWorkAddressTypeCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("workAddressTypeCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localWorkAddressTypeCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWorkAddressLocationCodeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "workAddressLocationCode", xmlWriter);
                             

                                          if (localWorkAddressLocationCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("workAddressLocationCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localWorkAddressLocationCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWorkAddressLandPhoneNumberTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "workAddressLandPhoneNumber", xmlWriter);
                             

                                          if (localWorkAddressLandPhoneNumber==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("workAddressLandPhoneNumber cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localWorkAddressLandPhoneNumber);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWorkAddressMobilePhoneNumberTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "workAddressMobilePhoneNumber", xmlWriter);
                             

                                          if (localWorkAddressMobilePhoneNumber==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("workAddressMobilePhoneNumber cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localWorkAddressMobilePhoneNumber);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWorkAddressPoBoxTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "workAddressPoBox", xmlWriter);
                             

                                          if (localWorkAddressPoBox==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("workAddressPoBox cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localWorkAddressPoBox);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWorkAddressEmirateCodeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "workAddressEmirateCode", xmlWriter);
                             

                                          if (localWorkAddressEmirateCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("workAddressEmirateCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localWorkAddressEmirateCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWorkAddressEmailTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "workAddressEmail", xmlWriter);
                             

                                          if (localWorkAddressEmail==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("workAddressEmail cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localWorkAddressEmail);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd")){
                return "ns10";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
                    if (uri == null || uri.length() == 0) {
                        break;
                    }
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "IdType"));
                                 
                                        if (localIdType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIdType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("IdType cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "cardSerialNumber"));
                                 
                                        if (localCardSerialNumber != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCardSerialNumber));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("cardSerialNumber cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "cardNumber"));
                                 
                                        if (localCardNumber != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCardNumber));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("cardNumber cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "issueDate"));
                                 
                                        if (localIssueDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIssueDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("issueDate cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "expiryDate"));
                                 
                                        if (localExpiryDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExpiryDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("expiryDate cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "gender"));
                                 
                                        if (localGender != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGender));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("gender cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "dateOfBirth"));
                                 
                                        if (localDateOfBirth != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDateOfBirth));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("dateOfBirth cannot be null!!");
                                        }
                                     if (localMaritalStatusTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "maritalStatus"));
                                 
                                        if (localMaritalStatus != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMaritalStatus));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("maritalStatus cannot be null!!");
                                        }
                                    } if (localOccupationTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "occupation"));
                                 
                                        if (localOccupation != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOccupation));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("occupation cannot be null!!");
                                        }
                                    } if (localTitleTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "title"));
                                 
                                        if (localTitle != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTitle));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("title cannot be null!!");
                                        }
                                    }
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "nationality"));
                                 
                                        if (localNationality != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNationality));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("nationality cannot be null!!");
                                        }
                                     if (localMotherFirstNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "motherFirstName"));
                                 
                                        if (localMotherFirstName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMotherFirstName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("motherFirstName cannot be null!!");
                                        }
                                    } if (localFamilyIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "familyId"));
                                 
                                        if (localFamilyId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFamilyId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("familyId cannot be null!!");
                                        }
                                    } if (localHusbandIdnTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "husbandIdn"));
                                 
                                        if (localHusbandIdn != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHusbandIdn));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("husbandIdn cannot be null!!");
                                        }
                                    } if (localSponsorTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "sponsorType"));
                                 
                                        if (localSponsorType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSponsorType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("sponsorType cannot be null!!");
                                        }
                                    } if (localSponsorNumberTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "sponsorNumber"));
                                 
                                        if (localSponsorNumber != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSponsorNumber));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("sponsorNumber cannot be null!!");
                                        }
                                    } if (localSponsorNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "sponsorName"));
                                 
                                        if (localSponsorName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSponsorName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("sponsorName cannot be null!!");
                                        }
                                    } if (localResidencyTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "residencyType"));
                                 
                                        if (localResidencyType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResidencyType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("residencyType cannot be null!!");
                                        }
                                    } if (localResidencyNumberTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "residencyNumber"));
                                 
                                        if (localResidencyNumber != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResidencyNumber));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("residencyNumber cannot be null!!");
                                        }
                                    } if (localResidencyExpiryDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "residencyExpiryDate"));
                                 
                                        if (localResidencyExpiryDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResidencyExpiryDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("residencyExpiryDate cannot be null!!");
                                        }
                                    } if (localPassportNumberTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "passportNumber"));
                                 
                                        if (localPassportNumber != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPassportNumber));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("passportNumber cannot be null!!");
                                        }
                                    } if (localPassportCountryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "passportCountry"));
                                 
                                        if (localPassportCountry != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPassportCountry));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("passportCountry cannot be null!!");
                                        }
                                    } if (localPassportCountryDescTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "passportCountryDesc"));
                                 
                                        if (localPassportCountryDesc != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPassportCountryDesc));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("passportCountryDesc cannot be null!!");
                                        }
                                    } if (localPlaceOfBirthTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "placeOfBirth"));
                                 
                                        if (localPlaceOfBirth != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPlaceOfBirth));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("placeOfBirth cannot be null!!");
                                        }
                                    } if (localPassportTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "passportType"));
                                 
                                        if (localPassportType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPassportType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("passportType cannot be null!!");
                                        }
                                    } if (localPassportIssueDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "passportIssueDate"));
                                 
                                        if (localPassportIssueDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPassportIssueDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("passportIssueDate cannot be null!!");
                                        }
                                    } if (localPassportExpiryDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "passportExpiryDate"));
                                 
                                        if (localPassportExpiryDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPassportExpiryDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("passportExpiryDate cannot be null!!");
                                        }
                                    } if (localHomeAddressEmirateDescTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "homeAddressEmirateDesc"));
                                 
                                        if (localHomeAddressEmirateDesc != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHomeAddressEmirateDesc));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("homeAddressEmirateDesc cannot be null!!");
                                        }
                                    } if (localHomeAddressCityCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "homeAddressCityCode"));
                                 
                                        if (localHomeAddressCityCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHomeAddressCityCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("homeAddressCityCode cannot be null!!");
                                        }
                                    } if (localHomeAddressCityDescTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "homeAddressCityDesc"));
                                 
                                        if (localHomeAddressCityDesc != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHomeAddressCityDesc));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("homeAddressCityDesc cannot be null!!");
                                        }
                                    } if (localHomeAddressStreetTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "homeAddressStreet"));
                                 
                                        if (localHomeAddressStreet != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHomeAddressStreet));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("homeAddressStreet cannot be null!!");
                                        }
                                    } if (localHomeAddressAreaDescTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "homeAddressAreaDesc"));
                                 
                                        if (localHomeAddressAreaDesc != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHomeAddressAreaDesc));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("homeAddressAreaDesc cannot be null!!");
                                        }
                                    } if (localHomeAddressBuildingTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "homeAddressBuilding"));
                                 
                                        if (localHomeAddressBuilding != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHomeAddressBuilding));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("homeAddressBuilding cannot be null!!");
                                        }
                                    } if (localHomeAddressAreaCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "homeAddressAreaCode"));
                                 
                                        if (localHomeAddressAreaCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHomeAddressAreaCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("homeAddressAreaCode cannot be null!!");
                                        }
                                    } if (localHomeAddressFlatNumberTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "homeAddressFlatNumber"));
                                 
                                        if (localHomeAddressFlatNumber != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHomeAddressFlatNumber));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("homeAddressFlatNumber cannot be null!!");
                                        }
                                    } if (localHomeAddressTypeCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "homeAddressTypeCode"));
                                 
                                        if (localHomeAddressTypeCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHomeAddressTypeCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("homeAddressTypeCode cannot be null!!");
                                        }
                                    } if (localHomeAddressLocationCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "homeAddressLocationCode"));
                                 
                                        if (localHomeAddressLocationCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHomeAddressLocationCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("homeAddressLocationCode cannot be null!!");
                                        }
                                    } if (localHomeAddressResidentPhoneNumberTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "homeAddressResidentPhoneNumber"));
                                 
                                        if (localHomeAddressResidentPhoneNumber != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHomeAddressResidentPhoneNumber));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("homeAddressResidentPhoneNumber cannot be null!!");
                                        }
                                    } if (localHomeAddressMobilePhoneNumberTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "homeAddressMobilePhoneNumber"));
                                 
                                        if (localHomeAddressMobilePhoneNumber != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHomeAddressMobilePhoneNumber));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("homeAddressMobilePhoneNumber cannot be null!!");
                                        }
                                    } if (localHomeAddressPoBoxTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "homeAddressPoBox"));
                                 
                                        if (localHomeAddressPoBox != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHomeAddressPoBox));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("homeAddressPoBox cannot be null!!");
                                        }
                                    } if (localHomeAddressEmailTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "homeAddressEmail"));
                                 
                                        if (localHomeAddressEmail != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHomeAddressEmail));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("homeAddressEmail cannot be null!!");
                                        }
                                    } if (localHomeAddressEmirateCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "homeAddressEmirateCode"));
                                 
                                        if (localHomeAddressEmirateCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHomeAddressEmirateCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("homeAddressEmirateCode cannot be null!!");
                                        }
                                    } if (localWorkAddressCompanyNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "workAddressCompanyName"));
                                 
                                        if (localWorkAddressCompanyName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWorkAddressCompanyName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("workAddressCompanyName cannot be null!!");
                                        }
                                    } if (localWorkAddressEmirateDescTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "workAddressEmirateDesc"));
                                 
                                        if (localWorkAddressEmirateDesc != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWorkAddressEmirateDesc));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("workAddressEmirateDesc cannot be null!!");
                                        }
                                    } if (localWorkAddressCityCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "workAddressCityCode"));
                                 
                                        if (localWorkAddressCityCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWorkAddressCityCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("workAddressCityCode cannot be null!!");
                                        }
                                    } if (localWorkAddressCityDescTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "workAddressCityDesc"));
                                 
                                        if (localWorkAddressCityDesc != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWorkAddressCityDesc));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("workAddressCityDesc cannot be null!!");
                                        }
                                    } if (localWorkAddressStreetTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "workAddressStreet"));
                                 
                                        if (localWorkAddressStreet != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWorkAddressStreet));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("workAddressStreet cannot be null!!");
                                        }
                                    } if (localWorkAddressAreaDescTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "workAddressAreaDesc"));
                                 
                                        if (localWorkAddressAreaDesc != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWorkAddressAreaDesc));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("workAddressAreaDesc cannot be null!!");
                                        }
                                    } if (localWorkAddressBuildingTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "workAddressBuilding"));
                                 
                                        if (localWorkAddressBuilding != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWorkAddressBuilding));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("workAddressBuilding cannot be null!!");
                                        }
                                    } if (localWorkAddressAreaCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "workAddressAreaCode"));
                                 
                                        if (localWorkAddressAreaCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWorkAddressAreaCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("workAddressAreaCode cannot be null!!");
                                        }
                                    } if (localWorkAddressTypeCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "workAddressTypeCode"));
                                 
                                        if (localWorkAddressTypeCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWorkAddressTypeCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("workAddressTypeCode cannot be null!!");
                                        }
                                    } if (localWorkAddressLocationCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "workAddressLocationCode"));
                                 
                                        if (localWorkAddressLocationCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWorkAddressLocationCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("workAddressLocationCode cannot be null!!");
                                        }
                                    } if (localWorkAddressLandPhoneNumberTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "workAddressLandPhoneNumber"));
                                 
                                        if (localWorkAddressLandPhoneNumber != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWorkAddressLandPhoneNumber));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("workAddressLandPhoneNumber cannot be null!!");
                                        }
                                    } if (localWorkAddressMobilePhoneNumberTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "workAddressMobilePhoneNumber"));
                                 
                                        if (localWorkAddressMobilePhoneNumber != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWorkAddressMobilePhoneNumber));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("workAddressMobilePhoneNumber cannot be null!!");
                                        }
                                    } if (localWorkAddressPoBoxTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "workAddressPoBox"));
                                 
                                        if (localWorkAddressPoBox != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWorkAddressPoBox));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("workAddressPoBox cannot be null!!");
                                        }
                                    } if (localWorkAddressEmirateCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "workAddressEmirateCode"));
                                 
                                        if (localWorkAddressEmirateCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWorkAddressEmirateCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("workAddressEmirateCode cannot be null!!");
                                        }
                                    } if (localWorkAddressEmailTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "workAddressEmail"));
                                 
                                        if (localWorkAddressEmail != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWorkAddressEmail));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("workAddressEmail cannot be null!!");
                                        }
                                    }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static CustomerInformation_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            CustomerInformation_type0 object =
                new CustomerInformation_type0();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"customerInformation_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (CustomerInformation_type0)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","IdType").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"IdType" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setIdType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","cardSerialNumber").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"cardSerialNumber" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCardSerialNumber(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","cardNumber").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"cardNumber" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCardNumber(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","issueDate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"issueDate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setIssueDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","expiryDate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"expiryDate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setExpiryDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","gender").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"gender" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setGender(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","dateOfBirth").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"dateOfBirth" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDateOfBirth(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","maritalStatus").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"maritalStatus" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMaritalStatus(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","occupation").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"occupation" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOccupation(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","title").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"title" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTitle(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","nationality").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"nationality" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNationality(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","motherFirstName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"motherFirstName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMotherFirstName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","familyId").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"familyId" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFamilyId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","husbandIdn").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"husbandIdn" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHusbandIdn(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","sponsorType").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"sponsorType" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSponsorType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","sponsorNumber").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"sponsorNumber" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSponsorNumber(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","sponsorName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"sponsorName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSponsorName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","residencyType").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"residencyType" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setResidencyType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","residencyNumber").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"residencyNumber" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setResidencyNumber(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","residencyExpiryDate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"residencyExpiryDate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setResidencyExpiryDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","passportNumber").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"passportNumber" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPassportNumber(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","passportCountry").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"passportCountry" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPassportCountry(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","passportCountryDesc").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"passportCountryDesc" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPassportCountryDesc(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","placeOfBirth").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"placeOfBirth" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPlaceOfBirth(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","passportType").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"passportType" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPassportType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","passportIssueDate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"passportIssueDate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPassportIssueDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","passportExpiryDate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"passportExpiryDate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPassportExpiryDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","homeAddressEmirateDesc").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"homeAddressEmirateDesc" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHomeAddressEmirateDesc(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","homeAddressCityCode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"homeAddressCityCode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHomeAddressCityCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","homeAddressCityDesc").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"homeAddressCityDesc" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHomeAddressCityDesc(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","homeAddressStreet").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"homeAddressStreet" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHomeAddressStreet(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","homeAddressAreaDesc").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"homeAddressAreaDesc" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHomeAddressAreaDesc(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","homeAddressBuilding").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"homeAddressBuilding" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHomeAddressBuilding(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","homeAddressAreaCode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"homeAddressAreaCode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHomeAddressAreaCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","homeAddressFlatNumber").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"homeAddressFlatNumber" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHomeAddressFlatNumber(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","homeAddressTypeCode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"homeAddressTypeCode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHomeAddressTypeCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","homeAddressLocationCode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"homeAddressLocationCode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHomeAddressLocationCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","homeAddressResidentPhoneNumber").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"homeAddressResidentPhoneNumber" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHomeAddressResidentPhoneNumber(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","homeAddressMobilePhoneNumber").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"homeAddressMobilePhoneNumber" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHomeAddressMobilePhoneNumber(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","homeAddressPoBox").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"homeAddressPoBox" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHomeAddressPoBox(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","homeAddressEmail").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"homeAddressEmail" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHomeAddressEmail(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","homeAddressEmirateCode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"homeAddressEmirateCode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHomeAddressEmirateCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","workAddressCompanyName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"workAddressCompanyName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWorkAddressCompanyName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","workAddressEmirateDesc").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"workAddressEmirateDesc" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWorkAddressEmirateDesc(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","workAddressCityCode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"workAddressCityCode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWorkAddressCityCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","workAddressCityDesc").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"workAddressCityDesc" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWorkAddressCityDesc(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","workAddressStreet").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"workAddressStreet" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWorkAddressStreet(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","workAddressAreaDesc").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"workAddressAreaDesc" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWorkAddressAreaDesc(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","workAddressBuilding").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"workAddressBuilding" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWorkAddressBuilding(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","workAddressAreaCode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"workAddressAreaCode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWorkAddressAreaCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","workAddressTypeCode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"workAddressTypeCode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWorkAddressTypeCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","workAddressLocationCode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"workAddressLocationCode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWorkAddressLocationCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","workAddressLandPhoneNumber").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"workAddressLandPhoneNumber" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWorkAddressLandPhoneNumber(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","workAddressMobilePhoneNumber").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"workAddressMobilePhoneNumber" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWorkAddressMobilePhoneNumber(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","workAddressPoBox").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"workAddressPoBox" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWorkAddressPoBox(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","workAddressEmirateCode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"workAddressEmirateCode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWorkAddressEmirateCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","workAddressEmail").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"workAddressEmail" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWorkAddressEmail(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    
        public static class Header
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://www.adcb.com/esb/common/header.xsd",
                "header",
                "ns2");

            

                        /**
                        * field for Header
                        */

                        
                                    protected HeaderType localHeader ;
                                

                           /**
                           * Auto generated getter method
                           * @return HeaderType
                           */
                           public  HeaderType getHeader(){
                               return localHeader;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Header
                               */
                               public void setHeader(HeaderType param){
                            
                                            this.localHeader=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME);
               return factory.createOMElement(dataSource,MY_QNAME);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                
                //We can safely assume an element has only one type associated with it
                
                                 if (localHeader==null){
                                   throw new org.apache.axis2.databinding.ADBException("header cannot be null!");
                                 }
                                 localHeader.serialize(MY_QNAME,xmlWriter);
                            

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/common/header.xsd")){
                return "ns2";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
                    if (uri == null || uri.length() == 0) {
                        break;
                    }
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                
                //We can safely assume an element has only one type associated with it
                return localHeader.getPullParser(MY_QNAME);

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static Header parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            Header object =
                new Header();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                   
                while(!reader.isEndElement()) {
                    if (reader.isStartElement() ){
                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header").equals(reader.getName())){
                                
                                                object.setHeader(HeaderType.Factory.parse(reader));
                                            
                              }  // End of if for expected property start element
                                
                             else{
                                        // A start element we are not expecting indicates an invalid parameter was passed
                                        throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                             }
                          
                             } else {
                                reader.next();
                             }  
                           }  // end of while loop
                        



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    
        public static class PhotoImage_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = photoImage_type0
                Namespace URI = http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd
                Namespace Prefix = ns10
                */
            

                        /**
                        * field for ImageBinaryData
                        */

                        
                                    protected javax.activation.DataHandler localImageBinaryData ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localImageBinaryDataTracker = false ;

                           public boolean isImageBinaryDataSpecified(){
                               return localImageBinaryDataTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return javax.activation.DataHandler
                           */
                           public  javax.activation.DataHandler getImageBinaryData(){
                               return localImageBinaryData;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ImageBinaryData
                               */
                               public void setImageBinaryData(javax.activation.DataHandler param){
                            localImageBinaryDataTracker = param != null;
                                   
                                            this.localImageBinaryData=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":photoImage_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "photoImage_type0",
                           xmlWriter);
                   }

               
                   }
                if (localImageBinaryDataTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "imageBinaryData", xmlWriter);
                             
                                        
                                    if (localImageBinaryData!=null)  {
                                       try {
                                           org.apache.axiom.util.stax.XMLStreamWriterUtils.writeDataHandler(xmlWriter, localImageBinaryData, null, true);
                                       } catch (java.io.IOException ex) {
                                           throw new javax.xml.stream.XMLStreamException("Unable to read data handler for imageBinaryData", ex);
                                       }
                                    } else {
                                         
                                    }
                                 
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd")){
                return "ns10";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
                    if (uri == null || uri.length() == 0) {
                        break;
                    }
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localImageBinaryDataTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                        "imageBinaryData"));
                                
                            elementList.add(localImageBinaryData);
                        }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static PhotoImage_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            PhotoImage_type0 object =
                new PhotoImage_type0();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"photoImage_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (PhotoImage_type0)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","imageBinaryData").equals(reader.getName())){
                                
                                            object.setImageBinaryData(org.apache.axiom.util.stax.XMLStreamReaderUtils.getDataHandlerFromElement(reader));
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    
        public static class AuthenticationDetails_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = authenticationDetails_type0
                Namespace URI = http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd
                Namespace Prefix = ns10
                */
            

                        /**
                        * field for EidaCardNumber
                        */

                        
                                    protected java.lang.String localEidaCardNumber ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEidaCardNumber(){
                               return localEidaCardNumber;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EidaCardNumber
                               */
                               public void setEidaCardNumber(java.lang.String param){
                            
                                            this.localEidaCardNumber=param;
                                    

                               }
                            

                        /**
                        * field for EidaCardHolderName
                        */

                        
                                    protected java.lang.String localEidaCardHolderName ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEidaCardHolderName(){
                               return localEidaCardHolderName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EidaCardHolderName
                               */
                               public void setEidaCardHolderName(java.lang.String param){
                            
                                            this.localEidaCardHolderName=param;
                                    

                               }
                            

                        /**
                        * field for AuthReferenceNumber
                        */

                        
                                    protected java.lang.String localAuthReferenceNumber ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAuthReferenceNumber(){
                               return localAuthReferenceNumber;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AuthReferenceNumber
                               */
                               public void setAuthReferenceNumber(java.lang.String param){
                            
                                            this.localAuthReferenceNumber=param;
                                    

                               }
                            

                        /**
                        * field for FingerIndex
                        */

                        
                                    protected java.lang.String localFingerIndex ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFingerIndexTracker = false ;

                           public boolean isFingerIndexSpecified(){
                               return localFingerIndexTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getFingerIndex(){
                               return localFingerIndex;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param FingerIndex
                               */
                               public void setFingerIndex(java.lang.String param){
                            localFingerIndexTracker = param != null;
                                   
                                            this.localFingerIndex=param;
                                    

                               }
                            

                        /**
                        * field for AuthStaus
                        */

                        
                                    protected java.lang.String localAuthStaus ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAuthStaus(){
                               return localAuthStaus;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AuthStaus
                               */
                               public void setAuthStaus(java.lang.String param){
                            
                                            this.localAuthStaus=param;
                                    

                               }
                            

                        /**
                        * field for AuthenticationType
                        */

                        
                                    protected java.lang.String localAuthenticationType ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAuthenticationType(){
                               return localAuthenticationType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AuthenticationType
                               */
                               public void setAuthenticationType(java.lang.String param){
                            
                                            this.localAuthenticationType=param;
                                    

                               }
                            

                        /**
                        * field for AuthDateTime
                        */

                        
                                    protected java.lang.String localAuthDateTime ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAuthDateTime(){
                               return localAuthDateTime;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AuthDateTime
                               */
                               public void setAuthDateTime(java.lang.String param){
                            
                                            this.localAuthDateTime=param;
                                    

                               }
                            

                        /**
                        * field for ErrorCode
                        */

                        
                                    protected java.lang.String localErrorCode ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getErrorCode(){
                               return localErrorCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ErrorCode
                               */
                               public void setErrorCode(java.lang.String param){
                            
                                            this.localErrorCode=param;
                                    

                               }
                            

                        /**
                        * field for ErrorMsg
                        */

                        
                                    protected java.lang.String localErrorMsg ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getErrorMsg(){
                               return localErrorMsg;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ErrorMsg
                               */
                               public void setErrorMsg(java.lang.String param){
                            
                                            this.localErrorMsg=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":authenticationDetails_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "authenticationDetails_type0",
                           xmlWriter);
                   }

               
                   }
               
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "eidaCardNumber", xmlWriter);
                             

                                          if (localEidaCardNumber==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("eidaCardNumber cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEidaCardNumber);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "eidaCardHolderName", xmlWriter);
                             

                                          if (localEidaCardHolderName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("eidaCardHolderName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEidaCardHolderName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "authReferenceNumber", xmlWriter);
                             

                                          if (localAuthReferenceNumber==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("authReferenceNumber cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAuthReferenceNumber);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localFingerIndexTracker){
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "fingerIndex", xmlWriter);
                             

                                          if (localFingerIndex==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("fingerIndex cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localFingerIndex);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "authStaus", xmlWriter);
                             

                                          if (localAuthStaus==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("authStaus cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAuthStaus);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "authenticationType", xmlWriter);
                             

                                          if (localAuthenticationType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("authenticationType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAuthenticationType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "authDateTime", xmlWriter);
                             

                                          if (localAuthDateTime==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("authDateTime cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAuthDateTime);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "errorCode", xmlWriter);
                             

                                          if (localErrorCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("errorCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localErrorCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd";
                                    writeStartElement(null, namespace, "errorMsg", xmlWriter);
                             

                                          if (localErrorMsg==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("errorMsg cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localErrorMsg);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd")){
                return "ns10";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
                    if (uri == null || uri.length() == 0) {
                        break;
                    }
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "eidaCardNumber"));
                                 
                                        if (localEidaCardNumber != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEidaCardNumber));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("eidaCardNumber cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "eidaCardHolderName"));
                                 
                                        if (localEidaCardHolderName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEidaCardHolderName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("eidaCardHolderName cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "authReferenceNumber"));
                                 
                                        if (localAuthReferenceNumber != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuthReferenceNumber));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("authReferenceNumber cannot be null!!");
                                        }
                                     if (localFingerIndexTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "fingerIndex"));
                                 
                                        if (localFingerIndex != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFingerIndex));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("fingerIndex cannot be null!!");
                                        }
                                    }
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "authStaus"));
                                 
                                        if (localAuthStaus != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuthStaus));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("authStaus cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "authenticationType"));
                                 
                                        if (localAuthenticationType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuthenticationType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("authenticationType cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "authDateTime"));
                                 
                                        if (localAuthDateTime != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuthDateTime));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("authDateTime cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "errorCode"));
                                 
                                        if (localErrorCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localErrorCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("errorCode cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd",
                                                                      "errorMsg"));
                                 
                                        if (localErrorMsg != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localErrorMsg));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("errorMsg cannot be null!!");
                                        }
                                    

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static AuthenticationDetails_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            AuthenticationDetails_type0 object =
                new AuthenticationDetails_type0();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"authenticationDetails_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (AuthenticationDetails_type0)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","eidaCardNumber").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"eidaCardNumber" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEidaCardNumber(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","eidaCardHolderName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"eidaCardHolderName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEidaCardHolderName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","authReferenceNumber").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"authReferenceNumber" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAuthReferenceNumber(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","fingerIndex").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"fingerIndex" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFingerIndex(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","authStaus").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"authStaus" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAuthStaus(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","authenticationType").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"authenticationType" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAuthenticationType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","authDateTime").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"authDateTime" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAuthDateTime(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","errorCode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"errorCode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setErrorCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqCustEmiratesIDAuthDtls.xsd","errorMsg").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"errorMsg" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setErrorMsg(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                              
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    
            private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.InqCustEmiratesIDAuthDtlsReqMsg param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.InqCustEmiratesIDAuthDtlsReqMsg.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.InqCustEmiratesIDAuthDtlsResMsg param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.InqCustEmiratesIDAuthDtlsResMsg.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.InqCustEmiratesIDAuthDtlsReqMsg param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.InqCustEmiratesIDAuthDtlsReqMsg.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }


        private  java.lang.Object fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{

        try {
        
                if (com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.InqCustEmiratesIDAuthDtlsReqMsg.class.equals(type)){
                
                           return com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.InqCustEmiratesIDAuthDtlsReqMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.InqCustEmiratesIDAuthDtlsResMsg.class.equals(type)){
                
                           return com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.InqCustEmiratesIDAuthDtlsResMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    
   }
   