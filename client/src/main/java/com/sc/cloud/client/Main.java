package com.sc.cloud.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    //private Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        if (Controller.btnShowAutorizeModal()) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample.fxml"));
            Parent root = loader.load();

//        this.primaryStage = primaryStage;
//        Scene scene = new Scene(root, primaryStage.getWidth(),primaryStage.getHeight());

            primaryStage.setTitle("Shama Cloud");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
            Controller controller = loader.getController();

            primaryStage.setOnCloseRequest(controller.getCloseEventHandler());
        }
    }
    public static void main(String[] args) {
        //new DirList();
        launch(args);
    }
}

