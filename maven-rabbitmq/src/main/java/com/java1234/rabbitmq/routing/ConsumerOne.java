package com.java1234.rabbitmq.routing;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq-路由模式：消费者
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

        channel.exchangeDeclare(Producer.DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);

        channel.queueDeclare(Producer.DIRECT_QUEUE_INSERT, true, false, false, null);

        channel.queueBind(Producer.DIRECT_QUEUE_INSERT, Producer.DIRECT_EXCHANGE, "insert");

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("路由模式消费者1号接收的消息主体：" + new String(body,"utf-8"));
            }
        };

        channel.basicConsume(Producer.DIRECT_QUEUE_INSERT, true, defaultConsumer);

    }
}
