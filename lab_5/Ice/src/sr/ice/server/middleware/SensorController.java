package sr.ice.server.middleware;

import SensorDemo.SensorIce;
import com.zeroc.Ice.Current;
import sr.ice.other.SUID;
import sr.ice.server.devices.Sensor;
import sr.ice.server.devices.SensorTmp;

import java.util.HashMap;
import java.util.Map;

public class SensorController implements SensorIce {

    private static final Map<Long, SensorTmp> sensorTempMap = new HashMap<>();
    private static final Map<Long, Sensor> sensorMap = new HashMap<>();

    // TODO connect heater with sensors
    public static void registerDevices() {
        sensorTempMap.put(SUID.id(), new SensorTmp(18));
        sensorTempMap.put(SUID.id(), new SensorTmp(24));

        sensorMap.put(SUID.id(), new Sensor(20, 40, 0));
        sensorMap.put(SUID.id(), new Sensor(21, 30, 25));
    }

    public static String getDevices() {
        StringBuilder devices = new StringBuilder();

        for (Map.Entry<Long, SensorTmp> fridge : sensorTempMap.entrySet()) {
            devices.append("SensorTemp:").append(fridge.getKey()).append("\n");
        }

        for (Map.Entry<Long, Sensor> fridge : sensorMap.entrySet()) {
            devices.append("Sensor:").append(fridge.getKey()).append("\n");
        }

        return devices.toString();
    }

    @Override
    public float getSensorTemp(long id, Current current) {
        if (sensorTempMap.containsKey(id)) {
            return (float) sensorTempMap.get(id).getTemperature();
        } else if (sensorMap.containsKey(id)) {
            return (float) sensorMap.get(id).getTemperature();
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public float getSensorHumidity(long id, Current current) {
        if (sensorMap.containsKey(id)) {
            return sensorMap.get(id).getHumidity();
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public float getSensorBrightness(long id, Current current) {
        if (sensorMap.containsKey(id)) {
            return sensorMap.get(id).getBrightness();
        } else {
            throw new IllegalArgumentException();
        }
    }
}
