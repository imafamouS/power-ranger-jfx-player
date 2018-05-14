package com.infinity.stone.db.subtitle;

import com.infinity.stone.db.core.Entity;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

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
    
    public Subtitle(String timeStart, String content) {
        this.timeStart = timeStart;
        this.content = content;     
    }
    
    public Subtitle(String timeStart, String timeEnd, String content) {
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.content = content;      
    }
    
    public Subtitle(String id, String videoId, String timeStart, String timeEnd, String content) {
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
                this.timeEnd = resultSet.getString(4);
                this.content = resultSet.getString(5);
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((videoId == null) ? 0 : videoId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subtitle other = (Subtitle) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (videoId == null) {
			if (other.videoId != null)
				return false;
		} else if (!videoId.equals(other.videoId))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "Subtitle{" +
               "id='" + id + '\'' +
               ", videoId='" + videoId + '\'' +
               ", timeStart='" + timeStart + '\'' +
               ", timeEnd='" + timeEnd + '\'' +
               ", content='" + content + '\'' +
               '}';
    }
}
