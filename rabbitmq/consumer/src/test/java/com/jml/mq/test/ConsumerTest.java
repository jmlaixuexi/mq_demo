package com.jml.mq.test;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerTest {

    private final static String QUEUE_NAME = "hello";

    @Test
    public void testPullMessage() throws IOException, TimeoutException {
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
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //订阅消息
        channel.basicConsume(QUEUE_NAME,true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //处理消息
                String message = new String(body);
                System.out.println("接收到消息：["+ message +"]");
            }
        });
        System.out.println("等待接收消息。。。。。");
    }
}
