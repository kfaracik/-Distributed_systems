package sr.ice.server.devices;

import HeaterDemo.TemperaturePlan;

import java.util.*;

import static sr.ice.server.devices.DevicesConfig.HEATER_NO_HEATING_TMP;

public class Heater {

    private int temperature;
    private int newTemperature;
    private boolean isChanging;

    private boolean isHeatingPlanOn = false;
    private int targetHeatingPlanTemperature;
    private int startHeatingPlanHour;
    private int endHeatingPlanHour;

    public Heater(int initialTemperature) {
        this.temperature = initialTemperature;
        newTemperature = initialTemperature;
    }

    public void setNewTemperature(int temperature) {
        newTemperature = temperature;
        performTemperatureChange();
    }

    public void performTemperatureChange() {
        if (isChanging) {
            return;
        }

        isChanging = true;
        Thread changingTempThread = new Thread(() -> {
            while (temperature != newTemperature) {
                if (temperature - newTemperature > 0) {
                    perform();
                    temperature--;
                } else {
                    perform();
                    temperature++;
                }
            }
            isChanging = false;
        });
        changingTempThread.start();
    }

    private void perform() {
        try {
            Thread.sleep(DevicesConfig.HEATER_CHANGE_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int parseTime(String time) {
        int result = Integer.parseInt(time);
        if (result <= 0 || result > 24) {
            throw new IllegalArgumentException("Time have to be in format 0..24");
        }

        return result;
    }

    public void setHeatingPlan(TemperaturePlan data) {
        System.out.println("HEATING PLAN!!!");
        isHeatingPlanOn = true;
        targetHeatingPlanTemperature = (int) data.temp;
        startHeatingPlanHour = parseTime(data.startTime);
        endHeatingPlanHour = parseTime(data.endTime);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, parseTime(data.startTime));
        Date dateStart = cal.getTime();

        cal.set(Calendar.HOUR_OF_DAY, parseTime(data.endTime));
        Date dateEnd = cal.getTime();

        Timer timer = new Timer();
        timer.schedule(startHeatingPlan(), dateStart);
        timer.schedule(stopHeatingPlan(), dateEnd);
    }

    private TimerTask startHeatingPlan() {
        newTemperature = targetHeatingPlanTemperature;
        performTemperatureChange();
        return null;
    }

    private TimerTask stopHeatingPlan() {
        newTemperature = HEATER_NO_HEATING_TMP;
        performTemperatureChange();
        return null;
    }

    public String getState() {
        String result =  "current temperature: " + temperature + "\n" +
                "target heating temperature: " + newTemperature + "\n" +
                "is heating: " + isChanging + "\n";

        if (isHeatingPlanOn) {
            result += "is heating plan on: " + isHeatingPlanOn + "\n" +
                    "target heating plan temperature:" + targetHeatingPlanTemperature + "\n" +
                    "start heating hour" + startHeatingPlanHour + "\n" +
                    "end heating hour" + endHeatingPlanHour;
        }

        return result;
    }
}
