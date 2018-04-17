package com.infinity.stone.ui;

import com.infinity.stone.util.ResourceUtils;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
    private static Logger LOG = Logger.getLogger("Main");
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader
                      .load(ResourceUtils.getInstance().loadLayout("select_video.fxml"));
            primaryStage.setTitle("T h e G o o d P l a y e r");
            primaryStage.setScene(new Scene(root));
            primaryStage.sizeToScene();
            primaryStage.centerOnScreen();
            primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
                root.prefWidth(newValue.doubleValue());
                LOG.info(root.toString());
            });
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
