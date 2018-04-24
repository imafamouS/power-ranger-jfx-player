package com.infinity.stone.controller;

import com.infinity.stone.model.VideoModel;
import java.util.logging.Logger;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class BaseVideoController {
    
    private static final Logger LOG = Logger
              .getLogger(BaseVideoController.class.getCanonicalName());
    protected final MediaView mediaView;
    protected MediaPlayer mediaPlayer;
    private final double default_width;
    private final double default_height;
    private final double default_volumn;
    private double cachedvolumebeforemute;
    private boolean isDragTimeLine;
    private boolean isPlaying = false;
    private boolean isFullScreen = false;
    private final OnVideoControllerListener listener;
    
    public BaseVideoController(MediaView mediaView, OnVideoControllerListener listener) {
        this.mediaView = mediaView;
        this.listener = listener;
        this.default_height = 720.0d;
        this.default_width = 756.0d;
        this.default_volumn = 1.0d;
        //this.mediaView.prefHeight(default_height);
        //this.mediaView.prefWidth(default_width);
    }
    
    public OnVideoControllerListener getListener() {
        return listener;
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
        ((Stage) mediaView.getScene().getWindow()).setFullScreen(true);
        isFullScreen = true;
        listener.onFullScreen(isFullScreen);
    }
    
    public void collapsedFullScreen() {
        ((Stage) mediaView.getScene().getWindow()).setFullScreen(false);
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
    
    public void setCachedvolumebeforemute(double cachedvolumebeforemute) {
        this.cachedvolumebeforemute = cachedvolumebeforemute;
    }
    
    public void unmute() {
        if (mediaPlayer != null) {
            mediaPlayer.setMute(false);
            listener.onUnMute(cachedvolumebeforemute);
        } else {
            LOG.info("mediaPlayer reference null pls check to set mediaPlayer variable");
        }
    }
    
    public void mute() {
        if (mediaPlayer != null) {
            mediaPlayer.setMute(true);
            listener.onMute();
        } else {
            LOG.info("mediaPlayer reference null pls check to set mediaPlayer variable");
        }
        
    }
    
    public boolean isPlaying() {
        return isPlaying;
    }
    
    public void setPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
    
    public boolean isPausing() {
        return !isPlaying;
    }
    
    public boolean isFullWidth() {
        
        return false;
    }
    
    public void toggleFullWidth() {
    
    }
    
    public abstract void loadSourceVideo(VideoModel name);
    
    public abstract void releaseMedia();
    
    public interface OnVideoControllerListener {
        
        void onPause();
        
        void onPlaying();
        
        void onMute();
        
        void onLoadVideo(Duration contentlength);
        
        void onEnd();
        
        void onUnMute(double cachedvolumn);
        
        void onReady();
        
        void updateValues(Duration currentTime, Duration contentLength);
        
        void onFullScreen(boolean isFullScreen);
    }
}
