package game;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;


import game.Utils.Log;
import game.Utils.FileIO;
import game.Utils.FSM;
import game.Utils.Timer;


/*

@todo:

@right_now:
    - github


- user name "login"
- matchmaking
- invites
- game config(time, color)
- implement full on chess game

@maybe:
    - handle abrupt and unexpected disconnections
    - handle packet loss
    - handle double packets
*/


public class Main {
    public static void main(String[] args) {
        Window.init("Chess Game", Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);

        Renderer.init();
        Asset.loadAll();
        Game.init();
        FrameControl.init();

        while(true) {
            FrameControl.sync(Game.TARGET_FPS);
            Input.poll();
            Game.update();
            Game.render();
        }
    }
}
