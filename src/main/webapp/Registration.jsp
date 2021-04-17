<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.dbapp.*" import = "com.cs336.user.User"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
<link rel="stylesheet" type="text/css" href="style.css" />
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Register - Create new user</title>
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
	<h1>Create an account</h1>
	<a href="Home.jsp"><img src="auctionlogo.png"></a>
	<%
		String result = (String) session.getAttribute("result");
		if(result!=null) {
			
	%>
				<div style="color:red;"> <% out.println(result); %> </div>
	<%
				session.setAttribute("result", null);	
		}
	%>
	<form method = "post" action = "<%=request.getContextPath()%>/registration">
		<table>
			<tr>    
				<td>Username: </td><td><input type="text" name="username" required></td>
			</tr>
			<tr>
				<td>Password: </td><td><input type="password" name="password" required></td>
			</tr>
			
		</table>
		<input type="submit" value="Create account">
	</form>

</body>
</html>