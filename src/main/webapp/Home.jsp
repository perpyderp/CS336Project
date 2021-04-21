<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    import = "com.cs336.user.User" import = "java.util.ArrayList" import = "com.cs336.auction.Auction"
    import = "com.cs336.dbapp.ApplicationDB" import = "java.sql.*"
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
	ApplicationDB database = new ApplicationDB();
	User currentUser = (User) session.getAttribute("currentUser");
	String success = (String) session.getAttribute("unsuccessful");
	ArrayList<Auction> auctions = database.getAuctions();
	session.setAttribute("auctions", auctions);
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
<div class="content">
    	<%
			Connection conn = database.getConnection();
    		ResultSet rs = null;
    	
    		try {
    			
				String allAuctionsQuery = "SELECT * FROM Product WHERE sold=false";
				Statement stm = conn.createStatement();
				rs = s.executeQuery(allAuctionsQuery);
				if (rs.next()) { %>
					<h2>All Live Auctions</h2>
					<table>
						<tr>
							<th>Item</th>
							<th>Seller</th>
							<th>Current Bid</th>
							<th>End Date/Time</th>
						</tr>
						<%	do { %>
						<tr>
							<td>
								<a href="auction.jsp?productId=<%= rs.getInt("productId") %>">
									<%= rs.getString("brand") + " " + rs.getString("model") + " " + rs.getString("gender") +  " " + rs.getFloat("size") %>
								</a>
							</td>
							<td><%= rs.getString("seller") %></td>
							<td><%= currency.format(rs.getDouble("price")) %></td>
							<td><%= rs.getString("endDate") %></td>
						</tr>
				 <%		} while (rs.next()); %> 
					</table>
				<%	} else { %>
						<br><h3>There are currently no live auctions.</h3>
				<%	} %>		
			<%	
			
				
				
    		} catch (SQLException e){
    			out.print("<p>Error connecting to MYSQL server.</p>");
			    e.printStackTrace();    			
    		} finally {
				try { rs.close(); } catch (Exception e) {} 
				try { s.close(); } catch (Exception e) {} 
				try { conn.close(); } catch (Exception e) {} 
    		}   	
    	%>
    	</div>
</body>
</html>