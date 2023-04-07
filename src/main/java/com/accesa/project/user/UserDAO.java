package com.accesa.project.user;

import com.accesa.project.DBConnections;

import java.sql.*;
public class UserDAO {
    private final Connection connection;

    public UserDAO() throws SQLException {
        DBConnections DBConnection = new DBConnections();
        connection = DBConnections.databaseConnection();
    }

    public User getUserByUsername(String username) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM accesa_accounts WHERE username_accesa = ?");
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("id_accesa");
            int tokens = resultSet.getInt("tokens_accesa");
            boolean isAdmin = resultSet.getBoolean("privileges_accesa");
            String password = resultSet.getString("password_accesa");
            return new User(username, password, id, tokens, isAdmin);
        } else {
            return null;
        }
    }


}
