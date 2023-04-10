package com.accesa.project.controllers;

import com.accesa.project.questions.QuestionsManager;
import com.accesa.project.user.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class MainAdminController {
    @FXML
    public VBox questionBox;
    @FXML
    private BorderPane rootPane;

    private SessionManager sessionManager;
    private QuestionsManager questionsManager;

    public void initialize() throws SQLException {
        sessionManager = SessionManager.getInstance();
        questionsManager = new QuestionsManager();
        showQuestions();
    }

    @FXML
    public void showQuestions() {
        List<String[]> questions = questionsManager.getApprovalList();
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
        questionLabel.setStyle("-fx-text-fill: white; -fx-font-family: 'Helvetica';");

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);
        Button approveButton = new Button("Approve");
        approveButton.setStyle("-fx-background-color: #1F1B24; -fx-text-fill: white; -fx-font-family: 'Helvetica';");
        approveButton.setOnAction(event -> {
            try {
                questionsManager.approveQuestion(Integer.parseInt(question[0]));
                showQuestions();
                questionBox.getScene().getWindow().hide();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #1F1B24; -fx-text-fill: white; -fx-font-family: 'Helvetica';");
        deleteButton.setOnAction(event -> {
            try {
                questionsManager.deleteQuestion(Integer.parseInt(question[0]));
                showQuestions();
                questionBox.getScene().getWindow().hide();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        buttonBox.getChildren().addAll(approveButton, deleteButton);

        HBox topBox = new HBox();
        topBox.getChildren().add(questionLabel);
        topBox.setAlignment(Pos.CENTER_RIGHT);
        topBox.setSpacing(10);
        topBox.setStyle("-fx-background-color: #121212;");

        questionBox.getChildren().addAll(topBox, buttonBox);
        return questionBox;
    }

    @FXML
    public void logoutButtonClicked() throws IOException {
        sessionManager.logout();
        changeScene("StartView.fxml");
    }

    @FXML
    public void addingButtonClicked() throws IOException {
        changeScene("AdminAddUserView.fxml");
    }

    private void changeScene(String fxml) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
        Stage stage = (Stage) rootPane.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
