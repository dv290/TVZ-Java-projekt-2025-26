package hr.javafx.energycms.controllers;

import hr.javafx.energycms.app.HelloApplication;
import hr.javafx.energycms.utils.DialogUtils;
import hr.javafx.energycms.utils.FxUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

import static hr.javafx.energycms.app.MainConsoleLauncher.logger;

public class MenuController {

    @FXML
    private void showMeasurementsList() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/hr/javafx/energycms/measurements-list.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 800);
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();

        }
        catch (IOException | IllegalStateException e) {
            logger.error("Greška prilikom učitavanja measurements-list.fxml", e);
            DialogUtils.showError("Error!",
                    "Greška prilikom otvaranja Measurements ekrana.",
                    "Došlo je do pogreške prilikom otvaranja datoteke measurements-list.fxml");
        }
    }

    @FXML
    private void showAddNewMeasurement() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/hr/javafx/energycms/add-new-measurement.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 800);
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();

        }
        catch (IOException | IllegalStateException e) {
            logger.error("Greška prilikom učitavanja add-new-measurements.fxml", e);
            DialogUtils.showError("Error!",
                    "Greška prilikom otvaranja Add Measurement ekrana.",
                    "Došlo je do pogreške prilikom otvaranja datoteke add-new-measurements.fxml");
        }
    }

    @FXML
    private void showDevicesList() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/hr/javafx/energycms/devices-list.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 800);
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
        }
        catch (IOException | IllegalStateException e) {
            logger.error("Greška prilikom učitavanja devices-list.fxml", e);
            DialogUtils.showError("Error!",
                    "Greška prilikom otvaranja Devices ekrana.",
                    "Došlo je do pogreške prilikom otvaranja datoteke devices-list.fxml");
        }
    }

    @FXML
    private void showAddNewDevice() {
        FxUtils.showAddDevicesScreen();
    }


    @FXML
    private void showUsersList() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/hr/javafx/energycms/users-list.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 800);
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
        }
        catch (IOException | IllegalStateException e) {
            logger.error("Greška prilikom učitavanja users-list.fxml", e);
            DialogUtils.showError("Error!",
                    "Greška prilikom otvaranja Users ekrana.",
                    "Došlo je do pogreške prilikom otvaranja datoteke users-list.fxml");
        }
    }

    @FXML
    private void showAddNewUser() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/hr/javafx/energycms/add-new-users.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 800);
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
        }
        catch (IOException | IllegalStateException e) {
            logger.error("Greška prilikom učitavanja add-new-users.fxml", e);
            DialogUtils.showError("Error!",
                    "Greška prilikom otvaranja Add Users ekrana.",
                    "Došlo je do pogreške prilikom otvaranja datoteke add-new-users.fxml");
        }
    }

    @FXML
    private void showStartScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/hr/javafx/energycms/hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 800);
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
        }
        catch (IOException | IllegalStateException e) {
            logger.error("Greška prilikom učitavanja početnog ekrana, hello-view.fxml", e);
            DialogUtils.showError("Error!",
                    "Greška prilikom otvaranja početnog ekrana.",
                    "Došlo je do pogreške prilikom otvaranja datoteke hello-view.fxml");
        }
    }

    @FXML
    private void showEditUsers() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/hr/javafx/energycms/edit-user.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 800);
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
        }
        catch (IOException | IllegalStateException e) {
            logger.error("Greška prilikom učitavanja edit-user.fxml", e);
            DialogUtils.showError("Error!",
                    "Greška prilikom otvaranja ekrana za uređivanje korisnika.",
                    "Došlo je do pogreške prilikom otvaranja datoteke edit-users.fxml");
        }
    }

    @FXML
    private void showEditSmartDevice() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/hr/javafx/energycms/edit-smart-device.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 800);
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
        }
        catch (IOException | IllegalStateException e) {
            logger.error("Greška prilikom učitavanja edit-smart-device.fxml", e);
            DialogUtils.showError("Error!",
                    "Greška prilikom otvaranja ekrana za uređivanje smart uređaja.",
                    "Došlo je do pogreške prilikom otvaranja datoteke edit-smart-device.fxml");
        }
    }

    @FXML
    private void showEditStandardDevice() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/hr/javafx/energycms/edit-standard-device.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 800);
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
        }
        catch (IOException | IllegalStateException e) {
            logger.error("Greška prilikom učitavanja ekrana edit-standard-device.fxml", e);
            DialogUtils.showError("Error!",
                    "Greška prilikom otvaranja ekrana za uređivanje standardnih uređaja.",
                    "Došlo je do pogreške prilikom otvaranja datoteke edit-standarddevice.fxml");
        }
    }
}
