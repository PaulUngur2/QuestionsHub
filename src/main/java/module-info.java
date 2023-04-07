module com.accesa.project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires jbcrypt;

    opens com.accesa.project to javafx.fxml;
    exports com.accesa.project;
    exports com.accesa.project.user;
    opens com.accesa.project.user to javafx.fxml;
    exports com.accesa.project.main;
    opens com.accesa.project.main to javafx.fxml;
    exports com.accesa.project.ranking;
    opens com.accesa.project.ranking to javafx.fxml;
}