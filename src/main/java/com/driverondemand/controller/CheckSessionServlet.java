package com.driverondemand.controller;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.driverondemand.model.User;

@WebServlet("/check-session")
public class CheckSessionServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loggedUser") == null) {

            response.getWriter().write(
                "{ \"loggedIn\": false, \"message\": \"No active session\" }"
            );

        } else {

            User user = (User) session.getAttribute("loggedUser");

            response.getWriter().write(
                "{ \"loggedIn\": true, " +
                "\"email\": \"" + user.getEmail() + "\", " +
                "\"role\": \"" + user.getRole() + "\" }"
            );
        }
    }
}
