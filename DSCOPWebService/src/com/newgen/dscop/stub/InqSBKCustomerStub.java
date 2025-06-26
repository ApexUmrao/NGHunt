
/**
 * InqSBKCustomerStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:22:40 CEST)
 */
        package com.newgen.dscop.stub;

        

        /*
        *  InqSBKCustomerStub java implementation
        */

        
        public class InqSBKCustomerStub extends org.apache.axis2.client.Stub
        {
        protected org.apache.axis2.description.AxisOperation[] _operations;

        //hashmaps to keep the fault mapping
        private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
        private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
        private java.util.HashMap faultMessageMap = new java.util.HashMap();

        //Added by Nishant 
        public String resChqLeaves = "";
        public String resKioskEIDA = "";
        public String resCustInfo = "";
		public String resKioskDtls = "";
        
        private static int counter = 0;

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
     _service = new org.apache.axis2.description.AxisService("InqSBKCustomer" + getUniqueSuffix());
     addAnonymousOperations();

        //creating the operations
        org.apache.axis2.description.AxisOperation __operation;

        _operations = new org.apache.axis2.description.AxisOperation[4];
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://xmlns.example.com/1454403905186", "getChqLeavesIssueAccts_Oper"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[0]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://xmlns.example.com/1454403905186", "getKioskCustEIDAInfo_Oper"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[1]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://xmlns.example.com/1454403905186", "getCustomerInfo_Oper"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[2]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://xmlns.example.com/1454403905186", "getKioskCustActivitiesDtl_Oper"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[3]=__operation;
            
        
        }

    //populates the faults
    private void populateFaults(){
         


    }

    /**
      *Constructor that takes in a configContext
      */

    public InqSBKCustomerStub(org.apache.axis2.context.ConfigurationContext configurationContext,
       java.lang.String targetEndpoint)
       throws org.apache.axis2.AxisFault {
         this(configurationContext,targetEndpoint,false);
   }


   /**
     * Constructor that takes in a configContext  and useseperate listner
     */
   public InqSBKCustomerStub(org.apache.axis2.context.ConfigurationContext configurationContext,
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
    public InqSBKCustomerStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {
        
                    this(configurationContext,"http://10.146.163.71:5536/Services/SmartKioskServices/SmartKioskInquiryServices/Service/InqSBKCustomer.serviceagent/InqSBKCustomerPortTypeEndpoint0" );
                
    }

    /**
     * Default Constructor
     */
    public InqSBKCustomerStub() throws org.apache.axis2.AxisFault {
        
                    this("http://10.146.163.71:5536/Services/SmartKioskServices/SmartKioskInquiryServices/Service/InqSBKCustomer.serviceagent/InqSBKCustomerPortTypeEndpoint0" );
                
    }

    /**
     * Constructor taking the target endpoint
     */
    public InqSBKCustomerStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
        this(null,targetEndpoint);
    }

    //Added by Nishant Parmar for getting input XML (23-02-2016):
    public String getInputXml_chqLeaves(
    		com.newgen.dscop.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsReqMsg getChqLeavesIssueAcctsReqMsg) 
    throws java.rmi.RemoteException{
    	org.apache.axis2.context.MessageContext _messageContext = null;
    	try{
    		org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
    		_operationClient.getOptions().setAction("/Services/SmartKioskServices/SmartKioskInquiryServices/Service/InqSBKCustomer.serviceagent/InqSBKCustomerPortTypeEndpoint0/getChqLeavesIssueAccts_Oper");
    		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



    		addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");


    		// create a message context
    		_messageContext = new org.apache.axis2.context.MessageContext();



    		// create SOAP envelope with that payload
    		org.apache.axiom.soap.SOAPEnvelope env = null;


    		env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
    				getChqLeavesIssueAcctsReqMsg,
    				optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1447321201540",
    				"getChqLeavesIssueAccts_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1447321201540",
    				"getChqLeavesIssueAccts_Oper"));

    		//adding SOAP soap_headers
    		_serviceClient.addHeadersToEnvelope(env);
    		// set the message context with that soap envelope
    		_messageContext.setEnvelope(env);

