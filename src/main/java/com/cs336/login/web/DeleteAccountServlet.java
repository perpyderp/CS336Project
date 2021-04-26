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


@WebServlet("/deleteAccount")
public class DeleteAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteAccountServlet() { super(); }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			User currentUser = (User) session.getAttribute("currentUser");
			int userID = currentUser.getUserID();
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
			session.removeAttribute("currentUser");
			session.setAttribute("SUCCESS", "Successfully deleted your account");
			response.sendRedirect("Home.jsp");
			database.closeConnection(conn);
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
