package com.jt.test.rabbitmq;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class TestRabbitMQ {
	/**
	 * 如何连接rabbitMQ
	 * 1.获取连接的Connection
	 * 2.利用工厂模式获取Connection
	 * 3.需要为工厂模式设定连接的参数
	 * 	 host/端口/用户名/密码/虚拟主机
	 */
	
	private  Connection connection;
	
	private String queueName = "simple";//简单模式
	
	/** 初始化Connection参数 
	 * @throws IOException */
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
	
	
	/**
	 * 定义消息提供者  
	 * 步骤：
	 * 	1.定义连接通道Channel
	 * 	2.定义队列
	 * 	3.定义发送消息内容
	 * 	4.将消息发送到队列中
	 * @throws IOException 
	 */
	@Test
	public void provider() throws IOException{
		Channel channel  = connection.createChannel();
		/**
		 * 参数介绍：
		 * queue：队列名称
		 * durable：是否持久化
		 * exclusive：是否由提供者独有,如果该参数为true,那么消费者将不能消费
		 * autoDelete：当消息队列中没有消息时,是否自动删除队列
		 * arguments：传递的参数,如果没有参数一般为null
		 */
		channel.queueDeclare(queueName, false, false, false, null);
		String msg = "我是一个简单模式";
		
		/**
		 * 参数介绍：
		 * exchange：交换机,作用：可以将消息发送到不同的队列中
		 * routingKey：标识消息发往哪个队列
		 * props：消息队列中的配置文件properties,一般为null
		 * body：消息二进制文件
		 */
		channel.basicPublish("", queueName, null, msg.getBytes());
		
		channel.close();
		connection.close();
		System.out.println("消息发送完成");
		
	}
	
	
	/**
	 * 1.创建通道
	 * 2.创建队列
	 * 3.创建消费者对象
	 * 4.将消费者对象与队列进行绑定
	 * 5.从队列中获取消息
	 */
	@Test
	public void consumer() throws Exception{
		//1.创建通道
		Channel channel  = connection.createChannel();
		//2.创建队列
		channel.queueDeclare(queueName, false, false, false, null);
		//3.创建消费者对象
		QueueingConsumer consumer = new QueueingConsumer(channel);
		//4.将消费者对象与队列进行绑定
		channel.basicConsume(queueName, true, consumer);
		//5.从队列中获取消息
		while(true){
			Delivery delivery = consumer.nextDelivery();
			System.out.println("获取消息："+new String(delivery.getBody()));
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
