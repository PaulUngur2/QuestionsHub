package com.accesa.project.controllers;

import com.accesa.project.questions.QuestionsManager;
import com.accesa.project.user.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class MainController {
    @FXML
    private VBox questionBox;
    @FXML
    private BorderPane rootPane;
    @FXML
    private Label tokensLabel;

    private SessionManager sessionManager;
    private QuestionsManager questionsManager;


    public void initialize() throws SQLException {
        sessionManager = SessionManager.getInstance();
        questionsManager = new QuestionsManager();
        setTokens(sessionManager.getCurrentUserTokens());
        showQuestions();
    }

    @FXML
    private void setTokens(int tokens) {
        tokensLabel.setText("Tokens: " + tokens);
    }

   @FXML
   private void showQuestions() throws SQLException {
       List<String[]> questions = questionsManager.getQuestionAnswersList();
       questionBox.getChildren().clear();
       for (String[] question : questions) {
           Label questionLabel = new Label(question[1]);
           questionLabel.getStyleClass().add("question-label");
           questionLabel.setStyle("-fx-text-fill: WHITE; -fx-font-family: 'Helvetica'; -fx-padding: 5;");
           questionLabel.setOnMouseEntered(e -> questionLabel.setStyle("-fx-background-color: #1F1B24; -fx-text-fill: WHITE; -fx-font-family: 'Helvetica'; -fx-padding: 5;"));
           questionLabel.setOnMouseExited(e -> questionLabel.setStyle("-fx-text-fill: WHITE; -fx-font-family: 'Helvetica'; -fx-padding: 5;"));
           questionLabel.setOnMouseClicked(event -> showQuestionPopup(question));
           questionBox.getChildren().add(questionLabel);
       }
   }
   @FXML
    private void showQuestionPopup(String[] question) {
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.initModality(Modality.APPLICATION_MODAL);

        dialog.getDialogPane().setStyle("-fx-background-color: #121212;");

        VBox questionBox = createQuestionBox(question);

        dialog.getDialogPane().setContent(questionBox);
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/accesa/project/icon/qHub.png")));
        stage.getIcons().add(icon);
        ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(closeButton);

        dialog.showAndWait();

        Button closeButtonNode = (Button) dialog.getDialogPane().lookupButton(closeButton);
        closeButtonNode.setStyle("-fx-background-color: #1F1B24; -fx-text-fill: white; -fx-font-family: 'Helvetica';");
        closeButtonNode.setOnAction(e -> dialog.close());
    }

    @FXML
    private VBox createQuestionBox(String[] question) {

        VBox questionBox = new VBox();
        questionBox.getStyleClass().add("question-box");
        questionBox.setStyle("-fx-background-color: #121212;");

        Label questionLabel = new Label(question[1]);
        questionLabel.getStyleClass().add("question-text");
        questionLabel.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica'; -fx-padding: 5;");

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);
        Button trueButton = new Button("True");
        trueButton.setStyle("-fx-background-color: #1F1B24; -fx-text-fill: white; -fx-font-family: 'Helvetica';");
        trueButton.setOnAction(event -> {
            answerCheck(question[3], Integer.parseInt(question[2]), "true", Integer.parseInt(question[0]));
            questionBox.getScene().getWindow().hide();
        });
        Button falseButton = new Button("False");
        falseButton.setStyle("-fx-background-color: #1F1B24; -fx-text-fill: white; -fx-font-family: 'Helvetica';");
        falseButton.setOnAction(event -> {
            answerCheck(question[3], Integer.parseInt(question[2]), "false", Integer.parseInt(question[0]));
            questionBox.getScene().getWindow().hide();
        });
        buttonBox.getChildren().addAll(trueButton, falseButton);

        HBox topBox = new HBox();
        topBox.getChildren().add(questionLabel);
        topBox.setAlignment(Pos.CENTER_RIGHT);
        topBox.setSpacing(10);
        topBox.setStyle("-fx-background-color: #121212;");

        questionBox.getChildren().addAll(topBox, buttonBox);
        return questionBox;
    }

    private void answerCheck(String answer, int reward, String userAnswer, int questionId) {
        if (answer.equalsIgnoreCase(userAnswer)) {
            try {
                sessionManager.addTokens(reward);
                setTokens(sessionManager.getCurrentUserTokens());
                questionsManager.interactionWithQuestion(sessionManager.getCurrentUserId(), questionId, true);
                showQuestions();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                questionsManager.interactionWithQuestion(sessionManager.getCurrentUserId(), questionId, false);
                showQuestions();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void logoutButtonClicked() throws IOException {
        sessionManager.logout();
        changeScene("StartView.fxml");
    }

    @FXML
    private void rankingButtonClicked() throws IOException {
        changeScene("RankingView.fxml");
    }

    @FXML
    private void addButtonClicked() throws IOException {
        changeScene("AddQuestionsView.fxml");
    }

    private void changeScene(String fxml) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
        Stage stage = (Stage) rootPane.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
