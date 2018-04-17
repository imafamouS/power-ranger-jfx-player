package com.infinity.stone.model;

public class SubtitleModel {
	private String time;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	private String sentence;
	public SubtitleModel(String time,String sentence) {
		this.time = time;
		this.sentence = sentence;
	}
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof SubtitleModel))return false;
		if(this.time != null && this.sentence!=null) {
			if(this.time.equals(((SubtitleModel)obj).time) && ((SubtitleModel)obj).sentence.equals(this.sentence)) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
}
