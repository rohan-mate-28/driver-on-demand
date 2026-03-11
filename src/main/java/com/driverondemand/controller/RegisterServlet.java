package com.driverondemand.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.driverondemand.dao.UserDAO;
import com.driverondemand.dao.UserDAOImpl;
import com.driverondemand.model.User;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Read form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
 
        // Create User object
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        user.setRole("CUSTOMER");

        // Call DAO
        boolean result = userDAO.registerUser(user);

        response.setContentType("text/plain");

        if (result) {
            response.getWriter().write("Registration Successful");
        } else {
            response.getWriter().write("Registration Failed");
        }
    }
}
