package net;

import net.server.ServerInfo;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class PacketListener extends Thread {
    private DatagramSocket socket;
    private Packet currentPacket;

    public PacketListener(DatagramSocket serverSocket) {
        socket = serverSocket;
    }

    public void run() {
        currentPacket = NetUDP.receivePacket(socket);
    }

    public Packet getCurrentPacket() { return currentPacket; }
}
