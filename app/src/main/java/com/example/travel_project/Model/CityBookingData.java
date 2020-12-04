package com.example.travel_project.Model;

public class CityBookingData {
    String cityName;
    String countryName;
    String imageUrl;
    String code;

    public CityBookingData() {

    }

    public CityBookingData(String cityName, String countryName, String imageUrl, String code) {
        this.cityName = cityName;
        this.countryName = countryName;
        this.imageUrl = imageUrl;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
