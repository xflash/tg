package org.xflash.astar;

import org.lwjgl.util.Dimension;
import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.InputAdapter;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

/**
 */
public class PlayMap extends InputAdapter implements TileBasedMap {

    public static final int TILE_WIDTH = 16;
    public static final int TILE_HEIGHT = 16;
    private final Cell[][] tiles;
    private boolean[][] visited;
    private final Dimension dimension;
    private final Point pos;
    private final Point coord2;
    private Point hover;
    private Point start;
    private Point finish;

    public PlayMap(Dimension dim) {
        this.dimension = dim;
        tiles = new Cell[dim.getHeight()][dim.getWidth()];
        visited = new boolean[dim.getHeight()][dim.getWidth()];
        pos = new Point(20, 100);
        coord2 = new Point(pos);
        coord2.translate(dim.getWidth() * TILE_WIDTH, dim.getHeight() * TILE_HEIGHT);
    }


    public void render(GameContainer gc, Graphics gfx) {
        int yt = pos.getY();
        for (Cell[] row : tiles) {
            int xt = pos.getX();
            for (Cell cell : row) {
                drawCell(gfx, xt, yt, cell);
                xt += TILE_WIDTH;
            }
            yt += TILE_HEIGHT;
        }

        for (int r = 0; r < visited.length; r++) {
            boolean[] booleans = visited[r];
            for (int c = 0; c < booleans.length; c++) {
                if(visited[r][c]) {
                    gfx.setColor(Color.magenta);
                    gfx.drawRect(pos.getX() + c * TILE_WIDTH,
                            pos.getY() + r * TILE_HEIGHT,
                            TILE_WIDTH, TILE_HEIGHT);
                }
                
            }
        }

        if (hover != null) {
            gfx.setColor(Color.lightGray);
            gfx.drawRect(pos.getX() + hover.getX() * TILE_WIDTH,
                    pos.getY() + hover.getY() * TILE_HEIGHT,
                    TILE_WIDTH, TILE_HEIGHT);
        }
        gfx.setColor(Color.red);
        gfx.drawRect(pos.getX() - 1, pos.getY() - 1,
                2 + dimension.getWidth() * TILE_WIDTH, 2 + dimension.getHeight() * TILE_HEIGHT);
        gfx.drawString(String.format("%d,%d - %d,%d", pos.getX(), pos.getY(), coord2.getX(), coord2.getY()),
                pos.getX(), pos.getY() - gfx.getFont().getLineHeight() - 5);

    }

    public void drawCell(Graphics gfx, int x, int y, Cell cell) {
        if (Cell.WALL.equals(cell)) {
            gfx.setColor(Color.darkGray);
            gfx.fillRect(x + 1, y + 1, TILE_WIDTH - 1, TILE_HEIGHT - 1);
        } else if (Cell.START.equals(cell)) {
            gfx.setColor(Color.green);
            gfx.fillRect(x, y, TILE_WIDTH, TILE_HEIGHT);
        } else if (Cell.FINISH.equals(cell)) {
            gfx.setColor(Color.blue);
            gfx.fillRect(x, y, TILE_WIDTH, TILE_HEIGHT);
        }
    }

    public void setStart(int tx, int ty) {
        start = new Point(tx, ty);
        setCell(tx, ty, Cell.START);
    }

    public void setFinish(int tx, int ty) {
        finish = new Point(tx, ty);
        setCell(tx, ty, Cell.FINISH);
    }

    public Point getStart() {
        return start;
    }

    public Point getFinish() {
        return finish;
    }

    private void setCell(int tx, int ty, Cell cell) {
        this.tiles[ty][tx] = cell;
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int xm, int ym) {
        if (!isOver(xm, ym)) {
            hover = null;
            return;
        }

        hover = new Point((xm - pos.getX()) / TILE_WIDTH, (ym - pos.getY()) / TILE_HEIGHT);
    }

    @Override
    public void mousePressed(int button, int xm, int ym) {
        if (!isOver(xm, ym)) return;
//        System.out.println("button = " + button + " - (" + xm + "," + ym + ")");
        int xt = (xm - pos.getX()) / TILE_WIDTH;
        int yt = (ym - pos.getY()) / TILE_HEIGHT;
        if(tiles[yt][xt]==Cell.START || tiles[yt][xt]==Cell.FINISH)return;
        tiles[yt][xt] = button == 0 ? Cell.WALL : Cell.FREE;
    }

    private boolean isOver(int xm, int ym) {
        return xm >= this.pos.getX() && ym >= this.pos.getY()
                && xm < coord2.getX() && ym < coord2.getY();
    }

    public int getWidthInTiles() {
        return dimension.getWidth();
    }

    public int getHeightInTiles() {
        return dimension.getHeight();
    }

    public void pathFinderVisited(int tx, int ty) {
        visited[ty][tx]=true;
    }

    public boolean blocked(PathFindingContext pathFindingContext, int tx, int ty) {
        return Cell.WALL.equals(tiles[ty][tx]);
    }

    public float getCost(PathFindingContext pathFindingContext, int tx, int ty) {
        return 1.0f;
    }

    public Point getPos() {
        return pos;
    }

    public int getTileWidth() {
        return TILE_WIDTH;
    }

    public int getTileHeight() {
        return TILE_HEIGHT;
    }

    public void clearVisit() {
        visited = new boolean[dimension.getHeight()][dimension.getWidth()];
    }
}
