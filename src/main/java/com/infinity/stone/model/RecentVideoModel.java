package com.infinity.stone.model;

/**
 * Created by infamouSs on 4/17/18.
 */

public class RecentVideoModel {
    
    private String imageThumbnail;
    private String videoName;
    
    
    public RecentVideoModel() {

    }
    
    public RecentVideoModel(String imageThumbnail, String videoName) {
        this.imageThumbnail = imageThumbnail;
        this.videoName = videoName;
    }
    
    public String getImageThumbnail() {
        return imageThumbnail;
    }
    
    public void setImageThumbnail(String imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }
    
    public String getVideoName() {
        return videoName;
    }
    
    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }
    
    @Override
    public String toString() {
        return "RecentVideoModel{" +
               "imageThumbnail='" + imageThumbnail + '\'' +
               ", videoName='" + videoName + '\'' +
               '}';
    }
}
