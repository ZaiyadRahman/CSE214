package com.company.hw6;

public class ClosedAuctionException extends Exception {
    public ClosedAuctionException() {
        super("The auction is closed.");
    }

    public ClosedAuctionException(String message) {
        super(message);
    }
}
