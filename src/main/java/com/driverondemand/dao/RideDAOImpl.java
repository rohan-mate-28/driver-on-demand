package com.driverondemand.dao;

import java.sql.*;
import java.util.*;
import com.driverondemand.model.Ride;
import com.driverondemand.util.DBConnection;

public class RideDAOImpl implements RideDAO {

	@Override
	public boolean createRide(Ride r) {
		String sql = "INSERT INTO rides (customer_id, pickup_location, drop_location, status) VALUES (?,?,?, 'REQUESTED')";
		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, r.getCustomerId());
			ps.setString(2, r.getPickupLocation());
			ps.setString(3, r.getDropLocation());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Ride> getAssignedRides(int driverId) {
	    List<Ride> list = new ArrayList<>();
	    String sql = "SELECT * FROM rides WHERE driver_id=? AND status='ACCEPTED'";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, driverId);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Ride r = new Ride();
	            r.setRideId(rs.getInt("ride_id"));
	            r.setPickupLocation(rs.getString("pickup_location"));
	            r.setDropLocation(rs.getString("drop_location"));
	            r.setStatus(rs.getString("status"));
	            list.add(r);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}


	public boolean acceptRide(int rideId, int driverId) {
	    String sql = "UPDATE rides SET driver_id=?, status='ACCEPTED' " +
	                 "WHERE ride_id=? AND driver_id IS NULL";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, driverId);
	        ps.setInt(2, rideId);

	        return ps.executeUpdate() > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	public List<Ride> getAvailableRides() {
	    List<Ride> list = new ArrayList<>();
	    String sql = "SELECT * FROM rides WHERE status='REQUESTED' AND driver_id IS NULL";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            Ride r = new Ride();
	            r.setRideId(rs.getInt("ride_id"));
	            r.setPickupLocation(rs.getString("pickup_location"));
	            r.setDropLocation(rs.getString("drop_location"));
	            r.setStatus(rs.getString("status"));
	            list.add(r);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}
	
	
	@Override
	public boolean rejectRide(int rideId, int driverId) {
	    String sql = "UPDATE rides SET driver_id=NULL, status='REJECTED' " +
	                 "WHERE ride_id=? AND driver_id=?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, rideId);
	        ps.setInt(2, driverId);

	        return ps.executeUpdate() > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	
	@Override
	public boolean completeRide(int rideId, int driverId) {
	    String sql =
	        "UPDATE rides SET status='COMPLETED' " +
	        "WHERE ride_id=? AND driver_id=? AND status='ACCEPTED'";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, rideId);
	        ps.setInt(2, driverId);

	        return ps.executeUpdate() > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	public List<Ride> getCustomerRideHistory(int customerId) {
	    List<Ride> list = new ArrayList<>();
	    String sql =
	        "SELECT * FROM rides WHERE customer_id=? AND status='COMPLETED'";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, customerId);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Ride r = new Ride();
	            r.setRideId(rs.getInt("ride_id"));
	            r.setPickupLocation(rs.getString("pickup_location"));
	            r.setDropLocation(rs.getString("drop_location"));
	            r.setStatus(rs.getString("status"));
	            list.add(r);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}
	
	
	@Override
	public boolean completeRide(int rideId, int driverId, double distance) {

	    double baseFare = 50;
	    double perKmRate = 10;

	    double fare = baseFare + (distance * perKmRate);

	    String sql = "UPDATE rides SET status='COMPLETED', distance_km=?, fare_amount=? " +
	                 "WHERE ride_id=? AND driver_id=? AND status='ACCEPTED'";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setDouble(1, distance);
	        ps.setDouble(2, fare);
	        ps.setInt(3, rideId);
	        ps.setInt(4, driverId);

	        return ps.executeUpdate() > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return false;
	}
 
}
