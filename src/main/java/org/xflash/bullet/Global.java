package org.xflash.bullet;

/**
 */
public class Global {
    public static int randint(int a, int b){
        return (int)(Math.random()*(b-a+1)) + a;
    }
    public static int randint(double a, double b){
        return randint((int)a,(int)b);
    }
}
