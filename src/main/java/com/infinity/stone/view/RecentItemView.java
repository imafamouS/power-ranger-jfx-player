package com.infinity.stone.view;

import com.infinity.stone.model.RecentVideoModel;
import com.infinity.stone.util.ResourceUtils;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

/**
 * Created by infamouSs on 4/12/18.
 */

public class RecentItemView extends ListCell<RecentVideoModel> {
    
    //
    @FXML
    private ImageView mImageView;
    
    @FXML
    private Label mTextView;
    
    public RecentItemView() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                  ResourceUtils.getInstance().loadLayout("item_video.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }
    
    @Override
    protected void updateItem(RecentVideoModel item, boolean empty) {
        super.updateItem(item, empty);
        
        if (empty) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {
            // titleLabel.setText(item.getTitle());
            mTextView.setText(item.getText());
            
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}
