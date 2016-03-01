package org.xflash.life;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

import java.util.Arrays;

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
        reset();
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
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                int x1 = i * size + 0;
                int y1 = j * size + 0;
                if (cells[i][j]) {
                    g.setColor(Color.cyan);
                    g.fillRect(x1, y1, size - 0, size - 0);
                }
                if (i == xm && j == ym) {
                    g.setColor(Color.orange);
                    g.fillRect(x1, y1, size - 0, size - 0);
                }
                g.setColor(Color.lightGray);
                g.drawRect(i * size, j * size, size, size);
            }
        }
        g.resetTransform();
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        super.mousePressed(button, x, y);
        if (Rectangle.contains(x, y, getX(), getY(), getWidth(), getHeight())) {
            int gx = Math.min(w - 1, (int) ((x - pos.x) / size));
            int gy = Math.min(h - 1, (int) ((y - pos.y) / size));
            cells[gx][gy] = !cells[gx][gy];
            mouseDragging = cells[gx][gy];
        } else
            mouseDragging = null;
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int x, int y) {
        if (Rectangle.contains(x, y, getX(), getY(), getWidth(), getHeight())) {
            int gx = Math.min(w - 1, (int) ((x - pos.x) / size));
            int gy = Math.min(h - 1, (int) ((y - pos.y) / size));
            cells[gx][gy] = mouseDragging;
            xm = -1;
            ym = -1;
        }
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
//        super.mouseReleased(button, x, y);

        if (Rectangle.contains(x, y, getX(), getY(), getWidth(), getHeight())) {
            if (mouseDragging != null) {
                mouseDragging = null;
            } else {
                int gx = Math.min(w - 1, (int) ((x - pos.x) / size));
                int gy = Math.min(h - 1, (int) ((y - pos.y) / size));
                cells[gx][gy] = !cells[gx][gy];
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
        if (age >= 1000) {
            age = 0;
            updateCells();
        }
    }

    private void updateCells() {
// Si l'on considère la somme de ses côtés S et E l'état initial de la cellule,
// il est possible de calculer son état suivant avec: (S = 3) OU (E = 1 ET S = 2).
// Avec 1 pour une cellule vivante et 0 pour une cellule morte.

        boolean[][] nextCells = Arrays.copyOf(cells, cells.length);

        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[x].length; y++) {
                int S = calcS(x, y);
//                System.out.println(String.format("S(%d,%d)=%d", x, y, S));
                boolean E = cells[x][y];
                nextCells[x][y] = (S == 3) || (E && S == 2);
            }
        }
        cells = nextCells;
    }

    private int calcS(int x, int y) {
        int S = 0;

        S += addNW(x, y);
        S += addN(x, y);
        S += addNE(x, y);
        S += addE(x, y);
        S += addSE(x, y);
        S += addS(x, y);
        S += addSW(x, y);
        S += addW(x, y);

        return S;
    }

    private int addNW(int x, int y) {
        return x > 0 && y > 0 ? cells[x - 1][y - 1] ? 1 : 0 : 0;
    }

    private int addN(int x, int y) {
        return y > 0 ? cells[x][y - 1] ? 1 : 0 : 0;
    }

    private int addNE(int x, int y) {
        return x < w - 1 && y > 0 ? cells[x + 1][y - 1] ? 1 : 0 : 0;
    }

    private int addE(int x, int y) {
        return x < w - 1 ? cells[x + 1][y] ? 1 : 0 : 0;
    }

    private int addSE(int x, int y) {
        return x < w - 1 && y < h - 1 ? cells[x + 1][y + 1] ? 1 : 0 : 0;
    }

    private int addS(int x, int y) {
        return y < h - 1 ? cells[x][y + 1] ? 1 : 0 : 0;
    }

    private int addSW(int x, int y) {
        return x > 0 && y < h - 1 ? cells[x - 1][y + 1] ? 1 : 0 : 0;
    }

    private int addW(int x, int y) {
        return x > 0 ? cells[x - 1][y] ? 1 : 0 : 0;
    }


}
