package sr.ice.server.devices;

import lombok.Getter;
import lombok.Setter;

public class Fridge {
    private int fridgeTemperature;
    private int newFridgeTemp;
    private boolean isFridgeChanging = false;

    private int freezerTemperature;
    private int newFreezerTemp;
    private boolean isFreezerChanging = false;

    private final int fridgeFullness;

    public Fridge(int initialFridgeTemp, int initialFreezerTemp) {
        this.fridgeTemperature = initialFridgeTemp;
        this.newFridgeTemp = initialFridgeTemp;
        this.freezerTemperature = initialFreezerTemp;
        this.newFreezerTemp = initialFridgeTemp;
        fridgeFullness = (int)(Math.random()*100);
    }

    public void setFridgeTemperature(int temperature) {
        newFridgeTemp = temperature;
        performFridgeTemperatureChange();
    }

    public void setFreezerTemperature(int temperature) {
        newFreezerTemp = temperature;
        performFreezerTemperatureChange();
    }

    public int getFridgeTemperature() {
        return fridgeTemperature;
    }

    public int getFreezerTemperature() {
        return freezerTemperature;
    }

    public int getFridgeFullness() {
        return fridgeFullness;
    }

    private void performFridgeTemperatureChange() {
        if (isFridgeChanging) {
            return;
        }

        isFridgeChanging = true;

        new Thread(() -> {
            while (fridgeTemperature != newFridgeTemp) {
                if (fridgeTemperature - newFridgeTemp > 0) {
                    perform();
                    fridgeTemperature--;
                } else {
                    perform();
                    fridgeTemperature++;
                }
            }
            isFridgeChanging = false;
        }).start();
    }

    private void performFreezerTemperatureChange() {
        if (isFreezerChanging) {
            return;
        }

        isFreezerChanging = true;

        new Thread(() -> {
            while (freezerTemperature != newFreezerTemp) {
                if (freezerTemperature - newFreezerTemp > 0) {
                    perform();
                    freezerTemperature--;
                } else {
                    perform();
                    freezerTemperature++;
                }
            }
            isFreezerChanging = false;
        }).start();
    }

    private void perform() {
        try {
            Thread.sleep(DevicesConfig.FRIDGE_CHANGE_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
