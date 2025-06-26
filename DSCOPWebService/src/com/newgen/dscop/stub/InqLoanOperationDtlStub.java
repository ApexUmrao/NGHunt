
/**
 * InqLoanOperationDtlStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
package com.newgen.dscop.stub;



/*
 *  InqLoanOperationDtlStub java implementation
 */


public class InqLoanOperationDtlStub extends org.apache.axis2.client.Stub
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

	public  String inqLoanOperationDtlXML(

			com.newgen.dscop.stub.InqLoanOperationDtlStub.GetLoanOperativeInfoReqMsg getLoanOperativeInfoReqMsg0)


					throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try{
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
			_operationClient.getOptions().setAction("/Services/EnterpriseServicesInquiry/Loan/Service/InqLoanOperationDtl.serviceagent/InqLoanOperationDtlPortTypeEndpoint1/InqLoanOperationDtl_Oper");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



			addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");


			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();



			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;


			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
					getLoanOperativeInfoReqMsg0,
					optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1238430813937",
							"inqLoanOperationDtl_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1238430813937",
									"inqLoanOperationDtl_Oper"));

			//adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			return env.toString();
			// add the message contxt to the operation client

		}catch(org.apache.axis2.AxisFault f){
			return "";

		} finally {

		}
	}


	private void populateAxisService() throws org.apache.axis2.AxisFault {

		//creating the Service with a unique name
		_service = new org.apache.axis2.description.AxisService("InqLoanOperationDtl" + getUniqueSuffix());
		addAnonymousOperations();

		//creating the operations
		org.apache.axis2.description.AxisOperation __operation;

		_operations = new org.apache.axis2.description.AxisOperation[1];

		__operation = new org.apache.axis2.description.OutInAxisOperation();


		__operation.setName(new javax.xml.namespace.QName("http://xmlns.example.com/1238430813937", "inqLoanOperationDtl_Oper"));
		_service.addOperation(__operation);




		_operations[0]=__operation;


	}

	//populates the faults
	private void populateFaults(){



	}

	/**
	 *Constructor that takes in a configContext
	 */

	public InqLoanOperationDtlStub(org.apache.axis2.context.ConfigurationContext configurationContext,
			java.lang.String targetEndpoint)
					throws org.apache.axis2.AxisFault {
		this(configurationContext,targetEndpoint,false);
	}


	/**
	 * Constructor that takes in a configContext  and useseperate listner
	 */
	public InqLoanOperationDtlStub(org.apache.axis2.context.ConfigurationContext configurationContext,
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
	public InqLoanOperationDtlStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {

		this(configurationContext,"http://10.101.107.25:5503/Services/EnterpriseServicesInquiry/Loan/Service/InqLoanOperationDtl.serviceagent/InqLoanOperationDtlPortTypeEndpoint1" );

	}

	/**
	 * Default Constructor
	 */
	public InqLoanOperationDtlStub() throws org.apache.axis2.AxisFault {

		this("http://10.101.107.25:5503/Services/EnterpriseServicesInquiry/Loan/Service/InqLoanOperationDtl.serviceagent/InqLoanOperationDtlPortTypeEndpoint1" );

	}

	/**
	 * Constructor taking the target endpoint
	 */
	public InqLoanOperationDtlStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
		this(null,targetEndpoint);
	}




	/**
	 * Auto generated method signature
	 * 
	 * @see com.newgen.dscop.stub.InqLoanOperationDtl#inqLoanOperationDtl_Oper
	 * @param getLoanOperativeInfoReqMsg0

	 */



	public  com.newgen.dscop.stub.InqLoanOperationDtlStub.GetLoanOperativeInfoResMsg inqLoanOperationDtl_Oper(

			com.newgen.dscop.stub.InqLoanOperationDtlStub.GetLoanOperativeInfoReqMsg getLoanOperativeInfoReqMsg0)


					throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try{
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
			_operationClient.getOptions().setAction("/Services/EnterpriseServicesInquiry/Loan/Service/InqLoanOperationDtl.serviceagent/InqLoanOperationDtlPortTypeEndpoint1/InqLoanOperationDtl_Oper");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



			addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");


			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();



			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;


			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
					getLoanOperativeInfoReqMsg0,
					optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1238430813937",
							"inqLoanOperationDtl_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1238430813937",
									"inqLoanOperationDtl_Oper"));

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
			outputXML = _returnEnv.toString();

			java.lang.Object object = fromOM(
					_returnEnv.getBody().getFirstElement() ,
					com.newgen.dscop.stub.InqLoanOperationDtlStub.GetLoanOperativeInfoResMsg.class,
					getEnvelopeNamespaces(_returnEnv));


			return (com.newgen.dscop.stub.InqLoanOperationDtlStub.GetLoanOperativeInfoResMsg)object;

		}catch(org.apache.axis2.AxisFault f){

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt!=null){
				if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqLoanOperationDtl_Oper"))){
					//make the fault by reflection
					try{
						java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqLoanOperationDtl_Oper"));
						java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
						java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
						java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
						//message class
						java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqLoanOperationDtl_Oper"));
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
	 * @see com.newgen.dscop.stub.InqLoanOperationDtl#startinqLoanOperationDtl_Oper
	 * @param getLoanOperativeInfoReqMsg0

	 */
	public  void startinqLoanOperationDtl_Oper(

			com.newgen.dscop.stub.InqLoanOperationDtlStub.GetLoanOperativeInfoReqMsg getLoanOperativeInfoReqMsg0,

			final com.newgen.dscop.stub.InqLoanOperationDtlCallbackHandler callback)

					throws java.rmi.RemoteException{

		org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
		_operationClient.getOptions().setAction("/Services/EnterpriseServicesInquiry/Loan/Service/InqLoanOperationDtl.serviceagent/InqLoanOperationDtlPortTypeEndpoint1/InqLoanOperationDtl_Oper");
		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



		addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");



		// create SOAP envelope with that payload
		org.apache.axiom.soap.SOAPEnvelope env=null;
		final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();


		//Style is Doc.


		env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
				getLoanOperativeInfoReqMsg0,
				optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1238430813937",
						"inqLoanOperationDtl_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1238430813937",
								"inqLoanOperationDtl_Oper"));

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
							com.newgen.dscop.stub.InqLoanOperationDtlStub.GetLoanOperativeInfoResMsg.class,
							getEnvelopeNamespaces(resultEnv));
					callback.receiveResultinqLoanOperationDtl_Oper(
							(com.newgen.dscop.stub.InqLoanOperationDtlStub.GetLoanOperativeInfoResMsg)object);

				} catch (org.apache.axis2.AxisFault e) {
					callback.receiveErrorinqLoanOperationDtl_Oper(e);
				}
			}

			public void onError(java.lang.Exception error) {
				if (error instanceof org.apache.axis2.AxisFault) {
					org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
					org.apache.axiom.om.OMElement faultElt = f.getDetail();
					if (faultElt!=null){
						if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqLoanOperationDtl_Oper"))){
							//make the fault by reflection
							try{
								java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqLoanOperationDtl_Oper"));
								java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
								java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
								java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
								//message class
								java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqLoanOperationDtl_Oper"));
								java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
								java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
								java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
										new java.lang.Class[]{messageClass});
								m.invoke(ex,new java.lang.Object[]{messageObject});


								callback.receiveErrorinqLoanOperationDtl_Oper(new java.rmi.RemoteException(ex.getMessage(), ex));
							} catch(java.lang.ClassCastException e){
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorinqLoanOperationDtl_Oper(f);
							} catch (java.lang.ClassNotFoundException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorinqLoanOperationDtl_Oper(f);
							} catch (java.lang.NoSuchMethodException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorinqLoanOperationDtl_Oper(f);
							} catch (java.lang.reflect.InvocationTargetException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorinqLoanOperationDtl_Oper(f);
							} catch (java.lang.IllegalAccessException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorinqLoanOperationDtl_Oper(f);
							} catch (java.lang.InstantiationException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorinqLoanOperationDtl_Oper(f);
							} catch (org.apache.axis2.AxisFault e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorinqLoanOperationDtl_Oper(f);
							}
						} else {
							callback.receiveErrorinqLoanOperationDtl_Oper(f);
						}
					} else {
						callback.receiveErrorinqLoanOperationDtl_Oper(f);
					}
				} else {
					callback.receiveErrorinqLoanOperationDtl_Oper(error);
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
					callback.receiveErrorinqLoanOperationDtl_Oper(axisFault);
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
	//http://10.101.107.25:5503/Services/EnterpriseServicesInquiry/Loan/Service/InqLoanOperationDtl.serviceagent/InqLoanOperationDtlPortTypeEndpoint1
	public static class GetLoanOperativeInfoReq_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = GetLoanOperativeInfoReq_type0
                Namespace URI = http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd
                Namespace Prefix = ns3
		 */


		/**
		 * field for LoanAcctNo
		 */


		protected java.lang.String localLoanAcctNo ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLoanAcctNoTracker = false ;

		public boolean isLoanAcctNoSpecified(){
			return localLoanAcctNoTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLoanAcctNo(){
			return localLoanAcctNo;
		}



		/**
		 * Auto generated setter method
		 * @param param LoanAcctNo
		 */
		public void setLoanAcctNo(java.lang.String param){
			localLoanAcctNoTracker = param != null;

			this.localLoanAcctNo=param;


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
		 * field for SettlementValDate
		 */


		protected java.lang.String localSettlementValDate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localSettlementValDateTracker = false ;

		public boolean isSettlementValDateSpecified(){
			return localSettlementValDateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getSettlementValDate(){
			return localSettlementValDate;
		}



		/**
		 * Auto generated setter method
		 * @param param SettlementValDate
		 */
		public void setSettlementValDate(java.lang.String param){
			localSettlementValDateTracker = param != null;

			this.localSettlementValDate=param;


		}


		/**
		 * field for EsfPercentage
		 */


		protected java.lang.String localEsfPercentage ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localEsfPercentageTracker = false ;

		public boolean isEsfPercentageSpecified(){
			return localEsfPercentageTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getEsfPercentage(){
			return localEsfPercentage;
		}



		/**
		 * Auto generated setter method
		 * @param param EsfPercentage
		 */
		public void setEsfPercentage(java.lang.String param){
			localEsfPercentageTracker = param != null;

			this.localEsfPercentage=param;


		}


		/**
		 * field for EsfAmount
		 */


		protected java.lang.String localEsfAmount ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localEsfAmountTracker = false ;

		public boolean isEsfAmountSpecified(){
			return localEsfAmountTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getEsfAmount(){
			return localEsfAmount;
		}



		/**
		 * Auto generated setter method
		 * @param param EsfAmount
		 */
		public void setEsfAmount(java.lang.String param){
			localEsfAmountTracker = param != null;

			this.localEsfAmount=param;


		}


		/**
		 * field for LoanForeClosureFlag
		 */


		protected java.lang.String localLoanForeClosureFlag ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLoanForeClosureFlagTracker = false ;

		public boolean isLoanForeClosureFlagSpecified(){
			return localLoanForeClosureFlagTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLoanForeClosureFlag(){
			return localLoanForeClosureFlag;
		}



		/**
		 * Auto generated setter method
		 * @param param LoanForeClosureFlag
		 */
		public void setLoanForeClosureFlag(java.lang.String param){
			localLoanForeClosureFlagTracker = param != null;

			this.localLoanForeClosureFlag=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":GetLoanOperativeInfoReq_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"GetLoanOperativeInfoReq_type0",
							xmlWriter);
				}


			}
			if (localLoanAcctNoTracker){
				namespace = "http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd";
				writeStartElement(null, namespace, "LoanAcctNo", xmlWriter);


				if (localLoanAcctNo==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("LoanAcctNo cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLoanAcctNo);

				}

				xmlWriter.writeEndElement();
			} if (localCustomerIdTracker){
				namespace = "http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd";
				writeStartElement(null, namespace, "customerId", xmlWriter);


				if (localCustomerId==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCustomerId);

				}

				xmlWriter.writeEndElement();
			} if (localSettlementValDateTracker){
				namespace = "http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd";
				writeStartElement(null, namespace, "settlementValDate", xmlWriter);


				if (localSettlementValDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("settlementValDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localSettlementValDate);

				}

				xmlWriter.writeEndElement();
			} if (localEsfPercentageTracker){
				namespace = "http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd";
				writeStartElement(null, namespace, "esfPercentage", xmlWriter);


				if (localEsfPercentage==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("esfPercentage cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localEsfPercentage);

				}

				xmlWriter.writeEndElement();
			} if (localEsfAmountTracker){
				namespace = "http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd";
				writeStartElement(null, namespace, "esfAmount", xmlWriter);


				if (localEsfAmount==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("esfAmount cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localEsfAmount);

				}

				xmlWriter.writeEndElement();
			} if (localLoanForeClosureFlagTracker){
				namespace = "http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd";
				writeStartElement(null, namespace, "loanForeClosureFlag", xmlWriter);


				if (localLoanForeClosureFlag==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("loanForeClosureFlag cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLoanForeClosureFlag);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd")){
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

			if (localLoanAcctNoTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd",
						"LoanAcctNo"));

				if (localLoanAcctNo != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanAcctNo));
				} else {
					throw new org.apache.axis2.databinding.ADBException("LoanAcctNo cannot be null!!");
				}
			} if (localCustomerIdTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd",
						"customerId"));

				if (localCustomerId != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerId));
				} else {
					throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");
				}
			} if (localSettlementValDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd",
						"settlementValDate"));

				if (localSettlementValDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSettlementValDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("settlementValDate cannot be null!!");
				}
			} if (localEsfPercentageTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd",
						"esfPercentage"));

				if (localEsfPercentage != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEsfPercentage));
				} else {
					throw new org.apache.axis2.databinding.ADBException("esfPercentage cannot be null!!");
				}
			} if (localEsfAmountTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd",
						"esfAmount"));

				if (localEsfAmount != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEsfAmount));
				} else {
					throw new org.apache.axis2.databinding.ADBException("esfAmount cannot be null!!");
				}
			} if (localLoanForeClosureFlagTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd",
						"loanForeClosureFlag"));

				if (localLoanForeClosureFlag != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanForeClosureFlag));
				} else {
					throw new org.apache.axis2.databinding.ADBException("loanForeClosureFlag cannot be null!!");
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
			public static GetLoanOperativeInfoReq_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				GetLoanOperativeInfoReq_type0 object =
						new GetLoanOperativeInfoReq_type0();

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

							if (!"GetLoanOperativeInfoReq_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (GetLoanOperativeInfoReq_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd","LoanAcctNo").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"LoanAcctNo" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLoanAcctNo(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd","customerId").equals(reader.getName())){

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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd","settlementValDate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"settlementValDate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setSettlementValDate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd","esfPercentage").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"esfPercentage" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setEsfPercentage(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd","esfAmount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"esfAmount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setEsfAmount(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd","loanForeClosureFlag").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"loanForeClosureFlag" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLoanForeClosureFlag(
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
					"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd".equals(namespaceURI) &&
					"limits_type1".equals(typeName)){

				return  Limits_type1.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd".equals(namespaceURI) &&
					"balScheduleDetails_type0".equals(typeName)){

				return  BalScheduleDetails_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd".equals(namespaceURI) &&
					"balScheduleDetails_type1".equals(typeName)){

				return  BalScheduleDetails_type1.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd".equals(namespaceURI) &&
					"GetLoanOperativeInfoReq_type0".equals(typeName)){

				return  GetLoanOperativeInfoReq_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd".equals(namespaceURI) &&
					"standingInst_type1".equals(typeName)){

				return  StandingInst_type1.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd".equals(namespaceURI) &&
					"standingInst_type0".equals(typeName)){

				return  StandingInst_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd".equals(namespaceURI) &&
					"delinquency_type1".equals(typeName)){

				return  Delinquency_type1.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd".equals(namespaceURI) &&
					"delinquency_type0".equals(typeName)){

				return  Delinquency_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/header.xsd".equals(namespaceURI) &&
					"headerType".equals(typeName)){

				return  HeaderType.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd".equals(namespaceURI) &&
					"linkedCustomer_type1".equals(typeName)){

				return  LinkedCustomer_type1.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd".equals(namespaceURI) &&
					"linkedCustomer_type0".equals(typeName)){

				return  LinkedCustomer_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd".equals(namespaceURI) &&
					"GetLoanOperativeInfoRes_type0".equals(typeName)){

				return  GetLoanOperativeInfoRes_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd".equals(namespaceURI) &&
					"Loan_Operative_Info_type0".equals(typeName)){

				return  Loan_Operative_Info_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd".equals(namespaceURI) &&
					"PDC_type1".equals(typeName)){

				return  PDC_type1.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd".equals(namespaceURI) &&
					"PDC_type0".equals(typeName)){

				return  PDC_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd".equals(namespaceURI) &&
					"limits_type0".equals(typeName)){

				return  Limits_type0.Factory.parse(reader);


			}


			throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
		}

	}

	public static class GetLoanOperativeInfoReq
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd",
				"GetLoanOperativeInfoReq",
				"ns3");



		/**
		 * field for GetLoanOperativeInfoReq
		 */


		protected GetLoanOperativeInfoReq_type0 localGetLoanOperativeInfoReq ;


		/**
		 * Auto generated getter method
		 * @return GetLoanOperativeInfoReq_type0
		 */
		public  GetLoanOperativeInfoReq_type0 getGetLoanOperativeInfoReq(){
			return localGetLoanOperativeInfoReq;
		}



		/**
		 * Auto generated setter method
		 * @param param GetLoanOperativeInfoReq
		 */
		public void setGetLoanOperativeInfoReq(GetLoanOperativeInfoReq_type0 param){

			this.localGetLoanOperativeInfoReq=param;


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

			if (localGetLoanOperativeInfoReq==null){
				throw new org.apache.axis2.databinding.ADBException("GetLoanOperativeInfoReq cannot be null!");
			}
			localGetLoanOperativeInfoReq.serialize(MY_QNAME,xmlWriter);


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd")){
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




			//We can safely assume an element has only one type associated with it
			return localGetLoanOperativeInfoReq.getPullParser(MY_QNAME);

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
			public static GetLoanOperativeInfoReq parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				GetLoanOperativeInfoReq object =
						new GetLoanOperativeInfoReq();

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

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd","GetLoanOperativeInfoReq").equals(reader.getName())){

								object.setGetLoanOperativeInfoReq(GetLoanOperativeInfoReq_type0.Factory.parse(reader));

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


	public static class LoanAcctNo
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd",
				"LoanAcctNo",
				"ns3");



		/**
		 * field for LoanAcctNo
		 */


		protected java.lang.String localLoanAcctNo ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLoanAcctNo(){
			return localLoanAcctNo;
		}



		/**
		 * Auto generated setter method
		 * @param param LoanAcctNo
		 */
		public void setLoanAcctNo(java.lang.String param){

			this.localLoanAcctNo=param;


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

			java.lang.String namespace = "http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd";
			java.lang.String _localName = "LoanAcctNo";

			writeStartElement(null, namespace, _localName, xmlWriter);

			// add the type details if this is used in a simple type
			if (serializeType){
				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":LoanAcctNo",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"LoanAcctNo",
							xmlWriter);
				}
			}

			if (localLoanAcctNo==null){

				throw new org.apache.axis2.databinding.ADBException("LoanAcctNo cannot be null !!");

			}else{

				xmlWriter.writeCharacters(localLoanAcctNo);

			}

			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd")){
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




			//We can safely assume an element has only one type associated with it
			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(MY_QNAME,
					new java.lang.Object[]{
					org.apache.axis2.databinding.utils.reader.ADBXMLStreamReader.ELEMENT_TEXT,
					org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanAcctNo)
			},
			null);

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
			public static LoanAcctNo parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				LoanAcctNo object =
						new LoanAcctNo();

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

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd","LoanAcctNo").equals(reader.getName())){

								nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
								if ("true".equals(nillableValue) || "1".equals(nillableValue)){
									throw new org.apache.axis2.databinding.ADBException("The element: "+"LoanAcctNo" +"  cannot be null");
								}


								java.lang.String content = reader.getElementText();

								object.setLoanAcctNo(
										org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

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


	public static class StandingInst_type1
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = standingInst_type1
                Namespace URI = http://www.adcb.com/esb/common/LoanOperativeInfo.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for SINo
		 */


		protected java.lang.String localSINo ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localSINoTracker = false ;

		public boolean isSINoSpecified(){
			return localSINoTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getSINo(){
			return localSINo;
		}



		/**
		 * Auto generated setter method
		 * @param param SINo
		 */
		public void setSINo(java.lang.String param){
			localSINoTracker = param != null;

			this.localSINo=param;


		}


		/**
		 * field for SIStartDate
		 */


		protected java.lang.String localSIStartDate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localSIStartDateTracker = false ;

		public boolean isSIStartDateSpecified(){
			return localSIStartDateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getSIStartDate(){
			return localSIStartDate;
		}



		/**
		 * Auto generated setter method
		 * @param param SIStartDate
		 */
		public void setSIStartDate(java.lang.String param){
			localSIStartDateTracker = param != null;

			this.localSIStartDate=param;


		}


		/**
		 * field for SIEndDate
		 */


		protected java.lang.String localSIEndDate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localSIEndDateTracker = false ;

		public boolean isSIEndDateSpecified(){
			return localSIEndDateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getSIEndDate(){
			return localSIEndDate;
		}



		/**
		 * Auto generated setter method
		 * @param param SIEndDate
		 */
		public void setSIEndDate(java.lang.String param){
			localSIEndDateTracker = param != null;

			this.localSIEndDate=param;


		}


		/**
		 * field for Amount
		 */


		protected java.lang.String localAmount ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAmountTracker = false ;

		public boolean isAmountSpecified(){
			return localAmountTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getAmount(){
			return localAmount;
		}



		/**
		 * Auto generated setter method
		 * @param param Amount
		 */
		public void setAmount(java.lang.String param){
			localAmountTracker = param != null;

			this.localAmount=param;


		}


		/**
		 * field for Beneficiery
		 */


		protected java.lang.String localBeneficiery ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localBeneficieryTracker = false ;

		public boolean isBeneficierySpecified(){
			return localBeneficieryTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getBeneficiery(){
			return localBeneficiery;
		}



		/**
		 * Auto generated setter method
		 * @param param Beneficiery
		 */
		public void setBeneficiery(java.lang.String param){
			localBeneficieryTracker = param != null;

			this.localBeneficiery=param;


		}


		/**
		 * field for LastSIExecution
		 */


		protected java.lang.String localLastSIExecution ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLastSIExecutionTracker = false ;

		public boolean isLastSIExecutionSpecified(){
			return localLastSIExecutionTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLastSIExecution(){
			return localLastSIExecution;
		}



		/**
		 * Auto generated setter method
		 * @param param LastSIExecution
		 */
		public void setLastSIExecution(java.lang.String param){
			localLastSIExecutionTracker = param != null;

			this.localLastSIExecution=param;


		}


		/**
		 * field for ReasonForFailure
		 */


		protected java.lang.String localReasonForFailure ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localReasonForFailureTracker = false ;

		public boolean isReasonForFailureSpecified(){
			return localReasonForFailureTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getReasonForFailure(){
			return localReasonForFailure;
		}



		/**
		 * Auto generated setter method
		 * @param param ReasonForFailure
		 */
		public void setReasonForFailure(java.lang.String param){
			localReasonForFailureTracker = param != null;

			this.localReasonForFailure=param;


		}


		/**
		 * field for Narration
		 */


		protected java.lang.String localNarration ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNarrationTracker = false ;

		public boolean isNarrationSpecified(){
			return localNarrationTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNarration(){
			return localNarration;
		}



		/**
		 * Auto generated setter method
		 * @param param Narration
		 */
		public void setNarration(java.lang.String param){
			localNarrationTracker = param != null;

			this.localNarration=param;


		}


		/**
		 * field for SIFrequency
		 */


		protected java.lang.String localSIFrequency ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localSIFrequencyTracker = false ;

		public boolean isSIFrequencySpecified(){
			return localSIFrequencyTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getSIFrequency(){
			return localSIFrequency;
		}



		/**
		 * Auto generated setter method
		 * @param param SIFrequency
		 */
		public void setSIFrequency(java.lang.String param){
			localSIFrequencyTracker = param != null;

			this.localSIFrequency=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":standingInst_type1",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"standingInst_type1",
							xmlWriter);
				}


			}
			if (localSINoTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "SINo", xmlWriter);


				if (localSINo==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("SINo cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localSINo);

				}

				xmlWriter.writeEndElement();
			} if (localSIStartDateTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "SIStartDate", xmlWriter);


				if (localSIStartDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("SIStartDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localSIStartDate);

				}

				xmlWriter.writeEndElement();
			} if (localSIEndDateTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "SIEndDate", xmlWriter);


				if (localSIEndDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("SIEndDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localSIEndDate);

				}

				xmlWriter.writeEndElement();
			} if (localAmountTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "amount", xmlWriter);


				if (localAmount==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("amount cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localAmount);

				}

				xmlWriter.writeEndElement();
			} if (localBeneficieryTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "beneficiery", xmlWriter);


				if (localBeneficiery==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("beneficiery cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localBeneficiery);

				}

				xmlWriter.writeEndElement();
			} if (localLastSIExecutionTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "lastSIExecution", xmlWriter);


				if (localLastSIExecution==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("lastSIExecution cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLastSIExecution);

				}

				xmlWriter.writeEndElement();
			} if (localReasonForFailureTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "reasonForFailure", xmlWriter);


				if (localReasonForFailure==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("reasonForFailure cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localReasonForFailure);

				}

				xmlWriter.writeEndElement();
			} if (localNarrationTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "narration", xmlWriter);


				if (localNarration==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("narration cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNarration);

				}

				xmlWriter.writeEndElement();
			} if (localSIFrequencyTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "SIFrequency", xmlWriter);


				if (localSIFrequency==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("SIFrequency cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localSIFrequency);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd")){
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

			if (localSINoTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"SINo"));

				if (localSINo != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSINo));
				} else {
					throw new org.apache.axis2.databinding.ADBException("SINo cannot be null!!");
				}
			} if (localSIStartDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"SIStartDate"));

				if (localSIStartDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSIStartDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("SIStartDate cannot be null!!");
				}
			} if (localSIEndDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"SIEndDate"));

				if (localSIEndDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSIEndDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("SIEndDate cannot be null!!");
				}
			} if (localAmountTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"amount"));

				if (localAmount != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAmount));
				} else {
					throw new org.apache.axis2.databinding.ADBException("amount cannot be null!!");
				}
			} if (localBeneficieryTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"beneficiery"));

				if (localBeneficiery != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBeneficiery));
				} else {
					throw new org.apache.axis2.databinding.ADBException("beneficiery cannot be null!!");
				}
			} if (localLastSIExecutionTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"lastSIExecution"));

				if (localLastSIExecution != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLastSIExecution));
				} else {
					throw new org.apache.axis2.databinding.ADBException("lastSIExecution cannot be null!!");
				}
			} if (localReasonForFailureTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"reasonForFailure"));

				if (localReasonForFailure != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReasonForFailure));
				} else {
					throw new org.apache.axis2.databinding.ADBException("reasonForFailure cannot be null!!");
				}
			} if (localNarrationTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"narration"));

				if (localNarration != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNarration));
				} else {
					throw new org.apache.axis2.databinding.ADBException("narration cannot be null!!");
				}
			} if (localSIFrequencyTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"SIFrequency"));

				if (localSIFrequency != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSIFrequency));
				} else {
					throw new org.apache.axis2.databinding.ADBException("SIFrequency cannot be null!!");
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
			public static StandingInst_type1 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				StandingInst_type1 object =
						new StandingInst_type1();

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

							if (!"standingInst_type1".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (StandingInst_type1)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","SINo").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"SINo" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setSINo(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","SIStartDate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"SIStartDate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setSIStartDate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","SIEndDate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"SIEndDate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setSIEndDate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","amount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"amount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAmount(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","beneficiery").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"beneficiery" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setBeneficiery(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","lastSIExecution").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"lastSIExecution" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLastSIExecution(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","reasonForFailure").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"reasonForFailure" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setReasonForFailure(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","narration").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"narration" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNarration(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","SIFrequency").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"SIFrequency" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setSIFrequency(
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


	public static class StandingInst_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = standingInst_type0
                Namespace URI = http://www.adcb.com/esb/common/LoanOperativeInfo.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for SINo
		 */


		protected java.lang.String localSINo ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localSINoTracker = false ;

		public boolean isSINoSpecified(){
			return localSINoTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getSINo(){
			return localSINo;
		}



		/**
		 * Auto generated setter method
		 * @param param SINo
		 */
		public void setSINo(java.lang.String param){
			localSINoTracker = param != null;

			this.localSINo=param;


		}


		/**
		 * field for SIStartDate
		 */


		protected java.lang.String localSIStartDate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localSIStartDateTracker = false ;

		public boolean isSIStartDateSpecified(){
			return localSIStartDateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getSIStartDate(){
			return localSIStartDate;
		}



		/**
		 * Auto generated setter method
		 * @param param SIStartDate
		 */
		public void setSIStartDate(java.lang.String param){
			localSIStartDateTracker = param != null;

			this.localSIStartDate=param;


		}


		/**
		 * field for SIEndDate
		 */


		protected java.lang.String localSIEndDate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localSIEndDateTracker = false ;

		public boolean isSIEndDateSpecified(){
			return localSIEndDateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getSIEndDate(){
			return localSIEndDate;
		}



		/**
		 * Auto generated setter method
		 * @param param SIEndDate
		 */
		public void setSIEndDate(java.lang.String param){
			localSIEndDateTracker = param != null;

			this.localSIEndDate=param;


		}


		/**
		 * field for Amount
		 */


		protected java.lang.String localAmount ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAmountTracker = false ;

		public boolean isAmountSpecified(){
			return localAmountTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getAmount(){
			return localAmount;
		}



		/**
		 * Auto generated setter method
		 * @param param Amount
		 */
		public void setAmount(java.lang.String param){
			localAmountTracker = param != null;

			this.localAmount=param;


		}


		/**
		 * field for Beneficiery
		 */


		protected java.lang.String localBeneficiery ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localBeneficieryTracker = false ;

		public boolean isBeneficierySpecified(){
			return localBeneficieryTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getBeneficiery(){
			return localBeneficiery;
		}



		/**
		 * Auto generated setter method
		 * @param param Beneficiery
		 */
		public void setBeneficiery(java.lang.String param){
			localBeneficieryTracker = param != null;

			this.localBeneficiery=param;


		}


		/**
		 * field for LastSIExecution
		 */


		protected java.lang.String localLastSIExecution ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLastSIExecutionTracker = false ;

		public boolean isLastSIExecutionSpecified(){
			return localLastSIExecutionTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLastSIExecution(){
			return localLastSIExecution;
		}



		/**
		 * Auto generated setter method
		 * @param param LastSIExecution
		 */
		public void setLastSIExecution(java.lang.String param){
			localLastSIExecutionTracker = param != null;

			this.localLastSIExecution=param;


		}


		/**
		 * field for ReasonForFailure
		 */


		protected java.lang.String localReasonForFailure ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localReasonForFailureTracker = false ;

		public boolean isReasonForFailureSpecified(){
			return localReasonForFailureTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getReasonForFailure(){
			return localReasonForFailure;
		}



		/**
		 * Auto generated setter method
		 * @param param ReasonForFailure
		 */
		public void setReasonForFailure(java.lang.String param){
			localReasonForFailureTracker = param != null;

			this.localReasonForFailure=param;


		}


		/**
		 * field for Narration
		 */


		protected java.lang.String localNarration ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNarrationTracker = false ;

		public boolean isNarrationSpecified(){
			return localNarrationTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNarration(){
			return localNarration;
		}



		/**
		 * Auto generated setter method
		 * @param param Narration
		 */
		public void setNarration(java.lang.String param){
			localNarrationTracker = param != null;

			this.localNarration=param;


		}


		/**
		 * field for SIFrequency
		 */


		protected java.lang.String localSIFrequency ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localSIFrequencyTracker = false ;

		public boolean isSIFrequencySpecified(){
			return localSIFrequencyTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getSIFrequency(){
			return localSIFrequency;
		}



		/**
		 * Auto generated setter method
		 * @param param SIFrequency
		 */
		public void setSIFrequency(java.lang.String param){
			localSIFrequencyTracker = param != null;

			this.localSIFrequency=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":standingInst_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"standingInst_type0",
							xmlWriter);
				}


			}
			if (localSINoTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "SINo", xmlWriter);


				if (localSINo==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("SINo cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localSINo);

				}

				xmlWriter.writeEndElement();
			} if (localSIStartDateTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "SIStartDate", xmlWriter);


				if (localSIStartDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("SIStartDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localSIStartDate);

				}

				xmlWriter.writeEndElement();
			} if (localSIEndDateTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "SIEndDate", xmlWriter);


				if (localSIEndDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("SIEndDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localSIEndDate);

				}

				xmlWriter.writeEndElement();
			} if (localAmountTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "amount", xmlWriter);


				if (localAmount==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("amount cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localAmount);

				}

				xmlWriter.writeEndElement();
			} if (localBeneficieryTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "beneficiery", xmlWriter);


				if (localBeneficiery==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("beneficiery cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localBeneficiery);

				}

				xmlWriter.writeEndElement();
			} if (localLastSIExecutionTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "lastSIExecution", xmlWriter);


				if (localLastSIExecution==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("lastSIExecution cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLastSIExecution);

				}

				xmlWriter.writeEndElement();
			} if (localReasonForFailureTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "reasonForFailure", xmlWriter);


				if (localReasonForFailure==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("reasonForFailure cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localReasonForFailure);

				}

				xmlWriter.writeEndElement();
			} if (localNarrationTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "narration", xmlWriter);


				if (localNarration==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("narration cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNarration);

				}

				xmlWriter.writeEndElement();
			} if (localSIFrequencyTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "SIFrequency", xmlWriter);


				if (localSIFrequency==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("SIFrequency cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localSIFrequency);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd")){
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

			if (localSINoTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"SINo"));

				if (localSINo != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSINo));
				} else {
					throw new org.apache.axis2.databinding.ADBException("SINo cannot be null!!");
				}
			} if (localSIStartDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"SIStartDate"));

				if (localSIStartDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSIStartDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("SIStartDate cannot be null!!");
				}
			} if (localSIEndDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"SIEndDate"));

				if (localSIEndDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSIEndDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("SIEndDate cannot be null!!");
				}
			} if (localAmountTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"amount"));

				if (localAmount != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAmount));
				} else {
					throw new org.apache.axis2.databinding.ADBException("amount cannot be null!!");
				}
			} if (localBeneficieryTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"beneficiery"));

				if (localBeneficiery != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBeneficiery));
				} else {
					throw new org.apache.axis2.databinding.ADBException("beneficiery cannot be null!!");
				}
			} if (localLastSIExecutionTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"lastSIExecution"));

				if (localLastSIExecution != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLastSIExecution));
				} else {
					throw new org.apache.axis2.databinding.ADBException("lastSIExecution cannot be null!!");
				}
			} if (localReasonForFailureTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"reasonForFailure"));

				if (localReasonForFailure != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReasonForFailure));
				} else {
					throw new org.apache.axis2.databinding.ADBException("reasonForFailure cannot be null!!");
				}
			} if (localNarrationTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"narration"));

				if (localNarration != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNarration));
				} else {
					throw new org.apache.axis2.databinding.ADBException("narration cannot be null!!");
				}
			} if (localSIFrequencyTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"SIFrequency"));

				if (localSIFrequency != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSIFrequency));
				} else {
					throw new org.apache.axis2.databinding.ADBException("SIFrequency cannot be null!!");
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
			public static StandingInst_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				StandingInst_type0 object =
						new StandingInst_type0();

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

							if (!"standingInst_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (StandingInst_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","SINo").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"SINo" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setSINo(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","SIStartDate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"SIStartDate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setSIStartDate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","SIEndDate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"SIEndDate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setSIEndDate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","amount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"amount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAmount(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","beneficiery").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"beneficiery" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setBeneficiery(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","lastSIExecution").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"lastSIExecution" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLastSIExecution(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","reasonForFailure").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"reasonForFailure" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setReasonForFailure(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","narration").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"narration" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNarration(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","SIFrequency").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"SIFrequency" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setSIFrequency(
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


	public static class Limits_type1
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = limits_type1
                Namespace URI = http://www.adcb.com/esb/common/LoanOperativeInfo.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for LimitNo
		 */


		protected java.lang.String localLimitNo ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLimitNoTracker = false ;

		public boolean isLimitNoSpecified(){
			return localLimitNoTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLimitNo(){
			return localLimitNo;
		}



		/**
		 * Auto generated setter method
		 * @param param LimitNo
		 */
		public void setLimitNo(java.lang.String param){
			localLimitNoTracker = param != null;

			this.localLimitNo=param;


		}


		/**
		 * field for StartDate
		 */


		protected java.lang.String localStartDate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localStartDateTracker = false ;

		public boolean isStartDateSpecified(){
			return localStartDateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getStartDate(){
			return localStartDate;
		}



		/**
		 * Auto generated setter method
		 * @param param StartDate
		 */
		public void setStartDate(java.lang.String param){
			localStartDateTracker = param != null;

			this.localStartDate=param;


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
		 * field for Amount
		 */


		protected java.lang.String localAmount ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAmountTracker = false ;

		public boolean isAmountSpecified(){
			return localAmountTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getAmount(){
			return localAmount;
		}



		/**
		 * Auto generated setter method
		 * @param param Amount
		 */
		public void setAmount(java.lang.String param){
			localAmountTracker = param != null;

			this.localAmount=param;


		}


		/**
		 * field for Rate
		 */


		protected java.lang.String localRate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localRateTracker = false ;

		public boolean isRateSpecified(){
			return localRateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getRate(){
			return localRate;
		}



		/**
		 * Auto generated setter method
		 * @param param Rate
		 */
		public void setRate(java.lang.String param){
			localRateTracker = param != null;

			this.localRate=param;


		}


		/**
		 * field for ReasonCode
		 */


		protected java.lang.String localReasonCode ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localReasonCodeTracker = false ;

		public boolean isReasonCodeSpecified(){
			return localReasonCodeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getReasonCode(){
			return localReasonCode;
		}



		/**
		 * Auto generated setter method
		 * @param param ReasonCode
		 */
		public void setReasonCode(java.lang.String param){
			localReasonCodeTracker = param != null;

			this.localReasonCode=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":limits_type1",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"limits_type1",
							xmlWriter);
				}


			}
			if (localLimitNoTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "limitNo", xmlWriter);


				if (localLimitNo==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("limitNo cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLimitNo);

				}

				xmlWriter.writeEndElement();
			} if (localStartDateTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "startDate", xmlWriter);


				if (localStartDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("startDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localStartDate);

				}

				xmlWriter.writeEndElement();
			} if (localExpiryDateTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "expiryDate", xmlWriter);


				if (localExpiryDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("expiryDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localExpiryDate);

				}

				xmlWriter.writeEndElement();
			} if (localAmountTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "amount", xmlWriter);


				if (localAmount==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("amount cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localAmount);

				}

				xmlWriter.writeEndElement();
			} if (localRateTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "rate", xmlWriter);


				if (localRate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("rate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localRate);

				}

				xmlWriter.writeEndElement();
			} if (localReasonCodeTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "reasonCode", xmlWriter);


				if (localReasonCode==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("reasonCode cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localReasonCode);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd")){
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

			if (localLimitNoTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"limitNo"));

				if (localLimitNo != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLimitNo));
				} else {
					throw new org.apache.axis2.databinding.ADBException("limitNo cannot be null!!");
				}
			} if (localStartDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"startDate"));

				if (localStartDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStartDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("startDate cannot be null!!");
				}
			} if (localExpiryDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"expiryDate"));

				if (localExpiryDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExpiryDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("expiryDate cannot be null!!");
				}
			} if (localAmountTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"amount"));

				if (localAmount != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAmount));
				} else {
					throw new org.apache.axis2.databinding.ADBException("amount cannot be null!!");
				}
			} if (localRateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"rate"));

				if (localRate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("rate cannot be null!!");
				}
			} if (localReasonCodeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"reasonCode"));

				if (localReasonCode != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReasonCode));
				} else {
					throw new org.apache.axis2.databinding.ADBException("reasonCode cannot be null!!");
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
			public static Limits_type1 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				Limits_type1 object =
						new Limits_type1();

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

							if (!"limits_type1".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (Limits_type1)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","limitNo").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"limitNo" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLimitNo(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","startDate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"startDate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setStartDate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","expiryDate").equals(reader.getName())){

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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","amount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"amount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAmount(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","rate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"rate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setRate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","reasonCode").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"reasonCode" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setReasonCode(
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


	public static class Limits_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = limits_type0
                Namespace URI = http://www.adcb.com/esb/common/LoanOperativeInfo.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for LimitNo
		 */


		protected java.lang.String localLimitNo ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLimitNoTracker = false ;

		public boolean isLimitNoSpecified(){
			return localLimitNoTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLimitNo(){
			return localLimitNo;
		}



		/**
		 * Auto generated setter method
		 * @param param LimitNo
		 */
		public void setLimitNo(java.lang.String param){
			localLimitNoTracker = param != null;

			this.localLimitNo=param;


		}


		/**
		 * field for StartDate
		 */


		protected java.lang.String localStartDate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localStartDateTracker = false ;

		public boolean isStartDateSpecified(){
			return localStartDateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getStartDate(){
			return localStartDate;
		}



		/**
		 * Auto generated setter method
		 * @param param StartDate
		 */
		public void setStartDate(java.lang.String param){
			localStartDateTracker = param != null;

			this.localStartDate=param;


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
		 * field for Amount
		 */


		protected java.lang.String localAmount ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAmountTracker = false ;

		public boolean isAmountSpecified(){
			return localAmountTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getAmount(){
			return localAmount;
		}



		/**
		 * Auto generated setter method
		 * @param param Amount
		 */
		public void setAmount(java.lang.String param){
			localAmountTracker = param != null;

			this.localAmount=param;


		}


		/**
		 * field for Rate
		 */


		protected java.lang.String localRate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localRateTracker = false ;

		public boolean isRateSpecified(){
			return localRateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getRate(){
			return localRate;
		}



		/**
		 * Auto generated setter method
		 * @param param Rate
		 */
		public void setRate(java.lang.String param){
			localRateTracker = param != null;

			this.localRate=param;


		}


		/**
		 * field for ReasonCode
		 */


		protected java.lang.String localReasonCode ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localReasonCodeTracker = false ;

		public boolean isReasonCodeSpecified(){
			return localReasonCodeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getReasonCode(){
			return localReasonCode;
		}



		/**
		 * Auto generated setter method
		 * @param param ReasonCode
		 */
		public void setReasonCode(java.lang.String param){
			localReasonCodeTracker = param != null;

			this.localReasonCode=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":limits_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"limits_type0",
							xmlWriter);
				}


			}
			if (localLimitNoTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "limitNo", xmlWriter);


				if (localLimitNo==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("limitNo cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLimitNo);

				}

				xmlWriter.writeEndElement();
			} if (localStartDateTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "startDate", xmlWriter);


				if (localStartDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("startDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localStartDate);

				}

				xmlWriter.writeEndElement();
			} if (localExpiryDateTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "expiryDate", xmlWriter);


				if (localExpiryDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("expiryDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localExpiryDate);

				}

				xmlWriter.writeEndElement();
			} if (localAmountTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "amount", xmlWriter);


				if (localAmount==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("amount cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localAmount);

				}

				xmlWriter.writeEndElement();
			} if (localRateTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "rate", xmlWriter);


				if (localRate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("rate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localRate);

				}

				xmlWriter.writeEndElement();
			} if (localReasonCodeTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "reasonCode", xmlWriter);


				if (localReasonCode==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("reasonCode cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localReasonCode);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd")){
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

			if (localLimitNoTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"limitNo"));

				if (localLimitNo != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLimitNo));
				} else {
					throw new org.apache.axis2.databinding.ADBException("limitNo cannot be null!!");
				}
			} if (localStartDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"startDate"));

				if (localStartDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStartDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("startDate cannot be null!!");
				}
			} if (localExpiryDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"expiryDate"));

				if (localExpiryDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localExpiryDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("expiryDate cannot be null!!");
				}
			} if (localAmountTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"amount"));

				if (localAmount != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAmount));
				} else {
					throw new org.apache.axis2.databinding.ADBException("amount cannot be null!!");
				}
			} if (localRateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"rate"));

				if (localRate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("rate cannot be null!!");
				}
			} if (localReasonCodeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"reasonCode"));

				if (localReasonCode != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReasonCode));
				} else {
					throw new org.apache.axis2.databinding.ADBException("reasonCode cannot be null!!");
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
			public static Limits_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				Limits_type0 object =
						new Limits_type0();

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

							if (!"limits_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (Limits_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","limitNo").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"limitNo" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLimitNo(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","startDate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"startDate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setStartDate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","expiryDate").equals(reader.getName())){

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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","amount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"amount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAmount(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","rate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"rate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setRate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","reasonCode").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"reasonCode" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setReasonCode(
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


	public static class BalScheduleDetails_type1
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = balScheduleDetails_type1
                Namespace URI = http://www.adcb.com/esb/common/LoanOperativeInfo.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for OutstandingBal
		 */


		protected java.lang.String localOutstandingBal ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localOutstandingBalTracker = false ;

		public boolean isOutstandingBalSpecified(){
			return localOutstandingBalTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getOutstandingBal(){
			return localOutstandingBal;
		}



		/**
		 * Auto generated setter method
		 * @param param OutstandingBal
		 */
		public void setOutstandingBal(java.lang.String param){
			localOutstandingBalTracker = param != null;

			this.localOutstandingBal=param;


		}


		/**
		 * field for InterestCharged
		 */


		protected java.lang.String localInterestCharged ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localInterestChargedTracker = false ;

		public boolean isInterestChargedSpecified(){
			return localInterestChargedTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getInterestCharged(){
			return localInterestCharged;
		}



		/**
		 * Auto generated setter method
		 * @param param InterestCharged
		 */
		public void setInterestCharged(java.lang.String param){
			localInterestChargedTracker = param != null;

			this.localInterestCharged=param;


		}


		/**
		 * field for AdvancePayment
		 */


		protected java.lang.String localAdvancePayment ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAdvancePaymentTracker = false ;

		public boolean isAdvancePaymentSpecified(){
			return localAdvancePaymentTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getAdvancePayment(){
			return localAdvancePayment;
		}



		/**
		 * Auto generated setter method
		 * @param param AdvancePayment
		 */
		public void setAdvancePayment(java.lang.String param){
			localAdvancePaymentTracker = param != null;

			this.localAdvancePayment=param;


		}


		/**
		 * field for NetPayableAmt
		 */


		protected java.lang.String localNetPayableAmt ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNetPayableAmtTracker = false ;

		public boolean isNetPayableAmtSpecified(){
			return localNetPayableAmtTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNetPayableAmt(){
			return localNetPayableAmt;
		}



		/**
		 * Auto generated setter method
		 * @param param NetPayableAmt
		 */
		public void setNetPayableAmt(java.lang.String param){
			localNetPayableAmtTracker = param != null;

			this.localNetPayableAmt=param;


		}


		/**
		 * field for PrincipalBal
		 */


		protected java.lang.String localPrincipalBal ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPrincipalBalTracker = false ;

		public boolean isPrincipalBalSpecified(){
			return localPrincipalBalTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPrincipalBal(){
			return localPrincipalBal;
		}



		/**
		 * Auto generated setter method
		 * @param param PrincipalBal
		 */
		public void setPrincipalBal(java.lang.String param){
			localPrincipalBalTracker = param != null;

			this.localPrincipalBal=param;


		}


		/**
		 * field for InstallmentArrears
		 */


		protected java.lang.String localInstallmentArrears ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getInstallmentArrears(){
			return localInstallmentArrears;
		}



		/**
		 * Auto generated setter method
		 * @param param InstallmentArrears
		 */
		public void setInstallmentArrears(java.lang.String param){

			this.localInstallmentArrears=param;


		}


		/**
		 * field for PenalInterestDue
		 */


		protected java.lang.String localPenalInterestDue ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPenalInterestDue(){
			return localPenalInterestDue;
		}



		/**
		 * Auto generated setter method
		 * @param param PenalInterestDue
		 */
		public void setPenalInterestDue(java.lang.String param){

			this.localPenalInterestDue=param;


		}


		/**
		 * field for InterestDue
		 */


		protected java.lang.String localInterestDue ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localInterestDueTracker = false ;

		public boolean isInterestDueSpecified(){
			return localInterestDueTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getInterestDue(){
			return localInterestDue;
		}



		/**
		 * Auto generated setter method
		 * @param param InterestDue
		 */
		public void setInterestDue(java.lang.String param){
			localInterestDueTracker = param != null;

			this.localInterestDue=param;


		}


		/**
		 * field for NoOfDefsTotal
		 */


		protected java.lang.String localNoOfDefsTotal ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNoOfDefsTotalTracker = false ;

		public boolean isNoOfDefsTotalSpecified(){
			return localNoOfDefsTotalTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNoOfDefsTotal(){
			return localNoOfDefsTotal;
		}



		/**
		 * Auto generated setter method
		 * @param param NoOfDefsTotal
		 */
		public void setNoOfDefsTotal(java.lang.String param){
			localNoOfDefsTotalTracker = param != null;

			this.localNoOfDefsTotal=param;


		}


		/**
		 * field for NoOfDefsCurrentYear
		 */


		protected java.lang.String localNoOfDefsCurrentYear ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNoOfDefsCurrentYearTracker = false ;

		public boolean isNoOfDefsCurrentYearSpecified(){
			return localNoOfDefsCurrentYearTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNoOfDefsCurrentYear(){
			return localNoOfDefsCurrentYear;
		}



		/**
		 * Auto generated setter method
		 * @param param NoOfDefsCurrentYear
		 */
		public void setNoOfDefsCurrentYear(java.lang.String param){
			localNoOfDefsCurrentYearTracker = param != null;

			this.localNoOfDefsCurrentYear=param;


		}


		/**
		 * field for LastDefDate
		 */


		protected java.lang.String localLastDefDate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLastDefDateTracker = false ;

		public boolean isLastDefDateSpecified(){
			return localLastDefDateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLastDefDate(){
			return localLastDefDate;
		}



		/**
		 * Auto generated setter method
		 * @param param LastDefDate
		 */
		public void setLastDefDate(java.lang.String param){
			localLastDefDateTracker = param != null;

			this.localLastDefDate=param;


		}


		/**
		 * field for InterestPaidLastEmi
		 */


		protected java.lang.String localInterestPaidLastEmi ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localInterestPaidLastEmiTracker = false ;

		public boolean isInterestPaidLastEmiSpecified(){
			return localInterestPaidLastEmiTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getInterestPaidLastEmi(){
			return localInterestPaidLastEmi;
		}



		/**
		 * Auto generated setter method
		 * @param param InterestPaidLastEmi
		 */
		public void setInterestPaidLastEmi(java.lang.String param){
			localInterestPaidLastEmiTracker = param != null;

			this.localInterestPaidLastEmi=param;


		}


		/**
		 * field for InterestToBePaid
		 */


		protected java.lang.String localInterestToBePaid ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localInterestToBePaidTracker = false ;

		public boolean isInterestToBePaidSpecified(){
			return localInterestToBePaidTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getInterestToBePaid(){
			return localInterestToBePaid;
		}



		/**
		 * Auto generated setter method
		 * @param param InterestToBePaid
		 */
		public void setInterestToBePaid(java.lang.String param){
			localInterestToBePaidTracker = param != null;

			this.localInterestToBePaid=param;


		}


		/**
		 * field for PenalltyAmount
		 */


		protected java.lang.String localPenalltyAmount ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPenalltyAmountTracker = false ;

		public boolean isPenalltyAmountSpecified(){
			return localPenalltyAmountTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPenalltyAmount(){
			return localPenalltyAmount;
		}



		/**
		 * Auto generated setter method
		 * @param param PenalltyAmount
		 */
		public void setPenalltyAmount(java.lang.String param){
			localPenalltyAmountTracker = param != null;

			this.localPenalltyAmount=param;


		}


		/**
		 * field for RateFlag
		 */


		protected java.lang.String localRateFlag ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localRateFlagTracker = false ;

		public boolean isRateFlagSpecified(){
			return localRateFlagTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getRateFlag(){
			return localRateFlag;
		}



		/**
		 * Auto generated setter method
		 * @param param RateFlag
		 */
		public void setRateFlag(java.lang.String param){
			localRateFlagTracker = param != null;

			this.localRateFlag=param;


		}


		/**
		 * field for CustShortName
		 */


		protected java.lang.String localCustShortName ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCustShortNameTracker = false ;

		public boolean isCustShortNameSpecified(){
			return localCustShortNameTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCustShortName(){
			return localCustShortName;
		}



		/**
		 * Auto generated setter method
		 * @param param CustShortName
		 */
		public void setCustShortName(java.lang.String param){
			localCustShortNameTracker = param != null;

			this.localCustShortName=param;


		}


		/**
		 * field for UnclearAmount
		 */


		protected java.lang.String localUnclearAmount ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localUnclearAmountTracker = false ;

		public boolean isUnclearAmountSpecified(){
			return localUnclearAmountTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getUnclearAmount(){
			return localUnclearAmount;
		}



		/**
		 * Auto generated setter method
		 * @param param UnclearAmount
		 */
		public void setUnclearAmount(java.lang.String param){
			localUnclearAmountTracker = param != null;

			this.localUnclearAmount=param;


		}


		/**
		 * field for ArrearsChargesAmount
		 */


		protected java.lang.String localArrearsChargesAmount ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localArrearsChargesAmountTracker = false ;

		public boolean isArrearsChargesAmountSpecified(){
			return localArrearsChargesAmountTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getArrearsChargesAmount(){
			return localArrearsChargesAmount;
		}



		/**
		 * Auto generated setter method
		 * @param param ArrearsChargesAmount
		 */
		public void setArrearsChargesAmount(java.lang.String param){
			localArrearsChargesAmountTracker = param != null;

			this.localArrearsChargesAmount=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":balScheduleDetails_type1",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"balScheduleDetails_type1",
							xmlWriter);
				}


			}
			if (localOutstandingBalTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "outstandingBal", xmlWriter);


				if (localOutstandingBal==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("outstandingBal cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localOutstandingBal);

				}

				xmlWriter.writeEndElement();
			} if (localInterestChargedTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "interestCharged", xmlWriter);


				if (localInterestCharged==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("interestCharged cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localInterestCharged);

				}

				xmlWriter.writeEndElement();
			} if (localAdvancePaymentTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "advancePayment", xmlWriter);


				if (localAdvancePayment==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("advancePayment cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localAdvancePayment);

				}

				xmlWriter.writeEndElement();
			} if (localNetPayableAmtTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "netPayableAmt", xmlWriter);


				if (localNetPayableAmt==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("netPayableAmt cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNetPayableAmt);

				}

				xmlWriter.writeEndElement();
			} if (localPrincipalBalTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "principalBal", xmlWriter);


				if (localPrincipalBal==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("principalBal cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPrincipalBal);

				}

				xmlWriter.writeEndElement();
			}
			namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
			writeStartElement(null, namespace, "installmentArrears", xmlWriter);


			if (localInstallmentArrears==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("installmentArrears cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localInstallmentArrears);

			}

			xmlWriter.writeEndElement();

			namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
			writeStartElement(null, namespace, "penalInterestDue", xmlWriter);


			if (localPenalInterestDue==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("penalInterestDue cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localPenalInterestDue);

			}

			xmlWriter.writeEndElement();
			if (localInterestDueTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "interestDue", xmlWriter);


				if (localInterestDue==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("interestDue cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localInterestDue);

				}

				xmlWriter.writeEndElement();
			} if (localNoOfDefsTotalTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "noOfDefsTotal", xmlWriter);


				if (localNoOfDefsTotal==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("noOfDefsTotal cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNoOfDefsTotal);

				}

				xmlWriter.writeEndElement();
			} if (localNoOfDefsCurrentYearTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "noOfDefsCurrentYear", xmlWriter);


				if (localNoOfDefsCurrentYear==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("noOfDefsCurrentYear cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNoOfDefsCurrentYear);

				}

				xmlWriter.writeEndElement();
			} if (localLastDefDateTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "lastDefDate", xmlWriter);


				if (localLastDefDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("lastDefDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLastDefDate);

				}

				xmlWriter.writeEndElement();
			} if (localInterestPaidLastEmiTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "interestPaidLastEmi", xmlWriter);


				if (localInterestPaidLastEmi==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("interestPaidLastEmi cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localInterestPaidLastEmi);

				}

				xmlWriter.writeEndElement();
			} if (localInterestToBePaidTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "interestToBePaid", xmlWriter);


				if (localInterestToBePaid==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("interestToBePaid cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localInterestToBePaid);

				}

				xmlWriter.writeEndElement();
			} if (localPenalltyAmountTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "penalltyAmount", xmlWriter);


				if (localPenalltyAmount==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("penalltyAmount cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPenalltyAmount);

				}

				xmlWriter.writeEndElement();
			} if (localRateFlagTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "rateFlag", xmlWriter);


				if (localRateFlag==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("rateFlag cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localRateFlag);

				}

				xmlWriter.writeEndElement();
			} if (localCustShortNameTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "custShortName", xmlWriter);


				if (localCustShortName==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("custShortName cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCustShortName);

				}

				xmlWriter.writeEndElement();
			} if (localUnclearAmountTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "unclearAmount", xmlWriter);


				if (localUnclearAmount==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("unclearAmount cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localUnclearAmount);

				}

				xmlWriter.writeEndElement();
			} if (localArrearsChargesAmountTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "arrearsChargesAmount", xmlWriter);


				if (localArrearsChargesAmount==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("arrearsChargesAmount cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localArrearsChargesAmount);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd")){
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

			if (localOutstandingBalTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"outstandingBal"));

				if (localOutstandingBal != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOutstandingBal));
				} else {
					throw new org.apache.axis2.databinding.ADBException("outstandingBal cannot be null!!");
				}
			} if (localInterestChargedTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"interestCharged"));

				if (localInterestCharged != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInterestCharged));
				} else {
					throw new org.apache.axis2.databinding.ADBException("interestCharged cannot be null!!");
				}
			} if (localAdvancePaymentTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"advancePayment"));

				if (localAdvancePayment != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAdvancePayment));
				} else {
					throw new org.apache.axis2.databinding.ADBException("advancePayment cannot be null!!");
				}
			} if (localNetPayableAmtTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"netPayableAmt"));

				if (localNetPayableAmt != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNetPayableAmt));
				} else {
					throw new org.apache.axis2.databinding.ADBException("netPayableAmt cannot be null!!");
				}
			} if (localPrincipalBalTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"principalBal"));

				if (localPrincipalBal != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPrincipalBal));
				} else {
					throw new org.apache.axis2.databinding.ADBException("principalBal cannot be null!!");
				}
			}
			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
					"installmentArrears"));

			if (localInstallmentArrears != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInstallmentArrears));
			} else {
				throw new org.apache.axis2.databinding.ADBException("installmentArrears cannot be null!!");
			}

			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
					"penalInterestDue"));

			if (localPenalInterestDue != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPenalInterestDue));
			} else {
				throw new org.apache.axis2.databinding.ADBException("penalInterestDue cannot be null!!");
			}
			if (localInterestDueTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"interestDue"));

				if (localInterestDue != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInterestDue));
				} else {
					throw new org.apache.axis2.databinding.ADBException("interestDue cannot be null!!");
				}
			} if (localNoOfDefsTotalTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"noOfDefsTotal"));

				if (localNoOfDefsTotal != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNoOfDefsTotal));
				} else {
					throw new org.apache.axis2.databinding.ADBException("noOfDefsTotal cannot be null!!");
				}
			} if (localNoOfDefsCurrentYearTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"noOfDefsCurrentYear"));

				if (localNoOfDefsCurrentYear != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNoOfDefsCurrentYear));
				} else {
					throw new org.apache.axis2.databinding.ADBException("noOfDefsCurrentYear cannot be null!!");
				}
			} if (localLastDefDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"lastDefDate"));

				if (localLastDefDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLastDefDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("lastDefDate cannot be null!!");
				}
			} if (localInterestPaidLastEmiTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"interestPaidLastEmi"));

				if (localInterestPaidLastEmi != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInterestPaidLastEmi));
				} else {
					throw new org.apache.axis2.databinding.ADBException("interestPaidLastEmi cannot be null!!");
				}
			} if (localInterestToBePaidTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"interestToBePaid"));

				if (localInterestToBePaid != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInterestToBePaid));
				} else {
					throw new org.apache.axis2.databinding.ADBException("interestToBePaid cannot be null!!");
				}
			} if (localPenalltyAmountTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"penalltyAmount"));

				if (localPenalltyAmount != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPenalltyAmount));
				} else {
					throw new org.apache.axis2.databinding.ADBException("penalltyAmount cannot be null!!");
				}
			} if (localRateFlagTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"rateFlag"));

				if (localRateFlag != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRateFlag));
				} else {
					throw new org.apache.axis2.databinding.ADBException("rateFlag cannot be null!!");
				}
			} if (localCustShortNameTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"custShortName"));

				if (localCustShortName != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustShortName));
				} else {
					throw new org.apache.axis2.databinding.ADBException("custShortName cannot be null!!");
				}
			} if (localUnclearAmountTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"unclearAmount"));

				if (localUnclearAmount != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUnclearAmount));
				} else {
					throw new org.apache.axis2.databinding.ADBException("unclearAmount cannot be null!!");
				}
			} if (localArrearsChargesAmountTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"arrearsChargesAmount"));

				if (localArrearsChargesAmount != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localArrearsChargesAmount));
				} else {
					throw new org.apache.axis2.databinding.ADBException("arrearsChargesAmount cannot be null!!");
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
			public static BalScheduleDetails_type1 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				BalScheduleDetails_type1 object =
						new BalScheduleDetails_type1();

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

							if (!"balScheduleDetails_type1".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (BalScheduleDetails_type1)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","outstandingBal").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"outstandingBal" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setOutstandingBal(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","interestCharged").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"interestCharged" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setInterestCharged(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","advancePayment").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"advancePayment" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAdvancePayment(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","netPayableAmt").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"netPayableAmt" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNetPayableAmt(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","principalBal").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"principalBal" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPrincipalBal(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","installmentArrears").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"installmentArrears" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setInstallmentArrears(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","penalInterestDue").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"penalInterestDue" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPenalInterestDue(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","interestDue").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"interestDue" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setInterestDue(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","noOfDefsTotal").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"noOfDefsTotal" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNoOfDefsTotal(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","noOfDefsCurrentYear").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"noOfDefsCurrentYear" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNoOfDefsCurrentYear(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","lastDefDate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"lastDefDate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLastDefDate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","interestPaidLastEmi").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"interestPaidLastEmi" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setInterestPaidLastEmi(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","interestToBePaid").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"interestToBePaid" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setInterestToBePaid(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","penalltyAmount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"penalltyAmount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPenalltyAmount(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","rateFlag").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"rateFlag" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setRateFlag(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","custShortName").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"custShortName" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCustShortName(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","unclearAmount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"unclearAmount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setUnclearAmount(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","arrearsChargesAmount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"arrearsChargesAmount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setArrearsChargesAmount(
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


	public static class Loan_Operative_Info
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
				"Loan_Operative_Info",
				"ns2");



		/**
		 * field for BalScheduleDetails
		 */


		protected BalScheduleDetails_type0 localBalScheduleDetails ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localBalScheduleDetailsTracker = false ;

		public boolean isBalScheduleDetailsSpecified(){
			return localBalScheduleDetailsTracker;
		}



		/**
		 * Auto generated getter method
		 * @return BalScheduleDetails_type0
		 */
		public  BalScheduleDetails_type0 getBalScheduleDetails(){
			return localBalScheduleDetails;
		}



		/**
		 * Auto generated setter method
		 * @param param BalScheduleDetails
		 */
		public void setBalScheduleDetails(BalScheduleDetails_type0 param){
			localBalScheduleDetailsTracker = param != null;

			this.localBalScheduleDetails=param;


		}


		/**
		 * field for PDC
		 */


		protected PDC_type0 localPDC ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPDCTracker = false ;

		public boolean isPDCSpecified(){
			return localPDCTracker;
		}



		/**
		 * Auto generated getter method
		 * @return PDC_type0
		 */
		 public  PDC_type0 getPDC(){
			 return localPDC;
		 }



		 /**
		  * Auto generated setter method
		  * @param param PDC
		  */
		 public void setPDC(PDC_type0 param){
			 localPDCTracker = param != null;

			 this.localPDC=param;


		 }


		 /**
		  * field for StandingInst
		  * This was an Array!
		  */


		 protected StandingInst_type0[] localStandingInst ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localStandingInstTracker = false ;

		 public boolean isStandingInstSpecified(){
			 return localStandingInstTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return StandingInst_type0[]
		  */
		 public  StandingInst_type0[] getStandingInst(){
			 return localStandingInst;
		 }






		 /**
		  * validate the array for StandingInst
		  */
		 protected void validateStandingInst(StandingInst_type0[] param){

		 }


		 /**
		  * Auto generated setter method
		  * @param param StandingInst
		  */
		 public void setStandingInst(StandingInst_type0[] param){

			 validateStandingInst(param);

			 localStandingInstTracker = param != null;

			 this.localStandingInst=param;
		 }



		 /**
		  * Auto generated add method for the array for convenience
		  * @param param StandingInst_type0
		  */
		 public void addStandingInst(StandingInst_type0 param){
			 if (localStandingInst == null){
				 localStandingInst = new StandingInst_type0[]{};
			 }


			 //update the setting tracker
			 localStandingInstTracker = true;


			 java.util.List list =
					 org.apache.axis2.databinding.utils.ConverterUtil.toList(localStandingInst);
			 list.add(param);
			 this.localStandingInst =
					 (StandingInst_type0[])list.toArray(
							 new StandingInst_type0[list.size()]);

		 }


		 /**
		  * field for Limits
		  * This was an Array!
		  */


		 protected Limits_type0[] localLimits ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localLimitsTracker = false ;

		 public boolean isLimitsSpecified(){
			 return localLimitsTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return Limits_type0[]
		  */
		 public  Limits_type0[] getLimits(){
			 return localLimits;
		 }






		 /**
		  * validate the array for Limits
		  */
		 protected void validateLimits(Limits_type0[] param){

		 }


		 /**
		  * Auto generated setter method
		  * @param param Limits
		  */
		 public void setLimits(Limits_type0[] param){

			 validateLimits(param);

			 localLimitsTracker = param != null;

			 this.localLimits=param;
		 }



		 /**
		  * Auto generated add method for the array for convenience
		  * @param param Limits_type0
		  */
		 public void addLimits(Limits_type0 param){
			 if (localLimits == null){
				 localLimits = new Limits_type0[]{};
			 }


			 //update the setting tracker
			 localLimitsTracker = true;


			 java.util.List list =
					 org.apache.axis2.databinding.utils.ConverterUtil.toList(localLimits);
			 list.add(param);
			 this.localLimits =
					 (Limits_type0[])list.toArray(
							 new Limits_type0[list.size()]);

		 }


		 /**
		  * field for LinkedCustomer
		  * This was an Array!
		  */


		 protected LinkedCustomer_type0[] localLinkedCustomer ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localLinkedCustomerTracker = false ;

		 public boolean isLinkedCustomerSpecified(){
			 return localLinkedCustomerTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return LinkedCustomer_type0[]
		  */
		 public  LinkedCustomer_type0[] getLinkedCustomer(){
			 return localLinkedCustomer;
		 }






		 /**
		  * validate the array for LinkedCustomer
		  */
		 protected void validateLinkedCustomer(LinkedCustomer_type0[] param){

		 }


		 /**
		  * Auto generated setter method
		  * @param param LinkedCustomer
		  */
		 public void setLinkedCustomer(LinkedCustomer_type0[] param){

			 validateLinkedCustomer(param);

			 localLinkedCustomerTracker = param != null;

			 this.localLinkedCustomer=param;
		 }



		 /**
		  * Auto generated add method for the array for convenience
		  * @param param LinkedCustomer_type0
		  */
		 public void addLinkedCustomer(LinkedCustomer_type0 param){
			 if (localLinkedCustomer == null){
				 localLinkedCustomer = new LinkedCustomer_type0[]{};
			 }


			 //update the setting tracker
			 localLinkedCustomerTracker = true;


			 java.util.List list =
					 org.apache.axis2.databinding.utils.ConverterUtil.toList(localLinkedCustomer);
			 list.add(param);
			 this.localLinkedCustomer =
					 (LinkedCustomer_type0[])list.toArray(
							 new LinkedCustomer_type0[list.size()]);

		 }


		 /**
		  * field for Delinquency
		  */


		 protected Delinquency_type0 localDelinquency ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localDelinquencyTracker = false ;

		 public boolean isDelinquencySpecified(){
			 return localDelinquencyTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return Delinquency_type0
		  */
		 public  Delinquency_type0 getDelinquency(){
			 return localDelinquency;
		 }



		 /**
		  * Auto generated setter method
		  * @param param Delinquency
		  */
		 public void setDelinquency(Delinquency_type0 param){
			 localDelinquencyTracker = param != null;

			 this.localDelinquency=param;


		 }


		 /**
		  * field for DealerCode
		  */


		 protected java.lang.String localDealerCode ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localDealerCodeTracker = false ;

		 public boolean isDealerCodeSpecified(){
			 return localDealerCodeTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return java.lang.String
		  */
		 public  java.lang.String getDealerCode(){
			 return localDealerCode;
		 }



		 /**
		  * Auto generated setter method
		  * @param param DealerCode
		  */
		 public void setDealerCode(java.lang.String param){
			 localDealerCodeTracker = param != null;

			 this.localDealerCode=param;


		 }


		 /**
		  * field for SourceBy
		  */


		 protected java.lang.String localSourceBy ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localSourceByTracker = false ;

		 public boolean isSourceBySpecified(){
			 return localSourceByTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return java.lang.String
		  */
		 public  java.lang.String getSourceBy(){
			 return localSourceBy;
		 }



		 /**
		  * Auto generated setter method
		  * @param param SourceBy
		  */
		 public void setSourceBy(java.lang.String param){
			 localSourceByTracker = param != null;

			 this.localSourceBy=param;


		 }


		 /**
		  * field for SourceCode
		  */


		 protected java.lang.String localSourceCode ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localSourceCodeTracker = false ;

		 public boolean isSourceCodeSpecified(){
			 return localSourceCodeTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return java.lang.String
		  */
		 public  java.lang.String getSourceCode(){
			 return localSourceCode;
		 }



		 /**
		  * Auto generated setter method
		  * @param param SourceCode
		  */
		 public void setSourceCode(java.lang.String param){
			 localSourceCodeTracker = param != null;

			 this.localSourceCode=param;


		 }


		 /**
		  * field for PrincipalOverDue
		  */


		 protected java.lang.String localPrincipalOverDue ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localPrincipalOverDueTracker = false ;

		 public boolean isPrincipalOverDueSpecified(){
			 return localPrincipalOverDueTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return java.lang.String
		  */
		 public  java.lang.String getPrincipalOverDue(){
			 return localPrincipalOverDue;
		 }



		 /**
		  * Auto generated setter method
		  * @param param PrincipalOverDue
		  */
		 public void setPrincipalOverDue(java.lang.String param){
			 localPrincipalOverDueTracker = param != null;

			 this.localPrincipalOverDue=param;


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
		  * field for LoanAccountNumber
		  */


		 protected java.lang.String localLoanAccountNumber ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localLoanAccountNumberTracker = false ;

		 public boolean isLoanAccountNumberSpecified(){
			 return localLoanAccountNumberTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return java.lang.String
		  */
		 public  java.lang.String getLoanAccountNumber(){
			 return localLoanAccountNumber;
		 }



		 /**
		  * Auto generated setter method
		  * @param param LoanAccountNumber
		  */
		 public void setLoanAccountNumber(java.lang.String param){
			 localLoanAccountNumberTracker = param != null;

			 this.localLoanAccountNumber=param;


		 }


		 /**
		  * field for ValueDate
		  */


		 protected java.lang.String localValueDate ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localValueDateTracker = false ;

		 public boolean isValueDateSpecified(){
			 return localValueDateTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return java.lang.String
		  */
		 public  java.lang.String getValueDate(){
			 return localValueDate;
		 }



		 /**
		  * Auto generated setter method
		  * @param param ValueDate
		  */
		 public void setValueDate(java.lang.String param){
			 localValueDateTracker = param != null;

			 this.localValueDate=param;


		 }


		 /**
		  * field for PenalIntOverDue
		  */


		 protected java.lang.String localPenalIntOverDue ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localPenalIntOverDueTracker = false ;

		 public boolean isPenalIntOverDueSpecified(){
			 return localPenalIntOverDueTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return java.lang.String
		  */
		 public  java.lang.String getPenalIntOverDue(){
			 return localPenalIntOverDue;
		 }



		 /**
		  * Auto generated setter method
		  * @param param PenalIntOverDue
		  */
		 public void setPenalIntOverDue(java.lang.String param){
			 localPenalIntOverDueTracker = param != null;

			 this.localPenalIntOverDue=param;


		 }


		 /**
		  * field for FutureIntAccural
		  */


		 protected java.lang.String localFutureIntAccural ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localFutureIntAccuralTracker = false ;

		 public boolean isFutureIntAccuralSpecified(){
			 return localFutureIntAccuralTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return java.lang.String
		  */
		 public  java.lang.String getFutureIntAccural(){
			 return localFutureIntAccural;
		 }



		 /**
		  * Auto generated setter method
		  * @param param FutureIntAccural
		  */
		 public void setFutureIntAccural(java.lang.String param){
			 localFutureIntAccuralTracker = param != null;

			 this.localFutureIntAccural=param;


		 }


		 /**
		  * field for FuturePenalInt
		  */


		 protected java.lang.String localFuturePenalInt ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localFuturePenalIntTracker = false ;

		 public boolean isFuturePenalIntSpecified(){
			 return localFuturePenalIntTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return java.lang.String
		  */
		 public  java.lang.String getFuturePenalInt(){
			 return localFuturePenalInt;
		 }



		 /**
		  * Auto generated setter method
		  * @param param FuturePenalInt
		  */
		 public void setFuturePenalInt(java.lang.String param){
			 localFuturePenalIntTracker = param != null;

			 this.localFuturePenalInt=param;


		 }


		 /**
		  * field for EsfPercentage
		  */


		 protected java.lang.String localEsfPercentage ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localEsfPercentageTracker = false ;

		 public boolean isEsfPercentageSpecified(){
			 return localEsfPercentageTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return java.lang.String
		  */
		 public  java.lang.String getEsfPercentage(){
			 return localEsfPercentage;
		 }



		 /**
		  * Auto generated setter method
		  * @param param EsfPercentage
		  */
		 public void setEsfPercentage(java.lang.String param){
			 localEsfPercentageTracker = param != null;

			 this.localEsfPercentage=param;


		 }


		 /**
		  * field for EsfAmount
		  */


		 protected java.lang.String localEsfAmount ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localEsfAmountTracker = false ;

		 public boolean isEsfAmountSpecified(){
			 return localEsfAmountTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return java.lang.String
		  */
		 public  java.lang.String getEsfAmount(){
			 return localEsfAmount;
		 }



		 /**
		  * Auto generated setter method
		  * @param param EsfAmount
		  */
		 public void setEsfAmount(java.lang.String param){
			 localEsfAmountTracker = param != null;

			 this.localEsfAmount=param;


		 }


		 /**
		  * field for RpaBalance
		  */


		 protected java.lang.String localRpaBalance ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localRpaBalanceTracker = false ;

		 public boolean isRpaBalanceSpecified(){
			 return localRpaBalanceTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return java.lang.String
		  */
		 public  java.lang.String getRpaBalance(){
			 return localRpaBalance;
		 }



		 /**
		  * Auto generated setter method
		  * @param param RpaBalance
		  */
		 public void setRpaBalance(java.lang.String param){
			 localRpaBalanceTracker = param != null;

			 this.localRpaBalance=param;


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


				 java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd");
				 if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					 writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							 namespacePrefix+":Loan_Operative_Info",
							 xmlWriter);
				 } else {
					 writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							 "Loan_Operative_Info",
							 xmlWriter);
				 }


			 }
			 if (localBalScheduleDetailsTracker){
				 if (localBalScheduleDetails==null){
					 throw new org.apache.axis2.databinding.ADBException("balScheduleDetails cannot be null!!");
				 }
				 localBalScheduleDetails.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","balScheduleDetails"),
						 xmlWriter);
			 } if (localPDCTracker){
				 if (localPDC==null){
					 throw new org.apache.axis2.databinding.ADBException("PDC cannot be null!!");
				 }
				 localPDC.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","PDC"),
						 xmlWriter);
			 } if (localStandingInstTracker){
				 if (localStandingInst!=null){
					 for (int i = 0;i < localStandingInst.length;i++){
						 if (localStandingInst[i] != null){
							 localStandingInst[i].serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","standingInst"),
									 xmlWriter);
						 } else {

							 // we don't have to do any thing since minOccures is zero

						 }

					 }
				 } else {

					 throw new org.apache.axis2.databinding.ADBException("standingInst cannot be null!!");

				 }
			 } if (localLimitsTracker){
				 if (localLimits!=null){
					 for (int i = 0;i < localLimits.length;i++){
						 if (localLimits[i] != null){
							 localLimits[i].serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","limits"),
									 xmlWriter);
						 } else {

							 // we don't have to do any thing since minOccures is zero

						 }

					 }
				 } else {

					 throw new org.apache.axis2.databinding.ADBException("limits cannot be null!!");

				 }
			 } if (localLinkedCustomerTracker){
				 if (localLinkedCustomer!=null){
					 for (int i = 0;i < localLinkedCustomer.length;i++){
						 if (localLinkedCustomer[i] != null){
							 localLinkedCustomer[i].serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","linkedCustomer"),
									 xmlWriter);
						 } else {

							 // we don't have to do any thing since minOccures is zero

						 }

					 }
				 } else {

					 throw new org.apache.axis2.databinding.ADBException("linkedCustomer cannot be null!!");

				 }
			 } if (localDelinquencyTracker){
				 if (localDelinquency==null){
					 throw new org.apache.axis2.databinding.ADBException("delinquency cannot be null!!");
				 }
				 localDelinquency.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","delinquency"),
						 xmlWriter);
			 } if (localDealerCodeTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "dealerCode", xmlWriter);


				 if (localDealerCode==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("dealerCode cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localDealerCode);

				 }

				 xmlWriter.writeEndElement();
			 } if (localSourceByTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "sourceBy", xmlWriter);


				 if (localSourceBy==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("sourceBy cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localSourceBy);

				 }

				 xmlWriter.writeEndElement();
			 } if (localSourceCodeTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "sourceCode", xmlWriter);


				 if (localSourceCode==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("sourceCode cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localSourceCode);

				 }

				 xmlWriter.writeEndElement();
			 } if (localPrincipalOverDueTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "principalOverDue", xmlWriter);


				 if (localPrincipalOverDue==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("principalOverDue cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localPrincipalOverDue);

				 }

				 xmlWriter.writeEndElement();
			 } if (localCustomerIdTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "customerId", xmlWriter);


				 if (localCustomerId==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localCustomerId);

				 }

				 xmlWriter.writeEndElement();
			 } if (localLoanAccountNumberTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "loanAccountNumber", xmlWriter);


				 if (localLoanAccountNumber==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("loanAccountNumber cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localLoanAccountNumber);

				 }

				 xmlWriter.writeEndElement();
			 } if (localValueDateTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "valueDate", xmlWriter);


				 if (localValueDate==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("valueDate cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localValueDate);

				 }

				 xmlWriter.writeEndElement();
			 } if (localPenalIntOverDueTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "penalIntOverDue", xmlWriter);


				 if (localPenalIntOverDue==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("penalIntOverDue cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localPenalIntOverDue);

				 }

				 xmlWriter.writeEndElement();
			 } if (localFutureIntAccuralTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "futureIntAccural", xmlWriter);


				 if (localFutureIntAccural==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("futureIntAccural cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localFutureIntAccural);

				 }

				 xmlWriter.writeEndElement();
			 } if (localFuturePenalIntTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "futurePenalInt", xmlWriter);


				 if (localFuturePenalInt==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("futurePenalInt cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localFuturePenalInt);

				 }

				 xmlWriter.writeEndElement();
			 } if (localEsfPercentageTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "esfPercentage", xmlWriter);


				 if (localEsfPercentage==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("esfPercentage cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localEsfPercentage);

				 }

				 xmlWriter.writeEndElement();
			 } if (localEsfAmountTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "esfAmount", xmlWriter);


				 if (localEsfAmount==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("esfAmount cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localEsfAmount);

				 }

				 xmlWriter.writeEndElement();
			 } if (localRpaBalanceTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "rpaBalance", xmlWriter);


				 if (localRpaBalance==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("rpaBalance cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localRpaBalance);

				 }

				 xmlWriter.writeEndElement();
			 }
			 xmlWriter.writeEndElement();


		 }

		 private static java.lang.String generatePrefix(java.lang.String namespace) {
			 if(namespace.equals("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd")){
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

			 if (localBalScheduleDetailsTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "balScheduleDetails"));


				 if (localBalScheduleDetails==null){
					 throw new org.apache.axis2.databinding.ADBException("balScheduleDetails cannot be null!!");
				 }
				 elementList.add(localBalScheduleDetails);
			 } if (localPDCTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "PDC"));


				 if (localPDC==null){
					 throw new org.apache.axis2.databinding.ADBException("PDC cannot be null!!");
				 }
				 elementList.add(localPDC);
			 } if (localStandingInstTracker){
				 if (localStandingInst!=null) {
					 for (int i = 0;i < localStandingInst.length;i++){

						 if (localStandingInst[i] != null){
							 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
									 "standingInst"));
							 elementList.add(localStandingInst[i]);
						 } else {

							 // nothing to do

						 }

					 }
				 } else {

					 throw new org.apache.axis2.databinding.ADBException("standingInst cannot be null!!");

				 }

			 } if (localLimitsTracker){
				 if (localLimits!=null) {
					 for (int i = 0;i < localLimits.length;i++){

						 if (localLimits[i] != null){
							 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
									 "limits"));
							 elementList.add(localLimits[i]);
						 } else {

							 // nothing to do

						 }

					 }
				 } else {

					 throw new org.apache.axis2.databinding.ADBException("limits cannot be null!!");

				 }

			 } if (localLinkedCustomerTracker){
				 if (localLinkedCustomer!=null) {
					 for (int i = 0;i < localLinkedCustomer.length;i++){

						 if (localLinkedCustomer[i] != null){
							 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
									 "linkedCustomer"));
							 elementList.add(localLinkedCustomer[i]);
						 } else {

							 // nothing to do

						 }

					 }
				 } else {

					 throw new org.apache.axis2.databinding.ADBException("linkedCustomer cannot be null!!");

				 }

			 } if (localDelinquencyTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "delinquency"));


				 if (localDelinquency==null){
					 throw new org.apache.axis2.databinding.ADBException("delinquency cannot be null!!");
				 }
				 elementList.add(localDelinquency);
			 } if (localDealerCodeTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "dealerCode"));

				 if (localDealerCode != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDealerCode));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("dealerCode cannot be null!!");
				 }
			 } if (localSourceByTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "sourceBy"));

				 if (localSourceBy != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSourceBy));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("sourceBy cannot be null!!");
				 }
			 } if (localSourceCodeTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "sourceCode"));

				 if (localSourceCode != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSourceCode));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("sourceCode cannot be null!!");
				 }
			 } if (localPrincipalOverDueTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "principalOverDue"));

				 if (localPrincipalOverDue != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPrincipalOverDue));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("principalOverDue cannot be null!!");
				 }
			 } if (localCustomerIdTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "customerId"));

				 if (localCustomerId != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerId));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");
				 }
			 } if (localLoanAccountNumberTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "loanAccountNumber"));

				 if (localLoanAccountNumber != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanAccountNumber));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("loanAccountNumber cannot be null!!");
				 }
			 } if (localValueDateTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "valueDate"));

				 if (localValueDate != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localValueDate));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("valueDate cannot be null!!");
				 }
			 } if (localPenalIntOverDueTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "penalIntOverDue"));

				 if (localPenalIntOverDue != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPenalIntOverDue));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("penalIntOverDue cannot be null!!");
				 }
			 } if (localFutureIntAccuralTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "futureIntAccural"));

				 if (localFutureIntAccural != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFutureIntAccural));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("futureIntAccural cannot be null!!");
				 }
			 } if (localFuturePenalIntTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "futurePenalInt"));

				 if (localFuturePenalInt != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFuturePenalInt));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("futurePenalInt cannot be null!!");
				 }
			 } if (localEsfPercentageTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "esfPercentage"));

				 if (localEsfPercentage != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEsfPercentage));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("esfPercentage cannot be null!!");
				 }
			 } if (localEsfAmountTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "esfAmount"));

				 if (localEsfAmount != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEsfAmount));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("esfAmount cannot be null!!");
				 }
			 } if (localRpaBalanceTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "rpaBalance"));

				 if (localRpaBalance != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRpaBalance));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("rpaBalance cannot be null!!");
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
			 public static Loan_Operative_Info parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				 Loan_Operative_Info object =
						 new Loan_Operative_Info();

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

							 if (!"Loan_Operative_Info".equals(type)){
								 //find namespace for the prefix
								 java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								 return (Loan_Operative_Info)ExtensionMapper.getTypeObject(
										 nsUri,type,reader);
							 }


						 }


					 }




					 // Note all attributes that were handled. Used to differ normal attributes
					 // from anyAttributes.
					 java.util.Vector handledAttributes = new java.util.Vector();




					 reader.next();

					 java.util.ArrayList list3 = new java.util.ArrayList();

					 java.util.ArrayList list4 = new java.util.ArrayList();

					 java.util.ArrayList list5 = new java.util.ArrayList();


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","balScheduleDetails").equals(reader.getName())){

						 object.setBalScheduleDetails(BalScheduleDetails_type0.Factory.parse(reader));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","PDC").equals(reader.getName())){

						 object.setPDC(PDC_type0.Factory.parse(reader));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","standingInst").equals(reader.getName())){



						 // Process the array and step past its final element's end.
						 list3.add(StandingInst_type0.Factory.parse(reader));

						 //loop until we find a start element that is not part of this array
						 boolean loopDone3 = false;
						 while(!loopDone3){
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
								 loopDone3 = true;
							 } else {
								 if (new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","standingInst").equals(reader.getName())){
									 list3.add(StandingInst_type0.Factory.parse(reader));

								 }else{
									 loopDone3 = true;
								 }
							 }
						 }
						 // call the converter utility  to convert and set the array

						 object.setStandingInst((StandingInst_type0[])
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
										 StandingInst_type0.class,
										 list3));

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","limits").equals(reader.getName())){



						 // Process the array and step past its final element's end.
						 list4.add(Limits_type0.Factory.parse(reader));

						 //loop until we find a start element that is not part of this array
						 boolean loopDone4 = false;
						 while(!loopDone4){
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
								 loopDone4 = true;
							 } else {
								 if (new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","limits").equals(reader.getName())){
									 list4.add(Limits_type0.Factory.parse(reader));

								 }else{
									 loopDone4 = true;
								 }
							 }
						 }
						 // call the converter utility  to convert and set the array

						 object.setLimits((Limits_type0[])
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
										 Limits_type0.class,
										 list4));

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","linkedCustomer").equals(reader.getName())){



						 // Process the array and step past its final element's end.
						 list5.add(LinkedCustomer_type0.Factory.parse(reader));

						 //loop until we find a start element that is not part of this array
						 boolean loopDone5 = false;
						 while(!loopDone5){
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
								 loopDone5 = true;
							 } else {
								 if (new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","linkedCustomer").equals(reader.getName())){
									 list5.add(LinkedCustomer_type0.Factory.parse(reader));

								 }else{
									 loopDone5 = true;
								 }
							 }
						 }
						 // call the converter utility  to convert and set the array

						 object.setLinkedCustomer((LinkedCustomer_type0[])
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
										 LinkedCustomer_type0.class,
										 list5));

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","delinquency").equals(reader.getName())){

						 object.setDelinquency(Delinquency_type0.Factory.parse(reader));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","dealerCode").equals(reader.getName())){

						 nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						 if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							 throw new org.apache.axis2.databinding.ADBException("The element: "+"dealerCode" +"  cannot be null");
						 }


						 java.lang.String content = reader.getElementText();

						 object.setDealerCode(
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","sourceBy").equals(reader.getName())){

						 nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						 if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							 throw new org.apache.axis2.databinding.ADBException("The element: "+"sourceBy" +"  cannot be null");
						 }


						 java.lang.String content = reader.getElementText();

						 object.setSourceBy(
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","sourceCode").equals(reader.getName())){

						 nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						 if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							 throw new org.apache.axis2.databinding.ADBException("The element: "+"sourceCode" +"  cannot be null");
						 }


						 java.lang.String content = reader.getElementText();

						 object.setSourceCode(
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","principalOverDue").equals(reader.getName())){

						 nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						 if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							 throw new org.apache.axis2.databinding.ADBException("The element: "+"principalOverDue" +"  cannot be null");
						 }


						 java.lang.String content = reader.getElementText();

						 object.setPrincipalOverDue(
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","customerId").equals(reader.getName())){

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

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","loanAccountNumber").equals(reader.getName())){

						 nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						 if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							 throw new org.apache.axis2.databinding.ADBException("The element: "+"loanAccountNumber" +"  cannot be null");
						 }


						 java.lang.String content = reader.getElementText();

						 object.setLoanAccountNumber(
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","valueDate").equals(reader.getName())){

						 nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						 if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							 throw new org.apache.axis2.databinding.ADBException("The element: "+"valueDate" +"  cannot be null");
						 }


						 java.lang.String content = reader.getElementText();

						 object.setValueDate(
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","penalIntOverDue").equals(reader.getName())){

						 nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						 if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							 throw new org.apache.axis2.databinding.ADBException("The element: "+"penalIntOverDue" +"  cannot be null");
						 }


						 java.lang.String content = reader.getElementText();

						 object.setPenalIntOverDue(
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","futureIntAccural").equals(reader.getName())){

						 nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						 if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							 throw new org.apache.axis2.databinding.ADBException("The element: "+"futureIntAccural" +"  cannot be null");
						 }


						 java.lang.String content = reader.getElementText();

						 object.setFutureIntAccural(
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","futurePenalInt").equals(reader.getName())){

						 nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						 if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							 throw new org.apache.axis2.databinding.ADBException("The element: "+"futurePenalInt" +"  cannot be null");
						 }


						 java.lang.String content = reader.getElementText();

						 object.setFuturePenalInt(
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","esfPercentage").equals(reader.getName())){

						 nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						 if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							 throw new org.apache.axis2.databinding.ADBException("The element: "+"esfPercentage" +"  cannot be null");
						 }


						 java.lang.String content = reader.getElementText();

						 object.setEsfPercentage(
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","esfAmount").equals(reader.getName())){

						 nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						 if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							 throw new org.apache.axis2.databinding.ADBException("The element: "+"esfAmount" +"  cannot be null");
						 }


						 java.lang.String content = reader.getElementText();

						 object.setEsfAmount(
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","rpaBalance").equals(reader.getName())){

						 nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						 if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							 throw new org.apache.axis2.databinding.ADBException("The element: "+"rpaBalance" +"  cannot be null");
						 }


						 java.lang.String content = reader.getElementText();

						 object.setRpaBalance(
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


	public static class BalScheduleDetails_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = balScheduleDetails_type0
                Namespace URI = http://www.adcb.com/esb/common/LoanOperativeInfo.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for OutstandingBal
		 */


		protected java.lang.String localOutstandingBal ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localOutstandingBalTracker = false ;

		public boolean isOutstandingBalSpecified(){
			return localOutstandingBalTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getOutstandingBal(){
			return localOutstandingBal;
		}



		/**
		 * Auto generated setter method
		 * @param param OutstandingBal
		 */
		public void setOutstandingBal(java.lang.String param){
			localOutstandingBalTracker = param != null;

			this.localOutstandingBal=param;


		}


		/**
		 * field for InterestCharged
		 */


		protected java.lang.String localInterestCharged ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localInterestChargedTracker = false ;

		public boolean isInterestChargedSpecified(){
			return localInterestChargedTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getInterestCharged(){
			return localInterestCharged;
		}



		/**
		 * Auto generated setter method
		 * @param param InterestCharged
		 */
		public void setInterestCharged(java.lang.String param){
			localInterestChargedTracker = param != null;

			this.localInterestCharged=param;


		}


		/**
		 * field for AdvancePayment
		 */


		protected java.lang.String localAdvancePayment ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAdvancePaymentTracker = false ;

		public boolean isAdvancePaymentSpecified(){
			return localAdvancePaymentTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getAdvancePayment(){
			return localAdvancePayment;
		}



		/**
		 * Auto generated setter method
		 * @param param AdvancePayment
		 */
		public void setAdvancePayment(java.lang.String param){
			localAdvancePaymentTracker = param != null;

			this.localAdvancePayment=param;


		}


		/**
		 * field for NetPayableAmt
		 */


		protected java.lang.String localNetPayableAmt ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNetPayableAmtTracker = false ;

		public boolean isNetPayableAmtSpecified(){
			return localNetPayableAmtTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNetPayableAmt(){
			return localNetPayableAmt;
		}



		/**
		 * Auto generated setter method
		 * @param param NetPayableAmt
		 */
		public void setNetPayableAmt(java.lang.String param){
			localNetPayableAmtTracker = param != null;

			this.localNetPayableAmt=param;


		}


		/**
		 * field for PrincipalBal
		 */


		protected java.lang.String localPrincipalBal ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPrincipalBalTracker = false ;

		public boolean isPrincipalBalSpecified(){
			return localPrincipalBalTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPrincipalBal(){
			return localPrincipalBal;
		}



		/**
		 * Auto generated setter method
		 * @param param PrincipalBal
		 */
		public void setPrincipalBal(java.lang.String param){
			localPrincipalBalTracker = param != null;

			this.localPrincipalBal=param;


		}


		/**
		 * field for InstallmentArrears
		 */


		protected java.lang.String localInstallmentArrears ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getInstallmentArrears(){
			return localInstallmentArrears;
		}



		/**
		 * Auto generated setter method
		 * @param param InstallmentArrears
		 */
		public void setInstallmentArrears(java.lang.String param){

			this.localInstallmentArrears=param;


		}


		/**
		 * field for PenalInterestDue
		 */


		protected java.lang.String localPenalInterestDue ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPenalInterestDue(){
			return localPenalInterestDue;
		}



		/**
		 * Auto generated setter method
		 * @param param PenalInterestDue
		 */
		public void setPenalInterestDue(java.lang.String param){

			this.localPenalInterestDue=param;


		}


		/**
		 * field for InterestDue
		 */


		protected java.lang.String localInterestDue ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localInterestDueTracker = false ;

		public boolean isInterestDueSpecified(){
			return localInterestDueTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getInterestDue(){
			return localInterestDue;
		}



		/**
		 * Auto generated setter method
		 * @param param InterestDue
		 */
		public void setInterestDue(java.lang.String param){
			localInterestDueTracker = param != null;

			this.localInterestDue=param;


		}


		/**
		 * field for NoOfDefsTotal
		 */


		protected java.lang.String localNoOfDefsTotal ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNoOfDefsTotalTracker = false ;

		public boolean isNoOfDefsTotalSpecified(){
			return localNoOfDefsTotalTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNoOfDefsTotal(){
			return localNoOfDefsTotal;
		}



		/**
		 * Auto generated setter method
		 * @param param NoOfDefsTotal
		 */
		public void setNoOfDefsTotal(java.lang.String param){
			localNoOfDefsTotalTracker = param != null;

			this.localNoOfDefsTotal=param;


		}


		/**
		 * field for NoOfDefsCurrentYear
		 */


		protected java.lang.String localNoOfDefsCurrentYear ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNoOfDefsCurrentYearTracker = false ;

		public boolean isNoOfDefsCurrentYearSpecified(){
			return localNoOfDefsCurrentYearTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNoOfDefsCurrentYear(){
			return localNoOfDefsCurrentYear;
		}



		/**
		 * Auto generated setter method
		 * @param param NoOfDefsCurrentYear
		 */
		public void setNoOfDefsCurrentYear(java.lang.String param){
			localNoOfDefsCurrentYearTracker = param != null;

			this.localNoOfDefsCurrentYear=param;


		}


		/**
		 * field for LastDefDate
		 */


		protected java.lang.String localLastDefDate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLastDefDateTracker = false ;

		public boolean isLastDefDateSpecified(){
			return localLastDefDateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLastDefDate(){
			return localLastDefDate;
		}



		/**
		 * Auto generated setter method
		 * @param param LastDefDate
		 */
		public void setLastDefDate(java.lang.String param){
			localLastDefDateTracker = param != null;

			this.localLastDefDate=param;


		}


		/**
		 * field for InterestPaidLastEmi
		 */


		protected java.lang.String localInterestPaidLastEmi ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localInterestPaidLastEmiTracker = false ;

		public boolean isInterestPaidLastEmiSpecified(){
			return localInterestPaidLastEmiTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getInterestPaidLastEmi(){
			return localInterestPaidLastEmi;
		}



		/**
		 * Auto generated setter method
		 * @param param InterestPaidLastEmi
		 */
		public void setInterestPaidLastEmi(java.lang.String param){
			localInterestPaidLastEmiTracker = param != null;

			this.localInterestPaidLastEmi=param;


		}


		/**
		 * field for InterestToBePaid
		 */


		protected java.lang.String localInterestToBePaid ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localInterestToBePaidTracker = false ;

		public boolean isInterestToBePaidSpecified(){
			return localInterestToBePaidTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getInterestToBePaid(){
			return localInterestToBePaid;
		}



		/**
		 * Auto generated setter method
		 * @param param InterestToBePaid
		 */
		public void setInterestToBePaid(java.lang.String param){
			localInterestToBePaidTracker = param != null;

			this.localInterestToBePaid=param;


		}


		/**
		 * field for PenalltyAmount
		 */


		protected java.lang.String localPenalltyAmount ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPenalltyAmountTracker = false ;

		public boolean isPenalltyAmountSpecified(){
			return localPenalltyAmountTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPenalltyAmount(){
			return localPenalltyAmount;
		}



		/**
		 * Auto generated setter method
		 * @param param PenalltyAmount
		 */
		public void setPenalltyAmount(java.lang.String param){
			localPenalltyAmountTracker = param != null;

			this.localPenalltyAmount=param;


		}


		/**
		 * field for RateFlag
		 */


		protected java.lang.String localRateFlag ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localRateFlagTracker = false ;

		public boolean isRateFlagSpecified(){
			return localRateFlagTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getRateFlag(){
			return localRateFlag;
		}



		/**
		 * Auto generated setter method
		 * @param param RateFlag
		 */
		public void setRateFlag(java.lang.String param){
			localRateFlagTracker = param != null;

			this.localRateFlag=param;


		}


		/**
		 * field for CustShortName
		 */


		protected java.lang.String localCustShortName ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCustShortNameTracker = false ;

		public boolean isCustShortNameSpecified(){
			return localCustShortNameTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCustShortName(){
			return localCustShortName;
		}



		/**
		 * Auto generated setter method
		 * @param param CustShortName
		 */
		public void setCustShortName(java.lang.String param){
			localCustShortNameTracker = param != null;

			this.localCustShortName=param;


		}


		/**
		 * field for UnclearAmount
		 */


		protected java.lang.String localUnclearAmount ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localUnclearAmountTracker = false ;

		public boolean isUnclearAmountSpecified(){
			return localUnclearAmountTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getUnclearAmount(){
			return localUnclearAmount;
		}



		/**
		 * Auto generated setter method
		 * @param param UnclearAmount
		 */
		public void setUnclearAmount(java.lang.String param){
			localUnclearAmountTracker = param != null;

			this.localUnclearAmount=param;


		}


		/**
		 * field for ArrearsChargesAmount
		 */


		protected java.lang.String localArrearsChargesAmount ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localArrearsChargesAmountTracker = false ;

		public boolean isArrearsChargesAmountSpecified(){
			return localArrearsChargesAmountTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getArrearsChargesAmount(){
			return localArrearsChargesAmount;
		}



		/**
		 * Auto generated setter method
		 * @param param ArrearsChargesAmount
		 */
		public void setArrearsChargesAmount(java.lang.String param){
			localArrearsChargesAmountTracker = param != null;

			this.localArrearsChargesAmount=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":balScheduleDetails_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"balScheduleDetails_type0",
							xmlWriter);
				}


			}
			if (localOutstandingBalTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "outstandingBal", xmlWriter);


				if (localOutstandingBal==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("outstandingBal cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localOutstandingBal);

				}

				xmlWriter.writeEndElement();
			} if (localInterestChargedTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "interestCharged", xmlWriter);


				if (localInterestCharged==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("interestCharged cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localInterestCharged);

				}

				xmlWriter.writeEndElement();
			} if (localAdvancePaymentTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "advancePayment", xmlWriter);


				if (localAdvancePayment==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("advancePayment cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localAdvancePayment);

				}

				xmlWriter.writeEndElement();
			} if (localNetPayableAmtTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "netPayableAmt", xmlWriter);


				if (localNetPayableAmt==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("netPayableAmt cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNetPayableAmt);

				}

				xmlWriter.writeEndElement();
			} if (localPrincipalBalTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "principalBal", xmlWriter);


				if (localPrincipalBal==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("principalBal cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPrincipalBal);

				}

				xmlWriter.writeEndElement();
			}
			namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
			writeStartElement(null, namespace, "installmentArrears", xmlWriter);


			if (localInstallmentArrears==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("installmentArrears cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localInstallmentArrears);

			}

			xmlWriter.writeEndElement();

			namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
			writeStartElement(null, namespace, "penalInterestDue", xmlWriter);


			if (localPenalInterestDue==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("penalInterestDue cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localPenalInterestDue);

			}

			xmlWriter.writeEndElement();
			if (localInterestDueTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "interestDue", xmlWriter);


				if (localInterestDue==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("interestDue cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localInterestDue);

				}

				xmlWriter.writeEndElement();
			} if (localNoOfDefsTotalTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "noOfDefsTotal", xmlWriter);


				if (localNoOfDefsTotal==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("noOfDefsTotal cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNoOfDefsTotal);

				}

				xmlWriter.writeEndElement();
			} if (localNoOfDefsCurrentYearTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "noOfDefsCurrentYear", xmlWriter);


				if (localNoOfDefsCurrentYear==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("noOfDefsCurrentYear cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNoOfDefsCurrentYear);

				}

				xmlWriter.writeEndElement();
			} if (localLastDefDateTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "lastDefDate", xmlWriter);


				if (localLastDefDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("lastDefDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLastDefDate);

				}

				xmlWriter.writeEndElement();
			} if (localInterestPaidLastEmiTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "interestPaidLastEmi", xmlWriter);


				if (localInterestPaidLastEmi==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("interestPaidLastEmi cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localInterestPaidLastEmi);

				}

				xmlWriter.writeEndElement();
			} if (localInterestToBePaidTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "interestToBePaid", xmlWriter);


				if (localInterestToBePaid==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("interestToBePaid cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localInterestToBePaid);

				}

				xmlWriter.writeEndElement();
			} if (localPenalltyAmountTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "penalltyAmount", xmlWriter);


				if (localPenalltyAmount==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("penalltyAmount cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPenalltyAmount);

				}

				xmlWriter.writeEndElement();
			} if (localRateFlagTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "rateFlag", xmlWriter);


				if (localRateFlag==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("rateFlag cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localRateFlag);

				}

				xmlWriter.writeEndElement();
			} if (localCustShortNameTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "custShortName", xmlWriter);


				if (localCustShortName==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("custShortName cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCustShortName);

				}

				xmlWriter.writeEndElement();
			} if (localUnclearAmountTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "unclearAmount", xmlWriter);


				if (localUnclearAmount==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("unclearAmount cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localUnclearAmount);

				}

				xmlWriter.writeEndElement();
			} if (localArrearsChargesAmountTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "arrearsChargesAmount", xmlWriter);


				if (localArrearsChargesAmount==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("arrearsChargesAmount cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localArrearsChargesAmount);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd")){
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

			if (localOutstandingBalTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"outstandingBal"));

				if (localOutstandingBal != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOutstandingBal));
				} else {
					throw new org.apache.axis2.databinding.ADBException("outstandingBal cannot be null!!");
				}
			} if (localInterestChargedTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"interestCharged"));

				if (localInterestCharged != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInterestCharged));
				} else {
					throw new org.apache.axis2.databinding.ADBException("interestCharged cannot be null!!");
				}
			} if (localAdvancePaymentTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"advancePayment"));

				if (localAdvancePayment != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAdvancePayment));
				} else {
					throw new org.apache.axis2.databinding.ADBException("advancePayment cannot be null!!");
				}
			} if (localNetPayableAmtTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"netPayableAmt"));

				if (localNetPayableAmt != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNetPayableAmt));
				} else {
					throw new org.apache.axis2.databinding.ADBException("netPayableAmt cannot be null!!");
				}
			} if (localPrincipalBalTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"principalBal"));

				if (localPrincipalBal != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPrincipalBal));
				} else {
					throw new org.apache.axis2.databinding.ADBException("principalBal cannot be null!!");
				}
			}
			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
					"installmentArrears"));

			if (localInstallmentArrears != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInstallmentArrears));
			} else {
				throw new org.apache.axis2.databinding.ADBException("installmentArrears cannot be null!!");
			}

			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
					"penalInterestDue"));

			if (localPenalInterestDue != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPenalInterestDue));
			} else {
				throw new org.apache.axis2.databinding.ADBException("penalInterestDue cannot be null!!");
			}
			if (localInterestDueTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"interestDue"));

				if (localInterestDue != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInterestDue));
				} else {
					throw new org.apache.axis2.databinding.ADBException("interestDue cannot be null!!");
				}
			} if (localNoOfDefsTotalTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"noOfDefsTotal"));

				if (localNoOfDefsTotal != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNoOfDefsTotal));
				} else {
					throw new org.apache.axis2.databinding.ADBException("noOfDefsTotal cannot be null!!");
				}
			} if (localNoOfDefsCurrentYearTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"noOfDefsCurrentYear"));

				if (localNoOfDefsCurrentYear != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNoOfDefsCurrentYear));
				} else {
					throw new org.apache.axis2.databinding.ADBException("noOfDefsCurrentYear cannot be null!!");
				}
			} if (localLastDefDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"lastDefDate"));

				if (localLastDefDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLastDefDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("lastDefDate cannot be null!!");
				}
			} if (localInterestPaidLastEmiTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"interestPaidLastEmi"));

				if (localInterestPaidLastEmi != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInterestPaidLastEmi));
				} else {
					throw new org.apache.axis2.databinding.ADBException("interestPaidLastEmi cannot be null!!");
				}
			} if (localInterestToBePaidTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"interestToBePaid"));

				if (localInterestToBePaid != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInterestToBePaid));
				} else {
					throw new org.apache.axis2.databinding.ADBException("interestToBePaid cannot be null!!");
				}
			} if (localPenalltyAmountTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"penalltyAmount"));

				if (localPenalltyAmount != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPenalltyAmount));
				} else {
					throw new org.apache.axis2.databinding.ADBException("penalltyAmount cannot be null!!");
				}
			} if (localRateFlagTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"rateFlag"));

				if (localRateFlag != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRateFlag));
				} else {
					throw new org.apache.axis2.databinding.ADBException("rateFlag cannot be null!!");
				}
			} if (localCustShortNameTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"custShortName"));

				if (localCustShortName != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustShortName));
				} else {
					throw new org.apache.axis2.databinding.ADBException("custShortName cannot be null!!");
				}
			} if (localUnclearAmountTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"unclearAmount"));

				if (localUnclearAmount != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUnclearAmount));
				} else {
					throw new org.apache.axis2.databinding.ADBException("unclearAmount cannot be null!!");
				}
			} if (localArrearsChargesAmountTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"arrearsChargesAmount"));

				if (localArrearsChargesAmount != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localArrearsChargesAmount));
				} else {
					throw new org.apache.axis2.databinding.ADBException("arrearsChargesAmount cannot be null!!");
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
			public static BalScheduleDetails_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				BalScheduleDetails_type0 object =
						new BalScheduleDetails_type0();

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

							if (!"balScheduleDetails_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (BalScheduleDetails_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","outstandingBal").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"outstandingBal" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setOutstandingBal(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","interestCharged").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"interestCharged" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setInterestCharged(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","advancePayment").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"advancePayment" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAdvancePayment(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","netPayableAmt").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"netPayableAmt" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNetPayableAmt(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","principalBal").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"principalBal" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPrincipalBal(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","installmentArrears").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"installmentArrears" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setInstallmentArrears(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","penalInterestDue").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"penalInterestDue" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPenalInterestDue(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","interestDue").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"interestDue" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setInterestDue(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","noOfDefsTotal").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"noOfDefsTotal" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNoOfDefsTotal(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","noOfDefsCurrentYear").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"noOfDefsCurrentYear" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNoOfDefsCurrentYear(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","lastDefDate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"lastDefDate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLastDefDate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","interestPaidLastEmi").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"interestPaidLastEmi" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setInterestPaidLastEmi(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","interestToBePaid").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"interestToBePaid" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setInterestToBePaid(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","penalltyAmount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"penalltyAmount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPenalltyAmount(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","rateFlag").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"rateFlag" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setRateFlag(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","custShortName").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"custShortName" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCustShortName(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","unclearAmount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"unclearAmount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setUnclearAmount(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","arrearsChargesAmount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"arrearsChargesAmount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setArrearsChargesAmount(
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


	public static class GetLoanOperativeInfoRes
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd",
				"GetLoanOperativeInfoRes",
				"ns3");



		/**
		 * field for GetLoanOperativeInfoRes
		 */


		protected GetLoanOperativeInfoRes_type0 localGetLoanOperativeInfoRes ;


		/**
		 * Auto generated getter method
		 * @return GetLoanOperativeInfoRes_type0
		 */
		public  GetLoanOperativeInfoRes_type0 getGetLoanOperativeInfoRes(){
			return localGetLoanOperativeInfoRes;
		}



		/**
		 * Auto generated setter method
		 * @param param GetLoanOperativeInfoRes
		 */
		public void setGetLoanOperativeInfoRes(GetLoanOperativeInfoRes_type0 param){

			this.localGetLoanOperativeInfoRes=param;


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

			if (localGetLoanOperativeInfoRes==null){
				throw new org.apache.axis2.databinding.ADBException("GetLoanOperativeInfoRes cannot be null!");
			}
			localGetLoanOperativeInfoRes.serialize(MY_QNAME,xmlWriter);


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd")){
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




			//We can safely assume an element has only one type associated with it
			return localGetLoanOperativeInfoRes.getPullParser(MY_QNAME);

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
			public static GetLoanOperativeInfoRes parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				GetLoanOperativeInfoRes object =
						new GetLoanOperativeInfoRes();

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

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd","GetLoanOperativeInfoRes").equals(reader.getName())){

								object.setGetLoanOperativeInfoRes(GetLoanOperativeInfoRes_type0.Factory.parse(reader));

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


	public static class LinkedCustomer_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = linkedCustomer_type0
                Namespace URI = http://www.adcb.com/esb/common/LoanOperativeInfo.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for CustomerID
		 */


		protected java.lang.String localCustomerID ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCustomerIDTracker = false ;

		public boolean isCustomerIDSpecified(){
			return localCustomerIDTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCustomerID(){
			return localCustomerID;
		}



		/**
		 * Auto generated setter method
		 * @param param CustomerID
		 */
		public void setCustomerID(java.lang.String param){
			localCustomerIDTracker = param != null;

			this.localCustomerID=param;


		}


		/**
		 * field for Relation
		 */


		protected java.lang.String localRelation ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localRelationTracker = false ;

		public boolean isRelationSpecified(){
			return localRelationTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getRelation(){
			return localRelation;
		}



		/**
		 * Auto generated setter method
		 * @param param Relation
		 */
		public void setRelation(java.lang.String param){
			localRelationTracker = param != null;

			this.localRelation=param;


		}


		/**
		 * field for CustFullName
		 */


		protected java.lang.String localCustFullName ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCustFullName(){
			return localCustFullName;
		}



		/**
		 * Auto generated setter method
		 * @param param CustFullName
		 */
		public void setCustFullName(java.lang.String param){

			this.localCustFullName=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":linkedCustomer_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"linkedCustomer_type0",
							xmlWriter);
				}


			}
			if (localCustomerIDTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "customerID", xmlWriter);


				if (localCustomerID==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("customerID cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCustomerID);

				}

				xmlWriter.writeEndElement();
			} if (localRelationTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "relation", xmlWriter);


				if (localRelation==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("relation cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localRelation);

				}

				xmlWriter.writeEndElement();
			}
			namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
			writeStartElement(null, namespace, "CustFullName", xmlWriter);


			if (localCustFullName==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("CustFullName cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localCustFullName);

			}

			xmlWriter.writeEndElement();

			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd")){
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

			if (localCustomerIDTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"customerID"));

				if (localCustomerID != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerID));
				} else {
					throw new org.apache.axis2.databinding.ADBException("customerID cannot be null!!");
				}
			} if (localRelationTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"relation"));

				if (localRelation != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRelation));
				} else {
					throw new org.apache.axis2.databinding.ADBException("relation cannot be null!!");
				}
			}
			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
					"CustFullName"));

			if (localCustFullName != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustFullName));
			} else {
				throw new org.apache.axis2.databinding.ADBException("CustFullName cannot be null!!");
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
			public static LinkedCustomer_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				LinkedCustomer_type0 object =
						new LinkedCustomer_type0();

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

							if (!"linkedCustomer_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (LinkedCustomer_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","customerID").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"customerID" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCustomerID(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","relation").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"relation" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setRelation(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","CustFullName").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"CustFullName" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCustFullName(
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


	public static class Delinquency_type1
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = delinquency_type1
                Namespace URI = http://www.adcb.com/esb/common/LoanOperativeInfo.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for DelinquencyAmount
		 */


		protected java.lang.String localDelinquencyAmount ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDelinquencyAmountTracker = false ;

		public boolean isDelinquencyAmountSpecified(){
			return localDelinquencyAmountTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDelinquencyAmount(){
			return localDelinquencyAmount;
		}



		/**
		 * Auto generated setter method
		 * @param param DelinquencyAmount
		 */
		public void setDelinquencyAmount(java.lang.String param){
			localDelinquencyAmountTracker = param != null;

			this.localDelinquencyAmount=param;


		}


		/**
		 * field for NPLStatus
		 */


		protected java.lang.String localNPLStatus ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNPLStatusTracker = false ;

		public boolean isNPLStatusSpecified(){
			return localNPLStatusTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNPLStatus(){
			return localNPLStatus;
		}



		/**
		 * Auto generated setter method
		 * @param param NPLStatus
		 */
		public void setNPLStatus(java.lang.String param){
			localNPLStatusTracker = param != null;

			this.localNPLStatus=param;


		}


		/**
		 * field for LastDeliquencyDate
		 */


		protected java.lang.String localLastDeliquencyDate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLastDeliquencyDateTracker = false ;

		public boolean isLastDeliquencyDateSpecified(){
			return localLastDeliquencyDateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLastDeliquencyDate(){
			return localLastDeliquencyDate;
		}



		/**
		 * Auto generated setter method
		 * @param param LastDeliquencyDate
		 */
		public void setLastDeliquencyDate(java.lang.String param){
			localLastDeliquencyDateTracker = param != null;

			this.localLastDeliquencyDate=param;


		}


		/**
		 * field for NoOfDaysPastDue
		 */


		protected java.lang.String localNoOfDaysPastDue ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNoOfDaysPastDueTracker = false ;

		public boolean isNoOfDaysPastDueSpecified(){
			return localNoOfDaysPastDueTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNoOfDaysPastDue(){
			return localNoOfDaysPastDue;
		}



		/**
		 * Auto generated setter method
		 * @param param NoOfDaysPastDue
		 */
		public void setNoOfDaysPastDue(java.lang.String param){
			localNoOfDaysPastDueTracker = param != null;

			this.localNoOfDaysPastDue=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":delinquency_type1",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"delinquency_type1",
							xmlWriter);
				}


			}
			if (localDelinquencyAmountTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "delinquencyAmount", xmlWriter);


				if (localDelinquencyAmount==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("delinquencyAmount cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDelinquencyAmount);

				}

				xmlWriter.writeEndElement();
			} if (localNPLStatusTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "NPLStatus", xmlWriter);


				if (localNPLStatus==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("NPLStatus cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNPLStatus);

				}

				xmlWriter.writeEndElement();
			} if (localLastDeliquencyDateTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "lastDeliquencyDate", xmlWriter);


				if (localLastDeliquencyDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("lastDeliquencyDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLastDeliquencyDate);

				}

				xmlWriter.writeEndElement();
			} if (localNoOfDaysPastDueTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "noOfDaysPastDue", xmlWriter);


				if (localNoOfDaysPastDue==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("noOfDaysPastDue cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNoOfDaysPastDue);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd")){
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

			if (localDelinquencyAmountTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"delinquencyAmount"));

				if (localDelinquencyAmount != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDelinquencyAmount));
				} else {
					throw new org.apache.axis2.databinding.ADBException("delinquencyAmount cannot be null!!");
				}
			} if (localNPLStatusTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"NPLStatus"));

				if (localNPLStatus != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNPLStatus));
				} else {
					throw new org.apache.axis2.databinding.ADBException("NPLStatus cannot be null!!");
				}
			} if (localLastDeliquencyDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"lastDeliquencyDate"));

				if (localLastDeliquencyDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLastDeliquencyDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("lastDeliquencyDate cannot be null!!");
				}
			} if (localNoOfDaysPastDueTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"noOfDaysPastDue"));

				if (localNoOfDaysPastDue != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNoOfDaysPastDue));
				} else {
					throw new org.apache.axis2.databinding.ADBException("noOfDaysPastDue cannot be null!!");
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
			public static Delinquency_type1 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				Delinquency_type1 object =
						new Delinquency_type1();

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

							if (!"delinquency_type1".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (Delinquency_type1)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","delinquencyAmount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"delinquencyAmount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDelinquencyAmount(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","NPLStatus").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"NPLStatus" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNPLStatus(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","lastDeliquencyDate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"lastDeliquencyDate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLastDeliquencyDate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","noOfDaysPastDue").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"noOfDaysPastDue" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNoOfDaysPastDue(
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


	public static class Delinquency_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = delinquency_type0
                Namespace URI = http://www.adcb.com/esb/common/LoanOperativeInfo.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for DelinquencyAmount
		 */


		protected java.lang.String localDelinquencyAmount ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDelinquencyAmountTracker = false ;

		public boolean isDelinquencyAmountSpecified(){
			return localDelinquencyAmountTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDelinquencyAmount(){
			return localDelinquencyAmount;
		}



		/**
		 * Auto generated setter method
		 * @param param DelinquencyAmount
		 */
		public void setDelinquencyAmount(java.lang.String param){
			localDelinquencyAmountTracker = param != null;

			this.localDelinquencyAmount=param;


		}


		/**
		 * field for NPLStatus
		 */


		protected java.lang.String localNPLStatus ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNPLStatusTracker = false ;

		public boolean isNPLStatusSpecified(){
			return localNPLStatusTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNPLStatus(){
			return localNPLStatus;
		}



		/**
		 * Auto generated setter method
		 * @param param NPLStatus
		 */
		public void setNPLStatus(java.lang.String param){
			localNPLStatusTracker = param != null;

			this.localNPLStatus=param;


		}


		/**
		 * field for LastDeliquencyDate
		 */


		protected java.lang.String localLastDeliquencyDate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLastDeliquencyDateTracker = false ;

		public boolean isLastDeliquencyDateSpecified(){
			return localLastDeliquencyDateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLastDeliquencyDate(){
			return localLastDeliquencyDate;
		}



		/**
		 * Auto generated setter method
		 * @param param LastDeliquencyDate
		 */
		public void setLastDeliquencyDate(java.lang.String param){
			localLastDeliquencyDateTracker = param != null;

			this.localLastDeliquencyDate=param;


		}


		/**
		 * field for NoOfDaysPastDue
		 */


		protected java.lang.String localNoOfDaysPastDue ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNoOfDaysPastDueTracker = false ;

		public boolean isNoOfDaysPastDueSpecified(){
			return localNoOfDaysPastDueTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNoOfDaysPastDue(){
			return localNoOfDaysPastDue;
		}



		/**
		 * Auto generated setter method
		 * @param param NoOfDaysPastDue
		 */
		public void setNoOfDaysPastDue(java.lang.String param){
			localNoOfDaysPastDueTracker = param != null;

			this.localNoOfDaysPastDue=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":delinquency_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"delinquency_type0",
							xmlWriter);
				}


			}
			if (localDelinquencyAmountTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "delinquencyAmount", xmlWriter);


				if (localDelinquencyAmount==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("delinquencyAmount cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDelinquencyAmount);

				}

				xmlWriter.writeEndElement();
			} if (localNPLStatusTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "NPLStatus", xmlWriter);


				if (localNPLStatus==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("NPLStatus cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNPLStatus);

				}

				xmlWriter.writeEndElement();
			} if (localLastDeliquencyDateTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "lastDeliquencyDate", xmlWriter);


				if (localLastDeliquencyDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("lastDeliquencyDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLastDeliquencyDate);

				}

				xmlWriter.writeEndElement();
			} if (localNoOfDaysPastDueTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "noOfDaysPastDue", xmlWriter);


				if (localNoOfDaysPastDue==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("noOfDaysPastDue cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNoOfDaysPastDue);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd")){
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

			if (localDelinquencyAmountTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"delinquencyAmount"));

				if (localDelinquencyAmount != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDelinquencyAmount));
				} else {
					throw new org.apache.axis2.databinding.ADBException("delinquencyAmount cannot be null!!");
				}
			} if (localNPLStatusTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"NPLStatus"));

				if (localNPLStatus != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNPLStatus));
				} else {
					throw new org.apache.axis2.databinding.ADBException("NPLStatus cannot be null!!");
				}
			} if (localLastDeliquencyDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"lastDeliquencyDate"));

				if (localLastDeliquencyDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLastDeliquencyDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("lastDeliquencyDate cannot be null!!");
				}
			} if (localNoOfDaysPastDueTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"noOfDaysPastDue"));

				if (localNoOfDaysPastDue != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNoOfDaysPastDue));
				} else {
					throw new org.apache.axis2.databinding.ADBException("noOfDaysPastDue cannot be null!!");
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
			public static Delinquency_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				Delinquency_type0 object =
						new Delinquency_type0();

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

							if (!"delinquency_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (Delinquency_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","delinquencyAmount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"delinquencyAmount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDelinquencyAmount(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","NPLStatus").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"NPLStatus" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNPLStatus(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","lastDeliquencyDate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"lastDeliquencyDate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLastDeliquencyDate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","noOfDaysPastDue").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"noOfDaysPastDue" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNoOfDaysPastDue(
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


	public static class LinkedCustomer_type1
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = linkedCustomer_type1
                Namespace URI = http://www.adcb.com/esb/common/LoanOperativeInfo.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for CustomerID
		 */


		protected java.lang.String localCustomerID ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCustomerIDTracker = false ;

		public boolean isCustomerIDSpecified(){
			return localCustomerIDTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCustomerID(){
			return localCustomerID;
		}



		/**
		 * Auto generated setter method
		 * @param param CustomerID
		 */
		public void setCustomerID(java.lang.String param){
			localCustomerIDTracker = param != null;

			this.localCustomerID=param;


		}


		/**
		 * field for Relation
		 */


		protected java.lang.String localRelation ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localRelationTracker = false ;

		public boolean isRelationSpecified(){
			return localRelationTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getRelation(){
			return localRelation;
		}



		/**
		 * Auto generated setter method
		 * @param param Relation
		 */
		public void setRelation(java.lang.String param){
			localRelationTracker = param != null;

			this.localRelation=param;


		}


		/**
		 * field for CustFullName
		 */


		protected java.lang.String localCustFullName ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCustFullName(){
			return localCustFullName;
		}



		/**
		 * Auto generated setter method
		 * @param param CustFullName
		 */
		public void setCustFullName(java.lang.String param){

			this.localCustFullName=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":linkedCustomer_type1",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"linkedCustomer_type1",
							xmlWriter);
				}


			}
			if (localCustomerIDTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "customerID", xmlWriter);


				if (localCustomerID==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("customerID cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCustomerID);

				}

				xmlWriter.writeEndElement();
			} if (localRelationTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "relation", xmlWriter);


				if (localRelation==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("relation cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localRelation);

				}

				xmlWriter.writeEndElement();
			}
			namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
			writeStartElement(null, namespace, "CustFullName", xmlWriter);


			if (localCustFullName==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("CustFullName cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localCustFullName);

			}

			xmlWriter.writeEndElement();

			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd")){
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

			if (localCustomerIDTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"customerID"));

				if (localCustomerID != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerID));
				} else {
					throw new org.apache.axis2.databinding.ADBException("customerID cannot be null!!");
				}
			} if (localRelationTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"relation"));

				if (localRelation != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRelation));
				} else {
					throw new org.apache.axis2.databinding.ADBException("relation cannot be null!!");
				}
			}
			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
					"CustFullName"));

			if (localCustFullName != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustFullName));
			} else {
				throw new org.apache.axis2.databinding.ADBException("CustFullName cannot be null!!");
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
			public static LinkedCustomer_type1 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				LinkedCustomer_type1 object =
						new LinkedCustomer_type1();

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

							if (!"linkedCustomer_type1".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (LinkedCustomer_type1)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","customerID").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"customerID" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCustomerID(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","relation").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"relation" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setRelation(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","CustFullName").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"CustFullName" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCustFullName(
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


	public static class GetLoanOperativeInfoResMsg
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd",
				"GetLoanOperativeInfoResMsg",
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
		 * field for GetLoanOperativeInfoRes
		 */


		protected GetLoanOperativeInfoRes_type0 localGetLoanOperativeInfoRes ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localGetLoanOperativeInfoResTracker = false ;

		public boolean isGetLoanOperativeInfoResSpecified(){
			return localGetLoanOperativeInfoResTracker;
		}



		/**
		 * Auto generated getter method
		 * @return GetLoanOperativeInfoRes_type0
		 */
		public  GetLoanOperativeInfoRes_type0 getGetLoanOperativeInfoRes(){
			return localGetLoanOperativeInfoRes;
		}



		/**
		 * Auto generated setter method
		 * @param param GetLoanOperativeInfoRes
		 */
		public void setGetLoanOperativeInfoRes(GetLoanOperativeInfoRes_type0 param){
			localGetLoanOperativeInfoResTracker = param != null;

			this.localGetLoanOperativeInfoRes=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":GetLoanOperativeInfoResMsg",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"GetLoanOperativeInfoResMsg",
							xmlWriter);
				}


			}

			if (localHeader==null){
				throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
			}
			localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
					xmlWriter);
			if (localGetLoanOperativeInfoResTracker){
				if (localGetLoanOperativeInfoRes==null){
					throw new org.apache.axis2.databinding.ADBException("GetLoanOperativeInfoRes cannot be null!!");
				}
				localGetLoanOperativeInfoRes.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd","GetLoanOperativeInfoRes"),
						xmlWriter);
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd")){
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
			if (localGetLoanOperativeInfoResTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd",
						"GetLoanOperativeInfoRes"));


				if (localGetLoanOperativeInfoRes==null){
					throw new org.apache.axis2.databinding.ADBException("GetLoanOperativeInfoRes cannot be null!!");
				}
				elementList.add(localGetLoanOperativeInfoRes);
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
			public static GetLoanOperativeInfoResMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				GetLoanOperativeInfoResMsg object =
						new GetLoanOperativeInfoResMsg();

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

							if (!"GetLoanOperativeInfoResMsg".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (GetLoanOperativeInfoResMsg)ExtensionMapper.getTypeObject(
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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd","GetLoanOperativeInfoRes").equals(reader.getName())){

						object.setGetLoanOperativeInfoRes(GetLoanOperativeInfoRes_type0.Factory.parse(reader));

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


	public static class GetLoanOperativeInfoRes_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = GetLoanOperativeInfoRes_type0
                Namespace URI = http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd
                Namespace Prefix = ns3
		 */


		/**
		 * field for Loan_Operative_Info
		 */


		protected Loan_Operative_Info_type0 localLoan_Operative_Info ;


		/**
		 * Auto generated getter method
		 * @return Loan_Operative_Info_type0
		 */
		public  Loan_Operative_Info_type0 getLoan_Operative_Info(){
			return localLoan_Operative_Info;
		}



		/**
		 * Auto generated setter method
		 * @param param Loan_Operative_Info
		 */
		public void setLoan_Operative_Info(Loan_Operative_Info_type0 param){

			this.localLoan_Operative_Info=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":GetLoanOperativeInfoRes_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"GetLoanOperativeInfoRes_type0",
							xmlWriter);
				}


			}

			if (localLoan_Operative_Info==null){
				throw new org.apache.axis2.databinding.ADBException("Loan_Operative_Info cannot be null!!");
			}
			localLoan_Operative_Info.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","Loan_Operative_Info"),
					xmlWriter);

			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd")){
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


			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
					"Loan_Operative_Info"));


			if (localLoan_Operative_Info==null){
				throw new org.apache.axis2.databinding.ADBException("Loan_Operative_Info cannot be null!!");
			}
			elementList.add(localLoan_Operative_Info);


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
			public static GetLoanOperativeInfoRes_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				GetLoanOperativeInfoRes_type0 object =
						new GetLoanOperativeInfoRes_type0();

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

							if (!"GetLoanOperativeInfoRes_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (GetLoanOperativeInfoRes_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","Loan_Operative_Info").equals(reader.getName())){

						object.setLoan_Operative_Info(Loan_Operative_Info_type0.Factory.parse(reader));

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


	public static class Loan_Operative_Info_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = Loan_Operative_Info_type0
                Namespace URI = http://www.adcb.com/esb/common/LoanOperativeInfo.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for BalScheduleDetails
		 */


		protected BalScheduleDetails_type1 localBalScheduleDetails ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localBalScheduleDetailsTracker = false ;

		public boolean isBalScheduleDetailsSpecified(){
			return localBalScheduleDetailsTracker;
		}



		/**
		 * Auto generated getter method
		 * @return BalScheduleDetails_type1
		 */
		public  BalScheduleDetails_type1 getBalScheduleDetails(){
			return localBalScheduleDetails;
		}



		/**
		 * Auto generated setter method
		 * @param param BalScheduleDetails
		 */
		public void setBalScheduleDetails(BalScheduleDetails_type1 param){
			localBalScheduleDetailsTracker = param != null;

			this.localBalScheduleDetails=param;


		}


		/**
		 * field for PDC
		 */


		protected PDC_type1 localPDC ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPDCTracker = false ;

		public boolean isPDCSpecified(){
			return localPDCTracker;
		}



		/**
		 * Auto generated getter method
		 * @return PDC_type1
		 */
		 public  PDC_type1 getPDC(){
			 return localPDC;
		 }



		 /**
		  * Auto generated setter method
		  * @param param PDC
		  */
		 public void setPDC(PDC_type1 param){
			 localPDCTracker = param != null;

			 this.localPDC=param;


		 }


		 /**
		  * field for StandingInst
		  * This was an Array!
		  */


		 protected StandingInst_type1[] localStandingInst ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localStandingInstTracker = false ;

		 public boolean isStandingInstSpecified(){
			 return localStandingInstTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return StandingInst_type1[]
		  */
		 public  StandingInst_type1[] getStandingInst(){
			 return localStandingInst;
		 }






		 /**
		  * validate the array for StandingInst
		  */
		 protected void validateStandingInst(StandingInst_type1[] param){

		 }


		 /**
		  * Auto generated setter method
		  * @param param StandingInst
		  */
		 public void setStandingInst(StandingInst_type1[] param){

			 validateStandingInst(param);

			 localStandingInstTracker = param != null;

			 this.localStandingInst=param;
		 }



		 /**
		  * Auto generated add method for the array for convenience
		  * @param param StandingInst_type1
		  */
		 public void addStandingInst(StandingInst_type1 param){
			 if (localStandingInst == null){
				 localStandingInst = new StandingInst_type1[]{};
			 }


			 //update the setting tracker
			 localStandingInstTracker = true;


			 java.util.List list =
					 org.apache.axis2.databinding.utils.ConverterUtil.toList(localStandingInst);
			 list.add(param);
			 this.localStandingInst =
					 (StandingInst_type1[])list.toArray(
							 new StandingInst_type1[list.size()]);

		 }


		 /**
		  * field for Limits
		  * This was an Array!
		  */


		 protected Limits_type1[] localLimits ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localLimitsTracker = false ;

		 public boolean isLimitsSpecified(){
			 return localLimitsTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return Limits_type1[]
		  */
		 public  Limits_type1[] getLimits(){
			 return localLimits;
		 }






		 /**
		  * validate the array for Limits
		  */
		 protected void validateLimits(Limits_type1[] param){

		 }


		 /**
		  * Auto generated setter method
		  * @param param Limits
		  */
		 public void setLimits(Limits_type1[] param){

			 validateLimits(param);

			 localLimitsTracker = param != null;

			 this.localLimits=param;
		 }



		 /**
		  * Auto generated add method for the array for convenience
		  * @param param Limits_type1
		  */
		 public void addLimits(Limits_type1 param){
			 if (localLimits == null){
				 localLimits = new Limits_type1[]{};
			 }


			 //update the setting tracker
			 localLimitsTracker = true;


			 java.util.List list =
					 org.apache.axis2.databinding.utils.ConverterUtil.toList(localLimits);
			 list.add(param);
			 this.localLimits =
					 (Limits_type1[])list.toArray(
							 new Limits_type1[list.size()]);

		 }


		 /**
		  * field for LinkedCustomer
		  * This was an Array!
		  */


		 protected LinkedCustomer_type1[] localLinkedCustomer ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localLinkedCustomerTracker = false ;

		 public boolean isLinkedCustomerSpecified(){
			 return localLinkedCustomerTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return LinkedCustomer_type1[]
		  */
		 public  LinkedCustomer_type1[] getLinkedCustomer(){
			 return localLinkedCustomer;
		 }






		 /**
		  * validate the array for LinkedCustomer
		  */
		 protected void validateLinkedCustomer(LinkedCustomer_type1[] param){

		 }


		 /**
		  * Auto generated setter method
		  * @param param LinkedCustomer
		  */
		 public void setLinkedCustomer(LinkedCustomer_type1[] param){

			 validateLinkedCustomer(param);

			 localLinkedCustomerTracker = param != null;

			 this.localLinkedCustomer=param;
		 }



		 /**
		  * Auto generated add method for the array for convenience
		  * @param param LinkedCustomer_type1
		  */
		 public void addLinkedCustomer(LinkedCustomer_type1 param){
			 if (localLinkedCustomer == null){
				 localLinkedCustomer = new LinkedCustomer_type1[]{};
			 }


			 //update the setting tracker
			 localLinkedCustomerTracker = true;


			 java.util.List list =
					 org.apache.axis2.databinding.utils.ConverterUtil.toList(localLinkedCustomer);
			 list.add(param);
			 this.localLinkedCustomer =
					 (LinkedCustomer_type1[])list.toArray(
							 new LinkedCustomer_type1[list.size()]);

		 }


		 /**
		  * field for Delinquency
		  */


		 protected Delinquency_type1 localDelinquency ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localDelinquencyTracker = false ;

		 public boolean isDelinquencySpecified(){
			 return localDelinquencyTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return Delinquency_type1
		  */
		 public  Delinquency_type1 getDelinquency(){
			 return localDelinquency;
		 }



		 /**
		  * Auto generated setter method
		  * @param param Delinquency
		  */
		 public void setDelinquency(Delinquency_type1 param){
			 localDelinquencyTracker = param != null;

			 this.localDelinquency=param;


		 }


		 /**
		  * field for DealerCode
		  */


		 protected java.lang.String localDealerCode ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localDealerCodeTracker = false ;

		 public boolean isDealerCodeSpecified(){
			 return localDealerCodeTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return java.lang.String
		  */
		 public  java.lang.String getDealerCode(){
			 return localDealerCode;
		 }



		 /**
		  * Auto generated setter method
		  * @param param DealerCode
		  */
		 public void setDealerCode(java.lang.String param){
			 localDealerCodeTracker = param != null;

			 this.localDealerCode=param;


		 }


		 /**
		  * field for SourceBy
		  */


		 protected java.lang.String localSourceBy ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localSourceByTracker = false ;

		 public boolean isSourceBySpecified(){
			 return localSourceByTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return java.lang.String
		  */
		 public  java.lang.String getSourceBy(){
			 return localSourceBy;
		 }



		 /**
		  * Auto generated setter method
		  * @param param SourceBy
		  */
		 public void setSourceBy(java.lang.String param){
			 localSourceByTracker = param != null;

			 this.localSourceBy=param;


		 }


		 /**
		  * field for SourceCode
		  */


		 protected java.lang.String localSourceCode ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localSourceCodeTracker = false ;

		 public boolean isSourceCodeSpecified(){
			 return localSourceCodeTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return java.lang.String
		  */
		 public  java.lang.String getSourceCode(){
			 return localSourceCode;
		 }



		 /**
		  * Auto generated setter method
		  * @param param SourceCode
		  */
		 public void setSourceCode(java.lang.String param){
			 localSourceCodeTracker = param != null;

			 this.localSourceCode=param;


		 }


		 /**
		  * field for PrincipalOverDue
		  */


		 protected java.lang.String localPrincipalOverDue ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localPrincipalOverDueTracker = false ;

		 public boolean isPrincipalOverDueSpecified(){
			 return localPrincipalOverDueTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return java.lang.String
		  */
		 public  java.lang.String getPrincipalOverDue(){
			 return localPrincipalOverDue;
		 }



		 /**
		  * Auto generated setter method
		  * @param param PrincipalOverDue
		  */
		 public void setPrincipalOverDue(java.lang.String param){
			 localPrincipalOverDueTracker = param != null;

			 this.localPrincipalOverDue=param;


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
		  * field for LoanAccountNumber
		  */


		 protected java.lang.String localLoanAccountNumber ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localLoanAccountNumberTracker = false ;

		 public boolean isLoanAccountNumberSpecified(){
			 return localLoanAccountNumberTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return java.lang.String
		  */
		 public  java.lang.String getLoanAccountNumber(){
			 return localLoanAccountNumber;
		 }



		 /**
		  * Auto generated setter method
		  * @param param LoanAccountNumber
		  */
		 public void setLoanAccountNumber(java.lang.String param){
			 localLoanAccountNumberTracker = param != null;

			 this.localLoanAccountNumber=param;


		 }


		 /**
		  * field for ValueDate
		  */


		 protected java.lang.String localValueDate ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localValueDateTracker = false ;

		 public boolean isValueDateSpecified(){
			 return localValueDateTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return java.lang.String
		  */
		 public  java.lang.String getValueDate(){
			 return localValueDate;
		 }



		 /**
		  * Auto generated setter method
		  * @param param ValueDate
		  */
		 public void setValueDate(java.lang.String param){
			 localValueDateTracker = param != null;

			 this.localValueDate=param;


		 }


		 /**
		  * field for PenalIntOverDue
		  */


		 protected java.lang.String localPenalIntOverDue ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localPenalIntOverDueTracker = false ;

		 public boolean isPenalIntOverDueSpecified(){
			 return localPenalIntOverDueTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return java.lang.String
		  */
		 public  java.lang.String getPenalIntOverDue(){
			 return localPenalIntOverDue;
		 }



		 /**
		  * Auto generated setter method
		  * @param param PenalIntOverDue
		  */
		 public void setPenalIntOverDue(java.lang.String param){
			 localPenalIntOverDueTracker = param != null;

			 this.localPenalIntOverDue=param;


		 }


		 /**
		  * field for FutureIntAccural
		  */


		 protected java.lang.String localFutureIntAccural ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localFutureIntAccuralTracker = false ;

		 public boolean isFutureIntAccuralSpecified(){
			 return localFutureIntAccuralTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return java.lang.String
		  */
		 public  java.lang.String getFutureIntAccural(){
			 return localFutureIntAccural;
		 }



		 /**
		  * Auto generated setter method
		  * @param param FutureIntAccural
		  */
		 public void setFutureIntAccural(java.lang.String param){
			 localFutureIntAccuralTracker = param != null;

			 this.localFutureIntAccural=param;


		 }


		 /**
		  * field for FuturePenalInt
		  */


		 protected java.lang.String localFuturePenalInt ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localFuturePenalIntTracker = false ;

		 public boolean isFuturePenalIntSpecified(){
			 return localFuturePenalIntTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return java.lang.String
		  */
		 public  java.lang.String getFuturePenalInt(){
			 return localFuturePenalInt;
		 }



		 /**
		  * Auto generated setter method
		  * @param param FuturePenalInt
		  */
		 public void setFuturePenalInt(java.lang.String param){
			 localFuturePenalIntTracker = param != null;

			 this.localFuturePenalInt=param;


		 }


		 /**
		  * field for EsfPercentage
		  */


		 protected java.lang.String localEsfPercentage ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localEsfPercentageTracker = false ;

		 public boolean isEsfPercentageSpecified(){
			 return localEsfPercentageTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return java.lang.String
		  */
		 public  java.lang.String getEsfPercentage(){
			 return localEsfPercentage;
		 }



		 /**
		  * Auto generated setter method
		  * @param param EsfPercentage
		  */
		 public void setEsfPercentage(java.lang.String param){
			 localEsfPercentageTracker = param != null;

			 this.localEsfPercentage=param;


		 }


		 /**
		  * field for EsfAmount
		  */


		 protected java.lang.String localEsfAmount ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localEsfAmountTracker = false ;

		 public boolean isEsfAmountSpecified(){
			 return localEsfAmountTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return java.lang.String
		  */
		 public  java.lang.String getEsfAmount(){
			 return localEsfAmount;
		 }



		 /**
		  * Auto generated setter method
		  * @param param EsfAmount
		  */
		 public void setEsfAmount(java.lang.String param){
			 localEsfAmountTracker = param != null;

			 this.localEsfAmount=param;


		 }


		 /**
		  * field for RpaBalance
		  */


		 protected java.lang.String localRpaBalance ;

		 /*  This tracker boolean wil be used to detect whether the user called the set method
		  *   for this attribute. It will be used to determine whether to include this field
		  *   in the serialized XML
		  */
		 protected boolean localRpaBalanceTracker = false ;

		 public boolean isRpaBalanceSpecified(){
			 return localRpaBalanceTracker;
		 }



		 /**
		  * Auto generated getter method
		  * @return java.lang.String
		  */
		 public  java.lang.String getRpaBalance(){
			 return localRpaBalance;
		 }



		 /**
		  * Auto generated setter method
		  * @param param RpaBalance
		  */
		 public void setRpaBalance(java.lang.String param){
			 localRpaBalanceTracker = param != null;

			 this.localRpaBalance=param;


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


				 java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd");
				 if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					 writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							 namespacePrefix+":Loan_Operative_Info_type0",
							 xmlWriter);
				 } else {
					 writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							 "Loan_Operative_Info_type0",
							 xmlWriter);
				 }


			 }
			 if (localBalScheduleDetailsTracker){
				 if (localBalScheduleDetails==null){
					 throw new org.apache.axis2.databinding.ADBException("balScheduleDetails cannot be null!!");
				 }
				 localBalScheduleDetails.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","balScheduleDetails"),
						 xmlWriter);
			 } if (localPDCTracker){
				 if (localPDC==null){
					 throw new org.apache.axis2.databinding.ADBException("PDC cannot be null!!");
				 }
				 localPDC.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","PDC"),
						 xmlWriter);
			 } if (localStandingInstTracker){
				 if (localStandingInst!=null){
					 for (int i = 0;i < localStandingInst.length;i++){
						 if (localStandingInst[i] != null){
							 localStandingInst[i].serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","standingInst"),
									 xmlWriter);
						 } else {

							 // we don't have to do any thing since minOccures is zero

						 }

					 }
				 } else {

					 throw new org.apache.axis2.databinding.ADBException("standingInst cannot be null!!");

				 }
			 } if (localLimitsTracker){
				 if (localLimits!=null){
					 for (int i = 0;i < localLimits.length;i++){
						 if (localLimits[i] != null){
							 localLimits[i].serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","limits"),
									 xmlWriter);
						 } else {

							 // we don't have to do any thing since minOccures is zero

						 }

					 }
				 } else {

					 throw new org.apache.axis2.databinding.ADBException("limits cannot be null!!");

				 }
			 } if (localLinkedCustomerTracker){
				 if (localLinkedCustomer!=null){
					 for (int i = 0;i < localLinkedCustomer.length;i++){
						 if (localLinkedCustomer[i] != null){
							 localLinkedCustomer[i].serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","linkedCustomer"),
									 xmlWriter);
						 } else {

							 // we don't have to do any thing since minOccures is zero

						 }

					 }
				 } else {

					 throw new org.apache.axis2.databinding.ADBException("linkedCustomer cannot be null!!");

				 }
			 } if (localDelinquencyTracker){
				 if (localDelinquency==null){
					 throw new org.apache.axis2.databinding.ADBException("delinquency cannot be null!!");
				 }
				 localDelinquency.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","delinquency"),
						 xmlWriter);
			 } if (localDealerCodeTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "dealerCode", xmlWriter);


				 if (localDealerCode==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("dealerCode cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localDealerCode);

				 }

				 xmlWriter.writeEndElement();
			 } if (localSourceByTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "sourceBy", xmlWriter);


				 if (localSourceBy==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("sourceBy cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localSourceBy);

				 }

				 xmlWriter.writeEndElement();
			 } if (localSourceCodeTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "sourceCode", xmlWriter);


				 if (localSourceCode==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("sourceCode cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localSourceCode);

				 }

				 xmlWriter.writeEndElement();
			 } if (localPrincipalOverDueTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "principalOverDue", xmlWriter);


				 if (localPrincipalOverDue==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("principalOverDue cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localPrincipalOverDue);

				 }

				 xmlWriter.writeEndElement();
			 } if (localCustomerIdTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "customerId", xmlWriter);


				 if (localCustomerId==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localCustomerId);

				 }

				 xmlWriter.writeEndElement();
			 } if (localLoanAccountNumberTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "loanAccountNumber", xmlWriter);


				 if (localLoanAccountNumber==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("loanAccountNumber cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localLoanAccountNumber);

				 }

				 xmlWriter.writeEndElement();
			 } if (localValueDateTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "valueDate", xmlWriter);


				 if (localValueDate==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("valueDate cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localValueDate);

				 }

				 xmlWriter.writeEndElement();
			 } if (localPenalIntOverDueTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "penalIntOverDue", xmlWriter);


				 if (localPenalIntOverDue==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("penalIntOverDue cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localPenalIntOverDue);

				 }

				 xmlWriter.writeEndElement();
			 } if (localFutureIntAccuralTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "futureIntAccural", xmlWriter);


				 if (localFutureIntAccural==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("futureIntAccural cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localFutureIntAccural);

				 }

				 xmlWriter.writeEndElement();
			 } if (localFuturePenalIntTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "futurePenalInt", xmlWriter);


				 if (localFuturePenalInt==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("futurePenalInt cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localFuturePenalInt);

				 }

				 xmlWriter.writeEndElement();
			 } if (localEsfPercentageTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "esfPercentage", xmlWriter);


				 if (localEsfPercentage==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("esfPercentage cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localEsfPercentage);

				 }

				 xmlWriter.writeEndElement();
			 } if (localEsfAmountTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "esfAmount", xmlWriter);


				 if (localEsfAmount==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("esfAmount cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localEsfAmount);

				 }

				 xmlWriter.writeEndElement();
			 } if (localRpaBalanceTracker){
				 namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				 writeStartElement(null, namespace, "rpaBalance", xmlWriter);


				 if (localRpaBalance==null){
					 // write the nil attribute

					 throw new org.apache.axis2.databinding.ADBException("rpaBalance cannot be null!!");

				 }else{


					 xmlWriter.writeCharacters(localRpaBalance);

				 }

				 xmlWriter.writeEndElement();
			 }
			 xmlWriter.writeEndElement();


		 }

		 private static java.lang.String generatePrefix(java.lang.String namespace) {
			 if(namespace.equals("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd")){
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

			 if (localBalScheduleDetailsTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "balScheduleDetails"));


				 if (localBalScheduleDetails==null){
					 throw new org.apache.axis2.databinding.ADBException("balScheduleDetails cannot be null!!");
				 }
				 elementList.add(localBalScheduleDetails);
			 } if (localPDCTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "PDC"));


				 if (localPDC==null){
					 throw new org.apache.axis2.databinding.ADBException("PDC cannot be null!!");
				 }
				 elementList.add(localPDC);
			 } if (localStandingInstTracker){
				 if (localStandingInst!=null) {
					 for (int i = 0;i < localStandingInst.length;i++){

						 if (localStandingInst[i] != null){
							 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
									 "standingInst"));
							 elementList.add(localStandingInst[i]);
						 } else {

							 // nothing to do

						 }

					 }
				 } else {

					 throw new org.apache.axis2.databinding.ADBException("standingInst cannot be null!!");

				 }

			 } if (localLimitsTracker){
				 if (localLimits!=null) {
					 for (int i = 0;i < localLimits.length;i++){

						 if (localLimits[i] != null){
							 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
									 "limits"));
							 elementList.add(localLimits[i]);
						 } else {

							 // nothing to do

						 }

					 }
				 } else {

					 throw new org.apache.axis2.databinding.ADBException("limits cannot be null!!");

				 }

			 } if (localLinkedCustomerTracker){
				 if (localLinkedCustomer!=null) {
					 for (int i = 0;i < localLinkedCustomer.length;i++){

						 if (localLinkedCustomer[i] != null){
							 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
									 "linkedCustomer"));
							 elementList.add(localLinkedCustomer[i]);
						 } else {

							 // nothing to do

						 }

					 }
				 } else {

					 throw new org.apache.axis2.databinding.ADBException("linkedCustomer cannot be null!!");

				 }

			 } if (localDelinquencyTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "delinquency"));


				 if (localDelinquency==null){
					 throw new org.apache.axis2.databinding.ADBException("delinquency cannot be null!!");
				 }
				 elementList.add(localDelinquency);
			 } if (localDealerCodeTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "dealerCode"));

				 if (localDealerCode != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDealerCode));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("dealerCode cannot be null!!");
				 }
			 } if (localSourceByTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "sourceBy"));

				 if (localSourceBy != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSourceBy));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("sourceBy cannot be null!!");
				 }
			 } if (localSourceCodeTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "sourceCode"));

				 if (localSourceCode != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSourceCode));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("sourceCode cannot be null!!");
				 }
			 } if (localPrincipalOverDueTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "principalOverDue"));

				 if (localPrincipalOverDue != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPrincipalOverDue));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("principalOverDue cannot be null!!");
				 }
			 } if (localCustomerIdTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "customerId"));

				 if (localCustomerId != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerId));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("customerId cannot be null!!");
				 }
			 } if (localLoanAccountNumberTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "loanAccountNumber"));

				 if (localLoanAccountNumber != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanAccountNumber));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("loanAccountNumber cannot be null!!");
				 }
			 } if (localValueDateTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "valueDate"));

				 if (localValueDate != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localValueDate));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("valueDate cannot be null!!");
				 }
			 } if (localPenalIntOverDueTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "penalIntOverDue"));

				 if (localPenalIntOverDue != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPenalIntOverDue));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("penalIntOverDue cannot be null!!");
				 }
			 } if (localFutureIntAccuralTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "futureIntAccural"));

				 if (localFutureIntAccural != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFutureIntAccural));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("futureIntAccural cannot be null!!");
				 }
			 } if (localFuturePenalIntTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "futurePenalInt"));

				 if (localFuturePenalInt != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFuturePenalInt));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("futurePenalInt cannot be null!!");
				 }
			 } if (localEsfPercentageTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "esfPercentage"));

				 if (localEsfPercentage != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEsfPercentage));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("esfPercentage cannot be null!!");
				 }
			 } if (localEsfAmountTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "esfAmount"));

				 if (localEsfAmount != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEsfAmount));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("esfAmount cannot be null!!");
				 }
			 } if (localRpaBalanceTracker){
				 elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						 "rpaBalance"));

				 if (localRpaBalance != null){
					 elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRpaBalance));
				 } else {
					 throw new org.apache.axis2.databinding.ADBException("rpaBalance cannot be null!!");
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
			 public static Loan_Operative_Info_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				 Loan_Operative_Info_type0 object =
						 new Loan_Operative_Info_type0();

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

							 if (!"Loan_Operative_Info_type0".equals(type)){
								 //find namespace for the prefix
								 java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								 return (Loan_Operative_Info_type0)ExtensionMapper.getTypeObject(
										 nsUri,type,reader);
							 }


						 }


					 }




					 // Note all attributes that were handled. Used to differ normal attributes
					 // from anyAttributes.
					 java.util.Vector handledAttributes = new java.util.Vector();




					 reader.next();

					 java.util.ArrayList list3 = new java.util.ArrayList();

					 java.util.ArrayList list4 = new java.util.ArrayList();

					 java.util.ArrayList list5 = new java.util.ArrayList();


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","balScheduleDetails").equals(reader.getName())){

						 object.setBalScheduleDetails(BalScheduleDetails_type1.Factory.parse(reader));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","PDC").equals(reader.getName())){

						 object.setPDC(PDC_type1.Factory.parse(reader));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","standingInst").equals(reader.getName())){



						 // Process the array and step past its final element's end.
						 list3.add(StandingInst_type1.Factory.parse(reader));

						 //loop until we find a start element that is not part of this array
						 boolean loopDone3 = false;
						 while(!loopDone3){
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
								 loopDone3 = true;
							 } else {
								 if (new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","standingInst").equals(reader.getName())){
									 list3.add(StandingInst_type1.Factory.parse(reader));

								 }else{
									 loopDone3 = true;
								 }
							 }
						 }
						 // call the converter utility  to convert and set the array

						 object.setStandingInst((StandingInst_type1[])
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
										 StandingInst_type1.class,
										 list3));

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","limits").equals(reader.getName())){



						 // Process the array and step past its final element's end.
						 list4.add(Limits_type1.Factory.parse(reader));

						 //loop until we find a start element that is not part of this array
						 boolean loopDone4 = false;
						 while(!loopDone4){
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
								 loopDone4 = true;
							 } else {
								 if (new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","limits").equals(reader.getName())){
									 list4.add(Limits_type1.Factory.parse(reader));

								 }else{
									 loopDone4 = true;
								 }
							 }
						 }
						 // call the converter utility  to convert and set the array

						 object.setLimits((Limits_type1[])
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
										 Limits_type1.class,
										 list4));

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","linkedCustomer").equals(reader.getName())){



						 // Process the array and step past its final element's end.
						 list5.add(LinkedCustomer_type1.Factory.parse(reader));

						 //loop until we find a start element that is not part of this array
						 boolean loopDone5 = false;
						 while(!loopDone5){
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
								 loopDone5 = true;
							 } else {
								 if (new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","linkedCustomer").equals(reader.getName())){
									 list5.add(LinkedCustomer_type1.Factory.parse(reader));

								 }else{
									 loopDone5 = true;
								 }
							 }
						 }
						 // call the converter utility  to convert and set the array

						 object.setLinkedCustomer((LinkedCustomer_type1[])
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
										 LinkedCustomer_type1.class,
										 list5));

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","delinquency").equals(reader.getName())){

						 object.setDelinquency(Delinquency_type1.Factory.parse(reader));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","dealerCode").equals(reader.getName())){

						 nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						 if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							 throw new org.apache.axis2.databinding.ADBException("The element: "+"dealerCode" +"  cannot be null");
						 }


						 java.lang.String content = reader.getElementText();

						 object.setDealerCode(
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","sourceBy").equals(reader.getName())){

						 nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						 if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							 throw new org.apache.axis2.databinding.ADBException("The element: "+"sourceBy" +"  cannot be null");
						 }


						 java.lang.String content = reader.getElementText();

						 object.setSourceBy(
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","sourceCode").equals(reader.getName())){

						 nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						 if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							 throw new org.apache.axis2.databinding.ADBException("The element: "+"sourceCode" +"  cannot be null");
						 }


						 java.lang.String content = reader.getElementText();

						 object.setSourceCode(
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","principalOverDue").equals(reader.getName())){

						 nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						 if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							 throw new org.apache.axis2.databinding.ADBException("The element: "+"principalOverDue" +"  cannot be null");
						 }


						 java.lang.String content = reader.getElementText();

						 object.setPrincipalOverDue(
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","customerId").equals(reader.getName())){

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

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","loanAccountNumber").equals(reader.getName())){

						 nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						 if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							 throw new org.apache.axis2.databinding.ADBException("The element: "+"loanAccountNumber" +"  cannot be null");
						 }


						 java.lang.String content = reader.getElementText();

						 object.setLoanAccountNumber(
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","valueDate").equals(reader.getName())){

						 nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						 if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							 throw new org.apache.axis2.databinding.ADBException("The element: "+"valueDate" +"  cannot be null");
						 }


						 java.lang.String content = reader.getElementText();

						 object.setValueDate(
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","penalIntOverDue").equals(reader.getName())){

						 nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						 if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							 throw new org.apache.axis2.databinding.ADBException("The element: "+"penalIntOverDue" +"  cannot be null");
						 }


						 java.lang.String content = reader.getElementText();

						 object.setPenalIntOverDue(
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","futureIntAccural").equals(reader.getName())){

						 nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						 if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							 throw new org.apache.axis2.databinding.ADBException("The element: "+"futureIntAccural" +"  cannot be null");
						 }


						 java.lang.String content = reader.getElementText();

						 object.setFutureIntAccural(
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","futurePenalInt").equals(reader.getName())){

						 nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						 if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							 throw new org.apache.axis2.databinding.ADBException("The element: "+"futurePenalInt" +"  cannot be null");
						 }


						 java.lang.String content = reader.getElementText();

						 object.setFuturePenalInt(
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","esfPercentage").equals(reader.getName())){

						 nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						 if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							 throw new org.apache.axis2.databinding.ADBException("The element: "+"esfPercentage" +"  cannot be null");
						 }


						 java.lang.String content = reader.getElementText();

						 object.setEsfPercentage(
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","esfAmount").equals(reader.getName())){

						 nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						 if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							 throw new org.apache.axis2.databinding.ADBException("The element: "+"esfAmount" +"  cannot be null");
						 }


						 java.lang.String content = reader.getElementText();

						 object.setEsfAmount(
								 org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						 reader.next();

					 }  // End of if for expected property start element

					 else {

					 }


					 while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					 if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","rpaBalance").equals(reader.getName())){

						 nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						 if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							 throw new org.apache.axis2.databinding.ADBException("The element: "+"rpaBalance" +"  cannot be null");
						 }


						 java.lang.String content = reader.getElementText();

						 object.setRpaBalance(
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


	public static class GetLoanOperativeInfoReqMsg
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd",
				"GetLoanOperativeInfoReqMsg",
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
		 * field for GetLoanOperativeInfoReq
		 */


		protected GetLoanOperativeInfoReq_type0 localGetLoanOperativeInfoReq ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localGetLoanOperativeInfoReqTracker = false ;

		public boolean isGetLoanOperativeInfoReqSpecified(){
			return localGetLoanOperativeInfoReqTracker;
		}



		/**
		 * Auto generated getter method
		 * @return GetLoanOperativeInfoReq_type0
		 */
		public  GetLoanOperativeInfoReq_type0 getGetLoanOperativeInfoReq(){
			return localGetLoanOperativeInfoReq;
		}



		/**
		 * Auto generated setter method
		 * @param param GetLoanOperativeInfoReq
		 */
		public void setGetLoanOperativeInfoReq(GetLoanOperativeInfoReq_type0 param){
			localGetLoanOperativeInfoReqTracker = param != null;

			this.localGetLoanOperativeInfoReq=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":GetLoanOperativeInfoReqMsg",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"GetLoanOperativeInfoReqMsg",
							xmlWriter);
				}


			}

			if (localHeader==null){
				throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
			}
			localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
					xmlWriter);
			if (localGetLoanOperativeInfoReqTracker){
				if (localGetLoanOperativeInfoReq==null){
					throw new org.apache.axis2.databinding.ADBException("GetLoanOperativeInfoReq cannot be null!!");
				}
				localGetLoanOperativeInfoReq.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd","GetLoanOperativeInfoReq"),
						xmlWriter);
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd")){
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
			if (localGetLoanOperativeInfoReqTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd",
						"GetLoanOperativeInfoReq"));


				if (localGetLoanOperativeInfoReq==null){
					throw new org.apache.axis2.databinding.ADBException("GetLoanOperativeInfoReq cannot be null!!");
				}
				elementList.add(localGetLoanOperativeInfoReq);
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
			public static GetLoanOperativeInfoReqMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				GetLoanOperativeInfoReqMsg object =
						new GetLoanOperativeInfoReqMsg();

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

							if (!"GetLoanOperativeInfoReqMsg".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (GetLoanOperativeInfoReqMsg)ExtensionMapper.getTypeObject(
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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanOperationDtl.xsd","GetLoanOperativeInfoReq").equals(reader.getName())){

						object.setGetLoanOperativeInfoReq(GetLoanOperativeInfoReq_type0.Factory.parse(reader));

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


	public static class PDC_type1
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = PDC_type1
                Namespace URI = http://www.adcb.com/esb/common/LoanOperativeInfo.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for PDCAmount
		 */


		protected java.lang.String localPDCAmount ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPDCAmountTracker = false ;

		public boolean isPDCAmountSpecified(){
			return localPDCAmountTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPDCAmount(){
			return localPDCAmount;
		}



		/**
		 * Auto generated setter method
		 * @param param PDCAmount
		 */
		public void setPDCAmount(java.lang.String param){
			localPDCAmountTracker = param != null;

			this.localPDCAmount=param;


		}


		/**
		 * field for PDCDraweeAccNO
		 */


		protected java.lang.String localPDCDraweeAccNO ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPDCDraweeAccNOTracker = false ;

		public boolean isPDCDraweeAccNOSpecified(){
			return localPDCDraweeAccNOTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPDCDraweeAccNO(){
			return localPDCDraweeAccNO;
		}



		/**
		 * Auto generated setter method
		 * @param param PDCDraweeAccNO
		 */
		public void setPDCDraweeAccNO(java.lang.String param){
			localPDCDraweeAccNOTracker = param != null;

			this.localPDCDraweeAccNO=param;


		}


		/**
		 * field for PDCDate
		 */


		protected java.lang.String localPDCDate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPDCDateTracker = false ;

		public boolean isPDCDateSpecified(){
			return localPDCDateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPDCDate(){
			return localPDCDate;
		}



		/**
		 * Auto generated setter method
		 * @param param PDCDate
		 */
		public void setPDCDate(java.lang.String param){
			localPDCDateTracker = param != null;

			this.localPDCDate=param;


		}


		/**
		 * field for PDCBankName
		 */


		protected java.lang.String localPDCBankName ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPDCBankNameTracker = false ;

		public boolean isPDCBankNameSpecified(){
			return localPDCBankNameTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPDCBankName(){
			return localPDCBankName;
		}



		/**
		 * Auto generated setter method
		 * @param param PDCBankName
		 */
		public void setPDCBankName(java.lang.String param){
			localPDCBankNameTracker = param != null;

			this.localPDCBankName=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":PDC_type1",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"PDC_type1",
							xmlWriter);
				}


			}
			if (localPDCAmountTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "PDCAmount", xmlWriter);


				if (localPDCAmount==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("PDCAmount cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPDCAmount);

				}

				xmlWriter.writeEndElement();
			} if (localPDCDraweeAccNOTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "PDCDraweeAccNO", xmlWriter);


				if (localPDCDraweeAccNO==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("PDCDraweeAccNO cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPDCDraweeAccNO);

				}

				xmlWriter.writeEndElement();
			} if (localPDCDateTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "PDCDate", xmlWriter);


				if (localPDCDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("PDCDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPDCDate);

				}

				xmlWriter.writeEndElement();
			} if (localPDCBankNameTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "PDCBankName", xmlWriter);


				if (localPDCBankName==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("PDCBankName cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPDCBankName);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd")){
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

			if (localPDCAmountTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"PDCAmount"));

				if (localPDCAmount != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPDCAmount));
				} else {
					throw new org.apache.axis2.databinding.ADBException("PDCAmount cannot be null!!");
				}
			} if (localPDCDraweeAccNOTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"PDCDraweeAccNO"));

				if (localPDCDraweeAccNO != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPDCDraweeAccNO));
				} else {
					throw new org.apache.axis2.databinding.ADBException("PDCDraweeAccNO cannot be null!!");
				}
			} if (localPDCDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"PDCDate"));

				if (localPDCDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPDCDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("PDCDate cannot be null!!");
				}
			} if (localPDCBankNameTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"PDCBankName"));

				if (localPDCBankName != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPDCBankName));
				} else {
					throw new org.apache.axis2.databinding.ADBException("PDCBankName cannot be null!!");
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
			public static PDC_type1 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				PDC_type1 object =
						new PDC_type1();

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

							if (!"PDC_type1".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (PDC_type1)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","PDCAmount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"PDCAmount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPDCAmount(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","PDCDraweeAccNO").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"PDCDraweeAccNO" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPDCDraweeAccNO(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","PDCDate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"PDCDate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPDCDate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","PDCBankName").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"PDCBankName" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPDCBankName(
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


	public static class PDC_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = PDC_type0
                Namespace URI = http://www.adcb.com/esb/common/LoanOperativeInfo.xsd
                Namespace Prefix = ns2
		 */


		/**
		 * field for PDCAmount
		 */


		protected java.lang.String localPDCAmount ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPDCAmountTracker = false ;

		public boolean isPDCAmountSpecified(){
			return localPDCAmountTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPDCAmount(){
			return localPDCAmount;
		}



		/**
		 * Auto generated setter method
		 * @param param PDCAmount
		 */
		public void setPDCAmount(java.lang.String param){
			localPDCAmountTracker = param != null;

			this.localPDCAmount=param;


		}


		/**
		 * field for PDCDraweeAccNO
		 */


		protected java.lang.String localPDCDraweeAccNO ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPDCDraweeAccNOTracker = false ;

		public boolean isPDCDraweeAccNOSpecified(){
			return localPDCDraweeAccNOTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPDCDraweeAccNO(){
			return localPDCDraweeAccNO;
		}



		/**
		 * Auto generated setter method
		 * @param param PDCDraweeAccNO
		 */
		public void setPDCDraweeAccNO(java.lang.String param){
			localPDCDraweeAccNOTracker = param != null;

			this.localPDCDraweeAccNO=param;


		}


		/**
		 * field for PDCDate
		 */


		protected java.lang.String localPDCDate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPDCDateTracker = false ;

		public boolean isPDCDateSpecified(){
			return localPDCDateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPDCDate(){
			return localPDCDate;
		}



		/**
		 * Auto generated setter method
		 * @param param PDCDate
		 */
		public void setPDCDate(java.lang.String param){
			localPDCDateTracker = param != null;

			this.localPDCDate=param;


		}


		/**
		 * field for PDCBankName
		 */


		protected java.lang.String localPDCBankName ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPDCBankNameTracker = false ;

		public boolean isPDCBankNameSpecified(){
			return localPDCBankNameTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPDCBankName(){
			return localPDCBankName;
		}



		/**
		 * Auto generated setter method
		 * @param param PDCBankName
		 */
		public void setPDCBankName(java.lang.String param){
			localPDCBankNameTracker = param != null;

			this.localPDCBankName=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/LoanOperativeInfo.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":PDC_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"PDC_type0",
							xmlWriter);
				}


			}
			if (localPDCAmountTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "PDCAmount", xmlWriter);


				if (localPDCAmount==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("PDCAmount cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPDCAmount);

				}

				xmlWriter.writeEndElement();
			} if (localPDCDraweeAccNOTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "PDCDraweeAccNO", xmlWriter);


				if (localPDCDraweeAccNO==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("PDCDraweeAccNO cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPDCDraweeAccNO);

				}

				xmlWriter.writeEndElement();
			} if (localPDCDateTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "PDCDate", xmlWriter);


				if (localPDCDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("PDCDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPDCDate);

				}

				xmlWriter.writeEndElement();
			} if (localPDCBankNameTracker){
				namespace = "http://www.adcb.com/esb/common/LoanOperativeInfo.xsd";
				writeStartElement(null, namespace, "PDCBankName", xmlWriter);


				if (localPDCBankName==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("PDCBankName cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPDCBankName);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd")){
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

			if (localPDCAmountTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"PDCAmount"));

				if (localPDCAmount != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPDCAmount));
				} else {
					throw new org.apache.axis2.databinding.ADBException("PDCAmount cannot be null!!");
				}
			} if (localPDCDraweeAccNOTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"PDCDraweeAccNO"));

				if (localPDCDraweeAccNO != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPDCDraweeAccNO));
				} else {
					throw new org.apache.axis2.databinding.ADBException("PDCDraweeAccNO cannot be null!!");
				}
			} if (localPDCDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"PDCDate"));

				if (localPDCDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPDCDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("PDCDate cannot be null!!");
				}
			} if (localPDCBankNameTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd",
						"PDCBankName"));

				if (localPDCBankName != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPDCBankName));
				} else {
					throw new org.apache.axis2.databinding.ADBException("PDCBankName cannot be null!!");
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
			public static PDC_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				PDC_type0 object =
						new PDC_type0();

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

							if (!"PDC_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (PDC_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","PDCAmount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"PDCAmount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPDCAmount(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","PDCDraweeAccNO").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"PDCDraweeAccNO" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPDCDraweeAccNO(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","PDCDate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"PDCDate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPDCDate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanOperativeInfo.xsd","PDCBankName").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"PDCBankName" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPDCBankName(
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


	private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqLoanOperationDtlStub.GetLoanOperativeInfoReqMsg param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {


		try{
			return param.getOMElement(com.newgen.dscop.stub.InqLoanOperationDtlStub.GetLoanOperativeInfoReqMsg.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}


	}

	private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqLoanOperationDtlStub.GetLoanOperativeInfoResMsg param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {


		try{
			return param.getOMElement(com.newgen.dscop.stub.InqLoanOperationDtlStub.GetLoanOperativeInfoResMsg.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}


	}


	private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.newgen.dscop.stub.InqLoanOperationDtlStub.GetLoanOperativeInfoReqMsg param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault{


		try{

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(com.newgen.dscop.stub.InqLoanOperationDtlStub.GetLoanOperativeInfoReqMsg.MY_QNAME,factory));
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

			if (com.newgen.dscop.stub.InqLoanOperationDtlStub.GetLoanOperativeInfoReqMsg.class.equals(type)){

				return com.newgen.dscop.stub.InqLoanOperationDtlStub.GetLoanOperativeInfoReqMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());


			}

			if (com.newgen.dscop.stub.InqLoanOperationDtlStub.GetLoanOperativeInfoResMsg.class.equals(type)){

				return com.newgen.dscop.stub.InqLoanOperationDtlStub.GetLoanOperativeInfoResMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());


			}

		} catch (java.lang.Exception e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
		return null;
	}




}
