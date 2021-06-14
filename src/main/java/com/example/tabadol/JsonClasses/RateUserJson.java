package com.example.tabadol.JsonClasses;

public class RateUserJson {
    private int rateValue;

    public RateUserJson(int rateValue) {
        this.rateValue = rateValue;
    }

    public RateUserJson() {
    }

    public int getRateValue() {
        return rateValue;
    }

    public void setRateValue(int rateValue) {
        this.rateValue = rateValue;
    }
}
