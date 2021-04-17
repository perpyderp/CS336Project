package com.cs336.dbapp;

import java.sql.*;

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
	
	public int countDB(String query) throws SQLException{
		int count = 0;
		PreparedStatement prepState = null;
		ResultSet rs = null;
		try{
			prepState = getConnection().prepareStatement(query);
			System.out.println(prepState.toString());
			rs = prepState.executeQuery();
			while(rs.next()){
				count = rs.getInt(1);
			}
		} catch (SQLException e){
			e.printStackTrace();
		} finally{
			try { if (rs != null) rs.close(); } catch (SQLException e){};
			try { if (prepState != null) prepState.close(); } catch (SQLException e){};
		}
		return count;
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
	
	

}
