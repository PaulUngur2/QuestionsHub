package com.accesa.project.controllers;

import com.accesa.project.ranking.RanksManager;
import com.accesa.project.user.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class RankingController {
    @FXML
    private BorderPane rootPane;
    @FXML
    private VBox rankBox;
    @FXML
    private Label rankLabel;

    private SessionManager sessionManager;
    private RanksManager ranksManager;


    public void initialize() throws SQLException {
        sessionManager = SessionManager.getInstance();
        ranksManager = new RanksManager();
        showRanking();
    }

    @FXML
    private void setRank(int rank) {
        rankLabel.setText("Your Rank: " + rank);
    }

    @FXML
    private void showRanking() throws SQLException {
        List<String[]> ranks = ranksManager.getRanksList();
        rankBox.getChildren().clear();
        int i = 0;
        for (String[] rank : ranks) {
            i += 1 ;
            if (sessionManager.getCurrentUserName().equals(rank[0]))
                setRank(i);
            Label rankLabel = new Label(i + ". " + rank[0] + " " + rank[1]);
            rankLabel.getStyleClass().add("rank-label");
            rankLabel.setStyle("-fx-text-fill: WHITE; -fx-font-family: 'Helvetica'; -fx-padding: 5;");
            rankLabel.setOnMouseEntered(e -> rankLabel.setStyle("-fx-background-color: #1F1B24; -fx-text-fill: WHITE; -fx-font-family: 'Helvetica'; -fx-padding: 5;"));
            rankLabel.setOnMouseExited(e -> rankLabel.setStyle("-fx-text-fill: WHITE; -fx-font-family: 'Helvetica'; -fx-padding: 5;"));
            rankBox.getChildren().add(rankLabel);
        }
    }

    @FXML
    private void backButtonClicked() throws IOException {
        changeScene("MainView.fxml");
    }

    @FXML
    private void logoutButtonClicked() throws IOException {
        sessionManager.logout();
        changeScene("StartView.fxml");
    }

    private void changeScene(String fxml) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
        Stage stage = (Stage) rootPane.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
