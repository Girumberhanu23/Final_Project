package com.example.balageru_user_app.Product;

import android.net.Uri;

public class Cart {
    String productName;
    String productPrice;
    String productQuantity;
    Uri productImage;
    String userId;

    public Cart(String productName, String productPrice, String productQuantity, Uri productImage, String userId) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productImage = productImage;
        this.userId = userId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public Uri getProductImage() {
        return productImage;
    }

    public String getUserId() {
        return userId;
    }
}
