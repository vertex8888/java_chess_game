package net.client;

import game.PlayerMove;
import game.Utils.ByteBuff;
import net.server.ServerInfo;
import net.Packet;

public class ClientPacket {
    public static final int CLIENT_PACKET_TYPE_INVALID         = 0;
    public static final int CLIENT_PACKET_TYPE_MAKE_CONNECTION = 1;
    public static final int CLIENT_PACKET_TYPE_CHECK_PING      = 2;
    public static final int CLIENT_PACKET_TYPE_MOVE            = 3;

    public static Packet makeConnection() { return(new Packet(CLIENT_PACKET_TYPE_MAKE_CONNECTION)); }
    public static Packet checkPing()      { return(new Packet(CLIENT_PACKET_TYPE_CHECK_PING)); }

    public static Packet move(PlayerMove move) {
        Packet packet = new Packet(CLIENT_PACKET_TYPE_MOVE);

        packet.writeInt(move.fromX);
        packet.writeInt(move.fromY);
        packet.writeInt(move.toX);
        packet.writeInt(move.toY);

        return packet;
    }

    public static PlayerMove move(Packet packet) {
        assert(packet.type == CLIENT_PACKET_TYPE_MOVE);

        PlayerMove move = new PlayerMove();
        move.fromX = packet.readInt();
        move.fromY = packet.readInt();
        move.toX   = packet.readInt();
        move.toY   = packet.readInt();

        return move;
    }
}