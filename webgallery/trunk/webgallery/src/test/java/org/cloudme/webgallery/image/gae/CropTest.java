package org.cloudme.webgallery.image.gae;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class CropTest {
    private static final double PRECISION = 0.0001;

    @Test
    public void testCrop() {
        assertCrop(800, 600, 400, 300, 1, 1, 0, 0, 0.5f);
        assertCrop(800, 600, 300, 300, 0.75f, 1, 0.125f, 0, 0.5f);
        assertCrop(800, 600, 300, 400, 0.5625f, 1, 0.21875f, 0, 0.5f);
        assertCrop(600, 800, 400, 300, 1, 0.5625f, 0, 0.21875f, 0.5f);
        assertCrop(600, 800, 300, 300, 1, 0.75f, 0, 0.125f, 0.5f);
        assertCrop(600, 800, 300, 400, 1, 1, 0, 0, 0.5f);
        assertCrop(800, 600, 300, 300, 0.75f, 1, 0, 0, 0.0f);
        assertCrop(800, 600, 300, 300, 0.75f, 1, 0.25f, 0, 1.0f);
    }

    private void assertCrop(int w1, int h1, int w2, int h2, float width, float height, float x, float y, float balance) {
        Crop crop = new Crop(w1, h1, w2, h2, balance);
        assertEquals(width, crop.width, PRECISION);
        assertEquals(height, crop.height, PRECISION);
        assertEquals(x, crop.x, PRECISION);
        assertEquals(y, crop.y, PRECISION);
    }
}
