package org.xflash.flock;

import org.newdawn.slick.*;

/**
 */
public class FlockGame extends BasicGame {
    private Flock flock;

    public FlockGame() {
        super("Flock");
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        flock = new Flock();
        // Add an initial set of boids into the system
        for (int i = 0; i < 150; i++) {
            flock.addBoid(new Boid(container.getWidth() / 2, container.getHeight() / 2));
        }
    }

    // Add a new boid into the System
    @Override
    public void mousePressed(int button, int mouseX, int mouseY) {
        flock.addBoid(new Boid(mouseX, mouseY));
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        flock.update(container);
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        flock.render(g);
    }
}
