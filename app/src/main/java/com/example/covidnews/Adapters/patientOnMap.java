package com.example.covidnews.Adapters;

public class patientOnMap {
    private double mX;
    private double mY;
    private int nInfectedDay;
    public patientOnMap() {
        //default: my house
        mX = 10.7471773;
        mY = 106.6871793;
        nInfectedDay = 0;
    }

    public patientOnMap(double x, double y, int n) {
        mX = x;
        mY = y;
        nInfectedDay = n;
    }

    public double getCurPosX(){
        return mX;
    }

    public double getCurPosY(){
        return mY;
    }

    public int getCurPosDay(){
        return nInfectedDay;
    }
}
