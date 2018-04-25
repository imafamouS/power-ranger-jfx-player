package com.infinity.stone.model;

import java.util.Iterator;
import java.util.List;

import com.infinity.stone.db.subtitle.Subtitle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SubtitleCollection {
    
    final ObservableList<Subtitle> lstModel = FXCollections.observableArrayList();
    
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
