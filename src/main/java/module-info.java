module com.accesa.project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires jbcrypt;
    requires java.net.http;

    exports com.accesa.project.user;
    opens com.accesa.project.user to javafx.fxml;
    exports com.accesa.project.controllers;
    opens com.accesa.project.controllers to javafx.fxml;
    exports com.accesa.project.database;
    opens com.accesa.project.database to javafx.fxml;
}