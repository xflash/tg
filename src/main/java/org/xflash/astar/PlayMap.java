package org.xflash.astar;

import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import java.util.HashSet;
import java.util.Set;

/**
 */
public class PlayMap extends AbstractComponent implements TileBasedMap {

    public static final int TILE_WIDTH = 16;
    public static final int TILE_HEIGHT = 16;
    private final Cell[][] tiles;
    private final ClickableBox startBbox;
    private final ClickableBox finishBbox;
    private final ClickableBox wallBbox;
    private Point pos;
    private boolean[][] visited;
    private Point hover;
    private Point start;
    private Point finish;
    private int widthInTiles;
    private int heightInTiles;
    private final Set<MapListener> mapListeners=new HashSet<MapListener>();
    private boolean over;

    private Cell selectedCell=Cell.FREE;

    public PlayMap(GUIContext gc, int widthInTiles, int heightInTiles) {
        super(gc);
        this.widthInTiles = widthInTiles;
        this.heightInTiles = heightInTiles;
        tiles = new Cell[heightInTiles][widthInTiles];
        visited = new boolean[heightInTiles][widthInTiles];

        int x = 200;
        startBbox = new ClickableBox(gc, "S", x, 300);
        startBbox.addListener(new ComponentListener() {
            public void componentActivated(AbstractComponent source) {
                selectedCell = Cell.START;
            }
        });
        x += startBbox.getWidth() + 5;
        finishBbox = new ClickableBox(gc, "F", x, 300);
        finishBbox.addListener(new ComponentListener() {
            public void componentActivated(AbstractComponent source) {
                selectedCell = Cell.FINISH;
            }
        });

        x += finishBbox.getWidth() + 5;
        wallBbox = new ClickableBox(gc, "W", x, 300);
        wallBbox.addListener(new ComponentListener() {
            public void componentActivated(AbstractComponent abstractComponent) {
                selectedCell=Cell.WALL;
            }
        });
    }

    @Override
    public void setLocation(int x, int y) {
        pos = new Point(x, y);
    }


    @Override
    public void render(GUIContext gc, Graphics gfx) {

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
                if (visited[r][c]) {
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
                2 + widthInTiles * TILE_WIDTH,
                2 + heightInTiles * TILE_HEIGHT);
        gfx.drawString(String.format("%d,%d", pos.getX(), pos.getY()),
                pos.getX(), pos.getY() - gfx.getFont().getLineHeight() - 5);

        startBbox.render(gc, gfx);
        finishBbox.render(gc, gfx);
        drawCell(gfx, startBbox.getX() - startBbox.getWidth() - 5, startBbox.getY(), selectedCell);
        wallBbox.render(gc, gfx);

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
        setCell(tx, ty, Cell.START);
    }

    public void setFinish(int tx, int ty) {
        setCell(tx, ty, Cell.FINISH);
    }

    public Point getStart() {
        return start;
    }

    public Point getFinish() {
        return finish;
    }

    private void setCell(int tx, int ty, Cell cell) {

        if(cell==Cell.FINISH) {
            clearATile(Cell.FINISH);
            finish = new Point(tx, ty);
        }
        if(cell==Cell.START){
            clearATile(Cell.START);
            start = new Point(tx, ty);
        }


        tiles[ty][tx] = cell;
        for (MapListener mapListener : mapListeners) {
            mapListener.cellChanged(tx, ty, cell);
        }
    }

    private void clearATile(Cell cell) {
        for (int r = 0; r < tiles.length; r++) {
            Cell[] tile = tiles[r];
            for (int c = 0; c < tile.length; c++) {
                Cell cell1 = tile[c];
                if(cell.equals(cell1)) {
                    tiles[r][c]=Cell.FREE;
                }
            }
        }
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int xm, int ym) {

        setOver(Rectangle.contains(xm, ym, getX(), getY(), getWidth() - 1, getHeight() - 1));

        if (!isOver()) {
            hover = null;
            return;
        }

        hover = new Point((xm - pos.getX()) / TILE_WIDTH, (ym - pos.getY()) / TILE_HEIGHT);
    }


    @Override
    public void mousePressed(int button, int xm, int ym) {
        if (!isOver()) return;
//        System.out.println("button = " + button + " - (" + xm + "," + ym + ")");
        int xt = (xm - pos.getX()) / TILE_WIDTH;
        int yt = (ym - pos.getY()) / TILE_HEIGHT;
        setCell(xt, yt, button==0?selectedCell:Cell.FREE);

    }

    public int getWidthInTiles() {
        return widthInTiles;
    }

    public int getHeightInTiles() {
        return heightInTiles;
    }

    public void pathFinderVisited(int tx, int ty) {
        visited[ty][tx] = true;
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
        visited = new boolean[heightInTiles][widthInTiles];
    }

    @Override
    public int getX() {
        return pos.getX();
    }

    @Override
    public int getY() {
        return pos.getY();
    }

    @Override
    public int getWidth() {
        return widthInTiles * TILE_WIDTH;
    }

    @Override
    public int getHeight() {
        return heightInTiles * TILE_HEIGHT;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public void addMapListener(MapListener mapListener) {
        this.mapListeners.add(mapListener);
    }
}
