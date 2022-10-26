package com.jml.mq.test;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class PublisherTest {

    private final static String SIMPLE_QUEUE_NAME = "simple.queue";

    @Test
    public void testSendMessage() throws IOException, TimeoutException {
        //建立连接
        ConnectionFactory factory = new ConnectionFactory();
        //设置连接参数、分别是：主机ip、vhost、端口号、用户名、密码
        factory.setHost("192.168.158.100");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("root");
        factory.setPassword("123456");
        //建立连接
        Connection connection = factory.newConnection();
        //创建通道Channel
        Channel channel = connection.createChannel();
        //创建队列
        channel.queueDeclare(SIMPLE_QUEUE_NAME, false, false, false, null);
        //发送消息
        String message = "hello，rabbitmq!";
        channel.basicPublish("",SIMPLE_QUEUE_NAME,null,message.getBytes());

        System.out.println("发送消息成功：["+ message +"]");
        //关闭通道和连接
        channel.close();
        connection.close();
    }


    private final static String WORK_QUEUE_NAME = "work.queue";

    @Test
    public void testWorkSendMessage() throws IOException, TimeoutException {
        //建立连接
        ConnectionFactory factory = new ConnectionFactory();
        //设置连接参数、分别是：主机ip、vhost、端口号、用户名、密码
        factory.setHost("192.168.158.100");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("root");
        factory.setPassword("123456");
        //建立连接
        Connection connection = factory.newConnection();
        //创建通道Channel
        Channel channel = connection.createChannel();
        //创建队列
        channel.queueDeclare(WORK_QUEUE_NAME, false, false, false, null);
        //发送消息
        String message = "hello，rabbitmq!";
        channel.basicPublish("",WORK_QUEUE_NAME,null,message.getBytes());

        System.out.println("发送消息成功：["+ message +"]");
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
