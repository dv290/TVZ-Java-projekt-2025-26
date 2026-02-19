package hr.javafx.energycms.repository;

import hr.javafx.energycms.entities.ContactInfo;
import hr.javafx.energycms.entities.User;
import hr.javafx.energycms.exceptions.DatabaseException;
import hr.javafx.energycms.utils.DatabaseUtils;
import hr.javafx.energycms.utils.DialogUtils;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {

    public static List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = """
    SELECT ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, ADDRESS
    FROM USERS
    """;

        try (Connection conn = DatabaseUtils.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                User user = mapRowToUser(rs);
                users.add(user);
            }
        }
        catch(IOException | SQLException _) {
            DialogUtils.showError("Greška", "Greška kod spajanja na bazu podataka", "Nije moguće se spojiti na USERS tablicu");
        }
        return users;
    }

    private static User mapRowToUser(ResultSet rs) throws SQLException {
        User user = new User(
                rs.getString("FIRST_NAME"),
                rs.getString("LAST_NAME"),

                new ContactInfo(
                        rs.getString("EMAIL"),
                        rs.getString("PHONE_NUMBER"),
                        rs.getString("ADDRESS")),

                new ArrayList<>());
        user.setId(rs.getLong("ID"));

        return user;
    }




    public static boolean saveUser(User user) {
        String sql = """
    INSERT INTO USERS (FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, ADDRESS)
    VALUES (?, ?, ?, ?, ?)
    """;

        try (Connection conn = DatabaseUtils.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getContact().email());
            pstmt.setString(4, user.getContact().phoneNumber());
            pstmt.setString(5, user.getContact().address());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getLong(1));
                }
            }
            return true;
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            DialogUtils.showError("Greška", "Spremanje nije uspjelo", e.getMessage());
            return false;
        }
    }

    public static void deleteUser(Long id) {
        String deleteMeasurements = "DELETE FROM MEASUREMENTS WHERE USER_ID = ?";
        String deleteUser = "DELETE FROM USERS WHERE ID = ?";

        try (Connection conn = DatabaseUtils.connectToDatabase()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps1 = conn.prepareStatement(deleteMeasurements);
                 PreparedStatement ps2 = conn.prepareStatement(deleteUser)) {

                ps1.setLong(1, id);
                ps1.executeUpdate();

                ps2.setLong(1, id);
                ps2.executeUpdate();

                conn.commit();
            } catch (SQLException _) {
                conn.rollback();
                throw new DatabaseException();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


    public static Optional<User> findLastUser() {
        String sql = "SELECT * FROM USERS ORDER BY ID DESC LIMIT 1";
        try (Connection conn = DatabaseUtils.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("ID"));
                user.setFirstName(rs.getString("FIRST_NAME"));
                user.setLastName(rs.getString("LAST_NAME"));
                return Optional.of(user);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static void updateUser(User user) {
        String sql = "UPDATE USERS SET FIRST_NAME = ?, LAST_NAME = ?, EMAIL = ?, PHONE_NUMBER = ?, ADDRESS = ? WHERE ID = ?";
        try (Connection conn = DatabaseUtils.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getContact().email());
            pstmt.setString(4, user.getContact().phoneNumber());
            pstmt.setString(5, user.getContact().address());
            pstmt.setLong(6, user.getId());

            pstmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }




    public static boolean authenticate(String username, String password) {
        String sql = "SELECT * FROM APP_USERS WHERE USERNAME = ? AND PASSWORD = ?";
        try (Connection conn = DatabaseUtils.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Vraća true ako je pronađen redak s tim podacima
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }




    private UserRepository() {}
}
