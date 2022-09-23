/**
 * This <code>TrainLinkedList</code> class implements a Double-Linked List
 * ADT. The class should contain references to the head and tail of the list,
 * as well as a cursor variable (all TrainCarNode), and should
 * provide methods to perform insertion, deletion, search, and various other
 * operations.
 *
 * @author Zaiyad Munair Rahman
 * SBU ID: 114578879
 * CSE 214.01
 */

package com.company.HW2;

public class TrainLinkedList extends TrainCarNode {

    private TrainCarNode head = null;
    private TrainCarNode tail = null;
    private TrainCarNode cursor = null;
    private double trainLength = 0.0;
    private double trainValue = 0.0;
    private double trainWeight = 0.0;
    private int size = 0;
    private boolean isDangerous = false;

    /**
     * Constructs an empty TrainLinkedList object.
     */

    public TrainLinkedList() {}

    /**
     * @return
     * size of the linked list.
     */
    public int size() {
        return this.size;
    }

    /**
     * @return
     * Length of the train in meters.
     */
    public double getTrainLength() {
        return trainLength;
    }

    /**
     * @return
     * Total value of all ProductLoad in dollars.
     */
    public double getTrainValue() {
        return trainValue;
    }

    /**
     * @return
     * Weight of the train cars as well as ProductLoad objects in tons.
     */
    public double getTrainWeight() {
        return trainWeight;
    }

    /**
     * @return
     * Whether the train has any dangerous cargo or not.
     */
    public boolean isDangerous() {
        return isDangerous;
    }

    /**
     * Returns a reference to the TrainCar at the node currently referenced by the cursor.
     * @return
     * The reference to the TrainCar at the node currently referenced by the cursor.
     */
    public TrainCar getCursorData() {
        return  cursor.getCar();
    }

    /**
     * Places car in the node currently referenced by the cursor.
     * @param data
     * The cursor node now contains a reference to car as its data.
     */
    public void setCursorData(TrainCar data) {
        this.cursor.setCar(data);
    }

    /**
     * Assigns a length to the trainLength variable in meters.
     * @param trainLength
     * Value to be assigned to trainLength.
     */
    public void setTrainLength(double trainLength) {
        this.trainLength = trainLength;
    }

    /**
     * @param trainValue
     * Value to be assigned to trainValue in dollars.
     */
    public void setTrainValue(double trainValue) {
        this.trainValue = trainValue;
    }

    /**
     * @param trainWeight
     * Value to be assigned to trainWeight in tons.
     */
    public void setTrainWeight(double trainWeight) {
        this.trainWeight = trainWeight;
    }

    /**
     * @param dangerous
     * Value to assign to whether there is dangerous cargo on the train or not.
     */
    public void setDangerous(boolean dangerous) {
        isDangerous = dangerous;
    }

    /**
     * Moves the cursor to point at the next TrainCarNode, unless cursor is
     * already at the tail of the list.
     * @exception NullPointerException
     * thrown if cursor is already at the head of the list.
     */
    public void cursorForward() {
            if (cursor.getCar().equals(tail.getCar()))
                throw new NullPointerException("Can't go further forward.");
            else
                cursor = cursor.getNext();
    }

    /**
     * Moves the cursor to point at the previous TrainCarNode, unless cursor
     * is already at the head of the list.
     * @exception NullPointerException
     * thrown if cursor is already at the tail of the list.
     */
    public void cursorBackward() {
        if(cursor.getCar().equals(head.getCar()))
            throw new NullPointerException("Can't go further back.");
        else
            cursor = cursor.getPrev();
    }

    /**
     * Inserts a car into the train after the cursor position. Cursor now
     * references the new car.
     * @param newCar
     * The new TrainCar to be inserted into the train.
     */
    public void insertAfterCursor(TrainCar newCar) {
        TrainCarNode newNode = new TrainCarNode(newCar);
        if(head == null) {
            head = cursor = tail = newNode;
            head.setPrev(null);
        }
        else if(cursor.equals(tail)) {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
            tail.setNext(null);
            cursor = tail;
        }
        else {
            newNode.setPrev(cursor);
            newNode.setNext(cursor.getNext());
            cursorForward();
        }

        size++;
        trainLength = trainLength + newCar.getCarLength();
        trainValue = trainValue + newCar.getValue();
        trainWeight = trainWeight + newCar.getTotalWeight();
        if(newCar.isDangerous())
            setDangerous(true);
    }

