package multicast;

import other.Data;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.time.Duration;
import java.time.Instant;

public class MulticastReceiver {
    protected MulticastSocket socket = null;
    protected byte[] buf = new byte[256];
    public String userName;
    private Thread watchDogThread;
    private Thread serverThread;
    private final long MAX_RUNNING_MILLIS = 600000; // 10 min
    private Boolean isRunning = false;

    public void start() {
        serverThread = new Thread(() -> {
            isRunning = true;
        });

            try {
            startWatchDog();
            socket = new MulticastSocket(Data.MULTICAST_PORT);
            InetAddress group = InetAddress.getByName(Data.MULTICAST_GROUP_ADDRESS);
            socket.joinGroup(group);

            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());

                if (received.equals(Data.MULTICAST_END)) {
                    socket.leaveGroup(group);
                    break;
                }

                filerMessage(received);
            }
        } catch (IOException e) {
            // interrupt possible
        }
    }

    public void startWatchDog() {
        Instant start = Instant.now();
        watchDogThread = new Thread(() -> {
            while (true) {
                Instant end = Instant.now();
                Duration timeElapsed = Duration.between(start, end);
                if (timeElapsed.toMillis() > MAX_RUNNING_MILLIS) {
                    System.err.println("Timeout!");
                    close();
                    return;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void filterMsgFrom(String userName) {
        this.userName = userName;
    }

    private void filerMessage(String received) {
        if (!received.contains(userName)) {
            System.out.println("[Multicast received] : " + received);
        }
    }

    private void close() {
        if (isRunning) {
            watchDogThread.interrupt();
            serverThread.interrupt();
        }

        if (!socket.isClosed()) {
            socket.close();
        }
    }
}