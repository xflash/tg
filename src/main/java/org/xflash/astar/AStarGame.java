package org.xflash.astar;

import org.newdawn.slick.*;

/**
 */
public class AStarGame extends BasicGame {

    private PlayMap playMap;
    private int xm;
    private int ym;

    public AStarGame() {
        super("AStar");
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        playMap = new PlayMap(20, 10);
        gc.getInput().addMouseListener(playMap);
        
//        Player player = new Player();
        playMap.setStart(2, 5);
        playMap.setFinish(18, 5);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
    }

    public void mouseMoved(int oldx, int oldy, int xm, int ym) {
        this.xm=xm;
        this.ym=ym;
    }

    public void render(GameContainer gc, Graphics gfx) throws SlickException {
        playMap.render(gc, gfx);
        
        gfx.setColor(Color.cyan);
        gfx.drawString(String.format("%d,%d",xm,ym), xm+10,ym+10);
    }
}
