package com.infinity.stone.model;

/**
 * Created by infamouSs on 4/19/18.
 */

public class CaptionsModel {
    
    private String id;
    private String lang;
    
    public CaptionsModel() {
    
    }
    
    public CaptionsModel(String id, String lang) {
        this.id = id;
        this.lang = lang;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getLang() {
        return lang;
    }
    
    public void setLang(String lang) {
        this.lang = lang;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        CaptionsModel that = (CaptionsModel) o;
        
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        return lang != null ? lang.equals(that.lang) : that.lang == null;
    }
    
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (lang != null ? lang.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "CaptionsModel{" +
               "id='" + id + '\'' +
               ", lang='" + lang + '\'' +
               '}';
    }
}
