<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    import = "com.cs336.user.User" import = "java.util.ArrayList" import = "com.cs336.auction.Auction"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css" />
<meta charset="ISO-8859-1">
<title>Home</title>
</head>
<body>
<%
	User currentUser = (User) session.getAttribute("currentUser");
	String success = (String) session.getAttribute("unsuccessful");
	ArrayList<Auction> auctions = (ArrayList<Auction>) session.getAttribute("Auctions");
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
  	<div class="container">
		<a href="Home.jsp"><img src="auctionlogo.png"></a>
		
	</div>
<div class="headerbar">
  <div class="dropdown">
    <button class="dropbtn">Fashion 
      <i class="fa fa-caret-down"></i>
    </button>
    <div class="dropdown-content">
      <a href="#">Men's Clothing</a>
      <a href="#">Women's Clothing</a>
      <a href="#">Accessories</a>
    </div>
  </div> 
  <div class="dropdown">
    <button class="dropbtn">Technology 
      <i class="fa fa-caret-down"></i>
    </button>
    <div class="dropdown-content">
      <a href="#">Phones, Watches, Accessories</a>
      <a href="#">Computers & Laptops</a>
      <a href="#">TV, Cables, & Speakers</a>
    </div>
  </div> 
  <div class="dropdown">
    <button class="dropbtn">Collectibles 
      <i class="fa fa-caret-down"></i>
    </button>
    <div class="dropdown-content">
      <a href="#">Pottery</a>
      <a href="#">Antiques</a>
      <a href="#">Pokemon Cards</a>
    </div>
  </div> 
    <div class="dropdown">
    <button class="dropbtn">Sporting Goods 
      <i class="fa fa-caret-down"></i>
    </button>
    <div class="dropdown-content">
      <a href="#">Football</a>
      <a href="#">Fishing</a>
      <a href="#">Fitness Gear</a>
    </div>
  </div> 
</div>

<%
	if(auctions != null) {
		
	}
	else { %> There are no auctions available! <% }
%>
</body>
</html>