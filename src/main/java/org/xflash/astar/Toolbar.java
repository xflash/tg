package org.xflash.astar;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;

import java.util.HashSet;

/**
 * @author rcoqueugniot
 * @since 04.12.15
 */
public class Toolbar {

    public static final int TILE_WIDTH = 16;
    public static final int TILE_HEIGHT = 16;

    private final HashSet<ClickableBox> boxes = new HashSet<ClickableBox>();
    private final int x;
    private final int y;

    Cell selectedCell=Cell.FREE;

    public Toolbar(GUIContext gc, int x, int y) {
        this.x = x;
        this.y = y;

        for (final Cell cell : Cell.values()) {
            ClickableBox clickableBox = new ClickableBox(gc, cell.name().substring(0, 1), x, y);
            boxes.add(clickableBox);
            clickableBox.addListener(new ComponentListener() {
                public void componentActivated(AbstractComponent abstractComponent) {
                    selectedCell = cell;
                }
            });
            x += clickableBox.getWidth() + 5;
        }
    }

    public void render(GUIContext gc, Graphics gfx) {
        int x1=x;
        for (ClickableBox box : boxes) {
            box.render(gc, gfx);
            x1 += box.getWidth() + 5;
        }
        drawCell(gfx, x1, y, selectedCell);
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
}
