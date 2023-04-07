package com.accesa.project.user;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class SessionManager {
    private static SessionManager instance;
    private User currentUser;

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public String getCurrentUserName(){
        return currentUser.getName();
    }

    public int getCurrentUserTokens(){
        return currentUser.getTokens();
    }

    public boolean isAdmin(){
        return currentUser.isAdmin();
    }

    public User login(String username, String password) throws SQLException, NoSuchAlgorithmException {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByUsername(username);
        if (user != null && password.equals(user.getPassword())) {
            //BCrypt.checkpw(password, user.getPassword())
            currentUser = user;
        }
        return user;
    }

    public void logout() {
        currentUser = null;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
