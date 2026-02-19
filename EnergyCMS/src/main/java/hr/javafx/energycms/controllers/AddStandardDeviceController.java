package hr.javafx.energycms.controllers;

import hr.javafx.energycms.entities.Device;
import hr.javafx.energycms.entities.StandardDevice;
import hr.javafx.energycms.entities.enums.DeviceType;
import hr.javafx.energycms.repository.DeviceRepository;
import hr.javafx.energycms.repository.JsonRepository;
import hr.javafx.energycms.utils.DialogUtils;
import hr.javafx.energycms.utils.FxUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.nio.file.Path;
import java.util.List;

import static java.lang.Integer.parseInt;

public class AddStandardDeviceController {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField powerRatingTextField;
    @FXML
    private TextField controlKnobsTextField;
    @FXML
    private TextField powerGradeTextField;
    @FXML
    private ComboBox<DeviceType> deviceTypeComboBox;


    @FXML
    private void initialize() {
        deviceTypeComboBox.getItems().addAll(DeviceType.values());
    }

    @FXML
    private void saveStandardDevice() {
        String name = nameTextField.getText();
        String powerRatingStr = powerRatingTextField.getText();
        String controlKnobsStr = controlKnobsTextField.getText();
        String powerGradeStr = powerGradeTextField.getText();
        DeviceType selectedDeviceType = deviceTypeComboBox.getValue();

        if (name.isBlank() || powerRatingStr.isBlank() || controlKnobsStr.isBlank() || powerGradeStr.isBlank() || selectedDeviceType == null) {
            DialogUtils.showError("Greška pri unosu", "Neispravan unos", "Sva polja moraju biti popunjena!");
            return;
        }

        try {
            int powerRating = Integer.parseInt(powerRatingStr);
            int controlKnobs = Integer.parseInt(controlKnobsStr);
            Character powerGrade = powerGradeStr.toUpperCase().charAt(0);

            if (powerRating < 0 || controlKnobs < 0) {
                DialogUtils.showError("Greška", "Neispravna vrijednost", "Brojčane vrijednosti ne smiju biti negativne!");
                return;
            }

            StandardDevice newDevice = new StandardDevice(
                    name,
                    selectedDeviceType,
                    powerRating,
                    controlKnobs,
                    powerGrade,
                    true
            );

            DeviceRepository.saveStandardDevice(newDevice);

            DialogUtils.showInfo("Spremanje", "Uspjeh", "Standardni uređaj " + name + " je uspješno spremljen!");

            nameTextField.clear();
            powerRatingTextField.clear();
            controlKnobsTextField.clear();
            powerGradeTextField.clear();
            deviceTypeComboBox.setValue(null);

        } catch (NumberFormatException _) {
            DialogUtils.showError("Greška", "Neispravan format", "Power rating i broj gumba moraju biti cijeli brojevi!");
        } catch (IndexOutOfBoundsException _) {
            DialogUtils.showError("Greška", "Neispravan format", "Energetski razred mora biti jedan znak!");
        }
    }


    @FXML
    private void goBack() { FxUtils.showAddDevicesScreen(); }
}
