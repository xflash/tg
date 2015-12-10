package org.xflash.astar;

import org.newdawn.slick.*;
import org.xflash.astar.gui.CheckBoxWidget;
import org.xflash.astar.gui.PlusMinus;
import org.xflash.astar.gui.ValueListener;

import java.util.HashSet;

/**
 */
public class AStarGame extends BasicGame {

    private PlayMap playMap;
    private int xm;
    private int ym;
    private CrawlerWave crawlerWave;
    private PlusMinus maxDistanceWidget;
    private CheckBoxWidget checkBoxWidget;
    private HashSet<PathFinderListener> pathFinderListeners = new HashSet<PathFinderListener>();
    private Toolbar toolbar;

    public AStarGame() {
        super("AStar");
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        playMap = new PlayMap(gc, 20, 100, 40, 25);
        playMap.setStart(2, 5);
        playMap.setFinish(18, 5);

        toolbar = new Toolbar(gc, 170, 20);

        maxDistanceWidget = new PlusMinus(gc, 350, 20, 50, new ValueListener<Integer>() {
            public void valueChanged(Integer value) {
                for (PathFinderListener pathFinderListener : pathFinderListeners) {
                    pathFinderListener.distanceChanged(value);
                }
            }
        });

        checkBoxWidget = new CheckBoxWidget(gc, maxDistanceWidget.getX() + maxDistanceWidget.getWidth() + 5, maxDistanceWidget.getY(), new ValueListener<Boolean>() {
            public void valueChanged(Boolean value) {
                for (PathFinderListener pathFinderListener : pathFinderListeners) {
                    pathFinderListener.diagonalAllowed(value);
                }
            }
        });

        crawlerWave = new CrawlerWave(gc, playMap, 5, new CrawerSpawnListener(){
            public void crawlerSpawned(Crawler crawler) {
                crawler.setDiagonalAllowed(checkBoxWidget.getValue());
                crawler.setMaxDistance(maxDistanceWidget.getValue());
                pathFinderListeners.add(crawler);
            }
        });



    }


    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        crawlerWave.update(gc, delta);
    }

    public void render(GameContainer gc, Graphics gfx) throws SlickException {
        playMap.render(gc, gfx);

        maxDistanceWidget.render(gc, gfx);
        gfx.drawString(String.format("max dist. = %d", maxDistanceWidget.getValue()),
                maxDistanceWidget.getX(),
                maxDistanceWidget.getY() - gfx.getFont().getLineHeight());

        checkBoxWidget.render(gc, gfx);
        crawlerWave.render(gc, gfx);

        toolbar.render(gc, gfx);
        toolbar.addListener(new ToolbarListener(){
            public void selectionChanged(Cell cell) {
                playMap.setSelectedCell(cell);
            }
        });
                


        gfx.setColor(Color.cyan);
        gfx.drawString(String.format("%d,%d", xm, ym), xm + 10, ym + 10);
    }

    public void mouseMoved(int oldx, int oldy, int xm, int ym) {
        this.xm=xm;
        this.ym=ym;
    }
    
    

}
