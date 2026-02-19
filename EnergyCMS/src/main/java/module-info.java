module hr.javafx.energycms {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.slf4j;
    requires java.logging;
    requires java.sql;
    requires com.h2database;
    requires jakarta.json.bind;
    requires jakarta.xml.bind;


    opens hr.javafx.energycms to javafx.fxml;
    exports hr.javafx.energycms.app;
    opens hr.javafx.energycms.app to javafx.fxml;
    exports hr.javafx.energycms.controllers;
    opens hr.javafx.energycms.controllers to javafx.fxml;
    exports hr.javafx.energycms.entities;
    opens hr.javafx.energycms.entities to javafx.fxml;

    exports hr.javafx.energycms.entities.enums;
    opens hr.javafx.energycms.entities.enums to javafx.fxml;

    exports hr.javafx.energycms.repository;
    opens hr.javafx.energycms.repository to javafx.fxml;

    exports hr.javafx.energycms.generics;
    opens hr.javafx.energycms.generics to javafx.fxml;

    opens hr.javafx.energycms.controllers.edit to javafx.fxml;
    opens hr.javafx.energycms.controllers.login to javafx.fxml;
}