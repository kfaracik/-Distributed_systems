package server.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static other.Data.CLIENT_PORT_PING_KEY;

public class ServerUdp {

    private static DatagramSocket datagramSocket = null;
    private static Boolean isRunning = false;

    private final AddressStack addressStack;
    private final int MAX_TRY = 20;
    private String broadcastMessage = null;
    private Thread serverThread;
    private Thread clientListenerThread;
    private int numberOfTry = 0;

    public ServerUdp(int port) {
        addressStack = new AddressStack();
        System.out.println("Starting UDP server...");
        try {
            if (datagramSocket == null || !isRunning) {
                datagramSocket = new DatagramSocket(port);
            } else {
                System.out.println("Server is running.");
            }
        } catch (SocketException e) {
            System.out.println("Unable to create UDP socket!");
            e.printStackTrace();
        }
    }

    public Boolean isRunning() {
        return isRunning || (numberOfTry <= MAX_TRY);
    }

    public void startServer() {
        setClientListener();
        byte[] messageByte = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(messageByte, messageByte.length);
        isRunning = true;

        serverThread = new Thread(() -> {
            while (isRunning) {
                Arrays.fill(messageByte, (byte)0);
                String message = receiveData(datagramPacket);

                if (message.equals(CLIENT_PORT_PING_KEY)) {
                    addressStack.push(datagramPacket.getPort(), datagramPacket.getAddress());
                } else {
                    // new message received
                    broadcastMessage = message;
                    numberOfTry = 0;
                }
            }

            closeServer();
        });
        serverThread.start();
    }

    private void setClientListener() {
        clientListenerThread = new Thread(() -> {
            while (numberOfTry <= MAX_TRY) {
                try {
                    while (!addressStack.empty() && broadcastMessage != null) {
                        Thread.sleep(500);

                        int port = addressStack.popPrt();
                        InetAddress address = addressStack.popAddress();

                        senData(broadcastMessage, address, port);
                        System.out.println("\t[UDP send to port " + port +"] " + broadcastMessage);

                        if (addressStack.empty()) {
                            numberOfTry = MAX_TRY + 1;  // finish
                        }
                    }

                    Thread.sleep(500);
                    numberOfTry++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            closeServer();
        });
        clientListenerThread.start();
    }

    private void senData(String message, InetAddress clientAddress, int clientPort) {
        byte[] messageByte = message.getBytes(StandardCharsets.UTF_8);
        DatagramPacket datagramPacket = new DatagramPacket(messageByte, messageByte.length, clientAddress, clientPort);
        try {
            datagramSocket.send(datagramPacket);
        } catch (IOException e) {
            System.err.println("Unable to send UDP datagram.");
            e.printStackTrace();
        }
    }

    private String receiveData(DatagramPacket datagramPacket) {
        try {
            datagramSocket.receive(datagramPacket);
        } catch (IOException e) {
            // Unable to receive UDP datagram or timeout.
        }

        return new String(datagramPacket.getData()).trim();
    }

    public void closeServer() {
        serverThread.interrupt();
        clientListenerThread.interrupt();
        isRunning = false;
        if (!datagramSocket.isClosed()) {
            datagramSocket.close();
            System.out.println("UDP server was closed.");
        }
    }
}
