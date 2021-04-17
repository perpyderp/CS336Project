<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    import = "com.cs336.user.User" import = "com.cs336.auction.Auction"
    import = "com.cs336.auction.Item" import = "com.cs336.auction.Bid"
    import = "java.util.ArrayList" import = "java.util.Date" 
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Auction</title>
</head>
<body>

<%
	User user = (User)session.getAttribute("currentUser");
	Date date = new Date();
	Item item = (Item)request.getAttribute("item");
	Auction auction = (Auction)request.getAttribute("auction");	
	ArrayList<Bid> bids = (ArrayList<Bid>)request.getAttribute("bids");
	ArrayList<Item> itemResults = (ArrayList<Item>)request.getAttribute("simItems");
	ArrayList<Auction> auctionResults = (ArrayList<Auction>)request.getAttribute("simAucts");
%>


</body>
</html>