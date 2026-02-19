package hr.javafx.energycms.controllers.edit;

import hr.javafx.energycms.entities.Device;
import hr.javafx.energycms.entities.SmartDevice;
import hr.javafx.energycms.entities.enums.DeviceType;
import hr.javafx.energycms.repository.DeviceRepository;
import hr.javafx.energycms.utils.DialogUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class EditSmartDeviceController {
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

    private List<SmartDevice> smartDevices;

    private SmartDevice selectedDevice;


    @FXML
    private void initialize() {
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

        osComboBox.setItems(FXCollections.observableArrayList("Android", "iOS", "Windows", "Linux", "Tizen"));
        deviceTypeComboBox.setItems(FXCollections.observableArrayList(DeviceType.values()));


        deviceTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection instanceof SmartDevice smart) {
                selectedDevice = smart;
                nameTextField.setText(smart.getName());
                powerRatingTextField.setText(smart.getPowerRating().toString());
                ipAddressTextField.setText(smart.getIpAddress());
                osComboBox.setValue(smart.getOperatingSystem());
                deviceTypeComboBox.setValue(smart.getDeviceType());
            }
        });

        loadData();

    }

    private void loadData() {
        smartDevices = new ArrayList<>();
        List<Device> allDevices = DeviceRepository.findAllDevices();
        for (Device device : allDevices) {
            if (device instanceof SmartDevice) {
                smartDevices.add((SmartDevice) device);
            }
        }
        deviceTableView.setItems(FXCollections.observableArrayList(smartDevices));
    }

    @FXML
    private void saveChanges() {
        if (selectedDevice == null) {
            DialogUtils.showError("Greška", "Uređaj nije odabran", "Odaberite uređaj iz tablice.");
            return;
        }

        selectedDevice.setName(nameTextField.getText());
        selectedDevice.setPowerRating(Integer.parseInt(powerRatingTextField.getText()));
        selectedDevice.setIpAddress(ipAddressTextField.getText());
        selectedDevice.setOperatingSystem(osComboBox.getValue());

        DeviceRepository.updateSmartDevice(selectedDevice);

        loadData();
        clearFields();
        DialogUtils.showInfo("Uspjeh", "Spremljeno", "Smart uređaj je ažuriran.");
    }

    private void clearFields() {
        nameTextField.clear();
        powerRatingTextField.clear();
        ipAddressTextField.clear();
        osComboBox.setValue(null);
        selectedDevice = null;
    }
}
