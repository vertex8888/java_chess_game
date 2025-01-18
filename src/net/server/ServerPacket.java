package net.server;

import game.Piece;
import game.Utils.Log;
import game.Utils.ByteBuff;

public class ServerPacket {
    public static final int SERVER_PACKET_TYPE_INVALID             = 0;
    public static final int SERVER_PACKET_TYPE_CONNECTION_OK       = 1;
    public static final int SERVER_PACKET_TYPE_PING_OK             = 2;
    public static final int SERVER_PACKET_TYPE_PLAYER_TURN         = 3;
    public static final int SERVER_PACKET_TYPE_PLAYER_MOVE_OK      = 4;
    public static final int SERVER_PACKET_TYPE_PLAYER_COLOR_ASSIGN = 5;

    public static ByteBuff connectionOk() {
        ByteBuff buff = new ByteBuff(ServerInfo.PACKET_BUFFER_SIZE);
        buff.writeInt(SERVER_PACKET_TYPE_CONNECTION_OK);

        return buff;
    }

    public static ByteBuff pingOk() {
        ByteBuff buff = new ByteBuff(ServerInfo.PACKET_BUFFER_SIZE);
        buff.writeInt(SERVER_PACKET_TYPE_PING_OK);

        return buff;
    }

    public static ByteBuff playerTurn(Piece[][] board) {
        ByteBuff buff = new ByteBuff(ServerInfo.PACKET_BUFFER_SIZE);
        buff.writeInt(SERVER_PACKET_TYPE_PLAYER_TURN);

        for(int y = 0; y < 8; y += 1) {
            for(int x = 0; x < 8; x += 1) {
                int color = -1;
                int type = -1;

                if(board[y][x] != null) {
                    color = board[y][x].color;
                    type = board[y][x].type;
                }
                buff.writeInt(color);
                buff.writeInt(type);
            }
        }

        return buff;
    }

    public static ByteBuff playerMoveOk(Piece[][] board) {
        ByteBuff buff = new ByteBuff(ServerInfo.PACKET_BUFFER_SIZE);
        buff.writeInt(SERVER_PACKET_TYPE_PLAYER_MOVE_OK);

        for(int y = 0; y < 8; y += 1) {
            for(int x = 0; x < 8; x += 1) {
                int color = -1;
                int type = -1;

                if(board[y][x] != null) {
                    color = board[y][x].color;
                    type = board[y][x].type;
                }
                buff.writeInt(color);
                buff.writeInt(type);
            }
        }

        return buff;
    }

    public static ByteBuff playerColorAssign(int colorValue) {
        ByteBuff buff = new ByteBuff(ServerInfo.PACKET_BUFFER_SIZE);
        buff.writeInt(SERVER_PACKET_TYPE_PLAYER_COLOR_ASSIGN);
        buff.writeInt(colorValue);

        return buff;
    }
}
