package com.infinity.stone.view;

import com.infinity.stone.model.RecentVideoModel;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Created by infamouSs on 4/12/18.
 */

public class RecentItemFactory implements
                               Callback<ListView<RecentVideoModel>, ListCell<RecentVideoModel>> {
    
    
    public ListCell<RecentVideoModel> call(ListView<RecentVideoModel> param) {
        return new RecentItemView();
    }
}