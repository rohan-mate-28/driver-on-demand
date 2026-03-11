package com.driverondemand.dao;

import com.driverondemand.model.Driver;

public interface DriverDAO {

    boolean createDriverProfile(Driver driver);

    boolean updateAvailability(int userId, String status);

    Driver getDriverByUserId(int userId);
}
