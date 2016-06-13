package com.hjc.activemq.demo;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {
	private static final int SEND_NUMBER = 5;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//连接工厂，JMS用它创建连接
		ConnectionFactory connectionFactory;
		//JMS客户端到JMS Provider的连接
		Connection connection = null;
		//一个发送或接收消息的线程
		Session session;
		//消息的目的地；消息发送给谁
		Destination destination;
//		消息发送者
		MessageProducer producer;
//		构造ConnectionFactory实例对象
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, 
				ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
		try{
//			构造从工厂得到连接对象
			connection  = connectionFactory.createConnection();
//			启动
			connection.start();
//			获取操作连接
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
//			获取session注意参数值FirstQueue是一个服务器的queue，需在ActiveMq的console配置
			destination = session.createQueue("FirstQueue");
//			得到消息生成者【发送者】
			producer = session.createProducer(destination);
//			设置不持久化，实际根据项目决定
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			sendMessage(session,producer);
			session.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try{
				if(null != connection){
					connection.close();
				}
			}catch(Throwable ignore){
				
			}
		}
	}
	
	public static void sendMessage(Session session,MessageProducer producer)throws Exception{
		for(int i = 1;i<SEND_NUMBER;i++){
			TextMessage message = session.createTextMessage("ActiveMq发送的消息"+i+" 2");
			System.out.println("发送消息："+"ActiveMq发送的消息"+i+" 2");
			producer.send(message);
		}
	}
}
