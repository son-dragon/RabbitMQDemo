package com.java1234.rabbitmq.work;

import com.java1234.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *rabbitmq-work工作队列模式：接收消息
 */
public class ConsumerOne {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1. 创建连接
        Connection connection = ConnectionUtil.getConnecion();

        // 2. 创建信道
        final Channel channel = connection.createChannel();

        // 3. 声明队列
        channel.queueDeclare(Producer.QUEUE_WORK,true,false,false,null);

        // 3.5：设置每次可以接受多少条消息
        channel.basicQos(1);

        // 4. 创建消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //由于多条消息，所以暂停1s
                try {
                    System.out.println("消费者1----消息主体：" + new String(body,"utf-8"));

                    Thread.sleep(1000);

                    /**
                     * 消息确认
                     * 参数1：消息id
                     * 参数2：false表示只有当前信息被处理
                     */
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        //因为需要持续监听，所以不关闭，改为监听队列
        channel.basicConsume(Producer.QUEUE_WORK, true,defaultConsumer);
    }
}
