package com.cs336.auction;



public class Item {

	private int itemID;		//Primary key
	private int aucID;		//Foreign key from Auction
	private long duration;
	private String seller;		//Foreign key from End_User
	private String itemName;
	private String category;
	private String description; //Foreign key from Auction
	
	public Item(int itemID, int aucID, long duration, String seller, String itemName, String category, String description) {
		super();
		this.itemID = itemID;
		this.aucID = aucID;
		this.duration = duration;
		this.seller = seller;
		this.itemName = itemName;
		this.category = category;
		this.description = description;
	}
	
	public int getItemID() { return itemID; }
	
	public void setItemID(int itemID) { this.itemID = itemID; }
	
	public int getAucID() { return aucID; }
	
	public void setAucID(int aucID) { this.aucID = aucID; }
	
	public long getDuration() { return duration; }
	
	public void setDuration(long duration) { this.duration = duration; }
	
	public String getSeller() { return seller; }
	
	public void setSeller(String seller) { this.seller = seller; }
	
	public String getItemName() { return itemName; }
	
	public void setItemName(String itemName) { this.itemName = itemName; }
	
	public String getCategory() { return category; }
	
	public void setCategory(String category) { this.category = category; }
	
	public String getDescription() { return description; }
	
	public void setDescription(String description) { this.description = description; }

	
}
