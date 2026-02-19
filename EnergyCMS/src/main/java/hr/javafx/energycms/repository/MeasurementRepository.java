package hr.javafx.energycms.repository;

import hr.javafx.energycms.entities.Device;
import hr.javafx.energycms.entities.Measurement;
import hr.javafx.energycms.entities.User;
import hr.javafx.energycms.entities.enums.MeasurementType;
import hr.javafx.energycms.utils.DatabaseUtils;
import hr.javafx.energycms.utils.DialogUtils;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MeasurementRepository {

    public static void save(Measurement measurement) {
        String sql = """
            INSERT INTO MEASUREMENTS (USER_ID, DEVICE_ID, MEASUREMENT_DATE, 
                                     MEASUREMENT_TYPE, MEASUREMENT_VALUE, UNIT)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = DatabaseUtils.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setLong(1, measurement.getUserId());
            pstmt.setLong(2, measurement.getDeviceId());
            pstmt.setDate(3, java.sql.Date.valueOf(measurement.getDate()));
            pstmt.setString(4, measurement.getMeasurementType().name());
            pstmt.setBigDecimal(5, measurement.getValue());
            pstmt.setString(6, measurement.getMeasurementUnit());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    measurement.setId(rs.getLong(1));
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            DialogUtils.showError("Greška", "Spremanje mjerenja nije uspjelo", e.getMessage());
        }
    }


    public static List<Measurement> findAllMeasurements() {
        List<Measurement> measurements = new ArrayList<>();
        String sql = "SELECT * FROM MEASUREMENTS";

        List<User> allUsers = UserRepository.findAllUsers();
        List<Device> allDevices = DeviceRepository.findAllDevices();

        try (Connection conn = DatabaseUtils.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Long userId = rs.getLong("USER_ID");
                Long deviceId = rs.getLong("DEVICE_ID");

                User user = allUsers.stream()
                        .filter(u -> u.getId().equals(userId))
                        .findFirst()
                        .orElse(null);

                Device device = allDevices.stream()
                        .filter(d -> d.getId().equals(deviceId))
                        .findFirst()
                        .orElse(null);

                Measurement m = new Measurement.Builder()
                        .user(user)
                        .device(device)
                        .date(rs.getDate("MEASUREMENT_DATE").toLocalDate())
                        .measurementType(MeasurementType.valueOf(rs.getString("MEASUREMENT_TYPE")))
                        .value(rs.getBigDecimal("MEASUREMENT_VALUE"))
                        .measurementUnit(rs.getString("UNIT"))
                        .build();

                m.setId(rs.getLong("ID"));
                measurements.add(m);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            DialogUtils.showError("Greška", "Dohvaćanje mjerenja nije uspjelo", e.getMessage());
        }
        return measurements;
    }

    public static void deleteMeasurement(Long id) {
        String sql = "DELETE FROM MEASUREMENTS WHERE ID = ?";
        try (Connection conn = DatabaseUtils.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private MeasurementRepository() {}
}
