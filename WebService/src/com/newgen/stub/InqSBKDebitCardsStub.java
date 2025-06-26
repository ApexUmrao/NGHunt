
/**
 * InqSBKDebitCardsStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:22:40 CEST)
 */
package com.newgen.stub;



/*
 *  InqSBKDebitCardsStub java implementation
 */


public class InqSBKDebitCardsStub extends org.apache.axis2.client.Stub
{
	protected org.apache.axis2.description.AxisOperation[] _operations;

	//hashmaps to keep the fault mapping
	private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
	private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
	private java.util.HashMap faultMessageMap = new java.util.HashMap();
	
    public String resMsgDebitCard = null;
    public String resMsgKiosk = null;
    
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
		_service = new org.apache.axis2.description.AxisService("InqSBKDebitCards" + getUniqueSuffix());
		addAnonymousOperations();

		//creating the operations
		org.apache.axis2.description.AxisOperation __operation;

		_operations = new org.apache.axis2.description.AxisOperation[2];

		__operation = new org.apache.axis2.description.OutInAxisOperation();


		__operation.setName(new javax.xml.namespace.QName("http://xmlns.example.com/1447321201540", "getDebitCardPrintStatus_Oper"));
		_service.addOperation(__operation);




		_operations[0]=__operation;


		__operation = new org.apache.axis2.description.OutInAxisOperation();


		__operation.setName(new javax.xml.namespace.QName("http://xmlns.example.com/1447321201540", "getKioskList_Oper"));
		_service.addOperation(__operation);




		_operations[1]=__operation;


	}

	//populates the faults
	private void populateFaults(){



	}

	/**
	 *Constructor that takes in a configContext
	 */

	public InqSBKDebitCardsStub(org.apache.axis2.context.ConfigurationContext configurationContext,
			java.lang.String targetEndpoint)
	throws org.apache.axis2.AxisFault {
		this(configurationContext,targetEndpoint,false);
	}


	/**
	 * Constructor that takes in a configContext  and useseperate listner
	 */
	public InqSBKDebitCardsStub(org.apache.axis2.context.ConfigurationContext configurationContext,
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
	public InqSBKDebitCardsStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {

		this(configurationContext,"http://10.146.163.71:5536/Services/SmartKioskServices/SmartKioskInquiryServices/Service/InqSBKDebitCards.serviceagent/InqSBKDebitCardsPortTypeEndpoint0" );

	}

	/**
	 * Default Constructor
	 */
	public InqSBKDebitCardsStub() throws org.apache.axis2.AxisFault {

		this("http://10.146.163.71:5536/Services/SmartKioskServices/SmartKioskInquiryServices/Service/InqSBKDebitCards.serviceagent/InqSBKDebitCardsPortTypeEndpoint0" );

	}

	/**
	 * Constructor taking the target endpoint
	 */
	public InqSBKDebitCardsStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
		this(null,targetEndpoint);
	}
	
	//Added by Nishant Parmar for getting input XML (25-01-2016):
	public String getInputXml_DebitCard(
			com.newgen.stub.InqSBKDebitCardsStub.GetDebitCardPrintStatusReqMsg getDebitCardPrintStatusReqMsg0) 
	throws java.rmi.RemoteException{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try{
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
			_operationClient.getOptions().setAction("/Services/SmartKioskServices/SmartKioskInquiryServices/Service/InqSBKDebitCards.serviceagent/InqSBKDebitCardsPortTypeEndpoint0/getDebitCardPrintStatus_Oper");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



			addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");


			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();



			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;


			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
					getDebitCardPrintStatusReqMsg0,
					optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1447321201540",
					"getDebitCardPrintStatus_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1447321201540",
					"getDebitCardPrintStatus_Oper"));

			//adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

