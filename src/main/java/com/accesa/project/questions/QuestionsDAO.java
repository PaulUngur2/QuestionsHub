package com.accesa.project.questions;

import com.accesa.project.database.DBConnections;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.log10;

public class QuestionsDAO {
    private final Connection connection;

    public QuestionsDAO() throws SQLException {
        DBConnections DBConnection = new DBConnections();
        connection = DBConnections.databaseConnection();
    }

    public void insertQuestion(String question, Boolean answer, int reward, int idUser) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO qHub_questions (questions_qHub, answers_qHub, rewards_qHub) VALUES (?, ?, ?)");
        statement.setString(1, question);
        statement.setBoolean(2, answer);
        statement.setInt(3, reward);
        statement.executeUpdate();
        insertQuestionBadgePoints(idUser, reward);
    }

    private void insertQuestionBadgePoints (int idUser, int tokenSpent) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT badgesPoints_qHub, badges_qHub, tokens_qHub, username_qHub FROM qHub_accounts WHERE id_qHub = ?");
        statement.setInt(1, idUser);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String username = resultSet.getString("username_qHub");
            int badgesPoints = resultSet.getInt("badgesPoints_qHub");
            int badges = resultSet.getInt("badges_qHub");
            int tokens = resultSet.getInt("tokens_qHub");

            badgesPoints += (int) (log10(tokenSpent * 5) + 5);

            if (badgesPoints >= 10) {
                int badgesEarned = badgesPoints / 10;
                badges += badgesEarned;
                badgesPoints %= 10;
                updateScore(username, tokens + (badges * 500));
            }

            PreparedStatement statement2 = connection.prepareStatement("UPDATE qHub_accounts SET badgesPoints_qHub = ?, badges_qHub = ? WHERE id_qHub = ?");
            statement2.setInt(1, badgesPoints);
            statement2.setInt(2, badges);
            statement2.setInt(3, idUser);
            statement2.executeUpdate();
        }
    }

    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT questions_id, questions_qHub, answers_qHub, approval_qHub, rewards_qHub FROM qHub_questions");

            while (resultSet.next()) {
                int id = resultSet.getInt("questions_id");
                String question = resultSet.getString("questions_qHub");
                boolean answer = resultSet.getBoolean("answers_qHub");
                boolean approval = resultSet.getBoolean("approval_qHub");
                int rewards = resultSet.getInt("rewards_qHub");

                Question q = new Question(id, question, answer, approval, rewards);
                questions.add(q);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }

    public void updateQuestionApproval(int id, boolean approval) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE qHub_questions SET approval_qHub = ? WHERE questions_id = ?");
        statement.setBoolean(1, approval);
        statement.setInt(2, id);
        statement.executeUpdate();
    }

    public void deleteQuestion(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM qHub_questions WHERE questions_id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public void interactions(int idUser, int idQuestion, boolean status) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO qHub_interactions (id_user, id_question, status) VALUES (?, ?, ?)");
        statement.setInt(1, idUser);
        statement.setInt(2, idQuestion);
        statement.setBoolean(3, status);
        statement.executeUpdate();
        serverLogic(idUser, status);
    }

    private void serverLogic(int idUser, boolean status) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT badgesPoints_qHub, badges_qHub, tokens_qHub, username_qHub FROM qHub_accounts WHERE id_qHub = ?");
        statement.setInt(1, idUser);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int badgesPoints = resultSet.getInt("badgesPoints_qHub");
            int badges = resultSet.getInt("badges_qHub");
            int tokens = resultSet.getInt("tokens_qHub");
            String username = resultSet.getString("username_qHub");
            if (status) {
                badgesPoints += 1;
                updateScore(username, tokens);
            }

            if (badgesPoints >= 10) {
                int badgesEarned = badgesPoints / 10;
                badges += badgesEarned;
                badgesPoints %= 10;
                updateScore(username, tokens + (badges * 500));
            }

            PreparedStatement statement2 = connection.prepareStatement("UPDATE qHub_accounts SET badgesPoints_qHub = ?, badges_qHub = ? WHERE id_qHub = ?");
            statement2.setInt(1, badgesPoints);
            statement2.setInt(2, badges);
            statement2.setInt(3, idUser);
            statement2.executeUpdate();
        }
    }

    private void updateScore(String nameUser, int score) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE qHub_rankings SET ranking_score = ? WHERE ranking_name = ?");
        statement.setInt(1, score);
        statement.setString(2, nameUser);
        statement.executeUpdate();
    }

    public boolean onTheList(int idUser, int idQuestion) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM qHub_interactions WHERE id_user = ? AND id_question = ?");
        statement.setInt(1, idUser);
        statement.setInt(2, idQuestion);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }
}
