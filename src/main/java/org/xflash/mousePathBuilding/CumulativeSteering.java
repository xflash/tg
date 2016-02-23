package org.xflash.mousePathBuilding;

import org.lwjgl.util.vector.Vector2f;
import org.xflash.detection.VectorUtils;
import org.xflash.steering.PathBuildingFollower;
import org.xflash.steering.Steering;

/**
 * Created by core on 08.02.2016.
 */
public class CumulativeSteering implements Steering {
    private Steering[] steerers;

    public CumulativeSteering(Steering... steerers) {
        this.steerers = steerers;
    }

    public Vector2f computeTarget(Vector2f position) {

        Vector2f steering = new Vector2f(0, 0);
        for (Steering steerer : steerers) {
            Vector2f v2 = steerer.computeTarget(position);
            if (v2 != null)
                VectorUtils.add(steering, v2);
        }
        return steering;
    }
}
