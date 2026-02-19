package hr.javafx.energycms.app.loader;

import hr.javafx.energycms.entities.*;
import hr.javafx.energycms.entities.enums.DeviceType;
import hr.javafx.energycms.entities.enums.MeasurementType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class ObjectLoaders {
    private ObjectLoaders () {}

    public static List<SmartDevice> loadSmartDevices() {
        List<SmartDevice> smartDevicesList = new ArrayList<>();
        smartDevicesList.add(new SmartDevice("Samsung Frizider", DeviceType.WHITE_ELECTRONICS, 150, "TizenOS", "192.168.1.10", 85, true));
        smartDevicesList.add(new SmartDevice("Daikin AC", DeviceType.AIR_CONDITIONING, 2500, "Daikin Cloud", "192.168.1.11", 90, false));
        smartDevicesList.add(new SmartDevice("Sony OLED TV", DeviceType.ELECTRONICS, 120, "Android TV", "192.168.1.12", 95, true));
        smartDevicesList.add(new SmartDevice("Gaming PC", DeviceType.IT, 600, "Windows 11", "192.168.1.13", 100, true));
        smartDevicesList.add(new SmartDevice("Tesla Zidni Punjac", DeviceType.ELECTRIC_CHARGING, 11000, "Tesla OS", "192.168.1.14", 80, false));

        return smartDevicesList;
    }

    public static List<StandardDevice> loadStandardDevices() {
        List<StandardDevice> standardDevicesList = new ArrayList<>();
        standardDevicesList.add(new StandardDevice("Gorenje Stednjak", DeviceType.KITCHEN_APPLIANCES, 3000, 4, 'B', false));
        standardDevicesList.add(new StandardDevice("Bosch Perilica Rublja", DeviceType.LAUNDRY_CLEANING, 2200, 3, 'A', true));
        standardDevicesList.add(new StandardDevice("Pegla", DeviceType.HOUSE_APPLIANCES, 2000, 1, 'C', false));
        standardDevicesList.add(new StandardDevice("Toster", DeviceType.KITCHEN_APPLIANCES, 800, 2, 'A', true));
        standardDevicesList.add(new StandardDevice("Usisavac", DeviceType.HOUSE_APPLIANCES, 1600, 1, 'D', false));

        return standardDevicesList;
    }

        public static Set<User> loadUsers(List<Device> devices) {
            Set<User> users = new HashSet<>();
            users.add(new User("Ivan", "Ivic",new ContactInfo("ivan@email.com", "091234567", "Ulica 1"), devices));
            users.add(new User("Marija", "Maric",new ContactInfo("marija@email.com", "098765432", "Ulica 2"), devices) );
            users.add(new User ("Dino","Dinic",new ContactInfo("dino@email.com", "092565781", "Ulica 3"), devices) );
            users.add(new User("Pero", "Peric",new ContactInfo("pero@email.com", "095467321", "Ulica 4"), devices) );
            users.add(new User("Ana", "Anic",new ContactInfo("ana@email.com", "099125422", "Ulica 5"),devices) );

            return users;
        }

        public static List<Measurement> loadMeasurements(List<User> users, List<Device> devices) {
            List<Measurement> measurements = new ArrayList<>();
            measurements.add(new Measurement.Builder()
                            .user(users.getFirst())
                            .device(devices.getFirst())
                            .date(LocalDate.now())
                            .measurementType(MeasurementType.GAS)
                            .value(new BigDecimal("12.45"))
                            .measurementUnit("kWh")
                            .build());

            measurements.add(new Measurement.Builder()
                    .user(users.get(1))
                    .device(devices.get(2))
                    .date(LocalDate.of(2026,1,9))
                    .measurementType(MeasurementType.ELECTRICITY)
                    .value(new BigDecimal("1.20"))
                    .measurementUnit("kWh")
                    .build());

            measurements.add(new Measurement.Builder()
                    .user(users.get(2))
                    .device(devices.get(1))
                    .date(LocalDate.now().minusMonths(2).minusWeeks(1))
                    .measurementType(MeasurementType.ELECTRICITY)
                    .value(new BigDecimal("45.60"))
                    .measurementUnit("kWh")
                    .build());

            measurements.add(new Measurement.Builder()
                    .user(users.get(3))
                    .device(devices.get(3))
                    .date(LocalDate.now().minusMonths(1))
                    .measurementType(MeasurementType.ELECTRICITY)
                    .value(new BigDecimal("3.45"))
                    .measurementUnit("kWh")
                    .build());

            measurements.add(new Measurement.Builder()
                    .user(users.getLast())
                    .device(devices.getLast())
                    .date(LocalDate.of(2025,5,12))
                    .measurementType(MeasurementType.ELECTRICITY)
                    .value(new BigDecimal("8.90"))
                    .measurementUnit("kWh")
                    .build());

            return measurements;
        }
}
