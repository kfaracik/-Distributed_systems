package client;

import other.Data;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class ClientUDP {

    DatagramSocket datagramSocket;

        public ClientUDP() {
        try {
            datagramSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void sendPacket(String message) {
        byte[] messageByte = message.getBytes(StandardCharsets.UTF_8);
        InetAddress address = InetAddress.getLoopbackAddress();
        DatagramPacket datagramPacket = new DatagramPacket(messageByte, messageByte.length, address, Data.PORT);

        try {
            datagramSocket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receiveUdpPacket() {
        byte[] receiveBuffer = new byte[256];
        DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

        try {
            datagramSocket.receive(receivePacket);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(receivePacket.getData()).trim();
    }
}
