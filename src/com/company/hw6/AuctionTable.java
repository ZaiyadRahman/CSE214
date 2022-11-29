/**
 * This <code>AuctionTable</code> class implements a database of auctions as
 * a hash table, using auctionIDs as the key for the corresponding objects.
 *
 * @author Zaiyad Munair Rahman
 * SBU ID: 114578879
 * CSE 214.01
 */

package com.company.hw6;

import big.data.DataSource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

public class AuctionTable extends Hashtable<String, Auction> implements Serializable {

    /**
     * Uses the BigData library to construct an AuctionTable from a remote data source.
     * @param URL
     * String representing the URL fo the remote data source.
     * @return
     * The AuctionTable constructed from the remote data source.
     * @throws IllegalArgumentException
     * Thrown if the URL does not represent a valid datasource (can't connect or invalid syntax).
     */
    public static AuctionTable buildFromURL(String URL) throws IllegalArgumentException {
        DataSource ds = DataSource.connect(URL).load();
        String[] sellerNames = ds.fetchStringArray("listing/seller_info" +
                "/seller_name");
        String[] currentBids = ds.fetchStringArray("listing/auction_info" +
                "/current_bid");
        String[] timeRemaining = ds.fetchStringArray("listing/auction_info" +
                "/time_left");
        String[] auctionIDs = ds.fetchStringArray("listing/auction_info" +
                "/id_num");
        String[] bidderNames = ds.fetchStringArray("listing/auction_info" +
                "/high_bidder/bidder_name");
        String[] cpu = ds.fetchStringArray("listing/item_info" +
                "/cpu");
        String[] memory = ds.fetchStringArray("listing/item_info/memory");
        String[] hardDrive = ds.fetchStringArray("listing/item_info" +
                "/hard_drive");

        ArrayList<String> itemInfo = new ArrayList<>();
        AuctionTable table = new AuctionTable();
        for (int i = 0; i < auctionIDs.length; i++) {
            if(cpu[i].isBlank())
                cpu[i] = "N/A";
            if(memory[i].isBlank())
                memory[i] = "N/A";
            if(hardDrive[i].isBlank())
                hardDrive[i] = "N/A";
            itemInfo.add(cpu[i] + " - " + memory[i] + " - " + hardDrive[i]);

            if(currentBids[i] == null || currentBids[i].equals("")) {
                currentBids[i] = "-1";
            }
            if(bidderNames[i] == null) {
                bidderNames[i] = "";
            }

            currentBids[i] = currentBids[i].replace("$", "");
            currentBids[i] = currentBids[i].replace(",", "");
            if(timeRemaining[i].contains("days")) {
                timeRemaining[i] =
                        String.valueOf(Integer.parseInt(
                                timeRemaining[i].split(" ")[0]) * 24);
            }

            else
                timeRemaining[i] = timeRemaining[i].split(" ")[0];

            sellerNames[i] = sellerNames[i].replace("\r", " ");
            sellerNames[i] = sellerNames[i].replace("\n", " ");
            sellerNames[i] = sellerNames[i].replace("\t", " ");
            Auction auction = new Auction(Integer.parseInt(timeRemaining[i]),
                    Double.parseDouble(currentBids[i]), auctionIDs[i],
                    sellerNames[i], bidderNames[i], itemInfo.get(i));
            table.put(auctionIDs[i], auction);
        }

        return table; // FIXME
    }

    /**
     * Manually posts an auction, and add it into the table.
     * @param auctionID
     * The unique key for this object.
     * @param auction
     * The auction to insert into the table with the corresponding auctionID.
     * @throws IllegalArgumentException
     * Thrown if the given auctionID is already stored in the table.
     */
    public void putAuction(String auctionID, Auction auction) throws
            IllegalArgumentException {
        putIfAbsent(auctionID, auction);
    }

    /**
     * Get the information of an Auction that contains the given ID as key
     * @param auctionID
     * the unique key for this object
     * @return
     * An Auction object with the given key, null otherwise.
     */
    public Auction getAuction(String auctionID) {
        return get(auctionID);
    }

    /**
     * Simulates the passing of time. Decrease the timeRemaining of all
     * Auction objects by the amount specified. The value cannot go below 0.
     * @param numHours
     * The number of hours to decrease the timeRemaining value by.
     * @throws IllegalArgumentException
     * Thrown if the given numHours is non positive.
     */
    public void letTimePass(int numHours) throws IllegalArgumentException {
        if(numHours <= 0) {
            throw new IllegalArgumentException("number of hours must be " +
                    "positive");
        }
        System.out.println("Time passing...");
        for(Auction auction : values()) {
            auction.decrementTimeRemaining(numHours);
        }
        System.out.println("Auction times updated.");
    }

    /**
     * Iterates over all Auction objects in the table and removes them if
     * they are expired (timeRemaining == 0).
     */

    //FIXME
    public void removeExpiredAuctions() {
        this.entrySet().removeIf(entry -> entry.getValue().getTimeRemaining() == 0);

    }

    /**
     * Prints the AuctionTable in tabular form.
     */
    public void printTable() {
        System.out.println(this);
    }

    /**
     * A neatly formatted string that returns the string of auctions in
     * table form.
     * @return
     * A neatly formatted table of Auction objects.
     */
    @Override
    public synchronized String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%12s%1s%12s%1s%24s%1s%26s%1s%12s%1s%-43s",
                "Auction ID " ,"|",
                "Bid     ", "|",
                "Seller        ", "|", "Buyer         ", "|",
                "Time    ", "|", "  Item Info ")).append("\n");
        sb.append(
                "=============================================================" +
                        "====================================================" +
                        "==================").append("\n");
        for(Auction auction : values()) {
            sb.append(auction.toString()).append("\n");
        }

        return sb.toString();
    }
}