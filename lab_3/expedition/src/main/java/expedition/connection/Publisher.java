package expedition.connection;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import static expedition.data.ExchangeController.HOST;


public class Publisher {

    private Channel channel;
    private String exchangeName;
    private BuiltinExchangeType exchangeType;

    public Publisher(String exchangeName, BuiltinExchangeType type) {
        this.exchangeName = exchangeName;
        this.exchangeType = type;

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST);
            Connection connection = factory.newConnection();
            channel = connection.createChannel();

            channel.exchangeDeclare(exchangeName, type);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void publish(String message, String key) {
        try {
            channel.basicPublish(exchangeName, key, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(getIdentifier(key) + "Sent: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Contract(pure = true)
    private @NotNull String getIdentifier(String queueKey) {
        return "[" + exchangeName + "." + queueKey + "] ";
    }

    public void close() {
        try {
            if (channel.isOpen()) {
                channel.close();
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
