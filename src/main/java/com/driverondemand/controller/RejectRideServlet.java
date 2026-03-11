package com.driverondemand.controller;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.driverondemand.dao.RideDAO;
import com.driverondemand.dao.RideDAOImpl;
import com.driverondemand.model.Driver;
import com.driverondemand.model.User;
@WebServlet("/driver/ride/reject")
public class RejectRideServlet extends HttpServlet {

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

        if (!"DRIVER".equals(driver.getRole())) {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            res.getWriter().write("{\"error\":\"Access denied\"}");
            return;
        }

        int rideId = Integer.parseInt(req.getParameter("rideId"));

        boolean ok = rideDAO.rejectRide(rideId, driver.getId());

        res.getWriter().write(ok
                ? "{\"success\":true}"
                : "{\"error\":\"Cannot reject ride\"}");
    }
}
