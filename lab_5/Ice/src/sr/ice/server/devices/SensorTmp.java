package sr.ice.server.devices;

public class SensorTmp {

    private final int temperature;

    public SensorTmp(int initialTemp) {
        this.temperature = initialTemp;
    }

    public int getTemperature() {
        return temperature;
    }
}