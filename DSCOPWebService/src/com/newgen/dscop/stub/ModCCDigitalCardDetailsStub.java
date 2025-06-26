
/**
 * ModCCDigitalCardDetailsStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
package com.newgen.dscop.stub;



/*
 *  ModCCDigitalCardDetailsStub java implementation
 */


public class ModCCDigitalCardDetailsStub extends org.apache.axis2.client.Stub
{
	protected org.apache.axis2.description.AxisOperation[] _operations;

	//hashmaps to keep the fault mapping
	private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
	private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
	private java.util.HashMap faultMessageMap = new java.util.HashMap();

	private static int counter = 0;
	public String cardonboardingMsg = "";
	public String updatedigitalcardMsg = "";
	
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
		_service = new org.apache.axis2.description.AxisService("ModCCDigitalCardDetails" + getUniqueSuffix());
		addAnonymousOperations();

		//creating the operations
		org.apache.axis2.description.AxisOperation __operation;

		_operations = new org.apache.axis2.description.AxisOperation[2];

		__operation = new org.apache.axis2.description.OutInAxisOperation();


		__operation.setName(new javax.xml.namespace.QName("http://xmlns.example.com/1521961335520", "updateDigitalCardStatus_Oper"));
		_service.addOperation(__operation);




		_operations[0]=__operation;


		__operation = new org.apache.axis2.description.OutInAxisOperation();


		__operation.setName(new javax.xml.namespace.QName("http://xmlns.example.com/1521961335520", "addDigitalCard_Oper"));
		_service.addOperation(__operation);




