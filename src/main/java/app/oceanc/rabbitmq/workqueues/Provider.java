package app.oceanc.rabbitmq.workqueues;

import app.oceanc.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @Description: 生产者
 * @Author bryan
 * @Date 2021/3/12 9:30 上午
 * @Version 1.0
 */
public class Provider {
    public static void main(String[] args) {
        try {
            Connection connection = RabbitMqUtils.getConnection();

            Channel channel = connection.createChannel();

            channel.queueDeclare("work", true, false, false, null);
            for (int i = 1; i <= 20; i++) {
                channel.basicPublish("", "work", null, (i + "work 队列消息").getBytes());
            }
            RabbitMqUtils.closeChannelAndConn(channel, connection);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}