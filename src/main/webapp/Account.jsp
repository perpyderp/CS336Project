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
<% if(currentUser == null) response.sendRedirect("Login.jsp");%>
<div id="mySidenav" class="sidenav">
  <a href="#">Account Information</a>
  <a href="#">Auctions</a>
  <a href="#">Buy History</a>
  <a href="#">Alerts</a>
  <a href="#">Delete Account</a>
</div>
<div class="sidemain">
	<h2>Account Information</h2>
	<% String username = currentUser.getUsername(); %>
	<h2>My Alerts</h2>
	<table>
	<tr>
		<th>AlertID</th>
		<th>Message</th>
	</tr>
	<% 
	String queryAlerts = "SELECT * FROM alert WHERE user = ?";
	Connection conn = database.getConnection();
	PreparedStatement getAlerts = conn.prepareStatement(queryAlerts);
	getAlerts.setString(1, username);
	ResultSet getAlertsResult = getAlerts.executeQuery();
	while(getAlertsResult.next()) { %>
	<tr>
		<td><%=getAlertsResult.getInt("alertID") %></td>
		<td><%=getAlertsResult.getString("message") %></td>
	
	</tr>
	<% } %>
	</table>
	<a href="<%=request.getContextPath()%>/deleteAccount"> Delete Account</a>
</div>
</body>
</html>