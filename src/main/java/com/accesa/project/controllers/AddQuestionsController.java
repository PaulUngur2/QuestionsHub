package com.accesa.project.controllers;

import com.accesa.project.questions.QuestionsManager;
import com.accesa.project.user.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class AddQuestionsController {
    @FXML
    private Label tokensLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private TextArea questionTextArea;
    @FXML
    private ComboBox answerComboBox;
    @FXML
    private TextField rewardTextField;
    @FXML
    private Label successLabel;
    @FXML
    private AnchorPane rootPane;


    private SessionManager sessionManager;
    private QuestionsManager questionsManager;

    public void initialize() throws SQLException {
        sessionManager = SessionManager.getInstance();
        questionsManager = new QuestionsManager();
        setTokens(sessionManager.getCurrentUserTokens());
        textFormatting();
    }

    @FXML
    private void setTokens(int tokens) {
        tokensLabel.setText("Tokens: " + tokens);
    }

    @FXML
    private void handleAddQuestion() throws SQLException {
        errorLabel.setText("");
        successLabel.setText("");
        String question = questionTextArea.getText();
        String answer = (String) answerComboBox.getValue();
        String rewardText = rewardTextField.getText();

        int reward;
        try {
            reward = Integer.parseInt(rewardText);
        } catch (NumberFormatException e) {
            errorLabel.setText("Reward must be an integer.");
            return;
        }

        int tokens = sessionManager.getCurrentUserTokens();

        if (reward > tokens) {
            errorLabel.setText("Not enough tokens.");
            return;
        }

        if (question.isEmpty()) {
            errorLabel.setText("Question cannot be empty.");
            return;
        }

        if (answer == null) {
            errorLabel.setText("Answer cannot be empty.");
            return;
        }

        sessionManager.addTokens(-reward);
        setTokens(sessionManager.getCurrentUserTokens());
        questionsManager.insertQuestion(question, Boolean.valueOf(answer), reward, sessionManager.getCurrentUserId());
        successLabel.setText("Question added successfully.");
    }

    private void textFormatting() {
        TextFormatter<String> formatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().length() > 1000) {
                return null;
            }
            return change;
        });
        questionTextArea.setTextFormatter(formatter);
        rewardTextField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
    }
    @FXML
    private void backButton() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainView.fxml")));
        Stage stage = (Stage) rootPane.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