    /**
     * Removes the TrainCarNode referenced by the cursor
     * @return
     * The TrainCar contained within the deleted node.
     */
    public TrainCar removeCursor() {
        if(cursor == head) {
            trainWeight = trainWeight - head.getCar().getTotalWeight();
            trainValue = trainValue - head.getCar().getValue();
            trainLength = trainLength - head.getCar().getCarLength();
            size--;
            head = head.getNext();
        }
        else if (cursor == tail) {
            tail = tail.getPrev();
            size--;
            trainWeight = trainWeight - tail.getCar().getTotalWeight();
            trainValue = trainValue - tail.getCar().getValue();
            trainLength = trainLength - tail.getCar().getCarLength();
        }
        else {
            size--;
            trainWeight = trainWeight - getCursorData().getTotalWeight();
            trainValue = trainValue - getCursorData().getValue();
            trainLength = trainLength - getCursorData().getCarLength();
            cursor.getNext().setPrev(cursor.getPrev());
            cursor.getPrev().setNext(cursor.getNext());
        }

        if(cursor.getNext() == null)
            cursorBackward();
        else
            cursorForward();

        return getCursorData();
    }

    /**
     * Searches the list for all ProductLoad objects with the indicated name
     * and sums together their weight and value (Also keeps track of whether
     * the product is dangerous or not), then prints a single ProductLoad
     * record to the console.
     * @param name
     * the name of the ProductLoad to find on the train.
     */
    public void findProduct(String name) {
        cursor = head;
        ProductLoad product = new ProductLoad();
        product.setName(name);
        for (int i = 0; i < size(); i++) {

            if (getCursorData().getLoad().getName().equals(name)) {
                product.setDangerous(getCursorData().getLoad().isDangerous());
                product.setValue(product.getValue() +
                        getCursorData().getLoad().getValue());
                product.setWeight(product.getWeight() +
                        getCursorData().getLoad().getWeight());
            }
            if(!cursor.equals(tail))
                cursorForward();
        }
            if (product.getWeight() == 0)
                System.out.println("Product not found.");

            else {
                System.out.printf("%-14s%-14s%-12s%9s", "Name", "Weight (t)",
                        "Value ($)", "Dangerous\n");
                System.out.println(
                        "==================================================");
                System.out.println(product);

            }
        }

    /**
     * Prints a neatly formatted table of the car number, car length, car
     * weight, load name, load weight, load value, and load dangerousness for
     * all the cars on the train.
     */
    public void printManifest() {
        cursor = head;
        StringBuilder newString = new StringBuilder();
        System.out.printf("%-35s%-7s" +
                        "%-8s%-14s%-12s%-15s%-14s%-12s%9s", "Car:", "Load:\n"
                , "Num", "Length (m)", "Weight (t)", "|Name"
                , "Weight (t)", "Value ($)", "Dangerous\n");
        System.out.println(
                "===================================+======================" +
                        "===========================");
        for (int i = 1; i <= size; i++) {
            newString.append("  ").append(i).append("      ").append(cursor)
                    .append("\n");
            if(!cursor.equals(tail))
                cursorForward();
        }
        System.out.println(newString);
    }

    /**
     * Removes all dangerous cars from the train, maintaining the order of
     * the cars in the train.
     */
    public void removeDangerous() {
        cursor = head;
        for (int i = 1; i <= size; i++) {
            if(getCursorData().getLoad().isDangerous())
                removeCursor();
            if(!cursor.equals(tail))
                cursorForward();
        }
        setDangerous(false);
    }

    /**
     * Returns a neatly formatted String representation of the train.
     * @return
     *
    A neatly formatted string containing information about the train,
    including its size (number of cars), length in meters, weight in tons,
    value in dollars, and whether it is dangerous or not.
     */
    @Override
    public String toString() {
        String danger;
        if(isDangerous())
            danger = "dangerous";
        else
            danger = "not dangerous";

        return "Train: " + size + " cars, " + getTrainLength() + "m " +
                "long, " + getTrainWeight() + " tons, $" + getTrainValue() +
                ", " + danger;
    }
}