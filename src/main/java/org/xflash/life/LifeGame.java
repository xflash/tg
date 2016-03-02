package org.xflash.life;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.util.InputAdapter;
import org.xflash.astar.gui.CheckBoxWidget;
import org.xflash.astar.gui.ClickableBox;
import org.xflash.astar.gui.ValueListener;

import java.util.ArrayList;

/**
 */
public class LifeGame extends BasicGame {

    private Grid grid;
    private boolean lifeRunning;
    private ArrayList<AbstractComponent> widgets;


    public LifeGame() {
        super("LifeGame");
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        container.setShowFPS(false);

        int x = 20;
        int y = 20;
        widgets = new ArrayList<AbstractComponent>();

        AbstractComponent widget;

        widget = new CheckBoxWidget(container, x, y, "Run", new ValueListener<Boolean>() {
            public void valueChanged(Boolean value) {
                lifeRunning = !lifeRunning;
            }
        });
        x += 0;
        y += widget.getHeight() + 20;
        widgets.add(widget);
        widget = new ClickableBox(container, "Clear", x, y, new ComponentListener() {
            public void componentActivated(AbstractComponent source) {
                grid.reset();
            }
        });
        x += 0;
        y += widget.getHeight() + 20;
        widgets.add(widget);

        for (final Pattern pattern : Pattern.patterns) {
            widget = new ClickableBox(container, pattern.getName(), x, y, new ComponentListener() {
                public void componentActivated(AbstractComponent source) {
//                    grid.apply(5, 5, pattern.getChars());
                    grid.setMousePattern(pattern.getChars());
                }
            });
            widgets.add(widget);
            y += widget.getHeight() + 10;
        }

        widgets.add(new ClickableBox(container, "ROT", x, y, new ComponentListener() {
            public void componentActivated(AbstractComponent source) {
                grid.rotateMousePattern(true);
            }
        }));

        container.getInput().addKeyListener(new InputAdapter() {
            @Override
            public void keyReleased(int key, char c) {
                if (key == Input.KEY_RIGHT)
                    grid.rotateMousePattern(true);
                if (key == Input.KEY_LEFT)
                    grid.rotateMousePattern(false);
            }
        });

        grid = new Grid(container,
                100, 20,
                100, 100, 5);

        grid.setMousePattern(new char[][]{{1}});

    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        if (lifeRunning) grid.update(delta);
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        for (AbstractComponent widget : widgets) {
            widget.render(container, g);
        }

        grid.render(container, g);

        g.setColor(Color.red);
        g.drawRect(grid.getX(), grid.getY(), grid.getWidth(), grid.getHeight());
    }
}
