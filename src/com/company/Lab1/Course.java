/**
 * The <Code>Course</Code> class generates an object that contains general
 * information such as course name, course code, course section, and instructor.
 *
 * @author Zaiyad Munair Rahman
 * SBU ID: 114578879
 * CSE 214.01
 */

package com.company.Lab1;

import java.util.InputMismatchException;

public class Course implements Cloneable {
    private String courseName;
    private String department;
    private String instructor;
    private int code;
    private byte section;

    /**
     * Default constructor of Course objects, with default blank values.
     */
    public Course() {
    this.courseName = "";
    this.department = "";
    this.instructor = "";
    this.code = 0;
    this.section = 0;
    }

    /**
     * Constructor of Course objects.
     * @param courseName
     * Name of the course.
     * @param department
     * Department of the course.
     * @param instructor
     * Instructor of the course.
     * @param code
     * The course code.
     * @param section
     * The course's section.
     */

    public Course(String courseName, String department, String instructor, int code, byte section) {
        this.courseName = courseName;
        this.department = department;
        this.instructor = instructor;
        this.code = code;
        this.section = section;
    }

    /**
     *
     * @return
     * Name of the course, with max length of 25 characters.
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     *
     * @param courseName
     * Name of the course, with max length of 25 characters.
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     *
     * @return
     * Department of the Course in 3 letter format.
     */
    public String getDepartment() {
        return department;
    }

    /**
     *
     * @param department
     * The department of the Course in 3 letter format.
     * @throws InputMismatchException
     * When the department input is blank.
     */
    public void setDepartment(String department) throws InputMismatchException {
        try {
            if(department.isBlank())
                throw new InputMismatchException();
            this.department = department;
        }
        catch (InputMismatchException ex) {
            System.out.println("The department cannot be blank. Please try again.");
        }
    }

    /**
     *
     * @return
     * Instructor name, no longer than 25 characters.
     */
    public String getInstructor() {
        return instructor;
    }

    /**
     *
     * @param instructor
     * Instructor name, with length no longer than 25 characters.
     * @throws InputMismatchException
     * When the name is blank.
     */
    public void setInstructor(String instructor) throws InputMismatchException {
        try {
            if (instructor.equals(" "))
                throw new InputMismatchException();
            this.instructor = instructor;

        } catch (InputMismatchException ex) {
            System.out.println("Instructor cannot be blank. Please try again.");
        }
    }

    /**
     *
     * @return
     * Code for the Course as a 3-digit number.
     */
    public int getCode() {
        return code;
    }

    /**
     *
     * @param code
     * Code of the Course, as a 3-digit positive number.
     * @throws IndexOutOfBoundsException
     * When the given code is negative.
     */
    public void setCode(int code) throws IndexOutOfBoundsException {
        try {
            if(code < 0)
                throw new IndexOutOfBoundsException();
            this.code = code;
        }

        catch (IndexOutOfBoundsException exception) {
            System.out.println("The code cannot be negative! Please try again.");
        }
    }

    /**
     *
     * @return
     * Section number of the course.
     */
    public byte getSection() {
        return section;
    }

    /**
     *
     * @param section
     * Section number of the Course.
     * @throws IndexOutOfBoundsException
     * When the input number is negative.
     */
    public void setSection(byte section) throws IndexOutOfBoundsException {
        try {
            if(section < 0)
                throw new IndexOutOfBoundsException();
            this.section = section;
        }
        catch (IndexOutOfBoundsException ex) {
            System.out.println("Your section cannot be a negative number. Please try again.");
        }
    }

    /**
     * Deep copies the Course as a new Course.
     * @return
     * A copy of this Course. Subsequent changes to the copy will not affect
     * the original and vice versa. Note that the return value must be
     * typecasted to a Course before it can be used.
     */
    public Object clone() {
        String clonedCourseName = new String(getCourseName());
        String clonedDepartment = new String(getDepartment());
        String clonedInstructor = new String(getInstructor());
        return new Course( clonedCourseName, clonedDepartment, clonedInstructor, getCode(), getSection());
    }

    /**
     * Checks if given object has the same attributes as this Course.
     * @param o
     * Object that is being determined to be equal or not.
     * @return
     * A return value of true indicates that obj refers to a Course object
     * with the same attributes as this Course. Otherwise, the return value
     * is false.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        return getCode() == course.getCode() & getSection() == course.getSection()
                & getCourseName().equals(course.getCourseName()) & getInstructor().equals(course.getInstructor())
                & getDepartment().equals(course.getDepartment());
    }

    /**
     * Gets the String representation of this Course object, which is a
     * neatly formatted row.
     * @return
     * The String representation of this Course object.
     */

    @Override
    public String toString() {
        return String.format("%-25s%-25s%-10d%-7s%-20s",
                getCourseName(), getDepartment(), getCode(), getSection(), getInstructor());
    }
}