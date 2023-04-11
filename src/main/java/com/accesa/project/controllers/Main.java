package com.accesa.project.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StartView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 480);
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/accesa/project/icon/qHub.png")));
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setTitle("QuestionsHub");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}