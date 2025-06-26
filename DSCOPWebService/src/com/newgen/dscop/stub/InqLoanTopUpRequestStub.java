
/**
 * InqLoanTopUpRequestStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
package com.newgen.dscop.stub;



/*
 *  InqLoanTopUpRequestStub java implementation
 */


public class InqLoanTopUpRequestStub extends org.apache.axis2.client.Stub
{
	protected org.apache.axis2.description.AxisOperation[] _operations;

	//hashmaps to keep the fault mapping
	private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
	private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
	private java.util.HashMap faultMessageMap = new java.util.HashMap();
	public String outputXML="";
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
		_service = new org.apache.axis2.description.AxisService("InqLoanTopUpRequest" + getUniqueSuffix());
		addAnonymousOperations();

		//creating the operations
		org.apache.axis2.description.AxisOperation __operation;

		_operations = new org.apache.axis2.description.AxisOperation[2];

		__operation = new org.apache.axis2.description.OutInAxisOperation();


		__operation.setName(new javax.xml.namespace.QName("http://xmlns.example.com/1425296844141", "fetchCustOffers_Oper"));
		_service.addOperation(__operation);




		_operations[0]=__operation;


		__operation = new org.apache.axis2.description.OutInAxisOperation();


		__operation.setName(new javax.xml.namespace.QName("http://xmlns.example.com/1425296844141", "fetchEMIDetails_Oper"));
		_service.addOperation(__operation);




