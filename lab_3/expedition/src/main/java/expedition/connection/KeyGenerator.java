package expedition.connection;

import static expedition.data.ExchangeController.*;

public class KeyGenerator {

    private KeyGenerator() {
    }

    public static String generateInfoKey(String key, String function) {
        return EXCHANGE_INFO + SEPARATOR + function + SEPARATOR + key;
    }

    public static String generateOrderKey(String key) {
        return EXCHANGE_ORDER + SEPARATOR + key;
    }

    public static String generateInfoKeyAll(String key, String function) {
        return EXCHANGE_INFO + SEPARATOR + function + SEPARATOR + "#";
    }

    public static String generateOrderKeyAll(String key) {
        return EXCHANGE_ORDER + SEPARATOR + "#";
    }
}
