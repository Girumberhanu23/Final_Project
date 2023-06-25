package com.example.balageru_user_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.balageru_user_app.Fragments.CartFragment;
import com.example.balageru_user_app.Fragments.GoOutFragment;
import com.example.balageru_user_app.Fragments.OrdersFragment;
import com.example.balageru_user_app.Fragments.VideosFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    FrameLayout frameLayout;

    CartFragment cartFragment =new CartFragment();
    GoOutFragment goOutFragment=new GoOutFragment();
    OrdersFragment ordersFragment=new OrdersFragment();
    VideosFragment videosFragment=new VideosFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);






        ///////////changing the color of status text color//////////////////////
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        ///////////changing the color of status text color//////////////////////
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, ordersFragment).commit();
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);

        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.orders:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, ordersFragment).commit();
                        break;
                    case R.id.goout:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, goOutFragment).commit();
                        break;
                    case R.id.gold:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, cartFragment).commit();
                        break;
                    case R.id.videos:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, videosFragment).commit();
                        break;
                }
                return true;
            }
        });
        SharedPreferences user_id_stored= getSharedPreferences("USER_ID",MODE_PRIVATE);
//        Toast.makeText(HomeActivity.this, user_id_stored.getString("userNameStored", null), Toast.LENGTH_LONG).show();

        //////////////replacing by default fragment on home activity///////////////
    }


}