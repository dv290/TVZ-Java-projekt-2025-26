package hr.javafx.energycms.utils;

import hr.javafx.energycms.exceptions.InvalidDataInputException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static hr.javafx.energycms.app.MainConsoleLauncher.logger;

public class Inputs {
    private Inputs() {
    }

    public static LocalDate getSafeDateInput(Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");

        while (true) {
            System.out.print("Unesi datum mjerenja (d.m.yyyy): ");
            String input = scanner.nextLine();

            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException _) {
                try {
                    throw new InvalidDataInputException();
                } catch (InvalidDataInputException _) {
                    logger.warn("Neispravan unos datuma: {}", input);
                    System.out.println("Pogrešan format! Pokušajte ponovno!");
                }
            }
        }
    }
}
