
/**
 * InqFATCAValidationStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */
        package com.newgen.dscop.stub;

        

        /*
        *  InqFATCAValidationStub java implementation
        */

        
        public class InqFATCAValidationStub extends org.apache.axis2.client.Stub
        {
        protected org.apache.axis2.description.AxisOperation[] _operations;

        //hashmaps to keep the fault mapping
        private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
        private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
        private java.util.HashMap faultMessageMap = new java.util.HashMap();
        public String resFatcavMsg=null;//Added By Harish For inserting original mssg
        private static int counter = 0;
        public String output="";

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
     _service = new org.apache.axis2.description.AxisService("InqFATCAValidation" + getUniqueSuffix());
     addAnonymousOperations();

        //creating the operations
        org.apache.axis2.description.AxisOperation __operation;

        _operations = new org.apache.axis2.description.AxisOperation[1];
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://xmlns.example.com/1389002640628", "inqFATCAValidation_Oper"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[0]=__operation;
            
        
        }

    //populates the faults
    private void populateFaults(){
         


    }

    /**
      *Constructor that takes in a configContext
      */

    public InqFATCAValidationStub(org.apache.axis2.context.ConfigurationContext configurationContext,
       java.lang.String targetEndpoint)
       throws org.apache.axis2.AxisFault {
         this(configurationContext,targetEndpoint,false);
   }


   /**
     * Constructor that takes in a configContext  and useseperate listner
     */
   public InqFATCAValidationStub(org.apache.axis2.context.ConfigurationContext configurationContext,
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
    public InqFATCAValidationStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {
        
                    this(configurationContext,"http://10.109.1.57:5527/Services/EnterpriseServicesInquiry/ENTInquiryServices/WSDL/InqFATCAValidation.serviceagent/InqFATCAValidationPortTypeEndpoint0" );
                
    }

    /**
     * Default Constructor
     */
    public InqFATCAValidationStub() throws org.apache.axis2.AxisFault {
        
                    this("http://10.109.1.57:5527/Services/EnterpriseServicesInquiry/ENTInquiryServices/WSDL/InqFATCAValidation.serviceagent/InqFATCAValidationPortTypeEndpoint0" );
                
    }

    /**
     * Constructor taking the target endpoint
     */
    public InqFATCAValidationStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
        this(null,targetEndpoint);
    }


    /**
     * @author gupta.ashish
     * @Date : 21st May 2014
     * @Purpose : to get SOAP input XML as String
     */
    
    public String getinputXML(com.newgen.dscop.stub.InqFATCAValidationStub.InqFATCAValidationReqMsg inqFATCAValidationReqMsg0)throws java.rmi.RemoteException
    {
		org.apache.axis2.context.MessageContext _messageContext = null;
		try
		{
			org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
			_operationClient.getOptions().setAction("/Services/EnterpriseServicesInquiry/ENTInquiryServices/WSDL/InqFATCAValidation.serviceagent/InqFATCAValidationPortTypeEndpoint0/InqFATCAValidation_Oper");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
			addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
		
			// create a message context
			_messageContext = new org.apache.axis2.context.MessageContext();
			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env = null;    
                                    
	        env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
	        inqFATCAValidationReqMsg0,
	        optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1389002640628",
	        "inqFATCAValidation_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1389002640628",
	        "inqFATCAValidation_Oper"));
                                
			//adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);
				
			return env.toString();                
		}	
		catch(java.lang.ClassCastException e)
		{
			return "";
		} 
		finally 
		{
		}
    }

        
                    /**
                     * Auto generated method signature
                     * 
                     * @see com.newgen.dscop.stub.InqFATCAValidation#inqFATCAValidation_Oper
                     * @param inqFATCAValidationReqMsg0
                    
                     */

                    

                            public  com.newgen.dscop.stub.InqFATCAValidationStub.InqFATCAValidationResMsg inqFATCAValidation_Oper(

                            com.newgen.dscop.stub.InqFATCAValidationStub.InqFATCAValidationReqMsg inqFATCAValidationReqMsg0)
                        

                    throws java.rmi.RemoteException
                    
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
              _operationClient.getOptions().setAction("/Services/EnterpriseServicesInquiry/ENTInquiryServices/WSDL/InqFATCAValidation.serviceagent/InqFATCAValidationPortTypeEndpoint0/InqFATCAValidation_Oper");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    inqFATCAValidationReqMsg0,
                                                    optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1389002640628",
                                                    "inqFATCAValidation_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1389002640628",
                                                    "inqFATCAValidation_Oper"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        output=env.toString();
        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                resFatcavMsg=_returnEnv.toString();//Added By Harish For inserting original mssg
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.newgen.dscop.stub.InqFATCAValidationStub.InqFATCAValidationResMsg.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.newgen.dscop.stub.InqFATCAValidationStub.InqFATCAValidationResMsg)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqFATCAValidation_Oper"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqFATCAValidation_Oper"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqFATCAValidation_Oper"));
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
                * @see com.newgen.dscop.stub.InqFATCAValidation#startinqFATCAValidation_Oper
                    * @param inqFATCAValidationReqMsg0
                
                */
                public  void startinqFATCAValidation_Oper(

                 com.newgen.dscop.stub.InqFATCAValidationStub.InqFATCAValidationReqMsg inqFATCAValidationReqMsg0,

                  final com.newgen.dscop.stub.InqFATCAValidationCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
             _operationClient.getOptions().setAction("/Services/EnterpriseServicesInquiry/ENTInquiryServices/WSDL/InqFATCAValidation.serviceagent/InqFATCAValidationPortTypeEndpoint0/InqFATCAValidation_Oper");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    inqFATCAValidationReqMsg0,
                                                    optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1389002640628",
                                                    "inqFATCAValidation_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1389002640628",
                                                    "inqFATCAValidation_Oper"));
                                                
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
                                                                         com.newgen.dscop.stub.InqFATCAValidationStub.InqFATCAValidationResMsg.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultinqFATCAValidation_Oper(
                                        (com.newgen.dscop.stub.InqFATCAValidationStub.InqFATCAValidationResMsg)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorinqFATCAValidation_Oper(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqFATCAValidation_Oper"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqFATCAValidation_Oper"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.Exception ex = (java.lang.Exception) exceptionClass.newInstance();
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqFATCAValidation_Oper"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
					
										            callback.receiveErrorinqFATCAValidation_Oper(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinqFATCAValidation_Oper(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinqFATCAValidation_Oper(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinqFATCAValidation_Oper(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinqFATCAValidation_Oper(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinqFATCAValidation_Oper(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinqFATCAValidation_Oper(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinqFATCAValidation_Oper(f);
                                            }
									    } else {
										    callback.receiveErrorinqFATCAValidation_Oper(f);
									    }
									} else {
									    callback.receiveErrorinqFATCAValidation_Oper(f);
									}
								} else {
								    callback.receiveErrorinqFATCAValidation_Oper(error);
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
                                    callback.receiveErrorinqFATCAValidation_Oper(axisFault);
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
     //http://10.109.1.57:5527/Services/EnterpriseServicesInquiry/ENTInquiryServices/WSDL/InqFATCAValidation.serviceagent/InqFATCAValidationPortTypeEndpoint0
        public static class InqFATCAValidationRes_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = InqFATCAValidationRes_type0
                Namespace URI = http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd
                Namespace Prefix = ns2
                */
            

                        /**
                        * field for CustomerSegment
                        */

                        
                                    protected java.lang.String localCustomerSegment ;
                                

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
                            
                                            this.localCustomerSegment=param;
                                    

                               }
                            

                        /**
                        * field for ServiceType
                        */

                        
                                    protected java.lang.String localServiceType ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getServiceType(){
                               return localServiceType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ServiceType
                               */
                               public void setServiceType(java.lang.String param){
                            
                                            this.localServiceType=param;
                                    

                               }
                            

                        /**
                        * field for Product
                        */

                        
                                    protected java.lang.String localProduct ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getProduct(){
                               return localProduct;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Product
                               */
                               public void setProduct(java.lang.String param){
                            
                                            this.localProduct=param;
                                    

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
                        * field for ResidentialAddressCountry
                        */

                        
                                    protected java.lang.String localResidentialAddressCountry ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResidentialAddressCountryTracker = false ;

                           public boolean isResidentialAddressCountrySpecified(){
                               return localResidentialAddressCountryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getResidentialAddressCountry(){
                               return localResidentialAddressCountry;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ResidentialAddressCountry
                               */
                               public void setResidentialAddressCountry(java.lang.String param){
                            localResidentialAddressCountryTracker = param != null;
                                   
                                            this.localResidentialAddressCountry=param;
                                    

                               }
                            

                        /**
                        * field for MailingAddressCountry
                        */

                        
                                    protected java.lang.String localMailingAddressCountry ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMailingAddressCountryTracker = false ;

                           public boolean isMailingAddressCountrySpecified(){
                               return localMailingAddressCountryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMailingAddressCountry(){
                               return localMailingAddressCountry;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MailingAddressCountry
                               */
                               public void setMailingAddressCountry(java.lang.String param){
                            localMailingAddressCountryTracker = param != null;
                                   
                                            this.localMailingAddressCountry=param;
                                    

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
                        * field for TelephoneOffice
                        */

                        
                                    protected java.lang.String localTelephoneOffice ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTelephoneOfficeTracker = false ;

                           public boolean isTelephoneOfficeSpecified(){
                               return localTelephoneOfficeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTelephoneOffice(){
                               return localTelephoneOffice;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TelephoneOffice
                               */
                               public void setTelephoneOffice(java.lang.String param){
                            localTelephoneOfficeTracker = param != null;
                                   
                                            this.localTelephoneOffice=param;
                                    

                               }
                            

                        /**
                        * field for TelephoneMobile
                        */

                        
                                    protected java.lang.String localTelephoneMobile ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTelephoneMobileTracker = false ;

                           public boolean isTelephoneMobileSpecified(){
                               return localTelephoneMobileTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTelephoneMobile(){
                               return localTelephoneMobile;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TelephoneMobile
                               */
                               public void setTelephoneMobile(java.lang.String param){
                            localTelephoneMobileTracker = param != null;
                                   
                                            this.localTelephoneMobile=param;
                                    

                               }
                            

                        /**
                        * field for USpassportholder
                        */

                        
                                    protected java.lang.String localUSpassportholder ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localUSpassportholderTracker = false ;

                           public boolean isUSpassportholderSpecified(){
                               return localUSpassportholderTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getUSpassportholder(){
                               return localUSpassportholder;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param USpassportholder
                               */
                               public void setUSpassportholder(java.lang.String param){
                            localUSpassportholderTracker = param != null;
                                   
                                            this.localUSpassportholder=param;
                                    

                               }
                            

                        /**
                        * field for USTaxLiable
                        */

                        
                                    protected java.lang.String localUSTaxLiable ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localUSTaxLiableTracker = false ;

                           public boolean isUSTaxLiableSpecified(){
                               return localUSTaxLiableTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getUSTaxLiable(){
                               return localUSTaxLiable;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param USTaxLiable
                               */
                               public void setUSTaxLiable(java.lang.String param){
                            localUSTaxLiableTracker = param != null;
                                   
                                            this.localUSTaxLiable=param;
                                    

                               }
                            

                        /**
                        * field for CountryOfBirth
                        */

                        
                                    protected java.lang.String localCountryOfBirth ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCountryOfBirthTracker = false ;

                           public boolean isCountryOfBirthSpecified(){
                               return localCountryOfBirthTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCountryOfBirth(){
                               return localCountryOfBirth;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CountryOfBirth
                               */
                               public void setCountryOfBirth(java.lang.String param){
                            localCountryOfBirthTracker = param != null;
                                   
                                            this.localCountryOfBirth=param;
                                    

                               }
                            

                        /**
                        * field for StandingInstructionCountry
                        */

                        
                                    protected java.lang.String localStandingInstructionCountry ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localStandingInstructionCountryTracker = false ;

                           public boolean isStandingInstructionCountrySpecified(){
                               return localStandingInstructionCountryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getStandingInstructionCountry(){
                               return localStandingInstructionCountry;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param StandingInstructionCountry
                               */
                               public void setStandingInstructionCountry(java.lang.String param){
                            localStandingInstructionCountryTracker = param != null;
                                   
                                            this.localStandingInstructionCountry=param;
                                    

                               }
                            

                        /**
                        * field for POAHolderCountry
                        */

                        
                                    protected java.lang.String localPOAHolderCountry ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPOAHolderCountryTracker = false ;

                           public boolean isPOAHolderCountrySpecified(){
                               return localPOAHolderCountryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPOAHolderCountry(){
                               return localPOAHolderCountry;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param POAHolderCountry
                               */
                               public void setPOAHolderCountry(java.lang.String param){
                            localPOAHolderCountryTracker = param != null;
                                   
                                            this.localPOAHolderCountry=param;
                                    

                               }
                            

                        /**
                        * field for SSN
                        */

                        
                                    protected java.lang.String localSSN ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSSNTracker = false ;

                           public boolean isSSNSpecified(){
                               return localSSNTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSSN(){
                               return localSSN;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SSN
                               */
                               public void setSSN(java.lang.String param){
                            localSSNTracker = param != null;
                                   
                                            this.localSSN=param;
                                    

                               }
                            

                        /**
                        * field for ReturnValue
                        */

                        
                                    protected java.lang.String localReturnValue ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localReturnValueTracker = false ;

                           public boolean isReturnValueSpecified(){
                               return localReturnValueTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getReturnValue(){
                               return localReturnValue;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ReturnValue
                               */
                               public void setReturnValue(java.lang.String param){
                            localReturnValueTracker = param != null;
                                   
                                            this.localReturnValue=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":InqFATCAValidationRes_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "InqFATCAValidationRes_type0",
                           xmlWriter);
                   }

               
                   }
               
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "customerSegment", xmlWriter);
                             

                                          if (localCustomerSegment==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerSegment cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerSegment);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "serviceType", xmlWriter);
                             

                                          if (localServiceType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("serviceType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localServiceType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "product", xmlWriter);
                             

                                          if (localProduct==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("product cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localProduct);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localNationalityTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "nationality", xmlWriter);
                             

                                          if (localNationality==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("nationality cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localNationality);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localResidentialAddressCountryTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "residentialAddressCountry", xmlWriter);
                             

                                          if (localResidentialAddressCountry==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("residentialAddressCountry cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localResidentialAddressCountry);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMailingAddressCountryTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "mailingAddressCountry", xmlWriter);
                             

                                          if (localMailingAddressCountry==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("mailingAddressCountry cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMailingAddressCountry);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTelephoneResidenceTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "telephoneResidence", xmlWriter);
                             

                                          if (localTelephoneResidence==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("telephoneResidence cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTelephoneResidence);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTelephoneOfficeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "telephoneOffice", xmlWriter);
                             

                                          if (localTelephoneOffice==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("telephoneOffice cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTelephoneOffice);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTelephoneMobileTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "telephoneMobile", xmlWriter);
                             

                                          if (localTelephoneMobile==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("telephoneMobile cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTelephoneMobile);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localUSpassportholderTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "USpassportholder", xmlWriter);
                             

                                          if (localUSpassportholder==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("USpassportholder cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localUSpassportholder);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localUSTaxLiableTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "USTaxLiable", xmlWriter);
                             

                                          if (localUSTaxLiable==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("USTaxLiable cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localUSTaxLiable);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCountryOfBirthTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "countryOfBirth", xmlWriter);
                             

                                          if (localCountryOfBirth==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("countryOfBirth cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCountryOfBirth);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localStandingInstructionCountryTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "standingInstructionCountry", xmlWriter);
                             

                                          if (localStandingInstructionCountry==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("standingInstructionCountry cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localStandingInstructionCountry);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPOAHolderCountryTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "POAHolderCountry", xmlWriter);
                             

                                          if (localPOAHolderCountry==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("POAHolderCountry cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPOAHolderCountry);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSSNTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "SSN", xmlWriter);
                             

                                          if (localSSN==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("SSN cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSSN);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localReturnValueTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "returnValue", xmlWriter);
                             

                                          if (localReturnValue==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("returnValue cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localReturnValue);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd")){
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
                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
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

                
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "customerSegment"));
                                 
                                        if (localCustomerSegment != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerSegment));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerSegment cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "serviceType"));
                                 
                                        if (localServiceType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localServiceType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("serviceType cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "product"));
                                 
                                        if (localProduct != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProduct));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("product cannot be null!!");
                                        }
                                     if (localNationalityTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "nationality"));
                                 
                                        if (localNationality != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNationality));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("nationality cannot be null!!");
                                        }
                                    } if (localResidentialAddressCountryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "residentialAddressCountry"));
                                 
                                        if (localResidentialAddressCountry != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResidentialAddressCountry));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("residentialAddressCountry cannot be null!!");
                                        }
                                    } if (localMailingAddressCountryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "mailingAddressCountry"));
                                 
                                        if (localMailingAddressCountry != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMailingAddressCountry));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("mailingAddressCountry cannot be null!!");
                                        }
                                    } if (localTelephoneResidenceTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "telephoneResidence"));
                                 
                                        if (localTelephoneResidence != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTelephoneResidence));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("telephoneResidence cannot be null!!");
                                        }
                                    } if (localTelephoneOfficeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "telephoneOffice"));
                                 
                                        if (localTelephoneOffice != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTelephoneOffice));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("telephoneOffice cannot be null!!");
                                        }
                                    } if (localTelephoneMobileTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "telephoneMobile"));
                                 
                                        if (localTelephoneMobile != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTelephoneMobile));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("telephoneMobile cannot be null!!");
                                        }
                                    } if (localUSpassportholderTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "USpassportholder"));
                                 
                                        if (localUSpassportholder != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUSpassportholder));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("USpassportholder cannot be null!!");
                                        }
                                    } if (localUSTaxLiableTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "USTaxLiable"));
                                 
                                        if (localUSTaxLiable != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUSTaxLiable));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("USTaxLiable cannot be null!!");
                                        }
                                    } if (localCountryOfBirthTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "countryOfBirth"));
                                 
                                        if (localCountryOfBirth != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCountryOfBirth));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("countryOfBirth cannot be null!!");
                                        }
                                    } if (localStandingInstructionCountryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "standingInstructionCountry"));
                                 
                                        if (localStandingInstructionCountry != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStandingInstructionCountry));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("standingInstructionCountry cannot be null!!");
                                        }
                                    } if (localPOAHolderCountryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "POAHolderCountry"));
                                 
                                        if (localPOAHolderCountry != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPOAHolderCountry));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("POAHolderCountry cannot be null!!");
                                        }
                                    } if (localSSNTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "SSN"));
                                 
                                        if (localSSN != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSSN));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("SSN cannot be null!!");
                                        }
                                    } if (localReturnValueTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "returnValue"));
                                 
                                        if (localReturnValue != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReturnValue));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("returnValue cannot be null!!");
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
        public static InqFATCAValidationRes_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            InqFATCAValidationRes_type0 object =
                new InqFATCAValidationRes_type0();

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
                    
                            if (!"InqFATCAValidationRes_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (InqFATCAValidationRes_type0)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","customerSegment").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerSegment(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","serviceType").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setServiceType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","product").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setProduct(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","nationality").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNationality(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","residentialAddressCountry").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setResidentialAddressCountry(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","mailingAddressCountry").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMailingAddressCountry(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","telephoneResidence").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTelephoneResidence(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","telephoneOffice").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTelephoneOffice(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","telephoneMobile").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTelephoneMobile(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","USpassportholder").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUSpassportholder(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","USTaxLiable").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUSTaxLiable(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","countryOfBirth").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCountryOfBirth(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","standingInstructionCountry").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setStandingInstructionCountry(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","POAHolderCountry").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPOAHolderCountry(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","SSN").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSSN(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","returnValue").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setReturnValue(
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
           
    
        public static class InqFATCAValidationReq2_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = InqFATCAValidationReq2_type0
                Namespace URI = http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd
                Namespace Prefix = ns2
                */
            

                        /**
                        * field for CustomerSegment
                        */

                        
                                    protected java.lang.String localCustomerSegment ;
                                

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
                            
                                            this.localCustomerSegment=param;
                                    

                               }
                            

                        /**
                        * field for ServiceType
                        */

                        
                                    protected java.lang.String localServiceType ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getServiceType(){
                               return localServiceType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ServiceType
                               */
                               public void setServiceType(java.lang.String param){
                            
                                            this.localServiceType=param;
                                    

                               }
                            

                        /**
                        * field for Product
                        */

                        
                                    protected java.lang.String localProduct ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getProduct(){
                               return localProduct;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Product
                               */
                               public void setProduct(java.lang.String param){
                            
                                            this.localProduct=param;
                                    

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
                        * field for ResidentialAddressCountry
                        */

                        
                                    protected java.lang.String localResidentialAddressCountry ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResidentialAddressCountryTracker = false ;

                           public boolean isResidentialAddressCountrySpecified(){
                               return localResidentialAddressCountryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getResidentialAddressCountry(){
                               return localResidentialAddressCountry;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ResidentialAddressCountry
                               */
                               public void setResidentialAddressCountry(java.lang.String param){
                            localResidentialAddressCountryTracker = param != null;
                                   
                                            this.localResidentialAddressCountry=param;
                                    

                               }
                            

                        /**
                        * field for MailingAddressCountry
                        */

                        
                                    protected java.lang.String localMailingAddressCountry ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMailingAddressCountryTracker = false ;

                           public boolean isMailingAddressCountrySpecified(){
                               return localMailingAddressCountryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMailingAddressCountry(){
                               return localMailingAddressCountry;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MailingAddressCountry
                               */
                               public void setMailingAddressCountry(java.lang.String param){
                            localMailingAddressCountryTracker = param != null;
                                   
                                            this.localMailingAddressCountry=param;
                                    

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
                        * field for TelephoneOffice
                        */

                        
                                    protected java.lang.String localTelephoneOffice ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTelephoneOfficeTracker = false ;

                           public boolean isTelephoneOfficeSpecified(){
                               return localTelephoneOfficeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTelephoneOffice(){
                               return localTelephoneOffice;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TelephoneOffice
                               */
                               public void setTelephoneOffice(java.lang.String param){
                            localTelephoneOfficeTracker = param != null;
                                   
                                            this.localTelephoneOffice=param;
                                    

                               }
                            

                        /**
                        * field for TelephoneMobile
                        */

                        
                                    protected java.lang.String localTelephoneMobile ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTelephoneMobileTracker = false ;

                           public boolean isTelephoneMobileSpecified(){
                               return localTelephoneMobileTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTelephoneMobile(){
                               return localTelephoneMobile;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TelephoneMobile
                               */
                               public void setTelephoneMobile(java.lang.String param){
                            localTelephoneMobileTracker = param != null;
                                   
                                            this.localTelephoneMobile=param;
                                    

                               }
                            

                        /**
                        * field for USpassportholder
                        */

                        
                                    protected java.lang.String localUSpassportholder ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localUSpassportholderTracker = false ;

                           public boolean isUSpassportholderSpecified(){
                               return localUSpassportholderTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getUSpassportholder(){
                               return localUSpassportholder;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param USpassportholder
                               */
                               public void setUSpassportholder(java.lang.String param){
                            localUSpassportholderTracker = param != null;
                                   
                                            this.localUSpassportholder=param;
                                    

                               }
                            

                        /**
                        * field for USTaxLiable
                        */

                        
                                    protected java.lang.String localUSTaxLiable ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localUSTaxLiableTracker = false ;

                           public boolean isUSTaxLiableSpecified(){
                               return localUSTaxLiableTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getUSTaxLiable(){
                               return localUSTaxLiable;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param USTaxLiable
                               */
                               public void setUSTaxLiable(java.lang.String param){
                            localUSTaxLiableTracker = param != null;
                                   
                                            this.localUSTaxLiable=param;
                                    

                               }
                            

                        /**
                        * field for CountryOfBirth
                        */

                        
                                    protected java.lang.String localCountryOfBirth ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCountryOfBirthTracker = false ;

                           public boolean isCountryOfBirthSpecified(){
                               return localCountryOfBirthTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCountryOfBirth(){
                               return localCountryOfBirth;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CountryOfBirth
                               */
                               public void setCountryOfBirth(java.lang.String param){
                            localCountryOfBirthTracker = param != null;
                                   
                                            this.localCountryOfBirth=param;
                                    

                               }
                            

                        /**
                        * field for StandingInstructionCountry
                        */

                        
                                    protected java.lang.String localStandingInstructionCountry ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localStandingInstructionCountryTracker = false ;

                           public boolean isStandingInstructionCountrySpecified(){
                               return localStandingInstructionCountryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getStandingInstructionCountry(){
                               return localStandingInstructionCountry;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param StandingInstructionCountry
                               */
                               public void setStandingInstructionCountry(java.lang.String param){
                            localStandingInstructionCountryTracker = param != null;
                                   
                                            this.localStandingInstructionCountry=param;
                                    

                               }
                            

                        /**
                        * field for POAHolderCountry
                        */

                        
                                    protected java.lang.String localPOAHolderCountry ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPOAHolderCountryTracker = false ;

                           public boolean isPOAHolderCountrySpecified(){
                               return localPOAHolderCountryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPOAHolderCountry(){
                               return localPOAHolderCountry;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param POAHolderCountry
                               */
                               public void setPOAHolderCountry(java.lang.String param){
                            localPOAHolderCountryTracker = param != null;
                                   
                                            this.localPOAHolderCountry=param;
                                    

                               }
                            

                        /**
                        * field for USIndiaciaFound
                        */

                        
                                    protected java.lang.String localUSIndiaciaFound ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localUSIndiaciaFoundTracker = false ;

                           public boolean isUSIndiaciaFoundSpecified(){
                               return localUSIndiaciaFoundTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getUSIndiaciaFound(){
                               return localUSIndiaciaFound;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param USIndiaciaFound
                               */
                               public void setUSIndiaciaFound(java.lang.String param){
                            localUSIndiaciaFoundTracker = param != null;
                                   
                                            this.localUSIndiaciaFound=param;
                                    

                               }
                            

                        /**
                        * field for DocumentCollected
                        */

                        
                                    protected java.lang.String localDocumentCollected ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDocumentCollectedTracker = false ;

                           public boolean isDocumentCollectedSpecified(){
                               return localDocumentCollectedTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDocumentCollected(){
                               return localDocumentCollected;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DocumentCollected
                               */
                               public void setDocumentCollected(java.lang.String param){
                            localDocumentCollectedTracker = param != null;
                                   
                                            this.localDocumentCollected=param;
                                    

                               }
                            

                        /**
                        * field for TINorSSN
                        */

                        
                                    protected java.lang.String localTINorSSN ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTINorSSNTracker = false ;

                           public boolean isTINorSSNSpecified(){
                               return localTINorSSNTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTINorSSN(){
                               return localTINorSSN;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TINorSSN
                               */
                               public void setTINorSSN(java.lang.String param){
                            localTINorSSNTracker = param != null;
                                   
                                            this.localTINorSSN=param;
                                    

                               }
                            

                        /**
                        * field for CustomerFATCAClsfctn
                        */

                        
                                    protected java.lang.String localCustomerFATCAClsfctn ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerFATCAClsfctnTracker = false ;

                           public boolean isCustomerFATCAClsfctnSpecified(){
                               return localCustomerFATCAClsfctnTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerFATCAClsfctn(){
                               return localCustomerFATCAClsfctn;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerFATCAClsfctn
                               */
                               public void setCustomerFATCAClsfctn(java.lang.String param){
                            localCustomerFATCAClsfctnTracker = param != null;
                                   
                                            this.localCustomerFATCAClsfctn=param;
                                    

                               }
                            

                        /**
                        * field for CustomerFATCAClsfctnDate
                        */

                        
                                    protected java.lang.String localCustomerFATCAClsfctnDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerFATCAClsfctnDateTracker = false ;

                           public boolean isCustomerFATCAClsfctnDateSpecified(){
                               return localCustomerFATCAClsfctnDateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerFATCAClsfctnDate(){
                               return localCustomerFATCAClsfctnDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerFATCAClsfctnDate
                               */
                               public void setCustomerFATCAClsfctnDate(java.lang.String param){
                            localCustomerFATCAClsfctnDateTracker = param != null;
                                   
                                            this.localCustomerFATCAClsfctnDate=param;
                                    

                               }
                            

                        /**
                        * field for W8SignDate
                        */

                        
                                    protected java.lang.String localW8SignDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localW8SignDateTracker = false ;

                           public boolean isW8SignDateSpecified(){
                               return localW8SignDateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getW8SignDate(){
                               return localW8SignDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param W8SignDate
                               */
                               public void setW8SignDate(java.lang.String param){
                            localW8SignDateTracker = param != null;
                                   
                                            this.localW8SignDate=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":InqFATCAValidationReq2_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "InqFATCAValidationReq2_type0",
                           xmlWriter);
                   }

               
                   }
               
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "customerSegment", xmlWriter);
                             

                                          if (localCustomerSegment==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerSegment cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerSegment);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "serviceType", xmlWriter);
                             

                                          if (localServiceType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("serviceType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localServiceType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "product", xmlWriter);
                             

                                          if (localProduct==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("product cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localProduct);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localNationalityTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "nationality", xmlWriter);
                             

                                          if (localNationality==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("nationality cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localNationality);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localResidentialAddressCountryTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "residentialAddressCountry", xmlWriter);
                             

                                          if (localResidentialAddressCountry==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("residentialAddressCountry cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localResidentialAddressCountry);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMailingAddressCountryTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "mailingAddressCountry", xmlWriter);
                             

                                          if (localMailingAddressCountry==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("mailingAddressCountry cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMailingAddressCountry);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTelephoneResidenceTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "telephoneResidence", xmlWriter);
                             

                                          if (localTelephoneResidence==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("telephoneResidence cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTelephoneResidence);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTelephoneOfficeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "telephoneOffice", xmlWriter);
                             

                                          if (localTelephoneOffice==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("telephoneOffice cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTelephoneOffice);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTelephoneMobileTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "telephoneMobile", xmlWriter);
                             

                                          if (localTelephoneMobile==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("telephoneMobile cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTelephoneMobile);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localUSpassportholderTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "USpassportholder", xmlWriter);
                             

                                          if (localUSpassportholder==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("USpassportholder cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localUSpassportholder);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localUSTaxLiableTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "USTaxLiable", xmlWriter);
                             

                                          if (localUSTaxLiable==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("USTaxLiable cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localUSTaxLiable);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCountryOfBirthTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "countryOfBirth", xmlWriter);
                             

                                          if (localCountryOfBirth==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("countryOfBirth cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCountryOfBirth);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localStandingInstructionCountryTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "standingInstructionCountry", xmlWriter);
                             

                                          if (localStandingInstructionCountry==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("standingInstructionCountry cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localStandingInstructionCountry);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPOAHolderCountryTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "POAHolderCountry", xmlWriter);
                             

                                          if (localPOAHolderCountry==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("POAHolderCountry cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPOAHolderCountry);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localUSIndiaciaFoundTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "USIndiaciaFound", xmlWriter);
                             

                                          if (localUSIndiaciaFound==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("USIndiaciaFound cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localUSIndiaciaFound);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDocumentCollectedTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "documentCollected", xmlWriter);
                             

                                          if (localDocumentCollected==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("documentCollected cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDocumentCollected);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTINorSSNTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "TINorSSN", xmlWriter);
                             

                                          if (localTINorSSN==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("TINorSSN cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTINorSSN);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCustomerFATCAClsfctnTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "customerFATCAClsfctn", xmlWriter);
                             

                                          if (localCustomerFATCAClsfctn==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerFATCAClsfctn cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerFATCAClsfctn);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCustomerFATCAClsfctnDateTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "customerFATCAClsfctnDate", xmlWriter);
                             

                                          if (localCustomerFATCAClsfctnDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerFATCAClsfctnDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerFATCAClsfctnDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localW8SignDateTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "W8SignDate", xmlWriter);
                             

                                          if (localW8SignDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("W8SignDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localW8SignDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd")){
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
                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
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

                
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "customerSegment"));
                                 
                                        if (localCustomerSegment != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerSegment));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerSegment cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "serviceType"));
                                 
                                        if (localServiceType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localServiceType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("serviceType cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "product"));
                                 
                                        if (localProduct != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProduct));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("product cannot be null!!");
                                        }
                                     if (localNationalityTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "nationality"));
                                 
                                        if (localNationality != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNationality));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("nationality cannot be null!!");
                                        }
                                    } if (localResidentialAddressCountryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "residentialAddressCountry"));
                                 
                                        if (localResidentialAddressCountry != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResidentialAddressCountry));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("residentialAddressCountry cannot be null!!");
                                        }
                                    } if (localMailingAddressCountryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "mailingAddressCountry"));
                                 
                                        if (localMailingAddressCountry != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMailingAddressCountry));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("mailingAddressCountry cannot be null!!");
                                        }
                                    } if (localTelephoneResidenceTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "telephoneResidence"));
                                 
                                        if (localTelephoneResidence != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTelephoneResidence));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("telephoneResidence cannot be null!!");
                                        }
                                    } if (localTelephoneOfficeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "telephoneOffice"));
                                 
                                        if (localTelephoneOffice != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTelephoneOffice));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("telephoneOffice cannot be null!!");
                                        }
                                    } if (localTelephoneMobileTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "telephoneMobile"));
                                 
                                        if (localTelephoneMobile != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTelephoneMobile));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("telephoneMobile cannot be null!!");
                                        }
                                    } if (localUSpassportholderTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "USpassportholder"));
                                 
                                        if (localUSpassportholder != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUSpassportholder));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("USpassportholder cannot be null!!");
                                        }
                                    } if (localUSTaxLiableTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "USTaxLiable"));
                                 
                                        if (localUSTaxLiable != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUSTaxLiable));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("USTaxLiable cannot be null!!");
                                        }
                                    } if (localCountryOfBirthTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "countryOfBirth"));
                                 
                                        if (localCountryOfBirth != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCountryOfBirth));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("countryOfBirth cannot be null!!");
                                        }
                                    } if (localStandingInstructionCountryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "standingInstructionCountry"));
                                 
                                        if (localStandingInstructionCountry != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStandingInstructionCountry));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("standingInstructionCountry cannot be null!!");
                                        }
                                    } if (localPOAHolderCountryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "POAHolderCountry"));
                                 
                                        if (localPOAHolderCountry != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPOAHolderCountry));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("POAHolderCountry cannot be null!!");
                                        }
                                    } if (localUSIndiaciaFoundTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "USIndiaciaFound"));
                                 
                                        if (localUSIndiaciaFound != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUSIndiaciaFound));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("USIndiaciaFound cannot be null!!");
                                        }
                                    } if (localDocumentCollectedTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "documentCollected"));
                                 
                                        if (localDocumentCollected != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDocumentCollected));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("documentCollected cannot be null!!");
                                        }
                                    } if (localTINorSSNTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "TINorSSN"));
                                 
                                        if (localTINorSSN != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTINorSSN));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("TINorSSN cannot be null!!");
                                        }
                                    } if (localCustomerFATCAClsfctnTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "customerFATCAClsfctn"));
                                 
                                        if (localCustomerFATCAClsfctn != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerFATCAClsfctn));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerFATCAClsfctn cannot be null!!");
                                        }
                                    } if (localCustomerFATCAClsfctnDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "customerFATCAClsfctnDate"));
                                 
                                        if (localCustomerFATCAClsfctnDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerFATCAClsfctnDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerFATCAClsfctnDate cannot be null!!");
                                        }
                                    } if (localW8SignDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "W8SignDate"));
                                 
                                        if (localW8SignDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localW8SignDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("W8SignDate cannot be null!!");
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
        public static InqFATCAValidationReq2_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            InqFATCAValidationReq2_type0 object =
                new InqFATCAValidationReq2_type0();

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
                    
                            if (!"InqFATCAValidationReq2_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (InqFATCAValidationReq2_type0)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","customerSegment").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerSegment(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","serviceType").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setServiceType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","product").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setProduct(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","nationality").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNationality(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","residentialAddressCountry").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setResidentialAddressCountry(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","mailingAddressCountry").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMailingAddressCountry(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","telephoneResidence").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTelephoneResidence(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","telephoneOffice").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTelephoneOffice(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","telephoneMobile").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTelephoneMobile(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","USpassportholder").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUSpassportholder(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","USTaxLiable").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUSTaxLiable(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","countryOfBirth").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCountryOfBirth(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","standingInstructionCountry").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setStandingInstructionCountry(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","POAHolderCountry").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPOAHolderCountry(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","USIndiaciaFound").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUSIndiaciaFound(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","documentCollected").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDocumentCollected(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","TINorSSN").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTINorSSN(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","customerFATCAClsfctn").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerFATCAClsfctn(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","customerFATCAClsfctnDate").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerFATCAClsfctnDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","W8SignDate").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setW8SignDate(
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
                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
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
           
    
        public static class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd".equals(namespaceURI) &&
                  "InqFATCAValidationReq2_type0".equals(typeName)){
                   
                            return  InqFATCAValidationReq2_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd".equals(namespaceURI) &&
                  "InqFATCAValidationRes_type0".equals(typeName)){
                   
                            return  InqFATCAValidationRes_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd".equals(namespaceURI) &&
                  "InqFATCAValidationReq_type0".equals(typeName)){
                   
                            return  InqFATCAValidationReq_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd".equals(namespaceURI) &&
                  "InqFATCAValidationRes2_type0".equals(typeName)){
                   
                            return  InqFATCAValidationRes2_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.adcb.com/esb/common/header.xsd".equals(namespaceURI) &&
                  "headerType".equals(typeName)){
                   
                            return  HeaderType.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    
        public static class InqFATCAValidationResMsgChoice_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = InqFATCAValidationResMsgChoice_type0
                Namespace URI = http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd
                Namespace Prefix = ns2
                */
            
            /** Whenever a new property is set ensure all others are unset
             *  There can be only one choice and the last one wins
             */
            private void clearAllSettingTrackers() {
            
                   localInqFATCAValidationResTracker = false;
                
                   localInqFATCAValidationRes2Tracker = false;
                
            }
        

                        /**
                        * field for InqFATCAValidationRes
                        */

                        
                                    protected InqFATCAValidationRes_type0 localInqFATCAValidationRes ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localInqFATCAValidationResTracker = false ;

                           public boolean isInqFATCAValidationResSpecified(){
                               return localInqFATCAValidationResTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return InqFATCAValidationRes_type0
                           */
                           public  InqFATCAValidationRes_type0 getInqFATCAValidationRes(){
                               return localInqFATCAValidationRes;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param InqFATCAValidationRes
                               */
                               public void setInqFATCAValidationRes(InqFATCAValidationRes_type0 param){
                            
                                clearAllSettingTrackers();
                            localInqFATCAValidationResTracker = param != null;
                                   
                                            this.localInqFATCAValidationRes=param;
                                    

                               }
                            

                        /**
                        * field for InqFATCAValidationRes2
                        */

                        
                                    protected InqFATCAValidationRes2_type0 localInqFATCAValidationRes2 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localInqFATCAValidationRes2Tracker = false ;

                           public boolean isInqFATCAValidationRes2Specified(){
                               return localInqFATCAValidationRes2Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return InqFATCAValidationRes2_type0
                           */
                           public  InqFATCAValidationRes2_type0 getInqFATCAValidationRes2(){
                               return localInqFATCAValidationRes2;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param InqFATCAValidationRes2
                               */
                               public void setInqFATCAValidationRes2(InqFATCAValidationRes2_type0 param){
                            
                                clearAllSettingTrackers();
                            localInqFATCAValidationRes2Tracker = param != null;
                                   
                                            this.localInqFATCAValidationRes2=param;
                                    

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
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":InqFATCAValidationResMsgChoice_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "InqFATCAValidationResMsgChoice_type0",
                           xmlWriter);
                   }

               
                   }
                if (localInqFATCAValidationResTracker){
                                            if (localInqFATCAValidationRes==null){
                                                 throw new org.apache.axis2.databinding.ADBException("InqFATCAValidationRes cannot be null!!");
                                            }
                                           localInqFATCAValidationRes.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","InqFATCAValidationRes"),
                                               xmlWriter);
                                        } if (localInqFATCAValidationRes2Tracker){
                                            if (localInqFATCAValidationRes2==null){
                                                 throw new org.apache.axis2.databinding.ADBException("InqFATCAValidationRes2 cannot be null!!");
                                            }
                                           localInqFATCAValidationRes2.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","InqFATCAValidationRes2"),
                                               xmlWriter);
                                        }

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd")){
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
                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
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

                 if (localInqFATCAValidationResTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "InqFATCAValidationRes"));
                            
                            
                                    if (localInqFATCAValidationRes==null){
                                         throw new org.apache.axis2.databinding.ADBException("InqFATCAValidationRes cannot be null!!");
                                    }
                                    elementList.add(localInqFATCAValidationRes);
                                } if (localInqFATCAValidationRes2Tracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "InqFATCAValidationRes2"));
                            
                            
                                    if (localInqFATCAValidationRes2==null){
                                         throw new org.apache.axis2.databinding.ADBException("InqFATCAValidationRes2 cannot be null!!");
                                    }
                                    elementList.add(localInqFATCAValidationRes2);
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
        public static InqFATCAValidationResMsgChoice_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            InqFATCAValidationResMsgChoice_type0 object =
                new InqFATCAValidationResMsgChoice_type0();

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
                

                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","InqFATCAValidationRes").equals(reader.getName())){
                                
                                                object.setInqFATCAValidationRes(InqFATCAValidationRes_type0.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                        else
                                    
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","InqFATCAValidationRes2").equals(reader.getName())){
                                
                                                object.setInqFATCAValidationRes2(InqFATCAValidationRes2_type0.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    
        public static class InqFATCAValidationResMsg
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                "InqFATCAValidationResMsg",
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
                        * field for InqFATCAValidationResMsgChoice_type0
                        */

                        
                                    protected InqFATCAValidationResMsgChoice_type0 localInqFATCAValidationResMsgChoice_type0 ;
                                

                           /**
                           * Auto generated getter method
                           * @return InqFATCAValidationResMsgChoice_type0
                           */
                           public  InqFATCAValidationResMsgChoice_type0 getInqFATCAValidationResMsgChoice_type0(){
                               return localInqFATCAValidationResMsgChoice_type0;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param InqFATCAValidationResMsgChoice_type0
                               */
                               public void setInqFATCAValidationResMsgChoice_type0(InqFATCAValidationResMsgChoice_type0 param){
                            
                                            this.localInqFATCAValidationResMsgChoice_type0=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":InqFATCAValidationResMsg",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "InqFATCAValidationResMsg",
                           xmlWriter);
                   }

               
                   }
               
                                            if (localHeader==null){
                                                 throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
                                            }
                                           localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
                                               xmlWriter);
                                        
                                            if (localInqFATCAValidationResMsgChoice_type0==null){
                                                 throw new org.apache.axis2.databinding.ADBException("InqFATCAValidationResMsgChoice_type0 cannot be null!!");
                                            }
                                           localInqFATCAValidationResMsgChoice_type0.serialize(null,xmlWriter);
                                        
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd")){
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
                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
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
                                
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "InqFATCAValidationResMsgChoice_type0"));
                            
                            
                                    if (localInqFATCAValidationResMsgChoice_type0==null){
                                         throw new org.apache.axis2.databinding.ADBException("InqFATCAValidationResMsgChoice_type0 cannot be null!!");
                                    }
                                    elementList.add(localInqFATCAValidationResMsgChoice_type0);
                                

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
        public static InqFATCAValidationResMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            InqFATCAValidationResMsg object =
                new InqFATCAValidationResMsg();

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
                    
                            if (!"InqFATCAValidationResMsg".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (InqFATCAValidationResMsg)ExtensionMapper.getTypeObject(
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
                                
                                    if (reader.isStartElement() ){
                                
                                                object.setInqFATCAValidationResMsgChoice_type0(InqFATCAValidationResMsgChoice_type0.Factory.parse(reader));
                                            
                              }  // End of if for expected property start element
                                  
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
           
    
        public static class InqFATCAValidationReqMsg
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                "InqFATCAValidationReqMsg",
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
                        * field for InqFATCAValidationReqMsgChoice_type0
                        */

                        
                                    protected InqFATCAValidationReqMsgChoice_type0 localInqFATCAValidationReqMsgChoice_type0 ;
                                

                           /**
                           * Auto generated getter method
                           * @return InqFATCAValidationReqMsgChoice_type0
                           */
                           public  InqFATCAValidationReqMsgChoice_type0 getInqFATCAValidationReqMsgChoice_type0(){
                               return localInqFATCAValidationReqMsgChoice_type0;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param InqFATCAValidationReqMsgChoice_type0
                               */
                               public void setInqFATCAValidationReqMsgChoice_type0(InqFATCAValidationReqMsgChoice_type0 param){
                            
                                            this.localInqFATCAValidationReqMsgChoice_type0=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":InqFATCAValidationReqMsg",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "InqFATCAValidationReqMsg",
                           xmlWriter);
                   }

               
                   }
               
                                            if (localHeader==null){
                                                 throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
                                            }
                                           localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
                                               xmlWriter);
                                        
                                            if (localInqFATCAValidationReqMsgChoice_type0==null){
                                                 throw new org.apache.axis2.databinding.ADBException("InqFATCAValidationReqMsgChoice_type0 cannot be null!!");
                                            }
                                           localInqFATCAValidationReqMsgChoice_type0.serialize(null,xmlWriter);
                                        
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd")){
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
                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
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
                                
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "InqFATCAValidationReqMsgChoice_type0"));
                            
                            
                                    if (localInqFATCAValidationReqMsgChoice_type0==null){
                                         throw new org.apache.axis2.databinding.ADBException("InqFATCAValidationReqMsgChoice_type0 cannot be null!!");
                                    }
                                    elementList.add(localInqFATCAValidationReqMsgChoice_type0);
                                

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
        public static InqFATCAValidationReqMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            InqFATCAValidationReqMsg object =
                new InqFATCAValidationReqMsg();

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
                    
                            if (!"InqFATCAValidationReqMsg".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (InqFATCAValidationReqMsg)ExtensionMapper.getTypeObject(
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
                                
                                    if (reader.isStartElement() ){
                                
                                                object.setInqFATCAValidationReqMsgChoice_type0(InqFATCAValidationReqMsgChoice_type0.Factory.parse(reader));
                                            
                              }  // End of if for expected property start element
                                  
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
           
    
        public static class InqFATCAValidationReqMsgChoice_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = InqFATCAValidationReqMsgChoice_type0
                Namespace URI = http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd
                Namespace Prefix = ns2
                */
            
            /** Whenever a new property is set ensure all others are unset
             *  There can be only one choice and the last one wins
             */
            private void clearAllSettingTrackers() {
            
                   localInqFATCAValidationReqTracker = false;
                
                   localInqFATCAValidationReq2Tracker = false;
                
            }
        

                        /**
                        * field for InqFATCAValidationReq
                        */

                        
                                    protected InqFATCAValidationReq_type0 localInqFATCAValidationReq ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localInqFATCAValidationReqTracker = false ;

                           public boolean isInqFATCAValidationReqSpecified(){
                               return localInqFATCAValidationReqTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return InqFATCAValidationReq_type0
                           */
                           public  InqFATCAValidationReq_type0 getInqFATCAValidationReq(){
                               return localInqFATCAValidationReq;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param InqFATCAValidationReq
                               */
                               public void setInqFATCAValidationReq(InqFATCAValidationReq_type0 param){
                            
                                clearAllSettingTrackers();
                            localInqFATCAValidationReqTracker = param != null;
                                   
                                            this.localInqFATCAValidationReq=param;
                                    

                               }
                            

                        /**
                        * field for InqFATCAValidationReq2
                        */

                        
                                    protected InqFATCAValidationReq2_type0 localInqFATCAValidationReq2 ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localInqFATCAValidationReq2Tracker = false ;

                           public boolean isInqFATCAValidationReq2Specified(){
                               return localInqFATCAValidationReq2Tracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return InqFATCAValidationReq2_type0
                           */
                           public  InqFATCAValidationReq2_type0 getInqFATCAValidationReq2(){
                               return localInqFATCAValidationReq2;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param InqFATCAValidationReq2
                               */
                               public void setInqFATCAValidationReq2(InqFATCAValidationReq2_type0 param){
                            
                                clearAllSettingTrackers();
                            localInqFATCAValidationReq2Tracker = param != null;
                                   
                                            this.localInqFATCAValidationReq2=param;
                                    

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
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":InqFATCAValidationReqMsgChoice_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "InqFATCAValidationReqMsgChoice_type0",
                           xmlWriter);
                   }

               
                   }
                if (localInqFATCAValidationReqTracker){
                                            if (localInqFATCAValidationReq==null){
                                                 throw new org.apache.axis2.databinding.ADBException("InqFATCAValidationReq cannot be null!!");
                                            }
                                           localInqFATCAValidationReq.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","InqFATCAValidationReq"),
                                               xmlWriter);
                                        } if (localInqFATCAValidationReq2Tracker){
                                            if (localInqFATCAValidationReq2==null){
                                                 throw new org.apache.axis2.databinding.ADBException("InqFATCAValidationReq2 cannot be null!!");
                                            }
                                           localInqFATCAValidationReq2.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","InqFATCAValidationReq2"),
                                               xmlWriter);
                                        }

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd")){
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
                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
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

                 if (localInqFATCAValidationReqTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "InqFATCAValidationReq"));
                            
                            
                                    if (localInqFATCAValidationReq==null){
                                         throw new org.apache.axis2.databinding.ADBException("InqFATCAValidationReq cannot be null!!");
                                    }
                                    elementList.add(localInqFATCAValidationReq);
                                } if (localInqFATCAValidationReq2Tracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "InqFATCAValidationReq2"));
                            
                            
                                    if (localInqFATCAValidationReq2==null){
                                         throw new org.apache.axis2.databinding.ADBException("InqFATCAValidationReq2 cannot be null!!");
                                    }
                                    elementList.add(localInqFATCAValidationReq2);
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
        public static InqFATCAValidationReqMsgChoice_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            InqFATCAValidationReqMsgChoice_type0 object =
                new InqFATCAValidationReqMsgChoice_type0();

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
                

                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","InqFATCAValidationReq").equals(reader.getName())){
                                
                                                object.setInqFATCAValidationReq(InqFATCAValidationReq_type0.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                        else
                                    
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","InqFATCAValidationReq2").equals(reader.getName())){
                                
                                                object.setInqFATCAValidationReq2(InqFATCAValidationReq2_type0.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                



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
                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
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
           
    
        public static class InqFATCAValidationReq_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = InqFATCAValidationReq_type0
                Namespace URI = http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd
                Namespace Prefix = ns2
                */
            

                        /**
                        * field for CustomerSegment
                        */

                        
                                    protected java.lang.String localCustomerSegment ;
                                

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
                            
                                            this.localCustomerSegment=param;
                                    

                               }
                            

                        /**
                        * field for ServiceType
                        */

                        
                                    protected java.lang.String localServiceType ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getServiceType(){
                               return localServiceType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ServiceType
                               */
                               public void setServiceType(java.lang.String param){
                            
                                            this.localServiceType=param;
                                    

                               }
                            

                        /**
                        * field for Product
                        */

                        
                                    protected java.lang.String localProduct ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getProduct(){
                               return localProduct;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Product
                               */
                               public void setProduct(java.lang.String param){
                            
                                            this.localProduct=param;
                                    

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
                        * field for ResidentialAddressCountry
                        */

                        
                                    protected java.lang.String localResidentialAddressCountry ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResidentialAddressCountryTracker = false ;

                           public boolean isResidentialAddressCountrySpecified(){
                               return localResidentialAddressCountryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getResidentialAddressCountry(){
                               return localResidentialAddressCountry;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ResidentialAddressCountry
                               */
                               public void setResidentialAddressCountry(java.lang.String param){
                            localResidentialAddressCountryTracker = param != null;
                                   
                                            this.localResidentialAddressCountry=param;
                                    

                               }
                            

                        /**
                        * field for MailingAddressCountry
                        */

                        
                                    protected java.lang.String localMailingAddressCountry ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMailingAddressCountryTracker = false ;

                           public boolean isMailingAddressCountrySpecified(){
                               return localMailingAddressCountryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMailingAddressCountry(){
                               return localMailingAddressCountry;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MailingAddressCountry
                               */
                               public void setMailingAddressCountry(java.lang.String param){
                            localMailingAddressCountryTracker = param != null;
                                   
                                            this.localMailingAddressCountry=param;
                                    

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
                        * field for TelephoneOffice
                        */

                        
                                    protected java.lang.String localTelephoneOffice ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTelephoneOfficeTracker = false ;

                           public boolean isTelephoneOfficeSpecified(){
                               return localTelephoneOfficeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTelephoneOffice(){
                               return localTelephoneOffice;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TelephoneOffice
                               */
                               public void setTelephoneOffice(java.lang.String param){
                            localTelephoneOfficeTracker = param != null;
                                   
                                            this.localTelephoneOffice=param;
                                    

                               }
                            

                        /**
                        * field for TelephoneMobile
                        */

                        
                                    protected java.lang.String localTelephoneMobile ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTelephoneMobileTracker = false ;

                           public boolean isTelephoneMobileSpecified(){
                               return localTelephoneMobileTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTelephoneMobile(){
                               return localTelephoneMobile;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TelephoneMobile
                               */
                               public void setTelephoneMobile(java.lang.String param){
                            localTelephoneMobileTracker = param != null;
                                   
                                            this.localTelephoneMobile=param;
                                    

                               }
                            

                        /**
                        * field for USpassportholder
                        */

                        
                                    protected java.lang.String localUSpassportholder ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localUSpassportholderTracker = false ;

                           public boolean isUSpassportholderSpecified(){
                               return localUSpassportholderTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getUSpassportholder(){
                               return localUSpassportholder;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param USpassportholder
                               */
                               public void setUSpassportholder(java.lang.String param){
                            localUSpassportholderTracker = param != null;
                                   
                                            this.localUSpassportholder=param;
                                    

                               }
                            

                        /**
                        * field for USTaxLiable
                        */

                        
                                    protected java.lang.String localUSTaxLiable ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localUSTaxLiableTracker = false ;

                           public boolean isUSTaxLiableSpecified(){
                               return localUSTaxLiableTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getUSTaxLiable(){
                               return localUSTaxLiable;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param USTaxLiable
                               */
                               public void setUSTaxLiable(java.lang.String param){
                            localUSTaxLiableTracker = param != null;
                                   
                                            this.localUSTaxLiable=param;
                                    

                               }
                            

                        /**
                        * field for CountryOfBirth
                        */

                        
                                    protected java.lang.String localCountryOfBirth ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCountryOfBirthTracker = false ;

                           public boolean isCountryOfBirthSpecified(){
                               return localCountryOfBirthTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCountryOfBirth(){
                               return localCountryOfBirth;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CountryOfBirth
                               */
                               public void setCountryOfBirth(java.lang.String param){
                            localCountryOfBirthTracker = param != null;
                                   
                                            this.localCountryOfBirth=param;
                                    

                               }
                            

                        /**
                        * field for StandingInstructionCountry
                        */

                        
                                    protected java.lang.String localStandingInstructionCountry ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localStandingInstructionCountryTracker = false ;

                           public boolean isStandingInstructionCountrySpecified(){
                               return localStandingInstructionCountryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getStandingInstructionCountry(){
                               return localStandingInstructionCountry;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param StandingInstructionCountry
                               */
                               public void setStandingInstructionCountry(java.lang.String param){
                            localStandingInstructionCountryTracker = param != null;
                                   
                                            this.localStandingInstructionCountry=param;
                                    

                               }
                            

                        /**
                        * field for POAHolderCountry
                        */

                        
                                    protected java.lang.String localPOAHolderCountry ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPOAHolderCountryTracker = false ;

                           public boolean isPOAHolderCountrySpecified(){
                               return localPOAHolderCountryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPOAHolderCountry(){
                               return localPOAHolderCountry;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param POAHolderCountry
                               */
                               public void setPOAHolderCountry(java.lang.String param){
                            localPOAHolderCountryTracker = param != null;
                                   
                                            this.localPOAHolderCountry=param;
                                    

                               }
                            

                        /**
                        * field for SSN
                        */

                        
                                    protected java.lang.String localSSN ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSSNTracker = false ;

                           public boolean isSSNSpecified(){
                               return localSSNTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSSN(){
                               return localSSN;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SSN
                               */
                               public void setSSN(java.lang.String param){
                            localSSNTracker = param != null;
                                   
                                            this.localSSN=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":InqFATCAValidationReq_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "InqFATCAValidationReq_type0",
                           xmlWriter);
                   }

               
                   }
               
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "customerSegment", xmlWriter);
                             

                                          if (localCustomerSegment==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerSegment cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerSegment);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "serviceType", xmlWriter);
                             

                                          if (localServiceType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("serviceType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localServiceType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "product", xmlWriter);
                             

                                          if (localProduct==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("product cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localProduct);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localNationalityTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "nationality", xmlWriter);
                             

                                          if (localNationality==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("nationality cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localNationality);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localResidentialAddressCountryTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "residentialAddressCountry", xmlWriter);
                             

                                          if (localResidentialAddressCountry==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("residentialAddressCountry cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localResidentialAddressCountry);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMailingAddressCountryTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "mailingAddressCountry", xmlWriter);
                             

                                          if (localMailingAddressCountry==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("mailingAddressCountry cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMailingAddressCountry);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTelephoneResidenceTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "telephoneResidence", xmlWriter);
                             

                                          if (localTelephoneResidence==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("telephoneResidence cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTelephoneResidence);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTelephoneOfficeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "telephoneOffice", xmlWriter);
                             

                                          if (localTelephoneOffice==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("telephoneOffice cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTelephoneOffice);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTelephoneMobileTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "telephoneMobile", xmlWriter);
                             

                                          if (localTelephoneMobile==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("telephoneMobile cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTelephoneMobile);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localUSpassportholderTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "USpassportholder", xmlWriter);
                             

                                          if (localUSpassportholder==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("USpassportholder cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localUSpassportholder);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localUSTaxLiableTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "USTaxLiable", xmlWriter);
                             

                                          if (localUSTaxLiable==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("USTaxLiable cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localUSTaxLiable);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCountryOfBirthTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "countryOfBirth", xmlWriter);
                             

                                          if (localCountryOfBirth==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("countryOfBirth cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCountryOfBirth);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localStandingInstructionCountryTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "standingInstructionCountry", xmlWriter);
                             

                                          if (localStandingInstructionCountry==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("standingInstructionCountry cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localStandingInstructionCountry);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPOAHolderCountryTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "POAHolderCountry", xmlWriter);
                             

                                          if (localPOAHolderCountry==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("POAHolderCountry cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPOAHolderCountry);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSSNTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "SSN", xmlWriter);
                             

                                          if (localSSN==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("SSN cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSSN);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd")){
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
                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
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

                
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "customerSegment"));
                                 
                                        if (localCustomerSegment != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerSegment));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerSegment cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "serviceType"));
                                 
                                        if (localServiceType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localServiceType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("serviceType cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "product"));
                                 
                                        if (localProduct != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProduct));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("product cannot be null!!");
                                        }
                                     if (localNationalityTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "nationality"));
                                 
                                        if (localNationality != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNationality));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("nationality cannot be null!!");
                                        }
                                    } if (localResidentialAddressCountryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "residentialAddressCountry"));
                                 
                                        if (localResidentialAddressCountry != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResidentialAddressCountry));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("residentialAddressCountry cannot be null!!");
                                        }
                                    } if (localMailingAddressCountryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "mailingAddressCountry"));
                                 
                                        if (localMailingAddressCountry != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMailingAddressCountry));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("mailingAddressCountry cannot be null!!");
                                        }
                                    } if (localTelephoneResidenceTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "telephoneResidence"));
                                 
                                        if (localTelephoneResidence != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTelephoneResidence));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("telephoneResidence cannot be null!!");
                                        }
                                    } if (localTelephoneOfficeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "telephoneOffice"));
                                 
                                        if (localTelephoneOffice != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTelephoneOffice));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("telephoneOffice cannot be null!!");
                                        }
                                    } if (localTelephoneMobileTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "telephoneMobile"));
                                 
                                        if (localTelephoneMobile != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTelephoneMobile));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("telephoneMobile cannot be null!!");
                                        }
                                    } if (localUSpassportholderTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "USpassportholder"));
                                 
                                        if (localUSpassportholder != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUSpassportholder));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("USpassportholder cannot be null!!");
                                        }
                                    } if (localUSTaxLiableTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "USTaxLiable"));
                                 
                                        if (localUSTaxLiable != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUSTaxLiable));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("USTaxLiable cannot be null!!");
                                        }
                                    } if (localCountryOfBirthTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "countryOfBirth"));
                                 
                                        if (localCountryOfBirth != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCountryOfBirth));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("countryOfBirth cannot be null!!");
                                        }
                                    } if (localStandingInstructionCountryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "standingInstructionCountry"));
                                 
                                        if (localStandingInstructionCountry != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStandingInstructionCountry));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("standingInstructionCountry cannot be null!!");
                                        }
                                    } if (localPOAHolderCountryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "POAHolderCountry"));
                                 
                                        if (localPOAHolderCountry != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPOAHolderCountry));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("POAHolderCountry cannot be null!!");
                                        }
                                    } if (localSSNTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "SSN"));
                                 
                                        if (localSSN != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSSN));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("SSN cannot be null!!");
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
        public static InqFATCAValidationReq_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            InqFATCAValidationReq_type0 object =
                new InqFATCAValidationReq_type0();

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
                    
                            if (!"InqFATCAValidationReq_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (InqFATCAValidationReq_type0)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","customerSegment").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerSegment(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","serviceType").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setServiceType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","product").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setProduct(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","nationality").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNationality(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","residentialAddressCountry").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setResidentialAddressCountry(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","mailingAddressCountry").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMailingAddressCountry(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","telephoneResidence").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTelephoneResidence(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","telephoneOffice").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTelephoneOffice(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","telephoneMobile").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTelephoneMobile(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","USpassportholder").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUSpassportholder(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","USTaxLiable").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUSTaxLiable(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","countryOfBirth").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCountryOfBirth(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","standingInstructionCountry").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setStandingInstructionCountry(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","POAHolderCountry").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPOAHolderCountry(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","SSN").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSSN(
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
           
    
        public static class InqFATCAValidationRes2_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = InqFATCAValidationRes2_type0
                Namespace URI = http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd
                Namespace Prefix = ns2
                */
            

                        /**
                        * field for CustomerSegment
                        */

                        
                                    protected java.lang.String localCustomerSegment ;
                                

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
                            
                                            this.localCustomerSegment=param;
                                    

                               }
                            

                        /**
                        * field for ServiceType
                        */

                        
                                    protected java.lang.String localServiceType ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getServiceType(){
                               return localServiceType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ServiceType
                               */
                               public void setServiceType(java.lang.String param){
                            
                                            this.localServiceType=param;
                                    

                               }
                            

                        /**
                        * field for Product
                        */

                        
                                    protected java.lang.String localProduct ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getProduct(){
                               return localProduct;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Product
                               */
                               public void setProduct(java.lang.String param){
                            
                                            this.localProduct=param;
                                    

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
                        * field for ResidentialAddressCountry
                        */

                        
                                    protected java.lang.String localResidentialAddressCountry ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResidentialAddressCountryTracker = false ;

                           public boolean isResidentialAddressCountrySpecified(){
                               return localResidentialAddressCountryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getResidentialAddressCountry(){
                               return localResidentialAddressCountry;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ResidentialAddressCountry
                               */
                               public void setResidentialAddressCountry(java.lang.String param){
                            localResidentialAddressCountryTracker = param != null;
                                   
                                            this.localResidentialAddressCountry=param;
                                    

                               }
                            

                        /**
                        * field for MailingAddressCountry
                        */

                        
                                    protected java.lang.String localMailingAddressCountry ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMailingAddressCountryTracker = false ;

                           public boolean isMailingAddressCountrySpecified(){
                               return localMailingAddressCountryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMailingAddressCountry(){
                               return localMailingAddressCountry;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MailingAddressCountry
                               */
                               public void setMailingAddressCountry(java.lang.String param){
                            localMailingAddressCountryTracker = param != null;
                                   
                                            this.localMailingAddressCountry=param;
                                    

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
                        * field for TelephoneOffice
                        */

                        
                                    protected java.lang.String localTelephoneOffice ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTelephoneOfficeTracker = false ;

                           public boolean isTelephoneOfficeSpecified(){
                               return localTelephoneOfficeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTelephoneOffice(){
                               return localTelephoneOffice;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TelephoneOffice
                               */
                               public void setTelephoneOffice(java.lang.String param){
                            localTelephoneOfficeTracker = param != null;
                                   
                                            this.localTelephoneOffice=param;
                                    

                               }
                            

                        /**
                        * field for TelephoneMobile
                        */

                        
                                    protected java.lang.String localTelephoneMobile ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTelephoneMobileTracker = false ;

                           public boolean isTelephoneMobileSpecified(){
                               return localTelephoneMobileTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTelephoneMobile(){
                               return localTelephoneMobile;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TelephoneMobile
                               */
                               public void setTelephoneMobile(java.lang.String param){
                            localTelephoneMobileTracker = param != null;
                                   
                                            this.localTelephoneMobile=param;
                                    

                               }
                            

                        /**
                        * field for USpassportholder
                        */

                        
                                    protected java.lang.String localUSpassportholder ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localUSpassportholderTracker = false ;

                           public boolean isUSpassportholderSpecified(){
                               return localUSpassportholderTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getUSpassportholder(){
                               return localUSpassportholder;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param USpassportholder
                               */
                               public void setUSpassportholder(java.lang.String param){
                            localUSpassportholderTracker = param != null;
                                   
                                            this.localUSpassportholder=param;
                                    

                               }
                            

                        /**
                        * field for USTaxLiable
                        */

                        
                                    protected java.lang.String localUSTaxLiable ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localUSTaxLiableTracker = false ;

                           public boolean isUSTaxLiableSpecified(){
                               return localUSTaxLiableTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getUSTaxLiable(){
                               return localUSTaxLiable;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param USTaxLiable
                               */
                               public void setUSTaxLiable(java.lang.String param){
                            localUSTaxLiableTracker = param != null;
                                   
                                            this.localUSTaxLiable=param;
                                    

                               }
                            

                        /**
                        * field for CountryOfBirth
                        */

                        
                                    protected java.lang.String localCountryOfBirth ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCountryOfBirthTracker = false ;

                           public boolean isCountryOfBirthSpecified(){
                               return localCountryOfBirthTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCountryOfBirth(){
                               return localCountryOfBirth;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CountryOfBirth
                               */
                               public void setCountryOfBirth(java.lang.String param){
                            localCountryOfBirthTracker = param != null;
                                   
                                            this.localCountryOfBirth=param;
                                    

                               }
                            

                        /**
                        * field for StandingInstructionCountry
                        */

                        
                                    protected java.lang.String localStandingInstructionCountry ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localStandingInstructionCountryTracker = false ;

                           public boolean isStandingInstructionCountrySpecified(){
                               return localStandingInstructionCountryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getStandingInstructionCountry(){
                               return localStandingInstructionCountry;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param StandingInstructionCountry
                               */
                               public void setStandingInstructionCountry(java.lang.String param){
                            localStandingInstructionCountryTracker = param != null;
                                   
                                            this.localStandingInstructionCountry=param;
                                    

                               }
                            

                        /**
                        * field for POAHolderCountry
                        */

                        
                                    protected java.lang.String localPOAHolderCountry ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPOAHolderCountryTracker = false ;

                           public boolean isPOAHolderCountrySpecified(){
                               return localPOAHolderCountryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPOAHolderCountry(){
                               return localPOAHolderCountry;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param POAHolderCountry
                               */
                               public void setPOAHolderCountry(java.lang.String param){
                            localPOAHolderCountryTracker = param != null;
                                   
                                            this.localPOAHolderCountry=param;
                                    

                               }
                            

                        /**
                        * field for USIndiaciaFound
                        */

                        
                                    protected java.lang.String localUSIndiaciaFound ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localUSIndiaciaFoundTracker = false ;

                           public boolean isUSIndiaciaFoundSpecified(){
                               return localUSIndiaciaFoundTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getUSIndiaciaFound(){
                               return localUSIndiaciaFound;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param USIndiaciaFound
                               */
                               public void setUSIndiaciaFound(java.lang.String param){
                            localUSIndiaciaFoundTracker = param != null;
                                   
                                            this.localUSIndiaciaFound=param;
                                    

                               }
                            

                        /**
                        * field for DocumentCollected
                        */

                        
                                    protected java.lang.String localDocumentCollected ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDocumentCollectedTracker = false ;

                           public boolean isDocumentCollectedSpecified(){
                               return localDocumentCollectedTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDocumentCollected(){
                               return localDocumentCollected;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DocumentCollected
                               */
                               public void setDocumentCollected(java.lang.String param){
                            localDocumentCollectedTracker = param != null;
                                   
                                            this.localDocumentCollected=param;
                                    

                               }
                            

                        /**
                        * field for TINorSSN
                        */

                        
                                    protected java.lang.String localTINorSSN ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTINorSSNTracker = false ;

                           public boolean isTINorSSNSpecified(){
                               return localTINorSSNTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTINorSSN(){
                               return localTINorSSN;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TINorSSN
                               */
                               public void setTINorSSN(java.lang.String param){
                            localTINorSSNTracker = param != null;
                                   
                                            this.localTINorSSN=param;
                                    

                               }
                            

                        /**
                        * field for CustomerFATCAClsfctn
                        */

                        
                                    protected java.lang.String localCustomerFATCAClsfctn ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerFATCAClsfctnTracker = false ;

                           public boolean isCustomerFATCAClsfctnSpecified(){
                               return localCustomerFATCAClsfctnTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerFATCAClsfctn(){
                               return localCustomerFATCAClsfctn;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerFATCAClsfctn
                               */
                               public void setCustomerFATCAClsfctn(java.lang.String param){
                            localCustomerFATCAClsfctnTracker = param != null;
                                   
                                            this.localCustomerFATCAClsfctn=param;
                                    

                               }
                            

                        /**
                        * field for CustomerFATCAClsfctnDate
                        */

                        
                                    protected java.lang.String localCustomerFATCAClsfctnDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCustomerFATCAClsfctnDateTracker = false ;

                           public boolean isCustomerFATCAClsfctnDateSpecified(){
                               return localCustomerFATCAClsfctnDateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCustomerFATCAClsfctnDate(){
                               return localCustomerFATCAClsfctnDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CustomerFATCAClsfctnDate
                               */
                               public void setCustomerFATCAClsfctnDate(java.lang.String param){
                            localCustomerFATCAClsfctnDateTracker = param != null;
                                   
                                            this.localCustomerFATCAClsfctnDate=param;
                                    

                               }
                            

                        /**
                        * field for W8SignDate
                        */

                        
                                    protected java.lang.String localW8SignDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localW8SignDateTracker = false ;

                           public boolean isW8SignDateSpecified(){
                               return localW8SignDateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getW8SignDate(){
                               return localW8SignDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param W8SignDate
                               */
                               public void setW8SignDate(java.lang.String param){
                            localW8SignDateTracker = param != null;
                                   
                                            this.localW8SignDate=param;
                                    

                               }
                            

                        /**
                        * field for ReturnValue
                        */

                        
                                    protected java.lang.String localReturnValue ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localReturnValueTracker = false ;

                           public boolean isReturnValueSpecified(){
                               return localReturnValueTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getReturnValue(){
                               return localReturnValue;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ReturnValue
                               */
                               public void setReturnValue(java.lang.String param){
                            localReturnValueTracker = param != null;
                                   
                                            this.localReturnValue=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":InqFATCAValidationRes2_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "InqFATCAValidationRes2_type0",
                           xmlWriter);
                   }

               
                   }
               
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "customerSegment", xmlWriter);
                             

                                          if (localCustomerSegment==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerSegment cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerSegment);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "serviceType", xmlWriter);
                             

                                          if (localServiceType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("serviceType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localServiceType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "product", xmlWriter);
                             

                                          if (localProduct==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("product cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localProduct);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localNationalityTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "nationality", xmlWriter);
                             

                                          if (localNationality==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("nationality cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localNationality);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localResidentialAddressCountryTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "residentialAddressCountry", xmlWriter);
                             

                                          if (localResidentialAddressCountry==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("residentialAddressCountry cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localResidentialAddressCountry);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMailingAddressCountryTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "mailingAddressCountry", xmlWriter);
                             

                                          if (localMailingAddressCountry==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("mailingAddressCountry cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMailingAddressCountry);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTelephoneResidenceTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "telephoneResidence", xmlWriter);
                             

                                          if (localTelephoneResidence==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("telephoneResidence cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTelephoneResidence);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTelephoneOfficeTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "telephoneOffice", xmlWriter);
                             

                                          if (localTelephoneOffice==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("telephoneOffice cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTelephoneOffice);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTelephoneMobileTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "telephoneMobile", xmlWriter);
                             

                                          if (localTelephoneMobile==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("telephoneMobile cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTelephoneMobile);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localUSpassportholderTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "USpassportholder", xmlWriter);
                             

                                          if (localUSpassportholder==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("USpassportholder cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localUSpassportholder);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localUSTaxLiableTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "USTaxLiable", xmlWriter);
                             

                                          if (localUSTaxLiable==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("USTaxLiable cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localUSTaxLiable);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCountryOfBirthTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "countryOfBirth", xmlWriter);
                             

                                          if (localCountryOfBirth==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("countryOfBirth cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCountryOfBirth);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localStandingInstructionCountryTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "standingInstructionCountry", xmlWriter);
                             

                                          if (localStandingInstructionCountry==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("standingInstructionCountry cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localStandingInstructionCountry);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPOAHolderCountryTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "POAHolderCountry", xmlWriter);
                             

                                          if (localPOAHolderCountry==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("POAHolderCountry cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPOAHolderCountry);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localUSIndiaciaFoundTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "USIndiaciaFound", xmlWriter);
                             

                                          if (localUSIndiaciaFound==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("USIndiaciaFound cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localUSIndiaciaFound);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDocumentCollectedTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "documentCollected", xmlWriter);
                             

                                          if (localDocumentCollected==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("documentCollected cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDocumentCollected);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTINorSSNTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "TINorSSN", xmlWriter);
                             

                                          if (localTINorSSN==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("TINorSSN cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTINorSSN);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCustomerFATCAClsfctnTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "customerFATCAClsfctn", xmlWriter);
                             

                                          if (localCustomerFATCAClsfctn==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerFATCAClsfctn cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerFATCAClsfctn);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCustomerFATCAClsfctnDateTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "customerFATCAClsfctnDate", xmlWriter);
                             

                                          if (localCustomerFATCAClsfctnDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("customerFATCAClsfctnDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustomerFATCAClsfctnDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localW8SignDateTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "W8SignDate", xmlWriter);
                             

                                          if (localW8SignDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("W8SignDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localW8SignDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localReturnValueTracker){
                                    namespace = "http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd";
                                    writeStartElement(null, namespace, "returnValue", xmlWriter);
                             

                                          if (localReturnValue==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("returnValue cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localReturnValue);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd")){
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
                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
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

                
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "customerSegment"));
                                 
                                        if (localCustomerSegment != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerSegment));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerSegment cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "serviceType"));
                                 
                                        if (localServiceType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localServiceType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("serviceType cannot be null!!");
                                        }
                                    
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "product"));
                                 
                                        if (localProduct != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProduct));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("product cannot be null!!");
                                        }
                                     if (localNationalityTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "nationality"));
                                 
                                        if (localNationality != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNationality));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("nationality cannot be null!!");
                                        }
                                    } if (localResidentialAddressCountryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "residentialAddressCountry"));
                                 
                                        if (localResidentialAddressCountry != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResidentialAddressCountry));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("residentialAddressCountry cannot be null!!");
                                        }
                                    } if (localMailingAddressCountryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "mailingAddressCountry"));
                                 
                                        if (localMailingAddressCountry != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMailingAddressCountry));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("mailingAddressCountry cannot be null!!");
                                        }
                                    } if (localTelephoneResidenceTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "telephoneResidence"));
                                 
                                        if (localTelephoneResidence != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTelephoneResidence));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("telephoneResidence cannot be null!!");
                                        }
                                    } if (localTelephoneOfficeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "telephoneOffice"));
                                 
                                        if (localTelephoneOffice != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTelephoneOffice));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("telephoneOffice cannot be null!!");
                                        }
                                    } if (localTelephoneMobileTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "telephoneMobile"));
                                 
                                        if (localTelephoneMobile != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTelephoneMobile));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("telephoneMobile cannot be null!!");
                                        }
                                    } if (localUSpassportholderTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "USpassportholder"));
                                 
                                        if (localUSpassportholder != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUSpassportholder));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("USpassportholder cannot be null!!");
                                        }
                                    } if (localUSTaxLiableTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "USTaxLiable"));
                                 
                                        if (localUSTaxLiable != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUSTaxLiable));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("USTaxLiable cannot be null!!");
                                        }
                                    } if (localCountryOfBirthTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "countryOfBirth"));
                                 
                                        if (localCountryOfBirth != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCountryOfBirth));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("countryOfBirth cannot be null!!");
                                        }
                                    } if (localStandingInstructionCountryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "standingInstructionCountry"));
                                 
                                        if (localStandingInstructionCountry != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStandingInstructionCountry));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("standingInstructionCountry cannot be null!!");
                                        }
                                    } if (localPOAHolderCountryTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "POAHolderCountry"));
                                 
                                        if (localPOAHolderCountry != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPOAHolderCountry));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("POAHolderCountry cannot be null!!");
                                        }
                                    } if (localUSIndiaciaFoundTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "USIndiaciaFound"));
                                 
                                        if (localUSIndiaciaFound != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUSIndiaciaFound));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("USIndiaciaFound cannot be null!!");
                                        }
                                    } if (localDocumentCollectedTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "documentCollected"));
                                 
                                        if (localDocumentCollected != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDocumentCollected));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("documentCollected cannot be null!!");
                                        }
                                    } if (localTINorSSNTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "TINorSSN"));
                                 
                                        if (localTINorSSN != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTINorSSN));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("TINorSSN cannot be null!!");
                                        }
                                    } if (localCustomerFATCAClsfctnTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "customerFATCAClsfctn"));
                                 
                                        if (localCustomerFATCAClsfctn != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerFATCAClsfctn));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerFATCAClsfctn cannot be null!!");
                                        }
                                    } if (localCustomerFATCAClsfctnDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "customerFATCAClsfctnDate"));
                                 
                                        if (localCustomerFATCAClsfctnDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustomerFATCAClsfctnDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("customerFATCAClsfctnDate cannot be null!!");
                                        }
                                    } if (localW8SignDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "W8SignDate"));
                                 
                                        if (localW8SignDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localW8SignDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("W8SignDate cannot be null!!");
                                        }
                                    } if (localReturnValueTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd",
                                                                      "returnValue"));
                                 
                                        if (localReturnValue != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReturnValue));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("returnValue cannot be null!!");
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
        public static InqFATCAValidationRes2_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            InqFATCAValidationRes2_type0 object =
                new InqFATCAValidationRes2_type0();

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
                    
                            if (!"InqFATCAValidationRes2_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (InqFATCAValidationRes2_type0)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","customerSegment").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerSegment(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","serviceType").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setServiceType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","product").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setProduct(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","nationality").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNationality(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","residentialAddressCountry").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setResidentialAddressCountry(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","mailingAddressCountry").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMailingAddressCountry(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","telephoneResidence").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTelephoneResidence(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","telephoneOffice").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTelephoneOffice(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","telephoneMobile").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTelephoneMobile(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","USpassportholder").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUSpassportholder(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","USTaxLiable").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUSTaxLiable(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","countryOfBirth").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCountryOfBirth(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","standingInstructionCountry").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setStandingInstructionCountry(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","POAHolderCountry").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPOAHolderCountry(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","USIndiaciaFound").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUSIndiaciaFound(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","documentCollected").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDocumentCollected(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","TINorSSN").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTINorSSN(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","customerFATCAClsfctn").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerFATCAClsfctn(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","customerFATCAClsfctnDate").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCustomerFATCAClsfctnDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","W8SignDate").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setW8SignDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/ENTInquirySrvs/InqFATCAValidation.xsd","returnValue").equals(reader.getName())){
                                
                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setReturnValue(
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
           
    
            private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqFATCAValidationStub.InqFATCAValidationReqMsg param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.newgen.dscop.stub.InqFATCAValidationStub.InqFATCAValidationReqMsg.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqFATCAValidationStub.InqFATCAValidationResMsg param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.newgen.dscop.stub.InqFATCAValidationStub.InqFATCAValidationResMsg.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.newgen.dscop.stub.InqFATCAValidationStub.InqFATCAValidationReqMsg param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.newgen.dscop.stub.InqFATCAValidationStub.InqFATCAValidationReqMsg.MY_QNAME,factory));
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
        
                if (com.newgen.dscop.stub.InqFATCAValidationStub.InqFATCAValidationReqMsg.class.equals(type)){
                
                           return com.newgen.dscop.stub.InqFATCAValidationStub.InqFATCAValidationReqMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.newgen.dscop.stub.InqFATCAValidationStub.InqFATCAValidationResMsg.class.equals(type)){
                
                           return com.newgen.dscop.stub.InqFATCAValidationStub.InqFATCAValidationResMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    
   }
   