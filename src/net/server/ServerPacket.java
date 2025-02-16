package net.server;

import game.Piece;
import game.Utils.Log;
import game.Utils.ByteBuff;

import java.net.InetAddress;
import net.Packet;

public class ServerPacket {

    public static final int SERVER_PACKET_TYPE_NONE                = 0;
    public static final int SERVER_PACKET_TYPE_PLAYER_TURN         = 1;
    public static final int SERVER_PACKET_TYPE_PLAYER_MOVE_OK      = 2;
    public static final int SERVER_PACKET_TYPE_PLAYER_COLOR_ASSIGN = 3;

    //
    // Packet Player Turn
    //
    public static Packet playerTurn(Piece[][] board) {
        Packet packet = new Packet(SERVER_PACKET_TYPE_PLAYER_TURN);
        packetWriteChessBoard(packet, board);
        return packet;
    }

    public static Piece[][] playerTurn(Packet packet) {
        assert(packet.type == SERVER_PACKET_TYPE_PLAYER_TURN);
        return packetReadChessBoard(packet);
    }

    //
    // Player move ok
    //
    public static Packet playerMoveOk(Piece[][] board) {
        Packet packet = new Packet(SERVER_PACKET_TYPE_PLAYER_MOVE_OK);
        packetWriteChessBoard(packet, board);
        return packet;
    }

    public static Piece[][] playerMoveOk(Packet packet) {
        assert(packet.type == SERVER_PACKET_TYPE_PLAYER_MOVE_OK);
        return packetReadChessBoard(packet);
    }


    //
    // Color assign
    //
    public static Packet playerColorAssign(int colorValue) {
        Packet packet = new Packet(SERVER_PACKET_TYPE_PLAYER_COLOR_ASSIGN);
        packet.writeInt(colorValue);

        return packet;
    }

    public static int playerColorAssign(Packet packet) {
        assert(packet.type == SERVER_PACKET_TYPE_PLAYER_COLOR_ASSIGN);
        return packet.readInt();
    }

    //
    //
    //
    private static void packetWriteChessBoard(Packet packet, Piece[][] board) {
        for(int y = 0; y < 8; y += 1) {
            for(int x = 0; x < 8; x += 1) {
                int color = -1;
                int type = -1;

                if(board[y][x] != null) {
                    color = board[y][x].color;
                    type = board[y][x].type;
                }
                packet.writeInt(color);
                packet.writeInt(type);
            }
        }
    }

    private static Piece[][] packetReadChessBoard(Packet packet) {
        Piece[][] board = new Piece[8][8];

        for(int y = 0; y < 8; y += 1) {
            for(int x = 0; x < 8; x += 1) {
                int color = packet.readInt();
                int type  = packet.readInt();

                Piece piece = null;
                if(color != -1 && type != -1) piece = new Piece(color, type);
                board[y][x] = piece;
            }
        }

        return board;
    }
}
