<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    import = "com.cs336.user.User" import = "java.util.ArrayList" import = "com.cs336.auction.Auction"
    import = "com.cs336.dbapp.ApplicationDB" import = "java.sql.*" import = "java.text.DecimalFormat"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
</head>
<body>
<%@ include file="navbar.jsp" %>
<div class="container"><a href="Home.jsp"><img src="auctionlogo.png"></a></div>
<%@ include file="Categories.jsp"%>
<%
	database.updateDatabase();
	String deleteAccount = (String) session.getAttribute("SUCCESS");
	String deleteAccountError = (String) session.getAttribute("ACCOUNT_DELETE");
	String deleteAccountAuction = (String) session.getAttribute("AUCTION_DELETE");
	DecimalFormat moneyFormat = new DecimalFormat("$0.00");
	String success = (String) session.getAttribute("unsuccessful");
	ArrayList<Auction> auctions = database.getAuctions();
	session.setAttribute("auctions", auctions);
%>
<div class="content">
<% if(deleteAccount != null) { %>
	<div style="color:red;"> <% out.println(deleteAccount); %> </div>
<% session.removeAttribute("SUCCESS"); } %>
<% if(deleteAccountError != null) { %>
	<div style="color:red;"> <% out.println(deleteAccountError); %> </div>
<% session.removeAttribute("ACCOUNT_DELETE"); } %>
<% if(deleteAccountAuction != null) { %>
	<div style="color:red;"> <% out.println(deleteAccountAuction); %> </div>
<% session.removeAttribute("AUCTION_DELETE"); } %>
<%
	Connection conn = database.getConnection();
	ResultSet rs = null;
	
	try {
    			
		String allAuctionsQuery = "SELECT * from Auction";
		Statement stm = conn.createStatement();
		rs = stm.executeQuery(allAuctionsQuery);
		if (rs.next()) { %>
			<h2>All Live Auctions</h2>
			<table>
			<tr>
				<th>Auction #</th>
				<th>Seller</th>
				<th>Initial Price</th>
				<th>Current Bid</th>
				<th>End Date/Time</th>
			</tr>
			<%	do { %>
			<tr>
				<td>
				<a href="ViewAuction.jsp?auctionID=<%= rs.getInt("auctionID") %>">
				<%= rs.getString("auctionID") %>
				</a>
				</td>
				<td><%= rs.getString("seller") %></td>
				<td><%= moneyFormat.format(rs.getFloat("initial_price")) %>
				<td><%= moneyFormat.format(rs.getFloat("highest_bid")) %></td>
				<td><%= rs.getString("close_time") %></td>
			</tr>
			<% } while (rs.next()); %> 
			</table>
		<%	} else { %>
		<br><h3>There are currently no live auctions.</h3>
		<%	} %>		
		<%	
			rs.close();
			stm.close();
			conn.close();
		} 
		catch (SQLException e){
			out.print("<p>Error connecting to MYSQL server.</p>");
			e.printStackTrace();    			
		} %>
</div>
</body>
</html>