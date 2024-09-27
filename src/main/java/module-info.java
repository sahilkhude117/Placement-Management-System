module com.example.pms {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires jbcrypt;
    requires java.mail;

    // Open specific packages to JavaFX for reflection
    opens com.example.pms to javafx.fxml;
    opens com.example.pms.Controllers to javafx.fxml;
    opens com.example.pms.Controllers.Admin to javafx.fxml;

    // Export packages to make them available to other modules
    exports com.example.pms;
    exports com.example.pms.Controllers;
    exports com.example.pms.Controllers.Admin;
    exports com.example.pms.Models;
    exports com.example.pms.Views;
}
