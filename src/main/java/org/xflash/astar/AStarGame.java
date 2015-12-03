package org.xflash.astar;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;

/**
 */
public class AStarGame extends BasicGame {

    private PlayMap playMap;
    private int xm;
    private int ym;
    private Crawler crawler;
    private TextField textField;

    public AStarGame() {
        super("AStar");
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        playMap = new PlayMap(gc, 20, 10);
        playMap.setStart(2, 5);
        playMap.setFinish(18, 5);
        playMap.setLocation(20, 100);

        crawler = new Crawler(gc, playMap, 400, 200);
    }

    @Override
    public void update(GameContainer gc, int frame) throws SlickException {
        playMap.update(gc, frame);
        crawler.update(gc, frame);
    }

    public void render(GameContainer gc, Graphics gfx) throws SlickException {
        playMap.render(gc, gfx);
        
        gfx.setColor(Color.cyan);
        gfx.drawString(String.format("%d,%d", xm, ym), xm + 10, ym + 10);


        crawler.render(gc, gfx);
    }

    public void mouseMoved(int oldx, int oldy, int xm, int ym) {
        this.xm=xm;
        this.ym=ym;
    }

}
