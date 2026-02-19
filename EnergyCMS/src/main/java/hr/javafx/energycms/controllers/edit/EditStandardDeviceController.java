package hr.javafx.energycms.controllers.edit;

import hr.javafx.energycms.entities.Device;
import hr.javafx.energycms.entities.StandardDevice;
import hr.javafx.energycms.entities.enums.DeviceType;
import hr.javafx.energycms.repository.DeviceRepository;
import hr.javafx.energycms.utils.DialogUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class EditStandardDeviceController {
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
    private TableView<Device> deviceTableView;
    @FXML
    private TableColumn<Device, String> idColumn;
    @FXML
    private TableColumn<Device, String> nameColumn;
    @FXML
    private TableColumn<Device, String> typeColumn;
    @FXML
    private TableColumn<Device, String> powerRatingColumn;
    @FXML
    private TableColumn<Device, String> statusColumn;

    private List<StandardDevice> standardDevices;

    private StandardDevice selectedDevice;

    @FXML
    private void  initialize()
    {
        idColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getId().toString()));

        nameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName()));

        typeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getClass().getSimpleName()));

        powerRatingColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPowerRating().toString() + " W"));

        statusColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().isOn() ? "DA" : "NE"));

        deviceTableView.setEditable(true);

        deviceTypeComboBox.setItems(FXCollections.observableArrayList(DeviceType.values()));


        deviceTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection instanceof StandardDevice standard) {
                selectedDevice = standard;
                nameTextField.setText(standard.getName());
                powerRatingTextField.setText(standard.getPowerRating().toString());
                controlKnobsTextField.setText(String.valueOf(standard.getNumberOfControlKnobs()));
                powerGradeTextField.setText(String.valueOf(standard.getEnergyEfficiencyClass()));
                deviceTypeComboBox.setValue(standard.getDeviceType());
            }
        });

        loadData();
    }

    private void loadData() {
        standardDevices = new ArrayList<>();

        List<Device> allDevices = DeviceRepository.findAllDevices();
        for (Device device : allDevices) {
            if (device instanceof StandardDevice) {
                standardDevices.add((StandardDevice) device);
            }
        }
        deviceTableView.setItems(FXCollections.observableArrayList(standardDevices));
    }

    @FXML
    private void saveChanges() {
        if (selectedDevice == null) {
            DialogUtils.showError("Greška", "Uređaj nije odabran", "Molimo odaberite uređaj iz tablice.");
            return;
        }

        selectedDevice.setName(nameTextField.getText());
        selectedDevice.setPowerRating(Integer.parseInt(powerRatingTextField.getText()));
        selectedDevice.setNumberOfControlKnobs(Integer.parseInt(controlKnobsTextField.getText()));

        String energyInput = powerGradeTextField.getText().trim().toUpperCase();
        if (!energyInput.isEmpty()) {
            selectedDevice.setEnergyEfficiencyClass(energyInput.charAt(0));
        }


        DeviceRepository.updateStandardDevice(selectedDevice);

        loadData();
        clearFields();
        DialogUtils.showInfo("Uspjeh", "Promjene spremljene", "Standardni uređaj je uspješno ažuriran.");
    }

    private void clearFields() {
        nameTextField.clear();
        powerRatingTextField.clear();
        controlKnobsTextField.clear();
        powerGradeTextField.clear();
        selectedDevice = null;
    }
}
