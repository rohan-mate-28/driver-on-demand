package com.driverondemand.controller;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.driverondemand.dao.RideDAO;
import com.driverondemand.dao.RideDAOImpl;
import com.driverondemand.model.Driver;
import com.driverondemand.model.User;

@WebServlet("/driver/ride/accept")
public class AcceptRideServlet extends HttpServlet {

    private RideDAO rideDAO = new RideDAOImpl();

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType("application/json");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("{\"error\":\"Unauthorized\"}");
            return;
        }

        User driver = (User) session.getAttribute("loggedUser");
        int driverId = driver.getId();

        int rideId = Integer.parseInt(req.getParameter("rideId"));

        boolean ok = rideDAO.acceptRide(rideId, driverId);

        if (ok) {
            res.getWriter().write("{\"success\":true}");
        } else {
            res.getWriter().write("{\"error\":\"Ride already taken\"}");
        }
    }
}
