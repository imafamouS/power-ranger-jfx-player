package com.infinity.stone.model;

import com.infinity.stone.db.video.Video;

/**
 * Created by infamouSs on 4/17/18.
 */

public class RecentVideoModel {
    
    private String imageThumbnail;
    private String videoName;
    private String videoPath;
    private String videoId;
    
    
    public RecentVideoModel() {
    
    }
    
    public RecentVideoModel(String imageThumbnail, String videoId, String videoName,
              String videoPath) {
        this.imageThumbnail = imageThumbnail;
        this.videoId = videoId;
        this.videoName = videoName;
        this.videoPath = videoPath;
    }
    
    public RecentVideoModel(Video video) {
        this.videoId = video.getId();
        this.videoName = video.getYoutubeId();
        this.videoPath = video.getUrl();
    }
    
    public String getVideoId() {
        return videoId;
    }
    
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
    
    public String getVideoPath() {
        return videoPath;
    }
    
    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
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
