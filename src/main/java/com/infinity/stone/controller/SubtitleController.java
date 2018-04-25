package com.infinity.stone.controller;

import java.util.TimerTask;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class SubtitleController implements SubtitleLogicCtrl {
	//View
	private final Label label;
	private boolean isOnSub = false;
	//default config
	private final double MAX_WIDTH = 200;
	private final double MAX_HEIGHT = 400;
	private final String DEFAULT_CLASS_CSS = "subtitle-label";
	
	//Callback
	private SubtitleCallback callback;
	
	public void setOnListenSubTitle(SubtitleCallback callback) {
		this.callback = callback;
	}
	
	public SubtitleController(Label label) {
		this.label = label;
		buildUILabel();
	}
	
	private void buildUILabel() {
		this.label.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
		this.label.setWrapText(true);
		this.label.getStyleClass().add(DEFAULT_CLASS_CSS);
		this.label.setTextFill(Color.WHITE);
		this.label.setAlignment(Pos.CENTER);
		this.label.setTextAlignment(TextAlignment.CENTER);
	}
	
	@Override
	public void showSub() {
		if(isOnSub) {
			this.label.setVisible(true);
			if(callback!=null) {
				callback.onShowSub();
			}
		}
	}

	@Override
	public void hideSub() {
		this.label.setVisible(false);
		if(callback!=null) {
			callback.onHideSub();
		}
		
	}

	@Override
	public boolean isShowSub() {
		return this.label.isVisible();
	}

	@Override
	public void toggleSub() {
		if(isOnSub) {
			turnOff();
		}else {
			turnOn();
		}
	}
	
	public interface SubtitleCallback {
		void onShowSub();
		void onHideSub();
	}

	@Override
	public void setText(String content) {
		this.label.setText(content);
	}

	@Override
	public void turnOn() {
		isOnSub = true;
	}

	@Override
	public void turnOff() {
		isOnSub = false;
		this.label.setVisible(false);
	}
	
	
}
