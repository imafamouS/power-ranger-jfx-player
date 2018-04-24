package com.infinity.stone.db.favorite;

import com.infinity.stone.db.core.Entity;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by infamouSs on 4/24/18.
 */

public class Favorite extends Entity {
    
    private String videoId;
    
    private String subId;
    
    public Favorite() {
    
    }
    
    public Favorite(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                this.videoId = resultSet.getString(1);
                this.subId = resultSet.getString(2);
            }
        } catch (SQLException ignored) {
        
        }
    }
    
    public Favorite(String videoId, String subId) {
        this.videoId = videoId;
        this.subId = subId;
    }
    
    public String getVideoId() {
        return videoId;
    }
    
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
    
    public String getSubId() {
        return subId;
    }
    
    public void setSubId(String subId) {
        this.subId = subId;
    }
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        Favorite favorite = (Favorite) o;
        
        return (videoId != null ? videoId.equals(favorite.videoId) : favorite.videoId == null) &&
               (subId != null ? subId.equals(favorite.subId) : favorite.subId == null);
    }
    
    @Override
    public int hashCode() {
        int result = videoId != null ? videoId.hashCode() : 0;
        result = 31 * result + (subId != null ? subId.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "Favorite{" +
               "videoId='" + videoId + '\'' +
               ", subId='" + subId + '\'' +
               '}';
    }
}
