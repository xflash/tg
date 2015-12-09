package org.xflash.astar;

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

    Cell selectedCell=Cell.WALL;

    public Toolbar(GUIContext gc, int x, int y) {
        this.x = x;
        this.y = y;

        for (final Cell cell : Cell.values()) {
            ClickableBox clickableBox = new ClickableBox(gc, cell.name().substring(0, 1), x, y);
            clickableBox.setBgColor(Cell.BG_COLORS[cell.ordinal()]);
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
        int h=TILE_HEIGHT;
        for (ClickableBox box : boxes) {
            box.render(gc, gfx);
            x1 += box.getWidth() + 5;
            h = box.getHeight();
        }
        gfx.setColor(Cell.BG_COLORS[selectedCell.ordinal()]);
        gfx.fillRect(x1, y+(h-TILE_HEIGHT)/2, TILE_WIDTH, TILE_HEIGHT);
    }

}
