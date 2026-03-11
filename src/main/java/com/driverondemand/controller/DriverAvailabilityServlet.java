package com.driverondemand.controller;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.driverondemand.dao.DriverDAO;
import com.driverondemand.dao.DriverDAOImpl;
import com.driverondemand.model.User;

@WebServlet("/driver/availability")
public class DriverAvailabilityServlet extends HttpServlet {

    private DriverDAO userDAO = new DriverDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType("application/json");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            res.setStatus(401);
            return;
        }

        User user = (User) session.getAttribute("loggedUser");

        if (!"DRIVER".equals(user.getRole())) {
            res.setStatus(403);
            return;
        }

        String status = req.getParameter("status"); // ONLINE / OFFLINE

        boolean updated = userDAO.updateAvailability(user.getId(), status);

        res.getWriter().write(
            updated ? "{\"success\":true}" : "{\"success\":false}"
        );
    }
}
