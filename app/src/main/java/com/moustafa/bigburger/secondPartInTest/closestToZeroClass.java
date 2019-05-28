package com.moustafa.bigburger.secondPartInTest;

import java.util.ArrayList;

public class closestToZeroClass {
    //list of temperatures
    private ArrayList<Double> temperature_list;

    //constructor with parameter
    public closestToZeroClass(ArrayList<Double> temperature_list) {
        this.temperature_list = temperature_list;
    }

    //constructor without parameter
    public closestToZeroClass() {
        temperature_list = new ArrayList<>();
    }

    // getter list of temperatures
    public ArrayList<Double> getTemperature_list() {
        return temperature_list;
    }

    // setter list of temperatures
    public void setTemperature_list(ArrayList<Double> temperature_list) {
        this.temperature_list = temperature_list;
    }

    // method for closest To Zero to return the temperature closer to zero from  list of temperatures
    public Double closestToZero() {
        Double minimize = 1000000.00;
        for (Double temp : temperature_list) {
            //method abs in Math to return the absolute value
            if (Math.abs(temp) < Math.abs(minimize)) {
                minimize = temp;
            }
            //if the values is equal in absolute and return the positive value
            else if (Math.abs(temp) == Math.abs(minimize)) {
                minimize = (temp >= 0 ? temp : minimize);
            }

        }

        return minimize;
    }
}
