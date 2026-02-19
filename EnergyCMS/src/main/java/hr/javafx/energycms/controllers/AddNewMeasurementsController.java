package hr.javafx.energycms.controllers;

import hr.javafx.energycms.entities.Device;
import hr.javafx.energycms.entities.Measurement;
import hr.javafx.energycms.entities.User;
import hr.javafx.energycms.entities.enums.MeasurementType;
import hr.javafx.energycms.repository.DeviceRepository;
import hr.javafx.energycms.repository.JsonRepository;
import hr.javafx.energycms.repository.MeasurementRepository;
import hr.javafx.energycms.repository.UserRepository;
import hr.javafx.energycms.utils.DialogUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class AddNewMeasurementsController {
    @FXML
    private ComboBox<User> userComboBox;
    @FXML
    private ComboBox<Device> deviceComboBox;
    @FXML
    private ComboBox<MeasurementType> measurementTypeComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField valueTextField;
    @FXML
    private TextField measurementUnitTextField;


    Set<User> users = JsonRepository.loadUsers(Path.of("src/data/users.json"));
    List<Device> devices = JsonRepository.loadDevices();

    @FXML
    private void initialize() {
        userComboBox.setItems(FXCollections.observableArrayList(UserRepository.findAllUsers()));
        deviceComboBox.setItems(FXCollections.observableArrayList(DeviceRepository.findAllDevices()));
        measurementTypeComboBox.setItems(FXCollections.observableArrayList(MeasurementType.values()));
    }

    @FXML
    private void saveMeasurement() {
        User user = userComboBox.getValue();
        Device device = deviceComboBox.getValue();
        MeasurementType mType = measurementTypeComboBox.getValue();
        LocalDate date = datePicker.getValue();
        String valStr = valueTextField.getText();
        String unit = measurementUnitTextField.getText();

        if(user == null || device == null || mType == null || date == null || valStr.isBlank() || unit.isBlank()) {
            DialogUtils.showError("Greška pri unosu", "Neispravan unos", "Sva polja moraju biti popunjena!");
            return;
        }

        try {
            BigDecimal value = new BigDecimal(valStr);

            if (value.compareTo(BigDecimal.ZERO) < 0) {
                DialogUtils.showError("Greška", "Negativna vrijednost", "Vrijednost mjerenja ne smije biti manja od 0!");
                return;
            }

            Measurement newMeasurement = new Measurement.Builder()
                    .user(user)
                    .device(device)
                    .measurementType(mType)
                    .date(date)
                    .value(value)
                    .measurementUnit(unit)
                    .build();

            MeasurementRepository.save(newMeasurement);

            DialogUtils.showInfo("Spremanje", "Uspjeh", "Mjerenje (ID: " + newMeasurement.getId() + ") je uspješno spremljeno!");

            userComboBox.setValue(null);
            deviceComboBox.setValue(null);
            measurementTypeComboBox.setValue(null);
            datePicker.setValue(null);
            valueTextField.clear();
            measurementUnitTextField.clear();

        } catch (NumberFormatException _) {
            DialogUtils.showError("Greška", "Neispravan format", "Vrijednost mora biti broj (npr. 10.5)!");
        }
    }
}
