package com.jml.mq.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringAmqpTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    //简单操作
    private final static String SIMPLE_QUEUE_NAME = "simple.queue";

    @Test
    public void testSendMessage2SimpleQueue() {
        String message = "hello,spring ampq!";
        rabbitTemplate.convertAndSend(SIMPLE_QUEUE_NAME,message);
    }

    //在工作线程之间分配任务（竞争消费者模式）
    private final static String WORK_QUEUE_NAME = "work.queue";//

    @Test
    public void testSendMessage2WorkQueue() throws InterruptedException {
        String message = "hello,spring ampq!---";
        for (int i = 0; i < 50; i++){
            rabbitTemplate.convertAndSend(WORK_QUEUE_NAME,message + i);
            Thread.sleep(20);
        }
    }


    @Test
    public void testFanoutExchange() throws InterruptedException {
        //交换机名称
        String exchange = "jml.fanout";
        //消息
        String message = "hello,every one!";
        //参数分别为：交换机、RoutingKey、消息
        rabbitTemplate.convertAndSend(exchange,"",message);
    }

    @Test
    public void testDirectExchange() throws InterruptedException {
        //交换机名称
        String exchange = "jml.direct";

        String colors[] = {"red","blue","yellow"};

        for (int i = 0; i < 50; i++) {
            //消息
            String message = "hello," + colors[i%3] + "!";
            //参数分别为：交换机、RoutingKey、消息
            rabbitTemplate.convertAndSend(exchange,colors[i%3],message);
        }
    }


    @Test
    public void testTopicExchange() throws InterruptedException {
        //交换机名称
        String exchange = "jml.topic";

        String colors[] = {"china.news","china.weather"};

        for (int i = 0; i < 50; i++) {
            //消息
            String message = "hello," + colors[i%2] + "!";
            //参数分别为：交换机、RoutingKey、消息
            rabbitTemplate.convertAndSend(exchange,colors[i%2],message);
        }
    }

    /**
     * 消息体发送object
     * @throws InterruptedException
     */
    @Test
    public void testSendObjectQueue() throws InterruptedException {
        Map<String,Object> map = new HashMap<>();
        map.put("name","张三");
        map.put("age","20");
        //参数分别为：交换机、RoutingKey、消息
        rabbitTemplate.convertAndSend("object.queue",map);
    }
}

