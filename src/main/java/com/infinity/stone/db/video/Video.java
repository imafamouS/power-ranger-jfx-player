package com.infinity.stone.db.video;

import com.infinity.stone.db.core.Entity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by infamouSs on 4/24/18.
 */

public class Video extends Entity {
    
    private String id;
    private String youtubeId;
    private String url;
    private Date dateCreated;
    
    public Video() {

    }
    
    public Video(String id, String youtubeId, String url, Date dateCreated) {
        this.id = id;
        this.youtubeId = youtubeId;
        this.url = url;
        this.dateCreated = dateCreated;
    }
    
    public Video(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                this.id = resultSet.getString(1);
                this.youtubeId = resultSet.getString(2);
                this.url = resultSet.getString(3);
                this.dateCreated = resultSet.getDate(4);
            } catch (SQLException ignored) {
            }
        }
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getYoutubeId() {
        return youtubeId;
    }
    
    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public Date getDateCreated() {
        return dateCreated;
    }
    
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
    
        Video video = (Video) o;
    
        if (id != null ? !id.equals(video.id) : video.id != null) {
            return false;
        }
        if (youtubeId != null ? !youtubeId.equals(video.youtubeId) : video.youtubeId != null) {
            return false;
        }
        return (url != null ? url.equals(video.url) : video.url == null) &&
               (dateCreated != null ? dateCreated.equals(video.dateCreated)
                         : video.dateCreated == null);
    }
    
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (youtubeId != null ? youtubeId.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "Video{" +
               "id='" + id + '\'' +
               ", youtubeId='" + youtubeId + '\'' +
               ", url='" + url + '\'' +
               ", dateCreated=" + dateCreated +
               '}';
    }
}
