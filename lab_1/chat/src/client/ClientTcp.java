package client;

import commands.CommandManager;
import commands.CommandType;
import other.ISocketCommunication;

import java.io.*;
import java.net.Socket;

import static other.Data.*;

public class ClientTcp implements ISocketCommunication {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    protected String username;

    public ClientTcp(Socket socket, String username) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        } catch (IOException e) {
            close();
        }
    }

    private IOnRequestReceived onRequestReceived;
    public void setOnRequestReceivedListener(IOnRequestReceived onRequestReceived) {
        this.onRequestReceived = onRequestReceived;
    }

    public void listenForMessage() {
        new Thread(() -> {
            String messageFromServer;

            while (socket.isConnected()) {
                try {
                    messageFromServer = bufferedReader.readLine();

                    if (CommandManager.getCommandType(messageFromServer) == CommandType.UDP_RECEIVE) {
                        onRequestReceived.onUdpReceived();
                    } else if (CommandManager.getCommandType(messageFromServer) == CommandType.MULTICAST_OPEN) {
                        onRequestReceived.onMulticastReceived();
                    } else {
                        System.out.println(messageFromServer);
                    }
                } catch (IOException e) {
                    close();
                }
            }
        }).start();
    }

    protected void sendMessage(String username, String message) throws IOException {
        if (parseMessage(username, message) != null) {
            bufferedWriter.write(parseMessage(username, message));
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
    }

    private String parseMessage(String username, String message) {
        String messageToServer;
        switch (message) {
            case USER_NAME_KEY:
                messageToServer = username;
                break;
            case QUIT_KEY:
            case UDP_KEY:
            case MULTICAST_KEY:
                messageToServer = message;
                break;
            case "":
                messageToServer = null;
            default:
                messageToServer = "[" + username + "] " + message;
                break;
        }

        return messageToServer;
    }

    protected void sendRequest(CommandType command) {
        try {
            switch (command) {
                case QUIT:
                    sendMessage(username, QUIT_KEY);
                    break;
                case UDP_RECEIVE:
                    sendMessage(username, UDP_KEY);
                    break;
                case MULTICAST_OPEN:
                    sendMessage(username, MULTICAST_KEY);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        sendRequest(CommandType.QUIT);
        ISocketCommunication.super.closeClientSession(socket, bufferedReader, bufferedWriter);
        System.exit(0);
    }
}
