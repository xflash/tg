package org.xflash.collision;

import org.newdawn.slick.*;
import org.newdawn.slick.util.pathfinding.Path;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class CollisionGame extends BasicGame {

    private Shooter shooter;
    private Target target;
    private BulletPool bulletPool;
    private Path path;
    private GameMap gameMap;

    public CollisionGame() {
        super("COLL");
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        List<Collidable> collidables = new ArrayList<Collidable>();
        bulletPool = new BulletPool(100, collidables);
        target = new Target(gc, 300, 200);
        collidables.add(target);
        gameMap = new GameMap(gc);
        shooter = new Shooter(gc, gameMap.getStartX(), gameMap.getStartY(), bulletPool, gameMap, new RightClickHandler() {
            public void mousePressed(int x, int y) {
            }

            public void mouseReleased(int x, int y) {
                gameMap.setFinish(x, y);
                path = gameMap.buildPath(shooter, shooter.getPosition().getX(), shooter.getPosition().getY());
            }
        });
        path = gameMap.buildPath(shooter, shooter.getPosition().getX(), shooter.getPosition().getY());
        collidables.add(gameMap);
    }

    @Override
    public void keyReleased(int key, char c) {
        path = gameMap.buildPath(shooter, shooter.getPosition().getX(), shooter.getPosition().getY());

    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        shooter.update(gc, delta);
        target.update(gc, delta);
        bulletPool.update(gc, delta);
    }

    public void render(GameContainer gc, Graphics g) throws SlickException {
        g.setColor(Color.cyan);
        g.drawRect(0, 0, gc.getWidth() - 1, gc.getHeight() - 1);

        gameMap.render(gc, g, shooter.getPosition().getX(), shooter.getPosition().getY());

        shooter.render(gc, g);

        gameMap.renderPath(gc,g,path);

        target.render(gc, g);
        bulletPool.render(gc, g);
    }
}
