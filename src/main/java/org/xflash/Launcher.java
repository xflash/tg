package org.xflash;

import org.newdawn.slick.AppGameContainer;
import org.xflash.collision.CollisionGame;

/**
 */
public class Launcher {

    public static void main(String[] args) {
        try {
            // create a new container for our example game. This container
            // just creates a normal native window for rendering OpenGL accelerated
            // elements to
            AppGameContainer appgc = new AppGameContainer(
//                    new LightningTest()
                    new CollisionGame()
//                    new LightningTest()
//                    new AStarGame()
//                    new ScrollerGame()
                    , 800, 600, false);
            appgc.setMinimumLogicUpdateInterval(1000 / 60);
            appgc.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
