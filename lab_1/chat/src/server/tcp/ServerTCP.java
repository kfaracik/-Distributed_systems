package server.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {

    private final ServerSocket serverSocket;

    public ServerTCP(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                ClientHandler clientManager = new ClientHandler(socket);
                Thread thread = new Thread(clientManager);
                thread.start();
            }
        } catch (IOException e) {
            closeServerSocket();
        }
    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
