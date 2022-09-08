/**
 * This <Code>PlannerManager</Code> class runs a menu-driven application that
 * allows a user to interact with a Planner object based on the commands
 * provided.
 *
 * @author Zaiyad Munair Rahman
 * SBU ID: 114578879
 * CSE 214.01
 */

package com.company.Lab1;

import java.util.Scanner;

public class PlannerManager {
    /**
     * The main method runs a menu-driven application which first creates an
     * empty Planner object. The program prompts the user for a command to
     * execute an operation. Once a command has been chosen, the program may
     * ask the user for additional information if necessary, and performs the
     * operation. The operations should be defined as follows:
     *
     A - Add Course <Name> <Code> <Section> <Instructor> <Position>
     Adds a new course into the list.
     G - Get Course <Position>
     Displays information of a Course at the given position.
     R - Remove Course <Position>
     Removes the Course at the given position.
     P - Print Courses in Planner
     Displays all courses in the list.
     F - Filter By Department Code <Code>
     Displays courses that match the given department code.
     L - Look For Course <Name> <Code> <Section> <Instructor>
     Determines whether the course with the given attributes is in the list.
     S - Size
     Determines the number of courses in the Planner.
     B - Backup
     Creates a copy of the given Planner. Changes to the copy will not affect
     the original and vice versa.
     PB - Print Courses in Backup
     Displays all the courses from the backed-up list.
     RB - Revert to Backup
     Reverts the current Planner to the backed up copy.
     Q - Quit
     Terminates the program.

     * @param args
     * Commands for the menu to execute.
     */
    public static void main(String[] args) {
        /**
         *Used to track user input.
         */
        Scanner input = new Scanner(System.in);
        /**
         * Default Planner object for the menu.
         */
        Planner init = new Planner();
        /**
         * Initializes the backup Planner as a blank object.
         */
        Planner backup = new Planner();

        /**
         * Stores the choice the user chooses for the menu.
         */

        String choice;

        /**
         * Do-while loops the menu after every choice is executed, until the
         * user enters 'Q' to quit the program.
         */

        System.out.print("(A) Add Course\n" +
                "(G) Get Course\n" +
                "(R) Remove Course\n" +
                "(P) Print Courses in Planner\n" +
                "(F) Filter by Department Code\n" +
                "(L) Look For Course\n" +
                "(S) Size\n" +
                "(B) Backup\n" +
                "(PB) Print Courses in Backup\n" +
                "(RB) Revert to Backup\n" +
                "(Q) Quit\n" +
                "Enter choice: ");


        do {
            choice = input.nextLine();
            /**
             * Switch statement to execute different arguments depending on
             * the user's input.
             */
            switch (choice.toUpperCase()) {
                case "G" -> {
                    System.out.println("Enter position: ");
                    int getPos = input.nextInt();
                    System.out.printf("%-25s%-25s%-10s%-8s%-10s", "Course " +
                            "Name", "Department", "Code", "Section",
                            "Instructor \n");
                    System.out.println(init.getCourse(getPos));

                }
                case "A" -> {
                    System.out.println("Enter position: " + "\n(To add to " +
                            "the end of the planner, enter 0)");
                    int pos = input.nextInt();
                    input.nextLine();
                    init.addCourse(pos, input);
                    printMenu();
                }

                case "R" -> {
                    System.out.println("Enter position: ");
                    int pos = input.nextInt();
                    init.removeCourse(pos);
                    printMenu();
                }

                case "P" -> {
                    init.printAllCourses();
                    printMenu();
                }

                case "F" -> {
                    System.out.println("Enter department: ");
                    String dept = input.nextLine();
                    Planner.filter(init, dept);
                    printMenu();
                }

                case "L" -> {
                    System.out.println("Enter course name: ");
                    String courseName = input.nextLine();
                    System.out.println("Enter department: ");
                    String dept = input.nextLine();
                    System.out.println("Enter Instructor");
                    String instrctr = input.nextLine();
                    System.out.println("Enter code: ");
                    int code = input.nextInt();
                    System.out.println("Enter section");
                    byte section = input.nextByte();


                    int getPos = init.findCourse(new Course(courseName, dept
                            , instrctr, code, section));
                    if (getPos == -1)
                        System.out.println("Course not found.");
                    else
                        System.out.println("Course is at position " + getPos);

                    printMenu();
                }

                case "S" -> {
                    System.out.println("There are " + init.size() +
                            "Courses in this planner.");
                    printMenu();
                }

                case "B" -> {
                    backup = (Planner) init.clone();
                    System.out.println("Backup successfully created.");
                    printMenu();
                }

                case "RB" -> {
                    init = (Planner) backup.clone();
                    System.out.println("Backup restored.");
                    printMenu();
                }

                case "PB" -> {
                    backup.printAllCourses();
                    printMenu();
                }
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
        while (!choice.equals("q"));
    }

    public static void printMenu() {
        System.out.print("(A) Add Course\n" +
                "(G) Get Course\n" +
                "(R) Remove Course\n" +
                "(P) Print Courses in Planner\n" +
                "(F) Filter by Department Code\n" +
                "(L) Look For Course\n" +
                "(S) Size\n" +
                "(B) Backup\n" +
                "(PB) Print Courses in Backup\n" +
                "(RB) Revert to Backup\n" +
                "(Q) Quit\n" +
                "Enter choice: ");
    }
}