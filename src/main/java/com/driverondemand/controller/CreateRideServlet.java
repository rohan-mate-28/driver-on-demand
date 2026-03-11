package com.driverondemand.controller;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.driverondemand.dao.RideDAO;
import com.driverondemand.dao.RideDAOImpl;
import com.driverondemand.model.Ride;
import com.driverondemand.model.User;

@WebServlet("/customer/ride/create")
public class CreateRideServlet extends HttpServlet {

    private RideDAO rideDAO = new RideDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType("application/json");

        HttpSession session = req.getSession(false);
        System.out.println("Session = " + session);

        if (session == null || session.getAttribute("loggedUser") == null) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("{\"error\":\"Unauthorized\"}");
            return;
        }

        // ✅ Correct way
        User user = (User) session.getAttribute("loggedUser");
        int customerId = user.getId();

        String pickup = req.getParameter("pickupLocation");
        String drop   = req.getParameter("dropLocation");

        if (pickup == null || drop == null) {
            res.getWriter().write("{\"error\":\"Missing fields\"}");
            return;
        }

        Ride ride = new Ride();
        ride.setCustomerId(customerId);
        ride.setPickupLocation(pickup);
        ride.setDropLocation(drop);

        boolean success = rideDAO.createRide(ride);

        res.getWriter().write(
            success ? "{\"success\":true}" : "{\"success\":false}"
        );
    }
}
