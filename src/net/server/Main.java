package net.server;

import game.FrameControl;
import game.Renderer;
import game.Window;
import net.PacketListener;

import java.awt.*;
import java.net.DatagramPacket;

public class Main {
    public static void main(String[] args) {
        Window.init("Server", 500, 500);

        Renderer.init();
        FrameControl.init();

        Server server = new Server();
        PacketListener packetListener = new PacketListener(server.socket);
        packetListener.start();


        while(true) {
            FrameControl.sync(120);

            DatagramPacket currentPacket = null;
            if(!packetListener.isAlive()) {
                currentPacket = packetListener.getCurrentPacket();
                packetListener = new PacketListener(server.socket);
                packetListener.start();
            }

            if(currentPacket != null) server.processCurrentPacket(currentPacket);

            Renderer.clearBackground(Color.blue);
            Renderer.frameFlip();
        }
    }
}
