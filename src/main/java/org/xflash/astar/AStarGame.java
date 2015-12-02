package org.xflash.astar;

import org.lwjgl.util.Dimension;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;

/**
 */
public class AStarGame extends BasicGame {

    private PlayMap playMap;
    private int xm;
    private int ym;
    private Crawler crawler;
    private Rectangle up;
    private Rectangle down;

    public AStarGame() {
        super("AStar");
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        playMap = new PlayMap(new Dimension(20, 10));
        gc.getInput().addMouseListener(playMap);
        playMap.setStart(2, 5);
        playMap.setFinish(18, 5);

        crawler = new Crawler(playMap);

        down = new Rectangle(400, 200, 20, 20);
        up = new Rectangle(425, 200, 20, 20);


    }

    @Override
    public void update(GameContainer gc, int frame) throws SlickException {
        crawler.update(gc, frame);
    }

    public void render(GameContainer gc, Graphics gfx) throws SlickException {
        playMap.render(gc, gfx);
        
        gfx.setColor(Color.cyan);
        gfx.drawString(String.format("%d,%d", xm, ym), xm + 10, ym + 10);

        Font font = gfx.getFont();
        gfx.draw(up);
        gfx.drawString("+", up.getX()+ font.getWidth("+")/2, up.getY());
        gfx.draw(down);
        gfx.drawString("-", down.getX()+ font.getWidth("-")/2, down.getY());
        gfx.drawString(String.format("max dist. = %d",crawler.getMaxSearchDistance()), down.getX(), down.getY()-font.getLineHeight());
        

        crawler.render(gc, gfx);
    }

    public void mouseMoved(int oldx, int oldy, int xm, int ym) {
        this.xm=xm;
        this.ym=ym;
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        if(up.contains(x,y)) {
            crawler.increaseDepth();
        } else if(down.contains(x,y)){
            crawler.decreaseDepth();
        }
        else crawler.updatePath();
    }
}
