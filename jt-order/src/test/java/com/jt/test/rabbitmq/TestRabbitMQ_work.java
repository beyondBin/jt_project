package com.jt.test.rabbitmq;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class TestRabbitMQ_work {
	
	
	private  Connection connection;
	
	private String queueName = "work";//工作模式
	
	/** 初始化Connection参数  */
	@Before
	public void init() throws IOException{
		ConnectionFactory factory = new ConnectionFactory();
		//	host/端口/用户名/密码/虚拟主机
		factory.setHost("192.168.220.141");
		factory.setPort(5672);
		factory.setUsername("jtadmin");
		factory.setPassword("jtadmin");
		factory.setVirtualHost("/jt");
		connection = factory.newConnection();
	}
	
	
	@Test
	public void provider() throws IOException{
		Channel channel  = connection.createChannel();
		channel.queueDeclare(queueName, false, false, false, null);
		String msg = "我是工作模式";
		channel.basicPublish("", queueName, null, msg.getBytes());
		channel.close();
		connection.close();
		System.out.println("消息发送完成");
	}
	
	
	//定义第一个消费者
	@Test
	public void consumerA() throws Exception{
		Channel channel = connection.createChannel();
		channel.queueDeclare(queueName, false, false, false, null);
		//设定每个消费者每次消费的个数,如果消息长时间没有接收到ack消息,则记录消费失败
		//如果队列连续3次没有接收到ACK返回消息,则不允许该消费者继续消费
		channel.basicQos(1);
		QueueingConsumer consumer = new QueueingConsumer(channel);
		//false表示手动返回
		channel.basicConsume(queueName, false, consumer);
		System.out.println("消费者A启动完成...");
		while(true){
			Delivery delivery = consumer.nextDelivery();
			System.out.println("获取消息："+new String(delivery.getBody()));
			/**
			 * deliveryTag：返回数据的下标
			 * multiple：是否批量返回
			 */
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), true);
		}
		
	}
	
	
	
	@Test
	public void consumerB() throws Exception {
		Channel channel = connection.createChannel();
		channel.queueDeclare(queueName, false, false, false, null);
		//设定每个消费者每次消费的个数,如果消息长时间没有接收到ack消息,则记录消费失败
		//如果队列连续3次没有接收到ACK返回消息,则不允许该消费者继续消费
		channel.basicQos(1);
		QueueingConsumer consumer = new QueueingConsumer(channel);
		//false表示手动返回
		channel.basicConsume(queueName, false, consumer);
		System.out.println("消费者B启动完成...");
		while(true){
			Delivery delivery = consumer.nextDelivery();
			System.out.println("获取消息："+new String(delivery.getBody()));
			/**
			 * deliveryTag：返回数据的下标
			 * multiple：是否批量返回
			 */
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), true);
		}
		
	}
	
}
