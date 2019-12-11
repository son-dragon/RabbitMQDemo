package com.java1234.rabbitmq.simple;

import com.java1234.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq-简单模式：消费者接收消息
 */
public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1. 创建连接工厂
        // 2. 创建连接
        Connection connecion = ConnectionUtil.getConnecion();

        // 3. 创建信道
        Channel channel = connecion.createChannel();

        // 4. 声明队列
        channel.queueDeclare(Producer.QUEUE_NAME,true,false,false,null);

        // 5. 创建消费者（接收并处理消息）
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // 路由key
                System.out.println("路由key：" + envelope.getRoutingKey());
                // 交换机
                System.out.println("交换机：" + envelope.getExchange());
                // 消息id
                System.out.println("消息id：" + envelope.getDeliveryTag());
                // 接收到的消息
                System.out.println("消息为：" + new String(body,"utf-8"));
            }
        };

        // 6. 监听队列
        /**
         * 参数1：队列名
         * 参数2：消息是否自动确认
         * 参数3：消费者
         */
        channel.basicConsume(Producer.QUEUE_NAME,true,defaultConsumer);

        // 因为需要持续不断的监听，接收信息，所以不需要关闭
    }

}
