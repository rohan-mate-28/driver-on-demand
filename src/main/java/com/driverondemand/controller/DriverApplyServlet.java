package com.driverondemand.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.driverondemand.dao.UserDAO;
import com.driverondemand.dao.UserDAOImpl;
import com.driverondemand.model.User;
@WebServlet("/customer/apply-driver")
public class DriverApplyServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("loggedUser");

        res.setContentType("application/json");

        if (!"CUSTOMER".equals(user.getRole())) {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            res.getWriter().write("{\"message\":\"Only customers can apply\"}");
            return;
        }

        boolean applied = userDAO.applyForDriver(user.getId());

        if (applied) {
            res.getWriter().write("{\"success\":true,\"message\":\"Driver application submitted\"}");
        } else {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().write("{\"message\":\"Already applied or invalid state\"}");
        }
    }
}
