package com.cs336.user;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	private int userID;			//Primary key created in SQL
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private boolean isCustomerRep;
	private boolean isAdmin;
	
	public User(int userID, String username, String password, boolean isCustomerRep, boolean isAdmin, String firstName, String lastName) {
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.setCustomerRep(isCustomerRep);
		this.setAdmin(isAdmin);
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public User() {}
	
	public String getUsername() { return username; }
	
	public void setUsername(String username) { this.username = username; }
	
	public String getPassword() { return password; }
	
	public void setPassword(String password) { this.password = password; }

	public boolean isCustomerRep() { return isCustomerRep; }

	public void setCustomerRep(boolean isCustomerRep) { this.isCustomerRep = isCustomerRep; }

	public boolean isAdmin() { return isAdmin; }

	public void setAdmin(boolean isAdmin) { this.isAdmin = isAdmin; }

	public String getLastName() { return lastName; }

	public void setLastName(String lastName) { this.lastName = lastName; }

	public String getFirstName() { return firstName; }

	public void setFirstName(String firstName) { this.firstName = firstName; }

	public int getUserID() { return userID; }

	public void setUserID(int userID) { this.userID = userID; }
	
}
