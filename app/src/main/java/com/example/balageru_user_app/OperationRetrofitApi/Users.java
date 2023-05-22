package com.example.balageru_user_app.OperationRetrofitApi;

import com.example.balageru_user_app.Models.BannerModel;
import com.example.balageru_user_app.Models.CategoryModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Users {

    @SerializedName("response")
    private String Response;

    @SerializedName("user_id")
    private String UserId;

    @SerializedName("strip_banner_image")
    private String strip_banner_image;

    @SerializedName("categories")
    private List<CategoryModel> category;

    @SerializedName("banners")
    private List<BannerModel> banners;

    public String getResponse() {
        return Response;
    }

    public String getUserId() {
        return UserId;
    }

    public String getStrip_banner_image() {
        return strip_banner_image;
    }

    public List<CategoryModel> getCategory() {
        return category;
    }

    public List<BannerModel> getBanners() {
        return banners;
    }
}
