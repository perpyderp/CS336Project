package com.cs336.auction;

import java.util.Date;

public class Auction {

	private int aucid;				//Primary key
	private String item_Name;
	private String category;
	private Date start_Time;
	private Date close_Time;
	private String description;
	private String seller;				//foreign key from User			
	private float initial_Price;
	private float hiddenMinPrice;
	private float highest_Bid;
	private float minIncrement;
	private boolean sold;
	
	public Auction(int aucid, String seller, Date start_Time, Date close_Time, String description, float initial_Price, String item_Name, String category, boolean sold, float highest_Bid, float hiddenMinPrice, 
			float minIncrement) {
		this.item_Name = item_Name;
		this.category = category;
		this.aucid = aucid;
		this.seller = seller;
		this.start_Time = start_Time;
		this.close_Time = close_Time;
		this.description = description;
		this.initial_Price = initial_Price;
		this.setSold(sold);
		this.highest_Bid = highest_Bid;
		this.hiddenMinPrice = hiddenMinPrice;
		this.minIncrement = minIncrement;
	}
	
	public Auction() {}
	
	public int getAucid() { return aucid; }

	public void setAucid(int aucid) { this.aucid = aucid; }

	public String getItem_Name() { return item_Name; }

	public void setItem_Name(String item_Name) { this.item_Name = item_Name; }

	public String getCategory() { return category; }

	public void setCategory(String category) { this.category = category; }

	public String getSeller() { return seller; }

	public void setSeller(String seller) { this.seller = seller; }
	
	public int getAuctionID() { return aucid; }
	
	public Date getStart_Time() { return start_Time; }

	public void setStart_Time(Date start_Time) { this.start_Time = start_Time; }

	public Date getClose_Time() { return close_Time; }

	public void setClose_Time(Date close_Time) { this.close_Time = close_Time; }

	public String getDescription() { return description; }

	public void setDescription(String description) { this.description = description; }

	public String getUserID() { return seller; }

	public void setUserID(String seller) { this.seller = seller; }

	public float getInitial_Price() { return initial_Price; }

	public void setInitial_Price(float initial_Price) { this.initial_Price = initial_Price; }

	public float getHighest_Bid() { return highest_Bid; }

	public void setHighest_Bid(float highest_Bid) { this.highest_Bid = highest_Bid; }

	public float getMinIncrement() { return minIncrement; }

	public void setMinIncrement(float minIncrement) { this.minIncrement = minIncrement; }
	
	public boolean isSold() { return sold; }

	public void setSold(boolean sold) { this.sold = sold; }

	public float getHiddenMinPrice() { return hiddenMinPrice; }
	
	
	@Override
	public String toString() {
		return "Auction #" + aucid + 
					"\n\t Start Time: " + start_Time +
					"\n\t Close Time: " + close_Time +
					"\n\t Initial Price: " + initial_Price +
					"\n\t Minimum Bid Increment: " + minIncrement +
					"\n\t Highest Bid: " + highest_Bid +
					"\n\t Description: " + description +
					"\n\t Seller: " + seller;
	}
}
