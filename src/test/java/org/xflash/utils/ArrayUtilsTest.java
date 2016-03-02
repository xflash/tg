package org.xflash.utils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 */
public class ArrayUtilsTest {

    @Test
    public void testRotateCW1_1() throws Exception {
        Assert.assertArrayEquals(
                new char[][]{{1}},
                ArrayUtils.rotateCW(new char[][]{
                        {1}
                })
        );
    }

    @Test
    public void testRotateCW2_1() throws Exception {
        Assert.assertArrayEquals(
                new char[][]{
                        {1},
                        {2}
                },
                ArrayUtils.rotateCW(new char[][]{
                        {1, 2}
                })
        );
    }

    @Test
    public void testRotateCW4_4() throws Exception {

        Assert.assertArrayEquals(
                new char[][]{
                        {3, 1},
                        {4, 2}
                },
                ArrayUtils.rotateCW(new char[][]{
                        {1, 2},
                        {3, 4}
                })
        );
    }

    @Test
    public void testRotateCW3_2() throws Exception {

        Assert.assertArrayEquals(
                new char[][]{
                        {4, 1},
                        {5, 2},
                        {6, 3}
                },
                ArrayUtils.rotateCW(new char[][]{
                        {1, 2, 3},
                        {4, 5, 6}
                })
        );
    }

    @Test
    public void testRotateCW2_3() throws Exception {

        Assert.assertArrayEquals(
                new char[][]{
                        {5, 3, 1},
                        {6, 4, 2},
                },
                ArrayUtils.rotateCW(new char[][]{
                        {1, 2,},
                        {3, 4,},
                        {5, 6,},
                })
        );
    }
}