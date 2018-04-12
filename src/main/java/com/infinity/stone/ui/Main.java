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
                      .load(ResourceUtils.getInstance().loadLayout("select_video.fxml"));
            primaryStage.setTitle("Hello World");
            primaryStage.setScene(new Scene(root, 1280, 720));
            primaryStage.setResizable(false);
            primaryStage.show();
            
//            ObservableList<String> items = FXCollections.observableArrayList(
//                      "A", "B", "C", "D", "E"
//            );
//            items.addListener(new ListChangeListener<String>() {
//                public void onChanged(Change<? extends String> c) {
//                    System.out.println(c);
//                }
//            });
//
//            StackPane root = new StackPane();
//            ListView<String> list = new ListView<String>(items);
//            list.setEditable(true);
//            list.setCellFactory(new Callback<ListView<String>,
//                                          ListCell<String>>() {
//                                    public ListCell<String> call(ListView<String> list) {
//                                        return new TFListCell();
//                                    }
//                                }
//            );
//            root.getChildren().add(list);
//
//            Scene scene = new Scene(root, 200, 200);
//
//            primaryStage.setScene(scene);
//            primaryStage.show();
        } catch (Exception ex) {
            LOG.log(Level.INFO, ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
