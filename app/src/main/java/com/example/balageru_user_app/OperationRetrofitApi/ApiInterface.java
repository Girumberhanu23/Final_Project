package com.example.balageru_user_app.OperationRetrofitApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    //////////////email registration/////////////////
    @GET("users/email_registration.php")
    Call<Users> performEmailRegistration(
        @Query("user_name") String user_name,
        @Query("user_email") String user_email,
        @Query("user_password") String user_password
    );

    //////////////email login/////////////////
    @GET("users/email_login.php")
    Call<Users> performEmailLogin(
            @Query("user_email") String user_email,
            @Query("user_password") String user_password
    );

    //////////////email registration/////////////////
    @GET("users/phone_registration.php")
    Call<Users> performPhoneRegistration(
            @Query("user_phone") String user_phone
    );

    //////////////email registration/////////////////
    @GET("users/phone_login.php")
    Call<Users> performPhoneLogin(
            @Query("user_phone") String user_phone
    );

    /////////////getting all users/////////////
    @GET("api/users.php")
    Call<Users> getUsers();


    /////////////getting all categories/////////////
    @GET("api/categories.php")
    Call<Users> getCategories();

    /////////////getting all banners/////////////
    @GET("api/banners.php")
    Call<Users> getBanners();

    /////////////getting strip banners/////////////
    @GET("api/strip_banners.php")
    Call<Users> getStripBanners();

    /////////////getting all random_shops/////////////
    @GET("api/random_shops.php")
    Call<Users> getRandomShops();

    /////////////getting all great offers shop/////////////
    @GET("api/great_offers_shops.php")
    Call<Users> greatOffersShop();

    /////////////getting all great offers shop vertical/////////////
    @GET("api/great_offers_shops_vertical.php")
    Call<Users> greatOffersVerticalShop();

    /////////////getting all new arrivals/////////////
    @GET("api/new_arrivals_shops.php")
    Call<Users> newArrivalsShops();

    /////////////getting all new arrivals vertical/////////////
    @GET("api/new_arrivals_shops_vertical.php")
    Call<Users> newArrivalsVerticalShops();

    /////////////getting all balageru exclusive/////////////
    @GET("api/balageru_exclusive.php")
    Call<Users> balageruExclusive();

    /////////////getting all balageru exclusive vertical/////////////
    @GET("api/balageru_exclusive_vertical.php")
    Call<Users> balageruExclusiveVertical();
}
