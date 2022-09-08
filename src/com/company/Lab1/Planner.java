/**
 * The <code>Planner</code> class implements an abstract data type for a
 * list of courses supporting some common operations on such lists.
 *
 * @author Zaiyad Munair Rahman
 * SBU ID: 114578879
 * CSE 214.01
 */

package com.company.Lab1;

import java.util.Scanner;

public class Planner extends Course {
    final int MAX_COURSES = 50;
    private int size;
    private Course[] courses = new Course[MAX_COURSES];

    public int getMAX_COURSES() {
        return MAX_COURSES;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Course[] getCourses() {
        return courses;
    }

    public void setCourses(Course[] courses) {
        this.courses = courses;
    }

    /**
     * Constructs an instance of the Planner with no Course
     * objects in it.
     */
    Planner() {}

    /**
     * Prints all Courses that are within the specified department.
     * @param planner
     * the list of courses to search in
     * @param department
     * the 3 letter department code for a Course
     */
    public static void filter(Planner planner, String department) {
        System.out.printf("%-4s%-25s%-25s%-10s%-8s%-10s", "No.", "Course " +
                "Name", "Department", "Code", "Section", "Instructor \n");
        for (int i = 0; i < planner.size; i++) {
            if (planner.courses[i].getDepartment().equals(department))
                System.out.println((i + 1) + "   " + planner.courses[i]);
        }
    }

    /**
     * Adds new course to the planner. All Courses that were originally
     * greater than or equal to position are
     * moved back one position.
     *
     * @param position
     * the position (preference) of this course on the list.
     * @param newCourse
     * the new course to add to the list.
     */

    public void addCourse(Course newCourse, int position) {
        try {
            if (position < 1 || position > size() + 1)
                throw new IllegalArgumentException();
            if (size == MAX_COURSES)
                throw new FullPlannerException();
            position = position - 1;
            if (size() - 1 - position > 0)
                System.arraycopy(courses, position, courses, position + 1,
                        size() + 1 - position);
            courses[position] = newCourse;
            size++;
            System.out.println("Course added successfully.");
        } catch (IllegalArgumentException ex) {
            System.out.println("The requested position is out of bounds! " +
                    "Please try again.");
        } catch (FullPlannerException exception) {
            System.out.println("The planner is full. Please remove a course " +
                    "first if you wish to add more courses.");
        }
    }

    /**
     * Adds a Course to the planner object, after user input.
     * @param position
     * the position (preference) of this course on the list
     * @param input
     * Scanned input from user.
     *
     * @exception IllegalArgumentException
     * When position is the valid range of
     * 1<=position<=items_currently_in_list + 1.
     * @exception  FullPlannerException
     * when planner size is at MAX_COURSES.
     */
    public void addCourse(int position, Scanner input) {
        try {
            System.out.println("Enter course name: ");
            String courseName = input.nextLine();
            System.out.println("Enter department: ");
            String dept = input.nextLine();
            System.out.println("Enter Instructor: ");
            String instrctr = input.nextLine();
            System.out.println("Enter code: ");
            int code = input.nextInt();
            if(code < 100)
                throw new IllegalArgumentException();
            System.out.println("Enter section: ");
            byte section = input.nextByte();
            if(section < 0 || position < 0 || position > size() + 1)
                throw new IllegalArgumentException();
            if(position == 0)
                position = size + 1;
            if (size == MAX_COURSES)
                throw new FullPlannerException();
            position = position - 1;
            if (size() - 1 - position > 0)
                System.arraycopy(courses, position, courses, position + 1,
                        size() + 1 - position);
            courses[position] =
                    new Course(courseName, dept, instrctr, code, section);
            size++;
            System.out.println(dept + code + "." + section + " added " +
                    "successfully.");
        } catch (IllegalArgumentException ex) {
            System.out.println("The inputted value is out of bounds! " +
                    "Please try again.");
        } catch (FullPlannerException exception) {
            System.out.println("The planner is full. Please remove a course " +
                    "first if you wish to add more courses.");
        }
    }

    /**
     * Works just like public void addCourse(Course newCourse, int position),
     * except adds to the end of the list.
     * @param newCourse
     * Course to be added to the planner.
     */

    public void addCourse(Course newCourse) {
        addCourse(newCourse, size + 1);
    }

    /**
     * Removes course at desired position. All Courses that were originally
     * greater than or equal to position are moved backward one position.
     * @param position
     * the position in the Planner where the Course will be removed from.
     *
     * @throws IllegalArgumentException
     * When the position is not within the valid range of
     * 1<=position<=items_currently_in_list.
     */
    public void removeCourse(int position) {
        try {
            if (position < 1 || position > size() + 1)
                throw new IllegalArgumentException();
            position = position - 1;
            if (size() - 1 - position > 0)
                System.arraycopy(courses, position + 1, courses, position,
                    size() + 1 - position);
            size--;
            System.out.println("Course removed successfully.");
        } catch (IllegalArgumentException ex) {
            System.out.println("The requested position is out of bounds! " +
                    "Please try again.");
        }
    }

    /**
     * Removes the desired course.
     * @param x
     * Course designated to be removed.
     * @throws IllegalArgumentException
     * When the course cannot be found in the planner.
     */

    public void removeCourse(Course x) {
        try {
            int pos = -1;
            for (int i = 0; i < size; i++) {
                if (courses[i].equals(x))
                    pos = i;
            }
            if (pos == -1)
                throw new IllegalArgumentException();
            System.arraycopy(
                    courses, pos + 1, courses, pos, size() + 1 - pos);
            size--;
        } catch (IllegalArgumentException ex) {
            System.out.println("Course not found.");
        }
    }

    /**
     * Retrieves course from planner and prints onto console.
     * @param position
     * Position of the course to retrieve.
     * @return
     * Returns the course at the specified position in this planner object.
     */

    public Course getCourse(int position) {
        if (position < 1 || position > size)
            throw new IllegalArgumentException("Invalid position. Try again.");
        position = position - 1;
        return courses[position];
    }

    /**
     * Determines the number of courses currently in the list.
     * @return
     * Returns the number of courses in this planner.
     */
    public int size() {
        return size;
    }

    /**
     * Checks whether a certain Course is already in the list.
     * @param course
     * The Course we are looking for.
     * @return
     * True if the Planner contains this Course, false otherwise.
     */

    public boolean exists(Course course) {
        for (int i = 0; i < size; i++) {
            if (course.equals(courses[i]))
                return true;
        }
        return false;
    }

    /**
     * Creates a copy of this Planner. Subsequent changes to the copy will
     * not affect the original and vice versa.
     * @return
     * A copy (backup) of this Planner object.
     */

    public Object clone() {
        Planner cloned = new Planner();
        for (int i = 0; i < this.size; i++) {
            cloned.courses[i] = (Course) this.courses[i].clone();
        }
        cloned.size = this.size;
        return cloned;
    }

    /**
     * Determines the position in the planner of a given course.
     * @param course
     * The Course whose position we are looking for.
     * @return
     * The position in the planner of the given course.
     */

    public int findCourse(Course course) {
        for (int i = 0; i < size; i++) {
            if (course.equals(courses[i]))
                return i + 1;
        }

        return -1;
    }

    /**
     * Prints a neatly formatted table of each item in the list with its
     * position number.
     */

    public void printAllCourses() {
        System.out.println(this);
    }

    /**
     * Gets the String representation of this Planner object, which is a
     * neatly formatted table of each Course in the Planner on its own line
     * with its position number.
     * @return
     * The String representation of this Planner object.
     */

    @Override
    public String toString() {
        System.out.printf("%-4s%-25s%-25s%-10s%-8s%-10s", "No.", "Course " +
                "name", "Department", "Code", "Section", "Instructor\n");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            stringBuilder.append(i + 1).append("   ")
                    .append(courses[i].toString()).append("\n");
        }

        return stringBuilder.toString();
    }
}
