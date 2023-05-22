package com.example.balageru_user_app.Models;

public class CategoryModel {

    public CategoryModel()
    {
        ////empty constructor//////
    }
    private String cat_image, cat_title;

    public CategoryModel(String cat_image, String cat_title) {
        this.cat_image = cat_image;
        this.cat_title = cat_title;
    }

    public String getCat_image() {
        return cat_image;
    }

    public void setCat_image(String cat_image) {
        this.cat_image = cat_image;
    }

    public String getCat_title() {
        return cat_title;
    }

    public void setCat_title(String cat_title) {
        this.cat_title = cat_title;
    }
}
