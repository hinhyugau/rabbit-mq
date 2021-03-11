package app.oceanc.rabbitmq.hello;

import app.oceanc.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description: 提供方
 * @Author bryan
 * @Date 2021/3/11 11:38 上午
 * @Version 1.0
 */
public class Provider {
    public static void main(String[] args) throws IOException, TimeoutException {
        /*//创建连接mq的连接工厂对象
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置rabbitmq主机
        connectionFactory.setHost("43.128.2.172");
        //设置端口号
        connectionFactory.setPort(5777);
        //设置所连接的虚拟主机
        connectionFactory.setVirtualHost("ems");
        //设置访问虚拟主机的账号密码
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");*/

        //获取连接对象
        Connection connection = RabbitMqUtils.getConnection();

        //获取连接冲的通道
        Channel channel = connection.createChannel();
        //通道绑定对应详细队列
        //参数1: 队列名称 如果队列不存在则自动创建
        //参数2: 用来定义队列特性是否要持久化 true 持久化队列 false不持久化  如果不做持久化重启rabbit-server此队列就没了
        //参数3: exclusive是否独占队列 true 独占队列 false 不独占
        //参数4: autoDelete: 是否在消费完成后自动删除队列, true 自动删除 false 不自动删除
        //参数5: 额外附加参数
        channel.queueDeclare("aa", true, false, false, null);

        //发布消息
        //参数1: 交换机名称
        //参数2: 队列名称
        //参数3: 传递消息额外设置
        //参数4: 消息的具体内容
        channel.basicPublish("", "aa", MessageProperties.PERSISTENT_TEXT_PLAIN, "这是持久化消息测试".getBytes());
        RabbitMqUtils.closeChannelAndConn(channel, connection);
    }
}
