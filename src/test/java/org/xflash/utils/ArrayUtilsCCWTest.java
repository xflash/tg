package org.xflash.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 */
public class ArrayUtilsCCWTest {

    @Test
    public void testRotateCCW1_1() throws Exception {
        Assert.assertArrayEquals(
                new char[][]{{1}},
                ArrayUtils.rotateCCW(new char[][]{
                        {1}
                })
        );
    }

    @Test
    public void testRotateCCW2_1() throws Exception {
        Assert.assertArrayEquals(
                new char[][]{
                        {2},
                        {1}
                },
                ArrayUtils.rotateCCW(new char[][]{
                        {1, 2}
                })
        );
    }

    @Test
    public void testRotateCCW4_4() throws Exception {

        Assert.assertArrayEquals(
                new char[][]{
                        {2, 4},
                        {1, 3}
                },
                ArrayUtils.rotateCCW(new char[][]{
                        {1, 2},
                        {3, 4}
                })
        );
    }

    @Test
    public void testRotateCCW3_2() throws Exception {

        Assert.assertArrayEquals(
                new char[][]{
                        {3, 6},
                        {2, 5},
                        {1, 4}
                },
                ArrayUtils.rotateCCW(new char[][]{
                        {1, 2, 3},
                        {4, 5, 6}
                })
        );
    }

    @Test
    public void testRotateCCW2_3() throws Exception {

        Assert.assertArrayEquals(
                new char[][]{
                        {2, 4, 6},
                        {1, 3, 5},
                },
                ArrayUtils.rotateCCW(new char[][]{
                        {1, 2,},
                        {3, 4,},
                        {5, 6,},
                })
        );
    }
}