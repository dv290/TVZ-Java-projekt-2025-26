package hr.javafx.energycms.entities;

import hr.javafx.energycms.entities.enums.DeviceType;
import hr.javafx.energycms.exceptions.NegativeValueException;
import jakarta.json.bind.annotation.JsonbSubtype;
import jakarta.json.bind.annotation.JsonbTypeInfo;

import java.math.BigDecimal;

import static hr.javafx.energycms.app.MainConsoleLauncher.logger;


@JsonbTypeInfo(
        key = "dType",
        value = {
                @JsonbSubtype(alias = "SMART", type = SmartDevice.class),
                @JsonbSubtype(alias = "STANDARD", type = StandardDevice.class)
        }
)
public abstract non-sealed class Device implements EnergyTaxable {
    private static Long idCounter = 1L;

    private Long id;
    private String name;
    private DeviceType deviceType;
    private Integer powerRating;
    private boolean isOn;


    protected Device() {}

    protected Device(String name, DeviceType deviceType, Integer powerRating, boolean isOn) {
        if (powerRating < 0) {throw new NegativeValueException();}

        this.id = idCounter++;
        this.name = name;
        this.deviceType = deviceType;
        this.powerRating = powerRating;
        this.isOn = isOn;
    }

    @Override
    public BigDecimal calculateEnvironmentalTax() {
        System.out.println("Test");
        return new BigDecimal("1.0");
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeviceType getDeviceType() {return deviceType;}

    public void setDeviceType(DeviceType deviceType) {this.deviceType = deviceType;}

    public Integer getPowerRating() { return powerRating; }

    public void setPowerRating(Integer powerRating) {
        if (powerRating < 0) {
            logger.warn("Pokušaj unosa negativne snage: {}", powerRating);
            throw new NegativeValueException("Ocjena snage uređaja ne može biti negativna!");
        }
        this.powerRating = powerRating;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean onParameter) {
        isOn = onParameter;
    }

    @Override
    public String toString() {
        return String.format("%s (ID: %d)", name, id);
    }
}
