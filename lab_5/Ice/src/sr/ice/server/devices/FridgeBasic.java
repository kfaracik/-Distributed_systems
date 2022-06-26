package sr.ice.server.devices;

public class FridgeBasic {

    private final int fridgeFullness;
    private int fridgeTemperature;
    private int newFridgeTemp;
    private boolean isFridgeChanging = false;

    public FridgeBasic(int initialFridgeTemp) {
        this.fridgeTemperature = initialFridgeTemp;
        this.newFridgeTemp = initialFridgeTemp;
        fridgeFullness = (int) (Math.random() * 100);
    }

    public int getFridgeTemperature() {
        return fridgeTemperature;
    }

    public void setFridgeTemperature(int temperature) {
        newFridgeTemp = temperature;
        performFridgeTemperatureChange();
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

    private void perform() {
        try {
            Thread.sleep(DevicesConfig.FRIDGE_CHANGE_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
