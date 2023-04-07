package com.accesa.project.main;

import com.accesa.project.user.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainViewController {
    @FXML
    private BorderPane rootPane;
    @FXML
    private Label tokensLabel;
    @FXML
    private Label user;

    private SessionManager sessionManager;

    public void initialize() {
        sessionManager = SessionManager.getInstance();
        setTokens(sessionManager.getCurrentUserTokens());
    }

    public void setTokens(int tokens) {
        tokensLabel.setText("Tokens: " + tokens);
    }

    @FXML
    public void logoutButtonClicked() throws IOException {
        sessionManager.logout();
        showStartScene();
    }

    private void showStartScene() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StartView.fxml")));
        Stage stage = (Stage) rootPane.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void rankingButtonClicked(ActionEvent actionEvent) {

    }

}
