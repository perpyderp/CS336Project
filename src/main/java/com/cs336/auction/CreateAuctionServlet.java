package com.cs336.auction;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;

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
			//float hiddenMinIncrement = Float.parseFloat(request.getParameter("hidden_min_Increment"));
			String description = request.getParameter("description");
			Date startDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(startTime);
			Date closeDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(startTime);
			
			int aucID = database.countDB("SELECT LAST_INSERT_ID();");
			
			String insert = "INSERT INTO auction(description, start_time, close_time, auctionID, initial_price)" + "VALUES(?, ?, ?, ?, ?)";
			
			PreparedStatement ps = conn.prepareStatement(insert);
			ps.setString(1, description);
			ps.setDate(2, new java.sql.Date(startDate.getTime()));
			ps.setDate(3, new java.sql.Date(closeDate.getTime()));
			ps.setInt(4, aucID);
			ps.setFloat(5, initialPrice);
			
			ps.executeUpdate();
			
			conn.close();
			
			response.sendRedirect("Account.jsp");
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
	}

}