		_operations[1]=__operation;


	}

	//populates the faults
	private void populateFaults(){



	}

	/**
	 *Constructor that takes in a configContext
	 */

	public ModCCDigitalCardDetailsStub(org.apache.axis2.context.ConfigurationContext configurationContext,
			java.lang.String targetEndpoint)
					throws org.apache.axis2.AxisFault {
		this(configurationContext,targetEndpoint,false);
	}


	/**
	 * Constructor that takes in a configContext  and useseperate listner
	 */
	public ModCCDigitalCardDetailsStub(org.apache.axis2.context.ConfigurationContext configurationContext,
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
	public ModCCDigitalCardDetailsStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {

		this(configurationContext,"http://10.101.107.21:6501/Services/EnterpriseServicesMaintenance/CreditCard/Service/ModCCDigitalCardDetails.serviceagent/ModCCDigitalCardDetailsPortTypeEndpoint1" );

	}

	/**
	 * Default Constructor
	 */
	public ModCCDigitalCardDetailsStub() throws org.apache.axis2.AxisFault {

		this("http://10.101.107.21:6501/Services/EnterpriseServicesMaintenance/CreditCard/Service/ModCCDigitalCardDetails.serviceagent/ModCCDigitalCardDetailsPortTypeEndpoint1" );

	}

	/**
	 * Constructor taking the target endpoint
	 */
	public ModCCDigitalCardDetailsStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
		this(null,targetEndpoint);
	}




	/**
	 * Auto generated method signature
	 * 
	 * @see com.newgen.dscop.stub.ModCCDigitalCardDetails#updateDigitalCardStatus_Oper
	 * @param updateDigitalCardStatusReqMsg0

	 */



	public  com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.UpdateDigitalCardStatusResMsg updateDigitalCardStatus_Oper(

			com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.UpdateDigitalCardStatusReqMsg updateDigitalCardStatusReqMsg0)


					throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try{
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
			_operationClient.getOptions().setAction("/Services/EnterpriseServicesMaintenance/CreditCard/Service/ModCCDigitalCardDetails.serviceagent/ModCCDigitalCardDetailsPortTypeEndpoint1/updateDigitalCardStatus_Oper");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



			addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");


			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();



			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;


			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
					updateDigitalCardStatusReqMsg0,
					optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1521961335520",
							"updateDigitalCardStatus_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1521961335520",
									"updateDigitalCardStatus_Oper"));

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

			updatedigitalcardMsg = _returnEnv.toString();
			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement() ,
					com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.UpdateDigitalCardStatusResMsg.class,
					getEnvelopeNamespaces(_returnEnv));


			return (com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.UpdateDigitalCardStatusResMsg)object;

		}catch(org.apache.axis2.AxisFault f){

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt!=null){
				if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"updateDigitalCardStatus_Oper"))){
					//make the fault by reflection
					try{
						java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"updateDigitalCardStatus_Oper"));
						java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
						java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
						java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
						//message class
						java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"updateDigitalCardStatus_Oper"));
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
	 * @see com.newgen.dscop.stub.ModCCDigitalCardDetails#startupdateDigitalCardStatus_Oper
	 * @param updateDigitalCardStatusReqMsg0

	 */
	public  void startupdateDigitalCardStatus_Oper(

			com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.UpdateDigitalCardStatusReqMsg updateDigitalCardStatusReqMsg0,

			final com.newgen.dscop.stub.ModCCDigitalCardDetailsCallbackHandler callback)

					throws java.rmi.RemoteException{

		org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
		_operationClient.getOptions().setAction("/Services/EnterpriseServicesMaintenance/CreditCard/Service/ModCCDigitalCardDetails.serviceagent/ModCCDigitalCardDetailsPortTypeEndpoint1/updateDigitalCardStatus_Oper");
		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



		addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");



		// create SOAP envelope with that payload
		org.apache.axiom.soap.SOAPEnvelope env=null;
		final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();


		//Style is Doc.


		env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
				updateDigitalCardStatusReqMsg0,
				optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1521961335520",
						"updateDigitalCardStatus_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1521961335520",
								"updateDigitalCardStatus_Oper"));

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
							com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.UpdateDigitalCardStatusResMsg.class,
							getEnvelopeNamespaces(resultEnv));
					callback.receiveResultupdateDigitalCardStatus_Oper(
							(com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.UpdateDigitalCardStatusResMsg)object);

				} catch (org.apache.axis2.AxisFault e) {
					callback.receiveErrorupdateDigitalCardStatus_Oper(e);
				}
			}

			public void onError(java.lang.Exception error) {
				if (error instanceof org.apache.axis2.AxisFault) {
					org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
					org.apache.axiom.om.OMElement faultElt = f.getDetail();
					if (faultElt!=null){
						if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"updateDigitalCardStatus_Oper"))){
							//make the fault by reflection
							try{
								java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"updateDigitalCardStatus_Oper"));
								java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
								java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
								java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
								//message class
								java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"updateDigitalCardStatus_Oper"));
								java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
								java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
								java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
										new java.lang.Class[]{messageClass});
								m.invoke(ex,new java.lang.Object[]{messageObject});


								callback.receiveErrorupdateDigitalCardStatus_Oper(new java.rmi.RemoteException(ex.getMessage(), ex));
							} catch(java.lang.ClassCastException e){
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorupdateDigitalCardStatus_Oper(f);
							} catch (java.lang.ClassNotFoundException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorupdateDigitalCardStatus_Oper(f);
							} catch (java.lang.NoSuchMethodException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorupdateDigitalCardStatus_Oper(f);
							} catch (java.lang.reflect.InvocationTargetException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorupdateDigitalCardStatus_Oper(f);
							} catch (java.lang.IllegalAccessException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorupdateDigitalCardStatus_Oper(f);
							} catch (java.lang.InstantiationException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorupdateDigitalCardStatus_Oper(f);
							} catch (org.apache.axis2.AxisFault e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorupdateDigitalCardStatus_Oper(f);
							}
						} else {
							callback.receiveErrorupdateDigitalCardStatus_Oper(f);
						}
					} else {
						callback.receiveErrorupdateDigitalCardStatus_Oper(f);
					}
				} else {
					callback.receiveErrorupdateDigitalCardStatus_Oper(error);
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
					callback.receiveErrorupdateDigitalCardStatus_Oper(axisFault);
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



	public String getInputXml(com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.AddDigitalCardReqMsg addDigitalCardReqMsg2) throws java.rmi.RemoteException
	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[0].getName());
			_operationClient
			.getOptions()
			.setAction(
					"/Services/EnterpriseServicesMaintenance/CreditCard/Service/ModCCDigitalCardDetails.serviceagent/ModDigitalCardDetailsPortTypeEndpoint1/addDigitalCard_Oper");
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

			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
					addDigitalCardReqMsg2,
					optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1521961335520",
							"addDigitalCard_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1521961335520",
									"addDigitalCard_Oper"));


			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);
			return env.toString();

		} catch (org.apache.axis2.AxisFault f) {
			return "";
		}
	}

	public String getInputXml(com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.UpdateDigitalCardStatusReqMsg updateDigitalCardReqMsg2) throws java.rmi.RemoteException
	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try {
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient
					.createClient(_operations[0].getName());
			_operationClient
			.getOptions()
			.setAction(
					"/Services/EnterpriseServicesMaintenance/CreditCard/Service/ModCCDigitalCardDetails.serviceagent/ModDigitalCardDetailsPortTypeEndpoint1/addDigitalCard_Oper");
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

			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
					updateDigitalCardReqMsg2,
					optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1521961335520",
							"addDigitalCard_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1521961335520",
									"addDigitalCard_Oper"));


			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);
			return env.toString();

		} catch (org.apache.axis2.AxisFault f) {
			return "";
		}
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see com.newgen.dscop.stub.ModCCDigitalCardDetails#addDigitalCard_Oper
	 * @param addDigitalCardReqMsg2

	 */



	public  com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.AddDigitalCardResMsg addDigitalCard_Oper(

			com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.AddDigitalCardReqMsg addDigitalCardReqMsg2)


					throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try{
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
			_operationClient.getOptions().setAction("/Services/EnterpriseServicesMaintenance/CreditCard/Service/ModCCDigitalCardDetails.serviceagent/ModCCDigitalCardDetailsPortTypeEndpoint1/addDigitalCard_Oper");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



			addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");


			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();



			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;


			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
					addDigitalCardReqMsg2,
					optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1521961335520",
							"addDigitalCard_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1521961335520",
									"addDigitalCard_Oper"));

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

			cardonboardingMsg = _returnEnv.toString();
			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement() ,
					com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.AddDigitalCardResMsg.class,
					getEnvelopeNamespaces(_returnEnv));


			return (com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.AddDigitalCardResMsg)object;

		}catch(org.apache.axis2.AxisFault f){

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt!=null){
				if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addDigitalCard_Oper"))){
					//make the fault by reflection
					try{
						java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addDigitalCard_Oper"));
						java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
						java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
						java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
						//message class
						java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addDigitalCard_Oper"));
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
	 * @see com.newgen.dscop.stub.ModCCDigitalCardDetails#startaddDigitalCard_Oper
	 * @param addDigitalCardReqMsg2

	 */
	public  void startaddDigitalCard_Oper(

			com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.AddDigitalCardReqMsg addDigitalCardReqMsg2,

			final com.newgen.dscop.stub.ModCCDigitalCardDetailsCallbackHandler callback)

					throws java.rmi.RemoteException{

		org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
		_operationClient.getOptions().setAction("/Services/EnterpriseServicesMaintenance/CreditCard/Service/ModCCDigitalCardDetails.serviceagent/ModCCDigitalCardDetailsPortTypeEndpoint1/addDigitalCard_Oper");
		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



		addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");



		// create SOAP envelope with that payload
		org.apache.axiom.soap.SOAPEnvelope env=null;
		final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();


		//Style is Doc.


		env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
				addDigitalCardReqMsg2,
				optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1521961335520",
						"addDigitalCard_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1521961335520",
								"addDigitalCard_Oper"));

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
							com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.AddDigitalCardResMsg.class,
							getEnvelopeNamespaces(resultEnv));
					callback.receiveResultaddDigitalCard_Oper(
							(com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.AddDigitalCardResMsg)object);

				} catch (org.apache.axis2.AxisFault e) {
					callback.receiveErroraddDigitalCard_Oper(e);
				}
			}

			public void onError(java.lang.Exception error) {
				if (error instanceof org.apache.axis2.AxisFault) {
					org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
					org.apache.axiom.om.OMElement faultElt = f.getDetail();
					if (faultElt!=null){
						if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addDigitalCard_Oper"))){
							//make the fault by reflection
							try{
								java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addDigitalCard_Oper"));
								java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
								java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
								java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
								//message class
								java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addDigitalCard_Oper"));
								java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
								java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
								java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
										new java.lang.Class[]{messageClass});
								m.invoke(ex,new java.lang.Object[]{messageObject});


								callback.receiveErroraddDigitalCard_Oper(new java.rmi.RemoteException(ex.getMessage(), ex));
							} catch(java.lang.ClassCastException e){
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErroraddDigitalCard_Oper(f);
							} catch (java.lang.ClassNotFoundException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErroraddDigitalCard_Oper(f);
							} catch (java.lang.NoSuchMethodException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErroraddDigitalCard_Oper(f);
							} catch (java.lang.reflect.InvocationTargetException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErroraddDigitalCard_Oper(f);
							} catch (java.lang.IllegalAccessException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErroraddDigitalCard_Oper(f);
							} catch (java.lang.InstantiationException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErroraddDigitalCard_Oper(f);
							} catch (org.apache.axis2.AxisFault e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErroraddDigitalCard_Oper(f);
							}
						} else {
							callback.receiveErroraddDigitalCard_Oper(f);
						}
					} else {
						callback.receiveErroraddDigitalCard_Oper(f);
					}
				} else {
					callback.receiveErroraddDigitalCard_Oper(error);
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
					callback.receiveErroraddDigitalCard_Oper(axisFault);
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
	//http://10.101.107.21:6501/Services/EnterpriseServicesMaintenance/CreditCard/Service/ModCCDigitalCardDetails.serviceagent/ModCCDigitalCardDetailsPortTypeEndpoint1
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


	public static class UpdateDigitalCardStatusReqMsg
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
				"updateDigitalCardStatusReqMsg",
				"ns8");



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
		 * field for UpdateDigitalCardStatusReq
		 */


		protected UpdateDigitalCardStatusReq_type0 localUpdateDigitalCardStatusReq ;


		/**
		 * Auto generated getter method
		 * @return UpdateDigitalCardStatusReq_type0
		 */
		public  UpdateDigitalCardStatusReq_type0 getUpdateDigitalCardStatusReq(){
			return localUpdateDigitalCardStatusReq;
		}



		/**
		 * Auto generated setter method
		 * @param param UpdateDigitalCardStatusReq
		 */
		public void setUpdateDigitalCardStatusReq(UpdateDigitalCardStatusReq_type0 param){

			this.localUpdateDigitalCardStatusReq=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":updateDigitalCardStatusReqMsg",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"updateDigitalCardStatusReqMsg",
							xmlWriter);
				}


			}

			if (localHeader==null){
				throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
			}
			localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
					xmlWriter);

			if (localUpdateDigitalCardStatusReq==null){
				throw new org.apache.axis2.databinding.ADBException("updateDigitalCardStatusReq cannot be null!!");
			}
			localUpdateDigitalCardStatusReq.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","updateDigitalCardStatusReq"),
					xmlWriter);

			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd")){
				return "ns8";
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

			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
					"updateDigitalCardStatusReq"));


			if (localUpdateDigitalCardStatusReq==null){
				throw new org.apache.axis2.databinding.ADBException("updateDigitalCardStatusReq cannot be null!!");
			}
			elementList.add(localUpdateDigitalCardStatusReq);


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
			public static UpdateDigitalCardStatusReqMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				UpdateDigitalCardStatusReqMsg object =
						new UpdateDigitalCardStatusReqMsg();

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

							if (!"updateDigitalCardStatusReqMsg".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (UpdateDigitalCardStatusReqMsg)ExtensionMapper.getTypeObject(
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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","updateDigitalCardStatusReq").equals(reader.getName())){

						object.setUpdateDigitalCardStatusReq(UpdateDigitalCardStatusReq_type0.Factory.parse(reader));

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
					"http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd".equals(namespaceURI) &&
					"addDigitalCardRes_type0".equals(typeName)){

				return  AddDigitalCardRes_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd".equals(namespaceURI) &&
					"updateDigitalCardStatusRes_type0".equals(typeName)){

				return  UpdateDigitalCardStatusRes_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd".equals(namespaceURI) &&
					"addDigitalCardReq_type0".equals(typeName)){

				return  AddDigitalCardReq_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd".equals(namespaceURI) &&
					"updateDigitalCardStatusReq_type0".equals(typeName)){

				return  UpdateDigitalCardStatusReq_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/header.xsd".equals(namespaceURI) &&
					"headerType".equals(typeName)){

				return  HeaderType.Factory.parse(reader);


			}


			throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
		}

	}

	public static class UpdateDigitalCardStatusResMsg
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
				"updateDigitalCardStatusResMsg",
				"ns8");



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
		 * field for UpdateDigitalCardStatusRes
		 */


		protected UpdateDigitalCardStatusRes_type0 localUpdateDigitalCardStatusRes ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localUpdateDigitalCardStatusResTracker = false ;

		public boolean isUpdateDigitalCardStatusResSpecified(){
			return localUpdateDigitalCardStatusResTracker;
		}



		/**
		 * Auto generated getter method
		 * @return UpdateDigitalCardStatusRes_type0
		 */
		public  UpdateDigitalCardStatusRes_type0 getUpdateDigitalCardStatusRes(){
			return localUpdateDigitalCardStatusRes;
		}



		/**
		 * Auto generated setter method
		 * @param param UpdateDigitalCardStatusRes
		 */
		public void setUpdateDigitalCardStatusRes(UpdateDigitalCardStatusRes_type0 param){
			localUpdateDigitalCardStatusResTracker = param != null;

			this.localUpdateDigitalCardStatusRes=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":updateDigitalCardStatusResMsg",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"updateDigitalCardStatusResMsg",
							xmlWriter);
				}


			}

			if (localHeader==null){
				throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
			}
			localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
					xmlWriter);
			if (localUpdateDigitalCardStatusResTracker){
				if (localUpdateDigitalCardStatusRes==null){
					throw new org.apache.axis2.databinding.ADBException("updateDigitalCardStatusRes cannot be null!!");
				}
				localUpdateDigitalCardStatusRes.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","updateDigitalCardStatusRes"),
						xmlWriter);
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd")){
				return "ns8";
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
			if (localUpdateDigitalCardStatusResTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"updateDigitalCardStatusRes"));


				if (localUpdateDigitalCardStatusRes==null){
					throw new org.apache.axis2.databinding.ADBException("updateDigitalCardStatusRes cannot be null!!");
				}
				elementList.add(localUpdateDigitalCardStatusRes);
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
			public static UpdateDigitalCardStatusResMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				UpdateDigitalCardStatusResMsg object =
						new UpdateDigitalCardStatusResMsg();

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

							if (!"updateDigitalCardStatusResMsg".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (UpdateDigitalCardStatusResMsg)ExtensionMapper.getTypeObject(
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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","updateDigitalCardStatusRes").equals(reader.getName())){

						object.setUpdateDigitalCardStatusRes(UpdateDigitalCardStatusRes_type0.Factory.parse(reader));

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


	public static class UpdateDigitalCardStatusRes_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = updateDigitalCardStatusRes_type0
                Namespace URI = http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd
                Namespace Prefix = ns8
		 */


		/**
		 * field for CardNumber
		 */


		protected java.lang.String localCardNumber ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCardNumberTracker = false ;

		public boolean isCardNumberSpecified(){
			return localCardNumberTracker;
		}



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
			localCardNumberTracker = param != null;

			this.localCardNumber=param;


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
		 * field for Reason
		 */


		protected java.lang.String localReason ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localReasonTracker = false ;

		public boolean isReasonSpecified(){
			return localReasonTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getReason(){
			return localReason;
		}



		/**
		 * Auto generated setter method
		 * @param param Reason
		 */
		public void setReason(java.lang.String param){
			localReasonTracker = param != null;

			this.localReason=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":updateDigitalCardStatusRes_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"updateDigitalCardStatusRes_type0",
							xmlWriter);
				}


			}
			if (localCardNumberTracker){
				namespace = "http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd";
				writeStartElement(null, namespace, "cardNumber", xmlWriter);


				if (localCardNumber==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("cardNumber cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCardNumber);

				}

				xmlWriter.writeEndElement();
			} if (localStatusTracker){
				namespace = "http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd";
				writeStartElement(null, namespace, "status", xmlWriter);


				if (localStatus==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("status cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localStatus);

				}

				xmlWriter.writeEndElement();
			} if (localReasonTracker){
				namespace = "http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd";
				writeStartElement(null, namespace, "reason", xmlWriter);


				if (localReason==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("reason cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localReason);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd")){
				return "ns8";
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

			if (localCardNumberTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"cardNumber"));

				if (localCardNumber != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCardNumber));
				} else {
					throw new org.apache.axis2.databinding.ADBException("cardNumber cannot be null!!");
				}
			} if (localStatusTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"status"));

				if (localStatus != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStatus));
				} else {
					throw new org.apache.axis2.databinding.ADBException("status cannot be null!!");
				}
			} if (localReasonTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"reason"));

				if (localReason != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReason));
				} else {
					throw new org.apache.axis2.databinding.ADBException("reason cannot be null!!");
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
			public static UpdateDigitalCardStatusRes_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				UpdateDigitalCardStatusRes_type0 object =
						new UpdateDigitalCardStatusRes_type0();

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

							if (!"updateDigitalCardStatusRes_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (UpdateDigitalCardStatusRes_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","cardNumber").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"cardNumber" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCardNumber(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","status").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"status" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setStatus(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","reason").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"reason" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setReason(
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


	public static class AddDigitalCardResMsg
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
				"addDigitalCardResMsg",
				"ns8");



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
		 * field for AddDigitalCardRes
		 */


		protected AddDigitalCardRes_type0 localAddDigitalCardRes ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAddDigitalCardResTracker = false ;

		public boolean isAddDigitalCardResSpecified(){
			return localAddDigitalCardResTracker;
		}



		/**
		 * Auto generated getter method
		 * @return AddDigitalCardRes_type0
		 */
		public  AddDigitalCardRes_type0 getAddDigitalCardRes(){
			return localAddDigitalCardRes;
		}



		/**
		 * Auto generated setter method
		 * @param param AddDigitalCardRes
		 */
		public void setAddDigitalCardRes(AddDigitalCardRes_type0 param){
			localAddDigitalCardResTracker = param != null;

			this.localAddDigitalCardRes=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":addDigitalCardResMsg",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"addDigitalCardResMsg",
							xmlWriter);
				}


			}

			if (localHeader==null){
				throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
			}
			localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
					xmlWriter);
			if (localAddDigitalCardResTracker){
				if (localAddDigitalCardRes==null){
					throw new org.apache.axis2.databinding.ADBException("addDigitalCardRes cannot be null!!");
				}
				localAddDigitalCardRes.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","addDigitalCardRes"),
						xmlWriter);
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd")){
				return "ns8";
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
			if (localAddDigitalCardResTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"addDigitalCardRes"));


				if (localAddDigitalCardRes==null){
					throw new org.apache.axis2.databinding.ADBException("addDigitalCardRes cannot be null!!");
				}
				elementList.add(localAddDigitalCardRes);
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
			public static AddDigitalCardResMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				AddDigitalCardResMsg object =
						new AddDigitalCardResMsg();

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

							if (!"addDigitalCardResMsg".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (AddDigitalCardResMsg)ExtensionMapper.getTypeObject(
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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","addDigitalCardRes").equals(reader.getName())){

						object.setAddDigitalCardRes(AddDigitalCardRes_type0.Factory.parse(reader));

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


	public static class AddDigitalCardRes_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = addDigitalCardRes_type0
                Namespace URI = http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd
                Namespace Prefix = ns8
		 */


		/**
		 * field for CardNumber
		 */


		protected java.lang.String localCardNumber ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCardNumberTracker = false ;

		public boolean isCardNumberSpecified(){
			return localCardNumberTracker;
		}



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
			localCardNumberTracker = param != null;

			this.localCardNumber=param;


		}


		/**
		 * field for TransactionRefId
		 */


		protected java.lang.String localTransactionRefId ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localTransactionRefIdTracker = false ;

		public boolean isTransactionRefIdSpecified(){
			return localTransactionRefIdTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getTransactionRefId(){
			return localTransactionRefId;
		}



		/**
		 * Auto generated setter method
		 * @param param TransactionRefId
		 */
		public void setTransactionRefId(java.lang.String param){
			localTransactionRefIdTracker = param != null;

			this.localTransactionRefId=param;


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
		 * field for Reason
		 */


		protected java.lang.String localReason ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localReasonTracker = false ;

		public boolean isReasonSpecified(){
			return localReasonTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getReason(){
			return localReason;
		}



		/**
		 * Auto generated setter method
		 * @param param Reason
		 */
		public void setReason(java.lang.String param){
			localReasonTracker = param != null;

			this.localReason=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":addDigitalCardRes_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"addDigitalCardRes_type0",
							xmlWriter);
				}


			}
			if (localCardNumberTracker){
				namespace = "http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd";
				writeStartElement(null, namespace, "cardNumber", xmlWriter);


				if (localCardNumber==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("cardNumber cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCardNumber);

				}

				xmlWriter.writeEndElement();
			} if (localTransactionRefIdTracker){
				namespace = "http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd";
				writeStartElement(null, namespace, "transactionRefId", xmlWriter);


				if (localTransactionRefId==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("transactionRefId cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localTransactionRefId);

				}

				xmlWriter.writeEndElement();
			} if (localStatusTracker){
				namespace = "http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd";
				writeStartElement(null, namespace, "status", xmlWriter);


				if (localStatus==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("status cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localStatus);

				}

				xmlWriter.writeEndElement();
			} if (localReasonTracker){
				namespace = "http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd";
				writeStartElement(null, namespace, "reason", xmlWriter);


				if (localReason==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("reason cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localReason);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd")){
				return "ns8";
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

			if (localCardNumberTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"cardNumber"));

				if (localCardNumber != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCardNumber));
				} else {
					throw new org.apache.axis2.databinding.ADBException("cardNumber cannot be null!!");
				}
			} if (localTransactionRefIdTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"transactionRefId"));

				if (localTransactionRefId != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTransactionRefId));
				} else {
					throw new org.apache.axis2.databinding.ADBException("transactionRefId cannot be null!!");
				}
			} if (localStatusTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"status"));

				if (localStatus != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStatus));
				} else {
					throw new org.apache.axis2.databinding.ADBException("status cannot be null!!");
				}
			} if (localReasonTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"reason"));

				if (localReason != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReason));
				} else {
					throw new org.apache.axis2.databinding.ADBException("reason cannot be null!!");
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
			public static AddDigitalCardRes_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				AddDigitalCardRes_type0 object =
						new AddDigitalCardRes_type0();

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

							if (!"addDigitalCardRes_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (AddDigitalCardRes_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","cardNumber").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"cardNumber" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCardNumber(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","transactionRefId").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"transactionRefId" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setTransactionRefId(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","status").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"status" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setStatus(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","reason").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"reason" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setReason(
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


	public static class AddDigitalCardReq_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = addDigitalCardReq_type0
                Namespace URI = http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd
                Namespace Prefix = ns8
		 */


		/**
		 * field for CardNumber
		 */


		protected java.lang.String localCardNumber ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCardNumberTracker = false ;

		public boolean isCardNumberSpecified(){
			return localCardNumberTracker;
		}



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
			localCardNumberTracker = param != null;

			this.localCardNumber=param;


		}


		/**
		 * field for CustomerNumber
		 */


		protected java.lang.String localCustomerNumber ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCustomerNumberTracker = false ;

		public boolean isCustomerNumberSpecified(){
			return localCustomerNumberTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCustomerNumber(){
			return localCustomerNumber;
		}



		/**
		 * Auto generated setter method
		 * @param param CustomerNumber
		 */
		public void setCustomerNumber(java.lang.String param){
			localCustomerNumberTracker = param != null;

			this.localCustomerNumber=param;


		}


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
		 * field for RequestType
		 */


		protected java.lang.String localRequestType ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localRequestTypeTracker = false ;

		public boolean isRequestTypeSpecified(){
			return localRequestTypeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getRequestType(){
			return localRequestType;
		}



		/**
		 * Auto generated setter method
		 * @param param RequestType
		 */
		public void setRequestType(java.lang.String param){
			localRequestTypeTracker = param != null;

			this.localRequestType=param;


		}


		/**
		 * field for CardLogo
		 */


		protected java.lang.String localCardLogo ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCardLogoTracker = false ;

		public boolean isCardLogoSpecified(){
			return localCardLogoTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCardLogo(){
			return localCardLogo;
		}



		/**
		 * Auto generated setter method
		 * @param param CardLogo
		 */
		public void setCardLogo(java.lang.String param){
			localCardLogoTracker = param != null;

			this.localCardLogo=param;


		}


		/**
		 * field for CreditLimit
		 */


		protected java.lang.String localCreditLimit ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCreditLimitTracker = false ;

		public boolean isCreditLimitSpecified(){
			return localCreditLimitTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCreditLimit(){
			return localCreditLimit;
		}



		/**
		 * Auto generated setter method
		 * @param param CreditLimit
		 */
		public void setCreditLimit(java.lang.String param){
			localCreditLimitTracker = param != null;

			this.localCreditLimit=param;


		}


		/**
		 * field for CardExpiry
		 */


		protected java.lang.String localCardExpiry ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCardExpiryTracker = false ;

		public boolean isCardExpirySpecified(){
			return localCardExpiryTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCardExpiry(){
			return localCardExpiry;
		}



		/**
		 * Auto generated setter method
		 * @param param CardExpiry
		 */
		public void setCardExpiry(java.lang.String param){
			localCardExpiryTracker = param != null;

			this.localCardExpiry=param;


		}


		/**
		 * field for NameLine1
		 */


		protected java.lang.String localNameLine1 ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNameLine1Tracker = false ;

		public boolean isNameLine1Specified(){
			return localNameLine1Tracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNameLine1(){
			return localNameLine1;
		}



		/**
		 * Auto generated setter method
		 * @param param NameLine1
		 */
		public void setNameLine1(java.lang.String param){
			localNameLine1Tracker = param != null;

			this.localNameLine1=param;


		}


		/**
		 * field for EmbosserName1
		 */


		protected java.lang.String localEmbosserName1 ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localEmbosserName1Tracker = false ;

		public boolean isEmbosserName1Specified(){
			return localEmbosserName1Tracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getEmbosserName1(){
			return localEmbosserName1;
		}



		/**
		 * Auto generated setter method
		 * @param param EmbosserName1
		 */
		public void setEmbosserName1(java.lang.String param){
			localEmbosserName1Tracker = param != null;

			this.localEmbosserName1=param;


		}


		/**
		 * field for MobileNo
		 */


		protected java.lang.String localMobileNo ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localMobileNoTracker = false ;

		public boolean isMobileNoSpecified(){
			return localMobileNoTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getMobileNo(){
			return localMobileNo;
		}



		/**
		 * Auto generated setter method
		 * @param param MobileNo
		 */
		public void setMobileNo(java.lang.String param){
			localMobileNoTracker = param != null;

			this.localMobileNo=param;


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
		 * field for BaseSegmentCardExpiry
		 */


		protected java.lang.String localBaseSegmentCardExpiry ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localBaseSegmentCardExpiryTracker = false ;

		public boolean isBaseSegmentCardExpirySpecified(){
			return localBaseSegmentCardExpiryTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getBaseSegmentCardExpiry(){
			return localBaseSegmentCardExpiry;
		}



		/**
		 * Auto generated setter method
		 * @param param BaseSegmentCardExpiry
		 */
		public void setBaseSegmentCardExpiry(java.lang.String param){
			localBaseSegmentCardExpiryTracker = param != null;

			this.localBaseSegmentCardExpiry=param;


		}


		/**
		 * field for SupCreditLimitPercentage
		 */


		protected java.lang.String localSupCreditLimitPercentage ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localSupCreditLimitPercentageTracker = false ;

		public boolean isSupCreditLimitPercentageSpecified(){
			return localSupCreditLimitPercentageTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getSupCreditLimitPercentage(){
			return localSupCreditLimitPercentage;
		}



		/**
		 * Auto generated setter method
		 * @param param SupCreditLimitPercentage
		 */
		public void setSupCreditLimitPercentage(java.lang.String param){
			localSupCreditLimitPercentageTracker = param != null;

			this.localSupCreditLimitPercentage=param;


		}


		/**
		 * field for AppRefNo
		 */


		protected java.lang.String localAppRefNo ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAppRefNoTracker = false ;

		public boolean isAppRefNoSpecified(){
			return localAppRefNoTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getAppRefNo(){
			return localAppRefNo;
		}



		/**
		 * Auto generated setter method
		 * @param param AppRefNo
		 */
		public void setAppRefNo(java.lang.String param){
			localAppRefNoTracker = param != null;

			this.localAppRefNo=param;


		}


		/**
		 * field for DigitalIndicator
		 */


		protected java.lang.String localDigitalIndicator ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDigitalIndicatorTracker = false ;

		public boolean isDigitalIndicatorSpecified(){
			return localDigitalIndicatorTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDigitalIndicator(){
			return localDigitalIndicator;
		}



		/**
		 * Auto generated setter method
		 * @param param DigitalIndicator
		 */
		public void setDigitalIndicator(java.lang.String param){
			localDigitalIndicatorTracker = param != null;

			this.localDigitalIndicator=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":addDigitalCardReq_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"addDigitalCardReq_type0",
							xmlWriter);
				}


			}
			if (localCardNumberTracker){
				namespace = "http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd";
				writeStartElement(null, namespace, "cardNumber", xmlWriter);


				if (localCardNumber==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("cardNumber cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCardNumber);

				}

				xmlWriter.writeEndElement();
			} if (localCustomerNumberTracker){
				namespace = "http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd";
				writeStartElement(null, namespace, "customerNumber", xmlWriter);


				if (localCustomerNumber==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("customerNumber cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCustomerNumber);

				}

				xmlWriter.writeEndElement();
			} if (localAccountNumberTracker){
				namespace = "http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd";
				writeStartElement(null, namespace, "accountNumber", xmlWriter);


				if (localAccountNumber==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("accountNumber cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localAccountNumber);

				}

				xmlWriter.writeEndElement();
			} if (localRequestTypeTracker){
				namespace = "http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd";
				writeStartElement(null, namespace, "requestType", xmlWriter);


				if (localRequestType==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("requestType cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localRequestType);

				}

				xmlWriter.writeEndElement();
			} if (localCardLogoTracker){
				namespace = "http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd";
				writeStartElement(null, namespace, "cardLogo", xmlWriter);


				if (localCardLogo==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("cardLogo cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCardLogo);

				}

				xmlWriter.writeEndElement();
			} if (localCreditLimitTracker){
				namespace = "http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd";
				writeStartElement(null, namespace, "creditLimit", xmlWriter);


				if (localCreditLimit==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("creditLimit cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCreditLimit);

				}

				xmlWriter.writeEndElement();
			} if (localCardExpiryTracker){
				namespace = "http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd";
				writeStartElement(null, namespace, "cardExpiry", xmlWriter);


				if (localCardExpiry==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("cardExpiry cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCardExpiry);

				}

				xmlWriter.writeEndElement();
			} if (localNameLine1Tracker){
				namespace = "http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd";
				writeStartElement(null, namespace, "nameLine1", xmlWriter);


				if (localNameLine1==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("nameLine1 cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNameLine1);

				}

				xmlWriter.writeEndElement();
			} if (localEmbosserName1Tracker){
				namespace = "http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd";
				writeStartElement(null, namespace, "embosserName1", xmlWriter);


				if (localEmbosserName1==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("embosserName1 cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localEmbosserName1);

				}

				xmlWriter.writeEndElement();
			} if (localMobileNoTracker){
				namespace = "http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd";
				writeStartElement(null, namespace, "mobileNo", xmlWriter);


				if (localMobileNo==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("mobileNo cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localMobileNo);

				}

				xmlWriter.writeEndElement();
			} if (localDateOfBirthTracker){
				namespace = "http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd";
				writeStartElement(null, namespace, "dateOfBirth", xmlWriter);


				if (localDateOfBirth==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("dateOfBirth cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDateOfBirth);

				}

				xmlWriter.writeEndElement();
			} if (localBaseSegmentCardExpiryTracker){
				namespace = "http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd";
				writeStartElement(null, namespace, "baseSegmentCardExpiry", xmlWriter);


				if (localBaseSegmentCardExpiry==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("baseSegmentCardExpiry cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localBaseSegmentCardExpiry);

				}

				xmlWriter.writeEndElement();
			} if (localSupCreditLimitPercentageTracker){
				namespace = "http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd";
				writeStartElement(null, namespace, "supCreditLimitPercentage", xmlWriter);


				if (localSupCreditLimitPercentage==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("supCreditLimitPercentage cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localSupCreditLimitPercentage);

				}

				xmlWriter.writeEndElement();
			} if (localAppRefNoTracker){
				namespace = "http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd";
				writeStartElement(null, namespace, "appRefNo", xmlWriter);


				if (localAppRefNo==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("appRefNo cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localAppRefNo);

				}

				xmlWriter.writeEndElement();
			} if (localDigitalIndicatorTracker){
				namespace = "http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd";
				writeStartElement(null, namespace, "digitalIndicator", xmlWriter);


				if (localDigitalIndicator==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("digitalIndicator cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDigitalIndicator);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd")){
				return "ns8";
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

			if (localCardNumberTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"cardNumber"));

				if (localCardNumber != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCardNumber));
				} else {
					throw new org.apache.axis2.databinding.ADBException("cardNumber cannot be null!!");
				}
			} if (localCustomerNumberTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"customerNumber"));

				if (localCustomerNumber != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerNumber));
				} else {
					throw new org.apache.axis2.databinding.ADBException("customerNumber cannot be null!!");
				}
			} if (localAccountNumberTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"accountNumber"));

				if (localAccountNumber != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAccountNumber));
				} else {
					throw new org.apache.axis2.databinding.ADBException("accountNumber cannot be null!!");
				}
			} if (localRequestTypeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"requestType"));

				if (localRequestType != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRequestType));
				} else {
					throw new org.apache.axis2.databinding.ADBException("requestType cannot be null!!");
				}
			} if (localCardLogoTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"cardLogo"));

				if (localCardLogo != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCardLogo));
				} else {
					throw new org.apache.axis2.databinding.ADBException("cardLogo cannot be null!!");
				}
			} if (localCreditLimitTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"creditLimit"));

				if (localCreditLimit != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCreditLimit));
				} else {
					throw new org.apache.axis2.databinding.ADBException("creditLimit cannot be null!!");
				}
			} if (localCardExpiryTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"cardExpiry"));

				if (localCardExpiry != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCardExpiry));
				} else {
					throw new org.apache.axis2.databinding.ADBException("cardExpiry cannot be null!!");
				}
			} if (localNameLine1Tracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"nameLine1"));

				if (localNameLine1 != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNameLine1));
				} else {
					throw new org.apache.axis2.databinding.ADBException("nameLine1 cannot be null!!");
				}
			} if (localEmbosserName1Tracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"embosserName1"));

				if (localEmbosserName1 != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEmbosserName1));
				} else {
					throw new org.apache.axis2.databinding.ADBException("embosserName1 cannot be null!!");
				}
			} if (localMobileNoTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"mobileNo"));

				if (localMobileNo != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMobileNo));
				} else {
					throw new org.apache.axis2.databinding.ADBException("mobileNo cannot be null!!");
				}
			} if (localDateOfBirthTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"dateOfBirth"));

				if (localDateOfBirth != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDateOfBirth));
				} else {
					throw new org.apache.axis2.databinding.ADBException("dateOfBirth cannot be null!!");
				}
			} if (localBaseSegmentCardExpiryTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"baseSegmentCardExpiry"));

				if (localBaseSegmentCardExpiry != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBaseSegmentCardExpiry));
				} else {
					throw new org.apache.axis2.databinding.ADBException("baseSegmentCardExpiry cannot be null!!");
				}
			} if (localSupCreditLimitPercentageTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"supCreditLimitPercentage"));

				if (localSupCreditLimitPercentage != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSupCreditLimitPercentage));
				} else {
					throw new org.apache.axis2.databinding.ADBException("supCreditLimitPercentage cannot be null!!");
				}
			} if (localAppRefNoTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"appRefNo"));

				if (localAppRefNo != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAppRefNo));
				} else {
					throw new org.apache.axis2.databinding.ADBException("appRefNo cannot be null!!");
				}
			} if (localDigitalIndicatorTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"digitalIndicator"));

				if (localDigitalIndicator != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDigitalIndicator));
				} else {
					throw new org.apache.axis2.databinding.ADBException("digitalIndicator cannot be null!!");
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
			public static AddDigitalCardReq_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				AddDigitalCardReq_type0 object =
						new AddDigitalCardReq_type0();

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

							if (!"addDigitalCardReq_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (AddDigitalCardReq_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","cardNumber").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"cardNumber" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCardNumber(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","customerNumber").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"customerNumber" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCustomerNumber(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","accountNumber").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"accountNumber" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAccountNumber(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","requestType").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"requestType" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setRequestType(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","cardLogo").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"cardLogo" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCardLogo(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","creditLimit").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"creditLimit" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCreditLimit(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","cardExpiry").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"cardExpiry" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCardExpiry(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","nameLine1").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"nameLine1" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNameLine1(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","embosserName1").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"embosserName1" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setEmbosserName1(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","mobileNo").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"mobileNo" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setMobileNo(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","dateOfBirth").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"dateOfBirth" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDateOfBirth(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","baseSegmentCardExpiry").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"baseSegmentCardExpiry" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setBaseSegmentCardExpiry(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","supCreditLimitPercentage").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"supCreditLimitPercentage" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setSupCreditLimitPercentage(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","appRefNo").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"appRefNo" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAppRefNo(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","digitalIndicator").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"digitalIndicator" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDigitalIndicator(
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


	public static class AddDigitalCardReqMsg
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
				"addDigitalCardReqMsg",
				"ns8");



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
		 * field for AddDigitalCardReq
		 */


		protected AddDigitalCardReq_type0 localAddDigitalCardReq ;


		/**
		 * Auto generated getter method
		 * @return AddDigitalCardReq_type0
		 */
		public  AddDigitalCardReq_type0 getAddDigitalCardReq(){
			return localAddDigitalCardReq;
		}



		/**
		 * Auto generated setter method
		 * @param param AddDigitalCardReq
		 */
		public void setAddDigitalCardReq(AddDigitalCardReq_type0 param){

			this.localAddDigitalCardReq=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":addDigitalCardReqMsg",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"addDigitalCardReqMsg",
							xmlWriter);
				}


			}

			if (localHeader==null){
				throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
			}
			localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
					xmlWriter);

			if (localAddDigitalCardReq==null){
				throw new org.apache.axis2.databinding.ADBException("addDigitalCardReq cannot be null!!");
			}
			localAddDigitalCardReq.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","addDigitalCardReq"),
					xmlWriter);

			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd")){
				return "ns8";
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

			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
					"addDigitalCardReq"));


			if (localAddDigitalCardReq==null){
				throw new org.apache.axis2.databinding.ADBException("addDigitalCardReq cannot be null!!");
			}
			elementList.add(localAddDigitalCardReq);


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
			public static AddDigitalCardReqMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				AddDigitalCardReqMsg object =
						new AddDigitalCardReqMsg();

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

							if (!"addDigitalCardReqMsg".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (AddDigitalCardReqMsg)ExtensionMapper.getTypeObject(
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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","addDigitalCardReq").equals(reader.getName())){

						object.setAddDigitalCardReq(AddDigitalCardReq_type0.Factory.parse(reader));

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


	public static class UpdateDigitalCardStatusReq_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = updateDigitalCardStatusReq_type0
                Namespace URI = http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd
                Namespace Prefix = ns8
		 */


		/**
		 * field for CardNumber
		 */


		protected java.lang.String localCardNumber ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCardNumberTracker = false ;

		public boolean isCardNumberSpecified(){
			return localCardNumberTracker;
		}



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
			localCardNumberTracker = param != null;

			this.localCardNumber=param;


		}


		/**
		 * field for CardType
		 */


		protected java.lang.String localCardType ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCardTypeTracker = false ;

		public boolean isCardTypeSpecified(){
			return localCardTypeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCardType(){
			return localCardType;
		}



		/**
		 * Auto generated setter method
		 * @param param CardType
		 */
		public void setCardType(java.lang.String param){
			localCardTypeTracker = param != null;

			this.localCardType=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":updateDigitalCardStatusReq_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"updateDigitalCardStatusReq_type0",
							xmlWriter);
				}


			}
			if (localCardNumberTracker){
				namespace = "http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd";
				writeStartElement(null, namespace, "cardNumber", xmlWriter);


				if (localCardNumber==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("cardNumber cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCardNumber);

				}

				xmlWriter.writeEndElement();
			} if (localCardTypeTracker){
				namespace = "http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd";
				writeStartElement(null, namespace, "cardType", xmlWriter);


				if (localCardType==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("cardType cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCardType);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd")){
				return "ns8";
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

			if (localCardNumberTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"cardNumber"));

				if (localCardNumber != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCardNumber));
				} else {
					throw new org.apache.axis2.databinding.ADBException("cardNumber cannot be null!!");
				}
			} if (localCardTypeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd",
						"cardType"));

				if (localCardType != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCardType));
				} else {
					throw new org.apache.axis2.databinding.ADBException("cardType cannot be null!!");
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
			public static UpdateDigitalCardStatusReq_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				UpdateDigitalCardStatusReq_type0 object =
						new UpdateDigitalCardStatusReq_type0();

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

							if (!"updateDigitalCardStatusReq_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (UpdateDigitalCardStatusReq_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","cardNumber").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"cardNumber" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCardNumber(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/ModCCDigitalCardDetails.xsd","cardType").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"cardType" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCardType(
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


	private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.UpdateDigitalCardStatusReqMsg param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {


		try{
			return param.getOMElement(com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.UpdateDigitalCardStatusReqMsg.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}


	}

	private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.UpdateDigitalCardStatusResMsg param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {


		try{
			return param.getOMElement(com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.UpdateDigitalCardStatusResMsg.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}


	}

	private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.AddDigitalCardReqMsg param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {


		try{
			return param.getOMElement(com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.AddDigitalCardReqMsg.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}


	}

	private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.AddDigitalCardResMsg param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {


		try{
			return param.getOMElement(com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.AddDigitalCardResMsg.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}


	}


	private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.UpdateDigitalCardStatusReqMsg param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault{


		try{

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.UpdateDigitalCardStatusReqMsg.MY_QNAME,factory));
			return emptyEnvelope;
		} catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}


	}


	/* methods to provide back word compatibility */



	private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.AddDigitalCardReqMsg param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault{


		try{

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.AddDigitalCardReqMsg.MY_QNAME,factory));
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

			if (com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.UpdateDigitalCardStatusReqMsg.class.equals(type)){

				return com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.UpdateDigitalCardStatusReqMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());


			}

			if (com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.UpdateDigitalCardStatusResMsg.class.equals(type)){

				return com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.UpdateDigitalCardStatusResMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());


			}

			if (com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.AddDigitalCardReqMsg.class.equals(type)){

				return com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.AddDigitalCardReqMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());


			}

			if (com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.AddDigitalCardResMsg.class.equals(type)){

				return com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.AddDigitalCardResMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());


			}

		} catch (java.lang.Exception e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
		return null;
	}




}
