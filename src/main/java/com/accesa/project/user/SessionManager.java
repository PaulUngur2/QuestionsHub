package com.accesa.project.user;

import org.mindrot.jbcrypt.BCrypt;

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

    public User login(String username, String password) throws SQLException {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByUsername(username);
        try {
            if (user != null && BCrypt.checkpw(password, user.getPassword())) {
                currentUser = user;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error checking password: " + e.getMessage());
        }
        return currentUser;
    }

    public boolean createUser(String username, String password, Boolean privileges) throws SQLException {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByUsername(username);
        if (user == null && currentUser.isAdmin()) {
            userDAO.insertUser(username, password, privileges);
            return true;
        }
        return false;
    }

    public void addTokens(int tokens) throws SQLException {
        UserDAO userDAO = new UserDAO();
        currentUser.addTokens(tokens);
        userDAO.updateUserTokens(currentUser.getId(), currentUser.getTokens());
    }

    public int getCurrentUserTokens(){
        return currentUser.getTokens();
    }

    public boolean isAdmin(){
        return currentUser.isAdmin();
    }

    public void logout() {
        currentUser = null;
    }

    public int getCurrentUserId(){
        return currentUser.getId();
    }

    public String getCurrentUserName(){
        return currentUser.getName();
    }

}
