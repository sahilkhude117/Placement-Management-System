package com.example.pms.Models;

import com.example.pms.Views.ViewFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    private final DatabaseDriver databaseDriver;
    private Admin loggedAdmin;

    // Admin Data Section
    private Admin admin;
    private boolean adminLoginSuccessFlag;


    private Model() {
        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();
        // Admin Data Section
        this.adminLoginSuccessFlag = false;
        this.admin = new Admin("", "", "");
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public DatabaseDriver getDatabaseDriver() {
        return databaseDriver;
    }


    /*
     * Admin Methods
     */
    public boolean getAdminLoginSuccessFlag() {
        return this.adminLoginSuccessFlag;
    }

    public void setAdminLoginSuccessFlag(boolean flag) {
        this.adminLoginSuccessFlag = flag;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void evaluateAdminCred(String username, String password) {
        ResultSet resultSet = databaseDriver.getAdminData(username);
        try {
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("Password");
                if (storedPassword.equals(password)) {
                    // Successful login
                    this.admin.usernameProperty().set(resultSet.getString("Username"));
                    this.admin.emailProperty().set(resultSet.getString("Email"));
                    String[] dateParts = resultSet.getString("Date").split("-");
                    LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                    this.admin.dateProperty().set(date);
                    this.adminLoginSuccessFlag = true;
                } else {
                    this.adminLoginSuccessFlag = false;
                }
            } else {
                this.adminLoginSuccessFlag = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            this.adminLoginSuccessFlag = false;
        }
    }

    public void setLoggedAdmin(Admin admin) {
        this.loggedAdmin = admin;
    }

    // Method to get the logged-in admin
    public Admin getLoggedAdmin() {
        return loggedAdmin;
    }
}
