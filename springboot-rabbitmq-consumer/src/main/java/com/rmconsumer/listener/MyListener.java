package com.rmconsumer.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息监听器
 */
@Component
public class MyListener {

    /**
     * 接收队列消息
     * @param message 队列接收到的消息
     */
    @RabbitListener(queues = "item_queue")
    public void myListener(String message){
        System.out.println("消费者接收到消息：" + message);
    }
}
