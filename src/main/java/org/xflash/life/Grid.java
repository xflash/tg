package org.xflash.life;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;
import org.xflash.utils.ArrayUtils;

/**
 */
public class Grid extends AbstractComponent {

    private Vector2f pos;
    private boolean[][] cells = new boolean[][]{};
    private int w;
    private int h;
    private int size;
    private int xm;
    private int ym;
    private int age;
    private Boolean mouseDragging;
    private char[][] mousePattern;

    public Grid(GUIContext container, int x, int y, int w, int h, int size) {
        super(container);
        this.w = w;
        this.h = h;
        this.size = size;
        setLocation(x, y);
        reset();
//        acorn();
    }

    protected void reset() {
        cells = new boolean[w][h];
    }

    public void apply(int offsetX, int offsetY, char[][] newcells) {
        if ((offsetY + newcells.length) > h || (offsetX + newcells[0].length) > w) return;
//        reset();
        for (int i = 0; i < newcells.length; i++) {
            for (int j = 0; j < newcells[i].length; j++) {
                char c = newcells[i][j];
                if (c != 0) cells[offsetX + j][offsetY + i] = true;
            }
        }
    }


    @Override
    public void setLocation(int x, int y) {
        pos = new Vector2f(x, y);
    }


    @Override
    public int getX() {
        return (int) pos.x;
    }

    @Override
    public int getY() {
        return (int) pos.y;
    }

    @Override
    public int getWidth() {
        return w * size;
    }

    @Override
    public int getHeight() {
        return h * size;
    }

    @Override
    public void render(GUIContext container, Graphics g) throws SlickException {
        g.translate(pos.x, pos.y);
        for (int gx = 0; gx < cells.length; gx++) {
            for (int gy = 0; gy < cells[gx].length; gy++) {
                int x1 = gx * size + 0;
                int y1 = gy * size + 0;
                if (cells[gx][gy]) {
                    g.setColor(Color.cyan);
                    g.fillRect(x1, y1, size - 0, size - 0);
                }
                if (gx == xm && gy == ym) {
                    renderPattern(g, xm, ym);
                }
                g.setColor(Color.lightGray);
                g.drawRect(gx * size, gy * size, size, size);

//                g.setColor(Color.green);
//                int S = calcS(gx, gy);
//                g.drawString("" + S, gx * size, gy * size);

            }
        }
        g.resetTransform();
    }

    private void renderPattern(Graphics g, int xm, int ym) {
        g.setColor(Color.orange);
//        g.fillRect(xm, ym, size - 0, size - 0);
        for (int y = 0; y < mousePattern.length; y++) {
            for (int x = 0; x < mousePattern[y].length; x++) {
                char c = mousePattern[y][x];
                if (c != 0) {
                    if ((xm + x) < w && (ym + y) < h)
                        g.fillRect((xm + x) * size, (ym + y) * size, size - 0, size - 0);
                }
            }
        }
    }
//
//    @Override
//    public void mousePressed(int button, int x, int y) {
//        super.mousePressed(button, x, y);
//        if (Rectangle.contains(x, y, getX(), getY(), getWidth(), getHeight())) {
//            int gx = Math.min(w - 1, (int) ((x - pos.x) / size));
//            int gy = Math.min(h - 1, (int) ((y - pos.y) / size));
//            cells[gx][gy] = !cells[gx][gy];
//            mouseDragging = cells[gx][gy];
//        } else
//            mouseDragging = null;
//    }
//
//    @Override
//    public void mouseDragged(int oldx, int oldy, int x, int y) {
//        if (Rectangle.contains(x, y, getX(), getY(), getWidth(), getHeight())) {
//            int gx = Math.min(w - 1, (int) ((x - pos.x) / size));
//            int gy = Math.min(h - 1, (int) ((y - pos.y) / size));
//            cells[gx][gy] = mouseDragging;
//            xm = -1;
//            ym = -1;
//        }
//    }

    @Override
    public void mouseReleased(int button, int x, int y) {
//        super.mouseReleased(button, x, y);

        if (Rectangle.contains(x, y, getX(), getY(), getWidth(), getHeight())) {
            if (mouseDragging != null) {
                mouseDragging = null;
            } else {
                int gx = Math.min(w - 1, (int) ((x - pos.x) / size));
                int gy = Math.min(h - 1, (int) ((y - pos.y) / size));
//                cells[gx][gy] = !cells[gx][gy];
                apply(gx, gy, mousePattern);
            }
        }
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        if (Rectangle.contains(newx, newy, getX(), getY(), getWidth(), getHeight())) {
            xm = Math.min(w - 1, (int) ((newx - pos.x) / size));
            ym = Math.min(h - 1, (int) ((newy - pos.y) / size));
        } else {
            xm = -1;
            ym = -1;
        }
    }

    public void update(int delta) {
        age += delta;
        if (age >= 100) {
            age = 0;
            updateCells();
        }
    }

    private void updateCells() {
        boolean[][] nextCells = new boolean[w][h];
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[x].length; y++) {
                int S = calcS(x, y);
                nextCells[x][y] = (S == 3) || (cells[x][y] && S == 2);
            }
        }
        cells = nextCells;
    }

    private int calcS(int x, int y) {
        int S = 0;
        S += add(x - 1, y - 1); //NW
        S += add(x, y - 1); //N
        S += add(x + 1, y - 1); //NE
        S += add(x + 1, y); //E
        S += add(x + 1, y + 1); //SE
        S += add(x, y + 1); //S
        S += add(x - 1, y + 1); //SW
        S += add(x - 1, y); //W
        return S;
    }

    private int add(int x, int y) {
        if (x < 0 || y < 0 || x >= w || y >= h) return 0;
        return cells[x][y] ? 1 : 0;
    }


    public void setMousePattern(char[][] mousePattern) {
        this.mousePattern = mousePattern;
    }

    public void rotateMousePattern(boolean cw) {
        if (mousePattern == null) return;
        mousePattern = cw ?
                ArrayUtils.rotateCW(mousePattern)
                : ArrayUtils.rotateCCW(mousePattern);
    }

}
