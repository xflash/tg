package org.xflash.astar;

import org.newdawn.slick.Color;

/**
 */
public enum Cell {
    FREE(1.f),WALL(0), START(0), FINISH(0), SAND(0.5f);
    
    static final Color[] BG_COLORS =new Color[]{
            Color.black,
            Color.darkGray,
            Color.green,
            Color.blue,
            
            Color.yellow
            
    };
    private float cost;

    Cell(float cost) {
        this.cost = cost;
    }

    public float cost() {
        return cost;
    }
}
