package com.example.pms.Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Admin {
    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty email;
    private final ObjectProperty<LocalDate> date;

    public Admin(String username, String password, String email) {
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.email = new SimpleStringProperty(email);
        this.date = new SimpleObjectProperty<LocalDate>(LocalDate.now());

    }

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringProperty emailProperty() {
        return email;
    }
    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }
}
