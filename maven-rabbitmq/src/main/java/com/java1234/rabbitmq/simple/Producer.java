package com.java1234.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq简单模式：发送消息
 */
public class Producer {
    // 设置队列名称
    static final String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1. 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
            // 1.1: 设置主机
            connectionFactory.setHost("127.0.0.1");
            // 1.1: 设置端口
            connectionFactory.setPort(5672);
            // 1.1: 设置用户名
            connectionFactory.setUsername("shangzhijie");
            // 1.1: 设置密码
            connectionFactory.setPassword("sj7644553");
            // 1.1: 设置虚拟主机
            connectionFactory.setVirtualHost("/wqj");

        // 2. 创建连接
        Connection connection = connectionFactory.newConnection();

        // 3. 创建信道
        Channel channel = connection.createChannel();

        // 4. 声明队列
        /**
         * 参数1：队列名称
         * 参数2：指定是否定义持久化
         * 参数3：指定是否独占本连接
         * 参数4：指定是否在不使用时自动删除
         * 参数5：其它参数
         */
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);

        // 5. 发送消息
        String message = "你好，小兔纸！！！";
        /**
         * 参数1：交换机名称，如果没有则指定空字符串（表示使用默认的）
         * 参数2：路由key，简单模式中使用队列名称
         * 参数3：消息的其它属性
         * 参数4：消息主题（消息内容）
         */
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("发送消息：" + message);
        // 6. 关闭资源
        channel.close();
        connection.close();
    }
}
