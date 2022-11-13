package com.company.hw6;

import java.io.Serializable;
import java.util.Scanner;

public class AuctionSystem implements Serializable {
    public static void main(String[] args) {
        if(prev table exists) {

        }

        else {
            setAuctionTable(new AuctionTable());
            table.buildFromURL(foo);
        }

        Scanner input = new Scanner(System.in);
        String choice;

        do {
            switch (choice.toUpperCase()) {
                case "D" -> {
                    System.out.println("Enter URL: ");
                    String URL = input.nextLine();
                    auctionTable.buildFromURL(URL);
                }

                case "A" -> {
                    System.out.println("Creating new auction as " + getusername() + ".");
                    System.out.println("Please enter auction ID: ");
                    String auctionID = input.nextLine();
                    System.out.println("Please enter an auction time: ");
                    int auctionTime = input.nextInt();
                    System.out.println("Please enter some auction info: ");
                    String auctionInfo = input.nextLine();
                    auctionTable.putAuction(auctionID,
                            new Auction(auctionTime, -1, auctionID,
                                    getUsername(), null, auctionInfo));

                }
            }
        }

    }

    private AuctionTable auctionTable;
    private String username;

    public AuctionTable getAuctionTable() {
        return auctionTable;
    }

    public void setAuctionTable(AuctionTable auctionTable) {
        this.auctionTable = auctionTable;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
