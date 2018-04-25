package com.infinity.stone.model;

import com.infinity.stone.db.subtitle.Subtitle;

import javafx.util.Duration;

public class VideoModel implements Comparable<VideoModel> {
    
    private LEVEL level;
    private String path;
    private SubtitleCollection collection;
    
    public VideoModel(String path,SubtitleCollection collection, LEVEL level) {
        this.path = path;
        this.level = level;
        this.collection = collection;
    }
    
   
    
    public SubtitleCollection getCollection() {
		return collection;
	}

    

	public void setCollection(SubtitleCollection collection) {
		this.collection = collection;
	}



	public LEVEL getLevel() {
        return level;
    }
    
    public void setLevel(LEVEL level) {
        this.level = level;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof VideoModel)) {
            return false;
        }
        if (obj == null) {
            return false;
        }
        return ((VideoModel) obj).getPath().equals(this.getPath()) || super.equals(obj);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public int compareTo(VideoModel o) {
        return Integer.compare(this.getLevel().getType(), o.getLevel().getType());
    }
    
    public enum LEVEL {
        BASIC(0), INTERMIDIATE(1), ADVANCE(2);
        private final int type;
        
        LEVEL(int type) {
            this.type = type;
        }
        
        public static LEVEL mapLevel(int type) {
            for (LEVEL level : values()) {
                if (type == level.getType()) {
                    return level;
                }
            }
            return LEVEL.BASIC;//default level
        }
        
        public int getType() {
            return type;
        }
    }
}
