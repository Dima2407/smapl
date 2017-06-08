package com.smapl_android.net.responses;

import com.google.gson.annotations.SerializedName;

public class GetCompanyHistoryResponse {

    @SerializedName("cars")
    private Car[] cars;

    public Car[] getCars() {
        return cars;
    }

    public void setCars(Car[] cars) {
        this.cars = cars;
    }

    public static class Car {

        @SerializedName("mark")
        private String mark;

        @SerializedName("model")
        private String model;

        @SerializedName("Income")
        private Income income;

        @SerializedName("car_id")
        private String carId;

        @SerializedName("term")
        private String term;

        public String getMark() {
            return mark;
        }

        public String getModel() {
            return model;
        }

        public Income getIncome() {
            return income;
        }

        public String getCarId() {
            return carId;
        }

        public String getTerm() {
            return term;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public void setIncome(Income income) {
            this.income = income;
        }

        public void setCarId(String carId) {
            this.carId = carId;
        }

        public void setTerm(String term) {
            this.term = term;
        }
    }

    public static class Income {

        @SerializedName("all")
        private String all;

        @SerializedName("maxDay")
        private String maxDay;

        @SerializedName("maxWeak")
        private String maxWeak;

        public String getAll() {
            return all;
        }

        public String getMaxDay() {
            return maxDay;
        }

        public String getMaxWeak() {
            return maxWeak;
        }

        public void setAll(String all) {
            this.all = all;
        }

        public void setMaxDay(String maxDay) {
            this.maxDay = maxDay;
        }

        public void setMaxWeak(String maxWeak) {
            this.maxWeak = maxWeak;
        }
    }

}
