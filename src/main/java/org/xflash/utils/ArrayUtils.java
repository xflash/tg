package org.xflash.utils;

import java.lang.reflect.Array;

/**
 */
public class ArrayUtils {

    public static char[][] transpose(char[][] array) {
        if (array == null || array.length == 0)//empty or unset array, nothing do to here
            return array;

        int width = array.length;
        int height = array[0].length;

        char[][] array_new = new char[height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                array_new[y][x] = array[x][y];
            }
        }
        return array_new;
    }

    public static char[][] rotateCW(char[][] matrix) {
        if (matrix == null || matrix[0].length == 0) return matrix;

       /* W and H are already swapped */
        int w = matrix.length;
        int h = matrix[0].length;
        char[][] ret = new char[h][w];
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                ret[i][j] = matrix[w - j - 1][i];
            }
        }
        return ret;
    }


    public static char[][] rotateCCW(char[][] matrix) {
        if (matrix == null || matrix[0].length == 0) return matrix;

       /* W and H are already swapped */
        int w = matrix.length;
        int h = matrix[0].length;
        char[][] ret = new char[h][w];
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
//                Destination(Y,3-X) = Source(X,Y)
                ret[i][j] = matrix[j][h - i - 1];
//                Destination(3-Y,X) = Source(X,Y)
            }
        }
        return ret;
    }
}
