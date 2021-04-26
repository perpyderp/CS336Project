package com.cs336.dbapp;

import java.sql.*;
import java.util.ArrayList;

public class ApplicationDB {
	
	public ApplicationDB(){ }

	@SuppressWarnings("deprecation")
	public Connection getConnection(){
		
		//Create a connection string
		String connectionUrl = "jdbc:mysql://localhost:3306/cs336project?autoReconnect=true&useSSL=false";
		Connection connection = null;
		
		try {
			//Load JDBC driver - the interface standardizing the connection procedure. Look at WEB-INF\lib for a mysql connector jar file, otherwise it fails.
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} 
		catch (InstantiationException e) { e.printStackTrace(); } 
		catch (IllegalAccessException e) { e.printStackTrace(); } 
		catch (ClassNotFoundException e) { e.printStackTrace(); }
		try {
			//Create a connection to your DB
			connection = DriverManager.getConnection(connectionUrl,"root", "G3t3ducated");
		} 
		catch (SQLException e) { e.printStackTrace(); }
		
		return connection;
	}
	
	
	public void closeConnection(Connection connection){
		try { connection.close(); } 
		catch (SQLException e) { e.printStackTrace(); }
	}
	
	public static void main(String[] args) {
		ApplicationDB dao = new ApplicationDB();
		Connection connection = dao.getConnection();
		
		System.out.println(connection);		
		dao.closeConnection(connection);
	}
	
	
	public void updateDatabase() {
		ApplicationDB database = new ApplicationDB();
		ArrayList<Integer> auctionIDPastClosed = new ArrayList<Integer>();
		try {
			Connection conn = database.getConnection();
			
			PreparedStatement updateAuctions = conn.prepareStatement("UPDATE auction SET sold=1 WHERE close_time < NOW() AND sold=0");
			PreparedStatement getAuctions = conn.prepareStatement("SELECT * FROM auction WHERE close_time < NOW()");
			ResultSet pastClosedResults = getAuctions.executeQuery();
			updateAuctions.executeUpdate();
			while(pastClosedResults.next()) {
				auctionIDPastClosed.add(pastClosedResults.getInt("auctionID"));
			}
			database.closeConnection(conn);
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
