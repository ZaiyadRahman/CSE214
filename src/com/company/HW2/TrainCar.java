/**
 * This <code>TrainCar</code> class contains a length in meters (double), a
 * weight in tons (double), and a load reference (ProductLoad). The load
 * variable of the train may be null, which indicates that the train car is
 * empty and contains no product.
 *
 * @author Zaiyad Munair Rahman
 * SBU ID: 114578879
 * CSE 214.01
 */

package com.company.HW2;

public class TrainCar extends ProductLoad {

    private final double carLength;
    private final double carWeight;
    private ProductLoad load;
    private double totalWeight;

    /**
     * Default constructor that creates an empty TrainCar object.
     */
    public TrainCar() {
        carLength = 0;
        carWeight = 0;
        load = null;
        totalWeight = 0;
    }

    /**
     * Constructor for a TrainCar object.
     * @param carLength
     * Length of the train car in meters.
     * @param carWeight
     * Weight of the train car in tons
     * @param load
     * The load the train car is holding.
     */

    public TrainCar(double carLength, double carWeight, ProductLoad load) {
        this.carLength = carLength;
        this.carWeight = carWeight;
        this.load = load;
        totalWeight = carWeight + load.getWeight();
    }

    /**
     * @return
     * Length of the car in meters.
     */
    public double getCarLength() {
        return carLength;
    }

    /**
     * @return
     * Weight of the train car.
     */
    public double getCarWeight() {
        return carWeight;
    }

    /**
     * @return
     * The load being carried by the train car.
     */
    public ProductLoad getLoad() {
        return load;
    }

    /**
     * @param load
     * The load to be carried by the train car.
     */
    public void setLoad(ProductLoad load) {
        this.load = load;

    }

    /**
     * Determines whether the car is empty or not.
     * @return
     * If the train car has no load, it will return true. Else, it will
     * return false.
     */
    public boolean isEmpty() {
        return load == null;
    }

    /**
     * @return
     * The weight of both the train car itself and the weight of the
     * load the car is carrying.
     */
    public double getTotalWeight() {
        return totalWeight;
    }

}
