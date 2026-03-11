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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserDAO userDAO = new UserDAOImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userDAO.loginUser(email, password);

        response.setContentType("application/json");

        if (user != null) {

            // 🔒 Create session
            HttpSession session = request.getSession();
            session.setAttribute("loggedUser", user);
            session.setAttribute("role", user.getRole());
            session.setMaxInactiveInterval(30 * 60);

            // ✅ JSON response (NO password)
            String json =
                "{ \"status\": \"SUCCESS\", " +
                "\"user\": { " +
                "\"id\": " + user.getId() + ", " +
                "\"name\": \"" + user.getName() + "\", " +
                "\"email\": \"" + user.getEmail() + "\", " +
                "\"phone\": \"" + user.getPhone() + "\", " +
                "\"role\": \"" + user.getRole() + "\" " +
                "} }";

            response.getWriter().write(json);

        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(
                "{ \"status\": \"FAILED\", \"message\": \"Invalid email or password\" }"
            );
        }
    }
     

}
