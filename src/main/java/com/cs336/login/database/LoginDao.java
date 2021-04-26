package com.cs336.login.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cs336.dbapp.ApplicationDB;
import com.cs336.user.User;

public class LoginDao {
	
	public User findUser(String username, String password) throws ClassNotFoundException {
		User user = null;
		try {
			ApplicationDB database = new ApplicationDB();
			Connection conn = database.getConnection();

			PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ? ");
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);

			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				if(username.equals(rs.getString("username")) && password.equals(rs.getString("password"))) {
					int userID = rs.getInt("userID");
					String firstName = rs.getString("first_name");
					String lastName = rs.getString("last_name");
					user = new User(userID, username, password, false, false, firstName, lastName);
				}
			}
		}
		catch (Exception e) { e.printStackTrace(); }
		return user;
	}

}
