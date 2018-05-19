package com.infinity.stone.controller;

import com.infinity.stone.custom.MyFileChooserDialog;
import com.infinity.stone.custom.MyRecentVideoView;
import com.infinity.stone.custom.MyRecentVideoView.OnClickRecentItem;
import com.infinity.stone.db.RepositoryManager;
import com.infinity.stone.db.RepositoryType;
import com.infinity.stone.db.favorite.FavoriteRepositoryImpl;
import com.infinity.stone.db.subtitle.Subtitle;
import com.infinity.stone.db.subtitle.SubtitleRepositoryImpl;
import com.infinity.stone.db.video.Video;
import com.infinity.stone.db.video.VideoRepositoryImpl;
import com.infinity.stone.model.RecentVideoModel;
import com.infinity.stone.tracking.Action;
import com.infinity.stone.tracking.TrackingManager;
import com.infinity.stone.util.Constant;
import com.infinity.stone.util.ResourceUtils;
import com.infinity.stone.util.SecurityUtils;
import com.infinity.stone.youtube.DownloadCaptionManager;
import com.jfoenix.controls.JFXListView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by infamouSs on 4/17/18.
 */

public class SelectVideoController implements Initializable {
    
    public static final String[] EXTENSIONS = new String[]{"*.mp4"};
    private static final Logger LOG = Logger.getLogger("SelectVideoController");
    
