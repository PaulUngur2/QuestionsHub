package com.accesa.project.main;

import com.accesa.project.user.SessionManager;
import com.accesa.project.user.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;

public class StartController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    private SessionManager sessionManager;

    public void initialize() {
        sessionManager = SessionManager.getInstance();
    }

    @FXML
    private void loginButtonClicked() throws SQLException, NoSuchAlgorithmException, IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = sessionManager.login(username, password);
        if (user != null) {
            if (sessionManager.isAdmin()) {
                showLoggedInSceneAdmin();
            } else {
                showLoggedInScene();
            }
        } else {
            errorLabel.setText("Invalid username or password");
        }
    }

    public void showLoggedInScene() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainView.fxml")));
        Stage stage = (Stage) rootPane.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showLoggedInSceneAdmin() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainAdminView.fxml")));
        Stage stage = (Stage) rootPane.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}