package sr.ice.client;

import FridgeDemo.FridgeIcePrx;
import GlobalDemo.GlobalIcePrx;
import HeaterDemo.HeaterIcePrx;
import HeaterDemo.Temperature;
import HeaterDemo.TemperaturePlan;
import SensorDemo.IllegalArgumentException;
import SensorDemo.SensorIcePrx;
import com.zeroc.Ice.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.Exception;
import java.util.Objects;

public class IceClient {

    public static void main(String[] args) {
        int status = 0;
        Communicator communicator = null;

        try {
            // 1. Inicjalizacja ICE
            communicator = Util.initialize(args);

            // 2. To samo co powy¿ej, ale mniej ³adnie
            ObjectPrx baseGlobal = communicator.stringToProxy("global/global_1:tcp -h 127.0.0.2 -p 10000 -z : udp -h 127.0.0.2 -p 10000");
            ObjectPrx baseSensor = communicator.stringToProxy("sensor/sensor_1:tcp -h 127.0.0.2 -p 10000 -z : udp -h 127.0.0.2 -p 10000");
            ObjectPrx baseHeater = communicator.stringToProxy("heater/heater_1:tcp -h 127.0.0.2 -p 10000 -z : udp -h 127.0.0.2 -p 10000");
            ObjectPrx baseFridge = communicator.stringToProxy("fridge/fridge_1:tcp -h 127.0.0.2 -p 10000 -z : udp -h 127.0.0.2 -p 10000");

            // 3. Rzutowanie, zawê¿anie (do typu Calc)
            GlobalIcePrx objGlobal = GlobalIcePrx.checkedCast(baseGlobal);
            if (objGlobal == null) throw new Error("Invalid global proxy");

            HeaterIcePrx heaterIcePrx = HeaterIcePrx.checkedCast(baseHeater);
            if (heaterIcePrx == null) throw new Error("Invalid heater proxy");


            SensorIcePrx sensorIcePrx = SensorIcePrx.checkedCast(baseSensor);
            if (sensorIcePrx == null) throw new Error("Invalid sensor proxy");

            FridgeIcePrx fridgeIcePrx = FridgeIcePrx.checkedCast(baseFridge);
            if (fridgeIcePrx == null) throw new Error("Invalid sensor proxy");

            // 4. Wywo³anie zdalnych operacji i zmiana trybu proxy dla
            handleActions(objGlobal, sensorIcePrx, heaterIcePrx, fridgeIcePrx);
        } catch (LocalException e) {
            e.printStackTrace();
            status = 1;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            status = 1;
        }

        //clean
        if (communicator != null) {
            try {
                communicator.destroy();
            } catch (Exception e) {
                System.err.println(e.getMessage());
                status = 1;
            }
        }
        System.exit(status);
    }

    private static void handleActions(
            GlobalIcePrx objGlobal,
            SensorIcePrx sensorIcePrx,
            HeaterIcePrx heaterIcePrx,
            FridgeIcePrx fridgeIcePrx) {
        String line = null;
        java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        System.out.println("Available devices: " + objGlobal.getDevices());

        do {
            try {
                System.out.println("==> ");
                line = in.readLine();

                switch (line) {
                    case "devices":
                        String devices = objGlobal.getDevices();
                        System.out.println("Available devices: " + devices);
                        break;
                    case "sensor":
                        handleSensor(in, sensorIcePrx);
                        break;
                    case "heater":
                        handleHeater(in, heaterIcePrx);
                        break;
                    case "fridge":
                        handleFridge(in, fridgeIcePrx);
                        break;
                    default:
                        System.out.println("Available: devices, sensor, heater, fridge");
                        break;
                }
            } catch (IOException | TwowayOnlyException ex) {
                System.err.println(ex.getMessage());
            } catch (IllegalArgumentException | HeaterDemo.IllegalArgumentException | FridgeDemo.IllegalArgumentException e) {
                System.err.println("Illegal argument!");
            }
//            } catch (EmptySequenceError e) {
//                System.err.println("Mean error empty input. " + e);
//            }
        }
        while (!Objects.equals(line, "exit"));
    }

    private static void handleSensor(BufferedReader in, SensorIcePrx sensorIcePrx) throws IOException, IllegalArgumentException {
        System.out.println("operation: [temperature, humidity, brightness]");
        String operation = in.readLine();

        System.out.println("id:");
        long id = Long.parseLong(in.readLine());

        switch (operation) {
            case "temperature":
                System.out.println("Sensor temperature: " + sensorIcePrx.getSensorTemp(id) + "°C");
                break;
            case "humidity":
                System.out.println("Sensor humidity: " + sensorIcePrx.getSensorHumidity(id) + "%");
                break;
            case "brightness":
                System.out.println("Sensor brightness: " + sensorIcePrx.getSensorBrightness(id) + "lux");
                break;
            default:
                System.err.println("Given operation is not supported!");
        }
    }

    private static void handleHeater(BufferedReader in, HeaterIcePrx heaterIcePrx) throws IOException, HeaterDemo.IllegalArgumentException {
        System.out.println("operation:\t[heat, heat_plan, status]");
        String operation = in.readLine();

        System.out.println("id:");
        long id = Long.parseLong(in.readLine());
        int targetTemp;

        switch (operation) {
            case "heat":
                System.out.println("enter target temperature:");
                targetTemp = Integer.parseInt(in.readLine());
                Temperature data = new Temperature(id, targetTemp);
                heaterIcePrx.setHeat(data);
                break;
            case "heat_plan":
                System.out.println("enter target temperature:");
                targetTemp = Integer.parseInt(in.readLine());
                System.out.println("start heating time:");
                String startHeatingTime = in.readLine();
                System.out.println("end heating time:");
                String endHeatingTime = in.readLine();

                TemperaturePlan dataPlan = new TemperaturePlan(id, targetTemp, startHeatingTime, endHeatingTime);
                heaterIcePrx.setHeatingPlan(dataPlan);
                break;
            case "status":
                System.out.println("Heater state: \n" + heaterIcePrx.getHeaterState(id));
                break;
            default:
                System.err.println("Given operation is not supported!");
        }
    }

    private static void handleFridge(BufferedReader in, FridgeIcePrx fridgeIcePrx) throws IOException, FridgeDemo.IllegalArgumentException {
        System.out.println("operation:\t[fridge_tmp, freezer_tmp, status]");
        String operation = in.readLine();

        System.out.println("id:");
        long id = Long.parseLong(in.readLine());
        int targetTemp;
        FridgeDemo.Temperature data;

        switch (operation) {
            case "fridge_tmp":
                System.out.println("enter target temperature:");
                targetTemp = Integer.parseInt(in.readLine());
                data = new FridgeDemo.Temperature(id, targetTemp);
                fridgeIcePrx.setFridgeTemp(data);
                break;
            case "freezer_tmp":
                System.out.println("enter target temperature:");
                targetTemp = Integer.parseInt(in.readLine());
                data = new FridgeDemo.Temperature(id, targetTemp);
                fridgeIcePrx.setFreezerTemp(data);
                break;
            case "status":
                System.out.println("Fridge fullness: " + fridgeIcePrx.getFridgeFullness(id) + "%");
                break;
            default:
                System.err.println("Given operation is not supported!");
        }
    }
}