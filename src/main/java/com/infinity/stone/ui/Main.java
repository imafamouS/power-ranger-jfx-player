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
    
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader
                      .load(ResourceUtils.getInstance().loadLayout("sample.fxml"));
            primaryStage.setTitle("Hello World");
            primaryStage.setScene(new Scene(root, 300, 275));
            primaryStage.show();
        } catch (Exception ex) {
            LOG.log(Level.INFO, ex.getMessage());
        }
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
