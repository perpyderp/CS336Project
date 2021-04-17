<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    import = "com.cs336.user.User"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css" />
<meta charset="ISO-8859-1">
<title>Account Information</title>
</head>
<body>
<%
	User currentUser = (User) session.getAttribute("currentUser");
%>
	<div class="headerbar">
	<div class="headerbar-right">
	<% if(currentUser == null) { %>
  		<a href="Login.jsp">Login</a>
  		<a href="Registration.jsp">Create an Account</a>
  	<% } 
  	else { %>
		<a href="CreateAuction.jsp">Create Auction</a>
  			<div class="dropdown">
    		<button class="dropbtn">Auction-IT Account 
      			<i class="fa fa-caret-down"></i>
    		</button>
    		<div class="dropdown-content">
    			<a href="Account.jsp">My Profile</a>
      			<a href="${pageContext.request.contextPath}/logout">Logout</a>
      		</div>
      		</div>
  	<% } %>
  	</div>
  	</div>
  	<div class="sidenav">
  <a href="#about">Account Information</a>
  <a href="#services">Auctions</a>
  <a href="#clients">Alerts</a>
  <a href="#contact">Ratings</a>
</div>
  	
  	
</body>
</html>