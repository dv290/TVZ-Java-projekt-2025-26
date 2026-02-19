package hr.javafx.energycms.controllers;

import hr.javafx.energycms.entities.SmartDevice;
import hr.javafx.energycms.entities.enums.DeviceType;
import hr.javafx.energycms.repository.DeviceRepository;
import hr.javafx.energycms.utils.DialogUtils;
import hr.javafx.energycms.utils.FxUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


import java.util.concurrent.ThreadLocalRandom;


public class AddSmartDeviceController {
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField powerRatingTextField;
    @FXML
    private TextField ipAddressTextField;
    @FXML
    private ComboBox<String> osComboBox;
    @FXML
    private ComboBox<DeviceType> deviceTypeComboBox;

    @FXML
    public void initialize() {
        osComboBox.setItems(FXCollections.observableArrayList(
                "Android",
                "iOS",
                "macOS",
                "Windows IoT",
                "Linux",
                "Tizen"));

        deviceTypeComboBox.setItems(FXCollections.observableArrayList(DeviceType.values()));
    }

    @FXML
    private void saveSmartDevice() {
        String name = nameTextField.getText();
        String powerRatingStr = powerRatingTextField.getText();
        String ip = ipAddressTextField.getText();
        String selectedOs = osComboBox.getValue();
        DeviceType selectedDeviceType = deviceTypeComboBox.getValue();

        if (name.isBlank() || powerRatingStr.isBlank() || ip.isBlank() || selectedOs == null || selectedDeviceType == null) {
            DialogUtils.showError("Greška pri unosu", "Neispravan unos", "Sva polja moraju biti popunjena!");
            return;
        }

        try {
            int powerRating = Integer.parseInt(powerRatingStr);

            if (powerRating < 0) {
                DialogUtils.showError("Greška", "Negativna vrijednost", "Power rating ne smije biti manji od 0!");
                return;
            }

            SmartDevice newDevice = new SmartDevice(
                    name,
                    selectedDeviceType,
                    powerRating,
                    ip,
                    selectedOs,
                    ThreadLocalRandom.current().nextInt(100),
                    true
            );

            DeviceRepository.saveSmartDevice(newDevice);

            DialogUtils.showInfo("Spremanje", "Uspjeh", "Uređaj " + name + " je uspješno spremljen u bazu podataka!");

            nameTextField.clear();
            powerRatingTextField.clear();
            ipAddressTextField.clear();
            osComboBox.setValue(null);
            deviceTypeComboBox.setValue(null);

        } catch (NumberFormatException _) {
            DialogUtils.showError("Greška", "Neispravan format", "Power rating mora biti cijeli broj!");
        }
    }


    @FXML
    private void goBack() {
        FxUtils.showAddDevicesScreen();
    }
}
