package com.cs336.dbapp;

import java.sql.*;
import com.cs336.auction.Auction;
import com.cs336.user.User;
import com.cs336.auction.Bid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ApplicationDB {
	
	public ApplicationDB(){
		
	}

	public Connection getConnection(){
		
		//Create a connection string
		String connectionUrl = "jdbc:mysql://localhost:3306/cs336project";
		Connection connection = null;
		
		try {
			//Load JDBC driver - the interface standardizing the connection procedure. Look at WEB-INF\lib for a mysql connector jar file, otherwise it fails.
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			//Create a connection to your DB
			connection = DriverManager.getConnection(connectionUrl,"root", "G3t3ducated");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return connection;
		
	}
	
	
	public void closeConnection(Connection connection){
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ApplicationDB dao = new ApplicationDB();
		Connection connection = dao.getConnection();
		
		System.out.println(connection);		
		dao.closeConnection(connection);
	}
	
	public ArrayList<Auction> getAuctions() {
		ApplicationDB database = new ApplicationDB();
		ArrayList<Auction> auctions = new ArrayList<Auction>();
		try {
			Connection conn = database.getConnection();

			PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM auction");
			ResultSet rs = preparedStatement.executeQuery();
			
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd"); 
			while(rs.next()) {
				
				auctions.add(new Auction(rs.getInt("userID"), rs.getInt("item_no"), formatDate.parse(rs.getString("start_time")), formatDate.parse(rs.getString("close_time")), 
						rs.getString("description"), rs.getInt("userID"), rs.getFloat("initial_price")));
			}

		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return auctions;
	}
	
	public ArrayList<Bid> bidHistory(Auction auction) {
		ApplicationDB database = new ApplicationDB();
		ArrayList<Bid> history = new ArrayList<Bid>();
		
		
		
		return history;
	}

}
