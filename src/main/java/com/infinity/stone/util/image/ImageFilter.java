package com.infinity.stone.util.image;

/**
 * Created by infamouSs on 4/17/18.
 */

public interface ImageFilter {
    
    int[] filter(int imageWidth, int imageHeigth, int[] inPixels);
    
}