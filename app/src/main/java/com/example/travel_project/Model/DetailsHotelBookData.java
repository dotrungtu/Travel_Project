package com.example.travel_project.Model;

public class DetailsHotelBookData {
    String imageUrl;
    String hotelName;
    String placeName;
    String userName;
    String phoneNumber;
    String checkIn;
    String checkOut;
    String price;
    String bedType;

    public DetailsHotelBookData()
    {

    }

    public DetailsHotelBookData(String imageUrl, String hotelName, String placeName, String userName, String phoneNumber, String checkIn, String checkOut, String price, String bedType) {
        this.imageUrl = imageUrl;
        this.hotelName = hotelName;
        this.placeName = placeName;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.price = price;
        this.bedType = bedType;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }
}
