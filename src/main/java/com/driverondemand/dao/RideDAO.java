package com.driverondemand.dao;

import java.util.List;
import com.driverondemand.model.Ride;

public interface RideDAO {

    boolean createRide(Ride ride);

    List<Ride> getAssignedRides(int driverId);
    
    List<Ride> getAvailableRides();

     boolean acceptRide(int rideId, int driverId);
     
     boolean rejectRide(int rideId, int driverId);
     
 
     List<Ride> getCustomerRideHistory(int customerId);
     boolean completeRide(int rideId, int driverId, double distance);
}
