package org.xflash.detection;

import org.lwjgl.util.vector.Vector2f;

/**
 * @author rcoqueugniot
 * @since 18.12.15
 */
public class VectorUtils {

    public static void normalize(Vector2f vector) {
        try {
            vector.normalise();
        } catch (Exception ignored) { }
    }

    public static void truncate(Vector2f vector, float max) {
        float i = max / vector.length();
        vector.scale(i < 1. ? 1.f : i);
    }

    public static float distance(Vector2f a, Vector2f b) {
        return (float) Math.sqrt((a.x - b.x) * (a.x - b.x)  + (a.y - b.y) * (a.y - b.y));

    }
}
