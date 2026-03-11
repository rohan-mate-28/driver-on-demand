package com.driverondemand.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.driverondemand.dao.RideDAO;
import com.driverondemand.dao.RideDAOImpl;
import com.driverondemand.model.Driver;
import com.driverondemand.model.Ride;
import com.driverondemand.model.User;


@WebServlet("/driver/rides")
public class DriverRidesServlet extends HttpServlet {

    private RideDAO rideDAO = new RideDAOImpl();

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType("application/json");

        HttpSession session = req.getSession(false);
        if (session == null) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !"DRIVER".equals(user.getRole())) {
            res.setStatus(403);
            return;
        }

        List<Ride> rides = rideDAO.getAssignedRides(user.getId());

        // simple JSON
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < rides.size(); i++) {
            Ride r = rides.get(i);
            json.append("{")
                .append("\"ride_id\":").append(r.getRideId()).append(",")
                .append("\"pickup\":\"").append(r.getPickupLocation()).append("\",")
                .append("\"drop\":\"").append(r.getDropLocation()).append("\",")
                .append("\"status\":\"").append(r.getStatus()).append("\"")
                .append("}");
            if (i < rides.size() - 1) json.append(",");
        }
        json.append("]");

        res.getWriter().write(json.toString());
    }
}
