package org.xflash.mousefollowing;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.xflash.steering.Boid;
import org.xflash.utils.AngleUtils;

/**
 * @author rcoqueugniot
 * @since 20.12.15
 */
public class MouseFollowingGame extends BasicGame {

    private Boid boid;
    private int posX;
    private int posY;
    private double targetAngle;
    private Shape shape;

    public MouseFollowingGame() {
        super("MouseFollowing");
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        posX = container.getWidth() / 2;
        posY = container.getHeight() / 2;

//        shape = new Rectangle(posX, posY, 30, 30);
        Polygon polygon = new Polygon();
        polygon.addPoint(0, -20);
        polygon.addPoint(10, 20);
        polygon.addPoint(-10, 20);
        polygon.addPoint(0, -20);

        shape = polygon;

        boid = new Boid(posX, posY, 20, 10);

    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
//        boid.update(container, delta);
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {

        targetAngle = AngleUtils.getTargetAngle(posX, posY, newx, newy);
//        boid.setRotation(targetAngle);

    }

    public void render(GameContainer container, Graphics g) throws SlickException {
//        boid.render(container, g);

        g.rotate(posX+10, posY+10, (float) Math.toDegrees(targetAngle));
        g.drawRect(posX, posY, 20, 20);
        g.resetTransform();

        g.translate(posX, posY);
        g.rotate(0, 0, (float) Math.toDegrees(targetAngle));
        g.draw(shape);
        g.resetTransform();
    }
}
