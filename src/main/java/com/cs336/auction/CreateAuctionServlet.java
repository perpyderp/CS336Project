package com.cs336.auction;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
			HttpSession session = request.getSession();
			Connection conn = database.getConnection();
			
			String startTime = request.getParameter("starttime");
			String closeTime = request.getParameter("closetime");
			float initialPrice = Float.parseFloat(request.getParameter("initialPrice"));
			String user = request.getParameter("username");
			float hiddenMinPrice = Float.parseFloat(request.getParameter("hidden_min_price"));
			String itemName = request.getParameter("itemName");
			String category = request.getParameter("subcat");
			String description = request.getParameter("description");
			Date startDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(startTime);
			Date closeDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(closeTime);
			Timestamp start = new Timestamp(startDate.getTime());
			Timestamp close = new Timestamp(closeDate.getTime());
//			Date todayDate = new Date();
			
			if(checkBeforeToday(startTime, closeTime)) {
				session.setAttribute("ERROR", "Invalid date! Start/Close date is before today's date");
				response.sendRedirect("CreateAuction.jsp");
				return;
			}
			else if(hiddenMinPrice<initialPrice && hiddenMinPrice != 0 || hiddenMinPrice < 0) {
				session.setAttribute("ERROR", "Invalid minimum buyout price! Should be greater than the initial price");
				response.sendRedirect("CreateAuction.jsp");
				return;
			}
			else if(initialPrice < 0) {
				session.setAttribute("ERROR", "Invalid initial price! Negative prices are not allowed");
				response.sendRedirect("CreateAuction.jsp");
				return;
			}
			String insert = "INSERT INTO auction(description, start_time, close_time, initial_price, seller, item_name, category, hidden_min_price)" + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement insertAuctionPS = conn.prepareStatement(insert);
			insertAuctionPS.setString(1, description);
			insertAuctionPS.setTimestamp(2, start);
			insertAuctionPS.setTimestamp(3, close);
			insertAuctionPS.setFloat(4, initialPrice);
			insertAuctionPS.setString(5, user);
			insertAuctionPS.setString(6, itemName);
			insertAuctionPS.setString(7, category);
			insertAuctionPS.setFloat(8, hiddenMinPrice);
			insertAuctionPS.executeUpdate();
			
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
		catch (NumberFormatException wrongFormat) {
			System.out.println("ERROR: Wrong number format!");
			HttpSession session = request.getSession();
			session.setAttribute("ERROR", "Invalid price(s)! Please enter a valid price");
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
