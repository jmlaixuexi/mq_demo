package com.jml.mq.listener;

import javafx.collections.MapChangeListener;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class SpringRabbitListener {


    /**
     * 1 "Hello World!"
     * The simplest thing that does something
     */
    //最简单的操作，一对一生产和消费
    private final static String SIMPLE_QUEUE_NAME = "simple.queue";
    @RabbitListener(queues = SIMPLE_QUEUE_NAME)
    public void listenSimpleQueue(String msg){
        System.out.println("消费者接收到"+ SIMPLE_QUEUE_NAME +"的消息：["+ msg+"]");
    }

    /**
     * 2 Work queues
     * Distributing tasks among workers (the competing consumers pattern)
     */
    // 工作队列
    //在工作线程之间分配任务（竞争消费者模式）
    private final static String WORK_QUEUE_NAME = "work.queue";
    @RabbitListener(queues = WORK_QUEUE_NAME)
    public void listenWorkQueue(String msg) throws InterruptedException {
        System.out.println("消费者接收到"+ WORK_QUEUE_NAME +"的消息：["+ msg+"]。消费时间：" + LocalDateTime.now());
        Thread.sleep(200);
    }

    /**
     * 3 Publish/Subscribe
     * Sending messages to many consumers at once
     */
    //发布/订阅（Publish/Subscribe）FanoutExchange
    //一次向多个使用者发送消息
    //订阅消费者1实现
    private final static String FANOUT_QUEUE1_NAME = "fanout.queue1";
    @RabbitListener(queues = FANOUT_QUEUE1_NAME)
    public void listenFanoutQueue1(String msg) throws InterruptedException {
        System.out.println("消费者1接收到"+ FANOUT_QUEUE1_NAME +"的消息：["+ msg+"]。消费时间：" + LocalDateTime.now());
    }

    //发布/订阅（Publish/Subscribe）FanoutExchange
    //一次向多个使用者发送消息
    //订阅消费者2实现
    private final static String FANOUT_QUEUE2_NAME = "fanout.queue2";
    @RabbitListener(queues = FANOUT_QUEUE2_NAME)
    public void listenFanoutQueue2(String msg) throws InterruptedException {
        System.out.println("消费者2接收到"+ FANOUT_QUEUE2_NAME +"的消息：["+ msg+"]。消费时间：" + LocalDateTime.now());
    }



    /**
     * 4 Routing
     * Receiving messages selectively
     */
    //指定消费（Routing）DirectExchange
    //有选择地接收消息
    //订阅消费者1现
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue1"),
            exchange = @Exchange(name = "jml.direct",type = ExchangeTypes.DIRECT),
            key = {"red","blue"}
    ))
    public void listenDirectQueue1(String msg) throws InterruptedException {
        System.out.println("消费者1接收到direct.queue1的消息：["+ msg+"]。消费时间：" + LocalDateTime.now());
    }

    //指定消费（Routing）DirectExchange
    //有选择地接收消息
    //订阅消费者1现
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue2"),
            exchange = @Exchange(name = "jml.direct",type = ExchangeTypes.DIRECT),
            key = {"red","yellow"}
    ))
    public void listenDirectQueue2(String msg) throws InterruptedException {
        System.out.println("消费者2接收到direct.queue2的消息：["+ msg+"]。消费时间：" + LocalDateTime.now());
    }


    /**
     * 5 Topics
     * Receiving messages based on a pattern (topics)
     */
    //指定消费（Topics）TopicExchange
    //根据模式接收消息（主题）
    //支持通配符 # 0个或者多个单词，* 一个单词
    //订阅消费者1现
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue1"),
            exchange = @Exchange(name = "jml.topic",type = ExchangeTypes.TOPIC),
            key = "china.#"
    ))
    public void listenTopicQueue1(String msg) throws InterruptedException {
        System.out.println("消费者1接收到topic.queue1的消息：["+ msg+"]。消费时间：" + LocalDateTime.now());
    }

    //指定消费（Topics）TopicExchange
    //根据模式接收消息（主题）
    //订阅消费者1现
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue2"),
            exchange = @Exchange(name = "jml.topic",type = ExchangeTypes.TOPIC),
            key = "#.news"
    ))
    public void listenTopicQueue2(String msg) throws InterruptedException {
        System.out.println("消费者2接收到topic.queue2的消息：["+ msg+"]。消费时间：" + LocalDateTime.now());
    }

    /**
     *6 RPC
     * Request/reply pattern example
     */
    //远程过程控制
    //请求/回复模式 这些例子

    /**
     * 7 Publisher Confirms
     * Reliable publishing with publisher confirms
     */
    //发布者确认
    //发布者确认的可靠发布

    /**
     * SpringAmqp序列化以及反序列化实现
     * 1、引入jackson-databind依赖
     * 2、声明MessageConverter对象
     */
    private final static String OBJECT_QUEUE_NAME = "object.queue";
    @RabbitListener(queues = OBJECT_QUEUE_NAME)
    public void listenObjectQueue(Map<String,Object> msg){
        System.out.println("消费者接收到"+ OBJECT_QUEUE_NAME +"的消息：["+ msg+"]");
    }
}
