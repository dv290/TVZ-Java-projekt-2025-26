package hr.javafx.energycms.app;

import hr.javafx.energycms.entities.*;

import hr.javafx.energycms.generics.GenericsUtils;
import hr.javafx.energycms.repository.BinaryRepository;
import hr.javafx.energycms.repository.EntityBackup;
import hr.javafx.energycms.repository.JsonRepository;
import hr.javafx.energycms.utils.Inputs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MainConsoleLauncher {
    public static final Logger logger = LoggerFactory.getLogger(MainConsoleLauncher.class);

    private MainConsoleLauncher(){}

    static void runApp(){
        logger.info("APLIKACIJA POKRENUTA");
        Scanner scanner = new Scanner(System.in);

        List<StandardDevice> standardDevices = JsonRepository.loadStandardDevices(Path.of("src/data/standarddevices.json"));
        List<SmartDevice> smartDevices = JsonRepository.loadSmartDevices(Path.of("src/data/smartdevices.json"));
        List<Device> allDevices = new ArrayList<>();
        allDevices.addAll(standardDevices);
        allDevices.addAll(smartDevices);

        Set<User> users = JsonRepository.loadUsers(Path.of("src/data/users.json"));
        List<User> usersList = new ArrayList<>(users);


        List<Measurement> measurements = JsonRepository.loadMeasurements(Path.of("src/data/measurements.json"),users, allDevices);


        Path backupPath = Path.of("src/data/backup.bin");

        EntityBackup initialBackup = new EntityBackup(
                new ArrayList<>(users),
                allDevices,
                measurements
        );

        BinaryRepository.saveBackup(initialBackup, backupPath);
        System.out.println("Inicijalni backup kreiran u: " + backupPath.toAbsolutePath());



        //searchDevicesByName(scanner, allDevices);
        //searchUserByLastName(scanner, usersList);

        GenericsUtils.searchEntity(scanner, allDevices, Device::getName,
                "Unesi naziv uređaja: ", "Uređaj");

        GenericsUtils.searchEntity(scanner, usersList, User::getLastName,
                "Unesi traženo prezime: ", "Prezime");



        searchMeasurementByDate(scanner, measurements);


        //findHighestPowerRatingDevice(allDevices);
        Optional<Device> maxDevice = findHighestPowerRatingDeviceOptional(allDevices);
        maxDevice.ifPresentOrElse(
                highest -> System.out.printf("Uređaj s najvećim power ratingom je: %s (%s) s power ratingom: %d %n %n",
                        highest.getName(),
                        highest.getDeviceType().getTypeName(),
                        highest.getPowerRating()),
                () -> System.out.println("Nema uređaja za ispisati!\n")
        );


        //findLowestMeasurement(measurements);
        Optional<Measurement> minMeasurement = findLowestMeasurementOptional(measurements);
        minMeasurementPrintout(minMeasurement);


        Map<Device, List<Measurement>> measurementsByDevice = measurements.stream()
                .collect(Collectors.groupingBy(Measurement::getDevice));

        measurementsByDevice.forEach((device, mList) ->
            System.out.println("Uređaj: " + device.getName() + " ima " + mList.size() + " mjerenja.")
        );




        Map<Boolean, List<Device>> activeDevicesMap = allDevices.stream()
                .collect(Collectors.partitioningBy(Device::isOn));

        System.out.println("Trenutno uključeno uređaja: " + activeDevicesMap.get(true).size());


        List<Device> sortedDevices = allDevices.stream()
                .sorted(Comparator.comparing(Device::getPowerRating).reversed())
                .toList();

        System.out.println("Uređaji sortirani po snazi:");
        sortedDevices.forEach(d -> System.out.println(d.getName() + " - " + d.getPowerRating() + "W"));





        Integer totalPower = allDevices.stream()
                .map(Device::getPowerRating)
                .reduce(0, Integer::sum);

        System.out.println("Ukupna snaga svih uređaja u sustavu: "+ totalPower+" W");


        scanner.close();
    }

    public static void searchDevicesByName(Scanner scanner, List<Device> devices) {
        System.out.print("Unesi naziv uređaja koji pretražujes: ");
        String input = scanner.nextLine();


        System.out.println("Pronađeni uređaji: ");
        boolean found = false;
        for(Device device : devices) {
            if (input.trim().equalsIgnoreCase(device.getName())) {
                System.out.println("- " + device.getName() + " (ID: "+device.getId()+")");
                found = true;
            }
        }
        if (!found) {
            System.out.println("Uređaj \""+input+"\" nije pronađen.");
            logger.debug("Uređaj {} u metodi searchDevicesByName() nije pronađen.", input);
        }

        System.out.println("\n");
    }


    public static void searchUserByLastName(Scanner scanner, List<User> users) {
        System.out.print("Unesi traženo prezime: ");
        String input = scanner.nextLine();

        System.out.println("Pronađene osobe: ");
        boolean found = false;

        int index = 0;
        while(index < users.size())
        {
            if(input.trim().equalsIgnoreCase(users.get(index).getLastName())) {
                System.out.println("- "+users.get(index).getLastName()+", "+users.get(index).getFirstName()+" (ID: "+users.get(index).getId()+")");
                found = true;
            }
            index++;
        }

        if (!found) {
            System.out.println("Prezime \""+input+"\" nije pronađeno.");
            logger.debug("Prezime {} u metodi searchUsersByLastName() nije pronađeno.", input);
        }

        System.out.println("\n");
    }


    public static void searchMeasurementByDate(Scanner scanner, List<Measurement> measurements) {
        LocalDate searchedDate = Inputs.getSafeDateInput(scanner);

        if(measurements.isEmpty()) {
            System.out.println("Nema dostupnih mjerenja!");
            logger.debug("Lista za mjerenja u metodi searchMeasurementByDate() je prazna.");
            return;
        }

        boolean found = false;
        int index = 0;
        System.out.println("Mjerenja za period u: "+ searchedDate.getMonth()+". "+searchedDate.getYear());
        do {
            if (searchedDate.getYear() == measurements.get(index).getDate().getYear()
                    && searchedDate.getMonth() == measurements.get(index).getDate().getMonth()) {

                System.out.println("---------------------------------------------------");
                System.out.println(measurements.get(index).getDate().getDayOfMonth()+". "+
                        measurements.get(index).getDate().getMonth()+". "+
                        measurements.get(index).getDate().getYear()+". ");

                System.out.println("Uređaj: "+ measurements.get(index).getDevice().getName());
                System.out.println("Vrsta mjerenja: "+measurements.get(index).getMeasurementType().name());
                System.out.println("Iznos: "+measurements.get(index).getValue());
                System.out.println("Mjerna jedinica: "+measurements.get(index).getMeasurementUnit());
                System.out.println("Mjerio: "+measurements.get(index).getUser().getFirstName()+
                        " "+measurements.get(index).getUser().getLastName());
                System.out.println("---------------------------------------------------\n");

                found = true;
            }
            index++;

        } while (index < measurements.size());


        if (!found) {
            System.out.println("Mjerenje za datum nije pronađeno.\n");
            logger.trace("U metodi searchMeasurementByDate(), navedeni datum nije pronađen, ali se metoda izvršila.\n");
        }
    }


    public static void findHighestPowerRatingDevice(List<Device> devices) {
        Device highest = devices.getFirst();

        if (highest != null && !devices.isEmpty()) {

            for (Device device : devices) {
                if (highest.getPowerRating() < device.getPowerRating()) {
                    highest = device;
                }
            }
            System.out.println("Uređaj s najvećim power ratingom je: "+highest.getName()+" ("+highest.getDeviceType().getTypeName()+") s power ratingom: "+highest.getPowerRating()+"\n");
        }
        else System.out.println("Nema uređaja za ispisati!\n");
    }

    public static Optional<Device> findHighestPowerRatingDeviceOptional(List<Device> allDevices) {
        return allDevices.stream()
                .max(Comparator.comparingInt(Device::getPowerRating));
    }

    public static void findLowestMeasurement(List<Measurement> measurements) {
        Measurement lowest = measurements.getFirst();

        if (lowest != null && !measurements.isEmpty()) {

            for(Measurement measurement : measurements) {

                if (lowest.getValue().compareTo(measurement.getValue()) > 0)
                    lowest = measurement;
            }

            System.out.println("NAJMANJE MJERENJE JE:");
            System.out.println("---------------------------------------------------");
            System.out.println(lowest.getDate().getDayOfMonth()+". "+
                    lowest.getDate().getMonth()+". "+
                    lowest.getDate().getYear()+". ");

            System.out.println("Uređaj: "+ lowest.getDevice().getName());
            System.out.println("Vrsta mjerenja: "+lowest.getMeasurementType().name());
            System.out.println("Iznos: "+lowest.getValue());
            System.out.println("Mjerna jedinica: "+lowest.getMeasurementUnit());
            System.out.println("Mjerio: "+lowest.getUser().getFirstName()+
                    " "+lowest.getUser().getLastName());
            System.out.println("---------------------------------------------------\n");


        }
        else {
            System.out.println("Nema mjerenja za ispisati!\n");
            logger.error("Lista s mjerenjima u metodi findLowestMeasurement je prazna ili null!");
        }
    }

    public static Optional<Measurement> findLowestMeasurementOptional(List<Measurement> measurements) {
        return measurements.stream()
                .min(Comparator.comparing(Measurement::getValue));
    }

    private static void minMeasurementPrintout(Optional<Measurement> minMeasurement) {
        if (minMeasurement.isPresent()) {
            Measurement lowest = minMeasurement.get();
            System.out.println("NAJMANJE MJERENJE JE:");
            System.out.println("---------------------------------------------------");
            System.out.printf("%d. %s. %d.%n",
                    lowest.getDate().getDayOfMonth(),
                    lowest.getDate().getMonth(),
                    lowest.getDate().getYear());

            System.out.println("Uređaj: " + lowest.getDevice().getName());
            System.out.println("Vrsta mjerenja: " + lowest.getMeasurementType().name());
            System.out.println("Iznos: " + lowest.getValue());
            System.out.println("Mjerna jedinica: " + lowest.getMeasurementUnit());
            System.out.printf("Mjerio: %s %s%n",
                    lowest.getUser().getFirstName(),
                    lowest.getUser().getLastName());
            System.out.println("---------------------------------------------------\n");
        } else {
            System.out.println("Nema mjerenja za ispisati!\n");
            logger.error("Lista s mjerenjima u metodi findLowestMeasurement je prazna ili null!");
        }
    }


}
