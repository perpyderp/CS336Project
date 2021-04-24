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
			String user = request.getParameter("username");
			//float hiddenMinIncrement = Float.parseFloat(request.getParameter("hidden_min_Increment"));
			String itemName = request.getParameter("itemName");
			String category = request.getParameter("subcat");
			System.out.println(category);
			String description = request.getParameter("description");
			Date startDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(startTime);
			Date closeDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(startTime);
			Date todayDate = new Date();
			
			if(checkBeforeToday(startTime, closeTime)) {
				HttpSession session = request.getSession();
				session.setAttribute("ERROR", "Invalid date! Start/Close date is before today's date");
				response.sendRedirect("CreateAuction.jsp");
			}
			String insert = "INSERT INTO auction(description, start_time, close_time, initial_price, seller, item_name, category)" + "VALUES(?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement ps = conn.prepareStatement(insert);
			ps.setString(1, description);
			ps.setString(2, startTime);
			ps.setString(3, closeTime);
			ps.setFloat(4, initialPrice);
			ps.setString(5, user);
			ps.setString(6, itemName);
			ps.setString(7, category);
			
			ps.executeUpdate();
			
			conn.close();
			
			System.out.println("Auction created successfully!");
			response.sendRedirect("Home.jsp");
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
