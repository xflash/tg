package org.xflash.utils;

import org.newdawn.slick.Color;

/**
 * @author rcoqueugniot
 * @since 17.12.15
 */
public class ColorUtils {
    public static Color buildTrnasprentColor(Color initialColor, float v) {
        Color color = new Color(initialColor);
        color.a = .6f;
        return color;
    }
}
