package com.example.travel_project.Model;

public class BookingSaPaData {
    String hotelName;
    String price;
    String phoneNumber;
    String imageUrl;
    String placeName;

    public BookingSaPaData() {

    }

    public BookingSaPaData(String hotelName, String price, String phoneNumber, String imageUrl, String placeName) {
        this.hotelName = hotelName;
        this.price = price;
        this.phoneNumber = phoneNumber;
        this.imageUrl = imageUrl;
        this.placeName = placeName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
