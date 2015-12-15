package org.xflash.collision;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class CollisionTest extends BasicGame {

    public static final int LAYER_INDEX = 0;
    public static final String BLOCKED = "blocked";
    private Shooter shooter;
    private Target target;
    private BulletPool bulletPool;
    private TiledMap map;
    private boolean[][] freecells;
    private int[][] timeouts;

    public CollisionTest() {
        super("Collision");
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        List<Collidable> collidables = new ArrayList<Collidable>();
        bulletPool = new BulletPool(100, collidables);
        shooter = new Shooter(gc, 100, 400, bulletPool, new CanMove() {
            public boolean canMove(int newX, int newY) {
//                if (newX>map.getTileWidth()*10 || newY>map.getTileHeight()*10)
//                    return true;

                int tx = newX / map.getTileWidth();
                int ty = newY / map.getTileHeight();
//                int tileId = map.getTileId(tx, ty, LAYER_INDEX);
                return freecells[tx][ty];
            }
        });
        target = new Target(gc, 300, 200);
        collidables.add(target);

        map = new TiledMap("testdata/scroller/map.tmx");
        // build a collision map based on tile properties in the TileD map
        freecells = new boolean[map.getWidth()][map.getHeight()];
        timeouts = new int[map.getWidth()][map.getHeight()];
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                int tileID = map.getTileId(x, y, LAYER_INDEX);
                freecells[x][y] = "true".equalsIgnoreCase(map.getTileProperty(tileID, BLOCKED, "false"));
                timeouts[x][y] = 0;
            }
        }

        collidables.add(new Collidable() {
            public boolean collideWith(Shape shape) {
                float centerX = shape.getCenterX();
                float centerY = shape.getCenterY();
//                if (centerX > map.getTileWidth() * 10 || centerY > map.getTileHeight() * 10)
//                    return false;

                int tx = (int) (centerX / map.getTileWidth());
                int ty = (int) (centerY / map.getTileHeight());
                return !freecells[tx][ty];
            }

            public void handleCollision(Shape shape) {
                float centerX = shape.getCenterX();
                float centerY = shape.getCenterY();
//                if (centerX > map.getTileWidth() * 10 || centerY > map.getTileHeight() * 10)
//                    return;

                int tx = (int) (centerX / map.getTileWidth());
                int ty = (int) (centerY / map.getTileHeight());

//                map.setTileId(tx, ty, LAYER_INDEX, 0);
//                System.out.println("handleCollision = " + tx+","+ty);
                if (!freecells[tx][ty]) {
                    freecells[tx][ty] = true;
                    timeouts[tx][ty] = 1000;
                    map.setTileId(tx, ty, LAYER_INDEX, 2);
                }

            }
        });

    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        shooter.update(gc, delta);
        target.update(gc, delta);
        bulletPool.update(gc, delta);

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                if (timeouts[x][y] > 0) {
                    timeouts[x][y] = timeouts[x][y] - delta;
                    if (timeouts[x][y] < 0) {
                        timeouts[x][y] = 0;
                        freecells[x][y] = false;
                        map.setTileId(x, y, LAYER_INDEX, 1);
                    }
                }
            }
        }

    }

    public void render(GameContainer gc, Graphics g) throws SlickException {
        g.setColor(Color.cyan);
        g.drawRect(0, 0, gc.getWidth() - 1, gc.getHeight() - 1);

        int xtiles = gc.getWidth() / map.getTileWidth();
        int ytiles = gc.getHeight() / map.getTileHeight();
        map.render(0, 0, 0, 0, xtiles, ytiles);

        for (int x = 0; x < freecells.length && x < xtiles; x++) {
            boolean[] row = freecells[x];
            for (int y = 0; y < row.length && y < ytiles; y++) {
                g.setColor(row[y] ? Color.green : Color.red);
                g.drawRect(x * map.getTileWidth(), y * map.getTileHeight(), map.getTileWidth() - 1, map.getTileHeight() - 1);
            }
        }
        
        shooter.render(gc, g);
        target.render(gc, g);
        bulletPool.render(gc, g);

    }


}
