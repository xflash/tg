package org.xflash.mousefollowing;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.xflash.steering.Boid;
import org.xflash.steering.PathFollower;
import org.xflash.steering.Steering;
import org.xflash.utils.AngleUtils;

/**
 * @author rcoqueugniot
 * @since 20.12.15
 */
public class MouseFollowingGame extends BasicGame {

    private boolean drawForces = true;
    private Boid boid;
    private Vector2f mousPos = new Vector2f();

    public MouseFollowingGame() {
        super("MouseFollowing");
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        int posX = container.getWidth() / 2;
        int posY = container.getHeight() / 2;

        boid = new Boid(posX, posY, 20, new Steering() {
            public Vector2f computeTarget(Vector2f position) {
                return mousPos;
            }
        });
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        mousPos.set(newx, newy);
    }

    @Override
    public void keyPressed(int key, char c) {
        if(Input.KEY_SPACE==key) drawForces = !drawForces;
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        boid.update(container, delta);
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        boid.render(container, g, drawForces);
    }
}
