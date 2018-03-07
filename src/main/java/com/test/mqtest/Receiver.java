package com.test.mqtest;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class Receiver {
	private String brokerUrl="tcp://localhost:61616";


	
	private Connection connection;
	private ConnectionFactory factory;
	private Session session;

	public void reveive() throws Exception{  
		factory = new ActiveMQConnectionFactory("admin","admin",brokerUrl);
        connection = factory.createConnection(); 

        try {  
	        connection.start();  
	        
	        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);  
	        Queue topicA=new ActiveMQQueue("Consumer.A.VirtualTopic.TEST");
	        Queue topicB=new ActiveMQQueue("Consumer.B.VirtualTopic.TEST");
	     // 消费者A组创建订阅  
	        MessageConsumer consumerA1 = session.createConsumer(topicA);  
	        consumerA1.setMessageListener(new MessageListener() {  
	            // 订阅接收方法  
	            public void onMessage(Message message) {  
	                TextMessage tm = (TextMessage) message;  
	                try {  
	                    System.out.println("Received message A1: " + tm.getText()+":"+tm.getStringProperty("property"));  
	                } catch (JMSException e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	        });  
	          
	        MessageConsumer consumerA2 = session.createConsumer(topicA);  
	        consumerA2.setMessageListener(new MessageListener() {  
	            // 订阅接收方法  
	            public void onMessage(Message message) {  
	                TextMessage tm = (TextMessage) message;  
	                try {  
	                    System.out.println("Received message A2: " + tm.getText()+":"+tm.getStringProperty("property"));  
	                } catch (JMSException e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	        });  
	          
	        //消费者B组创建订阅  
	        MessageConsumer consumerB1 = session.createConsumer(topicB);  
	        consumerB1.setMessageListener(new MessageListener() {  
	            // 订阅接收方法  
	            public void onMessage(Message message) {  
	                TextMessage tm = (TextMessage) message;  
	                try {  
	                    System.out.println("Received message B1: " + tm.getText()+":"+tm.getStringProperty("property"));  
	                } catch (JMSException e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	        });  
	        MessageConsumer consumerB2 = session.createConsumer(topicB);  
	        consumerB2.setMessageListener(new MessageListener() {  
	            // 订阅接收方法  
	            public void onMessage(Message message) {  
	                TextMessage tm = (TextMessage) message;  
	                try {  
	                    System.out.println("Received message B2: " + tm.getText()+":"+tm.getStringProperty("property"));  
	                } catch (JMSException e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	        });  
        }catch (JMSException jmse) {  
            connection.close();  
            throw jmse;  
        }  
    }
	
	public static void main(String[] args){
		Receiver receiver=new Receiver();
		try {
			receiver.reveive();
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		
	}
}

