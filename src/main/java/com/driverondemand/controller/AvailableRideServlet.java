package com.driverondemand.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.driverondemand.dao.RideDAO;
import com.driverondemand.dao.RideDAOImpl;
import com.driverondemand.model.Ride;
import com.google.gson.Gson;

@WebServlet("/driver/ride/available")
public class AvailableRideServlet extends HttpServlet {

    private RideDAO rideDAO = new RideDAOImpl();

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType("application/json");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("{\"error\":\"Unauthorized\"}");
            return;
        }

        List<Ride> rides = rideDAO.getAvailableRides();

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < rides.size(); i++) {
            Ride r = rides.get(i);
            json.append("{")
                .append("\"ride_id\":").append(r.getRideId()).append(",")
                .append("\"pickup_location\":\"").append(r.getPickupLocation()).append("\",")
                .append("\"drop_location\":\"").append(r.getDropLocation()).append("\",")
                .append("\"status\":\"").append(r.getStatus()).append("\"")
                .append("}");
            if (i < rides.size() - 1) json.append(",");
        }
        json.append("]");

        res.getWriter().write(json.toString());
    }
}
