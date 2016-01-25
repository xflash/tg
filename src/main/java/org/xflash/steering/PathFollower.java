package org.xflash.steering;

import org.lwjgl.util.vector.Vector2f;
import org.xflash.detection.VectorUtils;

import java.util.Vector;

/**
 */
public class PathFollower implements Steering {
    private final int nodeRadius;
    private int currentNode;
    protected Path path;
    private int pathDir;


    public PathFollower(Path path, int nodeRadius) {
        this.path = path;
        this.nodeRadius = nodeRadius;
        currentNode = 0;
        pathDir = 1;
    }

    public Vector2f computeTarget(Vector2f position) {
        Vector2f target = null;

        if (path != null && path.hasNodes()) {
            Vector<Vector2f> nodes = path.getNodes();

            if(nodes.isEmpty() || nodes.size()<=currentNode) return null;
            target = nodes.get(currentNode);

            if (VectorUtils.distance(position, target) <= nodeRadius) {
                nodeGoal(currentNode);
            }
        }

        return target;
    }

    protected void nodeGoal(int node) {
        currentNode += pathDir;
        if (currentNode >= path.getNodes().size() || currentNode<0) {
            pathDir *= -1;
//                    currentNode = nodes.size() - 1;
//                    currentNode = 0;
            currentNode += pathDir;
        }
    }

}
