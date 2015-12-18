package org.xflash.utils;

/**
 */
public class AngleUtils {

    public static double getTargetAngle(double x, double y, double centerX, double centerY) {
        double theta = Math.atan2(y - centerY, x - centerX);
        return theta < 0 ? theta + Math.PI * 2 : theta;
    }
}
