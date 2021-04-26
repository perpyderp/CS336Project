package com.cs336.registration.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.cs336.dbapp.ApplicationDB;
import com.cs336.user.User;

public class RegistrationDao {

	/**
	 * Method to validate if the taken login is valid
	 * @param loginBean LoginBean containing username
	 * @return true if status is found in the database, else false
	 * @throws ClassNotFoundException
	 */
	public boolean loginExists(User loginBean) throws ClassNotFoundException {
		
		boolean status = false;

		try {
			ApplicationDB database = new ApplicationDB();
			Connection conn = database.getConnection();

			PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
			preparedStatement.setString(1, loginBean.getUsername());

			ResultSet rs = preparedStatement.executeQuery();
			status = rs.next();

		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

}
