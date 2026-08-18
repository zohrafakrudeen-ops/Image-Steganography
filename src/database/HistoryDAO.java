package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class HistoryDAO {

    public void addHistory(String username,
                           String operation,
                           String imageName) {

        String sql =
                "INSERT INTO history(username,operation,image_name) VALUES(?,?,?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, operation);
            ps.setString(3, imageName);

            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public ArrayList<String[]> getHistory() {

        ArrayList<String[]> history = new ArrayList<>();

        String sql =
                "SELECT username, operation, image_name, date_time " +
                "FROM history WHERE username=? ORDER BY id DESC";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, Session.getCurrentUser());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                history.add(new String[]{

                        rs.getString("username"),
                        rs.getString("operation"),
                        rs.getString("image_name"),
                        rs.getString("date_time")

                });

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return history;

    }

    public int getOperationCount(String operation) {

        String sql =
                "SELECT COUNT(*) FROM history WHERE username=? AND operation=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, Session.getCurrentUser());
            ps.setString(2, operation);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getInt(1);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return 0;

    }

    public int getTotalOperations() {

        String sql =
                "SELECT COUNT(*) FROM history WHERE username=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, Session.getCurrentUser());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getInt(1);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return 0;

    }

}