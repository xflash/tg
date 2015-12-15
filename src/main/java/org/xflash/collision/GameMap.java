package org.xflash.collision;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.*;

/**
 * Created by coqueugniot on 15.12.2015.
 */
public class GameMap implements CanMove, Collidable {
    public static final int LAYER_INDEX = 0;
    public static final String BLOCKED = "blocked";
    public static final int LAYER1_INDEX = 1;
    private final static boolean DEBUG = false;
    private final TiledMap map;
    private final AStarPathFinder pathFinder;
    private boolean[][] blocked;
    private int sx;
    private int sy;
    private int fx;
    private int fy;


    public GameMap() throws SlickException {
        map = new TiledMap("testdata/scroller/map.tmx");

        pathFinder = new AStarPathFinder(new TileBasedMap() {
            public int getWidthInTiles() {
                return map.getWidth();
            }

            public int getHeightInTiles() {
                return map.getHeight();
            }

            public void pathFinderVisited(int x, int y) {
            }

            public boolean blocked(PathFindingContext context, int tx, int ty) {
                return blocked[tx][ty];
            }

            public float getCost(PathFindingContext context, int tx, int ty) {
                return 1.f;
            }
        }, 100, false);


        sx = 0;
        sy = 0;
        fx = 0;
        fy = 0;

        // build a collision map based on tile properties in the TileD map
        blocked = new boolean[map.getWidth()][map.getHeight()];
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                int tileID = map.getTileId(x, y, LAYER_INDEX);
                blocked[x][y] = "true".equalsIgnoreCase(map.getTileProperty(tileID, BLOCKED, "false"));
                tileID = map.getTileId(x, y, LAYER1_INDEX);
                if (5 == tileID) {
                    sx = x;
                    sy = y;
                } else if (6 == tileID) {
                    fx = x;
                    fy = y;
                }
            }
        }


    }

    public int getStartX() {
        return sx * map.getTileWidth();
    }

    public int getStartY() {
        return sy * map.getTileHeight();
    }

    public boolean canMove(int newX, int newY) {
//        if (newX>map.getTileWidth()*10 || newY>map.getTileHeight()*10)
//                    return true;

        int tx = newX / map.getTileWidth();
        int ty = newY / map.getTileHeight();
//                int tileId = map.getTileId(tx, ty, LAYER_INDEX);
        return !blocked[tx][ty];
    }

    public Path buildPath(Mover mover) {
        return pathFinder.findPath(mover, sx, sy, fx, fy);
    }

    public boolean collideWith(Shape shape) {
        float centerX = shape.getCenterX();
        float centerY = shape.getCenterY();
//                if (centerX > map.getTileWidth() * 10 || centerY > map.getTileHeight() * 10)
//                    return false;

        int tx = (int) (centerX / map.getTileWidth());
        int ty = (int) (centerY / map.getTileHeight());
        return blocked[tx][ty];
    }

    public void handleCollision(Shape shape) {

    }

    private void _render(GameContainer gc, Graphics g) {
        int xtiles = gc.getWidth() / map.getTileWidth();
        int ytiles = 1 + (gc.getHeight() / map.getTileHeight());
        map.render(0, 0, 0, 0, xtiles, ytiles);
        if (DEBUG) {
            for (int x = 0; x < blocked.length && x < xtiles; x++) {
                boolean[] row = blocked[x];
                for (int y = 0; y < row.length && y < ytiles; y++) {
                    g.setColor(row[y] ? Color.red : Color.green);
                    g.drawRect(x * map.getTileWidth(), y * map.getTileHeight(), map.getTileWidth() - 1, map.getTileHeight() - 1);
                }
            }
        }
    }

    public void render(GameContainer gc, Graphics g, Path path) {
        _render(gc, g);

        if (path != null) {

            for (int i = 0; i < path.getLength(); i++) {
                Path.Step step = path.getStep(i);
                int stepX = step.getX();
                int stepY = step.getY();
                g.setColor(Color.pink);
                g.fillRect(
                        stepX * map.getTileWidth() + (map.getTileWidth() / 2) - 2,
                        stepY * map.getTileHeight() + (map.getTileHeight() / 2) - 2,
                        5, 5
                );

            }
        }

    }

    public void setFinish(int x, int y) {
        fx = x / map.getTileWidth();
        fy = y / map.getTileHeight();

    }
}
