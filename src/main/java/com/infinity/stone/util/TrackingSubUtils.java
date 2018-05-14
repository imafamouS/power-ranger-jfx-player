package com.infinity.stone.util;

import com.infinity.stone.db.subtitle.Subtitle;
import com.infinity.stone.model.SubtitleCollection;

import javafx.util.Duration;

public class TrackingSubUtils {
	public interface OnChangeListener{
		void onChangedSub(Subtitle sub);
	}
	private SubtitleCollection collection;
	private Subtitle cachedSub;
	private OnChangeListener listener;
	
	public TrackingSubUtils(SubtitleCollection collection){
		this.collection = collection;
		this.cachedSub = collection.getLstModel().get(0);
	}
	
	public void setOnChangeListener(OnChangeListener listener) {
		this.listener = listener;
	}
	
	public void trackingSub(Duration now) {
		Subtitle subfind = TextUtils.locatedSub(collection, now);
		if(subfind!=null && !subfind.equals(cachedSub) ) {
			if(listener!=null) {
				cachedSub = subfind;
				listener.onChangedSub(subfind);				
			}
		}		
	}
}
