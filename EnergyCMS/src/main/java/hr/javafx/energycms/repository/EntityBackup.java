package hr.javafx.energycms.repository;

import hr.javafx.energycms.entities.Device;
import hr.javafx.energycms.entities.Measurement;
import hr.javafx.energycms.entities.User;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class EntityBackup implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<User> users;
    private List<Device> devices;
    private List<Measurement> measurements;

    public EntityBackup(List<User> users, List<Device> devices, List<Measurement> measurements) {
        this.users = users;
        this.devices = devices;
        this.measurements = measurements;
    }

    public List<User> getUsers() { return users; }
    public List<Device> getDevices() { return devices; }
    public List<Measurement> getMeasurements() { return measurements; }
}
