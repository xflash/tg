package org.xflash.astar;

import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFinder;

/**
 */
public class Crawler implements Mover {
    private final PlayMap playMap;
    private final PlusMinus maxDistanceWidget;
    private final CheckBoxWidget checkBoxWidget;
    private int timr = 0;
    private PathFinder pathFinder;
    private Path path;
    private int pathIdx;
    private Path.Step pathStep;


    public Crawler(GameContainer gc, PlayMap playMap, int x, int y) {
        this.playMap = playMap;
        maxDistanceWidget = new PlusMinus(gc, x, y, 50, new ValueListener<Integer>(){
            public void valueChanged(Integer value) {
                createPathFinder();
            }
        });

        checkBoxWidget = new CheckBoxWidget(gc, x + maxDistanceWidget.getWidth() + 5, y, new ValueListener<Boolean>(){
            public void valueChanged(Boolean value) {
                updatePath();
            }
        });

        playMap.addMapListener(new MapListener() {
            public void cellChanged(int xt, int yt, Cell cell) {
                updatePath();
            }
        });
        createPathFinder();

    }

    public void update(GameContainer gc, int frame) {
        timr += frame;
        if (timr > 500) {
            if (path != null) {
                pathIdx = Math.min(path.getLength(), pathIdx + 1);
                pathStep = path.getStep(pathIdx - 1);
            }
            timr = 0;
        }
    }

    public void render(GameContainer gc, Graphics gfx) throws SlickException {
        Point pt = playMap.getPos();
        if (path != null) {
            for (int i = 0; i < path.getLength(); i++) {
                Path.Step step = path.getStep(i);
                gfx.setColor(Color.white);
                gfx.drawRect(
                        pt.getX() + step.getX() * playMap.getTileWidth(),
                        pt.getY() + step.getY() * playMap.getTileHeight(),
                        playMap.getTileWidth(),
                        playMap.getTileHeight()
                );
            }
        }

        if (pathStep != null) {
            Point foe = new Point(pt);
            gfx.drawOval(
                    foe.getX() + pathStep.getX() * playMap.getTileWidth() + 4,
                    foe.getY() + pathStep.getY() * playMap.getTileHeight() + 4,
                    9, 9);
        }

        maxDistanceWidget.render(gc, gfx);
        gfx.drawString(String.format("max dist. = %d", maxDistanceWidget.getValue()),
                maxDistanceWidget.getX(),
                maxDistanceWidget.getY() - gfx.getFont().getLineHeight());

        checkBoxWidget.render(gc, gfx);
        
//        gfx.drawString(String.format("max dist. = %d", getMaxSearchDistance()), down.getX(), down.getY() - font.getLineHeight());

    }

    private void createPathFinder() {
        System.out.println("createPathFinder with maxSearchDistance=" + maxDistanceWidget.getValue());
        pathFinder = new AStarPathFinder(playMap, maxDistanceWidget.getValue(), checkBoxWidget.getValue());
        updatePath();
    }

    public Path updatePath() {
        Point start = playMap.getStart();
        Point finish = playMap.getFinish();
        playMap.clearVisit();
        path = pathFinder.findPath(this, start.getX(), start.getY(), finish.getX(), finish.getY());
        System.out.println("UpdatePath : " + (path == null ? "none" : " path length " + path.getLength()));
        pathIdx = 0;
        return path;
    }

}
