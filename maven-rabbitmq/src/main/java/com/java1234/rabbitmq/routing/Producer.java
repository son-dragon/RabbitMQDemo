package com.java1234.rabbitmq.routing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 路由模式：发送消息
 */
public class Producer {
    // 交换机名称
    final static String DIRECT_EXCHANGE = "direct_exchange";
    // 队列1名称
    final static String DIRECT_QUEUE_INSERT = "direct_queue_insert";
    // 队列2名称
    final static String DIRECT_QUEUE_UPDATE = "direct_queue_update";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1. 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("shangzhijie");
        connectionFactory.setPassword("sj7644553");
        connectionFactory.setVirtualHost("/wqj");

        // 2. 创建连接
        Connection connection = connectionFactory.newConnection();

        // 3. 创建信道
        Channel channel = connection.createChannel();

        // 4. 创建交换机；参数1：交换机名称；参数2：交换机类型（fanout,direct,topic）
        channel.exchangeDeclare(DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);


        channel.queueDeclare(DIRECT_QUEUE_INSERT,true, false,false,null);
        channel.queueDeclare(DIRECT_QUEUE_UPDATE,true, false,false,null);

        // 5. 队列绑定到交换机
        /**
         * 参数1：队列名称
         * 参数2：交换机名称
         * 参数3：路由key
         */
        channel.queueBind(DIRECT_QUEUE_INSERT, DIRECT_EXCHANGE, "insert");
        channel.queueBind(DIRECT_QUEUE_UPDATE, DIRECT_EXCHANGE, "update");

        // 6. 发送消息
        String insertMessage = "你好，小兔纸在路由模式下做insert操作！！！";
        String updateMessage = "你好，小兔纸在路由模式下做update操作！！！";

        channel.basicPublish(DIRECT_EXCHANGE, "insert", null, insertMessage.getBytes());
        System.out.println("已发送消息：" + insertMessage);
        channel.basicPublish(DIRECT_EXCHANGE, "update", null, updateMessage.getBytes());
        System.out.println("已发送消息：" + updateMessage);

        channel.close();
        connection.close();

    }
}
