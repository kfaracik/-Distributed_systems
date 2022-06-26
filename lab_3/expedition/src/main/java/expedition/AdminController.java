package expedition;

import com.rabbitmq.client.BuiltinExchangeType;
import expedition.connection.Publisher;
import expedition.connection.Receiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static expedition.data.ExchangeController.*;


public class AdminController {

    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        startExchangeOrderListening();
        startExchangeInfoListening();
        startMsgListener();
    }

    private static void startExchangeOrderListening() {
        Receiver orderReceiver = new Receiver(EXCHANGE_ORDER, BuiltinExchangeType.TOPIC);
        orderReceiver.setName("Admin");
        orderReceiver.startListening("#");
    }

    private static void startExchangeInfoListening() {
        Receiver orderReceiver = new Receiver(EXCHANGE_INFO, BuiltinExchangeType.TOPIC);
        orderReceiver.setName("Admin");
        orderReceiver.startListening("#");
    }

    private static void startMsgListener() {
        messageListen();
        String message;
        int messageType;
        System.out.println("\n***\nSERVER HAS STARTED\n***\n");
        while (true) {
            System.out.println("Send message to: [0 customers, 1 producers, 2 all]");
            messageType = Integer.parseInt(readLine());
            Publisher publisher = new Publisher(EXCHANGE_INFO, BuiltinExchangeType.TOPIC);

            System.out.println("Message:");
            message = readLine();

            if (messageType == 0) {
                publisher.publish(message, EXCHANGE_INFO + SEPARATOR + FUNCTION_CONSUMER + SEPARATOR + "*");
            } else if (messageType == 1) {
                // TODO create
                publisher.publish(message, EXCHANGE_INFO + SEPARATOR + FUNCTION_PRODUCER + SEPARATOR + "*");
            } else if (messageType == 2) {
                publisher.publish(message, EXCHANGE_INFO + SEPARATOR + "#");
            } else {
                System.err.println("Provided number is incorrect!");
                return;
            }
        }
    }

    private static String readLine() {
        String input = "";
        try {
            input = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

    private static void messageListen() {
        String message;
        int messageType;
        System.out.println("\n***\nSERVER HAS STARTED\n***\n");
        while (true) {
            System.out.println("Send message to: [0 customers, 1 producers, 2 all]");
            messageType = Integer.parseInt(readLine());
            Publisher publisher = new Publisher(EXCHANGE_INFO, BuiltinExchangeType.TOPIC);

            System.out.println("Message:");
            message = readLine();

            if (messageType == 0) {
                publisher.publish(message, "exchange.info.consumer.a");
                publisher.publish(message, "exchange.info.consumer.b");
            } else if (messageType == 1) {
                publisher.publish(message, "exchange.info.producer.X");
                publisher.publish(message, "exchange.info.producer.Y");
            } else if (messageType == 2) {
                publisher.publish(message, "exchange.info.consumer.a");
                publisher.publish(message, "exchange.info.consumer.b");
                publisher.publish(message, "exchange.info.producer.X");
                publisher.publish(message, "exchange.info.producer.Y");
            } else {
                System.err.println("Provided number is incorrect!");
                return;
            }
        }
    }
}
