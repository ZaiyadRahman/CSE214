/**
 * This <code>Router</code> Class represents a router in the network, which
 * is ultimately a linked list queue.
 *
 * @author Zaiyad Munair Rahman
 * SBU ID: 114578879
 * CSE 214.01
 */

package com.company.hw4;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class Router extends LinkedList<Packet> {
    int maxBuffer;
    int id;

    /**
     * Default constructor of the Router class.
     *
     * @param maxBuffer The maximum size of Packets the router is allowed to hold.
     */
    public Router(int maxBuffer, int id) {
        this.maxBuffer = maxBuffer;
        this.id = id;

    }

    /**
     * Inserts the specified element into the tail of the linked list queue.
     *
     * @param p Packet to be appended to the linked list queue.
     */
    // adds a new Packet to the end of the router
    // buffer.
    public void enqueue(Packet p) {
        this.add(p);
    }

    /**
     * Removes and returns the first Packet in the router.
     *
     * @return The first Packet iin this list
     */
    //- removes the first Packet in the router buffer.
    public Packet dequeue() {
        return this.removeFirst();
    }

    /**
     * @return the maximum number of Packets the router can hold.
     */
    public int getMaxBuffer() {
        return maxBuffer;
    }

    /**
     * Loops through a list Intermediate routers to find
     * the router with the most free buffer space (contains the least Packets),
     * and return the index of the router. If there are multiple routers,
     * any corresponding indices will be acceptable. If all router buffers
     * are full, throws an exception.
     *
     * @param routers Array of intermediate routers.
     *                The maximum number of Packets each router can hold.
     * @return The index of the router with the most free buffer space.
     * @throws FullBufferException Thrown if the buffers for all routers are full.
     */
    public static int sendPacketTo(Router[] routers) throws FullBufferException {
        int indexOfLeastFullRouter = -1;
        int minNumPackets = Integer.MAX_VALUE;

        for (int i = 0; i < routers.length; i++) {
            Router rt = routers[i];
            if (!rt.isFull() && rt.size() < minNumPackets) {
                minNumPackets = rt.size();
                indexOfLeastFullRouter = i;
            }
        }

        if (indexOfLeastFullRouter == -1) {
            throw new FullBufferException();
        }
        return indexOfLeastFullRouter;
    }

    /**
     * Checks whether the router object has reached the maxBuffer size.
     *
     * @return True if the router has the same number of packets as maxBuffer is.
     */
    public boolean isFull() {
        return this.size() >= getMaxBuffer();
    }

    /**
     * Checks if the router is not empty and the packet at the head of the queue
     * is not ready to send to destination before decrementing the time to
     * destination by 1.
     */
    // Checks if router is not empty and the packet at the head of the queue
    // is not ready to send to destination before decrementing the time to
    // destination by 1.
    public void advanceTime() {
        Packet packet = this.peek();
        if (!this.isEmpty() && packet.getTimeToDest() > 0)
            packet.setTimeToDest(packet.getTimeToDest() - 1);
    }

    /**
     * Returns a neatly formatted string representation of the router.
     *
     * @return Returns a String representation of the Router object in the following
     * format:
     * {[packet1], [packet2], ... , [packetN]}
     */
    public String toString() {

        return String.format("{%s}", String.join(", ",
                this.stream().map(p -> p.toString()).collect(Collectors.toList())));
    }


}
