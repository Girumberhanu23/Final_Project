package com.example.balageru_user_app.Product;

import android.net.Uri;

public class Product {
    private String productName;
    private String productDesc;
    private String productPrice;
    private String productQty;
    private String productCat;
    private String productId;
    private String userId;
    private String sellerName;
    private Uri productImg;

    public Product(String productName, String productDesc, String productPrice, String productQty, String productCat, String productId, String userId, Uri productImg, String sellerName) {
        this.productName = productName;
        this.productDesc = productDesc;
        this.productPrice = productPrice;
        this.productQty = productQty;
        this.productCat = productCat;
        this.productId = productId;
        this.userId = userId;
        this.sellerName = sellerName;
        this.productImg = productImg;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductQty() {
        return productQty;
    }

    public String getProductCat() {
        return productCat;
    }

    public String getProductId() {
        return productId;
    }

    public String getUserId() {
        return userId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public Uri getProductImg() {
        return productImg;
    }
}
