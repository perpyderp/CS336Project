package com.cs336.login.web;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cs336.dbapp.ApplicationDB;
import com.cs336.user.User;


/**
 * Servlet implementation class DeleteAccountServlet
 */
@WebServlet("/deleteAccount")
public class DeleteAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteAccountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			HttpSession session = request.getSession();
			User currentUser = (User) session.getAttribute("currentUser");
			int userID = currentUser.getUserID();
			String username = currentUser.getUsername();
			ApplicationDB database = new ApplicationDB();
			Connection conn = database.getConnection();
			PreparedStatement deleteAccount = conn.prepareStatement("DELETE FROM users WHERE userID=?");
			deleteAccount.setInt(1, userID);
			int deleteResult = deleteAccount.executeUpdate();
			if(deleteResult < 1) {
				session.setAttribute("ACCOUNT_DELETE", "FAILED TO DELETE ACCOUNT");
				response.sendRedirect("Account.jsp");
				return;
			}
			PreparedStatement deleteAccountAuctions = conn.prepareStatement("DELETE FROM Auction WHERE seller=?");
			deleteAccountAuctions.setString(1, username);
			int deleteAuctionResult = deleteAccountAuctions.executeUpdate();
			if(deleteAuctionResult < 1) {
				session.setAttribute("AUCTION_DELETE", "NO AUCTIONS FOUND FOR DELETED USER");
			}
			session.removeAttribute("currentUser");
			session.setAttribute("SUCCESS", "Successfully deleted your account");
			response.sendRedirect("Home.jsp");
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
