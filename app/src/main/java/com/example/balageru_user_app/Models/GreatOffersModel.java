package com.example.balageru_user_app.Models;

public class GreatOffersModel {

    private GreatOffersModel()
    {
        //////empty constructor/////////
    }
    private String shop_id, shop_name, shop_owner_name, license_number, pincode, shop_image, city, state, landmark, discount, coupon, description, rating, categories, shop_added_date;

    public GreatOffersModel(String shop_id, String shop_name, String shop_owner_name, String license_number, String pincode, String shop_image, String city, String state, String landmark, String discount, String coupon, String description, String rating, String categories, String shop_added_date) {
        this.shop_id = shop_id;
        this.shop_name = shop_name;
        this.shop_owner_name = shop_owner_name;
        this.license_number = license_number;
        this.pincode = pincode;
        this.shop_image = shop_image;
        this.city = city;
        this.state = state;
        this.landmark = landmark;
        this.discount = discount;
        this.coupon = coupon;
        this.description = description;
        this.rating = rating;
        this.categories = categories;
        this.shop_added_date = shop_added_date;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_owner_name() {
        return shop_owner_name;
    }

    public void setShop_owner_name(String shop_owner_name) {
        this.shop_owner_name = shop_owner_name;
    }

    public String getLicense_number() {
        return license_number;
    }

    public void setLicense_number(String license_number) {
        this.license_number = license_number;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getShop_image() {
        return shop_image;
    }

    public void setShop_image(String shop_image) {
        this.shop_image = shop_image;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getShop_added_date() {
        return shop_added_date;
    }

    public void setShop_added_date(String shop_added_date) {
        this.shop_added_date = shop_added_date;
    }
}
