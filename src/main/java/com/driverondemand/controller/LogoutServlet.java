package com.driverondemand.controller;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");

        HttpSession session = request.getSession(false); // 🔴 don't create new

        if (session != null) {
            session.invalidate(); // 🔥 destroy session
        }

        response.getWriter().write(
            "{ \"success\": true, \"message\": \"Logged out successfully\" }"
        );
    }
}
