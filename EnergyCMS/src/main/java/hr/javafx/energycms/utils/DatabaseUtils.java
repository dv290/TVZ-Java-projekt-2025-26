package hr.javafx.energycms.utils;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseUtils {
    private DatabaseUtils() {}

    private static final String PROPERTIES_FILE = "src/main/resources/database.properties";

    public static Connection connectToDatabase() throws SQLException, IOException {

        try (var reader = new FileReader(PROPERTIES_FILE)) {

            var properties = new Properties();
            properties.load(reader);

            var url  = properties.getProperty("url");
            var user = properties.getProperty("user");
            var pass = properties.getProperty("password");

            return DriverManager.getConnection(url, user, pass);
        }
    }



    public static void backupTable(String tableName) {
        String backupName = tableName.toUpperCase() + "_BACKUP";

        String dropSql = "DROP TABLE IF EXISTS " + backupName;
        String createSql = "CREATE TABLE " + backupName + " AS SELECT * FROM " + tableName;

        try (Connection conn = connectToDatabase();
             Statement stmt = conn.createStatement()) {

            stmt.execute(dropSql);
            stmt.execute(createSql);
            System.out.println("Backup tablice " + tableName + " uspješno kreiran.");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
