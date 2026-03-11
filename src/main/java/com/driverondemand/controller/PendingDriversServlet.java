package com.driverondemand.controller;

import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.driverondemand.dao.UserDAO;
import com.driverondemand.dao.UserDAOImpl;
import com.driverondemand.model.User;

@WebServlet("/admin/drivers/pending")
public class PendingDriversServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        UserDAO dao = new UserDAOImpl();
        List<User> drivers = dao.getPendingDrivers();

        res.setContentType("application/json");
        
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < drivers.size(); i++) {
            User u = drivers.get(i);
            json.append("{")
                .append("\"user_id\":").append(u.getId()).append(",")
                .append("\"name\":\"").append(u.getName()).append("\",")
                .append("\"email\":\"").append(u.getEmail()).append("\",")
                .append("\"phone\":\"").append(u.getPhone()).append("\"")
                .append("}");
            if (i < drivers.size() - 1) json.append(",");
        }
        json.append("]");

        res.getWriter().write(json.toString());

    }
}
