package app.oceanc.rabbitmq.hello;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description: 消费者
 * @Author bryan
 * @Date 2021/3/11 2:08 下午
 * @Version 1.0
 */
public class Customer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("43.128.2.172");
        connectionFactory.setPort(5777);
        connectionFactory.setVirtualHost("ems");
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();
        //通道绑定对应详细队列
        //参数1: 队列名称 如果队列不存在则自动创建
        //参数2: 用来定义队列特性是否要持久化 true 持久化队列 false不持久化
        //参数3: exclusive是否独占队列 true 独占队列 false 不独占
        //参数4: autoDelete: 是否在消费完成后自动删除队列, true 自动删除 false 不自动删除
        //参数5: 额外附加参数
        channel.queueDeclare("hello", false, false, false, null);
        //参数1: 消费哪个队列的消息 队列名称
        //参数2: 开始消息的自动确认机制
        //参数3: 消费消息时的回掉接口
        channel.basicConsume("hello", true, new DefaultConsumer(channel) {
            //
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body)
                    throws IOException {
                String s = new String(body, "utf-8");
                System.out.println(s);
            }
        });
        //如果不关闭会一直监听
        //channel.close();
        //connection.close();
    }
}
