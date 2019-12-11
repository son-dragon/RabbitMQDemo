package com.java1234.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtil {

    // 创建连接
    public static Connection getConnecion() throws IOException, TimeoutException {
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
        return connection;
    }
}
