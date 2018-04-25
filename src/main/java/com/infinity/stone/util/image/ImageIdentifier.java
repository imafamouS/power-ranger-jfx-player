package com.infinity.stone.util.image;

/**
 * Created by infamouSs on 4/17/18.
 */

public class ImageIdentifier {
    
    private final String url;
    
    private final int width;
    
    private final int height;
    
    public ImageIdentifier(String url, int width, int height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageIdentifier)) {
            return false;
        }
        
        ImageIdentifier that = (ImageIdentifier) o;
        
        if (height != that.height) {
            return false;
        }
        if (width != that.width) {
            return false;
        }
        if (!url.equals(that.url)) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public int hashCode() {
        int result = url.hashCode();
        result = 31 * result + width;
        result = 31 * result + height;
        return result;
    }
    
    public String getUrl() {
        return url;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
}
