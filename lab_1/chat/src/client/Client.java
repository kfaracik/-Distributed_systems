package client;

import commands.CommandManager;
import other.Data;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {
        ClientManager clientManager = new ClientManager();

        CommandManager.showHelp();
        String username = clientManager.parserUserName();

        Socket socket = new Socket(Data.HOST, Data.PORT);
        ClientTcp tcpClient = new ClientTcp(socket, username);
        tcpClient.listenForMessage();

        clientManager.init(tcpClient, socket, username);
        clientManager.start();
    }
}
