
/**
 * InqLoanBasicInfoStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
        package com.newgen.dscop.stub;

        

        /*
        *  InqLoanBasicInfoStub java implementation
        */

        
        public class InqLoanBasicInfoStub extends org.apache.axis2.client.Stub
        {
        protected org.apache.axis2.description.AxisOperation[] _operations;

        //hashmaps to keep the fault mapping
        private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
        private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
        private java.util.HashMap faultMessageMap = new java.util.HashMap();
        public String responseLoanBasic=null;

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
     _service = new org.apache.axis2.description.AxisService("InqLoanBasicInfo" + getUniqueSuffix());
     addAnonymousOperations();

        //creating the operations
        org.apache.axis2.description.AxisOperation __operation;

        _operations = new org.apache.axis2.description.AxisOperation[1];
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://xmlns.example.com/1238430575859", "inqLoanBasicInfo_Oper"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[0]=__operation;
            
        
        }

    //populates the faults
    private void populateFaults(){
         


    }

    /**
      *Constructor that takes in a configContext
      */

    public InqLoanBasicInfoStub(org.apache.axis2.context.ConfigurationContext configurationContext,
       java.lang.String targetEndpoint)
       throws org.apache.axis2.AxisFault {
         this(configurationContext,targetEndpoint,false);
   }


   /**
     * Constructor that takes in a configContext  and useseperate listner
     */
   public InqLoanBasicInfoStub(org.apache.axis2.context.ConfigurationContext configurationContext,
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
    public InqLoanBasicInfoStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {
        
                    this(configurationContext,"http://XXXXXXXXXX:5503/Services/EnterpriseServicesInquiry/Loan/Service/InqLoanBasicInfo.serviceagent/InqLoanBasicInfoPortTypeEndpoint1" );
                
    }

    /**
     * Default Constructor
     */
    public InqLoanBasicInfoStub() throws org.apache.axis2.AxisFault {
        
                    this("http://XXXXXXXXXX:5503/Services/EnterpriseServicesInquiry/Loan/Service/InqLoanBasicInfo.serviceagent/InqLoanBasicInfoPortTypeEndpoint1" );
                
    }

    /**
     * Constructor taking the target endpoint
     */
    public InqLoanBasicInfoStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
        this(null,targetEndpoint);
    }



        
                    /**
                     * Auto generated method signature
                     * 
                     * @see com.newgen.dscop.stub.InqLoanBasicInfo#inqLoanBasicInfo_Oper
                     * @param getLoanCommonDetailsReqMsg0
                    
                     */

    public String getinputXML(

            com.newgen.dscop.stub.InqLoanBasicInfoStub.GetLoanCommonDetailsReqMsg getLoanCommonDetailsReqMsg0)
        

    throws java.rmi.RemoteException
    
    {
org.apache.axis2.context.MessageContext _messageContext = null;
try{
org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
_operationClient.getOptions().setAction("/Services/EnterpriseServicesInquiry/Loan/Service/InqLoanBasicInfo.serviceagent/InqLoanBasicInfoPortTypeEndpoint1/InqLoanBasicInfo_Oper");
_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);



  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");


// create a message context
_messageContext = new org.apache.axis2.context.MessageContext();



// create SOAP envelope with that payload
org.apache.axiom.soap.SOAPEnvelope env = null;
    
                                    
                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                    getLoanCommonDetailsReqMsg0,
                                    optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1238430575859",
                                    "inqLoanBasicInfo_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1238430575859",
                                    "inqLoanBasicInfo_Oper"));
                                
//adding SOAP soap_headers
_serviceClient.addHeadersToEnvelope(env);
// set the message context with that soap envelope
//adding SOAP soap_headers
_serviceClient.addHeadersToEnvelope(env);
// set the message context with that soap envelope
_messageContext.setEnvelope(env);

