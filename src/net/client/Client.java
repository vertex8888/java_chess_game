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
import net.Packet;
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
        NetUDP.sendPacket(socket, ClientPacket.makeConnection(), serverAddress, serverPort);
    }

    public void pingServer() {
        NetUDP.sendPacket(socket, ClientPacket.checkPing(), serverAddress, serverPort);
    }

    public void sendMove(PlayerMove move) {
        NetUDP.sendPacket(socket, ClientPacket.move(move), serverAddress, serverPort);
    }

    public void processCurrentPacket(Packet currentPacket) {
        if(!gotConnection) {
            assert(currentPacket.type == ServerPacket.SERVER_PACKET_TYPE_CONNECTION_OK);
            Log.print("[Client] -> Server connection ok...\n");

            gotConnection = true;
            return;
        }

        switch (currentPacket.type) {
            case ServerPacket.SERVER_PACKET_TYPE_PING_OK: {
            } break;
            case ServerPacket.SERVER_PACKET_TYPE_PLAYER_TURN: {
                gotTurn = true;
                shouldUpdateBoard = true;
                board = ServerPacket.playerTurn(currentPacket);
            } break;
            case ServerPacket.SERVER_PACKET_TYPE_PLAYER_MOVE_OK: {
                shouldUpdateBoard = true;
                board = ServerPacket.playerMoveOk(currentPacket);
            } break;
            case ServerPacket.SERVER_PACKET_TYPE_PLAYER_COLOR_ASSIGN: {
                pieceColor = ServerPacket.playerColorAssign(currentPacket);
            } break;
        }
    }
}

