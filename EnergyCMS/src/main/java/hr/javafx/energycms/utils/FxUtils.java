package hr.javafx.energycms.utils;

import hr.javafx.energycms.app.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

import static hr.javafx.energycms.app.MainConsoleLauncher.logger;

public class FxUtils {
    private FxUtils() {}

    public static void showAddDevicesScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/hr/javafx/energycms/add-new-devices.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 800);
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
        }
        catch (IOException | IllegalStateException e) {
            logger.error("Greška prilikom učitavanja add-new-devices.fxml", e);
            DialogUtils.showError("Error!",
                    "Greška prilikom otvaranja Add Device ekrana.",
                    "Došlo je do pogreške prilikom otvaranja datoteke add-new-devices.fxml");
        }
    }
}
