package org.xflash.astar;

import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFinder;

/**
 */
public class Crawler implements Mover {
    private final PlayMap playMap;
    private final PlusMinusWidget widget;
    int timr = 0;
    private PathFinder pathFinder;
    private Path path;
    private int pathIdx;
    private Path.Step pathStep;
    private boolean allowDiagMovement = false;
    private final Rectangle cb;


    public Crawler(GameContainer gc, PlayMap playMap, int x, int y) {
        this.playMap = playMap;
        widget = new PlusMinusWidget(gc, x, y, 50, new ValueListener(){
            public void valueChanged(int value) {
                createPathFinder();
            }
        });
        createPathFinder();
        playMap.addMapListener(new MapListener() {
            public void cellChanged(int xt, int yt, Cell cell) {
                updatePath();
            }
        });

        cb = new Rectangle(x, y, 20, 20);
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

        widget.render(gc, gfx);
        gfx.drawString(String.format("max dist. = %d", widget.getValue()),
                widget.getX(),
                widget.getY() - gfx.getFont().getLineHeight());

        
        gfx.draw(cb);
        if(allowDiagMovement)
            gfx.drawString("X", cb.getX() + gfx.getFont().getWidth("X") / 2, cb.getY());
//        gfx.drawString(String.format("max dist. = %d", getMaxSearchDistance()), down.getX(), down.getY() - font.getLineHeight());

    }

    private void createPathFinder() {
        System.out.println("createPathFinder with maxSearchDistance=" + widget.getValue());
        pathFinder = new AStarPathFinder(playMap, widget.getValue(), allowDiagMovement);
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
