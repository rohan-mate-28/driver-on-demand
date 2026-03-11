package com.driverondemand.dao;

import java.sql.*;
import java.util.*;
import com.driverondemand.util.DBConnection;

public class PaymentDAOImpl {
	
	public boolean createPayment(int rideId, double amount, String method) {

	    String sql =
	    "INSERT INTO payments (ride_id, amount, payment_method, payment_status) " +
	    "VALUES (?, ?, ?, 'SUCCESS')";

	    try(Connection con = DBConnection.getConnection();
	        PreparedStatement ps = con.prepareStatement(sql)){

	        ps.setInt(1, rideId);
	        ps.setDouble(2, amount);
	        ps.setString(3, method);

	        return ps.executeUpdate() > 0;

	    }catch(Exception e){
	        e.printStackTrace();
	    }

	    return false;
	}
}
