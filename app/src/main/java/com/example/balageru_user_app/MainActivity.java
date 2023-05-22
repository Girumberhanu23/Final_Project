package com.example.balageru_user_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.balageru_user_app.Adapters.PlateAdapter;
import com.example.balageru_user_app.EmailLoginRegister.EmailLoginActivity;
import com.example.balageru_user_app.Models.CategoryModel;
import com.example.balageru_user_app.OperationRetrofitApi.ApiClient;
import com.example.balageru_user_app.OperationRetrofitApi.ApiInterface;
import com.example.balageru_user_app.OperationRetrofitApi.Users;
import com.example.balageru_user_app.PhoneLoginRegister.PhoneLoginActivity;
import com.example.balageru_user_app.Sessions.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<CategoryModel> plateModelList;
    private PlateAdapter plateAdapter;
    private LinearLayout emailContinue, phoneContinue;
    SessionManager sessionManager;

    ///////////////api's calling////////////////
    public static ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /////////////////////////////////////////////////////////////////////
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        sessionManager = new SessionManager(this);
        //////////////////hide status bar hide /////////////////
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //////////////////hide status bar end///////////////////


        emailContinue= (LinearLayout) findViewById(R.id.linear2);
        phoneContinue= (LinearLayout) findViewById(R.id.linear1);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setKeepScreenOn(true);
        recyclerView.setHasFixedSize(true);

        plateModelList = new ArrayList<>();
        Call<Users> categoryCall = apiInterface.getCategories();
        categoryCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                plateModelList = response.body().getCategory();

                plateAdapter = new PlateAdapter(plateModelList, getApplicationContext());
                recyclerView.setAdapter(plateAdapter);
                plateAdapter.notifyDataSetChanged();


//        //////////call autoScroll start/////////////
            autoScroll();
//        //////////call autoScroll end/////////////

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });


        ////////////continue with Email start/////////////
        emailContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EmailLoginActivity.class);
                startActivity(intent);
//                Animatoo.animateSlideDown( MainActivity.this);
            }
        });
        ////////////continue with Email end/////////////

        ////////////continue with Phone start/////////////
        phoneContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PhoneLoginActivity.class);
                startActivity(intent);
//                Animatoo.animateSlideDown( MainActivity.this);
            }
        });
        ////////////continue with Phone end/////////////
    }

    public void autoScroll(){
        final int speedScroll = 0;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable(){
            int count = 0;
            @Override
            public void run() {
                if(count == plateAdapter.getItemCount())
                    count = 0;
                if(count < plateAdapter.getItemCount()){
                    recyclerView.smoothScrollToPosition(++count);
                    handler.postDelayed(this,speedScroll);
                }
            }
        };
        handler.postDelayed(runnable, speedScroll);
    }

    public void goToHomePage(View view) {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
//        Animatoo.animateSwipeLeft(this);
    }
    @Override
    protected void onStart() {
        super.onStart();

        if(sessionManager.isLogin())
        {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
//            Animatoo.animateSwipeLeft(this);
        }
        else
        {

        }
    }
}