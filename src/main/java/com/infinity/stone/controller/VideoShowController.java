package com.infinity.stone.controller;

import com.infinity.stone.custom.MyImageView;
import com.infinity.stone.custom.MySliderView;
import com.infinity.stone.db.RepositoryManager;
import com.infinity.stone.db.RepositoryType;
import com.infinity.stone.db.favorite.Favorite;
import com.infinity.stone.db.favorite.FavoriteRepository;
import com.infinity.stone.db.favorite.FavoriteRepositoryImpl;
import com.infinity.stone.db.subtitle.Subtitle;
import com.infinity.stone.db.subtitle.SubtitleRepository;
import com.infinity.stone.model.SubtitleCollection;
import com.infinity.stone.model.VideoModel;
import com.infinity.stone.model.VideoModel.LEVEL;
import com.infinity.stone.tracking.Action;
import com.infinity.stone.tracking.TrackingManager;
import com.infinity.stone.util.Constant;
import com.infinity.stone.util.TextUtils;
import com.infinity.stone.youtube.DownloadCaptionManager;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXSnackbar;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import javafx.util.Duration;

@SuppressWarnings("unchecked")
public class VideoShowController implements Initializable,
                                            BaseVideoController.OnVideoControllerListener,
                                            OnClickFavorite {
    
    private static final Logger LOG = Logger
              .getLogger(VideoShowController.class.getCanonicalName());
    private final FavoriteRepository mFavoriteRepository = (FavoriteRepository) RepositoryManager
              .getInstance(RepositoryType.FAVORITE);
    @FXML
    public AnchorPane mainPane;
    SubtitleCollection mSubtitleCollection;
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
    final EventHandler<Event> onMouseEnterVideoContainer = new EventHandler<Event>() {
        @Override
        public void handle(Event e) {
            slidercontrollercontainer.setOpacity(1.0);
        }
    };
    final EventHandler<Event> onMouseExitVideoContainer = new EventHandler<Event>() {
        @Override
        public void handle(Event e) {
            slidercontrollercontainer.setOpacity(0.0);
        }
    };
    @FXML
    private Label subtilelabel;
    private FavoriteRepository favouriteRepo;
    @FXML
    private AnchorPane mediaContainer;
    @FXML
    private SplitPane splitPane;
    //    @FXML
    //    private JFXListView listviewSubtitleFullScreen;
    private DownloadCaptionManager mDownloadCaptionManager;
    private String videoPath;
    private MyImageView playImageView;
    private MyImageView toggleSubImageView;
    private MyImageView fullWidthImageView;
    private MyImageView nextImageView;
    private MyImageView previousImageView;
    private MyImageView volumeImageView;
    private Region region;
    private Label lblplaytime;
    private VideoController controller;
    private TranslateTransition transition;
    private JFXSlider slider;
    private SubtitleController subController;
    SubtitleRepository subtitleRepository;
    
    public String getVideoPath() {
        return videoPath;
    }
    
    public void setVideoPath(String path) {
        this.videoPath = path;
    }
    
    public VideoShowController() {
    
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        favouriteRepo = new FavoriteRepositoryImpl();
        subtitleRepository = (SubtitleRepository) RepositoryManager
                  .getInstance(RepositoryType.SUBTITLE);
        init();
        subtitleRepository.findAllSubtitleByVideoId(Constant.CURRENT_VIDEO_ID)
                  .doOnSubscribe(onSubscribe -> {
                      setUpVideoController();
                  })
                  .subscribe(success -> {
                      mSubtitleCollection = SubtitleCollection
                                .makeSubtitleCollectionFromListSub(success);
                      mSubtitleCollection.printValue();
                      controller.setSubtitle(mSubtitleCollection);
                      setUpSubTitleController();
                      setUpListSubTitle();
                  }, throwable -> {
                      throwable.printStackTrace();
                  });
        
        
    }
    
    public void init() {
        
        setUpSlider();
        
        setUpTimeline();
    }
    
    private void setUpVideoController() {
        playImageView = new MyImageView("ic_play_18dp_2x.png");
        toggleSubImageView = new MyImageView("ic_subtitles_white_18dp_2x.png");
        //fullWidthImageView = new MyImageView("ic_fullscreen_white_18dp_2x.png");
        nextImageView = new MyImageView("ic_skip_next_white_18dp_2x.png");
        previousImageView = new MyImageView("ic_skip_previous_white_18dp_2x.png");
        volumeImageView = new MyImageView("ic_volume_max_white_18dp_2x.png");
        lblplaytime = new Label();
        lblplaytime.getStyleClass().add("txt_play_time");
        region = new Region();
        region.setPrefWidth(200.0);
        //        transition = new TranslateTransition(Duration.millis(500),
        //                  listviewSubtitleFullScreen);
        //        transition.setByX(-512);
        //        transition.setFromX(512);
        //        listviewSubtitleFullScreen.setVisible(false);
        //        mediaContainer.setOnMouseEntered(onMouseEnterVideoContainer);
        //        mediaContainer.setOnMouseExited(onMouseExitVideoContainer);
        toggleSubImageView.setOnMouseClicked(event -> subController.toggleSub());
        //        fullWidthImageView.setOnMouseClicked(
        //                  (EventHandler<Event>) event -> controller.toggleFullScreen());
        
        controllerContainer.getChildren().addAll(
                  playImageView,
                  previousImageView,
                  nextImageView,
                  lblplaytime,
                  volumeImageView,
                  slider,
                  region,
                  toggleSubImageView);
        // fullWidthImageView);
        controllerContainer.setHgrow(region, Priority.ALWAYS);
        
        controller = new VideoController(videoPlayer,
                  new VideoModel(Constant.CURRENT_VIDEO_PATH, mSubtitleCollection, LEVEL.ADVANCE),
                  new ArrayList<>());
        playImageView.setOnMouseClicked(
                  (EventHandler<Event>) event -> controller.togglePausePlay());
        volumeImageView.setOnMouseClicked(
                  (EventHandler<Event>) event -> controller.toggleMuteOrUnmute());
        timeLine.valueProperty().addListener(observable -> {
            if (timeLine.isValueChanging()) {
                controller.calculatedPositionSlider(timeLine.getValue());
            }
        });
        controller.setOnVideoControllerListener(this);
    }
    
    private void setUpSlider() {
        slider = new MySliderView(0, 1, 1) {
            @Override
            public JFXSlider apply(JFXSlider slider) {
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
    }
    
    private void setUpSubTitleController() {
        subController = new SubtitleController(subtilelabel);
    }
    
    private void setUpListSubTitle() {
        listViewSubtitle.setItems(mSubtitleCollection.getLstModel());
        listViewSubtitle.setCellFactory(
                  (Callback<JFXListView<Subtitle>, JFXListCell<Subtitle>>) param ->
                            new ListViewCell(this, listViewSubtitle));
        listViewSubtitle.setOnMouseClicked(event -> {
            controller.calculatedClickSub(TextUtils
                      .reverseFormatTime(
                                ((Subtitle) listViewSubtitle.getSelectionModel().getSelectedItem())
                                          .getTimeStart()));
        });
    }
    
    //    private void setUpListSubTitleTransparent() {
    //        listviewSubtitleFullScreen.setItems(mSubtitleCollection.getLstModel());
    //        listviewSubtitleFullScreen.setCellFactory(
    //                  (Callback<JFXListView<Subtitle>, JFXListCell<Subtitle>>) param ->
    //                            new ListViewTransparentCell());
    //    }
    
    private void setUpTimeline() {
        timeLine.setOnMouseClicked(event -> {
            timeLine.setValueChanging(true);
            double value = (event.getX() / timeLine.getWidth()) * timeLine.getMax();
            timeLine.setValue(value);
            timeLine.setValueChanging(false);
        });
    }
    
    @Override
    public void onClickFavorite(Subtitle subtitle) {
        mFavoriteRepository.create(new Favorite(subtitle.getVideoId(), subtitle.getId()))
                  .subscribe(success -> {
                      JFXSnackbar snackbar = new JFXSnackbar(rightPaneSide);
                      snackbar.show("Add to favorite successfully", 1000);
                      TrackingManager.getInstance()
                                .track(Action.ADD_FAVORITE, subtitle.getContent());
                  }, throwable -> {
                      System.out.print(throwable.getMessage());
                  });
        
    }
    
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
    public void updateValues(Duration currentTime, Duration contentLength, String subVideo) {
        Platform.runLater(() -> {
            if (!timeLine.isDisable() && contentLength.greaterThan(Duration.ZERO)) {
                
                timeLine.setValue(
                          currentTime.divide(contentLength.toMillis()).toMillis() *
                          100.0);
                if (subVideo == null) {
                    subController.setText(subVideo);
                    subController.hideSub();
                } else {
                    subController.showSub();
                    subController.setText(subVideo);
                }
                
                lblplaytime.setText(TextUtils.formatTime(currentTime, contentLength));
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
    public void onFullScreen(boolean isFullScreen) {
        if (isFullScreen) {
            splitPane.setDividerPositions(1);
            rightPaneSide.setMaxWidth(0);
            rightPaneSide.setManaged(false);
            fullWidthImageView.setImage("ic_fullscreen_exit_white_18dp_2x.png");
            // listviewSubtitleFullScreen.setVisible(true);
            transition.play();
            
        } else {
            rightPaneSide.setMaxWidth(512.0);
            rightPaneSide.setManaged(true);
            // listviewSubtitleFullScreen.setVisible(false);
            //splitPane.setDividerPositions(1);
            fullWidthImageView.setImage("ic_fullscreen_white_18dp_2x.png");
        }
        
    }
    
    static class ListViewCell extends JFXListCell<Subtitle> {
        
        final HBox hbox = new HBox();
        final Label lblTime = new Label();
        final Label lblSentence = new Label();
        final BorderPane borderPane = new BorderPane();
        final Button button = new Button();
        final MyImageView image = new MyImageView("ic_add_black_18dp_1x.png");
        private OnClickFavorite mOnClickFavorite;
        
        public ListViewCell(OnClickFavorite onClickFavorite, JFXListView listViewSubtitle) {
            prefWidthProperty().bind(listViewSubtitle.widthProperty().subtract(2));
            setMaxWidth(Control.USE_PREF_SIZE);
            this.mOnClickFavorite = onClickFavorite;
        }
        
        @Override
        protected void updateItem(Subtitle item, boolean empty) {
            super.updateItem(item, empty);
            try {
                
                if (item != null) {
                    setText(null);
                    setUpLabelTime(lblTime);
                    setUpLabelSentence(lblSentence);
                    hbox.getChildren().clear();
                    borderPane.setCenter(button);
                    button.setGraphic(image);
                    button.setOnMouseClicked(event -> {
                        if (mOnClickFavorite != null) {
                            mOnClickFavorite.onClickFavorite(item);
                        }
                    });
                    hbox.setSpacing(30);
                    hbox.getChildren().addAll(lblTime, lblSentence, borderPane);
                    lblTime.setText(item.getTimeStart());
                    lblSentence.setText(item.getContent());
                    setGraphic(hbox);
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
    
    static class ListViewTransparentCell extends JFXListCell<Subtitle> {
        
        final HBox hbox = new HBox();
        final Label lblTime = new Label();
        final Label lblSentence = new Label();
        
        @Override
        protected void updateItem(Subtitle item, boolean empty) {
            super.updateItem(item, empty);
            try {
                if (item != null) {
                    setText(null);
                    setUpLabelTime(lblTime);
                    setUpLabelSentence(lblSentence);
                    hbox.getChildren().clear();
                    hbox.getChildren().addAll(lblTime, lblSentence);
                    lblTime.setText(item.getTimeStart());
                    lblSentence.setText(item.getContent());
                    setGraphic(hbox);
                    
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
