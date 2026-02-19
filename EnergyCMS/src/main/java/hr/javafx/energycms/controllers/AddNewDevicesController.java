package hr.javafx.energycms.controllers;

import hr.javafx.energycms.app.HelloApplication;
import hr.javafx.energycms.utils.DialogUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

import static hr.javafx.energycms.app.MainConsoleLauncher.logger;

public class AddNewDevicesController {

    @FXML
    private void showAddStandardDevices() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/hr/javafx/energycms/add-new-standard-device.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 800);
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();

        }
        catch (IOException | IllegalStateException e) {
            logger.error("Greška prilikom učitavanja measurements-list.fxml", e);
            DialogUtils.showError("Error!",
                    "Greška prilikom otvaranja Add Standard Device ekrana.",
                    "Došlo je do pogreške prilikom otvaranja datoteke add-new-standard-device.fxml");
        }
    }


    @FXML
    private void showAddSmartDevices() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/hr/javafx/energycms/add-new-smart-device.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 800);
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();

        }
        catch (IOException | IllegalStateException e) {
            logger.error("Greška prilikom učitavanja measurements-list.fxml", e);
            DialogUtils.showError("Error!",
                    "Greška prilikom otvaranja Add Smart Device ekrana.",
                    "Došlo je do pogreške prilikom otvaranja datoteke add-new-smart-device.fxml");
        }
    }
}
