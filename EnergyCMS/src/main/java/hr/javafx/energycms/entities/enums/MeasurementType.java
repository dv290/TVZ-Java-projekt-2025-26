package hr.javafx.energycms.entities.enums;

import java.io.Serializable;

public enum MeasurementType implements Serializable {
    GAS("Plin"),
    WATER("Voda"),
    TEMPERATURE("Temperatura"),
    ELECTRICITY("Struja");

    private final String measurementTypeName;

    MeasurementType(final String measurementTypeName) {
        this.measurementTypeName = measurementTypeName;
    }

    public String getMeasurementTypeName() {
        return measurementTypeName;
    }

    @Override
    public String toString() {
        return measurementTypeName;
    }
}
