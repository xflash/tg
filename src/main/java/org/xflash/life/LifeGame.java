package org.xflash.life;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.xflash.astar.gui.CheckBoxWidget;
import org.xflash.astar.gui.ClickableBox;
import org.xflash.astar.gui.ValueListener;

/**
 */
public class LifeGame extends BasicGame {

    private Grid grid;
    private boolean lifeRunning;
    private CheckBoxWidget widget;
    private ClickableBox acorn;
    private ClickableBox clear;

    public LifeGame() {
        super("LifeGame");
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        container.setShowFPS(false);

        grid = new Grid(container, 20, 20,
                20, 20, 15);

        int x = grid.getX() + grid.getWidth() + 20;
        int y = grid.getY();
        widget = new CheckBoxWidget(container, x, y, "Run", new ValueListener<Boolean>() {
            public void valueChanged(Boolean value) {
                lifeRunning = !lifeRunning;
            }
        });
        y += widget.getHeight() + 20;

        clear = new ClickableBox(container, "Clear", x, y, new ComponentListener() {
            public void componentActivated(AbstractComponent source) {
                grid.reset();
            }
        });
        y += clear.getHeight() + 20;

        acorn = new ClickableBox(container, "acorn", x, y, new ComponentListener() {
            public void componentActivated(AbstractComponent source) {
                grid.apply(5, 5, new char[][]{
                        {0, 1, 0, 0, 0, 0, 0},
                        {0, 0, 0, 1, 0, 0, 0},
                        {1, 1, 0, 0, 1, 1, 1},
                });
            }
        });
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        if (lifeRunning) grid.update(delta);
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        widget.render(container, g);
        clear.render(container, g);
        acorn.render(container, g);
        grid.render(container, g);

        g.setColor(Color.red);
        g.drawRect(grid.getX(), grid.getY(), grid.getWidth(), grid.getHeight());
    }
}
