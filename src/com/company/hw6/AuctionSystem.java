/**
 * This <code>AuctionSystem</code> class will allow the user to interact with
 * the database by listing open auctions, make bids on open auctions, and
 * create new auctions for different items. In addition, the class should
 * provide the functionality to load a saved (serialized) AuctionTable or
 * create a new one if a saved table does not exist.
 *
 * @author Zaiyad Munair Rahman
 * SBU ID: 114578879
 * CSE 214.01
 */

package com.company.hw6;

import java.io.*;
import java.util.Scanner;

public class AuctionSystem implements Serializable {
    /**
     * First prompts the user for a username. This is stored in username. The
     * rest of the program will be executed on behalf of this user.
     * Implemented are the following menu options:
     *
     *     (D) - Import Data from URL
     *     (A) - Create a New Auction
     *     (B) - Bid on an Item
     *     (I) - Get Info on Auction
     *     (P) - Print All Auctions
     *     (R) - Remove Expired Auctions
     *     (T) - Let Time Pass
     *     (Q) - Quit
     * @param args
     * Arguments passed to the program.
     */
    public static void main(String[] args) {
        System.out.println("Starting...");
        AuctionSystem auctionSystem = new AuctionSystem();
        try {
            if(AuctionSystem.readAuctionTable("auction.obj") == null) {
                System.out.println("No previous auction table detected \n " +
                        "creating new auction table...");
                auctionSystem.auctionTable = new AuctionTable();
            }
            else {
                System.out.println("Loading previous auction table...");
                auctionSystem.auctionTable = AuctionSystem.readAuctionTable("auction.obj");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous auction table detected. \n " +
                    "Creating new auction table...");
        }

        Scanner input = new Scanner(System.in);
        System.out.println("Please select a username: ");
        String choice = input.nextLine();
        auctionSystem.setUsername(choice);
        AuctionSystem.printMenu();
        do {
            choice = input.nextLine();
            switch (choice.toUpperCase()) {
                case "D" -> {
                    System.out.println("Enter URL: ");
                    String URL = input.nextLine();
                    System.out.println("Loading...");
                    AuctionTable temp = AuctionTable.buildFromURL(URL);
                    for (int i = 0; i < temp.size(); i++) {
                        auctionSystem.auctionTable.putAll(temp);
                    }
                    System.out.println("Auction data loaded successfully!");
                    AuctionSystem.printMenu();
                }

                case "A" -> {
                    System.out.println("Creating new auction as " + auctionSystem.username + ".");
                    System.out.println("Please enter auction ID: ");
                    String auctionID = input.nextLine();
                    System.out.println("Please enter an auction time (hours):" +
                            " ");
                    int auctionTime = input.nextInt();
                    if(auctionTime < 0) {
                        System.out.println("Invalid auction time. Auction time must be greater than 0.");
                        AuctionSystem.printMenu();
                        break;

                    }
                    System.out.println("Please enter some auction info: ");
                    input.nextLine();
                    String auctionInfo = input.nextLine();
                    auctionSystem.auctionTable.putAuction(auctionID,
                            new Auction(auctionTime, 0, auctionID,
                                    auctionSystem.username, "", auctionInfo));

                    AuctionSystem.printMenu();
                }

                case "B" -> {
                    String curBid;
                    System.out.println("Enter auction ID: ");
                    String auctionID = input.nextLine();
                    try {
                        Auction temp = auctionSystem.auctionTable.getAuction(auctionID);
                        if (temp.getCurrentBid() < 0)
                            curBid = "None";
                        else
                            curBid = String.valueOf(temp.getCurrentBid());
                        if (temp.isOpen()) {
                            System.out.println("Auction " + auctionID + " is OPEN");
                            System.out.println("    Current bid: " + curBid);
                            System.out.println("What would you like to bid?: ");
                            double bid = input.nextDouble();
                            auctionSystem.auctionTable.getAuction(auctionID).newBid(auctionSystem.username, bid);

                        } else {
                            System.out.println("Auction " + auctionID + " is CLOSED");
                            System.out.println("    Current bid: " + curBid);
                            System.out.println("You can no longer bid on this " +
                                    "item.");
                        }
                    }
                    catch(ClosedAuctionException | IllegalArgumentException e){
                            if (e.getMessage() != null)
                                System.out.println(e.getMessage());
                        }
                    catch (NullPointerException e) {
                        System.out.println("Auction " + auctionID + " does not exist.");
                    }

                        AuctionSystem.printMenu();
                }

                case "P" -> {
                    auctionSystem.auctionTable.printTable();
                    AuctionSystem.printMenu();
                }

                case "I" -> {
                    System.out.println("Please enter an auction ID: ");
                    String auctionID = input.nextLine();
                    try {
                        Auction temp = auctionSystem.auctionTable.getAuction(auctionID);
                    System.out.println("    Auction " + temp.getAuctionID() +
                            ":");
                    System.out.println("    Seller: " + temp.getSellerName());
                    System.out.println("    Buyer: " + temp.getBuyerName());
                    System.out.println("    Time: " + temp.getTimeRemaining() +
                            "hours");
                    System.out.println("    Info: " + temp.getItemInfo());
                    } catch (IllegalArgumentException | NullPointerException e) {
                        System.out.println("Auction " + auctionID + " does not exist.");
                    }

                    AuctionSystem.printMenu();
                }

                case "R" -> {
                    auctionSystem.auctionTable.removeExpiredAuctions();
                    System.out.println("All expired auctions removed.");
                    AuctionSystem.printMenu();
                }
                case "T" -> {
                    System.out.println("How many hours should pass: ");
                    int hours = input.nextInt();
                    try {
                        auctionSystem.auctionTable.letTimePass(hours);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }

                    AuctionSystem.printMenu();
                }

                case "Q" -> {
                    System.out.println("Writing Auction Table to file... ");
                    try {
                        FileOutputStream file = new FileOutputStream("auction.obj");
                        ObjectOutputStream outStream = new ObjectOutputStream(file);
                        outStream.writeObject(auctionSystem.auctionTable);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Done!");
                    System.out.println("Goodbye.");
                }

                default -> {
                    System.out.println("Invalid input. Please try again.");
                    AuctionSystem.printMenu();
                }
            }
        } while(!choice.equalsIgnoreCase("Q"));

    }

    private AuctionTable auctionTable;
    private String username;

    /**
     * Default constructor for AuctionSystem.
     */
    public AuctionSystem() {
        auctionTable = new AuctionTable();
        username = "";
    }

    /**
     * @return
     * The auction table in this auction system.
     */
    public AuctionTable getAuctionTable() {
        return auctionTable;
    }

    /**
     * @param auctionTable
     * The auction table to set in this auction system.
     */
    public void setAuctionTable(AuctionTable auctionTable) {
        this.auctionTable = auctionTable;
    }

    /**
     * @return
     * The username of the user in this auction system.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     * The username to set in this auction system.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Attempts to read an auction table from a file. If the file does not exist,
     * returns null.
     * @param filename
     * The name of the file to read from.
     * @return
     * The auction table read from the file.
     * @throws IOException
     * If the file cannot be read.
     * @throws ClassNotFoundException
     * If the file cannot be found or is not an auction table.
     */
    public static AuctionTable readAuctionTable(String filename) throws
            IOException, ClassNotFoundException {
        AuctionTable auctionTable = new AuctionTable();
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream inStream = new ObjectInputStream(file);
            auctionTable = (AuctionTable) inStream.readObject();
        return auctionTable;
    }

    public static void printMenu() {
        System.out.println("(D) - Import Data from URL\n" +
                "(A) - Create a New Auction\n" +
                "(B) - Bid on an Item\n" +
                "(I) - Get Info on Auction\n" +
                "(P) - Print All Auctions\n" +
                "(R) - Remove Expired Auctions\n" +
                "(T) - Let Time Pass\n" +
                "(Q) - Quit");
        System.out.print("Please select an option: ");
    }
}
