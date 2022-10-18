package com.company.hw4;


import java.util.LinkedList;

public class Router extends LinkedList<Packet> {
    public static void main(String[] args) {
        Router object = new Router(3);
        object.enqueue(new Packet(10, 2));
        object.enqueue(new Packet(10, 2));
        object.enqueue(new Packet(10, 2));
        object.enqueue(new Packet(10, 2));
        object.enqueue(new Packet(10, 2));
        object.enqueue(new Packet(10, 2));

        System.out.println(object.size());
    }

    int maxBuffer;

    public Router(int maxBuffer) {
        this.maxBuffer = maxBuffer;

    }

    // adds a new Packet to the end of the router
    // buffer.
    public void enqueue(Packet p) {
        this.add(p);
    }

    //- removes the first Packet in the router buffer.
    public Packet dequeue() {
        return this.removeFirst();
    }

    public void setMaxBuffer(int maxBuffer) {
        this.maxBuffer = maxBuffer;
    }

    public int getMaxBuffer() {
        return maxBuffer;
    }

    public static int sendPacketTo(Router[] routers, int maxBuffer) throws FullBufferException {
        int indexOfLeastFullRouter = -1;
        int minNumPackets = maxBuffer;
        for (int i = 0; i < routers.length; i++) {
            if (routers[i].size() < minNumPackets) {
                minNumPackets = routers[i].size();
                indexOfLeastFullRouter = i;
            }
        }
        if (indexOfLeastFullRouter == -1) {
            throw new FullBufferException();
        }
        return indexOfLeastFullRouter;
    }

    public boolean isFull() {
        return this.size() >= getMaxBuffer();
    }

    // Checks if router is not empty and the packet at the head of the queue
    // is not ready to send to destination before decrementing the time to
    // destination by 1.
    public void advanceTime() {
        Packet packet = this.peek();
        if (!this.isEmpty() && packet.getTimeToDest() > 0)
            packet.setTimeToDest(packet.getTimeToDest() - 1);
    }
    // Returns as {[id, timeArrive, timeToDest]}, {[...}-]},...
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");
        if(!this.isEmpty()) {
            for (int i = 0; i < this.size() - 2; i++) {
                stringBuilder.append(this.get(i).toString()).append(", ");
            }
            if (!this.isEmpty())
                stringBuilder.append(this.getLast().toString()).append("}");
        }
        else
            stringBuilder.append(" }");
        return stringBuilder.toString();
    }


}
