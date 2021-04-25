package com.cs336.auction;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cs336.dbapp.ApplicationDB;


@WebServlet("/PlaceBid")
public class PlaceBidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PlaceBidServlet() { super(); }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			HttpSession session = request.getSession();
			ApplicationDB database = new ApplicationDB();
			Connection conn = database.getConnection();
			PreparedStatement auctionPS = null;
			PreparedStatement bidPS = null;
			PreparedStatement autoBidPS = null;
			PreparedStatement bidHistoryPS = null;
			PreparedStatement alertPS = null;
			if(session.getAttribute("auction")==null) {
				response.sendRedirect("Error.jsp");
				return;
			}
			Auction auction = (Auction) session.getAttribute("auction");
			System.out.println("AUCTION IN SERVLET" + auction);
			int auctionID = auction.getAuctionID();
			String username = (String) request.getParameter("username");
			String prevBidder = null;
			float prevBid = 0;
			float bid = Float.parseFloat(request.getParameter("placeBid"));
			Timestamp currentTime = new Timestamp(new java.util.Date().getTime());
			
			String queryAuctionBid = "UPDATE auction SET highest_bid=? WHERE auctionID=?";
			String queryAuctionBidTable = "INSERT INTO bid(auctionID, bid_amount, time_of_bid, buyer)" + "VALUES(?, ?, ?, ?)";
			
			//Checks if it's the starting bid and if the bid is greater than the initial price
			if(auction.getHighest_Bid() == 0 && bid > auction.getInitial_Price()) {
				auctionPS = conn.prepareStatement(queryAuctionBid);
				auctionPS.setFloat(1, bid);
				auctionPS.setInt(2, auctionID);
				int updateAuctionBidResult = auctionPS.executeUpdate();
				//Checks if auction's highest bid was successfully updated
				if(updateAuctionBidResult < 1) {
					session.setAttribute("ERROR", "Couldn't place bid for auction");
					response.sendRedirect("ViewAuction.jsp?auctionID=" + auctionID);
					return;
				}
				bidPS = conn.prepareStatement(queryAuctionBidTable);
				bidPS.setInt(1, auctionID);
				bidPS.setFloat(2, bid);
				bidPS.setTimestamp(3, currentTime);
				bidPS.setString(4, username);
				int updateBidTableResult = bidPS.executeUpdate();
				//Checks if bid was successfully inserted into bid table
				if(updateBidTableResult < 1) {
					session.setAttribute("ERROR", "Couldn't place bid for auction");
					response.sendRedirect("ViewAuction.jsp?auctionID=" + auctionID);
					return;
				}
			}
			else if(bid > auction.getHighest_Bid() && auction.getHighest_Bid() != 0){
				String queryAuctionBidHistory = "INSERT INTO bidhistory(auctionID, buyer, bid, time_of_bid)" + "VALUES(?, ?, ?, ?)";
				String removeOldBid = "DELETE FROM bid WHERE auctionID=?";
				String queryOldBid = "SELECT * FROM bid WHERE auctionID=?";
				bidPS = conn.prepareStatement(queryOldBid);
				bidPS.setInt(1, auctionID);
				ResultSet queryOldRS = bidPS.executeQuery();
				//Gets the auction's previous bidder's information (sets prevBidder & prevBid)
				if(queryOldRS.next()) {
					prevBidder = queryOldRS.getString("buyer");
					prevBid = queryOldRS.getFloat("bid_amount");
				}
				//Removing the old bid
				bidPS = conn.prepareStatement(removeOldBid);
				bidPS.setInt(1, auctionID);
				int removeOldResult = bidPS.executeUpdate();
				if(removeOldResult < 1) { System.out.println("No old bids"); }
				else { System.out.println("Removed the old bid"); }
				bidHistoryPS = conn.prepareStatement(queryAuctionBidHistory);
				bidHistoryPS.setInt(1, auctionID);
				bidHistoryPS.setString(2, username);
				bidHistoryPS.setFloat(3, auction.getHighest_Bid());
				bidHistoryPS.setTimestamp(4, currentTime);
				auctionPS = conn.prepareStatement(queryAuctionBid);
				auctionPS.setFloat(1, bid);
				auctionPS.setInt(2, auctionID);
				int updateAuctionBidResult = auctionPS.executeUpdate();
				if(updateAuctionBidResult < 1) {
					session.setAttribute("ERROR", "Couldn't place bid for auction");
					response.sendRedirect("ViewAuction.jsp?auctionID=" + auctionID);
					return;
				}
				int updateAuctionBidHistoryResult = bidHistoryPS.executeUpdate();
				if(updateAuctionBidHistoryResult < 1) {
					session.setAttribute("ERROR", "Couldn't update the bid history for auction");
					response.sendRedirect("ViewAuction.jsp?auctionID=" + auctionID);
					return;
				}
				bidPS = conn.prepareStatement(queryAuctionBidTable);
				bidPS.setInt(1, auctionID);
				bidPS.setFloat(2, bid);
				bidPS.setTimestamp(3, currentTime);
				bidPS.setString(4, username);
				int updateBidTableResult = bidPS.executeUpdate();
				if(updateBidTableResult < 1) {
					session.setAttribute("ERROR", "Couldn't place bid for auction");
					response.sendRedirect("ViewAuction.jsp?auctionID=" + auctionID);
					return;
				}
				System.out.println("Successfully placed bid for auction #: " + auctionID);
			}
			else {
				System.out.println("Bid is invalid. Lower than current highest bid or lower than the initial price");
				session.setAttribute("ERROR", "Couldn't place bid for auction. Bid is lower than current highest bid or lower than the initial price");
				response.sendRedirect("ViewAuction.jsp?auctionID=" + auctionID);
				return;
			}
			
			String autoBid = request.getParameter("autobid");
			if(autoBid != null && autoBid.equals("true") && request.getParameter("bidIncrement") != "" && request.getParameter("upperLimit") != "") {
				float bidIncrement = Float.parseFloat(request.getParameter("bidIncrement"));
				float maxBid = Float.parseFloat(request.getParameter("upperLimit"));
				autoBidPS = conn.prepareStatement("INSERT INTO autobid(user, auctionID, max_price, bid_increment)" + "VALUES(?, ?, ?, ?)");
				autoBidPS.setString(1, username);
				autoBidPS.setInt(2, auctionID);
				autoBidPS.setFloat(3, maxBid);
				autoBidPS.setFloat(4, bidIncrement);
				int autoBidRS = autoBidPS.executeUpdate();
				if(autoBidRS < 1) {
					System.out.println("Unable to insert auto-bid into database");
				}
				else
					System.out.println("Successfully inserted auto-bid to database");
			}
			boolean prevHasAuto = false;
			float increment = 0;
			float maxPrice = 0;
			String queryAutoBid = "SELECT * FROM autobid WHERE user=? AND auctionID=?";
			autoBidPS = conn.prepareStatement(queryAutoBid);
			autoBidPS.setString(1, prevBidder);
			autoBidPS.setInt(2, auctionID);
			ResultSet getAutoBidUserRS = autoBidPS.executeQuery();
			if(getAutoBidUserRS.next()) {
				prevHasAuto = true;
				increment = getAutoBidUserRS.getFloat("bid_increment");
				maxPrice = getAutoBidUserRS.getFloat("max_price");
			}
			if(prevBidder != null && !prevBidder.equals(username) && prevHasAuto) {
				String insertAutoBid = "INSERT INTO bid(auctionID, bid_amount, time_of_bid, buyer)" + "VALUES(?, ?, ?, ?)";
				String updateAuctionBid = "UPDATE auction SET highest_bid=? WHERE auctionID=?";
				autoBidPS = conn.prepareStatement(insertAutoBid);
				autoBidPS.setInt(1, auctionID);
				autoBidPS.setTimestamp(3, new Timestamp(new Date().getTime()));
				autoBidPS.setString(4, prevBidder);
				
				//Makes sure the auto-bid doesn't go past the maxPrice
				if(bid + increment <= maxPrice) { bid+=increment; }
				else { bid=maxPrice; }
				autoBidPS.setFloat(2, bid);
				autoBidPS.executeUpdate();
				auctionPS = conn.prepareStatement(updateAuctionBid);
				auctionPS.setFloat(1, bid);
				auctionPS.setInt(2, auctionID);
				auctionPS.executeUpdate();
				//Removing the old bid
				String removeOldBid = "DELETE FROM bid WHERE auctionID=? && bid_amount<?";
				bidPS = conn.prepareStatement(removeOldBid);
				bidPS.setInt(1, auctionID);
				bidPS.setFloat(2, bid);
				int removeOldResult = bidPS.executeUpdate();
				if(removeOldResult < 1) { System.out.println("No old bids"); }
				else { System.out.println("Removed the old bid"); }
				
				String queryOutbidAlert = "INSERT INTO alert (user, message)" + "VALUES(?, ?)";
				alertPS = conn.prepareStatement(queryOutbidAlert);
				alertPS.setString(1, username);
				alertPS.setString(2, "You have been outbid. <a href=\"ViewAuction.jsp?auctionID=" +  auctionID + "  \">Click here to go to the auction.</a>");
				alertPS.executeUpdate();
				if ((prevBidder!= null && !prevBidder.equals(username) && prevHasAuto == false) || (prevBidder!= null && !prevBidder.equals(username) && prevHasAuto == true && prevBid >= maxPrice)) {
						String outBidAlert = "INSERT INTO Alerts (user, message) VALUES (?, ?)";
						alertPS = conn.prepareStatement(outBidAlert);
						alertPS.setString(1, prevBidder);
						alertPS.setString(2, "You have been outbid. <a href=\"auction.jsp?productId=" +  auctionID + "  \">Click here to go to the auction page.</a>");
						alertPS.executeUpdate();
					}
			}
			response.sendRedirect("ViewAuction.jsp?auctionID=" + auctionID);
			auctionPS.close();
			bidPS.close();
			autoBidPS.close();
			bidHistoryPS.close();
			try { alertPS.close(); } catch(NullPointerException exception) { }
			conn.close();
		}
		catch(Exception exception) {
			exception.printStackTrace();
		}
	}


}
