package org.xflash.astar;

/**
 */
public interface PathFinderListener {
    
    void distanceChanged(Integer newDistance);

    void diagonalAllowed(boolean allowed);
}
