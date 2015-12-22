package org.xflash.steering;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

/**
 * @author rcoqueugniot
 * @since 18.12.15
 */
public class SteeringPathFollowingGame extends BasicGame {

    private static final int MAX_PATH_NODES = 30;
    private static final int MAX_BOIDS = 5;
    private final int nodeRadius = 50;
    private boolean drawForces=false;
    private ArrayList<Boid> boids = new ArrayList<Boid>();
    private Path path;

    public SteeringPathFollowingGame() {
        super("SteeringPathFollowing");
    }

    @Override
    public void init(GameContainer container) throws SlickException {

        path = new Path();
        Random random = new Random();

        int containerWidth = container.getWidth();
        int containerHeight = container.getHeight();

        // Add path nodescircles
        for (int i = 0; i < MAX_PATH_NODES; i++) {
             path.addNode(new Vector2f(containerWidth * i / MAX_PATH_NODES + 50,
                                      containerHeight * random.nextFloat() * 0.8f + 20));
        }

        // Add boids
        for (int i = 0; i < MAX_BOIDS; i++) {
            Boid boid = new Boid(containerWidth / 2 + random.nextFloat() * 50,
                                 20 * random.nextFloat(),
                                 20 + random.nextFloat() * 20,
                                nodeRadius);
            boid.path = path;
            boids.add(boid);
        }

    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        drawForces = !drawForces;
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        for (Boid boid : boids) {
            boid.update(container, delta);
        }
    }

    public void render(GameContainer container, Graphics g) throws SlickException {

        path.render(container, g, nodeRadius);

        for (Boid boid : boids) {
            boid.render(container, g, drawForces);
        }
    }




}
