package hr.javafx.energycms.entities.enums;

import java.io.Serializable;

public enum DeviceType implements Serializable {
    WHITE_ELECTRONICS("Bijela elektronika"),
    AIR_CONDITIONING("Hlađenje"),
    ELECTRONICS("Elektronika"),
    IT("IT"),
    ELECTRIC_CHARGING("Električno punjenje"),
    KITCHEN_APPLIANCES("Kuhinjske naprave"),
    HOUSE_APPLIANCES("Kućni uređaji"),
    LAUNDRY_CLEANING("Čišćenje rublja");



    private final String deviceTypeName;

    DeviceType(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public String getTypeName() {return deviceTypeName;}


    @Override
    public String toString() {
        return deviceTypeName;
    }
}
