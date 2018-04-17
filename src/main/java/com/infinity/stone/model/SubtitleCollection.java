package com.infinity.stone.model;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SubtitleCollection {
	ObservableList<SubtitleModel> lstModel = FXCollections.observableArrayList();
	
	public ObservableList<SubtitleModel> getLstModel() {
		return lstModel;
	}

	public void addAll(List<SubtitleModel> collection){
		if(collection!=null) {
			this.lstModel.addAll(collection);
		}
		return; //end
	}
	
	public void add(SubtitleModel model) {
		if(model!=null) {
			lstModel.add(model);
		}
		return; //end
	}
	
	public void remove(SubtitleModel model) {
		Iterator iter = lstModel.iterator();
		while(iter.hasNext()) {
			if(iter.next().equals(model)) {
				
			}
		}
	}
}
