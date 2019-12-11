package com.java1234.rabbitmq.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq-work工作队列模式：发送消息
 */
public class Producer {
    final static String QUEUE_WORK = "work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("shangzhijie");
        connectionFactory.setPassword("sj7644553");
        connectionFactory.setVirtualHost("/wqj");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_WORK,true,false,false,null);

        for (int i = 0;i < 30;i++){
            String message = "你好，小兔纸" + i + "号";
            channel.basicPublish("",QUEUE_WORK,null,message.getBytes());
            System.out.println("发送消息为："+message);
        }

        channel.close();
        connection.close();


    }

}
