package org.xflash.steering;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.util.InputAdapter;
import org.xflash.detection.VectorUtils;

import java.util.Vector;

/**
 */
public class PathBuildingFollower extends PathFollower {


    public PathBuildingFollower(final Path path, int nodeRadius, GameContainer container) {
        super(path, nodeRadius);
        path.addNode(new Vector2f(container.getWidth()/2, container.getHeight()/2));
        container.getInput().addMouseListener(new InputAdapter(){
            @Override
            public void mousePressed(int button, int x, int y) {
                if(button==0)path.addNode(new Vector2f(x,y));
                else if(button==1)path.removeLastNodes();
            }
        });
    }

    @Override
    protected void nodeGoal(int node) {
        path.getNodes().remove(node);
    }
}