return env.toString();	
                
}catch(org.apache.axis2.AxisFault f)
{
return "";
} 
finally 
{

}
}


                            public  com.newgen.dscop.stub.InqLoanBasicInfoStub.GetLoanCommonDetailsResMsg inqLoanBasicInfo_Oper(

                            com.newgen.dscop.stub.InqLoanBasicInfoStub.GetLoanCommonDetailsReqMsg getLoanCommonDetailsReqMsg0)
                        

                    throws java.rmi.RemoteException
                    
                    {
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
              _operationClient.getOptions().setAction("/Services/EnterpriseServicesInquiry/Loan/Service/InqLoanBasicInfo.serviceagent/InqLoanBasicInfoPortTypeEndpoint1/InqLoanBasicInfo_Oper");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getLoanCommonDetailsReqMsg0,
                                                    optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1238430575859",
                                                    "inqLoanBasicInfo_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1238430575859",
                                                    "inqLoanBasicInfo_Oper"));
                                                
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
                
                responseLoanBasic = _returnEnv.toString();

                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.newgen.dscop.stub.InqLoanBasicInfoStub.GetLoanCommonDetailsResMsg.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.newgen.dscop.stub.InqLoanBasicInfoStub.GetLoanCommonDetailsResMsg)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqLoanBasicInfo_Oper"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqLoanBasicInfo_Oper"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqLoanBasicInfo_Oper"));
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
                            public  String getOutput(

                                    com.newgen.dscop.stub.InqLoanBasicInfoStub.GetLoanCommonDetailsReqMsg getLoanCommonDetailsReqMsg0)
                                

                            throws java.rmi.RemoteException
                            
                            {
                      org.apache.axis2.context.MessageContext _messageContext = null;
                      try{
                       org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
                      _operationClient.getOptions().setAction("/Services/EnterpriseServicesInquiry/Loan/Service/InqLoanBasicInfo.serviceagent/InqLoanBasicInfoPortTypeEndpoint1/InqLoanBasicInfo_Oper");
                      _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

                      
                      
                          addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
                      

                      // create a message context
                      _messageContext = new org.apache.axis2.context.MessageContext();

                      

                      // create SOAP envelope with that payload
                      org.apache.axiom.soap.SOAPEnvelope env = null;
                            
                                                            
                                                            env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                            getLoanCommonDetailsReqMsg0,
                                                            optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1238430575859",
                                                            "inqLoanBasicInfo_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1238430575859",
                                                            "inqLoanBasicInfo_Oper"));
                                                        
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
                        
                        responseLoanBasic = _returnEnv.toString();

                                   return responseLoanBasic;
                 }catch(org.apache.axis2.AxisFault f){

                    org.apache.axiom.om.OMElement faultElt = f.getDetail();
                    if (faultElt!=null){
                        if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqLoanBasicInfo_Oper"))){
                            //make the fault by reflection
                            try{
                                java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqLoanBasicInfo_Oper"));
                                java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                                java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                                //message class
                                java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqLoanBasicInfo_Oper"));
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
                * @see com.newgen.dscop.stub.InqLoanBasicInfo#startinqLoanBasicInfo_Oper
                    * @param getLoanCommonDetailsReqMsg0
                
                */
                public  void startinqLoanBasicInfo_Oper(

                 com.newgen.dscop.stub.InqLoanBasicInfoStub.GetLoanCommonDetailsReqMsg getLoanCommonDetailsReqMsg0,

                  final com.newgen.dscop.stub.InqLoanBasicInfoCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
             _operationClient.getOptions().setAction("/Services/EnterpriseServicesInquiry/Loan/Service/InqLoanBasicInfo.serviceagent/InqLoanBasicInfoPortTypeEndpoint1/InqLoanBasicInfo_Oper");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getLoanCommonDetailsReqMsg0,
                                                    optimizeContent(new javax.xml.namespace.QName("http://xmlns.example.com/1238430575859",
                                                    "inqLoanBasicInfo_Oper")), new javax.xml.namespace.QName("http://xmlns.example.com/1238430575859",
                                                    "inqLoanBasicInfo_Oper"));
                                                
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
                                                                         com.newgen.dscop.stub.InqLoanBasicInfoStub.GetLoanCommonDetailsResMsg.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultinqLoanBasicInfo_Oper(
                                        (com.newgen.dscop.stub.InqLoanBasicInfoStub.GetLoanCommonDetailsResMsg)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErrorinqLoanBasicInfo_Oper(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqLoanBasicInfo_Oper"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqLoanBasicInfo_Oper"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"InqLoanBasicInfo_Oper"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
					
										            callback.receiveErrorinqLoanBasicInfo_Oper(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinqLoanBasicInfo_Oper(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinqLoanBasicInfo_Oper(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinqLoanBasicInfo_Oper(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinqLoanBasicInfo_Oper(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinqLoanBasicInfo_Oper(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinqLoanBasicInfo_Oper(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErrorinqLoanBasicInfo_Oper(f);
                                            }
									    } else {
										    callback.receiveErrorinqLoanBasicInfo_Oper(f);
									    }
									} else {
									    callback.receiveErrorinqLoanBasicInfo_Oper(f);
									}
								} else {
								    callback.receiveErrorinqLoanBasicInfo_Oper(error);
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
                                    callback.receiveErrorinqLoanBasicInfo_Oper(axisFault);
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
     //http://XXXXXXXXXX:5503/Services/EnterpriseServicesInquiry/Loan/Service/InqLoanBasicInfo.serviceagent/InqLoanBasicInfoPortTypeEndpoint1
        public static class CID
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd",
                "CID",
                "ns8");

            

                        /**
                        * field for CID
                        */

                        
                                    protected java.lang.String localCID ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCID(){
                               return localCID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CID
                               */
                               public void setCID(java.lang.String param){
                            
                                            this.localCID=param;
                                    

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
                
                            java.lang.String namespace = "http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd";
                            java.lang.String _localName = "CID";
                        
                            writeStartElement(null, namespace, _localName, xmlWriter);

                            // add the type details if this is used in a simple type
                               if (serializeType){
                                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd");
                                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                                           namespacePrefix+":CID",
                                           xmlWriter);
                                   } else {
                                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                                           "CID",
                                           xmlWriter);
                                   }
                               }
                            
                                          if (localCID==null){
                                            
                                                     throw new org.apache.axis2.databinding.ADBException("CID cannot be null !!");
                                                
                                         }else{
                                        
                                                       xmlWriter.writeCharacters(localCID);
                                            
                                         }
                                    
                            xmlWriter.writeEndElement();
                    

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd")){
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


        
                
                //We can safely assume an element has only one type associated with it
                 return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(MY_QNAME,
                            new java.lang.Object[]{
                            org.apache.axis2.databinding.utils.reader.ADBXMLStreamReader.ELEMENT_TEXT,
                            org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCID)
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
        public static CID parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            CID object =
                new CID();

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
                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd","CID").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"CID" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCID(
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
           
    
        public static class GetLoanCommonDetailsReq
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd",
                "GetLoanCommonDetailsReq",
                "ns8");

            

                        /**
                        * field for GetLoanCommonDetailsReq
                        */

                        
                                    protected GetLoanCommonDetailsReq_type0 localGetLoanCommonDetailsReq ;
                                

                           /**
                           * Auto generated getter method
                           * @return GetLoanCommonDetailsReq_type0
                           */
                           public  GetLoanCommonDetailsReq_type0 getGetLoanCommonDetailsReq(){
                               return localGetLoanCommonDetailsReq;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param GetLoanCommonDetailsReq
                               */
                               public void setGetLoanCommonDetailsReq(GetLoanCommonDetailsReq_type0 param){
                            
                                            this.localGetLoanCommonDetailsReq=param;
                                    

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
                
                                 if (localGetLoanCommonDetailsReq==null){
                                   throw new org.apache.axis2.databinding.ADBException("GetLoanCommonDetailsReq cannot be null!");
                                 }
                                 localGetLoanCommonDetailsReq.serialize(MY_QNAME,xmlWriter);
                            

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd")){
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


        
                
                //We can safely assume an element has only one type associated with it
                return localGetLoanCommonDetailsReq.getPullParser(MY_QNAME);

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
        public static GetLoanCommonDetailsReq parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            GetLoanCommonDetailsReq object =
                new GetLoanCommonDetailsReq();

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
                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd","GetLoanCommonDetailsReq").equals(reader.getName())){
                                
                                                object.setGetLoanCommonDetailsReq(GetLoanCommonDetailsReq_type0.Factory.parse(reader));
                                            
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
           
    
        public static class GetLoanCommonDetailsRes
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd",
                "GetLoanCommonDetailsRes",
                "ns8");

            

                        /**
                        * field for GetLoanCommonDetailsRes
                        */

                        
                                    protected GetLoanCommonDetailsRes_type0 localGetLoanCommonDetailsRes ;
                                

                           /**
                           * Auto generated getter method
                           * @return GetLoanCommonDetailsRes_type0
                           */
                           public  GetLoanCommonDetailsRes_type0 getGetLoanCommonDetailsRes(){
                               return localGetLoanCommonDetailsRes;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param GetLoanCommonDetailsRes
                               */
                               public void setGetLoanCommonDetailsRes(GetLoanCommonDetailsRes_type0 param){
                            
                                            this.localGetLoanCommonDetailsRes=param;
                                    

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
                
                                 if (localGetLoanCommonDetailsRes==null){
                                   throw new org.apache.axis2.databinding.ADBException("GetLoanCommonDetailsRes cannot be null!");
                                 }
                                 localGetLoanCommonDetailsRes.serialize(MY_QNAME,xmlWriter);
                            

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd")){
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


        
                
                //We can safely assume an element has only one type associated with it
                return localGetLoanCommonDetailsRes.getPullParser(MY_QNAME);

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
        public static GetLoanCommonDetailsRes parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            GetLoanCommonDetailsRes object =
                new GetLoanCommonDetailsRes();

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
                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd","GetLoanCommonDetailsRes").equals(reader.getName())){
                                
                                                object.setGetLoanCommonDetailsRes(GetLoanCommonDetailsRes_type0.Factory.parse(reader));
                                            
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
           
    
        public static class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "http://www.adcb.com/esb/common/LoanCommonDetails.xsd".equals(namespaceURI) &&
                  "loan_Common_Details_type0".equals(typeName)){
                   
                            return  Loan_Common_Details_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.adcb.com/esb/common/LoanCommonDetails.xsd".equals(namespaceURI) &&
                  "loan_Details_type0".equals(typeName)){
                   
                            return  Loan_Details_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.adcb.com/esb/common/header.xsd".equals(namespaceURI) &&
                  "headerType".equals(typeName)){
                   
                            return  HeaderType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd".equals(namespaceURI) &&
                  "GetLoanCommonDetailsRes_type0".equals(typeName)){
                   
                            return  GetLoanCommonDetailsRes_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd".equals(namespaceURI) &&
                  "GetLoanCommonDetailsReq_type0".equals(typeName)){
                   
                            return  GetLoanCommonDetailsReq_type0.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    
        public static class Loan_Common_Details_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = loan_Common_Details_type0
                Namespace URI = http://www.adcb.com/esb/common/LoanCommonDetails.xsd
                Namespace Prefix = ns7
                */
            

                        /**
                        * field for Loan_Details
                        * This was an Array!
                        */

                        
                                    protected Loan_Details_type0[] localLoan_Details ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLoan_DetailsTracker = false ;

                           public boolean isLoan_DetailsSpecified(){
                               return localLoan_DetailsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return Loan_Details_type0[]
                           */
                           public  Loan_Details_type0[] getLoan_Details(){
                               return localLoan_Details;
                           }

                           
                        


                               
                              /**
                               * validate the array for Loan_Details
                               */
                              protected void validateLoan_Details(Loan_Details_type0[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param Loan_Details
                              */
                              public void setLoan_Details(Loan_Details_type0[] param){
                              
                                   validateLoan_Details(param);

                               localLoan_DetailsTracker = param != null;
                                      
                                      this.localLoan_Details=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param Loan_Details_type0
                             */
                             public void addLoan_Details(Loan_Details_type0 param){
                                   if (localLoan_Details == null){
                                   localLoan_Details = new Loan_Details_type0[]{};
                                   }

                            
                                 //update the setting tracker
                                localLoan_DetailsTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localLoan_Details);
                               list.add(param);
                               this.localLoan_Details =
                             (Loan_Details_type0[])list.toArray(
                            new Loan_Details_type0[list.size()]);

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/LoanCommonDetails.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":loan_Common_Details_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "loan_Common_Details_type0",
                           xmlWriter);
                   }

               
                   }
                if (localLoan_DetailsTracker){
                             if (localLoan_Details!=null) {
                                   namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                   for (int i = 0;i < localLoan_Details.length;i++){
                                        
                                            if (localLoan_Details[i] != null){
                                        
                                                writeStartElement(null, namespace, "loan_Details", xmlWriter);

                                            
                                                        xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoan_Details[i]));
                                                    
                                                xmlWriter.writeEndElement();
                                              
                                                } else {
                                                   
                                                           // we have to do nothing since minOccurs is zero
                                                       
                                                }

                                   }
                             } else {
                                 
                                         throw new org.apache.axis2.databinding.ADBException("loan_Details cannot be null!!");
                                    
                             }

                        }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/common/LoanCommonDetails.xsd")){
                return "ns7";
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

                 if (localLoan_DetailsTracker){
                            if (localLoan_Details!=null){
                                  for (int i = 0;i < localLoan_Details.length;i++){
                                      
                                         if (localLoan_Details[i] != null){
                                          elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                              "loan_Details"));
                                          elementList.add(
                                          org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoan_Details[i]));
                                          } else {
                                             
                                                    // have to do nothing
                                                
                                          }
                                      

                                  }
                            } else {
                              
                                    throw new org.apache.axis2.databinding.ADBException("loan_Details cannot be null!!");
                                
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
        public static Loan_Common_Details_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            Loan_Common_Details_type0 object =
                new Loan_Common_Details_type0();

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
                    
                            if (!"loan_Common_Details_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (Loan_Common_Details_type0)ExtensionMapper.getTypeObject(
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","loan_Details").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list1.add(reader.getElementText());
                                            
                                            //loop until we find a start element that is not part of this array
                                            boolean loopDone1 = false;
                                            while(!loopDone1){
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
                                                    loopDone1 = true;
                                                } else {
                                                    if (new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","loan_Details").equals(reader.getName())){
                                                         list1.add(reader.getElementText());
                                                        
                                                    }else{
                                                        loopDone1 = true;
                                                    }
                                                }
                                            }
                                            // call the converter utility  to convert and set the array
                                            
                                            object.setLoan_Details((Loan_Details_type0[])
                                                org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                            Loan_Details_type0.class,list1));
                                                
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
           
    
        public static class GetLoanCommonDetailsResMsg
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd",
                "GetLoanCommonDetailsResMsg",
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
                        * field for GetLoanCommonDetailsRes
                        */

                        
                                    protected GetLoanCommonDetailsRes_type0 localGetLoanCommonDetailsRes ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localGetLoanCommonDetailsResTracker = false ;

                           public boolean isGetLoanCommonDetailsResSpecified(){
                               return localGetLoanCommonDetailsResTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return GetLoanCommonDetailsRes_type0
                           */
                           public  GetLoanCommonDetailsRes_type0 getGetLoanCommonDetailsRes(){
                               return localGetLoanCommonDetailsRes;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param GetLoanCommonDetailsRes
                               */
                               public void setGetLoanCommonDetailsRes(GetLoanCommonDetailsRes_type0 param){
                            localGetLoanCommonDetailsResTracker = param != null;
                                   
                                            this.localGetLoanCommonDetailsRes=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":GetLoanCommonDetailsResMsg",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "GetLoanCommonDetailsResMsg",
                           xmlWriter);
                   }

               
                   }
               
                                            if (localHeader==null){
                                                 throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
                                            }
                                           localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
                                               xmlWriter);
                                         if (localGetLoanCommonDetailsResTracker){
                                            if (localGetLoanCommonDetailsRes==null){
                                                 throw new org.apache.axis2.databinding.ADBException("GetLoanCommonDetailsRes cannot be null!!");
                                            }
                                           localGetLoanCommonDetailsRes.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd","GetLoanCommonDetailsRes"),
                                               xmlWriter);
                                        }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd")){
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
                                 if (localGetLoanCommonDetailsResTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd",
                                                                      "GetLoanCommonDetailsRes"));
                            
                            
                                    if (localGetLoanCommonDetailsRes==null){
                                         throw new org.apache.axis2.databinding.ADBException("GetLoanCommonDetailsRes cannot be null!!");
                                    }
                                    elementList.add(localGetLoanCommonDetailsRes);
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
        public static GetLoanCommonDetailsResMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            GetLoanCommonDetailsResMsg object =
                new GetLoanCommonDetailsResMsg();

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
                    
                            if (!"GetLoanCommonDetailsResMsg".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (GetLoanCommonDetailsResMsg)ExtensionMapper.getTypeObject(
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd","GetLoanCommonDetailsRes").equals(reader.getName())){
                                
                                                object.setGetLoanCommonDetailsRes(GetLoanCommonDetailsRes_type0.Factory.parse(reader));
                                              
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
           
    
        public static class Loan_Details_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = loan_Details_type0
                Namespace URI = http://www.adcb.com/esb/common/LoanCommonDetails.xsd
                Namespace Prefix = ns7
                */
            

                        /**
                        * field for LoanAcct
                        */

                        
                                    protected java.lang.String localLoanAcct ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLoanAcctTracker = false ;

                           public boolean isLoanAcctSpecified(){
                               return localLoanAcctTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLoanAcct(){
                               return localLoanAcct;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LoanAcct
                               */
                               public void setLoanAcct(java.lang.String param){
                            localLoanAcctTracker = param != null;
                                   
                                            this.localLoanAcct=param;
                                    

                               }
                            

                        /**
                        * field for LAPSApplNo
                        */

                        
                                    protected java.lang.String localLAPSApplNo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLAPSApplNoTracker = false ;

                           public boolean isLAPSApplNoSpecified(){
                               return localLAPSApplNoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLAPSApplNo(){
                               return localLAPSApplNo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LAPSApplNo
                               */
                               public void setLAPSApplNo(java.lang.String param){
                            localLAPSApplNoTracker = param != null;
                                   
                                            this.localLAPSApplNo=param;
                                    

                               }
                            

                        /**
                        * field for BehaviouralScore
                        */

                        
                                    protected java.lang.String localBehaviouralScore ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localBehaviouralScoreTracker = false ;

                           public boolean isBehaviouralScoreSpecified(){
                               return localBehaviouralScoreTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getBehaviouralScore(){
                               return localBehaviouralScore;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param BehaviouralScore
                               */
                               public void setBehaviouralScore(java.lang.String param){
                            localBehaviouralScoreTracker = param != null;
                                   
                                            this.localBehaviouralScore=param;
                                    

                               }
                            

                        /**
                        * field for AttritionScore
                        */

                        
                                    protected java.lang.String localAttritionScore ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAttritionScoreTracker = false ;

                           public boolean isAttritionScoreSpecified(){
                               return localAttritionScoreTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAttritionScore(){
                               return localAttritionScore;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AttritionScore
                               */
                               public void setAttritionScore(java.lang.String param){
                            localAttritionScoreTracker = param != null;
                                   
                                            this.localAttritionScore=param;
                                    

                               }
                            

                        /**
                        * field for DomicileBranchName
                        */

                        
                                    protected java.lang.String localDomicileBranchName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDomicileBranchNameTracker = false ;

                           public boolean isDomicileBranchNameSpecified(){
                               return localDomicileBranchNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDomicileBranchName(){
                               return localDomicileBranchName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DomicileBranchName
                               */
                               public void setDomicileBranchName(java.lang.String param){
                            localDomicileBranchNameTracker = param != null;
                                   
                                            this.localDomicileBranchName=param;
                                    

                               }
                            

                        /**
                        * field for DomicileBranchLiteral
                        */

                        
                                    protected java.lang.String localDomicileBranchLiteral ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDomicileBranchLiteralTracker = false ;

                           public boolean isDomicileBranchLiteralSpecified(){
                               return localDomicileBranchLiteralTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDomicileBranchLiteral(){
                               return localDomicileBranchLiteral;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DomicileBranchLiteral
                               */
                               public void setDomicileBranchLiteral(java.lang.String param){
                            localDomicileBranchLiteralTracker = param != null;
                                   
                                            this.localDomicileBranchLiteral=param;
                                    

                               }
                            

                        /**
                        * field for DomicileBranchCode
                        */

                        
                                    protected java.lang.String localDomicileBranchCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDomicileBranchCodeTracker = false ;

                           public boolean isDomicileBranchCodeSpecified(){
                               return localDomicileBranchCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDomicileBranchCode(){
                               return localDomicileBranchCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DomicileBranchCode
                               */
                               public void setDomicileBranchCode(java.lang.String param){
                            localDomicileBranchCodeTracker = param != null;
                                   
                                            this.localDomicileBranchCode=param;
                                    

                               }
                            

                        /**
                        * field for AcctCurrencyCode
                        */

                        
                                    protected java.lang.String localAcctCurrencyCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAcctCurrencyCodeTracker = false ;

                           public boolean isAcctCurrencyCodeSpecified(){
                               return localAcctCurrencyCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAcctCurrencyCode(){
                               return localAcctCurrencyCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AcctCurrencyCode
                               */
                               public void setAcctCurrencyCode(java.lang.String param){
                            localAcctCurrencyCodeTracker = param != null;
                                   
                                            this.localAcctCurrencyCode=param;
                                    

                               }
                            

                        /**
                        * field for AcctCurrencyLiteral
                        */

                        
                                    protected java.lang.String localAcctCurrencyLiteral ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAcctCurrencyLiteralTracker = false ;

                           public boolean isAcctCurrencyLiteralSpecified(){
                               return localAcctCurrencyLiteralTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAcctCurrencyLiteral(){
                               return localAcctCurrencyLiteral;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AcctCurrencyLiteral
                               */
                               public void setAcctCurrencyLiteral(java.lang.String param){
                            localAcctCurrencyLiteralTracker = param != null;
                                   
                                            this.localAcctCurrencyLiteral=param;
                                    

                               }
                            

                        /**
                        * field for AcctCurrencyName
                        */

                        
                                    protected java.lang.String localAcctCurrencyName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAcctCurrencyNameTracker = false ;

                           public boolean isAcctCurrencyNameSpecified(){
                               return localAcctCurrencyNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAcctCurrencyName(){
                               return localAcctCurrencyName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AcctCurrencyName
                               */
                               public void setAcctCurrencyName(java.lang.String param){
                            localAcctCurrencyNameTracker = param != null;
                                   
                                            this.localAcctCurrencyName=param;
                                    

                               }
                            

                        /**
                        * field for LoanProductType
                        */

                        
                                    protected java.lang.String localLoanProductType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLoanProductTypeTracker = false ;

                           public boolean isLoanProductTypeSpecified(){
                               return localLoanProductTypeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLoanProductType(){
                               return localLoanProductType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LoanProductType
                               */
                               public void setLoanProductType(java.lang.String param){
                            localLoanProductTypeTracker = param != null;
                                   
                                            this.localLoanProductType=param;
                                    

                               }
                            

                        /**
                        * field for LoanProductCode
                        */

                        
                                    protected java.lang.String localLoanProductCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLoanProductCodeTracker = false ;

                           public boolean isLoanProductCodeSpecified(){
                               return localLoanProductCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLoanProductCode(){
                               return localLoanProductCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LoanProductCode
                               */
                               public void setLoanProductCode(java.lang.String param){
                            localLoanProductCodeTracker = param != null;
                                   
                                            this.localLoanProductCode=param;
                                    

                               }
                            

                        /**
                        * field for LoanProductName
                        */

                        
                                    protected java.lang.String localLoanProductName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLoanProductNameTracker = false ;

                           public boolean isLoanProductNameSpecified(){
                               return localLoanProductNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLoanProductName(){
                               return localLoanProductName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LoanProductName
                               */
                               public void setLoanProductName(java.lang.String param){
                            localLoanProductNameTracker = param != null;
                                   
                                            this.localLoanProductName=param;
                                    

                               }
                            

                        /**
                        * field for LoanAmt
                        */

                        
                                    protected java.lang.String localLoanAmt ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLoanAmtTracker = false ;

                           public boolean isLoanAmtSpecified(){
                               return localLoanAmtTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLoanAmt(){
                               return localLoanAmt;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LoanAmt
                               */
                               public void setLoanAmt(java.lang.String param){
                            localLoanAmtTracker = param != null;
                                   
                                            this.localLoanAmt=param;
                                    

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
                        * field for DisbursalDate
                        */

                        
                                    protected java.lang.String localDisbursalDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDisbursalDateTracker = false ;

                           public boolean isDisbursalDateSpecified(){
                               return localDisbursalDateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getDisbursalDate(){
                               return localDisbursalDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DisbursalDate
                               */
                               public void setDisbursalDate(java.lang.String param){
                            localDisbursalDateTracker = param != null;
                                   
                                            this.localDisbursalDate=param;
                                    

                               }
                            

                        /**
                        * field for MaturityDate
                        */

                        
                                    protected java.lang.String localMaturityDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMaturityDateTracker = false ;

                           public boolean isMaturityDateSpecified(){
                               return localMaturityDateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMaturityDate(){
                               return localMaturityDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MaturityDate
                               */
                               public void setMaturityDate(java.lang.String param){
                            localMaturityDateTracker = param != null;
                                   
                                            this.localMaturityDate=param;
                                    

                               }
                            

                        /**
                        * field for AcctType
                        */

                        
                                    protected java.lang.String localAcctType ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAcctTypeTracker = false ;

                           public boolean isAcctTypeSpecified(){
                               return localAcctTypeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAcctType(){
                               return localAcctType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AcctType
                               */
                               public void setAcctType(java.lang.String param){
                            localAcctTypeTracker = param != null;
                                   
                                            this.localAcctType=param;
                                    

                               }
                            

                        /**
                        * field for AcctStatus
                        */

                        
                                    protected java.lang.String localAcctStatus ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAcctStatusTracker = false ;

                           public boolean isAcctStatusSpecified(){
                               return localAcctStatusTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAcctStatus(){
                               return localAcctStatus;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AcctStatus
                               */
                               public void setAcctStatus(java.lang.String param){
                            localAcctStatusTracker = param != null;
                                   
                                            this.localAcctStatus=param;
                                    

                               }
                            

                        /**
                        * field for AcctStatusCode
                        */

                        
                                    protected java.lang.String localAcctStatusCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAcctStatusCodeTracker = false ;

                           public boolean isAcctStatusCodeSpecified(){
                               return localAcctStatusCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAcctStatusCode(){
                               return localAcctStatusCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AcctStatusCode
                               */
                               public void setAcctStatusCode(java.lang.String param){
                            localAcctStatusCodeTracker = param != null;
                                   
                                            this.localAcctStatusCode=param;
                                    

                               }
                            

                        /**
                        * field for LoanAccountOpeningDate
                        */

                        
                                    protected java.lang.String localLoanAccountOpeningDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLoanAccountOpeningDateTracker = false ;

                           public boolean isLoanAccountOpeningDateSpecified(){
                               return localLoanAccountOpeningDateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLoanAccountOpeningDate(){
                               return localLoanAccountOpeningDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LoanAccountOpeningDate
                               */
                               public void setLoanAccountOpeningDate(java.lang.String param){
                            localLoanAccountOpeningDateTracker = param != null;
                                   
                                            this.localLoanAccountOpeningDate=param;
                                    

                               }
                            

                        /**
                        * field for EMIAmt
                        */

                        
                                    protected java.lang.String localEMIAmt ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEMIAmtTracker = false ;

                           public boolean isEMIAmtSpecified(){
                               return localEMIAmtTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEMIAmt(){
                               return localEMIAmt;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EMIAmt
                               */
                               public void setEMIAmt(java.lang.String param){
                            localEMIAmtTracker = param != null;
                                   
                                            this.localEMIAmt=param;
                                    

                               }
                            

                        /**
                        * field for EMIMode
                        */

                        
                                    protected java.lang.String localEMIMode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEMIModeTracker = false ;

                           public boolean isEMIModeSpecified(){
                               return localEMIModeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getEMIMode(){
                               return localEMIMode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EMIMode
                               */
                               public void setEMIMode(java.lang.String param){
                            localEMIModeTracker = param != null;
                                   
                                            this.localEMIMode=param;
                                    

                               }
                            

                        /**
                        * field for LinkedCASAAccount
                        */

                        
                                    protected java.lang.String localLinkedCASAAccount ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLinkedCASAAccountTracker = false ;

                           public boolean isLinkedCASAAccountSpecified(){
                               return localLinkedCASAAccountTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLinkedCASAAccount(){
                               return localLinkedCASAAccount;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LinkedCASAAccount
                               */
                               public void setLinkedCASAAccount(java.lang.String param){
                            localLinkedCASAAccountTracker = param != null;
                                   
                                            this.localLinkedCASAAccount=param;
                                    

                               }
                            

                        /**
                        * field for NextEMIDate
                        */

                        
                                    protected java.lang.String localNextEMIDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNextEMIDateTracker = false ;

                           public boolean isNextEMIDateSpecified(){
                               return localNextEMIDateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getNextEMIDate(){
                               return localNextEMIDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NextEMIDate
                               */
                               public void setNextEMIDate(java.lang.String param){
                            localNextEMIDateTracker = param != null;
                                   
                                            this.localNextEMIDate=param;
                                    

                               }
                            

                        /**
                        * field for NoofEMIPaid
                        */

                        
                                    protected java.lang.String localNoofEMIPaid ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNoofEMIPaidTracker = false ;

                           public boolean isNoofEMIPaidSpecified(){
                               return localNoofEMIPaidTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getNoofEMIPaid(){
                               return localNoofEMIPaid;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NoofEMIPaid
                               */
                               public void setNoofEMIPaid(java.lang.String param){
                            localNoofEMIPaidTracker = param != null;
                                   
                                            this.localNoofEMIPaid=param;
                                    

                               }
                            

                        /**
                        * field for OutstandingBalance
                        */

                        
                                    protected java.lang.String localOutstandingBalance ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOutstandingBalanceTracker = false ;

                           public boolean isOutstandingBalanceSpecified(){
                               return localOutstandingBalanceTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOutstandingBalance(){
                               return localOutstandingBalance;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OutstandingBalance
                               */
                               public void setOutstandingBalance(java.lang.String param){
                            localOutstandingBalanceTracker = param != null;
                                   
                                            this.localOutstandingBalance=param;
                                    

                               }
                            

                        /**
                        * field for LoanFaceValue
                        */

                        
                                    protected java.lang.String localLoanFaceValue ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLoanFaceValueTracker = false ;

                           public boolean isLoanFaceValueSpecified(){
                               return localLoanFaceValueTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getLoanFaceValue(){
                               return localLoanFaceValue;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LoanFaceValue
                               */
                               public void setLoanFaceValue(java.lang.String param){
                            localLoanFaceValueTracker = param != null;
                                   
                                            this.localLoanFaceValue=param;
                                    

                               }
                            

                        /**
                        * field for NetPayableAmount
                        */

                        
                                    protected java.lang.String localNetPayableAmount ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNetPayableAmountTracker = false ;

                           public boolean isNetPayableAmountSpecified(){
                               return localNetPayableAmountTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getNetPayableAmount(){
                               return localNetPayableAmount;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NetPayableAmount
                               */
                               public void setNetPayableAmount(java.lang.String param){
                            localNetPayableAmountTracker = param != null;
                                   
                                            this.localNetPayableAmount=param;
                                    

                               }
                            

                        /**
                        * field for HomeBranchName
                        */

                        
                                    protected java.lang.String localHomeBranchName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHomeBranchNameTracker = false ;

                           public boolean isHomeBranchNameSpecified(){
                               return localHomeBranchNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getHomeBranchName(){
                               return localHomeBranchName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HomeBranchName
                               */
                               public void setHomeBranchName(java.lang.String param){
                            localHomeBranchNameTracker = param != null;
                                   
                                            this.localHomeBranchName=param;
                                    

                               }
                            

                        /**
                        * field for HomeBranchLiteral
                        */

                        
                                    protected java.lang.String localHomeBranchLiteral ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHomeBranchLiteralTracker = false ;

                           public boolean isHomeBranchLiteralSpecified(){
                               return localHomeBranchLiteralTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getHomeBranchLiteral(){
                               return localHomeBranchLiteral;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HomeBranchLiteral
                               */
                               public void setHomeBranchLiteral(java.lang.String param){
                            localHomeBranchLiteralTracker = param != null;
                                   
                                            this.localHomeBranchLiteral=param;
                                    

                               }
                            

                        /**
                        * field for HomeBranchCode
                        */

                        
                                    protected java.lang.String localHomeBranchCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHomeBranchCodeTracker = false ;

                           public boolean isHomeBranchCodeSpecified(){
                               return localHomeBranchCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getHomeBranchCode(){
                               return localHomeBranchCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HomeBranchCode
                               */
                               public void setHomeBranchCode(java.lang.String param){
                            localHomeBranchCodeTracker = param != null;
                                   
                                            this.localHomeBranchCode=param;
                                    

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
                        * field for RepaymentModeFlag
                        */

                        
                                    protected java.lang.String localRepaymentModeFlag ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRepaymentModeFlagTracker = false ;

                           public boolean isRepaymentModeFlagSpecified(){
                               return localRepaymentModeFlagTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getRepaymentModeFlag(){
                               return localRepaymentModeFlag;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RepaymentModeFlag
                               */
                               public void setRepaymentModeFlag(java.lang.String param){
                            localRepaymentModeFlagTracker = param != null;
                                   
                                            this.localRepaymentModeFlag=param;
                                    

                               }
                            

                        /**
                        * field for RepaymentModeDesc
                        */

                        
                                    protected java.lang.String localRepaymentModeDesc ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRepaymentModeDescTracker = false ;

                           public boolean isRepaymentModeDescSpecified(){
                               return localRepaymentModeDescTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getRepaymentModeDesc(){
                               return localRepaymentModeDesc;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RepaymentModeDesc
                               */
                               public void setRepaymentModeDesc(java.lang.String param){
                            localRepaymentModeDescTracker = param != null;
                                   
                                            this.localRepaymentModeDesc=param;
                                    

                               }
                            

                        /**
                        * field for TotalOutstandingBalanceTCY
                        */

                        
                                    protected java.lang.String localTotalOutstandingBalanceTCY ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTotalOutstandingBalanceTCYTracker = false ;

                           public boolean isTotalOutstandingBalanceTCYSpecified(){
                               return localTotalOutstandingBalanceTCYTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTotalOutstandingBalanceTCY(){
                               return localTotalOutstandingBalanceTCY;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TotalOutstandingBalanceTCY
                               */
                               public void setTotalOutstandingBalanceTCY(java.lang.String param){
                            localTotalOutstandingBalanceTCYTracker = param != null;
                                   
                                            this.localTotalOutstandingBalanceTCY=param;
                                    

                               }
                            

                        /**
                        * field for TotalOutstandingBalanceLCY
                        */

                        
                                    protected java.lang.String localTotalOutstandingBalanceLCY ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTotalOutstandingBalanceLCYTracker = false ;

                           public boolean isTotalOutstandingBalanceLCYSpecified(){
                               return localTotalOutstandingBalanceLCYTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getTotalOutstandingBalanceLCY(){
                               return localTotalOutstandingBalanceLCY;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TotalOutstandingBalanceLCY
                               */
                               public void setTotalOutstandingBalanceLCY(java.lang.String param){
                            localTotalOutstandingBalanceLCYTracker = param != null;
                                   
                                            this.localTotalOutstandingBalanceLCY=param;
                                    

                               }
                            

                        /**
                        * field for ModuleCode
                        */

                        
                                    protected java.lang.String localModuleCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localModuleCodeTracker = false ;

                           public boolean isModuleCodeSpecified(){
                               return localModuleCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getModuleCode(){
                               return localModuleCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ModuleCode
                               */
                               public void setModuleCode(java.lang.String param){
                            localModuleCodeTracker = param != null;
                                   
                                            this.localModuleCode=param;
                                    

                               }
                            

                        /**
                        * field for FirstEMIDate
                        */

                        
                                    protected java.lang.String localFirstEMIDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFirstEMIDateTracker = false ;

                           public boolean isFirstEMIDateSpecified(){
                               return localFirstEMIDateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getFirstEMIDate(){
                               return localFirstEMIDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param FirstEMIDate
                               */
                               public void setFirstEMIDate(java.lang.String param){
                            localFirstEMIDateTracker = param != null;
                                   
                                            this.localFirstEMIDate=param;
                                    

                               }
                            

                        /**
                        * field for NoOfArrears
                        */

                        
                                    protected java.lang.String localNoOfArrears ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNoOfArrearsTracker = false ;

                           public boolean isNoOfArrearsSpecified(){
                               return localNoOfArrearsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getNoOfArrears(){
                               return localNoOfArrears;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param NoOfArrears
                               */
                               public void setNoOfArrears(java.lang.String param){
                            localNoOfArrearsTracker = param != null;
                                   
                                            this.localNoOfArrears=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/LoanCommonDetails.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":loan_Details_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "loan_Details_type0",
                           xmlWriter);
                   }

               
                   }
                if (localLoanAcctTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "loanAcct", xmlWriter);
                             

                                          if (localLoanAcct==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("loanAcct cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLoanAcct);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLAPSApplNoTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "LAPSApplNo", xmlWriter);
                             

                                          if (localLAPSApplNo==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("LAPSApplNo cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLAPSApplNo);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localBehaviouralScoreTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "behaviouralScore", xmlWriter);
                             

                                          if (localBehaviouralScore==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("behaviouralScore cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localBehaviouralScore);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAttritionScoreTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "attritionScore", xmlWriter);
                             

                                          if (localAttritionScore==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("attritionScore cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAttritionScore);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDomicileBranchNameTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "domicileBranchName", xmlWriter);
                             

                                          if (localDomicileBranchName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("domicileBranchName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDomicileBranchName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDomicileBranchLiteralTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "domicileBranchLiteral", xmlWriter);
                             

                                          if (localDomicileBranchLiteral==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("domicileBranchLiteral cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDomicileBranchLiteral);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDomicileBranchCodeTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "domicileBranchCode", xmlWriter);
                             

                                          if (localDomicileBranchCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("domicileBranchCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDomicileBranchCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAcctCurrencyCodeTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "acctCurrencyCode", xmlWriter);
                             

                                          if (localAcctCurrencyCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("acctCurrencyCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAcctCurrencyCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAcctCurrencyLiteralTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "acctCurrencyLiteral", xmlWriter);
                             

                                          if (localAcctCurrencyLiteral==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("acctCurrencyLiteral cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAcctCurrencyLiteral);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAcctCurrencyNameTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "acctCurrencyName", xmlWriter);
                             

                                          if (localAcctCurrencyName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("acctCurrencyName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAcctCurrencyName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLoanProductTypeTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "loanProductType", xmlWriter);
                             

                                          if (localLoanProductType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("loanProductType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLoanProductType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLoanProductCodeTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "loanProductCode", xmlWriter);
                             

                                          if (localLoanProductCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("loanProductCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLoanProductCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLoanProductNameTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "loanProductName", xmlWriter);
                             

                                          if (localLoanProductName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("loanProductName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLoanProductName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLoanAmtTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "loanAmt", xmlWriter);
                             

                                          if (localLoanAmt==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("loanAmt cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLoanAmt);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLoanTenorTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "loanTenor", xmlWriter);
                             

                                          if (localLoanTenor==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("loanTenor cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLoanTenor);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLoanInterestRateTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "loanInterestRate", xmlWriter);
                             

                                          if (localLoanInterestRate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("loanInterestRate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLoanInterestRate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localDisbursalDateTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "disbursalDate", xmlWriter);
                             

                                          if (localDisbursalDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("disbursalDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localDisbursalDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMaturityDateTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "maturityDate", xmlWriter);
                             

                                          if (localMaturityDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("maturityDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMaturityDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAcctTypeTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "acctType", xmlWriter);
                             

                                          if (localAcctType==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("acctType cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAcctType);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAcctStatusTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "acctStatus", xmlWriter);
                             

                                          if (localAcctStatus==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("acctStatus cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAcctStatus);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAcctStatusCodeTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "acctStatusCode", xmlWriter);
                             

                                          if (localAcctStatusCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("acctStatusCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAcctStatusCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLoanAccountOpeningDateTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "loanAccountOpeningDate", xmlWriter);
                             

                                          if (localLoanAccountOpeningDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("loanAccountOpeningDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLoanAccountOpeningDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localEMIAmtTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "EMIAmt", xmlWriter);
                             

                                          if (localEMIAmt==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("EMIAmt cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEMIAmt);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localEMIModeTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "EMIMode", xmlWriter);
                             

                                          if (localEMIMode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("EMIMode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localEMIMode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLinkedCASAAccountTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "linkedCASAAccount", xmlWriter);
                             

                                          if (localLinkedCASAAccount==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("linkedCASAAccount cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLinkedCASAAccount);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNextEMIDateTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "nextEMIDate", xmlWriter);
                             

                                          if (localNextEMIDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("nextEMIDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localNextEMIDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNoofEMIPaidTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "NoofEMIPaid", xmlWriter);
                             

                                          if (localNoofEMIPaid==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("NoofEMIPaid cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localNoofEMIPaid);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOutstandingBalanceTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "outstandingBalance", xmlWriter);
                             

                                          if (localOutstandingBalance==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("outstandingBalance cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOutstandingBalance);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLoanFaceValueTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "loanFaceValue", xmlWriter);
                             

                                          if (localLoanFaceValue==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("loanFaceValue cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLoanFaceValue);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNetPayableAmountTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "netPayableAmount", xmlWriter);
                             

                                          if (localNetPayableAmount==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("netPayableAmount cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localNetPayableAmount);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHomeBranchNameTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "homeBranchName", xmlWriter);
                             

                                          if (localHomeBranchName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("homeBranchName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localHomeBranchName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHomeBranchLiteralTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "homeBranchLiteral", xmlWriter);
                             

                                          if (localHomeBranchLiteral==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("homeBranchLiteral cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localHomeBranchLiteral);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHomeBranchCodeTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "homeBranchCode", xmlWriter);
                             

                                          if (localHomeBranchCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("homeBranchCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localHomeBranchCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localCustShortNameTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "custShortName", xmlWriter);
                             

                                          if (localCustShortName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("custShortName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCustShortName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localRepaymentModeFlagTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "repaymentModeFlag", xmlWriter);
                             

                                          if (localRepaymentModeFlag==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("repaymentModeFlag cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localRepaymentModeFlag);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localRepaymentModeDescTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "repaymentModeDesc", xmlWriter);
                             

                                          if (localRepaymentModeDesc==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("repaymentModeDesc cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localRepaymentModeDesc);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTotalOutstandingBalanceTCYTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "totalOutstandingBalanceTCY", xmlWriter);
                             

                                          if (localTotalOutstandingBalanceTCY==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("totalOutstandingBalanceTCY cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTotalOutstandingBalanceTCY);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTotalOutstandingBalanceLCYTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "totalOutstandingBalanceLCY", xmlWriter);
                             

                                          if (localTotalOutstandingBalanceLCY==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("totalOutstandingBalanceLCY cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localTotalOutstandingBalanceLCY);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localModuleCodeTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "moduleCode", xmlWriter);
                             

                                          if (localModuleCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("moduleCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localModuleCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localFirstEMIDateTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "firstEMIDate", xmlWriter);
                             

                                          if (localFirstEMIDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("firstEMIDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localFirstEMIDate);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNoOfArrearsTracker){
                                    namespace = "http://www.adcb.com/esb/common/LoanCommonDetails.xsd";
                                    writeStartElement(null, namespace, "noOfArrears", xmlWriter);
                             

                                          if (localNoOfArrears==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("noOfArrears cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localNoOfArrears);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/common/LoanCommonDetails.xsd")){
                return "ns7";
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

                 if (localLoanAcctTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "loanAcct"));
                                 
                                        if (localLoanAcct != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanAcct));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("loanAcct cannot be null!!");
                                        }
                                    } if (localLAPSApplNoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "LAPSApplNo"));
                                 
                                        if (localLAPSApplNo != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLAPSApplNo));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("LAPSApplNo cannot be null!!");
                                        }
                                    } if (localBehaviouralScoreTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "behaviouralScore"));
                                 
                                        if (localBehaviouralScore != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBehaviouralScore));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("behaviouralScore cannot be null!!");
                                        }
                                    } if (localAttritionScoreTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "attritionScore"));
                                 
                                        if (localAttritionScore != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAttritionScore));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("attritionScore cannot be null!!");
                                        }
                                    } if (localDomicileBranchNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "domicileBranchName"));
                                 
                                        if (localDomicileBranchName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDomicileBranchName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("domicileBranchName cannot be null!!");
                                        }
                                    } if (localDomicileBranchLiteralTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "domicileBranchLiteral"));
                                 
                                        if (localDomicileBranchLiteral != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDomicileBranchLiteral));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("domicileBranchLiteral cannot be null!!");
                                        }
                                    } if (localDomicileBranchCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "domicileBranchCode"));
                                 
                                        if (localDomicileBranchCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDomicileBranchCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("domicileBranchCode cannot be null!!");
                                        }
                                    } if (localAcctCurrencyCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "acctCurrencyCode"));
                                 
                                        if (localAcctCurrencyCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAcctCurrencyCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("acctCurrencyCode cannot be null!!");
                                        }
                                    } if (localAcctCurrencyLiteralTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "acctCurrencyLiteral"));
                                 
                                        if (localAcctCurrencyLiteral != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAcctCurrencyLiteral));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("acctCurrencyLiteral cannot be null!!");
                                        }
                                    } if (localAcctCurrencyNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "acctCurrencyName"));
                                 
                                        if (localAcctCurrencyName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAcctCurrencyName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("acctCurrencyName cannot be null!!");
                                        }
                                    } if (localLoanProductTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "loanProductType"));
                                 
                                        if (localLoanProductType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanProductType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("loanProductType cannot be null!!");
                                        }
                                    } if (localLoanProductCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "loanProductCode"));
                                 
                                        if (localLoanProductCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanProductCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("loanProductCode cannot be null!!");
                                        }
                                    } if (localLoanProductNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "loanProductName"));
                                 
                                        if (localLoanProductName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanProductName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("loanProductName cannot be null!!");
                                        }
                                    } if (localLoanAmtTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "loanAmt"));
                                 
                                        if (localLoanAmt != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanAmt));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("loanAmt cannot be null!!");
                                        }
                                    } if (localLoanTenorTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "loanTenor"));
                                 
                                        if (localLoanTenor != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanTenor));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("loanTenor cannot be null!!");
                                        }
                                    } if (localLoanInterestRateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "loanInterestRate"));
                                 
                                        if (localLoanInterestRate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanInterestRate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("loanInterestRate cannot be null!!");
                                        }
                                    } if (localDisbursalDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "disbursalDate"));
                                 
                                        if (localDisbursalDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDisbursalDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("disbursalDate cannot be null!!");
                                        }
                                    } if (localMaturityDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "maturityDate"));
                                 
                                        if (localMaturityDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMaturityDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("maturityDate cannot be null!!");
                                        }
                                    } if (localAcctTypeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "acctType"));
                                 
                                        if (localAcctType != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAcctType));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("acctType cannot be null!!");
                                        }
                                    } if (localAcctStatusTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "acctStatus"));
                                 
                                        if (localAcctStatus != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAcctStatus));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("acctStatus cannot be null!!");
                                        }
                                    } if (localAcctStatusCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "acctStatusCode"));
                                 
                                        if (localAcctStatusCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAcctStatusCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("acctStatusCode cannot be null!!");
                                        }
                                    } if (localLoanAccountOpeningDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "loanAccountOpeningDate"));
                                 
                                        if (localLoanAccountOpeningDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanAccountOpeningDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("loanAccountOpeningDate cannot be null!!");
                                        }
                                    } if (localEMIAmtTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "EMIAmt"));
                                 
                                        if (localEMIAmt != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEMIAmt));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("EMIAmt cannot be null!!");
                                        }
                                    } if (localEMIModeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "EMIMode"));
                                 
                                        if (localEMIMode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEMIMode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("EMIMode cannot be null!!");
                                        }
                                    } if (localLinkedCASAAccountTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "linkedCASAAccount"));
                                 
                                        if (localLinkedCASAAccount != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLinkedCASAAccount));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("linkedCASAAccount cannot be null!!");
                                        }
                                    } if (localNextEMIDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "nextEMIDate"));
                                 
                                        if (localNextEMIDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNextEMIDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("nextEMIDate cannot be null!!");
                                        }
                                    } if (localNoofEMIPaidTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "NoofEMIPaid"));
                                 
                                        if (localNoofEMIPaid != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNoofEMIPaid));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("NoofEMIPaid cannot be null!!");
                                        }
                                    } if (localOutstandingBalanceTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "outstandingBalance"));
                                 
                                        if (localOutstandingBalance != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOutstandingBalance));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("outstandingBalance cannot be null!!");
                                        }
                                    } if (localLoanFaceValueTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "loanFaceValue"));
                                 
                                        if (localLoanFaceValue != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanFaceValue));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("loanFaceValue cannot be null!!");
                                        }
                                    } if (localNetPayableAmountTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "netPayableAmount"));
                                 
                                        if (localNetPayableAmount != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNetPayableAmount));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("netPayableAmount cannot be null!!");
                                        }
                                    } if (localHomeBranchNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "homeBranchName"));
                                 
                                        if (localHomeBranchName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHomeBranchName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("homeBranchName cannot be null!!");
                                        }
                                    } if (localHomeBranchLiteralTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "homeBranchLiteral"));
                                 
                                        if (localHomeBranchLiteral != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHomeBranchLiteral));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("homeBranchLiteral cannot be null!!");
                                        }
                                    } if (localHomeBranchCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "homeBranchCode"));
                                 
                                        if (localHomeBranchCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHomeBranchCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("homeBranchCode cannot be null!!");
                                        }
                                    } if (localCustShortNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "custShortName"));
                                 
                                        if (localCustShortName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustShortName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("custShortName cannot be null!!");
                                        }
                                    } if (localRepaymentModeFlagTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "repaymentModeFlag"));
                                 
                                        if (localRepaymentModeFlag != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRepaymentModeFlag));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("repaymentModeFlag cannot be null!!");
                                        }
                                    } if (localRepaymentModeDescTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "repaymentModeDesc"));
                                 
                                        if (localRepaymentModeDesc != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRepaymentModeDesc));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("repaymentModeDesc cannot be null!!");
                                        }
                                    } if (localTotalOutstandingBalanceTCYTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "totalOutstandingBalanceTCY"));
                                 
                                        if (localTotalOutstandingBalanceTCY != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTotalOutstandingBalanceTCY));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("totalOutstandingBalanceTCY cannot be null!!");
                                        }
                                    } if (localTotalOutstandingBalanceLCYTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "totalOutstandingBalanceLCY"));
                                 
                                        if (localTotalOutstandingBalanceLCY != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTotalOutstandingBalanceLCY));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("totalOutstandingBalanceLCY cannot be null!!");
                                        }
                                    } if (localModuleCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "moduleCode"));
                                 
                                        if (localModuleCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localModuleCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("moduleCode cannot be null!!");
                                        }
                                    } if (localFirstEMIDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "firstEMIDate"));
                                 
                                        if (localFirstEMIDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFirstEMIDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("firstEMIDate cannot be null!!");
                                        }
                                    } if (localNoOfArrearsTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "noOfArrears"));
                                 
                                        if (localNoOfArrears != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNoOfArrears));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("noOfArrears cannot be null!!");
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
        public static Loan_Details_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            Loan_Details_type0 object =
                new Loan_Details_type0();

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
                    
                            if (!"loan_Details_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (Loan_Details_type0)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","loanAcct").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"loanAcct" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLoanAcct(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","LAPSApplNo").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"LAPSApplNo" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLAPSApplNo(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","behaviouralScore").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"behaviouralScore" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setBehaviouralScore(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","attritionScore").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"attritionScore" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAttritionScore(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","domicileBranchName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"domicileBranchName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDomicileBranchName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","domicileBranchLiteral").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"domicileBranchLiteral" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDomicileBranchLiteral(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","domicileBranchCode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"domicileBranchCode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDomicileBranchCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","acctCurrencyCode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"acctCurrencyCode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAcctCurrencyCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","acctCurrencyLiteral").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"acctCurrencyLiteral" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAcctCurrencyLiteral(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","acctCurrencyName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"acctCurrencyName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAcctCurrencyName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","loanProductType").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"loanProductType" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLoanProductType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","loanProductCode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"loanProductCode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLoanProductCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","loanProductName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"loanProductName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLoanProductName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","loanAmt").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"loanAmt" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLoanAmt(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","loanTenor").equals(reader.getName())){
                                
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","loanInterestRate").equals(reader.getName())){
                                
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","disbursalDate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"disbursalDate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setDisbursalDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","maturityDate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"maturityDate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMaturityDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","acctType").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"acctType" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAcctType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","acctStatus").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"acctStatus" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAcctStatus(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","acctStatusCode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"acctStatusCode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAcctStatusCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","loanAccountOpeningDate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"loanAccountOpeningDate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLoanAccountOpeningDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","EMIAmt").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"EMIAmt" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEMIAmt(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","EMIMode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"EMIMode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setEMIMode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","linkedCASAAccount").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"linkedCASAAccount" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLinkedCASAAccount(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","nextEMIDate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"nextEMIDate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNextEMIDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","NoofEMIPaid").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"NoofEMIPaid" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNoofEMIPaid(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","outstandingBalance").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"outstandingBalance" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOutstandingBalance(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","loanFaceValue").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"loanFaceValue" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLoanFaceValue(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","netPayableAmount").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"netPayableAmount" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNetPayableAmount(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","homeBranchName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"homeBranchName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHomeBranchName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","homeBranchLiteral").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"homeBranchLiteral" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHomeBranchLiteral(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","homeBranchCode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"homeBranchCode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setHomeBranchCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","custShortName").equals(reader.getName())){
                                
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","repaymentModeFlag").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"repaymentModeFlag" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRepaymentModeFlag(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","repaymentModeDesc").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"repaymentModeDesc" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRepaymentModeDesc(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","totalOutstandingBalanceTCY").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"totalOutstandingBalanceTCY" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTotalOutstandingBalanceTCY(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","totalOutstandingBalanceLCY").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"totalOutstandingBalanceLCY" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTotalOutstandingBalanceLCY(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","moduleCode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"moduleCode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setModuleCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","firstEMIDate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"firstEMIDate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFirstEMIDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","noOfArrears").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"noOfArrears" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setNoOfArrears(
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
           
    
        public static class Loan_Common_Details
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                "loan_Common_Details",
                "ns7");

            

                        /**
                        * field for Loan_Details
                        * This was an Array!
                        */

                        
                                    protected Loan_Details_type0[] localLoan_Details ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLoan_DetailsTracker = false ;

                           public boolean isLoan_DetailsSpecified(){
                               return localLoan_DetailsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return Loan_Details_type0[]
                           */
                           public  Loan_Details_type0[] getLoan_Details(){
                               return localLoan_Details;
                           }

                           
                        


                               
                              /**
                               * validate the array for Loan_Details
                               */
                              protected void validateLoan_Details(Loan_Details_type0[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param Loan_Details
                              */
                              public void setLoan_Details(Loan_Details_type0[] param){
                              
                                   validateLoan_Details(param);

                               localLoan_DetailsTracker = param != null;
                                      
                                      this.localLoan_Details=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param Loan_Details_type0
                             */
                             public void addLoan_Details(Loan_Details_type0 param){
                                   if (localLoan_Details == null){
                                   localLoan_Details = new Loan_Details_type0[]{};
                                   }

                            
                                 //update the setting tracker
                                localLoan_DetailsTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localLoan_Details);
                               list.add(param);
                               this.localLoan_Details =
                             (Loan_Details_type0[])list.toArray(
                            new Loan_Details_type0[list.size()]);

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/common/LoanCommonDetails.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":loan_Common_Details",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "loan_Common_Details",
                           xmlWriter);
                   }

               
                   }
                if (localLoan_DetailsTracker){
                                       if (localLoan_Details!=null){
                                            for (int i = 0;i < localLoan_Details.length;i++){
                                                if (localLoan_Details[i] != null){
                                                 localLoan_Details[i].serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","loan_Details"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("loan_Details cannot be null!!");
                                        
                                    }
                                 }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/common/LoanCommonDetails.xsd")){
                return "ns7";
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

                 if (localLoan_DetailsTracker){
                             if (localLoan_Details!=null) {
                                 for (int i = 0;i < localLoan_Details.length;i++){

                                    if (localLoan_Details[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                          "loan_Details"));
                                         elementList.add(localLoan_Details[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("loan_Details cannot be null!!");
                                    
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
        public static Loan_Common_Details parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            Loan_Common_Details object =
                new Loan_Common_Details();

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
                    
                            if (!"loan_Common_Details".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (Loan_Common_Details)ExtensionMapper.getTypeObject(
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","loan_Details").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list1.add(Loan_Details_type0.Factory.parse(reader));
                                                                
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
                                                                if (new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","loan_Details").equals(reader.getName())){
                                                                    list1.add(Loan_Details_type0.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone1 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setLoan_Details((Loan_Details_type0[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                Loan_Details_type0.class,
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
           
    
        public static class Loan_Details
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                "loan_Details",
                "ns7");

            

                        /**
                        * field for Loan_Details
                        */

                        
                                    protected Loan_Details_type0 localLoan_Details ;
                                

                           /**
                           * Auto generated getter method
                           * @return Loan_Details_type0
                           */
                           public  Loan_Details_type0 getLoan_Details(){
                               return localLoan_Details;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Loan_Details
                               */
                               public void setLoan_Details(Loan_Details_type0 param){
                            
                                            this.localLoan_Details=param;
                                    

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
                
                                 if (localLoan_Details==null){
                                   throw new org.apache.axis2.databinding.ADBException("loan_Details cannot be null!");
                                 }
                                 localLoan_Details.serialize(MY_QNAME,xmlWriter);
                            

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/common/LoanCommonDetails.xsd")){
                return "ns7";
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
                return localLoan_Details.getPullParser(MY_QNAME);

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
        public static Loan_Details parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            Loan_Details object =
                new Loan_Details();

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
                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","loan_Details").equals(reader.getName())){
                                
                                                object.setLoan_Details(Loan_Details_type0.Factory.parse(reader));
                                            
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
           
    
        public static class GetLoanCommonDetailsRes_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = GetLoanCommonDetailsRes_type0
                Namespace URI = http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd
                Namespace Prefix = ns8
                */
            

                        /**
                        * field for Loan_Common_Details
                        */

                        
                                    protected Loan_Common_Details_type0 localLoan_Common_Details ;
                                

                           /**
                           * Auto generated getter method
                           * @return Loan_Common_Details_type0
                           */
                           public  Loan_Common_Details_type0 getLoan_Common_Details(){
                               return localLoan_Common_Details;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Loan_Common_Details
                               */
                               public void setLoan_Common_Details(Loan_Common_Details_type0 param){
                            
                                            this.localLoan_Common_Details=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":GetLoanCommonDetailsRes_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "GetLoanCommonDetailsRes_type0",
                           xmlWriter);
                   }

               
                   }
               
                                            if (localLoan_Common_Details==null){
                                                 throw new org.apache.axis2.databinding.ADBException("loan_Common_Details cannot be null!!");
                                            }
                                           localLoan_Common_Details.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","loan_Common_Details"),
                                               xmlWriter);
                                        
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd")){
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

                
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd",
                                                                      "loan_Common_Details"));
                            
                            
                                    if (localLoan_Common_Details==null){
                                         throw new org.apache.axis2.databinding.ADBException("loan_Common_Details cannot be null!!");
                                    }
                                    elementList.add(localLoan_Common_Details);
                                

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
        public static GetLoanCommonDetailsRes_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            GetLoanCommonDetailsRes_type0 object =
                new GetLoanCommonDetailsRes_type0();

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
                    
                            if (!"GetLoanCommonDetailsRes_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (GetLoanCommonDetailsRes_type0)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/common/LoanCommonDetails.xsd","loan_Common_Details").equals(reader.getName())){
                                
                                                object.setLoan_Common_Details(Loan_Common_Details_type0.Factory.parse(reader));
                                              
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
           
    
        public static class GetLoanCommonDetailsReqMsg
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd",
                "GetLoanCommonDetailsReqMsg",
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
                        * field for GetLoanCommonDetailsReq
                        */

                        
                                    protected GetLoanCommonDetailsReq_type0 localGetLoanCommonDetailsReq ;
                                

                           /**
                           * Auto generated getter method
                           * @return GetLoanCommonDetailsReq_type0
                           */
                           public  GetLoanCommonDetailsReq_type0 getGetLoanCommonDetailsReq(){
                               return localGetLoanCommonDetailsReq;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param GetLoanCommonDetailsReq
                               */
                               public void setGetLoanCommonDetailsReq(GetLoanCommonDetailsReq_type0 param){
                            
                                            this.localGetLoanCommonDetailsReq=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":GetLoanCommonDetailsReqMsg",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "GetLoanCommonDetailsReqMsg",
                           xmlWriter);
                   }

               
                   }
               
                                            if (localHeader==null){
                                                 throw new org.apache.axis2.databinding.ADBException("header cannot be null!!");
                                            }
                                           localHeader.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/common/header.xsd","header"),
                                               xmlWriter);
                                        
                                            if (localGetLoanCommonDetailsReq==null){
                                                 throw new org.apache.axis2.databinding.ADBException("GetLoanCommonDetailsReq cannot be null!!");
                                            }
                                           localGetLoanCommonDetailsReq.serialize(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd","GetLoanCommonDetailsReq"),
                                               xmlWriter);
                                        
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd")){
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
                                
                            elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd",
                                                                      "GetLoanCommonDetailsReq"));
                            
                            
                                    if (localGetLoanCommonDetailsReq==null){
                                         throw new org.apache.axis2.databinding.ADBException("GetLoanCommonDetailsReq cannot be null!!");
                                    }
                                    elementList.add(localGetLoanCommonDetailsReq);
                                

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
        public static GetLoanCommonDetailsReqMsg parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            GetLoanCommonDetailsReqMsg object =
                new GetLoanCommonDetailsReqMsg();

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
                    
                            if (!"GetLoanCommonDetailsReqMsg".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (GetLoanCommonDetailsReqMsg)ExtensionMapper.getTypeObject(
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
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd","GetLoanCommonDetailsReq").equals(reader.getName())){
                                
                                                object.setGetLoanCommonDetailsReq(GetLoanCommonDetailsReq_type0.Factory.parse(reader));
                                              
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
           
    
        public static class GetLoanCommonDetailsReq_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = GetLoanCommonDetailsReq_type0
                Namespace URI = http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd
                Namespace Prefix = ns8
                */
            

                        /**
                        * field for CID
                        */

                        
                                    protected java.lang.String localCID ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCIDTracker = false ;

                           public boolean isCIDSpecified(){
                               return localCIDTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCID(){
                               return localCID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CID
                               */
                               public void setCID(java.lang.String param){
                            localCIDTracker = param != null;
                                   
                                            this.localCID=param;
                                    

                               }
                            

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":GetLoanCommonDetailsReq_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "GetLoanCommonDetailsReq_type0",
                           xmlWriter);
                   }

               
                   }
                if (localCIDTracker){
                                    namespace = "http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd";
                                    writeStartElement(null, namespace, "CID", xmlWriter);
                             

                                          if (localCID==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("CID cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCID);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLoanAcctNoTracker){
                                    namespace = "http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd";
                                    writeStartElement(null, namespace, "LoanAcctNo", xmlWriter);
                             

                                          if (localLoanAcctNo==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("LoanAcctNo cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localLoanAcctNo);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd")){
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

                 if (localCIDTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd",
                                                                      "CID"));
                                 
                                        if (localCID != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCID));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("CID cannot be null!!");
                                        }
                                    } if (localLoanAcctNoTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd",
                                                                      "LoanAcctNo"));
                                 
                                        if (localLoanAcctNo != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLoanAcctNo));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("LoanAcctNo cannot be null!!");
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
        public static GetLoanCommonDetailsReq_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            GetLoanCommonDetailsReq_type0 object =
                new GetLoanCommonDetailsReq_type0();

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
                    
                            if (!"GetLoanCommonDetailsReq_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (GetLoanCommonDetailsReq_type0)ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd","CID").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"CID" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd","LoanAcctNo").equals(reader.getName())){
                                
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
           
    
        public static class LoanAcctNo
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd",
                "LoanAcctNo",
                "ns8");

            

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
                
                            java.lang.String namespace = "http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd";
                            java.lang.String _localName = "LoanAcctNo";
                        
                            writeStartElement(null, namespace, _localName, xmlWriter);

                            // add the type details if this is used in a simple type
                               if (serializeType){
                                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd");
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
            if(namespace.equals("http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd")){
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
                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.adcb.com/esb/inq/loan/InqLoanBasicInfo.xsd","LoanAcctNo").equals(reader.getName())){
                                
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
           
    
            private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqLoanBasicInfoStub.GetLoanCommonDetailsReqMsg param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.newgen.dscop.stub.InqLoanBasicInfoStub.GetLoanCommonDetailsReqMsg.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.newgen.dscop.stub.InqLoanBasicInfoStub.GetLoanCommonDetailsResMsg param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.newgen.dscop.stub.InqLoanBasicInfoStub.GetLoanCommonDetailsResMsg.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.newgen.dscop.stub.InqLoanBasicInfoStub.GetLoanCommonDetailsReqMsg param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.newgen.dscop.stub.InqLoanBasicInfoStub.GetLoanCommonDetailsReqMsg.MY_QNAME,factory));
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
        
                if (com.newgen.dscop.stub.InqLoanBasicInfoStub.GetLoanCommonDetailsReqMsg.class.equals(type)){
                
                           return com.newgen.dscop.stub.InqLoanBasicInfoStub.GetLoanCommonDetailsReqMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.newgen.dscop.stub.InqLoanBasicInfoStub.GetLoanCommonDetailsResMsg.class.equals(type)){
                
                           return com.newgen.dscop.stub.InqLoanBasicInfoStub.GetLoanCommonDetailsResMsg.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    
   }
   