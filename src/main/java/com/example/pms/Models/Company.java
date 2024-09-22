package com.example.pms.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.ObjectProperty;
import java.time.LocalDate;

public class Company {

    private final StringProperty companyName;
    private final StringProperty companyId;
    private final StringProperty companyCoordinator;
    private final StringProperty companyPhone;
    private final StringProperty companyEmail;
    private final ObjectProperty<LocalDate> companyDate;
    private final StringProperty companyHighestPackage;
    private final StringProperty companyAddress;

    public Company(String companyName, String companyId, String companyCoordinator, String companyEmail, String companyPhone,
                   LocalDate companyDate, String companyHighestPackage, String companyAddress) {
        this.companyName = new SimpleStringProperty(this, "companyName", companyName);
        this.companyId = new SimpleStringProperty(this, "companyId", companyId);
        this.companyCoordinator = new SimpleStringProperty(this, "companyCoordinator", companyCoordinator);
        this.companyEmail = new SimpleStringProperty(this, "companyEmail", companyEmail);
        this.companyPhone = new SimpleStringProperty(this, "companyPhone", companyPhone);
        this.companyDate = new SimpleObjectProperty<>(this, "companyDate", companyDate);
        this.companyHighestPackage = new SimpleStringProperty(this, "companyHighestPackage", companyHighestPackage);
        this.companyAddress = new SimpleStringProperty(this, "companyAddress", companyAddress);
    }

    public StringProperty companyNameProperty() {
        return companyName;
    }

    public StringProperty companyIdProperty() {
        return companyId;
    }

    public StringProperty companyCoordinatorProperty() {
        return companyCoordinator;
    }

    public StringProperty companyEmailProperty() {
        return companyEmail;
    }

    public StringProperty companyPhoneProperty() {
        return companyPhone;
    }

    public ObjectProperty<LocalDate> companyDateProperty() {
        return companyDate;
    }

    public StringProperty companyHighestPackageProperty() {
        return companyHighestPackage;
    }

    public StringProperty companyAddressProperty() {
        return companyAddress;
    }
}
