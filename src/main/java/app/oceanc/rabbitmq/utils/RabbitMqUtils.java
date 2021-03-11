package app.oceanc.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sun.tools.javac.util.Assert;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description: RabbitMqUtils
 * @Author bryan
 * @Date 2021/3/11 2:51 下午
 * @Version 1.0
 */
public class RabbitMqUtils {
    private static ConnectionFactory connectionFactory;

    static {
        connectionFactory = new ConnectionFactory();
        //重量级资源没必要使用一次创建一次,类加载执行只执行一次
        //设置连接主机
        connectionFactory.setHost("43.128.2.172");
        //设置端口
        connectionFactory.setPort(5777);
        //设置虚拟主机名
        connectionFactory.setVirtualHost("ems");
        //设置访问虚拟主机名
        connectionFactory.setUsername("ems");
        //设置访问虚拟主机端口
        connectionFactory.setPassword("123");
    }

    /**
     * 获取连接
     *
     * @return
     */
    public static Connection getConnection() {
        try {

            return connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭通道
     *
     * @param channel
     * @param connection
     */
    public static void closeChannelAndConn(Channel channel, Connection connection) {
        Assert.checkNonNull(channel, "NOT NULL");
        Assert.checkNonNull(connection, "NOT NULL");
        try {
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}