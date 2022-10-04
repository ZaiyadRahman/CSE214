/**
 * This <code>Complexity</code> class represents the Big-Oh complexity of
 * some block of code. Since Big-Oh notation can get quite messy (e.g. log(n^
 * (1/2) / n!)), we will restrict the possible orders to powers of two base
 * types: n, and log_n. Following this practice, include two member variables
 * in the Complexity class: nPower (int) and logPower (int). These two
 * variables will keep track of what power each of the base types is present
 * in the complexity object.
 *
 * @author Zaiyad Munair Rahman
 * SBU ID: 114578879
 * CSE 214.01
 */

package com.company.hw3;

public class Complexity {
    private int nPower;
    private int logPower;

    /**
     * Default constructor of Complexity.
     */
    public Complexity() {
        nPower = 0;
        logPower = 0;
    }

    /**
     * @return
     * The power of n in time complexity.
     */
    public int getnPower() {
        return nPower;
    }

    /**
     * @param nPower
     * The power of n in time complexity to be assigned.
     */
    public void setnPower(int nPower) {
        this.nPower = nPower;
    }

    /**
     * @return
     * The log(n) power in the complexity.
     */
    public int getLogPower() {
        return logPower;
    }

    /**
     * @param logPower
     * Value to be assigned to the log(n) power in the complexity.
     */
    public void setLogPower(int logPower) {
        this.logPower = logPower;
    }

    /**
     * Provides the powers of n and log(n) respectively as a string,
     * separated by a comma.
     * @return
     * the power of n, followed by the log(n) power.
     */
    public String getPowers() {
        return nPower + "," + logPower;
    }

    /**
     * Returns a neatly formatted String representation of the big-O complexity.
     * @return
     * Returns a string that provides human-readable Big-Oh notation.
     */
    @Override
    public String toString() {
        if(nPower == 0 & logPower == 0)
            return "O(1)";
        if(nPower == 1 & logPower == 0)
            return "O(n)";
        if(nPower == 0 & logPower == 1)
            return "O(log(n))";
        if(nPower > 1 & logPower == 0)
            return "O(n^" + nPower + ")";
        if(nPower == 0 & logPower > 1)
            return "O(log(n)^" + logPower + ")";
        if(nPower == 1 & logPower == 1)
            return "O(n * log(n))";
        else if (nPower > 1 & logPower == 1)
            return "O(n^" + nPower + " * log(n))";
        else if(nPower == 1 & logPower > 1)
            return "O(n * log(n)^" + logPower + ")";
        else
            return "O(n^" + nPower + " * log(n)^" + logPower + ")";
    }
}
