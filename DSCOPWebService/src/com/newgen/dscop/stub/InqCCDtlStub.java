
/**
 * InqCCDtlStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
package com.newgen.dscop.stub;



/*
 *  InqCCDtlStub java implementation
 */


public class InqCCDtlStub extends org.apache.axis2.client.Stub
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

	public  String getInputXml(com.newgen.dscop.stub.InqCCDtlStub.GetCreditCardDetailsReqMsg getCreditCardDetailsReqMsg0)
			throws java.rmi.RemoteException
	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try{
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
			_operationClient.getOptions().setAction("/Services/EnterpriseServicesInquiry/CreditCards/Service/InqCCDtl.serviceagent/InqCCDtlPortTypeEndpoint1/InqCCDtl_Oper");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



			addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");


			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();



			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;


			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
					getCreditCardDetailsReqMsg0,
					optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1238481142625",
							"inqCCDtl_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1238481142625",
									"inqCCDtl_Oper"));

			//adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			//execute the operation client
			_operationClient.execute(true);

			return env.toString();

		}catch(org.apache.axis2.AxisFault f){

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt!=null){
				if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqCCDtl_Oper"))){
					//make the fault by reflection
					try{
						java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqCCDtl_Oper"));
						java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
						java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
						java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
						//message class
						java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqCCDtl_Oper"));
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

	private void populateAxisService() throws org.apache.axis2.AxisFault {

		//creating the Service with a unique name
		_service = new org.apache.axis2.description.AxisService("InqCCDtl" + getUniqueSuffix());
		addAnonymousOperations();

		//creating the operations
		org.apache.axis2.description.AxisOperation __operation;

		_operations = new org.apache.axis2.description.AxisOperation[1];

		__operation = new org.apache.axis2.description.OutInAxisOperation();


		__operation.setName(new javax.xml.namespace.QName("http://xmlns.example.com/1238481142625", "inqCCDtl_Oper"));
		_service.addOperation(__operation);




		_operations[0]=__operation;


	}

	//populates the faults
	private void populateFaults(){



	}

	/**
	 *Constructor that takes in a configContext
	 */

	public InqCCDtlStub(org.apache.axis2.context.ConfigurationContext configurationContext,
			java.lang.String targetEndpoint)
					throws org.apache.axis2.AxisFault {
		this(configurationContext,targetEndpoint,false);
	}


	/**
	 * Constructor that takes in a configContext  and useseperate listner
	 */
	public InqCCDtlStub(org.apache.axis2.context.ConfigurationContext configurationContext,
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
	public InqCCDtlStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {

		this(configurationContext,"http://10.101.107.21:5508/Services/EnterpriseServicesInquiry/CreditCards/Service/InqCCDtl.serviceagent/InqCCDtlPortTypeEndpoint1" );

	}

	/**
	 * Default Constructor
	 */
	public InqCCDtlStub() throws org.apache.axis2.AxisFault {

		this("http://10.101.107.21:5508/Services/EnterpriseServicesInquiry/CreditCards/Service/InqCCDtl.serviceagent/InqCCDtlPortTypeEndpoint1" );

	}

	/**
	 * Constructor taking the target endpoint
	 */
	public InqCCDtlStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
		this(null,targetEndpoint);
	}




	/**
	 * Auto generated method signature
	 * 
	 * @see com.newgen.dscop.stub.InqCCDtl#inqCCDtl_Oper
	 * @param getCreditCardDetailsReqMsg0

	 */



	public  String inqCCDtl_Oper(

			com.newgen.dscop.stub.InqCCDtlStub.GetCreditCardDetailsReqMsg getCreditCardDetailsReqMsg0)


					throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext = null;
		try{
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
			_operationClient.getOptions().setAction("/Services/EnterpriseServicesInquiry/CreditCards/Service/InqCCDtl.serviceagent/InqCCDtlPortTypeEndpoint1/InqCCDtl_Oper");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



			addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");


			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();



			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;


			env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
					getCreditCardDetailsReqMsg0,
					optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1238481142625",
							"inqCCDtl_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1238481142625",
									"inqCCDtl_Oper"));

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
			//			java.lang.Object object = fromOM(
			//					_returnEnv.getBody().getFirstElement() ,
			//					com.newgen.dscop.stub.InqCCDtlStub.GetCreditCardDetailsResMsg.class,
			//					getEnvelopeNamespaces(_returnEnv));


			return outputXML;


		}catch(org.apache.axis2.AxisFault f){

			org.apache.axiom.om.OMElement faultElt = f.getDetail();
			if (faultElt!=null){
				if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqCCDtl_Oper"))){
					//make the fault by reflection
					try{
						java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqCCDtl_Oper"));
						java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
						java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
						java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
						//message class
						java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqCCDtl_Oper"));
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
	 * @see com.newgen.dscop.stub.InqCCDtl#startinqCCDtl_Oper
	 * @param getCreditCardDetailsReqMsg0

	 */
	public  void startinqCCDtl_Oper(

			com.newgen.dscop.stub.InqCCDtlStub.GetCreditCardDetailsReqMsg getCreditCardDetailsReqMsg0,

			final com.newgen.dscop.stub.InqCCDtlCallbackHandler callback)

					throws java.rmi.RemoteException{

		org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
		_operationClient.getOptions().setAction("/Services/EnterpriseServicesInquiry/CreditCards/Service/InqCCDtl.serviceagent/InqCCDtlPortTypeEndpoint1/InqCCDtl_Oper");
		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



		addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");



		// create SOAP envelope with that payload
		org.apache.axiom.soap.SOAPEnvelope env=null;
		final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();


		//Style is Doc.


		env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
				getCreditCardDetailsReqMsg0,
				optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1238481142625",
						"inqCCDtl_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1238481142625",
								"inqCCDtl_Oper"));

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
							com.newgen.dscop.stub.InqCCDtlStub.GetCreditCardDetailsResMsg.class,
							getEnvelopeNamespaces(resultEnv));
					callback.receiveResultinqCCDtl_Oper(
							(com.newgen.dscop.stub.InqCCDtlStub.GetCreditCardDetailsResMsg)object);

				} catch (org.apache.axis2.AxisFault e) {
					callback.receiveErrorinqCCDtl_Oper(e);
				}
			}

			public void onError(java.lang.Exception error) {
				if (error instanceof org.apache.axis2.AxisFault) {
					org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
					org.apache.axiom.om.OMElement faultElt = f.getDetail();
					if (faultElt!=null){
						if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqCCDtl_Oper"))){
							//make the fault by reflection
							try{
								java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqCCDtl_Oper"));
								java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
								java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
								java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
								//message class
								java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqCCDtl_Oper"));
								java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
								java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
								java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
										new java.lang.Class[]{messageClass});
								m.invoke(ex,new java.lang.Object[]{messageObject});


								callback.receiveErrorinqCCDtl_Oper(new java.rmi.RemoteException(ex.getMessage(), ex));
							} catch(java.lang.ClassCastException e){
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorinqCCDtl_Oper(f);
							} catch (java.lang.ClassNotFoundException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorinqCCDtl_Oper(f);
							} catch (java.lang.NoSuchMethodException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorinqCCDtl_Oper(f);
							} catch (java.lang.reflect.InvocationTargetException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorinqCCDtl_Oper(f);
							} catch (java.lang.IllegalAccessException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorinqCCDtl_Oper(f);
							} catch (java.lang.InstantiationException e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorinqCCDtl_Oper(f);
							} catch (org.apache.axis2.AxisFault e) {
								// we cannot intantiate the class - throw the original Axis fault
								callback.receiveErrorinqCCDtl_Oper(f);
							}
						} else {
							callback.receiveErrorinqCCDtl_Oper(f);
						}
					} else {
						callback.receiveErrorinqCCDtl_Oper(f);
					}
				} else {
					callback.receiveErrorinqCCDtl_Oper(error);
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
					callback.receiveErrorinqCCDtl_Oper(axisFault);
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
	//http://10.101.107.21:5508/Services/EnterpriseServicesInquiry/CreditCards/Service/InqCCDtl.serviceagent/InqCCDtlPortTypeEndpoint1
	public static class Contact_details
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
				"contact_details",
				"ns1");



		/**
		 * field for Contact_details
		 */


		protected Contact_details_type0 localContact_details ;


		/**
		 * Auto generated getter method
		 * @return Contact_details_type0
		 */
		public  Contact_details_type0 getContact_details(){
			return localContact_details;
		}



		/**
		 * Auto generated setter method
		 * @param param Contact_details
		 */
		public void setContact_details(Contact_details_type0 param){

			this.localContact_details=param;


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

			if (localContact_details==null){
				throw new org.apache.axis2.databinding.ADBException("contact_details cannot be null!");
			}
			localContact_details.serialize(MY_QNAME,xmlWriter);


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd")){
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
			return localContact_details.getPullParser(MY_QNAME);

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
			public static Contact_details parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				Contact_details object =
						new Contact_details();

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

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","contact_details").equals(reader.getName())){

								object.setContact_details(Contact_details_type0.Factory.parse(reader));

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


	public static class Employment_details_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = employment_details_type0
                Namespace URI = http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd
                Namespace Prefix = ns1
		 */


		/**
		 * field for Employer_name
		 */


		protected java.lang.String localEmployer_name ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localEmployer_nameTracker = false ;

		public boolean isEmployer_nameSpecified(){
			return localEmployer_nameTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getEmployer_name(){
			return localEmployer_name;
		}



		/**
		 * Auto generated setter method
		 * @param param Employer_name
		 */
		public void setEmployer_name(java.lang.String param){
			localEmployer_nameTracker = param != null;

			this.localEmployer_name=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":employment_details_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"employment_details_type0",
							xmlWriter);
				}


			}
			if (localEmployer_nameTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "employer_name", xmlWriter);


				if (localEmployer_name==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("employer_name cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localEmployer_name);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd")){
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

			if (localEmployer_nameTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"employer_name"));

				if (localEmployer_name != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEmployer_name));
				} else {
					throw new org.apache.axis2.databinding.ADBException("employer_name cannot be null!!");
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
			public static Employment_details_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				Employment_details_type0 object =
						new Employment_details_type0();

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

							if (!"employment_details_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (Employment_details_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","employer_name").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"employer_name" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setEmployer_name(
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


	public static class Employment_details
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
				"employment_details",
				"ns1");



		/**
		 * field for Employment_details
		 */


		protected Employment_details_type0 localEmployment_details ;


		/**
		 * Auto generated getter method
		 * @return Employment_details_type0
		 */
		public  Employment_details_type0 getEmployment_details(){
			return localEmployment_details;
		}



		/**
		 * Auto generated setter method
		 * @param param Employment_details
		 */
		public void setEmployment_details(Employment_details_type0 param){

			this.localEmployment_details=param;


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

			if (localEmployment_details==null){
				throw new org.apache.axis2.databinding.ADBException("employment_details cannot be null!");
			}
			localEmployment_details.serialize(MY_QNAME,xmlWriter);


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd")){
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
			return localEmployment_details.getPullParser(MY_QNAME);

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
			public static Employment_details parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				Employment_details object =
						new Employment_details();

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

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","employment_details").equals(reader.getName())){

								object.setEmployment_details(Employment_details_type0.Factory.parse(reader));

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


	public static class Rep_CreditCardDetails
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
				"rep_CreditCardDetails",
				"ns1");



		/**
		 * field for Card_details
		 */


		protected Card_details_type0 localCard_details ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCard_detailsTracker = false ;

		public boolean isCard_detailsSpecified(){
			return localCard_detailsTracker;
		}



		/**
		 * Auto generated getter method
		 * @return Card_details_type0
		 */
		public  Card_details_type0 getCard_details(){
			return localCard_details;
		}



		/**
		 * Auto generated setter method
		 * @param param Card_details
		 */
		public void setCard_details(Card_details_type0 param){
			localCard_detailsTracker = param != null;

			this.localCard_details=param;


		}


		/**
		 * field for Personal_details
		 */


		protected Personal_details_type0 localPersonal_details ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPersonal_detailsTracker = false ;

		public boolean isPersonal_detailsSpecified(){
			return localPersonal_detailsTracker;
		}



		/**
		 * Auto generated getter method
		 * @return Personal_details_type0
		 */
		public  Personal_details_type0 getPersonal_details(){
			return localPersonal_details;
		}



		/**
		 * Auto generated setter method
		 * @param param Personal_details
		 */
		public void setPersonal_details(Personal_details_type0 param){
			localPersonal_detailsTracker = param != null;

			this.localPersonal_details=param;


		}


		/**
		 * field for Address_details
		 */


		protected Address_details_type0 localAddress_details ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAddress_detailsTracker = false ;

		public boolean isAddress_detailsSpecified(){
			return localAddress_detailsTracker;
		}



		/**
		 * Auto generated getter method
		 * @return Address_details_type0
		 */
		public  Address_details_type0 getAddress_details(){
			return localAddress_details;
		}



		/**
		 * Auto generated setter method
		 * @param param Address_details
		 */
		public void setAddress_details(Address_details_type0 param){
			localAddress_detailsTracker = param != null;

			this.localAddress_details=param;


		}


		/**
		 * field for Employment_details
		 */


		protected Employment_details_type0 localEmployment_details ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localEmployment_detailsTracker = false ;

		public boolean isEmployment_detailsSpecified(){
			return localEmployment_detailsTracker;
		}



		/**
		 * Auto generated getter method
		 * @return Employment_details_type0
		 */
		public  Employment_details_type0 getEmployment_details(){
			return localEmployment_details;
		}



		/**
		 * Auto generated setter method
		 * @param param Employment_details
		 */
		public void setEmployment_details(Employment_details_type0 param){
			localEmployment_detailsTracker = param != null;

			this.localEmployment_details=param;


		}


		/**
		 * field for Contact_details
		 * This was an Array!
		 */


		protected Contact_details_type0[] localContact_details ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localContact_detailsTracker = false ;

		public boolean isContact_detailsSpecified(){
			return localContact_detailsTracker;
		}



		/**
		 * Auto generated getter method
		 * @return Contact_details_type0[]
		 */
		public  Contact_details_type0[] getContact_details(){
			return localContact_details;
		}






		/**
		 * validate the array for Contact_details
		 */
		protected void validateContact_details(Contact_details_type0[] param){

		}


		/**
		 * Auto generated setter method
		 * @param param Contact_details
		 */
		public void setContact_details(Contact_details_type0[] param){

			validateContact_details(param);

			localContact_detailsTracker = param != null;

			this.localContact_details=param;
		}



		/**
		 * Auto generated add method for the array for convenience
		 * @param param Contact_details_type0
		 */
		public void addContact_details(Contact_details_type0 param){
			if (localContact_details == null){
				localContact_details = new Contact_details_type0[]{};
			}


			//update the setting tracker
			localContact_detailsTracker = true;


			java.util.List list =
					org.apache.axis2.databinding.utils.ConverterUtil.toList(localContact_details);
			list.add(param);
			this.localContact_details =
					(Contact_details_type0[])list.toArray(
							new Contact_details_type0[list.size()]);

		}


		/**
		 * field for Other_details
		 */


		protected Other_details_type0 localOther_details ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localOther_detailsTracker = false ;

		public boolean isOther_detailsSpecified(){
			return localOther_detailsTracker;
		}



		/**
		 * Auto generated getter method
		 * @return Other_details_type0
		 */
		public  Other_details_type0 getOther_details(){
			return localOther_details;
		}



		/**
		 * Auto generated setter method
		 * @param param Other_details
		 */
		public void setOther_details(Other_details_type0 param){
			localOther_detailsTracker = param != null;

			this.localOther_details=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":rep_CreditCardDetails",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"rep_CreditCardDetails",
							xmlWriter);
				}


			}
			if (localCard_detailsTracker){
				if (localCard_details==null){
					throw new org.apache.axis2.databinding.ADBException("card_details cannot be null!!");
				}
				localCard_details.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","card_details"),
						xmlWriter);
			} if (localPersonal_detailsTracker){
				if (localPersonal_details==null){
					throw new org.apache.axis2.databinding.ADBException("personal_details cannot be null!!");
				}
				localPersonal_details.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","personal_details"),
						xmlWriter);
			} if (localAddress_detailsTracker){
				if (localAddress_details==null){
					throw new org.apache.axis2.databinding.ADBException("address_details cannot be null!!");
				}
				localAddress_details.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","address_details"),
						xmlWriter);
			} if (localEmployment_detailsTracker){
				if (localEmployment_details==null){
					throw new org.apache.axis2.databinding.ADBException("employment_details cannot be null!!");
				}
				localEmployment_details.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","employment_details"),
						xmlWriter);
			} if (localContact_detailsTracker){
				if (localContact_details!=null){
					for (int i = 0;i < localContact_details.length;i++){
						if (localContact_details[i] != null){
							localContact_details[i].serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","contact_details"),
									xmlWriter);
						} else {

							// we don't have to do any thing since minOccures is zero

						}

					}
				} else {

					throw new org.apache.axis2.databinding.ADBException("contact_details cannot be null!!");

				}
			} if (localOther_detailsTracker){
				if (localOther_details==null){
					throw new org.apache.axis2.databinding.ADBException("other_details cannot be null!!");
				}
				localOther_details.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","other_details"),
						xmlWriter);
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd")){
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

			if (localCard_detailsTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"card_details"));


				if (localCard_details==null){
					throw new org.apache.axis2.databinding.ADBException("card_details cannot be null!!");
				}
				elementList.add(localCard_details);
			} if (localPersonal_detailsTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"personal_details"));


				if (localPersonal_details==null){
					throw new org.apache.axis2.databinding.ADBException("personal_details cannot be null!!");
				}
				elementList.add(localPersonal_details);
			} if (localAddress_detailsTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"address_details"));


				if (localAddress_details==null){
					throw new org.apache.axis2.databinding.ADBException("address_details cannot be null!!");
				}
				elementList.add(localAddress_details);
			} if (localEmployment_detailsTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"employment_details"));


				if (localEmployment_details==null){
					throw new org.apache.axis2.databinding.ADBException("employment_details cannot be null!!");
				}
				elementList.add(localEmployment_details);
			} if (localContact_detailsTracker){
				if (localContact_details!=null) {
					for (int i = 0;i < localContact_details.length;i++){

						if (localContact_details[i] != null){
							elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
									"contact_details"));
							elementList.add(localContact_details[i]);
						} else {

							// nothing to do

						}

					}
				} else {

					throw new org.apache.axis2.databinding.ADBException("contact_details cannot be null!!");

				}

			} if (localOther_detailsTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"other_details"));


				if (localOther_details==null){
					throw new org.apache.axis2.databinding.ADBException("other_details cannot be null!!");
				}
				elementList.add(localOther_details);
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
			public static Rep_CreditCardDetails parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				Rep_CreditCardDetails object =
						new Rep_CreditCardDetails();

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

							if (!"rep_CreditCardDetails".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (Rep_CreditCardDetails)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();

					java.util.ArrayList list5 = new java.util.ArrayList();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","card_details").equals(reader.getName())){

						object.setCard_details(Card_details_type0.Factory.parse(reader));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","personal_details").equals(reader.getName())){

						object.setPersonal_details(Personal_details_type0.Factory.parse(reader));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","address_details").equals(reader.getName())){

						object.setAddress_details(Address_details_type0.Factory.parse(reader));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","employment_details").equals(reader.getName())){

						object.setEmployment_details(Employment_details_type0.Factory.parse(reader));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","contact_details").equals(reader.getName())){



						// Process the array and step past its final element's end.
						list5.add(Contact_details_type0.Factory.parse(reader));

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
								if (new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","contact_details").equals(reader.getName())){
									list5.add(Contact_details_type0.Factory.parse(reader));

								}else{
									loopDone5 = true;
								}
							}
						}
						// call the converter utility  to convert and set the array

						object.setContact_details((Contact_details_type0[])
								org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
										Contact_details_type0.class,
										list5));

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","other_details").equals(reader.getName())){

						object.setOther_details(Other_details_type0.Factory.parse(reader));

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
					"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd".equals(namespaceURI) &&
					"plans_type0".equals(typeName)){

				return  Plans_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd".equals(namespaceURI) &&
					"personal_details_type0".equals(typeName)){

				return  Personal_details_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd".equals(namespaceURI) &&
					"rep_CreditCardDetails_type0".equals(typeName)){

				return  Rep_CreditCardDetails_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd".equals(namespaceURI) &&
					"insurance_products_type0".equals(typeName)){

				return  Insurance_products_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd".equals(namespaceURI) &&
					"contact_details_type0".equals(typeName)){

				return  Contact_details_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/header.xsd".equals(namespaceURI) &&
					"headerType".equals(typeName)){

				return  HeaderType.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd".equals(namespaceURI) &&
					"installment_plans_type0".equals(typeName)){

				return  Installment_plans_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd".equals(namespaceURI) &&
					"card_details_type0".equals(typeName)){

				return  Card_details_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd".equals(namespaceURI) &&
					"employment_details_type0".equals(typeName)){

				return  Employment_details_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd".equals(namespaceURI) &&
					"address_details_type0".equals(typeName)){

				return  Address_details_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd".equals(namespaceURI) &&
					"other_details_type0".equals(typeName)){

				return  Other_details_type0.Factory.parse(reader);


			}


			if (
					"http://www.adcb.com/esb/common/InqCCDtl.xsd".equals(namespaceURI) &&
					"GetCreditCardDetailsReq_type0".equals(typeName)){

				return  GetCreditCardDetailsReq_type0.Factory.parse(reader);


			}


			throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
		}

	}

	public static class GetCreditCardDetailsResMsg
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/common/InqCCDtl.xsd",
				"GetCreditCardDetailsResMsg",
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
		 * field for Rep_CreditCardDetails
		 */


		protected Rep_CreditCardDetails_type0 localRep_CreditCardDetails ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localRep_CreditCardDetailsTracker = false ;

		public boolean isRep_CreditCardDetailsSpecified(){
			return localRep_CreditCardDetailsTracker;
		}



		/**
		 * Auto generated getter method
		 * @return Rep_CreditCardDetails_type0
		 */
		public  Rep_CreditCardDetails_type0 getRep_CreditCardDetails(){
			return localRep_CreditCardDetails;
		}



		/**
		 * Auto generated setter method
		 * @param param Rep_CreditCardDetails
		 */
		public void setRep_CreditCardDetails(Rep_CreditCardDetails_type0 param){
			localRep_CreditCardDetailsTracker = param != null;

			this.localRep_CreditCardDetails=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/InqCCDtl.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":GetCreditCardDetailsResMsg",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"GetCreditCardDetailsResMsg",
							xmlWriter);
				}


			}

			if (localHeader==null){
				throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
			}
			localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
					xmlWriter);
			if (localRep_CreditCardDetailsTracker){
				if (localRep_CreditCardDetails==null){
					throw new org.apache.axis2.databinding.ADBException("rep_CreditCardDetails cannot be null!!");
				}
				localRep_CreditCardDetails.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","rep_CreditCardDetails"),
						xmlWriter);
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/InqCCDtl.xsd")){
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
			if (localRep_CreditCardDetailsTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"rep_CreditCardDetails"));


				if (localRep_CreditCardDetails==null){
					throw new org.apache.axis2.databinding.ADBException("rep_CreditCardDetails cannot be null!!");
				}
				elementList.add(localRep_CreditCardDetails);
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
			public static GetCreditCardDetailsResMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				GetCreditCardDetailsResMsg object =
						new GetCreditCardDetailsResMsg();

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

							if (!"GetCreditCardDetailsResMsg".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (GetCreditCardDetailsResMsg)ExtensionMapper.getTypeObject(
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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","rep_CreditCardDetails").equals(reader.getName())){

						object.setRep_CreditCardDetails(Rep_CreditCardDetails_type0.Factory.parse(reader));

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


	public static class Card_details_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = card_details_type0
                Namespace URI = http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd
                Namespace Prefix = ns1
		 */


		/**
		 * field for Cif_id
		 */


		protected java.lang.String localCif_id ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCif_idTracker = false ;

		public boolean isCif_idSpecified(){
			return localCif_idTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCif_id(){
			return localCif_id;
		}



		/**
		 * Auto generated setter method
		 * @param param Cif_id
		 */
		public void setCif_id(java.lang.String param){
			localCif_idTracker = param != null;

			this.localCif_id=param;


		}


		/**
		 * field for Card_no
		 */


		protected java.lang.String localCard_no ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCard_noTracker = false ;

		public boolean isCard_noSpecified(){
			return localCard_noTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCard_no(){
			return localCard_no;
		}



		/**
		 * Auto generated setter method
		 * @param param Card_no
		 */
		public void setCard_no(java.lang.String param){
			localCard_noTracker = param != null;

			this.localCard_no=param;


		}


		/**
		 * field for Card_currency
		 */


		protected java.lang.String localCard_currency ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCard_currency(){
			return localCard_currency;
		}



		/**
		 * Auto generated setter method
		 * @param param Card_currency
		 */
		public void setCard_currency(java.lang.String param){

			this.localCard_currency=param;


		}


		/**
		 * field for Card_name
		 */


		protected java.lang.String localCard_name ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCard_name(){
			return localCard_name;
		}



		/**
		 * Auto generated setter method
		 * @param param Card_name
		 */
		public void setCard_name(java.lang.String param){

			this.localCard_name=param;


		}


		/**
		 * field for Card_brand
		 */


		protected java.lang.String localCard_brand ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCard_brandTracker = false ;

		public boolean isCard_brandSpecified(){
			return localCard_brandTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCard_brand(){
			return localCard_brand;
		}



		/**
		 * Auto generated setter method
		 * @param param Card_brand
		 */
		public void setCard_brand(java.lang.String param){
			localCard_brandTracker = param != null;

			this.localCard_brand=param;


		}


		/**
		 * field for Card_limit
		 */


		protected java.lang.String localCard_limit ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCard_limit(){
			return localCard_limit;
		}



		/**
		 * Auto generated setter method
		 * @param param Card_limit
		 */
		public void setCard_limit(java.lang.String param){

			this.localCard_limit=param;


		}


		/**
		 * field for Card_limit_percentage
		 */


		protected java.lang.String localCard_limit_percentage ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCard_limit_percentageTracker = false ;

		public boolean isCard_limit_percentageSpecified(){
			return localCard_limit_percentageTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCard_limit_percentage(){
			return localCard_limit_percentage;
		}



		/**
		 * Auto generated setter method
		 * @param param Card_limit_percentage
		 */
		public void setCard_limit_percentage(java.lang.String param){
			localCard_limit_percentageTracker = param != null;

			this.localCard_limit_percentage=param;


		}


		/**
		 * field for Available_credit
		 */


		protected java.lang.String localAvailable_credit ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getAvailable_credit(){
			return localAvailable_credit;
		}



		/**
		 * Auto generated setter method
		 * @param param Available_credit
		 */
		public void setAvailable_credit(java.lang.String param){

			this.localAvailable_credit=param;


		}


		/**
		 * field for Last_credit_limit_increase_date
		 */


		protected java.lang.String localLast_credit_limit_increase_date ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLast_credit_limit_increase_dateTracker = false ;

		public boolean isLast_credit_limit_increase_dateSpecified(){
			return localLast_credit_limit_increase_dateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLast_credit_limit_increase_date(){
			return localLast_credit_limit_increase_date;
		}



		/**
		 * Auto generated setter method
		 * @param param Last_credit_limit_increase_date
		 */
		public void setLast_credit_limit_increase_date(java.lang.String param){
			localLast_credit_limit_increase_dateTracker = param != null;

			this.localLast_credit_limit_increase_date=param;


		}


		/**
		 * field for Highest_balance_date
		 */


		protected java.lang.String localHighest_balance_date ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localHighest_balance_dateTracker = false ;

		public boolean isHighest_balance_dateSpecified(){
			return localHighest_balance_dateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getHighest_balance_date(){
			return localHighest_balance_date;
		}



		/**
		 * Auto generated setter method
		 * @param param Highest_balance_date
		 */
		public void setHighest_balance_date(java.lang.String param){
			localHighest_balance_dateTracker = param != null;

			this.localHighest_balance_date=param;


		}


		/**
		 * field for Si_start_date
		 */


		protected java.lang.String localSi_start_date ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localSi_start_dateTracker = false ;

		public boolean isSi_start_dateSpecified(){
			return localSi_start_dateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getSi_start_date(){
			return localSi_start_date;
		}



		/**
		 * Auto generated setter method
		 * @param param Si_start_date
		 */
		public void setSi_start_date(java.lang.String param){
			localSi_start_dateTracker = param != null;

			this.localSi_start_date=param;


		}


		/**
		 * field for Cash_limit
		 */


		protected java.lang.String localCash_limit ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCash_limit(){
			return localCash_limit;
		}



		/**
		 * Auto generated setter method
		 * @param param Cash_limit
		 */
		public void setCash_limit(java.lang.String param){

			this.localCash_limit=param;


		}


		/**
		 * field for Cash_limit_percentage
		 */


		protected java.lang.String localCash_limit_percentage ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCash_limit_percentageTracker = false ;

		public boolean isCash_limit_percentageSpecified(){
			return localCash_limit_percentageTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCash_limit_percentage(){
			return localCash_limit_percentage;
		}



		/**
		 * Auto generated setter method
		 * @param param Cash_limit_percentage
		 */
		public void setCash_limit_percentage(java.lang.String param){
			localCash_limit_percentageTracker = param != null;

			this.localCash_limit_percentage=param;


		}


		/**
		 * field for Available_cash
		 */


		protected java.lang.String localAvailable_cash ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getAvailable_cash(){
			return localAvailable_cash;
		}



		/**
		 * Auto generated setter method
		 * @param param Available_cash
		 */
		public void setAvailable_cash(java.lang.String param){

			this.localAvailable_cash=param;


		}


		/**
		 * field for Outstanding_balance
		 */


		protected java.lang.String localOutstanding_balance ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getOutstanding_balance(){
			return localOutstanding_balance;
		}



		/**
		 * Auto generated setter method
		 * @param param Outstanding_balance
		 */
		public void setOutstanding_balance(java.lang.String param){

			this.localOutstanding_balance=param;


		}


		/**
		 * field for Current_card_expiry_date
		 */


		protected java.lang.String localCurrent_card_expiry_date ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCurrent_card_expiry_date(){
			return localCurrent_card_expiry_date;
		}



		/**
		 * Auto generated setter method
		 * @param param Current_card_expiry_date
		 */
		public void setCurrent_card_expiry_date(java.lang.String param){

			this.localCurrent_card_expiry_date=param;


		}


		/**
		 * field for Current_card_activation_flag
		 */


		protected java.lang.String localCurrent_card_activation_flag ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCurrent_card_activation_flagTracker = false ;

		public boolean isCurrent_card_activation_flagSpecified(){
			return localCurrent_card_activation_flagTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCurrent_card_activation_flag(){
			return localCurrent_card_activation_flag;
		}



		/**
		 * Auto generated setter method
		 * @param param Current_card_activation_flag
		 */
		public void setCurrent_card_activation_flag(java.lang.String param){
			localCurrent_card_activation_flagTracker = param != null;

			this.localCurrent_card_activation_flag=param;


		}


		/**
		 * field for Last_card_expiry_date
		 */


		protected java.lang.String localLast_card_expiry_date ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLast_card_expiry_dateTracker = false ;

		public boolean isLast_card_expiry_dateSpecified(){
			return localLast_card_expiry_dateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLast_card_expiry_date(){
			return localLast_card_expiry_date;
		}



		/**
		 * Auto generated setter method
		 * @param param Last_card_expiry_date
		 */
		public void setLast_card_expiry_date(java.lang.String param){
			localLast_card_expiry_dateTracker = param != null;

			this.localLast_card_expiry_date=param;


		}


		/**
		 * field for Last_card_activation_flag
		 */


		protected java.lang.String localLast_card_activation_flag ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLast_card_activation_flagTracker = false ;

		public boolean isLast_card_activation_flagSpecified(){
			return localLast_card_activation_flagTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLast_card_activation_flag(){
			return localLast_card_activation_flag;
		}



		/**
		 * Auto generated setter method
		 * @param param Last_card_activation_flag
		 */
		public void setLast_card_activation_flag(java.lang.String param){
			localLast_card_activation_flagTracker = param != null;

			this.localLast_card_activation_flag=param;


		}


		/**
		 * field for Min_payment
		 */


		protected java.lang.String localMin_payment ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getMin_payment(){
			return localMin_payment;
		}



		/**
		 * Auto generated setter method
		 * @param param Min_payment
		 */
		public void setMin_payment(java.lang.String param){

			this.localMin_payment=param;


		}


		/**
		 * field for Due_date
		 */


		protected java.lang.String localDue_date ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDue_date(){
			return localDue_date;
		}



		/**
		 * Auto generated setter method
		 * @param param Due_date
		 */
		public void setDue_date(java.lang.String param){

			this.localDue_date=param;


		}


		/**
		 * field for Due_date_with_grace_period
		 */


		protected java.lang.String localDue_date_with_grace_period ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDue_date_with_grace_periodTracker = false ;

		public boolean isDue_date_with_grace_periodSpecified(){
			return localDue_date_with_grace_periodTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDue_date_with_grace_period(){
			return localDue_date_with_grace_period;
		}



		/**
		 * Auto generated setter method
		 * @param param Due_date_with_grace_period
		 */
		public void setDue_date_with_grace_period(java.lang.String param){
			localDue_date_with_grace_periodTracker = param != null;

			this.localDue_date_with_grace_period=param;


		}


		/**
		 * field for Unbilled_amount
		 */


		protected java.lang.String localUnbilled_amount ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localUnbilled_amountTracker = false ;

		public boolean isUnbilled_amountSpecified(){
			return localUnbilled_amountTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getUnbilled_amount(){
			return localUnbilled_amount;
		}



		/**
		 * Auto generated setter method
		 * @param param Unbilled_amount
		 */
		public void setUnbilled_amount(java.lang.String param){
			localUnbilled_amountTracker = param != null;

			this.localUnbilled_amount=param;


		}


		/**
		 * field for Past_due_flag
		 */


		protected java.lang.String localPast_due_flag ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPast_due_flagTracker = false ;

		public boolean isPast_due_flagSpecified(){
			return localPast_due_flagTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPast_due_flag(){
			return localPast_due_flag;
		}



		/**
		 * Auto generated setter method
		 * @param param Past_due_flag
		 */
		public void setPast_due_flag(java.lang.String param){
			localPast_due_flagTracker = param != null;

			this.localPast_due_flag=param;


		}


		/**
		 * field for Past_due_amount
		 */


		protected java.lang.String localPast_due_amount ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPast_due_amountTracker = false ;

		public boolean isPast_due_amountSpecified(){
			return localPast_due_amountTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPast_due_amount(){
			return localPast_due_amount;
		}



		/**
		 * Auto generated setter method
		 * @param param Past_due_amount
		 */
		public void setPast_due_amount(java.lang.String param){
			localPast_due_amountTracker = param != null;

			this.localPast_due_amount=param;


		}


		/**
		 * field for Product_type
		 */


		protected java.lang.String localProduct_type ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getProduct_type(){
			return localProduct_type;
		}



		/**
		 * Auto generated setter method
		 * @param param Product_type
		 */
		public void setProduct_type(java.lang.String param){

			this.localProduct_type=param;


		}


		/**
		 * field for Card_type
		 */


		protected java.lang.String localCard_type ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCard_typeTracker = false ;

		public boolean isCard_typeSpecified(){
			return localCard_typeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCard_type(){
			return localCard_type;
		}



		/**
		 * Auto generated setter method
		 * @param param Card_type
		 */
		public void setCard_type(java.lang.String param){
			localCard_typeTracker = param != null;

			this.localCard_type=param;


		}


		/**
		 * field for Customer_type
		 */


		protected java.lang.String localCustomer_type ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCustomer_typeTracker = false ;

		public boolean isCustomer_typeSpecified(){
			return localCustomer_typeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCustomer_type(){
			return localCustomer_type;
		}



		/**
		 * Auto generated setter method
		 * @param param Customer_type
		 */
		public void setCustomer_type(java.lang.String param){
			localCustomer_typeTracker = param != null;

			this.localCustomer_type=param;


		}


		/**
		 * field for Card_logo_description
		 */


		protected java.lang.String localCard_logo_description ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCard_logo_descriptionTracker = false ;

		public boolean isCard_logo_descriptionSpecified(){
			return localCard_logo_descriptionTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCard_logo_description(){
			return localCard_logo_description;
		}



		/**
		 * Auto generated setter method
		 * @param param Card_logo_description
		 */
		public void setCard_logo_description(java.lang.String param){
			localCard_logo_descriptionTracker = param != null;

			this.localCard_logo_description=param;


		}


		/**
		 * field for Shadow_credit
		 */


		protected java.lang.String localShadow_credit ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localShadow_creditTracker = false ;

		public boolean isShadow_creditSpecified(){
			return localShadow_creditTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getShadow_credit(){
			return localShadow_credit;
		}



		/**
		 * Auto generated setter method
		 * @param param Shadow_credit
		 */
		public void setShadow_credit(java.lang.String param){
			localShadow_creditTracker = param != null;

			this.localShadow_credit=param;


		}


		/**
		 * field for Shadow_debit
		 */


		protected java.lang.String localShadow_debit ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localShadow_debitTracker = false ;

		public boolean isShadow_debitSpecified(){
			return localShadow_debitTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getShadow_debit(){
			return localShadow_debit;
		}



		/**
		 * Auto generated setter method
		 * @param param Shadow_debit
		 */
		public void setShadow_debit(java.lang.String param){
			localShadow_debitTracker = param != null;

			this.localShadow_debit=param;


		}


		/**
		 * field for Card_block_code
		 */


		protected java.lang.String localCard_block_code ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCard_block_codeTracker = false ;

		public boolean isCard_block_codeSpecified(){
			return localCard_block_codeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCard_block_code(){
			return localCard_block_code;
		}



		/**
		 * Auto generated setter method
		 * @param param Card_block_code
		 */
		public void setCard_block_code(java.lang.String param){
			localCard_block_codeTracker = param != null;

			this.localCard_block_code=param;


		}


		/**
		 * field for Card_block_code_date
		 */


		protected java.lang.String localCard_block_code_date ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCard_block_code_dateTracker = false ;

		public boolean isCard_block_code_dateSpecified(){
			return localCard_block_code_dateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCard_block_code_date(){
			return localCard_block_code_date;
		}



		/**
		 * Auto generated setter method
		 * @param param Card_block_code_date
		 */
		public void setCard_block_code_date(java.lang.String param){
			localCard_block_code_dateTracker = param != null;

			this.localCard_block_code_date=param;


		}


		/**
		 * field for Account_block_date1
		 */


		protected java.lang.String localAccount_block_date1 ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAccount_block_date1Tracker = false ;

		public boolean isAccount_block_date1Specified(){
			return localAccount_block_date1Tracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getAccount_block_date1(){
			return localAccount_block_date1;
		}



		/**
		 * Auto generated setter method
		 * @param param Account_block_date1
		 */
		public void setAccount_block_date1(java.lang.String param){
			localAccount_block_date1Tracker = param != null;

			this.localAccount_block_date1=param;


		}


		/**
		 * field for Account_block_date2
		 */


		protected java.lang.String localAccount_block_date2 ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAccount_block_date2Tracker = false ;

		public boolean isAccount_block_date2Specified(){
			return localAccount_block_date2Tracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getAccount_block_date2(){
			return localAccount_block_date2;
		}



		/**
		 * Auto generated setter method
		 * @param param Account_block_date2
		 */
		public void setAccount_block_date2(java.lang.String param){
			localAccount_block_date2Tracker = param != null;

			this.localAccount_block_date2=param;


		}


		/**
		 * field for Account_block_code1
		 */


		protected java.lang.String localAccount_block_code1 ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAccount_block_code1Tracker = false ;

		public boolean isAccount_block_code1Specified(){
			return localAccount_block_code1Tracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getAccount_block_code1(){
			return localAccount_block_code1;
		}



		/**
		 * Auto generated setter method
		 * @param param Account_block_code1
		 */
		public void setAccount_block_code1(java.lang.String param){
			localAccount_block_code1Tracker = param != null;

			this.localAccount_block_code1=param;


		}


		/**
		 * field for Account_block_code2
		 */


		protected java.lang.String localAccount_block_code2 ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAccount_block_code2Tracker = false ;

		public boolean isAccount_block_code2Specified(){
			return localAccount_block_code2Tracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getAccount_block_code2(){
			return localAccount_block_code2;
		}



		/**
		 * Auto generated setter method
		 * @param param Account_block_code2
		 */
		public void setAccount_block_code2(java.lang.String param){
			localAccount_block_code2Tracker = param != null;

			this.localAccount_block_code2=param;


		}


		/**
		 * field for Last_statement_date
		 */


		protected java.lang.String localLast_statement_date ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLast_statement_dateTracker = false ;

		public boolean isLast_statement_dateSpecified(){
			return localLast_statement_dateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLast_statement_date(){
			return localLast_statement_date;
		}



		/**
		 * Auto generated setter method
		 * @param param Last_statement_date
		 */
		public void setLast_statement_date(java.lang.String param){
			localLast_statement_dateTracker = param != null;

			this.localLast_statement_date=param;


		}


		/**
		 * field for Statement_opening_balance
		 */


		protected java.lang.String localStatement_opening_balance ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localStatement_opening_balanceTracker = false ;

		public boolean isStatement_opening_balanceSpecified(){
			return localStatement_opening_balanceTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getStatement_opening_balance(){
			return localStatement_opening_balance;
		}



		/**
		 * Auto generated setter method
		 * @param param Statement_opening_balance
		 */
		public void setStatement_opening_balance(java.lang.String param){
			localStatement_opening_balanceTracker = param != null;

			this.localStatement_opening_balance=param;


		}


		/**
		 * field for Statement_closing_balance
		 */


		protected java.lang.String localStatement_closing_balance ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localStatement_closing_balanceTracker = false ;

		public boolean isStatement_closing_balanceSpecified(){
			return localStatement_closing_balanceTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getStatement_closing_balance(){
			return localStatement_closing_balance;
		}



		/**
		 * Auto generated setter method
		 * @param param Statement_closing_balance
		 */
		public void setStatement_closing_balance(java.lang.String param){
			localStatement_closing_balanceTracker = param != null;

			this.localStatement_closing_balance=param;


		}


		/**
		 * field for Statement_outstanding_balance
		 */


		protected java.lang.String localStatement_outstanding_balance ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localStatement_outstanding_balanceTracker = false ;

		public boolean isStatement_outstanding_balanceSpecified(){
			return localStatement_outstanding_balanceTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getStatement_outstanding_balance(){
			return localStatement_outstanding_balance;
		}



		/**
		 * Auto generated setter method
		 * @param param Statement_outstanding_balance
		 */
		public void setStatement_outstanding_balance(java.lang.String param){
			localStatement_outstanding_balanceTracker = param != null;

			this.localStatement_outstanding_balance=param;


		}


		/**
		 * field for Statement_due
		 */


		protected java.lang.String localStatement_due ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localStatement_dueTracker = false ;

		public boolean isStatement_dueSpecified(){
			return localStatement_dueTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getStatement_due(){
			return localStatement_due;
		}



		/**
		 * Auto generated setter method
		 * @param param Statement_due
		 */
		public void setStatement_due(java.lang.String param){
			localStatement_dueTracker = param != null;

			this.localStatement_due=param;


		}


		/**
		 * field for Next_statement_date
		 */


		protected java.lang.String localNext_statement_date ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNext_statement_dateTracker = false ;

		public boolean isNext_statement_dateSpecified(){
			return localNext_statement_dateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNext_statement_date(){
			return localNext_statement_date;
		}



		/**
		 * Auto generated setter method
		 * @param param Next_statement_date
		 */
		public void setNext_statement_date(java.lang.String param){
			localNext_statement_dateTracker = param != null;

			this.localNext_statement_date=param;


		}


		/**
		 * field for Card_account_no
		 */


		protected java.lang.String localCard_account_no ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCard_account_noTracker = false ;

		public boolean isCard_account_noSpecified(){
			return localCard_account_noTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCard_account_no(){
			return localCard_account_no;
		}



		/**
		 * Auto generated setter method
		 * @param param Card_account_no
		 */
		public void setCard_account_no(java.lang.String param){
			localCard_account_noTracker = param != null;

			this.localCard_account_no=param;


		}


		/**
		 * field for Relationship_start_date
		 */


		protected java.lang.String localRelationship_start_date ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localRelationship_start_dateTracker = false ;

		public boolean isRelationship_start_dateSpecified(){
			return localRelationship_start_dateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getRelationship_start_date(){
			return localRelationship_start_date;
		}



		/**
		 * Auto generated setter method
		 * @param param Relationship_start_date
		 */
		public void setRelationship_start_date(java.lang.String param){
			localRelationship_start_dateTracker = param != null;

			this.localRelationship_start_date=param;


		}


		/**
		 * field for Card_account_limit
		 */


		protected java.lang.String localCard_account_limit ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCard_account_limitTracker = false ;

		public boolean isCard_account_limitSpecified(){
			return localCard_account_limitTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCard_account_limit(){
			return localCard_account_limit;
		}



		/**
		 * Auto generated setter method
		 * @param param Card_account_limit
		 */
		public void setCard_account_limit(java.lang.String param){
			localCard_account_limitTracker = param != null;

			this.localCard_account_limit=param;


		}


		/**
		 * field for Card_account_available_credit
		 */


		protected java.lang.String localCard_account_available_credit ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCard_account_available_creditTracker = false ;

		public boolean isCard_account_available_creditSpecified(){
			return localCard_account_available_creditTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCard_account_available_credit(){
			return localCard_account_available_credit;
		}



		/**
		 * Auto generated setter method
		 * @param param Card_account_available_credit
		 */
		public void setCard_account_available_credit(java.lang.String param){
			localCard_account_available_creditTracker = param != null;

			this.localCard_account_available_credit=param;


		}


		/**
		 * field for Overlimit_flag
		 */


		protected java.lang.String localOverlimit_flag ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localOverlimit_flagTracker = false ;

		public boolean isOverlimit_flagSpecified(){
			return localOverlimit_flagTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getOverlimit_flag(){
			return localOverlimit_flag;
		}



		/**
		 * Auto generated setter method
		 * @param param Overlimit_flag
		 */
		public void setOverlimit_flag(java.lang.String param){
			localOverlimit_flagTracker = param != null;

			this.localOverlimit_flag=param;


		}


		/**
		 * field for Overlimit_amount
		 */


		protected java.lang.String localOverlimit_amount ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localOverlimit_amountTracker = false ;

		public boolean isOverlimit_amountSpecified(){
			return localOverlimit_amountTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getOverlimit_amount(){
			return localOverlimit_amount;
		}



		/**
		 * Auto generated setter method
		 * @param param Overlimit_amount
		 */
		public void setOverlimit_amount(java.lang.String param){
			localOverlimit_amountTracker = param != null;

			this.localOverlimit_amount=param;


		}


		/**
		 * field for Direct_debit_flag
		 */


		protected java.lang.String localDirect_debit_flag ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDirect_debit_flagTracker = false ;

		public boolean isDirect_debit_flagSpecified(){
			return localDirect_debit_flagTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDirect_debit_flag(){
			return localDirect_debit_flag;
		}



		/**
		 * Auto generated setter method
		 * @param param Direct_debit_flag
		 */
		public void setDirect_debit_flag(java.lang.String param){
			localDirect_debit_flagTracker = param != null;

			this.localDirect_debit_flag=param;


		}


		/**
		 * field for Direct_debit_account
		 */


		protected java.lang.String localDirect_debit_account ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDirect_debit_accountTracker = false ;

		public boolean isDirect_debit_accountSpecified(){
			return localDirect_debit_accountTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDirect_debit_account(){
			return localDirect_debit_account;
		}



		/**
		 * Auto generated setter method
		 * @param param Direct_debit_account
		 */
		public void setDirect_debit_account(java.lang.String param){
			localDirect_debit_accountTracker = param != null;

			this.localDirect_debit_account=param;


		}


		/**
		 * field for Direct_debit_percentage
		 */


		protected java.lang.String localDirect_debit_percentage ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDirect_debit_percentageTracker = false ;

		public boolean isDirect_debit_percentageSpecified(){
			return localDirect_debit_percentageTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDirect_debit_percentage(){
			return localDirect_debit_percentage;
		}



		/**
		 * Auto generated setter method
		 * @param param Direct_debit_percentage
		 */
		public void setDirect_debit_percentage(java.lang.String param){
			localDirect_debit_percentageTracker = param != null;

			this.localDirect_debit_percentage=param;


		}


		/**
		 * field for Card_branch_code
		 */


		protected java.lang.String localCard_branch_code ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCard_branch_codeTracker = false ;

		public boolean isCard_branch_codeSpecified(){
			return localCard_branch_codeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCard_branch_code(){
			return localCard_branch_code;
		}



		/**
		 * Auto generated setter method
		 * @param param Card_branch_code
		 */
		public void setCard_branch_code(java.lang.String param){
			localCard_branch_codeTracker = param != null;

			this.localCard_branch_code=param;


		}


		/**
		 * field for Card_pct
		 */


		protected java.lang.String localCard_pct ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCard_pctTracker = false ;

		public boolean isCard_pctSpecified(){
			return localCard_pctTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCard_pct(){
			return localCard_pct;
		}



		/**
		 * Auto generated setter method
		 * @param param Card_pct
		 */
		public void setCard_pct(java.lang.String param){
			localCard_pctTracker = param != null;

			this.localCard_pct=param;


		}


		/**
		 * field for Last_payment_date
		 */


		protected java.lang.String localLast_payment_date ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLast_payment_dateTracker = false ;

		public boolean isLast_payment_dateSpecified(){
			return localLast_payment_dateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLast_payment_date(){
			return localLast_payment_date;
		}



		/**
		 * Auto generated setter method
		 * @param param Last_payment_date
		 */
		public void setLast_payment_date(java.lang.String param){
			localLast_payment_dateTracker = param != null;

			this.localLast_payment_date=param;


		}


		/**
		 * field for Last_payment_amount
		 */


		protected java.lang.String localLast_payment_amount ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLast_payment_amountTracker = false ;

		public boolean isLast_payment_amountSpecified(){
			return localLast_payment_amountTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLast_payment_amount(){
			return localLast_payment_amount;
		}



		/**
		 * Auto generated setter method
		 * @param param Last_payment_amount
		 */
		public void setLast_payment_amount(java.lang.String param){
			localLast_payment_amountTracker = param != null;

			this.localLast_payment_amount=param;


		}


		/**
		 * field for Last_purchase_date
		 */


		protected java.lang.String localLast_purchase_date ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLast_purchase_dateTracker = false ;

		public boolean isLast_purchase_dateSpecified(){
			return localLast_purchase_dateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLast_purchase_date(){
			return localLast_purchase_date;
		}



		/**
		 * Auto generated setter method
		 * @param param Last_purchase_date
		 */
		public void setLast_purchase_date(java.lang.String param){
			localLast_purchase_dateTracker = param != null;

			this.localLast_purchase_date=param;


		}


		/**
		 * field for Last_purchase_amount
		 */


		protected java.lang.String localLast_purchase_amount ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLast_purchase_amountTracker = false ;

		public boolean isLast_purchase_amountSpecified(){
			return localLast_purchase_amountTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLast_purchase_amount(){
			return localLast_purchase_amount;
		}



		/**
		 * Auto generated setter method
		 * @param param Last_purchase_amount
		 */
		public void setLast_purchase_amount(java.lang.String param){
			localLast_purchase_amountTracker = param != null;

			this.localLast_purchase_amount=param;


		}


		/**
		 * field for Embosser_last_maintenance_date
		 */


		protected java.lang.String localEmbosser_last_maintenance_date ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localEmbosser_last_maintenance_dateTracker = false ;

		public boolean isEmbosser_last_maintenance_dateSpecified(){
			return localEmbosser_last_maintenance_dateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getEmbosser_last_maintenance_date(){
			return localEmbosser_last_maintenance_date;
		}



		/**
		 * Auto generated setter method
		 * @param param Embosser_last_maintenance_date
		 */
		public void setEmbosser_last_maintenance_date(java.lang.String param){
			localEmbosser_last_maintenance_dateTracker = param != null;

			this.localEmbosser_last_maintenance_date=param;


		}


		/**
		 * field for Total_cycle_debit
		 */


		protected java.lang.String localTotal_cycle_debit ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localTotal_cycle_debitTracker = false ;

		public boolean isTotal_cycle_debitSpecified(){
			return localTotal_cycle_debitTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getTotal_cycle_debit(){
			return localTotal_cycle_debit;
		}



		/**
		 * Auto generated setter method
		 * @param param Total_cycle_debit
		 */
		public void setTotal_cycle_debit(java.lang.String param){
			localTotal_cycle_debitTracker = param != null;

			this.localTotal_cycle_debit=param;


		}


		/**
		 * field for Total_cycle_credit
		 */


		protected java.lang.String localTotal_cycle_credit ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localTotal_cycle_creditTracker = false ;

		public boolean isTotal_cycle_creditSpecified(){
			return localTotal_cycle_creditTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getTotal_cycle_credit(){
			return localTotal_cycle_credit;
		}



		/**
		 * Auto generated setter method
		 * @param param Total_cycle_credit
		 */
		public void setTotal_cycle_credit(java.lang.String param){
			localTotal_cycle_creditTracker = param != null;

			this.localTotal_cycle_credit=param;


		}


		/**
		 * field for Cycle_payments
		 */


		protected java.lang.String localCycle_payments ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCycle_paymentsTracker = false ;

		public boolean isCycle_paymentsSpecified(){
			return localCycle_paymentsTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCycle_payments(){
			return localCycle_payments;
		}



		/**
		 * Auto generated setter method
		 * @param param Cycle_payments
		 */
		public void setCycle_payments(java.lang.String param){
			localCycle_paymentsTracker = param != null;

			this.localCycle_payments=param;


		}


		/**
		 * field for Ytd_retail_purchase
		 */


		protected java.lang.String localYtd_retail_purchase ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localYtd_retail_purchaseTracker = false ;

		public boolean isYtd_retail_purchaseSpecified(){
			return localYtd_retail_purchaseTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getYtd_retail_purchase(){
			return localYtd_retail_purchase;
		}



		/**
		 * Auto generated setter method
		 * @param param Ytd_retail_purchase
		 */
		public void setYtd_retail_purchase(java.lang.String param){
			localYtd_retail_purchaseTracker = param != null;

			this.localYtd_retail_purchase=param;


		}


		/**
		 * field for Ytd_cash
		 */


		protected java.lang.String localYtd_cash ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localYtd_cashTracker = false ;

		public boolean isYtd_cashSpecified(){
			return localYtd_cashTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getYtd_cash(){
			return localYtd_cash;
		}



		/**
		 * Auto generated setter method
		 * @param param Ytd_cash
		 */
		public void setYtd_cash(java.lang.String param){
			localYtd_cashTracker = param != null;

			this.localYtd_cash=param;


		}


		/**
		 * field for Ytd_retail_returns
		 */


		protected java.lang.String localYtd_retail_returns ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localYtd_retail_returnsTracker = false ;

		public boolean isYtd_retail_returnsSpecified(){
			return localYtd_retail_returnsTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getYtd_retail_returns(){
			return localYtd_retail_returns;
		}



		/**
		 * Auto generated setter method
		 * @param param Ytd_retail_returns
		 */
		public void setYtd_retail_returns(java.lang.String param){
			localYtd_retail_returnsTracker = param != null;

			this.localYtd_retail_returns=param;


		}


		/**
		 * field for Ltd_purhcase
		 */


		protected java.lang.String localLtd_purhcase ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLtd_purhcaseTracker = false ;

		public boolean isLtd_purhcaseSpecified(){
			return localLtd_purhcaseTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLtd_purhcase(){
			return localLtd_purhcase;
		}



		/**
		 * Auto generated setter method
		 * @param param Ltd_purhcase
		 */
		public void setLtd_purhcase(java.lang.String param){
			localLtd_purhcaseTracker = param != null;

			this.localLtd_purhcase=param;


		}


		/**
		 * field for Ltd_cash
		 */


		protected java.lang.String localLtd_cash ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLtd_cashTracker = false ;

		public boolean isLtd_cashSpecified(){
			return localLtd_cashTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLtd_cash(){
			return localLtd_cash;
		}



		/**
		 * Auto generated setter method
		 * @param param Ltd_cash
		 */
		public void setLtd_cash(java.lang.String param){
			localLtd_cashTracker = param != null;

			this.localLtd_cash=param;


		}


		/**
		 * field for Ltd_retail_purchase
		 */


		protected java.lang.String localLtd_retail_purchase ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLtd_retail_purchaseTracker = false ;

		public boolean isLtd_retail_purchaseSpecified(){
			return localLtd_retail_purchaseTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLtd_retail_purchase(){
			return localLtd_retail_purchase;
		}



		/**
		 * Auto generated setter method
		 * @param param Ltd_retail_purchase
		 */
		public void setLtd_retail_purchase(java.lang.String param){
			localLtd_retail_purchaseTracker = param != null;

			this.localLtd_retail_purchase=param;


		}


		/**
		 * field for Ctd_retail_purchases
		 */


		protected java.lang.String localCtd_retail_purchases ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCtd_retail_purchasesTracker = false ;

		public boolean isCtd_retail_purchasesSpecified(){
			return localCtd_retail_purchasesTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCtd_retail_purchases(){
			return localCtd_retail_purchases;
		}



		/**
		 * Auto generated setter method
		 * @param param Ctd_retail_purchases
		 */
		public void setCtd_retail_purchases(java.lang.String param){
			localCtd_retail_purchasesTracker = param != null;

			this.localCtd_retail_purchases=param;


		}


		/**
		 * field for Ctd_cash
		 */


		protected java.lang.String localCtd_cash ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCtd_cashTracker = false ;

		public boolean isCtd_cashSpecified(){
			return localCtd_cashTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCtd_cash(){
			return localCtd_cash;
		}



		/**
		 * Auto generated setter method
		 * @param param Ctd_cash
		 */
		public void setCtd_cash(java.lang.String param){
			localCtd_cashTracker = param != null;

			this.localCtd_cash=param;


		}


		/**
		 * field for Ctd_retail_returns
		 */


		protected java.lang.String localCtd_retail_returns ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCtd_retail_returnsTracker = false ;

		public boolean isCtd_retail_returnsSpecified(){
			return localCtd_retail_returnsTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCtd_retail_returns(){
			return localCtd_retail_returns;
		}



		/**
		 * Auto generated setter method
		 * @param param Ctd_retail_returns
		 */
		public void setCtd_retail_returns(java.lang.String param){
			localCtd_retail_returnsTracker = param != null;

			this.localCtd_retail_returns=param;


		}


		/**
		 * field for Interest_free
		 */


		protected java.lang.String localInterest_free ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localInterest_freeTracker = false ;

		public boolean isInterest_freeSpecified(){
			return localInterest_freeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getInterest_free(){
			return localInterest_free;
		}



		/**
		 * Auto generated setter method
		 * @param param Interest_free
		 */
		public void setInterest_free(java.lang.String param){
			localInterest_freeTracker = param != null;

			this.localInterest_free=param;


		}


		/**
		 * field for Last_credit_limit
		 */


		protected java.lang.String localLast_credit_limit ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localLast_credit_limitTracker = false ;

		public boolean isLast_credit_limitSpecified(){
			return localLast_credit_limitTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getLast_credit_limit(){
			return localLast_credit_limit;
		}



		/**
		 * Auto generated setter method
		 * @param param Last_credit_limit
		 */
		public void setLast_credit_limit(java.lang.String param){
			localLast_credit_limitTracker = param != null;

			this.localLast_credit_limit=param;


		}


		/**
		 * field for Highest_balance
		 */


		protected java.lang.String localHighest_balance ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localHighest_balanceTracker = false ;

		public boolean isHighest_balanceSpecified(){
			return localHighest_balanceTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getHighest_balance(){
			return localHighest_balance;
		}



		/**
		 * Auto generated setter method
		 * @param param Highest_balance
		 */
		public void setHighest_balance(java.lang.String param){
			localHighest_balanceTracker = param != null;

			this.localHighest_balance=param;


		}


		/**
		 * field for Billing_cycle
		 */


		protected java.lang.String localBilling_cycle ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localBilling_cycleTracker = false ;

		public boolean isBilling_cycleSpecified(){
			return localBilling_cycleTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getBilling_cycle(){
			return localBilling_cycle;
		}



		/**
		 * Auto generated setter method
		 * @param param Billing_cycle
		 */
		public void setBilling_cycle(java.lang.String param){
			localBilling_cycleTracker = param != null;

			this.localBilling_cycle=param;


		}


		/**
		 * field for Next_card_fee_recovery_date
		 */


		protected java.lang.String localNext_card_fee_recovery_date ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNext_card_fee_recovery_dateTracker = false ;

		public boolean isNext_card_fee_recovery_dateSpecified(){
			return localNext_card_fee_recovery_dateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNext_card_fee_recovery_date(){
			return localNext_card_fee_recovery_date;
		}



		/**
		 * Auto generated setter method
		 * @param param Next_card_fee_recovery_date
		 */
		public void setNext_card_fee_recovery_date(java.lang.String param){
			localNext_card_fee_recovery_dateTracker = param != null;

			this.localNext_card_fee_recovery_date=param;


		}


		/**
		 * field for Setup_date
		 */


		protected java.lang.String localSetup_date ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localSetup_dateTracker = false ;

		public boolean isSetup_dateSpecified(){
			return localSetup_dateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getSetup_date(){
			return localSetup_date;
		}



		/**
		 * Auto generated setter method
		 * @param param Setup_date
		 */
		public void setSetup_date(java.lang.String param){
			localSetup_dateTracker = param != null;

			this.localSetup_date=param;


		}


		/**
		 * field for Card_creation_date
		 */


		protected java.lang.String localCard_creation_date ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCard_creation_dateTracker = false ;

		public boolean isCard_creation_dateSpecified(){
			return localCard_creation_dateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCard_creation_date(){
			return localCard_creation_date;
		}



		/**
		 * Auto generated setter method
		 * @param param Card_creation_date
		 */
		public void setCard_creation_date(java.lang.String param){
			localCard_creation_dateTracker = param != null;

			this.localCard_creation_date=param;


		}


		/**
		 * field for Card_issuance_date
		 */


		protected java.lang.String localCard_issuance_date ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCard_issuance_dateTracker = false ;

		public boolean isCard_issuance_dateSpecified(){
			return localCard_issuance_dateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCard_issuance_date(){
			return localCard_issuance_date;
		}



		/**
		 * Auto generated setter method
		 * @param param Card_issuance_date
		 */
		public void setCard_issuance_date(java.lang.String param){
			localCard_issuance_dateTracker = param != null;

			this.localCard_issuance_date=param;


		}


		/**
		 * field for Card_activation_date
		 */


		protected java.lang.String localCard_activation_date ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCard_activation_dateTracker = false ;

		public boolean isCard_activation_dateSpecified(){
			return localCard_activation_dateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCard_activation_date(){
			return localCard_activation_date;
		}



		/**
		 * Auto generated setter method
		 * @param param Card_activation_date
		 */
		public void setCard_activation_date(java.lang.String param){
			localCard_activation_dateTracker = param != null;

			this.localCard_activation_date=param;


		}


		/**
		 * field for Direct_debit_request_day
		 */


		protected java.lang.String localDirect_debit_request_day ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDirect_debit_request_dayTracker = false ;

		public boolean isDirect_debit_request_daySpecified(){
			return localDirect_debit_request_dayTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDirect_debit_request_day(){
			return localDirect_debit_request_day;
		}



		/**
		 * Auto generated setter method
		 * @param param Direct_debit_request_day
		 */
		public void setDirect_debit_request_day(java.lang.String param){
			localDirect_debit_request_dayTracker = param != null;

			this.localDirect_debit_request_day=param;


		}


		/**
		 * field for Current_credit_limit_date
		 */


		protected java.lang.String localCurrent_credit_limit_date ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCurrent_credit_limit_dateTracker = false ;

		public boolean isCurrent_credit_limit_dateSpecified(){
			return localCurrent_credit_limit_dateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCurrent_credit_limit_date(){
			return localCurrent_credit_limit_date;
		}



		/**
		 * Auto generated setter method
		 * @param param Current_credit_limit_date
		 */
		public void setCurrent_credit_limit_date(java.lang.String param){
			localCurrent_credit_limit_dateTracker = param != null;

			this.localCurrent_credit_limit_date=param;


		}


		/**
		 * field for No_of_plans
		 */


		protected java.lang.String localNo_of_plans ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localNo_of_plansTracker = false ;

		public boolean isNo_of_plansSpecified(){
			return localNo_of_plansTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNo_of_plans(){
			return localNo_of_plans;
		}



		/**
		 * Auto generated setter method
		 * @param param No_of_plans
		 */
		public void setNo_of_plans(java.lang.String param){
			localNo_of_plansTracker = param != null;

			this.localNo_of_plans=param;


		}


		/**
		 * field for EStatementSubFlag
		 */


		protected java.lang.String localEStatementSubFlag ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localEStatementSubFlagTracker = false ;

		public boolean isEStatementSubFlagSpecified(){
			return localEStatementSubFlagTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getEStatementSubFlag(){
			return localEStatementSubFlag;
		}



		/**
		 * Auto generated setter method
		 * @param param EStatementSubFlag
		 */
		public void setEStatementSubFlag(java.lang.String param){
			localEStatementSubFlagTracker = param != null;

			this.localEStatementSubFlag=param;


		}


		/**
		 * field for EStatementSubDate
		 */


		protected java.lang.String localEStatementSubDate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localEStatementSubDateTracker = false ;

		public boolean isEStatementSubDateSpecified(){
			return localEStatementSubDateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getEStatementSubDate(){
			return localEStatementSubDate;
		}



		/**
		 * Auto generated setter method
		 * @param param EStatementSubDate
		 */
		public void setEStatementSubDate(java.lang.String param){
			localEStatementSubDateTracker = param != null;

			this.localEStatementSubDate=param;


		}


		/**
		 * field for TotalLULUPoint
		 */


		protected java.lang.String localTotalLULUPoint ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localTotalLULUPointTracker = false ;

		public boolean isTotalLULUPointSpecified(){
			return localTotalLULUPointTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getTotalLULUPoint(){
			return localTotalLULUPoint;
		}



		/**
		 * Auto generated setter method
		 * @param param TotalLULUPoint
		 */
		public void setTotalLULUPoint(java.lang.String param){
			localTotalLULUPointTracker = param != null;

			this.localTotalLULUPoint=param;


		}


		/**
		 * field for Insurance_products
		 * This was an Array!
		 */


		protected Insurance_products_type0[] localInsurance_products ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localInsurance_productsTracker = false ;

		public boolean isInsurance_productsSpecified(){
			return localInsurance_productsTracker;
		}



		/**
		 * Auto generated getter method
		 * @return Insurance_products_type0[]
		 */
		public  Insurance_products_type0[] getInsurance_products(){
			return localInsurance_products;
		}






		/**
		 * validate the array for Insurance_products
		 */
		protected void validateInsurance_products(Insurance_products_type0[] param){

		}


		/**
		 * Auto generated setter method
		 * @param param Insurance_products
		 */
		public void setInsurance_products(Insurance_products_type0[] param){

			validateInsurance_products(param);

			localInsurance_productsTracker = param != null;

			this.localInsurance_products=param;
		}



		/**
		 * Auto generated add method for the array for convenience
		 * @param param Insurance_products_type0
		 */
		public void addInsurance_products(Insurance_products_type0 param){
			if (localInsurance_products == null){
				localInsurance_products = new Insurance_products_type0[]{};
			}


			//update the setting tracker
			localInsurance_productsTracker = true;


			java.util.List list =
					org.apache.axis2.databinding.utils.ConverterUtil.toList(localInsurance_products);
			list.add(param);
			this.localInsurance_products =
					(Insurance_products_type0[])list.toArray(
							new Insurance_products_type0[list.size()]);

		}


		/**
		 * field for Installment_plans
		 * This was an Array!
		 */


		protected Installment_plans_type0[] localInstallment_plans ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localInstallment_plansTracker = false ;

		public boolean isInstallment_plansSpecified(){
			return localInstallment_plansTracker;
		}



		/**
		 * Auto generated getter method
		 * @return Installment_plans_type0[]
		 */
		public  Installment_plans_type0[] getInstallment_plans(){
			return localInstallment_plans;
		}






		/**
		 * validate the array for Installment_plans
		 */
		protected void validateInstallment_plans(Installment_plans_type0[] param){

		}


		/**
		 * Auto generated setter method
		 * @param param Installment_plans
		 */
		public void setInstallment_plans(Installment_plans_type0[] param){

			validateInstallment_plans(param);

			localInstallment_plansTracker = param != null;

			this.localInstallment_plans=param;
		}



		/**
		 * Auto generated add method for the array for convenience
		 * @param param Installment_plans_type0
		 */
		public void addInstallment_plans(Installment_plans_type0 param){
			if (localInstallment_plans == null){
				localInstallment_plans = new Installment_plans_type0[]{};
			}


			//update the setting tracker
			localInstallment_plansTracker = true;


			java.util.List list =
					org.apache.axis2.databinding.utils.ConverterUtil.toList(localInstallment_plans);
			list.add(param);
			this.localInstallment_plans =
					(Installment_plans_type0[])list.toArray(
							new Installment_plans_type0[list.size()]);

		}


		/**
		 * field for Plans
		 * This was an Array!
		 */


		protected Plans_type0[] localPlans ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPlansTracker = false ;

		public boolean isPlansSpecified(){
			return localPlansTracker;
		}



		/**
		 * Auto generated getter method
		 * @return Plans_type0[]
		 */
		public  Plans_type0[] getPlans(){
			return localPlans;
		}






		/**
		 * validate the array for Plans
		 */
		protected void validatePlans(Plans_type0[] param){

		}


		/**
		 * Auto generated setter method
		 * @param param Plans
		 */
		public void setPlans(Plans_type0[] param){

			validatePlans(param);

			localPlansTracker = param != null;

			this.localPlans=param;
		}



		/**
		 * Auto generated add method for the array for convenience
		 * @param param Plans_type0
		 */
		public void addPlans(Plans_type0 param){
			if (localPlans == null){
				localPlans = new Plans_type0[]{};
			}


			//update the setting tracker
			localPlansTracker = true;


			java.util.List list =
					org.apache.axis2.databinding.utils.ConverterUtil.toList(localPlans);
			list.add(param);
			this.localPlans =
					(Plans_type0[])list.toArray(
							new Plans_type0[list.size()]);

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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":card_details_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"card_details_type0",
							xmlWriter);
				}


			}
			if (localCif_idTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "cif_id", xmlWriter);


				if (localCif_id==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("cif_id cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCif_id);

				}

				xmlWriter.writeEndElement();
			} if (localCard_noTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "card_no", xmlWriter);


				if (localCard_no==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("card_no cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCard_no);

				}

				xmlWriter.writeEndElement();
			}
			namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
			writeStartElement(null, namespace, "card_currency", xmlWriter);


			if (localCard_currency==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("card_currency cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localCard_currency);

			}

			xmlWriter.writeEndElement();

			namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
			writeStartElement(null, namespace, "card_name", xmlWriter);


			if (localCard_name==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("card_name cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localCard_name);

			}

			xmlWriter.writeEndElement();
			if (localCard_brandTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "card_brand", xmlWriter);


				if (localCard_brand==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("card_brand cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCard_brand);

				}

				xmlWriter.writeEndElement();
			}
			namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
			writeStartElement(null, namespace, "card_limit", xmlWriter);


			if (localCard_limit==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("card_limit cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localCard_limit);

			}

			xmlWriter.writeEndElement();
			if (localCard_limit_percentageTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "card_limit_percentage", xmlWriter);


				if (localCard_limit_percentage==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("card_limit_percentage cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCard_limit_percentage);

				}

				xmlWriter.writeEndElement();
			}
			namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
			writeStartElement(null, namespace, "available_credit", xmlWriter);


			if (localAvailable_credit==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("available_credit cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localAvailable_credit);

			}

			xmlWriter.writeEndElement();
			if (localLast_credit_limit_increase_dateTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "last_credit_limit_increase_date", xmlWriter);


				if (localLast_credit_limit_increase_date==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("last_credit_limit_increase_date cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLast_credit_limit_increase_date);

				}

				xmlWriter.writeEndElement();
			} if (localHighest_balance_dateTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "highest_balance_date", xmlWriter);


				if (localHighest_balance_date==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("highest_balance_date cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localHighest_balance_date);

				}

				xmlWriter.writeEndElement();
			} if (localSi_start_dateTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "si_start_date", xmlWriter);


				if (localSi_start_date==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("si_start_date cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localSi_start_date);

				}

				xmlWriter.writeEndElement();
			}
			namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
			writeStartElement(null, namespace, "cash_limit", xmlWriter);


			if (localCash_limit==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("cash_limit cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localCash_limit);

			}

			xmlWriter.writeEndElement();
			if (localCash_limit_percentageTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "cash_limit_percentage", xmlWriter);


				if (localCash_limit_percentage==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("cash_limit_percentage cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCash_limit_percentage);

				}

				xmlWriter.writeEndElement();
			}
			namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
			writeStartElement(null, namespace, "available_cash", xmlWriter);


			if (localAvailable_cash==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("available_cash cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localAvailable_cash);

			}

			xmlWriter.writeEndElement();

			namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
			writeStartElement(null, namespace, "outstanding_balance", xmlWriter);


			if (localOutstanding_balance==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("outstanding_balance cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localOutstanding_balance);

			}

			xmlWriter.writeEndElement();

			namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
			writeStartElement(null, namespace, "current_card_expiry_date", xmlWriter);


			if (localCurrent_card_expiry_date==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("current_card_expiry_date cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localCurrent_card_expiry_date);

			}

			xmlWriter.writeEndElement();
			if (localCurrent_card_activation_flagTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "current_card_activation_flag", xmlWriter);


				if (localCurrent_card_activation_flag==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("current_card_activation_flag cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCurrent_card_activation_flag);

				}

				xmlWriter.writeEndElement();
			} if (localLast_card_expiry_dateTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "last_card_expiry_date", xmlWriter);


				if (localLast_card_expiry_date==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("last_card_expiry_date cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLast_card_expiry_date);

				}

				xmlWriter.writeEndElement();
			} if (localLast_card_activation_flagTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "last_card_activation_flag", xmlWriter);


				if (localLast_card_activation_flag==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("last_card_activation_flag cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLast_card_activation_flag);

				}

				xmlWriter.writeEndElement();
			}
			namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
			writeStartElement(null, namespace, "min_payment", xmlWriter);


			if (localMin_payment==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("min_payment cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localMin_payment);

			}

			xmlWriter.writeEndElement();

			namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
			writeStartElement(null, namespace, "due_date", xmlWriter);


			if (localDue_date==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("due_date cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localDue_date);

			}

			xmlWriter.writeEndElement();
			if (localDue_date_with_grace_periodTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "due_date_with_grace_period", xmlWriter);


				if (localDue_date_with_grace_period==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("due_date_with_grace_period cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDue_date_with_grace_period);

				}

				xmlWriter.writeEndElement();
			} if (localUnbilled_amountTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "unbilled_amount", xmlWriter);


				if (localUnbilled_amount==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("unbilled_amount cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localUnbilled_amount);

				}

				xmlWriter.writeEndElement();
			} if (localPast_due_flagTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "past_due_flag", xmlWriter);


				if (localPast_due_flag==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("past_due_flag cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPast_due_flag);

				}

				xmlWriter.writeEndElement();
			} if (localPast_due_amountTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "past_due_amount", xmlWriter);


				if (localPast_due_amount==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("past_due_amount cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPast_due_amount);

				}

				xmlWriter.writeEndElement();
			}
			namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
			writeStartElement(null, namespace, "product_type", xmlWriter);


			if (localProduct_type==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("product_type cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localProduct_type);

			}

			xmlWriter.writeEndElement();
			if (localCard_typeTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "card_type", xmlWriter);


				if (localCard_type==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("card_type cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCard_type);

				}

				xmlWriter.writeEndElement();
			} if (localCustomer_typeTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "customer_type", xmlWriter);


				if (localCustomer_type==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("customer_type cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCustomer_type);

				}

				xmlWriter.writeEndElement();
			} if (localCard_logo_descriptionTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "card_logo_description", xmlWriter);


				if (localCard_logo_description==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("card_logo_description cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCard_logo_description);

				}

				xmlWriter.writeEndElement();
			} if (localShadow_creditTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "shadow_credit", xmlWriter);


				if (localShadow_credit==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("shadow_credit cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localShadow_credit);

				}

				xmlWriter.writeEndElement();
			} if (localShadow_debitTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "shadow_debit", xmlWriter);


				if (localShadow_debit==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("shadow_debit cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localShadow_debit);

				}

				xmlWriter.writeEndElement();
			} if (localCard_block_codeTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "card_block_code", xmlWriter);


				if (localCard_block_code==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("card_block_code cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCard_block_code);

				}

				xmlWriter.writeEndElement();
			} if (localCard_block_code_dateTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "card_block_code_date", xmlWriter);


				if (localCard_block_code_date==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("card_block_code_date cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCard_block_code_date);

				}

				xmlWriter.writeEndElement();
			} if (localAccount_block_date1Tracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "account_block_date1", xmlWriter);


				if (localAccount_block_date1==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("account_block_date1 cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localAccount_block_date1);

				}

				xmlWriter.writeEndElement();
			} if (localAccount_block_date2Tracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "account_block_date2", xmlWriter);


				if (localAccount_block_date2==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("account_block_date2 cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localAccount_block_date2);

				}

				xmlWriter.writeEndElement();
			} if (localAccount_block_code1Tracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "account_block_code1", xmlWriter);


				if (localAccount_block_code1==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("account_block_code1 cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localAccount_block_code1);

				}

				xmlWriter.writeEndElement();
			} if (localAccount_block_code2Tracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "account_block_code2", xmlWriter);


				if (localAccount_block_code2==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("account_block_code2 cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localAccount_block_code2);

				}

				xmlWriter.writeEndElement();
			} if (localLast_statement_dateTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "last_statement_date", xmlWriter);


				if (localLast_statement_date==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("last_statement_date cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLast_statement_date);

				}

				xmlWriter.writeEndElement();
			} if (localStatement_opening_balanceTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "statement_opening_balance", xmlWriter);


				if (localStatement_opening_balance==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("statement_opening_balance cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localStatement_opening_balance);

				}

				xmlWriter.writeEndElement();
			} if (localStatement_closing_balanceTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "statement_closing_balance", xmlWriter);


				if (localStatement_closing_balance==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("statement_closing_balance cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localStatement_closing_balance);

				}

				xmlWriter.writeEndElement();
			} if (localStatement_outstanding_balanceTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "statement_outstanding_balance", xmlWriter);


				if (localStatement_outstanding_balance==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("statement_outstanding_balance cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localStatement_outstanding_balance);

				}

				xmlWriter.writeEndElement();
			} if (localStatement_dueTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "statement_due", xmlWriter);


				if (localStatement_due==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("statement_due cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localStatement_due);

				}

				xmlWriter.writeEndElement();
			} if (localNext_statement_dateTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "next_statement_date", xmlWriter);


				if (localNext_statement_date==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("next_statement_date cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNext_statement_date);

				}

				xmlWriter.writeEndElement();
			} if (localCard_account_noTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "card_account_no", xmlWriter);


				if (localCard_account_no==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("card_account_no cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCard_account_no);

				}

				xmlWriter.writeEndElement();
			} if (localRelationship_start_dateTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "relationship_start_date", xmlWriter);


				if (localRelationship_start_date==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("relationship_start_date cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localRelationship_start_date);

				}

				xmlWriter.writeEndElement();
			} if (localCard_account_limitTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "card_account_limit", xmlWriter);


				if (localCard_account_limit==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("card_account_limit cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCard_account_limit);

				}

				xmlWriter.writeEndElement();
			} if (localCard_account_available_creditTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "card_account_available_credit", xmlWriter);


				if (localCard_account_available_credit==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("card_account_available_credit cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCard_account_available_credit);

				}

				xmlWriter.writeEndElement();
			} if (localOverlimit_flagTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "overlimit_flag", xmlWriter);


				if (localOverlimit_flag==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("overlimit_flag cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localOverlimit_flag);

				}

				xmlWriter.writeEndElement();
			} if (localOverlimit_amountTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "overlimit_amount", xmlWriter);


				if (localOverlimit_amount==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("overlimit_amount cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localOverlimit_amount);

				}

				xmlWriter.writeEndElement();
			} if (localDirect_debit_flagTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "direct_debit_flag", xmlWriter);


				if (localDirect_debit_flag==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("direct_debit_flag cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDirect_debit_flag);

				}

				xmlWriter.writeEndElement();
			} if (localDirect_debit_accountTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "direct_debit_account", xmlWriter);


				if (localDirect_debit_account==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("direct_debit_account cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDirect_debit_account);

				}

				xmlWriter.writeEndElement();
			} if (localDirect_debit_percentageTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "direct_debit_percentage", xmlWriter);


				if (localDirect_debit_percentage==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("direct_debit_percentage cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDirect_debit_percentage);

				}

				xmlWriter.writeEndElement();
			} if (localCard_branch_codeTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "card_branch_code", xmlWriter);


				if (localCard_branch_code==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("card_branch_code cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCard_branch_code);

				}

				xmlWriter.writeEndElement();
			} if (localCard_pctTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "card_pct", xmlWriter);


				if (localCard_pct==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("card_pct cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCard_pct);

				}

				xmlWriter.writeEndElement();
			} if (localLast_payment_dateTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "last_payment_date", xmlWriter);


				if (localLast_payment_date==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("last_payment_date cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLast_payment_date);

				}

				xmlWriter.writeEndElement();
			} if (localLast_payment_amountTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "last_payment_amount", xmlWriter);


				if (localLast_payment_amount==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("last_payment_amount cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLast_payment_amount);

				}

				xmlWriter.writeEndElement();
			} if (localLast_purchase_dateTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "last_purchase_date", xmlWriter);


				if (localLast_purchase_date==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("last_purchase_date cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLast_purchase_date);

				}

				xmlWriter.writeEndElement();
			} if (localLast_purchase_amountTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "last_purchase_amount", xmlWriter);


				if (localLast_purchase_amount==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("last_purchase_amount cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLast_purchase_amount);

				}

				xmlWriter.writeEndElement();
			} if (localEmbosser_last_maintenance_dateTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "embosser_last_maintenance_date", xmlWriter);


				if (localEmbosser_last_maintenance_date==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("embosser_last_maintenance_date cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localEmbosser_last_maintenance_date);

				}

				xmlWriter.writeEndElement();
			} if (localTotal_cycle_debitTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "total_cycle_debit", xmlWriter);


				if (localTotal_cycle_debit==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("total_cycle_debit cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localTotal_cycle_debit);

				}

				xmlWriter.writeEndElement();
			} if (localTotal_cycle_creditTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "total_cycle_credit", xmlWriter);


				if (localTotal_cycle_credit==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("total_cycle_credit cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localTotal_cycle_credit);

				}

				xmlWriter.writeEndElement();
			} if (localCycle_paymentsTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "cycle_payments", xmlWriter);


				if (localCycle_payments==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("cycle_payments cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCycle_payments);

				}

				xmlWriter.writeEndElement();
			} if (localYtd_retail_purchaseTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "ytd_retail_purchase", xmlWriter);


				if (localYtd_retail_purchase==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("ytd_retail_purchase cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localYtd_retail_purchase);

				}

				xmlWriter.writeEndElement();
			} if (localYtd_cashTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "ytd_cash", xmlWriter);


				if (localYtd_cash==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("ytd_cash cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localYtd_cash);

				}

				xmlWriter.writeEndElement();
			} if (localYtd_retail_returnsTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "ytd_retail_returns", xmlWriter);


				if (localYtd_retail_returns==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("ytd_retail_returns cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localYtd_retail_returns);

				}

				xmlWriter.writeEndElement();
			} if (localLtd_purhcaseTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "ltd_purhcase", xmlWriter);


				if (localLtd_purhcase==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("ltd_purhcase cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLtd_purhcase);

				}

				xmlWriter.writeEndElement();
			} if (localLtd_cashTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "ltd_cash", xmlWriter);


				if (localLtd_cash==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("ltd_cash cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLtd_cash);

				}

				xmlWriter.writeEndElement();
			} if (localLtd_retail_purchaseTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "ltd_retail_purchase", xmlWriter);


				if (localLtd_retail_purchase==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("ltd_retail_purchase cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLtd_retail_purchase);

				}

				xmlWriter.writeEndElement();
			} if (localCtd_retail_purchasesTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "ctd_retail_purchases", xmlWriter);


				if (localCtd_retail_purchases==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("ctd_retail_purchases cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCtd_retail_purchases);

				}

				xmlWriter.writeEndElement();
			} if (localCtd_cashTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "ctd_cash", xmlWriter);


				if (localCtd_cash==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("ctd_cash cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCtd_cash);

				}

				xmlWriter.writeEndElement();
			} if (localCtd_retail_returnsTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "ctd_retail_returns", xmlWriter);


				if (localCtd_retail_returns==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("ctd_retail_returns cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCtd_retail_returns);

				}

				xmlWriter.writeEndElement();
			} if (localInterest_freeTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "interest_free", xmlWriter);


				if (localInterest_free==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("interest_free cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localInterest_free);

				}

				xmlWriter.writeEndElement();
			} if (localLast_credit_limitTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "last_credit_limit", xmlWriter);


				if (localLast_credit_limit==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("last_credit_limit cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localLast_credit_limit);

				}

				xmlWriter.writeEndElement();
			} if (localHighest_balanceTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "highest_balance", xmlWriter);


				if (localHighest_balance==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("highest_balance cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localHighest_balance);

				}

				xmlWriter.writeEndElement();
			} if (localBilling_cycleTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "billing_cycle", xmlWriter);


				if (localBilling_cycle==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("billing_cycle cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localBilling_cycle);

				}

				xmlWriter.writeEndElement();
			} if (localNext_card_fee_recovery_dateTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "next_card_fee_recovery_date", xmlWriter);


				if (localNext_card_fee_recovery_date==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("next_card_fee_recovery_date cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNext_card_fee_recovery_date);

				}

				xmlWriter.writeEndElement();
			} if (localSetup_dateTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "setup_date", xmlWriter);


				if (localSetup_date==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("setup_date cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localSetup_date);

				}

				xmlWriter.writeEndElement();
			} if (localCard_creation_dateTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "card_creation_date", xmlWriter);


				if (localCard_creation_date==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("card_creation_date cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCard_creation_date);

				}

				xmlWriter.writeEndElement();
			} if (localCard_issuance_dateTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "card_issuance_date", xmlWriter);


				if (localCard_issuance_date==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("card_issuance_date cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCard_issuance_date);

				}

				xmlWriter.writeEndElement();
			} if (localCard_activation_dateTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "card_activation_date", xmlWriter);


				if (localCard_activation_date==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("card_activation_date cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCard_activation_date);

				}

				xmlWriter.writeEndElement();
			} if (localDirect_debit_request_dayTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "direct_debit_request_day", xmlWriter);


				if (localDirect_debit_request_day==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("direct_debit_request_day cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDirect_debit_request_day);

				}

				xmlWriter.writeEndElement();
			} if (localCurrent_credit_limit_dateTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "current_credit_limit_date", xmlWriter);


				if (localCurrent_credit_limit_date==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("current_credit_limit_date cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCurrent_credit_limit_date);

				}

				xmlWriter.writeEndElement();
			} if (localNo_of_plansTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "no_of_plans", xmlWriter);


				if (localNo_of_plans==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("no_of_plans cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNo_of_plans);

				}

				xmlWriter.writeEndElement();
			} if (localEStatementSubFlagTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "eStatementSubFlag", xmlWriter);


				if (localEStatementSubFlag==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("eStatementSubFlag cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localEStatementSubFlag);

				}

				xmlWriter.writeEndElement();
			} if (localEStatementSubDateTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "eStatementSubDate", xmlWriter);


				if (localEStatementSubDate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("eStatementSubDate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localEStatementSubDate);

				}

				xmlWriter.writeEndElement();
			} if (localTotalLULUPointTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "totalLULUPoint", xmlWriter);


				if (localTotalLULUPoint==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("totalLULUPoint cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localTotalLULUPoint);

				}

				xmlWriter.writeEndElement();
			} if (localInsurance_productsTracker){
				if (localInsurance_products!=null){
					for (int i = 0;i < localInsurance_products.length;i++){
						if (localInsurance_products[i] != null){
							localInsurance_products[i].serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","insurance_products"),
									xmlWriter);
						} else {

							// we don't have to do any thing since minOccures is zero

						}

					}
				} else {

					throw new org.apache.axis2.databinding.ADBException("insurance_products cannot be null!!");

				}
			} if (localInstallment_plansTracker){
				if (localInstallment_plans!=null){
					for (int i = 0;i < localInstallment_plans.length;i++){
						if (localInstallment_plans[i] != null){
							localInstallment_plans[i].serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","installment_plans"),
									xmlWriter);
						} else {

							// we don't have to do any thing since minOccures is zero

						}

					}
				} else {

					throw new org.apache.axis2.databinding.ADBException("installment_plans cannot be null!!");

				}
			} if (localPlansTracker){
				if (localPlans!=null){
					for (int i = 0;i < localPlans.length;i++){
						if (localPlans[i] != null){
							localPlans[i].serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","plans"),
									xmlWriter);
						} else {

							// we don't have to do any thing since minOccures is zero

						}

					}
				} else {

					throw new org.apache.axis2.databinding.ADBException("plans cannot be null!!");

				}
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd")){
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

			if (localCif_idTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"cif_id"));

				if (localCif_id != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCif_id));
				} else {
					throw new org.apache.axis2.databinding.ADBException("cif_id cannot be null!!");
				}
			} if (localCard_noTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"card_no"));

				if (localCard_no != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCard_no));
				} else {
					throw new org.apache.axis2.databinding.ADBException("card_no cannot be null!!");
				}
			}
			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
					"card_currency"));

			if (localCard_currency != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCard_currency));
			} else {
				throw new org.apache.axis2.databinding.ADBException("card_currency cannot be null!!");
			}

			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
					"card_name"));

			if (localCard_name != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCard_name));
			} else {
				throw new org.apache.axis2.databinding.ADBException("card_name cannot be null!!");
			}
			if (localCard_brandTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"card_brand"));

				if (localCard_brand != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCard_brand));
				} else {
					throw new org.apache.axis2.databinding.ADBException("card_brand cannot be null!!");
				}
			}
			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
					"card_limit"));

			if (localCard_limit != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCard_limit));
			} else {
				throw new org.apache.axis2.databinding.ADBException("card_limit cannot be null!!");
			}
			if (localCard_limit_percentageTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"card_limit_percentage"));

				if (localCard_limit_percentage != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCard_limit_percentage));
				} else {
					throw new org.apache.axis2.databinding.ADBException("card_limit_percentage cannot be null!!");
				}
			}
			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
					"available_credit"));

			if (localAvailable_credit != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAvailable_credit));
			} else {
				throw new org.apache.axis2.databinding.ADBException("available_credit cannot be null!!");
			}
			if (localLast_credit_limit_increase_dateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"last_credit_limit_increase_date"));

				if (localLast_credit_limit_increase_date != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLast_credit_limit_increase_date));
				} else {
					throw new org.apache.axis2.databinding.ADBException("last_credit_limit_increase_date cannot be null!!");
				}
			} if (localHighest_balance_dateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"highest_balance_date"));

				if (localHighest_balance_date != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHighest_balance_date));
				} else {
					throw new org.apache.axis2.databinding.ADBException("highest_balance_date cannot be null!!");
				}
			} if (localSi_start_dateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"si_start_date"));

				if (localSi_start_date != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSi_start_date));
				} else {
					throw new org.apache.axis2.databinding.ADBException("si_start_date cannot be null!!");
				}
			}
			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
					"cash_limit"));

			if (localCash_limit != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCash_limit));
			} else {
				throw new org.apache.axis2.databinding.ADBException("cash_limit cannot be null!!");
			}
			if (localCash_limit_percentageTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"cash_limit_percentage"));

				if (localCash_limit_percentage != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCash_limit_percentage));
				} else {
					throw new org.apache.axis2.databinding.ADBException("cash_limit_percentage cannot be null!!");
				}
			}
			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
					"available_cash"));

			if (localAvailable_cash != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAvailable_cash));
			} else {
				throw new org.apache.axis2.databinding.ADBException("available_cash cannot be null!!");
			}

			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
					"outstanding_balance"));

			if (localOutstanding_balance != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOutstanding_balance));
			} else {
				throw new org.apache.axis2.databinding.ADBException("outstanding_balance cannot be null!!");
			}

			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
					"current_card_expiry_date"));

			if (localCurrent_card_expiry_date != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCurrent_card_expiry_date));
			} else {
				throw new org.apache.axis2.databinding.ADBException("current_card_expiry_date cannot be null!!");
			}
			if (localCurrent_card_activation_flagTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"current_card_activation_flag"));

				if (localCurrent_card_activation_flag != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCurrent_card_activation_flag));
				} else {
					throw new org.apache.axis2.databinding.ADBException("current_card_activation_flag cannot be null!!");
				}
			} if (localLast_card_expiry_dateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"last_card_expiry_date"));

				if (localLast_card_expiry_date != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLast_card_expiry_date));
				} else {
					throw new org.apache.axis2.databinding.ADBException("last_card_expiry_date cannot be null!!");
				}
			} if (localLast_card_activation_flagTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"last_card_activation_flag"));

				if (localLast_card_activation_flag != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLast_card_activation_flag));
				} else {
					throw new org.apache.axis2.databinding.ADBException("last_card_activation_flag cannot be null!!");
				}
			}
			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
					"min_payment"));

			if (localMin_payment != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMin_payment));
			} else {
				throw new org.apache.axis2.databinding.ADBException("min_payment cannot be null!!");
			}

			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
					"due_date"));

			if (localDue_date != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDue_date));
			} else {
				throw new org.apache.axis2.databinding.ADBException("due_date cannot be null!!");
			}
			if (localDue_date_with_grace_periodTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"due_date_with_grace_period"));

				if (localDue_date_with_grace_period != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDue_date_with_grace_period));
				} else {
					throw new org.apache.axis2.databinding.ADBException("due_date_with_grace_period cannot be null!!");
				}
			} if (localUnbilled_amountTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"unbilled_amount"));

				if (localUnbilled_amount != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUnbilled_amount));
				} else {
					throw new org.apache.axis2.databinding.ADBException("unbilled_amount cannot be null!!");
				}
			} if (localPast_due_flagTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"past_due_flag"));

				if (localPast_due_flag != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPast_due_flag));
				} else {
					throw new org.apache.axis2.databinding.ADBException("past_due_flag cannot be null!!");
				}
			} if (localPast_due_amountTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"past_due_amount"));

				if (localPast_due_amount != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPast_due_amount));
				} else {
					throw new org.apache.axis2.databinding.ADBException("past_due_amount cannot be null!!");
				}
			}
			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
					"product_type"));

			if (localProduct_type != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProduct_type));
			} else {
				throw new org.apache.axis2.databinding.ADBException("product_type cannot be null!!");
			}
			if (localCard_typeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"card_type"));

				if (localCard_type != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCard_type));
				} else {
					throw new org.apache.axis2.databinding.ADBException("card_type cannot be null!!");
				}
			} if (localCustomer_typeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"customer_type"));

				if (localCustomer_type != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomer_type));
				} else {
					throw new org.apache.axis2.databinding.ADBException("customer_type cannot be null!!");
				}
			} if (localCard_logo_descriptionTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"card_logo_description"));

				if (localCard_logo_description != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCard_logo_description));
				} else {
					throw new org.apache.axis2.databinding.ADBException("card_logo_description cannot be null!!");
				}
			} if (localShadow_creditTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"shadow_credit"));

				if (localShadow_credit != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localShadow_credit));
				} else {
					throw new org.apache.axis2.databinding.ADBException("shadow_credit cannot be null!!");
				}
			} if (localShadow_debitTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"shadow_debit"));

				if (localShadow_debit != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localShadow_debit));
				} else {
					throw new org.apache.axis2.databinding.ADBException("shadow_debit cannot be null!!");
				}
			} if (localCard_block_codeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"card_block_code"));

				if (localCard_block_code != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCard_block_code));
				} else {
					throw new org.apache.axis2.databinding.ADBException("card_block_code cannot be null!!");
				}
			} if (localCard_block_code_dateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"card_block_code_date"));

				if (localCard_block_code_date != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCard_block_code_date));
				} else {
					throw new org.apache.axis2.databinding.ADBException("card_block_code_date cannot be null!!");
				}
			} if (localAccount_block_date1Tracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"account_block_date1"));

				if (localAccount_block_date1 != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAccount_block_date1));
				} else {
					throw new org.apache.axis2.databinding.ADBException("account_block_date1 cannot be null!!");
				}
			} if (localAccount_block_date2Tracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"account_block_date2"));

				if (localAccount_block_date2 != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAccount_block_date2));
				} else {
					throw new org.apache.axis2.databinding.ADBException("account_block_date2 cannot be null!!");
				}
			} if (localAccount_block_code1Tracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"account_block_code1"));

				if (localAccount_block_code1 != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAccount_block_code1));
				} else {
					throw new org.apache.axis2.databinding.ADBException("account_block_code1 cannot be null!!");
				}
			} if (localAccount_block_code2Tracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"account_block_code2"));

				if (localAccount_block_code2 != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAccount_block_code2));
				} else {
					throw new org.apache.axis2.databinding.ADBException("account_block_code2 cannot be null!!");
				}
			} if (localLast_statement_dateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"last_statement_date"));

				if (localLast_statement_date != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLast_statement_date));
				} else {
					throw new org.apache.axis2.databinding.ADBException("last_statement_date cannot be null!!");
				}
			} if (localStatement_opening_balanceTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"statement_opening_balance"));

				if (localStatement_opening_balance != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStatement_opening_balance));
				} else {
					throw new org.apache.axis2.databinding.ADBException("statement_opening_balance cannot be null!!");
				}
			} if (localStatement_closing_balanceTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"statement_closing_balance"));

				if (localStatement_closing_balance != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStatement_closing_balance));
				} else {
					throw new org.apache.axis2.databinding.ADBException("statement_closing_balance cannot be null!!");
				}
			} if (localStatement_outstanding_balanceTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"statement_outstanding_balance"));

				if (localStatement_outstanding_balance != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStatement_outstanding_balance));
				} else {
					throw new org.apache.axis2.databinding.ADBException("statement_outstanding_balance cannot be null!!");
				}
			} if (localStatement_dueTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"statement_due"));

				if (localStatement_due != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStatement_due));
				} else {
					throw new org.apache.axis2.databinding.ADBException("statement_due cannot be null!!");
				}
			} if (localNext_statement_dateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"next_statement_date"));

				if (localNext_statement_date != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNext_statement_date));
				} else {
					throw new org.apache.axis2.databinding.ADBException("next_statement_date cannot be null!!");
				}
			} if (localCard_account_noTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"card_account_no"));

				if (localCard_account_no != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCard_account_no));
				} else {
					throw new org.apache.axis2.databinding.ADBException("card_account_no cannot be null!!");
				}
			} if (localRelationship_start_dateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"relationship_start_date"));

				if (localRelationship_start_date != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRelationship_start_date));
				} else {
					throw new org.apache.axis2.databinding.ADBException("relationship_start_date cannot be null!!");
				}
			} if (localCard_account_limitTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"card_account_limit"));

				if (localCard_account_limit != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCard_account_limit));
				} else {
					throw new org.apache.axis2.databinding.ADBException("card_account_limit cannot be null!!");
				}
			} if (localCard_account_available_creditTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"card_account_available_credit"));

				if (localCard_account_available_credit != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCard_account_available_credit));
				} else {
					throw new org.apache.axis2.databinding.ADBException("card_account_available_credit cannot be null!!");
				}
			} if (localOverlimit_flagTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"overlimit_flag"));

				if (localOverlimit_flag != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOverlimit_flag));
				} else {
					throw new org.apache.axis2.databinding.ADBException("overlimit_flag cannot be null!!");
				}
			} if (localOverlimit_amountTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"overlimit_amount"));

				if (localOverlimit_amount != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOverlimit_amount));
				} else {
					throw new org.apache.axis2.databinding.ADBException("overlimit_amount cannot be null!!");
				}
			} if (localDirect_debit_flagTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"direct_debit_flag"));

				if (localDirect_debit_flag != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDirect_debit_flag));
				} else {
					throw new org.apache.axis2.databinding.ADBException("direct_debit_flag cannot be null!!");
				}
			} if (localDirect_debit_accountTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"direct_debit_account"));

				if (localDirect_debit_account != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDirect_debit_account));
				} else {
					throw new org.apache.axis2.databinding.ADBException("direct_debit_account cannot be null!!");
				}
			} if (localDirect_debit_percentageTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"direct_debit_percentage"));

				if (localDirect_debit_percentage != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDirect_debit_percentage));
				} else {
					throw new org.apache.axis2.databinding.ADBException("direct_debit_percentage cannot be null!!");
				}
			} if (localCard_branch_codeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"card_branch_code"));

				if (localCard_branch_code != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCard_branch_code));
				} else {
					throw new org.apache.axis2.databinding.ADBException("card_branch_code cannot be null!!");
				}
			} if (localCard_pctTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"card_pct"));

				if (localCard_pct != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCard_pct));
				} else {
					throw new org.apache.axis2.databinding.ADBException("card_pct cannot be null!!");
				}
			} if (localLast_payment_dateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"last_payment_date"));

				if (localLast_payment_date != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLast_payment_date));
				} else {
					throw new org.apache.axis2.databinding.ADBException("last_payment_date cannot be null!!");
				}
			} if (localLast_payment_amountTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"last_payment_amount"));

				if (localLast_payment_amount != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLast_payment_amount));
				} else {
					throw new org.apache.axis2.databinding.ADBException("last_payment_amount cannot be null!!");
				}
			} if (localLast_purchase_dateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"last_purchase_date"));

				if (localLast_purchase_date != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLast_purchase_date));
				} else {
					throw new org.apache.axis2.databinding.ADBException("last_purchase_date cannot be null!!");
				}
			} if (localLast_purchase_amountTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"last_purchase_amount"));

				if (localLast_purchase_amount != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLast_purchase_amount));
				} else {
					throw new org.apache.axis2.databinding.ADBException("last_purchase_amount cannot be null!!");
				}
			} if (localEmbosser_last_maintenance_dateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"embosser_last_maintenance_date"));

				if (localEmbosser_last_maintenance_date != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEmbosser_last_maintenance_date));
				} else {
					throw new org.apache.axis2.databinding.ADBException("embosser_last_maintenance_date cannot be null!!");
				}
			} if (localTotal_cycle_debitTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"total_cycle_debit"));

				if (localTotal_cycle_debit != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTotal_cycle_debit));
				} else {
					throw new org.apache.axis2.databinding.ADBException("total_cycle_debit cannot be null!!");
				}
			} if (localTotal_cycle_creditTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"total_cycle_credit"));

				if (localTotal_cycle_credit != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTotal_cycle_credit));
				} else {
					throw new org.apache.axis2.databinding.ADBException("total_cycle_credit cannot be null!!");
				}
			} if (localCycle_paymentsTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"cycle_payments"));

				if (localCycle_payments != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCycle_payments));
				} else {
					throw new org.apache.axis2.databinding.ADBException("cycle_payments cannot be null!!");
				}
			} if (localYtd_retail_purchaseTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"ytd_retail_purchase"));

				if (localYtd_retail_purchase != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localYtd_retail_purchase));
				} else {
					throw new org.apache.axis2.databinding.ADBException("ytd_retail_purchase cannot be null!!");
				}
			} if (localYtd_cashTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"ytd_cash"));

				if (localYtd_cash != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localYtd_cash));
				} else {
					throw new org.apache.axis2.databinding.ADBException("ytd_cash cannot be null!!");
				}
			} if (localYtd_retail_returnsTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"ytd_retail_returns"));

				if (localYtd_retail_returns != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localYtd_retail_returns));
				} else {
					throw new org.apache.axis2.databinding.ADBException("ytd_retail_returns cannot be null!!");
				}
			} if (localLtd_purhcaseTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"ltd_purhcase"));

				if (localLtd_purhcase != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLtd_purhcase));
				} else {
					throw new org.apache.axis2.databinding.ADBException("ltd_purhcase cannot be null!!");
				}
			} if (localLtd_cashTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"ltd_cash"));

				if (localLtd_cash != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLtd_cash));
				} else {
					throw new org.apache.axis2.databinding.ADBException("ltd_cash cannot be null!!");
				}
			} if (localLtd_retail_purchaseTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"ltd_retail_purchase"));

				if (localLtd_retail_purchase != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLtd_retail_purchase));
				} else {
					throw new org.apache.axis2.databinding.ADBException("ltd_retail_purchase cannot be null!!");
				}
			} if (localCtd_retail_purchasesTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"ctd_retail_purchases"));

				if (localCtd_retail_purchases != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCtd_retail_purchases));
				} else {
					throw new org.apache.axis2.databinding.ADBException("ctd_retail_purchases cannot be null!!");
				}
			} if (localCtd_cashTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"ctd_cash"));

				if (localCtd_cash != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCtd_cash));
				} else {
					throw new org.apache.axis2.databinding.ADBException("ctd_cash cannot be null!!");
				}
			} if (localCtd_retail_returnsTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"ctd_retail_returns"));

				if (localCtd_retail_returns != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCtd_retail_returns));
				} else {
					throw new org.apache.axis2.databinding.ADBException("ctd_retail_returns cannot be null!!");
				}
			} if (localInterest_freeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"interest_free"));

				if (localInterest_free != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInterest_free));
				} else {
					throw new org.apache.axis2.databinding.ADBException("interest_free cannot be null!!");
				}
			} if (localLast_credit_limitTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"last_credit_limit"));

				if (localLast_credit_limit != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLast_credit_limit));
				} else {
					throw new org.apache.axis2.databinding.ADBException("last_credit_limit cannot be null!!");
				}
			} if (localHighest_balanceTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"highest_balance"));

				if (localHighest_balance != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHighest_balance));
				} else {
					throw new org.apache.axis2.databinding.ADBException("highest_balance cannot be null!!");
				}
			} if (localBilling_cycleTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"billing_cycle"));

				if (localBilling_cycle != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBilling_cycle));
				} else {
					throw new org.apache.axis2.databinding.ADBException("billing_cycle cannot be null!!");
				}
			} if (localNext_card_fee_recovery_dateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"next_card_fee_recovery_date"));

				if (localNext_card_fee_recovery_date != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNext_card_fee_recovery_date));
				} else {
					throw new org.apache.axis2.databinding.ADBException("next_card_fee_recovery_date cannot be null!!");
				}
			} if (localSetup_dateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"setup_date"));

				if (localSetup_date != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSetup_date));
				} else {
					throw new org.apache.axis2.databinding.ADBException("setup_date cannot be null!!");
				}
			} if (localCard_creation_dateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"card_creation_date"));

				if (localCard_creation_date != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCard_creation_date));
				} else {
					throw new org.apache.axis2.databinding.ADBException("card_creation_date cannot be null!!");
				}
			} if (localCard_issuance_dateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"card_issuance_date"));

				if (localCard_issuance_date != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCard_issuance_date));
				} else {
					throw new org.apache.axis2.databinding.ADBException("card_issuance_date cannot be null!!");
				}
			} if (localCard_activation_dateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"card_activation_date"));

				if (localCard_activation_date != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCard_activation_date));
				} else {
					throw new org.apache.axis2.databinding.ADBException("card_activation_date cannot be null!!");
				}
			} if (localDirect_debit_request_dayTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"direct_debit_request_day"));

				if (localDirect_debit_request_day != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDirect_debit_request_day));
				} else {
					throw new org.apache.axis2.databinding.ADBException("direct_debit_request_day cannot be null!!");
				}
			} if (localCurrent_credit_limit_dateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"current_credit_limit_date"));

				if (localCurrent_credit_limit_date != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCurrent_credit_limit_date));
				} else {
					throw new org.apache.axis2.databinding.ADBException("current_credit_limit_date cannot be null!!");
				}
			} if (localNo_of_plansTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"no_of_plans"));

				if (localNo_of_plans != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNo_of_plans));
				} else {
					throw new org.apache.axis2.databinding.ADBException("no_of_plans cannot be null!!");
				}
			} if (localEStatementSubFlagTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"eStatementSubFlag"));

				if (localEStatementSubFlag != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEStatementSubFlag));
				} else {
					throw new org.apache.axis2.databinding.ADBException("eStatementSubFlag cannot be null!!");
				}
			} if (localEStatementSubDateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"eStatementSubDate"));

				if (localEStatementSubDate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEStatementSubDate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("eStatementSubDate cannot be null!!");
				}
			} if (localTotalLULUPointTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"totalLULUPoint"));

				if (localTotalLULUPoint != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTotalLULUPoint));
				} else {
					throw new org.apache.axis2.databinding.ADBException("totalLULUPoint cannot be null!!");
				}
			} if (localInsurance_productsTracker){
				if (localInsurance_products!=null) {
					for (int i = 0;i < localInsurance_products.length;i++){

						if (localInsurance_products[i] != null){
							elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
									"insurance_products"));
							elementList.add(localInsurance_products[i]);
						} else {

							// nothing to do

						}

					}
				} else {

					throw new org.apache.axis2.databinding.ADBException("insurance_products cannot be null!!");

				}

			} if (localInstallment_plansTracker){
				if (localInstallment_plans!=null) {
					for (int i = 0;i < localInstallment_plans.length;i++){

						if (localInstallment_plans[i] != null){
							elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
									"installment_plans"));
							elementList.add(localInstallment_plans[i]);
						} else {

							// nothing to do

						}

					}
				} else {

					throw new org.apache.axis2.databinding.ADBException("installment_plans cannot be null!!");

				}

			} if (localPlansTracker){
				if (localPlans!=null) {
					for (int i = 0;i < localPlans.length;i++){

						if (localPlans[i] != null){
							elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
									"plans"));
							elementList.add(localPlans[i]);
						} else {

							// nothing to do

						}

					}
				} else {

					throw new org.apache.axis2.databinding.ADBException("plans cannot be null!!");

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
			public static Card_details_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				Card_details_type0 object =
						new Card_details_type0();

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

							if (!"card_details_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (Card_details_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();

					java.util.ArrayList list87 = new java.util.ArrayList();

					java.util.ArrayList list88 = new java.util.ArrayList();

					java.util.ArrayList list89 = new java.util.ArrayList();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","cif_id").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"cif_id" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCif_id(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","card_no").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"card_no" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCard_no(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","card_currency").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"card_currency" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCard_currency(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","card_name").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"card_name" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCard_name(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","card_brand").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"card_brand" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCard_brand(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","card_limit").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"card_limit" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCard_limit(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","card_limit_percentage").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"card_limit_percentage" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCard_limit_percentage(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","available_credit").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"available_credit" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAvailable_credit(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","last_credit_limit_increase_date").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"last_credit_limit_increase_date" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLast_credit_limit_increase_date(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","highest_balance_date").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"highest_balance_date" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setHighest_balance_date(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","si_start_date").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"si_start_date" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setSi_start_date(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","cash_limit").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"cash_limit" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCash_limit(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","cash_limit_percentage").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"cash_limit_percentage" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCash_limit_percentage(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","available_cash").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"available_cash" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAvailable_cash(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","outstanding_balance").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"outstanding_balance" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setOutstanding_balance(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","current_card_expiry_date").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"current_card_expiry_date" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCurrent_card_expiry_date(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","current_card_activation_flag").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"current_card_activation_flag" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCurrent_card_activation_flag(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","last_card_expiry_date").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"last_card_expiry_date" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLast_card_expiry_date(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","last_card_activation_flag").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"last_card_activation_flag" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLast_card_activation_flag(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","min_payment").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"min_payment" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setMin_payment(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","due_date").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"due_date" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDue_date(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","due_date_with_grace_period").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"due_date_with_grace_period" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDue_date_with_grace_period(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","unbilled_amount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"unbilled_amount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setUnbilled_amount(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","past_due_flag").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"past_due_flag" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPast_due_flag(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","past_due_amount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"past_due_amount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPast_due_amount(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","product_type").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"product_type" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setProduct_type(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","card_type").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"card_type" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCard_type(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","customer_type").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"customer_type" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCustomer_type(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","card_logo_description").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"card_logo_description" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCard_logo_description(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","shadow_credit").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"shadow_credit" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setShadow_credit(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","shadow_debit").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"shadow_debit" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setShadow_debit(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","card_block_code").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"card_block_code" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCard_block_code(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","card_block_code_date").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"card_block_code_date" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCard_block_code_date(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","account_block_date1").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"account_block_date1" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAccount_block_date1(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","account_block_date2").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"account_block_date2" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAccount_block_date2(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","account_block_code1").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"account_block_code1" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAccount_block_code1(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","account_block_code2").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"account_block_code2" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAccount_block_code2(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","last_statement_date").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"last_statement_date" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLast_statement_date(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","statement_opening_balance").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"statement_opening_balance" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setStatement_opening_balance(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","statement_closing_balance").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"statement_closing_balance" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setStatement_closing_balance(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","statement_outstanding_balance").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"statement_outstanding_balance" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setStatement_outstanding_balance(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","statement_due").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"statement_due" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setStatement_due(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","next_statement_date").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"next_statement_date" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNext_statement_date(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","card_account_no").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"card_account_no" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCard_account_no(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","relationship_start_date").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"relationship_start_date" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setRelationship_start_date(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","card_account_limit").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"card_account_limit" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCard_account_limit(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","card_account_available_credit").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"card_account_available_credit" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCard_account_available_credit(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","overlimit_flag").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"overlimit_flag" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setOverlimit_flag(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","overlimit_amount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"overlimit_amount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setOverlimit_amount(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","direct_debit_flag").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"direct_debit_flag" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDirect_debit_flag(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","direct_debit_account").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"direct_debit_account" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDirect_debit_account(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","direct_debit_percentage").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"direct_debit_percentage" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDirect_debit_percentage(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","card_branch_code").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"card_branch_code" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCard_branch_code(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","card_pct").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"card_pct" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCard_pct(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","last_payment_date").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"last_payment_date" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLast_payment_date(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","last_payment_amount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"last_payment_amount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLast_payment_amount(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","last_purchase_date").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"last_purchase_date" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLast_purchase_date(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","last_purchase_amount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"last_purchase_amount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLast_purchase_amount(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","embosser_last_maintenance_date").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"embosser_last_maintenance_date" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setEmbosser_last_maintenance_date(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","total_cycle_debit").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"total_cycle_debit" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setTotal_cycle_debit(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","total_cycle_credit").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"total_cycle_credit" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setTotal_cycle_credit(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","cycle_payments").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"cycle_payments" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCycle_payments(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","ytd_retail_purchase").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"ytd_retail_purchase" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setYtd_retail_purchase(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","ytd_cash").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"ytd_cash" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setYtd_cash(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","ytd_retail_returns").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"ytd_retail_returns" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setYtd_retail_returns(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","ltd_purhcase").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"ltd_purhcase" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLtd_purhcase(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","ltd_cash").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"ltd_cash" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLtd_cash(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","ltd_retail_purchase").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"ltd_retail_purchase" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLtd_retail_purchase(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","ctd_retail_purchases").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"ctd_retail_purchases" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCtd_retail_purchases(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","ctd_cash").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"ctd_cash" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCtd_cash(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","ctd_retail_returns").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"ctd_retail_returns" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCtd_retail_returns(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","interest_free").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"interest_free" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setInterest_free(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","last_credit_limit").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"last_credit_limit" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setLast_credit_limit(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","highest_balance").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"highest_balance" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setHighest_balance(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","billing_cycle").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"billing_cycle" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setBilling_cycle(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","next_card_fee_recovery_date").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"next_card_fee_recovery_date" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNext_card_fee_recovery_date(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","setup_date").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"setup_date" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setSetup_date(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","card_creation_date").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"card_creation_date" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCard_creation_date(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","card_issuance_date").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"card_issuance_date" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCard_issuance_date(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","card_activation_date").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"card_activation_date" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCard_activation_date(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","direct_debit_request_day").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"direct_debit_request_day" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDirect_debit_request_day(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","current_credit_limit_date").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"current_credit_limit_date" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCurrent_credit_limit_date(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","no_of_plans").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"no_of_plans" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNo_of_plans(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","eStatementSubFlag").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"eStatementSubFlag" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setEStatementSubFlag(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","eStatementSubDate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"eStatementSubDate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setEStatementSubDate(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","totalLULUPoint").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"totalLULUPoint" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setTotalLULUPoint(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","insurance_products").equals(reader.getName())){



						// Process the array and step past its final element's end.
						list87.add(Insurance_products_type0.Factory.parse(reader));

						//loop until we find a start element that is not part of this array
						boolean loopDone87 = false;
						while(!loopDone87){
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
								loopDone87 = true;
							} else {
								if (new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","insurance_products").equals(reader.getName())){
									list87.add(Insurance_products_type0.Factory.parse(reader));

								}else{
									loopDone87 = true;
								}
							}
						}
						// call the converter utility  to convert and set the array

						object.setInsurance_products((Insurance_products_type0[])
								org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
										Insurance_products_type0.class,
										list87));

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","installment_plans").equals(reader.getName())){



						// Process the array and step past its final element's end.
						list88.add(Installment_plans_type0.Factory.parse(reader));

						//loop until we find a start element that is not part of this array
						boolean loopDone88 = false;
						while(!loopDone88){
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
								loopDone88 = true;
							} else {
								if (new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","installment_plans").equals(reader.getName())){
									list88.add(Installment_plans_type0.Factory.parse(reader));

								}else{
									loopDone88 = true;
								}
							}
						}
						// call the converter utility  to convert and set the array

						object.setInstallment_plans((Installment_plans_type0[])
								org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
										Installment_plans_type0.class,
										list88));

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","plans").equals(reader.getName())){



						// Process the array and step past its final element's end.
						list89.add(Plans_type0.Factory.parse(reader));

						//loop until we find a start element that is not part of this array
						boolean loopDone89 = false;
						while(!loopDone89){
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
								loopDone89 = true;
							} else {
								if (new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","plans").equals(reader.getName())){
									list89.add(Plans_type0.Factory.parse(reader));

								}else{
									loopDone89 = true;
								}
							}
						}
						// call the converter utility  to convert and set the array

						object.setPlans((Plans_type0[])
								org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
										Plans_type0.class,
										list89));

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


	public static class Personal_details_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = personal_details_type0
                Namespace URI = http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd
                Namespace Prefix = ns1
		 */


		/**
		 * field for Preferred_name
		 */


		protected java.lang.String localPreferred_name ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPreferred_nameTracker = false ;

		public boolean isPreferred_nameSpecified(){
			return localPreferred_nameTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPreferred_name(){
			return localPreferred_name;
		}



		/**
		 * Auto generated setter method
		 * @param param Preferred_name
		 */
		public void setPreferred_name(java.lang.String param){
			localPreferred_nameTracker = param != null;

			this.localPreferred_name=param;


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
		 * field for Name_line_1
		 */


		protected java.lang.String localName_line_1 ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localName_line_1Tracker = false ;

		public boolean isName_line_1Specified(){
			return localName_line_1Tracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getName_line_1(){
			return localName_line_1;
		}



		/**
		 * Auto generated setter method
		 * @param param Name_line_1
		 */
		public void setName_line_1(java.lang.String param){
			localName_line_1Tracker = param != null;

			this.localName_line_1=param;


		}


		/**
		 * field for Name_line_2
		 */


		protected java.lang.String localName_line_2 ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localName_line_2Tracker = false ;

		public boolean isName_line_2Specified(){
			return localName_line_2Tracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getName_line_2(){
			return localName_line_2;
		}



		/**
		 * Auto generated setter method
		 * @param param Name_line_2
		 */
		public void setName_line_2(java.lang.String param){
			localName_line_2Tracker = param != null;

			this.localName_line_2=param;


		}


		/**
		 * field for Name_line_3
		 */


		protected java.lang.String localName_line_3 ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localName_line_3Tracker = false ;

		public boolean isName_line_3Specified(){
			return localName_line_3Tracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getName_line_3(){
			return localName_line_3;
		}



		/**
		 * Auto generated setter method
		 * @param param Name_line_3
		 */
		public void setName_line_3(java.lang.String param){
			localName_line_3Tracker = param != null;

			this.localName_line_3=param;


		}


		/**
		 * field for Passport_no
		 */


		protected java.lang.String localPassport_no ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPassport_noTracker = false ;

		public boolean isPassport_noSpecified(){
			return localPassport_noTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPassport_no(){
			return localPassport_no;
		}



		/**
		 * Auto generated setter method
		 * @param param Passport_no
		 */
		public void setPassport_no(java.lang.String param){
			localPassport_noTracker = param != null;

			this.localPassport_no=param;


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
		 * field for Date_of_birth
		 */


		protected java.lang.String localDate_of_birth ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDate_of_birthTracker = false ;

		public boolean isDate_of_birthSpecified(){
			return localDate_of_birthTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDate_of_birth(){
			return localDate_of_birth;
		}



		/**
		 * Auto generated setter method
		 * @param param Date_of_birth
		 */
		public void setDate_of_birth(java.lang.String param){
			localDate_of_birthTracker = param != null;

			this.localDate_of_birth=param;


		}


		/**
		 * field for Email_address
		 */


		protected java.lang.String localEmail_address ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localEmail_addressTracker = false ;

		public boolean isEmail_addressSpecified(){
			return localEmail_addressTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getEmail_address(){
			return localEmail_address;
		}



		/**
		 * Auto generated setter method
		 * @param param Email_address
		 */
		public void setEmail_address(java.lang.String param){
			localEmail_addressTracker = param != null;

			this.localEmail_address=param;


		}


		/**
		 * field for Mothers_maiden_name
		 */


		protected java.lang.String localMothers_maiden_name ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localMothers_maiden_nameTracker = false ;

		public boolean isMothers_maiden_nameSpecified(){
			return localMothers_maiden_nameTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getMothers_maiden_name(){
			return localMothers_maiden_name;
		}



		/**
		 * Auto generated setter method
		 * @param param Mothers_maiden_name
		 */
		public void setMothers_maiden_name(java.lang.String param){
			localMothers_maiden_nameTracker = param != null;

			this.localMothers_maiden_name=param;


		}


		/**
		 * field for Driving_license_no
		 */


		protected java.lang.String localDriving_license_no ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDriving_license_noTracker = false ;

		public boolean isDriving_license_noSpecified(){
			return localDriving_license_noTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDriving_license_no(){
			return localDriving_license_no;
		}



		/**
		 * Auto generated setter method
		 * @param param Driving_license_no
		 */
		public void setDriving_license_no(java.lang.String param){
			localDriving_license_noTracker = param != null;

			this.localDriving_license_no=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":personal_details_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"personal_details_type0",
							xmlWriter);
				}


			}
			if (localPreferred_nameTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "preferred_name", xmlWriter);


				if (localPreferred_name==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("preferred_name cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPreferred_name);

				}

				xmlWriter.writeEndElement();
			} if (localGenderTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "gender", xmlWriter);


				if (localGender==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("gender cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localGender);

				}

				xmlWriter.writeEndElement();
			} if (localName_line_1Tracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "name_line_1", xmlWriter);


				if (localName_line_1==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("name_line_1 cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localName_line_1);

				}

				xmlWriter.writeEndElement();
			} if (localName_line_2Tracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "name_line_2", xmlWriter);


				if (localName_line_2==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("name_line_2 cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localName_line_2);

				}

				xmlWriter.writeEndElement();
			} if (localName_line_3Tracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "name_line_3", xmlWriter);


				if (localName_line_3==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("name_line_3 cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localName_line_3);

				}

				xmlWriter.writeEndElement();
			} if (localPassport_noTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "passport_no", xmlWriter);


				if (localPassport_no==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("passport_no cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPassport_no);

				}

				xmlWriter.writeEndElement();
			} if (localNationalityTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "nationality", xmlWriter);


				if (localNationality==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("nationality cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localNationality);

				}

				xmlWriter.writeEndElement();
			} if (localDate_of_birthTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "date_of_birth", xmlWriter);


				if (localDate_of_birth==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("date_of_birth cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDate_of_birth);

				}

				xmlWriter.writeEndElement();
			} if (localEmail_addressTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "email_address", xmlWriter);


				if (localEmail_address==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("email_address cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localEmail_address);

				}

				xmlWriter.writeEndElement();
			} if (localMothers_maiden_nameTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "mothers_maiden_name", xmlWriter);


				if (localMothers_maiden_name==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("mothers_maiden_name cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localMothers_maiden_name);

				}

				xmlWriter.writeEndElement();
			} if (localDriving_license_noTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "driving_license_no", xmlWriter);


				if (localDriving_license_no==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("driving_license_no cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDriving_license_no);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd")){
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

			if (localPreferred_nameTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"preferred_name"));

				if (localPreferred_name != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPreferred_name));
				} else {
					throw new org.apache.axis2.databinding.ADBException("preferred_name cannot be null!!");
				}
			} if (localGenderTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"gender"));

				if (localGender != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGender));
				} else {
					throw new org.apache.axis2.databinding.ADBException("gender cannot be null!!");
				}
			} if (localName_line_1Tracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"name_line_1"));

				if (localName_line_1 != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localName_line_1));
				} else {
					throw new org.apache.axis2.databinding.ADBException("name_line_1 cannot be null!!");
				}
			} if (localName_line_2Tracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"name_line_2"));

				if (localName_line_2 != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localName_line_2));
				} else {
					throw new org.apache.axis2.databinding.ADBException("name_line_2 cannot be null!!");
				}
			} if (localName_line_3Tracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"name_line_3"));

				if (localName_line_3 != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localName_line_3));
				} else {
					throw new org.apache.axis2.databinding.ADBException("name_line_3 cannot be null!!");
				}
			} if (localPassport_noTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"passport_no"));

				if (localPassport_no != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPassport_no));
				} else {
					throw new org.apache.axis2.databinding.ADBException("passport_no cannot be null!!");
				}
			} if (localNationalityTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"nationality"));

				if (localNationality != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNationality));
				} else {
					throw new org.apache.axis2.databinding.ADBException("nationality cannot be null!!");
				}
			} if (localDate_of_birthTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"date_of_birth"));

				if (localDate_of_birth != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDate_of_birth));
				} else {
					throw new org.apache.axis2.databinding.ADBException("date_of_birth cannot be null!!");
				}
			} if (localEmail_addressTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"email_address"));

				if (localEmail_address != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEmail_address));
				} else {
					throw new org.apache.axis2.databinding.ADBException("email_address cannot be null!!");
				}
			} if (localMothers_maiden_nameTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"mothers_maiden_name"));

				if (localMothers_maiden_name != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMothers_maiden_name));
				} else {
					throw new org.apache.axis2.databinding.ADBException("mothers_maiden_name cannot be null!!");
				}
			} if (localDriving_license_noTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"driving_license_no"));

				if (localDriving_license_no != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDriving_license_no));
				} else {
					throw new org.apache.axis2.databinding.ADBException("driving_license_no cannot be null!!");
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
			public static Personal_details_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				Personal_details_type0 object =
						new Personal_details_type0();

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

							if (!"personal_details_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (Personal_details_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","preferred_name").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"preferred_name" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPreferred_name(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","gender").equals(reader.getName())){

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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","name_line_1").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"name_line_1" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setName_line_1(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","name_line_2").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"name_line_2" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setName_line_2(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","name_line_3").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"name_line_3" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setName_line_3(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","passport_no").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"passport_no" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPassport_no(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","nationality").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"nationality" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNationality(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","date_of_birth").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"date_of_birth" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDate_of_birth(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","email_address").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"email_address" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setEmail_address(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","mothers_maiden_name").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"mothers_maiden_name" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setMothers_maiden_name(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","driving_license_no").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"driving_license_no" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDriving_license_no(
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


	public static class Address_details
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
				"address_details",
				"ns1");



		/**
		 * field for Address_details
		 */


		protected Address_details_type0 localAddress_details ;


		/**
		 * Auto generated getter method
		 * @return Address_details_type0
		 */
		public  Address_details_type0 getAddress_details(){
			return localAddress_details;
		}



		/**
		 * Auto generated setter method
		 * @param param Address_details
		 */
		public void setAddress_details(Address_details_type0 param){

			this.localAddress_details=param;


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

			if (localAddress_details==null){
				throw new org.apache.axis2.databinding.ADBException("address_details cannot be null!");
			}
			localAddress_details.serialize(MY_QNAME,xmlWriter);


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd")){
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
			return localAddress_details.getPullParser(MY_QNAME);

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
			public static Address_details parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				Address_details object =
						new Address_details();

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

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","address_details").equals(reader.getName())){

								object.setAddress_details(Address_details_type0.Factory.parse(reader));

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


	public static class Installment_plans_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = installment_plans_type0
                Namespace URI = http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd
                Namespace Prefix = ns1
		 */


		/**
		 * field for Installment_plan_type
		 */


		protected java.lang.String localInstallment_plan_type ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localInstallment_plan_typeTracker = false ;

		public boolean isInstallment_plan_typeSpecified(){
			return localInstallment_plan_typeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getInstallment_plan_type(){
			return localInstallment_plan_type;
		}



		/**
		 * Auto generated setter method
		 * @param param Installment_plan_type
		 */
		public void setInstallment_plan_type(java.lang.String param){
			localInstallment_plan_typeTracker = param != null;

			this.localInstallment_plan_type=param;


		}


		/**
		 * field for Plan_date
		 */


		protected java.lang.String localPlan_date ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPlan_dateTracker = false ;

		public boolean isPlan_dateSpecified(){
			return localPlan_dateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPlan_date(){
			return localPlan_date;
		}



		/**
		 * Auto generated setter method
		 * @param param Plan_date
		 */
		public void setPlan_date(java.lang.String param){
			localPlan_dateTracker = param != null;

			this.localPlan_date=param;


		}


		/**
		 * field for Principle_amount
		 */


		protected java.lang.String localPrinciple_amount ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPrinciple_amount(){
			return localPrinciple_amount;
		}



		/**
		 * Auto generated setter method
		 * @param param Principle_amount
		 */
		public void setPrinciple_amount(java.lang.String param){

			this.localPrinciple_amount=param;


		}


		/**
		 * field for Principle_amount_remaining
		 */


		protected java.lang.String localPrinciple_amount_remaining ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPrinciple_amount_remaining(){
			return localPrinciple_amount_remaining;
		}



		/**
		 * Auto generated setter method
		 * @param param Principle_amount_remaining
		 */
		public void setPrinciple_amount_remaining(java.lang.String param){

			this.localPrinciple_amount_remaining=param;


		}


		/**
		 * field for Principle_amount_billed
		 */


		protected java.lang.String localPrinciple_amount_billed ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPrinciple_amount_billedTracker = false ;

		public boolean isPrinciple_amount_billedSpecified(){
			return localPrinciple_amount_billedTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPrinciple_amount_billed(){
			return localPrinciple_amount_billed;
		}



		/**
		 * Auto generated setter method
		 * @param param Principle_amount_billed
		 */
		public void setPrinciple_amount_billed(java.lang.String param){
			localPrinciple_amount_billedTracker = param != null;

			this.localPrinciple_amount_billed=param;


		}


		/**
		 * field for Installment_amount_billed
		 */


		protected java.lang.String localInstallment_amount_billed ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getInstallment_amount_billed(){
			return localInstallment_amount_billed;
		}



		/**
		 * Auto generated setter method
		 * @param param Installment_amount_billed
		 */
		public void setInstallment_amount_billed(java.lang.String param){

			this.localInstallment_amount_billed=param;


		}


		/**
		 * field for Installment_amount_remaining
		 */


		protected java.lang.String localInstallment_amount_remaining ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localInstallment_amount_remainingTracker = false ;

		public boolean isInstallment_amount_remainingSpecified(){
			return localInstallment_amount_remainingTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getInstallment_amount_remaining(){
			return localInstallment_amount_remaining;
		}



		/**
		 * Auto generated setter method
		 * @param param Installment_amount_remaining
		 */
		public void setInstallment_amount_remaining(java.lang.String param){
			localInstallment_amount_remainingTracker = param != null;

			this.localInstallment_amount_remaining=param;


		}


		/**
		 * field for Monthly_installment_amount
		 */


		protected java.lang.String localMonthly_installment_amount ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getMonthly_installment_amount(){
			return localMonthly_installment_amount;
		}



		/**
		 * Auto generated setter method
		 * @param param Monthly_installment_amount
		 */
		public void setMonthly_installment_amount(java.lang.String param){

			this.localMonthly_installment_amount=param;


		}


		/**
		 * field for Total_no_of_installments
		 */


		protected java.lang.String localTotal_no_of_installments ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getTotal_no_of_installments(){
			return localTotal_no_of_installments;
		}



		/**
		 * Auto generated setter method
		 * @param param Total_no_of_installments
		 */
		public void setTotal_no_of_installments(java.lang.String param){

			this.localTotal_no_of_installments=param;


		}


		/**
		 * field for No_of_installments_remaining
		 */


		protected java.lang.String localNo_of_installments_remaining ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getNo_of_installments_remaining(){
			return localNo_of_installments_remaining;
		}



		/**
		 * Auto generated setter method
		 * @param param No_of_installments_remaining
		 */
		public void setNo_of_installments_remaining(java.lang.String param){

			this.localNo_of_installments_remaining=param;


		}


		/**
		 * field for Interest_amount
		 */


		protected java.lang.String localInterest_amount ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getInterest_amount(){
			return localInterest_amount;
		}



		/**
		 * Auto generated setter method
		 * @param param Interest_amount
		 */
		public void setInterest_amount(java.lang.String param){

			this.localInterest_amount=param;


		}


		/**
		 * field for Interest_amount_billed
		 */


		protected java.lang.String localInterest_amount_billed ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getInterest_amount_billed(){
			return localInterest_amount_billed;
		}



		/**
		 * Auto generated setter method
		 * @param param Interest_amount_billed
		 */
		public void setInterest_amount_billed(java.lang.String param){

			this.localInterest_amount_billed=param;


		}


		/**
		 * field for Interest_amount_remaining
		 */


		protected java.lang.String localInterest_amount_remaining ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localInterest_amount_remainingTracker = false ;

		public boolean isInterest_amount_remainingSpecified(){
			return localInterest_amount_remainingTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getInterest_amount_remaining(){
			return localInterest_amount_remaining;
		}



		/**
		 * Auto generated setter method
		 * @param param Interest_amount_remaining
		 */
		public void setInterest_amount_remaining(java.lang.String param){
			localInterest_amount_remainingTracker = param != null;

			this.localInterest_amount_remaining=param;


		}


		/**
		 * field for Installment_description
		 */


		protected java.lang.String localInstallment_description ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localInstallment_descriptionTracker = false ;

		public boolean isInstallment_descriptionSpecified(){
			return localInstallment_descriptionTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getInstallment_description(){
			return localInstallment_description;
		}



		/**
		 * Auto generated setter method
		 * @param param Installment_description
		 */
		public void setInstallment_description(java.lang.String param){
			localInstallment_descriptionTracker = param != null;

			this.localInstallment_description=param;


		}


		/**
		 * field for Foreclosure_indicator
		 */


		protected java.lang.String localForeclosure_indicator ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localForeclosure_indicatorTracker = false ;

		public boolean isForeclosure_indicatorSpecified(){
			return localForeclosure_indicatorTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getForeclosure_indicator(){
			return localForeclosure_indicator;
		}



		/**
		 * Auto generated setter method
		 * @param param Foreclosure_indicator
		 */
		public void setForeclosure_indicator(java.lang.String param){
			localForeclosure_indicatorTracker = param != null;

			this.localForeclosure_indicator=param;


		}


		/**
		 * field for Emi_principle
		 */


		protected java.lang.String localEmi_principle ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localEmi_principleTracker = false ;

		public boolean isEmi_principleSpecified(){
			return localEmi_principleTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getEmi_principle(){
			return localEmi_principle;
		}



		/**
		 * Auto generated setter method
		 * @param param Emi_principle
		 */
		public void setEmi_principle(java.lang.String param){
			localEmi_principleTracker = param != null;

			this.localEmi_principle=param;


		}


		/**
		 * field for Emi_interest
		 */


		protected java.lang.String localEmi_interest ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localEmi_interestTracker = false ;

		public boolean isEmi_interestSpecified(){
			return localEmi_interestTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getEmi_interest(){
			return localEmi_interest;
		}



		/**
		 * Auto generated setter method
		 * @param param Emi_interest
		 */
		public void setEmi_interest(java.lang.String param){
			localEmi_interestTracker = param != null;

			this.localEmi_interest=param;


		}


		/**
		 * field for Calc_rate
		 */


		protected java.lang.String localCalc_rate ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCalc_rateTracker = false ;

		public boolean isCalc_rateSpecified(){
			return localCalc_rateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCalc_rate(){
			return localCalc_rate;
		}



		/**
		 * Auto generated setter method
		 * @param param Calc_rate
		 */
		public void setCalc_rate(java.lang.String param){
			localCalc_rateTracker = param != null;

			this.localCalc_rate=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":installment_plans_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"installment_plans_type0",
							xmlWriter);
				}


			}
			if (localInstallment_plan_typeTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "installment_plan_type", xmlWriter);


				if (localInstallment_plan_type==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("installment_plan_type cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localInstallment_plan_type);

				}

				xmlWriter.writeEndElement();
			} if (localPlan_dateTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "plan_date", xmlWriter);


				if (localPlan_date==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("plan_date cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPlan_date);

				}

				xmlWriter.writeEndElement();
			}
			namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
			writeStartElement(null, namespace, "principle_amount", xmlWriter);


			if (localPrinciple_amount==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("principle_amount cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localPrinciple_amount);

			}

			xmlWriter.writeEndElement();

			namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
			writeStartElement(null, namespace, "principle_amount_remaining", xmlWriter);


			if (localPrinciple_amount_remaining==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("principle_amount_remaining cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localPrinciple_amount_remaining);

			}

			xmlWriter.writeEndElement();
			if (localPrinciple_amount_billedTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "principle_amount_billed", xmlWriter);


				if (localPrinciple_amount_billed==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("principle_amount_billed cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPrinciple_amount_billed);

				}

				xmlWriter.writeEndElement();
			}
			namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
			writeStartElement(null, namespace, "installment_amount_billed", xmlWriter);


			if (localInstallment_amount_billed==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("installment_amount_billed cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localInstallment_amount_billed);

			}

			xmlWriter.writeEndElement();
			if (localInstallment_amount_remainingTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "installment_amount_remaining", xmlWriter);


				if (localInstallment_amount_remaining==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("installment_amount_remaining cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localInstallment_amount_remaining);

				}

				xmlWriter.writeEndElement();
			}
			namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
			writeStartElement(null, namespace, "monthly_installment_amount", xmlWriter);


			if (localMonthly_installment_amount==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("monthly_installment_amount cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localMonthly_installment_amount);

			}

			xmlWriter.writeEndElement();

			namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
			writeStartElement(null, namespace, "total_no_of_installments", xmlWriter);


			if (localTotal_no_of_installments==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("total_no_of_installments cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localTotal_no_of_installments);

			}

			xmlWriter.writeEndElement();

			namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
			writeStartElement(null, namespace, "no_of_installments_remaining", xmlWriter);


			if (localNo_of_installments_remaining==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("no_of_installments_remaining cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localNo_of_installments_remaining);

			}

			xmlWriter.writeEndElement();

			namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
			writeStartElement(null, namespace, "interest_amount", xmlWriter);


			if (localInterest_amount==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("interest_amount cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localInterest_amount);

			}

			xmlWriter.writeEndElement();

			namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
			writeStartElement(null, namespace, "interest_amount_billed", xmlWriter);


			if (localInterest_amount_billed==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("interest_amount_billed cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localInterest_amount_billed);

			}

			xmlWriter.writeEndElement();
			if (localInterest_amount_remainingTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "interest_amount_remaining", xmlWriter);


				if (localInterest_amount_remaining==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("interest_amount_remaining cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localInterest_amount_remaining);

				}

				xmlWriter.writeEndElement();
			} if (localInstallment_descriptionTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "installment_description", xmlWriter);


				if (localInstallment_description==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("installment_description cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localInstallment_description);

				}

				xmlWriter.writeEndElement();
			} if (localForeclosure_indicatorTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "foreclosure_indicator", xmlWriter);


				if (localForeclosure_indicator==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("foreclosure_indicator cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localForeclosure_indicator);

				}

				xmlWriter.writeEndElement();
			} if (localEmi_principleTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "emi_principle", xmlWriter);


				if (localEmi_principle==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("emi_principle cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localEmi_principle);

				}

				xmlWriter.writeEndElement();
			} if (localEmi_interestTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "emi_interest", xmlWriter);


				if (localEmi_interest==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("emi_interest cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localEmi_interest);

				}

				xmlWriter.writeEndElement();
			} if (localCalc_rateTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "calc_rate", xmlWriter);


				if (localCalc_rate==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("calc_rate cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCalc_rate);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd")){
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

			if (localInstallment_plan_typeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"installment_plan_type"));

				if (localInstallment_plan_type != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInstallment_plan_type));
				} else {
					throw new org.apache.axis2.databinding.ADBException("installment_plan_type cannot be null!!");
				}
			} if (localPlan_dateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"plan_date"));

				if (localPlan_date != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPlan_date));
				} else {
					throw new org.apache.axis2.databinding.ADBException("plan_date cannot be null!!");
				}
			}
			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
					"principle_amount"));

			if (localPrinciple_amount != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPrinciple_amount));
			} else {
				throw new org.apache.axis2.databinding.ADBException("principle_amount cannot be null!!");
			}

			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
					"principle_amount_remaining"));

			if (localPrinciple_amount_remaining != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPrinciple_amount_remaining));
			} else {
				throw new org.apache.axis2.databinding.ADBException("principle_amount_remaining cannot be null!!");
			}
			if (localPrinciple_amount_billedTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"principle_amount_billed"));

				if (localPrinciple_amount_billed != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPrinciple_amount_billed));
				} else {
					throw new org.apache.axis2.databinding.ADBException("principle_amount_billed cannot be null!!");
				}
			}
			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
					"installment_amount_billed"));

			if (localInstallment_amount_billed != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInstallment_amount_billed));
			} else {
				throw new org.apache.axis2.databinding.ADBException("installment_amount_billed cannot be null!!");
			}
			if (localInstallment_amount_remainingTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"installment_amount_remaining"));

				if (localInstallment_amount_remaining != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInstallment_amount_remaining));
				} else {
					throw new org.apache.axis2.databinding.ADBException("installment_amount_remaining cannot be null!!");
				}
			}
			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
					"monthly_installment_amount"));

			if (localMonthly_installment_amount != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMonthly_installment_amount));
			} else {
				throw new org.apache.axis2.databinding.ADBException("monthly_installment_amount cannot be null!!");
			}

			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
					"total_no_of_installments"));

			if (localTotal_no_of_installments != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTotal_no_of_installments));
			} else {
				throw new org.apache.axis2.databinding.ADBException("total_no_of_installments cannot be null!!");
			}

			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
					"no_of_installments_remaining"));

			if (localNo_of_installments_remaining != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNo_of_installments_remaining));
			} else {
				throw new org.apache.axis2.databinding.ADBException("no_of_installments_remaining cannot be null!!");
			}

			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
					"interest_amount"));

			if (localInterest_amount != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInterest_amount));
			} else {
				throw new org.apache.axis2.databinding.ADBException("interest_amount cannot be null!!");
			}

			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
					"interest_amount_billed"));

			if (localInterest_amount_billed != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInterest_amount_billed));
			} else {
				throw new org.apache.axis2.databinding.ADBException("interest_amount_billed cannot be null!!");
			}
			if (localInterest_amount_remainingTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"interest_amount_remaining"));

				if (localInterest_amount_remaining != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInterest_amount_remaining));
				} else {
					throw new org.apache.axis2.databinding.ADBException("interest_amount_remaining cannot be null!!");
				}
			} if (localInstallment_descriptionTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"installment_description"));

				if (localInstallment_description != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInstallment_description));
				} else {
					throw new org.apache.axis2.databinding.ADBException("installment_description cannot be null!!");
				}
			} if (localForeclosure_indicatorTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"foreclosure_indicator"));

				if (localForeclosure_indicator != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localForeclosure_indicator));
				} else {
					throw new org.apache.axis2.databinding.ADBException("foreclosure_indicator cannot be null!!");
				}
			} if (localEmi_principleTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"emi_principle"));

				if (localEmi_principle != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEmi_principle));
				} else {
					throw new org.apache.axis2.databinding.ADBException("emi_principle cannot be null!!");
				}
			} if (localEmi_interestTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"emi_interest"));

				if (localEmi_interest != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEmi_interest));
				} else {
					throw new org.apache.axis2.databinding.ADBException("emi_interest cannot be null!!");
				}
			} if (localCalc_rateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"calc_rate"));

				if (localCalc_rate != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCalc_rate));
				} else {
					throw new org.apache.axis2.databinding.ADBException("calc_rate cannot be null!!");
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
			public static Installment_plans_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				Installment_plans_type0 object =
						new Installment_plans_type0();

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

							if (!"installment_plans_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (Installment_plans_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","installment_plan_type").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"installment_plan_type" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setInstallment_plan_type(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","plan_date").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"plan_date" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPlan_date(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","principle_amount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"principle_amount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPrinciple_amount(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","principle_amount_remaining").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"principle_amount_remaining" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPrinciple_amount_remaining(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","principle_amount_billed").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"principle_amount_billed" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPrinciple_amount_billed(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","installment_amount_billed").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"installment_amount_billed" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setInstallment_amount_billed(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","installment_amount_remaining").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"installment_amount_remaining" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setInstallment_amount_remaining(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","monthly_installment_amount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"monthly_installment_amount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setMonthly_installment_amount(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","total_no_of_installments").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"total_no_of_installments" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setTotal_no_of_installments(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","no_of_installments_remaining").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"no_of_installments_remaining" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setNo_of_installments_remaining(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","interest_amount").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"interest_amount" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setInterest_amount(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","interest_amount_billed").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"interest_amount_billed" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setInterest_amount_billed(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","interest_amount_remaining").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"interest_amount_remaining" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setInterest_amount_remaining(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","installment_description").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"installment_description" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setInstallment_description(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","foreclosure_indicator").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"foreclosure_indicator" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setForeclosure_indicator(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","emi_principle").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"emi_principle" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setEmi_principle(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","emi_interest").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"emi_interest" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setEmi_interest(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","calc_rate").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"calc_rate" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCalc_rate(
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


	public static class GetCreditCardDetailsReqMsg
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/common/InqCCDtl.xsd",
				"GetCreditCardDetailsReqMsg",
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
		 * field for GetCreditCardDetailsReq
		 */


		protected GetCreditCardDetailsReq_type0 localGetCreditCardDetailsReq ;


		/**
		 * Auto generated getter method
		 * @return GetCreditCardDetailsReq_type0
		 */
		public  GetCreditCardDetailsReq_type0 getGetCreditCardDetailsReq(){
			return localGetCreditCardDetailsReq;
		}



		/**
		 * Auto generated setter method
		 * @param param GetCreditCardDetailsReq
		 */
		public void setGetCreditCardDetailsReq(GetCreditCardDetailsReq_type0 param){

			this.localGetCreditCardDetailsReq=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/InqCCDtl.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":GetCreditCardDetailsReqMsg",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"GetCreditCardDetailsReqMsg",
							xmlWriter);
				}


			}

			if (localHeader==null){
				throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
			}
			localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
					xmlWriter);

			if (localGetCreditCardDetailsReq==null){
				throw new org.apache.axis2.databinding.ADBException("GetCreditCardDetailsReq cannot be null!!");
			}
			localGetCreditCardDetailsReq.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/InqCCDtl.xsd","GetCreditCardDetailsReq"),
					xmlWriter);

			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/InqCCDtl.xsd")){
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

			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/InqCCDtl.xsd",
					"GetCreditCardDetailsReq"));


			if (localGetCreditCardDetailsReq==null){
				throw new org.apache.axis2.databinding.ADBException("GetCreditCardDetailsReq cannot be null!!");
			}
			elementList.add(localGetCreditCardDetailsReq);


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
			public static GetCreditCardDetailsReqMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				GetCreditCardDetailsReqMsg object =
						new GetCreditCardDetailsReqMsg();

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

							if (!"GetCreditCardDetailsReqMsg".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (GetCreditCardDetailsReqMsg)ExtensionMapper.getTypeObject(
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

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/InqCCDtl.xsd","GetCreditCardDetailsReq").equals(reader.getName())){

						object.setGetCreditCardDetailsReq(GetCreditCardDetailsReq_type0.Factory.parse(reader));

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


	public static class GetCreditCardDetailsReq_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = GetCreditCardDetailsReq_type0
                Namespace URI = http://www.adcb.com/esb/common/InqCCDtl.xsd
                Namespace Prefix = ns3
		 */


		/**
		 * field for CreditCardNumber
		 */


		protected java.lang.String localCreditCardNumber ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCreditCardNumber(){
			return localCreditCardNumber;
		}



		/**
		 * Auto generated setter method
		 * @param param CreditCardNumber
		 */
		public void setCreditCardNumber(java.lang.String param){

			this.localCreditCardNumber=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/InqCCDtl.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":GetCreditCardDetailsReq_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"GetCreditCardDetailsReq_type0",
							xmlWriter);
				}


			}

			namespace = "http://www.adcb.com/esb/common/InqCCDtl.xsd";
			writeStartElement(null, namespace, "creditCardNumber", xmlWriter);


			if (localCreditCardNumber==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("creditCardNumber cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localCreditCardNumber);

			}

			xmlWriter.writeEndElement();

			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/InqCCDtl.xsd")){
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


			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/InqCCDtl.xsd",
					"creditCardNumber"));

			if (localCreditCardNumber != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCreditCardNumber));
			} else {
				throw new org.apache.axis2.databinding.ADBException("creditCardNumber cannot be null!!");
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
			public static GetCreditCardDetailsReq_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				GetCreditCardDetailsReq_type0 object =
						new GetCreditCardDetailsReq_type0();

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

							if (!"GetCreditCardDetailsReq_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (GetCreditCardDetailsReq_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/InqCCDtl.xsd","creditCardNumber").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"creditCardNumber" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCreditCardNumber(
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


	public static class Card_details
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
				"card_details",
				"ns1");



		/**
		 * field for Card_details
		 */


		protected Card_details_type0 localCard_details ;


		/**
		 * Auto generated getter method
		 * @return Card_details_type0
		 */
		public  Card_details_type0 getCard_details(){
			return localCard_details;
		}



		/**
		 * Auto generated setter method
		 * @param param Card_details
		 */
		public void setCard_details(Card_details_type0 param){

			this.localCard_details=param;


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

			if (localCard_details==null){
				throw new org.apache.axis2.databinding.ADBException("card_details cannot be null!");
			}
			localCard_details.serialize(MY_QNAME,xmlWriter);


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd")){
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
			return localCard_details.getPullParser(MY_QNAME);

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
			public static Card_details parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				Card_details object =
						new Card_details();

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

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","card_details").equals(reader.getName())){

								object.setCard_details(Card_details_type0.Factory.parse(reader));

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


	public static class Rep_CreditCardDetails_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = rep_CreditCardDetails_type0
                Namespace URI = http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd
                Namespace Prefix = ns1
		 */


		/**
		 * field for Card_details
		 */


		protected Card_details_type0 localCard_details ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCard_detailsTracker = false ;

		public boolean isCard_detailsSpecified(){
			return localCard_detailsTracker;
		}



		/**
		 * Auto generated getter method
		 * @return Card_details_type0
		 */
		public  Card_details_type0 getCard_details(){
			return localCard_details;
		}



		/**
		 * Auto generated setter method
		 * @param param Card_details
		 */
		public void setCard_details(Card_details_type0 param){
			localCard_detailsTracker = param != null;

			this.localCard_details=param;


		}


		/**
		 * field for Personal_details
		 */


		protected Personal_details_type0 localPersonal_details ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPersonal_detailsTracker = false ;

		public boolean isPersonal_detailsSpecified(){
			return localPersonal_detailsTracker;
		}



		/**
		 * Auto generated getter method
		 * @return Personal_details_type0
		 */
		public  Personal_details_type0 getPersonal_details(){
			return localPersonal_details;
		}



		/**
		 * Auto generated setter method
		 * @param param Personal_details
		 */
		public void setPersonal_details(Personal_details_type0 param){
			localPersonal_detailsTracker = param != null;

			this.localPersonal_details=param;


		}


		/**
		 * field for Address_details
		 */


		protected Address_details_type0 localAddress_details ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAddress_detailsTracker = false ;

		public boolean isAddress_detailsSpecified(){
			return localAddress_detailsTracker;
		}



		/**
		 * Auto generated getter method
		 * @return Address_details_type0
		 */
		public  Address_details_type0 getAddress_details(){
			return localAddress_details;
		}



		/**
		 * Auto generated setter method
		 * @param param Address_details
		 */
		public void setAddress_details(Address_details_type0 param){
			localAddress_detailsTracker = param != null;

			this.localAddress_details=param;


		}


		/**
		 * field for Employment_details
		 */


		protected Employment_details_type0 localEmployment_details ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localEmployment_detailsTracker = false ;

		public boolean isEmployment_detailsSpecified(){
			return localEmployment_detailsTracker;
		}



		/**
		 * Auto generated getter method
		 * @return Employment_details_type0
		 */
		public  Employment_details_type0 getEmployment_details(){
			return localEmployment_details;
		}



		/**
		 * Auto generated setter method
		 * @param param Employment_details
		 */
		public void setEmployment_details(Employment_details_type0 param){
			localEmployment_detailsTracker = param != null;

			this.localEmployment_details=param;


		}


		/**
		 * field for Contact_details
		 * This was an Array!
		 */


		protected Contact_details_type0[] localContact_details ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localContact_detailsTracker = false ;

		public boolean isContact_detailsSpecified(){
			return localContact_detailsTracker;
		}



		/**
		 * Auto generated getter method
		 * @return Contact_details_type0[]
		 */
		public  Contact_details_type0[] getContact_details(){
			return localContact_details;
		}






		/**
		 * validate the array for Contact_details
		 */
		protected void validateContact_details(Contact_details_type0[] param){

		}


		/**
		 * Auto generated setter method
		 * @param param Contact_details
		 */
		public void setContact_details(Contact_details_type0[] param){

			validateContact_details(param);

			localContact_detailsTracker = param != null;

			this.localContact_details=param;
		}



		/**
		 * Auto generated add method for the array for convenience
		 * @param param Contact_details_type0
		 */
		public void addContact_details(Contact_details_type0 param){
			if (localContact_details == null){
				localContact_details = new Contact_details_type0[]{};
			}


			//update the setting tracker
			localContact_detailsTracker = true;


			java.util.List list =
					org.apache.axis2.databinding.utils.ConverterUtil.toList(localContact_details);
			list.add(param);
			this.localContact_details =
					(Contact_details_type0[])list.toArray(
							new Contact_details_type0[list.size()]);

		}


		/**
		 * field for Other_details
		 */


		protected Other_details_type0 localOther_details ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localOther_detailsTracker = false ;

		public boolean isOther_detailsSpecified(){
			return localOther_detailsTracker;
		}



		/**
		 * Auto generated getter method
		 * @return Other_details_type0
		 */
		public  Other_details_type0 getOther_details(){
			return localOther_details;
		}



		/**
		 * Auto generated setter method
		 * @param param Other_details
		 */
		public void setOther_details(Other_details_type0 param){
			localOther_detailsTracker = param != null;

			this.localOther_details=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":rep_CreditCardDetails_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"rep_CreditCardDetails_type0",
							xmlWriter);
				}


			}
			if (localCard_detailsTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "card_details", xmlWriter);


				if (localCard_details==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("card_details cannot be null!!");

				}else{


					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCard_details));

				}

				xmlWriter.writeEndElement();
			} if (localPersonal_detailsTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "personal_details", xmlWriter);


				if (localPersonal_details==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("personal_details cannot be null!!");

				}else{


					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPersonal_details));

				}

				xmlWriter.writeEndElement();
			} if (localAddress_detailsTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "address_details", xmlWriter);


				if (localAddress_details==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("address_details cannot be null!!");

				}else{


					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAddress_details));

				}

				xmlWriter.writeEndElement();
			} if (localEmployment_detailsTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "employment_details", xmlWriter);


				if (localEmployment_details==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("employment_details cannot be null!!");

				}else{


					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEmployment_details));

				}

				xmlWriter.writeEndElement();
			} if (localContact_detailsTracker){
				if (localContact_details!=null) {
					namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
					for (int i = 0;i < localContact_details.length;i++){

						if (localContact_details[i] != null){

							writeStartElement(null, namespace, "contact_details", xmlWriter);


							xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localContact_details[i]));

							xmlWriter.writeEndElement();

						} else {

							// we have to do nothing since minOccurs is zero

						}

					}
				} else {

					throw new org.apache.axis2.databinding.ADBException("contact_details cannot be null!!");

				}

			} if (localOther_detailsTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "other_details", xmlWriter);


				if (localOther_details==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("other_details cannot be null!!");

				}else{


					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOther_details));

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd")){
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

			if (localCard_detailsTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"card_details"));

				if (localCard_details != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCard_details));
				} else {
					throw new org.apache.axis2.databinding.ADBException("card_details cannot be null!!");
				}
			} if (localPersonal_detailsTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"personal_details"));

				if (localPersonal_details != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPersonal_details));
				} else {
					throw new org.apache.axis2.databinding.ADBException("personal_details cannot be null!!");
				}
			} if (localAddress_detailsTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"address_details"));

				if (localAddress_details != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAddress_details));
				} else {
					throw new org.apache.axis2.databinding.ADBException("address_details cannot be null!!");
				}
			} if (localEmployment_detailsTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"employment_details"));

				if (localEmployment_details != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEmployment_details));
				} else {
					throw new org.apache.axis2.databinding.ADBException("employment_details cannot be null!!");
				}
			} if (localContact_detailsTracker){
				if (localContact_details!=null){
					for (int i = 0;i < localContact_details.length;i++){

						if (localContact_details[i] != null){
							elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
									"contact_details"));
							elementList.add(
									org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localContact_details[i]));
						} else {

							// have to do nothing

						}


					}
				} else {

					throw new org.apache.axis2.databinding.ADBException("contact_details cannot be null!!");

				}

			} if (localOther_detailsTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"other_details"));

				if (localOther_details != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOther_details));
				} else {
					throw new org.apache.axis2.databinding.ADBException("other_details cannot be null!!");
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
			public static Rep_CreditCardDetails_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				Rep_CreditCardDetails_type0 object =
						new Rep_CreditCardDetails_type0();

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

							if (!"rep_CreditCardDetails_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (Rep_CreditCardDetails_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();

					java.util.ArrayList list5 = new java.util.ArrayList();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","card_details").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"card_details" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						//						object.setCard_details(org.apache.axis2.databinding.utils.ConverterUtil.convertToCard_details_type0(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","personal_details").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"personal_details" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						//						object.setPersonal_details(org.apache.axis2.databinding.utils.ConverterUtil.convertToPersonal_details_type0(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","address_details").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"address_details" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						//						object.setAddress_details(org.apache.axis2.databinding.utils.ConverterUtil.convertToAddress_details_type0(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","employment_details").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"employment_details" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						//						object.setEmployment_details(org.apache.axis2.databinding.utils.ConverterUtil.convertToEmployment_details_type0(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","contact_details").equals(reader.getName())){



						// Process the array and step past its final element's end.
						list5.add(reader.getElementText());

						//loop until we find a start element that is not part of this array
						boolean loopDone5 = false;
						while(!loopDone5){
							// Ensure we are at the EndElement
							while (!reader.isEndElement()){
								reader.next();
							}
							// Step out of this element
							reader.next();
							// Step to next element event.
							while (!reader.isStartElement() && !reader.isEndElement())
								reader.next();
							if (reader.isEndElement()){
								//two continuous end elements means we are exiting the xml structure
								loopDone5 = true;
							} else {
								if (new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","contact_details").equals(reader.getName())){
									list5.add(reader.getElementText());

								}else{
									loopDone5 = true;
								}
							}
						}
						// call the converter utility  to convert and set the array

						object.setContact_details((Contact_details_type0[])
								org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
										Contact_details_type0.class,list5));

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","other_details").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"other_details" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						//						object.setOther_details(org.apache.axis2.databinding.utils.ConverterUtil.convertToOther_details_type0(content));

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


	public static class Personal_details
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
				"personal_details",
				"ns1");



		/**
		 * field for Personal_details
		 */


		protected Personal_details_type0 localPersonal_details ;


		/**
		 * Auto generated getter method
		 * @return Personal_details_type0
		 */
		public  Personal_details_type0 getPersonal_details(){
			return localPersonal_details;
		}



		/**
		 * Auto generated setter method
		 * @param param Personal_details
		 */
		public void setPersonal_details(Personal_details_type0 param){

			this.localPersonal_details=param;


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

			if (localPersonal_details==null){
				throw new org.apache.axis2.databinding.ADBException("personal_details cannot be null!");
			}
			localPersonal_details.serialize(MY_QNAME,xmlWriter);


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd")){
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
			return localPersonal_details.getPullParser(MY_QNAME);

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
			public static Personal_details parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				Personal_details object =
						new Personal_details();

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

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","personal_details").equals(reader.getName())){

								object.setPersonal_details(Personal_details_type0.Factory.parse(reader));

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


	public static class Insurance_products_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = insurance_products_type0
                Namespace URI = http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd
                Namespace Prefix = ns1
		 */


		/**
		 * field for Insurance_type
		 */


		protected java.lang.String localInsurance_type ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getInsurance_type(){
			return localInsurance_type;
		}



		/**
		 * Auto generated setter method
		 * @param param Insurance_type
		 */
		public void setInsurance_type(java.lang.String param){

			this.localInsurance_type=param;


		}


		/**
		 * field for Insurance_status
		 */


		protected java.lang.String localInsurance_status ;


		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getInsurance_status(){
			return localInsurance_status;
		}



		/**
		 * Auto generated setter method
		 * @param param Insurance_status
		 */
		public void setInsurance_status(java.lang.String param){

			this.localInsurance_status=param;


		}


		/**
		 * field for Activated_date
		 */


		protected java.lang.String localActivated_date ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localActivated_dateTracker = false ;

		public boolean isActivated_dateSpecified(){
			return localActivated_dateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getActivated_date(){
			return localActivated_date;
		}



		/**
		 * Auto generated setter method
		 * @param param Activated_date
		 */
		public void setActivated_date(java.lang.String param){
			localActivated_dateTracker = param != null;

			this.localActivated_date=param;


		}


		/**
		 * field for Deactivated_date
		 */


		protected java.lang.String localDeactivated_date ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDeactivated_dateTracker = false ;

		public boolean isDeactivated_dateSpecified(){
			return localDeactivated_dateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDeactivated_date(){
			return localDeactivated_date;
		}



		/**
		 * Auto generated setter method
		 * @param param Deactivated_date
		 */
		public void setDeactivated_date(java.lang.String param){
			localDeactivated_dateTracker = param != null;

			this.localDeactivated_date=param;


		}


		/**
		 * field for Reinstated
		 */


		protected java.lang.String localReinstated ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localReinstatedTracker = false ;

		public boolean isReinstatedSpecified(){
			return localReinstatedTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getReinstated(){
			return localReinstated;
		}



		/**
		 * Auto generated setter method
		 * @param param Reinstated
		 */
		public void setReinstated(java.lang.String param){
			localReinstatedTracker = param != null;

			this.localReinstated=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":insurance_products_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"insurance_products_type0",
							xmlWriter);
				}


			}

			namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
			writeStartElement(null, namespace, "insurance_type", xmlWriter);


			if (localInsurance_type==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("insurance_type cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localInsurance_type);

			}

			xmlWriter.writeEndElement();

			namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
			writeStartElement(null, namespace, "insurance_status", xmlWriter);


			if (localInsurance_status==null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("insurance_status cannot be null!!");

			}else{


				xmlWriter.writeCharacters(localInsurance_status);

			}

			xmlWriter.writeEndElement();
			if (localActivated_dateTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "activated_date", xmlWriter);


				if (localActivated_date==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("activated_date cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localActivated_date);

				}

				xmlWriter.writeEndElement();
			} if (localDeactivated_dateTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "deactivated_date", xmlWriter);


				if (localDeactivated_date==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("deactivated_date cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDeactivated_date);

				}

				xmlWriter.writeEndElement();
			} if (localReinstatedTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "reinstated", xmlWriter);


				if (localReinstated==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("reinstated cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localReinstated);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd")){
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


			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
					"insurance_type"));

			if (localInsurance_type != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInsurance_type));
			} else {
				throw new org.apache.axis2.databinding.ADBException("insurance_type cannot be null!!");
			}

			elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
					"insurance_status"));

			if (localInsurance_status != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInsurance_status));
			} else {
				throw new org.apache.axis2.databinding.ADBException("insurance_status cannot be null!!");
			}
			if (localActivated_dateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"activated_date"));

				if (localActivated_date != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localActivated_date));
				} else {
					throw new org.apache.axis2.databinding.ADBException("activated_date cannot be null!!");
				}
			} if (localDeactivated_dateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"deactivated_date"));

				if (localDeactivated_date != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDeactivated_date));
				} else {
					throw new org.apache.axis2.databinding.ADBException("deactivated_date cannot be null!!");
				}
			} if (localReinstatedTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"reinstated"));

				if (localReinstated != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReinstated));
				} else {
					throw new org.apache.axis2.databinding.ADBException("reinstated cannot be null!!");
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
			public static Insurance_products_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				Insurance_products_type0 object =
						new Insurance_products_type0();

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

							if (!"insurance_products_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (Insurance_products_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","insurance_type").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"insurance_type" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setInsurance_type(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","insurance_status").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"insurance_status" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setInsurance_status(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","activated_date").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"activated_date" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setActivated_date(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","deactivated_date").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"deactivated_date" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDeactivated_date(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","reinstated").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"reinstated" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setReinstated(
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


	public static class Contact_details_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = contact_details_type0
                Namespace URI = http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd
                Namespace Prefix = ns1
		 */


		/**
		 * field for Contact_type
		 */


		protected java.lang.String localContact_type ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localContact_typeTracker = false ;

		public boolean isContact_typeSpecified(){
			return localContact_typeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getContact_type(){
			return localContact_type;
		}



		/**
		 * Auto generated setter method
		 * @param param Contact_type
		 */
		public void setContact_type(java.lang.String param){
			localContact_typeTracker = param != null;

			this.localContact_type=param;


		}


		/**
		 * field for Contact_no
		 */


		protected java.lang.String localContact_no ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localContact_noTracker = false ;

		public boolean isContact_noSpecified(){
			return localContact_noTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getContact_no(){
			return localContact_no;
		}



		/**
		 * Auto generated setter method
		 * @param param Contact_no
		 */
		public void setContact_no(java.lang.String param){
			localContact_noTracker = param != null;

			this.localContact_no=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":contact_details_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"contact_details_type0",
							xmlWriter);
				}


			}
			if (localContact_typeTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "contact_type", xmlWriter);


				if (localContact_type==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("contact_type cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localContact_type);

				}

				xmlWriter.writeEndElement();
			} if (localContact_noTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "contact_no", xmlWriter);


				if (localContact_no==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("contact_no cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localContact_no);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd")){
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

			if (localContact_typeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"contact_type"));

				if (localContact_type != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localContact_type));
				} else {
					throw new org.apache.axis2.databinding.ADBException("contact_type cannot be null!!");
				}
			} if (localContact_noTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"contact_no"));

				if (localContact_no != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localContact_no));
				} else {
					throw new org.apache.axis2.databinding.ADBException("contact_no cannot be null!!");
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
			public static Contact_details_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				Contact_details_type0 object =
						new Contact_details_type0();

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

							if (!"contact_details_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (Contact_details_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","contact_type").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"contact_type" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setContact_type(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","contact_no").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"contact_no" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setContact_no(
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


	public static class Other_details_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = other_details_type0
                Namespace URI = http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd
                Namespace Prefix = ns1
		 */


		/**
		 * field for Memo_line_1
		 */


		protected java.lang.String localMemo_line_1 ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localMemo_line_1Tracker = false ;

		public boolean isMemo_line_1Specified(){
			return localMemo_line_1Tracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getMemo_line_1(){
			return localMemo_line_1;
		}



		/**
		 * Auto generated setter method
		 * @param param Memo_line_1
		 */
		public void setMemo_line_1(java.lang.String param){
			localMemo_line_1Tracker = param != null;

			this.localMemo_line_1=param;


		}


		/**
		 * field for Memo_line_2
		 */


		protected java.lang.String localMemo_line_2 ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localMemo_line_2Tracker = false ;

		public boolean isMemo_line_2Specified(){
			return localMemo_line_2Tracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getMemo_line_2(){
			return localMemo_line_2;
		}



		/**
		 * Auto generated setter method
		 * @param param Memo_line_2
		 */
		public void setMemo_line_2(java.lang.String param){
			localMemo_line_2Tracker = param != null;

			this.localMemo_line_2=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":other_details_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"other_details_type0",
							xmlWriter);
				}


			}
			if (localMemo_line_1Tracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "memo_line_1", xmlWriter);


				if (localMemo_line_1==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("memo_line_1 cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localMemo_line_1);

				}

				xmlWriter.writeEndElement();
			} if (localMemo_line_2Tracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "memo_line_2", xmlWriter);


				if (localMemo_line_2==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("memo_line_2 cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localMemo_line_2);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd")){
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

			if (localMemo_line_1Tracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"memo_line_1"));

				if (localMemo_line_1 != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMemo_line_1));
				} else {
					throw new org.apache.axis2.databinding.ADBException("memo_line_1 cannot be null!!");
				}
			} if (localMemo_line_2Tracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"memo_line_2"));

				if (localMemo_line_2 != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMemo_line_2));
				} else {
					throw new org.apache.axis2.databinding.ADBException("memo_line_2 cannot be null!!");
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
			public static Other_details_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				Other_details_type0 object =
						new Other_details_type0();

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

							if (!"other_details_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (Other_details_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","memo_line_1").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"memo_line_1" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setMemo_line_1(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","memo_line_2").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"memo_line_2" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setMemo_line_2(
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


	public static class Address_details_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = address_details_type0
                Namespace URI = http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd
                Namespace Prefix = ns1
		 */


		/**
		 * field for Address_type
		 */


		protected java.lang.String localAddress_type ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAddress_typeTracker = false ;

		public boolean isAddress_typeSpecified(){
			return localAddress_typeTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getAddress_type(){
			return localAddress_type;
		}



		/**
		 * Auto generated setter method
		 * @param param Address_type
		 */
		public void setAddress_type(java.lang.String param){
			localAddress_typeTracker = param != null;

			this.localAddress_type=param;


		}


		/**
		 * field for Address_line_1
		 */


		protected java.lang.String localAddress_line_1 ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAddress_line_1Tracker = false ;

		public boolean isAddress_line_1Specified(){
			return localAddress_line_1Tracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getAddress_line_1(){
			return localAddress_line_1;
		}



		/**
		 * Auto generated setter method
		 * @param param Address_line_1
		 */
		public void setAddress_line_1(java.lang.String param){
			localAddress_line_1Tracker = param != null;

			this.localAddress_line_1=param;


		}


		/**
		 * field for Address_line_2
		 */


		protected java.lang.String localAddress_line_2 ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAddress_line_2Tracker = false ;

		public boolean isAddress_line_2Specified(){
			return localAddress_line_2Tracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getAddress_line_2(){
			return localAddress_line_2;
		}



		/**
		 * Auto generated setter method
		 * @param param Address_line_2
		 */
		public void setAddress_line_2(java.lang.String param){
			localAddress_line_2Tracker = param != null;

			this.localAddress_line_2=param;


		}


		/**
		 * field for City
		 */


		protected java.lang.String localCity ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCityTracker = false ;

		public boolean isCitySpecified(){
			return localCityTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCity(){
			return localCity;
		}



		/**
		 * Auto generated setter method
		 * @param param City
		 */
		public void setCity(java.lang.String param){
			localCityTracker = param != null;

			this.localCity=param;


		}


		/**
		 * field for State
		 */


		protected java.lang.String localState ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localStateTracker = false ;

		public boolean isStateSpecified(){
			return localStateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getState(){
			return localState;
		}



		/**
		 * Auto generated setter method
		 * @param param State
		 */
		public void setState(java.lang.String param){
			localStateTracker = param != null;

			this.localState=param;


		}


		/**
		 * field for Country
		 */


		protected java.lang.String localCountry ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localCountryTracker = false ;

		public boolean isCountrySpecified(){
			return localCountryTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getCountry(){
			return localCountry;
		}



		/**
		 * Auto generated setter method
		 * @param param Country
		 */
		public void setCountry(java.lang.String param){
			localCountryTracker = param != null;

			this.localCountry=param;


		}


		/**
		 * field for Zip
		 */


		protected java.lang.String localZip ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localZipTracker = false ;

		public boolean isZipSpecified(){
			return localZipTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getZip(){
			return localZip;
		}



		/**
		 * Auto generated setter method
		 * @param param Zip
		 */
		public void setZip(java.lang.String param){
			localZipTracker = param != null;

			this.localZip=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":address_details_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"address_details_type0",
							xmlWriter);
				}


			}
			if (localAddress_typeTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "address_type", xmlWriter);


				if (localAddress_type==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("address_type cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localAddress_type);

				}

				xmlWriter.writeEndElement();
			} if (localAddress_line_1Tracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "address_line_1", xmlWriter);


				if (localAddress_line_1==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("address_line_1 cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localAddress_line_1);

				}

				xmlWriter.writeEndElement();
			} if (localAddress_line_2Tracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "address_line_2", xmlWriter);


				if (localAddress_line_2==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("address_line_2 cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localAddress_line_2);

				}

				xmlWriter.writeEndElement();
			} if (localCityTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "city", xmlWriter);


				if (localCity==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("city cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCity);

				}

				xmlWriter.writeEndElement();
			} if (localStateTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "state", xmlWriter);


				if (localState==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("state cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localState);

				}

				xmlWriter.writeEndElement();
			} if (localCountryTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "country", xmlWriter);


				if (localCountry==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("country cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localCountry);

				}

				xmlWriter.writeEndElement();
			} if (localZipTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "zip", xmlWriter);


				if (localZip==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("zip cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localZip);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd")){
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

			if (localAddress_typeTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"address_type"));

				if (localAddress_type != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAddress_type));
				} else {
					throw new org.apache.axis2.databinding.ADBException("address_type cannot be null!!");
				}
			} if (localAddress_line_1Tracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"address_line_1"));

				if (localAddress_line_1 != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAddress_line_1));
				} else {
					throw new org.apache.axis2.databinding.ADBException("address_line_1 cannot be null!!");
				}
			} if (localAddress_line_2Tracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"address_line_2"));

				if (localAddress_line_2 != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAddress_line_2));
				} else {
					throw new org.apache.axis2.databinding.ADBException("address_line_2 cannot be null!!");
				}
			} if (localCityTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"city"));

				if (localCity != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCity));
				} else {
					throw new org.apache.axis2.databinding.ADBException("city cannot be null!!");
				}
			} if (localStateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"state"));

				if (localState != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localState));
				} else {
					throw new org.apache.axis2.databinding.ADBException("state cannot be null!!");
				}
			} if (localCountryTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"country"));

				if (localCountry != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCountry));
				} else {
					throw new org.apache.axis2.databinding.ADBException("country cannot be null!!");
				}
			} if (localZipTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"zip"));

				if (localZip != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localZip));
				} else {
					throw new org.apache.axis2.databinding.ADBException("zip cannot be null!!");
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
			public static Address_details_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				Address_details_type0 object =
						new Address_details_type0();

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

							if (!"address_details_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (Address_details_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","address_type").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"address_type" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAddress_type(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","address_line_1").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"address_line_1" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAddress_line_1(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","address_line_2").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"address_line_2" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAddress_line_2(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","city").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"city" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCity(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","state").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"state" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setState(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","country").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"country" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setCountry(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","zip").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"zip" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setZip(
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


	public static class Plans_type0
	implements org.apache.axis2.databinding.ADBBean{
		/* This type was generated from the piece of schema that had
                name = plans_type0
                Namespace URI = http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd
                Namespace Prefix = ns1
		 */


		/**
		 * field for Plan_number
		 */


		protected java.lang.String localPlan_number ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPlan_numberTracker = false ;

		public boolean isPlan_numberSpecified(){
			return localPlan_numberTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPlan_number(){
			return localPlan_number;
		}



		/**
		 * Auto generated setter method
		 * @param param Plan_number
		 */
		public void setPlan_number(java.lang.String param){
			localPlan_numberTracker = param != null;

			this.localPlan_number=param;


		}


		/**
		 * field for Payment_credited
		 */


		protected java.lang.String localPayment_credited ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPayment_creditedTracker = false ;

		public boolean isPayment_creditedSpecified(){
			return localPayment_creditedTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPayment_credited(){
			return localPayment_credited;
		}



		/**
		 * Auto generated setter method
		 * @param param Payment_credited
		 */
		public void setPayment_credited(java.lang.String param){
			localPayment_creditedTracker = param != null;

			this.localPayment_credited=param;


		}


		/**
		 * field for Outstanding_balance
		 */


		protected java.lang.String localOutstanding_balance ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localOutstanding_balanceTracker = false ;

		public boolean isOutstanding_balanceSpecified(){
			return localOutstanding_balanceTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getOutstanding_balance(){
			return localOutstanding_balance;
		}



		/**
		 * Auto generated setter method
		 * @param param Outstanding_balance
		 */
		public void setOutstanding_balance(java.lang.String param){
			localOutstanding_balanceTracker = param != null;

			this.localOutstanding_balance=param;


		}


		/**
		 * field for Minimum_amount_due
		 */


		protected java.lang.String localMinimum_amount_due ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localMinimum_amount_dueTracker = false ;

		public boolean isMinimum_amount_dueSpecified(){
			return localMinimum_amount_dueTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getMinimum_amount_due(){
			return localMinimum_amount_due;
		}



		/**
		 * Auto generated setter method
		 * @param param Minimum_amount_due
		 */
		public void setMinimum_amount_due(java.lang.String param){
			localMinimum_amount_dueTracker = param != null;

			this.localMinimum_amount_due=param;


		}


		/**
		 * field for Accrued_interest
		 */


		protected java.lang.String localAccrued_interest ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localAccrued_interestTracker = false ;

		public boolean isAccrued_interestSpecified(){
			return localAccrued_interestTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getAccrued_interest(){
			return localAccrued_interest;
		}



		/**
		 * Auto generated setter method
		 * @param param Accrued_interest
		 */
		public void setAccrued_interest(java.lang.String param){
			localAccrued_interestTracker = param != null;

			this.localAccrued_interest=param;


		}


		/**
		 * field for Perdiem_interest
		 */


		protected java.lang.String localPerdiem_interest ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPerdiem_interestTracker = false ;

		public boolean isPerdiem_interestSpecified(){
			return localPerdiem_interestTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPerdiem_interest(){
			return localPerdiem_interest;
		}



		/**
		 * Auto generated setter method
		 * @param param Perdiem_interest
		 */
		public void setPerdiem_interest(java.lang.String param){
			localPerdiem_interestTracker = param != null;

			this.localPerdiem_interest=param;


		}


		/**
		 * field for Deferred_interest
		 */


		protected java.lang.String localDeferred_interest ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localDeferred_interestTracker = false ;

		public boolean isDeferred_interestSpecified(){
			return localDeferred_interestTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getDeferred_interest(){
			return localDeferred_interest;
		}



		/**
		 * Auto generated setter method
		 * @param param Deferred_interest
		 */
		public void setDeferred_interest(java.lang.String param){
			localDeferred_interestTracker = param != null;

			this.localDeferred_interest=param;


		}


		/**
		 * field for Plan_open_date
		 */


		protected java.lang.String localPlan_open_date ;

		/*  This tracker boolean wil be used to detect whether the user called the set method
		 *   for this attribute. It will be used to determine whether to include this field
		 *   in the serialized XML
		 */
		protected boolean localPlan_open_dateTracker = false ;

		public boolean isPlan_open_dateSpecified(){
			return localPlan_open_dateTracker;
		}



		/**
		 * Auto generated getter method
		 * @return java.lang.String
		 */
		public  java.lang.String getPlan_open_date(){
			return localPlan_open_date;
		}



		/**
		 * Auto generated setter method
		 * @param param Plan_open_date
		 */
		public void setPlan_open_date(java.lang.String param){
			localPlan_open_dateTracker = param != null;

			this.localPlan_open_date=param;


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


				java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd");
				if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							namespacePrefix+":plans_type0",
							xmlWriter);
				} else {
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
							"plans_type0",
							xmlWriter);
				}


			}
			if (localPlan_numberTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "plan_number", xmlWriter);


				if (localPlan_number==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("plan_number cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPlan_number);

				}

				xmlWriter.writeEndElement();
			} if (localPayment_creditedTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "payment_credited", xmlWriter);


				if (localPayment_credited==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("payment_credited cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPayment_credited);

				}

				xmlWriter.writeEndElement();
			} if (localOutstanding_balanceTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "outstanding_balance", xmlWriter);


				if (localOutstanding_balance==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("outstanding_balance cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localOutstanding_balance);

				}

				xmlWriter.writeEndElement();
			} if (localMinimum_amount_dueTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "minimum_amount_due", xmlWriter);


				if (localMinimum_amount_due==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("minimum_amount_due cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localMinimum_amount_due);

				}

				xmlWriter.writeEndElement();
			} if (localAccrued_interestTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "accrued_interest", xmlWriter);


				if (localAccrued_interest==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("accrued_interest cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localAccrued_interest);

				}

				xmlWriter.writeEndElement();
			} if (localPerdiem_interestTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "perdiem_interest", xmlWriter);


				if (localPerdiem_interest==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("perdiem_interest cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPerdiem_interest);

				}

				xmlWriter.writeEndElement();
			} if (localDeferred_interestTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "deferred_interest", xmlWriter);


				if (localDeferred_interest==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("deferred_interest cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localDeferred_interest);

				}

				xmlWriter.writeEndElement();
			} if (localPlan_open_dateTracker){
				namespace = "http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd";
				writeStartElement(null, namespace, "plan_open_date", xmlWriter);


				if (localPlan_open_date==null){
					// write the nil attribute

					throw new org.apache.axis2.databinding.ADBException("plan_open_date cannot be null!!");

				}else{


					xmlWriter.writeCharacters(localPlan_open_date);

				}

				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd")){
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

			if (localPlan_numberTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"plan_number"));

				if (localPlan_number != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPlan_number));
				} else {
					throw new org.apache.axis2.databinding.ADBException("plan_number cannot be null!!");
				}
			} if (localPayment_creditedTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"payment_credited"));

				if (localPayment_credited != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPayment_credited));
				} else {
					throw new org.apache.axis2.databinding.ADBException("payment_credited cannot be null!!");
				}
			} if (localOutstanding_balanceTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"outstanding_balance"));

				if (localOutstanding_balance != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOutstanding_balance));
				} else {
					throw new org.apache.axis2.databinding.ADBException("outstanding_balance cannot be null!!");
				}
			} if (localMinimum_amount_dueTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"minimum_amount_due"));

				if (localMinimum_amount_due != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMinimum_amount_due));
				} else {
					throw new org.apache.axis2.databinding.ADBException("minimum_amount_due cannot be null!!");
				}
			} if (localAccrued_interestTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"accrued_interest"));

				if (localAccrued_interest != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAccrued_interest));
				} else {
					throw new org.apache.axis2.databinding.ADBException("accrued_interest cannot be null!!");
				}
			} if (localPerdiem_interestTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"perdiem_interest"));

				if (localPerdiem_interest != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPerdiem_interest));
				} else {
					throw new org.apache.axis2.databinding.ADBException("perdiem_interest cannot be null!!");
				}
			} if (localDeferred_interestTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"deferred_interest"));

				if (localDeferred_interest != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDeferred_interest));
				} else {
					throw new org.apache.axis2.databinding.ADBException("deferred_interest cannot be null!!");
				}
			} if (localPlan_open_dateTracker){
				elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
						"plan_open_date"));

				if (localPlan_open_date != null){
					elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPlan_open_date));
				} else {
					throw new org.apache.axis2.databinding.ADBException("plan_open_date cannot be null!!");
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
			public static Plans_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				Plans_type0 object =
						new Plans_type0();

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

							if (!"plans_type0".equals(type)){
								//find namespace for the prefix
								java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (Plans_type0)ExtensionMapper.getTypeObject(
										nsUri,type,reader);
							}


						}


					}




					// Note all attributes that were handled. Used to differ normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes = new java.util.Vector();




					reader.next();


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","plan_number").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"plan_number" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPlan_number(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","payment_credited").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"payment_credited" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPayment_credited(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","outstanding_balance").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"outstanding_balance" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setOutstanding_balance(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","minimum_amount_due").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"minimum_amount_due" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setMinimum_amount_due(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","accrued_interest").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"accrued_interest" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setAccrued_interest(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","perdiem_interest").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"perdiem_interest" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPerdiem_interest(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","deferred_interest").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"deferred_interest" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setDeferred_interest(
								org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					}  // End of if for expected property start element

					else {

					}


					while (!reader.isStartElement() && !reader.isEndElement()) reader.next();

					if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","plan_open_date").equals(reader.getName())){

						nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
						if ("true".equals(nillableValue) || "1".equals(nillableValue)){
							throw new org.apache.axis2.databinding.ADBException("The element: "+"plan_open_date" +"  cannot be null");
						}


						java.lang.String content = reader.getElementText();

						object.setPlan_open_date(
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


	public static class Other_details
	implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
				"http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd",
				"other_details",
				"ns1");



		/**
		 * field for Other_details
		 */


		protected Other_details_type0 localOther_details ;


		/**
		 * Auto generated getter method
		 * @return Other_details_type0
		 */
		public  Other_details_type0 getOther_details(){
			return localOther_details;
		}



		/**
		 * Auto generated setter method
		 * @param param Other_details
		 */
		public void setOther_details(Other_details_type0 param){

			this.localOther_details=param;


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

			if (localOther_details==null){
				throw new org.apache.axis2.databinding.ADBException("other_details cannot be null!");
			}
			localOther_details.serialize(MY_QNAME,xmlWriter);


		}

		private static java.lang.String generatePrefix(java.lang.String namespace) {
			if(namespace.equals("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd")){
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
			return localOther_details.getPullParser(MY_QNAME);

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
			public static Other_details parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				Other_details object =
						new Other_details();

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

							if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/Rep_InqCCDtl.xsd","other_details").equals(reader.getName())){

								object.setOther_details(Other_details_type0.Factory.parse(reader));

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


	private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqCCDtlStub.GetCreditCardDetailsReqMsg param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {


		try{
			return param.getOMElement(com.newgen.dscop.stub.InqCCDtlStub.GetCreditCardDetailsReqMsg.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}


	}

	private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqCCDtlStub.GetCreditCardDetailsResMsg param, boolean optimizeContent)
			throws org.apache.axis2.AxisFault {


		try{
			return param.getOMElement(com.newgen.dscop.stub.InqCCDtlStub.GetCreditCardDetailsResMsg.MY_QNAME,
					org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		} catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}


	}


	private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.newgen.dscop.stub.InqCCDtlStub.GetCreditCardDetailsReqMsg param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
			throws org.apache.axis2.AxisFault{


		try{

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(com.newgen.dscop.stub.InqCCDtlStub.GetCreditCardDetailsReqMsg.MY_QNAME,factory));
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

			if (com.newgen.dscop.stub.InqCCDtlStub.GetCreditCardDetailsReqMsg.class.equals(type)){

				return com.newgen.dscop.stub.InqCCDtlStub.GetCreditCardDetailsReqMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());


			}

			if (com.newgen.dscop.stub.InqCCDtlStub.GetCreditCardDetailsResMsg.class.equals(type)){

				return com.newgen.dscop.stub.InqCCDtlStub.GetCreditCardDetailsResMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());


			}

		} catch (java.lang.Exception e) {
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
		return null;
	}




}
