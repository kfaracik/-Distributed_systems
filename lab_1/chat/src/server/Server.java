package server;

import server.tcp.ServerTCP;
import other.Data;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(Data.PORT);
        ServerTCP serverTCP = new ServerTCP(serverSocket);
        serverTCP.startServer();
    }
}
