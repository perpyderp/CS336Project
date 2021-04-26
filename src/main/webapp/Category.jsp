<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	import = "java.sql.*"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title><%=request.getParameter("category") %></title>
</head>
<body>
<%@ include file="navbar.jsp" %>
<div class="container"><a href="Home.jsp"><img src="auctionlogo.png"></a></div>
<%@ include file="Categories.jsp"%>
<%
	try{
		Connection conn = database.getConnection();
		
		String category = request.getParameter("category");
		String queryCategoryAuctions = "SELECT * FROM auction WHERE category=?";
		PreparedStatement categoryPS = conn.prepareStatement(queryCategoryAuctions);
		categoryPS.setString(1, category);
		ResultSet categoryAuctionsRS = categoryPS.executeQuery();
		if(categoryAuctionsRS.next()) { %>
			<h2>Category: <%=category %></h2>
			<table>
			<tr>
				<th>Auction #</th>
				<th>Seller</th>
				<th>Initial Price</th>
				<th>Current Bid</th>
				<th>End Date/Time</th>
			</tr>
			<% do { %>
			<tr>
				<td><a href="ViewAuction.jsp?auctionID=<%= categoryAuctionsRS.getInt("auctionID") %>"><%= categoryAuctionsRS.getString("auctionID") %></a></td>
				<td><%= categoryAuctionsRS.getString("seller") %></td>
				<td><%= moneyFormat.format(categoryAuctionsRS.getFloat("initial_price")) %>
				<td><%= moneyFormat.format(categoryAuctionsRS.getFloat("highest_bid")) %></td>
				<td><%= categoryAuctionsRS.getString("close_time") %></td>
			</tr>
			<% } while(categoryAuctionsRS.next()); %>
			</table>
		
		<% }
		else { %> <h2>There aren't any auctions under <%=category %> category</h2> <% }
		database.closeConnection(conn);
	}
	catch(Exception exception) {
		response.sendRedirect("Error.jsp");
	}
%>

</body>
</html>