package com.infinity.stone.controller;

import com.infinity.stone.db.subtitle.Subtitle;
import com.infinity.stone.model.SubtitleCollection;
import com.infinity.stone.model.VideoModel;
import com.infinity.stone.util.TextUtils;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class VideoController extends BaseVideoController {
    
    private static final Logger LOG = Logger
              .getLogger(VideoController.class.getCanonicalName());
    private final List<VideoModel> lst_video;
    private final MediaView videoView;
    private final VideoModel activeVideo;
    private int indexActiveVideo;
    private boolean isEnd;
    private Media media;
    
    public VideoController(MediaView videoView, VideoModel picked_video_name,
              List<VideoModel> lst_video) {
        super(videoView);
        this.videoView = videoView;
        this.videoView.setSmooth(true);
        this.videoView.setPreserveRatio(true);
        this.lst_video = lst_video;
        this.activeVideo = picked_video_name;
        
        this.isEnd = false;
    }
    
    public void setSub(SubtitleCollection subtitleCollection) {
        this.activeVideo.setCollection(subtitleCollection);
        loadSourceVideo(activeVideo);
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
        mediaPlayer.pause();
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
    
    private String trackVideoSub() {
        Duration currentTime = mediaPlayer.getCurrentTime();
        for (Subtitle sub : activeVideo.getCollection().getLstModel()) {
            if (TextUtils.reverseFormatTime(sub.getTimeStart()).lessThan(currentTime) &&
                TextUtils.reverseFormatTime(sub.getTimeEnd()).greaterThan(currentTime)) {
                return sub.getContent();
            }
        }
        return null;
    }
    
    @Override
    public void loadSourceVideo(VideoModel video) {
        try {
            String filePath = TextUtils.formatFilePath(video.getPath());
            URI uri = new URI(filePath.replaceAll(" ", "%20"));
            media = new Media(uri.toString());
            mediaPlayer = new MediaPlayer(media);
            DoubleProperty mvw = videoView.fitWidthProperty();
            DoubleProperty mvh = videoView.fitHeightProperty();
            mvw.bind(Bindings.selectDouble(videoView.sceneProperty(), "width"));
            mvh.bind(Bindings.selectDouble(videoView.sceneProperty(), "height"));
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.currentTimeProperty().addListener(observable -> {
                if (listener != null && mediaPlayer != null) {
                    listener.updateValues(mediaPlayer.getCurrentTime(),
                              media.getDuration(),
                              trackVideoSub());
                }
            });
            mediaPlayer.setOnReady(
                      () -> {
                          if (listener != null) {
                              listener.updateValues(mediaPlayer.getCurrentTime(),
                                        media.getDuration(),
                                        trackVideoSub());
                          }
                      });
            mediaPlayer.setOnPlaying(() -> {
                if (isPlaying() && listener != null) {
                    listener.onPlaying();
                }
            });
            mediaPlayer.setOnPaused(() -> {
                if (isPausing() && listener != null) {
                    listener.onPause();
                }
            });
            mediaPlayer.setOnEndOfMedia(() -> {
                isEnd = true;
                if (listener != null) {
                    listener.onEnd();
                }
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
    
    public void calculatedClickSub(Duration time) {
        if (mediaPlayer != null) {
            mediaPlayer.seek(time);
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
