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
		
		//���ӹ�����JMS������������
		ConnectionFactory connectionFactory;
		//JMS�ͻ��˵�JMS Provider������
		Connection connection = null;
		//һ�����ͻ������Ϣ���߳�
		Session session;
		//��Ϣ��Ŀ�ĵأ���Ϣ���͸�˭
		Destination destination;
//		��Ϣ������
		MessageProducer producer;
//		����ConnectionFactoryʵ������
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, 
				ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
		try{
//			����ӹ����õ����Ӷ���
			connection  = connectionFactory.createConnection();
//			����
			connection.start();
//			��ȡ��������
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
//			��ȡsessionע�����ֵFirstQueue��һ����������queue������ActiveMq��console����
			destination = session.createQueue("FirstQueue");
//			�õ���Ϣ�����ߡ������ߡ�
			producer = session.createProducer(destination);
//			���ò��־û���ʵ�ʸ�����Ŀ����
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
			TextMessage message = session.createTextMessage("ActiveMq���͵���Ϣ"+i+" 2");
			System.out.println("������Ϣ��"+"ActiveMq���͵���Ϣ"+i+" 2");
			producer.send(message);
		}
	}
}
