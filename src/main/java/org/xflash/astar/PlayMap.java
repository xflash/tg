package org.xflash.astar;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.InputAdapter;

/**
 */
public class PlayMap extends InputAdapter {

    public static final int TILE_WIDTH = 16;
    public static final int TILE_HEIGHT = 16;
    private final Cell[][] tiles;
    private final int w;
    private final int h;
    private final int xmap;
    private final int ymap;
    private final int xmap2;
    private final int ymap2;
    private int cellY;
    private int cellX;

    public PlayMap(int w, int h) {
        this.w = w;
        this.h = h;
        tiles = new Cell[h][w];
        xmap = 20;
        ymap = 100;
        xmap2 = this.xmap + w * TILE_WIDTH;
        ymap2 = this.ymap + h * TILE_HEIGHT;
    }

    public void render(GameContainer gc, Graphics gfx) {
        int yt = ymap;
        for (Cell[] row : tiles) {
            int xt = xmap;
            for (Cell cell : row) {
                if (Cell.WALL.equals(cell)) {
                    gfx.setColor(Color.darkGray);
                    gfx.fillRect(xt + 1, yt + 1, TILE_WIDTH - 1, TILE_HEIGHT - 1);
                } else if (Cell.START.equals(cell)) {
                    gfx.setColor(Color.green);
                    gfx.fillRect(xt, yt, TILE_WIDTH, TILE_HEIGHT);
                } else if (Cell.FINISH.equals(cell)) {
                    gfx.setColor(Color.blue);
                    gfx.fillRect(xt, yt, TILE_WIDTH, TILE_HEIGHT);
                }
                xt += TILE_WIDTH;
            }
            yt += TILE_HEIGHT;
        }

        if (cellX >= 0 && cellY >= 0) {
            gfx.setColor(Color.lightGray);
            gfx.drawRect(xmap + cellX * TILE_WIDTH, ymap + cellY * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
        }
        gfx.setColor(Color.red);
        gfx.drawRect(xmap - 1, ymap - 1, 2 + w * TILE_WIDTH, 2 + h * TILE_HEIGHT);
        gfx.drawString(String.format("%d,%d - %d,%d", xmap, ymap, xmap2, ymap2), 
                xmap, ymap - gfx.getFont().getLineHeight()-5);

    }

    public void setStart(int x, int y) {
        setCell(x, y, Cell.START);
    }

    public void setFinish(int x, int y) {
        setCell(x, y, Cell.FINISH);
    }

    private void setCell(int x, int y, Cell cell) {
        this.tiles[y][x] = cell;
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int xm, int ym) {
        if (!isOver(xm, ym)) {
            cellY = -1;
            cellX = -1;
            return;
        }

        cellY = (ym - ymap) / TILE_HEIGHT;
        cellX = (xm - xmap) / TILE_WIDTH;
    }

    @Override
    public void mousePressed(int button, int xm, int ym) {
        if (!isOver(xm, ym)) return;
        System.out.println("button = " + button + " - (" + xm + "," + ym + ")");
        int yt = (ym - ymap) / TILE_HEIGHT;
        int xt = (xm - xmap) / TILE_WIDTH;
        tiles[yt][xt] = button == 0 ? Cell.WALL : Cell.FREE;
    }

    private boolean isOver(int xm, int ym) {
        return xm >= this.xmap && ym >= this.ymap && xm < xmap2 && ym < ymap2;
    }
}
