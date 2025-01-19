package net.server;

import game.Piece;
import game.Utils.ByteBuff;
import game.Utils.Log;
import net.NetUDP;

import net.client.ClientPacket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

import net.Packet;

import game.PlayerMove;

class ClientInfo {
    String userName;
    InetAddress address;
    int port;

    int pieceColor;

    public ClientInfo(InetAddress address, int port) {
        this.userName = String.format("%s:d", address.getHostAddress(), port);
        this.address = address;
        this.port = port;
    }
}


class Server  {
    DatagramSocket socket;

    ClientInfo[] clients = new ClientInfo[2];
    int connectedClientsCount = 0;
    int currentClientTurn = 0;
    int movesPlayedCount = 0;

    Piece[][] board;

    public Server() {
        socket = NetUDP.createSocket(ServerInfo.PORT, ServerInfo.HOST_NAME);

        // @todo: Don't copy this stuff
        Piece WHITE_KING   = new Piece(Piece.COLOR_WHITE, Piece.TYPE_KING);
        Piece WHITE_QUEEN  = new Piece(Piece.COLOR_WHITE, Piece.TYPE_QUEEN);
        Piece WHITE_ROOK   = new Piece(Piece.COLOR_WHITE, Piece.TYPE_ROOK);
        Piece WHITE_BISHOP = new Piece(Piece.COLOR_WHITE, Piece.TYPE_BISHOP);
        Piece WHITE_KNIGHT = new Piece(Piece.COLOR_WHITE, Piece.TYPE_KNIGHT);
        Piece WHITE_PAWN   = new Piece(Piece.COLOR_WHITE, Piece.TYPE_PAWN);

        Piece BLACK_KING   = new Piece(Piece.COLOR_BLACK, Piece.TYPE_KING);
        Piece BLACK_QUEEN  = new Piece(Piece.COLOR_BLACK, Piece.TYPE_QUEEN);
        Piece BLACK_ROOK   = new Piece(Piece.COLOR_BLACK, Piece.TYPE_ROOK);
        Piece BLACK_BISHOP = new Piece(Piece.COLOR_BLACK, Piece.TYPE_BISHOP);
        Piece BLACK_KNIGHT = new Piece(Piece.COLOR_BLACK, Piece.TYPE_KNIGHT);
        Piece BLACK_PAWN   = new Piece(Piece.COLOR_BLACK, Piece.TYPE_PAWN);

        Piece[][] clasicBoard = {
                {BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_QUEEN, BLACK_KING, BLACK_BISHOP, BLACK_KNIGHT, BLACK_ROOK},
                {BLACK_PAWN, BLACK_PAWN,   BLACK_PAWN,   BLACK_PAWN,  BLACK_PAWN, BLACK_PAWN,   BLACK_PAWN,   BLACK_PAWN},
                {null,       null,         null,         null,        null,       null,         null,         null      },
                {null,       null,         null,         null,        null,       null,         null,         null      },
                {null,       null,         null,         null,        null,       null,         null,         null      },
                {null,       null,         null,         null,        null,       null,         null,         null      },
                {WHITE_PAWN, WHITE_PAWN,   WHITE_PAWN,   WHITE_PAWN,  WHITE_PAWN, WHITE_PAWN,   WHITE_PAWN,   WHITE_PAWN},
                {WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_QUEEN, WHITE_KING, WHITE_BISHOP, WHITE_KNIGHT, WHITE_ROOK}
        };

        board = clasicBoard;
    }

    void processCurrentPacket(Packet currentPacket) {

        InetAddress packetAddress = currentPacket.address;
        int         packetPort    = currentPacket.port;
        String      clientName    = String.format("%s:%d", packetAddress.getHostAddress(), packetPort);

        switch(currentPacket.type) {
            case ClientPacket.CLIENT_PACKET_TYPE_MAKE_CONNECTION: {

                Log.print("[Server] -> Client["+clientName+"] attempting connection...\n");

                NetUDP.sendPacket(socket, ServerPacket.connectionOk(), packetAddress, packetPort);

                clients[connectedClientsCount] = new ClientInfo(packetAddress, packetPort);
                connectedClientsCount += 1;
                if(connectedClientsCount == 2) {
                    Random rand = new Random();
                    currentClientTurn = rand.nextInt(0, 2);

                    ClientInfo whiteClient = clients[currentClientTurn];
                    ClientInfo blackClient = clients[(currentClientTurn + 1)%2];
                    whiteClient.pieceColor = Piece.COLOR_WHITE;
                    blackClient.pieceColor = Piece.COLOR_BLACK;


                    NetUDP.sendPacket(socket, ServerPacket.playerColorAssign(Piece.COLOR_WHITE), whiteClient.address, whiteClient.port);
                    NetUDP.sendPacket(socket, ServerPacket.playerColorAssign(Piece.COLOR_BLACK), blackClient.address, blackClient.port);
                    NetUDP.sendPacket(socket, ServerPacket.playerTurn(board), whiteClient.address, whiteClient.port);
                }
            } break;
            case ClientPacket.CLIENT_PACKET_TYPE_CHECK_PING: {
                NetUDP.sendPacket(socket, ServerPacket.pingOk(), packetAddress, packetPort);
            } break;
            case ClientPacket.CLIENT_PACKET_TYPE_MOVE: {
                movesPlayedCount += 1;
                Log.print("[Server] -> Client["+clientName+"] executed move "+movesPlayedCount+"...\n");

                PlayerMove move = ClientPacket.move(currentPacket);

                board[move.toY][move.toX] = board[move.fromY][move.fromX];
                board[move.fromY][move.fromX] = null;

                {
                    ClientInfo currClient = clients[currentClientTurn];
                    NetUDP.sendPacket(socket, ServerPacket.playerMoveOk(board), currClient.address, currClient.port);
                };

                currentClientTurn = (currentClientTurn + 1)%2;
                ClientInfo currentClientOnTurn = clients[currentClientTurn];
                NetUDP.sendPacket(socket, ServerPacket.playerTurn(board), currentClientOnTurn.address, currentClientOnTurn.port);

            } break;
        }
    }
}
