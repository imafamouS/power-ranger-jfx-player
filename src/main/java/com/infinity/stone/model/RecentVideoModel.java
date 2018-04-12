package com.infinity.stone.model;

/**
 * Created by infamouSs on 4/12/18.
 */

public class RecentVideoModel {
    
    
    private String imageUrl;
    private String text;
    
    
    public RecentVideoModel(String imageUrl, String text) {
        this.imageUrl = imageUrl;
        this.text = text;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
}
