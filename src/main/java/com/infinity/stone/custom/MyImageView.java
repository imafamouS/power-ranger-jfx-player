package com.infinity.stone.custom;

import java.util.HashMap;
import java.util.Map;

import com.infinity.stone.controller.VideoController;
import com.infinity.stone.util.ResourceUtils;
import com.sun.istack.internal.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MyImageView extends ImageView{
	private static final Logger LOG = Logger.getLogger(MyImageView.class.getCanonicalName(),MyImageView.class);
	
	public MyImageView(String url) {
		super(ResourceUtils.getInstance().loadIcon(url).toString());
		this.prefHeight(24.0);
		this.prefWidth(24.0);
		this.setPreserveRatio(true);
		this.setSmooth(true);
		this.setOpacity(0.8);
		this.hoverProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue) {
					MyImageView.this.setOpacity(1.0);
				}else {
					MyImageView.this.setOpacity(0.8);
				}
			}
			
		});
		this.setPickOnBounds(true);
		this.setStyle("-fx-background-color:transparent");
	}
	Map<String,Image> cached = new HashMap<>();
	public void setImage(String name) {
		LOG.info(ResourceUtils.getInstance().loadIcon(name).toString());
		if(!cached.containsKey(name)) 
			cached.put(name, new Image(ResourceUtils.getInstance().loadIcon(name).toString()));
		super.setImage(cached.get(name));
	}
}