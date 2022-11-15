/**
 * This <Code>Auction</Code> class represents an active auction currently in
 * the database. It contains the information of the auction, such as
 *  the seller's name, the current bid, the time remaining (in hours),
 *  current bidder's name, information about the item, and the unique ID for
 *  the auction.
 *
 *  @author Zaiyad Munair Rahman
 *  SBU ID: 114578879
 *  CSE 214.01
 */

package com.company.hw6;

import java.io.Serializable;

public class Auction implements Serializable {

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
        this.currentBid = -1;
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

    /**
     *
     Decreases the time remaining for this auction by the specified amount.
     If time is greater than the current remaining time for the auction, then
     the time remaining is set to 0 (i.e. no negative times).
     * @param time
     * The amount of time to decrease the time remaining by.
     */
    public void decrementTimeRemaining(int time) {
        timeRemaining = Math.max(0, timeRemaining - time);
    }

    /**
     *
     Makes a new bid on this auction. If bidAmt is larger than currentBid,
     then the value of currentBid is replaced by bidAmt and buyerName is is
     replaced by bidderName.
     * @param bidderName
     * The name of the bidder.
     * @param bidAmount
     * Amount bidded by the bidder.
     * @throws ClosedAuctionException
     * Thrown if the time remaining is 0, meaning the auction is closed.
     */
    public void newBid(String bidderName, double bidAmount) throws ClosedAuctionException {
        if(bidAmount < 0)
            throw new IllegalArgumentException("Bid amount cannot be negative.");
        if(timeRemaining < 0)
            throw new ClosedAuctionException("Auction is closed.");

        else if (bidAmount > getCurrentBid()) {
            buyerName = bidderName;
            currentBid = bidAmount;
            System.out.println("Bid accepted.");
        }
    }

    /**
     * Returns whether the auction is closed or not.
     * @return
     * True if the auction is open, false otherwise.
     */
    public boolean isOpen() {
        return timeRemaining > 0;
    }

    /**
     * @return
     * The time remaining in the auction.
     */
    public int getTimeRemaining() {
        return timeRemaining;
    }

    /**
     * @return
     * The current bid in the auction.
     */
    public double getCurrentBid() {
        return currentBid;
    }

    /**
     * @return
     * The key of the auction, as a String.
     */
    public String getAuctionID() {
        return auctionID;
    }

    /**
     * @return
     * The name of the seller.
     */
    public String getSellerName() {
        return sellerName;
    }

    /**
     * @return
     * The name of the buyer.
     */
    public String getBuyerName() {
        return buyerName;
    }

    /**
     * @return
     * The information about the item being auctioned.
     */
    public String getItemInfo() {
        return itemInfo;
    }

    /**
     * A neatly formatted string that returns the string of data members in
     * table form.
     * @return
     * A neatly formatted table of the data members.
     */
    @Override
    public String toString() {
        String currentBidString;
        String itemInfoTruncated;
        if(currentBid == 0)
            currentBidString = "";
        else
            currentBidString = String.format("$%.2f", currentBid);
        if(itemInfo.length() > 43)
            itemInfoTruncated = itemInfo.substring(0, 43);
        else
            itemInfoTruncated = itemInfo;

         return String.format("%-12s%-13s%-25s%-27s%-13s%-42s", auctionID,
               "|" + currentBidString, "| " + sellerName, "| " + buyerName,
                 "| " + timeRemaining + " hours", "| " + itemInfoTruncated);
    }
}
