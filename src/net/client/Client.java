package net.client;

import game.Piece;
import game.PlayerMove;
import game.Utils.ByteBuff;
import game.Utils.CurrentThread;
import game.Utils.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

import net.NetUDP;
import net.server.ServerInfo;
import net.server.ServerPacket;

import game.Utils.Timer;

public class Client {
    DatagramSocket socket;
    InetAddress serverAddress;
    int serverPort;

    boolean gotConnection = false;

    int pieceColor = -1;

    boolean gotTurn = false;

    Piece[][] board = new Piece[8][8];
    boolean shouldUpdateBoard = false;

    public Client() {
        socket = NetUDP.createSocket();
        serverAddress = NetUDP.getInetAddress(ServerInfo.HOST_NAME);
        serverPort = ServerInfo.PORT;

        makeConnection();
    }

    public void makeConnection() {
        ByteBuff packetBuff = ClientPacket.makeConnection();
        DatagramPacket packet = NetUDP.createPacket(packetBuff, serverAddress, serverPort);
        NetUDP.sendPacket(socket, packet);
    }

    public void pingServer() {
        ByteBuff packetBuff = ClientPacket.checkPing();
        DatagramPacket packet = NetUDP.createPacket(packetBuff, serverAddress, serverPort);
        NetUDP.sendPacket(socket, packet);
    }

    public void sendMove(PlayerMove move) {
        ByteBuff outBuff = ClientPacket.move(move);
        DatagramPacket outPacket = NetUDP.createPacket(outBuff, serverAddress, serverPort);
        NetUDP.sendPacket(socket, outPacket);
    }

    public void processCurrentPacket(DatagramPacket currentPacket) {
        if(!gotConnection) {
            ByteBuff packetBuff = new ByteBuff(currentPacket.getData(), ServerInfo.PACKET_BUFFER_SIZE);
            int packetType = packetBuff.readInt();

            assert(packetType == ServerPacket.SERVER_PACKET_TYPE_CONNECTION_OK);
            Log.print("[Client] -> Server connection ok...\n");

            gotConnection = true;
        }

        ByteBuff packetBuff = new ByteBuff(currentPacket.getData(), ServerInfo.PACKET_BUFFER_SIZE);
        int packetType = packetBuff.readInt();

        switch (packetType) {
            case ServerPacket.SERVER_PACKET_TYPE_PING_OK: {
            } break;
            case ServerPacket.SERVER_PACKET_TYPE_PLAYER_TURN: {
                gotTurn = true;
                shouldUpdateBoard = true;

                for(int y = 0; y < 8; y += 1) {
                    for(int x = 0; x < 8; x += 1) {
                        int color = packetBuff.readInt();
                        int type  = packetBuff.readInt();

                        Piece piece = null;
                        if(color != -1 && type != -1) piece = new Piece(color, type);
                        board[y][x] = piece;
                    }
                }
            } break;
            case ServerPacket.SERVER_PACKET_TYPE_PLAYER_MOVE_OK: {
                shouldUpdateBoard = true;

                for(int y = 0; y < 8; y += 1) {
                    for(int x = 0; x < 8; x += 1) {
                        int color = packetBuff.readInt();
                        int type  = packetBuff.readInt();

                        Piece piece = null;
                        if(color != -1 && type != -1) piece = new Piece(color, type);
                        board[y][x] = piece;
                    }
                }
            } break;
            case ServerPacket.SERVER_PACKET_TYPE_PLAYER_COLOR_ASSIGN: {
                pieceColor = packetBuff.readInt();
            } break;
        }
    }
}

