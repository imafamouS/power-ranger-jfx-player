package com.infinity.stone.custom;

import com.infinity.stone.model.RecentVideoModel;
import com.infinity.stone.util.ResourceUtils;
import com.infinity.stone.util.image.ImageUtilities;
import com.jfoenix.controls.JFXListCell;
import java.io.IOException;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

/**
 * Created by infamouSs on 4/17/18.
 */

public class MyRecentVideoView extends VBox {
    
    private static final Logger LOG = Logger.getLogger("MyRecentVideoView");
    
    //fitHeight="150" fitWidth="210"
    
    private static final int HEIGHT = 150;
    private static final int WIDTH = 210;
    
    @FXML
    private ImageView mImageView;
    
    @FXML
    private Label mTextView;
    
    public MyRecentVideoView() {
        try {
            FXMLLoader loader = new FXMLLoader(
                      ResourceUtils.getInstance().loadLayout("item_recent.fxml"));
            loader.setController(this);
            VBox vBox = loader.load();
            this.getChildren().add(vBox);
        } catch (IOException exc) {
            LOG.info(exc.getMessage());
        }
    }
    
    
    public void setImage(String url) {
        Image image = new Image(
                  ResourceUtils.getInstance().loadRaw(url).toString());
        
        Image imageResize = ImageUtilities.fastResize(image, WIDTH, HEIGHT);
        mImageView.setImage(imageResize);
    }
    
    public void setText(String text) {
        mTextView.setText(text);
    }
    
    public ImageView getImageView() {
        return mImageView;
    }
    
    public void setImageView(ImageView imageView) {
        mImageView = imageView;
    }
    
    public Label getTextView() {
        return mTextView;
    }
    
    public void setTextView(Label textView) {
        mTextView = textView;
    }
    
    public interface OnClickRecentItem {
        
        void onClickRecentItem(Event event, RecentVideoModel item);
        
        void onRemoveRecentItem(Event event, RecentVideoModel item);
    }
    
    public static class RecentVideoFactory extends JFXListCell<RecentVideoModel> {
        
        final MyRecentVideoView view = new MyRecentVideoView();
        
        final OnClickRecentItem mOnClickRecentItem;
        
        public RecentVideoFactory(OnClickRecentItem listener) {
            this.mOnClickRecentItem = listener;
        }
        
        @Override
        protected void updateItem(final RecentVideoModel item, boolean empty) {
            super.updateItem(item, empty);
            try {
                if (item != null) {
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        view.setOnMouseClicked(
                                  event -> {
                                      if (mOnClickRecentItem == null) {
                                          return;
                                      }
                                      if (event.getButton() == MouseButton.SECONDARY) {
                                          mOnClickRecentItem.onRemoveRecentItem(event, item);
                                      } else if (event.getButton() == MouseButton.PRIMARY) {
                                          mOnClickRecentItem.onClickRecentItem(event, item);
                                      }
                                  });
                        view.setImage(item.getImageThumbnail());
                        view.setText(item.getVideoName());
                        setText(null);
                        setGraphic(view);
                    }
                } else {
                    setGraphic(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
