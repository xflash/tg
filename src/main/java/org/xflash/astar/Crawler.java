package org.xflash.astar;

import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;

/**
 */
public class Crawler implements Mover, PathFinderListener {
    private final PlayMap playMap;
    private int timr = 0;
    private Path path;
    private int pathIdx;
    private Path.Step pathStep;
    private Point start;
    private Point finish;
    private Integer maxDistance;
    private boolean diagonalAllowed;
    private AStarPathFinder pathFinder;


    public Crawler(GameContainer gc, PlayMap playMap) {
        this.playMap = playMap;
        
        playMap.addMapListener(new MapListener() {
            public void cellChanged(int tx, int ty, Cell cell) {
                updatePath();
                if(Cell.FINISH.equals(cell)) {
                    finish.setX(tx);
                    finish.setY(ty);
                    updatePath();
                }
                    
            }
        });

    }

    
    public void update(GameContainer gc, int frame) {
        timr += frame;
        if (timr > 500) {
            if (path != null) {
                pathIdx = Math.min(path.getLength(), pathIdx + 1);
                pathStep = path.getStep(pathIdx - 1);
                start.setX(pathStep.getX());
                start.setY(pathStep.getY());
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
    }


    public void spawn(Point start, Point finish) {
        this.start = new Point(start);
        this.finish = new Point(finish);
        updatePathFinder();

    }

    public void distanceChanged(Integer newDistance) {
        maxDistance = newDistance;
        updatePathFinder();
    }

    public void diagonalAllowed(boolean diagonalAllowed) {
        this.diagonalAllowed = diagonalAllowed;
        updatePathFinder();
    }


    private void updatePathFinder() {
        pathFinder = new AStarPathFinder(this.playMap, maxDistance, diagonalAllowed);
        updatePath();
    }

    private Path updatePath() {
//        playMap.clearVisit();
        System.out.println("Crawler = " + start);
        path = pathFinder.findPath(this, start.getX(), start.getY(), finish.getX(), finish.getY());
        System.out.println("UpdatePath : " + (path == null ? "none" : " path length " + path.getLength()));
        pathIdx = 0;
        return path;
    }

    public void setDiagonalAllowed(boolean diagonalAllowed) {
        this.diagonalAllowed = diagonalAllowed;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }
}
