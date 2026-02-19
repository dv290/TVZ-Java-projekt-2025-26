package hr.javafx.energycms.generics;

import hr.javafx.energycms.entities.Device;
import hr.javafx.energycms.entities.EnergyTaxable;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import static hr.javafx.energycms.app.MainConsoleLauncher.logger;

public class GenericsUtils {
    private GenericsUtils() {}

    public static <T> void searchEntity(Scanner scanner, List<? extends T> entities,
                                        Function<T, String> propertyExtractor,
                                        String promptMessage, String errorMessage) {

        System.out.print(promptMessage);
        String input = scanner.nextLine().trim();

        System.out.println("Rezultati pretrage: ");

        List<? extends T> results = entities.stream()
                .filter(e -> propertyExtractor.apply(e).equalsIgnoreCase(input))
                .toList();

        if (results.isEmpty()) {
            System.out.println(errorMessage + " \"" + input + "\" nije pronađen.");
            logger.debug("Pretraga za '{}' nije dala rezultate.", input);
        } else {
            results.forEach(e -> System.out.println("- " + e.toString()));
        }
        System.out.println("\n");
    }




    //beskorisno
    public static <T extends Device & EnergyTaxable> void processTaxableDevice(T device) {
        System.out.println("Obrađujem porez za: " + device.getName());
        System.out.println("Iznos poreza: " + device.calculateEnvironmentalTax());
    }


    public static void printDeviceNames(List<? extends Device> devices) {
        devices.forEach(d -> System.out.println("Uređaj: " + d.getName()));
    }

    public static <T extends Device> void addDeviceToList(List<? super T> deviceList, T device) {
        deviceList.add(device);
    }


}
