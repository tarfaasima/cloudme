package org.cloudme.webgallery.image.gae;

public class Crop {
    public final float x;
    public final float y;
    public final float width;
    public final float height;

    public Crop(float w1, float h1, float w2, float h2, float balance) {
        float r1 = w1 / h1;
        float r2 = w2 / h2;
        if (r1 < r2) {
            width = 1;
            height = r1 / r2;
            x = 0;
            y = (1f - height) * balance;
        }
        else {
            width = r2 / r1;
            height = 1;
            x = (1f - width) * balance;
            y = 0;
        }
    }
}
