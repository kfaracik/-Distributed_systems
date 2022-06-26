package sr.ice.server;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;
import sr.ice.server.middleware.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class IceServer {

    public static void main(String[] args) {
        IceServer app = new IceServer();
        app.start(args);
    }

    public void start(String[] args) {
        int status = 0;
        Communicator communicator = null;

        try {
            // 1. Inicjalizacja ICE - utworzenie communicatora
            communicator = Util.initialize(args);

            // 2. Konfiguracja adaptera
            // METODA 1 (polecana produkcyjnie): Konfiguracja adaptera Adapter1 jest w pliku konfiguracyjnym podanym jako parametr uruchomienia serwera
            ObjectAdapter adapter = communicator.createObjectAdapter("Adapter1");

            // 3. Stworzenie serwanta/serwantów
            GlobalController globalServant = new GlobalController();
            SensorController sensorServant = new SensorController();
            HeaterController heaterServant = new HeaterController();
            FridgeController fridgeServant = new FridgeController();

            GlobalController.registerDevices();

            // 4. Dodanie wpisów do tablicy ASM, skojarzenie nazwy obiektu (Identity) z serwantem
            adapter.add(globalServant, new Identity("global_1", "global"));
            adapter.add(sensorServant, new Identity("sensor_1", "sensor"));
            adapter.add(heaterServant, new Identity("heater_1", "heater"));
            adapter.add(fridgeServant, new Identity("fridge_1", "fridge"));

            // 5. Aktywacja adaptera i wejœcie w pêtlê przetwarzania ¿¹dañ
            adapter.activate();
            System.out.println("Entering event processing loop...");
            communicator.waitForShutdown();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            status = 1;
        }

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
}
