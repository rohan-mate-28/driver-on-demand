package com.driverondemand.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.driverondemand.dao.PaymentDAO;
import com.driverondemand.dao.PaymentDAOImpl;
/**
 * Servlet implementation class Paymentserverlet
 */
@WebServlet("/Paymentserverlet")
public class Paymentserverlet extends HttpServlet {
 
	@WebServlet("/payment/pay")
	public class PaymentServlet extends HttpServlet {

	    private PaymentDAOImpl paymentDAO = new PaymentDAOImpl();

	    protected void doPost(HttpServletRequest req, HttpServletResponse res)
	            throws IOException {

	        int rideId = Integer.parseInt(req.getParameter("rideId"));
	        double amount = Double.parseDouble(req.getParameter("amount"));
	        String method = req.getParameter("paymentMethod");

	        boolean ok = paymentDAO.createPayment(rideId, amount, method);

	        res.setContentType("application/json");

	        if(ok){
	            res.getWriter().write("{\"payment\":\"success\"}");
	        } else {
	            res.getWriter().write("{\"payment\":\"failed\"}");
	        }
	    }
	}

}
