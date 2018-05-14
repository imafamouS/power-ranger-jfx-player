package com.infinity.stone.model;

import com.infinity.stone.db.favorite.Favorite;
import com.infinity.stone.db.subtitle.Subtitle;
import java.util.Iterator;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SubtitleCollection {
    
    final ObservableList<Subtitle> lstModel = FXCollections.observableArrayList();
    
    public static SubtitleCollection makeSubtitleCollectionFromListSub(List<Subtitle> list) {
        SubtitleCollection collection = new SubtitleCollection();        
        collection.addAll(list);
        return collection;
    }
    
    public static SubtitleCollection makeSubtitleCollectionFromListFavorite(List<Favorite> list,List<Subtitle> listsub) {
    	SubtitleCollection collection = new SubtitleCollection();
    	for(Favorite favorite:list) {
    		for(Subtitle sub : listsub) {
    			if(favorite.getSubId().equals(sub.getId())) {
    				collection.add(sub);
    			}
    		}
    	}
    	return collection;
    }
    public ObservableList<Subtitle> getLstModel() {
        return lstModel;
    }
    
    public void addAll(List<Subtitle> collection) {
        if (collection != null) {
            this.lstModel.addAll(collection);
        }
    }
    
    public void add(Subtitle model) {
        if (model != null) {
            lstModel.add(model);
        }
    }
    
    public void remove(Subtitle model) {
        Iterator iter = lstModel.iterator();
        while (iter.hasNext()) {
            if (iter.next().equals(model)) {

            }
        }
    }
    
    public void printValue() {
        for (Subtitle i : lstModel) {
            System.out.print(i.toString());
        }
    }
}
