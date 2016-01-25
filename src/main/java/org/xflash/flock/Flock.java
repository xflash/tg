package org.xflash.flock;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

/**
 * The Flock (a list of Boid objects)
 */
public class Flock {
    private final ArrayList<Boid> boids;

    public Flock() {
        boids = new ArrayList<Boid>(); // Initialize the ArrayList
    }

    public void addBoid(Boid b) {
        boids.add(b);
    }

    public void update(GameContainer container) {
        for (Boid b : boids) {
            b.update(container, boids);  // Passing the entire list of boids to each boid individually
        }

    }

    public void render(Graphics g) {
        for (Boid b : boids) {
            b.render(g);
        }
    }
}
