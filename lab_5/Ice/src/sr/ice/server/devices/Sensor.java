package sr.ice.server.devices;

public class Sensor {

    private final int temperature;
    private final int humidity;
    private final int brightness;

    public Sensor(int initialTemp, int initialHumidity, int initialBrightness) {
        this.temperature = initialTemp;
        this.humidity = initialHumidity;
        this.brightness = initialBrightness;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getBrightness() {
        return brightness;
    }
}