package org.xflash.collision;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.*;

/**
 */
public class GameMap implements CanMove, Collidable {
    public static final int LAYER_INDEX = 0;
    public static final String BLOCKED = "blocked";
    public static final int LAYER1_INDEX = 1;
    private final static boolean DEBUG = false;
    private final TiledMap map;
    private final AStarPathFinder pathFinder;
    private final int widthInTiles;
    private final int heightInTiles;
    private final int topOffsetInTiles;
    private final int leftOffsetInTiles;
    private boolean[][] blocked;
    private int sx;
    private int sy;
    private int fx;
    private int fy;
    private final int mapTileWidth;
    private final int mapTileHeight;


    public GameMap(GameContainer gc) throws SlickException {
        map = new TiledMap("testdata/scroller/map.tmx");

        mapTileWidth = map.getTileWidth();
        mapTileHeight = map.getTileHeight();

        // caculate some layout values for rendering the tilemap. How many tiles
        // do we need to render to fill the screen in each dimension and how far is
        // it from the centre of the screen
        widthInTiles = gc.getWidth() / mapTileWidth;
        heightInTiles = gc.getHeight() / mapTileHeight;
        topOffsetInTiles = heightInTiles / 2;
        leftOffsetInTiles = widthInTiles / 2;


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
        for (int x = 0; x < mapTileWidth; x++) {
            for (int y = 0; y < mapTileHeight; y++) {
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

        int tx = newX / mapTileWidth;
        int ty = newY / mapTileHeight;
//                int tileId = map.getTileId(tx, ty, LAYER_INDEX);
        return !blocked[tx][ty];
    }

    public Path buildPath(Mover mover, float centerX, float centerY) {
        return pathFinder.findPath(mover, (int)centerX/mapTileWidth, (int)centerY/mapTileHeight, fx, fy);
    }

    public boolean collideWith(Shape shape) {
        float centerX = shape.getCenterX();
        float centerY = shape.getCenterY();
//                if (centerX > map.getTileWidth() * 10 || centerY > map.getTileHeight() * 10)
//                    return false;

        int tx = (int) (centerX / mapTileWidth);
        int ty = (int) (centerY / mapTileHeight);
        return blocked[tx][ty];
    }

    public void handleCollision(Shape shape) {

    }

    public void render(GameContainer gc, Graphics g, float centerX, float centerY) {

        // draw the appropriate section of the tilemap based on the centre of the center coord
        int playerTileX = (int) centerX/mapTileWidth;
        int playerTileY = (int) centerY/mapTileHeight;



        int xtiles = gc.getWidth() / map.getTileWidth();
        int ytiles = 1 + (gc.getHeight() / map.getTileHeight());
        map.render(0, 0, 0, 0, xtiles, ytiles, LAYER_INDEX, false);
//
//        int xtiles = playerTileX - leftOffsetInTiles - 1;
//        int ytiles = playerTileY - topOffsetInTiles - 1;
//        map.render(0, 0,
//                sx,
//                sy,
//                widthInTiles + 3,
//                heightInTiles + 3);


        if (DEBUG) {
            for (int x = 0; x < blocked.length && x < xtiles; x++) {
                boolean[] row = blocked[x];
                for (int y = 0; y < row.length && y < ytiles; y++) {
                    g.setColor(row[y] ? Color.red : Color.green);
                    g.drawRect(x * map.getTileWidth(), y * map.getTileHeight(), map.getTileWidth() - 1, map.getTileHeight() - 1);
                }
            }
        }

//        g.translate(gc.getWidth()/2 - playerTileX * mapTileWidth, gc.getHeight() / 2 - playerTileY * mapTileHeight);

    }

    public void renderPath(GameContainer gc, Graphics g, Path path) {
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
