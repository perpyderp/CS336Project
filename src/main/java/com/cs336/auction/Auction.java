package com.cs336.auction;

import java.util.Date;
import com.cs336.user.User;
import java.util.ArrayList;

public class Auction {

	private int aucid;				//Primary key
	private Date start_Time;
	private Date close_Time;
	private String description;
	private User seller;			
	private float initial_Price;
	private float highest_Bid;
	private float next_Min_Bid;
	
	public Auction(Date start_Time, Date close_Time, String description, User seller, float initial_Price) {
		this.start_Time = start_Time;
		this.close_Time = close_Time;
		this.description = description;
		this.seller = seller;
		this.initial_Price = initial_Price;
	}
	
	public Auction() {}
	
	public Date getStart_Time() { return start_Time; }

	public void setStart_Time(Date start_Time) { this.start_Time = start_Time; }

	public Date getClose_Time() { return close_Time; }

	public void setClose_Time(Date close_Time) { this.close_Time = close_Time; }

	public String getDescription() { return description; }

	public void setDescription(String description) { this.description = description; }

	public User getSeller() { return seller; }

	public void setSeller(User seller) { this.seller = seller; }

	public float getInitial_Price() { return initial_Price; }

	public void setInitial_Price(float initial_Price) { this.initial_Price = initial_Price; }

	public float getHighest_Bid() { return highest_Bid; }

	public void setHighest_Bid(float highest_Bid) { this.highest_Bid = highest_Bid; }

	public float getNext_Min_Bid() { return next_Min_Bid; }

	public void setNext_Min_Bid(float next_Min_Bid) { this.next_Min_Bid = next_Min_Bid; }
	
	
}
