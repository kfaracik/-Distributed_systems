package expedition.products;

import lombok.Getter;

import java.util.*;

import static expedition.products.Product.*;

public class ProductController {

    @Getter
    private static final List<Product> productsList = new ArrayList<>(Arrays.asList(values()));

    // Set product prices
    public static final Map<Product, Integer> productsPriceMap;
    static {
        Map<Product, Integer> map = new HashMap<>();
        map.put(OXYGEN, 150);
        map.put(BOOTS, 1000);
        map.put(BACKPACK, 300);
        map.put(COMMUNICATION_MODULE, 450);
        map.put(WINTER_JACKET, 3500);
        map.put(SKI_GOOGLES, 100);
        productsPriceMap = Collections.unmodifiableMap(map);
    }

    // Set product production time
    public static final Map<Product, Integer> productsTimeMap;
    static {
        Map<Product, Integer> map = new HashMap<>();
        map.put(OXYGEN, 5);
        map.put(BOOTS, 1);
        map.put(BACKPACK, 1);
        map.put(COMMUNICATION_MODULE, 2);
        map.put(WINTER_JACKET, 3);
        map.put(SKI_GOOGLES, 1);
        productsTimeMap = Collections.unmodifiableMap(map);
    }

    public static List<Product> generateRandomList(int n) {
        Random rand = new Random();
        List<Product> randomProducts = new ArrayList<>();

        while (n > randomProducts.size() || randomProducts.size() == productsList.size()) {
            Product randomElement = productsList.get(rand.nextInt(productsList.size()));
            if (!randomProducts.contains(randomElement)) {
                randomProducts.add(randomElement);
            }
        }

        return randomProducts;
    }

    public static int getProductionTime(String productName) {
        Product productKey = Product.get(productName);
        return productsPriceMap.get(productKey);
    }
}
