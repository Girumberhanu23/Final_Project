package com.example.balageru_user_app.Models;

public class BannerModel {
    public BannerModel()
    {
        //empty constructor//////////
    }
    private String banner_id, banner_image, banner_date;

    public BannerModel(String banner_id, String banner_image, String banner_date) {
        this.banner_id = banner_id;
        this.banner_image = banner_image;
        this.banner_date = banner_date;
    }

    public String getBanner_id() {
        return banner_id;
    }

    public void setBanner_id(String banner_id) {
        this.banner_id = banner_id;
    }

    public String getBanner_image() {
        return banner_image;
    }

    public void setBanner_image(String banner_image) {
        this.banner_image = banner_image;
    }

    public String getBanner_date() {
        return banner_date;
    }

    public void setBanner_date(String banner_date) {
        this.banner_date = banner_date;
    }
}
