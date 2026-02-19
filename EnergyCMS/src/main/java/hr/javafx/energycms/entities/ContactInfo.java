package hr.javafx.energycms.entities;

import java.io.Serializable;

public record ContactInfo(String email, String phoneNumber, String address) implements Serializable {}
