package expedition;

import com.rabbitmq.client.BuiltinExchangeType;
import expedition.connection.Publisher;
import expedition.connection.Receiver;
import expedition.products.Product;
import expedition.products.ProductController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static expedition.connection.KeyGenerator.generateInfoKey;
import static expedition.data.ExchangeController.*;

public class ConsumerController {

    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private static String teamName;
    private static List<Product> productsToOrder = new ArrayList<>();

    public static void main(String[] argv) {
        parseName();
        parseProducts();
        greet();

        sendOrder();
        confirmOrder();
    }

    public static void confirmOrder() {
        Receiver consumerInfo = new Receiver(EXCHANGE_INFO, BuiltinExchangeType.TOPIC);
        consumerInfo.start(generateInfoKey(teamName, FUNCTION_CONSUMER));

        while (consumerInfo.getReceivedConfirm() < productsToOrder.size()) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Received all orders.");
    }

    private static void sendOrder() {
        int orderId = 0;
        for (Product product : productsToOrder) {    // send each item to proper queue
            Publisher consumerOrderPublisher = new Publisher(EXCHANGE_ORDER, BuiltinExchangeType.TOPIC);
            String messageOrder = teamName + "," + product + "," + orderId; // Todo id
            consumerOrderPublisher.publish(messageOrder, product.toString());
            consumerOrderPublisher.close();
            orderId++;
        }
    }

    private static void parseName() {
        System.out.println("Enter expedition team name: ");
        teamName = readLine();
    }

    private static void parseProducts() {
        System.out.println("Available products: " + ProductController.getProductsList().toString());
        System.out.println("Type products to order? (or generate random 'r n')");
        String order = readLine();
        String[] parts = order.split(" ");

        if (Objects.equals(parts[0], "r")) {
            productsToOrder = ProductController.generateRandomList(Integer.parseInt(parts[1]));
        } else {
            for (String item : parts) {
                Product product = Product.get(item);
                productsToOrder.add(product);
            }
        }

        System.out.println("Your order: " + productsToOrder);
    }

    private static void greet() {
        System.out.println("Team " + teamName + " have products to order: " + productsToOrder);
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
}