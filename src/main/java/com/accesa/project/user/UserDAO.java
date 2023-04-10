package com.accesa.project.user;

import com.accesa.project.database.DBConnections;

import java.sql.*;
public class UserDAO {
    private final Connection connection;

    public UserDAO() throws SQLException {
        DBConnections DBConnection = new DBConnections();
        connection = DBConnections.databaseConnection();
    }

    public User getUserByUsername(String username) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM qHub_accounts WHERE username_qHub = ?");
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("id_qHub");
            int tokens = resultSet.getInt("tokens_qHub");
            boolean isAdmin = resultSet.getBoolean("privileges_qHub");
            String password = resultSet.getString("password_qHub");
            return new User(username, password, id, tokens, isAdmin);
        } else {
            return null;
        }
    }

    public boolean insertUser(String username, String password, Boolean privileges) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO qHub_accounts (username_qHub, password_qHub, privileges_qHub) VALUES (?, ?, ?)");
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setBoolean(3, privileges);
        int rowsInserted = statement.executeUpdate();
        addUserRanking(username);
        return rowsInserted > 0;
    }

    private void addUserRanking(String username) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO qHub_rankings (ranking_name, ranking_score) VALUES (?, ?)");
        statement.setString(1, username);
        statement.setInt(2, 0);
        statement.executeUpdate();
    }

    public void updateUserTokens(int id, int tokens) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE qHub_accounts SET tokens_qHub = ? WHERE id_qHub = ?");
        statement.setInt(1, tokens);
        statement.setInt(2, id);
        statement.executeUpdate();
    }
}
