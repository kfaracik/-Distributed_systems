package sr.ice.server.middleware;

import FridgeDemo.FridgeIce;
import FridgeDemo.Temperature;
import com.zeroc.Ice.Current;
import sr.ice.other.SUID;
import sr.ice.server.devices.Fridge;
import sr.ice.server.devices.FridgeBasic;

import java.util.HashMap;
import java.util.Map;

import static sr.ice.server.devices.DevicesConfig.INITIAL_FREEZER_TEMP;
import static sr.ice.server.devices.DevicesConfig.INITIAL_FRIDGE_TEMP;

public class FridgeController implements FridgeIce {

    private static final Map<Long, FridgeBasic> fridgeBasicMap = new HashMap<>();
    private static final Map<Long, Fridge> fridgeMap = new HashMap<>();

    public static void registerDevices() {
        fridgeBasicMap.put(SUID.id(), new FridgeBasic(INITIAL_FRIDGE_TEMP));
        fridgeBasicMap.put(SUID.id(), new FridgeBasic(INITIAL_FRIDGE_TEMP));

        fridgeMap.put(SUID.id(), new Fridge(INITIAL_FRIDGE_TEMP, INITIAL_FREEZER_TEMP));
    }

    public static String getDevices() {
        StringBuilder devices = new StringBuilder();

        for (Map.Entry<Long, FridgeBasic> fridge : fridgeBasicMap.entrySet()) {
            devices.append("FridgeBasic:").append(fridge.getKey()).append("\n");
        }

        for (Map.Entry<Long, Fridge> fridge : fridgeMap.entrySet()) {
            devices.append("Fridge:").append(fridge.getKey()).append("\n");
        }

        return devices.toString();
    }

    @Override
    public void setFridgeTemp(Temperature data, Current current) throws IllegalArgumentException {
        if (fridgeBasicMap.containsKey(data.id)) {
            fridgeBasicMap.get(data.id).setFridgeTemperature(data.temp);
        } else if (fridgeMap.containsKey(data.id)) {
            fridgeMap.get(data.id).setFridgeTemperature(data.temp);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void setFreezerTemp(Temperature data, Current current) throws IllegalArgumentException {
        if (fridgeMap.containsKey(data.id)) {
            fridgeMap.get(data.id).setFreezerTemperature(data.temp);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public float getFridgeFullness(long id, Current current) throws IllegalArgumentException {
        if (fridgeBasicMap.containsKey(id)) {
            return fridgeBasicMap.get(id).getFridgeFullness();
        } else if (fridgeMap.containsKey(id)) {
            return fridgeMap.get(id).getFridgeFullness();
        } else {
            throw new IllegalArgumentException();
        }
    }
}
