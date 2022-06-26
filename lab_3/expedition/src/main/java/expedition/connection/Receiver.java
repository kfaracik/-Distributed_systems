package expedition.connection;

import com.rabbitmq.client.*;
import expedition.products.ProductController;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

import static expedition.connection.KeyGenerator.generateInfoKey;
import static expedition.data.ExchangeController.*;

public class Receiver {

    private final String exchangeName;
    @Setter
    private String name;
    private Channel channel;
    private Consumer consumer;
    @Getter
    private int receivedConfirm = 0;
    private Connection connection;

    public Receiver(String exchangeName, BuiltinExchangeType type) {
        this.exchangeName = exchangeName;

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST);
            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.exchangeDeclare(this.exchangeName, type);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void start(String queueKey) {
        try {
            String queueName = initQueue(queueKey, true);
            run(queueName, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startListening(String queueKey) {
        try {
            String queueName = initServerQueue(queueKey, false);
            run(queueName, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String initQueue(String queueKey, boolean confirm) throws IOException {
        // queue & bind
        String queueName = channel.queueDeclare(queueKey, false, false, false, null).getQueue();
        channel.queueBind(queueName, exchangeName, queueKey);
        System.out.println(getIdentifier(queueKey) + " created queue: " + queueName);

        // consumer (message handling)
        consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(
                    String consumerTag,
                    Envelope envelope,
                    AMQP.BasicProperties properties,
                    byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println(getIdentifier(queueKey) + "Received: " + message);

                if (Objects.equals(exchangeName, EXCHANGE_ORDER)) {
                    handleOrder(message, queueKey);
                } else {
                    // confirmed message
                    receivedConfirm++;
                    if (confirm) {
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                    return;
                }

                if (confirm) {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        return queueName;
    }

    private String initServerQueue(String queueKey, boolean confirm) throws IOException {
        // queue & bind
        String queueName = channel.queueDeclare(queueKey, false, false, false, null).getQueue();
        channel.queueBind(queueName, exchangeName, queueKey);
        System.out.println(getIdentifier(queueKey) + " created queue: " + queueName);

        consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(
                    String consumerTag,
                    Envelope envelope,
                    AMQP.BasicProperties properties,
                    byte[] body) {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println(getIdentifier(queueKey) + "Received: " + message);
            }
        };

        return queueName;
    }

    private void run(String queueName, int qos) throws IOException {
        channel.basicQos(qos);
        channel.basicConsume(queueName, false, consumer);
    }

    private void handleOrder(String message, String queueKey) {
        String[] parts = message.split(",");
        String customerTeamName = parts[0];
        String productToOrder = parts[1];
        String orderId = parts[2];

        try {
            int productionTime = ProductController.getProductionTime(productToOrder);
            Thread.sleep(productionTime);
            String reply = "Finished production " + productToOrder + " by " + name + " for " + customerTeamName + " id=" + orderId;
            System.out.println(getIdentifier(queueKey) + reply);

            Publisher publisher = new Publisher(EXCHANGE_INFO, BuiltinExchangeType.TOPIC);
            publisher.publish(reply, generateInfoKey(customerTeamName, FUNCTION_CONSUMER));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getIdentifier(String queueKey) {
        return "[" + exchangeName + " " + queueKey + "] ";
    }

    public void close() {
        try {
            if (channel.isOpen()) {
                channel.close();
            }
            if (connection.isOpen()) {
                connection.close();
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
