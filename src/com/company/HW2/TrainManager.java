/**
 * This <Code>TrainManager</Code> class runs a menu-driven application that
 *  * allows a user to interact with a TrainLinkedList object based on the
 *  commands provided.
 *
 * @author Zaiyad Munair Rahman
 * SBU ID: 114578879
 * CSE 214.01
 */

package com.company.HW2;

import java.util.Scanner;

public class TrainManager {
    public static void main(String[] args) {
        /**
         * The main method runs a menu driven application which first creates
         * an empty TrainLinkedList object. The program prompts the user for
         * a command to execute an operation. Once a command has been chosen,
         * the program may ask the user for additional information if
         * necessary, and performs the operation. The operations should be
         * defined as follows:
         *
         * F - Move Cursor Forward
         * Moves the cursor forward one car (if a next car exists).
         *
         * B - Move Cursor Backward
         * Moves the cursor backward one car (if a previous car exists).
         *
         * I - Insert Car After Cursor <Length> <Weight>
         * Inserts a new empty car after the cursor. If the cursor is null (i
         * .e. the train is empty), the car is set as the head of the train.
         * After insertion, the cursor is set to the newly inserted car.
         *
         * R - Remove Car At Cursor
         * Removes the car at current position of the cursor. After deletion,
         * the cursor is set to the next car in the list if one exists,
         * otherwise the previous car. If there is no previous car, the list
         * is empty and the cursor is set to null.
         *
         * L - Set Load At Cursor <Name> <Weight> <Value> <Is Dangerous>
         * Sets the product load at the current position in the list.
         * S - Search For Product <name>
         * Searches the train for all the loads with the indicated name and
         * prints out the total weight and value, and whether the load is
         * dangerous or not. If the product could not be found, indicate to
         * the user that the train does not contain the indicated product.
         *
         * T - Print Train
         * Prints the String value of the train to the console.
         *
         * M - Print Manifest
         * Prints the train manifest - the loads carried by each car.
         *
         * D - Remove Dangerous Cars
         * Removes all the dangerous cars from the train.
         *
         * Q - Quit
         * Terminates the program.
         */

        /**
         *Used to track user input.
         */
        Scanner input = new Scanner(System.in);

        /**
         * Default TrainLinkedList object for the menu.
         */

        TrainLinkedList linkedList = new TrainLinkedList();
    /**
     * Stores the choice the user chooses for the menu.
     */
        String choice;

        /**
         * Prints the menu upon running the program.
         */
        printMenu();

        /**
         * Do-while loops the menu after every choice is executed, until the
         * user enters 'Q' to quit the program.
         */
        do {
            choice = input.nextLine();

            /**
             * Switch statement to execute different arguments depending on
             * the user's input.
             */
            switch (choice.toUpperCase()) {
                case "F" -> {
                    try {
                        linkedList.cursorForward();
                        System.out.println("Cursor points forward.");
                        printMenu();
                    }
                    catch (NullPointerException exception) {
                        System.out.println("Cursor is at end of the list.");
                        printMenu();
                    }
                }

                case "B" -> {
                    try {
                    linkedList.cursorBackward();
                    System.out.println("Cursor moved backward.");
                    printMenu();
                    }
                    catch (NullPointerException exception) {
                        System.out.println("Cursor cannot go any further " +
                                "backward.");
                        printMenu();
                    }
                }

                case "I" -> {
                    try {
                        System.out.println("Enter length: ");
                        double length = input.nextDouble();
                        System.out.println("Enter weight: ");
                        double weight = input.nextDouble();

                        if (length < 0 || weight < 0)
                            throw new IllegalArgumentException("Value cannot be " +
                                    "negative.");
                        linkedList.insertAfterCursor(new TrainCar(length, weight,
                                new ProductLoad()));

                        System.out.println("Train car of " + length + "m and " +
                                weight +
                                " tons has been added successfully to the list.");
                        printMenu();
                    }
                    catch (IllegalArgumentException ex) {
                        System.out.println("Values cannot be negative.");
                        printMenu();
                    }
                }

                case "R" -> {
                    try {
                        System.out.printf("%-15s%-14s%-12s%9s", "Name"
                                , "Weight (t)", "Value ($)", "Dangerous\n" +
                                        "========================================" +
                                        "=========\n");
                        System.out.println(linkedList.removeCursor().getLoad() +
                                "\n" + "Has been successfully removed.");
                        printMenu();
                    }
                    catch (NullPointerException ex) {
                        System.out.println("Cursor is pointing at nothing to " +
                                "delete.");
                        printMenu();
                    }
                }

                case "L" -> {
                    try {
                        System.out.println("Enter name: ");
                        String name = input.nextLine();
                        System.out.println("Enter weight: ");
                        double weight = input.nextDouble();
                        linkedList.setTrainWeight(linkedList.getTrainWeight() +
                                weight);
                        System.out.println("Enter value: ");
                        double value = input.nextDouble();
                        linkedList.setTrainValue(linkedList.getTrainValue() + value);
                        System.out.println("Is load dangerous? Enter true or " +
                                "false.");
                        boolean isDangerous = input.nextBoolean();
                        linkedList.setDangerous(isDangerous);
                        linkedList.getCursorData().setLoad(new ProductLoad(name,
                                weight, value, isDangerous));

                        System.out.println("Load added successfully.");
                        printMenu();
                    }
                    catch (IllegalArgumentException ex) {
                        System.out.println("Values cannot be negative.");
                        printMenu();
                    }
                }

                case "S" -> {
                    System.out.println("Enter name: ");
                    String name = input.nextLine();
                    linkedList.findProduct(name);
                    printMenu();

                }

                case "T" -> {
                    System.out.println(linkedList);
                    printMenu();
                }

                case "M" -> {
                    linkedList.printManifest();
                    printMenu();
                }

                case "D" ->{
                    linkedList.removeDangerous();
                    System.out.println("Dangerous cars deleted successfully.");
                    printMenu();
                }
            }
        }
        while (!choice.equalsIgnoreCase("Q"));

    }

    /**
     * Method to print the menu to the console.
     */
    public static void printMenu() {
        System.out.print("\n" +
                "    F - Move Cursor Forward\n" +
                "    B - Move Cursor Backward\n" +
                "    I - Insert Car After Cursor <Length> <Weight>\n" +
                "    R - Remove Car At Cursor\n" +
                "    L - Set Load At Cursor <Name> <Weight> <Value> <Is Dangerous>\n" +
                "    S - Search For Product <name>\n" +
                "    T - Print Train\n" +
                "    M - Print Manifest\n" +
                "    D - Remove Dangerous Cars\n" +
                "    Q - Quit\n");
    }
}
