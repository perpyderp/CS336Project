<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    import = "com.cs336.user.User" import = "java.sql.*"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css" />
<meta charset="ISO-8859-1">
<title>Account Information</title>
</head>
<body>
<%@ include file="navbar.jsp" %>
<% 	try { %>
<div id="mySidenav" class="sidenav">
  <a href="Account.jsp?username=<%=currentUser.getUserID()%>">Account Information</a>
  <a href="Account.jsp?username=<%=currentUser.getUserID()%>&tab=auction">Auctions</a>
  <a href="Account.jsp?username=<%=currentUser.getUserID()%>&tab=buyhistory">Buy History</a>
  <a href="Account.jsp?username=<%=currentUser.getUserID()%>&tab=alerts">Alerts</a>
  <a href="Account.jsp?username=<%=currentUser.getUserID()%>&tab=deleteaccount">Delete Account</a>
</div>
<div class="sidemain">
<%
		String username = currentUser.getUsername();
		String tab = request.getParameter("tab");
		if(tab==null) { %>
			<h2>Account Information</h2>
			<%
			String queryAccountInfo = "SELECT * FROM users WHERE userID=?";
			Connection conn = database.getConnection();
			PreparedStatement accountInfoPS = conn.prepareStatement(queryAccountInfo);
			accountInfoPS.setInt(1, currentUser.getUserID());
			ResultSet accountAuctionsRS = accountInfoPS.executeQuery();			
			accountAuctionsRS.next();
			%>
			<form method = "post" action = "<%=request.getContextPath()%>/UpdateAccountInfo">
			<table>
			<tr>
				<td>First Name: <input type="text" name="firstName" value=<%=accountAuctionsRS.getString("first_name") %> /></input></td>
			</tr>
			<tr>
				<td>Last Name: <input type="text" name="lastName" value=<%=accountAuctionsRS.getString("last_name") %> /></input></td>
			</tr>
			<tr>
				<td>Username: <input type="text" name="username" value=<%=accountAuctionsRS.getString("username") %> /></input></td>
			</tr>
			<tr>
				<td>New Password: <input type="password" name="password"  /></input></td>
			</tr>
			<tr>
				<td>Confirm New Password: <input type="password" name="confirmPass" /></input></td>
			</tr>
			</table>
			</form>
		<% }
		else if(tab.equals("auction")) { %>
			<h2>Your Auctions</h2>
			<%
			String queryAccountAuctions = "SELECT * FROM auction WHERE seller=?";
			Connection conn = database.getConnection();
			PreparedStatement accountAuctionsPS = conn.prepareStatement(queryAccountAuctions);
			accountAuctionsPS.setString(1, username);
			ResultSet accountAuctionsRS = accountAuctionsPS.executeQuery();
			if(accountAuctionsRS.next()) { %>
				<table class="auction">
				<tr>
					<th>Auction #</th>
					<th>Item Name</th>
					<th>Initial Price</th>
					<th>Current Bid</th>
					<th>End Date/Time</th>
				</tr>
				<%	do { %>
				<tr>
					<td><a href="ViewAuction.jsp?auctionID=<%= accountAuctionsRS.getInt("auctionID") %>"><%= accountAuctionsRS.getString("auctionID") %></a></td>
					<td><%= accountAuctionsRS.getString("item_name") %></td>
					<td><%= moneyFormat.format(accountAuctionsRS.getFloat("initial_price")) %>
					<td><%= moneyFormat.format(accountAuctionsRS.getFloat("highest_bid")) %></td>
					<td><%= accountAuctionsRS.getString("close_time") %></td>
				</tr>
				<% } while (accountAuctionsRS.next()); %> 
			</table>
			
			
			<% }
			else { %>
				You have no auctions.
			<%}
		}
		else if(tab.equals("buyhistory")) { %>
			<h2>Your buy history</h2>
			<%
			String queryAccountBuyHistory = "SELECT * FROM buyhistory WHERE buyer=?";
			Connection conn = database.getConnection();
			PreparedStatement accountBuyHistoryPS = conn.prepareStatement(queryAccountBuyHistory);
			accountBuyHistoryPS.setString(1, username);
			ResultSet accountBuyHistoryRS = accountBuyHistoryPS.executeQuery();
			if(accountBuyHistoryRS.next()) { %>
				<table class="auction">
				<tr>
					<th>Auction #</th>
					<th>Price Bought</th>
					<th>Date Purchased</th>
				</tr>
				<%	do { %>
				<tr>
					<td><a href="ViewAuction.jsp?auctionID=<%= accountBuyHistoryRS.getInt("auctionID") %>"><%= accountBuyHistoryRS.getString("auctionID") %></a></td>
					<td><%= moneyFormat.format(accountBuyHistoryRS.getFloat("priceBought")) %></td>
					<td><%= accountBuyHistoryRS.getString("dateBought") %></td>
				</tr>
				<% } while (accountBuyHistoryRS.next()); %> 
			</table>
			<%}
			else { %>
				You have no items bought.
			<%}
		
		}
		else if(tab.equals("alerts")) { %>
			<h2>My Alerts</h2>
			<% 
			String queryAlerts = "SELECT * FROM alert WHERE user = ?";
			Connection conn = database.getConnection();
			PreparedStatement getAlerts = conn.prepareStatement(queryAlerts);
			getAlerts.setString(1, username);
			ResultSet getAlertsResult = getAlerts.executeQuery();
			if(getAlertsResult.next()) { %>
				<table class="alert">
				<tr>
					<th>AlertID</th>
					<th>Message</th>
				</tr>
			<% do { %>
			<tr>
				<td><%=getAlertsResult.getInt("alertID") %></td>
				<td><%=getAlertsResult.getString("message") %></td>
			
			</tr>
			<% } while(getAlertsResult.next()); %>
			</table>
			<%}
			else { %>
				You have no alerts.
			<%}
		}
		else if(tab.equals("deleteaccount")) { %>
			<h2>Delete Account</h2>
			<form method = "post" action = "<%=request.getContextPath()%>/deleteAccount">
			<input type = "submit" value = "Delete Account" width="20" height="50">
			</form>
			<%
		}
	}
	catch(Exception exception) {
		response.sendRedirect("Error.jsp");
	}
%>
</div>
</body>
</html>