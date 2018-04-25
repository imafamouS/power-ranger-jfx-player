package com.infinity.stone.db.subtitle;

import com.infinity.stone.db.core.Entity;

import javafx.util.Duration;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by infamouSs on 4/24/18.
 */

public class Subtitle extends Entity {
    
    
    private String id;
    private String videoId;
    private String timeStart;
    private String timeEnd;
    private String content;
    
    public Subtitle() {
    
    }
    
    public Subtitle(String timeStart,String content) {
    	this.timeStart = timeStart;
    	this.content = content;
    }
    
    public Subtitle(String timeStart,String timeEnd,String content) {
    	this.timeStart = timeStart;
    	this.timeEnd = timeEnd;
    	this.content = content;
    }
    
    public Subtitle(String id, String videoId, String timeStart,String timeEnd, String content) {
        this.id = id;
        this.videoId = videoId;
        this.timeEnd = timeEnd;
        this.timeStart = timeStart;
        this.content = content;
    }
    
    public Subtitle(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                this.id = resultSet.getString(1);
                this.videoId = resultSet.getString(2);
                this.timeStart = resultSet.getString(3);
                this.content = resultSet.getString(4);
                this.timeEnd = resultSet.getString(5);
            } catch (SQLException ignored) {
            }
        }
    }
    
    public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getVideoId() {
        return videoId;
    }
    
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
    
    public String getTimeStart() {
        return timeStart;
    }
    
    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        Subtitle subtitle = (Subtitle) o;
        
        return (id != null ? id.equals(subtitle.id) : subtitle.id == null) &&
               (videoId != null ? videoId.equals(subtitle.videoId) : subtitle.videoId == null) &&
               ((timeStart != null) ? timeStart.equals(subtitle.timeStart)
                         : (subtitle.timeStart == null)) &&
               ((timeEnd !=null)? timeEnd.equals(subtitle.timeEnd):(subtitle.timeEnd == null)) &&
               (content != null ? content.equals(subtitle.content) : subtitle.content == null);
    }
    
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (videoId != null ? videoId.hashCode() : 0);
        result = 31 * result + (timeStart != null ? timeStart.hashCode() : 0);
        result = 31 * result + (timeEnd !=null ? timeEnd.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "Subtitle{" +
               "id='" + id + '\'' +
               ", videoId='" + videoId + '\'' +
               ", timeStart='" + timeStart + '\'' +
               ", timeEnd='"+timeEnd+'\''+
               ", content='" + content + '\'' +
               '}';
    }
}
