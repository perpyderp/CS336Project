<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    import = "com.cs336.user.User" import = "com.cs336.auction.Auction" import = "com.cs336.auction.Bid"
    import = "java.util.ArrayList" import = "java.util.Date" import = "java.text.SimpleDateFormat"
	import="java.io.*,java.util.*,java.sql.*,java.text.*"
	import= "javax.servlet.http.*" import = "javax.servlet.*" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Auction</title>
<script type="text/javascript">
function hidePlaceBid () {

	document.getElementById("placeBid").style.display = "none";
}
function showAutoBid (checked) {
	print();
	if(checked == true)
		$("autoBidForm").fadeIn();
	else $("autoBidForm").fadeOut();
}

</script>
</head>
<body>
<%@ include file="navbar.jsp" %>
<% if(session.getAttribute("currentUser") == null) { response.sendRedirect("Login.jsp"); }
   else { %>
<div class="container"><a href="Home.jsp"><img src="auctionlogo.png"></a></div>
<%@ include file="Categories.jsp"%>
	<%try{
		String username = currentUser.getUsername();
		Date date = new Date();
		int auctionID = Integer.parseInt(request.getParameter("auctionID"));
		Connection conn = database.getConnection();
		String queryAuction = "SELECT * FROM Auction WHERE auctionID=?";
		Auction auction;
		PreparedStatement getAuction = conn.prepareStatement(queryAuction);
		getAuction.setInt(1, auctionID);
		ResultSet getAuctionResult = getAuction.executeQuery();
		if(!getAuctionResult.next()) { %>
			<h2>ERROR: Sorry! There doesn't seem to be an auction with that ID</h2>
		<%}
		else {
			//Auction auction = new Auction(int aucid, String seller, Date start_Time, Date close_Time, String description, float initial_Price, String item_Name, String category, boolean sold)
			auction = new Auction(getAuctionResult.getInt("auctionID"), getAuctionResult.getString("seller"), getAuctionResult.getTimestamp("start_time"), getAuctionResult.getTimestamp("close_time"), 
				getAuctionResult.getString("description"), getAuctionResult.getFloat("initial_price"), getAuctionResult.getString("item_name"), getAuctionResult.getString("category"), 
				getAuctionResult.getBoolean("sold"), getAuctionResult.getFloat("highest_bid"), getAuctionResult.getFloat("hidden_min_price"), getAuctionResult.getFloat("min_increment"));
			session.setAttribute("auction", auction);
		}

		String ERROR = (String) session.getAttribute("ERROR");
		if(ERROR != null) { %>
			<div style="color:red;"> <% out.println(ERROR); %> </div>
			<% session.removeAttribute("ERROR"); 
		}%>

		<h2>Item: <%= getAuctionResult.getString("item_name") %></h2>
		<h3>Auction Category: <%= getAuctionResult.getString("category") %> <br>
		Current Highest Bid: <% if(getAuctionResult.getString("highest_bid").equals("0.00")){ out.print("NO BIDS"); out.println("\nNext Minimum Bid: " + moneyFormat.format(getAuctionResult.getFloat("initial_price") + getAuctionResult.getFloat("min_increment")));} 
								else { out.println("$" + getAuctionResult.getString("highest_bid")); out.println("\nNext Minimum Bid: " + moneyFormat.format(getAuctionResult.getFloat("highest_bid") + getAuctionResult.getFloat("min_increment")));} %></h3>

		Initial Price: $<%= getAuctionResult.getFloat("initial_price") %> <br>
		Seller: <%= getAuctionResult.getString("seller") %> <br>
		Close time: <%= getAuctionResult.getString("close_time") %> <br>
		Description: <%= getAuctionResult.getString("description") %> <br>
		<%
		if(getAuctionResult.getBoolean("sold") == true) { %> 
			<div style="color:red;"> AUCTION HAS ENDED </div> 
			<script>hidePlaceBid();</script>
			<%	
			String queryWinner = "SELECT * FROM buyhistory WHERE auctionID=?";
			PreparedStatement winnerPS = conn.prepareStatement(queryWinner);
			winnerPS.setInt(1, auctionID);
			ResultSet winnerRS = winnerPS.executeQuery();
			if(winnerRS.next()) { %>
				Winner of Auction: <%=winnerRS.getString("buyer") %> at <%=moneyFormat.format(winnerRS.getFloat("priceBought")) %> <br>
			<% } 
			else { %>
				Winner of Auction: No winner because no bids were placed <br>
			<%}
		}
		else {
			if(!username.equals(getAuctionResult.getString("seller"))) {%>
				<div class="placeBid">
				<h2>Place a Bid</h2>
				<form method = "post" action = "<%=request.getContextPath()%>/PlaceBid">
					<input type="hidden" name="username" value="${currentUser.getUsername()}" /></input>
					Place bid: $<input type="number" name="placeBid" placeholder="0.00" step="0.01" size="10" maxlength="7"><br>
					<label for="autobid">SHOW/HIDE AUTO-BID FORM: </label><input type="checkbox" id="trigger" name="autobid" value="true"/>
					<div class="autobid">
						Auto-bidding Form
						Max Bid: $<input type="number" name="upperLimit" placeholder="0.00" step="0.01" size="10" maxlength="7"><br>
						Bid Increment: $<input type="number" name="bidIncrement" placeholder="0.00" step="0.01" size="10" maxlength="7">
					</div>
					<br><input type = "submit" value = "Place Bid" width="50" height="50">
				</form>
				</div>
			<%}
		}
	}
	catch(Exception exception) {
		response.sendRedirect("Error.jsp");
	}
} %>
</body>
</html>