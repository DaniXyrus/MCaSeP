package com.example.mcasep.model;

public class Motor {

    private String motorName, motorBrand, motorModel, motorYear, time;

    public Motor() {

    }

    public Motor(String motorName, String motorBrand, String motorModel, String motorYear, String time) {
        this.motorName = motorName;
        this.motorBrand = motorBrand;
        this.motorModel = motorModel;
        this.motorYear = motorYear;
        this.time = time;
    }

    public String getMotorName() {
        return motorName;
    }

    public void setMotorName(String motorName) {
        this.motorName = motorName;
    }

    public String getMotorBrand() {
        return motorBrand;
    }

    public void setMotorBrand(String motorBrand) {
        this.motorBrand = motorBrand;
    }

    public String getMotorModel() {
        return motorModel;
    }

    public void setMotorModel(String motorModel) {
        this.motorModel = motorModel;
    }

    public String getMotorYear() {
        return motorYear;
    }

    public void setMotorYear(String motorYear) {
        this.motorYear = motorYear;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
