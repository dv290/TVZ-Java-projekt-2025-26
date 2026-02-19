package hr.javafx.energycms.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private static Long idCounter = 1L;

    private Long id;
    private String firstName;
    private String lastName;
    private ContactInfo contact;
    private List<Device> devices = new ArrayList<>();

    public User() {}

    public User(String firstName, String lastName, ContactInfo contact, List<Device> devices) {
        this.id = idCounter;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
        this.devices = devices;
        idCounter++;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ContactInfo getContact() {
        return contact;
    }

    public void setContact(ContactInfo contact) {
        this.contact = contact;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(contact, user.contact) && Objects.equals(devices, user.devices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, contact, devices);
    }


    @Override
    public String toString() {
        return String.format("%s, %s (ID: %d)", lastName, firstName, id);
    }
}
