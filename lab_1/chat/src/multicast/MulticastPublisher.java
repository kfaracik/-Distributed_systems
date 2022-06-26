package multicast;

import other.Data;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class MulticastPublisher {
    private DatagramSocket socket;
    private InetAddress group;
    private byte[] buf;
    private Thread multicastThread;
    private Boolean isRunning = false;

    public void multicast(String multicastMessage) {
        multicastThread = new Thread(() -> {
            try {
                isRunning = true;
                Thread.sleep(500);              // delay
                socket = new DatagramSocket();
                group = InetAddress.getByName(Data.MULTICAST_GROUP_ADDRESS);
                buf = multicastMessage.getBytes();

                DatagramPacket packet = new DatagramPacket(buf, buf.length, group, Data.MULTICAST_PORT);
                socket.send(packet);
            } catch (IOException | InterruptedException e) {
                // possible interrupt
                if(isRunning) {
                    try {
                        closeConnection();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        multicastThread.start();
    }

    private void sendEndingPacket() throws IOException {
        buf = Data.MULTICAST_END.getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, Data.MULTICAST_PORT);
        socket.send(packet);
    }

    public void closeConnection() throws IOException {
        if (isRunning) {
            System.out.println("Closing multicast connection.");
            isRunning = false;
            multicastThread.interrupt();
            sendEndingPacket();
            socket.close();
        }
    }
}