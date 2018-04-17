package com.infinity.stone.custom;

import com.infinity.stone.util.ResourceUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MyImageView extends ImageView {
    
    private static Logger LOG = Logger.getLogger("MyImageView");
    
    Map<String, Image> cached = new HashMap<>();
    
    public MyImageView(String url) {
        super(ResourceUtils.getInstance().loadIcon(url).toString());
        this.prefHeight(24.0);
        this.prefWidth(24.0);
        this.setPreserveRatio(true);
        this.setSmooth(true);
        this.setOpacity(0.8);
        this.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                MyImageView.this.setOpacity(1.0);
            } else {
                MyImageView.this.setOpacity(0.8);
            }
        });
        this.setPickOnBounds(true);
        this.setStyle("-fx-background-color:transparent");
    }
    
    public void setImage(String name) {
        LOG.info(ResourceUtils.getInstance().loadIcon(name).toString());
        if (!cached.containsKey(name)) {
            cached.put(name, new Image(ResourceUtils.getInstance().loadIcon(name).toString()));
        }
        super.setImage(cached.get(name));
    }
}