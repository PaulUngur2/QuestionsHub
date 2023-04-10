package com.accesa.project.controllers;

import com.accesa.project.user.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class AdminAddUserController {
    @FXML
    public AnchorPane rootPane;
    @FXML
    public Label errorLabel;
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public CheckBox checkBox;
    @FXML
    public Label successLabel;

    private SessionManager sessionManager;
    public void initialize() {
        sessionManager = SessionManager.getInstance();
    }

    @FXML
    public void createButtonClicked() throws SQLException {
        successLabel.setText("");
        errorLabel.setText("");
        String name = usernameField.getText();
        String password = passwordField.getText();
        Boolean privileges = checkBox.isSelected();

        String encryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        if(sessionManager.createUser(name, encryptedPassword, privileges)) {
            successLabel.setText("User created successfully!");
        } else {
            errorLabel.setText("User already exists!");
        }
    }

    @FXML
    public void backButtonClicked() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainAdminView.fxml")));
        Stage stage = (Stage) rootPane.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
