package hr.javafx.energycms.entities;

import hr.javafx.energycms.entities.enums.MeasurementType;
import hr.javafx.energycms.exceptions.NegativeValueException;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbTransient;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Measurement {
    private static Long idCounter = 1L;

    private Long id;

    @JsonbTransient
    private User user;

    @JsonbTransient
    private Device device;

    private Long userId;
    private Long deviceId;

    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate date;

    private MeasurementType measurementType;
    private BigDecimal value;
    private String measurementUnit;

    public Measurement() {}

    private Measurement(Builder builder) {
        this.id = idCounter++;
        this.user = builder.user;
        this.device = builder.device;
        this.date = builder.date;
        this.measurementType = builder.measurementType;
        this.value = builder.value;
        this.measurementUnit = builder.measurementUnit;

        if (builder.user != null) this.userId = builder.user.getId();
        if (builder.device != null) this.deviceId = builder.device.getId();
    }

    public static class Builder {
        private User user;
        private Device device;
        private LocalDate date;
        private MeasurementType measurementType;
        private BigDecimal value;
        private String measurementUnit;

        public Builder user(User user) { this.user = user; return this; }
        public Builder device(Device device) { this.device = device; return this; }
        public Builder date(LocalDate date) { this.date = date; return this; }
        public Builder measurementType(MeasurementType measurementType) { this.measurementType = measurementType; return this; }
        public Builder value(BigDecimal value) {
            if (value.compareTo(BigDecimal.ZERO) < 0) throw new NegativeValueException();
            this.value = value;
            return this;
        }
        public Builder measurementUnit(String measurementUnit) { this.measurementUnit = measurementUnit; return this; }
        public Measurement build() { return new Measurement(this); }
    }

    public void setId(Long id) { this.id = id; }
    public void setUser(User user) {
        this.user = user;
        if (user != null) this.userId = user.getId();
    }
    public void setDevice(Device device) {
        this.device = device;
        if (device != null) this.deviceId = device.getId();
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getDeviceId() { return deviceId; }
    public void setDeviceId(Long deviceId) { this.deviceId = deviceId; }

    public void setDate(LocalDate date) { this.date = date; }
    public void setMeasurementType(MeasurementType measurementType) { this.measurementType = measurementType; }
    public void setValue(BigDecimal value) { this.value = value; }
    public void setMeasurementUnit(String measurementUnit) { this.measurementUnit = measurementUnit; }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public Device getDevice() { return device; }
    public LocalDate getDate() { return date; }
    public BigDecimal getValue() { return value; }
    public MeasurementType getMeasurementType() { return measurementType; }
    public String getMeasurementUnit() { return measurementUnit; }

    public static void setIdCounter(Long counter) { idCounter = counter; }
}
