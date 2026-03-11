package com.driverondemand.controller;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.driverondemand.dao.DriverDAO;
import com.driverondemand.dao.DriverDAOImpl;
import com.driverondemand.model.Driver;
import com.driverondemand.model.User;

@WebServlet("/driver/profile")
public class DriverProfileServlet extends HttpServlet {

    private DriverDAO userDAO = new DriverDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType("application/json");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            res.setStatus(401);
            res.getWriter().write("{\"error\":\"Not logged in\"}");
            return;
        }

        User sessionUser = (User) session.getAttribute("loggedUser");

        // 🔐 SECURITY CHECK
        if (!"DRIVER".equals(sessionUser.getRole())) {
            res.setStatus(403);
            res.getWriter().write("{\"error\":\"Not a driver\"}");
            return;
        }

        // Inputs
        String license = req.getParameter("licenseNumber");
        String vehicleNo = req.getParameter("vehicleNumber");
        String vehicleType = req.getParameter("vehicleType");

        Driver d = new Driver();
        d.setUserId(sessionUser.getId());
        d.setLicenseNumber(license);
        d.setVehicleNumber(vehicleNo);
        d.setVehicleType(vehicleType);

        boolean success = userDAO.createDriverProfile(d);

        if (success) {
            res.getWriter().write("{\"success\":true,\"message\":\"Driver profile created\"}");
        } else {
            res.setStatus(500);
            res.getWriter().write("{\"success\":false}");
        }
    }
}
