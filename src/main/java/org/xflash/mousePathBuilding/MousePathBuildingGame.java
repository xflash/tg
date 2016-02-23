package org.xflash.mousePathBuilding;

import org.newdawn.slick.*;
import org.xflash.steering.Boid;
import org.xflash.steering.Path;
import org.xflash.steering.PathBuildingFollower;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 */
public class MousePathBuildingGame extends BasicGame {

    public static final int NODE_RADIUS = 50;
    private final List<Boid> boids = new ArrayList<Boid>();
    private boolean drawForces = false;
    private final Path path = new Path();

    public MousePathBuildingGame() {
        super("MousePathBuilding");
    }

    @Override
    public void init(GameContainer container) throws SlickException {

        int containerWidth = container.getWidth();
        int containerHeight = container.getHeight();

        Random random = new Random();
        Flocker flocker = new Flocker(boids);
        PathBuildingFollower steerer = new PathBuildingFollower(path, NODE_RADIUS, container);
        CumulativeSteering cumulativeSteering = new CumulativeSteering(steerer);
        for (int i = 0; i < 50; i++) {
            boids.add(new Boid(
                    containerWidth / 2 + random.nextFloat() * 50,
                    containerHeight / 2 + 20 * random.nextFloat(),
                    20 + random.nextFloat() * 20,
                    cumulativeSteering));

        }
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        for (Boid boid : boids) {
            boid.update(container, delta, boids);
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        if(Input.KEY_SPACE==key) drawForces = !drawForces;
    }


    public void render(GameContainer container, Graphics g) throws SlickException {

//        g.setBackground(Color.white);
        path.render(container, g, NODE_RADIUS);

        for (Boid boid : boids) {
            boid.render(container, g, drawForces);
        }
    }
}
