
<%@page import="javax.naming.InitialContext, javax.jms.Queue, javax.jms.Session, javax.jms.TextMessage, javax.jms.QueueSession, javax.jms.QueueSender, javax.jms.DeliveryMode, javax.jms.QueueReceiver,javax.jms.QueueConnection, javax.jms.QueueConnectionFactory"%>
<%

try
{
	InitialContext ctx = new InitialContext();
	
	out.println("Read InitialContext 1<br>");
	Queue queue = (Queue) ctx.lookup("jms/queuetffcubs_notify");	
	QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx.lookup("jms/qcftffcubs_notify");	
    QueueConnection queueConn = connFactory.createQueueConnection();
    QueueSession queueSession = queueConn.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
    QueueReceiver queueReceiver = queueSession.createReceiver(queue);
    queueConn.start();
    TextMessage message = (TextMessage) queueReceiver.receive();
    out.println("received: from listener <br> <h1>" + message.getText() + "<h1><br> <br><br><br>");
    queueConn.close();
}catch(Exception e){
		e.printStackTrace();
}

%>
