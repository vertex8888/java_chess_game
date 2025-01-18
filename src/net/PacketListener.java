package net;

import net.server.ServerInfo;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class PacketListener extends Thread {
    private DatagramSocket socket;
    private DatagramPacket currentPacket;

    public PacketListener(DatagramSocket serverSocket) {
        socket = serverSocket;
    }

    public void run() {
        byte[] packetBytes = new byte[ServerInfo.PACKET_BUFFER_SIZE];
        DatagramPacket packet = NetUDP.createPacket(packetBytes, packetBytes.length);
        NetUDP.receivePacket(socket, packet);

        currentPacket = packet;
    }

    public DatagramPacket getCurrentPacket() { return currentPacket; }
}
