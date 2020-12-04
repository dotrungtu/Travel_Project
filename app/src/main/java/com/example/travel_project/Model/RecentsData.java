package com.example.travel_project.Model;

public class RecentsData {

    String placeName;
    String countryName;
    String price;
    String imageUrl;
    String dePartures;
    String desCription;
    String imageUrl_1;
    String imageUrl_2;
    String imageUrl_3;

    public RecentsData() {

    }

    public RecentsData(String placeName, String countryName, String price, String imageUrl, String dePartures, String desCription, String imageUrl_1, String imageUrl_2, String imageUrl_3) {
        this.placeName = placeName;
        this.countryName = countryName;
        this.price = price;
        this.imageUrl = imageUrl;
        this.dePartures = dePartures;
        this.desCription = desCription;
        this.imageUrl_1 = imageUrl_1;
        this.imageUrl_2 = imageUrl_2;
        this.imageUrl_3 = imageUrl_3;
    }

    public String getImageUrl_1() {
        return imageUrl_1;
    }

    public void setImageUrl_1(String imageUrl_1) {
        this.imageUrl_1 = imageUrl_1;
    }

    public String getImageUrl_2() {
        return imageUrl_2;
    }

    public void setImageUrl_2(String imageUrl_2) {
        this.imageUrl_2 = imageUrl_2;
    }

    public String getImageUrl_3() {
        return imageUrl_3;
    }

    public void setImageUrl_3(String imageUrl_3) {
        this.imageUrl_3 = imageUrl_3;
    }

    public String getDesCription() {
        return desCription;
    }

    public void setDesCription(String desCription) {
        this.desCription = desCription;
    }

    public String getDePartures() {
        return dePartures;
    }

    public void setDePartures(String dePartures) {
        this.dePartures = dePartures;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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
}
