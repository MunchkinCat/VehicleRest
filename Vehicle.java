package com.example;

/**
 * Created by Gabe on 10/28/2016.
 */

import java.io.Serializable;

public class Vehicle implements Serializable {
    private int id;
    private String makeModel;
    private int year;
    private double retailPrice;

    public Vehicle() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMakeModel() {
        return makeModel;
    }

    public void setMakeModel(String makeModel) {
        this.makeModel = makeModel;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Vehicle(int id, String makeModel, int year, double retailPrice) {
        this.id = id;
        this.makeModel = makeModel;
        this.year = year;
        this.retailPrice = retailPrice;

    }

    public String toString() {
        return "Vehicle{" + "id=" + id +
                ", makeModel='" + makeModel + '\'' +
                ", year=" + year +
                ", retailPrice=" + retailPrice +
                '}';
    }


}
