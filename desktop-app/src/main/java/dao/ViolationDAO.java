package dao;

import model.ViolationRecord;
import util.DBConnection;

import java.sql.*;
import java.util.*;

public class ViolationDAO {

    public void saveViolation(ViolationRecord v) {
        String query = "INSERT INTO violations(vehicle_id, speed, zone, fine) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, v.vehicleId);
            ps.setDouble(2, v.speed);
            ps.setString(3, v.zone);
            ps.setInt(4, v.fine);

            ps.executeUpdate();
            System.out.println("✔ Saved to DB");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔥 ADD THIS METHOD
    public List<ViolationRecord> getAllViolations() {

        List<ViolationRecord> list = new ArrayList<>();

        String query = "SELECT * FROM violations";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                ViolationRecord v = new ViolationRecord(
                        rs.getString("vehicle_id"),
                        rs.getDouble("speed"),
                        rs.getString("zone"),
                        rs.getInt("fine")
                );
                list.add(v);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}