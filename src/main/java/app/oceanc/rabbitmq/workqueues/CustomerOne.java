package app.oceanc.rabbitmq.workqueues;

import app.oceanc.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Description: 消费者1
 * @Author bryan
 * @Date 2021/3/12 9:39 上午
 * @Version 1.0
 */
public class CustomerOne {
    public static void main(String[] args) {
        try {
            Connection connection = RabbitMqUtils.getConnection();

            Channel channel = connection.createChannel();
            channel.queueDeclare("work", true, false, false, null);
            channel.basicConsume("work", true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("消费者1--------->" + new String(body, "UTF-8"));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}