package com.infinity.stone.controller;

import java.io.File;

import com.infinity.stone.model.VideoModel;
import com.infinity.stone.util.ResourceUtils;
import com.sun.istack.internal.logging.Logger;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class  BaseVideoController{
	private double default_width;
	private double default_height;
	private double default_volumn;
	protected MediaView mediaView;
	protected MediaPlayer mediaPlayer;
	private double cachedvolumebeforemute;
	private boolean isDragTimeLine;
	private boolean isPlaying = false;
	private boolean isFullScreen = false;
	
	private OnVideoControllerListener listener;
	public OnVideoControllerListener getListener() {
		return listener;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}
	private static final Logger LOG = Logger.getLogger(BaseVideoController.class.getCanonicalName(),BaseVideoController.class);
	
	public void setCachedvolumebeforemute(double cachedvolumebeforemute) {
		this.cachedvolumebeforemute = cachedvolumebeforemute;
	}

	public BaseVideoController(MediaView mediaView,OnVideoControllerListener listener) {
		this.mediaView = mediaView;
		this.listener = listener;
		this.default_height = 720.0d;
		this.default_width = 756.0d;
		this.default_volumn = 1.0d;
		//this.mediaView.prefHeight(default_height);
		//this.mediaView.prefWidth(default_width);
	}
	
	public interface OnVideoControllerListener{
		public void onPause();
		public void onPlaying();
		public void onMute();
		public void onLoadVideo(Duration contentlength);
		public void onEnd();
		public void onUnMute(double cachedvolumn);
		public void onReady();
		public void updateValues(Duration currentTime,Duration contentLength);
		public void onFullScreen(boolean isFullScreen);
	}
	
	
	public void play() {
		isPlaying = true;
		mediaPlayer.play();
	}
	
	public void startAtTime(double values) {
		mediaPlayer.setStartTime(Duration.millis(values));
		mediaPlayer.setStopTime(mediaPlayer.getMedia().getDuration());
	}
	
	public boolean isDragTimeLine() {
		return isDragTimeLine;
	}
	
	public boolean isFullScreen() {
		return isFullScreen;
	}
	
	public void expandfullScreen() {
		((Stage)mediaView.getScene().getWindow()).setFullScreen(true);
		isFullScreen = true;
		listener.onFullScreen(isFullScreen);
	}
	
	public void collapsedFullScreen() {
		((Stage)mediaView.getScene().getWindow()).setFullScreen(false);
		isFullScreen = false;
		listener.onFullScreen(isFullScreen);
	}
	
	public void pause() {
		isPlaying = false;
		mediaPlayer.pause();
	}

	public boolean isMute() {
		return mediaPlayer.isMute();
	}

	public abstract void previous();


	public abstract void next();


	public void toggleSub() {
		// TODO Auto-generated method stub
		
	}


	public abstract void setVolume(double volume);

	public void stopDragTimeLine() {
		isDragTimeLine = false;
	}

	public void dragTimeLine() {
		isDragTimeLine = true;
	}
	
	public double getCachedvolumebeforemute() {
		return cachedvolumebeforemute;
	}
	
	public void unmute() {
		if(mediaPlayer!=null) {
			mediaPlayer.setMute(false);
			listener.onUnMute(cachedvolumebeforemute);
		}else {
			LOG.info("mediaPlayer reference null pls check to set mediaPlayer variable");
		}
	}

	public void mute() {
		if(mediaPlayer!=null) {
			mediaPlayer.setMute(true);
			listener.onMute();
		}else {
			LOG.info("mediaPlayer reference null pls check to set mediaPlayer variable");
		}
		
	}


	public boolean isPlaying() {
		return isPlaying;
	}


	public boolean isPausing() {
		return !isPlaying;
	}


	public boolean isFullWidth() {
		
		return false;
	}


	public void toggleFullWidth() {
		
	}


	public abstract void loadSourceVideo(VideoModel name) ;

	public abstract void releaseMedia();
}
