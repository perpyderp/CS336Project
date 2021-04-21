package com.cs336.auction;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cs336.dbapp.ApplicationDB;

@WebServlet("/createAuction")
public class CreateAuctionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");   
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			ApplicationDB database = new ApplicationDB();
			Connection conn = database.getConnection();
			Statement stm = conn.createStatement();
			
			String startTime = request.getParameter("starttime");
			String closeTime = request.getParameter("closetime");
			float initialPrice = Float.parseFloat(request.getParameter("initialPrice"));
			float minIncrement = Float.parseFloat(request.getParameter("min_Increment"));
			System.out.println(request.getParameter("userID"));
			int userID = Integer.parseInt(request.getParameter("userID"));
			//float hiddenMinIncrement = Float.parseFloat(request.getParameter("hidden_min_Increment"));
			String description = request.getParameter("description");
			Date startDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(startTime);
			Date closeDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(startTime);
			Date todayDate = new Date();
			
			if(checkBeforeToday(startTime, closeTime)) {
				HttpSession session = request.getSession();
				session.setAttribute("ERROR", "Invalid date! Start/Close date is before today's date");
				response.sendRedirect("CreateAuction.jsp");
			}
			String insert = "INSERT INTO auction(description, start_time, close_time, initial_price, userID)" + "VALUES(?, ?, ?, ?, ?)";
			
			PreparedStatement ps = conn.prepareStatement(insert);
			ps.setString(1, description);
			ps.setString(2, startTime);
			ps.setString(3, closeTime);
			ps.setFloat(4, initialPrice);
			ps.setInt(5, userID);
			
			ps.executeUpdate();
			
			conn.close();
			
			System.out.println("Auction created successfully!");
		}
		catch (ParseException parseException) {
			System.out.println("ERROR: Unable to parse start/close time");
			HttpSession session = request.getSession();
			session.setAttribute("ERROR", "Invalid date! Please enter a valid start/close time");
			response.sendRedirect("CreateAuction.jsp");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ApplicationDB database = new ApplicationDB();
			Connection conn = database.getConnection();
			Statement stm = conn.createStatement();
			
			String startTime = request.getParameter("starttime");
			String closeTime = request.getParameter("closetime");
			Date startDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(startTime);
			Date closeDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(closeTime);
			System.out.println("Start Date: " + startDate);
			System.out.println("Close Date: " + closeDate);
			long diff = closeDate.getTime() - startDate.getTime();
			long diffHours = diff / (60 * 60 * 1000);
			long days = diffHours/24;
			String duration = "Days: " + days;
			
			String itemName = request.getParameter("itemName");
			String category = request.getParameter("subcat");
			String description = request.getParameter("description");
			String userID = request.getParameter("userID");
			
			String insert = "INSERT INTO item(duration, seller, itemName, category, description)" + "VALUES(?, ?, ?, ?, ?)";
			
			PreparedStatement ps = conn.prepareStatement(insert);
			ps.setString(1, duration);
			ps.setString(2, userID);
			ps.setString(3, itemName);
			ps.setString(4, category);
			ps.setString(5, description);
			
			ps.executeUpdate();
			
			conn.close();
			
			response.sendRedirect("Account.jsp");
			System.out.println("Item created successfully!");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean checkBeforeToday(String startTime, String closeTime) throws ParseException {
		Date startDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(startTime);
		Date closeDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(startTime);
		Date todayDate = new Date();
		if(startDate.before(todayDate) || closeDate.before(todayDate)) {
			return true;
		}
		return false;
	}

}
