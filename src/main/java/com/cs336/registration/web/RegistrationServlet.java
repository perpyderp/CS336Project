package com.cs336.registration.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cs336.dbapp.*;
import java.sql.*;

import com.cs336.registration.database.RegistrationDao;
import com.cs336.user.User;


@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RegistrationDao registrationDao;

	public void init() { registrationDao = new RegistrationDao(); }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User loginBean = new User();
		loginBean.setUsername(username);
		loginBean.setPassword(password);

		try {
			if (registrationDao.loginExists(loginBean) || loginBean.getPassword().equals("")) {
				HttpSession session = request.getSession();
				session.setAttribute("result","Username already exists!");
				response.sendRedirect("Registration.jsp");
			} 
			else {
				try{
					HttpSession session = request.getSession();
					ApplicationDB database = new ApplicationDB();
					Connection conn = database.getConnection();
					String insert = "INSERT INTO users(username, first_name, last_name, password)" + "VALUES(?, ?, ?, ?)";
					
					PreparedStatement ps = conn.prepareStatement(insert);
					ps.setString(1, username);
					ps.setString(2, firstName);
					ps.setString(3, lastName);
					ps.setString(4, password);
					ps.execute();
					conn.close();
					
					session.setAttribute("ACCOUNT_CREATE", "Successfully created account!");
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				response.sendRedirect("Home.jsp");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
