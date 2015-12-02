package org.xflash.astar;

import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFinder;

/**
 */
public class Crawler implements Mover{
    private final PlayMap playMap;
    private PathFinder pathFinder;
    private int maxSearchDistance;
    private Path path;
    int timr=0;
    private int pathIdx;
    private Path.Step pathStep;

    public Crawler(PlayMap playMap) {
        this.playMap = playMap;
        maxSearchDistance = 50;
        createPathFinder();
        
    }

    private void createPathFinder() {
        System.out.println("createPathFinder with maxSearchDistance=" + maxSearchDistance);
        pathFinder=new AStarPathFinder(playMap, this.maxSearchDistance, true);
        updatePath();
    }

    public void update(GameContainer gc, int frame) {
       timr+=frame;
        if(timr>1000) {
            if(path!=null){
                pathIdx = Math.min(path.getLength(), pathIdx + 1);
                pathStep = path.getStep(pathIdx-1);
            }
            timr = 0;
        }
        
    }

    public void render(GameContainer gc, Graphics gfx) {
        Point pt=playMap.getPos();
        if(path!=null) {
            for (int i = 0; i < path.getLength(); i++) {
                Path.Step step = path.getStep(i);
                gfx.setColor(Color.white);
                gfx.drawRect(
                        pt.getX()+step.getX()*playMap.getTileWidth(),
                        pt.getY()+step.getY()*playMap.getTileHeight(),
                        playMap.getTileWidth(),
                        playMap.getTileHeight()
                        );
            }
        }
        
        if(pathStep!=null) {
            Point foe=new Point(pt);
            gfx.drawOval(
                    foe.getX()+pathStep.getX()*playMap.getTileWidth() + 4,
                    foe.getY()+pathStep.getY()*playMap.getTileHeight() + 4, 
                    9, 9);
        }
    }

    public Path updatePath() {
        System.out.println("UpdatePath");
        Point start = playMap.getStart();
        Point finish = playMap.getFinish();
        playMap.clearVisit();
        path = pathFinder.findPath(this, start.getX(), start.getY(), finish.getX(), finish.getY());
        pathIdx=0;
        return path;
    }

    public void increaseDepth() {
        this.maxSearchDistance+=5;
        createPathFinder();
    }

    public void decreaseDepth() {
        this.maxSearchDistance-=5;
        createPathFinder();
    }

    public int getMaxSearchDistance() {
        return maxSearchDistance;
    }

    public void setMaxSearchDistance(int maxSearchDistance) {
        this.maxSearchDistance = maxSearchDistance;
    }
}
