<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    import = "java.util.ArrayList" import = "com.cs336.auction.Auction"
    import = "java.sql.*"
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
try {
	database.updateDatabase();
	String deleteAccount = (String) session.getAttribute("SUCCESS");
	String deleteAccountError = (String) session.getAttribute("ACCOUNT_DELETE");
	String deleteAccountAuction = (String) session.getAttribute("AUCTION_DELETE");
	String success = (String) session.getAttribute("unsuccessful");
	String createAccount = (String) session.getAttribute("ACCOUNT_CREATE");
	//ArrayList<Auction> auctions = database.getAuctions();
	//session.setAttribute("auctions", auctions);
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
<% if(createAccount != null) { %>
	<div style="color:green;"> <% out.println(createAccount); %> </div>
<% session.removeAttribute("ACCOUNT_CREATE"); } %>
<%
	Connection conn = database.getConnection();
	ResultSet rs = null;
    			
		String allAuctionsQuery = "SELECT * from Auction";
		Statement allAuctions = conn.createStatement();
		rs = allAuctions.executeQuery(allAuctionsQuery);
		if (rs.next()) { %>
			<h2>All Live Auctions</h2>
			<table class="auction">
			<tr>
				<th>Auction #</th>
				<th>Item Name</th>
				<th>Seller</th>
				<th>Initial Price</th>
				<th>Current Bid</th>
				<th>End Date/Time</th>
				<th>Status</th>
			</tr>
			<%	do { %>
			<tr>
				<td><a href="ViewAuction.jsp?auctionID=<%= rs.getInt("auctionID") %>"><%= rs.getString("auctionID") %></a></td>
				<td><%= rs.getString("item_name") %></td>
				<td><%= rs.getString("seller") %></td>
				<td><%= moneyFormat.format(rs.getFloat("initial_price")) %></td>
				<td><%= moneyFormat.format(rs.getFloat("highest_bid")) %></td>
				<td><%= rs.getString("close_time") %></td>
				<td><% if(rs.getBoolean("sold")) { %><div style="color:red;">CLOSED</div><% } else { %><div style="color:green;">OPEN</div><% } %></td>
			</tr>
			<% } while (rs.next()); %> 
			</table>
		<%	} else { %>
		<br><h3>There are currently no live auctions.</h3>
		<%	} %>		
		<%	
			rs.close();
			allAuctions.close();
			conn.close();
		} 
		catch (SQLException e){
			out.print("Oops! Something went wrong connecting to MYSQL server.");
			e.printStackTrace();    			
		}
		catch(Exception exception) {
			response.sendRedirect("Error.jsp");
		}%>
</div>
</body>
</html>