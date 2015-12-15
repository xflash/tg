package org.xflash;

import org.newdawn.slick.AppGameContainer;
import org.xflash.bullet.LightningTest;
import org.xflash.collision.CollisionTest;

/**
 */
public class Launcher {

    public static void main(String[] args) {
        try {
            // create a new container for our example game. This container
            // just creates a normal native window for rendering OpenGL accelerated
            // elements to
            AppGameContainer container = new AppGameContainer(
//                    new LightningTest()
                    new CollisionTest()
//                    new LightningTest()
//                    new AStarGame()
//                    new Scroller()
                    , 800, 600, false);
            container.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
