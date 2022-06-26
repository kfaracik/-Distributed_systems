package client;

import commands.CommandManager;
import commands.CommandType;
import multicast.MulticastPublisher;
import multicast.MulticastReceiver;
import other.Data;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

import static other.Data.CLIENT_PORT_PING_KEY;
import static other.Data.USER_NAME_KEY;

public class ClientManager {

    private final Scanner scanner;
    private CommandType mode = CommandType.INVALID_COMMAND;
    private String username;
    private ClientTcp clientTcp;
    private MulticastPublisher multicastPublisher = null;
    private MulticastReceiver multicastReceiver = null;
    private Socket socket;


    public ClientManager() {
        scanner = new Scanner(System.in);
    }

    public void init(ClientTcp clientTcp, Socket socket, String username) {
        this.username = username;
        this.clientTcp = clientTcp;
        this.socket = socket;
    }

    public void start() throws IOException {
        startListening(clientTcp);

        clientTcp.sendMessage(username, USER_NAME_KEY);    // send username to server
        String message;

        while (socket.isConnected()) {
            if (mode == CommandType.MULTICAST && multicastPublisher != null) {
                handleMulticast();
                continue;
            } else {
                message = scanner.nextLine();
            }

            switch(CommandManager.getCommandType(message)) {
                case DEFAULT_MODE:
                    mode = CommandType.DEFAULT_MODE;
                    break;
                case QUIT:
                    clientTcp.close();
                    break;
                case UDP_SEND:
                    clientTcp.sendRequest(CommandType.UDP_RECEIVE);
                    System.out.print("[Enter UDP message]: ");
                    message = scanner.nextLine();
                    sendUdpMessage("[" + username + "] " + message);
                    break;
                case MULTICAST:
                    multicastMode();
                    break;
                default:
                    mode = CommandType.DEFAULT_MODE;
                    clientTcp.sendMessage(username, message);
                    break;
            }
        }
    }

    private void startListening(ClientTcp clientTcp) {

        clientTcp.setOnRequestReceivedListener(new IOnRequestReceived() {

            // todo error when server is no active (listener not exists yet)

            @Override
            public void onUdpReceived() {
                Thread udpReceiveThread = new Thread(() -> {
                    ClientUDP clientUDP = new ClientUDP();
                    clientUDP.sendPacket(CLIENT_PORT_PING_KEY);
                    String message = clientUDP.receiveUdpPacket();
                    System.out.println("[UDP received]: " + message);
                });

                udpReceiveThread.start();

                // receiving timeout
                new Thread(() -> {
                    try {
                        Thread.sleep(15000);
                        udpReceiveThread.interrupt();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onMulticastReceived() {
                System.out.println("Multicast chanel opened");
                MulticastReceiver multicastReceiver = new MulticastReceiver();
                multicastReceiver.filterMsgFrom(username);
                multicastReceiver.start();
            }
        });
    }

    private void handleMulticast() throws IOException {
        System.out.println("[Enter Multicast message]: ");
        String message = scanner.nextLine();

        if (message.equals(Data.MULTICAST_END)) {
            changeToDefaultMode();
            multicastReceiver = null;
        } else if (CommandManager.getCommandType(message) == CommandType.DEFAULT_MODE) {
            changeToDefaultMode();
        } else {
            multicastPublisher.multicast("[" + username + "] " + message);
        }
    }

    private void changeToDefaultMode() throws IOException {
        mode = CommandType.DEFAULT_MODE;
        multicastPublisher.closeConnection();
        multicastPublisher = null;
        System.out.println("[TCP mode]");
    }

    private void multicastMode() {
        mode = CommandType.MULTICAST;
        clientTcp.sendRequest(CommandType.MULTICAST_OPEN);
        System.out.println("[Enter Multicast message]: ");
        multicastPublisher = new MulticastPublisher();
        multicastPublisher.multicast("[" + username + "] " + scanner.nextLine());
    }

    private void sendUdpMessage(String message) {
        if (!Objects.equals(message, "")) {
            ClientUDP clientUDP = new ClientUDP();
            clientUDP.sendPacket(message);
        }
    }

    public String parserUserName() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        while (CommandManager.getCommandType(username) != CommandType.INVALID_COMMAND) {
            System.out.println("Given string is unavailable. Enter your username: ");
            username = scanner.nextLine();
        }

        return username;
    }
}
