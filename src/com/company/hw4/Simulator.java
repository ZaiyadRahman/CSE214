package com.company.hw4;

import java.util.ArrayList;
import java.util.Scanner;

/*
 * TODO:
 *  Ensure mathematical accuracy (ie average)
 *      Use step / debugger / breakpoints
 *  LITERALLY diff the sample output string with ours for PERFECTION
 *  Ensure removal of debug bullshit after hardcode stuff works
 *  Efficiency
 * */

public class Simulator {
    public static void main(String[] args) {
//        pre loop
        Scanner input = new Scanner(System.in);
        String choice;

//        loop
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

            boolean debug = true;

            if (debug) {
                arrivalProb = .5;
                maxBufferSize = 10;
                maxPacketSize = 1500;
                minPacketSize = 500;
                duration = 26;
                numIntRouters = 4;
                bandwidth = 2;
            } else {
                System.out.println("Enter the number of Intermediate routers: ");
                numIntRouters = input.nextInt();
                System.out.println("Enter the arrival probability of a packet: ");
                arrivalProb = input.nextDouble();
                System.out.println("Enter the maximum buffer size of a router: ");
                maxBufferSize = input.nextInt();
                System.out.println("Enter the minimum size of a packet: ");
                minPacketSize = input.nextInt();
                System.out.println("Enter the maximum size of a packet: ");
                maxPacketSize = input.nextInt();
                System.out.println("Enter the bandwidth size: ");
                bandwidth = input.nextInt();
                System.out.println("Enter the simulation duration: ");
                duration = input.nextInt();
            }

            sim.setArrivalProb(arrivalProb);
            sim.setMaxBufferSize(maxBufferSize);
            sim.setMaxPacketSize(maxPacketSize);
            sim.setMinPacketSize(minPacketSize);
            sim.setDuration(duration);
            sim.setNumIntRouters(numIntRouters);
            sim.setBandwidth(bandwidth);

//            run the simulatah
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
            choice = input.nextLine();

        } while (!choice.equalsIgnoreCase("n"));

//        post loop
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


    public double simulate() {
        Router dispatch = new Router(MAX_PACKETS);
        Router[] routers = new Router[numIntRouters];
        for (int i = 0; i < routers.length; i++) {
            routers[i] = new Router(maxBufferSize);
        }
        int currentTime = 0;
        int usedBandwidth = 0;
        int packetsCreated;
        while (currentTime <= duration) {
            packetsCreated = 0;
            System.out.println("Time: " + currentTime);
            // Creates new packets in the dispatcher
            dispatch.clear();
            for (int i = 0; i < MAX_PACKETS; i++) {
                int packetSize = randInt(minPacketSize, maxPacketSize);
                if (packetSize > 0) {
                    Packet packet = new Packet(packetSize, currentTime);
                    System.out.println("Packet " + packet.getId() +
                            " arrives at dispatcher with size " + packetSize + ".");
                    dispatch.enqueue(packet);
                    packetsCreated++;
                }
            }
            if(packetsCreated == 0)
                System.out.println("No packets arrived.");
            //}
            //}
            //As long as the dispatcher has packets, tries to send them to
            // intermediate routers
            if (!dispatch.isEmpty()) {
                for (int i = 0; i <= dispatch.size(); i++) {
                    Packet packet = dispatch.dequeue();
                    int routerIndex = 0;
                    try {
                        // Identify the least "full" router that has capacity
                        // for another packet
                        routerIndex = Router.sendPacketTo(routers, maxBufferSize);
                    } catch (FullBufferException e) {
                        System.out.println("Network is congested. Packet " + packet.getId() +
                                " is dropped.");
                        setPacketsDropped(getPacketsDropped() + 1);
                    }
                    System.out.println("Packet " + packet.getId() + " sent " +
                            "to router " + routerIndex + ".");
                    routers[routerIndex].enqueue(packet);
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


    // If an intermediate router has a packet ready to send
    // to destination, it will set the total service time to the difference
    // between currentTime and time of packet creation (timeArrive) until all
    // bandwidth is used. If there are still more packets ready to send, they
    // will be sent to waitingQueue, an array list, which gets priority over
    // the router array.
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
                        // System.out.println(getTotalServiceTime());
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

    public void setNumIntRouters(int numIntRouters) {
        this.numIntRouters = numIntRouters;
    }

    public int getMaxBufferSize() {
        return maxBufferSize;
    }

    public void setMaxBufferSize(int maxBufferSize) {
        this.maxBufferSize = maxBufferSize;
    }

    public int getMinPacketSize() {
        return minPacketSize;
    }

    public void setMinPacketSize(int minPacketSize) {
        this.minPacketSize = minPacketSize;
    }

    public int getMaxPacketSize() {
        return maxPacketSize;
    }

    public void setMaxPacketSize(int maxPacketSize) {
        this.maxPacketSize = maxPacketSize;
    }

    public int getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(int bandwidth) {
        this.bandwidth = bandwidth;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPacketsDropped() {
        return packetsDropped;
    }

    public void setPacketsDropped(int packetsDropped) {
        this.packetsDropped = packetsDropped;
    }

    public int getTotalServiceTime() {
        return totalServiceTime;
    }

    public void setTotalServiceTime(int totalServiceTime) {
        this.totalServiceTime = totalServiceTime;
    }

    public int getTotalPacketsArrived() {
        return totalPacketsArrived;
    }

    public void setTotalPacketsArrived(int totalPacketsArrived) {
        this.totalPacketsArrived = totalPacketsArrived;
    }

    public void setArrivalProb(double arrivalProb) {
        this.arrivalProb = arrivalProb;
    }

    public double getArrivalProb() {
        return arrivalProb;
    }

    //Uses Math.random() to generate a random number within the maximum and
    // minimum value as assigned by the user. If the number is less than the
    // probability assigned by user, it returns the number.
    public int randInt(int minVal, int maxVal) {
        double random = Math.random();
        if (random < getArrivalProb()) {
            return (int) (Math.random() * (maxVal - minVal));
        }
        return -1;
    }
}
