package com.java1234.rabbitmq.topic;

import com.java1234.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * topic(通配符)模式：消费者-接收消息
 */
public class ConsumerTwo {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnecion();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(Producer.TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);
        channel.queueDeclare(Producer.TOPIC_QUEUE_2, true, false, false, null);
        channel.queueBind(Producer.TOPIC_QUEUE_2, Producer.TOPIC_EXCHANGE, "java.#");
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("通配符接收者2号接收："+new String(body,"utf-8"));
            }
        };
        channel.basicConsume(Producer.TOPIC_QUEUE_2, true, defaultConsumer);
    }
}
