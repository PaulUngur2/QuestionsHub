package com.accesa.project.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StartView.fxml"));
        String css = String.valueOf(Main.class.getResource("StartView.css"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 480);
        scene.getStylesheets().add(css);
        stage.setResizable(false);
        stage.setTitle("QuestionsHub");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}