package hr.javafx.energycms.entities;

import hr.javafx.energycms.entities.enums.DeviceType;


public class SmartDevice extends Device implements RemoteControllable {

    private String operatingSystem;
    private String ipAddress;
    private int connectivityStrength;


    public SmartDevice () {}

    public SmartDevice(String name, DeviceType smartDeviceType, Integer powerRating, String operatingSystem, String ipAddress, int connectivityStrength, boolean isOn) {
        super(name, smartDeviceType, powerRating, isOn);

        this.operatingSystem = operatingSystem;
        this.ipAddress = ipAddress;
        this.connectivityStrength = connectivityStrength;
    }

    @Override
    public void updateFirmware() {
        System.out.println("Updated Test!");
    }

    @Override
    public boolean checkConnectivity() {
        System.out.println("Test");
        return false;
    }


    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getConnectivityStrength() {
        return connectivityStrength;
    }

    public void setConnectivityStrength(int connectivityStrength) {
        this.connectivityStrength = connectivityStrength;
    }
}
