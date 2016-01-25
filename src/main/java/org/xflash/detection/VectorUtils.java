package org.xflash.detection;

import org.lwjgl.util.vector.Vector2f;

/**
 */
public class VectorUtils {

    public static void normalize(Vector2f vector) {
        try {
            vector.normalise();
        } catch (Exception ignored) { }
    }

    public static void truncate(Vector2f vector, float max) {
        float length = vector.length();
        if(length==0) return;
        float i = max / length;
        vector.scale(i < 1. ? 1.f : i);
    }

    public static float distance(Vector2f a, Vector2f b) {
        return (float) Math.sqrt((a.x - b.x) * (a.x - b.x)  + (a.y - b.y) * (a.y - b.y));
    }

    public static Vector2f add(Vector2f v1, Vector2f v2) {
        return Vector2f.add(v1, v2, v1);
    }

    public static double heading2D(Vector2f vector) {
        return Math.atan2(vector.y, vector.x);
    }
}
