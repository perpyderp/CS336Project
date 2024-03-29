<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    import = "com.cs336.user.User" import = "com.cs336.dbapp.ApplicationDB"
    import = "java.text.DecimalFormat"
    pageEncoding="ISO-8859-1"%>
<link rel="stylesheet" type="text/css" href="style.css" />
<% 
	ApplicationDB database = new ApplicationDB();
	database.updateDatabase();
	User currentUser = (User) session.getAttribute("currentUser");
	DecimalFormat moneyFormat = new DecimalFormat("$0.00");
%>
	<div class="headerbar">
	<a href="Home.jsp">Home</a>
	<div class="headerbar-right">
	<% if(currentUser == null) { %>
  		<a href="Login.jsp">Login</a>
  		<a href="Registration.jsp">Create an Account</a>
  	<% } 
  	else { %>
		<a href="CreateAuction.jsp">Create Auction</a>
  			<div class="dropdown">
    		<button class="dropbtn">User: <% out.print(currentUser.getFirstName()); %> 
      			<i class="fa fa-caret-down"></i>
    		</button>
    		<div class="dropdown-content">
    			<a href="Account.jsp?userID=<%=currentUser.getUsername()%>">My Account</a>
      			<a href="${pageContext.request.contextPath}/logout">Logout</a>
      		</div>
      		</div>
  	<% } %>
  	</div>
  	</div>
