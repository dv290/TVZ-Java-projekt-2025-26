package hr.javafx.energycms.controllers;

import hr.javafx.energycms.entities.*;
import hr.javafx.energycms.repository.MeasurementRepository;
import hr.javafx.energycms.utils.DialogUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public class MeasurementsController {
    @FXML
    private TableView<Measurement> measurementTableView;
    @FXML
    private TableColumn<Measurement, String> idColumn;
    @FXML
    private TableColumn<Measurement, String> dateColumn;
    @FXML
    private TableColumn<Measurement, String> deviceColumn;
    @FXML
    private TableColumn<Measurement, String> valueColumn;
    @FXML
    private TableColumn<Measurement, String> unitColumn;
    @FXML
    private TableColumn<Measurement, String> typeColumn;
    @FXML
    private TableColumn<Measurement, String> userColumn;
    @FXML
    private DatePicker datePicker;

    private List<Measurement> allMeasurements;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));

        deviceColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDevice() != null ?
                        cellData.getValue().getDevice().getName() : "Nepoznato"));

        valueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().toString()));
        unitColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMeasurementUnit()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMeasurementType().toString()));

        userColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUser() != null ?
                        cellData.getValue().getUser().getFirstName() + " " + cellData.getValue().getUser().getLastName() : "Sustav"));

        loadData();
    }

    private void loadData() {
        allMeasurements = MeasurementRepository.findAllMeasurements();
        measurementTableView.setItems(FXCollections.observableArrayList(allMeasurements));
    }

    @FXML
    private void searchMeasurement() {
        LocalDate selectedDate = datePicker.getValue();

        if (selectedDate == null) {
            measurementTableView.setItems(FXCollections.observableArrayList(allMeasurements));
            return;
        }

        List<Measurement> filteredList = allMeasurements.stream()
                .filter(m -> m.getDate() != null && m.getDate().equals(selectedDate))
                .toList();

        if (filteredList.isEmpty()) {
            DialogUtils.showInfo("Rezultati pretrage", "Nema podataka",
                    "Nema zabilježenih mjerenja za datum: " + selectedDate);
            measurementTableView.setItems(FXCollections.observableArrayList(allMeasurements));
        } else {
            measurementTableView.setItems(FXCollections.observableArrayList(filteredList));
        }
    }

    @FXML
    private void deleteSelectedMeasurement() {
        Measurement selected = measurementTableView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            DialogUtils.showError("Greška", "Odabir", "Molimo odaberite mjerenje.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja");
        alert.setHeaderText("Brisanje mjerenja #" + selected.getId());
        alert.setContentText("Jeste li sigurni?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            MeasurementRepository.deleteMeasurement(selected.getId());
            loadData();
            DialogUtils.showInfo("Uspjeh", "Obrisano", "Mjerenje je uklonjeno.");
        }
    }

}
