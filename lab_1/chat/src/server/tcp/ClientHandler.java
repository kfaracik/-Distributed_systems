package server.tcp;

import server.udp.ServerUdp;
import commands.CommandManager;
import other.Data;
import other.ISocketCommunication;
import other.TimeManager;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

import static other.Data.UDP_KEY;

public class ClientHandler implements Runnable, ISocketCommunication {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUserName;
    private ServerUdp serverUdp = null;
    private boolean closeSocket = false;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUserName = bufferedReader.readLine();
            parseClient();
        } catch (IOException e) {
            closeClientSession(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()) {
            if (closeSocket) {
                closeClientSession(socket, bufferedReader, bufferedWriter);
                return;
            }

            try {
                messageFromClient = bufferedReader.readLine();

                switch(CommandManager.getCommandType(messageFromClient.trim())) {
                    case QUIT:
                        closeClientSession(socket, bufferedReader, bufferedWriter);
                        return;
                    case UDP_RECEIVE:
                        openUdpChanel();
                        break;
                    default:
                        broadcastMessage(messageFromClient);
                        break;
                }
            } catch (IOException e) {
                closeClientSession(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    private void openUdpChanel() {
        if (serverUdp != null && serverUdp.isRunning()) {
            serverUdp.closeServer();
        }

        // opening clients UDP chanel
        broadcastMessage(UDP_KEY);
        serverUdp = new ServerUdp(Data.PORT);
        serverUdp.startServer();
    }

    public void broadcastMessage(String message) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientUserName.equals(clientUserName)) {
                    clientHandler.bufferedWriter.write(message);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeClientSession(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    private void parseClient() {
        if (Objects.equals(clientUserName, "GET / HTTP/1.1")) {
            closeSocket = true;
        } else {
            // manage same names problem
            // optionally manage new client - inform client about current active servers
            clientHandlers.add(this);
            broadcastMessage("[SERVER] \"" + clientUserName + "\" join the chat!");
            showOnTerminal("join the chat!");
        }
    }

    private void showOnTerminal(String message) {
        if (!closeSocket) {
            System.out.println("[" + TimeManager.getTime() + "] \"" + clientUserName + "\" " + message);
        }
    }

    private void removeClientHandler() {
        clientHandlers.remove(this);
        if (!closeSocket) {
            broadcastMessage("[SERVER] \"" + clientUserName + "\" left the chat!");
        }
    }

    @Override
    public void closeClientSession(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
        ISocketCommunication.super.closeClientSession(socket, bufferedReader, bufferedWriter);
        showOnTerminal("connection closed!");
        closeSocket = true;
    }
}
