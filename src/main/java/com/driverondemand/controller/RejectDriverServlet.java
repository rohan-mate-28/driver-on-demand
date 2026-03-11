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

@WebServlet("/admin/driver/reject")
public class RejectDriverServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));

        UserDAO dao = new UserDAOImpl();
        boolean success = dao.rejectDriver(userId);

        res.setContentType("application/json");
        res.getWriter().write(
            success ? "{\"success\":true}" : "{\"success\":false}"
        );
    }
}
