
/**
 * InqCustomerUAEPassInfoStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
package com.newgen.dscop.stub;



/*
 *  InqCustomerUAEPassInfoStub java implementation
 */


public class InqCustomerUAEPassInfoStub extends org.apache.axis2.client.Stub
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

	public  String getInputXml(	com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchCustomerUAEPassInfoReqMsg fetchCustomerUAEPassInfoReqMsg2)	throws java.rmi.RemoteException
	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try{
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
			_operationClient.getOptions().setAction("/Services/CustomerAuthenticationServices/CustomerAuthenticationServicesInquiry/Service/InqCustomerUAEPassInfo.serviceagent/InqCustomerUAEPassInfoPortTypeEndpoint0/fetchCustomerUAEPassInfo_Oper");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



			addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");


			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();


			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;


			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
					fetchCustomerUAEPassInfoReqMsg2,
					optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342",
							"fetchCustomerUAEPassInfo_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342",
									"fetchCustomerUAEPassInfo_Oper"));
			return env.toString();
		}
		catch(Exception e){
			return "";
		}
	}   

	public String getInputXml(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.RequestUAEPassDocumentsReqMsg requestUAEPassDocumentsReqMsg6)
	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try{
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[3].getName());
			_operationClient.getOptions().setAction("/Services/CustomerAuthenticationServices/CustomerAuthenticationServicesInquiry/Service/InqCustomerUAEPassInfo.serviceagent/InqCustomerUAEPassInfoPortTypeEndpoint0/requestUAEPassDocuments_Oper");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

			addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");


			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;


			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
					requestUAEPassDocumentsReqMsg6,
					optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342",
							"requestUAEPassDocuments_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342",
									"requestUAEPassDocuments_Oper"));

			return env.toString();

		}catch(org.apache.axis2.AxisFault f){
			return "";
		}
	}

	public String getInputXml(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchUAEPassDocumentDtlsReqMsg fetchUAEPassDocumentDtlsReqMsg4) throws java.rmi.RemoteException
	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try{
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[2].getName());
			_operationClient.getOptions().setAction("/Services/CustomerAuthenticationServices/CustomerAuthenticationServicesInquiry/Service/InqCustomerUAEPassInfo.serviceagent/InqCustomerUAEPassInfoPortTypeEndpoint0/fetchUAEPassDocumentDtls_Oper");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

			addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");

			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;


			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
					fetchUAEPassDocumentDtlsReqMsg4,
					optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342",
							"fetchUAEPassDocumentDtls_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342",
									"fetchUAEPassDocumentDtls_Oper"));

			return env.toString();

		}catch(org.apache.axis2.AxisFault f){
			return "";
		}
	}

	private void populateAxisService() throws org.apache.axis2.AxisFault {

		//creating the Service with a unique name
		_service = new org.apache.axis2.description.AxisService("InqCustomerUAEPassInfo" + getUniqueSuffix());
		addAnonymousOperations();

		//creating the operations
		org.apache.axis2.description.AxisOperation __operation;

		_operations = new org.apache.axis2.description.AxisOperation[4];

		__operation = new org.apache.axis2.description.OutInAxisOperation();


		__operation.setName(new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342", "updateUAEPassDocumentDtls_Oper"));
		_service.addOperation(__operation);




		_operations[0]=__operation;


		__operation = new org.apache.axis2.description.OutInAxisOperation();


		__operation.setName(new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342", "fetchCustomerUAEPassInfo_Oper"));
		_service.addOperation(__operation);




		_operations[1]=__operation;


		__operation = new org.apache.axis2.description.OutInAxisOperation();


		__operation.setName(new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342", "fetchUAEPassDocumentDtls_Oper"));
		_service.addOperation(__operation);




		_operations[2]=__operation;


		__operation = new org.apache.axis2.description.OutInAxisOperation();


		__operation.setName(new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342", "requestUAEPassDocuments_Oper"));
		_service.addOperation(__operation);




		_operations[3]=__operation;


	}

	//populates the faults
	private void populateFaults(){



	}

	/**
	 *Constructor that takes in a configContext
	 */

	public InqCustomerUAEPassInfoStub(org.apache.axis2.context.ConfigurationContext configurationContext,
			java.lang.String targetEndpoint)
					throws org.apache.axis2.AxisFault {
		this(configurationContext,targetEndpoint,false);
	}


	/**
	 * Constructor that takes in a configContext  and useseperate listner
	 */
	public InqCustomerUAEPassInfoStub(org.apache.axis2.context.ConfigurationContext configurationContext,
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
	public InqCustomerUAEPassInfoStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {

		this(configurationContext,"http://10.101.107.21:5563/Services/CustomerAuthenticationServices/CustomerAuthenticationServicesInquiry/Service/InqCustomerUAEPassInfo.serviceagent/InqCustomerUAEPassInfoPortTypeEndpoint0" );

	}

	/**
	 * Default Constructor
	 */
	public InqCustomerUAEPassInfoStub() throws org.apache.axis2.AxisFault {

		this("http://10.101.107.21:5563/Services/CustomerAuthenticationServices/CustomerAuthenticationServicesInquiry/Service/InqCustomerUAEPassInfo.serviceagent/InqCustomerUAEPassInfoPortTypeEndpoint0" );

	}

	/**
	 * Constructor taking the target endpoint
	 */
	public InqCustomerUAEPassInfoStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
		this(null,targetEndpoint);
	}




	/**
	 * Auto generated method signature
	 * 
	 * @see com.newgen.dscop.stub.InqCustomerUAEPassInfo#updateUAEPassDocumentDtls_Oper
	 * @param updateUAEPassDocumentDtlsReqMsg0

	 */



	public  com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.UpdateUAEPassDocumentDtlsResMsg updateUAEPassDocumentDtls_Oper(

			com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.UpdateUAEPassDocumentDtlsReqMsg updateUAEPassDocumentDtlsReqMsg0)


					throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try{
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
			_operationClient.getOptions().setAction("/Services/CustomerAuthenticationServices/CustomerAuthenticationServicesInquiry/Service/InqCustomerUAEPassInfo.serviceagent/InqCustomerUAEPassInfoPortTypeEndpoint0/updateUAEPassDocumentDtls_Oper");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



			addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");


			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();



			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;


			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
					updateUAEPassDocumentDtlsReqMsg0,
					optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342",
							"updateUAEPassDocumentDtls_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342",
									"updateUAEPassDocumentDtls_Oper"));

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


			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement() ,
					com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.UpdateUAEPassDocumentDtlsResMsg.class,
					getEnvelopeNamespaces(_returnEnv));


			return (com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.UpdateUAEPassDocumentDtlsResMsg)object;

		}catch(org.apache.axis2.AxisFault f){

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt!=null){
				if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"updateUAEPassDocumentDtls_Oper"))){
					//make the fault by reflection
					try{
						java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"updateUAEPassDocumentDtls_Oper"));
						java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
						java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
						java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
						//message class
						java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"updateUAEPassDocumentDtls_Oper"));
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
	 * @see com.newgen.dscop.stub.InqCustomerUAEPassInfo#startupdateUAEPassDocumentDtls_Oper
	 * @param updateUAEPassDocumentDtlsReqMsg0

	 */
	public  void startupdateUAEPassDocumentDtls_Oper(

			com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.UpdateUAEPassDocumentDtlsReqMsg updateUAEPassDocumentDtlsReqMsg0,

			final com.newgen.dscop.stub.InqCustomerUAEPassInfoCallbackHandler callback)

					throws java.rmi.RemoteException{

		org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
		_operationClient.getOptions().setAction("/Services/CustomerAuthenticationServices/CustomerAuthenticationServicesInquiry/Service/InqCustomerUAEPassInfo.serviceagent/InqCustomerUAEPassInfoPortTypeEndpoint0/updateUAEPassDocumentDtls_Oper");
		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



		addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");



		// create SOAP envelope with that payload
		org.apache.axiom.soap.SOAPEnvelope env=null;
		final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();


		//Style is Doc.


		env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
				updateUAEPassDocumentDtlsReqMsg0,
				optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342",
						"updateUAEPassDocumentDtls_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342",
								"updateUAEPassDocumentDtls_Oper"));

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
							com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.UpdateUAEPassDocumentDtlsResMsg.class,
							getEnvelopeNamespaces(resultEnv));
					callback.receiveResultupdateUAEPassDocumentDtls_Oper(
							(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.UpdateUAEPassDocumentDtlsResMsg)object);

				} catch (org.apache.axis2.AxisFault e) {
					callback.receiveErrorupdateUAEPassDocumentDtls_Oper(e);
				}
			}

			public void onError(java.lang.Exception error) {
				if (error instanceof org.apache.axis2.AxisFault) {
					org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
					org.apache.axiom.om.OMElement faultElt = f.getDetail();
					if (faultElt!=null){
						if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"updateUAEPassDocumentDtls_Oper"))){
							//make the fault by reflection
							try{
								java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"updateUAEPassDocumentDtls_Oper"));
								java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
								java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
								java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
								//message class
								java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"updateUAEPassDocumentDtls_Oper"));
								java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
								java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
								java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
										new java.lang.Class[]{messageClass});
								m.invoke(ex,new java.lang.Object[]{messageObject});


								callback.receiveErrorupdateUAEPassDocumentDtls_Oper(new java.rmi.RemoteException(ex.getMessage(), ex));
							} catch(java.lang.ClassCastException e){
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorupdateUAEPassDocumentDtls_Oper(f);
							} catch (java.lang.ClassNotFoundException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorupdateUAEPassDocumentDtls_Oper(f);
							} catch (java.lang.NoSuchMethodException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorupdateUAEPassDocumentDtls_Oper(f);
							} catch (java.lang.reflect.InvocationTargetException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorupdateUAEPassDocumentDtls_Oper(f);
							} catch (java.lang.IllegalAccessException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorupdateUAEPassDocumentDtls_Oper(f);
							} catch (java.lang.InstantiationException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorupdateUAEPassDocumentDtls_Oper(f);
							} catch (org.apache.axis2.AxisFault e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorupdateUAEPassDocumentDtls_Oper(f);
							}
						} else {
							callback.receiveErrorupdateUAEPassDocumentDtls_Oper(f);
						}
					} else {
						callback.receiveErrorupdateUAEPassDocumentDtls_Oper(f);
					}
				} else {
					callback.receiveErrorupdateUAEPassDocumentDtls_Oper(error);
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
					callback.receiveErrorupdateUAEPassDocumentDtls_Oper(axisFault);
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
	 * @see com.newgen.dscop.stub.InqCustomerUAEPassInfo#fetchCustomerUAEPassInfo_Oper
	 * @param fetchCustomerUAEPassInfoReqMsg2

	 */



	public  com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchCustomerUAEPassInfoResMsg fetchCustomerUAEPassInfo_Oper(

			com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchCustomerUAEPassInfoReqMsg fetchCustomerUAEPassInfoReqMsg2)


					throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try{
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
			_operationClient.getOptions().setAction("/Services/CustomerAuthenticationServices/CustomerAuthenticationServicesInquiry/Service/InqCustomerUAEPassInfo.serviceagent/InqCustomerUAEPassInfoPortTypeEndpoint0/fetchCustomerUAEPassInfo_Oper");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



			addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");


			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();



			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;


			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
					fetchCustomerUAEPassInfoReqMsg2,
					optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342",
							"fetchCustomerUAEPassInfo_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342",
									"fetchCustomerUAEPassInfo_Oper"));

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
					com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchCustomerUAEPassInfoResMsg.class,
					getEnvelopeNamespaces(_returnEnv));


			return (com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchCustomerUAEPassInfoResMsg)object;

		}catch(org.apache.axis2.AxisFault f){

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt!=null){
				if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"fetchCustomerUAEPassInfo_Oper"))){
					//make the fault by reflection
					try{
						java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"fetchCustomerUAEPassInfo_Oper"));
						java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
						java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
						java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
						//message class
						java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"fetchCustomerUAEPassInfo_Oper"));
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
	 * @see com.newgen.dscop.stub.InqCustomerUAEPassInfo#startfetchCustomerUAEPassInfo_Oper
	 * @param fetchCustomerUAEPassInfoReqMsg2

	 */
	public  void startfetchCustomerUAEPassInfo_Oper(

			com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchCustomerUAEPassInfoReqMsg fetchCustomerUAEPassInfoReqMsg2,

			final com.newgen.dscop.stub.InqCustomerUAEPassInfoCallbackHandler callback)

					throws java.rmi.RemoteException{

		org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
		_operationClient.getOptions().setAction("/Services/CustomerAuthenticationServices/CustomerAuthenticationServicesInquiry/Service/InqCustomerUAEPassInfo.serviceagent/InqCustomerUAEPassInfoPortTypeEndpoint0/fetchCustomerUAEPassInfo_Oper");
		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



		addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");



		// create SOAP envelope with that payload
		org.apache.axiom.soap.SOAPEnvelope env=null;
		final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();


		//Style is Doc.


		env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
				fetchCustomerUAEPassInfoReqMsg2,
				optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342",
						"fetchCustomerUAEPassInfo_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342",
								"fetchCustomerUAEPassInfo_Oper"));

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
							com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchCustomerUAEPassInfoResMsg.class,
							getEnvelopeNamespaces(resultEnv));
					callback.receiveResultfetchCustomerUAEPassInfo_Oper(
							(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchCustomerUAEPassInfoResMsg)object);

				} catch (org.apache.axis2.AxisFault e) {
					callback.receiveErrorfetchCustomerUAEPassInfo_Oper(e);
				}
			}

			public void onError(java.lang.Exception error) {
				if (error instanceof org.apache.axis2.AxisFault) {
					org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
					org.apache.axiom.om.OMElement faultElt = f.getDetail();
					if (faultElt!=null){
						if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"fetchCustomerUAEPassInfo_Oper"))){
							//make the fault by reflection
							try{
								java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"fetchCustomerUAEPassInfo_Oper"));
								java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
								java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
								java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
								//message class
								java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"fetchCustomerUAEPassInfo_Oper"));
								java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
								java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
								java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
										new java.lang.Class[]{messageClass});
								m.invoke(ex,new java.lang.Object[]{messageObject});


								callback.receiveErrorfetchCustomerUAEPassInfo_Oper(new java.rmi.RemoteException(ex.getMessage(), ex));
							} catch(java.lang.ClassCastException e){
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchCustomerUAEPassInfo_Oper(f);
							} catch (java.lang.ClassNotFoundException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchCustomerUAEPassInfo_Oper(f);
							} catch (java.lang.NoSuchMethodException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchCustomerUAEPassInfo_Oper(f);
							} catch (java.lang.reflect.InvocationTargetException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchCustomerUAEPassInfo_Oper(f);
							} catch (java.lang.IllegalAccessException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchCustomerUAEPassInfo_Oper(f);
							} catch (java.lang.InstantiationException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchCustomerUAEPassInfo_Oper(f);
							} catch (org.apache.axis2.AxisFault e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchCustomerUAEPassInfo_Oper(f);
							}
						} else {
							callback.receiveErrorfetchCustomerUAEPassInfo_Oper(f);
						}
					} else {
						callback.receiveErrorfetchCustomerUAEPassInfo_Oper(f);
					}
				} else {
					callback.receiveErrorfetchCustomerUAEPassInfo_Oper(error);
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
					callback.receiveErrorfetchCustomerUAEPassInfo_Oper(axisFault);
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
	 * Auto generated method signature
	 * 
	 * @see com.newgen.dscop.stub.InqCustomerUAEPassInfo#fetchUAEPassDocumentDtls_Oper
	 * @param fetchUAEPassDocumentDtlsReqMsg4

	 */



	public  com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchUAEPassDocumentDtlsResMsg fetchUAEPassDocumentDtls_Oper(

			com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchUAEPassDocumentDtlsReqMsg fetchUAEPassDocumentDtlsReqMsg4)


					throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try{
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[2].getName());
			_operationClient.getOptions().setAction("/Services/CustomerAuthenticationServices/CustomerAuthenticationServicesInquiry/Service/InqCustomerUAEPassInfo.serviceagent/InqCustomerUAEPassInfoPortTypeEndpoint0/fetchUAEPassDocumentDtls_Oper");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



			addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");


			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();



			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;


			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
					fetchUAEPassDocumentDtlsReqMsg4,
					optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342",
							"fetchUAEPassDocumentDtls_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342",
									"fetchUAEPassDocumentDtls_Oper"));

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
					com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchUAEPassDocumentDtlsResMsg.class,
					getEnvelopeNamespaces(_returnEnv));


			return (com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchUAEPassDocumentDtlsResMsg)object;

		}catch(org.apache.axis2.AxisFault f){

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt!=null){
				if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"fetchUAEPassDocumentDtls_Oper"))){
					//make the fault by reflection
					try{
						java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"fetchUAEPassDocumentDtls_Oper"));
						java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
						java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
						java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
						//message class
						java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"fetchUAEPassDocumentDtls_Oper"));
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
	 * @see com.newgen.dscop.stub.InqCustomerUAEPassInfo#startfetchUAEPassDocumentDtls_Oper
	 * @param fetchUAEPassDocumentDtlsReqMsg4

	 */
	public  void startfetchUAEPassDocumentDtls_Oper(

			com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchUAEPassDocumentDtlsReqMsg fetchUAEPassDocumentDtlsReqMsg4,

			final com.newgen.dscop.stub.InqCustomerUAEPassInfoCallbackHandler callback)

					throws java.rmi.RemoteException{

		org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[2].getName());
		_operationClient.getOptions().setAction("/Services/CustomerAuthenticationServices/CustomerAuthenticationServicesInquiry/Service/InqCustomerUAEPassInfo.serviceagent/InqCustomerUAEPassInfoPortTypeEndpoint0/fetchUAEPassDocumentDtls_Oper");
		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



		addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");



		// create SOAP envelope with that payload
		org.apache.axiom.soap.SOAPEnvelope env=null;
		final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();


		//Style is Doc.


		env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
				fetchUAEPassDocumentDtlsReqMsg4,
				optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342",
						"fetchUAEPassDocumentDtls_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342",
								"fetchUAEPassDocumentDtls_Oper"));

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
							com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchUAEPassDocumentDtlsResMsg.class,
							getEnvelopeNamespaces(resultEnv));
					callback.receiveResultfetchUAEPassDocumentDtls_Oper(
							(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchUAEPassDocumentDtlsResMsg)object);

				} catch (org.apache.axis2.AxisFault e) {
					callback.receiveErrorfetchUAEPassDocumentDtls_Oper(e);
				}
			}

			public void onError(java.lang.Exception error) {
				if (error instanceof org.apache.axis2.AxisFault) {
					org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
					org.apache.axiom.om.OMElement faultElt = f.getDetail();
					if (faultElt!=null){
						if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"fetchUAEPassDocumentDtls_Oper"))){
							//make the fault by reflection
							try{
								java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"fetchUAEPassDocumentDtls_Oper"));
								java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
								java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
								java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
								//message class
								java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"fetchUAEPassDocumentDtls_Oper"));
								java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
								java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
								java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
										new java.lang.Class[]{messageClass});
								m.invoke(ex,new java.lang.Object[]{messageObject});


								callback.receiveErrorfetchUAEPassDocumentDtls_Oper(new java.rmi.RemoteException(ex.getMessage(), ex));
							} catch(java.lang.ClassCastException e){
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchUAEPassDocumentDtls_Oper(f);
							} catch (java.lang.ClassNotFoundException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchUAEPassDocumentDtls_Oper(f);
							} catch (java.lang.NoSuchMethodException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchUAEPassDocumentDtls_Oper(f);
							} catch (java.lang.reflect.InvocationTargetException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchUAEPassDocumentDtls_Oper(f);
							} catch (java.lang.IllegalAccessException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchUAEPassDocumentDtls_Oper(f);
							} catch (java.lang.InstantiationException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchUAEPassDocumentDtls_Oper(f);
							} catch (org.apache.axis2.AxisFault e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorfetchUAEPassDocumentDtls_Oper(f);
							}
						} else {
							callback.receiveErrorfetchUAEPassDocumentDtls_Oper(f);
						}
					} else {
						callback.receiveErrorfetchUAEPassDocumentDtls_Oper(f);
					}
				} else {
					callback.receiveErrorfetchUAEPassDocumentDtls_Oper(error);
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
					callback.receiveErrorfetchUAEPassDocumentDtls_Oper(axisFault);
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
	 * @see com.newgen.dscop.stub.InqCustomerUAEPassInfo#requestUAEPassDocuments_Oper
	 * @param requestUAEPassDocumentsReqMsg6

	 */



	public  com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.RequestUARPassDocumentsResMsg requestUAEPassDocuments_Oper(

			com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.RequestUAEPassDocumentsReqMsg requestUAEPassDocumentsReqMsg6)


					throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try{
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[3].getName());
			_operationClient.getOptions().setAction("/Services/CustomerAuthenticationServices/CustomerAuthenticationServicesInquiry/Service/InqCustomerUAEPassInfo.serviceagent/InqCustomerUAEPassInfoPortTypeEndpoint0/requestUAEPassDocuments_Oper");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



			addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");


			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();



			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;


			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
					requestUAEPassDocumentsReqMsg6,
					optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342",
							"requestUAEPassDocuments_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342",
									"requestUAEPassDocuments_Oper"));

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
					com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.RequestUARPassDocumentsResMsg.class,
					getEnvelopeNamespaces(_returnEnv));


			return (com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.RequestUARPassDocumentsResMsg)object;

		}catch(org.apache.axis2.AxisFault f){

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt!=null){
				if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"requestUAEPassDocuments_Oper"))){
					//make the fault by reflection
					try{
						java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"requestUAEPassDocuments_Oper"));
						java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
						java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
						java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
						//message class
						java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"requestUAEPassDocuments_Oper"));
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
	 * @see com.newgen.dscop.stub.InqCustomerUAEPassInfo#startrequestUAEPassDocuments_Oper
	 * @param requestUAEPassDocumentsReqMsg6

	 */
	public  void startrequestUAEPassDocuments_Oper(

			com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.RequestUAEPassDocumentsReqMsg requestUAEPassDocumentsReqMsg6,

			final com.newgen.dscop.stub.InqCustomerUAEPassInfoCallbackHandler callback)

					throws java.rmi.RemoteException{

		org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[3].getName());
		_operationClient.getOptions().setAction("/Services/CustomerAuthenticationServices/CustomerAuthenticationServicesInquiry/Service/InqCustomerUAEPassInfo.serviceagent/InqCustomerUAEPassInfoPortTypeEndpoint0/requestUAEPassDocuments_Oper");
		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



		addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");



		// create SOAP envelope with that payload
		org.apache.axiom.soap.SOAPEnvelope env=null;
		final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();


		//Style is Doc.


		env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
				requestUAEPassDocumentsReqMsg6,
				optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342",
						"requestUAEPassDocuments_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1649327745342",
								"requestUAEPassDocuments_Oper"));

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
							com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.RequestUARPassDocumentsResMsg.class,
							getEnvelopeNamespaces(resultEnv));
					callback.receiveResultrequestUAEPassDocuments_Oper(
							(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.RequestUARPassDocumentsResMsg)object);

				} catch (org.apache.axis2.AxisFault e) {
					callback.receiveErrorrequestUAEPassDocuments_Oper(e);
				}
			}

			public void onError(java.lang.Exception error) {
				if (error instanceof org.apache.axis2.AxisFault) {
					org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
					org.apache.axiom.om.OMElement faultElt = f.getDetail();
					if (faultElt!=null){
						if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"requestUAEPassDocuments_Oper"))){
							//make the fault by reflection
							try{
								java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"requestUAEPassDocuments_Oper"));
								java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
								java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
								java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
								//message class
								java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"requestUAEPassDocuments_Oper"));
								java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
								java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
								java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
										new java.lang.Class[]{messageClass});
								m.invoke(ex,new java.lang.Object[]{messageObject});


								callback.receiveErrorrequestUAEPassDocuments_Oper(new java.rmi.RemoteException(ex.getMessage(), ex));
							} catch(java.lang.ClassCastException e){
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorrequestUAEPassDocuments_Oper(f);
							} catch (java.lang.ClassNotFoundException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorrequestUAEPassDocuments_Oper(f);
							} catch (java.lang.NoSuchMethodException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorrequestUAEPassDocuments_Oper(f);
							} catch (java.lang.reflect.InvocationTargetException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorrequestUAEPassDocuments_Oper(f);
							} catch (java.lang.IllegalAccessException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorrequestUAEPassDocuments_Oper(f);
							} catch (java.lang.InstantiationException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorrequestUAEPassDocuments_Oper(f);
							} catch (org.apache.axis2.AxisFault e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorrequestUAEPassDocuments_Oper(f);
							}
						} else {
							callback.receiveErrorrequestUAEPassDocuments_Oper(f);
						}
					} else {
						callback.receiveErrorrequestUAEPassDocuments_Oper(f);
					}
				} else {
					callback.receiveErrorrequestUAEPassDocuments_Oper(error);
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
					callback.receiveErrorrequestUAEPassDocuments_Oper(axisFault);
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
	//http://10.101.107.21:5563/Services/CustomerAuthenticationServices/CustomerAuthenticationServicesInquiry/Service/InqCustomerUAEPassInfo.serviceagent/InqCustomerUAEPassInfoPortTypeEndpoint0
	public static class UpdateUAEPassDocumentDtlsRes_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = updateUAEPassDocumentDtlsRes_type0
                Namespace URI = http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for NotifyToCustomer
		 */


		protected java.lang.String localNotifyToCustomer ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNotifyToCustomerTracker = false ;

		public boolean isNotifyToCustomerSpecified(){
			return localNotifyToCustomerTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNotifyToCustomer(){
			return localNotifyToCustomer;
		}



		/**
		 * Auto generated setter method
		 * @param param NotifyToCustomer
		 */
		public void setNotifyToCustomer(java.lang.String param){
			localNotifyToCustomerTracker = param != null;

			this.localNotifyToCustomer=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":updateUAEPassDocumentDtlsRes_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"updateUAEPassDocumentDtlsRes_type0",
							xmlWriter);
				}


			}
			if (localNotifyToCustomerTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "notifyToCustomer", xmlWriter);


				if (localNotifyToCustomer==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("notifyToCustomer cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNotifyToCustomer);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd")){
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

			if (localNotifyToCustomerTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"notifyToCustomer"));

				if (localNotifyToCustomer != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNotifyToCustomer));
				} else {
					throw new org.apache.axis2.databinding.ADBException("notifyToCustomer cannot be null!!");
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
			public static UpdateUAEPassDocumentDtlsRes_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				UpdateUAEPassDocumentDtlsRes_type0 object =
						new UpdateUAEPassDocumentDtlsRes_type0();

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

							if (!"updateUAEPassDocumentDtlsRes_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (UpdateUAEPassDocumentDtlsRes_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","notifyToCustomer").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"notifyToCustomer" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNotifyToCustomer(
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


	public static class DocumentDetails_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = documentDetails_type0
                Namespace URI = http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for DocumentType
		 */


		protected java.lang.String localDocumentType ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDocumentTypeTracker = false ;

		public boolean isDocumentTypeSpecified(){
			return localDocumentTypeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDocumentType(){
			return localDocumentType;
		}



		/**
		 * Auto generated setter method
		 * @param param DocumentType
		 */
		public void setDocumentType(java.lang.String param){
			localDocumentTypeTracker = param != null;

			this.localDocumentType=param;


		}


		/**
		 * field for ExternalDocRefNo
		 */


		protected java.lang.String localExternalDocRefNo ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localExternalDocRefNoTracker = false ;

		public boolean isExternalDocRefNoSpecified(){
			return localExternalDocRefNoTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getExternalDocRefNo(){
			return localExternalDocRefNo;
		}



		/**
		 * Auto generated setter method
		 * @param param ExternalDocRefNo
		 */
		public void setExternalDocRefNo(java.lang.String param){
			localExternalDocRefNoTracker = param != null;

			this.localExternalDocRefNo=param;


		}


		/**
		 * field for TransactionRefNo
		 */


		protected java.lang.String localTransactionRefNo ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localTransactionRefNoTracker = false ;

		public boolean isTransactionRefNoSpecified(){
			return localTransactionRefNoTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getTransactionRefNo(){
			return localTransactionRefNo;
		}



		/**
		 * Auto generated setter method
		 * @param param TransactionRefNo
		 */
		public void setTransactionRefNo(java.lang.String param){
			localTransactionRefNoTracker = param != null;

			this.localTransactionRefNo=param;


		}


		/**
		 * field for EdmsDocRefNo
		 */


		protected java.lang.String localEdmsDocRefNo ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localEdmsDocRefNoTracker = false ;

		public boolean isEdmsDocRefNoSpecified(){
			return localEdmsDocRefNoTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getEdmsDocRefNo(){
			return localEdmsDocRefNo;
		}



		/**
		 * Auto generated setter method
		 * @param param EdmsDocRefNo
		 */
		public void setEdmsDocRefNo(java.lang.String param){
			localEdmsDocRefNoTracker = param != null;

			this.localEdmsDocRefNo=param;


		}


		/**
		 * field for DocumentExpiryDate
		 */


		protected java.lang.String localDocumentExpiryDate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDocumentExpiryDateTracker = false ;

		public boolean isDocumentExpiryDateSpecified(){
			return localDocumentExpiryDateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDocumentExpiryDate(){
			return localDocumentExpiryDate;
		}



		/**
		 * Auto generated setter method
		 * @param param DocumentExpiryDate
		 */
		public void setDocumentExpiryDate(java.lang.String param){
			localDocumentExpiryDateTracker = param != null;

			this.localDocumentExpiryDate=param;


		}


		/**
		 * field for DocumentAssuranceLevel
		 */


		protected java.lang.String localDocumentAssuranceLevel ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDocumentAssuranceLevelTracker = false ;

		public boolean isDocumentAssuranceLevelSpecified(){
			return localDocumentAssuranceLevelTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDocumentAssuranceLevel(){
			return localDocumentAssuranceLevel;
		}



		/**
		 * Auto generated setter method
		 * @param param DocumentAssuranceLevel
		 */
		public void setDocumentAssuranceLevel(java.lang.String param){
			localDocumentAssuranceLevelTracker = param != null;

			this.localDocumentAssuranceLevel=param;


		}


		/**
		 * field for CreatedOn
		 */


		protected java.lang.String localCreatedOn ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCreatedOnTracker = false ;

		public boolean isCreatedOnSpecified(){
			return localCreatedOnTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCreatedOn(){
			return localCreatedOn;
		}



		/**
		 * Auto generated setter method
		 * @param param CreatedOn
		 */
		public void setCreatedOn(java.lang.String param){
			localCreatedOnTracker = param != null;

			this.localCreatedOn=param;


		}


		/**
		 * field for Creator
		 */


		protected java.lang.String localCreator ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCreatorTracker = false ;

		public boolean isCreatorSpecified(){
			return localCreatorTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCreator(){
			return localCreator;
		}



		/**
		 * Auto generated setter method
		 * @param param Creator
		 */
		public void setCreator(java.lang.String param){
			localCreatorTracker = param != null;

			this.localCreator=param;


		}


		/**
		 * field for ExternalBlockChainId
		 */


		protected java.lang.String localExternalBlockChainId ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localExternalBlockChainIdTracker = false ;

		public boolean isExternalBlockChainIdSpecified(){
			return localExternalBlockChainIdTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getExternalBlockChainId(){
			return localExternalBlockChainId;
		}



		/**
		 * Auto generated setter method
		 * @param param ExternalBlockChainId
		 */
		public void setExternalBlockChainId(java.lang.String param){
			localExternalBlockChainIdTracker = param != null;

			this.localExternalBlockChainId=param;


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
		 * field for NameAr
		 */


		protected java.lang.String localNameAr ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNameArTracker = false ;

		public boolean isNameArSpecified(){
			return localNameArTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNameAr(){
			return localNameAr;
		}



		/**
		 * Auto generated setter method
		 * @param param NameAr
		 */
		public void setNameAr(java.lang.String param){
			localNameArTracker = param != null;

			this.localNameAr=param;


		}


		/**
		 * field for NameEn
		 */


		protected java.lang.String localNameEn ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNameEnTracker = false ;

		public boolean isNameEnSpecified(){
			return localNameEnTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNameEn(){
			return localNameEn;
		}



		/**
		 * Auto generated setter method
		 * @param param NameEn
		 */
		public void setNameEn(java.lang.String param){
			localNameEnTracker = param != null;

			this.localNameEn=param;


		}


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
		 * field for DocumentTypeCode
		 */


		protected java.lang.String localDocumentTypeCode ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDocumentTypeCodeTracker = false ;

		public boolean isDocumentTypeCodeSpecified(){
			return localDocumentTypeCodeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDocumentTypeCode(){
			return localDocumentTypeCode;
		}



		/**
		 * Auto generated setter method
		 * @param param DocumentTypeCode
		 */
		public void setDocumentTypeCode(java.lang.String param){
			localDocumentTypeCodeTracker = param != null;

			this.localDocumentTypeCode=param;


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
		 * field for GenderAr
		 */


		protected java.lang.String localGenderAr ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localGenderArTracker = false ;

		public boolean isGenderArSpecified(){
			return localGenderArTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getGenderAr(){
			return localGenderAr;
		}



		/**
		 * Auto generated setter method
		 * @param param GenderAr
		 */
		public void setGenderAr(java.lang.String param){
			localGenderArTracker = param != null;

			this.localGenderAr=param;


		}


		/**
		 * field for GenderCode
		 */


		protected java.lang.String localGenderCode ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localGenderCodeTracker = false ;

		public boolean isGenderCodeSpecified(){
			return localGenderCodeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getGenderCode(){
			return localGenderCode;
		}



		/**
		 * Auto generated setter method
		 * @param param GenderCode
		 */
		public void setGenderCode(java.lang.String param){
			localGenderCodeTracker = param != null;

			this.localGenderCode=param;


		}


		/**
		 * field for GenderEn
		 */


		protected java.lang.String localGenderEn ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localGenderEnTracker = false ;

		public boolean isGenderEnSpecified(){
			return localGenderEnTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getGenderEn(){
			return localGenderEn;
		}



		/**
		 * Auto generated setter method
		 * @param param GenderEn
		 */
		public void setGenderEn(java.lang.String param){
			localGenderEnTracker = param != null;

			this.localGenderEn=param;


		}


		/**
		 * field for IdNumber
		 */


		protected java.lang.String localIdNumber ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localIdNumberTracker = false ;

		public boolean isIdNumberSpecified(){
			return localIdNumberTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getIdNumber(){
			return localIdNumber;
		}



		/**
		 * Auto generated setter method
		 * @param param IdNumber
		 */
		public void setIdNumber(java.lang.String param){
			localIdNumberTracker = param != null;

			this.localIdNumber=param;


		}


		/**
		 * field for NationalityAr
		 */


		protected java.lang.String localNationalityAr ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNationalityArTracker = false ;

		public boolean isNationalityArSpecified(){
			return localNationalityArTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNationalityAr(){
			return localNationalityAr;
		}



		/**
		 * Auto generated setter method
		 * @param param NationalityAr
		 */
		public void setNationalityAr(java.lang.String param){
			localNationalityArTracker = param != null;

			this.localNationalityAr=param;


		}


		/**
		 * field for NationalityCode
		 */


		protected java.lang.String localNationalityCode ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNationalityCodeTracker = false ;

		public boolean isNationalityCodeSpecified(){
			return localNationalityCodeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNationalityCode(){
			return localNationalityCode;
		}



		/**
		 * Auto generated setter method
		 * @param param NationalityCode
		 */
		public void setNationalityCode(java.lang.String param){
			localNationalityCodeTracker = param != null;

			this.localNationalityCode=param;


		}


		/**
		 * field for NationalityEn
		 */


		protected java.lang.String localNationalityEn ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNationalityEnTracker = false ;

		public boolean isNationalityEnSpecified(){
			return localNationalityEnTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNationalityEn(){
			return localNationalityEn;
		}



		/**
		 * Auto generated setter method
		 * @param param NationalityEn
		 */
		public void setNationalityEn(java.lang.String param){
			localNationalityEnTracker = param != null;

			this.localNationalityEn=param;


		}


		/**
		 * field for PhotoBase64
		 */


		protected java.lang.String localPhotoBase64 ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPhotoBase64Tracker = false ;

		public boolean isPhotoBase64Specified(){
			return localPhotoBase64Tracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPhotoBase64(){
			return localPhotoBase64;
		}



		/**
		 * Auto generated setter method
		 * @param param PhotoBase64
		 */
		public void setPhotoBase64(java.lang.String param){
			localPhotoBase64Tracker = param != null;

			this.localPhotoBase64=param;


		}


		/**
		 * field for SignatureBase64
		 */


		protected java.lang.String localSignatureBase64 ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localSignatureBase64Tracker = false ;

		public boolean isSignatureBase64Specified(){
			return localSignatureBase64Tracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getSignatureBase64(){
			return localSignatureBase64;
		}



		/**
		 * Auto generated setter method
		 * @param param SignatureBase64
		 */
		public void setSignatureBase64(java.lang.String param){
			localSignatureBase64Tracker = param != null;

			this.localSignatureBase64=param;


		}


		/**
		 * field for PlaceOfBirthEn
		 */


		protected java.lang.String localPlaceOfBirthEn ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPlaceOfBirthEnTracker = false ;

		public boolean isPlaceOfBirthEnSpecified(){
			return localPlaceOfBirthEnTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPlaceOfBirthEn(){
			return localPlaceOfBirthEn;
		}



		/**
		 * Auto generated setter method
		 * @param param PlaceOfBirthEn
		 */
		public void setPlaceOfBirthEn(java.lang.String param){
			localPlaceOfBirthEnTracker = param != null;

			this.localPlaceOfBirthEn=param;


		}


		/**
		 * field for PlaceOfBirthAr
		 */


		protected java.lang.String localPlaceOfBirthAr ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPlaceOfBirthArTracker = false ;

		public boolean isPlaceOfBirthArSpecified(){
			return localPlaceOfBirthArTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPlaceOfBirthAr(){
			return localPlaceOfBirthAr;
		}



		/**
		 * Auto generated setter method
		 * @param param PlaceOfBirthAr
		 */
		public void setPlaceOfBirthAr(java.lang.String param){
			localPlaceOfBirthArTracker = param != null;

			this.localPlaceOfBirthAr=param;


		}


		/**
		 * field for IssuingAuthEn
		 */


		protected java.lang.String localIssuingAuthEn ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localIssuingAuthEnTracker = false ;

		public boolean isIssuingAuthEnSpecified(){
			return localIssuingAuthEnTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getIssuingAuthEn(){
			return localIssuingAuthEn;
		}



		/**
		 * Auto generated setter method
		 * @param param IssuingAuthEn
		 */
		public void setIssuingAuthEn(java.lang.String param){
			localIssuingAuthEnTracker = param != null;

			this.localIssuingAuthEn=param;


		}


		/**
		 * field for IssuingAuthAr
		 */


		protected java.lang.String localIssuingAuthAr ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localIssuingAuthArTracker = false ;

		public boolean isIssuingAuthArSpecified(){
			return localIssuingAuthArTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getIssuingAuthAr(){
			return localIssuingAuthAr;
		}



		/**
		 * Auto generated setter method
		 * @param param IssuingAuthAr
		 */
		public void setIssuingAuthAr(java.lang.String param){
			localIssuingAuthArTracker = param != null;

			this.localIssuingAuthAr=param;


		}


		/**
		 * field for PassportTypeEn
		 */


		protected java.lang.String localPassportTypeEn ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPassportTypeEnTracker = false ;

		public boolean isPassportTypeEnSpecified(){
			return localPassportTypeEnTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPassportTypeEn(){
			return localPassportTypeEn;
		}



		/**
		 * Auto generated setter method
		 * @param param PassportTypeEn
		 */
		public void setPassportTypeEn(java.lang.String param){
			localPassportTypeEnTracker = param != null;

			this.localPassportTypeEn=param;


		}


		/**
		 * field for CountryCode
		 */


		protected java.lang.String localCountryCode ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCountryCodeTracker = false ;

		public boolean isCountryCodeSpecified(){
			return localCountryCodeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCountryCode(){
			return localCountryCode;
		}



		/**
		 * Auto generated setter method
		 * @param param CountryCode
		 */
		public void setCountryCode(java.lang.String param){
			localCountryCodeTracker = param != null;

			this.localCountryCode=param;


		}


		/**
		 * field for FileNumber
		 */


		protected java.lang.String localFileNumber ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localFileNumberTracker = false ;

		public boolean isFileNumberSpecified(){
			return localFileNumberTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getFileNumber(){
			return localFileNumber;
		}



		/**
		 * Auto generated setter method
		 * @param param FileNumber
		 */
		public void setFileNumber(java.lang.String param){
			localFileNumberTracker = param != null;

			this.localFileNumber=param;


		}


		/**
		 * field for ProfessionCode
		 */


		protected java.lang.String localProfessionCode ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localProfessionCodeTracker = false ;

		public boolean isProfessionCodeSpecified(){
			return localProfessionCodeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getProfessionCode(){
			return localProfessionCode;
		}



		/**
		 * Auto generated setter method
		 * @param param ProfessionCode
		 */
		public void setProfessionCode(java.lang.String param){
			localProfessionCodeTracker = param != null;

			this.localProfessionCode=param;


		}


		/**
		 * field for ProfessionAr
		 */


		protected java.lang.String localProfessionAr ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localProfessionArTracker = false ;

		public boolean isProfessionArSpecified(){
			return localProfessionArTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getProfessionAr(){
			return localProfessionAr;
		}



		/**
		 * Auto generated setter method
		 * @param param ProfessionAr
		 */
		public void setProfessionAr(java.lang.String param){
			localProfessionArTracker = param != null;

			this.localProfessionAr=param;


		}


		/**
		 * field for ProfessionEn
		 */


		protected java.lang.String localProfessionEn ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localProfessionEnTracker = false ;

		public boolean isProfessionEnSpecified(){
			return localProfessionEnTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getProfessionEn(){
			return localProfessionEn;
		}



		/**
		 * Auto generated setter method
		 * @param param ProfessionEn
		 */
		public void setProfessionEn(java.lang.String param){
			localProfessionEnTracker = param != null;

			this.localProfessionEn=param;


		}


		/**
		 * field for SponsorNo
		 */


		protected java.lang.String localSponsorNo ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localSponsorNoTracker = false ;

		public boolean isSponsorNoSpecified(){
			return localSponsorNoTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getSponsorNo(){
			return localSponsorNo;
		}



		/**
		 * Auto generated setter method
		 * @param param SponsorNo
		 */
		public void setSponsorNo(java.lang.String param){
			localSponsorNoTracker = param != null;

			this.localSponsorNo=param;


		}


		/**
		 * field for SponsorAr
		 */


		protected java.lang.String localSponsorAr ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localSponsorArTracker = false ;

		public boolean isSponsorArSpecified(){
			return localSponsorArTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getSponsorAr(){
			return localSponsorAr;
		}



		/**
		 * Auto generated setter method
		 * @param param SponsorAr
		 */
		public void setSponsorAr(java.lang.String param){
			localSponsorArTracker = param != null;

			this.localSponsorAr=param;


		}


		/**
		 * field for SponsorEn
		 */


		protected java.lang.String localSponsorEn ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localSponsorEnTracker = false ;

		public boolean isSponsorEnSpecified(){
			return localSponsorEnTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getSponsorEn(){
			return localSponsorEn;
		}



		/**
		 * Auto generated setter method
		 * @param param SponsorEn
		 */
		public void setSponsorEn(java.lang.String param){
			localSponsorEnTracker = param != null;

			this.localSponsorEn=param;


		}


		/**
		 * field for AccompaniedBy
		 */


		protected java.lang.String localAccompaniedBy ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAccompaniedByTracker = false ;

		public boolean isAccompaniedBySpecified(){
			return localAccompaniedByTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getAccompaniedBy(){
			return localAccompaniedBy;
		}



		/**
		 * Auto generated setter method
		 * @param param AccompaniedBy
		 */
		public void setAccompaniedBy(java.lang.String param){
			localAccompaniedByTracker = param != null;

			this.localAccompaniedBy=param;


		}


		/**
		 * field for ExternalRequestId
		 */


		protected java.lang.String localExternalRequestId ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localExternalRequestIdTracker = false ;

		public boolean isExternalRequestIdSpecified(){
			return localExternalRequestIdTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getExternalRequestId(){
			return localExternalRequestId;
		}



		/**
		 * Auto generated setter method
		 * @param param ExternalRequestId
		 */
		public void setExternalRequestId(java.lang.String param){
			localExternalRequestIdTracker = param != null;

			this.localExternalRequestId=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":documentDetails_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"documentDetails_type0",
							xmlWriter);
				}


			}
			if (localDocumentTypeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "documentType", xmlWriter);


				if (localDocumentType==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("documentType cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDocumentType);

				}

				xmlWriter.writeEndElement();
			} if (localExternalDocRefNoTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "externalDocRefNo", xmlWriter);


				if (localExternalDocRefNo==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("externalDocRefNo cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localExternalDocRefNo);

				}

				xmlWriter.writeEndElement();
			} if (localTransactionRefNoTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "transactionRefNo", xmlWriter);


				if (localTransactionRefNo==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("transactionRefNo cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localTransactionRefNo);

				}

				xmlWriter.writeEndElement();
			} if (localEdmsDocRefNoTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "edmsDocRefNo", xmlWriter);


				if (localEdmsDocRefNo==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("edmsDocRefNo cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localEdmsDocRefNo);

				}

				xmlWriter.writeEndElement();
			} if (localDocumentExpiryDateTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "documentExpiryDate", xmlWriter);


				if (localDocumentExpiryDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("documentExpiryDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDocumentExpiryDate);

				}

				xmlWriter.writeEndElement();
			} if (localDocumentAssuranceLevelTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "documentAssuranceLevel", xmlWriter);


				if (localDocumentAssuranceLevel==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("documentAssuranceLevel cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDocumentAssuranceLevel);

				}

				xmlWriter.writeEndElement();
			} if (localCreatedOnTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "createdOn", xmlWriter);


				if (localCreatedOn==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("createdOn cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCreatedOn);

				}

				xmlWriter.writeEndElement();
			} if (localCreatorTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "creator", xmlWriter);


				if (localCreator==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("creator cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCreator);

				}

				xmlWriter.writeEndElement();
			} if (localExternalBlockChainIdTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "externalBlockChainId", xmlWriter);


				if (localExternalBlockChainId==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("externalBlockChainId cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localExternalBlockChainId);

				}

				xmlWriter.writeEndElement();
			} if (localStatusTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "status", xmlWriter);


				if (localStatus==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("status cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localStatus);

				}

				xmlWriter.writeEndElement();
			} if (localNameArTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "nameAr", xmlWriter);


				if (localNameAr==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("nameAr cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNameAr);

				}

				xmlWriter.writeEndElement();
			} if (localNameEnTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "nameEn", xmlWriter);


				if (localNameEn==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("nameEn cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNameEn);

				}

				xmlWriter.writeEndElement();
			} if (localCardNumberTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "cardNumber", xmlWriter);


				if (localCardNumber==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("cardNumber cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCardNumber);

				}

				xmlWriter.writeEndElement();
			} if (localDateOfBirthTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "dateOfBirth", xmlWriter);


				if (localDateOfBirth==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("dateOfBirth cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDateOfBirth);

				}

				xmlWriter.writeEndElement();
			} if (localDocumentTypeCodeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "documentTypeCode", xmlWriter);


				if (localDocumentTypeCode==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("documentTypeCode cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDocumentTypeCode);

				}

				xmlWriter.writeEndElement();
			} if (localIssueDateTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "issueDate", xmlWriter);


				if (localIssueDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("issueDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localIssueDate);

				}

				xmlWriter.writeEndElement();
			} if (localExpiryDateTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "expiryDate", xmlWriter);


				if (localExpiryDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("expiryDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localExpiryDate);

				}

				xmlWriter.writeEndElement();
			} if (localGenderArTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "genderAr", xmlWriter);


				if (localGenderAr==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("genderAr cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localGenderAr);

				}

				xmlWriter.writeEndElement();
			} if (localGenderCodeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "genderCode", xmlWriter);


				if (localGenderCode==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("genderCode cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localGenderCode);

				}

				xmlWriter.writeEndElement();
			} if (localGenderEnTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "genderEn", xmlWriter);


				if (localGenderEn==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("genderEn cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localGenderEn);

				}

				xmlWriter.writeEndElement();
			} if (localIdNumberTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "idNumber", xmlWriter);


				if (localIdNumber==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("idNumber cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localIdNumber);

				}

				xmlWriter.writeEndElement();
			} if (localNationalityArTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "nationalityAr", xmlWriter);


				if (localNationalityAr==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("nationalityAr cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNationalityAr);

				}

				xmlWriter.writeEndElement();
			} if (localNationalityCodeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "nationalityCode", xmlWriter);


				if (localNationalityCode==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("nationalityCode cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNationalityCode);

				}

				xmlWriter.writeEndElement();
			} if (localNationalityEnTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "nationalityEn", xmlWriter);


				if (localNationalityEn==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("nationalityEn cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNationalityEn);

				}

				xmlWriter.writeEndElement();
			} if (localPhotoBase64Tracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "photoBase64", xmlWriter);


				if (localPhotoBase64==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("photoBase64 cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPhotoBase64);

				}

				xmlWriter.writeEndElement();
			} if (localSignatureBase64Tracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "signatureBase64", xmlWriter);


				if (localSignatureBase64==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("signatureBase64 cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localSignatureBase64);

				}

				xmlWriter.writeEndElement();
			} if (localPlaceOfBirthEnTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "placeOfBirthEn", xmlWriter);


				if (localPlaceOfBirthEn==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("placeOfBirthEn cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPlaceOfBirthEn);

				}

				xmlWriter.writeEndElement();
			} if (localPlaceOfBirthArTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "placeOfBirthAr", xmlWriter);


				if (localPlaceOfBirthAr==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("placeOfBirthAr cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPlaceOfBirthAr);

				}

				xmlWriter.writeEndElement();
			} if (localIssuingAuthEnTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "IssuingAuthEn", xmlWriter);


				if (localIssuingAuthEn==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("IssuingAuthEn cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localIssuingAuthEn);

				}

				xmlWriter.writeEndElement();
			} if (localIssuingAuthArTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "IssuingAuthAr", xmlWriter);


				if (localIssuingAuthAr==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("IssuingAuthAr cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localIssuingAuthAr);

				}

				xmlWriter.writeEndElement();
			} if (localPassportTypeEnTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "passportTypeEn", xmlWriter);


				if (localPassportTypeEn==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("passportTypeEn cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPassportTypeEn);

				}

				xmlWriter.writeEndElement();
			} if (localCountryCodeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "countryCode", xmlWriter);


				if (localCountryCode==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("countryCode cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCountryCode);

				}

				xmlWriter.writeEndElement();
			} if (localFileNumberTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "fileNumber", xmlWriter);


				if (localFileNumber==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("fileNumber cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localFileNumber);

				}

				xmlWriter.writeEndElement();
			} if (localProfessionCodeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "professionCode", xmlWriter);


				if (localProfessionCode==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("professionCode cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localProfessionCode);

				}

				xmlWriter.writeEndElement();
			} if (localProfessionArTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "professionAr", xmlWriter);


				if (localProfessionAr==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("professionAr cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localProfessionAr);

				}

				xmlWriter.writeEndElement();
			} if (localProfessionEnTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "professionEn", xmlWriter);


				if (localProfessionEn==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("professionEn cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localProfessionEn);

				}

				xmlWriter.writeEndElement();
			} if (localSponsorNoTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "sponsorNo", xmlWriter);


				if (localSponsorNo==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("sponsorNo cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localSponsorNo);

				}

				xmlWriter.writeEndElement();
			} if (localSponsorArTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "sponsorAr", xmlWriter);


				if (localSponsorAr==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("sponsorAr cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localSponsorAr);

				}

				xmlWriter.writeEndElement();
			} if (localSponsorEnTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "sponsorEn", xmlWriter);


				if (localSponsorEn==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("sponsorEn cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localSponsorEn);

				}

				xmlWriter.writeEndElement();
			} if (localAccompaniedByTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "accompaniedBy", xmlWriter);


				if (localAccompaniedBy==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("accompaniedBy cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localAccompaniedBy);

				}

				xmlWriter.writeEndElement();
			} if (localExternalRequestIdTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "externalRequestId", xmlWriter);


				if (localExternalRequestId==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("externalRequestId cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localExternalRequestId);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd")){
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

			if (localDocumentTypeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"documentType"));

				if (localDocumentType != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDocumentType));
				} else {
					throw new org.apache.axis2.databinding.ADBException("documentType cannot be null!!");
				}
			} if (localExternalDocRefNoTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"externalDocRefNo"));

				if (localExternalDocRefNo != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExternalDocRefNo));
				} else {
					throw new org.apache.axis2.databinding.ADBException("externalDocRefNo cannot be null!!");
				}
			} if (localTransactionRefNoTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"transactionRefNo"));

				if (localTransactionRefNo != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTransactionRefNo));
				} else {
					throw new org.apache.axis2.databinding.ADBException("transactionRefNo cannot be null!!");
				}
			} if (localEdmsDocRefNoTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"edmsDocRefNo"));

				if (localEdmsDocRefNo != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEdmsDocRefNo));
				} else {
					throw new org.apache.axis2.databinding.ADBException("edmsDocRefNo cannot be null!!");
				}
			} if (localDocumentExpiryDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"documentExpiryDate"));

				if (localDocumentExpiryDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDocumentExpiryDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("documentExpiryDate cannot be null!!");
				}
			} if (localDocumentAssuranceLevelTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"documentAssuranceLevel"));

				if (localDocumentAssuranceLevel != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDocumentAssuranceLevel));
				} else {
					throw new org.apache.axis2.databinding.ADBException("documentAssuranceLevel cannot be null!!");
				}
			} if (localCreatedOnTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"createdOn"));

				if (localCreatedOn != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCreatedOn));
				} else {
					throw new org.apache.axis2.databinding.ADBException("createdOn cannot be null!!");
				}
			} if (localCreatorTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"creator"));

				if (localCreator != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCreator));
				} else {
					throw new org.apache.axis2.databinding.ADBException("creator cannot be null!!");
				}
			} if (localExternalBlockChainIdTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"externalBlockChainId"));

				if (localExternalBlockChainId != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExternalBlockChainId));
				} else {
					throw new org.apache.axis2.databinding.ADBException("externalBlockChainId cannot be null!!");
				}
			} if (localStatusTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"status"));

				if (localStatus != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStatus));
				} else {
					throw new org.apache.axis2.databinding.ADBException("status cannot be null!!");
				}
			} if (localNameArTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"nameAr"));

				if (localNameAr != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNameAr));
				} else {
					throw new org.apache.axis2.databinding.ADBException("nameAr cannot be null!!");
				}
			} if (localNameEnTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"nameEn"));

				if (localNameEn != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNameEn));
				} else {
					throw new org.apache.axis2.databinding.ADBException("nameEn cannot be null!!");
				}
			} if (localCardNumberTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"cardNumber"));

				if (localCardNumber != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCardNumber));
				} else {
					throw new org.apache.axis2.databinding.ADBException("cardNumber cannot be null!!");
				}
			} if (localDateOfBirthTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"dateOfBirth"));

				if (localDateOfBirth != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDateOfBirth));
				} else {
					throw new org.apache.axis2.databinding.ADBException("dateOfBirth cannot be null!!");
				}
			} if (localDocumentTypeCodeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"documentTypeCode"));

				if (localDocumentTypeCode != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDocumentTypeCode));
				} else {
					throw new org.apache.axis2.databinding.ADBException("documentTypeCode cannot be null!!");
				}
			} if (localIssueDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"issueDate"));

				if (localIssueDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIssueDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("issueDate cannot be null!!");
				}
			} if (localExpiryDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"expiryDate"));

				if (localExpiryDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExpiryDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("expiryDate cannot be null!!");
				}
			} if (localGenderArTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"genderAr"));

				if (localGenderAr != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGenderAr));
				} else {
					throw new org.apache.axis2.databinding.ADBException("genderAr cannot be null!!");
				}
			} if (localGenderCodeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"genderCode"));

				if (localGenderCode != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGenderCode));
				} else {
					throw new org.apache.axis2.databinding.ADBException("genderCode cannot be null!!");
				}
			} if (localGenderEnTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"genderEn"));

				if (localGenderEn != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGenderEn));
				} else {
					throw new org.apache.axis2.databinding.ADBException("genderEn cannot be null!!");
				}
			} if (localIdNumberTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"idNumber"));

				if (localIdNumber != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIdNumber));
				} else {
					throw new org.apache.axis2.databinding.ADBException("idNumber cannot be null!!");
				}
			} if (localNationalityArTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"nationalityAr"));

				if (localNationalityAr != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNationalityAr));
				} else {
					throw new org.apache.axis2.databinding.ADBException("nationalityAr cannot be null!!");
				}
			} if (localNationalityCodeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"nationalityCode"));

				if (localNationalityCode != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNationalityCode));
				} else {
					throw new org.apache.axis2.databinding.ADBException("nationalityCode cannot be null!!");
				}
			} if (localNationalityEnTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"nationalityEn"));

				if (localNationalityEn != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNationalityEn));
				} else {
					throw new org.apache.axis2.databinding.ADBException("nationalityEn cannot be null!!");
				}
			} if (localPhotoBase64Tracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"photoBase64"));

				if (localPhotoBase64 != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPhotoBase64));
				} else {
					throw new org.apache.axis2.databinding.ADBException("photoBase64 cannot be null!!");
				}
			} if (localSignatureBase64Tracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"signatureBase64"));

				if (localSignatureBase64 != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSignatureBase64));
				} else {
					throw new org.apache.axis2.databinding.ADBException("signatureBase64 cannot be null!!");
				}
			} if (localPlaceOfBirthEnTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"placeOfBirthEn"));

				if (localPlaceOfBirthEn != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPlaceOfBirthEn));
				} else {
					throw new org.apache.axis2.databinding.ADBException("placeOfBirthEn cannot be null!!");
				}
			} if (localPlaceOfBirthArTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"placeOfBirthAr"));

				if (localPlaceOfBirthAr != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPlaceOfBirthAr));
				} else {
					throw new org.apache.axis2.databinding.ADBException("placeOfBirthAr cannot be null!!");
				}
			} if (localIssuingAuthEnTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"IssuingAuthEn"));

				if (localIssuingAuthEn != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIssuingAuthEn));
				} else {
					throw new org.apache.axis2.databinding.ADBException("IssuingAuthEn cannot be null!!");
				}
			} if (localIssuingAuthArTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"IssuingAuthAr"));

				if (localIssuingAuthAr != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIssuingAuthAr));
				} else {
					throw new org.apache.axis2.databinding.ADBException("IssuingAuthAr cannot be null!!");
				}
			} if (localPassportTypeEnTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"passportTypeEn"));

				if (localPassportTypeEn != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPassportTypeEn));
				} else {
					throw new org.apache.axis2.databinding.ADBException("passportTypeEn cannot be null!!");
				}
			} if (localCountryCodeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"countryCode"));

				if (localCountryCode != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCountryCode));
				} else {
					throw new org.apache.axis2.databinding.ADBException("countryCode cannot be null!!");
				}
			} if (localFileNumberTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"fileNumber"));

				if (localFileNumber != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFileNumber));
				} else {
					throw new org.apache.axis2.databinding.ADBException("fileNumber cannot be null!!");
				}
			} if (localProfessionCodeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"professionCode"));

				if (localProfessionCode != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProfessionCode));
				} else {
					throw new org.apache.axis2.databinding.ADBException("professionCode cannot be null!!");
				}
			} if (localProfessionArTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"professionAr"));

				if (localProfessionAr != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProfessionAr));
				} else {
					throw new org.apache.axis2.databinding.ADBException("professionAr cannot be null!!");
				}
			} if (localProfessionEnTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"professionEn"));

				if (localProfessionEn != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProfessionEn));
				} else {
					throw new org.apache.axis2.databinding.ADBException("professionEn cannot be null!!");
				}
			} if (localSponsorNoTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"sponsorNo"));

				if (localSponsorNo != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSponsorNo));
				} else {
					throw new org.apache.axis2.databinding.ADBException("sponsorNo cannot be null!!");
				}
			} if (localSponsorArTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"sponsorAr"));

				if (localSponsorAr != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSponsorAr));
				} else {
					throw new org.apache.axis2.databinding.ADBException("sponsorAr cannot be null!!");
				}
			} if (localSponsorEnTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"sponsorEn"));

				if (localSponsorEn != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSponsorEn));
				} else {
					throw new org.apache.axis2.databinding.ADBException("sponsorEn cannot be null!!");
				}
			} if (localAccompaniedByTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"accompaniedBy"));

				if (localAccompaniedBy != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAccompaniedBy));
				} else {
					throw new org.apache.axis2.databinding.ADBException("accompaniedBy cannot be null!!");
				}
			} if (localExternalRequestIdTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"externalRequestId"));

				if (localExternalRequestId != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExternalRequestId));
				} else {
					throw new org.apache.axis2.databinding.ADBException("externalRequestId cannot be null!!");
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
			public static DocumentDetails_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				DocumentDetails_type0 object =
						new DocumentDetails_type0();

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

							if (!"documentDetails_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (DocumentDetails_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","documentType").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"documentType" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDocumentType(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","externalDocRefNo").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"externalDocRefNo" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setExternalDocRefNo(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","transactionRefNo").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"transactionRefNo" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setTransactionRefNo(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","edmsDocRefNo").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"edmsDocRefNo" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setEdmsDocRefNo(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","documentExpiryDate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"documentExpiryDate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDocumentExpiryDate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","documentAssuranceLevel").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"documentAssuranceLevel" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDocumentAssuranceLevel(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","createdOn").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"createdOn" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCreatedOn(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","creator").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"creator" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCreator(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","externalBlockChainId").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"externalBlockChainId" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setExternalBlockChainId(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","status").equals(reader.getName())){

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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","nameAr").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"nameAr" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNameAr(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","nameEn").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"nameEn" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNameEn(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","cardNumber").equals(reader.getName())){

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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","dateOfBirth").equals(reader.getName())){

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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","documentTypeCode").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"documentTypeCode" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDocumentTypeCode(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","issueDate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"issueDate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setIssueDate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","expiryDate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"expiryDate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setExpiryDate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","genderAr").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"genderAr" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setGenderAr(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","genderCode").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"genderCode" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setGenderCode(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","genderEn").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"genderEn" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setGenderEn(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","idNumber").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"idNumber" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setIdNumber(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","nationalityAr").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"nationalityAr" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNationalityAr(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","nationalityCode").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"nationalityCode" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNationalityCode(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","nationalityEn").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"nationalityEn" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNationalityEn(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","photoBase64").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"photoBase64" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPhotoBase64(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","signatureBase64").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"signatureBase64" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setSignatureBase64(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","placeOfBirthEn").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"placeOfBirthEn" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPlaceOfBirthEn(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","placeOfBirthAr").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"placeOfBirthAr" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPlaceOfBirthAr(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","IssuingAuthEn").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"IssuingAuthEn" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setIssuingAuthEn(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","IssuingAuthAr").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"IssuingAuthAr" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setIssuingAuthAr(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","passportTypeEn").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"passportTypeEn" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPassportTypeEn(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","countryCode").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"countryCode" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCountryCode(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","fileNumber").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"fileNumber" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setFileNumber(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","professionCode").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"professionCode" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setProfessionCode(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","professionAr").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"professionAr" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setProfessionAr(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","professionEn").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"professionEn" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setProfessionEn(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","sponsorNo").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"sponsorNo" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setSponsorNo(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","sponsorAr").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"sponsorAr" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setSponsorAr(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","sponsorEn").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"sponsorEn" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setSponsorEn(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","accompaniedBy").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"accompaniedBy" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAccompaniedBy(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","externalRequestId").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"externalRequestId" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setExternalRequestId(
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


	public static class FetchCustomerUAEPassInfoRes_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = fetchCustomerUAEPassInfoRes_type0
                Namespace URI = http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for TitleEnglish
		 */


		protected java.lang.String localTitleEnglish ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localTitleEnglishTracker = false ;

		public boolean isTitleEnglishSpecified(){
			return localTitleEnglishTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getTitleEnglish(){
			return localTitleEnglish;
		}



		/**
		 * Auto generated setter method
		 * @param param TitleEnglish
		 */
		public void setTitleEnglish(java.lang.String param){
			localTitleEnglishTracker = param != null;

			this.localTitleEnglish=param;


		}


		/**
		 * field for TitleArabic
		 */


		protected java.lang.String localTitleArabic ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localTitleArabicTracker = false ;

		public boolean isTitleArabicSpecified(){
			return localTitleArabicTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getTitleArabic(){
			return localTitleArabic;
		}



		/**
		 * Auto generated setter method
		 * @param param TitleArabic
		 */
		public void setTitleArabic(java.lang.String param){
			localTitleArabicTracker = param != null;

			this.localTitleArabic=param;


		}


		/**
		 * field for FullNameEnglish
		 */


		protected java.lang.String localFullNameEnglish ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localFullNameEnglishTracker = false ;

		public boolean isFullNameEnglishSpecified(){
			return localFullNameEnglishTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getFullNameEnglish(){
			return localFullNameEnglish;
		}



		/**
		 * Auto generated setter method
		 * @param param FullNameEnglish
		 */
		public void setFullNameEnglish(java.lang.String param){
			localFullNameEnglishTracker = param != null;

			this.localFullNameEnglish=param;


		}


		/**
		 * field for FullNameArabic
		 */


		protected java.lang.String localFullNameArabic ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localFullNameArabicTracker = false ;

		public boolean isFullNameArabicSpecified(){
			return localFullNameArabicTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getFullNameArabic(){
			return localFullNameArabic;
		}



		/**
		 * Auto generated setter method
		 * @param param FullNameArabic
		 */
		public void setFullNameArabic(java.lang.String param){
			localFullNameArabicTracker = param != null;

			this.localFullNameArabic=param;


		}


		/**
		 * field for FirstNameEnglish
		 */


		protected java.lang.String localFirstNameEnglish ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localFirstNameEnglishTracker = false ;

		public boolean isFirstNameEnglishSpecified(){
			return localFirstNameEnglishTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getFirstNameEnglish(){
			return localFirstNameEnglish;
		}



		/**
		 * Auto generated setter method
		 * @param param FirstNameEnglish
		 */
		public void setFirstNameEnglish(java.lang.String param){
			localFirstNameEnglishTracker = param != null;

			this.localFirstNameEnglish=param;


		}


		/**
		 * field for FirstNameArabic
		 */


		protected java.lang.String localFirstNameArabic ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localFirstNameArabicTracker = false ;

		public boolean isFirstNameArabicSpecified(){
			return localFirstNameArabicTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getFirstNameArabic(){
			return localFirstNameArabic;
		}



		/**
		 * Auto generated setter method
		 * @param param FirstNameArabic
		 */
		public void setFirstNameArabic(java.lang.String param){
			localFirstNameArabicTracker = param != null;

			this.localFirstNameArabic=param;


		}


		/**
		 * field for LastNameEnglish
		 */


		protected java.lang.String localLastNameEnglish ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLastNameEnglishTracker = false ;

		public boolean isLastNameEnglishSpecified(){
			return localLastNameEnglishTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLastNameEnglish(){
			return localLastNameEnglish;
		}



		/**
		 * Auto generated setter method
		 * @param param LastNameEnglish
		 */
		public void setLastNameEnglish(java.lang.String param){
			localLastNameEnglishTracker = param != null;

			this.localLastNameEnglish=param;


		}


		/**
		 * field for LastNameArabic
		 */


		protected java.lang.String localLastNameArabic ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLastNameArabicTracker = false ;

		public boolean isLastNameArabicSpecified(){
			return localLastNameArabicTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLastNameArabic(){
			return localLastNameArabic;
		}



		/**
		 * Auto generated setter method
		 * @param param LastNameArabic
		 */
		public void setLastNameArabic(java.lang.String param){
			localLastNameArabicTracker = param != null;

			this.localLastNameArabic=param;


		}


		/**
		 * field for NationalityEnglish
		 */


		protected java.lang.String localNationalityEnglish ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNationalityEnglishTracker = false ;

		public boolean isNationalityEnglishSpecified(){
			return localNationalityEnglishTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNationalityEnglish(){
			return localNationalityEnglish;
		}



		/**
		 * Auto generated setter method
		 * @param param NationalityEnglish
		 */
		public void setNationalityEnglish(java.lang.String param){
			localNationalityEnglishTracker = param != null;

			this.localNationalityEnglish=param;


		}


		/**
		 * field for NationalityArabic
		 */


		protected java.lang.String localNationalityArabic ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNationalityArabicTracker = false ;

		public boolean isNationalityArabicSpecified(){
			return localNationalityArabicTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNationalityArabic(){
			return localNationalityArabic;
		}



		/**
		 * Auto generated setter method
		 * @param param NationalityArabic
		 */
		public void setNationalityArabic(java.lang.String param){
			localNationalityArabicTracker = param != null;

			this.localNationalityArabic=param;


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
		 * field for EmailId
		 */


		protected java.lang.String localEmailId ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localEmailIdTracker = false ;

		public boolean isEmailIdSpecified(){
			return localEmailIdTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getEmailId(){
			return localEmailId;
		}



		/**
		 * Auto generated setter method
		 * @param param EmailId
		 */
		public void setEmailId(java.lang.String param){
			localEmailIdTracker = param != null;

			this.localEmailId=param;


		}


		/**
		 * field for MobileNumber
		 */


		protected java.lang.String localMobileNumber ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localMobileNumberTracker = false ;

		public boolean isMobileNumberSpecified(){
			return localMobileNumberTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getMobileNumber(){
			return localMobileNumber;
		}



		/**
		 * Auto generated setter method
		 * @param param MobileNumber
		 */
		public void setMobileNumber(java.lang.String param){
			localMobileNumberTracker = param != null;

			this.localMobileNumber=param;


		}


		/**
		 * field for EmiratesId
		 */


		protected java.lang.String localEmiratesId ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localEmiratesIdTracker = false ;

		public boolean isEmiratesIdSpecified(){
			return localEmiratesIdTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getEmiratesId(){
			return localEmiratesId;
		}



		/**
		 * Auto generated setter method
		 * @param param EmiratesId
		 */
		public void setEmiratesId(java.lang.String param){
			localEmiratesIdTracker = param != null;

			this.localEmiratesId=param;


		}


		/**
		 * field for UserType
		 */


		protected java.lang.String localUserType ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localUserTypeTracker = false ;

		public boolean isUserTypeSpecified(){
			return localUserTypeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getUserType(){
			return localUserType;
		}



		/**
		 * Auto generated setter method
		 * @param param UserType
		 */
		public void setUserType(java.lang.String param){
			localUserTypeTracker = param != null;

			this.localUserType=param;


		}


		/**
		 * field for RegisteredIdType
		 */


		protected java.lang.String localRegisteredIdType ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localRegisteredIdTypeTracker = false ;

		public boolean isRegisteredIdTypeSpecified(){
			return localRegisteredIdTypeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getRegisteredIdType(){
			return localRegisteredIdType;
		}



		/**
		 * Auto generated setter method
		 * @param param RegisteredIdType
		 */
		public void setRegisteredIdType(java.lang.String param){
			localRegisteredIdTypeTracker = param != null;

			this.localRegisteredIdType=param;


		}


		/**
		 * field for UniqueUserIdentifier
		 */


		protected java.lang.String localUniqueUserIdentifier ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localUniqueUserIdentifierTracker = false ;

		public boolean isUniqueUserIdentifierSpecified(){
			return localUniqueUserIdentifierTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getUniqueUserIdentifier(){
			return localUniqueUserIdentifier;
		}



		/**
		 * Auto generated setter method
		 * @param param UniqueUserIdentifier
		 */
		public void setUniqueUserIdentifier(java.lang.String param){
			localUniqueUserIdentifierTracker = param != null;

			this.localUniqueUserIdentifier=param;


		}


		/**
		 * field for SmartDubaiPassId
		 */


		protected java.lang.String localSmartDubaiPassId ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localSmartDubaiPassIdTracker = false ;

		public boolean isSmartDubaiPassIdSpecified(){
			return localSmartDubaiPassIdTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getSmartDubaiPassId(){
			return localSmartDubaiPassId;
		}



		/**
		 * Auto generated setter method
		 * @param param SmartDubaiPassId
		 */
		public void setSmartDubaiPassId(java.lang.String param){
			localSmartDubaiPassIdTracker = param != null;

			this.localSmartDubaiPassId=param;


		}


		/**
		 * field for TransactionId
		 */


		protected java.lang.String localTransactionId ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localTransactionIdTracker = false ;

		public boolean isTransactionIdSpecified(){
			return localTransactionIdTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getTransactionId(){
			return localTransactionId;
		}



		/**
		 * Auto generated setter method
		 * @param param TransactionId
		 */
		public void setTransactionId(java.lang.String param){
			localTransactionIdTracker = param != null;

			this.localTransactionId=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":fetchCustomerUAEPassInfoRes_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"fetchCustomerUAEPassInfoRes_type0",
							xmlWriter);
				}


			}
			if (localTitleEnglishTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "titleEnglish", xmlWriter);


				if (localTitleEnglish==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("titleEnglish cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localTitleEnglish);

				}

				xmlWriter.writeEndElement();
			} if (localTitleArabicTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "titleArabic", xmlWriter);


				if (localTitleArabic==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("titleArabic cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localTitleArabic);

				}

				xmlWriter.writeEndElement();
			} if (localFullNameEnglishTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "fullNameEnglish", xmlWriter);


				if (localFullNameEnglish==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("fullNameEnglish cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localFullNameEnglish);

				}

				xmlWriter.writeEndElement();
			} if (localFullNameArabicTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "fullNameArabic", xmlWriter);


				if (localFullNameArabic==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("fullNameArabic cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localFullNameArabic);

				}

				xmlWriter.writeEndElement();
			} if (localFirstNameEnglishTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "firstNameEnglish", xmlWriter);


				if (localFirstNameEnglish==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("firstNameEnglish cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localFirstNameEnglish);

				}

				xmlWriter.writeEndElement();
			} if (localFirstNameArabicTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "firstNameArabic", xmlWriter);


				if (localFirstNameArabic==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("firstNameArabic cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localFirstNameArabic);

				}

				xmlWriter.writeEndElement();
			} if (localLastNameEnglishTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "lastNameEnglish", xmlWriter);


				if (localLastNameEnglish==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("lastNameEnglish cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLastNameEnglish);

				}

				xmlWriter.writeEndElement();
			} if (localLastNameArabicTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "lastNameArabic", xmlWriter);


				if (localLastNameArabic==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("lastNameArabic cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLastNameArabic);

				}

				xmlWriter.writeEndElement();
			} if (localNationalityEnglishTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "nationalityEnglish", xmlWriter);


				if (localNationalityEnglish==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("nationalityEnglish cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNationalityEnglish);

				}

				xmlWriter.writeEndElement();
			} if (localNationalityArabicTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "nationalityArabic", xmlWriter);


				if (localNationalityArabic==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("nationalityArabic cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNationalityArabic);

				}

				xmlWriter.writeEndElement();
			} if (localGenderTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "gender", xmlWriter);


				if (localGender==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("gender cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localGender);

				}

				xmlWriter.writeEndElement();
			} if (localEmailIdTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "emailId", xmlWriter);


				if (localEmailId==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("emailId cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localEmailId);

				}

				xmlWriter.writeEndElement();
			} if (localMobileNumberTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "mobileNumber", xmlWriter);


				if (localMobileNumber==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("mobileNumber cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localMobileNumber);

				}

				xmlWriter.writeEndElement();
			} if (localEmiratesIdTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "emiratesId", xmlWriter);


				if (localEmiratesId==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("emiratesId cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localEmiratesId);

				}

				xmlWriter.writeEndElement();
			} if (localUserTypeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "userType", xmlWriter);


				if (localUserType==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("userType cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localUserType);

				}

				xmlWriter.writeEndElement();
			} if (localRegisteredIdTypeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "registeredIdType", xmlWriter);


				if (localRegisteredIdType==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("registeredIdType cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localRegisteredIdType);

				}

				xmlWriter.writeEndElement();
			} if (localUniqueUserIdentifierTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "uniqueUserIdentifier", xmlWriter);


				if (localUniqueUserIdentifier==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("uniqueUserIdentifier cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localUniqueUserIdentifier);

				}

				xmlWriter.writeEndElement();
			} if (localSmartDubaiPassIdTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "smartDubaiPassId", xmlWriter);


				if (localSmartDubaiPassId==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("smartDubaiPassId cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localSmartDubaiPassId);

				}

				xmlWriter.writeEndElement();
			} if (localTransactionIdTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "transactionId", xmlWriter);


				if (localTransactionId==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("transactionId cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localTransactionId);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd")){
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

			if (localTitleEnglishTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"titleEnglish"));

				if (localTitleEnglish != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTitleEnglish));
				} else {
					throw new org.apache.axis2.databinding.ADBException("titleEnglish cannot be null!!");
				}
			} if (localTitleArabicTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"titleArabic"));

				if (localTitleArabic != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTitleArabic));
				} else {
					throw new org.apache.axis2.databinding.ADBException("titleArabic cannot be null!!");
				}
			} if (localFullNameEnglishTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"fullNameEnglish"));

				if (localFullNameEnglish != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFullNameEnglish));
				} else {
					throw new org.apache.axis2.databinding.ADBException("fullNameEnglish cannot be null!!");
				}
			} if (localFullNameArabicTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"fullNameArabic"));

				if (localFullNameArabic != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFullNameArabic));
				} else {
					throw new org.apache.axis2.databinding.ADBException("fullNameArabic cannot be null!!");
				}
			} if (localFirstNameEnglishTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"firstNameEnglish"));

				if (localFirstNameEnglish != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFirstNameEnglish));
				} else {
					throw new org.apache.axis2.databinding.ADBException("firstNameEnglish cannot be null!!");
				}
			} if (localFirstNameArabicTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"firstNameArabic"));

				if (localFirstNameArabic != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFirstNameArabic));
				} else {
					throw new org.apache.axis2.databinding.ADBException("firstNameArabic cannot be null!!");
				}
			} if (localLastNameEnglishTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"lastNameEnglish"));

				if (localLastNameEnglish != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLastNameEnglish));
				} else {
					throw new org.apache.axis2.databinding.ADBException("lastNameEnglish cannot be null!!");
				}
			} if (localLastNameArabicTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"lastNameArabic"));

				if (localLastNameArabic != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLastNameArabic));
				} else {
					throw new org.apache.axis2.databinding.ADBException("lastNameArabic cannot be null!!");
				}
			} if (localNationalityEnglishTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"nationalityEnglish"));

				if (localNationalityEnglish != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNationalityEnglish));
				} else {
					throw new org.apache.axis2.databinding.ADBException("nationalityEnglish cannot be null!!");
				}
			} if (localNationalityArabicTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"nationalityArabic"));

				if (localNationalityArabic != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNationalityArabic));
				} else {
					throw new org.apache.axis2.databinding.ADBException("nationalityArabic cannot be null!!");
				}
			} if (localGenderTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"gender"));

				if (localGender != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGender));
				} else {
					throw new org.apache.axis2.databinding.ADBException("gender cannot be null!!");
				}
			} if (localEmailIdTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"emailId"));

				if (localEmailId != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEmailId));
				} else {
					throw new org.apache.axis2.databinding.ADBException("emailId cannot be null!!");
				}
			} if (localMobileNumberTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"mobileNumber"));

				if (localMobileNumber != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMobileNumber));
				} else {
					throw new org.apache.axis2.databinding.ADBException("mobileNumber cannot be null!!");
				}
			} if (localEmiratesIdTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"emiratesId"));

				if (localEmiratesId != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEmiratesId));
				} else {
					throw new org.apache.axis2.databinding.ADBException("emiratesId cannot be null!!");
				}
			} if (localUserTypeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"userType"));

				if (localUserType != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUserType));
				} else {
					throw new org.apache.axis2.databinding.ADBException("userType cannot be null!!");
				}
			} if (localRegisteredIdTypeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"registeredIdType"));

				if (localRegisteredIdType != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRegisteredIdType));
				} else {
					throw new org.apache.axis2.databinding.ADBException("registeredIdType cannot be null!!");
				}
			} if (localUniqueUserIdentifierTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"uniqueUserIdentifier"));

				if (localUniqueUserIdentifier != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUniqueUserIdentifier));
				} else {
					throw new org.apache.axis2.databinding.ADBException("uniqueUserIdentifier cannot be null!!");
				}
			} if (localSmartDubaiPassIdTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"smartDubaiPassId"));

				if (localSmartDubaiPassId != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSmartDubaiPassId));
				} else {
					throw new org.apache.axis2.databinding.ADBException("smartDubaiPassId cannot be null!!");
				}
			} if (localTransactionIdTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"transactionId"));

				if (localTransactionId != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTransactionId));
				} else {
					throw new org.apache.axis2.databinding.ADBException("transactionId cannot be null!!");
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
			public static FetchCustomerUAEPassInfoRes_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				FetchCustomerUAEPassInfoRes_type0 object =
						new FetchCustomerUAEPassInfoRes_type0();

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

							if (!"fetchCustomerUAEPassInfoRes_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (FetchCustomerUAEPassInfoRes_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","titleEnglish").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"titleEnglish" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setTitleEnglish(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","titleArabic").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"titleArabic" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setTitleArabic(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","fullNameEnglish").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"fullNameEnglish" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setFullNameEnglish(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","fullNameArabic").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"fullNameArabic" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setFullNameArabic(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","firstNameEnglish").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"firstNameEnglish" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setFirstNameEnglish(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","firstNameArabic").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"firstNameArabic" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setFirstNameArabic(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","lastNameEnglish").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"lastNameEnglish" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLastNameEnglish(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","lastNameArabic").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"lastNameArabic" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLastNameArabic(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","nationalityEnglish").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"nationalityEnglish" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNationalityEnglish(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","nationalityArabic").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"nationalityArabic" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNationalityArabic(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","gender").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"gender" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setGender(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","emailId").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"emailId" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setEmailId(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","mobileNumber").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"mobileNumber" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setMobileNumber(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","emiratesId").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"emiratesId" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setEmiratesId(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","userType").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"userType" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setUserType(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","registeredIdType").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"registeredIdType" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setRegisteredIdType(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","uniqueUserIdentifier").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"uniqueUserIdentifier" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setUniqueUserIdentifier(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","smartDubaiPassId").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"smartDubaiPassId" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setSmartDubaiPassId(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","transactionId").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"transactionId" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setTransactionId(
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


	public static class DocumentDetails_type1
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = documentDetails_type1
                Namespace URI = http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for DocumentType
		 */


		protected java.lang.String localDocumentType ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDocumentTypeTracker = false ;

		public boolean isDocumentTypeSpecified(){
			return localDocumentTypeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDocumentType(){
			return localDocumentType;
		}



		/**
		 * Auto generated setter method
		 * @param param DocumentType
		 */
		public void setDocumentType(java.lang.String param){
			localDocumentTypeTracker = param != null;

			this.localDocumentType=param;


		}


		/**
		 * field for ExternalDocRefNo
		 */


		protected java.lang.String localExternalDocRefNo ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localExternalDocRefNoTracker = false ;

		public boolean isExternalDocRefNoSpecified(){
			return localExternalDocRefNoTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getExternalDocRefNo(){
			return localExternalDocRefNo;
		}



		/**
		 * Auto generated setter method
		 * @param param ExternalDocRefNo
		 */
		public void setExternalDocRefNo(java.lang.String param){
			localExternalDocRefNoTracker = param != null;

			this.localExternalDocRefNo=param;


		}


		/**
		 * field for TransactionRefNo
		 */


		protected java.lang.String localTransactionRefNo ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localTransactionRefNoTracker = false ;

		public boolean isTransactionRefNoSpecified(){
			return localTransactionRefNoTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getTransactionRefNo(){
			return localTransactionRefNo;
		}



		/**
		 * Auto generated setter method
		 * @param param TransactionRefNo
		 */
		public void setTransactionRefNo(java.lang.String param){
			localTransactionRefNoTracker = param != null;

			this.localTransactionRefNo=param;


		}


		/**
		 * field for EdmsDocRefNo
		 */


		protected java.lang.String localEdmsDocRefNo ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localEdmsDocRefNoTracker = false ;

		public boolean isEdmsDocRefNoSpecified(){
			return localEdmsDocRefNoTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getEdmsDocRefNo(){
			return localEdmsDocRefNo;
		}



		/**
		 * Auto generated setter method
		 * @param param EdmsDocRefNo
		 */
		public void setEdmsDocRefNo(java.lang.String param){
			localEdmsDocRefNoTracker = param != null;

			this.localEdmsDocRefNo=param;


		}


		/**
		 * field for DocumentExpiryDate
		 */


		protected java.lang.String localDocumentExpiryDate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDocumentExpiryDateTracker = false ;

		public boolean isDocumentExpiryDateSpecified(){
			return localDocumentExpiryDateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDocumentExpiryDate(){
			return localDocumentExpiryDate;
		}



		/**
		 * Auto generated setter method
		 * @param param DocumentExpiryDate
		 */
		public void setDocumentExpiryDate(java.lang.String param){
			localDocumentExpiryDateTracker = param != null;

			this.localDocumentExpiryDate=param;


		}


		/**
		 * field for DocumentAssuranceLevel
		 */


		protected java.lang.String localDocumentAssuranceLevel ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDocumentAssuranceLevelTracker = false ;

		public boolean isDocumentAssuranceLevelSpecified(){
			return localDocumentAssuranceLevelTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDocumentAssuranceLevel(){
			return localDocumentAssuranceLevel;
		}



		/**
		 * Auto generated setter method
		 * @param param DocumentAssuranceLevel
		 */
		public void setDocumentAssuranceLevel(java.lang.String param){
			localDocumentAssuranceLevelTracker = param != null;

			this.localDocumentAssuranceLevel=param;


		}


		/**
		 * field for CreatedOn
		 */


		protected java.lang.String localCreatedOn ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCreatedOnTracker = false ;

		public boolean isCreatedOnSpecified(){
			return localCreatedOnTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCreatedOn(){
			return localCreatedOn;
		}



		/**
		 * Auto generated setter method
		 * @param param CreatedOn
		 */
		public void setCreatedOn(java.lang.String param){
			localCreatedOnTracker = param != null;

			this.localCreatedOn=param;


		}


		/**
		 * field for Creator
		 */


		protected java.lang.String localCreator ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCreatorTracker = false ;

		public boolean isCreatorSpecified(){
			return localCreatorTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCreator(){
			return localCreator;
		}



		/**
		 * Auto generated setter method
		 * @param param Creator
		 */
		public void setCreator(java.lang.String param){
			localCreatorTracker = param != null;

			this.localCreator=param;


		}


		/**
		 * field for ExternalBlockChainId
		 */


		protected java.lang.String localExternalBlockChainId ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localExternalBlockChainIdTracker = false ;

		public boolean isExternalBlockChainIdSpecified(){
			return localExternalBlockChainIdTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getExternalBlockChainId(){
			return localExternalBlockChainId;
		}



		/**
		 * Auto generated setter method
		 * @param param ExternalBlockChainId
		 */
		public void setExternalBlockChainId(java.lang.String param){
			localExternalBlockChainIdTracker = param != null;

			this.localExternalBlockChainId=param;


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
		 * field for NameAr
		 */


		protected java.lang.String localNameAr ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNameArTracker = false ;

		public boolean isNameArSpecified(){
			return localNameArTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNameAr(){
			return localNameAr;
		}



		/**
		 * Auto generated setter method
		 * @param param NameAr
		 */
		public void setNameAr(java.lang.String param){
			localNameArTracker = param != null;

			this.localNameAr=param;


		}


		/**
		 * field for NameEn
		 */


		protected java.lang.String localNameEn ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNameEnTracker = false ;

		public boolean isNameEnSpecified(){
			return localNameEnTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNameEn(){
			return localNameEn;
		}



		/**
		 * Auto generated setter method
		 * @param param NameEn
		 */
		public void setNameEn(java.lang.String param){
			localNameEnTracker = param != null;

			this.localNameEn=param;


		}


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
		 * field for DocumentTypeCode
		 */


		protected java.lang.String localDocumentTypeCode ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDocumentTypeCodeTracker = false ;

		public boolean isDocumentTypeCodeSpecified(){
			return localDocumentTypeCodeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDocumentTypeCode(){
			return localDocumentTypeCode;
		}



		/**
		 * Auto generated setter method
		 * @param param DocumentTypeCode
		 */
		public void setDocumentTypeCode(java.lang.String param){
			localDocumentTypeCodeTracker = param != null;

			this.localDocumentTypeCode=param;


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
		 * field for GenderAr
		 */


		protected java.lang.String localGenderAr ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localGenderArTracker = false ;

		public boolean isGenderArSpecified(){
			return localGenderArTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getGenderAr(){
			return localGenderAr;
		}



		/**
		 * Auto generated setter method
		 * @param param GenderAr
		 */
		public void setGenderAr(java.lang.String param){
			localGenderArTracker = param != null;

			this.localGenderAr=param;


		}


		/**
		 * field for GenderCode
		 */


		protected java.lang.String localGenderCode ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localGenderCodeTracker = false ;

		public boolean isGenderCodeSpecified(){
			return localGenderCodeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getGenderCode(){
			return localGenderCode;
		}



		/**
		 * Auto generated setter method
		 * @param param GenderCode
		 */
		public void setGenderCode(java.lang.String param){
			localGenderCodeTracker = param != null;

			this.localGenderCode=param;


		}


		/**
		 * field for GenderEn
		 */


		protected java.lang.String localGenderEn ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localGenderEnTracker = false ;

		public boolean isGenderEnSpecified(){
			return localGenderEnTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getGenderEn(){
			return localGenderEn;
		}



		/**
		 * Auto generated setter method
		 * @param param GenderEn
		 */
		public void setGenderEn(java.lang.String param){
			localGenderEnTracker = param != null;

			this.localGenderEn=param;


		}


		/**
		 * field for IdNumber
		 */


		protected java.lang.String localIdNumber ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localIdNumberTracker = false ;

		public boolean isIdNumberSpecified(){
			return localIdNumberTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getIdNumber(){
			return localIdNumber;
		}



		/**
		 * Auto generated setter method
		 * @param param IdNumber
		 */
		public void setIdNumber(java.lang.String param){
			localIdNumberTracker = param != null;

			this.localIdNumber=param;


		}


		/**
		 * field for NationalityAr
		 */


		protected java.lang.String localNationalityAr ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNationalityArTracker = false ;

		public boolean isNationalityArSpecified(){
			return localNationalityArTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNationalityAr(){
			return localNationalityAr;
		}



		/**
		 * Auto generated setter method
		 * @param param NationalityAr
		 */
		public void setNationalityAr(java.lang.String param){
			localNationalityArTracker = param != null;

			this.localNationalityAr=param;


		}


		/**
		 * field for NationalityCode
		 */


		protected java.lang.String localNationalityCode ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNationalityCodeTracker = false ;

		public boolean isNationalityCodeSpecified(){
			return localNationalityCodeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNationalityCode(){
			return localNationalityCode;
		}



		/**
		 * Auto generated setter method
		 * @param param NationalityCode
		 */
		public void setNationalityCode(java.lang.String param){
			localNationalityCodeTracker = param != null;

			this.localNationalityCode=param;


		}


		/**
		 * field for NationalityEn
		 */


		protected java.lang.String localNationalityEn ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNationalityEnTracker = false ;

		public boolean isNationalityEnSpecified(){
			return localNationalityEnTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNationalityEn(){
			return localNationalityEn;
		}



		/**
		 * Auto generated setter method
		 * @param param NationalityEn
		 */
		public void setNationalityEn(java.lang.String param){
			localNationalityEnTracker = param != null;

			this.localNationalityEn=param;


		}


		/**
		 * field for PhotoBase64
		 */


		protected java.lang.String localPhotoBase64 ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPhotoBase64Tracker = false ;

		public boolean isPhotoBase64Specified(){
			return localPhotoBase64Tracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPhotoBase64(){
			return localPhotoBase64;
		}



		/**
		 * Auto generated setter method
		 * @param param PhotoBase64
		 */
		public void setPhotoBase64(java.lang.String param){
			localPhotoBase64Tracker = param != null;

			this.localPhotoBase64=param;


		}


		/**
		 * field for SignatureBase64
		 */


		protected java.lang.String localSignatureBase64 ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localSignatureBase64Tracker = false ;

		public boolean isSignatureBase64Specified(){
			return localSignatureBase64Tracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getSignatureBase64(){
			return localSignatureBase64;
		}



		/**
		 * Auto generated setter method
		 * @param param SignatureBase64
		 */
		public void setSignatureBase64(java.lang.String param){
			localSignatureBase64Tracker = param != null;

			this.localSignatureBase64=param;


		}


		/**
		 * field for PlaceOfBirthEn
		 */


		protected java.lang.String localPlaceOfBirthEn ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPlaceOfBirthEnTracker = false ;

		public boolean isPlaceOfBirthEnSpecified(){
			return localPlaceOfBirthEnTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPlaceOfBirthEn(){
			return localPlaceOfBirthEn;
		}



		/**
		 * Auto generated setter method
		 * @param param PlaceOfBirthEn
		 */
		public void setPlaceOfBirthEn(java.lang.String param){
			localPlaceOfBirthEnTracker = param != null;

			this.localPlaceOfBirthEn=param;


		}


		/**
		 * field for PlaceOfBirthAr
		 */


		protected java.lang.String localPlaceOfBirthAr ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPlaceOfBirthArTracker = false ;

		public boolean isPlaceOfBirthArSpecified(){
			return localPlaceOfBirthArTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPlaceOfBirthAr(){
			return localPlaceOfBirthAr;
		}



		/**
		 * Auto generated setter method
		 * @param param PlaceOfBirthAr
		 */
		public void setPlaceOfBirthAr(java.lang.String param){
			localPlaceOfBirthArTracker = param != null;

			this.localPlaceOfBirthAr=param;


		}


		/**
		 * field for IssuingAuthEn
		 */


		protected java.lang.String localIssuingAuthEn ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localIssuingAuthEnTracker = false ;

		public boolean isIssuingAuthEnSpecified(){
			return localIssuingAuthEnTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getIssuingAuthEn(){
			return localIssuingAuthEn;
		}



		/**
		 * Auto generated setter method
		 * @param param IssuingAuthEn
		 */
		public void setIssuingAuthEn(java.lang.String param){
			localIssuingAuthEnTracker = param != null;

			this.localIssuingAuthEn=param;


		}


		/**
		 * field for IssuingAuthAr
		 */


		protected java.lang.String localIssuingAuthAr ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localIssuingAuthArTracker = false ;

		public boolean isIssuingAuthArSpecified(){
			return localIssuingAuthArTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getIssuingAuthAr(){
			return localIssuingAuthAr;
		}



		/**
		 * Auto generated setter method
		 * @param param IssuingAuthAr
		 */
		public void setIssuingAuthAr(java.lang.String param){
			localIssuingAuthArTracker = param != null;

			this.localIssuingAuthAr=param;


		}


		/**
		 * field for PassportTypeEn
		 */


		protected java.lang.String localPassportTypeEn ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPassportTypeEnTracker = false ;

		public boolean isPassportTypeEnSpecified(){
			return localPassportTypeEnTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPassportTypeEn(){
			return localPassportTypeEn;
		}



		/**
		 * Auto generated setter method
		 * @param param PassportTypeEn
		 */
		public void setPassportTypeEn(java.lang.String param){
			localPassportTypeEnTracker = param != null;

			this.localPassportTypeEn=param;


		}


		/**
		 * field for CountryCode
		 */


		protected java.lang.String localCountryCode ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCountryCodeTracker = false ;

		public boolean isCountryCodeSpecified(){
			return localCountryCodeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCountryCode(){
			return localCountryCode;
		}



		/**
		 * Auto generated setter method
		 * @param param CountryCode
		 */
		public void setCountryCode(java.lang.String param){
			localCountryCodeTracker = param != null;

			this.localCountryCode=param;


		}


		/**
		 * field for FileNumber
		 */


		protected java.lang.String localFileNumber ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localFileNumberTracker = false ;

		public boolean isFileNumberSpecified(){
			return localFileNumberTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getFileNumber(){
			return localFileNumber;
		}



		/**
		 * Auto generated setter method
		 * @param param FileNumber
		 */
		public void setFileNumber(java.lang.String param){
			localFileNumberTracker = param != null;

			this.localFileNumber=param;


		}


		/**
		 * field for ProfessionCode
		 */


		protected java.lang.String localProfessionCode ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localProfessionCodeTracker = false ;

		public boolean isProfessionCodeSpecified(){
			return localProfessionCodeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getProfessionCode(){
			return localProfessionCode;
		}



		/**
		 * Auto generated setter method
		 * @param param ProfessionCode
		 */
		public void setProfessionCode(java.lang.String param){
			localProfessionCodeTracker = param != null;

			this.localProfessionCode=param;


		}


		/**
		 * field for ProfessionAr
		 */


		protected java.lang.String localProfessionAr ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localProfessionArTracker = false ;

		public boolean isProfessionArSpecified(){
			return localProfessionArTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getProfessionAr(){
			return localProfessionAr;
		}



		/**
		 * Auto generated setter method
		 * @param param ProfessionAr
		 */
		public void setProfessionAr(java.lang.String param){
			localProfessionArTracker = param != null;

			this.localProfessionAr=param;


		}


		/**
		 * field for ProfessionEn
		 */


		protected java.lang.String localProfessionEn ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localProfessionEnTracker = false ;

		public boolean isProfessionEnSpecified(){
			return localProfessionEnTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getProfessionEn(){
			return localProfessionEn;
		}



		/**
		 * Auto generated setter method
		 * @param param ProfessionEn
		 */
		public void setProfessionEn(java.lang.String param){
			localProfessionEnTracker = param != null;

			this.localProfessionEn=param;


		}


		/**
		 * field for SponsorNo
		 */


		protected java.lang.String localSponsorNo ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localSponsorNoTracker = false ;

		public boolean isSponsorNoSpecified(){
			return localSponsorNoTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getSponsorNo(){
			return localSponsorNo;
		}



		/**
		 * Auto generated setter method
		 * @param param SponsorNo
		 */
		public void setSponsorNo(java.lang.String param){
			localSponsorNoTracker = param != null;

			this.localSponsorNo=param;


		}


		/**
		 * field for SponsorAr
		 */


		protected java.lang.String localSponsorAr ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localSponsorArTracker = false ;

		public boolean isSponsorArSpecified(){
			return localSponsorArTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getSponsorAr(){
			return localSponsorAr;
		}



		/**
		 * Auto generated setter method
		 * @param param SponsorAr
		 */
		public void setSponsorAr(java.lang.String param){
			localSponsorArTracker = param != null;

			this.localSponsorAr=param;


		}


		/**
		 * field for SponsorEn
		 */


		protected java.lang.String localSponsorEn ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localSponsorEnTracker = false ;

		public boolean isSponsorEnSpecified(){
			return localSponsorEnTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getSponsorEn(){
			return localSponsorEn;
		}



		/**
		 * Auto generated setter method
		 * @param param SponsorEn
		 */
		public void setSponsorEn(java.lang.String param){
			localSponsorEnTracker = param != null;

			this.localSponsorEn=param;


		}


		/**
		 * field for AccompaniedBy
		 */


		protected java.lang.String localAccompaniedBy ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAccompaniedByTracker = false ;

		public boolean isAccompaniedBySpecified(){
			return localAccompaniedByTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getAccompaniedBy(){
			return localAccompaniedBy;
		}



		/**
		 * Auto generated setter method
		 * @param param AccompaniedBy
		 */
		public void setAccompaniedBy(java.lang.String param){
			localAccompaniedByTracker = param != null;

			this.localAccompaniedBy=param;


		}


		/**
		 * field for NotifyToCustomer
		 */


		protected java.lang.String localNotifyToCustomer ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNotifyToCustomerTracker = false ;

		public boolean isNotifyToCustomerSpecified(){
			return localNotifyToCustomerTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNotifyToCustomer(){
			return localNotifyToCustomer;
		}



		/**
		 * Auto generated setter method
		 * @param param NotifyToCustomer
		 */
		public void setNotifyToCustomer(java.lang.String param){
			localNotifyToCustomerTracker = param != null;

			this.localNotifyToCustomer=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":documentDetails_type1",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"documentDetails_type1",
							xmlWriter);
				}


			}
			if (localDocumentTypeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "documentType", xmlWriter);


				if (localDocumentType==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("documentType cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDocumentType);

				}

				xmlWriter.writeEndElement();
			} if (localExternalDocRefNoTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "externalDocRefNo", xmlWriter);


				if (localExternalDocRefNo==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("externalDocRefNo cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localExternalDocRefNo);

				}

				xmlWriter.writeEndElement();
			} if (localTransactionRefNoTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "transactionRefNo", xmlWriter);


				if (localTransactionRefNo==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("transactionRefNo cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localTransactionRefNo);

				}

				xmlWriter.writeEndElement();
			} if (localEdmsDocRefNoTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "edmsDocRefNo", xmlWriter);


				if (localEdmsDocRefNo==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("edmsDocRefNo cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localEdmsDocRefNo);

				}

				xmlWriter.writeEndElement();
			} if (localDocumentExpiryDateTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "documentExpiryDate", xmlWriter);


				if (localDocumentExpiryDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("documentExpiryDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDocumentExpiryDate);

				}

				xmlWriter.writeEndElement();
			} if (localDocumentAssuranceLevelTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "documentAssuranceLevel", xmlWriter);


				if (localDocumentAssuranceLevel==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("documentAssuranceLevel cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDocumentAssuranceLevel);

				}

				xmlWriter.writeEndElement();
			} if (localCreatedOnTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "createdOn", xmlWriter);


				if (localCreatedOn==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("createdOn cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCreatedOn);

				}

				xmlWriter.writeEndElement();
			} if (localCreatorTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "creator", xmlWriter);


				if (localCreator==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("creator cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCreator);

				}

				xmlWriter.writeEndElement();
			} if (localExternalBlockChainIdTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "externalBlockChainId", xmlWriter);


				if (localExternalBlockChainId==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("externalBlockChainId cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localExternalBlockChainId);

				}

				xmlWriter.writeEndElement();
			} if (localStatusTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "status", xmlWriter);


				if (localStatus==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("status cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localStatus);

				}

				xmlWriter.writeEndElement();
			} if (localNameArTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "nameAr", xmlWriter);


				if (localNameAr==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("nameAr cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNameAr);

				}

				xmlWriter.writeEndElement();
			} if (localNameEnTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "nameEn", xmlWriter);


				if (localNameEn==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("nameEn cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNameEn);

				}

				xmlWriter.writeEndElement();
			} if (localCardNumberTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "cardNumber", xmlWriter);


				if (localCardNumber==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("cardNumber cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCardNumber);

				}

				xmlWriter.writeEndElement();
			} if (localDateOfBirthTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "dateOfBirth", xmlWriter);


				if (localDateOfBirth==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("dateOfBirth cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDateOfBirth);

				}

				xmlWriter.writeEndElement();
			} if (localDocumentTypeCodeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "documentTypeCode", xmlWriter);


				if (localDocumentTypeCode==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("documentTypeCode cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDocumentTypeCode);

				}

				xmlWriter.writeEndElement();
			} if (localIssueDateTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "issueDate", xmlWriter);


				if (localIssueDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("issueDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localIssueDate);

				}

				xmlWriter.writeEndElement();
			} if (localExpiryDateTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "expiryDate", xmlWriter);


				if (localExpiryDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("expiryDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localExpiryDate);

				}

				xmlWriter.writeEndElement();
			} if (localGenderArTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "genderAr", xmlWriter);


				if (localGenderAr==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("genderAr cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localGenderAr);

				}

				xmlWriter.writeEndElement();
			} if (localGenderCodeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "genderCode", xmlWriter);


				if (localGenderCode==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("genderCode cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localGenderCode);

				}

				xmlWriter.writeEndElement();
			} if (localGenderEnTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "genderEn", xmlWriter);


				if (localGenderEn==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("genderEn cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localGenderEn);

				}

				xmlWriter.writeEndElement();
			} if (localIdNumberTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "idNumber", xmlWriter);


				if (localIdNumber==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("idNumber cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localIdNumber);

				}

				xmlWriter.writeEndElement();
			} if (localNationalityArTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "nationalityAr", xmlWriter);


				if (localNationalityAr==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("nationalityAr cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNationalityAr);

				}

				xmlWriter.writeEndElement();
			} if (localNationalityCodeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "nationalityCode", xmlWriter);


				if (localNationalityCode==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("nationalityCode cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNationalityCode);

				}

				xmlWriter.writeEndElement();
			} if (localNationalityEnTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "nationalityEn", xmlWriter);


				if (localNationalityEn==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("nationalityEn cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNationalityEn);

				}

				xmlWriter.writeEndElement();
			} if (localPhotoBase64Tracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "photoBase64", xmlWriter);


				if (localPhotoBase64==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("photoBase64 cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPhotoBase64);

				}

				xmlWriter.writeEndElement();
			} if (localSignatureBase64Tracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "signatureBase64", xmlWriter);


				if (localSignatureBase64==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("signatureBase64 cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localSignatureBase64);

				}

				xmlWriter.writeEndElement();
			} if (localPlaceOfBirthEnTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "placeOfBirthEn", xmlWriter);


				if (localPlaceOfBirthEn==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("placeOfBirthEn cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPlaceOfBirthEn);

				}

				xmlWriter.writeEndElement();
			} if (localPlaceOfBirthArTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "placeOfBirthAr", xmlWriter);


				if (localPlaceOfBirthAr==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("placeOfBirthAr cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPlaceOfBirthAr);

				}

				xmlWriter.writeEndElement();
			} if (localIssuingAuthEnTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "IssuingAuthEn", xmlWriter);


				if (localIssuingAuthEn==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("IssuingAuthEn cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localIssuingAuthEn);

				}

				xmlWriter.writeEndElement();
			} if (localIssuingAuthArTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "IssuingAuthAr", xmlWriter);


				if (localIssuingAuthAr==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("IssuingAuthAr cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localIssuingAuthAr);

				}

				xmlWriter.writeEndElement();
			} if (localPassportTypeEnTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "passportTypeEn", xmlWriter);


				if (localPassportTypeEn==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("passportTypeEn cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPassportTypeEn);

				}

				xmlWriter.writeEndElement();
			} if (localCountryCodeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "countryCode", xmlWriter);


				if (localCountryCode==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("countryCode cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCountryCode);

				}

				xmlWriter.writeEndElement();
			} if (localFileNumberTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "fileNumber", xmlWriter);


				if (localFileNumber==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("fileNumber cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localFileNumber);

				}

				xmlWriter.writeEndElement();
			} if (localProfessionCodeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "professionCode", xmlWriter);


				if (localProfessionCode==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("professionCode cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localProfessionCode);

				}

				xmlWriter.writeEndElement();
			} if (localProfessionArTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "professionAr", xmlWriter);


				if (localProfessionAr==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("professionAr cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localProfessionAr);

				}

				xmlWriter.writeEndElement();
			} if (localProfessionEnTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "professionEn", xmlWriter);


				if (localProfessionEn==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("professionEn cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localProfessionEn);

				}

				xmlWriter.writeEndElement();
			} if (localSponsorNoTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "sponsorNo", xmlWriter);


				if (localSponsorNo==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("sponsorNo cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localSponsorNo);

				}

				xmlWriter.writeEndElement();
			} if (localSponsorArTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "sponsorAr", xmlWriter);


				if (localSponsorAr==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("sponsorAr cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localSponsorAr);

				}

				xmlWriter.writeEndElement();
			} if (localSponsorEnTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "sponsorEn", xmlWriter);


				if (localSponsorEn==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("sponsorEn cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localSponsorEn);

				}

				xmlWriter.writeEndElement();
			} if (localAccompaniedByTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "accompaniedBy", xmlWriter);


				if (localAccompaniedBy==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("accompaniedBy cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localAccompaniedBy);

				}

				xmlWriter.writeEndElement();
			} if (localNotifyToCustomerTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "notifyToCustomer", xmlWriter);


				if (localNotifyToCustomer==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("notifyToCustomer cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNotifyToCustomer);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd")){
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

			if (localDocumentTypeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"documentType"));

				if (localDocumentType != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDocumentType));
				} else {
					throw new org.apache.axis2.databinding.ADBException("documentType cannot be null!!");
				}
			} if (localExternalDocRefNoTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"externalDocRefNo"));

				if (localExternalDocRefNo != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExternalDocRefNo));
				} else {
					throw new org.apache.axis2.databinding.ADBException("externalDocRefNo cannot be null!!");
				}
			} if (localTransactionRefNoTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"transactionRefNo"));

				if (localTransactionRefNo != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTransactionRefNo));
				} else {
					throw new org.apache.axis2.databinding.ADBException("transactionRefNo cannot be null!!");
				}
			} if (localEdmsDocRefNoTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"edmsDocRefNo"));

				if (localEdmsDocRefNo != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEdmsDocRefNo));
				} else {
					throw new org.apache.axis2.databinding.ADBException("edmsDocRefNo cannot be null!!");
				}
			} if (localDocumentExpiryDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"documentExpiryDate"));

				if (localDocumentExpiryDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDocumentExpiryDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("documentExpiryDate cannot be null!!");
				}
			} if (localDocumentAssuranceLevelTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"documentAssuranceLevel"));

				if (localDocumentAssuranceLevel != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDocumentAssuranceLevel));
				} else {
					throw new org.apache.axis2.databinding.ADBException("documentAssuranceLevel cannot be null!!");
				}
			} if (localCreatedOnTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"createdOn"));

				if (localCreatedOn != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCreatedOn));
				} else {
					throw new org.apache.axis2.databinding.ADBException("createdOn cannot be null!!");
				}
			} if (localCreatorTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"creator"));

				if (localCreator != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCreator));
				} else {
					throw new org.apache.axis2.databinding.ADBException("creator cannot be null!!");
				}
			} if (localExternalBlockChainIdTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"externalBlockChainId"));

				if (localExternalBlockChainId != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExternalBlockChainId));
				} else {
					throw new org.apache.axis2.databinding.ADBException("externalBlockChainId cannot be null!!");
				}
			} if (localStatusTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"status"));

				if (localStatus != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStatus));
				} else {
					throw new org.apache.axis2.databinding.ADBException("status cannot be null!!");
				}
			} if (localNameArTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"nameAr"));

				if (localNameAr != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNameAr));
				} else {
					throw new org.apache.axis2.databinding.ADBException("nameAr cannot be null!!");
				}
			} if (localNameEnTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"nameEn"));

				if (localNameEn != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNameEn));
				} else {
					throw new org.apache.axis2.databinding.ADBException("nameEn cannot be null!!");
				}
			} if (localCardNumberTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"cardNumber"));

				if (localCardNumber != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCardNumber));
				} else {
					throw new org.apache.axis2.databinding.ADBException("cardNumber cannot be null!!");
				}
			} if (localDateOfBirthTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"dateOfBirth"));

				if (localDateOfBirth != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDateOfBirth));
				} else {
					throw new org.apache.axis2.databinding.ADBException("dateOfBirth cannot be null!!");
				}
			} if (localDocumentTypeCodeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"documentTypeCode"));

				if (localDocumentTypeCode != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDocumentTypeCode));
				} else {
					throw new org.apache.axis2.databinding.ADBException("documentTypeCode cannot be null!!");
				}
			} if (localIssueDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"issueDate"));

				if (localIssueDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIssueDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("issueDate cannot be null!!");
				}
			} if (localExpiryDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"expiryDate"));

				if (localExpiryDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExpiryDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("expiryDate cannot be null!!");
				}
			} if (localGenderArTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"genderAr"));

				if (localGenderAr != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGenderAr));
				} else {
					throw new org.apache.axis2.databinding.ADBException("genderAr cannot be null!!");
				}
			} if (localGenderCodeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"genderCode"));

				if (localGenderCode != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGenderCode));
				} else {
					throw new org.apache.axis2.databinding.ADBException("genderCode cannot be null!!");
				}
			} if (localGenderEnTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"genderEn"));

				if (localGenderEn != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGenderEn));
				} else {
					throw new org.apache.axis2.databinding.ADBException("genderEn cannot be null!!");
				}
			} if (localIdNumberTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"idNumber"));

				if (localIdNumber != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIdNumber));
				} else {
					throw new org.apache.axis2.databinding.ADBException("idNumber cannot be null!!");
				}
			} if (localNationalityArTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"nationalityAr"));

				if (localNationalityAr != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNationalityAr));
				} else {
					throw new org.apache.axis2.databinding.ADBException("nationalityAr cannot be null!!");
				}
			} if (localNationalityCodeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"nationalityCode"));

				if (localNationalityCode != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNationalityCode));
				} else {
					throw new org.apache.axis2.databinding.ADBException("nationalityCode cannot be null!!");
				}
			} if (localNationalityEnTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"nationalityEn"));

				if (localNationalityEn != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNationalityEn));
				} else {
					throw new org.apache.axis2.databinding.ADBException("nationalityEn cannot be null!!");
				}
			} if (localPhotoBase64Tracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"photoBase64"));

				if (localPhotoBase64 != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPhotoBase64));
				} else {
					throw new org.apache.axis2.databinding.ADBException("photoBase64 cannot be null!!");
				}
			} if (localSignatureBase64Tracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"signatureBase64"));

				if (localSignatureBase64 != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSignatureBase64));
				} else {
					throw new org.apache.axis2.databinding.ADBException("signatureBase64 cannot be null!!");
				}
			} if (localPlaceOfBirthEnTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"placeOfBirthEn"));

				if (localPlaceOfBirthEn != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPlaceOfBirthEn));
				} else {
					throw new org.apache.axis2.databinding.ADBException("placeOfBirthEn cannot be null!!");
				}
			} if (localPlaceOfBirthArTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"placeOfBirthAr"));

				if (localPlaceOfBirthAr != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPlaceOfBirthAr));
				} else {
					throw new org.apache.axis2.databinding.ADBException("placeOfBirthAr cannot be null!!");
				}
			} if (localIssuingAuthEnTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"IssuingAuthEn"));

				if (localIssuingAuthEn != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIssuingAuthEn));
				} else {
					throw new org.apache.axis2.databinding.ADBException("IssuingAuthEn cannot be null!!");
				}
			} if (localIssuingAuthArTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"IssuingAuthAr"));

				if (localIssuingAuthAr != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIssuingAuthAr));
				} else {
					throw new org.apache.axis2.databinding.ADBException("IssuingAuthAr cannot be null!!");
				}
			} if (localPassportTypeEnTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"passportTypeEn"));

				if (localPassportTypeEn != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPassportTypeEn));
				} else {
					throw new org.apache.axis2.databinding.ADBException("passportTypeEn cannot be null!!");
				}
			} if (localCountryCodeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"countryCode"));

				if (localCountryCode != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCountryCode));
				} else {
					throw new org.apache.axis2.databinding.ADBException("countryCode cannot be null!!");
				}
			} if (localFileNumberTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"fileNumber"));

				if (localFileNumber != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFileNumber));
				} else {
					throw new org.apache.axis2.databinding.ADBException("fileNumber cannot be null!!");
				}
			} if (localProfessionCodeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"professionCode"));

				if (localProfessionCode != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProfessionCode));
				} else {
					throw new org.apache.axis2.databinding.ADBException("professionCode cannot be null!!");
				}
			} if (localProfessionArTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"professionAr"));

				if (localProfessionAr != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProfessionAr));
				} else {
					throw new org.apache.axis2.databinding.ADBException("professionAr cannot be null!!");
				}
			} if (localProfessionEnTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"professionEn"));

				if (localProfessionEn != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProfessionEn));
				} else {
					throw new org.apache.axis2.databinding.ADBException("professionEn cannot be null!!");
				}
			} if (localSponsorNoTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"sponsorNo"));

				if (localSponsorNo != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSponsorNo));
				} else {
					throw new org.apache.axis2.databinding.ADBException("sponsorNo cannot be null!!");
				}
			} if (localSponsorArTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"sponsorAr"));

				if (localSponsorAr != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSponsorAr));
				} else {
					throw new org.apache.axis2.databinding.ADBException("sponsorAr cannot be null!!");
				}
			} if (localSponsorEnTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"sponsorEn"));

				if (localSponsorEn != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSponsorEn));
				} else {
					throw new org.apache.axis2.databinding.ADBException("sponsorEn cannot be null!!");
				}
			} if (localAccompaniedByTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"accompaniedBy"));

				if (localAccompaniedBy != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAccompaniedBy));
				} else {
					throw new org.apache.axis2.databinding.ADBException("accompaniedBy cannot be null!!");
				}
			} if (localNotifyToCustomerTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"notifyToCustomer"));

				if (localNotifyToCustomer != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNotifyToCustomer));
				} else {
					throw new org.apache.axis2.databinding.ADBException("notifyToCustomer cannot be null!!");
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
			public static DocumentDetails_type1 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				DocumentDetails_type1 object =
						new DocumentDetails_type1();

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

							if (!"documentDetails_type1".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (DocumentDetails_type1)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","documentType").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"documentType" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDocumentType(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","externalDocRefNo").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"externalDocRefNo" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setExternalDocRefNo(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","transactionRefNo").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"transactionRefNo" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setTransactionRefNo(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","edmsDocRefNo").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"edmsDocRefNo" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setEdmsDocRefNo(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","documentExpiryDate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"documentExpiryDate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDocumentExpiryDate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","documentAssuranceLevel").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"documentAssuranceLevel" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDocumentAssuranceLevel(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","createdOn").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"createdOn" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCreatedOn(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","creator").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"creator" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCreator(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","externalBlockChainId").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"externalBlockChainId" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setExternalBlockChainId(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","status").equals(reader.getName())){

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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","nameAr").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"nameAr" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNameAr(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","nameEn").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"nameEn" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNameEn(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","cardNumber").equals(reader.getName())){

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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","dateOfBirth").equals(reader.getName())){

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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","documentTypeCode").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"documentTypeCode" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDocumentTypeCode(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","issueDate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"issueDate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setIssueDate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","expiryDate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"expiryDate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setExpiryDate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","genderAr").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"genderAr" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setGenderAr(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","genderCode").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"genderCode" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setGenderCode(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","genderEn").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"genderEn" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setGenderEn(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","idNumber").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"idNumber" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setIdNumber(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","nationalityAr").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"nationalityAr" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNationalityAr(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","nationalityCode").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"nationalityCode" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNationalityCode(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","nationalityEn").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"nationalityEn" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNationalityEn(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","photoBase64").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"photoBase64" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPhotoBase64(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","signatureBase64").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"signatureBase64" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setSignatureBase64(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","placeOfBirthEn").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"placeOfBirthEn" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPlaceOfBirthEn(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","placeOfBirthAr").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"placeOfBirthAr" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPlaceOfBirthAr(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","IssuingAuthEn").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"IssuingAuthEn" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setIssuingAuthEn(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","IssuingAuthAr").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"IssuingAuthAr" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setIssuingAuthAr(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","passportTypeEn").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"passportTypeEn" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPassportTypeEn(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","countryCode").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"countryCode" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCountryCode(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","fileNumber").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"fileNumber" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setFileNumber(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","professionCode").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"professionCode" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setProfessionCode(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","professionAr").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"professionAr" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setProfessionAr(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","professionEn").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"professionEn" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setProfessionEn(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","sponsorNo").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"sponsorNo" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setSponsorNo(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","sponsorAr").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"sponsorAr" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setSponsorAr(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","sponsorEn").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"sponsorEn" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setSponsorEn(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","accompaniedBy").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"accompaniedBy" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAccompaniedBy(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","notifyToCustomer").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"notifyToCustomer" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNotifyToCustomer(
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


	public static class ExtensionMapper{

		public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
				java.lang.String typeName,
				javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{


			if (
					"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd".equals(namespaceURI) &&
					"documentDetails_type0".equals(typeName)){

				return  DocumentDetails_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd".equals(namespaceURI) &&
					"documentDetails_type1".equals(typeName)){

				return  DocumentDetails_type1.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd".equals(namespaceURI) &&
					"requestUARPassDocumentsRes_type0".equals(typeName)){

				return  RequestUARPassDocumentsRes_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd".equals(namespaceURI) &&
					"requestedDocuments_type0".equals(typeName)){

				return  RequestedDocuments_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd".equals(namespaceURI) &&
					"fetchUAEPassDocumentDtlsRes_type0".equals(typeName)){

				return  FetchUAEPassDocumentDtlsRes_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/header.xsd".equals(namespaceURI) &&
					"headerType".equals(typeName)){

				return  HeaderType.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd".equals(namespaceURI) &&
					"fetchCustomerUAEPassInfoRes_type0".equals(typeName)){

				return  FetchCustomerUAEPassInfoRes_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd".equals(namespaceURI) &&
					"updateUAEPassDocumentDtlsReq_type0".equals(typeName)){

				return  UpdateUAEPassDocumentDtlsReq_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd".equals(namespaceURI) &&
					"updateUAEPassDocumentDtlsRes_type0".equals(typeName)){

				return  UpdateUAEPassDocumentDtlsRes_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd".equals(namespaceURI) &&
					"requestUAEPassDocumentsReq_type0".equals(typeName)){

				return  RequestUAEPassDocumentsReq_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd".equals(namespaceURI) &&
					"fetchCustomerUAEPassInfoReq_type0".equals(typeName)){

				return  FetchCustomerUAEPassInfoReq_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd".equals(namespaceURI) &&
					"fetchUAEPassDocumentDtlsReq_type0".equals(typeName)){

				return  FetchUAEPassDocumentDtlsReq_type0.Factory.parse(reader);


			}


			throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
		}

	}

	public static class RequestUAEPassDocumentsReq_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = requestUAEPassDocumentsReq_type0
                Namespace URI = http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for PatnerId
		 */


		protected java.lang.String localPatnerId ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPatnerIdTracker = false ;

		public boolean isPatnerIdSpecified(){
			return localPatnerIdTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPatnerId(){
			return localPatnerId;
		}



		/**
		 * Auto generated setter method
		 * @param param PatnerId
		 */
		public void setPatnerId(java.lang.String param){
			localPatnerIdTracker = param != null;

			this.localPatnerId=param;


		}


		/**
		 * field for PurposeEn
		 */


		protected java.lang.String localPurposeEn ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPurposeEnTracker = false ;

		public boolean isPurposeEnSpecified(){
			return localPurposeEnTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPurposeEn(){
			return localPurposeEn;
		}



		/**
		 * Auto generated setter method
		 * @param param PurposeEn
		 */
		public void setPurposeEn(java.lang.String param){
			localPurposeEnTracker = param != null;

			this.localPurposeEn=param;


		}


		/**
		 * field for PurposeAr
		 */


		protected java.lang.String localPurposeAr ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPurposeArTracker = false ;

		public boolean isPurposeArSpecified(){
			return localPurposeArTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPurposeAr(){
			return localPurposeAr;
		}



		/**
		 * Auto generated setter method
		 * @param param PurposeAr
		 */
		public void setPurposeAr(java.lang.String param){
			localPurposeArTracker = param != null;

			this.localPurposeAr=param;


		}


		/**
		 * field for RequestId
		 */


		protected java.lang.String localRequestId ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localRequestIdTracker = false ;

		public boolean isRequestIdSpecified(){
			return localRequestIdTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getRequestId(){
			return localRequestId;
		}



		/**
		 * Auto generated setter method
		 * @param param RequestId
		 */
		public void setRequestId(java.lang.String param){
			localRequestIdTracker = param != null;

			this.localRequestId=param;


		}


		/**
		 * field for TypeOfRequest
		 */


		protected java.lang.String localTypeOfRequest ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localTypeOfRequestTracker = false ;

		public boolean isTypeOfRequestSpecified(){
			return localTypeOfRequestTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getTypeOfRequest(){
			return localTypeOfRequest;
		}



		/**
		 * Auto generated setter method
		 * @param param TypeOfRequest
		 */
		public void setTypeOfRequest(java.lang.String param){
			localTypeOfRequestTracker = param != null;

			this.localTypeOfRequest=param;


		}


		/**
		 * field for QrCode
		 */


		protected java.lang.String localQrCode ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localQrCodeTracker = false ;

		public boolean isQrCodeSpecified(){
			return localQrCodeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getQrCode(){
			return localQrCode;
		}



		/**
		 * Auto generated setter method
		 * @param param QrCode
		 */
		public void setQrCode(java.lang.String param){
			localQrCodeTracker = param != null;

			this.localQrCode=param;


		}


		/**
		 * field for ExpiryDateQR
		 */


		protected java.lang.String localExpiryDateQR ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localExpiryDateQRTracker = false ;

		public boolean isExpiryDateQRSpecified(){
			return localExpiryDateQRTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getExpiryDateQR(){
			return localExpiryDateQR;
		}



		/**
		 * Auto generated setter method
		 * @param param ExpiryDateQR
		 */
		public void setExpiryDateQR(java.lang.String param){
			localExpiryDateQRTracker = param != null;

			this.localExpiryDateQR=param;


		}


		/**
		 * field for Mobile
		 */


		protected java.lang.String localMobile ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localMobileTracker = false ;

		public boolean isMobileSpecified(){
			return localMobileTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getMobile(){
			return localMobile;
		}



		/**
		 * Auto generated setter method
		 * @param param Mobile
		 */
		public void setMobile(java.lang.String param){
			localMobileTracker = param != null;

			this.localMobile=param;


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
		 * field for Origin
		 */


		protected java.lang.String localOrigin ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localOriginTracker = false ;

		public boolean isOriginSpecified(){
			return localOriginTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getOrigin(){
			return localOrigin;
		}



		/**
		 * Auto generated setter method
		 * @param param Origin
		 */
		public void setOrigin(java.lang.String param){
			localOriginTracker = param != null;

			this.localOrigin=param;


		}


		/**
		 * field for TransactionId
		 */


		protected java.lang.String localTransactionId ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localTransactionIdTracker = false ;

		public boolean isTransactionIdSpecified(){
			return localTransactionIdTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getTransactionId(){
			return localTransactionId;
		}



		/**
		 * Auto generated setter method
		 * @param param TransactionId
		 */
		public void setTransactionId(java.lang.String param){
			localTransactionIdTracker = param != null;

			this.localTransactionId=param;


		}


		/**
		 * field for UnqueIdentifier
		 */


		protected java.lang.String localUnqueIdentifier ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localUnqueIdentifierTracker = false ;

		public boolean isUnqueIdentifierSpecified(){
			return localUnqueIdentifierTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getUnqueIdentifier(){
			return localUnqueIdentifier;
		}



		/**
		 * Auto generated setter method
		 * @param param UnqueIdentifier
		 */
		public void setUnqueIdentifier(java.lang.String param){
			localUnqueIdentifierTracker = param != null;

			this.localUnqueIdentifier=param;


		}


		/**
		 * field for EmiratesId
		 */


		protected java.lang.String localEmiratesId ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localEmiratesIdTracker = false ;

		public boolean isEmiratesIdSpecified(){
			return localEmiratesIdTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getEmiratesId(){
			return localEmiratesId;
		}



		/**
		 * Auto generated setter method
		 * @param param EmiratesId
		 */
		public void setEmiratesId(java.lang.String param){
			localEmiratesIdTracker = param != null;

			this.localEmiratesId=param;


		}


		/**
		 * field for DocumentCount
		 */


		protected java.lang.String localDocumentCount ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDocumentCountTracker = false ;

		public boolean isDocumentCountSpecified(){
			return localDocumentCountTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDocumentCount(){
			return localDocumentCount;
		}



		/**
		 * Auto generated setter method
		 * @param param DocumentCount
		 */
		public void setDocumentCount(java.lang.String param){
			localDocumentCountTracker = param != null;

			this.localDocumentCount=param;


		}


		/**
		 * field for RequestedDocuments
		 * This was an Array!
		 */


		protected RequestedDocuments_type0[] localRequestedDocuments ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localRequestedDocumentsTracker = false ;

		public boolean isRequestedDocumentsSpecified(){
			return localRequestedDocumentsTracker;
		}



		/**
		 * Auto generated getter method
		 * @return RequestedDocuments_type0[]
		 */
		public  RequestedDocuments_type0[] getRequestedDocuments(){
			return localRequestedDocuments;
		}






		/**
		 * validate the array for RequestedDocuments
		 */
		protected void validateRequestedDocuments(RequestedDocuments_type0[] param){

		}


		/**
		 * Auto generated setter method
		 * @param param RequestedDocuments
		 */
		public void setRequestedDocuments(RequestedDocuments_type0[] param){

			validateRequestedDocuments(param);

			localRequestedDocumentsTracker = param != null;

			this.localRequestedDocuments=param;
		}



		/**
		 * Auto generated add method for the array for convenience
		 * @param param RequestedDocuments_type0
		 */
		public void addRequestedDocuments(RequestedDocuments_type0 param){
			if (localRequestedDocuments == null){
				localRequestedDocuments = new RequestedDocuments_type0[]{};
			}


			//update the setting tracker
			localRequestedDocumentsTracker = true;


			java.util.List list =
					org.apache.axis2.databinding.utils.ConverterUtil.toList(localRequestedDocuments);
			list.add(param);
			this.localRequestedDocuments =
					(RequestedDocuments_type0[])list.toArray(
							new RequestedDocuments_type0[list.size()]);

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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":requestUAEPassDocumentsReq_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"requestUAEPassDocumentsReq_type0",
							xmlWriter);
				}


			}
			if (localPatnerIdTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "patnerId", xmlWriter);


				if (localPatnerId==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("patnerId cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPatnerId);

				}

				xmlWriter.writeEndElement();
			} if (localPurposeEnTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "purposeEn", xmlWriter);


				if (localPurposeEn==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("purposeEn cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPurposeEn);

				}

				xmlWriter.writeEndElement();
			} if (localPurposeArTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "purposeAr", xmlWriter);


				if (localPurposeAr==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("purposeAr cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPurposeAr);

				}

				xmlWriter.writeEndElement();
			} if (localRequestIdTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "requestId", xmlWriter);


				if (localRequestId==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("requestId cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localRequestId);

				}

				xmlWriter.writeEndElement();
			} if (localTypeOfRequestTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "typeOfRequest", xmlWriter);


				if (localTypeOfRequest==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("typeOfRequest cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localTypeOfRequest);

				}

				xmlWriter.writeEndElement();
			} if (localQrCodeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "qrCode", xmlWriter);


				if (localQrCode==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("qrCode cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localQrCode);

				}

				xmlWriter.writeEndElement();
			} if (localExpiryDateQRTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "expiryDateQR", xmlWriter);


				if (localExpiryDateQR==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("expiryDateQR cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localExpiryDateQR);

				}

				xmlWriter.writeEndElement();
			} if (localMobileTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "mobile", xmlWriter);


				if (localMobile==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("mobile cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localMobile);

				}

				xmlWriter.writeEndElement();
			} if (localEmailTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "email", xmlWriter);


				if (localEmail==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("email cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localEmail);

				}

				xmlWriter.writeEndElement();
			} if (localExpiryDateTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "expiryDate", xmlWriter);


				if (localExpiryDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("expiryDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localExpiryDate);

				}

				xmlWriter.writeEndElement();
			} if (localOriginTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "origin", xmlWriter);


				if (localOrigin==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("origin cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localOrigin);

				}

				xmlWriter.writeEndElement();
			} if (localTransactionIdTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "transactionId", xmlWriter);


				if (localTransactionId==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("transactionId cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localTransactionId);

				}

				xmlWriter.writeEndElement();
			} if (localUnqueIdentifierTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "unqueIdentifier", xmlWriter);


				if (localUnqueIdentifier==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("unqueIdentifier cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localUnqueIdentifier);

				}

				xmlWriter.writeEndElement();
			} if (localEmiratesIdTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "emiratesId", xmlWriter);


				if (localEmiratesId==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("emiratesId cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localEmiratesId);

				}

				xmlWriter.writeEndElement();
			} if (localDocumentCountTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "documentCount", xmlWriter);


				if (localDocumentCount==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("documentCount cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDocumentCount);

				}

				xmlWriter.writeEndElement();
			} if (localRequestedDocumentsTracker){
				if (localRequestedDocuments!=null){
					for (int i = 0;i < localRequestedDocuments.length;i++){
						if (localRequestedDocuments[i] != null){
							localRequestedDocuments[i].serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","requestedDocuments"),
									xmlWriter);
						} else {

							// we don't have to do any thing since minOccures is zero

						}

					}
				} else {

					throw new org.apache.axis2.databinding.ADBException("requestedDocuments cannot be null!!");

				}
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd")){
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

			if (localPatnerIdTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"patnerId"));

				if (localPatnerId != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPatnerId));
				} else {
					throw new org.apache.axis2.databinding.ADBException("patnerId cannot be null!!");
				}
			} if (localPurposeEnTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"purposeEn"));

				if (localPurposeEn != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPurposeEn));
				} else {
					throw new org.apache.axis2.databinding.ADBException("purposeEn cannot be null!!");
				}
			} if (localPurposeArTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"purposeAr"));

				if (localPurposeAr != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPurposeAr));
				} else {
					throw new org.apache.axis2.databinding.ADBException("purposeAr cannot be null!!");
				}
			} if (localRequestIdTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"requestId"));

				if (localRequestId != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRequestId));
				} else {
					throw new org.apache.axis2.databinding.ADBException("requestId cannot be null!!");
				}
			} if (localTypeOfRequestTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"typeOfRequest"));

				if (localTypeOfRequest != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTypeOfRequest));
				} else {
					throw new org.apache.axis2.databinding.ADBException("typeOfRequest cannot be null!!");
				}
			} if (localQrCodeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"qrCode"));

				if (localQrCode != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localQrCode));
				} else {
					throw new org.apache.axis2.databinding.ADBException("qrCode cannot be null!!");
				}
			} if (localExpiryDateQRTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"expiryDateQR"));

				if (localExpiryDateQR != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExpiryDateQR));
				} else {
					throw new org.apache.axis2.databinding.ADBException("expiryDateQR cannot be null!!");
				}
			} if (localMobileTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"mobile"));

				if (localMobile != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMobile));
				} else {
					throw new org.apache.axis2.databinding.ADBException("mobile cannot be null!!");
				}
			} if (localEmailTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"email"));

				if (localEmail != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEmail));
				} else {
					throw new org.apache.axis2.databinding.ADBException("email cannot be null!!");
				}
			} if (localExpiryDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"expiryDate"));

				if (localExpiryDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExpiryDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("expiryDate cannot be null!!");
				}
			} if (localOriginTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"origin"));

				if (localOrigin != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOrigin));
				} else {
					throw new org.apache.axis2.databinding.ADBException("origin cannot be null!!");
				}
			} if (localTransactionIdTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"transactionId"));

				if (localTransactionId != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTransactionId));
				} else {
					throw new org.apache.axis2.databinding.ADBException("transactionId cannot be null!!");
				}
			} if (localUnqueIdentifierTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"unqueIdentifier"));

				if (localUnqueIdentifier != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUnqueIdentifier));
				} else {
					throw new org.apache.axis2.databinding.ADBException("unqueIdentifier cannot be null!!");
				}
			} if (localEmiratesIdTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"emiratesId"));

				if (localEmiratesId != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEmiratesId));
				} else {
					throw new org.apache.axis2.databinding.ADBException("emiratesId cannot be null!!");
				}
			} if (localDocumentCountTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"documentCount"));

				if (localDocumentCount != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDocumentCount));
				} else {
					throw new org.apache.axis2.databinding.ADBException("documentCount cannot be null!!");
				}
			} if (localRequestedDocumentsTracker){
				if (localRequestedDocuments!=null) {
					for (int i = 0;i < localRequestedDocuments.length;i++){

						if (localRequestedDocuments[i] != null){
							elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
									"requestedDocuments"));
							elementList.add(localRequestedDocuments[i]);
						} else {

							// nothing to do

						}

					}
				} else {

					throw new org.apache.axis2.databinding.ADBException("requestedDocuments cannot be null!!");

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
			public static RequestUAEPassDocumentsReq_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				RequestUAEPassDocumentsReq_type0 object =
						new RequestUAEPassDocumentsReq_type0();

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

							if (!"requestUAEPassDocumentsReq_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (RequestUAEPassDocumentsReq_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();

					java.util.ArrayList list16 = new java.util.ArrayList();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","patnerId").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"patnerId" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPatnerId(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","purposeEn").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"purposeEn" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPurposeEn(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","purposeAr").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"purposeAr" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPurposeAr(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","requestId").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"requestId" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setRequestId(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","typeOfRequest").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"typeOfRequest" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setTypeOfRequest(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","qrCode").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"qrCode" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setQrCode(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","expiryDateQR").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"expiryDateQR" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setExpiryDateQR(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","mobile").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"mobile" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setMobile(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","email").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"email" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setEmail(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","expiryDate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"expiryDate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setExpiryDate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","origin").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"origin" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setOrigin(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","transactionId").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"transactionId" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setTransactionId(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","unqueIdentifier").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"unqueIdentifier" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setUnqueIdentifier(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","emiratesId").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"emiratesId" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setEmiratesId(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","documentCount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"documentCount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDocumentCount(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","requestedDocuments").equals(reader.getName())){



						// Process the array and step past its final element's end.
						list16.add(RequestedDocuments_type0.Factory.parse(reader));

						//loop until we find a start element that is not part of this array
						boolean loopDone16 = false;
						while(!loopDone16){
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
								loopDone16 = true;
							} else {
								if (new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","requestedDocuments").equals(reader.getName())){
									list16.add(RequestedDocuments_type0.Factory.parse(reader));

								}else{
									loopDone16 = true;
								}
							}
						}
						// call the converter utility  to convert and set the array

						object.setRequestedDocuments((RequestedDocuments_type0[])
								org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
										RequestedDocuments_type0.class,
										list16));

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


	public static class FetchUAEPassDocumentDtlsReqMsg
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
				"fetchUAEPassDocumentDtlsReqMsg",
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
		 * field for FetchUAEPassDocumentDtlsReq
		 */


		protected FetchUAEPassDocumentDtlsReq_type0 localFetchUAEPassDocumentDtlsReq ;


		/**
		 * Auto generated getter method
		 * @return FetchUAEPassDocumentDtlsReq_type0
		 */
		public  FetchUAEPassDocumentDtlsReq_type0 getFetchUAEPassDocumentDtlsReq(){
			return localFetchUAEPassDocumentDtlsReq;
		}



		/**
		 * Auto generated setter method
		 * @param param FetchUAEPassDocumentDtlsReq
		 */
		public void setFetchUAEPassDocumentDtlsReq(FetchUAEPassDocumentDtlsReq_type0 param){

			this.localFetchUAEPassDocumentDtlsReq=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":fetchUAEPassDocumentDtlsReqMsg",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"fetchUAEPassDocumentDtlsReqMsg",
							xmlWriter);
				}


			}

			if (localHeader==null){
				throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
			}
			localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
					xmlWriter);

			if (localFetchUAEPassDocumentDtlsReq==null){
				throw new org.apache.axis2.databinding.ADBException("fetchUAEPassDocumentDtlsReq cannot be null!!");
			}
			localFetchUAEPassDocumentDtlsReq.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","fetchUAEPassDocumentDtlsReq"),
					xmlWriter);

			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd")){
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

			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
					"fetchUAEPassDocumentDtlsReq"));


			if (localFetchUAEPassDocumentDtlsReq==null){
				throw new org.apache.axis2.databinding.ADBException("fetchUAEPassDocumentDtlsReq cannot be null!!");
			}
			elementList.add(localFetchUAEPassDocumentDtlsReq);


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
			public static FetchUAEPassDocumentDtlsReqMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				FetchUAEPassDocumentDtlsReqMsg object =
						new FetchUAEPassDocumentDtlsReqMsg();

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

							if (!"fetchUAEPassDocumentDtlsReqMsg".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (FetchUAEPassDocumentDtlsReqMsg)ExtensionMapper.getTypeObject(
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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","fetchUAEPassDocumentDtlsReq").equals(reader.getName())){

						object.setFetchUAEPassDocumentDtlsReq(FetchUAEPassDocumentDtlsReq_type0.Factory.parse(reader));

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


	public static class FetchUAEPassDocumentDtlsReq_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = fetchUAEPassDocumentDtlsReq_type0
                Namespace URI = http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for AcknowledgementRefno
		 */


		protected java.lang.String localAcknowledgementRefno ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAcknowledgementRefnoTracker = false ;

		public boolean isAcknowledgementRefnoSpecified(){
			return localAcknowledgementRefnoTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getAcknowledgementRefno(){
			return localAcknowledgementRefno;
		}



		/**
		 * Auto generated setter method
		 * @param param AcknowledgementRefno
		 */
		public void setAcknowledgementRefno(java.lang.String param){
			localAcknowledgementRefnoTracker = param != null;

			this.localAcknowledgementRefno=param;


		}


		/**
		 * field for UniqueUserId
		 */


		protected java.lang.String localUniqueUserId ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localUniqueUserIdTracker = false ;

		public boolean isUniqueUserIdSpecified(){
			return localUniqueUserIdTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getUniqueUserId(){
			return localUniqueUserId;
		}



		/**
		 * Auto generated setter method
		 * @param param UniqueUserId
		 */
		public void setUniqueUserId(java.lang.String param){
			localUniqueUserIdTracker = param != null;

			this.localUniqueUserId=param;


		}


		/**
		 * field for EmiratesId
		 */


		protected java.lang.String localEmiratesId ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localEmiratesIdTracker = false ;

		public boolean isEmiratesIdSpecified(){
			return localEmiratesIdTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getEmiratesId(){
			return localEmiratesId;
		}



		/**
		 * Auto generated setter method
		 * @param param EmiratesId
		 */
		public void setEmiratesId(java.lang.String param){
			localEmiratesIdTracker = param != null;

			this.localEmiratesId=param;


		}


		/**
		 * field for ExternalRefNo
		 */


		protected java.lang.String localExternalRefNo ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localExternalRefNoTracker = false ;

		public boolean isExternalRefNoSpecified(){
			return localExternalRefNoTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getExternalRefNo(){
			return localExternalRefNo;
		}



		/**
		 * Auto generated setter method
		 * @param param ExternalRefNo
		 */
		public void setExternalRefNo(java.lang.String param){
			localExternalRefNoTracker = param != null;

			this.localExternalRefNo=param;


		}


		/**
		 * field for ExternalDocRefNo
		 */


		protected java.lang.String localExternalDocRefNo ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localExternalDocRefNoTracker = false ;

		public boolean isExternalDocRefNoSpecified(){
			return localExternalDocRefNoTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getExternalDocRefNo(){
			return localExternalDocRefNo;
		}



		/**
		 * Auto generated setter method
		 * @param param ExternalDocRefNo
		 */
		public void setExternalDocRefNo(java.lang.String param){
			localExternalDocRefNoTracker = param != null;

			this.localExternalDocRefNo=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":fetchUAEPassDocumentDtlsReq_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"fetchUAEPassDocumentDtlsReq_type0",
							xmlWriter);
				}


			}
			if (localAcknowledgementRefnoTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "acknowledgementRefno", xmlWriter);


				if (localAcknowledgementRefno==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("acknowledgementRefno cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localAcknowledgementRefno);

				}

				xmlWriter.writeEndElement();
			} if (localUniqueUserIdTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "uniqueUserId", xmlWriter);


				if (localUniqueUserId==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("uniqueUserId cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localUniqueUserId);

				}

				xmlWriter.writeEndElement();
			} if (localEmiratesIdTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "emiratesId", xmlWriter);


				if (localEmiratesId==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("emiratesId cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localEmiratesId);

				}

				xmlWriter.writeEndElement();
			} if (localExternalRefNoTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "externalRefNo", xmlWriter);


				if (localExternalRefNo==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("externalRefNo cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localExternalRefNo);

				}

				xmlWriter.writeEndElement();
			} if (localExternalDocRefNoTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "externalDocRefNo", xmlWriter);


				if (localExternalDocRefNo==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("externalDocRefNo cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localExternalDocRefNo);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd")){
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

			if (localAcknowledgementRefnoTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"acknowledgementRefno"));

				if (localAcknowledgementRefno != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAcknowledgementRefno));
				} else {
					throw new org.apache.axis2.databinding.ADBException("acknowledgementRefno cannot be null!!");
				}
			} if (localUniqueUserIdTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"uniqueUserId"));

				if (localUniqueUserId != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUniqueUserId));
				} else {
					throw new org.apache.axis2.databinding.ADBException("uniqueUserId cannot be null!!");
				}
			} if (localEmiratesIdTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"emiratesId"));

				if (localEmiratesId != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEmiratesId));
				} else {
					throw new org.apache.axis2.databinding.ADBException("emiratesId cannot be null!!");
				}
			} if (localExternalRefNoTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"externalRefNo"));

				if (localExternalRefNo != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExternalRefNo));
				} else {
					throw new org.apache.axis2.databinding.ADBException("externalRefNo cannot be null!!");
				}
			} if (localExternalDocRefNoTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"externalDocRefNo"));

				if (localExternalDocRefNo != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExternalDocRefNo));
				} else {
					throw new org.apache.axis2.databinding.ADBException("externalDocRefNo cannot be null!!");
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
			public static FetchUAEPassDocumentDtlsReq_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				FetchUAEPassDocumentDtlsReq_type0 object =
						new FetchUAEPassDocumentDtlsReq_type0();

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

							if (!"fetchUAEPassDocumentDtlsReq_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (FetchUAEPassDocumentDtlsReq_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","acknowledgementRefno").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"acknowledgementRefno" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAcknowledgementRefno(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","uniqueUserId").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"uniqueUserId" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setUniqueUserId(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","emiratesId").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"emiratesId" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setEmiratesId(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","externalRefNo").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"externalRefNo" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setExternalRefNo(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","externalDocRefNo").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"externalDocRefNo" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setExternalDocRefNo(
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


	public static class UpdateUAEPassDocumentDtlsReqMsg
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
				"updateUAEPassDocumentDtlsReqMsg",
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
		 * field for UpdateUAEPassDocumentDtlsReq
		 */


		protected UpdateUAEPassDocumentDtlsReq_type0 localUpdateUAEPassDocumentDtlsReq ;


		/**
		 * Auto generated getter method
		 * @return UpdateUAEPassDocumentDtlsReq_type0
		 */
		public  UpdateUAEPassDocumentDtlsReq_type0 getUpdateUAEPassDocumentDtlsReq(){
			return localUpdateUAEPassDocumentDtlsReq;
		}



		/**
		 * Auto generated setter method
		 * @param param UpdateUAEPassDocumentDtlsReq
		 */
		public void setUpdateUAEPassDocumentDtlsReq(UpdateUAEPassDocumentDtlsReq_type0 param){

			this.localUpdateUAEPassDocumentDtlsReq=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":updateUAEPassDocumentDtlsReqMsg",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"updateUAEPassDocumentDtlsReqMsg",
							xmlWriter);
				}


			}

			if (localHeader==null){
				throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
			}
			localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
					xmlWriter);

			if (localUpdateUAEPassDocumentDtlsReq==null){
				throw new org.apache.axis2.databinding.ADBException("updateUAEPassDocumentDtlsReq cannot be null!!");
			}
			localUpdateUAEPassDocumentDtlsReq.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","updateUAEPassDocumentDtlsReq"),
					xmlWriter);

			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd")){
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

			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
					"updateUAEPassDocumentDtlsReq"));


			if (localUpdateUAEPassDocumentDtlsReq==null){
				throw new org.apache.axis2.databinding.ADBException("updateUAEPassDocumentDtlsReq cannot be null!!");
			}
			elementList.add(localUpdateUAEPassDocumentDtlsReq);


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
			public static UpdateUAEPassDocumentDtlsReqMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				UpdateUAEPassDocumentDtlsReqMsg object =
						new UpdateUAEPassDocumentDtlsReqMsg();

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

							if (!"updateUAEPassDocumentDtlsReqMsg".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (UpdateUAEPassDocumentDtlsReqMsg)ExtensionMapper.getTypeObject(
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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","updateUAEPassDocumentDtlsReq").equals(reader.getName())){

						object.setUpdateUAEPassDocumentDtlsReq(UpdateUAEPassDocumentDtlsReq_type0.Factory.parse(reader));

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


	public static class RequestUARPassDocumentsRes_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = requestUARPassDocumentsRes_type0
                Namespace URI = http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for AcknowledgementRefno
		 */


		protected java.lang.String localAcknowledgementRefno ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAcknowledgementRefnoTracker = false ;

		public boolean isAcknowledgementRefnoSpecified(){
			return localAcknowledgementRefnoTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getAcknowledgementRefno(){
			return localAcknowledgementRefno;
		}



		/**
		 * Auto generated setter method
		 * @param param AcknowledgementRefno
		 */
		public void setAcknowledgementRefno(java.lang.String param){
			localAcknowledgementRefnoTracker = param != null;

			this.localAcknowledgementRefno=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":requestUARPassDocumentsRes_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"requestUARPassDocumentsRes_type0",
							xmlWriter);
				}


			}
			if (localAcknowledgementRefnoTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "acknowledgementRefno", xmlWriter);


				if (localAcknowledgementRefno==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("acknowledgementRefno cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localAcknowledgementRefno);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd")){
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

			if (localAcknowledgementRefnoTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"acknowledgementRefno"));

				if (localAcknowledgementRefno != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAcknowledgementRefno));
				} else {
					throw new org.apache.axis2.databinding.ADBException("acknowledgementRefno cannot be null!!");
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
			public static RequestUARPassDocumentsRes_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				RequestUARPassDocumentsRes_type0 object =
						new RequestUARPassDocumentsRes_type0();

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

							if (!"requestUARPassDocumentsRes_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (RequestUARPassDocumentsRes_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","acknowledgementRefno").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"acknowledgementRefno" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAcknowledgementRefno(
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


	public static class UpdateUAEPassDocumentDtlsResMsg
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
				"updateUAEPassDocumentDtlsResMsg",
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
		 * field for UpdateUAEPassDocumentDtlsRes
		 */


		protected UpdateUAEPassDocumentDtlsRes_type0 localUpdateUAEPassDocumentDtlsRes ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localUpdateUAEPassDocumentDtlsResTracker = false ;

		public boolean isUpdateUAEPassDocumentDtlsResSpecified(){
			return localUpdateUAEPassDocumentDtlsResTracker;
		}



		/**
		 * Auto generated getter method
		 * @return UpdateUAEPassDocumentDtlsRes_type0
		 */
		public  UpdateUAEPassDocumentDtlsRes_type0 getUpdateUAEPassDocumentDtlsRes(){
			return localUpdateUAEPassDocumentDtlsRes;
		}



		/**
		 * Auto generated setter method
		 * @param param UpdateUAEPassDocumentDtlsRes
		 */
		public void setUpdateUAEPassDocumentDtlsRes(UpdateUAEPassDocumentDtlsRes_type0 param){
			localUpdateUAEPassDocumentDtlsResTracker = param != null;

			this.localUpdateUAEPassDocumentDtlsRes=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":updateUAEPassDocumentDtlsResMsg",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"updateUAEPassDocumentDtlsResMsg",
							xmlWriter);
				}


			}

			if (localHeader==null){
				throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
			}
			localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
					xmlWriter);
			if (localUpdateUAEPassDocumentDtlsResTracker){
				if (localUpdateUAEPassDocumentDtlsRes==null){
					throw new org.apache.axis2.databinding.ADBException("updateUAEPassDocumentDtlsRes cannot be null!!");
				}
				localUpdateUAEPassDocumentDtlsRes.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","updateUAEPassDocumentDtlsRes"),
						xmlWriter);
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd")){
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
			if (localUpdateUAEPassDocumentDtlsResTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"updateUAEPassDocumentDtlsRes"));


				if (localUpdateUAEPassDocumentDtlsRes==null){
					throw new org.apache.axis2.databinding.ADBException("updateUAEPassDocumentDtlsRes cannot be null!!");
				}
				elementList.add(localUpdateUAEPassDocumentDtlsRes);
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
			public static UpdateUAEPassDocumentDtlsResMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				UpdateUAEPassDocumentDtlsResMsg object =
						new UpdateUAEPassDocumentDtlsResMsg();

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

							if (!"updateUAEPassDocumentDtlsResMsg".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (UpdateUAEPassDocumentDtlsResMsg)ExtensionMapper.getTypeObject(
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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","updateUAEPassDocumentDtlsRes").equals(reader.getName())){

						object.setUpdateUAEPassDocumentDtlsRes(UpdateUAEPassDocumentDtlsRes_type0.Factory.parse(reader));

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


	public static class FetchCustomerUAEPassInfoResMsg
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
				"fetchCustomerUAEPassInfoResMsg",
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
		 * field for FetchCustomerUAEPassInfoRes
		 */


		protected FetchCustomerUAEPassInfoRes_type0 localFetchCustomerUAEPassInfoRes ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localFetchCustomerUAEPassInfoResTracker = false ;

		public boolean isFetchCustomerUAEPassInfoResSpecified(){
			return localFetchCustomerUAEPassInfoResTracker;
		}



		/**
		 * Auto generated getter method
		 * @return FetchCustomerUAEPassInfoRes_type0
		 */
		public  FetchCustomerUAEPassInfoRes_type0 getFetchCustomerUAEPassInfoRes(){
			return localFetchCustomerUAEPassInfoRes;
		}



		/**
		 * Auto generated setter method
		 * @param param FetchCustomerUAEPassInfoRes
		 */
		public void setFetchCustomerUAEPassInfoRes(FetchCustomerUAEPassInfoRes_type0 param){
			localFetchCustomerUAEPassInfoResTracker = param != null;

			this.localFetchCustomerUAEPassInfoRes=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":fetchCustomerUAEPassInfoResMsg",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"fetchCustomerUAEPassInfoResMsg",
							xmlWriter);
				}


			}

			if (localHeader==null){
				throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
			}
			localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
					xmlWriter);
			if (localFetchCustomerUAEPassInfoResTracker){
				if (localFetchCustomerUAEPassInfoRes==null){
					throw new org.apache.axis2.databinding.ADBException("fetchCustomerUAEPassInfoRes cannot be null!!");
				}
				localFetchCustomerUAEPassInfoRes.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","fetchCustomerUAEPassInfoRes"),
						xmlWriter);
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd")){
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
			if (localFetchCustomerUAEPassInfoResTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"fetchCustomerUAEPassInfoRes"));


				if (localFetchCustomerUAEPassInfoRes==null){
					throw new org.apache.axis2.databinding.ADBException("fetchCustomerUAEPassInfoRes cannot be null!!");
				}
				elementList.add(localFetchCustomerUAEPassInfoRes);
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
			public static FetchCustomerUAEPassInfoResMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				FetchCustomerUAEPassInfoResMsg object =
						new FetchCustomerUAEPassInfoResMsg();

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

							if (!"fetchCustomerUAEPassInfoResMsg".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (FetchCustomerUAEPassInfoResMsg)ExtensionMapper.getTypeObject(
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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","fetchCustomerUAEPassInfoRes").equals(reader.getName())){

						object.setFetchCustomerUAEPassInfoRes(FetchCustomerUAEPassInfoRes_type0.Factory.parse(reader));

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


	public static class FetchCustomerUAEPassInfoReqMsg
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
				"fetchCustomerUAEPassInfoReqMsg",
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
		 * field for FetchCustomerUAEPassInfoReq
		 */


		protected FetchCustomerUAEPassInfoReq_type0 localFetchCustomerUAEPassInfoReq ;


		/**
		 * Auto generated getter method
		 * @return FetchCustomerUAEPassInfoReq_type0
		 */
		public  FetchCustomerUAEPassInfoReq_type0 getFetchCustomerUAEPassInfoReq(){
			return localFetchCustomerUAEPassInfoReq;
		}



		/**
		 * Auto generated setter method
		 * @param param FetchCustomerUAEPassInfoReq
		 */
		public void setFetchCustomerUAEPassInfoReq(FetchCustomerUAEPassInfoReq_type0 param){

			this.localFetchCustomerUAEPassInfoReq=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":fetchCustomerUAEPassInfoReqMsg",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"fetchCustomerUAEPassInfoReqMsg",
							xmlWriter);
				}


			}

			if (localHeader==null){
				throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
			}
			localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
					xmlWriter);

			if (localFetchCustomerUAEPassInfoReq==null){
				throw new org.apache.axis2.databinding.ADBException("fetchCustomerUAEPassInfoReq cannot be null!!");
			}
			localFetchCustomerUAEPassInfoReq.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","fetchCustomerUAEPassInfoReq"),
					xmlWriter);

			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd")){
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

			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
					"fetchCustomerUAEPassInfoReq"));


			if (localFetchCustomerUAEPassInfoReq==null){
				throw new org.apache.axis2.databinding.ADBException("fetchCustomerUAEPassInfoReq cannot be null!!");
			}
			elementList.add(localFetchCustomerUAEPassInfoReq);


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
			public static FetchCustomerUAEPassInfoReqMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				FetchCustomerUAEPassInfoReqMsg object =
						new FetchCustomerUAEPassInfoReqMsg();

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

							if (!"fetchCustomerUAEPassInfoReqMsg".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (FetchCustomerUAEPassInfoReqMsg)ExtensionMapper.getTypeObject(
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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","fetchCustomerUAEPassInfoReq").equals(reader.getName())){

						object.setFetchCustomerUAEPassInfoReq(FetchCustomerUAEPassInfoReq_type0.Factory.parse(reader));

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


	public static class UpdateUAEPassDocumentDtlsReq_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = updateUAEPassDocumentDtlsReq_type0
                Namespace URI = http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for AcknowledgementRefno
		 */


		protected java.lang.String localAcknowledgementRefno ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAcknowledgementRefnoTracker = false ;

		public boolean isAcknowledgementRefnoSpecified(){
			return localAcknowledgementRefnoTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getAcknowledgementRefno(){
			return localAcknowledgementRefno;
		}



		/**
		 * Auto generated setter method
		 * @param param AcknowledgementRefno
		 */
		public void setAcknowledgementRefno(java.lang.String param){
			localAcknowledgementRefnoTracker = param != null;

			this.localAcknowledgementRefno=param;


		}


		/**
		 * field for ExternalRefNo
		 */


		protected java.lang.String localExternalRefNo ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localExternalRefNoTracker = false ;

		public boolean isExternalRefNoSpecified(){
			return localExternalRefNoTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getExternalRefNo(){
			return localExternalRefNo;
		}



		/**
		 * Auto generated setter method
		 * @param param ExternalRefNo
		 */
		public void setExternalRefNo(java.lang.String param){
			localExternalRefNoTracker = param != null;

			this.localExternalRefNo=param;


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
		 * field for RejectReason
		 */


		protected java.lang.String localRejectReason ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localRejectReasonTracker = false ;

		public boolean isRejectReasonSpecified(){
			return localRejectReasonTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getRejectReason(){
			return localRejectReason;
		}



		/**
		 * Auto generated setter method
		 * @param param RejectReason
		 */
		public void setRejectReason(java.lang.String param){
			localRejectReasonTracker = param != null;

			this.localRejectReason=param;


		}


		/**
		 * field for Remarks
		 */


		protected java.lang.String localRemarks ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localRemarksTracker = false ;

		public boolean isRemarksSpecified(){
			return localRemarksTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getRemarks(){
			return localRemarks;
		}



		/**
		 * Auto generated setter method
		 * @param param Remarks
		 */
		public void setRemarks(java.lang.String param){
			localRemarksTracker = param != null;

			this.localRemarks=param;


		}


		/**
		 * field for IssuerIdentifier
		 */


		protected java.lang.String localIssuerIdentifier ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localIssuerIdentifierTracker = false ;

		public boolean isIssuerIdentifierSpecified(){
			return localIssuerIdentifierTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getIssuerIdentifier(){
			return localIssuerIdentifier;
		}



		/**
		 * Auto generated setter method
		 * @param param IssuerIdentifier
		 */
		public void setIssuerIdentifier(java.lang.String param){
			localIssuerIdentifierTracker = param != null;

			this.localIssuerIdentifier=param;


		}


		/**
		 * field for VerifierIdentifier
		 */


		protected java.lang.String localVerifierIdentifier ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localVerifierIdentifierTracker = false ;

		public boolean isVerifierIdentifierSpecified(){
			return localVerifierIdentifierTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getVerifierIdentifier(){
			return localVerifierIdentifier;
		}



		/**
		 * Auto generated setter method
		 * @param param VerifierIdentifier
		 */
		public void setVerifierIdentifier(java.lang.String param){
			localVerifierIdentifierTracker = param != null;

			this.localVerifierIdentifier=param;


		}


		/**
		 * field for RequestedAt
		 */


		protected java.lang.String localRequestedAt ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localRequestedAtTracker = false ;

		public boolean isRequestedAtSpecified(){
			return localRequestedAtTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getRequestedAt(){
			return localRequestedAt;
		}



		/**
		 * Auto generated setter method
		 * @param param RequestedAt
		 */
		public void setRequestedAt(java.lang.String param){
			localRequestedAtTracker = param != null;

			this.localRequestedAt=param;


		}


		/**
		 * field for ReferenceNumber
		 */


		protected java.lang.String localReferenceNumber ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localReferenceNumberTracker = false ;

		public boolean isReferenceNumberSpecified(){
			return localReferenceNumberTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getReferenceNumber(){
			return localReferenceNumber;
		}



		/**
		 * Auto generated setter method
		 * @param param ReferenceNumber
		 */
		public void setReferenceNumber(java.lang.String param){
			localReferenceNumberTracker = param != null;

			this.localReferenceNumber=param;


		}


		/**
		 * field for DocumentDetails
		 * This was an Array!
		 */


		protected DocumentDetails_type0[] localDocumentDetails ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDocumentDetailsTracker = false ;

		public boolean isDocumentDetailsSpecified(){
			return localDocumentDetailsTracker;
		}



		/**
		 * Auto generated getter method
		 * @return DocumentDetails_type0[]
		 */
		public  DocumentDetails_type0[] getDocumentDetails(){
			return localDocumentDetails;
		}






		/**
		 * validate the array for DocumentDetails
		 */
		protected void validateDocumentDetails(DocumentDetails_type0[] param){

		}


		/**
		 * Auto generated setter method
		 * @param param DocumentDetails
		 */
		public void setDocumentDetails(DocumentDetails_type0[] param){

			validateDocumentDetails(param);

			localDocumentDetailsTracker = param != null;

			this.localDocumentDetails=param;
		}



		/**
		 * Auto generated add method for the array for convenience
		 * @param param DocumentDetails_type0
		 */
		public void addDocumentDetails(DocumentDetails_type0 param){
			if (localDocumentDetails == null){
				localDocumentDetails = new DocumentDetails_type0[]{};
			}


			//update the setting tracker
			localDocumentDetailsTracker = true;


			java.util.List list =
					org.apache.axis2.databinding.utils.ConverterUtil.toList(localDocumentDetails);
			list.add(param);
			this.localDocumentDetails =
					(DocumentDetails_type0[])list.toArray(
							new DocumentDetails_type0[list.size()]);

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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":updateUAEPassDocumentDtlsReq_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"updateUAEPassDocumentDtlsReq_type0",
							xmlWriter);
				}


			}
			if (localAcknowledgementRefnoTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "acknowledgementRefno", xmlWriter);


				if (localAcknowledgementRefno==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("acknowledgementRefno cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localAcknowledgementRefno);

				}

				xmlWriter.writeEndElement();
			} if (localExternalRefNoTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "externalRefNo", xmlWriter);


				if (localExternalRefNo==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("externalRefNo cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localExternalRefNo);

				}

				xmlWriter.writeEndElement();
			} if (localRequestTypeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "requestType", xmlWriter);


				if (localRequestType==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("requestType cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localRequestType);

				}

				xmlWriter.writeEndElement();
			} if (localRejectReasonTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "rejectReason", xmlWriter);


				if (localRejectReason==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("rejectReason cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localRejectReason);

				}

				xmlWriter.writeEndElement();
			} if (localRemarksTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "remarks", xmlWriter);


				if (localRemarks==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("remarks cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localRemarks);

				}

				xmlWriter.writeEndElement();
			} if (localIssuerIdentifierTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "issuerIdentifier", xmlWriter);


				if (localIssuerIdentifier==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("issuerIdentifier cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localIssuerIdentifier);

				}

				xmlWriter.writeEndElement();
			} if (localVerifierIdentifierTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "verifierIdentifier", xmlWriter);


				if (localVerifierIdentifier==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("verifierIdentifier cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localVerifierIdentifier);

				}

				xmlWriter.writeEndElement();
			} if (localRequestedAtTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "requestedAt", xmlWriter);


				if (localRequestedAt==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("requestedAt cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localRequestedAt);

				}

				xmlWriter.writeEndElement();
			} if (localReferenceNumberTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "referenceNumber", xmlWriter);


				if (localReferenceNumber==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("referenceNumber cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localReferenceNumber);

				}

				xmlWriter.writeEndElement();
			} if (localDocumentDetailsTracker){
				if (localDocumentDetails!=null){
					for (int i = 0;i < localDocumentDetails.length;i++){
						if (localDocumentDetails[i] != null){
							localDocumentDetails[i].serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","documentDetails"),
									xmlWriter);
						} else {

							// we don't have to do any thing since minOccures is zero

						}

					}
				} else {

					throw new org.apache.axis2.databinding.ADBException("documentDetails cannot be null!!");

				}
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd")){
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

			if (localAcknowledgementRefnoTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"acknowledgementRefno"));

				if (localAcknowledgementRefno != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAcknowledgementRefno));
				} else {
					throw new org.apache.axis2.databinding.ADBException("acknowledgementRefno cannot be null!!");
				}
			} if (localExternalRefNoTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"externalRefNo"));

				if (localExternalRefNo != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExternalRefNo));
				} else {
					throw new org.apache.axis2.databinding.ADBException("externalRefNo cannot be null!!");
				}
			} if (localRequestTypeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"requestType"));

				if (localRequestType != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRequestType));
				} else {
					throw new org.apache.axis2.databinding.ADBException("requestType cannot be null!!");
				}
			} if (localRejectReasonTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"rejectReason"));

				if (localRejectReason != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRejectReason));
				} else {
					throw new org.apache.axis2.databinding.ADBException("rejectReason cannot be null!!");
				}
			} if (localRemarksTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"remarks"));

				if (localRemarks != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRemarks));
				} else {
					throw new org.apache.axis2.databinding.ADBException("remarks cannot be null!!");
				}
			} if (localIssuerIdentifierTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"issuerIdentifier"));

				if (localIssuerIdentifier != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIssuerIdentifier));
				} else {
					throw new org.apache.axis2.databinding.ADBException("issuerIdentifier cannot be null!!");
				}
			} if (localVerifierIdentifierTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"verifierIdentifier"));

				if (localVerifierIdentifier != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localVerifierIdentifier));
				} else {
					throw new org.apache.axis2.databinding.ADBException("verifierIdentifier cannot be null!!");
				}
			} if (localRequestedAtTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"requestedAt"));

				if (localRequestedAt != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRequestedAt));
				} else {
					throw new org.apache.axis2.databinding.ADBException("requestedAt cannot be null!!");
				}
			} if (localReferenceNumberTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"referenceNumber"));

				if (localReferenceNumber != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReferenceNumber));
				} else {
					throw new org.apache.axis2.databinding.ADBException("referenceNumber cannot be null!!");
				}
			} if (localDocumentDetailsTracker){
				if (localDocumentDetails!=null) {
					for (int i = 0;i < localDocumentDetails.length;i++){

						if (localDocumentDetails[i] != null){
							elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
									"documentDetails"));
							elementList.add(localDocumentDetails[i]);
						} else {

							// nothing to do

						}

					}
				} else {

					throw new org.apache.axis2.databinding.ADBException("documentDetails cannot be null!!");

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
			public static UpdateUAEPassDocumentDtlsReq_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				UpdateUAEPassDocumentDtlsReq_type0 object =
						new UpdateUAEPassDocumentDtlsReq_type0();

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

							if (!"updateUAEPassDocumentDtlsReq_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (UpdateUAEPassDocumentDtlsReq_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();

					java.util.ArrayList list10 = new java.util.ArrayList();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","acknowledgementRefno").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"acknowledgementRefno" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAcknowledgementRefno(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","externalRefNo").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"externalRefNo" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setExternalRefNo(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","requestType").equals(reader.getName())){

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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","rejectReason").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"rejectReason" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setRejectReason(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","remarks").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"remarks" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setRemarks(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","issuerIdentifier").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"issuerIdentifier" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setIssuerIdentifier(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","verifierIdentifier").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"verifierIdentifier" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setVerifierIdentifier(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","requestedAt").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"requestedAt" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setRequestedAt(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","referenceNumber").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"referenceNumber" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setReferenceNumber(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","documentDetails").equals(reader.getName())){



						// Process the array and step past its final element's end.
						list10.add(DocumentDetails_type0.Factory.parse(reader));

						//loop until we find a start element that is not part of this array
						boolean loopDone10 = false;
						while(!loopDone10){
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
								loopDone10 = true;
							} else {
								if (new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","documentDetails").equals(reader.getName())){
									list10.add(DocumentDetails_type0.Factory.parse(reader));

								}else{
									loopDone10 = true;
								}
							}
						}
						// call the converter utility  to convert and set the array

						object.setDocumentDetails((DocumentDetails_type0[])
								org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
										DocumentDetails_type0.class,
										list10));

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


	public static class RequestUARPassDocumentsResMsg
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
				"requestUARPassDocumentsResMsg",
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
		 * field for RequestUARPassDocumentsRes
		 */


		protected RequestUARPassDocumentsRes_type0 localRequestUARPassDocumentsRes ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localRequestUARPassDocumentsResTracker = false ;

		public boolean isRequestUARPassDocumentsResSpecified(){
			return localRequestUARPassDocumentsResTracker;
		}



		/**
		 * Auto generated getter method
		 * @return RequestUARPassDocumentsRes_type0
		 */
		public  RequestUARPassDocumentsRes_type0 getRequestUARPassDocumentsRes(){
			return localRequestUARPassDocumentsRes;
		}



		/**
		 * Auto generated setter method
		 * @param param RequestUARPassDocumentsRes
		 */
		public void setRequestUARPassDocumentsRes(RequestUARPassDocumentsRes_type0 param){
			localRequestUARPassDocumentsResTracker = param != null;

			this.localRequestUARPassDocumentsRes=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":requestUARPassDocumentsResMsg",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"requestUARPassDocumentsResMsg",
							xmlWriter);
				}


			}

			if (localHeader==null){
				throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
			}
			localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
					xmlWriter);
			if (localRequestUARPassDocumentsResTracker){
				if (localRequestUARPassDocumentsRes==null){
					throw new org.apache.axis2.databinding.ADBException("requestUARPassDocumentsRes cannot be null!!");
				}
				localRequestUARPassDocumentsRes.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","requestUARPassDocumentsRes"),
						xmlWriter);
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd")){
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
			if (localRequestUARPassDocumentsResTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"requestUARPassDocumentsRes"));


				if (localRequestUARPassDocumentsRes==null){
					throw new org.apache.axis2.databinding.ADBException("requestUARPassDocumentsRes cannot be null!!");
				}
				elementList.add(localRequestUARPassDocumentsRes);
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
			public static RequestUARPassDocumentsResMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				RequestUARPassDocumentsResMsg object =
						new RequestUARPassDocumentsResMsg();

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

							if (!"requestUARPassDocumentsResMsg".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (RequestUARPassDocumentsResMsg)ExtensionMapper.getTypeObject(
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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","requestUARPassDocumentsRes").equals(reader.getName())){

						object.setRequestUARPassDocumentsRes(RequestUARPassDocumentsRes_type0.Factory.parse(reader));

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


	public static class RequestedDocuments_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = requestedDocuments_type0
                Namespace URI = http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for DocumentType
		 */


		protected java.lang.String localDocumentType ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDocumentTypeTracker = false ;

		public boolean isDocumentTypeSpecified(){
			return localDocumentTypeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDocumentType(){
			return localDocumentType;
		}



		/**
		 * Auto generated setter method
		 * @param param DocumentType
		 */
		public void setDocumentType(java.lang.String param){
			localDocumentTypeTracker = param != null;

			this.localDocumentType=param;


		}


		/**
		 * field for Required
		 */


		protected java.lang.String localRequired ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localRequiredTracker = false ;

		public boolean isRequiredSpecified(){
			return localRequiredTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getRequired(){
			return localRequired;
		}



		/**
		 * Auto generated setter method
		 * @param param Required
		 */
		public void setRequired(java.lang.String param){
			localRequiredTracker = param != null;

			this.localRequired=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":requestedDocuments_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"requestedDocuments_type0",
							xmlWriter);
				}


			}
			if (localDocumentTypeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "documentType", xmlWriter);


				if (localDocumentType==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("documentType cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDocumentType);

				}

				xmlWriter.writeEndElement();
			} if (localRequiredTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "required", xmlWriter);


				if (localRequired==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("required cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localRequired);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd")){
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

			if (localDocumentTypeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"documentType"));

				if (localDocumentType != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDocumentType));
				} else {
					throw new org.apache.axis2.databinding.ADBException("documentType cannot be null!!");
				}
			} if (localRequiredTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"required"));

				if (localRequired != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRequired));
				} else {
					throw new org.apache.axis2.databinding.ADBException("required cannot be null!!");
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
			public static RequestedDocuments_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				RequestedDocuments_type0 object =
						new RequestedDocuments_type0();

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

							if (!"requestedDocuments_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (RequestedDocuments_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","documentType").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"documentType" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDocumentType(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","required").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"required" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setRequired(
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


	public static class FetchCustomerUAEPassInfoReq_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = fetchCustomerUAEPassInfoReq_type0
                Namespace URI = http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for AccessCode
		 */


		protected java.lang.String localAccessCode ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAccessCodeTracker = false ;

		public boolean isAccessCodeSpecified(){
			return localAccessCodeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getAccessCode(){
			return localAccessCode;
		}



		/**
		 * Auto generated setter method
		 * @param param AccessCode
		 */
		public void setAccessCode(java.lang.String param){
			localAccessCodeTracker = param != null;

			this.localAccessCode=param;


		}


		/**
		 * field for HashCodeState
		 */


		protected java.lang.String localHashCodeState ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localHashCodeStateTracker = false ;

		public boolean isHashCodeStateSpecified(){
			return localHashCodeStateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getHashCodeState(){
			return localHashCodeState;
		}



		/**
		 * Auto generated setter method
		 * @param param HashCodeState
		 */
		public void setHashCodeState(java.lang.String param){
			localHashCodeStateTracker = param != null;

			this.localHashCodeState=param;


		}


		/**
		 * field for RedirectURI
		 */


		protected java.lang.String localRedirectURI ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localRedirectURITracker = false ;

		public boolean isRedirectURISpecified(){
			return localRedirectURITracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getRedirectURI(){
			return localRedirectURI;
		}



		/**
		 * Auto generated setter method
		 * @param param RedirectURI
		 */
		public void setRedirectURI(java.lang.String param){
			localRedirectURITracker = param != null;

			this.localRedirectURI=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":fetchCustomerUAEPassInfoReq_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"fetchCustomerUAEPassInfoReq_type0",
							xmlWriter);
				}


			}
			if (localAccessCodeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "accessCode", xmlWriter);


				if (localAccessCode==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("accessCode cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localAccessCode);

				}

				xmlWriter.writeEndElement();
			} if (localHashCodeStateTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "hashCodeState", xmlWriter);


				if (localHashCodeState==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("hashCodeState cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localHashCodeState);

				}

				xmlWriter.writeEndElement();
			} if (localRedirectURITracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "redirectURI", xmlWriter);


				if (localRedirectURI==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("redirectURI cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localRedirectURI);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd")){
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

			if (localAccessCodeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"accessCode"));

				if (localAccessCode != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAccessCode));
				} else {
					throw new org.apache.axis2.databinding.ADBException("accessCode cannot be null!!");
				}
			} if (localHashCodeStateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"hashCodeState"));

				if (localHashCodeState != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHashCodeState));
				} else {
					throw new org.apache.axis2.databinding.ADBException("hashCodeState cannot be null!!");
				}
			} if (localRedirectURITracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"redirectURI"));

				if (localRedirectURI != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRedirectURI));
				} else {
					throw new org.apache.axis2.databinding.ADBException("redirectURI cannot be null!!");
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
			public static FetchCustomerUAEPassInfoReq_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				FetchCustomerUAEPassInfoReq_type0 object =
						new FetchCustomerUAEPassInfoReq_type0();

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

							if (!"fetchCustomerUAEPassInfoReq_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (FetchCustomerUAEPassInfoReq_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","accessCode").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"accessCode" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAccessCode(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","hashCodeState").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"hashCodeState" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setHashCodeState(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","redirectURI").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"redirectURI" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setRedirectURI(
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


	public static class FetchUAEPassDocumentDtlsRes_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = fetchUAEPassDocumentDtlsRes_type0
                Namespace URI = http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for ExternalRefNo
		 */


		protected java.lang.String localExternalRefNo ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localExternalRefNoTracker = false ;

		public boolean isExternalRefNoSpecified(){
			return localExternalRefNoTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getExternalRefNo(){
			return localExternalRefNo;
		}



		/**
		 * Auto generated setter method
		 * @param param ExternalRefNo
		 */
		public void setExternalRefNo(java.lang.String param){
			localExternalRefNoTracker = param != null;

			this.localExternalRefNo=param;


		}


		/**
		 * field for PurposeEn
		 */


		protected java.lang.String localPurposeEn ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPurposeEnTracker = false ;

		public boolean isPurposeEnSpecified(){
			return localPurposeEnTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPurposeEn(){
			return localPurposeEn;
		}



		/**
		 * Auto generated setter method
		 * @param param PurposeEn
		 */
		public void setPurposeEn(java.lang.String param){
			localPurposeEnTracker = param != null;

			this.localPurposeEn=param;


		}


		/**
		 * field for PurposeAr
		 */


		protected java.lang.String localPurposeAr ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPurposeArTracker = false ;

		public boolean isPurposeArSpecified(){
			return localPurposeArTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPurposeAr(){
			return localPurposeAr;
		}



		/**
		 * Auto generated setter method
		 * @param param PurposeAr
		 */
		public void setPurposeAr(java.lang.String param){
			localPurposeArTracker = param != null;

			this.localPurposeAr=param;


		}


		/**
		 * field for RequestedAt
		 */


		protected java.lang.String localRequestedAt ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localRequestedAtTracker = false ;

		public boolean isRequestedAtSpecified(){
			return localRequestedAtTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getRequestedAt(){
			return localRequestedAt;
		}



		/**
		 * Auto generated setter method
		 * @param param RequestedAt
		 */
		public void setRequestedAt(java.lang.String param){
			localRequestedAtTracker = param != null;

			this.localRequestedAt=param;


		}


		/**
		 * field for Channel
		 */


		protected java.lang.String localChannel ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localChannelTracker = false ;

		public boolean isChannelSpecified(){
			return localChannelTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getChannel(){
			return localChannel;
		}



		/**
		 * Auto generated setter method
		 * @param param Channel
		 */
		public void setChannel(java.lang.String param){
			localChannelTracker = param != null;

			this.localChannel=param;


		}


		/**
		 * field for Orgin
		 */


		protected java.lang.String localOrgin ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localOrginTracker = false ;

		public boolean isOrginSpecified(){
			return localOrginTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getOrgin(){
			return localOrgin;
		}



		/**
		 * Auto generated setter method
		 * @param param Orgin
		 */
		public void setOrgin(java.lang.String param){
			localOrginTracker = param != null;

			this.localOrgin=param;


		}


		/**
		 * field for TypeOfRequest
		 */


		protected java.lang.String localTypeOfRequest ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localTypeOfRequestTracker = false ;

		public boolean isTypeOfRequestSpecified(){
			return localTypeOfRequestTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getTypeOfRequest(){
			return localTypeOfRequest;
		}



		/**
		 * Auto generated setter method
		 * @param param TypeOfRequest
		 */
		public void setTypeOfRequest(java.lang.String param){
			localTypeOfRequestTracker = param != null;

			this.localTypeOfRequest=param;


		}


		/**
		 * field for DocumentDtlsStatus
		 */


		protected java.lang.String localDocumentDtlsStatus ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDocumentDtlsStatusTracker = false ;

		public boolean isDocumentDtlsStatusSpecified(){
			return localDocumentDtlsStatusTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDocumentDtlsStatus(){
			return localDocumentDtlsStatus;
		}



		/**
		 * Auto generated setter method
		 * @param param DocumentDtlsStatus
		 */
		public void setDocumentDtlsStatus(java.lang.String param){
			localDocumentDtlsStatusTracker = param != null;

			this.localDocumentDtlsStatus=param;


		}


		/**
		 * field for DocumentReceiedStatus
		 */


		protected java.lang.String localDocumentReceiedStatus ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDocumentReceiedStatusTracker = false ;

		public boolean isDocumentReceiedStatusSpecified(){
			return localDocumentReceiedStatusTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDocumentReceiedStatus(){
			return localDocumentReceiedStatus;
		}



		/**
		 * Auto generated setter method
		 * @param param DocumentReceiedStatus
		 */
		public void setDocumentReceiedStatus(java.lang.String param){
			localDocumentReceiedStatusTracker = param != null;

			this.localDocumentReceiedStatus=param;


		}


		/**
		 * field for RejectStatus
		 */


		protected java.lang.String localRejectStatus ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localRejectStatusTracker = false ;

		public boolean isRejectStatusSpecified(){
			return localRejectStatusTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getRejectStatus(){
			return localRejectStatus;
		}



		/**
		 * Auto generated setter method
		 * @param param RejectStatus
		 */
		public void setRejectStatus(java.lang.String param){
			localRejectStatusTracker = param != null;

			this.localRejectStatus=param;


		}


		/**
		 * field for AuditDateTime
		 */


		protected java.lang.String localAuditDateTime ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAuditDateTimeTracker = false ;

		public boolean isAuditDateTimeSpecified(){
			return localAuditDateTimeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getAuditDateTime(){
			return localAuditDateTime;
		}



		/**
		 * Auto generated setter method
		 * @param param AuditDateTime
		 */
		public void setAuditDateTime(java.lang.String param){
			localAuditDateTimeTracker = param != null;

			this.localAuditDateTime=param;


		}


		/**
		 * field for RejectReason
		 */


		protected java.lang.String localRejectReason ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localRejectReasonTracker = false ;

		public boolean isRejectReasonSpecified(){
			return localRejectReasonTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getRejectReason(){
			return localRejectReason;
		}



		/**
		 * Auto generated setter method
		 * @param param RejectReason
		 */
		public void setRejectReason(java.lang.String param){
			localRejectReasonTracker = param != null;

			this.localRejectReason=param;


		}


		/**
		 * field for Remarks
		 */


		protected java.lang.String localRemarks ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localRemarksTracker = false ;

		public boolean isRemarksSpecified(){
			return localRemarksTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getRemarks(){
			return localRemarks;
		}



		/**
		 * Auto generated setter method
		 * @param param Remarks
		 */
		public void setRemarks(java.lang.String param){
			localRemarksTracker = param != null;

			this.localRemarks=param;


		}


		/**
		 * field for AcknowledgementRefno
		 */


		protected java.lang.String localAcknowledgementRefno ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAcknowledgementRefnoTracker = false ;

		public boolean isAcknowledgementRefnoSpecified(){
			return localAcknowledgementRefnoTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getAcknowledgementRefno(){
			return localAcknowledgementRefno;
		}



		/**
		 * Auto generated setter method
		 * @param param AcknowledgementRefno
		 */
		public void setAcknowledgementRefno(java.lang.String param){
			localAcknowledgementRefnoTracker = param != null;

			this.localAcknowledgementRefno=param;


		}


		/**
		 * field for MobileNumber
		 */


		protected java.lang.String localMobileNumber ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localMobileNumberTracker = false ;

		public boolean isMobileNumberSpecified(){
			return localMobileNumberTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getMobileNumber(){
			return localMobileNumber;
		}



		/**
		 * Auto generated setter method
		 * @param param MobileNumber
		 */
		public void setMobileNumber(java.lang.String param){
			localMobileNumberTracker = param != null;

			this.localMobileNumber=param;


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
		 * field for ReferenceNumber
		 */


		protected java.lang.String localReferenceNumber ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localReferenceNumberTracker = false ;

		public boolean isReferenceNumberSpecified(){
			return localReferenceNumberTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getReferenceNumber(){
			return localReferenceNumber;
		}



		/**
		 * Auto generated setter method
		 * @param param ReferenceNumber
		 */
		public void setReferenceNumber(java.lang.String param){
			localReferenceNumberTracker = param != null;

			this.localReferenceNumber=param;


		}


		/**
		 * field for IssuerIdentifier
		 */


		protected java.lang.String localIssuerIdentifier ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localIssuerIdentifierTracker = false ;

		public boolean isIssuerIdentifierSpecified(){
			return localIssuerIdentifierTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getIssuerIdentifier(){
			return localIssuerIdentifier;
		}



		/**
		 * Auto generated setter method
		 * @param param IssuerIdentifier
		 */
		public void setIssuerIdentifier(java.lang.String param){
			localIssuerIdentifierTracker = param != null;

			this.localIssuerIdentifier=param;


		}


		/**
		 * field for EmiratesId
		 */


		protected java.lang.String localEmiratesId ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localEmiratesIdTracker = false ;

		public boolean isEmiratesIdSpecified(){
			return localEmiratesIdTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getEmiratesId(){
			return localEmiratesId;
		}



		/**
		 * Auto generated setter method
		 * @param param EmiratesId
		 */
		public void setEmiratesId(java.lang.String param){
			localEmiratesIdTracker = param != null;

			this.localEmiratesId=param;


		}


		/**
		 * field for DocumentDetails
		 * This was an Array!
		 */


		protected DocumentDetails_type1[] localDocumentDetails ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDocumentDetailsTracker = false ;

		public boolean isDocumentDetailsSpecified(){
			return localDocumentDetailsTracker;
		}



		/**
		 * Auto generated getter method
		 * @return DocumentDetails_type1[]
		 */
		public  DocumentDetails_type1[] getDocumentDetails(){
			return localDocumentDetails;
		}






		/**
		 * validate the array for DocumentDetails
		 */
		protected void validateDocumentDetails(DocumentDetails_type1[] param){

		}


		/**
		 * Auto generated setter method
		 * @param param DocumentDetails
		 */
		public void setDocumentDetails(DocumentDetails_type1[] param){

			validateDocumentDetails(param);

			localDocumentDetailsTracker = param != null;

			this.localDocumentDetails=param;
		}



		/**
		 * Auto generated add method for the array for convenience
		 * @param param DocumentDetails_type1
		 */
		public void addDocumentDetails(DocumentDetails_type1 param){
			if (localDocumentDetails == null){
				localDocumentDetails = new DocumentDetails_type1[]{};
			}


			//update the setting tracker
			localDocumentDetailsTracker = true;


			java.util.List list =
					org.apache.axis2.databinding.utils.ConverterUtil.toList(localDocumentDetails);
			list.add(param);
			this.localDocumentDetails =
					(DocumentDetails_type1[])list.toArray(
							new DocumentDetails_type1[list.size()]);

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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":fetchUAEPassDocumentDtlsRes_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"fetchUAEPassDocumentDtlsRes_type0",
							xmlWriter);
				}


			}
			if (localExternalRefNoTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "externalRefNo", xmlWriter);


				if (localExternalRefNo==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("externalRefNo cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localExternalRefNo);

				}

				xmlWriter.writeEndElement();
			} if (localPurposeEnTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "purposeEn", xmlWriter);


				if (localPurposeEn==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("purposeEn cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPurposeEn);

				}

				xmlWriter.writeEndElement();
			} if (localPurposeArTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "purposeAr", xmlWriter);


				if (localPurposeAr==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("purposeAr cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPurposeAr);

				}

				xmlWriter.writeEndElement();
			} if (localRequestedAtTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "requestedAt", xmlWriter);


				if (localRequestedAt==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("requestedAt cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localRequestedAt);

				}

				xmlWriter.writeEndElement();
			} if (localChannelTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "channel", xmlWriter);


				if (localChannel==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("channel cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localChannel);

				}

				xmlWriter.writeEndElement();
			} if (localOrginTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "orgin", xmlWriter);


				if (localOrgin==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("orgin cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localOrgin);

				}

				xmlWriter.writeEndElement();
			} if (localTypeOfRequestTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "typeOfRequest", xmlWriter);


				if (localTypeOfRequest==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("typeOfRequest cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localTypeOfRequest);

				}

				xmlWriter.writeEndElement();
			} if (localDocumentDtlsStatusTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "documentDtlsStatus", xmlWriter);


				if (localDocumentDtlsStatus==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("documentDtlsStatus cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDocumentDtlsStatus);

				}

				xmlWriter.writeEndElement();
			} if (localDocumentReceiedStatusTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "documentReceiedStatus", xmlWriter);


				if (localDocumentReceiedStatus==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("documentReceiedStatus cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDocumentReceiedStatus);

				}

				xmlWriter.writeEndElement();
			} if (localRejectStatusTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "rejectStatus", xmlWriter);


				if (localRejectStatus==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("rejectStatus cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localRejectStatus);

				}

				xmlWriter.writeEndElement();
			} if (localAuditDateTimeTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "auditDateTime", xmlWriter);


				if (localAuditDateTime==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("auditDateTime cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localAuditDateTime);

				}

				xmlWriter.writeEndElement();
			} if (localRejectReasonTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "rejectReason", xmlWriter);


				if (localRejectReason==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("rejectReason cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localRejectReason);

				}

				xmlWriter.writeEndElement();
			} if (localRemarksTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "remarks", xmlWriter);


				if (localRemarks==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("remarks cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localRemarks);

				}

				xmlWriter.writeEndElement();
			} if (localAcknowledgementRefnoTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "acknowledgementRefno", xmlWriter);


				if (localAcknowledgementRefno==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("acknowledgementRefno cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localAcknowledgementRefno);

				}

				xmlWriter.writeEndElement();
			} if (localMobileNumberTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "mobileNumber", xmlWriter);


				if (localMobileNumber==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("mobileNumber cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localMobileNumber);

				}

				xmlWriter.writeEndElement();
			} if (localEmailTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "email", xmlWriter);


				if (localEmail==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("email cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localEmail);

				}

				xmlWriter.writeEndElement();
			} if (localReferenceNumberTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "referenceNumber", xmlWriter);


				if (localReferenceNumber==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("referenceNumber cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localReferenceNumber);

				}

				xmlWriter.writeEndElement();
			} if (localIssuerIdentifierTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "issuerIdentifier", xmlWriter);


				if (localIssuerIdentifier==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("issuerIdentifier cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localIssuerIdentifier);

				}

				xmlWriter.writeEndElement();
			} if (localEmiratesIdTracker){
				namespace = "http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd";
				writeStartElement(null, namespace, "emiratesId", xmlWriter);


				if (localEmiratesId==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("emiratesId cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localEmiratesId);

				}

				xmlWriter.writeEndElement();
			} if (localDocumentDetailsTracker){
				if (localDocumentDetails!=null){
					for (int i = 0;i < localDocumentDetails.length;i++){
						if (localDocumentDetails[i] != null){
							localDocumentDetails[i].serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","documentDetails"),
									xmlWriter);
						} else {

							// we don't have to do any thing since minOccures is zero

						}

					}
				} else {

					throw new org.apache.axis2.databinding.ADBException("documentDetails cannot be null!!");

				}
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd")){
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

			if (localExternalRefNoTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"externalRefNo"));

				if (localExternalRefNo != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExternalRefNo));
				} else {
					throw new org.apache.axis2.databinding.ADBException("externalRefNo cannot be null!!");
				}
			} if (localPurposeEnTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"purposeEn"));

				if (localPurposeEn != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPurposeEn));
				} else {
					throw new org.apache.axis2.databinding.ADBException("purposeEn cannot be null!!");
				}
			} if (localPurposeArTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"purposeAr"));

				if (localPurposeAr != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPurposeAr));
				} else {
					throw new org.apache.axis2.databinding.ADBException("purposeAr cannot be null!!");
				}
			} if (localRequestedAtTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"requestedAt"));

				if (localRequestedAt != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRequestedAt));
				} else {
					throw new org.apache.axis2.databinding.ADBException("requestedAt cannot be null!!");
				}
			} if (localChannelTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"channel"));

				if (localChannel != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localChannel));
				} else {
					throw new org.apache.axis2.databinding.ADBException("channel cannot be null!!");
				}
			} if (localOrginTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"orgin"));

				if (localOrgin != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOrgin));
				} else {
					throw new org.apache.axis2.databinding.ADBException("orgin cannot be null!!");
				}
			} if (localTypeOfRequestTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"typeOfRequest"));

				if (localTypeOfRequest != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTypeOfRequest));
				} else {
					throw new org.apache.axis2.databinding.ADBException("typeOfRequest cannot be null!!");
				}
			} if (localDocumentDtlsStatusTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"documentDtlsStatus"));

				if (localDocumentDtlsStatus != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDocumentDtlsStatus));
				} else {
					throw new org.apache.axis2.databinding.ADBException("documentDtlsStatus cannot be null!!");
				}
			} if (localDocumentReceiedStatusTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"documentReceiedStatus"));

				if (localDocumentReceiedStatus != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDocumentReceiedStatus));
				} else {
					throw new org.apache.axis2.databinding.ADBException("documentReceiedStatus cannot be null!!");
				}
			} if (localRejectStatusTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"rejectStatus"));

				if (localRejectStatus != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRejectStatus));
				} else {
					throw new org.apache.axis2.databinding.ADBException("rejectStatus cannot be null!!");
				}
			} if (localAuditDateTimeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"auditDateTime"));

				if (localAuditDateTime != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuditDateTime));
				} else {
					throw new org.apache.axis2.databinding.ADBException("auditDateTime cannot be null!!");
				}
			} if (localRejectReasonTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"rejectReason"));

				if (localRejectReason != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRejectReason));
				} else {
					throw new org.apache.axis2.databinding.ADBException("rejectReason cannot be null!!");
				}
			} if (localRemarksTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"remarks"));

				if (localRemarks != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRemarks));
				} else {
					throw new org.apache.axis2.databinding.ADBException("remarks cannot be null!!");
				}
			} if (localAcknowledgementRefnoTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"acknowledgementRefno"));

				if (localAcknowledgementRefno != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAcknowledgementRefno));
				} else {
					throw new org.apache.axis2.databinding.ADBException("acknowledgementRefno cannot be null!!");
				}
			} if (localMobileNumberTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"mobileNumber"));

				if (localMobileNumber != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMobileNumber));
				} else {
					throw new org.apache.axis2.databinding.ADBException("mobileNumber cannot be null!!");
				}
			} if (localEmailTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"email"));

				if (localEmail != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEmail));
				} else {
					throw new org.apache.axis2.databinding.ADBException("email cannot be null!!");
				}
			} if (localReferenceNumberTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"referenceNumber"));

				if (localReferenceNumber != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReferenceNumber));
				} else {
					throw new org.apache.axis2.databinding.ADBException("referenceNumber cannot be null!!");
				}
			} if (localIssuerIdentifierTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"issuerIdentifier"));

				if (localIssuerIdentifier != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIssuerIdentifier));
				} else {
					throw new org.apache.axis2.databinding.ADBException("issuerIdentifier cannot be null!!");
				}
			} if (localEmiratesIdTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"emiratesId"));

				if (localEmiratesId != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEmiratesId));
				} else {
					throw new org.apache.axis2.databinding.ADBException("emiratesId cannot be null!!");
				}
			} if (localDocumentDetailsTracker){
				if (localDocumentDetails!=null) {
					for (int i = 0;i < localDocumentDetails.length;i++){

						if (localDocumentDetails[i] != null){
							elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
									"documentDetails"));
							elementList.add(localDocumentDetails[i]);
						} else {

							// nothing to do

						}

					}
				} else {

					throw new org.apache.axis2.databinding.ADBException("documentDetails cannot be null!!");

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
			public static FetchUAEPassDocumentDtlsRes_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				FetchUAEPassDocumentDtlsRes_type0 object =
						new FetchUAEPassDocumentDtlsRes_type0();

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

							if (!"fetchUAEPassDocumentDtlsRes_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (FetchUAEPassDocumentDtlsRes_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();

					java.util.ArrayList list20 = new java.util.ArrayList();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","externalRefNo").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"externalRefNo" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setExternalRefNo(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","purposeEn").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"purposeEn" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPurposeEn(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","purposeAr").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"purposeAr" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPurposeAr(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","requestedAt").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"requestedAt" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setRequestedAt(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","channel").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"channel" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setChannel(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","orgin").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"orgin" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setOrgin(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","typeOfRequest").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"typeOfRequest" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setTypeOfRequest(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","documentDtlsStatus").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"documentDtlsStatus" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDocumentDtlsStatus(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","documentReceiedStatus").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"documentReceiedStatus" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDocumentReceiedStatus(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","rejectStatus").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"rejectStatus" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setRejectStatus(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","auditDateTime").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"auditDateTime" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAuditDateTime(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","rejectReason").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"rejectReason" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setRejectReason(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","remarks").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"remarks" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setRemarks(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","acknowledgementRefno").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"acknowledgementRefno" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAcknowledgementRefno(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","mobileNumber").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"mobileNumber" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setMobileNumber(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","email").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"email" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setEmail(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","referenceNumber").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"referenceNumber" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setReferenceNumber(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","issuerIdentifier").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"issuerIdentifier" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setIssuerIdentifier(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","emiratesId").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"emiratesId" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setEmiratesId(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","documentDetails").equals(reader.getName())){



						// Process the array and step past its final element's end.
						list20.add(DocumentDetails_type1.Factory.parse(reader));

						//loop until we find a start element that is not part of this array
						boolean loopDone20 = false;
						while(!loopDone20){
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
								loopDone20 = true;
							} else {
								if (new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","documentDetails").equals(reader.getName())){
									list20.add(DocumentDetails_type1.Factory.parse(reader));

								}else{
									loopDone20 = true;
								}
							}
						}
						// call the converter utility  to convert and set the array

						object.setDocumentDetails((DocumentDetails_type1[])
								org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
										DocumentDetails_type1.class,
										list20));

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


	public static class RequestUAEPassDocumentsReqMsg
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
				"requestUAEPassDocumentsReqMsg",
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
		 * field for RequestUAEPassDocumentsReq
		 */


		protected RequestUAEPassDocumentsReq_type0 localRequestUAEPassDocumentsReq ;


		/**
		 * Auto generated getter method
		 * @return RequestUAEPassDocumentsReq_type0
		 */
		public  RequestUAEPassDocumentsReq_type0 getRequestUAEPassDocumentsReq(){
			return localRequestUAEPassDocumentsReq;
		}



		/**
		 * Auto generated setter method
		 * @param param RequestUAEPassDocumentsReq
		 */
		public void setRequestUAEPassDocumentsReq(RequestUAEPassDocumentsReq_type0 param){

			this.localRequestUAEPassDocumentsReq=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":requestUAEPassDocumentsReqMsg",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"requestUAEPassDocumentsReqMsg",
							xmlWriter);
				}


			}

			if (localHeader==null){
				throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
			}
			localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
					xmlWriter);

			if (localRequestUAEPassDocumentsReq==null){
				throw new org.apache.axis2.databinding.ADBException("requestUAEPassDocumentsReq cannot be null!!");
			}
			localRequestUAEPassDocumentsReq.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","requestUAEPassDocumentsReq"),
					xmlWriter);

			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd")){
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

			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
					"requestUAEPassDocumentsReq"));


			if (localRequestUAEPassDocumentsReq==null){
				throw new org.apache.axis2.databinding.ADBException("requestUAEPassDocumentsReq cannot be null!!");
			}
			elementList.add(localRequestUAEPassDocumentsReq);


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
			public static RequestUAEPassDocumentsReqMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				RequestUAEPassDocumentsReqMsg object =
						new RequestUAEPassDocumentsReqMsg();

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

							if (!"requestUAEPassDocumentsReqMsg".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (RequestUAEPassDocumentsReqMsg)ExtensionMapper.getTypeObject(
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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","requestUAEPassDocumentsReq").equals(reader.getName())){

						object.setRequestUAEPassDocumentsReq(RequestUAEPassDocumentsReq_type0.Factory.parse(reader));

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


	public static class FetchUAEPassDocumentDtlsResMsg
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
				"fetchUAEPassDocumentDtlsResMsg",
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
		 * field for FetchUAEPassDocumentDtlsRes
		 */


		protected FetchUAEPassDocumentDtlsRes_type0 localFetchUAEPassDocumentDtlsRes ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localFetchUAEPassDocumentDtlsResTracker = false ;

		public boolean isFetchUAEPassDocumentDtlsResSpecified(){
			return localFetchUAEPassDocumentDtlsResTracker;
		}



		/**
		 * Auto generated getter method
		 * @return FetchUAEPassDocumentDtlsRes_type0
		 */
		public  FetchUAEPassDocumentDtlsRes_type0 getFetchUAEPassDocumentDtlsRes(){
			return localFetchUAEPassDocumentDtlsRes;
		}



		/**
		 * Auto generated setter method
		 * @param param FetchUAEPassDocumentDtlsRes
		 */
		public void setFetchUAEPassDocumentDtlsRes(FetchUAEPassDocumentDtlsRes_type0 param){
			localFetchUAEPassDocumentDtlsResTracker = param != null;

			this.localFetchUAEPassDocumentDtlsRes=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":fetchUAEPassDocumentDtlsResMsg",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"fetchUAEPassDocumentDtlsResMsg",
							xmlWriter);
				}


			}

			if (localHeader==null){
				throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
			}
			localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
					xmlWriter);
			if (localFetchUAEPassDocumentDtlsResTracker){
				if (localFetchUAEPassDocumentDtlsRes==null){
					throw new org.apache.axis2.databinding.ADBException("fetchUAEPassDocumentDtlsRes cannot be null!!");
				}
				localFetchUAEPassDocumentDtlsRes.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","fetchUAEPassDocumentDtlsRes"),
						xmlWriter);
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd")){
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
			if (localFetchUAEPassDocumentDtlsResTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd",
						"fetchUAEPassDocumentDtlsRes"));


				if (localFetchUAEPassDocumentDtlsRes==null){
					throw new org.apache.axis2.databinding.ADBException("fetchUAEPassDocumentDtlsRes cannot be null!!");
				}
				elementList.add(localFetchUAEPassDocumentDtlsRes);
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
			public static FetchUAEPassDocumentDtlsResMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				FetchUAEPassDocumentDtlsResMsg object =
						new FetchUAEPassDocumentDtlsResMsg();

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

							if (!"fetchUAEPassDocumentDtlsResMsg".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (FetchUAEPassDocumentDtlsResMsg)ExtensionMapper.getTypeObject(
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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/CustomerAuthenticationServicesInq/InqCustomerUAEPassInfo.xsd","fetchUAEPassDocumentDtlsRes").equals(reader.getName())){

						object.setFetchUAEPassDocumentDtlsRes(FetchUAEPassDocumentDtlsRes_type0.Factory.parse(reader));

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


	private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.UpdateUAEPassDocumentDtlsReqMsg param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {


		try{
			return param.getOMElement(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.UpdateUAEPassDocumentDtlsReqMsg.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}


	}

	private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.UpdateUAEPassDocumentDtlsResMsg param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {


		try{
			return param.getOMElement(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.UpdateUAEPassDocumentDtlsResMsg.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}


	}

	private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchCustomerUAEPassInfoReqMsg param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {


		try{
			return param.getOMElement(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchCustomerUAEPassInfoReqMsg.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}


	}

	private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchCustomerUAEPassInfoResMsg param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {


		try{
			return param.getOMElement(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchCustomerUAEPassInfoResMsg.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}


	}

	private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchUAEPassDocumentDtlsReqMsg param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {


		try{
			return param.getOMElement(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchUAEPassDocumentDtlsReqMsg.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}


	}

	private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchUAEPassDocumentDtlsResMsg param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {


		try{
			return param.getOMElement(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchUAEPassDocumentDtlsResMsg.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}


	}

	private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.RequestUAEPassDocumentsReqMsg param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {


		try{
			return param.getOMElement(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.RequestUAEPassDocumentsReqMsg.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}


	}

	private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.RequestUARPassDocumentsResMsg param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {


		try{
			return param.getOMElement(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.RequestUARPassDocumentsResMsg.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}


	}


	private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.UpdateUAEPassDocumentDtlsReqMsg param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault{


		try{

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.UpdateUAEPassDocumentDtlsReqMsg.MY_QNAME,factory));
			return emptyEnvelope;
		} catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}


	}


	/* methods to provide back word compatibility */



	private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchCustomerUAEPassInfoReqMsg param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault{


		try{

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchCustomerUAEPassInfoReqMsg.MY_QNAME,factory));
			return emptyEnvelope;
		} catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}


	}


	/* methods to provide back word compatibility */



	private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchUAEPassDocumentDtlsReqMsg param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault{


		try{

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchUAEPassDocumentDtlsReqMsg.MY_QNAME,factory));
			return emptyEnvelope;
		} catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}


	}


	/* methods to provide back word compatibility */



	private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.RequestUAEPassDocumentsReqMsg param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault{


		try{

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.RequestUAEPassDocumentsReqMsg.MY_QNAME,factory));
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

			if (com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.UpdateUAEPassDocumentDtlsReqMsg.class.equals(type)){

				return com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.UpdateUAEPassDocumentDtlsReqMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());


			}

			if (com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.UpdateUAEPassDocumentDtlsResMsg.class.equals(type)){

				return com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.UpdateUAEPassDocumentDtlsResMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());


			}

			if (com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchCustomerUAEPassInfoReqMsg.class.equals(type)){

				return com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchCustomerUAEPassInfoReqMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());


			}

			if (com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchCustomerUAEPassInfoResMsg.class.equals(type)){

				return com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchCustomerUAEPassInfoResMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());


			}

			if (com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchUAEPassDocumentDtlsReqMsg.class.equals(type)){

				return com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchUAEPassDocumentDtlsReqMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());


			}

			if (com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchUAEPassDocumentDtlsResMsg.class.equals(type)){

				return com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchUAEPassDocumentDtlsResMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());


			}

			if (com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.RequestUAEPassDocumentsReqMsg.class.equals(type)){

				return com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.RequestUAEPassDocumentsReqMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());


			}

			if (com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.RequestUARPassDocumentsResMsg.class.equals(type)){

				return com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.RequestUARPassDocumentsResMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());


			}

		} catch (java.lang.Exception e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
		return null;
	}




}
