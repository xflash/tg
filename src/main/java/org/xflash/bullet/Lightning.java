package org.xflash.bullet;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.xflash.utils.Actor;

/**
 */
public class Lightning extends Actor {

    protected double X[];
    protected double Y[];
    protected double theta, range;
    private float size;

    @Override
    public void init(Object[] args) { }

    public void spawn(int x, int y, float range, float size) {
        this.size = size;
        this.range=range;
        X = new double[(int)(range/size)];
        X[0] = x;
        Y = new double[(int)(range/size)];
        Y[0] = y;
        
        exists(true);
    }

    @Override
    public void update(GameContainer gc, int delta) {
        for(int i = 1; i < X.length; i++){
            double newAng = theta + Global.randint(-45, 45)*Math.PI /180;
            X[i] = X[i-1] + size*Math.cos(newAng);
            Y[i] = Y[i-1] + size*Math.sin(newAng);
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        for(int i = 1; i < X.length; i++){
            g.drawLine((int)X[i],(int)Y[i],(int)X[i-1],(int)Y[i-1]);
        }
    }
}
