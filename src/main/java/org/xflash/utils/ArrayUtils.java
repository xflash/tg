package org.xflash.utils;

import java.lang.reflect.Array;

/**
 */
public class ArrayUtils {

    public static int[][] rotateCW_(int[][] array) {
        if (array == null || array[0].length == 0) return array;

        int h = array.length;
        int w = array[0].length;
        int[][] newArray = new int[w][h];

        for (int i = 0; i < array[0].length; i++) {
            for (int j = array.length - 1; j >= 0; j--) {
                newArray[i][j] = array[j][i];
            }
        }

        return newArray;
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
}