/*    		// add the message context to the operation client
    		_operationClient.addMessageContext(_messageContext);

    		//execute the operation client
    		_operationClient.execute(true);*/
    		
    		return env.toString();
    	}
    	catch(org.apache.axis2.AxisFault f)
    	{
    		return "";

    	} finally {

    	}
    } 

        
                    /**
                     * Auto generated method signature
                     * 
                     * @see com.newgen.dscop.stub.InqSBKCustomer#getChqLeavesIssueAccts_Oper
                     * @param getChqLeavesIssueAcctsReqMsg0
                    
                     */

                    

                            public  com.newgen.dscop.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsResMsg getChqLeavesIssueAccts_Oper(

                            com.newgen.dscop.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsReqMsg getChqLeavesIssueAcctsReqMsg0)
                        

                    throws java.rmi.RemoteException
                    
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
              _operationClient.getOptions().setAction("/Services/SmartKioskServices/SmartKioskInquiryServices/Service/InqSBKCustomer.serviceagent/InqSBKCustomerPortTypeEndpoint0/getChqLeavesIssueAccts_Oper");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getChqLeavesIssueAcctsReqMsg0,
                                                    optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1454403905186",
                                                    "getChqLeavesIssueAccts_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1454403905186",
                                                    "getChqLeavesIssueAccts_Oper"));
                                                
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
                
				//add here output [Nishant Parmar | 23-02-2016]: 
                resChqLeaves = _returnEnv.toString();
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.newgen.dscop.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsResMsg.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.newgen.dscop.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsResMsg)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getChqLeavesIssueAccts_Oper"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getChqLeavesIssueAccts_Oper"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getChqLeavesIssueAccts_Oper"));
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
                * @see com.newgen.dscop.stub.InqSBKCustomer#startgetChqLeavesIssueAccts_Oper
                    * @param getChqLeavesIssueAcctsReqMsg0
                
                */
                public  void startgetChqLeavesIssueAccts_Oper(

                 com.newgen.dscop.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsReqMsg getChqLeavesIssueAcctsReqMsg0,

                  final com.newgen.dscop.stub.InqSBKCustomerCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
             _operationClient.getOptions().setAction("/Services/SmartKioskServices/SmartKioskInquiryServices/Service/InqSBKCustomer.serviceagent/InqSBKCustomerPortTypeEndpoint0/getChqLeavesIssueAccts_Oper");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getChqLeavesIssueAcctsReqMsg0,
                                                    optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1454403905186",
                                                    "getChqLeavesIssueAccts_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1454403905186",
                                                    "getChqLeavesIssueAccts_Oper"));
                                                
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
                                                                         com.newgen.dscop.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsResMsg.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetChqLeavesIssueAccts_Oper(
                                        (com.newgen.dscop.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsResMsg)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetChqLeavesIssueAccts_Oper(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getChqLeavesIssueAccts_Oper"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getChqLeavesIssueAccts_Oper"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getChqLeavesIssueAccts_Oper"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
					
										            callback.receiveErrorgetChqLeavesIssueAccts_Oper(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetChqLeavesIssueAccts_Oper(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetChqLeavesIssueAccts_Oper(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetChqLeavesIssueAccts_Oper(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetChqLeavesIssueAccts_Oper(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetChqLeavesIssueAccts_Oper(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetChqLeavesIssueAccts_Oper(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetChqLeavesIssueAccts_Oper(f);
                                            }
									    } else {
										    callback.receiveErrorgetChqLeavesIssueAccts_Oper(f);
									    }
									} else {
									    callback.receiveErrorgetChqLeavesIssueAccts_Oper(f);
									}
								} else {
								    callback.receiveErrorgetChqLeavesIssueAccts_Oper(error);
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
                                    callback.receiveErrorgetChqLeavesIssueAccts_Oper(axisFault);
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
                
                
                //Added by Nishant Parmar for getting input XML (24-02-2016):
                public String getInputXml_KioskEIDA(
                		com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoReqMsg getKioskCustEIDAInfoReqMsg) 
                throws java.rmi.RemoteException{
                	org.apache.axis2.context.MessageContext _messageContext = null;
                	try{
                		org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
                		_operationClient.getOptions().setAction("/Services/SmartKioskServices/SmartKioskInquiryServices/Service/InqSBKCustomer.serviceagent/InqSBKCustomerPortTypeEndpoint0/getKioskCustEIDAInfo_Oper");
                		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



                		addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");


                		// create a message context
                		_messageContext = new org.apache.axis2.context.MessageContext();



                		// create SOAP envelope with that payload
                		org.apache.axiom.soap.SOAPEnvelope env = null;


                		env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                				getKioskCustEIDAInfoReqMsg,
                				optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1447321201540",
                				"getKioskCustEIDAInfo_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1447321201540",
                				"getKioskCustEIDAInfo_Oper"));

                		//adding SOAP soap_headers
                		_serviceClient.addHeadersToEnvelope(env);
                		// set the message context with that soap envelope
                		_messageContext.setEnvelope(env);

/*                		// add the message context to the operation client
                		_operationClient.addMessageContext(_messageContext);

                		//execute the operation client
                		_operationClient.execute(true);*/
                		
                		return env.toString();
                	}
                	catch(org.apache.axis2.AxisFault f)
                	{
                		return "";

                	} finally {

                	}
                } 
                
                    /**
                     * Auto generated method signature
                     * 
                     * @see com.newgen.dscop.stub.InqSBKCustomer#getKioskCustEIDAInfo_Oper
                     * @param getKioskCustEIDAInfoReqMsg2
                    
                     */

                    

                            public  com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoResMsg getKioskCustEIDAInfo_Oper(

                            com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoReqMsg getKioskCustEIDAInfoReqMsg2)
                        

                    throws java.rmi.RemoteException
                    
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
              _operationClient.getOptions().setAction("/Services/SmartKioskServices/SmartKioskInquiryServices/Service/InqSBKCustomer.serviceagent/InqSBKCustomerPortTypeEndpoint0/getKioskCustEIDAInfo_Oper");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getKioskCustEIDAInfoReqMsg2,
                                                    optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1454403905186",
                                                    "getKioskCustEIDAInfo_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1454403905186",
                                                    "getKioskCustEIDAInfo_Oper"));
                                                
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
                
				//add here output [Nishant Parmar | 24-02-2016]: 
                resKioskEIDA = _returnEnv.toString();
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoResMsg.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoResMsg)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getKioskCustEIDAInfo_Oper"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getKioskCustEIDAInfo_Oper"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getKioskCustEIDAInfo_Oper"));
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
                * @see com.newgen.dscop.stub.InqSBKCustomer#startgetKioskCustEIDAInfo_Oper
                    * @param getKioskCustEIDAInfoReqMsg2
                
                */
                public  void startgetKioskCustEIDAInfo_Oper(

                 com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoReqMsg getKioskCustEIDAInfoReqMsg2,

                  final com.newgen.dscop.stub.InqSBKCustomerCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
             _operationClient.getOptions().setAction("/Services/SmartKioskServices/SmartKioskInquiryServices/Service/InqSBKCustomer.serviceagent/InqSBKCustomerPortTypeEndpoint0/getKioskCustEIDAInfo_Oper");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getKioskCustEIDAInfoReqMsg2,
                                                    optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1454403905186",
                                                    "getKioskCustEIDAInfo_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1454403905186",
                                                    "getKioskCustEIDAInfo_Oper"));
                                                
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
                                                                         com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoResMsg.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetKioskCustEIDAInfo_Oper(
                                        (com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoResMsg)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetKioskCustEIDAInfo_Oper(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getKioskCustEIDAInfo_Oper"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getKioskCustEIDAInfo_Oper"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getKioskCustEIDAInfo_Oper"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
					
										            callback.receiveErrorgetKioskCustEIDAInfo_Oper(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetKioskCustEIDAInfo_Oper(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetKioskCustEIDAInfo_Oper(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetKioskCustEIDAInfo_Oper(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetKioskCustEIDAInfo_Oper(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetKioskCustEIDAInfo_Oper(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetKioskCustEIDAInfo_Oper(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetKioskCustEIDAInfo_Oper(f);
                                            }
									    } else {
										    callback.receiveErrorgetKioskCustEIDAInfo_Oper(f);
									    }
									} else {
									    callback.receiveErrorgetKioskCustEIDAInfo_Oper(f);
									}
								} else {
								    callback.receiveErrorgetKioskCustEIDAInfo_Oper(error);
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
                                    callback.receiveErrorgetKioskCustEIDAInfo_Oper(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[1].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[1].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }

                //Added by Nishant Parmar for getting input XML (24-02-2016):
                public String getInputXml_CustInfo(
                		com.newgen.dscop.stub.InqSBKCustomerStub.GetCustomerInfoReqMsg getCustomerInfoReqMsg) 
                throws java.rmi.RemoteException{
                	org.apache.axis2.context.MessageContext _messageContext = null;
                	try{
                		org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
                		_operationClient.getOptions().setAction("/Services/SmartKioskServices/SmartKioskInquiryServices/Service/InqSBKCustomer.serviceagent/InqSBKCustomerPortTypeEndpoint0/getCustomerInfo_Oper");
                		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



                		addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");


                		// create a message context
                		_messageContext = new org.apache.axis2.context.MessageContext();



                		// create SOAP envelope with that payload
                		org.apache.axiom.soap.SOAPEnvelope env = null;


                		env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                				getCustomerInfoReqMsg,
                				optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1447321201540",
                				"getCustomerInfo_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1447321201540",
                				"getCustomerInfo_Oper"));

                		//adding SOAP soap_headers
                		_serviceClient.addHeadersToEnvelope(env);
                		// set the message context with that soap envelope
                		_messageContext.setEnvelope(env);

/*                		// add the message context to the operation client
                		_operationClient.addMessageContext(_messageContext);

                		//execute the operation client
                		_operationClient.execute(true);*/
                		
                		return env.toString();
                	}
                	catch(org.apache.axis2.AxisFault f)
                	{
                		return "";

                	} finally {

                	}
                }                 
                
                    /**
                     * Auto generated method signature
                     * 
                     * @see com.newgen.dscop.stub.InqSBKCustomer#getCustomerInfo_Oper
                     * @param getCustomerInfoReqMsg4
                    
                     */

                    

                            public  com.newgen.dscop.stub.InqSBKCustomerStub.GetCustomerInfoResMsg getCustomerInfo_Oper(

                            com.newgen.dscop.stub.InqSBKCustomerStub.GetCustomerInfoReqMsg getCustomerInfoReqMsg4)
                        

                    throws java.rmi.RemoteException
                    
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[2].getName());
              _operationClient.getOptions().setAction("/Services/SmartKioskServices/SmartKioskInquiryServices/Service/InqSBKCustomer.serviceagent/InqSBKCustomerPortTypeEndpoint0/getCustomerInfo_Oper");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getCustomerInfoReqMsg4,
                                                    optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1454403905186",
                                                    "getCustomerInfo_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1454403905186",
                                                    "getCustomerInfo_Oper"));
                                                
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
                
				//add here output [Nishant Parmar | 24-02-2016]: 
                resCustInfo = _returnEnv.toString();
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.newgen.dscop.stub.InqSBKCustomerStub.GetCustomerInfoResMsg.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.newgen.dscop.stub.InqSBKCustomerStub.GetCustomerInfoResMsg)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getCustomerInfo_Oper"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getCustomerInfo_Oper"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getCustomerInfo_Oper"));
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
                * @see com.newgen.dscop.stub.InqSBKCustomer#startgetCustomerInfo_Oper
                    * @param getCustomerInfoReqMsg4
                
                */
                public  void startgetCustomerInfo_Oper(

                 com.newgen.dscop.stub.InqSBKCustomerStub.GetCustomerInfoReqMsg getCustomerInfoReqMsg4,

                  final com.newgen.dscop.stub.InqSBKCustomerCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[2].getName());
             _operationClient.getOptions().setAction("/Services/SmartKioskServices/SmartKioskInquiryServices/Service/InqSBKCustomer.serviceagent/InqSBKCustomerPortTypeEndpoint0/getCustomerInfo_Oper");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getCustomerInfoReqMsg4,
                                                    optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1454403905186",
                                                    "getCustomerInfo_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1454403905186",
                                                    "getCustomerInfo_Oper"));
                                                
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
                                                                         com.newgen.dscop.stub.InqSBKCustomerStub.GetCustomerInfoResMsg.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetCustomerInfo_Oper(
                                        (com.newgen.dscop.stub.InqSBKCustomerStub.GetCustomerInfoResMsg)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetCustomerInfo_Oper(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getCustomerInfo_Oper"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getCustomerInfo_Oper"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getCustomerInfo_Oper"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
					
										            callback.receiveErrorgetCustomerInfo_Oper(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetCustomerInfo_Oper(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetCustomerInfo_Oper(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetCustomerInfo_Oper(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetCustomerInfo_Oper(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetCustomerInfo_Oper(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetCustomerInfo_Oper(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetCustomerInfo_Oper(f);
                                            }
									    } else {
										    callback.receiveErrorgetCustomerInfo_Oper(f);
									    }
									} else {
									    callback.receiveErrorgetCustomerInfo_Oper(f);
									}
								} else {
								    callback.receiveErrorgetCustomerInfo_Oper(error);
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
                                    callback.receiveErrorgetCustomerInfo_Oper(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[2].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[2].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                
                    /**
                     * Auto generated method signature
                     * 
                     * @see com.newgen.dscop.stub.InqSBKCustomer#getKioskCustActivitiesDtl_Oper
                     * @param getKioskCustActivitiesDtlReqMsg6
                    
                     */

                //Added by Nishant Parmar for getting input XML (05-04-2016):
                public String getInputXml_KioskActs(
                		com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlReqMsg getKioskCustActivitiesDtlReqMsg) 
                throws java.rmi.RemoteException{
                	org.apache.axis2.context.MessageContext _messageContext = null;
                	try{
                		org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
                		_operationClient.getOptions().setAction("/Services/SmartKioskServices/SmartKioskInquiryServices/Service/InqSBKCustomer.serviceagent//getKioskCustActivitiesDtl_Oper");
                		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



                		addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");


                		// create a message context
                		_messageContext = new org.apache.axis2.context.MessageContext();



                		// create SOAP envelope with that payload
                		org.apache.axiom.soap.SOAPEnvelope env = null;


                		env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                				getKioskCustActivitiesDtlReqMsg,
                				optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1454403905186",
                				"getKioskCustActivitiesDtl_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1454403905186",
                				"getKioskCustActivitiesDtl_Oper"));

                		//adding SOAP soap_headers
                		_serviceClient.addHeadersToEnvelope(env);
                		// set the message context with that soap envelope
                		_messageContext.setEnvelope(env);

                		/*// add the message context to the operation client
                		_operationClient.addMessageContext(_messageContext);

                		//execute the operation client
                		_operationClient.execute(true);*/
                		
                		return env.toString();
                	}
                	catch(org.apache.axis2.AxisFault f)
                	{
                		return "";

                	} finally {

                	}
                }

                            public  com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlResMsg getKioskCustActivitiesDtl_Oper(

                            com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlReqMsg getKioskCustActivitiesDtlReqMsg6)
                        

                    throws java.rmi.RemoteException
                    
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[3].getName());
              _operationClient.getOptions().setAction("/Services/SmartKioskServices/SmartKioskInquiryServices/Service/InqSBKCustomer.serviceagent//getKioskCustActivitiesDtl_Oper");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getKioskCustActivitiesDtlReqMsg6,
                                                    optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1454403905186",
                                                    "getKioskCustActivitiesDtl_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1454403905186",
                                                    "getKioskCustActivitiesDtl_Oper"));
                                                
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
				//add here output [Nishant Parmar | 05-04-2016]: 
                resKioskDtls = _returnEnv.toString();                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlResMsg.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlResMsg)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getKioskCustActivitiesDtl_Oper"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getKioskCustActivitiesDtl_Oper"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getKioskCustActivitiesDtl_Oper"));
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
                * @see com.newgen.dscop.stub.InqSBKCustomer#startgetKioskCustActivitiesDtl_Oper
                    * @param getKioskCustActivitiesDtlReqMsg6
                
                */
                public  void startgetKioskCustActivitiesDtl_Oper(

                 com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlReqMsg getKioskCustActivitiesDtlReqMsg6,

                  final com.newgen.dscop.stub.InqSBKCustomerCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[3].getName());
             _operationClient.getOptions().setAction("/Services/SmartKioskServices/SmartKioskInquiryServices/Service/InqSBKCustomer.serviceagent//getKioskCustActivitiesDtl_Oper");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getKioskCustActivitiesDtlReqMsg6,
                                                    optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1454403905186",
                                                    "getKioskCustActivitiesDtl_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1454403905186",
                                                    "getKioskCustActivitiesDtl_Oper"));
                                                
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
                                                                         com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlResMsg.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultgetKioskCustActivitiesDtl_Oper(
                                        (com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlResMsg)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorgetKioskCustActivitiesDtl_Oper(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getKioskCustActivitiesDtl_Oper"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getKioskCustActivitiesDtl_Oper"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getKioskCustActivitiesDtl_Oper"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
					
										            callback.receiveErrorgetKioskCustActivitiesDtl_Oper(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetKioskCustActivitiesDtl_Oper(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetKioskCustActivitiesDtl_Oper(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetKioskCustActivitiesDtl_Oper(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetKioskCustActivitiesDtl_Oper(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetKioskCustActivitiesDtl_Oper(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetKioskCustActivitiesDtl_Oper(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorgetKioskCustActivitiesDtl_Oper(f);
                                            }
									    } else {
										    callback.receiveErrorgetKioskCustActivitiesDtl_Oper(f);
									    }
									} else {
									    callback.receiveErrorgetKioskCustActivitiesDtl_Oper(f);
									}
								} else {
								    callback.receiveErrorgetKioskCustActivitiesDtl_Oper(error);
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
                                    callback.receiveErrorgetKioskCustActivitiesDtl_Oper(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[3].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[3].setMessageReceiver(
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
     //http://10.146.163.71:5536/Services/SmartKioskServices/SmartKioskInquiryServices/Service/InqSBKCustomer.serviceagent/InqSBKCustomerPortTypeEndpoint0
        public static class GetCustomerInfoReqMsg
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                "getCustomerInfoReqMsg",
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
                        * field for GetCustomerInfoReq
                        */

                        
                                    protected GetCustomerInfoReq_type0 localGetCustomerInfoReq ;
                                

                           /**
                           * Auto generated getter method
                           * @return GetCustomerInfoReq_type0
                           */
                           public  GetCustomerInfoReq_type0 getGetCustomerInfoReq(){
                               return localGetCustomerInfoReq;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param GetCustomerInfoReq
                               */
                               public void setGetCustomerInfoReq(GetCustomerInfoReq_type0 param){
                            
                                            this.localGetCustomerInfoReq=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":getCustomerInfoReqMsg",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "getCustomerInfoReqMsg",
                           xmlWriter);
                   }

               
                   }
               
                                            if (localHeader==null){
                                                 throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
                                            }
                                           localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
                                               xmlWriter);
                                        
                                            if (localGetCustomerInfoReq==null){
                                                 throw new org.apache.axis2.databinding.ADBException("getCustomerInfoReq cannot be null!!");
                                            }
                                           localGetCustomerInfoReq.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","getCustomerInfoReq"),
                                               xmlWriter);
                                        
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd")){
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

                
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd",
                                                                      "header"));
                            
                            
                                    if (localHeader==null){
                                         throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
                                    }
                                    elementList.add(localHeader);
                                
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "getCustomerInfoReq"));
                            
                            
                                    if (localGetCustomerInfoReq==null){
                                         throw new org.apache.axis2.databinding.ADBException("getCustomerInfoReq cannot be null!!");
                                    }
                                    elementList.add(localGetCustomerInfoReq);
                                

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
        public static GetCustomerInfoReqMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            GetCustomerInfoReqMsg object =
                new GetCustomerInfoReqMsg();

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
                    
                            if (!"getCustomerInfoReqMsg".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (GetCustomerInfoReqMsg)ExtensionMapper.getTypeObject(
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","getCustomerInfoReq").equals(reader.getName())){
                                
                                                object.setGetCustomerInfoReq(GetCustomerInfoReq_type0.Factory.parse(reader));
                                              
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
           
    
        public static class GetChqLeavesIssueAcctsReq_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = getChqLeavesIssueAcctsReq_type0
                Namespace URI = http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd
                Namespace Prefix = ns2
                */
            

                        /**
                        * field for CustomerId
                        */

                        
                                    protected java.lang.String localCustomerId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerIdTracker = false ;

                           public boolean isCustomerIdSpecified(){
                               return localCustomerIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerId(){
                               return localCustomerId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerId
                               */
                               public void setCustomerId(java.lang.String param){
                            localCustomerIdTracker = param != null;
                                   
                                            this.localCustomerId=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":getChqLeavesIssueAcctsReq_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "getChqLeavesIssueAcctsReq_type0",
                           xmlWriter);
                   }

               
                   }
                if (localCustomerIdTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "customerId", xmlWriter);
                             

                                          if (localCustomerId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd")){
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

                 if (localCustomerIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "customerId"));
                                 
                                        if (localCustomerId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");
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
        public static GetChqLeavesIssueAcctsReq_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            GetChqLeavesIssueAcctsReq_type0 object =
                new GetChqLeavesIssueAcctsReq_type0();

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
                    
                            if (!"getChqLeavesIssueAcctsReq_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (GetChqLeavesIssueAcctsReq_type0)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","customerId").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerId(
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
           
    
        public static class HeaderType
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = headerType
                Namespace URI = http://www.adcb.com/esb/common/header.xsd
                Namespace Prefix = ns1
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
                return "ns1";
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
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUsecaseID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","serviceName").equals(reader.getName())){
                                
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
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setServiceAction(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","correlationID").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCorrelationID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","sysRefNumber").equals(reader.getName())){
                                
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
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setConsumer(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","reqTimeStamp").equals(reader.getName())){
                                
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
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRepTimeStamp(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","username").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUsername(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","credentials").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCredentials(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","returnCode").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setReturnCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","errorDescription").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setErrorDescription(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","errorDetail").equals(reader.getName())){
                                
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
           
    
        public static class Activities_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = activities_type0
                Namespace URI = http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd
                Namespace Prefix = ns2
                */
            

                        /**
                        * field for Activity
                        */

                        
                                    protected java.lang.String localActivity ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localActivityTracker = false ;

                           public boolean isActivitySpecified(){
                               return localActivityTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getActivity(){
                               return localActivity;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Activity
                               */
                               public void setActivity(java.lang.String param){
                            localActivityTracker = param != null;
                                   
                                            this.localActivity=param;
                                    

                               }
                            

                        /**
                        * field for AuthFlag
                        */

                        
                                    protected java.lang.String localAuthFlag ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAuthFlagTracker = false ;

                           public boolean isAuthFlagSpecified(){
                               return localAuthFlagTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAuthFlag(){
                               return localAuthFlag;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AuthFlag
                               */
                               public void setAuthFlag(java.lang.String param){
                            localAuthFlagTracker = param != null;
                                   
                                            this.localAuthFlag=param;
                                    

                               }
                            

                        /**
                        * field for Authentication
                        */

                        
                                    protected java.lang.String localAuthentication ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAuthenticationTracker = false ;

                           public boolean isAuthenticationSpecified(){
                               return localAuthenticationTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAuthentication(){
                               return localAuthentication;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Authentication
                               */
                               public void setAuthentication(java.lang.String param){
                            localAuthenticationTracker = param != null;
                                   
                                            this.localAuthentication=param;
                                    

                               }
                            

                        /**
                        * field for Cline
                        */

                        
                                    protected java.lang.String localCline ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localClineTracker = false ;

                           public boolean isClineSpecified(){
                               return localClineTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCline(){
                               return localCline;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Cline
                               */
                               public void setCline(java.lang.String param){
                            localClineTracker = param != null;
                                   
                                            this.localCline=param;
                                    

                               }
                            

                        /**
                        * field for CallTransferTo
                        */

                        
                                    protected java.lang.String localCallTransferTo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCallTransferToTracker = false ;

                           public boolean isCallTransferToSpecified(){
                               return localCallTransferToTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCallTransferTo(){
                               return localCallTransferTo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CallTransferTo
                               */
                               public void setCallTransferTo(java.lang.String param){
                            localCallTransferToTracker = param != null;
                                   
                                            this.localCallTransferTo=param;
                                    

                               }
                            

                        /**
                        * field for CreatedBy
                        */

                        
                                    protected java.lang.String localCreatedBy ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCreatedByTracker = false ;

                           public boolean isCreatedBySpecified(){
                               return localCreatedByTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCreatedBy(){
                               return localCreatedBy;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CreatedBy
                               */
                               public void setCreatedBy(java.lang.String param){
                            localCreatedByTracker = param != null;
                                   
                                            this.localCreatedBy=param;
                                    

                               }
                            

                        /**
                        * field for CustomerCategory
                        */

                        
                                    protected java.lang.String localCustomerCategory ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerCategoryTracker = false ;

                           public boolean isCustomerCategorySpecified(){
                               return localCustomerCategoryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerCategory(){
                               return localCustomerCategory;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerCategory
                               */
                               public void setCustomerCategory(java.lang.String param){
                            localCustomerCategoryTracker = param != null;
                                   
                                            this.localCustomerCategory=param;
                                    

                               }
                            

                        /**
                        * field for CustType
                        */

                        
                                    protected java.lang.String localCustType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustTypeTracker = false ;

                           public boolean isCustTypeSpecified(){
                               return localCustTypeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustType(){
                               return localCustType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustType
                               */
                               public void setCustType(java.lang.String param){
                            localCustTypeTracker = param != null;
                                   
                                            this.localCustType=param;
                                    

                               }
                            

                        /**
                        * field for Description
                        */

                        
                                    protected java.lang.String localDescription ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDescriptionTracker = false ;

                           public boolean isDescriptionSpecified(){
                               return localDescriptionTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDescription(){
                               return localDescription;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Description
                               */
                               public void setDescription(java.lang.String param){
                            localDescriptionTracker = param != null;
                                   
                                            this.localDescription=param;
                                    

                               }
                            

                        /**
                        * field for DriverID
                        */

                        
                                    protected java.lang.String localDriverID ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDriverIDTracker = false ;

                           public boolean isDriverIDSpecified(){
                               return localDriverIDTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDriverID(){
                               return localDriverID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DriverID
                               */
                               public void setDriverID(java.lang.String param){
                            localDriverIDTracker = param != null;
                                   
                                            this.localDriverID=param;
                                    

                               }
                            

                        /**
                        * field for StartDateTime
                        */

                        
                                    protected java.lang.String localStartDateTime ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localStartDateTimeTracker = false ;

                           public boolean isStartDateTimeSpecified(){
                               return localStartDateTimeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getStartDateTime(){
                               return localStartDateTime;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param StartDateTime
                               */
                               public void setStartDateTime(java.lang.String param){
                            localStartDateTimeTracker = param != null;
                                   
                                            this.localStartDateTime=param;
                                    

                               }
                            

                        /**
                        * field for EndDateTime
                        */

                        
                                    protected java.lang.String localEndDateTime ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEndDateTimeTracker = false ;

                           public boolean isEndDateTimeSpecified(){
                               return localEndDateTimeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEndDateTime(){
                               return localEndDateTime;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EndDateTime
                               */
                               public void setEndDateTime(java.lang.String param){
                            localEndDateTimeTracker = param != null;
                                   
                                            this.localEndDateTime=param;
                                    

                               }
                            

                        /**
                        * field for IvrRequest
                        */

                        
                                    protected java.lang.String localIvrRequest ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localIvrRequestTracker = false ;

                           public boolean isIvrRequestSpecified(){
                               return localIvrRequestTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getIvrRequest(){
                               return localIvrRequest;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IvrRequest
                               */
                               public void setIvrRequest(java.lang.String param){
                            localIvrRequestTracker = param != null;
                                   
                                            this.localIvrRequest=param;
                                    

                               }
                            

                        /**
                        * field for Owner
                        */

                        
                                    protected java.lang.String localOwner ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOwnerTracker = false ;

                           public boolean isOwnerSpecified(){
                               return localOwnerTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOwner(){
                               return localOwner;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Owner
                               */
                               public void setOwner(java.lang.String param){
                            localOwnerTracker = param != null;
                                   
                                            this.localOwner=param;
                                    

                               }
                            

                        /**
                        * field for Status
                        */

                        
                                    protected java.lang.String localStatus ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localStatusTracker = false ;

                           public boolean isStatusSpecified(){
                               return localStatusTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getStatus(){
                               return localStatus;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Status
                               */
                               public void setStatus(java.lang.String param){
                            localStatusTracker = param != null;
                                   
                                            this.localStatus=param;
                                    

                               }
                            

                        /**
                        * field for Type
                        */

                        
                                    protected java.lang.String localType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTypeTracker = false ;

                           public boolean isTypeSpecified(){
                               return localTypeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getType(){
                               return localType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Type
                               */
                               public void setType(java.lang.String param){
                            localTypeTracker = param != null;
                                   
                                            this.localType=param;
                                    

                               }
                            

                        /**
                        * field for WrapUpFlag
                        */

                        
                                    protected java.lang.String localWrapUpFlag ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWrapUpFlagTracker = false ;

                           public boolean isWrapUpFlagSpecified(){
                               return localWrapUpFlagTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getWrapUpFlag(){
                               return localWrapUpFlag;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WrapUpFlag
                               */
                               public void setWrapUpFlag(java.lang.String param){
                            localWrapUpFlagTracker = param != null;
                                   
                                            this.localWrapUpFlag=param;
                                    

                               }
                            

                        /**
                        * field for WrapUpIncomplete
                        */

                        
                                    protected java.lang.String localWrapUpIncomplete ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWrapUpIncompleteTracker = false ;

                           public boolean isWrapUpIncompleteSpecified(){
                               return localWrapUpIncompleteTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getWrapUpIncomplete(){
                               return localWrapUpIncomplete;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WrapUpIncomplete
                               */
                               public void setWrapUpIncomplete(java.lang.String param){
                            localWrapUpIncompleteTracker = param != null;
                                   
                                            this.localWrapUpIncomplete=param;
                                    

                               }
                            

                        /**
                        * field for WrapUpCategory
                        */

                        
                                    protected java.lang.String localWrapUpCategory ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWrapUpCategoryTracker = false ;

                           public boolean isWrapUpCategorySpecified(){
                               return localWrapUpCategoryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getWrapUpCategory(){
                               return localWrapUpCategory;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WrapUpCategory
                               */
                               public void setWrapUpCategory(java.lang.String param){
                            localWrapUpCategoryTracker = param != null;
                                   
                                            this.localWrapUpCategory=param;
                                    

                               }
                            

                        /**
                        * field for WrapUpSubCategory
                        */

                        
                                    protected java.lang.String localWrapUpSubCategory ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localWrapUpSubCategoryTracker = false ;

                           public boolean isWrapUpSubCategorySpecified(){
                               return localWrapUpSubCategoryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getWrapUpSubCategory(){
                               return localWrapUpSubCategory;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param WrapUpSubCategory
                               */
                               public void setWrapUpSubCategory(java.lang.String param){
                            localWrapUpSubCategoryTracker = param != null;
                                   
                                            this.localWrapUpSubCategory=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":activities_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "activities_type0",
                           xmlWriter);
                   }

               
                   }
                if (localActivityTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "activity", xmlWriter);
                             

                                          if (localActivity==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("activity cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localActivity);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAuthFlagTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "authFlag", xmlWriter);
                             

                                          if (localAuthFlag==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("authFlag cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAuthFlag);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAuthenticationTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "authentication", xmlWriter);
                             

                                          if (localAuthentication==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("authentication cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAuthentication);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localClineTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "cline", xmlWriter);
                             

                                          if (localCline==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("cline cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCline);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCallTransferToTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "callTransferTo", xmlWriter);
                             

                                          if (localCallTransferTo==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("callTransferTo cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCallTransferTo);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCreatedByTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "createdBy", xmlWriter);
                             

                                          if (localCreatedBy==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("createdBy cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCreatedBy);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCustomerCategoryTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "customerCategory", xmlWriter);
                             

                                          if (localCustomerCategory==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerCategory cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerCategory);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCustTypeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "custType", xmlWriter);
                             

                                          if (localCustType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("custType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDescriptionTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "description", xmlWriter);
                             

                                          if (localDescription==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("description cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDescription);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDriverIDTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "driverID", xmlWriter);
                             

                                          if (localDriverID==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("driverID cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDriverID);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localStartDateTimeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "startDateTime", xmlWriter);
                             

                                          if (localStartDateTime==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("startDateTime cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localStartDateTime);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localEndDateTimeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "endDateTime", xmlWriter);
                             

                                          if (localEndDateTime==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("endDateTime cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEndDateTime);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localIvrRequestTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "ivrRequest", xmlWriter);
                             

                                          if (localIvrRequest==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("ivrRequest cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localIvrRequest);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOwnerTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "owner", xmlWriter);
                             

                                          if (localOwner==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("owner cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOwner);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localStatusTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "status", xmlWriter);
                             

                                          if (localStatus==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("status cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localStatus);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTypeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "type", xmlWriter);
                             

                                          if (localType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("type cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWrapUpFlagTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "wrapUpFlag", xmlWriter);
                             

                                          if (localWrapUpFlag==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("wrapUpFlag cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localWrapUpFlag);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWrapUpIncompleteTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "wrapUpIncomplete", xmlWriter);
                             

                                          if (localWrapUpIncomplete==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("wrapUpIncomplete cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localWrapUpIncomplete);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWrapUpCategoryTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "wrapUpCategory", xmlWriter);
                             

                                          if (localWrapUpCategory==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("wrapUpCategory cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localWrapUpCategory);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localWrapUpSubCategoryTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "wrapUpSubCategory", xmlWriter);
                             

                                          if (localWrapUpSubCategory==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("wrapUpSubCategory cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localWrapUpSubCategory);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd")){
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

                 if (localActivityTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "activity"));
                                 
                                        if (localActivity != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localActivity));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("activity cannot be null!!");
                                        }
                                    } if (localAuthFlagTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "authFlag"));
                                 
                                        if (localAuthFlag != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuthFlag));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("authFlag cannot be null!!");
                                        }
                                    } if (localAuthenticationTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "authentication"));
                                 
                                        if (localAuthentication != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuthentication));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("authentication cannot be null!!");
                                        }
                                    } if (localClineTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "cline"));
                                 
                                        if (localCline != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCline));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("cline cannot be null!!");
                                        }
                                    } if (localCallTransferToTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "callTransferTo"));
                                 
                                        if (localCallTransferTo != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCallTransferTo));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("callTransferTo cannot be null!!");
                                        }
                                    } if (localCreatedByTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "createdBy"));
                                 
                                        if (localCreatedBy != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCreatedBy));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("createdBy cannot be null!!");
                                        }
                                    } if (localCustomerCategoryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "customerCategory"));
                                 
                                        if (localCustomerCategory != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerCategory));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerCategory cannot be null!!");
                                        }
                                    } if (localCustTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "custType"));
                                 
                                        if (localCustType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("custType cannot be null!!");
                                        }
                                    } if (localDescriptionTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "description"));
                                 
                                        if (localDescription != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDescription));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("description cannot be null!!");
                                        }
                                    } if (localDriverIDTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "driverID"));
                                 
                                        if (localDriverID != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDriverID));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("driverID cannot be null!!");
                                        }
                                    } if (localStartDateTimeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "startDateTime"));
                                 
                                        if (localStartDateTime != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStartDateTime));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("startDateTime cannot be null!!");
                                        }
                                    } if (localEndDateTimeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "endDateTime"));
                                 
                                        if (localEndDateTime != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEndDateTime));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("endDateTime cannot be null!!");
                                        }
                                    } if (localIvrRequestTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "ivrRequest"));
                                 
                                        if (localIvrRequest != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIvrRequest));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("ivrRequest cannot be null!!");
                                        }
                                    } if (localOwnerTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "owner"));
                                 
                                        if (localOwner != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOwner));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("owner cannot be null!!");
                                        }
                                    } if (localStatusTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "status"));
                                 
                                        if (localStatus != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStatus));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("status cannot be null!!");
                                        }
                                    } if (localTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "type"));
                                 
                                        if (localType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("type cannot be null!!");
                                        }
                                    } if (localWrapUpFlagTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "wrapUpFlag"));
                                 
                                        if (localWrapUpFlag != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWrapUpFlag));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("wrapUpFlag cannot be null!!");
                                        }
                                    } if (localWrapUpIncompleteTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "wrapUpIncomplete"));
                                 
                                        if (localWrapUpIncomplete != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWrapUpIncomplete));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("wrapUpIncomplete cannot be null!!");
                                        }
                                    } if (localWrapUpCategoryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "wrapUpCategory"));
                                 
                                        if (localWrapUpCategory != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWrapUpCategory));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("wrapUpCategory cannot be null!!");
                                        }
                                    } if (localWrapUpSubCategoryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "wrapUpSubCategory"));
                                 
                                        if (localWrapUpSubCategory != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localWrapUpSubCategory));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("wrapUpSubCategory cannot be null!!");
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
        public static Activities_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            Activities_type0 object =
                new Activities_type0();

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
                    
                            if (!"activities_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (Activities_type0)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","activity").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setActivity(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","authFlag").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAuthFlag(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","authentication").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAuthentication(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","cline").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCline(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","callTransferTo").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCallTransferTo(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","createdBy").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCreatedBy(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","customerCategory").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerCategory(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","custType").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","description").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDescription(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","driverID").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDriverID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","startDateTime").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setStartDateTime(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","endDateTime").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEndDateTime(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","ivrRequest").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setIvrRequest(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","owner").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOwner(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","status").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setStatus(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","type").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","wrapUpFlag").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWrapUpFlag(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","wrapUpIncomplete").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWrapUpIncomplete(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","wrapUpCategory").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWrapUpCategory(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","wrapUpSubCategory").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setWrapUpSubCategory(
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
           
    
        public static class GetKioskCustActivitiesDtlResMsg
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                "getKioskCustActivitiesDtlResMsg",
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
                        * field for GetKioskCustActivitiesDtlRes
                        */

                        
                                    protected GetKioskCustActivitiesDtlRes_type0 localGetKioskCustActivitiesDtlRes ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localGetKioskCustActivitiesDtlResTracker = false ;

                           public boolean isGetKioskCustActivitiesDtlResSpecified(){
                               return localGetKioskCustActivitiesDtlResTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return GetKioskCustActivitiesDtlRes_type0
                           */
                           public  GetKioskCustActivitiesDtlRes_type0 getGetKioskCustActivitiesDtlRes(){
                               return localGetKioskCustActivitiesDtlRes;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param GetKioskCustActivitiesDtlRes
                               */
                               public void setGetKioskCustActivitiesDtlRes(GetKioskCustActivitiesDtlRes_type0 param){
                            localGetKioskCustActivitiesDtlResTracker = param != null;
                                   
                                            this.localGetKioskCustActivitiesDtlRes=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":getKioskCustActivitiesDtlResMsg",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "getKioskCustActivitiesDtlResMsg",
                           xmlWriter);
                   }

               
                   }
               
                                            if (localHeader==null){
                                                 throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
                                            }
                                           localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
                                               xmlWriter);
                                         if (localGetKioskCustActivitiesDtlResTracker){
                                            if (localGetKioskCustActivitiesDtlRes==null){
                                                 throw new org.apache.axis2.databinding.ADBException("getKioskCustActivitiesDtlRes cannot be null!!");
                                            }
                                           localGetKioskCustActivitiesDtlRes.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","getKioskCustActivitiesDtlRes"),
                                               xmlWriter);
                                        }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd")){
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

                
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd",
                                                                      "header"));
                            
                            
                                    if (localHeader==null){
                                         throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
                                    }
                                    elementList.add(localHeader);
                                 if (localGetKioskCustActivitiesDtlResTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "getKioskCustActivitiesDtlRes"));
                            
                            
                                    if (localGetKioskCustActivitiesDtlRes==null){
                                         throw new org.apache.axis2.databinding.ADBException("getKioskCustActivitiesDtlRes cannot be null!!");
                                    }
                                    elementList.add(localGetKioskCustActivitiesDtlRes);
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
        public static GetKioskCustActivitiesDtlResMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            GetKioskCustActivitiesDtlResMsg object =
                new GetKioskCustActivitiesDtlResMsg();

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
                    
                            if (!"getKioskCustActivitiesDtlResMsg".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (GetKioskCustActivitiesDtlResMsg)ExtensionMapper.getTypeObject(
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","getKioskCustActivitiesDtlRes").equals(reader.getName())){
                                
                                                object.setGetKioskCustActivitiesDtlRes(GetKioskCustActivitiesDtlRes_type0.Factory.parse(reader));
                                              
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
           
    
        public static class GetKioskCustActivitiesDtlReq_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = getKioskCustActivitiesDtlReq_type0
                Namespace URI = http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd
                Namespace Prefix = ns2
                */
            

                        /**
                        * field for CustomerId
                        */

                        
                                    protected java.lang.String localCustomerId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerIdTracker = false ;

                           public boolean isCustomerIdSpecified(){
                               return localCustomerIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerId(){
                               return localCustomerId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerId
                               */
                               public void setCustomerId(java.lang.String param){
                            localCustomerIdTracker = param != null;
                                   
                                            this.localCustomerId=param;
                                    

                               }
                            

                        /**
                        * field for SearchFromDate
                        */

                        
                                    protected java.lang.String localSearchFromDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSearchFromDateTracker = false ;

                           public boolean isSearchFromDateSpecified(){
                               return localSearchFromDateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSearchFromDate(){
                               return localSearchFromDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SearchFromDate
                               */
                               public void setSearchFromDate(java.lang.String param){
                            localSearchFromDateTracker = param != null;
                                   
                                            this.localSearchFromDate=param;
                                    

                               }
                            

                        /**
                        * field for SearchToDate
                        */

                        
                                    protected java.lang.String localSearchToDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSearchToDateTracker = false ;

                           public boolean isSearchToDateSpecified(){
                               return localSearchToDateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSearchToDate(){
                               return localSearchToDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SearchToDate
                               */
                               public void setSearchToDate(java.lang.String param){
                            localSearchToDateTracker = param != null;
                                   
                                            this.localSearchToDate=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":getKioskCustActivitiesDtlReq_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "getKioskCustActivitiesDtlReq_type0",
                           xmlWriter);
                   }

               
                   }
                if (localCustomerIdTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "customerId", xmlWriter);
                             

                                          if (localCustomerId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSearchFromDateTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "searchFromDate", xmlWriter);
                             

                                          if (localSearchFromDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("searchFromDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSearchFromDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSearchToDateTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "searchToDate", xmlWriter);
                             

                                          if (localSearchToDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("searchToDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSearchToDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd")){
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

                 if (localCustomerIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "customerId"));
                                 
                                        if (localCustomerId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");
                                        }
                                    } if (localSearchFromDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "searchFromDate"));
                                 
                                        if (localSearchFromDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSearchFromDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("searchFromDate cannot be null!!");
                                        }
                                    } if (localSearchToDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "searchToDate"));
                                 
                                        if (localSearchToDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSearchToDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("searchToDate cannot be null!!");
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
        public static GetKioskCustActivitiesDtlReq_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            GetKioskCustActivitiesDtlReq_type0 object =
                new GetKioskCustActivitiesDtlReq_type0();

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
                    
                            if (!"getKioskCustActivitiesDtlReq_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (GetKioskCustActivitiesDtlReq_type0)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","customerId").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","searchFromDate").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSearchFromDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","searchToDate").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSearchToDate(
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
           
    
        public static class Chqueueleaves_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = Chqueueleaves_type0
                Namespace URI = http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd
                Namespace Prefix = ns2
                */
            

                        /**
                        * field for AccountNumber
                        */

                        
                                    protected java.lang.String localAccountNumber ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAccountNumberTracker = false ;

                           public boolean isAccountNumberSpecified(){
                               return localAccountNumberTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAccountNumber(){
                               return localAccountNumber;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AccountNumber
                               */
                               public void setAccountNumber(java.lang.String param){
                            localAccountNumberTracker = param != null;
                                   
                                            this.localAccountNumber=param;
                                    

                               }
                            

                        /**
                        * field for AccountTitle
                        */

                        
                                    protected java.lang.String localAccountTitle ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAccountTitleTracker = false ;

                           public boolean isAccountTitleSpecified(){
                               return localAccountTitleTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAccountTitle(){
                               return localAccountTitle;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AccountTitle
                               */
                               public void setAccountTitle(java.lang.String param){
                            localAccountTitleTracker = param != null;
                                   
                                            this.localAccountTitle=param;
                                    

                               }
                            

                        /**
                        * field for ChequeBookEOBStatus
                        */

                        
                                    protected java.lang.String localChequeBookEOBStatus ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localChequeBookEOBStatusTracker = false ;

                           public boolean isChequeBookEOBStatusSpecified(){
                               return localChequeBookEOBStatusTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getChequeBookEOBStatus(){
                               return localChequeBookEOBStatus;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ChequeBookEOBStatus
                               */
                               public void setChequeBookEOBStatus(java.lang.String param){
                            localChequeBookEOBStatusTracker = param != null;
                                   
                                            this.localChequeBookEOBStatus=param;
                                    

                               }
                            

                        /**
                        * field for AccountStatus
                        */

                        
                                    protected java.lang.String localAccountStatus ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAccountStatusTracker = false ;

                           public boolean isAccountStatusSpecified(){
                               return localAccountStatusTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAccountStatus(){
                               return localAccountStatus;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AccountStatus
                               */
                               public void setAccountStatus(java.lang.String param){
                            localAccountStatusTracker = param != null;
                                   
                                            this.localAccountStatus=param;
                                    

                               }
                            

                        /**
                        * field for AccountProductCode
                        */

                        
                                    protected java.lang.String localAccountProductCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAccountProductCodeTracker = false ;

                           public boolean isAccountProductCodeSpecified(){
                               return localAccountProductCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAccountProductCode(){
                               return localAccountProductCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AccountProductCode
                               */
                               public void setAccountProductCode(java.lang.String param){
                            localAccountProductCodeTracker = param != null;
                                   
                                            this.localAccountProductCode=param;
                                    

                               }
                            

                        /**
                        * field for ProductDescription
                        */

                        
                                    protected java.lang.String localProductDescription ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localProductDescriptionTracker = false ;

                           public boolean isProductDescriptionSpecified(){
                               return localProductDescriptionTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getProductDescription(){
                               return localProductDescription;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ProductDescription
                               */
                               public void setProductDescription(java.lang.String param){
                            localProductDescriptionTracker = param != null;
                                   
                                            this.localProductDescription=param;
                                    

                               }
                            

                        /**
                        * field for BranchCode
                        */

                        
                                    protected java.lang.String localBranchCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localBranchCodeTracker = false ;

                           public boolean isBranchCodeSpecified(){
                               return localBranchCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getBranchCode(){
                               return localBranchCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param BranchCode
                               */
                               public void setBranchCode(java.lang.String param){
                            localBranchCodeTracker = param != null;
                                   
                                            this.localBranchCode=param;
                                    

                               }
                            

                        /**
                        * field for AccountRelationship
                        */

                        
                                    protected java.lang.String localAccountRelationship ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAccountRelationshipTracker = false ;

                           public boolean isAccountRelationshipSpecified(){
                               return localAccountRelationshipTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAccountRelationship(){
                               return localAccountRelationship;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AccountRelationship
                               */
                               public void setAccountRelationship(java.lang.String param){
                            localAccountRelationshipTracker = param != null;
                                   
                                            this.localAccountRelationship=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":Chqueueleaves_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "Chqueueleaves_type0",
                           xmlWriter);
                   }

               
                   }
                if (localAccountNumberTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "accountNumber", xmlWriter);
                             

                                          if (localAccountNumber==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("accountNumber cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAccountNumber);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAccountTitleTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "accountTitle", xmlWriter);
                             

                                          if (localAccountTitle==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("accountTitle cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAccountTitle);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localChequeBookEOBStatusTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "chequeBookEOBStatus", xmlWriter);
                             

                                          if (localChequeBookEOBStatus==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("chequeBookEOBStatus cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localChequeBookEOBStatus);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAccountStatusTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "accountStatus", xmlWriter);
                             

                                          if (localAccountStatus==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("accountStatus cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAccountStatus);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAccountProductCodeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "accountProductCode", xmlWriter);
                             

                                          if (localAccountProductCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("accountProductCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAccountProductCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localProductDescriptionTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "productDescription", xmlWriter);
                             

                                          if (localProductDescription==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("productDescription cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localProductDescription);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localBranchCodeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "branchCode", xmlWriter);
                             

                                          if (localBranchCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("branchCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localBranchCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAccountRelationshipTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "accountRelationship", xmlWriter);
                             

                                          if (localAccountRelationship==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("accountRelationship cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAccountRelationship);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd")){
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

                 if (localAccountNumberTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "accountNumber"));
                                 
                                        if (localAccountNumber != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAccountNumber));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("accountNumber cannot be null!!");
                                        }
                                    } if (localAccountTitleTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "accountTitle"));
                                 
                                        if (localAccountTitle != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAccountTitle));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("accountTitle cannot be null!!");
                                        }
                                    } if (localChequeBookEOBStatusTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "chequeBookEOBStatus"));
                                 
                                        if (localChequeBookEOBStatus != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localChequeBookEOBStatus));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("chequeBookEOBStatus cannot be null!!");
                                        }
                                    } if (localAccountStatusTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "accountStatus"));
                                 
                                        if (localAccountStatus != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAccountStatus));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("accountStatus cannot be null!!");
                                        }
                                    } if (localAccountProductCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "accountProductCode"));
                                 
                                        if (localAccountProductCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAccountProductCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("accountProductCode cannot be null!!");
                                        }
                                    } if (localProductDescriptionTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "productDescription"));
                                 
                                        if (localProductDescription != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProductDescription));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("productDescription cannot be null!!");
                                        }
                                    } if (localBranchCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "branchCode"));
                                 
                                        if (localBranchCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBranchCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("branchCode cannot be null!!");
                                        }
                                    } if (localAccountRelationshipTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "accountRelationship"));
                                 
                                        if (localAccountRelationship != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAccountRelationship));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("accountRelationship cannot be null!!");
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
        public static Chqueueleaves_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            Chqueueleaves_type0 object =
                new Chqueueleaves_type0();

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
                    
                            if (!"Chqueueleaves_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (Chqueueleaves_type0)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","accountNumber").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAccountNumber(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","accountTitle").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAccountTitle(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","chequeBookEOBStatus").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setChequeBookEOBStatus(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","accountStatus").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAccountStatus(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","accountProductCode").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAccountProductCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","productDescription").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setProductDescription(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","branchCode").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setBranchCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","accountRelationship").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAccountRelationship(
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
           
    
        public static class GetChqLeavesIssueAcctsReqMsg
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                "getChqLeavesIssueAcctsReqMsg",
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
                        * field for GetChqLeavesIssueAcctsReq
                        */

                        
                                    protected GetChqLeavesIssueAcctsReq_type0 localGetChqLeavesIssueAcctsReq ;
                                

                           /**
                           * Auto generated getter method
                           * @return GetChqLeavesIssueAcctsReq_type0
                           */
                           public  GetChqLeavesIssueAcctsReq_type0 getGetChqLeavesIssueAcctsReq(){
                               return localGetChqLeavesIssueAcctsReq;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param GetChqLeavesIssueAcctsReq
                               */
                               public void setGetChqLeavesIssueAcctsReq(GetChqLeavesIssueAcctsReq_type0 param){
                            
                                            this.localGetChqLeavesIssueAcctsReq=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":getChqLeavesIssueAcctsReqMsg",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "getChqLeavesIssueAcctsReqMsg",
                           xmlWriter);
                   }

               
                   }
               
                                            if (localHeader==null){
                                                 throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
                                            }
                                           localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
                                               xmlWriter);
                                        
                                            if (localGetChqLeavesIssueAcctsReq==null){
                                                 throw new org.apache.axis2.databinding.ADBException("getChqLeavesIssueAcctsReq cannot be null!!");
                                            }
                                           localGetChqLeavesIssueAcctsReq.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","getChqLeavesIssueAcctsReq"),
                                               xmlWriter);
                                        
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd")){
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

                
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd",
                                                                      "header"));
                            
                            
                                    if (localHeader==null){
                                         throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
                                    }
                                    elementList.add(localHeader);
                                
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "getChqLeavesIssueAcctsReq"));
                            
                            
                                    if (localGetChqLeavesIssueAcctsReq==null){
                                         throw new org.apache.axis2.databinding.ADBException("getChqLeavesIssueAcctsReq cannot be null!!");
                                    }
                                    elementList.add(localGetChqLeavesIssueAcctsReq);
                                

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
        public static GetChqLeavesIssueAcctsReqMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            GetChqLeavesIssueAcctsReqMsg object =
                new GetChqLeavesIssueAcctsReqMsg();

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
                    
                            if (!"getChqLeavesIssueAcctsReqMsg".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (GetChqLeavesIssueAcctsReqMsg)ExtensionMapper.getTypeObject(
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","getChqLeavesIssueAcctsReq").equals(reader.getName())){
                                
                                                object.setGetChqLeavesIssueAcctsReq(GetChqLeavesIssueAcctsReq_type0.Factory.parse(reader));
                                              
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
           
    
        public static class GetCustomerInfoRes_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = getCustomerInfoRes_type0
                Namespace URI = http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd
                Namespace Prefix = ns2
                */
            

                        /**
                        * field for CustomerId
                        */

                        
                                    protected java.lang.String localCustomerId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerIdTracker = false ;

                           public boolean isCustomerIdSpecified(){
                               return localCustomerIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerId(){
                               return localCustomerId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerId
                               */
                               public void setCustomerId(java.lang.String param){
                            localCustomerIdTracker = param != null;
                                   
                                            this.localCustomerId=param;
                                    

                               }
                            

                        /**
                        * field for EidaNo
                        */

                        
                                    protected java.lang.String localEidaNo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEidaNoTracker = false ;

                           public boolean isEidaNoSpecified(){
                               return localEidaNoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEidaNo(){
                               return localEidaNo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EidaNo
                               */
                               public void setEidaNo(java.lang.String param){
                            localEidaNoTracker = param != null;
                                   
                                            this.localEidaNo=param;
                                    

                               }
                            

                        /**
                        * field for CustomerNamePrefix
                        */

                        
                                    protected java.lang.String localCustomerNamePrefix ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerNamePrefixTracker = false ;

                           public boolean isCustomerNamePrefixSpecified(){
                               return localCustomerNamePrefixTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerNamePrefix(){
                               return localCustomerNamePrefix;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerNamePrefix
                               */
                               public void setCustomerNamePrefix(java.lang.String param){
                            localCustomerNamePrefixTracker = param != null;
                                   
                                            this.localCustomerNamePrefix=param;
                                    

                               }
                            

                        /**
                        * field for CustomerFullName
                        */

                        
                                    protected java.lang.String localCustomerFullName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerFullNameTracker = false ;

                           public boolean isCustomerFullNameSpecified(){
                               return localCustomerFullNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerFullName(){
                               return localCustomerFullName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerFullName
                               */
                               public void setCustomerFullName(java.lang.String param){
                            localCustomerFullNameTracker = param != null;
                                   
                                            this.localCustomerFullName=param;
                                    

                               }
                            

                        /**
                        * field for CustomerShortName
                        */

                        
                                    protected java.lang.String localCustomerShortName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerShortNameTracker = false ;

                           public boolean isCustomerShortNameSpecified(){
                               return localCustomerShortNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerShortName(){
                               return localCustomerShortName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerShortName
                               */
                               public void setCustomerShortName(java.lang.String param){
                            localCustomerShortNameTracker = param != null;
                                   
                                            this.localCustomerShortName=param;
                                    

                               }
                            

                        /**
                        * field for CustomerSegment
                        */

                        
                                    protected java.lang.String localCustomerSegment ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerSegmentTracker = false ;

                           public boolean isCustomerSegmentSpecified(){
                               return localCustomerSegmentTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerSegment(){
                               return localCustomerSegment;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerSegment
                               */
                               public void setCustomerSegment(java.lang.String param){
                            localCustomerSegmentTracker = param != null;
                                   
                                            this.localCustomerSegment=param;
                                    

                               }
                            

                        /**
                        * field for CustomerSegmentDesc
                        */

                        
                                    protected java.lang.String localCustomerSegmentDesc ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerSegmentDescTracker = false ;

                           public boolean isCustomerSegmentDescSpecified(){
                               return localCustomerSegmentDescTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerSegmentDesc(){
                               return localCustomerSegmentDesc;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerSegmentDesc
                               */
                               public void setCustomerSegmentDesc(java.lang.String param){
                            localCustomerSegmentDescTracker = param != null;
                                   
                                            this.localCustomerSegmentDesc=param;
                                    

                               }
                            

                        /**
                        * field for CustomerType
                        */

                        
                                    protected java.lang.String localCustomerType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerTypeTracker = false ;

                           public boolean isCustomerTypeSpecified(){
                               return localCustomerTypeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerType(){
                               return localCustomerType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerType
                               */
                               public void setCustomerType(java.lang.String param){
                            localCustomerTypeTracker = param != null;
                                   
                                            this.localCustomerType=param;
                                    

                               }
                            

                        /**
                        * field for CustomerDOB
                        */

                        
                                    protected java.lang.String localCustomerDOB ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerDOBTracker = false ;

                           public boolean isCustomerDOBSpecified(){
                               return localCustomerDOBTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerDOB(){
                               return localCustomerDOB;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerDOB
                               */
                               public void setCustomerDOB(java.lang.String param){
                            localCustomerDOBTracker = param != null;
                                   
                                            this.localCustomerDOB=param;
                                    

                               }
                            

                        /**
                        * field for CustomerPSPTNo
                        */

                        
                                    protected java.lang.String localCustomerPSPTNo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerPSPTNoTracker = false ;

                           public boolean isCustomerPSPTNoSpecified(){
                               return localCustomerPSPTNoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerPSPTNo(){
                               return localCustomerPSPTNo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerPSPTNo
                               */
                               public void setCustomerPSPTNo(java.lang.String param){
                            localCustomerPSPTNoTracker = param != null;
                                   
                                            this.localCustomerPSPTNo=param;
                                    

                               }
                            

                        /**
                        * field for CustomerNationality
                        */

                        
                                    protected java.lang.String localCustomerNationality ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerNationalityTracker = false ;

                           public boolean isCustomerNationalitySpecified(){
                               return localCustomerNationalityTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerNationality(){
                               return localCustomerNationality;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerNationality
                               */
                               public void setCustomerNationality(java.lang.String param){
                            localCustomerNationalityTracker = param != null;
                                   
                                            this.localCustomerNationality=param;
                                    

                               }
                            

                        /**
                        * field for CustomerMotherName
                        */

                        
                                    protected java.lang.String localCustomerMotherName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerMotherNameTracker = false ;

                           public boolean isCustomerMotherNameSpecified(){
                               return localCustomerMotherNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerMotherName(){
                               return localCustomerMotherName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerMotherName
                               */
                               public void setCustomerMotherName(java.lang.String param){
                            localCustomerMotherNameTracker = param != null;
                                   
                                            this.localCustomerMotherName=param;
                                    

                               }
                            

                        /**
                        * field for CustomerMobileNumber
                        */

                        
                                    protected java.lang.String localCustomerMobileNumber ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerMobileNumberTracker = false ;

                           public boolean isCustomerMobileNumberSpecified(){
                               return localCustomerMobileNumberTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerMobileNumber(){
                               return localCustomerMobileNumber;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerMobileNumber
                               */
                               public void setCustomerMobileNumber(java.lang.String param){
                            localCustomerMobileNumberTracker = param != null;
                                   
                                            this.localCustomerMobileNumber=param;
                                    

                               }
                            

                        /**
                        * field for CustomerEmail
                        */

                        
                                    protected java.lang.String localCustomerEmail ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerEmailTracker = false ;

                           public boolean isCustomerEmailSpecified(){
                               return localCustomerEmailTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerEmail(){
                               return localCustomerEmail;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerEmail
                               */
                               public void setCustomerEmail(java.lang.String param){
                            localCustomerEmailTracker = param != null;
                                   
                                            this.localCustomerEmail=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":getCustomerInfoRes_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "getCustomerInfoRes_type0",
                           xmlWriter);
                   }

               
                   }
                if (localCustomerIdTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "customerId", xmlWriter);
                             

                                          if (localCustomerId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localEidaNoTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "eidaNo", xmlWriter);
                             

                                          if (localEidaNo==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("eidaNo cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEidaNo);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCustomerNamePrefixTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "customerNamePrefix", xmlWriter);
                             

                                          if (localCustomerNamePrefix==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerNamePrefix cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerNamePrefix);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCustomerFullNameTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "customerFullName", xmlWriter);
                             

                                          if (localCustomerFullName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerFullName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerFullName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCustomerShortNameTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "customerShortName", xmlWriter);
                             

                                          if (localCustomerShortName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerShortName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerShortName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCustomerSegmentTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "customerSegment", xmlWriter);
                             

                                          if (localCustomerSegment==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerSegment cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerSegment);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCustomerSegmentDescTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "customerSegmentDesc", xmlWriter);
                             

                                          if (localCustomerSegmentDesc==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerSegmentDesc cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerSegmentDesc);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCustomerTypeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "customerType", xmlWriter);
                             

                                          if (localCustomerType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCustomerDOBTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "customerDOB", xmlWriter);
                             

                                          if (localCustomerDOB==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerDOB cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerDOB);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCustomerPSPTNoTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "customerPSPTNo", xmlWriter);
                             

                                          if (localCustomerPSPTNo==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerPSPTNo cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerPSPTNo);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCustomerNationalityTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "customerNationality", xmlWriter);
                             

                                          if (localCustomerNationality==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerNationality cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerNationality);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCustomerMotherNameTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "customerMotherName", xmlWriter);
                             

                                          if (localCustomerMotherName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerMotherName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerMotherName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCustomerMobileNumberTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "customerMobileNumber", xmlWriter);
                             

                                          if (localCustomerMobileNumber==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerMobileNumber cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerMobileNumber);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCustomerEmailTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "customerEmail", xmlWriter);
                             

                                          if (localCustomerEmail==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerEmail cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerEmail);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd")){
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

                 if (localCustomerIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "customerId"));
                                 
                                        if (localCustomerId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");
                                        }
                                    } if (localEidaNoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "eidaNo"));
                                 
                                        if (localEidaNo != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEidaNo));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("eidaNo cannot be null!!");
                                        }
                                    } if (localCustomerNamePrefixTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "customerNamePrefix"));
                                 
                                        if (localCustomerNamePrefix != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerNamePrefix));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerNamePrefix cannot be null!!");
                                        }
                                    } if (localCustomerFullNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "customerFullName"));
                                 
                                        if (localCustomerFullName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerFullName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerFullName cannot be null!!");
                                        }
                                    } if (localCustomerShortNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "customerShortName"));
                                 
                                        if (localCustomerShortName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerShortName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerShortName cannot be null!!");
                                        }
                                    } if (localCustomerSegmentTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "customerSegment"));
                                 
                                        if (localCustomerSegment != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerSegment));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerSegment cannot be null!!");
                                        }
                                    } if (localCustomerSegmentDescTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "customerSegmentDesc"));
                                 
                                        if (localCustomerSegmentDesc != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerSegmentDesc));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerSegmentDesc cannot be null!!");
                                        }
                                    } if (localCustomerTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "customerType"));
                                 
                                        if (localCustomerType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerType cannot be null!!");
                                        }
                                    } if (localCustomerDOBTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "customerDOB"));
                                 
                                        if (localCustomerDOB != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerDOB));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerDOB cannot be null!!");
                                        }
                                    } if (localCustomerPSPTNoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "customerPSPTNo"));
                                 
                                        if (localCustomerPSPTNo != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerPSPTNo));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerPSPTNo cannot be null!!");
                                        }
                                    } if (localCustomerNationalityTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "customerNationality"));
                                 
                                        if (localCustomerNationality != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerNationality));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerNationality cannot be null!!");
                                        }
                                    } if (localCustomerMotherNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "customerMotherName"));
                                 
                                        if (localCustomerMotherName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerMotherName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerMotherName cannot be null!!");
                                        }
                                    } if (localCustomerMobileNumberTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "customerMobileNumber"));
                                 
                                        if (localCustomerMobileNumber != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerMobileNumber));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerMobileNumber cannot be null!!");
                                        }
                                    } if (localCustomerEmailTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "customerEmail"));
                                 
                                        if (localCustomerEmail != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerEmail));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerEmail cannot be null!!");
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
        public static GetCustomerInfoRes_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            GetCustomerInfoRes_type0 object =
                new GetCustomerInfoRes_type0();

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
                    
                            if (!"getCustomerInfoRes_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (GetCustomerInfoRes_type0)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","customerId").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","eidaNo").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEidaNo(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","customerNamePrefix").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerNamePrefix(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","customerFullName").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerFullName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","customerShortName").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerShortName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","customerSegment").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerSegment(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","customerSegmentDesc").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerSegmentDesc(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","customerType").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","customerDOB").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerDOB(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","customerPSPTNo").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerPSPTNo(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","customerNationality").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerNationality(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","customerMotherName").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerMotherName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","customerMobileNumber").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerMobileNumber(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","customerEmail").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerEmail(
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
           
    
        public static class GetKioskCustEIDAInfoResMsg
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                "getKioskCustEIDAInfoResMsg",
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
                        * field for GetKioskCustEIDAInfoRes
                        */

                        
                                    protected GetKioskCustEIDAInfoRes_type0 localGetKioskCustEIDAInfoRes ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localGetKioskCustEIDAInfoResTracker = false ;

                           public boolean isGetKioskCustEIDAInfoResSpecified(){
                               return localGetKioskCustEIDAInfoResTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return GetKioskCustEIDAInfoRes_type0
                           */
                           public  GetKioskCustEIDAInfoRes_type0 getGetKioskCustEIDAInfoRes(){
                               return localGetKioskCustEIDAInfoRes;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param GetKioskCustEIDAInfoRes
                               */
                               public void setGetKioskCustEIDAInfoRes(GetKioskCustEIDAInfoRes_type0 param){
                            localGetKioskCustEIDAInfoResTracker = param != null;
                                   
                                            this.localGetKioskCustEIDAInfoRes=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":getKioskCustEIDAInfoResMsg",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "getKioskCustEIDAInfoResMsg",
                           xmlWriter);
                   }

               
                   }
               
                                            if (localHeader==null){
                                                 throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
                                            }
                                           localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
                                               xmlWriter);
                                         if (localGetKioskCustEIDAInfoResTracker){
                                            if (localGetKioskCustEIDAInfoRes==null){
                                                 throw new org.apache.axis2.databinding.ADBException("getKioskCustEIDAInfoRes cannot be null!!");
                                            }
                                           localGetKioskCustEIDAInfoRes.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","getKioskCustEIDAInfoRes"),
                                               xmlWriter);
                                        }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd")){
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

                
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd",
                                                                      "header"));
                            
                            
                                    if (localHeader==null){
                                         throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
                                    }
                                    elementList.add(localHeader);
                                 if (localGetKioskCustEIDAInfoResTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "getKioskCustEIDAInfoRes"));
                            
                            
                                    if (localGetKioskCustEIDAInfoRes==null){
                                         throw new org.apache.axis2.databinding.ADBException("getKioskCustEIDAInfoRes cannot be null!!");
                                    }
                                    elementList.add(localGetKioskCustEIDAInfoRes);
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
        public static GetKioskCustEIDAInfoResMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            GetKioskCustEIDAInfoResMsg object =
                new GetKioskCustEIDAInfoResMsg();

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
                    
                            if (!"getKioskCustEIDAInfoResMsg".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (GetKioskCustEIDAInfoResMsg)ExtensionMapper.getTypeObject(
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","getKioskCustEIDAInfoRes").equals(reader.getName())){
                                
                                                object.setGetKioskCustEIDAInfoRes(GetKioskCustEIDAInfoRes_type0.Factory.parse(reader));
                                              
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
           
    
        public static class GetKioskCustEIDAInfoReqMsg
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                "getKioskCustEIDAInfoReqMsg",
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
                        * field for GetKioskCustEIDAInfoReq
                        */

                        
                                    protected GetKioskCustEIDAInfoReq_type0 localGetKioskCustEIDAInfoReq ;
                                

                           /**
                           * Auto generated getter method
                           * @return GetKioskCustEIDAInfoReq_type0
                           */
                           public  GetKioskCustEIDAInfoReq_type0 getGetKioskCustEIDAInfoReq(){
                               return localGetKioskCustEIDAInfoReq;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param GetKioskCustEIDAInfoReq
                               */
                               public void setGetKioskCustEIDAInfoReq(GetKioskCustEIDAInfoReq_type0 param){
                            
                                            this.localGetKioskCustEIDAInfoReq=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":getKioskCustEIDAInfoReqMsg",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "getKioskCustEIDAInfoReqMsg",
                           xmlWriter);
                   }

               
                   }
               
                                            if (localHeader==null){
                                                 throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
                                            }
                                           localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
                                               xmlWriter);
                                        
                                            if (localGetKioskCustEIDAInfoReq==null){
                                                 throw new org.apache.axis2.databinding.ADBException("getKioskCustEIDAInfoReq cannot be null!!");
                                            }
                                           localGetKioskCustEIDAInfoReq.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","getKioskCustEIDAInfoReq"),
                                               xmlWriter);
                                        
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd")){
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

                
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd",
                                                                      "header"));
                            
                            
                                    if (localHeader==null){
                                         throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
                                    }
                                    elementList.add(localHeader);
                                
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "getKioskCustEIDAInfoReq"));
                            
                            
                                    if (localGetKioskCustEIDAInfoReq==null){
                                         throw new org.apache.axis2.databinding.ADBException("getKioskCustEIDAInfoReq cannot be null!!");
                                    }
                                    elementList.add(localGetKioskCustEIDAInfoReq);
                                

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
        public static GetKioskCustEIDAInfoReqMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            GetKioskCustEIDAInfoReqMsg object =
                new GetKioskCustEIDAInfoReqMsg();

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
                    
                            if (!"getKioskCustEIDAInfoReqMsg".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (GetKioskCustEIDAInfoReqMsg)ExtensionMapper.getTypeObject(
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","getKioskCustEIDAInfoReq").equals(reader.getName())){
                                
                                                object.setGetKioskCustEIDAInfoReq(GetKioskCustEIDAInfoReq_type0.Factory.parse(reader));
                                              
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
           
    
        public static class GetCustomerInfoReq_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = getCustomerInfoReq_type0
                Namespace URI = http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd
                Namespace Prefix = ns2
                */
            
            /** Whenever a new property is set ensure all others are unset
             *  There can be only one choice and the last one wins
             */
            private void clearAllSettingTrackers() {
            
                   localCustomerIdTracker = false;
                
                   localEidaNoTracker = false;
                
            }
        

                        /**
                        * field for CustomerId
                        */

                        
                                    protected java.lang.String localCustomerId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerIdTracker = false ;

                           public boolean isCustomerIdSpecified(){
                               return localCustomerIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerId(){
                               return localCustomerId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerId
                               */
                               public void setCustomerId(java.lang.String param){
                            
                                clearAllSettingTrackers();
                            localCustomerIdTracker = param != null;
                                   
                                            this.localCustomerId=param;
                                    

                               }
                            

                        /**
                        * field for EidaNo
                        */

                        
                                    protected java.lang.String localEidaNo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEidaNoTracker = false ;

                           public boolean isEidaNoSpecified(){
                               return localEidaNoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEidaNo(){
                               return localEidaNo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EidaNo
                               */
                               public void setEidaNo(java.lang.String param){
                            
                                clearAllSettingTrackers();
                            localEidaNoTracker = param != null;
                                   
                                            this.localEidaNo=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":getCustomerInfoReq_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "getCustomerInfoReq_type0",
                           xmlWriter);
                   }

               
                   }
                if (localCustomerIdTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "customerId", xmlWriter);
                             

                                          if (localCustomerId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localEidaNoTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "eidaNo", xmlWriter);
                             

                                          if (localEidaNo==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("eidaNo cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEidaNo);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd")){
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

                 if (localCustomerIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "customerId"));
                                 
                                        if (localCustomerId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");
                                        }
                                    } if (localEidaNoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "eidaNo"));
                                 
                                        if (localEidaNo != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEidaNo));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("eidaNo cannot be null!!");
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
        public static GetCustomerInfoReq_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            GetCustomerInfoReq_type0 object =
                new GetCustomerInfoReq_type0();

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
                    
                            if (!"getCustomerInfoReq_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (GetCustomerInfoReq_type0)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                   
                while(!reader.isEndElement()) {
                    if (reader.isStartElement() ){
                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","customerId").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                        else
                                    
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","eidaNo").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEidaNo(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
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
           
    
        public static class GetKioskCustEIDAInfoRes_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = getKioskCustEIDAInfoRes_type0
                Namespace URI = http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd
                Namespace Prefix = ns2
                */
            

                        /**
                        * field for EidaNo
                        */

                        
                                    protected java.lang.String localEidaNo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEidaNoTracker = false ;

                           public boolean isEidaNoSpecified(){
                               return localEidaNoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEidaNo(){
                               return localEidaNo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EidaNo
                               */
                               public void setEidaNo(java.lang.String param){
                            localEidaNoTracker = param != null;
                                   
                                            this.localEidaNo=param;
                                    

                               }
                            

                        /**
                        * field for DateOfBirth
                        */

                        
                                    protected java.lang.String localDateOfBirth ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDateOfBirthTracker = false ;

                           public boolean isDateOfBirthSpecified(){
                               return localDateOfBirthTracker;
                           }

                           

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
                            localDateOfBirthTracker = param != null;
                                   
                                            this.localDateOfBirth=param;
                                    

                               }
                            

                        /**
                        * field for Gender
                        */

                        
                                    protected java.lang.String localGender ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localGenderTracker = false ;

                           public boolean isGenderSpecified(){
                               return localGenderTracker;
                           }

                           

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
                            localGenderTracker = param != null;
                                   
                                            this.localGender=param;
                                    

                               }
                            

                        /**
                        * field for HusbandId
                        */

                        
                                    protected java.lang.String localHusbandId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHusbandIdTracker = false ;

                           public boolean isHusbandIdSpecified(){
                               return localHusbandIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getHusbandId(){
                               return localHusbandId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HusbandId
                               */
                               public void setHusbandId(java.lang.String param){
                            localHusbandIdTracker = param != null;
                                   
                                            this.localHusbandId=param;
                                    

                               }
                            

                        /**
                        * field for IdPhoto
                        */

                        
                                    protected java.lang.String localIdPhoto ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localIdPhotoTracker = false ;

                           public boolean isIdPhotoSpecified(){
                               return localIdPhotoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getIdPhoto(){
                               return localIdPhoto;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IdPhoto
                               */
                               public void setIdPhoto(java.lang.String param){
                            localIdPhotoTracker = param != null;
                                   
                                            this.localIdPhoto=param;
                                    

                               }
                            

                        /**
                        * field for IssueDate
                        */

                        
                                    protected java.lang.String localIssueDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localIssueDateTracker = false ;

                           public boolean isIssueDateSpecified(){
                               return localIssueDateTracker;
                           }

                           

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
                            localIssueDateTracker = param != null;
                                   
                                            this.localIssueDate=param;
                                    

                               }
                            

                        /**
                        * field for ExpiryDate
                        */

                        
                                    protected java.lang.String localExpiryDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localExpiryDateTracker = false ;

                           public boolean isExpiryDateSpecified(){
                               return localExpiryDateTracker;
                           }

                           

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
                            localExpiryDateTracker = param != null;
                                   
                                            this.localExpiryDate=param;
                                    

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
                        * field for MotherName
                        */

                        
                                    protected java.lang.String localMotherName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMotherNameTracker = false ;

                           public boolean isMotherNameSpecified(){
                               return localMotherNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMotherName(){
                               return localMotherName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MotherName
                               */
                               public void setMotherName(java.lang.String param){
                            localMotherNameTracker = param != null;
                                   
                                            this.localMotherName=param;
                                    

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
                        * field for Name
                        */

                        
                                    protected java.lang.String localName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNameTracker = false ;

                           public boolean isNameSpecified(){
                               return localNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getName(){
                               return localName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Name
                               */
                               public void setName(java.lang.String param){
                            localNameTracker = param != null;
                                   
                                            this.localName=param;
                                    

                               }
                            

                        /**
                        * field for Nationality
                        */

                        
                                    protected java.lang.String localNationality ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNationalityTracker = false ;

                           public boolean isNationalitySpecified(){
                               return localNationalityTracker;
                           }

                           

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
                            localNationalityTracker = param != null;
                                   
                                            this.localNationality=param;
                                    

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
                        * field for PassportIssuedCountry
                        */

                        
                                    protected java.lang.String localPassportIssuedCountry ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPassportIssuedCountryTracker = false ;

                           public boolean isPassportIssuedCountrySpecified(){
                               return localPassportIssuedCountryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPassportIssuedCountry(){
                               return localPassportIssuedCountry;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PassportIssuedCountry
                               */
                               public void setPassportIssuedCountry(java.lang.String param){
                            localPassportIssuedCountryTracker = param != null;
                                   
                                            this.localPassportIssuedCountry=param;
                                    

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
                        * field for ResidentNo
                        */

                        
                                    protected java.lang.String localResidentNo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResidentNoTracker = false ;

                           public boolean isResidentNoSpecified(){
                               return localResidentNoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getResidentNo(){
                               return localResidentNo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ResidentNo
                               */
                               public void setResidentNo(java.lang.String param){
                            localResidentNoTracker = param != null;
                                   
                                            this.localResidentNo=param;
                                    

                               }
                            

                        /**
                        * field for ResidentType
                        */

                        
                                    protected java.lang.String localResidentType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResidentTypeTracker = false ;

                           public boolean isResidentTypeSpecified(){
                               return localResidentTypeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getResidentType(){
                               return localResidentType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ResidentType
                               */
                               public void setResidentType(java.lang.String param){
                            localResidentTypeTracker = param != null;
                                   
                                            this.localResidentType=param;
                                    

                               }
                            

                        /**
                        * field for ResidentExpiry
                        */

                        
                                    protected java.lang.String localResidentExpiry ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResidentExpiryTracker = false ;

                           public boolean isResidentExpirySpecified(){
                               return localResidentExpiryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getResidentExpiry(){
                               return localResidentExpiry;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ResidentExpiry
                               */
                               public void setResidentExpiry(java.lang.String param){
                            localResidentExpiryTracker = param != null;
                                   
                                            this.localResidentExpiry=param;
                                    

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
                        * field for EmployerName
                        */

                        
                                    protected java.lang.String localEmployerName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEmployerNameTracker = false ;

                           public boolean isEmployerNameSpecified(){
                               return localEmployerNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEmployerName(){
                               return localEmployerName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EmployerName
                               */
                               public void setEmployerName(java.lang.String param){
                            localEmployerNameTracker = param != null;
                                   
                                            this.localEmployerName=param;
                                    

                               }
                            

                        /**
                        * field for CorrPoBox
                        */

                        
                                    protected java.lang.String localCorrPoBox ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCorrPoBoxTracker = false ;

                           public boolean isCorrPoBoxSpecified(){
                               return localCorrPoBoxTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCorrPoBox(){
                               return localCorrPoBox;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CorrPoBox
                               */
                               public void setCorrPoBox(java.lang.String param){
                            localCorrPoBoxTracker = param != null;
                                   
                                            this.localCorrPoBox=param;
                                    

                               }
                            

                        /**
                        * field for CorrCountry
                        */

                        
                                    protected java.lang.String localCorrCountry ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCorrCountryTracker = false ;

                           public boolean isCorrCountrySpecified(){
                               return localCorrCountryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCorrCountry(){
                               return localCorrCountry;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CorrCountry
                               */
                               public void setCorrCountry(java.lang.String param){
                            localCorrCountryTracker = param != null;
                                   
                                            this.localCorrCountry=param;
                                    

                               }
                            

                        /**
                        * field for CorrState
                        */

                        
                                    protected java.lang.String localCorrState ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCorrStateTracker = false ;

                           public boolean isCorrStateSpecified(){
                               return localCorrStateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCorrState(){
                               return localCorrState;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CorrState
                               */
                               public void setCorrState(java.lang.String param){
                            localCorrStateTracker = param != null;
                                   
                                            this.localCorrState=param;
                                    

                               }
                            

                        /**
                        * field for CorrCity
                        */

                        
                                    protected java.lang.String localCorrCity ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCorrCityTracker = false ;

                           public boolean isCorrCitySpecified(){
                               return localCorrCityTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCorrCity(){
                               return localCorrCity;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CorrCity
                               */
                               public void setCorrCity(java.lang.String param){
                            localCorrCityTracker = param != null;
                                   
                                            this.localCorrCity=param;
                                    

                               }
                            

                        /**
                        * field for CountryofResident
                        */

                        
                                    protected java.lang.String localCountryofResident ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCountryofResidentTracker = false ;

                           public boolean isCountryofResidentSpecified(){
                               return localCountryofResidentTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCountryofResident(){
                               return localCountryofResident;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CountryofResident
                               */
                               public void setCountryofResident(java.lang.String param){
                            localCountryofResidentTracker = param != null;
                                   
                                            this.localCountryofResident=param;
                                    

                               }
                            

                        /**
                        * field for CountryofPerm
                        */

                        
                                    protected java.lang.String localCountryofPerm ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCountryofPermTracker = false ;

                           public boolean isCountryofPermSpecified(){
                               return localCountryofPermTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCountryofPerm(){
                               return localCountryofPerm;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CountryofPerm
                               */
                               public void setCountryofPerm(java.lang.String param){
                            localCountryofPermTracker = param != null;
                                   
                                            this.localCountryofPerm=param;
                                    

                               }
                            

                        /**
                        * field for TelephoneResidence
                        */

                        
                                    protected java.lang.String localTelephoneResidence ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTelephoneResidenceTracker = false ;

                           public boolean isTelephoneResidenceSpecified(){
                               return localTelephoneResidenceTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTelephoneResidence(){
                               return localTelephoneResidence;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TelephoneResidence
                               */
                               public void setTelephoneResidence(java.lang.String param){
                            localTelephoneResidenceTracker = param != null;
                                   
                                            this.localTelephoneResidence=param;
                                    

                               }
                            

                        /**
                        * field for TelMobile
                        */

                        
                                    protected java.lang.String localTelMobile ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTelMobileTracker = false ;

                           public boolean isTelMobileSpecified(){
                               return localTelMobileTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTelMobile(){
                               return localTelMobile;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TelMobile
                               */
                               public void setTelMobile(java.lang.String param){
                            localTelMobileTracker = param != null;
                                   
                                            this.localTelMobile=param;
                                    

                               }
                            

                        /**
                        * field for Email
                        */

                        
                                    protected java.lang.String localEmail ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEmailTracker = false ;

                           public boolean isEmailSpecified(){
                               return localEmailTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEmail(){
                               return localEmail;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Email
                               */
                               public void setEmail(java.lang.String param){
                            localEmailTracker = param != null;
                                   
                                            this.localEmail=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":getKioskCustEIDAInfoRes_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "getKioskCustEIDAInfoRes_type0",
                           xmlWriter);
                   }

               
                   }
                if (localEidaNoTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "eidaNo", xmlWriter);
                             

                                          if (localEidaNo==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("eidaNo cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEidaNo);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDateOfBirthTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "dateOfBirth", xmlWriter);
                             

                                          if (localDateOfBirth==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("dateOfBirth cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDateOfBirth);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localGenderTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "gender", xmlWriter);
                             

                                          if (localGender==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("gender cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localGender);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHusbandIdTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "husbandId", xmlWriter);
                             

                                          if (localHusbandId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("husbandId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localHusbandId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localIdPhotoTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "idPhoto", xmlWriter);
                             

                                          if (localIdPhoto==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("idPhoto cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localIdPhoto);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localIssueDateTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "issueDate", xmlWriter);
                             

                                          if (localIssueDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("issueDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localIssueDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localExpiryDateTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "expiryDate", xmlWriter);
                             

                                          if (localExpiryDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("expiryDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localExpiryDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMaritalStatusTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "maritalStatus", xmlWriter);
                             

                                          if (localMaritalStatus==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("maritalStatus cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMaritalStatus);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMotherNameTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "motherName", xmlWriter);
                             

                                          if (localMotherName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("motherName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMotherName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTitleTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "title", xmlWriter);
                             

                                          if (localTitle==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("title cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTitle);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNameTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "name", xmlWriter);
                             

                                          if (localName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("name cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNationalityTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "nationality", xmlWriter);
                             

                                          if (localNationality==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("nationality cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localNationality);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOccupationTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "occupation", xmlWriter);
                             

                                          if (localOccupation==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("occupation cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOccupation);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPlaceOfBirthTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "placeOfBirth", xmlWriter);
                             

                                          if (localPlaceOfBirth==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("placeOfBirth cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPlaceOfBirth);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPassportNumberTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "passportNumber", xmlWriter);
                             

                                          if (localPassportNumber==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("passportNumber cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPassportNumber);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPassportTypeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "passportType", xmlWriter);
                             

                                          if (localPassportType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("passportType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPassportType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPassportIssuedCountryTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "passportIssuedCountry", xmlWriter);
                             

                                          if (localPassportIssuedCountry==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("passportIssuedCountry cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPassportIssuedCountry);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPassportExpiryDateTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "passportExpiryDate", xmlWriter);
                             

                                          if (localPassportExpiryDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("passportExpiryDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPassportExpiryDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localResidentNoTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "residentNo", xmlWriter);
                             

                                          if (localResidentNo==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("residentNo cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localResidentNo);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localResidentTypeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "residentType", xmlWriter);
                             

                                          if (localResidentType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("residentType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localResidentType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localResidentExpiryTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "residentExpiry", xmlWriter);
                             

                                          if (localResidentExpiry==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("residentExpiry cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localResidentExpiry);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPassportIssueDateTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "passportIssueDate", xmlWriter);
                             

                                          if (localPassportIssueDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("passportIssueDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPassportIssueDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localEmployerNameTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "employerName", xmlWriter);
                             

                                          if (localEmployerName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("employerName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEmployerName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCorrPoBoxTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "corrPoBox", xmlWriter);
                             

                                          if (localCorrPoBox==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("corrPoBox cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCorrPoBox);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCorrCountryTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "corrCountry", xmlWriter);
                             

                                          if (localCorrCountry==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("corrCountry cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCorrCountry);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCorrStateTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "corrState", xmlWriter);
                             

                                          if (localCorrState==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("corrState cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCorrState);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCorrCityTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "corrCity", xmlWriter);
                             

                                          if (localCorrCity==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("corrCity cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCorrCity);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCountryofResidentTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "countryofResident", xmlWriter);
                             

                                          if (localCountryofResident==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("countryofResident cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCountryofResident);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCountryofPermTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "countryofPerm", xmlWriter);
                             

                                          if (localCountryofPerm==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("countryofPerm cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCountryofPerm);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTelephoneResidenceTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "telephoneResidence", xmlWriter);
                             

                                          if (localTelephoneResidence==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("telephoneResidence cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTelephoneResidence);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTelMobileTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "telMobile", xmlWriter);
                             

                                          if (localTelMobile==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("telMobile cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTelMobile);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localEmailTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "email", xmlWriter);
                             

                                          if (localEmail==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("email cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEmail);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd")){
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

                 if (localEidaNoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "eidaNo"));
                                 
                                        if (localEidaNo != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEidaNo));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("eidaNo cannot be null!!");
                                        }
                                    } if (localDateOfBirthTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "dateOfBirth"));
                                 
                                        if (localDateOfBirth != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDateOfBirth));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("dateOfBirth cannot be null!!");
                                        }
                                    } if (localGenderTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "gender"));
                                 
                                        if (localGender != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGender));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("gender cannot be null!!");
                                        }
                                    } if (localHusbandIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "husbandId"));
                                 
                                        if (localHusbandId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHusbandId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("husbandId cannot be null!!");
                                        }
                                    } if (localIdPhotoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "idPhoto"));
                                 
                                        if (localIdPhoto != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIdPhoto));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("idPhoto cannot be null!!");
                                        }
                                    } if (localIssueDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "issueDate"));
                                 
                                        if (localIssueDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIssueDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("issueDate cannot be null!!");
                                        }
                                    } if (localExpiryDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "expiryDate"));
                                 
                                        if (localExpiryDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExpiryDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("expiryDate cannot be null!!");
                                        }
                                    } if (localMaritalStatusTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "maritalStatus"));
                                 
                                        if (localMaritalStatus != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMaritalStatus));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("maritalStatus cannot be null!!");
                                        }
                                    } if (localMotherNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "motherName"));
                                 
                                        if (localMotherName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMotherName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("motherName cannot be null!!");
                                        }
                                    } if (localTitleTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "title"));
                                 
                                        if (localTitle != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTitle));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("title cannot be null!!");
                                        }
                                    } if (localNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "name"));
                                 
                                        if (localName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("name cannot be null!!");
                                        }
                                    } if (localNationalityTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "nationality"));
                                 
                                        if (localNationality != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNationality));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("nationality cannot be null!!");
                                        }
                                    } if (localOccupationTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "occupation"));
                                 
                                        if (localOccupation != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOccupation));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("occupation cannot be null!!");
                                        }
                                    } if (localPlaceOfBirthTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "placeOfBirth"));
                                 
                                        if (localPlaceOfBirth != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPlaceOfBirth));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("placeOfBirth cannot be null!!");
                                        }
                                    } if (localPassportNumberTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "passportNumber"));
                                 
                                        if (localPassportNumber != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPassportNumber));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("passportNumber cannot be null!!");
                                        }
                                    } if (localPassportTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "passportType"));
                                 
                                        if (localPassportType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPassportType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("passportType cannot be null!!");
                                        }
                                    } if (localPassportIssuedCountryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "passportIssuedCountry"));
                                 
                                        if (localPassportIssuedCountry != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPassportIssuedCountry));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("passportIssuedCountry cannot be null!!");
                                        }
                                    } if (localPassportExpiryDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "passportExpiryDate"));
                                 
                                        if (localPassportExpiryDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPassportExpiryDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("passportExpiryDate cannot be null!!");
                                        }
                                    } if (localResidentNoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "residentNo"));
                                 
                                        if (localResidentNo != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResidentNo));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("residentNo cannot be null!!");
                                        }
                                    } if (localResidentTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "residentType"));
                                 
                                        if (localResidentType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResidentType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("residentType cannot be null!!");
                                        }
                                    } if (localResidentExpiryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "residentExpiry"));
                                 
                                        if (localResidentExpiry != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResidentExpiry));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("residentExpiry cannot be null!!");
                                        }
                                    } if (localPassportIssueDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "passportIssueDate"));
                                 
                                        if (localPassportIssueDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPassportIssueDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("passportIssueDate cannot be null!!");
                                        }
                                    } if (localEmployerNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "employerName"));
                                 
                                        if (localEmployerName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEmployerName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("employerName cannot be null!!");
                                        }
                                    } if (localCorrPoBoxTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "corrPoBox"));
                                 
                                        if (localCorrPoBox != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCorrPoBox));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("corrPoBox cannot be null!!");
                                        }
                                    } if (localCorrCountryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "corrCountry"));
                                 
                                        if (localCorrCountry != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCorrCountry));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("corrCountry cannot be null!!");
                                        }
                                    } if (localCorrStateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "corrState"));
                                 
                                        if (localCorrState != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCorrState));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("corrState cannot be null!!");
                                        }
                                    } if (localCorrCityTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "corrCity"));
                                 
                                        if (localCorrCity != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCorrCity));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("corrCity cannot be null!!");
                                        }
                                    } if (localCountryofResidentTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "countryofResident"));
                                 
                                        if (localCountryofResident != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCountryofResident));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("countryofResident cannot be null!!");
                                        }
                                    } if (localCountryofPermTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "countryofPerm"));
                                 
                                        if (localCountryofPerm != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCountryofPerm));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("countryofPerm cannot be null!!");
                                        }
                                    } if (localTelephoneResidenceTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "telephoneResidence"));
                                 
                                        if (localTelephoneResidence != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTelephoneResidence));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("telephoneResidence cannot be null!!");
                                        }
                                    } if (localTelMobileTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "telMobile"));
                                 
                                        if (localTelMobile != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTelMobile));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("telMobile cannot be null!!");
                                        }
                                    } if (localEmailTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "email"));
                                 
                                        if (localEmail != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEmail));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("email cannot be null!!");
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
        public static GetKioskCustEIDAInfoRes_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            GetKioskCustEIDAInfoRes_type0 object =
                new GetKioskCustEIDAInfoRes_type0();

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
                    
                            if (!"getKioskCustEIDAInfoRes_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (GetKioskCustEIDAInfoRes_type0)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","eidaNo").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEidaNo(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","dateOfBirth").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDateOfBirth(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","gender").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setGender(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","husbandId").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHusbandId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","idPhoto").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setIdPhoto(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","issueDate").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setIssueDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","expiryDate").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setExpiryDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","maritalStatus").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMaritalStatus(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","motherName").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMotherName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","title").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTitle(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","name").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","nationality").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNationality(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","occupation").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOccupation(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","placeOfBirth").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPlaceOfBirth(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","passportNumber").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPassportNumber(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","passportType").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPassportType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","passportIssuedCountry").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPassportIssuedCountry(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","passportExpiryDate").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPassportExpiryDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","residentNo").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setResidentNo(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","residentType").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setResidentType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","residentExpiry").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setResidentExpiry(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","passportIssueDate").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPassportIssueDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","employerName").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEmployerName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","corrPoBox").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCorrPoBox(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","corrCountry").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCorrCountry(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","corrState").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCorrState(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","corrCity").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCorrCity(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","countryofResident").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCountryofResident(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","countryofPerm").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCountryofPerm(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","telephoneResidence").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTelephoneResidence(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","telMobile").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTelMobile(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","email").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEmail(
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
           
    
        public static class GetChqLeavesIssueAcctsResMsg
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                "getChqLeavesIssueAcctsResMsg",
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
                        * field for GetChqLeavesIssueAcctsRes
                        */

                        
                                    protected GetChqLeavesIssueAcctsRes_type0 localGetChqLeavesIssueAcctsRes ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localGetChqLeavesIssueAcctsResTracker = false ;

                           public boolean isGetChqLeavesIssueAcctsResSpecified(){
                               return localGetChqLeavesIssueAcctsResTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return GetChqLeavesIssueAcctsRes_type0
                           */
                           public  GetChqLeavesIssueAcctsRes_type0 getGetChqLeavesIssueAcctsRes(){
                               return localGetChqLeavesIssueAcctsRes;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param GetChqLeavesIssueAcctsRes
                               */
                               public void setGetChqLeavesIssueAcctsRes(GetChqLeavesIssueAcctsRes_type0 param){
                            localGetChqLeavesIssueAcctsResTracker = param != null;
                                   
                                            this.localGetChqLeavesIssueAcctsRes=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":getChqLeavesIssueAcctsResMsg",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "getChqLeavesIssueAcctsResMsg",
                           xmlWriter);
                   }

               
                   }
               
                                            if (localHeader==null){
                                                 throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
                                            }
                                           localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
                                               xmlWriter);
                                         if (localGetChqLeavesIssueAcctsResTracker){
                                            if (localGetChqLeavesIssueAcctsRes==null){
                                                 throw new org.apache.axis2.databinding.ADBException("getChqLeavesIssueAcctsRes cannot be null!!");
                                            }
                                           localGetChqLeavesIssueAcctsRes.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","getChqLeavesIssueAcctsRes"),
                                               xmlWriter);
                                        }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd")){
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

                
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd",
                                                                      "header"));
                            
                            
                                    if (localHeader==null){
                                         throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
                                    }
                                    elementList.add(localHeader);
                                 if (localGetChqLeavesIssueAcctsResTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "getChqLeavesIssueAcctsRes"));
                            
                            
                                    if (localGetChqLeavesIssueAcctsRes==null){
                                         throw new org.apache.axis2.databinding.ADBException("getChqLeavesIssueAcctsRes cannot be null!!");
                                    }
                                    elementList.add(localGetChqLeavesIssueAcctsRes);
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
        public static GetChqLeavesIssueAcctsResMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            GetChqLeavesIssueAcctsResMsg object =
                new GetChqLeavesIssueAcctsResMsg();

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
                    
                            if (!"getChqLeavesIssueAcctsResMsg".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (GetChqLeavesIssueAcctsResMsg)ExtensionMapper.getTypeObject(
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","getChqLeavesIssueAcctsRes").equals(reader.getName())){
                                
                                                object.setGetChqLeavesIssueAcctsRes(GetChqLeavesIssueAcctsRes_type0.Factory.parse(reader));
                                              
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
           
    
        public static class GetChqLeavesIssueAcctsRes_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = getChqLeavesIssueAcctsRes_type0
                Namespace URI = http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd
                Namespace Prefix = ns2
                */
            

                        /**
                        * field for CustomerId
                        */

                        
                                    protected java.lang.String localCustomerId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerIdTracker = false ;

                           public boolean isCustomerIdSpecified(){
                               return localCustomerIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerId(){
                               return localCustomerId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerId
                               */
                               public void setCustomerId(java.lang.String param){
                            localCustomerIdTracker = param != null;
                                   
                                            this.localCustomerId=param;
                                    

                               }
                            

                        /**
                        * field for Chqueueleavesdetails
                        */

                        
                                    protected Chqueueleavesdetails_type0 localChqueueleavesdetails ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localChqueueleavesdetailsTracker = false ;

                           public boolean isChqueueleavesdetailsSpecified(){
                               return localChqueueleavesdetailsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return Chqueueleavesdetails_type0
                           */
                           public  Chqueueleavesdetails_type0 getChqueueleavesdetails(){
                               return localChqueueleavesdetails;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Chqueueleavesdetails
                               */
                               public void setChqueueleavesdetails(Chqueueleavesdetails_type0 param){
                            localChqueueleavesdetailsTracker = param != null;
                                   
                                            this.localChqueueleavesdetails=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":getChqLeavesIssueAcctsRes_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "getChqLeavesIssueAcctsRes_type0",
                           xmlWriter);
                   }

               
                   }
                if (localCustomerIdTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "customerId", xmlWriter);
                             

                                          if (localCustomerId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localChqueueleavesdetailsTracker){
                                            if (localChqueueleavesdetails==null){
                                                 throw new org.apache.axis2.databinding.ADBException("Chqueueleavesdetails cannot be null!!");
                                            }
                                           localChqueueleavesdetails.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","Chqueueleavesdetails"),
                                               xmlWriter);
                                        }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd")){
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

                 if (localCustomerIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "customerId"));
                                 
                                        if (localCustomerId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");
                                        }
                                    } if (localChqueueleavesdetailsTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "Chqueueleavesdetails"));
                            
                            
                                    if (localChqueueleavesdetails==null){
                                         throw new org.apache.axis2.databinding.ADBException("Chqueueleavesdetails cannot be null!!");
                                    }
                                    elementList.add(localChqueueleavesdetails);
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
        public static GetChqLeavesIssueAcctsRes_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            GetChqLeavesIssueAcctsRes_type0 object =
                new GetChqLeavesIssueAcctsRes_type0();

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
                    
                            if (!"getChqLeavesIssueAcctsRes_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (GetChqLeavesIssueAcctsRes_type0)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","customerId").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","Chqueueleavesdetails").equals(reader.getName())){
                                
                                                object.setChqueueleavesdetails(Chqueueleavesdetails_type0.Factory.parse(reader));
                                              
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
           
    
        public static class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd".equals(namespaceURI) &&
                  "Chqueueleaves_type0".equals(typeName)){
                   
                            return  Chqueueleaves_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd".equals(namespaceURI) &&
                  "getKioskCustEIDAInfoRes_type0".equals(typeName)){
                   
                            return  GetKioskCustEIDAInfoRes_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd".equals(namespaceURI) &&
                  "getChqLeavesIssueAcctsRes_type0".equals(typeName)){
                   
                            return  GetChqLeavesIssueAcctsRes_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd".equals(namespaceURI) &&
                  "getCustomerInfoRes_type0".equals(typeName)){
                   
                            return  GetCustomerInfoRes_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd".equals(namespaceURI) &&
                  "getKioskCustActivitiesDtlRes_type0".equals(typeName)){
                   
                            return  GetKioskCustActivitiesDtlRes_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd".equals(namespaceURI) &&
                  "getKioskCustEIDAInfoReq_type0".equals(typeName)){
                   
                            return  GetKioskCustEIDAInfoReq_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd".equals(namespaceURI) &&
                  "Chqueueleavesdetails_type0".equals(typeName)){
                   
                            return  Chqueueleavesdetails_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd".equals(namespaceURI) &&
                  "getCustomerInfoReq_type0".equals(typeName)){
                   
                            return  GetCustomerInfoReq_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd".equals(namespaceURI) &&
                  "getKioskCustActivitiesDtlReq_type0".equals(typeName)){
                   
                            return  GetKioskCustActivitiesDtlReq_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd".equals(namespaceURI) &&
                  "activities_type0".equals(typeName)){
                   
                            return  Activities_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd".equals(namespaceURI) &&
                  "getChqLeavesIssueAcctsReq_type0".equals(typeName)){
                   
                            return  GetChqLeavesIssueAcctsReq_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.adcb.com/esb/common/header.xsd".equals(namespaceURI) &&
                  "headerType".equals(typeName)){
                   
                            return  HeaderType.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    
        public static class GetCustomerInfoResMsg
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                "getCustomerInfoResMsg",
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
                        * field for GetCustomerInfoRes
                        */

                        
                                    protected GetCustomerInfoRes_type0 localGetCustomerInfoRes ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localGetCustomerInfoResTracker = false ;

                           public boolean isGetCustomerInfoResSpecified(){
                               return localGetCustomerInfoResTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return GetCustomerInfoRes_type0
                           */
                           public  GetCustomerInfoRes_type0 getGetCustomerInfoRes(){
                               return localGetCustomerInfoRes;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param GetCustomerInfoRes
                               */
                               public void setGetCustomerInfoRes(GetCustomerInfoRes_type0 param){
                            localGetCustomerInfoResTracker = param != null;
                                   
                                            this.localGetCustomerInfoRes=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":getCustomerInfoResMsg",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "getCustomerInfoResMsg",
                           xmlWriter);
                   }

               
                   }
               
                                            if (localHeader==null){
                                                 throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
                                            }
                                           localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
                                               xmlWriter);
                                         if (localGetCustomerInfoResTracker){
                                            if (localGetCustomerInfoRes==null){
                                                 throw new org.apache.axis2.databinding.ADBException("getCustomerInfoRes cannot be null!!");
                                            }
                                           localGetCustomerInfoRes.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","getCustomerInfoRes"),
                                               xmlWriter);
                                        }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd")){
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

                
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd",
                                                                      "header"));
                            
                            
                                    if (localHeader==null){
                                         throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
                                    }
                                    elementList.add(localHeader);
                                 if (localGetCustomerInfoResTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "getCustomerInfoRes"));
                            
                            
                                    if (localGetCustomerInfoRes==null){
                                         throw new org.apache.axis2.databinding.ADBException("getCustomerInfoRes cannot be null!!");
                                    }
                                    elementList.add(localGetCustomerInfoRes);
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
        public static GetCustomerInfoResMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            GetCustomerInfoResMsg object =
                new GetCustomerInfoResMsg();

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
                    
                            if (!"getCustomerInfoResMsg".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (GetCustomerInfoResMsg)ExtensionMapper.getTypeObject(
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","getCustomerInfoRes").equals(reader.getName())){
                                
                                                object.setGetCustomerInfoRes(GetCustomerInfoRes_type0.Factory.parse(reader));
                                              
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
           
    
        public static class GetKioskCustEIDAInfoReq_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = getKioskCustEIDAInfoReq_type0
                Namespace URI = http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd
                Namespace Prefix = ns2
                */
            

                        /**
                        * field for EidaNo
                        */

                        
                                    protected java.lang.String localEidaNo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEidaNoTracker = false ;

                           public boolean isEidaNoSpecified(){
                               return localEidaNoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEidaNo(){
                               return localEidaNo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EidaNo
                               */
                               public void setEidaNo(java.lang.String param){
                            localEidaNoTracker = param != null;
                                   
                                            this.localEidaNo=param;
                                    

                               }
                            

                        /**
                        * field for CustomerId
                        */

                        
                                    protected java.lang.String localCustomerId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerIdTracker = false ;

                           public boolean isCustomerIdSpecified(){
                               return localCustomerIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerId(){
                               return localCustomerId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerId
                               */
                               public void setCustomerId(java.lang.String param){
                            localCustomerIdTracker = param != null;
                                   
                                            this.localCustomerId=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":getKioskCustEIDAInfoReq_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "getKioskCustEIDAInfoReq_type0",
                           xmlWriter);
                   }

               
                   }
                if (localEidaNoTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "eidaNo", xmlWriter);
                             

                                          if (localEidaNo==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("eidaNo cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEidaNo);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCustomerIdTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "customerId", xmlWriter);
                             

                                          if (localCustomerId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd")){
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

                 if (localEidaNoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "eidaNo"));
                                 
                                        if (localEidaNo != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEidaNo));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("eidaNo cannot be null!!");
                                        }
                                    } if (localCustomerIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "customerId"));
                                 
                                        if (localCustomerId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");
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
        public static GetKioskCustEIDAInfoReq_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            GetKioskCustEIDAInfoReq_type0 object =
                new GetKioskCustEIDAInfoReq_type0();

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
                    
                            if (!"getKioskCustEIDAInfoReq_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (GetKioskCustEIDAInfoReq_type0)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","eidaNo").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEidaNo(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","customerId").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerId(
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
           
    
        public static class GetKioskCustActivitiesDtlReqMsg
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                "getKioskCustActivitiesDtlReqMsg",
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
                        * field for GetKioskCustActivitiesDtlReq
                        */

                        
                                    protected GetKioskCustActivitiesDtlReq_type0 localGetKioskCustActivitiesDtlReq ;
                                

                           /**
                           * Auto generated getter method
                           * @return GetKioskCustActivitiesDtlReq_type0
                           */
                           public  GetKioskCustActivitiesDtlReq_type0 getGetKioskCustActivitiesDtlReq(){
                               return localGetKioskCustActivitiesDtlReq;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param GetKioskCustActivitiesDtlReq
                               */
                               public void setGetKioskCustActivitiesDtlReq(GetKioskCustActivitiesDtlReq_type0 param){
                            
                                            this.localGetKioskCustActivitiesDtlReq=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":getKioskCustActivitiesDtlReqMsg",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "getKioskCustActivitiesDtlReqMsg",
                           xmlWriter);
                   }

               
                   }
               
                                            if (localHeader==null){
                                                 throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
                                            }
                                           localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
                                               xmlWriter);
                                        
                                            if (localGetKioskCustActivitiesDtlReq==null){
                                                 throw new org.apache.axis2.databinding.ADBException("getKioskCustActivitiesDtlReq cannot be null!!");
                                            }
                                           localGetKioskCustActivitiesDtlReq.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","getKioskCustActivitiesDtlReq"),
                                               xmlWriter);
                                        
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd")){
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

                
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd",
                                                                      "header"));
                            
                            
                                    if (localHeader==null){
                                         throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
                                    }
                                    elementList.add(localHeader);
                                
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "getKioskCustActivitiesDtlReq"));
                            
                            
                                    if (localGetKioskCustActivitiesDtlReq==null){
                                         throw new org.apache.axis2.databinding.ADBException("getKioskCustActivitiesDtlReq cannot be null!!");
                                    }
                                    elementList.add(localGetKioskCustActivitiesDtlReq);
                                

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
        public static GetKioskCustActivitiesDtlReqMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            GetKioskCustActivitiesDtlReqMsg object =
                new GetKioskCustActivitiesDtlReqMsg();

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
                    
                            if (!"getKioskCustActivitiesDtlReqMsg".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (GetKioskCustActivitiesDtlReqMsg)ExtensionMapper.getTypeObject(
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","getKioskCustActivitiesDtlReq").equals(reader.getName())){
                                
                                                object.setGetKioskCustActivitiesDtlReq(GetKioskCustActivitiesDtlReq_type0.Factory.parse(reader));
                                              
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
           
    
        public static class Chqueueleavesdetails_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = Chqueueleavesdetails_type0
                Namespace URI = http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd
                Namespace Prefix = ns2
                */
            

                        /**
                        * field for Chqueueleaves
                        * This was an Array!
                        */

                        
                                    protected Chqueueleaves_type0[] localChqueueleaves ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localChqueueleavesTracker = false ;

                           public boolean isChqueueleavesSpecified(){
                               return localChqueueleavesTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return Chqueueleaves_type0[]
                           */
                           public  Chqueueleaves_type0[] getChqueueleaves(){
                               return localChqueueleaves;
                           }

                           
                        


                               
                              /**
                               * validate the array for Chqueueleaves
                               */
                              protected void validateChqueueleaves(Chqueueleaves_type0[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param Chqueueleaves
                              */
                              public void setChqueueleaves(Chqueueleaves_type0[] param){
                              
                                   validateChqueueleaves(param);

                               localChqueueleavesTracker = param != null;
                                      
                                      this.localChqueueleaves=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param Chqueueleaves_type0
                             */
                             public void addChqueueleaves(Chqueueleaves_type0 param){
                                   if (localChqueueleaves == null){
                                   localChqueueleaves = new Chqueueleaves_type0[]{};
                                   }

                            
                                 //update the setting tracker
                                localChqueueleavesTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localChqueueleaves);
                               list.add(param);
                               this.localChqueueleaves =
                             (Chqueueleaves_type0[])list.toArray(
                            new Chqueueleaves_type0[list.size()]);

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":Chqueueleavesdetails_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "Chqueueleavesdetails_type0",
                           xmlWriter);
                   }

               
                   }
                if (localChqueueleavesTracker){
                                       if (localChqueueleaves!=null){
                                            for (int i = 0;i < localChqueueleaves.length;i++){
                                                if (localChqueueleaves[i] != null){
                                                 localChqueueleaves[i].serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","Chqueueleaves"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("Chqueueleaves cannot be null!!");
                                        
                                    }
                                 }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd")){
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

                 if (localChqueueleavesTracker){
                             if (localChqueueleaves!=null) {
                                 for (int i = 0;i < localChqueueleaves.length;i++){

                                    if (localChqueueleaves[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                          "Chqueueleaves"));
                                         elementList.add(localChqueueleaves[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("Chqueueleaves cannot be null!!");
                                    
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
        public static Chqueueleavesdetails_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            Chqueueleavesdetails_type0 object =
                new Chqueueleavesdetails_type0();

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
                    
                            if (!"Chqueueleavesdetails_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (Chqueueleavesdetails_type0)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                        java.util.ArrayList list1 = new java.util.ArrayList();
                    
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","Chqueueleaves").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list1.add(Chqueueleaves_type0.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone1 = false;
                                                        while(!loopDone1){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone1 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","Chqueueleaves").equals(reader.getName())){
                                                                    list1.add(Chqueueleaves_type0.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone1 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setChqueueleaves((Chqueueleaves_type0[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                Chqueueleaves_type0.class,
                                                                list1));
                                                            
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
                "ns1");

            

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
                return "ns1";
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
           
    
        public static class GetKioskCustActivitiesDtlRes_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = getKioskCustActivitiesDtlRes_type0
                Namespace URI = http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd
                Namespace Prefix = ns2
                */
            

                        /**
                        * field for CustomerId
                        */

                        
                                    protected java.lang.String localCustomerId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerIdTracker = false ;

                           public boolean isCustomerIdSpecified(){
                               return localCustomerIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerId(){
                               return localCustomerId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerId
                               */
                               public void setCustomerId(java.lang.String param){
                            localCustomerIdTracker = param != null;
                                   
                                            this.localCustomerId=param;
                                    

                               }
                            

                        /**
                        * field for Activities
                        * This was an Array!
                        */

                        
                                    protected Activities_type0[] localActivities ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localActivitiesTracker = false ;

                           public boolean isActivitiesSpecified(){
                               return localActivitiesTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return Activities_type0[]
                           */
                           public  Activities_type0[] getActivities(){
                               return localActivities;
                           }

                           
                        


                               
                              /**
                               * validate the array for Activities
                               */
                              protected void validateActivities(Activities_type0[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param Activities
                              */
                              public void setActivities(Activities_type0[] param){
                              
                                   validateActivities(param);

                               localActivitiesTracker = param != null;
                                      
                                      this.localActivities=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param Activities_type0
                             */
                             public void addActivities(Activities_type0 param){
                                   if (localActivities == null){
                                   localActivities = new Activities_type0[]{};
                                   }

                            
                                 //update the setting tracker
                                localActivitiesTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localActivities);
                               list.add(param);
                               this.localActivities =
                             (Activities_type0[])list.toArray(
                            new Activities_type0[list.size()]);

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":getKioskCustActivitiesDtlRes_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "getKioskCustActivitiesDtlRes_type0",
                           xmlWriter);
                   }

               
                   }
                if (localCustomerIdTracker){
                                    namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd";
                                    writeStartElement(null, namespace, "customerId", xmlWriter);
                             

                                          if (localCustomerId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localActivitiesTracker){
                                       if (localActivities!=null){
                                            for (int i = 0;i < localActivities.length;i++){
                                                if (localActivities[i] != null){
                                                 localActivities[i].serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","activities"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("activities cannot be null!!");
                                        
                                    }
                                 }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd")){
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

                 if (localCustomerIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                      "customerId"));
                                 
                                        if (localCustomerId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");
                                        }
                                    } if (localActivitiesTracker){
                             if (localActivities!=null) {
                                 for (int i = 0;i < localActivities.length;i++){

                                    if (localActivities[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd",
                                                                          "activities"));
                                         elementList.add(localActivities[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("activities cannot be null!!");
                                    
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
        public static GetKioskCustActivitiesDtlRes_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            GetKioskCustActivitiesDtlRes_type0 object =
                new GetKioskCustActivitiesDtlRes_type0();

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
                    
                            if (!"getKioskCustActivitiesDtlRes_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (GetKioskCustActivitiesDtlRes_type0)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                        java.util.ArrayList list2 = new java.util.ArrayList();
                    
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","customerId").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","activities").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list2.add(Activities_type0.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone2 = false;
                                                        while(!loopDone2){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone2 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKCustomer.xsd","activities").equals(reader.getName())){
                                                                    list2.add(Activities_type0.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone2 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setActivities((Activities_type0[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                Activities_type0.class,
                                                                list2));
                                                            
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
           
    
            private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsReqMsg param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.newgen.dscop.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsReqMsg.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsResMsg param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.newgen.dscop.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsResMsg.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoReqMsg param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoReqMsg.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoResMsg param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoResMsg.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqSBKCustomerStub.GetCustomerInfoReqMsg param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.newgen.dscop.stub.InqSBKCustomerStub.GetCustomerInfoReqMsg.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqSBKCustomerStub.GetCustomerInfoResMsg param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.newgen.dscop.stub.InqSBKCustomerStub.GetCustomerInfoResMsg.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlReqMsg param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlReqMsg.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlResMsg param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlResMsg.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.newgen.dscop.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsReqMsg param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.newgen.dscop.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsReqMsg.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoReqMsg param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoReqMsg.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.newgen.dscop.stub.InqSBKCustomerStub.GetCustomerInfoReqMsg param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.newgen.dscop.stub.InqSBKCustomerStub.GetCustomerInfoReqMsg.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlReqMsg param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlReqMsg.MY_QNAME,factory));
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
        
                if (com.newgen.dscop.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsReqMsg.class.equals(type)){
                
                           return com.newgen.dscop.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsReqMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.newgen.dscop.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsResMsg.class.equals(type)){
                
                           return com.newgen.dscop.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsResMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoReqMsg.class.equals(type)){
                
                           return com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoReqMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoResMsg.class.equals(type)){
                
                           return com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoResMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.newgen.dscop.stub.InqSBKCustomerStub.GetCustomerInfoReqMsg.class.equals(type)){
                
                           return com.newgen.dscop.stub.InqSBKCustomerStub.GetCustomerInfoReqMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.newgen.dscop.stub.InqSBKCustomerStub.GetCustomerInfoResMsg.class.equals(type)){
                
                           return com.newgen.dscop.stub.InqSBKCustomerStub.GetCustomerInfoResMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlReqMsg.class.equals(type)){
                
                           return com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlReqMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlResMsg.class.equals(type)){
                
                           return com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlResMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    
   }
   