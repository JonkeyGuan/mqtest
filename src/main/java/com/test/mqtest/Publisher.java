package com.test.mqtest;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Publisher {
	private String brokerUrl="tcp://localhost:61616";//8182
	
	private Connection connection;
	private ActiveMQConnectionFactory factory;
	private Session session;
	private MessageProducer producer;
	private Destination destination;
	
	public void send() throws Exception{  
        factory = new ActiveMQConnectionFactory(brokerUrl);  
  
        connection = factory.createConnection("admin","admin"); 
        try {  
	        connection.start();  
	        session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);  
	        destination=session.createTopic("VirtualTopic.TEST");
//	        destination=session.createTopic("topic-0224");
	        producer = session.createProducer(destination);
	        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
	        sendMessage(session,producer);
	        session.commit();
        }catch (JMSException jmse) {  //Not a transacted session
            connection.close();  
            throw jmse;  
        } finally {
        	connection.close();
        }
    }
	private void sendMessage(Session session, MessageProducer producer) throws Exception {
		TextMessage message=session.createTextMessage("activemq send virtual topic message");
		message.setIntProperty("i", 5);
		producer.send(message);
	}  
	
	public static void main(String[] args){
		Publisher publisher=new Publisher();
		try {
			publisher.send();
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		
	}
}
