package hr.javafx.energycms.repository;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static hr.javafx.energycms.app.MainConsoleLauncher.logger;

public class BinaryRepository {
    private BinaryRepository() {}

    public static void saveBackup(EntityBackup backup, Path path) {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
            oos.writeObject(backup);
            logger.info("Backup uspješno spremljen u: {}", path);
        } catch (IOException e) {
            logger.error("Greška pri spremanju binarnog backupa", e);
        }
    }

    public static EntityBackup loadBackup(Path path) {
        if (!Files.exists(path)) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path))) {
            return (EntityBackup) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Greška pri učitavanju binarnog backupa", e);
            return null;
        }
    }
}
