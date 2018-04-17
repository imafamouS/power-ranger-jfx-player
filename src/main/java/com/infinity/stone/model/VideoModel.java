package com.infinity.stone.model;

public class VideoModel implements Comparable<VideoModel>{
	public VideoModel(String path,LEVEL level) {
		this.path = path;
		this.level = level;
	}
	private LEVEL level;
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
	private String path;
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof VideoModel))return false;
		if(obj==null)return false;
		if(((VideoModel)obj).getPath().equals(this.getPath())) {
			return true;
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	public enum LEVEL{
		BASIC(0),INTERMIDIATE(1),ADVANCE(2);
		private final int type;
		LEVEL(int type){
			this.type = type;
		}
		public int getType() {
			return type;
		}
		
		public static LEVEL mapLevel(int type) {
			for(LEVEL level : values()) {
				if(type==level.getType()) {
					return level;
				}
			}
			return LEVEL.BASIC;//default level
		}
	}
	
	@Override
	public int compareTo(VideoModel o) {
		return Integer.compare(this.getLevel().getType(),o.getLevel().getType());
	}
}
