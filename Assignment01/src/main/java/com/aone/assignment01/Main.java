package com.aone.assignment01;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        primaryStage.setTitle("Restaurants Ratings in Toronto");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        showScene("graphic-view.fxml");
    }

    public void showScene(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load());
        Controller controller = fxmlLoader.getController();
        controller.initData(this, fxmlFile);  // Pass main application reference and FXML file name

        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}