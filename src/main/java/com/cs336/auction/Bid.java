package com.cs336.auction;

import java.util.Date;

public class Bid {

	private int bidID;		//PK
	private String buyer;	//FK from user
	private int auctionID;	//FK from auction
	private Date timeOfBid;
	private float amountOfBid;
	
	public Bid (int bidID, String buyer, int auctionID, Date timeOfBid, float amountOfBid) {
		this.bidID = bidID;
		this.buyer = buyer;
		this.auctionID = auctionID;
		this.timeOfBid = timeOfBid;
		this.amountOfBid = amountOfBid;
	}

	public int getBidID() {
		return bidID;
	}

	public void setBidID(int bidID) {
		this.bidID = bidID;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public int getAuctionID() {
		return auctionID;
	}

	public void setAuctionID(int auctionID) {
		this.auctionID = auctionID;
	}

	public Date getTimeOfBid() {
		return timeOfBid;
	}

	public void setTimeOfBid(Date timeOfBid) {
		this.timeOfBid = timeOfBid;
	}

	public float getAmountOfBid() {
		return amountOfBid;
	}

	public void setAmountOfBid(float amountOfBid) {
		this.amountOfBid = amountOfBid;
	}
}
