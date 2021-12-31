package com.example.mcasep.model;

public class Car {

    private String carName, carBrand, carModel, carYear, time;

    public Car() {

    }

    public Car(String carName, String carBrand, String carModel, String carYear, String time) {
        this.carName = carName;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.carYear = carYear;
        this.time = time;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarYear() {
        return carYear;
    }

    public void setCarYear(String carYear) {
        this.carYear = carYear;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
