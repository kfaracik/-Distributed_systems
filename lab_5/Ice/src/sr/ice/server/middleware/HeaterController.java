package sr.ice.server.middleware;

import HeaterDemo.HeaterIce;
import HeaterDemo.Temperature;
import HeaterDemo.TemperaturePlan;
import com.zeroc.Ice.Current;
import sr.ice.other.SUID;
import sr.ice.server.devices.Heater;

import java.util.HashMap;
import java.util.Map;

import static sr.ice.server.devices.DevicesConfig.INITIAL_HEATER_TEMP;

public class HeaterController implements HeaterIce {

    private static final Map<Long, Heater> heaterMap = new HashMap<>();

    public static void registerDevices() {
        heaterMap.put(SUID.id(), new Heater(INITIAL_HEATER_TEMP));
        heaterMap.put(SUID.id(), new Heater(INITIAL_HEATER_TEMP));
        heaterMap.put(SUID.id(), new Heater(INITIAL_HEATER_TEMP));
    }

    public static String getDevices() {
        StringBuilder devices = new StringBuilder();

        for (Map.Entry<Long, Heater> fridge : heaterMap.entrySet()) {
            devices.append("Heater:").append(fridge.getKey()).append("\n");
        }

        return devices.toString();
    }

    @Override
    public void setHeat(Temperature data, Current current) {
        if (heaterMap.containsKey(data.id)) {
            heaterMap.get(data.id).setNewTemperature((int) data.temp);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void setHeatingPlan(TemperaturePlan data, Current current) {
        if (heaterMap.containsKey(data.id)) {
            heaterMap.get(data.id).setHeatingPlan(data);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String getHeaterState(long id, Current current) {
        if (heaterMap.containsKey(id)) {
            return heaterMap.get(id).getState();
        } else {
            throw new IllegalArgumentException();
        }
    }
}