/*			// add the message contxt to the operation client
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
			 * @see com.newgen.stub.InqSBKDebitCards#getDebitCardPrintStatus_Oper
			 * @param getDebitCardPrintStatusReqMsg0

			 */



			public  com.newgen.stub.InqSBKDebitCardsStub.GetDebitCardPrintStatusResMsg getDebitCardPrintStatus_Oper(

					com.newgen.stub.InqSBKDebitCardsStub.GetDebitCardPrintStatusReqMsg getDebitCardPrintStatusReqMsg0)


			throws java.rmi.RemoteException

			{
				org.apache.axis2.context.MessageContext _messageContext = null;
				try{
					org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
					_operationClient.getOptions().setAction("/Services/SmartKioskServices/SmartKioskInquiryServices/Service/InqSBKDebitCards.serviceagent/InqSBKDebitCardsPortTypeEndpoint0/getDebitCardPrintStatus_Oper");
					_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



					addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");


					// create a message context
					_messageContext = new org.apache.axis2.context.MessageContext();



					// create SOAP envelope with that payload
					org.apache.axiom.soap.SOAPEnvelope env = null;


					env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
							getDebitCardPrintStatusReqMsg0,
							optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1447321201540",
							"getDebitCardPrintStatus_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1447321201540",
							"getDebitCardPrintStatus_Oper"));

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
					//add here output [Nishant Parmar | 25-01-2016]: 
					resMsgDebitCard = _returnEnv.toString();
					
					java.lang.Object object = fromOM(
							_returnEnv.getBody().getFirstElement() ,
							com.newgen.stub.InqSBKDebitCardsStub.GetDebitCardPrintStatusResMsg.class,
							getEnvelopeNamespaces(_returnEnv));


					return (com.newgen.stub.InqSBKDebitCardsStub.GetDebitCardPrintStatusResMsg)object;

				}catch(org.apache.axis2.AxisFault f){

					org.apache.axiom.om.OMElement faultElt = f.getDetail();
					if (faultElt!=null){
						if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getDebitCardPrintStatus_Oper"))){
							//make the fault by reflection
							try{
								java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getDebitCardPrintStatus_Oper"));
								java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
								java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
								//message class
								java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getDebitCardPrintStatus_Oper"));
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
			 * @see com.newgen.stub.InqSBKDebitCards#startgetDebitCardPrintStatus_Oper
			 * @param getDebitCardPrintStatusReqMsg0

			 */
			public  void startgetDebitCardPrintStatus_Oper(

					com.newgen.stub.InqSBKDebitCardsStub.GetDebitCardPrintStatusReqMsg getDebitCardPrintStatusReqMsg0,

					final com.newgen.stub.InqSBKDebitCardsCallbackHandler callback)

			throws java.rmi.RemoteException{

				org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
				_operationClient.getOptions().setAction("/Services/SmartKioskServices/SmartKioskInquiryServices/Service/InqSBKDebitCards.serviceagent/InqSBKDebitCardsPortTypeEndpoint0/getDebitCardPrintStatus_Oper");
				_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



				addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");



				// create SOAP envelope with that payload
				org.apache.axiom.soap.SOAPEnvelope env=null;
				final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();


				//Style is Doc.


				env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
						getDebitCardPrintStatusReqMsg0,
						optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1447321201540",
						"getDebitCardPrintStatus_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1447321201540",
						"getDebitCardPrintStatus_Oper"));

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
									com.newgen.stub.InqSBKDebitCardsStub.GetDebitCardPrintStatusResMsg.class,
									getEnvelopeNamespaces(resultEnv));
							callback.receiveResultgetDebitCardPrintStatus_Oper(
									(com.newgen.stub.InqSBKDebitCardsStub.GetDebitCardPrintStatusResMsg)object);

						} catch (org.apache.axis2.AxisFault e) {
							callback.receiveErrorgetDebitCardPrintStatus_Oper(e);
						}
					}

					public void onError(java.lang.Exception error) {
						if (error instanceof org.apache.axis2.AxisFault) {
							org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
							org.apache.axiom.om.OMElement faultElt = f.getDetail();
							if (faultElt!=null){
								if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getDebitCardPrintStatus_Oper"))){
									//make the fault by reflection
									try{
										java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getDebitCardPrintStatus_Oper"));
										java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
										java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
										//message class
										java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getDebitCardPrintStatus_Oper"));
										java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
										java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
										java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
												new java.lang.Class[]{messageClass});
										m.invoke(ex,new java.lang.Object[]{messageObject});


										callback.receiveErrorgetDebitCardPrintStatus_Oper(new java.rmi.RemoteException(ex.getMessage(), ex));
									} catch(java.lang.ClassCastException e){
										// we cannot intantiate the class - throw the original Axis fault
										callback.receiveErrorgetDebitCardPrintStatus_Oper(f);
									} catch (java.lang.ClassNotFoundException e) {
										// we cannot intantiate the class - throw the original Axis fault
										callback.receiveErrorgetDebitCardPrintStatus_Oper(f);
									} catch (java.lang.NoSuchMethodException e) {
										// we cannot intantiate the class - throw the original Axis fault
										callback.receiveErrorgetDebitCardPrintStatus_Oper(f);
									} catch (java.lang.reflect.InvocationTargetException e) {
										// we cannot intantiate the class - throw the original Axis fault
										callback.receiveErrorgetDebitCardPrintStatus_Oper(f);
									} catch (java.lang.IllegalAccessException e) {
										// we cannot intantiate the class - throw the original Axis fault
										callback.receiveErrorgetDebitCardPrintStatus_Oper(f);
									} catch (java.lang.InstantiationException e) {
										// we cannot intantiate the class - throw the original Axis fault
										callback.receiveErrorgetDebitCardPrintStatus_Oper(f);
									} catch (org.apache.axis2.AxisFault e) {
										// we cannot intantiate the class - throw the original Axis fault
										callback.receiveErrorgetDebitCardPrintStatus_Oper(f);
									}
								} else {
									callback.receiveErrorgetDebitCardPrintStatus_Oper(f);
								}
							} else {
								callback.receiveErrorgetDebitCardPrintStatus_Oper(f);
							}
						} else {
							callback.receiveErrorgetDebitCardPrintStatus_Oper(error);
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
							callback.receiveErrorgetDebitCardPrintStatus_Oper(axisFault);
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

			//Added by Nishant Parmar for getting input XML (25-01-2016):
			public String getInputXml_Kiosk(
					com.newgen.stub.InqSBKDebitCardsStub.GetKioskListReqMsg getKioskListReqMsgMsg0) 
			throws java.rmi.RemoteException{
				org.apache.axis2.context.MessageContext _messageContext = null;
				try{
					org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
					_operationClient.getOptions().setAction("/Services/SmartKioskServices/SmartKioskInquiryServices/Service/InqSBKDebitCards.serviceagent/InqSBKDebitCardsPortTypeEndpoint0/getKioskList_Oper");
					_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



					addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");


					// create a message context
					_messageContext = new org.apache.axis2.context.MessageContext();



					// create SOAP envelope with that payload
					org.apache.axiom.soap.SOAPEnvelope env = null;


					env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
							getKioskListReqMsgMsg0,
							optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1447321201540",
							"getKioskList_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1447321201540",
							"getKioskList_Oper"));

					//adding SOAP soap_headers
					_serviceClient.addHeadersToEnvelope(env);
					// set the message context with that soap envelope
					_messageContext.setEnvelope(env);

/*					// add the message contxt to the operation client
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
			 * @see com.newgen.stub.InqSBKDebitCards#getKioskList_Oper
			 * @param getKioskListReqMsg2

			 */



			public  com.newgen.stub.InqSBKDebitCardsStub.GetKioskListResMsg getKioskList_Oper(

					com.newgen.stub.InqSBKDebitCardsStub.GetKioskListReqMsg getKioskListReqMsg2)


			throws java.rmi.RemoteException

			{
				org.apache.axis2.context.MessageContext _messageContext = null;
				try{
					org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
					_operationClient.getOptions().setAction("/Services/SmartKioskServices/SmartKioskInquiryServices/Service/InqSBKDebitCards.serviceagent/InqSBKDebitCardsPortTypeEndpoint0/getKioskList_Oper");
					_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



					addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");


					// create a message context
					_messageContext = new org.apache.axis2.context.MessageContext();



					// create SOAP envelope with that payload
					org.apache.axiom.soap.SOAPEnvelope env = null;


					env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
							getKioskListReqMsg2,
							optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1447321201540",
							"getKioskList_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1447321201540",
							"getKioskList_Oper"));

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
					//add here output [Nishant Parmar | 25-01-2016]: 
					resMsgKiosk = _returnEnv.toString();
					
					java.lang.Object object = fromOM(
							_returnEnv.getBody().getFirstElement() ,
							com.newgen.stub.InqSBKDebitCardsStub.GetKioskListResMsg.class,
							getEnvelopeNamespaces(_returnEnv));


					return (com.newgen.stub.InqSBKDebitCardsStub.GetKioskListResMsg)object;

				}catch(org.apache.axis2.AxisFault f){

					org.apache.axiom.om.OMElement faultElt = f.getDetail();
					if (faultElt!=null){
						if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getKioskList_Oper"))){
							//make the fault by reflection
							try{
								java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getKioskList_Oper"));
								java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
								java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
								//message class
								java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getKioskList_Oper"));
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
			 * @see com.newgen.stub.InqSBKDebitCards#startgetKioskList_Oper
			 * @param getKioskListReqMsg2

			 */
			public  void startgetKioskList_Oper(

					com.newgen.stub.InqSBKDebitCardsStub.GetKioskListReqMsg getKioskListReqMsg2,

					final com.newgen.stub.InqSBKDebitCardsCallbackHandler callback)

			throws java.rmi.RemoteException{

				org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
				_operationClient.getOptions().setAction("/Services/SmartKioskServices/SmartKioskInquiryServices/Service/InqSBKDebitCards.serviceagent/InqSBKDebitCardsPortTypeEndpoint0/getKioskList_Oper");
				_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



				addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");



				// create SOAP envelope with that payload
				org.apache.axiom.soap.SOAPEnvelope env=null;
				final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();


				//Style is Doc.


				env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
						getKioskListReqMsg2,
						optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1447321201540",
						"getKioskList_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1447321201540",
						"getKioskList_Oper"));

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
									com.newgen.stub.InqSBKDebitCardsStub.GetKioskListResMsg.class,
									getEnvelopeNamespaces(resultEnv));
							callback.receiveResultgetKioskList_Oper(
									(com.newgen.stub.InqSBKDebitCardsStub.GetKioskListResMsg)object);

						} catch (org.apache.axis2.AxisFault e) {
							callback.receiveErrorgetKioskList_Oper(e);
						}
					}

					public void onError(java.lang.Exception error) {
						if (error instanceof org.apache.axis2.AxisFault) {
							org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
							org.apache.axiom.om.OMElement faultElt = f.getDetail();
							if (faultElt!=null){
								if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getKioskList_Oper"))){
									//make the fault by reflection
									try{
										java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getKioskList_Oper"));
										java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
										java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
										//message class
										java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"getKioskList_Oper"));
										java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
										java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
										java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
												new java.lang.Class[]{messageClass});
										m.invoke(ex,new java.lang.Object[]{messageObject});


										callback.receiveErrorgetKioskList_Oper(new java.rmi.RemoteException(ex.getMessage(), ex));
									} catch(java.lang.ClassCastException e){
										// we cannot intantiate the class - throw the original Axis fault
										callback.receiveErrorgetKioskList_Oper(f);
									} catch (java.lang.ClassNotFoundException e) {
										// we cannot intantiate the class - throw the original Axis fault
										callback.receiveErrorgetKioskList_Oper(f);
									} catch (java.lang.NoSuchMethodException e) {
										// we cannot intantiate the class - throw the original Axis fault
										callback.receiveErrorgetKioskList_Oper(f);
									} catch (java.lang.reflect.InvocationTargetException e) {
										// we cannot intantiate the class - throw the original Axis fault
										callback.receiveErrorgetKioskList_Oper(f);
									} catch (java.lang.IllegalAccessException e) {
										// we cannot intantiate the class - throw the original Axis fault
										callback.receiveErrorgetKioskList_Oper(f);
									} catch (java.lang.InstantiationException e) {
										// we cannot intantiate the class - throw the original Axis fault
										callback.receiveErrorgetKioskList_Oper(f);
									} catch (org.apache.axis2.AxisFault e) {
										// we cannot intantiate the class - throw the original Axis fault
										callback.receiveErrorgetKioskList_Oper(f);
									}
								} else {
									callback.receiveErrorgetKioskList_Oper(f);
								}
							} else {
								callback.receiveErrorgetKioskList_Oper(f);
							}
						} else {
							callback.receiveErrorgetKioskList_Oper(error);
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
							callback.receiveErrorgetKioskList_Oper(axisFault);
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
			//http://10.146.163.71:5536/Services/SmartKioskServices/SmartKioskInquiryServices/Service/InqSBKDebitCards.serviceagent/InqSBKDebitCardsPortTypeEndpoint0
			public static class GetKioskListReq_type0
			implements org.apache.axis2.databinding.ADBBean{
				/* This type was generated from the piece of schema that had
                name = getKioskListReq_type0
                Namespace URI = http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd
                Namespace Prefix = ns3
				 */


				/**
				 * field for CardProductGroup
				 */


				protected java.lang.String localCardProductGroup ;

				/*  This tracker boolean wil be used to detect whether the user called the set method
				 *   for this attribute. It will be used to determine whether to include this field
				 *   in the serialized XML
				 */
				protected boolean localCardProductGroupTracker = false ;

				public boolean isCardProductGroupSpecified(){
					return localCardProductGroupTracker;
				}



				/**
				 * Auto generated getter method
				 * @return java.lang.String
				 */
				public  java.lang.String getCardProductGroup(){
					return localCardProductGroup;
				}



				/**
				 * Auto generated setter method
				 * @param param CardProductGroup
				 */
				public void setCardProductGroup(java.lang.String param){
					localCardProductGroupTracker = param != null;

					this.localCardProductGroup=param;


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


						java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd");
						if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
							writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
									namespacePrefix+":getKioskListReq_type0",
									xmlWriter);
						} else {
							writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
									"getKioskListReq_type0",
									xmlWriter);
						}


					}
					if (localCardProductGroupTracker){
						namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd";
						writeStartElement(null, namespace, "cardProductGroup", xmlWriter);


						if (localCardProductGroup==null){
							// write the nil attribute

							throw new org.apache.axis2.databinding.ADBException("cardProductGroup cannot be null!!");

						}else{


							xmlWriter.writeCharacters(localCardProductGroup);

						}

						xmlWriter.writeEndElement();
					} if (localCustomerCategoryTracker){
						namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd";
						writeStartElement(null, namespace, "customerCategory", xmlWriter);


						if (localCustomerCategory==null){
							// write the nil attribute

							throw new org.apache.axis2.databinding.ADBException("customerCategory cannot be null!!");

						}else{


							xmlWriter.writeCharacters(localCustomerCategory);

						}

						xmlWriter.writeEndElement();
					}
					xmlWriter.writeEndElement();


				}

				private static java.lang.String generatePrefix(java.lang.String namespace) {
					if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd")){
						return "ns3";
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

					if (localCardProductGroupTracker){
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"cardProductGroup"));

						if (localCardProductGroup != null){
							elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCardProductGroup));
						} else {
							throw new org.apache.axis2.databinding.ADBException("cardProductGroup cannot be null!!");
						}
					} if (localCustomerCategoryTracker){
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"customerCategory"));

						if (localCustomerCategory != null){
							elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerCategory));
						} else {
							throw new org.apache.axis2.databinding.ADBException("customerCategory cannot be null!!");
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
					public static GetKioskListReq_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
						GetKioskListReq_type0 object =
							new GetKioskListReq_type0();

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

									if (!"getKioskListReq_type0".equals(type)){
										//find namespace for the prefix
										java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
										return (GetKioskListReq_type0)ExtensionMapper.getTypeObject(
												nsUri,type,reader);
									}


								}


							}




							// Note all attributes that were handled. Used to differ normal attributes
							// from anyAttributes.
							java.util.Vector handledAttributes = new java.util.Vector();




							reader.next();


							while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","cardProductGroup").equals(reader.getName())){

								java.lang.String content = reader.getElementText();

								object.setCardProductGroup(
										org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

								reader.next();

							}  // End of if for expected property start element

							else {

							}


							while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","customerCategory").equals(reader.getName())){

								java.lang.String content = reader.getElementText();

								object.setCustomerCategory(
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


			public static class DebitCardDetails_type0
			implements org.apache.axis2.databinding.ADBBean{
				/* This type was generated from the piece of schema that had
                name = debitCardDetails_type0
                Namespace URI = http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd
                Namespace Prefix = ns3
				 */


				/**
				 * field for DebitCards
				 * This was an Array!
				 */


				protected DebitCards_type0[] localDebitCards ;

				/*  This tracker boolean wil be used to detect whether the user called the set method
				 *   for this attribute. It will be used to determine whether to include this field
				 *   in the serialized XML
				 */
				protected boolean localDebitCardsTracker = false ;

				public boolean isDebitCardsSpecified(){
					return localDebitCardsTracker;
				}



				/**
				 * Auto generated getter method
				 * @return DebitCards_type0[]
				 */
				public  DebitCards_type0[] getDebitCards(){
					return localDebitCards;
				}






				/**
				 * validate the array for DebitCards
				 */
				protected void validateDebitCards(DebitCards_type0[] param){

				}


				/**
				 * Auto generated setter method
				 * @param param DebitCards
				 */
				public void setDebitCards(DebitCards_type0[] param){

					validateDebitCards(param);

					localDebitCardsTracker = param != null;

					this.localDebitCards=param;
				}



				/**
				 * Auto generated add method for the array for convenience
				 * @param param DebitCards_type0
				 */
				public void addDebitCards(DebitCards_type0 param){
					if (localDebitCards == null){
						localDebitCards = new DebitCards_type0[]{};
					}


					//update the setting tracker
					localDebitCardsTracker = true;


					java.util.List list =
						org.apache.axis2.databinding.utils.ConverterUtil.toList(localDebitCards);
					list.add(param);
					this.localDebitCards =
						(DebitCards_type0[])list.toArray(
								new DebitCards_type0[list.size()]);

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


						java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd");
						if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
							writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
									namespacePrefix+":debitCardDetails_type0",
									xmlWriter);
						} else {
							writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
									"debitCardDetails_type0",
									xmlWriter);
						}


					}
					if (localDebitCardsTracker){
						if (localDebitCards!=null){
							for (int i = 0;i < localDebitCards.length;i++){
								if (localDebitCards[i] != null){
									localDebitCards[i].serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","debitCards"),
											xmlWriter);
								} else {

									// we don't have to do any thing since minOccures is zero

								}

							}
						} else {

							throw new org.apache.axis2.databinding.ADBException("debitCards cannot be null!!");

						}
					}
					xmlWriter.writeEndElement();


				}

				private static java.lang.String generatePrefix(java.lang.String namespace) {
					if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd")){
						return "ns3";
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

					if (localDebitCardsTracker){
						if (localDebitCards!=null) {
							for (int i = 0;i < localDebitCards.length;i++){

								if (localDebitCards[i] != null){
									elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
									"debitCards"));
									elementList.add(localDebitCards[i]);
								} else {

									// nothing to do

								}

							}
						} else {

							throw new org.apache.axis2.databinding.ADBException("debitCards cannot be null!!");

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
					public static DebitCardDetails_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
						DebitCardDetails_type0 object =
							new DebitCardDetails_type0();

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

									if (!"debitCardDetails_type0".equals(type)){
										//find namespace for the prefix
										java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
										return (DebitCardDetails_type0)ExtensionMapper.getTypeObject(
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

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","debitCards").equals(reader.getName())){



								// Process the array and step past its final element's end.
								list1.add(DebitCards_type0.Factory.parse(reader));

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
										if (new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","debitCards").equals(reader.getName())){
											list1.add(DebitCards_type0.Factory.parse(reader));

										}else{
											loopDone1 = true;
										}
									}
								}
								// call the converter utility  to convert and set the array

								object.setDebitCards((DebitCards_type0[])
										org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
												DebitCards_type0.class,
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


			public static class GetKioskListReqMsg
			implements org.apache.axis2.databinding.ADBBean{

				public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
						"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"getKioskListReqMsg",
				"ns3");



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
				 * field for GetKioskListReq
				 */


				protected GetKioskListReq_type0 localGetKioskListReq ;


				/**
				 * Auto generated getter method
				 * @return GetKioskListReq_type0
				 */
				public  GetKioskListReq_type0 getGetKioskListReq(){
					return localGetKioskListReq;
				}



				/**
				 * Auto generated setter method
				 * @param param GetKioskListReq
				 */
				public void setGetKioskListReq(GetKioskListReq_type0 param){

					this.localGetKioskListReq=param;


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


						java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd");
						if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
							writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
									namespacePrefix+":getKioskListReqMsg",
									xmlWriter);
						} else {
							writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
									"getKioskListReqMsg",
									xmlWriter);
						}


					}

					if (localHeader==null){
						throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
					}
					localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
							xmlWriter);

					if (localGetKioskListReq==null){
						throw new org.apache.axis2.databinding.ADBException("getKioskListReq cannot be null!!");
					}
					localGetKioskListReq.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","getKioskListReq"),
							xmlWriter);

					xmlWriter.writeEndElement();


				}

				private static java.lang.String generatePrefix(java.lang.String namespace) {
					if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd")){
						return "ns3";
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

					elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
					"getKioskListReq"));


					if (localGetKioskListReq==null){
						throw new org.apache.axis2.databinding.ADBException("getKioskListReq cannot be null!!");
					}
					elementList.add(localGetKioskListReq);


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
					public static GetKioskListReqMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
						GetKioskListReqMsg object =
							new GetKioskListReqMsg();

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

									if (!"getKioskListReqMsg".equals(type)){
										//find namespace for the prefix
										java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
										return (GetKioskListReqMsg)ExtensionMapper.getTypeObject(
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

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","getKioskListReq").equals(reader.getName())){

								object.setGetKioskListReq(GetKioskListReq_type0.Factory.parse(reader));

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


			public static class GetKioskListRes_type0
			implements org.apache.axis2.databinding.ADBBean{
				/* This type was generated from the piece of schema that had
                name = getKioskListRes_type0
                Namespace URI = http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd
                Namespace Prefix = ns3
				 */


				/**
				 * field for CardProductGroup
				 */


				protected java.lang.String localCardProductGroup ;

				/*  This tracker boolean wil be used to detect whether the user called the set method
				 *   for this attribute. It will be used to determine whether to include this field
				 *   in the serialized XML
				 */
				protected boolean localCardProductGroupTracker = false ;

				public boolean isCardProductGroupSpecified(){
					return localCardProductGroupTracker;
				}



				/**
				 * Auto generated getter method
				 * @return java.lang.String
				 */
				public  java.lang.String getCardProductGroup(){
					return localCardProductGroup;
				}



				/**
				 * Auto generated setter method
				 * @param param CardProductGroup
				 */
				public void setCardProductGroup(java.lang.String param){
					localCardProductGroupTracker = param != null;

					this.localCardProductGroup=param;


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
				 * field for KioskDetails
				 */


				protected KioskDetails_type0 localKioskDetails ;

				/*  This tracker boolean wil be used to detect whether the user called the set method
				 *   for this attribute. It will be used to determine whether to include this field
				 *   in the serialized XML
				 */
				protected boolean localKioskDetailsTracker = false ;

				public boolean isKioskDetailsSpecified(){
					return localKioskDetailsTracker;
				}



				/**
				 * Auto generated getter method
				 * @return KioskDetails_type0
				 */
				public  KioskDetails_type0 getKioskDetails(){
					return localKioskDetails;
				}



				/**
				 * Auto generated setter method
				 * @param param KioskDetails
				 */
				public void setKioskDetails(KioskDetails_type0 param){
					localKioskDetailsTracker = param != null;

					this.localKioskDetails=param;


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


						java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd");
						if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
							writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
									namespacePrefix+":getKioskListRes_type0",
									xmlWriter);
						} else {
							writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
									"getKioskListRes_type0",
									xmlWriter);
						}


					}
					if (localCardProductGroupTracker){
						namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd";
						writeStartElement(null, namespace, "cardProductGroup", xmlWriter);


						if (localCardProductGroup==null){
							// write the nil attribute

							throw new org.apache.axis2.databinding.ADBException("cardProductGroup cannot be null!!");

						}else{


							xmlWriter.writeCharacters(localCardProductGroup);

						}

						xmlWriter.writeEndElement();
					} if (localCustomerCategoryTracker){
						namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd";
						writeStartElement(null, namespace, "customerCategory", xmlWriter);


						if (localCustomerCategory==null){
							// write the nil attribute

							throw new org.apache.axis2.databinding.ADBException("customerCategory cannot be null!!");

						}else{


							xmlWriter.writeCharacters(localCustomerCategory);

						}

						xmlWriter.writeEndElement();
					} if (localKioskDetailsTracker){
						if (localKioskDetails==null){
							throw new org.apache.axis2.databinding.ADBException("kioskDetails cannot be null!!");
						}
						localKioskDetails.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","kioskDetails"),
								xmlWriter);
					}
					xmlWriter.writeEndElement();


				}

				private static java.lang.String generatePrefix(java.lang.String namespace) {
					if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd")){
						return "ns3";
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

					if (localCardProductGroupTracker){
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"cardProductGroup"));

						if (localCardProductGroup != null){
							elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCardProductGroup));
						} else {
							throw new org.apache.axis2.databinding.ADBException("cardProductGroup cannot be null!!");
						}
					} if (localCustomerCategoryTracker){
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"customerCategory"));

						if (localCustomerCategory != null){
							elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerCategory));
						} else {
							throw new org.apache.axis2.databinding.ADBException("customerCategory cannot be null!!");
						}
					} if (localKioskDetailsTracker){
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"kioskDetails"));


						if (localKioskDetails==null){
							throw new org.apache.axis2.databinding.ADBException("kioskDetails cannot be null!!");
						}
						elementList.add(localKioskDetails);
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
					public static GetKioskListRes_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
						GetKioskListRes_type0 object =
							new GetKioskListRes_type0();

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

									if (!"getKioskListRes_type0".equals(type)){
										//find namespace for the prefix
										java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
										return (GetKioskListRes_type0)ExtensionMapper.getTypeObject(
												nsUri,type,reader);
									}


								}


							}




							// Note all attributes that were handled. Used to differ normal attributes
							// from anyAttributes.
							java.util.Vector handledAttributes = new java.util.Vector();




							reader.next();


							while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","cardProductGroup").equals(reader.getName())){

								java.lang.String content = reader.getElementText();

								object.setCardProductGroup(
										org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

								reader.next();

							}  // End of if for expected property start element

							else {

							}


							while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","customerCategory").equals(reader.getName())){

								java.lang.String content = reader.getElementText();

								object.setCustomerCategory(
										org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

								reader.next();

							}  // End of if for expected property start element

							else {

							}


							while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","kioskDetails").equals(reader.getName())){

								object.setKioskDetails(KioskDetails_type0.Factory.parse(reader));

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


			public static class GetDebitCardPrintStatusResMsg
			implements org.apache.axis2.databinding.ADBBean{

				public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
						"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"getDebitCardPrintStatusResMsg",
				"ns3");



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
				 * field for GetDebitCardPrintStatusRes
				 */


				protected GetDebitCardPrintStatusRes_type0 localGetDebitCardPrintStatusRes ;

				/*  This tracker boolean wil be used to detect whether the user called the set method
				 *   for this attribute. It will be used to determine whether to include this field
				 *   in the serialized XML
				 */
				protected boolean localGetDebitCardPrintStatusResTracker = false ;

				public boolean isGetDebitCardPrintStatusResSpecified(){
					return localGetDebitCardPrintStatusResTracker;
				}



				/**
				 * Auto generated getter method
				 * @return GetDebitCardPrintStatusRes_type0
				 */
				public  GetDebitCardPrintStatusRes_type0 getGetDebitCardPrintStatusRes(){
					return localGetDebitCardPrintStatusRes;
				}



				/**
				 * Auto generated setter method
				 * @param param GetDebitCardPrintStatusRes
				 */
				public void setGetDebitCardPrintStatusRes(GetDebitCardPrintStatusRes_type0 param){
					localGetDebitCardPrintStatusResTracker = param != null;

					this.localGetDebitCardPrintStatusRes=param;


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


						java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd");
						if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
							writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
									namespacePrefix+":getDebitCardPrintStatusResMsg",
									xmlWriter);
						} else {
							writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
									"getDebitCardPrintStatusResMsg",
									xmlWriter);
						}


					}

					if (localHeader==null){
						throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
					}
					localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
							xmlWriter);
					if (localGetDebitCardPrintStatusResTracker){
						if (localGetDebitCardPrintStatusRes==null){
							throw new org.apache.axis2.databinding.ADBException("getDebitCardPrintStatusRes cannot be null!!");
						}
						localGetDebitCardPrintStatusRes.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","getDebitCardPrintStatusRes"),
								xmlWriter);
					}
					xmlWriter.writeEndElement();


				}

				private static java.lang.String generatePrefix(java.lang.String namespace) {
					if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd")){
						return "ns3";
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
					if (localGetDebitCardPrintStatusResTracker){
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"getDebitCardPrintStatusRes"));


						if (localGetDebitCardPrintStatusRes==null){
							throw new org.apache.axis2.databinding.ADBException("getDebitCardPrintStatusRes cannot be null!!");
						}
						elementList.add(localGetDebitCardPrintStatusRes);
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
					public static GetDebitCardPrintStatusResMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
						GetDebitCardPrintStatusResMsg object =
							new GetDebitCardPrintStatusResMsg();

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

									if (!"getDebitCardPrintStatusResMsg".equals(type)){
										//find namespace for the prefix
										java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
										return (GetDebitCardPrintStatusResMsg)ExtensionMapper.getTypeObject(
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

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","getDebitCardPrintStatusRes").equals(reader.getName())){

								object.setGetDebitCardPrintStatusRes(GetDebitCardPrintStatusRes_type0.Factory.parse(reader));

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


			public static class GetDebitCardPrintStatusReqMsg
			implements org.apache.axis2.databinding.ADBBean{

				public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
						"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"getDebitCardPrintStatusReqMsg",
				"ns3");



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
				 * field for GetDebitCardPrintStatusReq
				 */


				protected GetDebitCardPrintStatusReq_type0 localGetDebitCardPrintStatusReq ;


				/**
				 * Auto generated getter method
				 * @return GetDebitCardPrintStatusReq_type0
				 */
				public  GetDebitCardPrintStatusReq_type0 getGetDebitCardPrintStatusReq(){
					return localGetDebitCardPrintStatusReq;
				}



				/**
				 * Auto generated setter method
				 * @param param GetDebitCardPrintStatusReq
				 */
				public void setGetDebitCardPrintStatusReq(GetDebitCardPrintStatusReq_type0 param){

					this.localGetDebitCardPrintStatusReq=param;


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


						java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd");
						if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
							writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
									namespacePrefix+":getDebitCardPrintStatusReqMsg",
									xmlWriter);
						} else {
							writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
									"getDebitCardPrintStatusReqMsg",
									xmlWriter);
						}


					}

					if (localHeader==null){
						throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
					}
					localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
							xmlWriter);

					if (localGetDebitCardPrintStatusReq==null){
						throw new org.apache.axis2.databinding.ADBException("getDebitCardPrintStatusReq cannot be null!!");
					}
					localGetDebitCardPrintStatusReq.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","getDebitCardPrintStatusReq"),
							xmlWriter);

					xmlWriter.writeEndElement();


				}

				private static java.lang.String generatePrefix(java.lang.String namespace) {
					if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd")){
						return "ns3";
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

					elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
					"getDebitCardPrintStatusReq"));


					if (localGetDebitCardPrintStatusReq==null){
						throw new org.apache.axis2.databinding.ADBException("getDebitCardPrintStatusReq cannot be null!!");
					}
					elementList.add(localGetDebitCardPrintStatusReq);


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
					public static GetDebitCardPrintStatusReqMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
						GetDebitCardPrintStatusReqMsg object =
							new GetDebitCardPrintStatusReqMsg();

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

									if (!"getDebitCardPrintStatusReqMsg".equals(type)){
										//find namespace for the prefix
										java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
										return (GetDebitCardPrintStatusReqMsg)ExtensionMapper.getTypeObject(
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

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","getDebitCardPrintStatusReq").equals(reader.getName())){

								object.setGetDebitCardPrintStatusReq(GetDebitCardPrintStatusReq_type0.Factory.parse(reader));

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


			public static class GetKioskListResMsg
			implements org.apache.axis2.databinding.ADBBean{

				public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
						"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"getKioskListResMsg",
				"ns3");



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
				 * field for GetKioskListRes
				 */


				protected GetKioskListRes_type0 localGetKioskListRes ;

				/*  This tracker boolean wil be used to detect whether the user called the set method
				 *   for this attribute. It will be used to determine whether to include this field
				 *   in the serialized XML
				 */
				protected boolean localGetKioskListResTracker = false ;

				public boolean isGetKioskListResSpecified(){
					return localGetKioskListResTracker;
				}



				/**
				 * Auto generated getter method
				 * @return GetKioskListRes_type0
				 */
				public  GetKioskListRes_type0 getGetKioskListRes(){
					return localGetKioskListRes;
				}



				/**
				 * Auto generated setter method
				 * @param param GetKioskListRes
				 */
				public void setGetKioskListRes(GetKioskListRes_type0 param){
					localGetKioskListResTracker = param != null;

					this.localGetKioskListRes=param;


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


						java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd");
						if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
							writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
									namespacePrefix+":getKioskListResMsg",
									xmlWriter);
						} else {
							writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
									"getKioskListResMsg",
									xmlWriter);
						}


					}

					if (localHeader==null){
						throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
					}
					localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
							xmlWriter);
					if (localGetKioskListResTracker){
						if (localGetKioskListRes==null){
							throw new org.apache.axis2.databinding.ADBException("getKioskListRes cannot be null!!");
						}
						localGetKioskListRes.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","getKioskListRes"),
								xmlWriter);
					}
					xmlWriter.writeEndElement();


				}

				private static java.lang.String generatePrefix(java.lang.String namespace) {
					if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd")){
						return "ns3";
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
					if (localGetKioskListResTracker){
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"getKioskListRes"));


						if (localGetKioskListRes==null){
							throw new org.apache.axis2.databinding.ADBException("getKioskListRes cannot be null!!");
						}
						elementList.add(localGetKioskListRes);
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
					public static GetKioskListResMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
						GetKioskListResMsg object =
							new GetKioskListResMsg();

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

									if (!"getKioskListResMsg".equals(type)){
										//find namespace for the prefix
										java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
										return (GetKioskListResMsg)ExtensionMapper.getTypeObject(
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

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","getKioskListRes").equals(reader.getName())){

								object.setGetKioskListRes(GetKioskListRes_type0.Factory.parse(reader));

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


			public static class GetDebitCardPrintStatusRes_type0
			implements org.apache.axis2.databinding.ADBBean{
				/* This type was generated from the piece of schema that had
                name = getDebitCardPrintStatusRes_type0
                Namespace URI = http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd
                Namespace Prefix = ns3
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
				 * field for DebitCardNumber
				 */


				protected java.lang.String localDebitCardNumber ;

				/*  This tracker boolean wil be used to detect whether the user called the set method
				 *   for this attribute. It will be used to determine whether to include this field
				 *   in the serialized XML
				 */
				protected boolean localDebitCardNumberTracker = false ;

				public boolean isDebitCardNumberSpecified(){
					return localDebitCardNumberTracker;
				}



				/**
				 * Auto generated getter method
				 * @return java.lang.String
				 */
				public  java.lang.String getDebitCardNumber(){
					return localDebitCardNumber;
				}



				/**
				 * Auto generated setter method
				 * @param param DebitCardNumber
				 */
				public void setDebitCardNumber(java.lang.String param){
					localDebitCardNumberTracker = param != null;

					this.localDebitCardNumber=param;


				}


				/**
				 * field for DebitCardDetails
				 */


				protected DebitCardDetails_type0 localDebitCardDetails ;

				/*  This tracker boolean wil be used to detect whether the user called the set method
				 *   for this attribute. It will be used to determine whether to include this field
				 *   in the serialized XML
				 */
				protected boolean localDebitCardDetailsTracker = false ;

				public boolean isDebitCardDetailsSpecified(){
					return localDebitCardDetailsTracker;
				}



				/**
				 * Auto generated getter method
				 * @return DebitCardDetails_type0
				 */
				public  DebitCardDetails_type0 getDebitCardDetails(){
					return localDebitCardDetails;
				}



				/**
				 * Auto generated setter method
				 * @param param DebitCardDetails
				 */
				public void setDebitCardDetails(DebitCardDetails_type0 param){
					localDebitCardDetailsTracker = param != null;

					this.localDebitCardDetails=param;


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


						java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd");
						if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
							writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
									namespacePrefix+":getDebitCardPrintStatusRes_type0",
									xmlWriter);
						} else {
							writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
									"getDebitCardPrintStatusRes_type0",
									xmlWriter);
						}


					}
					if (localCustomerIdTracker){
						namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd";
						writeStartElement(null, namespace, "customerId", xmlWriter);


						if (localCustomerId==null){
							// write the nil attribute

							throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");

						}else{


							xmlWriter.writeCharacters(localCustomerId);

						}

						xmlWriter.writeEndElement();
					} if (localDebitCardNumberTracker){
						namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd";
						writeStartElement(null, namespace, "debitCardNumber", xmlWriter);


						if (localDebitCardNumber==null){
							// write the nil attribute

							throw new org.apache.axis2.databinding.ADBException("debitCardNumber cannot be null!!");

						}else{


							xmlWriter.writeCharacters(localDebitCardNumber);

						}

						xmlWriter.writeEndElement();
					} if (localDebitCardDetailsTracker){
						if (localDebitCardDetails==null){
							throw new org.apache.axis2.databinding.ADBException("debitCardDetails cannot be null!!");
						}
						localDebitCardDetails.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","debitCardDetails"),
								xmlWriter);
					}
					xmlWriter.writeEndElement();


				}

				private static java.lang.String generatePrefix(java.lang.String namespace) {
					if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd")){
						return "ns3";
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
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"customerId"));

						if (localCustomerId != null){
							elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerId));
						} else {
							throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");
						}
					} if (localDebitCardNumberTracker){
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"debitCardNumber"));

						if (localDebitCardNumber != null){
							elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDebitCardNumber));
						} else {
							throw new org.apache.axis2.databinding.ADBException("debitCardNumber cannot be null!!");
						}
					} if (localDebitCardDetailsTracker){
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"debitCardDetails"));


						if (localDebitCardDetails==null){
							throw new org.apache.axis2.databinding.ADBException("debitCardDetails cannot be null!!");
						}
						elementList.add(localDebitCardDetails);
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
					public static GetDebitCardPrintStatusRes_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
						GetDebitCardPrintStatusRes_type0 object =
							new GetDebitCardPrintStatusRes_type0();

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

									if (!"getDebitCardPrintStatusRes_type0".equals(type)){
										//find namespace for the prefix
										java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
										return (GetDebitCardPrintStatusRes_type0)ExtensionMapper.getTypeObject(
												nsUri,type,reader);
									}


								}


							}




							// Note all attributes that were handled. Used to differ normal attributes
							// from anyAttributes.
							java.util.Vector handledAttributes = new java.util.Vector();




							reader.next();


							while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","customerId").equals(reader.getName())){

								java.lang.String content = reader.getElementText();

								object.setCustomerId(
										org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

								reader.next();

							}  // End of if for expected property start element

							else {

							}


							while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","debitCardNumber").equals(reader.getName())){

								java.lang.String content = reader.getElementText();

								object.setDebitCardNumber(
										org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

								reader.next();

							}  // End of if for expected property start element

							else {

							}


							while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","debitCardDetails").equals(reader.getName())){

								object.setDebitCardDetails(DebitCardDetails_type0.Factory.parse(reader));

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
							"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd".equals(namespaceURI) &&
							"getDebitCardPrintStatusReq_type0".equals(typeName)){

						return  GetDebitCardPrintStatusReq_type0.Factory.parse(reader);


					}


					if (
							"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd".equals(namespaceURI) &&
							"getKioskListReq_type0".equals(typeName)){

						return  GetKioskListReq_type0.Factory.parse(reader);


					}


					if (
							"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd".equals(namespaceURI) &&
							"getKioskListRes_type0".equals(typeName)){

						return  GetKioskListRes_type0.Factory.parse(reader);


					}


					if (
							"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd".equals(namespaceURI) &&
							"kioskDetails_type0".equals(typeName)){

						return  KioskDetails_type0.Factory.parse(reader);


					}


					if (
							"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd".equals(namespaceURI) &&
							"kiosks_type0".equals(typeName)){

						return  Kiosks_type0.Factory.parse(reader);


					}


					if (
							"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd".equals(namespaceURI) &&
							"getDebitCardPrintStatusRes_type0".equals(typeName)){

						return  GetDebitCardPrintStatusRes_type0.Factory.parse(reader);


					}


					if (
							"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd".equals(namespaceURI) &&
							"debitCards_type0".equals(typeName)){

						return  DebitCards_type0.Factory.parse(reader);


					}


					if (
							"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd".equals(namespaceURI) &&
							"debitCardDetails_type0".equals(typeName)){

						return  DebitCardDetails_type0.Factory.parse(reader);


					}


					if (
							"http://www.adcb.com/esb/common/header.xsd".equals(namespaceURI) &&
							"headerType".equals(typeName)){

						return  HeaderType.Factory.parse(reader);


					}


					throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
				}

			}

			public static class KioskDetails_type0
			implements org.apache.axis2.databinding.ADBBean{
				/* This type was generated from the piece of schema that had
                name = kioskDetails_type0
                Namespace URI = http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd
                Namespace Prefix = ns3
				 */


				/**
				 * field for Kiosks
				 * This was an Array!
				 */


				protected Kiosks_type0[] localKiosks ;

				/*  This tracker boolean wil be used to detect whether the user called the set method
				 *   for this attribute. It will be used to determine whether to include this field
				 *   in the serialized XML
				 */
				protected boolean localKiosksTracker = false ;

				public boolean isKiosksSpecified(){
					return localKiosksTracker;
				}



				/**
				 * Auto generated getter method
				 * @return Kiosks_type0[]
				 */
				public  Kiosks_type0[] getKiosks(){
					return localKiosks;
				}






				/**
				 * validate the array for Kiosks
				 */
				protected void validateKiosks(Kiosks_type0[] param){

				}


				/**
				 * Auto generated setter method
				 * @param param Kiosks
				 */
				public void setKiosks(Kiosks_type0[] param){

					validateKiosks(param);

					localKiosksTracker = param != null;

					this.localKiosks=param;
				}



				/**
				 * Auto generated add method for the array for convenience
				 * @param param Kiosks_type0
				 */
				public void addKiosks(Kiosks_type0 param){
					if (localKiosks == null){
						localKiosks = new Kiosks_type0[]{};
					}


					//update the setting tracker
					localKiosksTracker = true;


					java.util.List list =
						org.apache.axis2.databinding.utils.ConverterUtil.toList(localKiosks);
					list.add(param);
					this.localKiosks =
						(Kiosks_type0[])list.toArray(
								new Kiosks_type0[list.size()]);

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


						java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd");
						if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
							writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
									namespacePrefix+":kioskDetails_type0",
									xmlWriter);
						} else {
							writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
									"kioskDetails_type0",
									xmlWriter);
						}


					}
					if (localKiosksTracker){
						if (localKiosks!=null){
							for (int i = 0;i < localKiosks.length;i++){
								if (localKiosks[i] != null){
									localKiosks[i].serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","kiosks"),
											xmlWriter);
								} else {

									// we don't have to do any thing since minOccures is zero

								}

							}
						} else {

							throw new org.apache.axis2.databinding.ADBException("kiosks cannot be null!!");

						}
					}
					xmlWriter.writeEndElement();


				}

				private static java.lang.String generatePrefix(java.lang.String namespace) {
					if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd")){
						return "ns3";
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

					if (localKiosksTracker){
						if (localKiosks!=null) {
							for (int i = 0;i < localKiosks.length;i++){

								if (localKiosks[i] != null){
									elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
									"kiosks"));
									elementList.add(localKiosks[i]);
								} else {

									// nothing to do

								}

							}
						} else {

							throw new org.apache.axis2.databinding.ADBException("kiosks cannot be null!!");

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
					public static KioskDetails_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
						KioskDetails_type0 object =
							new KioskDetails_type0();

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

									if (!"kioskDetails_type0".equals(type)){
										//find namespace for the prefix
										java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
										return (KioskDetails_type0)ExtensionMapper.getTypeObject(
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

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","kiosks").equals(reader.getName())){



								// Process the array and step past its final element's end.
								list1.add(Kiosks_type0.Factory.parse(reader));

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
										if (new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","kiosks").equals(reader.getName())){
											list1.add(Kiosks_type0.Factory.parse(reader));

										}else{
											loopDone1 = true;
										}
									}
								}
								// call the converter utility  to convert and set the array

								object.setKiosks((Kiosks_type0[])
										org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
												Kiosks_type0.class,
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


			public static class GetDebitCardPrintStatusReq_type0
			implements org.apache.axis2.databinding.ADBBean{
				/* This type was generated from the piece of schema that had
                name = getDebitCardPrintStatusReq_type0
                Namespace URI = http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd
                Namespace Prefix = ns3
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
				 * field for DebitCardNumber
				 */


				protected java.lang.String localDebitCardNumber ;

				/*  This tracker boolean wil be used to detect whether the user called the set method
				 *   for this attribute. It will be used to determine whether to include this field
				 *   in the serialized XML
				 */
				protected boolean localDebitCardNumberTracker = false ;

				public boolean isDebitCardNumberSpecified(){
					return localDebitCardNumberTracker;
				}



				/**
				 * Auto generated getter method
				 * @return java.lang.String
				 */
				public  java.lang.String getDebitCardNumber(){
					return localDebitCardNumber;
				}



				/**
				 * Auto generated setter method
				 * @param param DebitCardNumber
				 */
				public void setDebitCardNumber(java.lang.String param){
					localDebitCardNumberTracker = param != null;

					this.localDebitCardNumber=param;


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


						java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd");
						if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
							writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
									namespacePrefix+":getDebitCardPrintStatusReq_type0",
									xmlWriter);
						} else {
							writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
									"getDebitCardPrintStatusReq_type0",
									xmlWriter);
						}


					}
					if (localCustomerIdTracker){
						namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd";
						writeStartElement(null, namespace, "customerId", xmlWriter);


						if (localCustomerId==null){
							// write the nil attribute

							throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");

						}else{


							xmlWriter.writeCharacters(localCustomerId);

						}

						xmlWriter.writeEndElement();
					} if (localDebitCardNumberTracker){
						namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd";
						writeStartElement(null, namespace, "debitCardNumber", xmlWriter);


						if (localDebitCardNumber==null){
							// write the nil attribute

							throw new org.apache.axis2.databinding.ADBException("debitCardNumber cannot be null!!");

						}else{


							xmlWriter.writeCharacters(localDebitCardNumber);

						}

						xmlWriter.writeEndElement();
					}
					xmlWriter.writeEndElement();


				}

				private static java.lang.String generatePrefix(java.lang.String namespace) {
					if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd")){
						return "ns3";
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
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"customerId"));

						if (localCustomerId != null){
							elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerId));
						} else {
							throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");
						}
					} if (localDebitCardNumberTracker){
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"debitCardNumber"));

						if (localDebitCardNumber != null){
							elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDebitCardNumber));
						} else {
							throw new org.apache.axis2.databinding.ADBException("debitCardNumber cannot be null!!");
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
					public static GetDebitCardPrintStatusReq_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
						GetDebitCardPrintStatusReq_type0 object =
							new GetDebitCardPrintStatusReq_type0();

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

									if (!"getDebitCardPrintStatusReq_type0".equals(type)){
										//find namespace for the prefix
										java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
										return (GetDebitCardPrintStatusReq_type0)ExtensionMapper.getTypeObject(
												nsUri,type,reader);
									}


								}


							}




							// Note all attributes that were handled. Used to differ normal attributes
							// from anyAttributes.
							java.util.Vector handledAttributes = new java.util.Vector();




							reader.next();


							while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","customerId").equals(reader.getName())){

								java.lang.String content = reader.getElementText();

								object.setCustomerId(
										org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

								reader.next();

							}  // End of if for expected property start element

							else {

							}


							while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","debitCardNumber").equals(reader.getName())){

								java.lang.String content = reader.getElementText();

								object.setDebitCardNumber(
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


			public static class Kiosks_type0
			implements org.apache.axis2.databinding.ADBBean{
				/* This type was generated from the piece of schema that had
                name = kiosks_type0
                Namespace URI = http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd
                Namespace Prefix = ns3
				 */


				/**
				 * field for KioskId
				 */


				protected java.lang.String localKioskId ;

				/*  This tracker boolean wil be used to detect whether the user called the set method
				 *   for this attribute. It will be used to determine whether to include this field
				 *   in the serialized XML
				 */
				protected boolean localKioskIdTracker = false ;

				public boolean isKioskIdSpecified(){
					return localKioskIdTracker;
				}



				/**
				 * Auto generated getter method
				 * @return java.lang.String
				 */
				public  java.lang.String getKioskId(){
					return localKioskId;
				}



				/**
				 * Auto generated setter method
				 * @param param KioskId
				 */
				public void setKioskId(java.lang.String param){
					localKioskIdTracker = param != null;

					this.localKioskId=param;


				}


				/**
				 * field for KioskDesc
				 */


				protected java.lang.String localKioskDesc ;

				/*  This tracker boolean wil be used to detect whether the user called the set method
				 *   for this attribute. It will be used to determine whether to include this field
				 *   in the serialized XML
				 */
				protected boolean localKioskDescTracker = false ;

				public boolean isKioskDescSpecified(){
					return localKioskDescTracker;
				}



				/**
				 * Auto generated getter method
				 * @return java.lang.String
				 */
				public  java.lang.String getKioskDesc(){
					return localKioskDesc;
				}



				/**
				 * Auto generated setter method
				 * @param param KioskDesc
				 */
				public void setKioskDesc(java.lang.String param){
					localKioskDescTracker = param != null;

					this.localKioskDesc=param;


				}


				/**
				 * field for KioskLocation
				 */


				protected java.lang.String localKioskLocation ;

				/*  This tracker boolean wil be used to detect whether the user called the set method
				 *   for this attribute. It will be used to determine whether to include this field
				 *   in the serialized XML
				 */
				protected boolean localKioskLocationTracker = false ;

				public boolean isKioskLocationSpecified(){
					return localKioskLocationTracker;
				}



				/**
				 * Auto generated getter method
				 * @return java.lang.String
				 */
				public  java.lang.String getKioskLocation(){
					return localKioskLocation;
				}



				/**
				 * Auto generated setter method
				 * @param param KioskLocation
				 */
				public void setKioskLocation(java.lang.String param){
					localKioskLocationTracker = param != null;

					this.localKioskLocation=param;


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


						java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd");
						if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
							writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
									namespacePrefix+":kiosks_type0",
									xmlWriter);
						} else {
							writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
									"kiosks_type0",
									xmlWriter);
						}


					}
					if (localKioskIdTracker){
						namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd";
						writeStartElement(null, namespace, "kioskId", xmlWriter);


						if (localKioskId==null){
							// write the nil attribute

							throw new org.apache.axis2.databinding.ADBException("kioskId cannot be null!!");

						}else{


							xmlWriter.writeCharacters(localKioskId);

						}

						xmlWriter.writeEndElement();
					} if (localKioskDescTracker){
						namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd";
						writeStartElement(null, namespace, "kioskDesc", xmlWriter);


						if (localKioskDesc==null){
							// write the nil attribute

							throw new org.apache.axis2.databinding.ADBException("kioskDesc cannot be null!!");

						}else{


							xmlWriter.writeCharacters(localKioskDesc);

						}

						xmlWriter.writeEndElement();
					} if (localKioskLocationTracker){
						namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd";
						writeStartElement(null, namespace, "kioskLocation", xmlWriter);


						if (localKioskLocation==null){
							// write the nil attribute

							throw new org.apache.axis2.databinding.ADBException("kioskLocation cannot be null!!");

						}else{


							xmlWriter.writeCharacters(localKioskLocation);

						}

						xmlWriter.writeEndElement();
					}
					xmlWriter.writeEndElement();


				}

				private static java.lang.String generatePrefix(java.lang.String namespace) {
					if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd")){
						return "ns3";
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

					if (localKioskIdTracker){
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"kioskId"));

						if (localKioskId != null){
							elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localKioskId));
						} else {
							throw new org.apache.axis2.databinding.ADBException("kioskId cannot be null!!");
						}
					} if (localKioskDescTracker){
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"kioskDesc"));

						if (localKioskDesc != null){
							elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localKioskDesc));
						} else {
							throw new org.apache.axis2.databinding.ADBException("kioskDesc cannot be null!!");
						}
					} if (localKioskLocationTracker){
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"kioskLocation"));

						if (localKioskLocation != null){
							elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localKioskLocation));
						} else {
							throw new org.apache.axis2.databinding.ADBException("kioskLocation cannot be null!!");
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
					public static Kiosks_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
						Kiosks_type0 object =
							new Kiosks_type0();

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

									if (!"kiosks_type0".equals(type)){
										//find namespace for the prefix
										java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
										return (Kiosks_type0)ExtensionMapper.getTypeObject(
												nsUri,type,reader);
									}


								}


							}




							// Note all attributes that were handled. Used to differ normal attributes
							// from anyAttributes.
							java.util.Vector handledAttributes = new java.util.Vector();




							reader.next();


							while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","kioskId").equals(reader.getName())){

								java.lang.String content = reader.getElementText();

								object.setKioskId(
										org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

								reader.next();

							}  // End of if for expected property start element

							else {

							}


							while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","kioskDesc").equals(reader.getName())){

								java.lang.String content = reader.getElementText();

								object.setKioskDesc(
										org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

								reader.next();

							}  // End of if for expected property start element

							else {

							}


							while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","kioskLocation").equals(reader.getName())){

								java.lang.String content = reader.getElementText();

								object.setKioskLocation(
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


			public static class DebitCards_type0
			implements org.apache.axis2.databinding.ADBBean{
				/* This type was generated from the piece of schema that had
                name = debitCards_type0
                Namespace URI = http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd
                Namespace Prefix = ns3
				 */


				/**
				 * field for CardTypeDescription
				 */


				protected java.lang.String localCardTypeDescription ;

				/*  This tracker boolean wil be used to detect whether the user called the set method
				 *   for this attribute. It will be used to determine whether to include this field
				 *   in the serialized XML
				 */
				protected boolean localCardTypeDescriptionTracker = false ;

				public boolean isCardTypeDescriptionSpecified(){
					return localCardTypeDescriptionTracker;
				}



				/**
				 * Auto generated getter method
				 * @return java.lang.String
				 */
				public  java.lang.String getCardTypeDescription(){
					return localCardTypeDescription;
				}



				/**
				 * Auto generated setter method
				 * @param param CardTypeDescription
				 */
				public void setCardTypeDescription(java.lang.String param){
					localCardTypeDescriptionTracker = param != null;

					this.localCardTypeDescription=param;


				}


				/**
				 * field for DebitProductGroup
				 */


				protected java.lang.String localDebitProductGroup ;

				/*  This tracker boolean wil be used to detect whether the user called the set method
				 *   for this attribute. It will be used to determine whether to include this field
				 *   in the serialized XML
				 */
				protected boolean localDebitProductGroupTracker = false ;

				public boolean isDebitProductGroupSpecified(){
					return localDebitProductGroupTracker;
				}



				/**
				 * Auto generated getter method
				 * @return java.lang.String
				 */
				public  java.lang.String getDebitProductGroup(){
					return localDebitProductGroup;
				}



				/**
				 * Auto generated setter method
				 * @param param DebitProductGroup
				 */
				public void setDebitProductGroup(java.lang.String param){
					localDebitProductGroupTracker = param != null;

					this.localDebitProductGroup=param;


				}


				/**
				 * field for EmbossName
				 */


				protected java.lang.String localEmbossName ;

				/*  This tracker boolean wil be used to detect whether the user called the set method
				 *   for this attribute. It will be used to determine whether to include this field
				 *   in the serialized XML
				 */
				protected boolean localEmbossNameTracker = false ;

				public boolean isEmbossNameSpecified(){
					return localEmbossNameTracker;
				}



				/**
				 * Auto generated getter method
				 * @return java.lang.String
				 */
				public  java.lang.String getEmbossName(){
					return localEmbossName;
				}



				/**
				 * Auto generated setter method
				 * @param param EmbossName
				 */
				public void setEmbossName(java.lang.String param){
					localEmbossNameTracker = param != null;

					this.localEmbossName=param;


				}


				/**
				 * field for PrimSuppFlag
				 */


				protected java.lang.String localPrimSuppFlag ;

				/*  This tracker boolean wil be used to detect whether the user called the set method
				 *   for this attribute. It will be used to determine whether to include this field
				 *   in the serialized XML
				 */
				protected boolean localPrimSuppFlagTracker = false ;

				public boolean isPrimSuppFlagSpecified(){
					return localPrimSuppFlagTracker;
				}



				/**
				 * Auto generated getter method
				 * @return java.lang.String
				 */
				public  java.lang.String getPrimSuppFlag(){
					return localPrimSuppFlag;
				}



				/**
				 * Auto generated setter method
				 * @param param PrimSuppFlag
				 */
				public void setPrimSuppFlag(java.lang.String param){
					localPrimSuppFlagTracker = param != null;

					this.localPrimSuppFlag=param;


				}


				/**
				 * field for StatusCode
				 */


				protected java.lang.String localStatusCode ;

				/*  This tracker boolean wil be used to detect whether the user called the set method
				 *   for this attribute. It will be used to determine whether to include this field
				 *   in the serialized XML
				 */
				protected boolean localStatusCodeTracker = false ;

				public boolean isStatusCodeSpecified(){
					return localStatusCodeTracker;
				}



				/**
				 * Auto generated getter method
				 * @return java.lang.String
				 */
				public  java.lang.String getStatusCode(){
					return localStatusCode;
				}



				/**
				 * Auto generated setter method
				 * @param param StatusCode
				 */
				public void setStatusCode(java.lang.String param){
					localStatusCodeTracker = param != null;

					this.localStatusCode=param;


				}


				/**
				 * field for StatusDescription
				 */


				protected java.lang.String localStatusDescription ;

				/*  This tracker boolean wil be used to detect whether the user called the set method
				 *   for this attribute. It will be used to determine whether to include this field
				 *   in the serialized XML
				 */
				protected boolean localStatusDescriptionTracker = false ;

				public boolean isStatusDescriptionSpecified(){
					return localStatusDescriptionTracker;
				}



				/**
				 * Auto generated getter method
				 * @return java.lang.String
				 */
				public  java.lang.String getStatusDescription(){
					return localStatusDescription;
				}



				/**
				 * Auto generated setter method
				 * @param param StatusDescription
				 */
				public void setStatusDescription(java.lang.String param){
					localStatusDescriptionTracker = param != null;

					this.localStatusDescription=param;


				}


				/**
				 * field for StatusChangeDate
				 */


				protected java.lang.String localStatusChangeDate ;

				/*  This tracker boolean wil be used to detect whether the user called the set method
				 *   for this attribute. It will be used to determine whether to include this field
				 *   in the serialized XML
				 */
				protected boolean localStatusChangeDateTracker = false ;

				public boolean isStatusChangeDateSpecified(){
					return localStatusChangeDateTracker;
				}



				/**
				 * Auto generated getter method
				 * @return java.lang.String
				 */
				public  java.lang.String getStatusChangeDate(){
					return localStatusChangeDate;
				}



				/**
				 * Auto generated setter method
				 * @param param StatusChangeDate
				 */
				public void setStatusChangeDate(java.lang.String param){
					localStatusChangeDateTracker = param != null;

					this.localStatusChangeDate=param;


				}


				/**
				 * field for StatusChangeUser
				 */


				protected java.lang.String localStatusChangeUser ;

				/*  This tracker boolean wil be used to detect whether the user called the set method
				 *   for this attribute. It will be used to determine whether to include this field
				 *   in the serialized XML
				 */
				protected boolean localStatusChangeUserTracker = false ;

				public boolean isStatusChangeUserSpecified(){
					return localStatusChangeUserTracker;
				}



				/**
				 * Auto generated getter method
				 * @return java.lang.String
				 */
				public  java.lang.String getStatusChangeUser(){
					return localStatusChangeUser;
				}



				/**
				 * Auto generated setter method
				 * @param param StatusChangeUser
				 */
				public void setStatusChangeUser(java.lang.String param){
					localStatusChangeUserTracker = param != null;

					this.localStatusChangeUser=param;


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
				 * field for FourthLineEmbossing
				 */


				protected java.lang.String localFourthLineEmbossing ;

				/*  This tracker boolean wil be used to detect whether the user called the set method
				 *   for this attribute. It will be used to determine whether to include this field
				 *   in the serialized XML
				 */
				protected boolean localFourthLineEmbossingTracker = false ;

				public boolean isFourthLineEmbossingSpecified(){
					return localFourthLineEmbossingTracker;
				}



				/**
				 * Auto generated getter method
				 * @return java.lang.String
				 */
				public  java.lang.String getFourthLineEmbossing(){
					return localFourthLineEmbossing;
				}



				/**
				 * Auto generated setter method
				 * @param param FourthLineEmbossing
				 */
				public void setFourthLineEmbossing(java.lang.String param){
					localFourthLineEmbossingTracker = param != null;

					this.localFourthLineEmbossing=param;


				}


				/**
				 * field for ServiceCode
				 */


				protected java.lang.String localServiceCode ;

				/*  This tracker boolean wil be used to detect whether the user called the set method
				 *   for this attribute. It will be used to determine whether to include this field
				 *   in the serialized XML
				 */
				protected boolean localServiceCodeTracker = false ;

				public boolean isServiceCodeSpecified(){
					return localServiceCodeTracker;
				}



				/**
				 * Auto generated getter method
				 * @return java.lang.String
				 */
				public  java.lang.String getServiceCode(){
					return localServiceCode;
				}



				/**
				 * Auto generated setter method
				 * @param param ServiceCode
				 */
				public void setServiceCode(java.lang.String param){
					localServiceCodeTracker = param != null;

					this.localServiceCode=param;


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


						java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd");
						if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
							writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
									namespacePrefix+":debitCards_type0",
									xmlWriter);
						} else {
							writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
									"debitCards_type0",
									xmlWriter);
						}


					}
					if (localCardTypeDescriptionTracker){
						namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd";
						writeStartElement(null, namespace, "cardTypeDescription", xmlWriter);


						if (localCardTypeDescription==null){
							// write the nil attribute

							throw new org.apache.axis2.databinding.ADBException("cardTypeDescription cannot be null!!");

						}else{


							xmlWriter.writeCharacters(localCardTypeDescription);

						}

						xmlWriter.writeEndElement();
					} if (localDebitProductGroupTracker){
						namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd";
						writeStartElement(null, namespace, "debitProductGroup", xmlWriter);


						if (localDebitProductGroup==null){
							// write the nil attribute

							throw new org.apache.axis2.databinding.ADBException("debitProductGroup cannot be null!!");

						}else{


							xmlWriter.writeCharacters(localDebitProductGroup);

						}

						xmlWriter.writeEndElement();
					} if (localEmbossNameTracker){
						namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd";
						writeStartElement(null, namespace, "embossName", xmlWriter);


						if (localEmbossName==null){
							// write the nil attribute

							throw new org.apache.axis2.databinding.ADBException("embossName cannot be null!!");

						}else{


							xmlWriter.writeCharacters(localEmbossName);

						}

						xmlWriter.writeEndElement();
					} if (localPrimSuppFlagTracker){
						namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd";
						writeStartElement(null, namespace, "primSuppFlag", xmlWriter);


						if (localPrimSuppFlag==null){
							// write the nil attribute

							throw new org.apache.axis2.databinding.ADBException("primSuppFlag cannot be null!!");

						}else{


							xmlWriter.writeCharacters(localPrimSuppFlag);

						}

						xmlWriter.writeEndElement();
					} if (localStatusCodeTracker){
						namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd";
						writeStartElement(null, namespace, "statusCode", xmlWriter);


						if (localStatusCode==null){
							// write the nil attribute

							throw new org.apache.axis2.databinding.ADBException("statusCode cannot be null!!");

						}else{


							xmlWriter.writeCharacters(localStatusCode);

						}

						xmlWriter.writeEndElement();
					} if (localStatusDescriptionTracker){
						namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd";
						writeStartElement(null, namespace, "statusDescription", xmlWriter);


						if (localStatusDescription==null){
							// write the nil attribute

							throw new org.apache.axis2.databinding.ADBException("statusDescription cannot be null!!");

						}else{


							xmlWriter.writeCharacters(localStatusDescription);

						}

						xmlWriter.writeEndElement();
					} if (localStatusChangeDateTracker){
						namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd";
						writeStartElement(null, namespace, "statusChangeDate", xmlWriter);


						if (localStatusChangeDate==null){
							// write the nil attribute

							throw new org.apache.axis2.databinding.ADBException("statusChangeDate cannot be null!!");

						}else{


							xmlWriter.writeCharacters(localStatusChangeDate);

						}

						xmlWriter.writeEndElement();
					} if (localStatusChangeUserTracker){
						namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd";
						writeStartElement(null, namespace, "statusChangeUser", xmlWriter);


						if (localStatusChangeUser==null){
							// write the nil attribute

							throw new org.apache.axis2.databinding.ADBException("statusChangeUser cannot be null!!");

						}else{


							xmlWriter.writeCharacters(localStatusChangeUser);

						}

						xmlWriter.writeEndElement();
					} if (localExpiryDateTracker){
						namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd";
						writeStartElement(null, namespace, "expiryDate", xmlWriter);


						if (localExpiryDate==null){
							// write the nil attribute

							throw new org.apache.axis2.databinding.ADBException("expiryDate cannot be null!!");

						}else{


							xmlWriter.writeCharacters(localExpiryDate);

						}

						xmlWriter.writeEndElement();
					} if (localIssueDateTracker){
						namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd";
						writeStartElement(null, namespace, "issueDate", xmlWriter);


						if (localIssueDate==null){
							// write the nil attribute

							throw new org.apache.axis2.databinding.ADBException("issueDate cannot be null!!");

						}else{


							xmlWriter.writeCharacters(localIssueDate);

						}

						xmlWriter.writeEndElement();
					} if (localFourthLineEmbossingTracker){
						namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd";
						writeStartElement(null, namespace, "fourthLineEmbossing", xmlWriter);


						if (localFourthLineEmbossing==null){
							// write the nil attribute

							throw new org.apache.axis2.databinding.ADBException("fourthLineEmbossing cannot be null!!");

						}else{


							xmlWriter.writeCharacters(localFourthLineEmbossing);

						}

						xmlWriter.writeEndElement();
					} if (localServiceCodeTracker){
						namespace = "http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd";
						writeStartElement(null, namespace, "serviceCode", xmlWriter);


						if (localServiceCode==null){
							// write the nil attribute

							throw new org.apache.axis2.databinding.ADBException("serviceCode cannot be null!!");

						}else{


							xmlWriter.writeCharacters(localServiceCode);

						}

						xmlWriter.writeEndElement();
					}
					xmlWriter.writeEndElement();


				}

				private static java.lang.String generatePrefix(java.lang.String namespace) {
					if(namespace.equals("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd")){
						return "ns3";
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

					if (localCardTypeDescriptionTracker){
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"cardTypeDescription"));

						if (localCardTypeDescription != null){
							elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCardTypeDescription));
						} else {
							throw new org.apache.axis2.databinding.ADBException("cardTypeDescription cannot be null!!");
						}
					} if (localDebitProductGroupTracker){
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"debitProductGroup"));

						if (localDebitProductGroup != null){
							elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDebitProductGroup));
						} else {
							throw new org.apache.axis2.databinding.ADBException("debitProductGroup cannot be null!!");
						}
					} if (localEmbossNameTracker){
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"embossName"));

						if (localEmbossName != null){
							elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEmbossName));
						} else {
							throw new org.apache.axis2.databinding.ADBException("embossName cannot be null!!");
						}
					} if (localPrimSuppFlagTracker){
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"primSuppFlag"));

						if (localPrimSuppFlag != null){
							elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPrimSuppFlag));
						} else {
							throw new org.apache.axis2.databinding.ADBException("primSuppFlag cannot be null!!");
						}
					} if (localStatusCodeTracker){
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"statusCode"));

						if (localStatusCode != null){
							elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStatusCode));
						} else {
							throw new org.apache.axis2.databinding.ADBException("statusCode cannot be null!!");
						}
					} if (localStatusDescriptionTracker){
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"statusDescription"));

						if (localStatusDescription != null){
							elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStatusDescription));
						} else {
							throw new org.apache.axis2.databinding.ADBException("statusDescription cannot be null!!");
						}
					} if (localStatusChangeDateTracker){
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"statusChangeDate"));

						if (localStatusChangeDate != null){
							elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStatusChangeDate));
						} else {
							throw new org.apache.axis2.databinding.ADBException("statusChangeDate cannot be null!!");
						}
					} if (localStatusChangeUserTracker){
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"statusChangeUser"));

						if (localStatusChangeUser != null){
							elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStatusChangeUser));
						} else {
							throw new org.apache.axis2.databinding.ADBException("statusChangeUser cannot be null!!");
						}
					} if (localExpiryDateTracker){
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"expiryDate"));

						if (localExpiryDate != null){
							elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExpiryDate));
						} else {
							throw new org.apache.axis2.databinding.ADBException("expiryDate cannot be null!!");
						}
					} if (localIssueDateTracker){
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"issueDate"));

						if (localIssueDate != null){
							elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIssueDate));
						} else {
							throw new org.apache.axis2.databinding.ADBException("issueDate cannot be null!!");
						}
					} if (localFourthLineEmbossingTracker){
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"fourthLineEmbossing"));

						if (localFourthLineEmbossing != null){
							elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFourthLineEmbossing));
						} else {
							throw new org.apache.axis2.databinding.ADBException("fourthLineEmbossing cannot be null!!");
						}
					} if (localServiceCodeTracker){
						elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd",
						"serviceCode"));

						if (localServiceCode != null){
							elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localServiceCode));
						} else {
							throw new org.apache.axis2.databinding.ADBException("serviceCode cannot be null!!");
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
					public static DebitCards_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
						DebitCards_type0 object =
							new DebitCards_type0();

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

									if (!"debitCards_type0".equals(type)){
										//find namespace for the prefix
										java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
										return (DebitCards_type0)ExtensionMapper.getTypeObject(
												nsUri,type,reader);
									}


								}


							}




							// Note all attributes that were handled. Used to differ normal attributes
							// from anyAttributes.
							java.util.Vector handledAttributes = new java.util.Vector();




							reader.next();


							while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","cardTypeDescription").equals(reader.getName())){

								java.lang.String content = reader.getElementText();

								object.setCardTypeDescription(
										org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

								reader.next();

							}  // End of if for expected property start element

							else {

							}


							while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","debitProductGroup").equals(reader.getName())){

								java.lang.String content = reader.getElementText();

								object.setDebitProductGroup(
										org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

								reader.next();

							}  // End of if for expected property start element

							else {

							}


							while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","embossName").equals(reader.getName())){

								java.lang.String content = reader.getElementText();

								object.setEmbossName(
										org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

								reader.next();

							}  // End of if for expected property start element

							else {

							}


							while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","primSuppFlag").equals(reader.getName())){

								java.lang.String content = reader.getElementText();

								object.setPrimSuppFlag(
										org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

								reader.next();

							}  // End of if for expected property start element

							else {

							}


							while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","statusCode").equals(reader.getName())){

								java.lang.String content = reader.getElementText();

								object.setStatusCode(
										org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

								reader.next();

							}  // End of if for expected property start element

							else {

							}


							while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","statusDescription").equals(reader.getName())){

								java.lang.String content = reader.getElementText();

								object.setStatusDescription(
										org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

								reader.next();

							}  // End of if for expected property start element

							else {

							}


							while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","statusChangeDate").equals(reader.getName())){

								java.lang.String content = reader.getElementText();

								object.setStatusChangeDate(
										org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

								reader.next();

							}  // End of if for expected property start element

							else {

							}


							while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","statusChangeUser").equals(reader.getName())){

								java.lang.String content = reader.getElementText();

								object.setStatusChangeUser(
										org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

								reader.next();

							}  // End of if for expected property start element

							else {

							}


							while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","expiryDate").equals(reader.getName())){

								java.lang.String content = reader.getElementText();

								object.setExpiryDate(
										org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

								reader.next();

							}  // End of if for expected property start element

							else {

							}


							while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","issueDate").equals(reader.getName())){

								java.lang.String content = reader.getElementText();

								object.setIssueDate(
										org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

								reader.next();

							}  // End of if for expected property start element

							else {

							}


							while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","fourthLineEmbossing").equals(reader.getName())){

								java.lang.String content = reader.getElementText();

								object.setFourthLineEmbossing(
										org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

								reader.next();

							}  // End of if for expected property start element

							else {

							}


							while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/SmartKioskInqServices/InqSBKDebitCards.xsd","serviceCode").equals(reader.getName())){

								java.lang.String content = reader.getElementText();

								object.setServiceCode(
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


			private  org.apache.axiom.om.OMElement  toOM(com.newgen.stub.InqSBKDebitCardsStub.GetDebitCardPrintStatusReqMsg param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {


				try{
					return param.getOMElement(com.newgen.stub.InqSBKDebitCardsStub.GetDebitCardPrintStatusReqMsg.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory.getOMFactory());
				} catch(org.apache.axis2.databinding.ADBException e){
					throw org.apache.axis2.AxisFault.makeFault(e);
				}


			}

			private  org.apache.axiom.om.OMElement  toOM(com.newgen.stub.InqSBKDebitCardsStub.GetDebitCardPrintStatusResMsg param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {


				try{
					return param.getOMElement(com.newgen.stub.InqSBKDebitCardsStub.GetDebitCardPrintStatusResMsg.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory.getOMFactory());
				} catch(org.apache.axis2.databinding.ADBException e){
					throw org.apache.axis2.AxisFault.makeFault(e);
				}


			}

			private  org.apache.axiom.om.OMElement  toOM(com.newgen.stub.InqSBKDebitCardsStub.GetKioskListReqMsg param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {


				try{
					return param.getOMElement(com.newgen.stub.InqSBKDebitCardsStub.GetKioskListReqMsg.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory.getOMFactory());
				} catch(org.apache.axis2.databinding.ADBException e){
					throw org.apache.axis2.AxisFault.makeFault(e);
				}


			}

			private  org.apache.axiom.om.OMElement  toOM(com.newgen.stub.InqSBKDebitCardsStub.GetKioskListResMsg param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {


				try{
					return param.getOMElement(com.newgen.stub.InqSBKDebitCardsStub.GetKioskListResMsg.MY_QNAME,
							org.apache.axiom.om.OMAbstractFactory.getOMFactory());
				} catch(org.apache.axis2.databinding.ADBException e){
					throw org.apache.axis2.AxisFault.makeFault(e);
				}


			}


			private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.newgen.stub.InqSBKDebitCardsStub.GetDebitCardPrintStatusReqMsg param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault{


				try{

					org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
					emptyEnvelope.getBody().addChild(param.getOMElement(com.newgen.stub.InqSBKDebitCardsStub.GetDebitCardPrintStatusReqMsg.MY_QNAME,factory));
					return emptyEnvelope;
				} catch(org.apache.axis2.databinding.ADBException e){
					throw org.apache.axis2.AxisFault.makeFault(e);
				}


			}


			/* methods to provide back word compatibility */



			private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.newgen.stub.InqSBKDebitCardsStub.GetKioskListReqMsg param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault{


				try{

					org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
					emptyEnvelope.getBody().addChild(param.getOMElement(com.newgen.stub.InqSBKDebitCardsStub.GetKioskListReqMsg.MY_QNAME,factory));
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

					if (com.newgen.stub.InqSBKDebitCardsStub.GetDebitCardPrintStatusReqMsg.class.equals(type)){

						return com.newgen.stub.InqSBKDebitCardsStub.GetDebitCardPrintStatusReqMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());


					}

					if (com.newgen.stub.InqSBKDebitCardsStub.GetDebitCardPrintStatusResMsg.class.equals(type)){

						return com.newgen.stub.InqSBKDebitCardsStub.GetDebitCardPrintStatusResMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());


					}

					if (com.newgen.stub.InqSBKDebitCardsStub.GetKioskListReqMsg.class.equals(type)){

						return com.newgen.stub.InqSBKDebitCardsStub.GetKioskListReqMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());


					}

					if (com.newgen.stub.InqSBKDebitCardsStub.GetKioskListResMsg.class.equals(type)){

						return com.newgen.stub.InqSBKDebitCardsStub.GetKioskListResMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());


					}

				} catch (java.lang.Exception e) {
					throw org.apache.axis2.AxisFault.makeFault(e);
				}
				return null;
			}




		}
