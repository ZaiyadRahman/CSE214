package com.company.hw3;

public class Complexity {
    public static void main(String[] args) {

    }

    private int nPower;
    private int logPower;

    public Complexity() {
        nPower = 0;
        logPower = 0;
    }

    public int getnPower() {
        return nPower;
    }

    public void setnPower(int nPower) {
        this.nPower = nPower;
    }

    public int getLogPower() {
        return logPower;
    }

    public void setLogPower(int logPower) {
        this.logPower = logPower;
    }

    public String getPowers() {
        return nPower + "," + logPower;
    }

    @Override
    public String toString() {
        if(nPower == 0 & logPower == 0)
            return "O(1)";
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
