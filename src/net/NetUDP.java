package net;

import game.Utils.ByteBuff;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import net.server.ServerInfo;

public class NetUDP {
    public static InetAddress getInetAddress(String name) {
        InetAddress addr = null;
        try {
            addr = InetAddress.getByName(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addr;
    }

    public static DatagramSocket createSocket() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return socket;
    }

    public static DatagramSocket createSocket(int port, String host) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(port, getInetAddress(host));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return socket;
    }

    public static Packet receivePacket(DatagramSocket socket) {
        Packet packet = null;

        try {
            ByteBuff dataBuffer = new ByteBuff(ServerInfo.PACKET_BUFFER_SIZE);
            DatagramPacket datagramPacket = new DatagramPacket(dataBuffer.getBytes(), dataBuffer.getSize());
            socket.receive(datagramPacket);

            InetAddress address = datagramPacket.getAddress();
            int port = datagramPacket.getPort();
            packet = new Packet(dataBuffer, address, port);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return packet;
    }

    public static void sendPacket(DatagramSocket socket, Packet packet, InetAddress address, int port) {
        DatagramPacket datagramPacket = new DatagramPacket(packet.getBytes(), packet.getSize(), address, port);

        try {
            socket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

