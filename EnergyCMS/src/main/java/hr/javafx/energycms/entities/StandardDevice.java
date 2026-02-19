package hr.javafx.energycms.entities;

import hr.javafx.energycms.entities.enums.DeviceType;
import hr.javafx.energycms.exceptions.NegativeValueException;


import static hr.javafx.energycms.app.MainConsoleLauncher.logger;

public class StandardDevice extends Device {

    private int numberOfControlKnobs;
    private Character energyEfficiencyClass;


    public StandardDevice() {}

    public StandardDevice(String name, DeviceType standardDeviceType, Integer powerRating, int numberOfControlKnobs, Character energyEfficiencyClass , boolean isOn) {
        super(name, standardDeviceType, powerRating, isOn);

        this.numberOfControlKnobs = numberOfControlKnobs;
        this.energyEfficiencyClass = energyEfficiencyClass;
        if (numberOfControlKnobs < 0) {
            logger.trace("Pokušaj stavljanja negativne vrijednosti varijable numberOfControlKnobs u konstruktoru klase StandardDevice");
            throw new NegativeValueException();
        }
    }

    public int getNumberOfControlKnobs() {
        return numberOfControlKnobs;
    }

    public void setNumberOfControlKnobs(int numberOfControlKnobs) {
        if (numberOfControlKnobs < 0) {
            logger.trace("Pokušaj stavljanja negativne vrijednosti varijable numberOfControlKnobs u metodi setNumberOfControlKnobs u klasi StandardDevice");
            throw new NegativeValueException();
        }
        this.numberOfControlKnobs = numberOfControlKnobs;
    }

    public Character getEnergyEfficiencyClass() {
        return energyEfficiencyClass;
    }

    public void setEnergyEfficiencyClass(Character energyEfficiencyClass) {
        this.energyEfficiencyClass = energyEfficiencyClass;
    }
}
