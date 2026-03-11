package com.driverondemand.dao;

import java.sql.*;
import com.driverondemand.model.Driver;
import com.driverondemand.util.DBConnection;

public class DriverDAOImpl implements DriverDAO {

    @Override
    public boolean createDriverProfile(Driver d) {
        String sql = "INSERT INTO drivers (user_id, license_number, vehicle_number, vehicle_type, availability) VALUES (?,?,?,?, 'OFFLINE')";
        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, d.getUserId());
            ps.setString(2, d.getLicenseNumber());
            ps.setString(3, d.getVehicleNumber());
            ps.setString(4, d.getVehicleType());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateAvailability(int userId, String status) {
        String sql = "UPDATE drivers SET availability=? WHERE user_id=?";
        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, status);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Driver getDriverByUserId(int userId) {
        String sql = "SELECT * FROM drivers WHERE user_id=?";
        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Driver d = new Driver();
                d.setDriverId(rs.getInt("driver_id"));
                d.setUserId(userId);
                d.setAvailability(rs.getString("availability"));
                return d;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
