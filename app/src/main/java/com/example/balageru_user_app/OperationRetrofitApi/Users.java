package com.example.balageru_user_app.OperationRetrofitApi;

import com.example.balageru_user_app.Models.BannerModel;
import com.example.balageru_user_app.Models.CategoryModel;
import com.example.balageru_user_app.Models.GreatOffersModel;
import com.example.balageru_user_app.Models.SimpleVerticalModel;
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

    @SerializedName("random_shops")
    private List<SimpleVerticalModel> random_shops;

    @SerializedName("great_offers_shops")
    private List<GreatOffersModel> great_offers_shops;

    @SerializedName("great_offers_shops_vertical")
    private List<SimpleVerticalModel> great_offers_shops_vertical;

    @SerializedName("new_arrivals_shops")
    private List<GreatOffersModel> new_arrivals_shops;

    @SerializedName("new_arrivals_shops_vertical")
    private List<SimpleVerticalModel> new_arrivals_shops_vertical;

    @SerializedName("balageru_exclusive")
    private List<GreatOffersModel> balageru_exclusive;

    @SerializedName("balageru_exclusive_vertical")
    private List<SimpleVerticalModel> balageru_exclusive_vertical;

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

    public List<SimpleVerticalModel> getRandom_shops() {
        return random_shops;
    }

    public List<GreatOffersModel> getGreat_offers_shops() {
        return great_offers_shops;
    }

    public List<SimpleVerticalModel> getGreat_offers_shops_vertical() {
        return great_offers_shops_vertical;
    }

    public List<GreatOffersModel> getNew_arrivals_shops() {
        return new_arrivals_shops;
    }

    public List<SimpleVerticalModel> getNew_arrivals_shops_vertical() {
        return new_arrivals_shops_vertical;
    }

    public List<GreatOffersModel> getBalageru_exclusive() {
        return balageru_exclusive;
    }

    public List<SimpleVerticalModel> getBalageru_exclusive_vertical() {
        return balageru_exclusive_vertical;
    }
}
