package com.company.hw4;

public class Packet {
    public static void main(String[] args) {

    }

    private static int packetCount = 0;
    private int id;
    private int packetSize;
    private int timeArrive;
    private int timeToDest;

    public Packet() {
        this.id = packetCount;
        this.packetSize = 0;
        this.timeArrive = 0;
        timeToDest = 0;
        packetCount++;
    }

    public Packet( int packetSize, int timeArrive) {
        this.id = packetCount;
        this.timeArrive = timeArrive;
        this.packetSize = packetSize;
        timeToDest = packetSize / 100;
        packetCount++;
    }

    public static int getPacketCount() {
        return packetCount;
    }

    public static void setPacketCount(int packetCount) {
        Packet.packetCount = packetCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPacketSize() {
        return packetSize;
    }

    public void setPacketSize(int packetSize) {
        this.packetSize = packetSize;
    }

    public int getTimeArrive() {
        return timeArrive;
    }

    public void setTimeArrive(int timeArrive) {
        this.timeArrive = timeArrive;
    }

    public int getTimeToDest() {
        return timeToDest;
    }

    public void setTimeToDest(int timeToDest) {
        this.timeToDest = timeToDest;
    }

    @Override
    public String toString() {
        return "[" + id + ", " + timeArrive + ", " + timeToDest + "]";
    }
}
