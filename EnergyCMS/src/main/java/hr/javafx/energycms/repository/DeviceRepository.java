package hr.javafx.energycms.repository;

import hr.javafx.energycms.entities.Device;
import hr.javafx.energycms.entities.SmartDevice;
import hr.javafx.energycms.entities.StandardDevice;
import hr.javafx.energycms.entities.enums.DeviceType;
import hr.javafx.energycms.utils.DatabaseUtils;
import hr.javafx.energycms.utils.DialogUtils;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeviceRepository {

    public static List<Device> findAllDevices() {
        List<Device> devices = new ArrayList<>();
        String sql = "SELECT * FROM DEVICES";

        try (Connection conn = DatabaseUtils.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String dtype = rs.getString("DTYPE");
                Device device;

                if ("SMART".equals(dtype)) {
                    device = new SmartDevice(
                            rs.getString("NAME"),
                            DeviceType.valueOf(rs.getString("DEVICE_TYPE")),
                            rs.getInt("POWER_RATING"),
                            rs.getString("IP_ADDRESS"),
                            rs.getString("OPERATING_SYSTEM"),
                            rs.getInt("CONNECTIVITY_STRENGTH"),
                            rs.getBoolean("IS_ON")
                    );
                } else {
                    device = new StandardDevice(
                            rs.getString("NAME"),
                            DeviceType.valueOf(rs.getString("DEVICE_TYPE")),
                            rs.getInt("POWER_RATING"),
                            rs.getInt("NUMBER_OF_KNOBS"),
                            rs.getString("ENERGY_CLASS").charAt(0),
                            rs.getBoolean("IS_ON")
                    );
                }
                device.setId(rs.getLong("ID"));
                devices.add(device);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return devices;
    }


    public static void saveSmartDevice(SmartDevice smart) {
        String sql = """
            INSERT INTO DEVICES (
                NAME, DEVICE_TYPE, POWER_RATING, IS_ON, DTYPE,
                OPERATING_SYSTEM, IP_ADDRESS, CONNECTIVITY_STRENGTH
            ) VALUES (?, ?, ?, ?, 'SMART', ?, ?, ?)
            """;

        try (Connection conn = DatabaseUtils.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, smart.getName());
            pstmt.setString(2, smart.getDeviceType().name());
            pstmt.setInt(3, smart.getPowerRating());
            pstmt.setBoolean(4, smart.isOn());
            pstmt.setString(5, smart.getOperatingSystem());
            pstmt.setString(6, smart.getIpAddress());
            pstmt.setInt(7, smart.getConnectivityStrength());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) smart.setId(rs.getLong(1));
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            DialogUtils.showError("Greška", "Spremanje nije uspjelo", e.getMessage());
        }
    }

    public static void saveStandardDevice(StandardDevice standard) {
        String sql = """
            INSERT INTO DEVICES (
                NAME, DEVICE_TYPE, POWER_RATING, IS_ON, DTYPE,
                NUMBER_OF_KNOBS, ENERGY_CLASS
            ) VALUES (?, ?, ?, ?, 'STANDARD', ?, ?)
            """;

        try (Connection conn = DatabaseUtils.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, standard.getName());
            pstmt.setString(2, standard.getDeviceType().name());
            pstmt.setInt(3, standard.getPowerRating());
            pstmt.setBoolean(4, standard.isOn());
            pstmt.setInt(5, standard.getNumberOfControlKnobs());
            pstmt.setString(6, String.valueOf(standard.getEnergyEfficiencyClass()));

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) standard.setId(rs.getLong(1));
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            DialogUtils.showError("Greška", "Spremanje nije uspjelo", e.getMessage());
        }
    }


    public static void deleteDevice(Long id) {
        String deleteMeasurements = "DELETE FROM MEASUREMENTS WHERE DEVICE_ID = ?";
        String deleteDevice = "DELETE FROM DEVICES WHERE ID = ?";

        try (Connection conn = DatabaseUtils.connectToDatabase()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps1 = conn.prepareStatement(deleteMeasurements);
                 PreparedStatement ps2 = conn.prepareStatement(deleteDevice)) {

                ps1.setLong(1, id);
                ps1.executeUpdate();

                ps2.setLong(1, id);
                ps2.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


    public static void updateSmartDevice(SmartDevice device) {
        String sql = "UPDATE DEVICES SET NAME = ?, POWER_RATING = ?, IP_ADDRESS = ?, OPERATING_SYSTEM = ? WHERE ID = ?";
        try (Connection conn = DatabaseUtils.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, device.getName());
            pstmt.setInt(2, device.getPowerRating());
            pstmt.setString(3, device.getIpAddress());
            pstmt.setString(4, device.getOperatingSystem());
            pstmt.setLong(5, device.getId());

            pstmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateStandardDevice(StandardDevice device) {
        String sql = "UPDATE DEVICES SET NAME = ?, POWER_RATING = ?, NUMBER_OF_KNOBS = ?, ENERGY_CLASS = ? WHERE ID = ?";

        try (Connection conn = DatabaseUtils.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, device.getName());
            pstmt.setInt(2, device.getPowerRating());
            pstmt.setInt(3, device.getNumberOfControlKnobs());

            pstmt.setString(4, String.valueOf(device.getEnergyEfficiencyClass()));

            pstmt.setLong(5, device.getId());

            pstmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }


    private DeviceRepository() {}
}
