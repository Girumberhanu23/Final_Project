package com.example.balageru_user_app.Models;

import android.net.Uri;

import com.example.balageru_user_app.Product.Cart;

public class OrderModel extends Cart {

    String location, date;
    public OrderModel(String productName, String productPrice, String productQuantity, Uri productImage, String userId, String location, String date) {
        super(productName, productPrice, productQuantity, productImage, userId);
        this.location=location;
        this.date= date;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }
}
