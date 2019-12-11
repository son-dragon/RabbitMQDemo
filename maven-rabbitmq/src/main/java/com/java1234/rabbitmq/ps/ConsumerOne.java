package com.java1234.rabbitmq.ps;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq-发布与订阅模式：消费者
 */
public class ConsumerOne {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("shangzhijie");
        connectionFactory.setPassword("sj7644553");
        connectionFactory.setVirtualHost("/wqj");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Producer.FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);

        channel.queueDeclare(Producer.FANOUT_QUEUE_ONE, true, false, false, null);

        channel.queueBind(Producer.FANOUT_QUEUE_ONE, Producer.FANOUT_EXCHANGE, "");

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("发布与订阅消费者1号接收的消息主体：" + new String(body,"utf-8"));
            }
        };

        channel.basicConsume(Producer.FANOUT_QUEUE_ONE, true, defaultConsumer);

    }
}
