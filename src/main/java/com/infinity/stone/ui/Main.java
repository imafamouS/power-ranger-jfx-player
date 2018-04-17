package com.infinity.stone.ui;

import com.infinity.stone.util.ResourceUtils;
import java.util.logging.Level;
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
                      .load(ResourceUtils.getInstance().loadLayout("sample.fxml"));
            primaryStage.setTitle("Hello World");
            primaryStage.setScene(new Scene(root));
            primaryStage.sizeToScene();
            primaryStage.centerOnScreen();
            primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
                root.prefWidth(newValue.doubleValue());
                LOG.info(root.toString());
            });
            primaryStage.show();
        } catch (Exception ex) {
            LOG.log(Level.INFO, ex.getMessage());
        }
    }
}
