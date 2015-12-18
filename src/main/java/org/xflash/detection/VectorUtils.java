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
}
