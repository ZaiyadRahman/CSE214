package com.company.hw6;

import java.io.Serializable;
import java.util.Hashtable;
import big.data.DataSource;

public class AuctionTable extends Hashtable<String, Auction> implements Serializable {
    public static void main(String[] args) {

    }

    public static AuctionTable buildFromURL(String URL) throws IllegalArgumentException {
        DataSource ds = DataSource.connect(URL).load();
        String[] sellers = ds.fetchStringArray("listing/seller_info" +
                "/seller_name");

    }

    public void putAuction(String auctionID, Auction auction) throws IllegalArgumentException {
        putIfAbsent(auctionID, auction);
    }

    public Auction getAuction(String auctionID) {
        return get(auctionID);
    }

    public void letTimePass(int numHours) throws IllegalArgumentException {
        for(Auction auction : values()) {
            auction.decrementTimeRemaining(numHours);
        }
    }

    public void removeExpiredAuctions() {
        for(Auction auction : values()) {
            if(auction.getTimeRemaining() == 0) {
                remove(auction.getAuctionID());
            }
        }
    }

    public void printTable() {
        System.out.println(this.toString());
    }

    @Override
    public synchronized String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%13s%13s%24s%26s%12s%43s", "Auction ID |",
                "Bid |",
                "Seller |", "Buyer |", "Time |", "Item Info")).append("\n");
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