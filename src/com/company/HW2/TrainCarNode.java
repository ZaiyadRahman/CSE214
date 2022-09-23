/**
 * This <code>TrainCarNode</code> class acts as a node wrapper around a
 * TrainCar object. The class should contain two TrainCarNode references (one
 * for the next node in the chain, one for the previous node in the chain),
 * and one TrainCar reference variable containing the data.
 *
 * @author Zaiyad Munair Rahman
 * SBU ID: 114578879
 * CSE 214.01
 */

package com.company.HW2;

public class TrainCarNode {

    private TrainCarNode prev;
    private TrainCarNode next;
    private TrainCar car;

    /**
     * Constructs an empty TrainCarNode object with all fields null.
     */

    public TrainCarNode() {}

    /**
     * Constructs a TrainCarNode with a TrainCar object reference.
     * @param car
     * The TrainCar object the TrainCarNode is referencing to.
     */
    public TrainCarNode(TrainCar car) {
        this.car = car;
        this.prev = null;
        this.next = null;

    }

    /**
     * @return
     * reference for the node before the current TrainCarNode.
     */
    public TrainCarNode getPrev() {
        return prev;
    }

    /**
     * @param prev
     * TrainCarNode to be set behind the current TrainCarNode.
     */
    public void setPrev(TrainCarNode prev) {
        this.prev = prev;
    }

    /**
     * @return
     * reference for the node next to the current TrainCarNode.
     */
    public TrainCarNode getNext() {
        return next;
    }

    /**
     * @param next
     * TrainCarNode to be set ahead of the current TrainCarNode.
     */
    public void setNext(TrainCarNode next) {
        this.next = next;
    }

    /**
     * @return
     * Car object the TrainCarNode is referring to.
     */
    public TrainCar getCar() {
        return car;
    }

    /**
     * @param car
     * Sets the TrainCarNode object's TrainCar reference.
     */
    public void setCar(TrainCar car) {
        this.car = car;
    }

    /**
     * Returns a neatly formatted String representation of the TrainCarNode.
     * @return
     * A neatly formatted string containing information about the train car,
     * including the car's weight, length, and details about the car's load.
     */
    @Override
    public String toString() {
        StringBuilder stringOutput = new StringBuilder(String.format("%-14s" +
                        "%-12s%-15s", this.car.getCarLength(),
                this.car.getCarWeight(), "|" + this.car.getLoad()));
        return stringOutput.toString();
    }
}
