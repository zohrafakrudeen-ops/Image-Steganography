package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:steganography.db";

    static {
        createTables();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    private static void createTables() {

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS users(
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        username TEXT UNIQUE NOT NULL,
                        password TEXT NOT NULL
                    );
                    """);

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS history(
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        username TEXT,
                        operation TEXT,
                        image_name TEXT,
                        message TEXT,
                        date_time DATETIME DEFAULT CURRENT_TIMESTAMP
                    );
                    """);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}