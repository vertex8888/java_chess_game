package net.client;

import game.*;
import game.Window;
import net.PacketListener;

import java.awt.*;
import java.net.DatagramPacket;

import net.Packet;

import game.Game;
import game.Utils.FSM;
import game.Utils.Log;

public class Main {

    enum ClientScreen {
        LOGIN,
        GAME,
    };

    static String clientUsername = "username";

    static void clientScreenLoginUpdate() {
        Gui.reset();

        // chess text
        {
            Font font = Asset.karminaFont64;

            String text = "Chess";
            int textWidth = Renderer.getTextWidth(font, text);

            int textX = Game.SCREEN_WIDTH/2 - textWidth/2;
            int textY = (int)(Game.SCREEN_HEIGHT*0.4f);

            Gui.setPosition(textX, textY);
            Gui.setFont(font);
            Gui.text(text);
        };

        // username field and button
        {
            // username field
            Font font = Asset.karminaFont32;

            int fieldWidth  = 300;
            int fieldHeight = font.getSize() + 16;

            int fieldX = Game.SCREEN_WIDTH/2 - fieldWidth/2;
            int fieldY = (int)(Game.SCREEN_HEIGHT*0.5f);

            Gui.setPosition(fieldX, fieldY);
            Gui.setSize(fieldWidth, fieldHeight);
            Gui.setFont(font);
            clientUsername = Gui.textField("idUsernameField", clientUsername);

            // login button
            String buttonText = "Login";

            int buttonWidth  = Renderer.getTextWidth(font, buttonText) + font.getSize()*2;
            int buttonHeight = font.getSize() + 16;

            int buttonX = Game.SCREEN_WIDTH/2 - buttonWidth/2;
            int buttonY = fieldY + fieldHeight + 20;

            Gui.setPosition(buttonX, buttonY);
            Gui.setSize(buttonWidth, buttonHeight);
            Gui.setFont(font);
            Gui.setText(buttonText);
            if (Gui.button("idLoginButton")) {
                // @todo login user...
            }
        };

    }

    static void clientScreenLoginRender() {
        Color bgColor = new Color(0x222222);
        Renderer.clearBackground(bgColor);

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

        Player tempPlayer = new Player(clasicBoard, Piece.COLOR_WHITE);
        tempPlayer.render();

        {
            int rectWidth  = (int)(Game.SCREEN_WIDTH*0.75f);
            int rectHeight = (int)(Game.SCREEN_HEIGHT*0.5f);

            int rectX = Game.SCREEN_WIDTH/2 - rectWidth/2;
            int rectY = Game.SCREEN_HEIGHT/2 - rectHeight/2;

            Renderer.drawRect(rectX, rectY, rectWidth, rectHeight, new Color(0.13f, 0.13f, 0.13f, 0.975f));
        };

        Gui.render();

        Renderer.frameFlip();
    }


    static void clientScreenGameUpdate() {
        switch(fsm.currState) {
            case "WAITING_FOR_COLOR" : {
                if(client.pieceColor != -1) {

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

                    player = new Player(clasicBoard, client.pieceColor);
                    fsm.set("WAITING_FOR_TURN");
                }
            } break;
            case "WAITING_FOR_TURN" : {
                if(client.gotTurn) {
                    client.gotTurn = false;
                    fsm.set("MAKING_MOVE");
                    Log.print("Got Turn\n");
                }

                if(client.shouldUpdateBoard) {
                    for(int y = 0; y < 8; y += 1) {
                        for(int x = 0; x < 8; x += 1) {
                            player.board[y][x] = client.board[y][x];
                        }
                    }
                    if(player.pieceColor == Piece.COLOR_BLACK) player.flipBoard();
                    client.shouldUpdateBoard = false;
                }
            } break;
            case "MAKING_MOVE" : {
                PlayerMove move = player.update();
                if(move != null) {

                    client.sendMove(move);
                    fsm.set("WAITING_FOR_TURN");
                }
            } break;
            default: {
                fsm.invalidState();
            }
        }


    }

    static void clientScreenGameRender() {
        Renderer.clearBackground(Color.black);

        switch(fsm.currState) {
            case "WAITING_FOR_COLOR" : {
            } break;
            case "WAITING_FOR_TURN" :
            case "MAKING_MOVE": {
                player.render();
            } break;
            default: {
                fsm.invalidState();
            }
        }

        Renderer.drawText(Asset.karminaFont32, fsm.currState, 30, 30, Color.white);

        Renderer.frameFlip();
    }


    static Client client = new Client();
    static PacketListener packetListener = new PacketListener(client.socket);

    static ClientScreen clientScreen = ClientScreen.LOGIN;

    static FSM fsm = new FSM("WAITING_FOR_COLOR");
    static Player player = null;

    public static void main(String[] args) {
        Window.init("Chess Client", Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);

        Asset.loadAll();
        Renderer.init();
        FrameControl.init();

        packetListener.start();

        while(true) {
            FrameControl.sync(60);

            Input.poll();

            Packet currentPacket = null;
            if(!packetListener.isAlive()) {
                currentPacket = packetListener.getCurrentPacket();
                packetListener = new PacketListener(client.socket);
                packetListener.start();
            }

            if(currentPacket != null) client.processCurrentPacket(currentPacket);

            switch(clientScreen) {
                case ClientScreen.LOGIN: clientScreenLoginUpdate(); break;
                case ClientScreen.GAME:  clientScreenGameUpdate();  break;
            }

            switch(clientScreen) {
                case ClientScreen.LOGIN: clientScreenLoginRender(); break;
                case ClientScreen.GAME:  clientScreenGameRender();  break;
            }

        }
    }
}
