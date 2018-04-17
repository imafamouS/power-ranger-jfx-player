package com.infinity.stone.controller;

import com.infinity.stone.model.VideoModel;
import com.infinity.stone.util.ResourceUtils;
import java.util.List;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class VideoController extends BaseVideoController {
    
    private static final Logger LOG = Logger
              .getLogger(VideoController.class.getCanonicalName());
    private int indexActiveVideo;
    private List<VideoModel> lst_video;
    private MediaView videoView;
    private VideoModel activeVideo;
    private boolean isEnd;
    private Media media;
    
    public VideoController(MediaView videoView, VideoModel picked_video_name,
              List<VideoModel> lst_video, OnVideoControllerListener listener) {
        super(videoView, listener);
        this.videoView = videoView;
        this.videoView.setSmooth(true);
        this.videoView.setPreserveRatio(true);
        this.lst_video = lst_video;
        this.activeVideo = picked_video_name;
        this.indexActiveVideo = getIndexActiveVideo();
        loadSourceVideo(picked_video_name);
        this.isEnd = false;
    }
    
    public Media getMedia() {
        return media;
    }
    
    public int getIndexActiveVideo() {
        for (int i = 0; i < this.lst_video.size(); i++) {
            if (this.lst_video.get(i).equals(this.activeVideo)) {
                return i;
            }
        }
        return 0;
        
    }
    
    @Override
    public void releaseMedia() {
        media = null;
        mediaPlayer = null;
    }
    
    public void toggleMuteOrUnmute() {
        if (isMute()) {
            unmute();
        } else {
            mute();
        }
    }
    
    public void toggleFullScreen() {
        if (isFullScreen()) {
            collapsedFullScreen();
        } else {
            expandfullScreen();
        }
    }
    
    @Override
    public void loadSourceVideo(VideoModel video) {
        try {
            media = new Media(ResourceUtils.getInstance().loadVideo(video.getPath()).toString());
            mediaPlayer = new MediaPlayer(media);
            getListener().onLoadVideo(media.getDuration());
            DoubleProperty mvw = videoView.fitWidthProperty();
            DoubleProperty mvh = videoView.fitHeightProperty();
            mvw.bind(Bindings.selectDouble(videoView.sceneProperty(), "width"));
            mvh.bind(Bindings.selectDouble(videoView.sceneProperty(), "height"));
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.currentTimeProperty().addListener(
                      observable -> getListener()
                                .updateValues(mediaPlayer.getCurrentTime(), media.getDuration()));
            mediaPlayer.setOnReady(
                      () -> getListener()
                                .updateValues(mediaPlayer.getCurrentTime(), media.getDuration()));
            mediaPlayer.setOnPlaying(() -> {
                if (isPlaying()) {
                    getListener().onPlaying();
                }
            });
            mediaPlayer.setOnPaused(() -> {
                if (isPausing()) {
                    getListener().onPause();
                }
            });
            mediaPlayer.setOnEndOfMedia(() -> {
                isEnd = true;
                getListener().onEnd();
            });
            mediaPlayer.setOnError(() -> {
            
            });
            mediaPlayer.errorProperty().addListener((observable, oldValue, newValue) -> {
                switch (newValue.getType()) {
                    case MEDIA_CORRUPTED:
                        LOG.info("corrupted " + newValue.getMessage());
                        return;
                    case PLAYBACK_ERROR:
                        LOG.info("playback error " + newValue.getMessage());
                        return;
                    case PLAYBACK_HALTED:
                        LOG.info("halted " + newValue.getMessage());
                        return;
                    default:
                        LOG.info("dunno what's going on");
                        return;
                }
            });
            setPlaying(true);
            videoView.setMediaPlayer(mediaPlayer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    /*
     * param : current position of slider when user drag time line
     * purpose : calculate the media duration to map with slider position
     * */
    public void calculatedPositionSlider(double sliderposition) {
        if (mediaPlayer != null) {
            mediaPlayer.seek(media.getDuration().multiply(sliderposition / 100.0));
        }
    }
    
    /*
     * param :
     * purpose : turn to previous position
     * */
    @Override
    public void previous() {
        releaseMedia();//release media reference
        if (indexActiveVideo == 0) {
            indexActiveVideo = lst_video.size() - 1;
        } else {
            indexActiveVideo--;
        }
        loadSourceVideo(this.lst_video.get(indexActiveVideo));
    }
    
    public void togglePausePlay() {
        if (!isPlaying()) {
            play();
        } else {
            pause();
        }
    }
  
	/*
   * Progress play another media in mutual controller
	 * prepare : to play video we need MediaView type Media type and MediaPlayer 
	 * with new MediaPlayer(Media) and mediaView.setMediaPlayer(MediaPlayer)
	 * step 1: release media reference and mediaplayer reference
	 * step 2: use loadSourceVideo(VideoModel) function to load resource from file
	 * step 3: enjoy =))
	 * */
    
    @Override
    public void next() {
        releaseMedia();//release media reference
        if (lst_video.size() - 1 == indexActiveVideo) {
            indexActiveVideo = 0;
        } else {
            indexActiveVideo++;
        }
        loadSourceVideo(this.lst_video.get(indexActiveVideo));
    }
    
    @Override
    public void setVolume(double volumn) {
        if (mediaPlayer != null) {
            if (volumn != 0) {
                setCachedvolumebeforemute(volumn);
            }
            mediaPlayer.setVolume(volumn);
        }
        LOG.info("mediaPlayer reference null pls check to set mediaPlayer variable ");
    }
    
    
}
