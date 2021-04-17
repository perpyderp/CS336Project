<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    import = "com.cs336.user.User"
    import = "java.util.ArrayList"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css" />
<meta charset="ISO-8859-1">
<title>Sign-in</title>
</head>
<body>
<%
	User currentUser = (User) session.getAttribute("currentUser");
	String success = (String) session.getAttribute("unsuccessful");

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
    		<button class="dropbtn">User: <% out.print(currentUser.getUsername()); %> 
      			<i class="fa fa-caret-down"></i>
    		</button>
    		<div class="dropdown-content">
    			<a href="Account.jsp">My Account</a>
      			<a href="${pageContext.request.contextPath}/logout">Logout</a>
      		</div>
      		</div>
  	<% } %>
  	</div>
  	</div>
	<h1>Sign-in</h1>
	<a href="Home.jsp"><img src="auctionlogo.png"></a>
	<% if(success != null && success.equals("unsuccessful")) { %>
		<div style="color:red;"> Invalid login! Please try again</div>
	<% session.removeAttribute("unsuccessful"); } %>
	<form method = "post" action = "${pageContext.request.contextPath}/login">
		<table>
			<tr>    
				<td>Username: </td><td><input type="text" name="username" required></td>
			</tr>
			<tr>
				<td>Password: </td><td><input type="password" name="password" required></td>
			</tr>
		</table>
		<input type="submit" value="Sign-in">
	</form>
</body>
</html>