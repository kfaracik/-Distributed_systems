package expedition.products;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum Product {
    OXYGEN("OXYGEN"),
    BOOTS("BOOTS"),
    BACKPACK("BACKPACK"),
    COMMUNICATION_MODULE("COMMUNICATION_MODULE"),
    WINTER_JACKET("WINTER_JACKET"),
    SKI_GOOGLES("SKI_GOOGLES");

    private final String text;

    private static final Map<String,Product> ENUM_MAP;

    Product(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    static {
        Map<String,Product> map = new ConcurrentHashMap<>();
        for (Product instance : Product.values()) {
            map.put(instance.text.toLowerCase(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static Product get (String name) {
        return ENUM_MAP.get(name.toLowerCase());
    }
}
