package net;

import game.Utils.ByteBuff;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

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

    public static DatagramPacket createPacket(byte[] buff, int buff_size) {
        DatagramPacket packet = new DatagramPacket(buff, buff_size);
        return packet;
    }


    public static DatagramPacket createPacket(ByteBuff buff) {
        buff.reset();
        DatagramPacket packet = new DatagramPacket(buff.getBytes(), buff.getSize());
        return packet;
    }

    public static DatagramPacket createPacket(byte[] buff, int buff_size, InetAddress addr, int port) {
        DatagramPacket packet = new DatagramPacket(buff, buff_size, addr, port);
        return packet;
    }

    public static DatagramPacket createPacket(ByteBuff buff, InetAddress addr, int port) {
        buff.reset();
        DatagramPacket packet = new DatagramPacket(buff.getBytes(), buff.getSize(), addr, port);
        return packet;
    }

    public static void receivePacket(DatagramSocket socket, DatagramPacket packet) {
        try {
            socket.receive(packet);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendPacket(DatagramSocket socket, DatagramPacket packet) {
        try {
            socket.send(packet);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

