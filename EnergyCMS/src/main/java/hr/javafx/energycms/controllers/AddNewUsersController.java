package hr.javafx.energycms.controllers;

import hr.javafx.energycms.entities.ContactInfo;
import hr.javafx.energycms.entities.User;
import hr.javafx.energycms.repository.UserRepository;
import hr.javafx.energycms.utils.DialogUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class AddNewUsersController {
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
    private void saveUser() {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String contactEmail = emailTextField.getText();
        String contactNumber = numberTextField.getText();
        String address = addressTextField.getText();

        String validationError = validateFields(firstName, lastName, contactEmail, contactNumber, address);
        if (validationError != null) {
            DialogUtils.showError("Greška", "Validacija", validationError);
            return;
        }

        User newUser = new User(firstName, lastName,
                new ContactInfo(contactEmail, contactNumber, address),
                new ArrayList<>());

        boolean isSaved = UserRepository.saveUser(newUser);

        if (isSaved) {
            DialogUtils.showInfo("Spremanje", "Uspjeh",
                    "Korisnik " + firstName + " " + lastName + " je uspješno spremljen u bazu!");

            firstNameTextField.clear();
            lastNameTextField.clear();
            emailTextField.clear();
            numberTextField.clear();
            addressTextField.clear();
        }
    }


    private String validateFields(String firstName, String lastName, String email, String number, String address) {
        if (firstName == null || firstName.trim().isEmpty()) return "Ime ne može biti prazno!";
        if (lastName == null || lastName.trim().isEmpty()) return "Prezime ne može biti prazno!";
        if (email == null || email.trim().isEmpty()) return "Email ne može biti prazan!";
        if (number == null || number.trim().isEmpty()) return "Broj ne može biti prazan!";
        if (address == null || address.trim().isEmpty()) return "Adresa ne može biti prazna!";
        return null;
    }
}
