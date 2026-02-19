package hr.javafx.energycms.repository;

import hr.javafx.energycms.entities.*;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static hr.javafx.energycms.app.MainConsoleLauncher.logger;

public class JsonRepository {
    private JsonRepository() {
    }

    private static final JsonbConfig config = new JsonbConfig()
            .withFormatting(true)
            .withNullValues(true)
            .withEncoding("UTF-8");


    public static <T> void saveList(List<T> list, Path path) {
        try (Jsonb jsonb = JsonbBuilder.create(config)) {
            String json = jsonb.toJson(list);
            Files.writeString(path, json);
        } catch (Exception e) {
            logger.error("Greška pri spremanju JSON-a: {}", path, e);
        }
    }

    public static void saveUser(User newUser) {
        Path usersFile = Path.of("src/data/users.json");
        Set<User> users = loadUsers(usersFile);
        users.add(newUser);

        try (Jsonb jsonb = JsonbBuilder.create(config)) {
            String json = jsonb.toJson(users);
            Files.writeString(usersFile, json);
        } catch (Exception e) {
            logger.error("Greška pri spremanju korisnika!", e);
        }
    }

    public static List<StandardDevice> loadStandardDevices(Path path) {
        if (!Files.exists(path)) return new ArrayList<>();

        try (Jsonb jsonb = JsonbBuilder.create(config)) {
            StandardDevice[] array = jsonb.fromJson(Files.readString(path), StandardDevice[].class);
            return array != null ? new ArrayList<>(List.of(array)) : new ArrayList<>();
        } catch (Exception e) {
            logger.error("Greška pri učitavanju standardnih uređaja", e);
            return new ArrayList<>();
        }
    }

    public static List<SmartDevice> loadSmartDevices(Path path) {
        if (!Files.exists(path)) return new ArrayList<>();
        try (Jsonb jsonb = JsonbBuilder.create(config)) {
            SmartDevice[] array = jsonb.fromJson(Files.readString(path), SmartDevice[].class);
            return array != null ? new ArrayList<>(List.of(array)) : new ArrayList<>();
        } catch (Exception e) {
            logger.error("Greška pri učitavanju pametnih uređaja", e);
            return new ArrayList<>();
        }
    }

    public static List<Device> loadDevices() {
        List<SmartDevice> smartDevices = loadSmartDevices(Path.of("src/data/smartdevices.json"));
        List<StandardDevice> standardDevices = loadStandardDevices(Path.of("src/data/standarddevices.json"));

        List<Device> devices = new ArrayList<>();
        devices.addAll(standardDevices);
        devices.addAll(smartDevices);

        return devices;
    }


    public static Set<User> loadUsers(Path path) {
        if (!Files.exists(path)) return new HashSet<>();

        try (Jsonb jsonb = JsonbBuilder.create(config)) {
            User[] usersArray = jsonb.fromJson(Files.readString(path), User[].class);

            if (usersArray != null) {
                return new HashSet<>(List.of(usersArray));
            }

            return new HashSet<>();
        } catch (Exception e) {
            logger.error("Greška pri učitavanju korisnika iz JSON-a: {}", path, e);
            return new HashSet<>();
        }
    }

    public static List<Measurement> loadMeasurements(Path path, Set<User> users, List<Device> devices) {
        if (!Files.exists(path)) return new ArrayList<>();

        try (Jsonb jsonb = JsonbBuilder.create(config)) {
            Measurement[] array = jsonb.fromJson(Files.readString(path), Measurement[].class);
            List<Measurement> measurements = array != null ? new ArrayList<>(List.of(array)) : new ArrayList<>();

            for (Measurement m : measurements) {
                if (m.getUserId() != null) {
                    users.stream()
                            .filter(u -> u.getId().equals(m.getUserId()))
                            .findFirst()
                            .ifPresent(m::setUser);
                }

                if (m.getDeviceId() != null) {
                    devices.stream()
                            .filter(d -> d.getId().equals(m.getDeviceId()))
                            .findFirst()
                            .ifPresent(m::setDevice);
                }
            }
            return measurements;
        } catch (Exception e) {
            logger.error("Greška pri učitavanju mjerenja, vraćanje prazne liste. ", e);
            return new ArrayList<>();
        }
    }
}
