package hr.javafx.energycms.controllers.edit;

import hr.javafx.energycms.entities.ContactInfo;
import hr.javafx.energycms.entities.User;
import hr.javafx.energycms.repository.UserRepository;
import hr.javafx.energycms.utils.DialogUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.List;

public class EditUserController {
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField numberTextField;
    @FXML
    private TextField addressTextField;



    @FXML
    private TableView<User> userTableView;

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

    private User selectedUser;


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

        userTableView.setEditable(true);

        userTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedUser = newSelection;
                firstNameTextField.setText(newSelection.getFirstName());
                lastNameTextField.setText(newSelection.getLastName());
                emailTextField.setText(newSelection.getContact().email());
                numberTextField.setText(newSelection.getContact().phoneNumber());
                addressTextField.setText(newSelection.getContact().address());
            }
        });


        loadData();
    }


    private void loadData() {
        allUsers = UserRepository.findAllUsers();
        userTableView.setItems(FXCollections.observableArrayList(allUsers));
    }

    @FXML
    private void saveUserChanges() {
        if (selectedUser == null) {
            DialogUtils.showError("Greška", "Korisnik nije odabran", "Molimo odaberite korisnika iz tablice.");
            return;
        }

        selectedUser.setFirstName(firstNameTextField.getText());
        selectedUser.setLastName(lastNameTextField.getText());

        selectedUser.setContact(new ContactInfo(
                emailTextField.getText(),
                numberTextField.getText(),
                addressTextField.getText()
        ));

        UserRepository.updateUser(selectedUser);

        loadData();
        clearFields();

        DialogUtils.showInfo("Uspjeh", "Promjene spremljene", "Podaci o korisniku su uspješno ažurirani.");
    }

    private void clearFields() {
        firstNameTextField.clear();
        lastNameTextField.clear();
        emailTextField.clear();
        numberTextField.clear();
        addressTextField.clear();
        selectedUser = null;
    }
}
