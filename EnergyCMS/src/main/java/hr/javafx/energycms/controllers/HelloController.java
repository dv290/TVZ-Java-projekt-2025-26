package hr.javafx.energycms.controllers;

import hr.javafx.energycms.entities.User;
import hr.javafx.energycms.repository.UserRepository;
import hr.javafx.energycms.utils.DatabaseUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Optional;

public class HelloController {
    @FXML
    private Label lastUserLabel;

    public void initialize() {
        Thread.ofVirtual().start(new Runnable() {
            @Override
            public void run() {
                Optional<User> lastUserOpt = UserRepository.findLastUser();
                String statusText = lastUserOpt
                        .map(u -> "Zadnji dodani korisnik: " + u.getFirstName() + " " + u.getLastName() + " (ID: "
                        +u.getId()+") ")
                        .orElse("U bazi trenutno nema korisnika.");

                Platform.runLater(() -> lastUserLabel.setText(statusText));
            }
        });


        Thread.ofVirtual().start(() -> {
            DatabaseUtils.backupTable("MEASUREMENTS");
        });
    }
}
