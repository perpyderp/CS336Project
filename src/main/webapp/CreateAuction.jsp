<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    import = "com.cs336.user.User"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css" />
<meta charset="ISO-8859-1">
<title>Create Auction</title>
</head>
<body>
<%
	User currentUser = (User) session.getAttribute("currentUser");
	String ERROR = (String) session.getAttribute("ERROR");
%>
	<div class="headerbar">
	<div class="headerbar-right">
	<% if(currentUser == null) { 
		response.sendRedirect("Login.jsp");
	} 
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
  	<div class="container">
		<a href="Home.jsp"><img src="auctionlogo.png"></a>
		
	</div>
	<% if(ERROR != null) { %>
		<div style="color:red;"> <% out.println(ERROR); %> </div>
	<% session.removeAttribute("ERROR"); } %>
	
	<div>
		<form method = "post" action = "<%=request.getContextPath()%>/createAuction">
		<table>
			<tr>    
				<td><label for="starttime">Start Date:</label>
				<input type="datetime-local" id="starttime" placeholder="yyyy-MM-dd'T'HH:mm" value = "2021-04-15T08:05" name=starttime maxlength="17" size="20"></td>
			</tr>
			<tr>
				<td><label for="closetime">Close Date:</label>
				<input type="datetime-local" id="closetime" placeholder="yyyy-MM-dd'T'HH:mm" value = "2021-04-15T08:10" name="closetime" maxlength="17" size="20"></td>
			</tr>
			<tr>
				<td>Starting Price: $<input type="number" name="initialPrice" placeholder="0.00" step="0.01" size="10" maxlength="7" required></td>
			</tr>
			<tr>
				<td>Minimum Increment Bid: $<input type="number" name="min_Increment" placeholder="0.00" step="0.01" size="10" maxlength="7" required></td>
			</tr>
			<tr>
				<td>Hidden Minimum Bid (Optional): $<input type="number" name="hidden_min_Increment" placeholder="0.00" step="0.01" size="10" maxlength="7"></td>
			</tr>
			<tr>
				<td><textarea name="description" cols="50" rows="10" placeholder="Description... (Optional)"></textarea></td>
			</tr>
		</table>
		
		<input type = "submit" value = "Post Auction" width="50" height="50">
		</form>
	
	</div>
	
</body>
</html>