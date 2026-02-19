package hr.javafx.energycms.controllers.login;

import hr.javafx.energycms.app.HelloApplication;
import hr.javafx.energycms.repository.UserRepository;
import hr.javafx.energycms.utils.DialogUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameTextField.getText();
        String password = passwordField.getText();

        if (UserRepository.authenticate(username, password)) {
            showMainScreen();
        } else {
            DialogUtils.showError("Greška pri prijavi", "Neispravni podaci",
                    "Korisničko ime ili lozinka nisu točni.");
        }
    }

    @FXML
    private void showMainScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/hr/javafx/energycms/hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 800);

            Stage stage = (Stage) usernameTextField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
