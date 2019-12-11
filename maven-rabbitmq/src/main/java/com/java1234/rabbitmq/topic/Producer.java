package com.java1234.rabbitmq.topic;

import com.java1234.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 通配符（topic）模式：生产者-发送消息
 */
public class Producer {
    final static String TOPIC_EXCHANGE = "topic_exchange";
    final static String TOPIC_QUEUE_1 = "topic_queue_1";
    final static String TOPIC_QUEUE_2 = "topic_queue_2";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnecion();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);

        channel.queueDeclare(TOPIC_QUEUE_1, true, false, false, null);
        channel.queueDeclare(TOPIC_QUEUE_2, true, false, false, null);

        /**
         * 统配符规则：*代表一个，#代表多个
         */
        channel.queueBind(TOPIC_QUEUE_1, TOPIC_EXCHANGE, "java.*");
        channel.queueBind(TOPIC_QUEUE_2, TOPIC_EXCHANGE, "java.#");

        String messageOne = "你好，小兔纸测试数据1号";
        channel.basicPublish(TOPIC_EXCHANGE, "java.insert", null, messageOne.getBytes());
        System.out.println("测试1号发送："+messageOne);

        String messageTwo = "你好，小兔纸测试数据2号";
        channel.basicPublish(TOPIC_EXCHANGE, "java.delete", null, messageTwo.getBytes());
        System.out.println("测试2号发送："+messageTwo);

        String messageThree = "你好，小兔纸测试数据3号";
        channel.basicPublish(TOPIC_EXCHANGE, "java.update.name", null, messageThree.getBytes());
        System.out.println("测试3号发送："+messageThree);

        channel.close();
        connection.close();
    }

}
