package com.infinity.stone.model;

import com.infinity.stone.db.subtitle.Subtitle;

import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class SubtitleModel {
    
    private String time;
    private String sentence;
    
    public SubtitleModel(String time, String sentence) {
        this.time = time;
        this.sentence = sentence;
    }
    
    public SubtitleModel(Subtitle subtitle) {
        this.time = subtitle.getTimeStart();
        this.sentence = subtitle.getContent();
    }
    
    public static List<SubtitleModel> convert(List<Subtitle> subtitles) {
        List<SubtitleModel> list = new ArrayList<>();
        for (Subtitle i : subtitles) {
            list.add(new SubtitleModel(i));
        }
        return list;
    }
    
    
    
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
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SubtitleModel)) {
            return false;
        }
        return this.time != null && this.sentence != null &&
               this.time.equals(((SubtitleModel) obj).time) &&
               ((SubtitleModel) obj).sentence.equals(this.sentence);
    }
    
    @Override
    public String toString() {
        return "SubtitleModel{" +
               "time='" + time + '\'' +
               ", sentence='" + sentence + '\'' +
               '}';
    }
}
