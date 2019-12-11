package com.java1234.rabbitmq.ps;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发布与订阅模式：发送消息
 */
public class Producer {
    // 交换机名称
    final static String FANOUT_EXCHANGE = "fanout_exchange";
    // 队列1名称
    final static String FANOUT_QUEUE_ONE = "fanout_queue_1";
    // 队列2名称
    final static String FANOUT_QUEUE_TWO = "fanout_queue_2";

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
        channel.exchangeDeclare(FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);


        channel.queueDeclare(FANOUT_QUEUE_ONE,true, false,false,null);
        channel.queueDeclare(FANOUT_QUEUE_TWO,true, false,false,null);

        // 5. 队列绑定到交换机
        /**
         * 参数1：队列名称
         * 参数2：交换机名称
         * 参数3：路由key
         */
        channel.queueBind(FANOUT_QUEUE_ONE, FANOUT_EXCHANGE, "");
        channel.queueBind(FANOUT_QUEUE_TWO, FANOUT_EXCHANGE, "");

        // 6. 发送消息
        for(int i=0;i<10;i++){
            String message = "你好，小兔纸发布订阅"+i+"号！！！";
            channel.basicPublish(FANOUT_EXCHANGE, "", null, message.getBytes());
            System.out.println("发送消息："+message);
        }

        channel.close();
        connection.close();

    }
}
