package expedition;

import com.rabbitmq.client.BuiltinExchangeType;
import expedition.connection.Receiver;
import expedition.products.Product;
import expedition.products.ProductController;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static expedition.connection.KeyGenerator.generateInfoKey;
import static expedition.data.ExchangeController.*;

public class ProducerController {

    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private static String name;
    private static List<Product> productsAvailable = new ArrayList<>();

    public static void main(String[] argv) {
        parseName();
        createInfoChanel();
        parseAvailableProducts();
        greet();

        bindToProductQueue();
    }

    private static void createInfoChanel() {
        Receiver consumerInfo = new Receiver(EXCHANGE_INFO, BuiltinExchangeType.TOPIC);
        consumerInfo.start(generateInfoKey(name, FUNCTION_PRODUCER));
    }

    private static void parseName() {
        System.out.println("Enter producer name: ");
        name = readLine();
    }

    private static void parseAvailableProducts() {
        System.out.println("Available products: " + ProductController.getProductsList().toString());
        System.out.println("Type products to produce? (or generate random 'r n')");
        String order = readLine();
        String[] parts = order.split(" ");

        if (Objects.equals(parts[0], "r")) {
            productsAvailable = ProductController.generateRandomList(Integer.parseInt(parts[1]));
        } else {
            for (String item : parts) {
                Product product = Product.get(item);
                productsAvailable.add(product);
            }
        }
    }

    private static @NotNull List<Receiver> bindToProductQueue() {
        List<Receiver> receiverList = new ArrayList<>();

        for (Product product : productsAvailable) {
            Receiver producerReceiver = new Receiver(EXCHANGE_ORDER, BuiltinExchangeType.TOPIC);
            producerReceiver.setName(name);
            producerReceiver.start(product.toString());
            receiverList.add(producerReceiver);
        }

        return receiverList;
    }

    private static void greet() {
        System.out.println("Producer " + name + " have products to order: " + productsAvailable);
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

