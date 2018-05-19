package com.infinity.stone.controller;

import com.infinity.stone.custom.MyImageView;
import com.infinity.stone.custom.MySliderView;
import com.infinity.stone.db.RepositoryManager;
import com.infinity.stone.db.RepositoryType;
import com.infinity.stone.db.favorite.Favorite;
import com.infinity.stone.db.favorite.FavoriteRepository;
import com.infinity.stone.db.favorite.FavoriteRepositoryImpl;
import com.infinity.stone.db.subtitle.Subtitle;
import com.infinity.stone.db.subtitle.SubtitleRepositoryImpl;
import com.infinity.stone.model.SubtitleCollection;
import com.infinity.stone.model.VideoModel;
import com.infinity.stone.model.VideoModel.LEVEL;
import com.infinity.stone.tracking.Action;
import com.infinity.stone.tracking.TrackingManager;
import com.infinity.stone.util.Constant;
import com.infinity.stone.util.ResourceUtils;
import com.infinity.stone.util.TextUtils;
import com.infinity.stone.util.TrackingSubUtils;
import com.infinity.stone.youtube.DownloadCaptionManager;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXSlider.IndicatorPosition;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

@SuppressWarnings("unchecked")
public class VideoShowController implements Initializable,
                                            BaseVideoController.OnVideoControllerListener,
                                            OnClickFavorite, TrackingSubUtils.OnChangeListener {
    
    private static final Logger LOG = Logger
              .getLogger(VideoShowController.class.getCanonicalName());
    
    @FXML
    public AnchorPane mainPane;
    @FXML
    public JFXTextField mTxtSearchFavorite;
    
    SubtitleCollection mSubtitleCollection;
    SubtitleCollection mFavoriteSubtitleCollection;
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
    @FXML
    private JFXListView favouriteListview;
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
    @FXML
    private ImageView backImageView;
    private FavoriteRepository favouriteRepo;
    @FXML
    private AnchorPane mediaContainer;
    @FXML
    private SplitPane splitPane;
    @FXML
    private JFXListView listviewSubtitleFullScreen;
    private DownloadCaptionManager mDownloadCaptionManager;
    private String videoPath;
    @FXML private ImageView playImageView;
    @FXML private ImageView toggleSubImageView;
//    private MyImageView fullWidthImageView;
//    private MyImageView nextImageView;
//    private MyImageView previousImageView;
    @FXML private ImageView volumeImageView;
    @FXML private Region region;
    @FXML private Label lblplaytime;
    private VideoController controller;
    private TranslateTransition transition;
    @FXML private JFXSlider slider;
    private SubtitleController subController;
    private TrackingSubUtils mTrackingSubUtils;
    
    public String getVideoPath() {
        return videoPath;
    }
    
    public void setVideoPath(String path) {
        this.videoPath = path;
    }
    
    private FavoriteRepositoryImpl mFavoriteRepository;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        mFavoriteRepository = (FavoriteRepositoryImpl) RepositoryManager
                  .getInstance(RepositoryType.FAVORITE);
        
        SubtitleRepositoryImpl subtitleRepository = (SubtitleRepositoryImpl) RepositoryManager
                  .getInstance(RepositoryType.SUBTITLE);
        
        List<Subtitle> subtitleList = subtitleRepository
                  ._findAllSubtitleByVideoId(Constant.CURRENT_VIDEO_ID);
        init();
        
        if (subtitleList != null) {
            mSubtitleCollection = SubtitleCollection
                      .makeSubtitleCollectionFromListSub(subtitleList);
            controller.setSub(mSubtitleCollection);
            setUpSubTitleController();
            setUpListSubTitle();
            setUpTrackingSub();
            
            List<Favorite> favoriteList = mFavoriteRepository
                      ._findFavoriteByVideoId(Constant.CURRENT_VIDEO_ID);
            if (favoriteList != null) {
                mFavoriteSubtitleCollection =
                          SubtitleCollection
                                    .makeSubtitleCollectionFromListFavorite(
                                              favoriteList, subtitleList);
                setUpFavoriteSubTitle();
            } else {
                TrackingManager.getInstance().track(Action.ERROR, " failure get list favorite");
            }
        } else {
            TrackingManager.getInstance().track(Action.ERROR, " failure get list sub");
        }
        
    }
    
    public void init() {
        tabPane.getSelectionModel().selectedItemProperty().addListener(
                  (observable, oldValue, newValue) -> {
                      if (newValue.getText().equals("Subtitle")) {
                          TrackingManager.getInstance()
                                    .track(Action.CHOOSE_TAB_SUB, "at " + new Date());
                      } else if (newValue.getText().equals("Favourite")) {
                          TrackingManager.getInstance()
                                    .track(Action.CHOOSE_TAG_FAVORITE, "at " + new Date());
                      }
                  });
        setUpVideoController();
        
        setUpSlider();
        setUpTimeline();
    }
    
    private void setUpVideoController() {
        playImageView.setImage(new Image(
                ResourceUtils.getInstance().loadIcon("ic_play_18dp_2x.png").toString()));
        toggleSubImageView.setImage(new Image(
                ResourceUtils.getInstance().loadIcon("ic_subtitles_white_18dp_2x.png").toString()));
        
        //fullWidthImageView = new MyImageView("ic_fullscreen_white_18dp_2x.png");
        //nextImageView = new MyImageView("ic_skip_next_white_18dp_2x.png");
        //previousImageView = new MyImageView("ic_skip_previous_white_18dp_2x.png");
        volumeImageView.setImage(new Image(
                ResourceUtils.getInstance().loadIcon("ic_volume_max_white_18dp_2x.png").toString()));
        backImageView.setImage(new Image(
                  ResourceUtils.getInstance().loadIcon("round_back_white_18dp2x.png").toString()));
        backImageView.setOnMouseClicked(this::openSelectVideoScreen);
        
        //lblplaytime = new Label();
        lblplaytime.getStyleClass().add("txt_play_time");
        //region = new Region();
        region.setPrefWidth(200.0);
        
        transition = new TranslateTransition(Duration.millis(500),
                  listviewSubtitleFullScreen);
        transition.setByX(-512);
        transition.setFromX(512);
        listviewSubtitleFullScreen.setVisible(false);
        mediaContainer.setOnMouseEntered(onMouseEnterVideoContainer);
        mediaContainer.setOnMouseExited(onMouseExitVideoContainer);
        toggleSubImageView.setOnMouseClicked(event -> subController.toggleSub());
//        fullWidthImageView.setOnMouseClicked(
//                  (EventHandler<Event>) event -> controller.toggleFullScreen());
        
        //        controllerContainer.getChildren().addAll(
        //                  playImageView,
        //                  lblplaytime,
        //                  volumeImageView,
        //                  slider,
        //                  region,
        //                  toggleSubImageView);
        //        // fullWidthImageView);
        //        controllerContainer.setHgrow(region, Priority.ALWAYS);
        
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
    
    
    private void openSelectVideoScreen(Event event) {
        Parent mainLayout;
        try {
            controller.releaseMedia();
            mainLayout = FXMLLoader
                      .load(ResourceUtils.getInstance().loadLayout("select_video.fxml"));
            
            Scene mainScreen = new Scene(mainLayout);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Platform.setImplicitExit(true);
            appStage.close();
            appStage.sizeToScene();
            appStage.centerOnScreen();
            Parent finalMainLayout = mainLayout;
            appStage.widthProperty().addListener((observable, oldValue, newValue) -> {
                finalMainLayout.prefWidth(newValue.doubleValue());
                LOG.info(finalMainLayout.toString());
            });
            appStage.setResizable(false);
            appStage.setScene(mainScreen);
            appStage.show();
        } catch (IOException e) {
            TrackingManager.getInstance().track(Action.ERROR, e.getCause());
            e.printStackTrace();
        }
    }
    
    private void setUpSlider() {
    	slider.valueProperty().addListener((observable, oldValue, newValue) -> {
    		if (newValue.doubleValue() > 0.0 && newValue.doubleValue() < 30.0) {
    			volumeImageView.setImage(new Image(
                        ResourceUtils.getInstance().loadIcon("ic_volume_min_white_18dp_2x.png").toString()));
            }
            if (newValue.doubleValue() >= 0.7 && newValue.doubleValue()<= 1) {
            	volumeImageView.setImage(new Image(
                        ResourceUtils.getInstance().loadIcon("ic_volume_max_white_18dp_2x.png").toString()));
            }
            if (newValue.doubleValue() == 0.0) {
            	volumeImageView.setImage(new Image(
                        ResourceUtils.getInstance().loadIcon("ic_volume_mute_white_18dp_2x.png").toString()));
            }
            if (newValue.doubleValue() >= 0.3 && newValue.doubleValue() < 0.7) {
            	volumeImageView.setImage(new Image(
                        ResourceUtils.getInstance().loadIcon("ic_volume_average_white_18dp_2x.png").toString()));
            }
            controller.setVolume(newValue.doubleValue());
        });
        slider.setIndicatorPosition(IndicatorPosition.LEFT);
        slider.setValueFactory(param -> Bindings.createStringBinding(
                  () -> ((int) param.getValue() * 100) + "%",
                  param.valueProperty()));

        /*slider = new MySliderView(0, 1, 1) {
            @Override
            public JFXSlider apply(JFXSlider slider) {
                
                return slider;
            }
            
            
        };*/
    }
    
    
    private void setUpTrackingSub() {
        mTrackingSubUtils = new TrackingSubUtils(mSubtitleCollection);
        mTrackingSubUtils.setOnChangeListener(this);
    }
    
    @Override
    public void onChangedSub(Subtitle sub) {
        listViewSubtitle.getSelectionModel().select(sub);
        listViewSubtitle.getFocusModel().focus(mSubtitleCollection.getLstModel().indexOf(sub));
        listViewSubtitle.scrollTo(sub);
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
            if (event.getButton() == MouseButton.PRIMARY) {
                Subtitle item = (Subtitle) listViewSubtitle.getSelectionModel()
                          .getSelectedItem();
                controller.calculatedClickSub(TextUtils
                          .reverseFormatTime(
                                    item.getTimeStart()));
                TrackingManager.getInstance().track(Action.CLICK_TO_SUB, item.getContent());
            }
        });
    }
    
    private void setUpListSubTitleTransparent() {
        listviewSubtitleFullScreen.setItems(mSubtitleCollection.getLstModel());
        listviewSubtitleFullScreen.setCellFactory(
                  (Callback<JFXListView<Subtitle>, JFXListCell<Subtitle>>) param ->
                            new ListViewTransparentCell());
    }
    
    private void filterFavorite(String oldValue, String newValue) {
        
        ObservableList<Subtitle> filteredList = FXCollections.observableArrayList();
        if (mTxtSearchFavorite == null || (newValue.length() < oldValue.length()) ||
            newValue == null) {
            favouriteListview.setItems(mFavoriteSubtitleCollection.getLstModel());
        } else {
            String input = newValue.toUpperCase();
            for (Subtitle sub : mFavoriteSubtitleCollection.getLstModel()) {
                String filterText = sub.getContent();
                if (filterText.toUpperCase().contains(input)) {
                    filteredList.add(sub);
                }
            }
            favouriteListview.setItems(filteredList);
        }
    }
    
    private void setUpFavoriteSubTitle() {
        mTxtSearchFavorite.textProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                filterFavorite((String) oldValue, (String) newValue);
            }
        });
        favouriteListview.setItems(mFavoriteSubtitleCollection.getLstModel());
        favouriteListview
                  .setCellFactory((Callback<JFXListView<Subtitle>, JFXListCell<Subtitle>>) param ->
                            new ListViewFavouriteCell(sub -> {
                                long resultRemoveFavorite = mFavoriteRepository
                                          ._delete(new Favorite(sub.getVideoId(), sub.getId()));
                                if (resultRemoveFavorite >= 0) {
                                    JFXSnackbar snackbar = new JFXSnackbar(rightPaneSide);
                                    snackbar.show("Remove favorite successfully", 1000);
                                    TrackingManager.getInstance()
                                              .track(Action.DELETE_FAVORITE,
                                                        sub.getContent());
                                } else {
                                    TrackingManager.getInstance()
                                              .track(Action.ERROR, "failure delete favorite " +
                                                                   sub.getContent());
                                }
                            }, favouriteListview));
        
    }
    
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
        long resultAddFavorite = mFavoriteRepository
                  ._create(new Favorite(subtitle.getVideoId(), subtitle.getId()));
        if (resultAddFavorite >= 0) {
            if (!mFavoriteSubtitleCollection.getLstModel().contains(subtitle)) {
                mFavoriteSubtitleCollection.add(subtitle);
            }
            JFXSnackbar snackbar = new JFXSnackbar(rightPaneSide);
            snackbar.show("Add to favorite successfully: " + subtitle.getContent(), 1000);
            TrackingManager.getInstance()
                      .track(Action.ADD_FAVORITE, subtitle.getContent());
        } else {
            TrackingManager.getInstance()
                      .track(Action.ERROR, " add favourite failure: " + subtitle.getContent());
        }
        
    }
    
    @Override
    public void onPause() {
        playImageView.setImage(new Image(
                ResourceUtils.getInstance().loadIcon("ic_play_18dp_2x.png").toString()));
    }
    
    @Override
    public void onPlaying() {
        playImageView.setImage(new Image(
                ResourceUtils.getInstance().loadIcon("ic_pause_white_18dp_2x.png").toString()));
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
                if (!hoverListViewSubtitle) {
                    mTrackingSubUtils.trackingSub(currentTime);
                }
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
        listViewSubtitle.addEventFilter(MouseEvent.MOUSE_ENTERED, event -> {
            hoverListViewSubtitle = true;
        });
        listViewSubtitle.addEventFilter(MouseEvent.MOUSE_EXITED, event -> {
            hoverListViewSubtitle = false;
        });
    }
    
    private boolean hoverListViewSubtitle = false;
    
    
    @Override
    public void onReady() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void onMute() {
        volumeImageView.setImage(new Image(
                ResourceUtils.getInstance().loadIcon("ic_volume_mute_white_18dp_2x.png").toString()));
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
            //fullWidthImageView.setImage("ic_fullscreen_exit_white_18dp_2x.png");
            listviewSubtitleFullScreen.setVisible(true);
            transition.play();
            
        } else {
            rightPaneSide.setMaxWidth(512.0);
            rightPaneSide.setManaged(true);
            listviewSubtitleFullScreen.setVisible(false);
            //splitPane.setDividerPositions(1);
            //fullWidthImageView.setImage("ic_fullscreen_white_18dp_2x.png");
        }
        
    }
    
    
    static class ListViewCell extends JFXListCell<Subtitle> {
        
        public static final String highlightLabelClass = "label_sentence";
        final HBox hbox = new HBox();
        final Label lblTime = new Label();
        final Label lblSentence = new Label();
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
                    hbox.setOnMouseClicked(event -> {
                        if (event.getButton() == MouseButton.SECONDARY) {
                            if (mOnClickFavorite != null) {
                                mOnClickFavorite.onClickFavorite(item);
                            }
                        }
                    });
                    hbox.setSpacing(30);
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
    
    static class ListViewFavouriteCell extends JFXListCell<Subtitle> {
        
        final HBox hbox = new HBox();
        final Label lblTime = new Label();
        final Label lblSentence = new Label();
        final BorderPane borderPane = new BorderPane();
        private OnClickFavorite mOnClickFavorite;
        private JFXListView listFavoriteSubtitle;
        
        public ListViewFavouriteCell(OnClickFavorite onClickFavorite,
                  JFXListView listFavoriteSubtitle) {
            prefWidthProperty().bind(listFavoriteSubtitle.widthProperty().subtract(2));
            this.listFavoriteSubtitle = listFavoriteSubtitle;
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
                    hbox.setOnMouseClicked(event -> {
                        if (event.getButton() == MouseButton.SECONDARY) {
                            if (mOnClickFavorite != null) {
                                mOnClickFavorite.onClickFavorite(item);
                                listFavoriteSubtitle.getItems().remove(item);
                            }
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
    
}
