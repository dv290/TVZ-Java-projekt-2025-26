package hr.javafx.energycms.controllers;

import hr.javafx.energycms.entities.User;
import hr.javafx.energycms.repository.UserRepository;
import hr.javafx.energycms.utils.DialogUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.Optional;


public class UsersController {

    @FXML
    private TableView<User> userTableView;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TableColumn<User, String> idColumn;

    @FXML
    private TableColumn<User, String> firstNameColumn;

    @FXML
    private TableColumn<User, String> lastNameColumn;

    @FXML
    private TableColumn<User, String> contactColumn;

    @FXML
    private TableColumn<User, String> devicesColumn;

    private List<User> allUsers;

    @FXML
    private void initialize() {

        idColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));

        firstNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFirstName()));

        lastNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLastName()));

        contactColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getContact().email()));

        devicesColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getDevices().size())));


        loadData();
    }

    @FXML
    private void searchUser() {
        String firstNameQuery = firstNameTextField.getText().toLowerCase().trim();
        String lastNameQuery = lastNameTextField.getText().toLowerCase().trim();

        List<User> filteredList = allUsers.stream()
                .filter(user -> user.getFirstName().toLowerCase().contains(firstNameQuery))
                .filter(user -> user.getLastName().toLowerCase().contains(lastNameQuery))
                .toList();

        if (filteredList.isEmpty()) {
            DialogUtils.showInfo("Rezultati pretrage", "Nema podudaranja",
                    "Nije pronađen nijedan korisnik prema zadanim kriterijima.");

            userTableView.setItems(FXCollections.observableArrayList(allUsers));
        } else {
            userTableView.setItems(FXCollections.observableArrayList(filteredList));
        }
    }


    private void loadData() {
        allUsers = UserRepository.findAllUsers();
        userTableView.setItems(FXCollections.observableArrayList(allUsers));
    }


    @FXML
    private void deleteSelectedUser() {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            DialogUtils.showError("Brisanje nije moguće", "Korisnik nije odabran",
                    "Molimo odaberite korisnika u tablici kojeg želite obrisati.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda brisanja");
        alert.setHeaderText("Brisanje korisnika: " + selectedUser.getFirstName() + " " + selectedUser.getLastName());
        alert.setContentText("Jeste li sigurni? Ovo će obrisati i sva mjerenja vezana uz ovog korisnika.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            UserRepository.deleteUser(selectedUser.getId());

            loadData();

            DialogUtils.showInfo("Uspjeh", "Korisnik obrisan", "Korisnik je uspješno uklonjen iz baze.");
        }
    }
}
