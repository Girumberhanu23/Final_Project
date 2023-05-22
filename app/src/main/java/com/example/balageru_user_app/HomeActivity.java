package com.example.balageru_user_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.balageru_user_app.Adapters.CatAdapter;
import com.example.balageru_user_app.Fragments.GoOutFragment;
import com.example.balageru_user_app.Fragments.GoldFragment;
import com.example.balageru_user_app.Fragments.OrdersFragment;
import com.example.balageru_user_app.Fragments.VideosFragment;
import com.example.balageru_user_app.Models.CategoryModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    FrameLayout frameLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        ///////////changing the color of status text color//////////////////////
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        ///////////changing the color of status text color//////////////////////

        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigation);

        //////////////replacing by default fragment on home activity///////////////
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigation =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item){

            Fragment selectedFragment = null;


            switch (item.getItemId())
            {
                case R.id.orders:
                    selectedFragment = new OrdersFragment();
                    break;
                case R.id.goout:
                    selectedFragment = new GoOutFragment();
                    break;
                case R.id.gold:
                    selectedFragment = new GoldFragment();
                    break;
                case R.id.videos:
                    selectedFragment = new VideosFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                    selectedFragment).commit();
            return true;
            }
        };
}