package com.infinity.stone.controller;

import com.infinity.stone.custom.MyImageView;
import com.infinity.stone.custom.MySliderView;
import com.infinity.stone.model.SubtitleCollection;
import com.infinity.stone.model.SubtitleModel;
import com.infinity.stone.model.VideoModel;
import com.infinity.stone.model.VideoModel.LEVEL;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import javafx.util.Duration;

public class TabController implements Initializable {
    
    private static final Logger LOG = Logger
              .getLogger(TabController.class.getCanonicalName());
    
    @FXML
    private TabPane tabPane;
    
    @FXML
    private MediaView videoPlayer;
    
    @FXML
    private JFXListView listViewSubtitle;
    
    @FXML
    private AnchorPane rightPaneSide;
    
    @FXML
    private JFXSlider timeLine;
    @FXML
    private HBox controllerContainer;
    
    @FXML
    private VBox slidercontrollercontainer;
    EventHandler<Event> onMouseEnterVideoContainer = new EventHandler<Event>() {
        @Override
        public void handle(Event e) {
            slidercontrollercontainer.setOpacity(1.0);
        }
    };
    EventHandler<Event> onMouseExitVideoContainer = new EventHandler<Event>() {
        @Override
        public void handle(Event e) {
            slidercontrollercontainer.setOpacity(0.0);
        }
    };
    @FXML
    private AnchorPane mediaContainer;
    @FXML
    private SplitPane splitPane;
    @FXML
    private JFXListView listviewSubtitleFullScreen;
    
    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
                             - elapsedMinutes * 60;
        
        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60 -
                                  durationMinutes * 60;
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d",
                          elapsedHours, elapsedMinutes, elapsedSeconds,
                          durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d",
                          elapsedMinutes, elapsedSeconds, durationMinutes,
                          durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours,
                          elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d", elapsedMinutes,
                          elapsedSeconds);
            }
        }
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        setUpListView();
        setUpController();
    }
    
    private void setUpController() {
        try {
            
            MyImageView playImageView = new MyImageView("ic_play_18dp_2x.png");
            MyImageView toggleSubImageView = new MyImageView("ic_subtitles_white_18dp_2x.png");
            MyImageView fullWidthImageView = new MyImageView("ic_fullscreen_white_18dp_2x.png");
            MyImageView nextImageView = new MyImageView("ic_skip_next_white_18dp_2x.png");
            MyImageView previousImageView = new MyImageView("ic_skip_previous_white_18dp_2x.png");
            MyImageView volumeImageView = new MyImageView("ic_volume_max_white_18dp_2x.png");
            Label lblplaytime = new Label();
            lblplaytime.getStyleClass().add("txt_play_time");
            Region region = new Region();
            region.setPrefWidth(200.0);
            TranslateTransition transition = new TranslateTransition(Duration.millis(500),
                      listviewSubtitleFullScreen);
            transition.setByX(-512);
            transition.setFromX(512);
            listviewSubtitleFullScreen.setVisible(false);
            VideoController controller = new VideoController(videoPlayer,
                      new VideoModel("video.mp4", LEVEL.ADVANCE),
                      new ArrayList<>(), new BaseVideoController.OnVideoControllerListener() {
                
                @Override
                public void onPause() {
                    playImageView.setImage("ic_play_18dp_2x.png");
                }
                
                @Override
                public void onPlaying() {
                    playImageView.setImage("ic_pause_white_18dp_2x.png");
                }
                
                @Override
                public void onEnd() {
                    // TODO Auto-generated method stub
                    
                }
                
                @Override
                public void updateValues(Duration currentTime, Duration contentLength) {
                    Platform.runLater(() -> {
                        if (!timeLine.isDisable() && contentLength.greaterThan(Duration.ZERO)) {
                            
                            timeLine.setValue(
                                      currentTime.divide(contentLength.toMillis()).toMillis() *
                                      100.0);
                            lblplaytime.setText(formatTime(currentTime, contentLength));
                        }
                        
                        // playTime.setText(formatTime(currentTime, duration));
                    });
                }
                
                @Override
                public void onReady() {
                    // TODO Auto-generated method stub
                    
                }
                
                @Override
                public void onMute() {
                    volumeImageView.setImage("ic_volume_mute_white_18dp_2x.png");
                }
                
                @Override
                public void onUnMute(double value) {
                    //slider.setValue(value);
                }
                
                @Override
                public void onLoadVideo(Duration contentlength) {
                    //timeLine.setMax(contentlength.toMinutes());
                }
                
                @Override
                public void onFullScreen(boolean isFullScreen) {
                    if (isFullScreen) {
                        splitPane.setDividerPositions(1);
                        rightPaneSide.setMaxWidth(0);
                        rightPaneSide.setManaged(false);
                        fullWidthImageView.setImage("ic_fullscreen_exit_white_18dp_2x.png");
                        listviewSubtitleFullScreen.setVisible(true);
                        transition.play();
                        
                    } else {
                        rightPaneSide.setMaxWidth(512.0);
                        rightPaneSide.setManaged(true);
                        listviewSubtitleFullScreen.setVisible(false);
                        //splitPane.setDividerPositions(1);
                        fullWidthImageView.setImage("ic_fullscreen_white_18dp_2x.png");
                    }
                    
                }
            });
            fullWidthImageView.setOnMouseClicked(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    controller.toggleFullScreen();
                }
                
            });
            JFXSlider slider = new MySliderView(0, 1, 1) {
                @Override
                public JFXSlider apply(JFXSlider slider) {
                    // TODO Auto-generated method stub
                    slider.setIndicatorPosition(IndicatorPosition.LEFT);
                    return slider;
                }
                
                @Override
                public void onMute() {
                    volumeImageView.setImage("ic_volume_mute_white_18dp_2x.png");
                }
                
                @Override
                public void onMin() {
                    volumeImageView.setImage("ic_volume_min_white_18dp_2x.png");
                }
                
                @Override
                public void onMax() {
                    volumeImageView.setImage("ic_volume_max_white_18dp_2x.png");
                }
                
                @Override
                public void onAverage() {
                    volumeImageView.setImage("ic_volume_average_white_18dp_2x.png");
                }
                
                @Override
                public void onValueChanged(double value) {
                    controller.setVolume(value);
                }
                
            };
            
            mediaContainer.setOnMouseEntered(onMouseEnterVideoContainer);
            mediaContainer.setOnMouseExited(onMouseExitVideoContainer);
            controllerContainer.getChildren().addAll(
                      playImageView,
                      previousImageView,
                      nextImageView,
                      lblplaytime,
                      volumeImageView,
                      slider,
                      region,
                      toggleSubImageView,
                      fullWidthImageView);
            controllerContainer.setHgrow(region, Priority.ALWAYS);
            playImageView.setOnMouseClicked(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    controller.togglePausePlay();
                }
                
            });
            volumeImageView.setOnMouseClicked(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    controller.toggleMuteOrUnmute();
                }
            });
            timeLine.valueProperty().addListener(new InvalidationListener() {
                
                @Override
                public void invalidated(Observable observable) {
                    if (timeLine.isValueChanging()) {
                        controller.calculatedPositionSlider(timeLine.getValue());
                    }
                    
                }
            });
            
            SubtitleCollection collection = new SubtitleCollection();
            collection.addAll(Arrays.asList(
                      new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                      new SubtitleModel("02:41", "When in Rome, do as the Romans"),
                      new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                      new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                      new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                      new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                      new SubtitleModel("03:12", "The pen is mightier than the sword"),
                      new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                      new SubtitleModel("02:41", "When in Rome, do as the Romans"),
                      new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                      new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                      new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                      new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                      new SubtitleModel("03:12", "The pen is mightier than the sword"),
                      new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                      new SubtitleModel("02:41", "When in Rome, do as the Romans"),
                      new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                      new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                      new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                      new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                      new SubtitleModel("03:12", "The pen is mightier than the sword"),
                      new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                      new SubtitleModel("02:41", "When in Rome, do as the Romans"),
                      new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                      new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                      new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                      new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                      new SubtitleModel("03:12", "The pen is mightier than the sword"),
                      new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                      new SubtitleModel("02:41", "When in Rome, do as the Romans"),
                      new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                      new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                      new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                      new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                      new SubtitleModel("03:12", "The pen is mightier than the sword"),
                      new SubtitleModel("05:51", "Two wrongs don't make a right")));
            listviewSubtitleFullScreen.setItems(collection.getLstModel());
            listviewSubtitleFullScreen.setCellFactory(
                      new Callback<JFXListView<SubtitleModel>, JFXListCell<SubtitleModel>>() {
                          @Override
                          public JFXListCell<SubtitleModel> call(JFXListView<SubtitleModel> param) {
                              return new ListViewTransparentCell();
                          }
                          
                      });
        } catch (Exception e) {
            e.printStackTrace();
            LOG.info(e.getMessage());
        }
    }
    
    private void setUpListView() {
        SubtitleCollection collection = new SubtitleCollection();
        collection.addAll(Arrays.asList(
                  new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                  new SubtitleModel("02:41", "When in Rome, do as the Romans"),
                  new SubtitleModel("03:12", "The pen is mightier than the sword"),
                  new SubtitleModel("05:51", "Two wrongs don't make a right"),
                  new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                  new SubtitleModel("02:41", "When in Rome, do as the Romans"),
                  new SubtitleModel("03:12", "The pen is mightier than the sword"),
                  new SubtitleModel("05:51", "Two wrongs don't make a right"),
                  new SubtitleModel("01:12", "The squeaky wheel gets the grease"),
                  new SubtitleModel("02:41", "When in Rome, do as the Romans"),
                  new SubtitleModel("03:12", "The pen is mightier than the sword"),
                  new SubtitleModel("05:51", "Two wrongs don't make a right")));
        listViewSubtitle.setItems(collection.getLstModel());
        listViewSubtitle.setCellFactory(
                  new Callback<JFXListView<SubtitleModel>, JFXListCell<SubtitleModel>>() {
                      @Override
                      public JFXListCell<SubtitleModel> call(JFXListView<SubtitleModel> param) {
                          return new ListViewCell();
                      }
                      
                  });
    }
    
    static class ListViewCell extends JFXListCell<SubtitleModel> {
        
        HBox hbox = new HBox();
        Label lblTime = new Label();
        Label lblSentence = new Label();
        
        @Override
        protected void updateItem(SubtitleModel item, boolean empty) {
            super.updateItem(item, empty);
            try {
                if (item != null) {
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(null);
                        setUpLabelTime(lblTime);
                        setUpLabelSentence(lblSentence);
                        hbox.getChildren().addAll(lblTime, lblSentence);
                        lblTime.setText(item.getTime());
                        lblSentence.setText(item.getSentence());
                        setGraphic(hbox);
                        
                    }
                } else {
                    setGraphic(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        private void setUpLabelTime(Label label) {
            label.setTextAlignment(TextAlignment.LEFT);
            label.setAlignment(Pos.CENTER_LEFT);
            label.setPrefWidth(170.0);
        }
        
        private void setUpLabelSentence(Label label) {
            label.setTextAlignment(TextAlignment.JUSTIFY);
            label.setAlignment(Pos.CENTER_LEFT);
            label.setPrefWidth(340.0);
            label.setWrapText(true);
        }
    }
    
    static class ListViewTransparentCell extends JFXListCell<SubtitleModel> {
        
        HBox hbox = new HBox();
        Label lblTime = new Label();
        Label lblSentence = new Label();
        
        @Override
        protected void updateItem(SubtitleModel item, boolean empty) {
            super.updateItem(item, empty);
            try {
                if (item != null) {
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(null);
                        setUpLabelTime(lblTime);
                        setUpLabelSentence(lblSentence);
                        hbox.getChildren().addAll(lblTime, lblSentence);
                        lblTime.setText(item.getTime());
                        lblSentence.setText(item.getSentence());
                        setGraphic(hbox);
                        
                    }
                } else {
                    setGraphic(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        private void setUpLabelTime(Label label) {
            label.setTextAlignment(TextAlignment.LEFT);
            label.setAlignment(Pos.CENTER);
            label.getStyleClass().add("white_text");
            label.setPrefWidth(170.0);
            label.setTextFill(Color.WHITE);
        }
        
        private void setUpLabelSentence(Label label) {
            label.setTextAlignment(TextAlignment.JUSTIFY);
            label.setAlignment(Pos.CENTER_LEFT);
            label.getStyleClass().add("white_text");
            label.setPrefWidth(340.0);
            label.setWrapText(true);
            label.setTextFill(Color.WHITE);
        }
    }
    
    
}
