package com.driverondemand.dao;

import java.util.List;

import com.driverondemand.model.User;
import com.driverondemand.model.Driver;
import com.driverondemand.model.Ride;

public interface UserDAO {

    boolean registerUser(User user);

    User loginUser(String email, String password);
    
    boolean applyForDriver(int userId);
    
    List<User> getPendingDrivers();
    boolean approveDriver(int userId);
    boolean rejectDriver(int userId);
    

 }
