/**
 * This <code>Simulator</code> Class contains the main method that tests
 * a simulation of routers.
 *
 * @author Zaiyad Munair Rahman
 * SBU ID: 114578879
 * CSE 214.01
 */

package com.company.hw4;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Simulator {
    /**
     * the main() method will prompt the user for inputs to the simulator. It
     * will then run the simulator, and outputs the result. Prompt the user
     * whether he or she wants to run another simulation.
     */
    public static void main(String[] args) {
        // pre loop
        Scanner input = new Scanner(System.in);
        String choice;

        // loop
        do {
            System.out.println("Starting simulator...");
            Simulator sim = new Simulator();

            double arrivalProb;
            int maxBufferSize;
            int maxPacketSize;
            int minPacketSize;
            int duration;
            int numIntRouters;
            int bandwidth;

            try {
                System.out.println("Enter the number of Intermediate routers: ");
                sim.setNumIntRouters(input.nextInt());
                System.out.println("Enter the arrival probability of a packet: ");
                sim.setArrivalProb(input.nextDouble());
                System.out.println("Enter the maximum buffer size of a router: ");
                sim.setMaxBufferSize(input.nextInt());
                System.out.println("Enter the minimum size of a packet: ");
                sim.setMinPacketSize(input.nextInt());
                System.out.println("Enter the maximum size of a packet: ");
                sim.setMaxPacketSize(input.nextInt());
                System.out.println("Enter the bandwidth size: ");
                sim.setBandwidth(input.nextInt());
                System.out.println("Enter the simulation duration: ");
                sim.setDuration(input.nextInt());
            } catch (InputMismatchException e) {
                if (!e.getMessage().isBlank()) {
                    System.out.println(e.getMessage());
                } else {
                    System.out.println("Values cannot be negative.");
                }
            }

            // run the simulation
            double average = sim.simulate();
            int totalServiceTime = sim.getTotalServiceTime();
            int totalPacketsArrived = sim.getTotalPacketsArrived();
            int packetsDropped = sim.getPacketsDropped();

            // end of loop
            // print result
            System.out.println("Simulation ending...");
            System.out.println("Total service time: " + totalServiceTime);
            System.out.println("Total packets served: " + totalPacketsArrived);
            System.out.println("Average service time per packet: " + average);
            System.out.println("Total packets dropped: " + packetsDropped);


            // confirm repeat
            System.out.println("Do you want to try another simulation? (y/n):" +
                    " ");
            choice = input.next();

        } while (!choice.equalsIgnoreCase("n"));

        // post loop
        System.out.println("Program terminating successfully...");
    }

    private int numIntRouters;
    private int maxBufferSize;
    private int minPacketSize;
    private int maxPacketSize;
    private int bandwidth;
    private int duration;
    private double arrivalProb;
    private int packetsDropped;
    private int totalServiceTime;
    private int totalPacketsArrived;
    static int MAX_PACKETS = 3;
    double avgTime;
    ArrayList<Packet> waitingQueue = new ArrayList<>();

    /**
     * Default constructor for a Simulator object that assigns all values to
     * 0.
     */
    public Simulator() {
        numIntRouters = 0;
        maxBufferSize = 0;
        minPacketSize = 0;
        maxPacketSize = 0;
        bandwidth = 0;
        duration = 0;
        arrivalProb = 0;
        packetsDropped = 0;
        totalServiceTime = 0;
        totalPacketsArrived = 0;
        avgTime = 0;
    }

    /**
     * Runs the simulator. Packets first arrive at the Dispatcher router.
     * Each packet can arrive at a probability, prob, that is determined by
     * the user. For each simulation time unit, a maximum of 3 packets can
     * arrive at the Dispatcher. When a Packet arrives at the Dispatcher, a
     * new Packet object should be created, and its data fields will be
     * determined systematically based on rules we will describe later in
     * the Packet class. On the same time unit, the Dispatcher will decide
     * which Intermediate router a specified packet should be forwarded to.
     * You are expected to write a simple algorithm that determines the
     * routing table. (Algorithm specified later in the specs.)
     * When a Packet arrives at an Intermediate router, it is placed onto its
     * corresponding queue. Only the first packet in the queue will be
     * processed, while the others remain in the queue. However, if the queue
     * is full at the time a packet comes in, we consider that as a buffer
     * overflow, and the network will drop this packet. Once the router finds
     * that a packet is ready to be sent, it will forward it to its final
     * destination.
     * The final destination receives all incoming packets from the
     * Intermediate routers. However, due to limited bandwidth, the
     * destination router can only accept a limited amount of packets,
     * which is determined by the user.
     *
     * @return The average time each packet spends within the network.
     */
    public double simulate() {
        Packet.setPacketCount(0);
        Router dispatch = new Router(MAX_PACKETS, -1);
        Router[] routers = new Router[getNumIntRouters()];
        for (int i = 0; i < routers.length; i++) {
            routers[i] = new Router(maxBufferSize, i);
        }
        int currentTime = 0;
        int usedBandwidth = 0;
        int packetsCreated;
        while (currentTime <= duration) {
            packetsCreated = 0;
            int packetSize;
            System.out.println("Time: " + currentTime);
            // Creates new packets in the dispatcher
            dispatch.clear();
            for (int i = 0; i < MAX_PACKETS; i++) {
                packetSize = randInt(minPacketSize, maxPacketSize);
                if (packetSize > getMinPacketSize()) {
                    Packet packet = new Packet(packetSize, currentTime);
                    System.out.println("Packet " + packet.getId() +
                            " arrives at dispatcher with size " + packetSize + ".");
                    dispatch.enqueue(packet);
                    packetsCreated++;
                }
            }
            if (packetsCreated == 0)
                System.out.println("No packets arrived.");

            //As long as the dispatcher has packets, tries to send them to
            // intermediate routers
            if (!dispatch.isEmpty()) {
                while (dispatch.size() > 0) {
                    Packet packet = dispatch.dequeue();
                    try {
                        // Identify the least "full" router that has capacity
                        // for another packet
                        int routerIndex = Router.sendPacketTo(routers);
                        System.out.println("Packet " + packet.getId() + " sent " +
                                "to router " + routerIndex + ".");
                        routers[routerIndex].enqueue(packet);
                    } catch (FullBufferException e) {
                        System.out.println("Network is congested. Packet " + packet.getId() +
                                " is dropped.");
                        setPacketsDropped(getPacketsDropped() + 1);
                    }
                }
            }

            // Send any packets at the heads of each router to
            // destination if any are ready and bandwidth allows it
            sendToDestination(routers, usedBandwidth, currentTime);

            for (Router router : routers) {
                // "advance time" by decrementing the timeToDest of the frontmost packet per
                // router
                router.advanceTime();
            }
            for (int i = 0; i < routers.length; i++) {
                System.out.println("R" + (i + 1) + ": " + routers[i].toString());
            }

            //Ends current time loop
            currentTime++;
        }

        avgTime = (double) getTotalServiceTime() / getTotalPacketsArrived();
        return avgTime;
    }


    /**
     * If an intermediate router has a packet ready to send
     * to destination, the Packet will be dequeue from the router and set the
     * total service time to the difference between currentTime and time of
     * packet creation (timeArrive) until all bandwidth is used. If there are
     * still more packets ready to send, they will be sent to waitingQueue,
     * an array list, which gets priority over the router array.
     */
    public void sendToDestination(Router[] routers, int usedBandwidth,
                                  int currentTime) {
        // Waiting Queue gets priority over intermediate routers' packets
        while (!waitingQueue.isEmpty() && usedBandwidth <= getBandwidth()) {
            waitingQueue.remove(0);
            usedBandwidth++;
            setTotalPacketsArrived(getTotalPacketsArrived() + 1);
        }
        for (Router router : routers) {
            if (!router.isEmpty()) {
                Packet packet = router.peek();
                if (packet.getTimeToDest() == 0) {
                    if (usedBandwidth < getBandwidth()) {
                        setTotalServiceTime(
                                getTotalServiceTime() + (currentTime - packet.getTimeArrive()));
                        System.out.println("Package " + packet.getId() + " " +
                                "has " +
                                " successfully arrived at destination: +" +
                                (currentTime - packet.getTimeArrive()));
                        router.dequeue();
                        usedBandwidth++;
                        setTotalPacketsArrived(getTotalPacketsArrived() + 1);
                    } else {
                        waitingQueue.add(router.dequeue());
                    }
                }
            }
        }
    }


    public int getNumIntRouters() {
        return numIntRouters;
    }

    /**
     * @param numIntRouters Number of intermediate routers.
     * @throws InputMismatchException thrown if the parameter is a negative number.
     */
    public void setNumIntRouters(int numIntRouters) throws InputMismatchException {
        if (numIntRouters >= 0) {
            this.numIntRouters = numIntRouters;
        } else
            throw new InputMismatchException();
    }

    public int getMaxBufferSize() {
        return maxBufferSize;
    }

    /**
     * @param maxBufferSize Integer to assign to the maximum number of packets the intermediate
     *                      routers can hold.
     *                      * exception InputMismatchException
     *                      * Thrown if input parameter is a negative number.
     */
    public void setMaxBufferSize(int maxBufferSize) throws InputMismatchException {
        if (maxBufferSize >= 0) {
            this.maxBufferSize = maxBufferSize;
        } else
            throw new InputMismatchException();
    }

    public int getMinPacketSize() {
        return minPacketSize;
    }

    /**
     * @param minPacketSize Integer to assign as the minimum size of the data in the packets.
     * @throws InputMismatchException Thrown if input parameter is a negative number.
     */
    public void setMinPacketSize(int minPacketSize) throws InputMismatchException {
        if (minPacketSize >= 0) {
            this.minPacketSize = minPacketSize;
        } else
            throw new InputMismatchException();
    }

    public int getMaxPacketSize() {
        return maxPacketSize;
    }

    /**
     * @param maxPacketSize Integer to assign as the maximum size of the data in the packets.
     * @throws InputMismatchException Thrown if input parameter is a negative number.
     */
    public void setMaxPacketSize(int maxPacketSize) throws InputMismatchException {
        if (maxPacketSize >= 0) {
            this.maxPacketSize = maxPacketSize;
        } else
            throw new InputMismatchException();
    }

    /**
     * @return Bandwidth of the network, i.e. the number of packets that can be sent
     * to destination in 1 time unit.
     */
    public int getBandwidth() {
        return bandwidth;
    }

    /**
     * @param bandwidth Integer to assign as the number of that can be sent
     *                  to destination in 1 time unit.
     * @throws InputMismatchException Thrown if input parameter is a negative number.
     */
    public void setBandwidth(int bandwidth) throws InputMismatchException {
        if (bandwidth >= 0) {
            this.bandwidth = bandwidth;
        } else
            throw new InputMismatchException();
    }

    public int getDuration() {
        return duration;
    }

    /**
     * @param duration Integer to set as the number of time units to be simulated.
     * @throws InputMismatchException Thrown if input parameter is a negative number.
     */
    public void setDuration(int duration) throws InputMismatchException {
        if (duration >= 0) {
            this.duration = duration;
        } else
            throw new InputMismatchException();
    }

    /**
     * @return Number of packets unable to reach destination due to a congested network.
     */
    public int getPacketsDropped() {
        return packetsDropped;
    }

    /**
     * @param packetsDropped Integer to assign as the number of packets unable to reach destination
     *                       due to a congested network.
     */
    public void setPacketsDropped(int packetsDropped) {
        this.packetsDropped = packetsDropped;
    }

    /**
     * @return The sum of the time it took for each sent packet to reach the
     * destination.
     */
    public int getTotalServiceTime() {
        return totalServiceTime;
    }

    /**
     * @param totalServiceTime Integer to assign as the sum of the time it took for each sent packet
     *                         to reach the destination.
     */
    public void setTotalServiceTime(int totalServiceTime) {
        this.totalServiceTime = totalServiceTime;
    }

    /**
     * @return Total number of packets that reached the destination.
     */
    public int getTotalPacketsArrived() {
        return totalPacketsArrived;
    }

    /**
     * @param totalPacketsArrived Integer to assign as the total number of packets that reached the
     *                            destination.
     */
    public void setTotalPacketsArrived(int totalPacketsArrived) {
        this.totalPacketsArrived = totalPacketsArrived;
    }

    /**
     * @param arrivalProb Double value between 0 and 1 set by the user as the probability of a
     *                    packet's arrival.
     * @throws InputMismatchException Thrown if input parameter is a negative number.
     */
    public void setArrivalProb(double arrivalProb) throws InputMismatchException {
        if (arrivalProb >= 0 & arrivalProb <= 1) {
            this.arrivalProb = arrivalProb;
        } else {
            throw new InputMismatchException("Arrival probability cannot be " +
                    "greater than 1 or less than 0.");
        }
    }

    /**
     * @return Double value between 0 and 1 set by the user as the probability of a
     * * packet's arrival.
     */
    public double getArrivalProb() {
        return arrivalProb;
    }

    /**
     * Uses Math.random() to generate a random number within the maximum and
     * minimum value as assigned by the user. If the number is less than the
     * probability assigned by user, it returns the number.
     *
     * @param minVal Minimum size of the packet as assigned by the user.
     * @param maxVal maximum size of the packet as assigned by the user.
     * @return A random number within the range of the assigned limits. If the number
     * is not within the range, returns -1.
     */

    public int randInt(int minVal, int maxVal) {
        double random = Math.random();
        if (random < getArrivalProb()) {
            int output = (int) (random * (maxVal - minVal)) + minVal;
            return output;
        }
        return -1;
    }
}
