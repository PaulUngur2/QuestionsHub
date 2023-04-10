package com.accesa.project.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnections {

    public static Connection databaseConnection() throws SQLException {
        String url = "jdbc:mariadb://localhost:3306/accesa";
        String username = "admin_user";
        String password = "secret_password";
        return DriverManager.getConnection(url, username, password);
    }

}
