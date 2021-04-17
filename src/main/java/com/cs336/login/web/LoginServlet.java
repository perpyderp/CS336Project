package com.cs336.login.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.cs336.login.database.LoginDao;
import com.cs336.user.User;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private LoginDao loginDao;

	public void init() {
		loginDao = new LoginDao();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		try {
			User user = loginDao.findUser(username, password);
			if (user != null) {
				HttpSession session = request.getSession();
				String userID = String.valueOf(user.getUserID());
				request.getSession().setAttribute(userID, userID);
				session.setAttribute("currentUser", user);
				response.sendRedirect("Home.jsp");
			} 
			else {
				HttpSession session = request.getSession();
				session.setAttribute("unsuccessful", "unsuccessful");
				response.sendRedirect("Login.jsp");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