		_operations[1]=__operation;


	}

	//populates the faults
	private void populateFaults(){



	}

	/**
	 *Constructor that takes in a configContext
	 */

	public InqLoanTopUpRequestStub(org.apache.axis2.context.ConfigurationContext configurationContext,
			java.lang.String targetEndpoint)
					throws org.apache.axis2.AxisFault {
		this(configurationContext,targetEndpoint,false);
	}


	/**
	 * Constructor that takes in a configContext  and useseperate listner
	 */
	public InqLoanTopUpRequestStub(org.apache.axis2.context.ConfigurationContext configurationContext,
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
	public InqLoanTopUpRequestStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {

		this(configurationContext,"http://10.XXX.107.21:5533/Services/CustomerCommonServices/CustomerInqServices/Service/InqLoanTopUpRequest.serviceagent/InqLoanTopUpRequestPortTypeEndpoint1" );

	}

	/**
	 * Default Constructor
	 */
	public InqLoanTopUpRequestStub() throws org.apache.axis2.AxisFault {

		this("http://10.XXX.107.21:5533/Services/CustomerCommonServices/CustomerInqServices/Service/InqLoanTopUpRequest.serviceagent/InqLoanTopUpRequestPortTypeEndpoint1" );

	}

	/**
	 * Constructor taking the target endpoint
	 */
	public InqLoanTopUpRequestStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
		this(null,targetEndpoint);
	}


	public  String getInputXml(com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchEMIDetailsReqMsg fetchEMIDetailsReqMsg2)


			throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try{
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
			_operationClient.getOptions().setAction("/Services/CustomerCommonServices/CustomerInqServices/Service/InqLoanTopUpRequest.serviceagent/InqLoanTopUpRequestPortTypeEndpoint1/FetchEMIDetails_Oper");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();



			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;


			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
					fetchEMIDetailsReqMsg2,
					optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1425296844141",
							"fetchEMIDetails_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1425296844141",
									"fetchEMIDetails_Oper"));

			//adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);
			return env.toString();


		}catch(org.apache.axis2.AxisFault f){
			return "";
		}
	}

	// getInputXMl Start
	public  String getInputXml(com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchCustOffersReqMsg fetchCustDetailsReqMsg2)

       	throws java.rmi.RemoteException

		{
			org.apache.axis2.context.MessageContext _messageContext = null;
			try{
				org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
				_operationClient.getOptions().setAction("/Services/CustomerCommonServices/CustomerInqServices/Service/InqLoanTopUpRequest.serviceagent/InqLoanTopUpRequestPortTypeEndpoint1/FetchCustOffers_Oper");
				_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



				// create a message context
				_messageContext = new org.apache.axis2.context.MessageContext();



				// create SOAP envelope with that payload
				org.apache.axiom.soap.SOAPEnvelope env = null;


				env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
						fetchCustDetailsReqMsg2,
						optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1425296844141",
								"FetchCustOffers_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1425296844141",
										"FetchCustOffers_Oper"));

				//adding SOAP soap_headers
				_serviceClient.addHeadersToEnvelope(env);
				// set the message context with that soap envelope
				_messageContext.setEnvelope(env);
				return env.toString();


			}catch(org.apache.axis2.AxisFault f){
				return "";
			}
		}

			//getInputXML End
	

	/**
	 * Auto generated method signature
	 * 
	 * @see com.newgen.dscop.stub.InqLoanTopUpRequest#fetchCustOffers_Oper
	 * @param fetchCustOffersReqMsg0

	 */



	public  com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchCustOffersResMsg fetchCustOffers_Oper(

			com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchCustOffersReqMsg fetchCustOffersReqMsg0)


					throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try{
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
			_operationClient.getOptions().setAction("/Services/CustomerCommonServices/CustomerInqServices/Service/InqLoanTopUpRequest.serviceagent/InqLoanTopUpRequestPortTypeEndpoint1/FetchCustOffers_Oper");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



			addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");


			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();



			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;


			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
					fetchCustOffersReqMsg0,
					optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1425296844141",
							"fetchCustOffers_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1425296844141",
									"fetchCustOffers_Oper"));

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

			outputXML=_returnEnv.toString();
			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement() ,
					com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchCustOffersResMsg.class,
					getEnvelopeNamespaces(_returnEnv));


			return (com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchCustOffersResMsg)object;

		}catch(org.apache.axis2.AxisFault f){

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt!=null){
				if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"FetchCustOffers_Oper"))){
					//make the fault by reflection
					try{
						java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"FetchCustOffers_Oper"));
						java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
						java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
						java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
						//message class
						java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"FetchCustOffers_Oper"));
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
	 * @see com.newgen.dscop.stub.InqLoanTopUpRequest#startfetchCustOffers_Oper
	 * @param fetchCustOffersReqMsg0

	 */
	public  void startfetchCustOffers_Oper(

			com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchCustOffersReqMsg fetchCustOffersReqMsg0,

			final com.newgen.dscop.stub.InqLoanTopUpRequestCallbackHandler callback)

					throws java.rmi.RemoteException{

		org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
		_operationClient.getOptions().setAction("/Services/CustomerCommonServices/CustomerInqServices/Service/InqLoanTopUpRequest.serviceagent/InqLoanTopUpRequestPortTypeEndpoint1/FetchCustOffers_Oper");
		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



		addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");



		// create SOAP envelope with that payload
		org.apache.axiom.soap.SOAPEnvelope env=null;
		final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();


		//Style is Doc.


		env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
				fetchCustOffersReqMsg0,
				optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1425296844141",
						"fetchCustOffers_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1425296844141",
								"fetchCustOffers_Oper"));

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
							com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchCustOffersResMsg.class,
							getEnvelopeNamespaces(resultEnv));
					callback.receiveResultfetchCustOffers_Oper(
							(com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchCustOffersResMsg)object);

				} catch (org.apache.axis2.AxisFault e) {
					callback.receiveErrorfetchCustOffers_Oper(e);
				}
			}

			public void onError(java.lang.Exception error) {
				if (error instanceof org.apache.axis2.AxisFault) {
					org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
					org.apache.axiom.om.OMElement faultElt = f.getDetail();
					if (faultElt!=null){
						if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"FetchCustOffers_Oper"))){
							//make the fault by reflection
							try{
								java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"FetchCustOffers_Oper"));
								java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
								java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
								java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
								//message class
								java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"FetchCustOffers_Oper"));
								java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
								java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
								java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
										new java.lang.Class[]{messageClass});
								m.invoke(ex,new java.lang.Object[]{messageObject});


								callback.receiveErrorfetchCustOffers_Oper(new java.rmi.RemoteException(ex.getMessage(), ex));
							} catch(java.lang.ClassCastException e){
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchCustOffers_Oper(f);
							} catch (java.lang.ClassNotFoundException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchCustOffers_Oper(f);
							} catch (java.lang.NoSuchMethodException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchCustOffers_Oper(f);
							} catch (java.lang.reflect.InvocationTargetException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchCustOffers_Oper(f);
							} catch (java.lang.IllegalAccessException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchCustOffers_Oper(f);
							} catch (java.lang.InstantiationException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchCustOffers_Oper(f);
							} catch (org.apache.axis2.AxisFault e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchCustOffers_Oper(f);
							}
						} else {
							callback.receiveErrorfetchCustOffers_Oper(f);
						}
					} else {
						callback.receiveErrorfetchCustOffers_Oper(f);
					}
				} else {
					callback.receiveErrorfetchCustOffers_Oper(error);
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
					callback.receiveErrorfetchCustOffers_Oper(axisFault);
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
	 * Auto generated method signature
	 * 
	 * @see com.newgen.dscop.stub.InqLoanTopUpRequest#fetchEMIDetails_Oper
	 * @param fetchEMIDetailsReqMsg2

	 */



	public  com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchEMIDetailsResMsg fetchEMIDetails_Oper(

			com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchEMIDetailsReqMsg fetchEMIDetailsReqMsg2)


					throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try{
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
			_operationClient.getOptions().setAction("/Services/CustomerCommonServices/CustomerInqServices/Service/InqLoanTopUpRequest.serviceagent/InqLoanTopUpRequestPortTypeEndpoint1/FetchEMIDetails_Oper");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



			addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");


			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();



			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;


			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
					fetchEMIDetailsReqMsg2,
					optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1425296844141",
							"fetchEMIDetails_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1425296844141",
									"fetchEMIDetails_Oper"));

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

			outputXML=_returnEnv.toString();
			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement() ,
					com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchEMIDetailsResMsg.class,
					getEnvelopeNamespaces(_returnEnv));



			return (com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchEMIDetailsResMsg)object;

		}catch(org.apache.axis2.AxisFault f){

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt!=null){
				if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"FetchEMIDetails_Oper"))){
					//make the fault by reflection
					try{
						java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"FetchEMIDetails_Oper"));
						java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
						java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
						java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
						//message class
						java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"FetchEMIDetails_Oper"));
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
	 * @see com.newgen.dscop.stub.InqLoanTopUpRequest#startfetchEMIDetails_Oper
	 * @param fetchEMIDetailsReqMsg2

	 */
	public  void startfetchEMIDetails_Oper(

			com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchEMIDetailsReqMsg fetchEMIDetailsReqMsg2,

			final com.newgen.dscop.stub.InqLoanTopUpRequestCallbackHandler callback)

					throws java.rmi.RemoteException{

		org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
		_operationClient.getOptions().setAction("/Services/CustomerCommonServices/CustomerInqServices/Service/InqLoanTopUpRequest.serviceagent/InqLoanTopUpRequestPortTypeEndpoint1/FetchEMIDetails_Oper");
		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



		addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");



		// create SOAP envelope with that payload
		org.apache.axiom.soap.SOAPEnvelope env=null;
		final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();


		//Style is Doc.


		env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
				fetchEMIDetailsReqMsg2,
				optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1425296844141",
						"fetchEMIDetails_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1425296844141",
								"fetchEMIDetails_Oper"));

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
							com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchEMIDetailsResMsg.class,
							getEnvelopeNamespaces(resultEnv));
					callback.receiveResultfetchEMIDetails_Oper(
							(com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchEMIDetailsResMsg)object);

				} catch (org.apache.axis2.AxisFault e) {
					callback.receiveErrorfetchEMIDetails_Oper(e);
				}
			}

			public void onError(java.lang.Exception error) {
				if (error instanceof org.apache.axis2.AxisFault) {
					org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
					org.apache.axiom.om.OMElement faultElt = f.getDetail();
					if (faultElt!=null){
						if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"FetchEMIDetails_Oper"))){
							//make the fault by reflection
							try{
								java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"FetchEMIDetails_Oper"));
								java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
								java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
								java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
								//message class
								java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"FetchEMIDetails_Oper"));
								java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
								java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
								java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
										new java.lang.Class[]{messageClass});
								m.invoke(ex,new java.lang.Object[]{messageObject});


								callback.receiveErrorfetchEMIDetails_Oper(new java.rmi.RemoteException(ex.getMessage(), ex));
							} catch(java.lang.ClassCastException e){
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchEMIDetails_Oper(f);
							} catch (java.lang.ClassNotFoundException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchEMIDetails_Oper(f);
							} catch (java.lang.NoSuchMethodException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchEMIDetails_Oper(f);
							} catch (java.lang.reflect.InvocationTargetException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchEMIDetails_Oper(f);
							} catch (java.lang.IllegalAccessException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchEMIDetails_Oper(f);
							} catch (java.lang.InstantiationException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchEMIDetails_Oper(f);
							} catch (org.apache.axis2.AxisFault e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchEMIDetails_Oper(f);
							}
						} else {
							callback.receiveErrorfetchEMIDetails_Oper(f);
						}
					} else {
						callback.receiveErrorfetchEMIDetails_Oper(f);
					}
				} else {
					callback.receiveErrorfetchEMIDetails_Oper(error);
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
					callback.receiveErrorfetchEMIDetails_Oper(axisFault);
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
	//http://10.XXX.107.21:5533/Services/CustomerCommonServices/CustomerInqServices/Service/InqLoanTopUpRequest.serviceagent/InqLoanTopUpRequestPortTypeEndpoint1
	public static class FetchEMIDetailsRes_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = fetchEMIDetailsRes_type0
                Namespace URI = http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for CustomerId
		 */


		protected java.lang.String localCustomerId ;


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

			this.localCustomerId=param;


		}


		/**
		 * field for LoanType
		 */


		protected java.lang.String localLoanType ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLoanType(){
			return localLoanType;
		}



		/**
		 * Auto generated setter method
		 * @param param LoanType
		 */
		public void setLoanType(java.lang.String param){

			this.localLoanType=param;


		}


		/**
		 * field for ProductCode
		 */


		protected java.lang.String localProductCode ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localProductCodeTracker = false ;

		public boolean isProductCodeSpecified(){
			return localProductCodeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getProductCode(){
			return localProductCode;
		}



		/**
		 * Auto generated setter method
		 * @param param ProductCode
		 */
		public void setProductCode(java.lang.String param){
			localProductCodeTracker = param != null;

			this.localProductCode=param;


		}


		/**
		 * field for LoanAmount
		 */


		protected java.lang.String localLoanAmount ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLoanAmountTracker = false ;

		public boolean isLoanAmountSpecified(){
			return localLoanAmountTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLoanAmount(){
			return localLoanAmount;
		}



		/**
		 * Auto generated setter method
		 * @param param LoanAmount
		 */
		public void setLoanAmount(java.lang.String param){
			localLoanAmountTracker = param != null;

			this.localLoanAmount=param;


		}


		/**
		 * field for LoanTenor
		 */


		protected java.lang.String localLoanTenor ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLoanTenorTracker = false ;

		public boolean isLoanTenorSpecified(){
			return localLoanTenorTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLoanTenor(){
			return localLoanTenor;
		}



		/**
		 * Auto generated setter method
		 * @param param LoanTenor
		 */
		public void setLoanTenor(java.lang.String param){
			localLoanTenorTracker = param != null;

			this.localLoanTenor=param;


		}


		/**
		 * field for LoanInterestRate
		 */


		protected java.lang.String localLoanInterestRate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLoanInterestRateTracker = false ;

		public boolean isLoanInterestRateSpecified(){
			return localLoanInterestRateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLoanInterestRate(){
			return localLoanInterestRate;
		}



		/**
		 * Auto generated setter method
		 * @param param LoanInterestRate
		 */
		public void setLoanInterestRate(java.lang.String param){
			localLoanInterestRateTracker = param != null;

			this.localLoanInterestRate=param;


		}


		/**
		 * field for LoanDisbursedDate
		 */


		protected java.lang.String localLoanDisbursedDate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLoanDisbursedDateTracker = false ;

		public boolean isLoanDisbursedDateSpecified(){
			return localLoanDisbursedDateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLoanDisbursedDate(){
			return localLoanDisbursedDate;
		}



		/**
		 * Auto generated setter method
		 * @param param LoanDisbursedDate
		 */
		public void setLoanDisbursedDate(java.lang.String param){
			localLoanDisbursedDateTracker = param != null;

			this.localLoanDisbursedDate=param;


		}


		/**
		 * field for LoanRepayFrequency
		 */


		protected java.lang.String localLoanRepayFrequency ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLoanRepayFrequencyTracker = false ;

		public boolean isLoanRepayFrequencySpecified(){
			return localLoanRepayFrequencyTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLoanRepayFrequency(){
			return localLoanRepayFrequency;
		}



		/**
		 * Auto generated setter method
		 * @param param LoanRepayFrequency
		 */
		public void setLoanRepayFrequency(java.lang.String param){
			localLoanRepayFrequencyTracker = param != null;

			this.localLoanRepayFrequency=param;


		}


		/**
		 * field for LoanFirstRepayDate
		 */


		protected java.lang.String localLoanFirstRepayDate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLoanFirstRepayDateTracker = false ;

		public boolean isLoanFirstRepayDateSpecified(){
			return localLoanFirstRepayDateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLoanFirstRepayDate(){
			return localLoanFirstRepayDate;
		}



		/**
		 * Auto generated setter method
		 * @param param LoanFirstRepayDate
		 */
		public void setLoanFirstRepayDate(java.lang.String param){
			localLoanFirstRepayDateTracker = param != null;

			this.localLoanFirstRepayDate=param;


		}


		/**
		 * field for LoanEMIValue
		 */


		protected java.lang.String localLoanEMIValue ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLoanEMIValueTracker = false ;

		public boolean isLoanEMIValueSpecified(){
			return localLoanEMIValueTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLoanEMIValue(){
			return localLoanEMIValue;
		}



		/**
		 * Auto generated setter method
		 * @param param LoanEMIValue
		 */
		public void setLoanEMIValue(java.lang.String param){
			localLoanEMIValueTracker = param != null;

			this.localLoanEMIValue=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":fetchEMIDetailsRes_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"fetchEMIDetailsRes_type0",
							xmlWriter);
				}


			}

			namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
			writeStartElement(null, namespace, "customerId", xmlWriter);


			if (localCustomerId==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localCustomerId);

			}

			xmlWriter.writeEndElement();

			namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
			writeStartElement(null, namespace, "loanType", xmlWriter);


			if (localLoanType==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("loanType cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localLoanType);

			}

			xmlWriter.writeEndElement();
			if (localProductCodeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "productCode", xmlWriter);


				if (localProductCode==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("productCode cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localProductCode);

				}

				xmlWriter.writeEndElement();
			} if (localLoanAmountTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "loanAmount", xmlWriter);


				if (localLoanAmount==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("loanAmount cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLoanAmount);

				}

				xmlWriter.writeEndElement();
			} if (localLoanTenorTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "loanTenor", xmlWriter);


				if (localLoanTenor==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("loanTenor cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLoanTenor);

				}

				xmlWriter.writeEndElement();
			} if (localLoanInterestRateTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "loanInterestRate", xmlWriter);


				if (localLoanInterestRate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("loanInterestRate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLoanInterestRate);

				}

				xmlWriter.writeEndElement();
			} if (localLoanDisbursedDateTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "loanDisbursedDate", xmlWriter);


				if (localLoanDisbursedDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("loanDisbursedDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLoanDisbursedDate);

				}

				xmlWriter.writeEndElement();
			} if (localLoanRepayFrequencyTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "loanRepayFrequency", xmlWriter);


				if (localLoanRepayFrequency==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("loanRepayFrequency cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLoanRepayFrequency);

				}

				xmlWriter.writeEndElement();
			} if (localLoanFirstRepayDateTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "loanFirstRepayDate", xmlWriter);


				if (localLoanFirstRepayDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("loanFirstRepayDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLoanFirstRepayDate);

				}

				xmlWriter.writeEndElement();
			} if (localLoanEMIValueTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "loanEMIValue", xmlWriter);


				if (localLoanEMIValue==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("loanEMIValue cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLoanEMIValue);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd")){
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


			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
					"customerId"));

			if (localCustomerId != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerId));
			} else {
				throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");
			}

			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
					"loanType"));

			if (localLoanType != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanType));
			} else {
				throw new org.apache.axis2.databinding.ADBException("loanType cannot be null!!");
			}
			if (localProductCodeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"productCode"));

				if (localProductCode != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProductCode));
				} else {
					throw new org.apache.axis2.databinding.ADBException("productCode cannot be null!!");
				}
			} if (localLoanAmountTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"loanAmount"));

				if (localLoanAmount != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanAmount));
				} else {
					throw new org.apache.axis2.databinding.ADBException("loanAmount cannot be null!!");
				}
			} if (localLoanTenorTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"loanTenor"));

				if (localLoanTenor != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanTenor));
				} else {
					throw new org.apache.axis2.databinding.ADBException("loanTenor cannot be null!!");
				}
			} if (localLoanInterestRateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"loanInterestRate"));

				if (localLoanInterestRate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanInterestRate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("loanInterestRate cannot be null!!");
				}
			} if (localLoanDisbursedDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"loanDisbursedDate"));

				if (localLoanDisbursedDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanDisbursedDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("loanDisbursedDate cannot be null!!");
				}
			} if (localLoanRepayFrequencyTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"loanRepayFrequency"));

				if (localLoanRepayFrequency != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanRepayFrequency));
				} else {
					throw new org.apache.axis2.databinding.ADBException("loanRepayFrequency cannot be null!!");
				}
			} if (localLoanFirstRepayDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"loanFirstRepayDate"));

				if (localLoanFirstRepayDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanFirstRepayDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("loanFirstRepayDate cannot be null!!");
				}
			} if (localLoanEMIValueTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"loanEMIValue"));

				if (localLoanEMIValue != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanEMIValue));
				} else {
					throw new org.apache.axis2.databinding.ADBException("loanEMIValue cannot be null!!");
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
			public static FetchEMIDetailsRes_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				FetchEMIDetailsRes_type0 object =
						new FetchEMIDetailsRes_type0();

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

							if (!"fetchEMIDetailsRes_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (FetchEMIDetailsRes_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","customerId").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"customerId" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCustomerId(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","loanType").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"loanType" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLoanType(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","productCode").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"productCode" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setProductCode(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","loanAmount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"loanAmount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLoanAmount(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","loanTenor").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"loanTenor" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLoanTenor(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","loanInterestRate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"loanInterestRate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLoanInterestRate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","loanDisbursedDate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"loanDisbursedDate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLoanDisbursedDate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","loanRepayFrequency").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"loanRepayFrequency" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLoanRepayFrequency(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","loanFirstRepayDate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"loanFirstRepayDate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLoanFirstRepayDate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","loanEMIValue").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"loanEMIValue" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLoanEMIValue(
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


	public static class FetchCustOffersReqMsg
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
				"fetchCustOffersReqMsg",
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
		 * field for FetchCustOffersReq
		 */


		protected FetchCustOffersReq_type0 localFetchCustOffersReq ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localFetchCustOffersReqTracker = false ;

		public boolean isFetchCustOffersReqSpecified(){
			return localFetchCustOffersReqTracker;
		}



		/**
		 * Auto generated getter method
		 * @return FetchCustOffersReq_type0
		 */
		public  FetchCustOffersReq_type0 getFetchCustOffersReq(){
			return localFetchCustOffersReq;
		}



		/**
		 * Auto generated setter method
		 * @param param FetchCustOffersReq
		 */
		public void setFetchCustOffersReq(FetchCustOffersReq_type0 param){
			localFetchCustOffersReqTracker = param != null;

			this.localFetchCustOffersReq=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":fetchCustOffersReqMsg",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"fetchCustOffersReqMsg",
							xmlWriter);
				}


			}

			if (localHeader==null){
				throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
			}
			localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
					xmlWriter);
			if (localFetchCustOffersReqTracker){
				if (localFetchCustOffersReq==null){
					throw new org.apache.axis2.databinding.ADBException("fetchCustOffersReq cannot be null!!");
				}
				localFetchCustOffersReq.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","fetchCustOffersReq"),
						xmlWriter);
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd")){
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
			if (localFetchCustOffersReqTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"fetchCustOffersReq"));


				if (localFetchCustOffersReq==null){
					throw new org.apache.axis2.databinding.ADBException("fetchCustOffersReq cannot be null!!");
				}
				elementList.add(localFetchCustOffersReq);
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
			public static FetchCustOffersReqMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				FetchCustOffersReqMsg object =
						new FetchCustOffersReqMsg();

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

							if (!"fetchCustOffersReqMsg".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (FetchCustOffersReqMsg)ExtensionMapper.getTypeObject(
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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","fetchCustOffersReq").equals(reader.getName())){

						object.setFetchCustOffersReq(FetchCustOffersReq_type0.Factory.parse(reader));

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


	public static class FetchEMIDetailsReqMsg
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
				"fetchEMIDetailsReqMsg",
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
		 * field for FetchEMIDetailsReq
		 */


		protected FetchEMIDetailsReq_type0 localFetchEMIDetailsReq ;


		/**
		 * Auto generated getter method
		 * @return FetchEMIDetailsReq_type0
		 */
		public  FetchEMIDetailsReq_type0 getFetchEMIDetailsReq(){
			return localFetchEMIDetailsReq;
		}



		/**
		 * Auto generated setter method
		 * @param param FetchEMIDetailsReq
		 */
		public void setFetchEMIDetailsReq(FetchEMIDetailsReq_type0 param){

			this.localFetchEMIDetailsReq=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":fetchEMIDetailsReqMsg",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"fetchEMIDetailsReqMsg",
							xmlWriter);
				}


			}

			if (localHeader==null){
				throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
			}
			localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
					xmlWriter);

			if (localFetchEMIDetailsReq==null){
				throw new org.apache.axis2.databinding.ADBException("fetchEMIDetailsReq cannot be null!!");
			}
			localFetchEMIDetailsReq.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","fetchEMIDetailsReq"),
					xmlWriter);

			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd")){
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

			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
					"fetchEMIDetailsReq"));


			if (localFetchEMIDetailsReq==null){
				throw new org.apache.axis2.databinding.ADBException("fetchEMIDetailsReq cannot be null!!");
			}
			elementList.add(localFetchEMIDetailsReq);


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
			public static FetchEMIDetailsReqMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				FetchEMIDetailsReqMsg object =
						new FetchEMIDetailsReqMsg();

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

							if (!"fetchEMIDetailsReqMsg".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (FetchEMIDetailsReqMsg)ExtensionMapper.getTypeObject(
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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","fetchEMIDetailsReq").equals(reader.getName())){

						object.setFetchEMIDetailsReq(FetchEMIDetailsReq_type0.Factory.parse(reader));

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
					"http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd".equals(namespaceURI) &&
					"fetchCustOffersRes_type0".equals(typeName)){

				return  FetchCustOffersRes_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd".equals(namespaceURI) &&
					"fetchEMIDetailsRes_type0".equals(typeName)){

				return  FetchEMIDetailsRes_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd".equals(namespaceURI) &&
					"fetchCustOffersReq_type0".equals(typeName)){

				return  FetchCustOffersReq_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd".equals(namespaceURI) &&
					"fetchEMIDetailsReq_type0".equals(typeName)){

				return  FetchEMIDetailsReq_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/header.xsd".equals(namespaceURI) &&
					"headerType".equals(typeName)){

				return  HeaderType.Factory.parse(reader);


			}


			throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
		}

	}

	public static class FetchCustOffersRes_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = fetchCustOffersRes_type0
                Namespace URI = http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd
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
		 * field for OfferId
		 */


		protected java.lang.String localOfferId ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localOfferIdTracker = false ;

		public boolean isOfferIdSpecified(){
			return localOfferIdTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getOfferId(){
			return localOfferId;
		}



		/**
		 * Auto generated setter method
		 * @param param OfferId
		 */
		public void setOfferId(java.lang.String param){
			localOfferIdTracker = param != null;

			this.localOfferId=param;


		}


		/**
		 * field for OfferType
		 */


		protected java.lang.String localOfferType ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localOfferTypeTracker = false ;

		public boolean isOfferTypeSpecified(){
			return localOfferTypeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getOfferType(){
			return localOfferType;
		}



		/**
		 * Auto generated setter method
		 * @param param OfferType
		 */
		public void setOfferType(java.lang.String param){
			localOfferTypeTracker = param != null;

			this.localOfferType=param;


		}


		/**
		 * field for OfferCode
		 */


		protected java.lang.String localOfferCode ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localOfferCodeTracker = false ;

		public boolean isOfferCodeSpecified(){
			return localOfferCodeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getOfferCode(){
			return localOfferCode;
		}



		/**
		 * Auto generated setter method
		 * @param param OfferCode
		 */
		public void setOfferCode(java.lang.String param){
			localOfferCodeTracker = param != null;

			this.localOfferCode=param;


		}


		/**
		 * field for LoanAcctNumber
		 */


		protected java.lang.String localLoanAcctNumber ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLoanAcctNumberTracker = false ;

		public boolean isLoanAcctNumberSpecified(){
			return localLoanAcctNumberTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLoanAcctNumber(){
			return localLoanAcctNumber;
		}



		/**
		 * Auto generated setter method
		 * @param param LoanAcctNumber
		 */
		public void setLoanAcctNumber(java.lang.String param){
			localLoanAcctNumberTracker = param != null;

			this.localLoanAcctNumber=param;


		}


		/**
		 * field for ProductDesc
		 */


		protected java.lang.String localProductDesc ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localProductDescTracker = false ;

		public boolean isProductDescSpecified(){
			return localProductDescTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getProductDesc(){
			return localProductDesc;
		}



		/**
		 * Auto generated setter method
		 * @param param ProductDesc
		 */
		public void setProductDesc(java.lang.String param){
			localProductDescTracker = param != null;

			this.localProductDesc=param;


		}


		/**
		 * field for ProductCode
		 */


		protected java.lang.String localProductCode ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localProductCodeTracker = false ;

		public boolean isProductCodeSpecified(){
			return localProductCodeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getProductCode(){
			return localProductCode;
		}



		/**
		 * Auto generated setter method
		 * @param param ProductCode
		 */
		public void setProductCode(java.lang.String param){
			localProductCodeTracker = param != null;

			this.localProductCode=param;


		}


		/**
		 * field for SalarySTL
		 */


		protected java.lang.String localSalarySTL ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localSalarySTLTracker = false ;

		public boolean isSalarySTLSpecified(){
			return localSalarySTLTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getSalarySTL(){
			return localSalarySTL;
		}



		/**
		 * Auto generated setter method
		 * @param param SalarySTL
		 */
		public void setSalarySTL(java.lang.String param){
			localSalarySTLTracker = param != null;

			this.localSalarySTL=param;


		}


		/**
		 * field for PFEligibility1
		 */


		protected java.lang.String localPFEligibility1 ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPFEligibility1Tracker = false ;

		public boolean isPFEligibility1Specified(){
			return localPFEligibility1Tracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPFEligibility1(){
			return localPFEligibility1;
		}



		/**
		 * Auto generated setter method
		 * @param param PFEligibility1
		 */
		public void setPFEligibility1(java.lang.String param){
			localPFEligibility1Tracker = param != null;

			this.localPFEligibility1=param;


		}


		/**
		 * field for PFEligibility2
		 */


		protected java.lang.String localPFEligibility2 ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPFEligibility2Tracker = false ;

		public boolean isPFEligibility2Specified(){
			return localPFEligibility2Tracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPFEligibility2(){
			return localPFEligibility2;
		}



		/**
		 * Auto generated setter method
		 * @param param PFEligibility2
		 */
		public void setPFEligibility2(java.lang.String param){
			localPFEligibility2Tracker = param != null;

			this.localPFEligibility2=param;


		}


		/**
		 * field for PFEligibility3
		 */


		protected java.lang.String localPFEligibility3 ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPFEligibility3Tracker = false ;

		public boolean isPFEligibility3Specified(){
			return localPFEligibility3Tracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPFEligibility3(){
			return localPFEligibility3;
		}



		/**
		 * Auto generated setter method
		 * @param param PFEligibility3
		 */
		public void setPFEligibility3(java.lang.String param){
			localPFEligibility3Tracker = param != null;

			this.localPFEligibility3=param;


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
		 * field for MaxiFinanceTenor
		 */


		protected java.lang.String localMaxiFinanceTenor ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localMaxiFinanceTenorTracker = false ;

		public boolean isMaxiFinanceTenorSpecified(){
			return localMaxiFinanceTenorTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getMaxiFinanceTenor(){
			return localMaxiFinanceTenor;
		}



		/**
		 * Auto generated setter method
		 * @param param MaxiFinanceTenor
		 */
		public void setMaxiFinanceTenor(java.lang.String param){
			localMaxiFinanceTenorTracker = param != null;

			this.localMaxiFinanceTenor=param;


		}


		/**
		 * field for ROP_ROI
		 */


		protected java.lang.String localROP_ROI ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localROP_ROITracker = false ;

		public boolean isROP_ROISpecified(){
			return localROP_ROITracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getROP_ROI(){
			return localROP_ROI;
		}



		/**
		 * Auto generated setter method
		 * @param param ROP_ROI
		 */
		public void setROP_ROI(java.lang.String param){
			localROP_ROITracker = param != null;

			this.localROP_ROI=param;


		}


		/**
		 * field for NewComdtyRatePerUnit
		 */


		protected java.lang.String localNewComdtyRatePerUnit ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNewComdtyRatePerUnitTracker = false ;

		public boolean isNewComdtyRatePerUnitSpecified(){
			return localNewComdtyRatePerUnitTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNewComdtyRatePerUnit(){
			return localNewComdtyRatePerUnit;
		}



		/**
		 * Auto generated setter method
		 * @param param NewComdtyRatePerUnit
		 */
		public void setNewComdtyRatePerUnit(java.lang.String param){
			localNewComdtyRatePerUnitTracker = param != null;

			this.localNewComdtyRatePerUnit=param;


		}


		/**
		 * field for NewComdtyUnit
		 */


		protected java.lang.String localNewComdtyUnit ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNewComdtyUnitTracker = false ;

		public boolean isNewComdtyUnitSpecified(){
			return localNewComdtyUnitTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNewComdtyUnit(){
			return localNewComdtyUnit;
		}



		/**
		 * Auto generated setter method
		 * @param param NewComdtyUnit
		 */
		public void setNewComdtyUnit(java.lang.String param){
			localNewComdtyUnitTracker = param != null;

			this.localNewComdtyUnit=param;


		}


		/**
		 * field for NewComdtyName
		 */


		protected java.lang.String localNewComdtyName ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNewComdtyNameTracker = false ;

		public boolean isNewComdtyNameSpecified(){
			return localNewComdtyNameTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNewComdtyName(){
			return localNewComdtyName;
		}



		/**
		 * Auto generated setter method
		 * @param param NewComdtyName
		 */
		public void setNewComdtyName(java.lang.String param){
			localNewComdtyNameTracker = param != null;

			this.localNewComdtyName=param;


		}


		/**
		 * field for ExistingComdtyRatePerUnit
		 */


		protected java.lang.String localExistingComdtyRatePerUnit ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localExistingComdtyRatePerUnitTracker = false ;

		public boolean isExistingComdtyRatePerUnitSpecified(){
			return localExistingComdtyRatePerUnitTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getExistingComdtyRatePerUnit(){
			return localExistingComdtyRatePerUnit;
		}



		/**
		 * Auto generated setter method
		 * @param param ExistingComdtyRatePerUnit
		 */
		public void setExistingComdtyRatePerUnit(java.lang.String param){
			localExistingComdtyRatePerUnitTracker = param != null;

			this.localExistingComdtyRatePerUnit=param;


		}


		/**
		 * field for ExistingComdtyUnit
		 */


		protected java.lang.String localExistingComdtyUnit ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localExistingComdtyUnitTracker = false ;

		public boolean isExistingComdtyUnitSpecified(){
			return localExistingComdtyUnitTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getExistingComdtyUnit(){
			return localExistingComdtyUnit;
		}



		/**
		 * Auto generated setter method
		 * @param param ExistingComdtyUnit
		 */
		public void setExistingComdtyUnit(java.lang.String param){
			localExistingComdtyUnitTracker = param != null;

			this.localExistingComdtyUnit=param;


		}


		/**
		 * field for ExistingComdtyName
		 */


		protected java.lang.String localExistingComdtyName ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localExistingComdtyNameTracker = false ;

		public boolean isExistingComdtyNameSpecified(){
			return localExistingComdtyNameTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getExistingComdtyName(){
			return localExistingComdtyName;
		}



		/**
		 * Auto generated setter method
		 * @param param ExistingComdtyName
		 */
		public void setExistingComdtyName(java.lang.String param){
			localExistingComdtyNameTracker = param != null;

			this.localExistingComdtyName=param;


		}


		/**
		 * field for DateOfEMI
		 */


		protected java.lang.String localDateOfEMI ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDateOfEMITracker = false ;

		public boolean isDateOfEMISpecified(){
			return localDateOfEMITracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDateOfEMI(){
			return localDateOfEMI;
		}



		/**
		 * Auto generated setter method
		 * @param param DateOfEMI
		 */
		public void setDateOfEMI(java.lang.String param){
			localDateOfEMITracker = param != null;

			this.localDateOfEMI=param;


		}


		/**
		 * field for ProcessingFeeType
		 */


		protected java.lang.String localProcessingFeeType ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localProcessingFeeTypeTracker = false ;

		public boolean isProcessingFeeTypeSpecified(){
			return localProcessingFeeTypeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getProcessingFeeType(){
			return localProcessingFeeType;
		}



		/**
		 * Auto generated setter method
		 * @param param ProcessingFeeType
		 */
		public void setProcessingFeeType(java.lang.String param){
			localProcessingFeeTypeTracker = param != null;

			this.localProcessingFeeType=param;


		}


		/**
		 * field for ProcessingFee
		 */


		protected java.lang.String localProcessingFee ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localProcessingFeeTracker = false ;

		public boolean isProcessingFeeSpecified(){
			return localProcessingFeeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getProcessingFee(){
			return localProcessingFee;
		}



		/**
		 * Auto generated setter method
		 * @param param ProcessingFee
		 */
		public void setProcessingFee(java.lang.String param){
			localProcessingFeeTracker = param != null;

			this.localProcessingFee=param;


		}


		/**
		 * field for MinProcessingFee
		 */


		protected java.lang.String localMinProcessingFee ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localMinProcessingFeeTracker = false ;

		public boolean isMinProcessingFeeSpecified(){
			return localMinProcessingFeeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getMinProcessingFee(){
			return localMinProcessingFee;
		}



		/**
		 * Auto generated setter method
		 * @param param MinProcessingFee
		 */
		public void setMinProcessingFee(java.lang.String param){
			localMinProcessingFeeTracker = param != null;

			this.localMinProcessingFee=param;


		}


		/**
		 * field for MaxProcessingFee
		 */


		protected java.lang.String localMaxProcessingFee ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localMaxProcessingFeeTracker = false ;

		public boolean isMaxProcessingFeeSpecified(){
			return localMaxProcessingFeeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getMaxProcessingFee(){
			return localMaxProcessingFee;
		}



		/**
		 * Auto generated setter method
		 * @param param MaxProcessingFee
		 */
		public void setMaxProcessingFee(java.lang.String param){
			localMaxProcessingFeeTracker = param != null;

			this.localMaxProcessingFee=param;


		}


		/**
		 * field for InsuranceFeeType
		 */


		protected java.lang.String localInsuranceFeeType ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localInsuranceFeeTypeTracker = false ;

		public boolean isInsuranceFeeTypeSpecified(){
			return localInsuranceFeeTypeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getInsuranceFeeType(){
			return localInsuranceFeeType;
		}



		/**
		 * Auto generated setter method
		 * @param param InsuranceFeeType
		 */
		public void setInsuranceFeeType(java.lang.String param){
			localInsuranceFeeTypeTracker = param != null;

			this.localInsuranceFeeType=param;


		}


		/**
		 * field for InsuranceFee
		 */


		protected java.lang.String localInsuranceFee ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localInsuranceFeeTracker = false ;

		public boolean isInsuranceFeeSpecified(){
			return localInsuranceFeeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getInsuranceFee(){
			return localInsuranceFee;
		}



		/**
		 * Auto generated setter method
		 * @param param InsuranceFee
		 */
		public void setInsuranceFee(java.lang.String param){
			localInsuranceFeeTracker = param != null;

			this.localInsuranceFee=param;


		}


		/**
		 * field for EarlySettlementFeeType
		 */


		protected java.lang.String localEarlySettlementFeeType ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localEarlySettlementFeeTypeTracker = false ;

		public boolean isEarlySettlementFeeTypeSpecified(){
			return localEarlySettlementFeeTypeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getEarlySettlementFeeType(){
			return localEarlySettlementFeeType;
		}



		/**
		 * Auto generated setter method
		 * @param param EarlySettlementFeeType
		 */
		public void setEarlySettlementFeeType(java.lang.String param){
			localEarlySettlementFeeTypeTracker = param != null;

			this.localEarlySettlementFeeType=param;


		}


		/**
		 * field for EarlySettlementFee
		 */


		protected java.lang.String localEarlySettlementFee ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localEarlySettlementFeeTracker = false ;

		public boolean isEarlySettlementFeeSpecified(){
			return localEarlySettlementFeeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getEarlySettlementFee(){
			return localEarlySettlementFee;
		}



		/**
		 * Auto generated setter method
		 * @param param EarlySettlementFee
		 */
		public void setEarlySettlementFee(java.lang.String param){
			localEarlySettlementFeeTracker = param != null;

			this.localEarlySettlementFee=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":fetchCustOffersRes_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"fetchCustOffersRes_type0",
							xmlWriter);
				}


			}
			if (localCustomerIdTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "customerId", xmlWriter);


				if (localCustomerId==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCustomerId);

				}

				xmlWriter.writeEndElement();
			} if (localOfferIdTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "offerId", xmlWriter);


				if (localOfferId==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("offerId cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localOfferId);

				}

				xmlWriter.writeEndElement();
			} if (localOfferTypeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "offerType", xmlWriter);


				if (localOfferType==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("offerType cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localOfferType);

				}

				xmlWriter.writeEndElement();
			} if (localOfferCodeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "offerCode", xmlWriter);


				if (localOfferCode==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("offerCode cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localOfferCode);

				}

				xmlWriter.writeEndElement();
			} if (localLoanAcctNumberTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "loanAcctNumber", xmlWriter);


				if (localLoanAcctNumber==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("loanAcctNumber cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLoanAcctNumber);

				}

				xmlWriter.writeEndElement();
			} if (localProductDescTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "productDesc", xmlWriter);


				if (localProductDesc==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("productDesc cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localProductDesc);

				}

				xmlWriter.writeEndElement();
			} if (localProductCodeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "productCode", xmlWriter);


				if (localProductCode==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("productCode cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localProductCode);

				}

				xmlWriter.writeEndElement();
			} if (localSalarySTLTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "salarySTL", xmlWriter);


				if (localSalarySTL==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("salarySTL cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localSalarySTL);

				}

				xmlWriter.writeEndElement();
			} if (localPFEligibility1Tracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "pFEligibility1", xmlWriter);


				if (localPFEligibility1==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("pFEligibility1 cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPFEligibility1);

				}

				xmlWriter.writeEndElement();
			} if (localPFEligibility2Tracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "pFEligibility2", xmlWriter);


				if (localPFEligibility2==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("pFEligibility2 cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPFEligibility2);

				}

				xmlWriter.writeEndElement();
			} if (localPFEligibility3Tracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "pFEligibility3", xmlWriter);


				if (localPFEligibility3==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("pFEligibility3 cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPFEligibility3);

				}

				xmlWriter.writeEndElement();
			} if (localEmployerNameTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "employerName", xmlWriter);


				if (localEmployerName==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("employerName cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localEmployerName);

				}

				xmlWriter.writeEndElement();
			} if (localMaxiFinanceTenorTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "maxiFinanceTenor", xmlWriter);


				if (localMaxiFinanceTenor==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("maxiFinanceTenor cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localMaxiFinanceTenor);

				}

				xmlWriter.writeEndElement();
			} if (localROP_ROITracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "ROP_ROI", xmlWriter);


				if (localROP_ROI==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("ROP_ROI cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localROP_ROI);

				}

				xmlWriter.writeEndElement();
			} if (localNewComdtyRatePerUnitTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "newComdtyRatePerUnit", xmlWriter);


				if (localNewComdtyRatePerUnit==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("newComdtyRatePerUnit cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNewComdtyRatePerUnit);

				}

				xmlWriter.writeEndElement();
			} if (localNewComdtyUnitTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "newComdtyUnit", xmlWriter);


				if (localNewComdtyUnit==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("newComdtyUnit cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNewComdtyUnit);

				}

				xmlWriter.writeEndElement();
			} if (localNewComdtyNameTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "newComdtyName", xmlWriter);


				if (localNewComdtyName==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("newComdtyName cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNewComdtyName);

				}

				xmlWriter.writeEndElement();
			} if (localExistingComdtyRatePerUnitTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "existingComdtyRatePerUnit", xmlWriter);


				if (localExistingComdtyRatePerUnit==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("existingComdtyRatePerUnit cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localExistingComdtyRatePerUnit);

				}

				xmlWriter.writeEndElement();
			} if (localExistingComdtyUnitTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "existingComdtyUnit", xmlWriter);


				if (localExistingComdtyUnit==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("existingComdtyUnit cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localExistingComdtyUnit);

				}

				xmlWriter.writeEndElement();
			} if (localExistingComdtyNameTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "existingComdtyName", xmlWriter);


				if (localExistingComdtyName==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("existingComdtyName cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localExistingComdtyName);

				}

				xmlWriter.writeEndElement();
			} if (localDateOfEMITracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "dateOfEMI", xmlWriter);


				if (localDateOfEMI==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("dateOfEMI cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDateOfEMI);

				}

				xmlWriter.writeEndElement();
			} if (localProcessingFeeTypeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "processingFeeType", xmlWriter);


				if (localProcessingFeeType==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("processingFeeType cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localProcessingFeeType);

				}

				xmlWriter.writeEndElement();
			} if (localProcessingFeeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "processingFee", xmlWriter);


				if (localProcessingFee==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("processingFee cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localProcessingFee);

				}

				xmlWriter.writeEndElement();
			} if (localMinProcessingFeeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "minProcessingFee", xmlWriter);


				if (localMinProcessingFee==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("minProcessingFee cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localMinProcessingFee);

				}

				xmlWriter.writeEndElement();
			} if (localMaxProcessingFeeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "maxProcessingFee", xmlWriter);


				if (localMaxProcessingFee==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("maxProcessingFee cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localMaxProcessingFee);

				}

				xmlWriter.writeEndElement();
			} if (localInsuranceFeeTypeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "insuranceFeeType", xmlWriter);


				if (localInsuranceFeeType==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("insuranceFeeType cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localInsuranceFeeType);

				}

				xmlWriter.writeEndElement();
			} if (localInsuranceFeeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "insuranceFee", xmlWriter);


				if (localInsuranceFee==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("insuranceFee cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localInsuranceFee);

				}

				xmlWriter.writeEndElement();
			} if (localEarlySettlementFeeTypeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "earlySettlementFeeType", xmlWriter);


				if (localEarlySettlementFeeType==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("earlySettlementFeeType cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localEarlySettlementFeeType);

				}

				xmlWriter.writeEndElement();
			} if (localEarlySettlementFeeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "earlySettlementFee", xmlWriter);


				if (localEarlySettlementFee==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("earlySettlementFee cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localEarlySettlementFee);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd")){
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
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"customerId"));

				if (localCustomerId != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerId));
				} else {
					throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");
				}
			} if (localOfferIdTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"offerId"));

				if (localOfferId != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOfferId));
				} else {
					throw new org.apache.axis2.databinding.ADBException("offerId cannot be null!!");
				}
			} if (localOfferTypeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"offerType"));

				if (localOfferType != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOfferType));
				} else {
					throw new org.apache.axis2.databinding.ADBException("offerType cannot be null!!");
				}
			} if (localOfferCodeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"offerCode"));

				if (localOfferCode != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOfferCode));
				} else {
					throw new org.apache.axis2.databinding.ADBException("offerCode cannot be null!!");
				}
			} if (localLoanAcctNumberTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"loanAcctNumber"));

				if (localLoanAcctNumber != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanAcctNumber));
				} else {
					throw new org.apache.axis2.databinding.ADBException("loanAcctNumber cannot be null!!");
				}
			} if (localProductDescTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"productDesc"));

				if (localProductDesc != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProductDesc));
				} else {
					throw new org.apache.axis2.databinding.ADBException("productDesc cannot be null!!");
				}
			} if (localProductCodeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"productCode"));

				if (localProductCode != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProductCode));
				} else {
					throw new org.apache.axis2.databinding.ADBException("productCode cannot be null!!");
				}
			} if (localSalarySTLTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"salarySTL"));

				if (localSalarySTL != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSalarySTL));
				} else {
					throw new org.apache.axis2.databinding.ADBException("salarySTL cannot be null!!");
				}
			} if (localPFEligibility1Tracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"pFEligibility1"));

				if (localPFEligibility1 != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPFEligibility1));
				} else {
					throw new org.apache.axis2.databinding.ADBException("pFEligibility1 cannot be null!!");
				}
			} if (localPFEligibility2Tracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"pFEligibility2"));

				if (localPFEligibility2 != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPFEligibility2));
				} else {
					throw new org.apache.axis2.databinding.ADBException("pFEligibility2 cannot be null!!");
				}
			} if (localPFEligibility3Tracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"pFEligibility3"));

				if (localPFEligibility3 != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPFEligibility3));
				} else {
					throw new org.apache.axis2.databinding.ADBException("pFEligibility3 cannot be null!!");
				}
			} if (localEmployerNameTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"employerName"));

				if (localEmployerName != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEmployerName));
				} else {
					throw new org.apache.axis2.databinding.ADBException("employerName cannot be null!!");
				}
			} if (localMaxiFinanceTenorTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"maxiFinanceTenor"));

				if (localMaxiFinanceTenor != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMaxiFinanceTenor));
				} else {
					throw new org.apache.axis2.databinding.ADBException("maxiFinanceTenor cannot be null!!");
				}
			} if (localROP_ROITracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"ROP_ROI"));

				if (localROP_ROI != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localROP_ROI));
				} else {
					throw new org.apache.axis2.databinding.ADBException("ROP_ROI cannot be null!!");
				}
			} if (localNewComdtyRatePerUnitTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"newComdtyRatePerUnit"));

				if (localNewComdtyRatePerUnit != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNewComdtyRatePerUnit));
				} else {
					throw new org.apache.axis2.databinding.ADBException("newComdtyRatePerUnit cannot be null!!");
				}
			} if (localNewComdtyUnitTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"newComdtyUnit"));

				if (localNewComdtyUnit != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNewComdtyUnit));
				} else {
					throw new org.apache.axis2.databinding.ADBException("newComdtyUnit cannot be null!!");
				}
			} if (localNewComdtyNameTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"newComdtyName"));

				if (localNewComdtyName != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNewComdtyName));
				} else {
					throw new org.apache.axis2.databinding.ADBException("newComdtyName cannot be null!!");
				}
			} if (localExistingComdtyRatePerUnitTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"existingComdtyRatePerUnit"));

				if (localExistingComdtyRatePerUnit != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExistingComdtyRatePerUnit));
				} else {
					throw new org.apache.axis2.databinding.ADBException("existingComdtyRatePerUnit cannot be null!!");
				}
			} if (localExistingComdtyUnitTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"existingComdtyUnit"));

				if (localExistingComdtyUnit != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExistingComdtyUnit));
				} else {
					throw new org.apache.axis2.databinding.ADBException("existingComdtyUnit cannot be null!!");
				}
			} if (localExistingComdtyNameTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"existingComdtyName"));

				if (localExistingComdtyName != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExistingComdtyName));
				} else {
					throw new org.apache.axis2.databinding.ADBException("existingComdtyName cannot be null!!");
				}
			} if (localDateOfEMITracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"dateOfEMI"));

				if (localDateOfEMI != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDateOfEMI));
				} else {
					throw new org.apache.axis2.databinding.ADBException("dateOfEMI cannot be null!!");
				}
			} if (localProcessingFeeTypeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"processingFeeType"));

				if (localProcessingFeeType != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProcessingFeeType));
				} else {
					throw new org.apache.axis2.databinding.ADBException("processingFeeType cannot be null!!");
				}
			} if (localProcessingFeeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"processingFee"));

				if (localProcessingFee != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProcessingFee));
				} else {
					throw new org.apache.axis2.databinding.ADBException("processingFee cannot be null!!");
				}
			} if (localMinProcessingFeeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"minProcessingFee"));

				if (localMinProcessingFee != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMinProcessingFee));
				} else {
					throw new org.apache.axis2.databinding.ADBException("minProcessingFee cannot be null!!");
				}
			} if (localMaxProcessingFeeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"maxProcessingFee"));

				if (localMaxProcessingFee != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMaxProcessingFee));
				} else {
					throw new org.apache.axis2.databinding.ADBException("maxProcessingFee cannot be null!!");
				}
			} if (localInsuranceFeeTypeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"insuranceFeeType"));

				if (localInsuranceFeeType != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInsuranceFeeType));
				} else {
					throw new org.apache.axis2.databinding.ADBException("insuranceFeeType cannot be null!!");
				}
			} if (localInsuranceFeeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"insuranceFee"));

				if (localInsuranceFee != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInsuranceFee));
				} else {
					throw new org.apache.axis2.databinding.ADBException("insuranceFee cannot be null!!");
				}
			} if (localEarlySettlementFeeTypeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"earlySettlementFeeType"));

				if (localEarlySettlementFeeType != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEarlySettlementFeeType));
				} else {
					throw new org.apache.axis2.databinding.ADBException("earlySettlementFeeType cannot be null!!");
				}
			} if (localEarlySettlementFeeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"earlySettlementFee"));

				if (localEarlySettlementFee != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEarlySettlementFee));
				} else {
					throw new org.apache.axis2.databinding.ADBException("earlySettlementFee cannot be null!!");
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
			public static FetchCustOffersRes_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				FetchCustOffersRes_type0 object =
						new FetchCustOffersRes_type0();

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

							if (!"fetchCustOffersRes_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (FetchCustOffersRes_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","customerId").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"customerId" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCustomerId(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","offerId").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"offerId" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setOfferId(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","offerType").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"offerType" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setOfferType(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","offerCode").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"offerCode" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setOfferCode(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","loanAcctNumber").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"loanAcctNumber" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLoanAcctNumber(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","productDesc").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"productDesc" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setProductDesc(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","productCode").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"productCode" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setProductCode(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","salarySTL").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"salarySTL" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setSalarySTL(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","pFEligibility1").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"pFEligibility1" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPFEligibility1(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","pFEligibility2").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"pFEligibility2" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPFEligibility2(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","pFEligibility3").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"pFEligibility3" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPFEligibility3(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","employerName").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"employerName" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setEmployerName(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","maxiFinanceTenor").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"maxiFinanceTenor" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setMaxiFinanceTenor(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","ROP_ROI").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"ROP_ROI" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setROP_ROI(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","newComdtyRatePerUnit").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"newComdtyRatePerUnit" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNewComdtyRatePerUnit(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","newComdtyUnit").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"newComdtyUnit" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNewComdtyUnit(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","newComdtyName").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"newComdtyName" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNewComdtyName(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","existingComdtyRatePerUnit").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"existingComdtyRatePerUnit" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setExistingComdtyRatePerUnit(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","existingComdtyUnit").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"existingComdtyUnit" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setExistingComdtyUnit(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","existingComdtyName").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"existingComdtyName" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setExistingComdtyName(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","dateOfEMI").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"dateOfEMI" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDateOfEMI(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","processingFeeType").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"processingFeeType" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setProcessingFeeType(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","processingFee").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"processingFee" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setProcessingFee(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","minProcessingFee").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"minProcessingFee" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setMinProcessingFee(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","maxProcessingFee").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"maxProcessingFee" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setMaxProcessingFee(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","insuranceFeeType").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"insuranceFeeType" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setInsuranceFeeType(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","insuranceFee").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"insuranceFee" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setInsuranceFee(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","earlySettlementFeeType").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"earlySettlementFeeType" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setEarlySettlementFeeType(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","earlySettlementFee").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"earlySettlementFee" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setEarlySettlementFee(
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


	public static class FetchEMIDetailsReq_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = fetchEMIDetailsReq_type0
                Namespace URI = http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for CustomerId
		 */


		protected java.lang.String localCustomerId ;


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

			this.localCustomerId=param;


		}


		/**
		 * field for LoanType
		 */


		protected java.lang.String localLoanType ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLoanType(){
			return localLoanType;
		}



		/**
		 * Auto generated setter method
		 * @param param LoanType
		 */
		public void setLoanType(java.lang.String param){

			this.localLoanType=param;


		}


		/**
		 * field for ProductCode
		 */


		protected java.lang.String localProductCode ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localProductCodeTracker = false ;

		public boolean isProductCodeSpecified(){
			return localProductCodeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getProductCode(){
			return localProductCode;
		}



		/**
		 * Auto generated setter method
		 * @param param ProductCode
		 */
		public void setProductCode(java.lang.String param){
			localProductCodeTracker = param != null;

			this.localProductCode=param;


		}


		/**
		 * field for LoanAmount
		 */


		protected java.lang.String localLoanAmount ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLoanAmountTracker = false ;

		public boolean isLoanAmountSpecified(){
			return localLoanAmountTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLoanAmount(){
			return localLoanAmount;
		}



		/**
		 * Auto generated setter method
		 * @param param LoanAmount
		 */
		public void setLoanAmount(java.lang.String param){
			localLoanAmountTracker = param != null;

			this.localLoanAmount=param;


		}


		/**
		 * field for LoanTenor
		 */


		protected java.lang.String localLoanTenor ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLoanTenorTracker = false ;

		public boolean isLoanTenorSpecified(){
			return localLoanTenorTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLoanTenor(){
			return localLoanTenor;
		}



		/**
		 * Auto generated setter method
		 * @param param LoanTenor
		 */
		public void setLoanTenor(java.lang.String param){
			localLoanTenorTracker = param != null;

			this.localLoanTenor=param;


		}


		/**
		 * field for LoanInterestRate
		 */


		protected java.lang.String localLoanInterestRate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLoanInterestRateTracker = false ;

		public boolean isLoanInterestRateSpecified(){
			return localLoanInterestRateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLoanInterestRate(){
			return localLoanInterestRate;
		}



		/**
		 * Auto generated setter method
		 * @param param LoanInterestRate
		 */
		public void setLoanInterestRate(java.lang.String param){
			localLoanInterestRateTracker = param != null;

			this.localLoanInterestRate=param;


		}


		/**
		 * field for LoanDisbursedDate
		 */


		protected java.lang.String localLoanDisbursedDate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLoanDisbursedDateTracker = false ;

		public boolean isLoanDisbursedDateSpecified(){
			return localLoanDisbursedDateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLoanDisbursedDate(){
			return localLoanDisbursedDate;
		}



		/**
		 * Auto generated setter method
		 * @param param LoanDisbursedDate
		 */
		public void setLoanDisbursedDate(java.lang.String param){
			localLoanDisbursedDateTracker = param != null;

			this.localLoanDisbursedDate=param;


		}


		/**
		 * field for LoanRepayFrequency
		 */


		protected java.lang.String localLoanRepayFrequency ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLoanRepayFrequencyTracker = false ;

		public boolean isLoanRepayFrequencySpecified(){
			return localLoanRepayFrequencyTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLoanRepayFrequency(){
			return localLoanRepayFrequency;
		}



		/**
		 * Auto generated setter method
		 * @param param LoanRepayFrequency
		 */
		public void setLoanRepayFrequency(java.lang.String param){
			localLoanRepayFrequencyTracker = param != null;

			this.localLoanRepayFrequency=param;


		}


		/**
		 * field for LoanFirstRepayDate
		 */


		protected java.lang.String localLoanFirstRepayDate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLoanFirstRepayDateTracker = false ;

		public boolean isLoanFirstRepayDateSpecified(){
			return localLoanFirstRepayDateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLoanFirstRepayDate(){
			return localLoanFirstRepayDate;
		}



		/**
		 * Auto generated setter method
		 * @param param LoanFirstRepayDate
		 */
		public void setLoanFirstRepayDate(java.lang.String param){
			localLoanFirstRepayDateTracker = param != null;

			this.localLoanFirstRepayDate=param;


		}


		/**
		 * field for Currency
		 */


		protected java.lang.String localCurrency ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCurrencyTracker = false ;

		public boolean isCurrencySpecified(){
			return localCurrencyTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCurrency(){
			return localCurrency;
		}



		/**
		 * Auto generated setter method
		 * @param param Currency
		 */
		public void setCurrency(java.lang.String param){
			localCurrencyTracker = param != null;

			this.localCurrency=param;


		}


		/**
		 * field for MoraProfitRate
		 */


		protected java.lang.String localMoraProfitRate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localMoraProfitRateTracker = false ;

		public boolean isMoraProfitRateSpecified(){
			return localMoraProfitRateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getMoraProfitRate(){
			return localMoraProfitRate;
		}



		/**
		 * Auto generated setter method
		 * @param param MoraProfitRate
		 */
		public void setMoraProfitRate(java.lang.String param){
			localMoraProfitRateTracker = param != null;

			this.localMoraProfitRate=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":fetchEMIDetailsReq_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"fetchEMIDetailsReq_type0",
							xmlWriter);
				}


			}

			namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
			writeStartElement(null, namespace, "customerId", xmlWriter);


			if (localCustomerId==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localCustomerId);

			}

			xmlWriter.writeEndElement();

			namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
			writeStartElement(null, namespace, "loanType", xmlWriter);


			if (localLoanType==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("loanType cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localLoanType);

			}

			xmlWriter.writeEndElement();
			if (localProductCodeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "productCode", xmlWriter);


				if (localProductCode==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("productCode cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localProductCode);

				}

				xmlWriter.writeEndElement();
			} if (localLoanAmountTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "loanAmount", xmlWriter);


				if (localLoanAmount==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("loanAmount cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLoanAmount);

				}

				xmlWriter.writeEndElement();
			} if (localLoanTenorTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "loanTenor", xmlWriter);


				if (localLoanTenor==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("loanTenor cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLoanTenor);

				}

				xmlWriter.writeEndElement();
			} if (localLoanInterestRateTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "loanInterestRate", xmlWriter);


				if (localLoanInterestRate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("loanInterestRate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLoanInterestRate);

				}

				xmlWriter.writeEndElement();
			} if (localLoanDisbursedDateTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "loanDisbursedDate", xmlWriter);


				if (localLoanDisbursedDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("loanDisbursedDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLoanDisbursedDate);

				}

				xmlWriter.writeEndElement();
			} if (localLoanRepayFrequencyTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "loanRepayFrequency", xmlWriter);


				if (localLoanRepayFrequency==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("loanRepayFrequency cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLoanRepayFrequency);

				}

				xmlWriter.writeEndElement();
			} if (localLoanFirstRepayDateTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "loanFirstRepayDate", xmlWriter);


				if (localLoanFirstRepayDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("loanFirstRepayDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLoanFirstRepayDate);

				}

				xmlWriter.writeEndElement();
			} if (localCurrencyTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "currency", xmlWriter);


				if (localCurrency==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("currency cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCurrency);

				}

				xmlWriter.writeEndElement();
			} if (localMoraProfitRateTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
				writeStartElement(null, namespace, "moraProfitRate", xmlWriter);


				if (localMoraProfitRate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("moraProfitRate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localMoraProfitRate);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd")){
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


			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
					"customerId"));

			if (localCustomerId != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerId));
			} else {
				throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");
			}

			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
					"loanType"));

			if (localLoanType != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanType));
			} else {
				throw new org.apache.axis2.databinding.ADBException("loanType cannot be null!!");
			}
			if (localProductCodeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"productCode"));

				if (localProductCode != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProductCode));
				} else {
					throw new org.apache.axis2.databinding.ADBException("productCode cannot be null!!");
				}
			} if (localLoanAmountTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"loanAmount"));

				if (localLoanAmount != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanAmount));
				} else {
					throw new org.apache.axis2.databinding.ADBException("loanAmount cannot be null!!");
				}
			} if (localLoanTenorTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"loanTenor"));

				if (localLoanTenor != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanTenor));
				} else {
					throw new org.apache.axis2.databinding.ADBException("loanTenor cannot be null!!");
				}
			} if (localLoanInterestRateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"loanInterestRate"));

				if (localLoanInterestRate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanInterestRate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("loanInterestRate cannot be null!!");
				}
			} if (localLoanDisbursedDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"loanDisbursedDate"));

				if (localLoanDisbursedDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanDisbursedDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("loanDisbursedDate cannot be null!!");
				}
			} if (localLoanRepayFrequencyTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"loanRepayFrequency"));

				if (localLoanRepayFrequency != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanRepayFrequency));
				} else {
					throw new org.apache.axis2.databinding.ADBException("loanRepayFrequency cannot be null!!");
				}
			} if (localLoanFirstRepayDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"loanFirstRepayDate"));

				if (localLoanFirstRepayDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanFirstRepayDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("loanFirstRepayDate cannot be null!!");
				}
			} if (localCurrencyTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"currency"));

				if (localCurrency != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCurrency));
				} else {
					throw new org.apache.axis2.databinding.ADBException("currency cannot be null!!");
				}
			} if (localMoraProfitRateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"moraProfitRate"));

				if (localMoraProfitRate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMoraProfitRate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("moraProfitRate cannot be null!!");
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
			public static FetchEMIDetailsReq_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				FetchEMIDetailsReq_type0 object =
						new FetchEMIDetailsReq_type0();

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

							if (!"fetchEMIDetailsReq_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (FetchEMIDetailsReq_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","customerId").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"customerId" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCustomerId(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","loanType").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"loanType" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLoanType(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","productCode").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"productCode" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setProductCode(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","loanAmount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"loanAmount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLoanAmount(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","loanTenor").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"loanTenor" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLoanTenor(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","loanInterestRate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"loanInterestRate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLoanInterestRate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","loanDisbursedDate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"loanDisbursedDate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLoanDisbursedDate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","loanRepayFrequency").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"loanRepayFrequency" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLoanRepayFrequency(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","loanFirstRepayDate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"loanFirstRepayDate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLoanFirstRepayDate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","currency").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"currency" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCurrency(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","moraProfitRate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"moraProfitRate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setMoraProfitRate(
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


	public static class FetchEMIDetailsResMsg
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
				"fetchEMIDetailsResMsg",
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
		 * field for FetchEMIDetailsRes
		 */


		protected FetchEMIDetailsRes_type0 localFetchEMIDetailsRes ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localFetchEMIDetailsResTracker = false ;

		public boolean isFetchEMIDetailsResSpecified(){
			return localFetchEMIDetailsResTracker;
		}



		/**
		 * Auto generated getter method
		 * @return FetchEMIDetailsRes_type0
		 */
		public  FetchEMIDetailsRes_type0 getFetchEMIDetailsRes(){
			return localFetchEMIDetailsRes;
		}



		/**
		 * Auto generated setter method
		 * @param param FetchEMIDetailsRes
		 */
		public void setFetchEMIDetailsRes(FetchEMIDetailsRes_type0 param){
			localFetchEMIDetailsResTracker = param != null;

			this.localFetchEMIDetailsRes=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":fetchEMIDetailsResMsg",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"fetchEMIDetailsResMsg",
							xmlWriter);
				}


			}

			if (localHeader==null){
				throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
			}
			localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
					xmlWriter);
			if (localFetchEMIDetailsResTracker){
				if (localFetchEMIDetailsRes==null){
					throw new org.apache.axis2.databinding.ADBException("fetchEMIDetailsRes cannot be null!!");
				}
				localFetchEMIDetailsRes.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","fetchEMIDetailsRes"),
						xmlWriter);
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd")){
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
			if (localFetchEMIDetailsResTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"fetchEMIDetailsRes"));


				if (localFetchEMIDetailsRes==null){
					throw new org.apache.axis2.databinding.ADBException("fetchEMIDetailsRes cannot be null!!");
				}
				elementList.add(localFetchEMIDetailsRes);
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
			public static FetchEMIDetailsResMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				FetchEMIDetailsResMsg object =
						new FetchEMIDetailsResMsg();

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

							if (!"fetchEMIDetailsResMsg".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (FetchEMIDetailsResMsg)ExtensionMapper.getTypeObject(
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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","fetchEMIDetailsRes").equals(reader.getName())){

						object.setFetchEMIDetailsRes(FetchEMIDetailsRes_type0.Factory.parse(reader));

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


	public static class FetchCustOffersResMsg
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
				"fetchCustOffersResMsg",
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
		 * field for FetchCustOffersRes
		 */


		protected FetchCustOffersRes_type0 localFetchCustOffersRes ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localFetchCustOffersResTracker = false ;

		public boolean isFetchCustOffersResSpecified(){
			return localFetchCustOffersResTracker;
		}



		/**
		 * Auto generated getter method
		 * @return FetchCustOffersRes_type0
		 */
		public  FetchCustOffersRes_type0 getFetchCustOffersRes(){
			return localFetchCustOffersRes;
		}



		/**
		 * Auto generated setter method
		 * @param param FetchCustOffersRes
		 */
		public void setFetchCustOffersRes(FetchCustOffersRes_type0 param){
			localFetchCustOffersResTracker = param != null;

			this.localFetchCustOffersRes=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":fetchCustOffersResMsg",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"fetchCustOffersResMsg",
							xmlWriter);
				}


			}

			if (localHeader==null){
				throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
			}
			localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
					xmlWriter);
			if (localFetchCustOffersResTracker){
				if (localFetchCustOffersRes==null){
					throw new org.apache.axis2.databinding.ADBException("fetchCustOffersRes cannot be null!!");
				}
				localFetchCustOffersRes.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","fetchCustOffersRes"),
						xmlWriter);
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd")){
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
			if (localFetchCustOffersResTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
						"fetchCustOffersRes"));


				if (localFetchCustOffersRes==null){
					throw new org.apache.axis2.databinding.ADBException("fetchCustOffersRes cannot be null!!");
				}
				elementList.add(localFetchCustOffersRes);
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
			public static FetchCustOffersResMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				FetchCustOffersResMsg object =
						new FetchCustOffersResMsg();

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

							if (!"fetchCustOffersResMsg".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (FetchCustOffersResMsg)ExtensionMapper.getTypeObject(
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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","fetchCustOffersRes").equals(reader.getName())){

						object.setFetchCustOffersRes(FetchCustOffersRes_type0.Factory.parse(reader));

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


	public static class FetchCustOffersReq_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = fetchCustOffersReq_type0
                Namespace URI = http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for CustomerId
		 */


		protected java.lang.String localCustomerId ;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":fetchCustOffersReq_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"fetchCustOffersReq_type0",
							xmlWriter);
				}


			}

			namespace = "http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd";
			writeStartElement(null, namespace, "customerId", xmlWriter);


			if (localCustomerId==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localCustomerId);

			}

			xmlWriter.writeEndElement();

			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd")){
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


			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd",
					"customerId"));

			if (localCustomerId != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerId));
			} else {
				throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");
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
			public static FetchCustOffersReq_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				FetchCustOffersReq_type0 object =
						new FetchCustOffersReq_type0();

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

							if (!"fetchCustOffersReq_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (FetchCustOffersReq_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerInqServices/InqLoanTopUpRequest.xsd","customerId").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"customerId" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCustomerId(
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


	private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchCustOffersReqMsg param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {


		try{
			return param.getOMElement(com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchCustOffersReqMsg.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}


	}

	private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchCustOffersResMsg param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {


		try{
			return param.getOMElement(com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchCustOffersResMsg.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}


	}

	private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchEMIDetailsReqMsg param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {


		try{
			return param.getOMElement(com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchEMIDetailsReqMsg.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}


	}

	private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchEMIDetailsResMsg param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {


		try{
			return param.getOMElement(com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchEMIDetailsResMsg.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}


	}


	private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchCustOffersReqMsg param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault{


		try{

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchCustOffersReqMsg.MY_QNAME,factory));
			return emptyEnvelope;
		} catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}


	}


	/* methods to provide back word compatibility */



	private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchEMIDetailsReqMsg param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault{


		try{

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchEMIDetailsReqMsg.MY_QNAME,factory));
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

			if (com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchCustOffersReqMsg.class.equals(type)){

				return com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchCustOffersReqMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());


			}

			if (com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchCustOffersResMsg.class.equals(type)){

				return com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchCustOffersResMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());


			}

			if (com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchEMIDetailsReqMsg.class.equals(type)){

				return com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchEMIDetailsReqMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());


			}

			if (com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchEMIDetailsResMsg.class.equals(type)){

				return com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchEMIDetailsResMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());


			}

		} catch (java.lang.Exception e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
		return null;
	}




}
