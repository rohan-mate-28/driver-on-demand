package com.driverondemand.filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

import com.driverondemand.model.User;

@WebFilter(urlPatterns = {
    "/customer/*",
    "/driver/*",
    "/admin/*"
})
public class RoleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // no initialization needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("loggedUser") == null) {
            res.setContentType("application/json");
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write(
                "{ \"success\": false, \"message\": \"Login required\" }"
            );
            return;
        }

        User user = (User) session.getAttribute("loggedUser");
        String role = user.getRole();
        String path = req.getRequestURI().substring(req.getContextPath().length());
        System.out.println("URI = " + path);
        System.out.println("ROLE = " + role);
        boolean allowed = false;

        if (path.startsWith("/customer/") && role.equals("CUSTOMER")) allowed = true;
        if (path.startsWith("/driver/") && role.equals("DRIVER")) allowed = true;
        if (path.startsWith("/admin/") && role.equals("ADMIN")) allowed = true;

        if (!allowed) {
            res.setContentType("application/json");
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            res.getWriter().write(
                "{ \"success\": false, \"message\": \"Access Denied\" }"
            );
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // cleanup if needed
    }
}
