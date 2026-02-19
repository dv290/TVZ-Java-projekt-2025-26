package hr.javafx.energycms.controllers;

import hr.javafx.energycms.entities.Device;
import hr.javafx.energycms.repository.DeviceRepository;
import hr.javafx.energycms.utils.DialogUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.Optional;

public class DevicesController {
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

    @FXML
    private TextField deviceNameTextField;
    @FXML
    private TextField deviceTypeTextField;

    private List<Device> allDevices;

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

        loadData();
    }

    private void loadData() {
        allDevices = DeviceRepository.findAllDevices();
        deviceTableView.setItems(FXCollections.observableArrayList(allDevices));
    }

    @FXML
    private void searchDevice() {
        String nameQuery = deviceNameTextField.getText().toLowerCase().trim();
        String typeQuery = deviceTypeTextField.getText().toLowerCase().trim();

        List<Device> filtered = allDevices.stream()
                .filter(d -> d.getName().toLowerCase().contains(nameQuery))
                .filter(d -> d.getClass().getSimpleName().toLowerCase().contains(typeQuery))
                .toList();

        if (filtered.isEmpty()) {
            DialogUtils.showInfo("Pretraga", "Nema rezultata", "Nije pronađen nijedan uređaj.");
            deviceTableView.setItems(FXCollections.observableArrayList(allDevices));
        } else {
            deviceTableView.setItems(FXCollections.observableArrayList(filtered));
        }
    }

    @FXML
    private void deleteSelectedDevice() {
        Device selectedDevice = deviceTableView.getSelectionModel().getSelectedItem();

        if (selectedDevice == null) {
            DialogUtils.showError("Greška pri brisanju", "Uređaj nije odabran",
                    "Molimo odaberite uređaj u tablici koji želite obrisati.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja");
        alert.setHeaderText("Brisanje uređaja: " + selectedDevice.getName());
        alert.setContentText("Jeste li sigurni? Brisanje uređaja će ukloniti i sva njegova povezana mjerenja iz baze.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            DeviceRepository.deleteDevice(selectedDevice.getId());

            loadData();

            DialogUtils.showInfo("Uspjeh", "Obrisano", "Uređaj je uspješno uklonjen.");
        }
    }
}
