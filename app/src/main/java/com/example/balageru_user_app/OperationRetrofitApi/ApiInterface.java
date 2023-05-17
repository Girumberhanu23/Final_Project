package com.example.balageru_user_app.OperationRetrofitApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    //////////////email registration/////////////////
    @GET("email_registration.php")
    Call<Users> performEmailRegistration(
        @Query("user_name") String user_name,
        @Query("user_email") String user_email,
        @Query("user_password") String user_password
    );

    //////////////email login/////////////////
    @GET("email_login.php")
    Call<Users> performEmailLogin(
            @Query("user_email") String user_email,
            @Query("user_password") String user_password
    );

    //////////////email registration/////////////////
    @GET("phone_registration.php")
    Call<Users> performPhoneRegistration(
            @Query("user_phone") String user_phone
    );

    //////////////email registration/////////////////
    @GET("phone_login.php")
    Call<Users> performPhoneLogin(
            @Query("user_phone") String user_phone
    );
}