    private final EventHandler<DragEvent> mOnDragOver = event -> {
        Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY);
        } else {
            event.consume();
        }
    };
    private final EventHandler<DragEvent> mOnDragDropped = event -> {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            String filePath = db.getFiles().get(0).getAbsolutePath();
            File selectedFile = new File(filePath);
            if (selectedFile.exists()) {
                insertVideoAndOpenScreen(selectedFile, event);
            }
            
        }
        event.setDropCompleted(success);
        event.consume();
    };
    @FXML
    private Pane mSelectVideoPanel;
    @FXML
    private JFXListView<RecentVideoModel> mListView;
    @FXML
    private ImageView imageView;
    
    VideoRepositoryImpl mVideoRepository;
    
    private final OnClickRecentItem mOnClickRecentItem = new OnClickRecentItem() {
        @Override
        public void onClickRecentItem(Event event, RecentVideoModel item) {
            TrackingManager.getInstance()
                      .track(Action.SELECT_VIDEO,
                                "Select video " + item.getVideoName() + " at " + new Date());
            showMainScreen(item, event);
        }
        
        @Override
        public void onRemoveRecentItem(Event event, RecentVideoModel item) {
            long resultRemoveVideo = mVideoRepository._delete(new Video(item.getVideoId()));
            if (resultRemoveVideo > 0) {
                mListView.getItems().remove(item);
                TrackingManager.getInstance().track(Action.ERROR, "remove video successfully");
                SubtitleRepositoryImpl subtitleRepository = (SubtitleRepositoryImpl) RepositoryManager
                          .getInstance(RepositoryType.SUBTITLE);
                
                subtitleRepository._deleteByVideoId(item.getVideoId());
                
                FavoriteRepositoryImpl favoriteRepository = (FavoriteRepositoryImpl) RepositoryManager
                          .getInstance(RepositoryType.FAVORITE);
                
                favoriteRepository.deleteByVideoId(item.getVideoId());
            } else {
                TrackingManager.getInstance().track(Action.ERROR, "remove video failed");
            }
        }
    };
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MyFileChooserDialog myFileChooserDialog = buildFileChooser();
        
        mSelectVideoPanel.setOnMouseClicked(event -> {
            TrackingManager.getInstance()
                      .track(Action.SELECT_VIDEO, "Select video start at" + new Date());
            File selectedFile = myFileChooserDialog.show();
            
            if (selectedFile.exists()) {
                TrackingManager.getInstance()
                          .track(Action.SELECT_VIDEO,
                                    "Video selected path " + selectedFile.getAbsolutePath());
                TrackingManager.getInstance()
                          .track(Action.SELECT_VIDEO, "Select video end at" + new Date());
                
                insertVideoAndOpenScreen(selectedFile, event);
            }
        });
        
        mSelectVideoPanel.setOnDragOver(mOnDragOver);
        mSelectVideoPanel.setOnDragDropped(mOnDragDropped);
        mVideoRepository = (VideoRepositoryImpl) RepositoryManager
                  .getInstance(RepositoryType.VIDEO);
        
        List<Video> videos = mVideoRepository._findAll();
        if (videos != null && !videos.isEmpty()) {
            List<RecentVideoModel> recentVideoModelList = getListRecentVideo(videos);
            mListView.setItems(FXCollections.observableArrayList(recentVideoModelList));
            mListView.setCellFactory(
                      param -> new MyRecentVideoView.RecentVideoFactory(
                                mOnClickRecentItem));
        }
    }
    
    private void insertVideoAndOpenScreen(File selectedFile, Event event) {
        
        VideoRepositoryImpl videoRepo = (VideoRepositoryImpl) RepositoryManager
                  .getInstance(RepositoryType.VIDEO);
        Video video = new Video(SecurityUtils.getRandomUUID(),
                  selectedFile.getName(),
                  selectedFile.getPath(),
                  new Date());
        long resultCreateVideo = videoRepo._create(video);
        if (resultCreateVideo >= 0) {
            TrackingManager.getInstance()
                      .track(Action.SELECT_VIDEO, "Insert video to database success");
            
            showMainScreen(new RecentVideoModel(video), event);
        } else {
            TrackingManager.getInstance()
                      .track(Action.SELECT_VIDEO, "Insert video to database failure");
        }
    }
    
    
    private List<RecentVideoModel> getListRecentVideo(List<Video> videos) {
        //TODO: GET FROM SqlITE
        List<RecentVideoModel> list = new ArrayList<>();
        
        for (Video video : videos) {
            list.add(new RecentVideoModel("1.JPG", video.getId(), video.getYoutubeId(),
                      video.getUrl()));
        }
        
        return list;
    }
    
    private MyFileChooserDialog buildFileChooser() {
        
        return new MyFileChooserDialog()
                  .setTitle("Select Video");
    }
    
    private void showMainScreen(RecentVideoModel video, Event event) {
        Constant.CURRENT_VIDEO_PATH = video.getVideoPath();
        Constant.CURRENT_VIDEO_ID = video.getVideoId();
        
        String pathWithoutExtension = video.getVideoPath().split("[.]")[0];
        String pathSub = pathWithoutExtension + ".ttml";
        
        SubtitleRepositoryImpl repository = (SubtitleRepositoryImpl) RepositoryManager
                  .getInstance(RepositoryType.SUBTITLE);
        
        List<Subtitle> subtitleList = repository._findAllSubtitleByVideoId(video.getVideoId());
        
        if (subtitleList != null) {
            if (subtitleList.isEmpty()) {
                long resultCreateListSub = repository._create(createSub(pathSub));
                if (resultCreateListSub >= 0) {
                    openScreen(event);
                } else {
                    TrackingManager.getInstance().track(Action.ERROR,
                              " error at SelectVideoController.showMainScreen:188");
                }
                return;
            }
            openScreen(event);
        } else {
            long resultCreateListSub = repository._create(createSub(pathSub));
            if (resultCreateListSub >= 0) {
                openScreen(event);
            } else {
                TrackingManager.getInstance().track(Action.ERROR,
                          " error at SelectVideoController.showMainScreen:200");
            }
        }
    }
    
    private List<Subtitle> createSub(String path) {
        DownloadCaptionManager downloadCaptionManager = new DownloadCaptionManager();
        List<Subtitle> collection = downloadCaptionManager
                  .buildSubtitleListFromFileTTML(path);
        
        for (Subtitle subtitle : collection) {
            subtitle.setId(SecurityUtils.getRandomUUID());
            subtitle.setVideoId(Constant.CURRENT_VIDEO_ID);
        }
        
        return collection;
    }
    
    private void openScreen(Event event) {
        Parent mainLayout;
        try {
            mainLayout = FXMLLoader
                      .load(ResourceUtils.getInstance().loadLayout("main_layout.fxml"));
            
            Scene mainScreen = new Scene(mainLayout);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            //appStage.hide();
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
            e.printStackTrace();
        }
    }
}
