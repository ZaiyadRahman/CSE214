/**
 * This <code>Packet</code> Class represents a packet that will be sent
 * through the network.
 *
 * @author Zaiyad Munair Rahman
 * SBU ID: 114578879
 * CSE 214.01
 */

package com.company.hw4;

public class Packet {
    public static void main(String[] args) {

    }

    private static int packetCount = 0;
    private int id;
    private int packetSize;
    private int timeArrive;
    private int timeToDest;

    /**
     * Default constructor for a Packet with null values.
     */
    public Packet() {
        this.id = packetCount;
        this.packetSize = 0;
        this.timeArrive = 0;
        timeToDest = 0;
        packetCount++;
    }

    /**
     * Overloaded constructor for a Packet object.
     *
     * @param packetSize the size of the packet being sent. This value is randomly determined
     *                   by the simulator by using the Math.random() method.
     * @param timeArrive the time this Packet is created should be recorded in this variable.
     */
    public Packet(int packetSize, int timeArrive) {
        this.id = packetCount;
        this.timeArrive = timeArrive;
        this.packetSize = packetSize;
        timeToDest = packetSize / 100;
        packetCount++;
    }

    /**
     * @return the current value of the packet counter.
     */
    public static int getPacketCount() {
        return packetCount;
    }

    /**
     * @param packetCount The packet counter integer to be assigned.
     */
    public static void setPacketCount(int packetCount) {
        Packet.packetCount = packetCount;
    }

    /**
     * @return the id of the Packet object.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id integer value to assign to a Packet as its id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return Size of the data inside the packet.
     */
    public int getPacketSize() {
        return packetSize;
    }

    /**
     * @param packetSize Integer to assign as the size of the data inside the packet.
     */
    public void setPacketSize(int packetSize) {
        this.packetSize = packetSize;
    }

    /**
     * @return The time the Packet was created.
     */
    public int getTimeArrive() {
        return timeArrive;
    }

    /**
     * @param timeArrive Integer to be assigned as the time the Packet was created.
     */
    public void setTimeArrive(int timeArrive) {
        this.timeArrive = timeArrive;
    }

    /**
     * @return Time remaining to reach the destination
     */
    public int getTimeToDest() {
        return timeToDest;
    }

    /**
     * @param timeToDest Integer to be assigned as the time remaining to reach the destination
     */
    public void setTimeToDest(int timeToDest) {
        this.timeToDest = timeToDest;
    }

    /**
     * Returns a neatly formatted string representation of the packet
     *
     * @return A neatly formatted string in the format: [id, timeArrive, timeToDest]
     */
    @Override
    public String toString() {
        return "[" + id + ", " + timeArrive + ", " + timeToDest + "]";
    }
}
