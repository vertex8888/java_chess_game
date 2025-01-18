package net.client;

import game.PlayerMove;
import game.Utils.ByteBuff;
import net.server.ServerInfo;

public class ClientPacket {
    public static final int CLIENT_PACKET_TYPE_INVALID         = 0;
    public static final int CLIENT_PACKET_TYPE_MAKE_CONNECTION = 1;
    public static final int CLIENT_PACKET_TYPE_CHECK_PING      = 2;
    public static final int CLIENT_PACKET_TYPE_MOVE            = 3;

    public static ByteBuff makeConnection() {
        ByteBuff buff = new ByteBuff(ServerInfo.PACKET_BUFFER_SIZE);
        buff.writeInt(CLIENT_PACKET_TYPE_MAKE_CONNECTION);

        return buff;
    }

    public static ByteBuff checkPing() {
        ByteBuff buff = new ByteBuff(ServerInfo.PACKET_BUFFER_SIZE);
        buff.writeInt(CLIENT_PACKET_TYPE_CHECK_PING);

        return buff;
    }

    public static ByteBuff move(PlayerMove move) {
        ByteBuff buff = new ByteBuff(ServerInfo.PACKET_BUFFER_SIZE);

        buff.writeInt(CLIENT_PACKET_TYPE_MOVE);

        buff.writeInt(move.fromX);
        buff.writeInt(move.fromY);
        buff.writeInt(move.toX);
        buff.writeInt(move.toY);

        return buff;
    }
}