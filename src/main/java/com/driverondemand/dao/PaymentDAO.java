package com.driverondemand.dao;

public interface PaymentDAO {
	
	boolean createPayment(int rideId, double amount, String method);
}
