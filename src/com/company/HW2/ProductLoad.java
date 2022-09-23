/**
 * This <code>ProductLoad</code> class contains the product name (String),
 * its weight in tons (double), its value in dollars (double), and whether
 * the product is dangerous or not (boolean). You should provide accessor and
 * mutator methods for each variable. The mutator methods for weight and
 * value should throw exceptions for illegal arguments (i.e. negative values).
 *
 * @author Zaiyad Munair Rahman
 * SBU ID: 114578879
 * CSE 214.01
 */

package com.company.HW2;

public class ProductLoad {

    private String name;
    private double weight;
    private double value;
    private boolean isDangerous;

    /**
     * Default constructor for an empty ProductLoad object.
     */
    public ProductLoad() {
        this.name = "Empty";
        this.weight = 0;
        this.value = 0;
        this.isDangerous = false;
    }

    /**
     * Constructor for a ProductLoad object. that has parameters to fill in
     * each field.
     * @param name
     * Name of the product being carried.
     * @param weight
     * Weight of the product in tons.
     * @param value
     * Value of the product in dollars.
     * @param isDangerous
     * Whether the product is dangerous or not.
     */
    public ProductLoad(String name, double weight, double value, boolean isDangerous) {
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.isDangerous = isDangerous;
    }

    /**
     * @return
     * The name of the product.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name
     * The name of the product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     * The weight of the product in tons.
     */
    public double getWeight() {
        return this.weight;
    }

    /**
     * @param weight
     * The weight of the product in tons.
     * @exception IllegalArgumentException
     * Throws if the input is a negative number.
     */
    public void setWeight(double weight) {
        try {
            if(weight < 0)
                throw new IllegalArgumentException();
            else
                this.weight = weight;
        } catch (IllegalArgumentException ex) {
            System.out.println("Weight cannot be less than 0. Please enter a " +
                    "new value.");
        }
    }

    /**
     * @return
     * Value of the product in dollars.
     */
    public double getValue() {
        return this.value;
    }

    /**
     * @param value
     * Value of the product in dollars.
     * @exception IllegalArgumentException
     * Throws if the input is a negative number.
     */
    public void setValue(double value) {
        try {
            if(value < 0)
                throw new IllegalArgumentException();
            this.value = value;
        }
        catch (IllegalArgumentException ex) {
            System.out.println("Value cannot be negative. Please enter try " +
                    "again.");
        }
    }

    /**
     * @return
     *Whether the product is dangerous or not.
     */
    public boolean isDangerous() {
        return this.isDangerous;
    }

    /**
     * @param dangerous
     * Whether the product is dangerous or not.
     */
    public void setDangerous(boolean dangerous) {
        this.isDangerous = dangerous;
    }

    /**
     * Returns a neatly formatted String representation of the product load.
     * @return
     * A neatly formatted string containing information about the product
     * load, including the name of the product, the weight in tons, the value
     * in dollars, and whether the product is dangerous or not.
     */
    @Override
    public String toString() {
        StringBuilder stringOutput = new StringBuilder();
        stringOutput.append(String.format("%-14s%-14s%-12s%9s", this.getName(),
                this.getWeight(), this.getValue(), this.isDangerous()));
    return stringOutput.toString();
    }
}
