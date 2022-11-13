package com.company.hw6;

import java.io.Serializable;

public class Auction implements Serializable {
    public static void main(String[] args) {

    }

    private int timeRemaining;
    private double currentBid;
    private String auctionID;
    private String sellerName;
    private String buyerName;
    private String itemInfo;

    /**
     * Default constructor for the Auction class.
     */
    public Auction() {
        this.timeRemaining = 0;
        this.currentBid = 0;
        this.auctionID = "";
        this.sellerName = "";
        this.buyerName = "";
        this.itemInfo = "";
    }

    /**
     * Constructor for the Auction class.
     * @param timeRemaining
     * The time remaining in the auction.
     * @param currentBid
     * The current bid in the auction.
     * @param auctionID
     * The ID of the auction.
     * @param sellerName
     * The name of the seller.
     * @param buyerName
     * The name of the buyer.
     * @param itemInfo
     * The information about the item being auctioned.
     */

    public Auction(int timeRemaining, double currentBid, String auctionID,
                   String sellerName, String buyerName, String itemInfo) {
        this.timeRemaining = timeRemaining;
        this.currentBid = currentBid;
        this.auctionID = auctionID;
        this.sellerName = sellerName;
        this.buyerName = buyerName;
        this.itemInfo = itemInfo;
    }

    public void decrementTimeRemaining(int time) {
        if(timeRemaining - time < 0) {
            timeRemaining = 0;
        } else {
            timeRemaining -= time;
        }
    }

    public void newBid(String bidderName, double bidAmount) throws ClosedAuctionException {
        if(timeRemaining < 0)
            throw new ClosedAuctionException("Auction is closed.");

        else if (bidAmount > getCurrentBid()) {
            buyerName = bidderName;
            currentBid = bidAmount;
        }
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public double getCurrentBid() {
        return currentBid;
    }

    public String getAuctionID() {
        return auctionID;
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getItemInfo() {
        return itemInfo;
    }

    @Override
    public String toString() {
         return String.format("%-13s%-13s%-24s%-26s%-12s%-43s", auctionID,
               "|" + currentBid, "|" + sellerName, "|" + buyerName,
                 "|" + timeRemaining, "|" + itemInfo);
    }
}
