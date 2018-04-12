package com.infinity.stone.controller;

import com.infinity.stone.model.RecentVideoModel;
import com.infinity.stone.view.RecentItemFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

/**
 * Created by infamouSs on 4/12/18.
 */

public class SelectVideoController implements Initializable {
    
    
    public ListView<RecentVideoModel> mListView;
    public Pane mSelectVideoView;
    
    public void initialize(URL location, ResourceBundle resources) {
        mListView.setCellFactory(new RecentItemFactory());
        List<RecentVideoModel> listRecentVideo = new ArrayList<RecentVideoModel>();
        
        RecentVideoModel item_1 =new RecentVideoModel("", "item 1");
        RecentVideoModel item_2 =new RecentVideoModel("", "item 1");
        RecentVideoModel item_3 =new RecentVideoModel("", "item 1");
        RecentVideoModel item_4 =new RecentVideoModel("", "item 1");
        RecentVideoModel item_5 =new RecentVideoModel("", "item 1");
    
        listRecentVideo.add(item_1);
        listRecentVideo.add(item_2);
        listRecentVideo.add(item_3);
        listRecentVideo.add(item_4);
        listRecentVideo.add(item_5);
        //
        //        List<String> listRecentVideo = new ArrayList<String>();
        //        listRecentVideo.add("Item 1");
        //        listRecentVideo.add("Item 1");
        //        listRecentVideo.add("Item 1");
        //        listRecentVideo.add("Item 1");
        //        listRecentVideo.add("Item 1");
        
        mListView.setItems(FXCollections.observableArrayList(listRecentVideo));
    }
}
