package com.driverondemand.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.driverondemand.dao.UserDAO;
import com.driverondemand.dao.UserDAOImpl;
import com.driverondemand.model.User;

@WebServlet("/admin/driver/approve")
public class ApproveDriverServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));

        UserDAO dao = new UserDAOImpl();
        boolean success = dao.approveDriver(userId);

        res.setContentType("application/json");
        if (success) {
            res.getWriter().write("{\\\"success\\\":true,\\\"message\\\":\\\"Driver approved. Please login again.\\\"}");
        } else {
            res.setStatus(500);
            res.getWriter().write("{\"success\":false}");
        }
    }
}
