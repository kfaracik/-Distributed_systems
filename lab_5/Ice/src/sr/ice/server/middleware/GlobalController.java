package sr.ice.server.middleware;

import GlobalDemo.GlobalIce;
import com.zeroc.Ice.Current;

public class GlobalController implements GlobalIce {

    public static void registerDevices() {
        SensorController.registerDevices();
        FridgeController.registerDevices();
        HeaterController.registerDevices();
    }

    @Override
    public String getDevices(Current current) {
        return "\n" + SensorController.getDevices() +
                FridgeController.getDevices() +
                HeaterController.getDevices();
    }
}
