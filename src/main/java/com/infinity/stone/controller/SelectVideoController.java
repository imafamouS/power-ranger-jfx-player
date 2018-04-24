package com.infinity.stone.controller;

import com.infinity.stone.custom.MyFileChooserDialog;
import com.infinity.stone.custom.MyRecentVideoView;
import com.infinity.stone.custom.MyRecentVideoView.OnClickRecentItem;
import com.infinity.stone.model.RecentVideoModel;
import com.infinity.stone.tracking.Action;
import com.infinity.stone.tracking.TrackingManager;
import com.infinity.stone.util.ResourceUtils;
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
    @FXML
    private Pane mSelectVideoPanel;
    
    @FXML
    private JFXListView<RecentVideoModel> mListView;
    
    @FXML
    private ImageView imageView;
    
    private final OnClickRecentItem mOnClickRecentItem = item -> LOG.info(item.toString());
    
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
            
            LOG.info(filePath);
        }
        event.setDropCompleted(success);
        event.consume();
    };
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MyFileChooserDialog myFileChooserDialog = buildFileChooser();
        
        mSelectVideoPanel.setOnMouseClicked(event -> {
            TrackingManager.getInstance()
                      .track(Action.SELECT_VIDEO, "Select video start at" + new Date());
            File selectedFile = myFileChooserDialog.show();
            
            TrackingManager.getInstance()
                      .track(Action.SELECT_VIDEO,
                                "Video selected path " + selectedFile.getAbsolutePath());
            showMainScreen(event);
            TrackingManager.getInstance()
                      .track(Action.SELECT_VIDEO, "Select video end at" + new Date());
        });
        
        mSelectVideoPanel.setOnDragOver(mOnDragOver);
        mSelectVideoPanel.setOnDragDropped(mOnDragDropped);
        
        List<RecentVideoModel> recentVideoModelList = getListRecentVideo();
        mListView.setItems(FXCollections.observableArrayList(recentVideoModelList));
        mListView.setCellFactory(
                  param -> new MyRecentVideoView.RecentVideoFactory(mOnClickRecentItem));
    }
    
    
    private List<RecentVideoModel> getListRecentVideo() {
        //TODO: GET FROM SqlITE
        List<RecentVideoModel> list = new ArrayList<>();
        
        list.add(new RecentVideoModel("1.JPG", "Video 1"));
        list.add(new RecentVideoModel("1.JPG", "Video 2"));
        list.add(new RecentVideoModel("1.JPG", "Video 3"));
        list.add(new RecentVideoModel("1.JPG", "Video 4"));
        list.add(new RecentVideoModel("1.JPG", "Video 5"));
        list.add(new RecentVideoModel("1.JPG", "Video 6"));
        list.add(new RecentVideoModel("1.JPG", "Video 7"));
        list.add(new RecentVideoModel("1.JPG", "Video 8"));
        list.add(new RecentVideoModel("1.JPG", "Video 9"));
        list.add(new RecentVideoModel("1.JPG", "Video 10"));
        
        return list;
    }
    
    private MyFileChooserDialog buildFileChooser() {
        
        return new MyFileChooserDialog()
                  .setTitle("Select Video");
    }
    
    private void showMainScreen(Event event) {
        Parent mainLayout;
        try {
            mainLayout = FXMLLoader
                      .load(ResourceUtils.getInstance().loadLayout("main_layout.fxml"));
            Scene mainScreen = new Scene(mainLayout);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.hide();
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
