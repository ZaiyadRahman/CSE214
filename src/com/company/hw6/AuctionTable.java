/**
 * This <code>AuctionTable</code> class implements a database of auctions as
 * a hash table, using auctionIDs as the key for the corresponding objects.
 *
 * @author Zaiyad Munair Rahman
 * SBU ID: 114578879
 * CSE 214.01
 */

package com.company.hw6;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import big.data.DataSource;

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

    /*
    listing/seller_info/seller_name
listing/auction_info/current_bid
listing/auction_info/time_left
listing/auction_info/id_num
listing/auction_info/high_bidder/bidder_name
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
                        String.valueOf(Integer.parseInt(timeRemaining[i].split(" ")[0]) * 24);
            }

            else
                timeRemaining[i] = timeRemaining[i].split(" ")[0];

            sellerNames[i] = sellerNames[i].replace("\n", " ");
            sellerNames[i] = sellerNames[i].replace("\t", " ");
            sellerNames[i] = sellerNames[i].replace("\r", " ");
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
    public void putAuction(String auctionID, Auction auction) throws IllegalArgumentException {
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
        for(Auction auction : values()) {
            auction.decrementTimeRemaining(numHours);
        }
    }

    /**
     * Iterates over all Auction objects in the table and removes them if
     * they are expired (timeRemaining == 0).
     */

    //FIXME
    public void removeExpiredAuctions() {
        Iterator<Map.Entry<String, Auction>> iterator =
                this.entrySet().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            Map.Entry<String, Auction> entry = iterator.next();
            if(entry.getValue().getTimeRemaining() == 0) {
                iterator.remove();
            }
        }
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

// Auction ID |      Bid   |        Seller         |          Buyer          |    Time   |  Item Info // truncated to fit on one line
//===================================================================================================================================
//  511601118 | $   620.00 | cubsfantony           |  gosha555@excite.com    | 110 hours | Pentium III 933 System - 256MB PC133 SDram
//  511448507 | $   620.00 | ct-inc                |  petitjc@yahoo.com      |  54 hours | Pentium III 800EB-MHz Coppermine CPU - 256
//  511443245 | $ 1,025.00 | ct-inc                |  hsclm9@peganet.com     |  54 hours | Intel Pentium III 933EB-MHz Coppermine CPU
//  511364992 | $   610.00 | bestbuys4systems      |  wizbang4               |  53 hours | Genuine Intel Pentium III 1000MHz Processo
//  511357667 | $   535.00 | sales@ctgcom.com      |  chul2@mail.utexas.edu  |  53 hours | INTEL Pentium III 800MHz - 256MB sdram -40