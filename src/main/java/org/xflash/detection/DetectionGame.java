package org.xflash.detection;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.util.pathfinding.Path;
import org.xflash.collision.Shooter;
import org.xflash.collision.Sightable;

import java.util.ArrayList;

/**
 */
public class DetectionGame extends BasicGame {

    private Foo foo;
    private Shooter shooter;
    private Path path;
    private ArrayList<Shape> pathShapes;

    public DetectionGame() {
        super("Detection");
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        ArrayList<Sightable> sightables = new ArrayList<Sightable>();
        foo = new Foo(sightables);
        foo.moveTo(50, 50);

        path = buildPath();
        foo.follow(path);
        shooter = new Shooter(container, container.getWidth() / 2, container.getHeight() / 2);
        sightables.add(shooter);
    }

    private Path buildPath() {
        Path path = new Path();
        path.appendStep(100, 200);
        path.appendStep(200, 200);
        path.appendStep(300, 400);
        path.appendStep(350, 450);
        path.appendStep(200, 500);
        path.appendStep(100, 310);

        pathShapes = new ArrayList<Shape>();
        for(int i=0; i< path.getLength(); i++) {
            pathShapes.add(new Circle(path.getX(i), path.getY(i), 3));
        }
        return path;
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        foo.update(container, delta);
        shooter.update(container, delta);
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        foo.render(container, g);
        shooter.render(container, g);

        renderPath(container, g, path);
    }

    private void renderPath(GameContainer container, Graphics g, Path path) {
        if (path == null) return;
        if (path.getLength() < 1) return;
        int x = path.getX(0);
        int y = path.getY(0);
        g.setColor(Color.red);
        drawStep(g, x, y, 0);
        for (int i = 1; i < path.getLength(); i++) {
            int x1 = path.getX(i);
            int y1 = path.getY(i);
            drawStep(g, x1, y1, i);
            g.drawLine(x, y, x1, y1);
            x = x1;
            y = y1;
        }
    }

    private void drawStep(Graphics g, int x, int y, int step) {
        g.drawString(String.format("%d", step), x, y+10);
        g.draw(pathShapes.get(step));
    }
}
