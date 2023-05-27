package com.example.balageru_user_app.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.balageru_user_app.Adapters.BannerAdapter;
import com.example.balageru_user_app.Adapters.CatAdapter;
import com.example.balageru_user_app.Adapters.GreatOffersAdapter;
import com.example.balageru_user_app.Adapters.SimpleVerticalAdapter;
import com.example.balageru_user_app.MainActivity;
import com.example.balageru_user_app.Models.BannerModel;
import com.example.balageru_user_app.Models.CategoryModel;
import com.example.balageru_user_app.Models.GreatOffersModel;
import com.example.balageru_user_app.Models.SimpleVerticalModel;
import com.example.balageru_user_app.OperationRetrofitApi.ApiClient;
import com.example.balageru_user_app.OperationRetrofitApi.ApiInterface;
import com.example.balageru_user_app.OperationRetrofitApi.Users;
import com.example.balageru_user_app.R;
import com.example.balageru_user_app.Sessions.SessionManager;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment implements  View.OnClickListener{



    public OrdersFragment() {
        // Required empty public constructor
    }

    DrawerLayout drawerLayout;
    ImageView navigationBar, strip_banner_image;
    NavigationView navigationView;
    private View view;
    private RelativeLayout bookmarks, eightMMGold;
    private TextView your_orders, favourite_orders, address_book, online_ordering_help, send_feedback, report_safety_emergency, rate_playstore;
    SessionManager sessionManager;
    private TextView login, logout;



    ///////////////category slider start/////////////////////
    RecyclerView recyclerViewCategory;
    private CatAdapter catAdapter;
    private List<CategoryModel> categoryModelList;
    ///////////////category slider end/////////////////////

    ///////////////Banner slider start/////////////////////
    private RecyclerView recyclerViewBanner;
    private BannerAdapter bannerAdapter;
    private List<BannerModel> bannerModelList;
    ///////////////Banner slider end/////////////////////

    ///////////////Simple Vertical slider start/////////////////////
    private RecyclerView recyclerViewSimple;
    private SimpleVerticalAdapter simpleVerticalAdapter;
    private List<SimpleVerticalModel> simpleVerticalModelList;
    ///////////////Simple Vertical  slider end/////////////////////

    ////////////////////////////////Great offer horizontal start//////////////////////////////
    private RecyclerView greatGreatOffersHorizontal;
    private List<GreatOffersModel> greatOffersModel;
    private GreatOffersAdapter greatOffersAdapter;
    ////////////////////////////////Great offer horizontal end//////////////////////////////

    ////////////////////////////////Great Offers vertical slider start//////////////////////////////
    private RecyclerView greatOffersRecyclerViewVertical;
    ////////////////////////////////Great Offers vertical slider end//////////////////////////////

    ////////////////////////////////New Arrival Horizontal slider start//////////////////////////////
    private RecyclerView newArrivalHorizontalRecyclerview;
    ////////////////////////////////New Arrival Horizontal slider end//////////////////////////////

    ////////////////////////////////New Arrival vertical slider start//////////////////////////////
    private RecyclerView newArrivalVerticalRecyclerview;
    ////////////////////////////////New Arrival Vertical slider end//////////////////////////////

    ////////////////////////////////balageru exclusive vertical slider start//////////////////////////////
    private RecyclerView exclusiveVerticalRecyclerview;
    ////////////////////////////////balageru exclusive Vertical slider end//////////////////////////////

    ////////////////////////////////balageru exclusive horizontal slider start//////////////////////////////
    private RecyclerView exclusiveHorizontalRecyclerview;
    ////////////////////////////////balageru exclusive horizontal slider end//////////////////////////////

    ///////////////api's calling////////////////
    public static ApiInterface apiInterface;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_orders, container, false);
        sessionManager = new SessionManager(getContext());


        onSetNavigationDrawerEvents();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        init();
        return view;
    }

    private void init() {

        //////////////////strip banner image start///////////////////////
        strip_banner_image= (ImageView) view.findViewById(R.id.strip_banner_image);
        Call<Users> stripBannerCall = apiInterface.getStripBanners();
        stripBannerCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> stripBannerCall, Response<Users> response) {

                Glide.with(getContext()).load(response.body().getStrip_banner_image()).placeholder(R.drawable.small_placeholder).into(strip_banner_image);
            }

            @Override
            public void onFailure(Call<Users> stripBannerCall, Throwable t) {

            }
        });

        //////////////////strip banner image end///////////////////////


        ////////////////////////////Category model start///////////////////////
        recyclerViewCategory = (RecyclerView) view.findViewById(R.id.recyclerViewCategory);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewCategory.setLayoutManager(layoutManager);

        categoryModelList = new ArrayList<>();
        Call<Users> categoryCall = apiInterface.getCategories();
        categoryCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> categoryCall, Response<Users> response) {

                categoryModelList = response.body().getCategory();

                catAdapter = new CatAdapter(categoryModelList,getContext());
                recyclerViewCategory.setAdapter(catAdapter);
                catAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Users> categoryCall, Throwable t) {

            }
        });

        ////////////////////////////Category model end///////////////////////

        ////////////////////////////////////Banner model list start////////////////////////////
        recyclerViewBanner = (RecyclerView) view.findViewById(R.id.recyclerViewBanner);
        LinearLayoutManager layoutManagerBanner = new LinearLayoutManager(getContext());
        layoutManagerBanner.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewBanner.setLayoutManager(layoutManagerBanner);

        bannerModelList = new ArrayList<>();
        Call<Users> bannerCall = apiInterface.getBanners();
        bannerCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> bannerCall, Response<Users> response) {
                bannerModelList = response.body().getBanners();

                bannerAdapter = new BannerAdapter(bannerModelList,getContext());
                recyclerViewBanner.setAdapter(bannerAdapter);
                bannerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Users> bannerCall, Throwable t) {

            }
        });


        ////////////////////////////////Banner model list end//////////////////////////////

        ////////////////////////////////////Simple Vertical list start////////////////////////////
        recyclerViewSimple = (RecyclerView) view.findViewById(R.id.recyclerViewSimple);
        LinearLayoutManager layoutManagerSimpleVerticalSlider = new LinearLayoutManager(getContext());
        layoutManagerSimpleVerticalSlider.setOrientation(RecyclerView.VERTICAL);
        recyclerViewSimple.setLayoutManager(layoutManagerSimpleVerticalSlider);

        simpleVerticalModelList = new ArrayList<>();
        Call<Users> random_shops = apiInterface.getRandomShops();
        random_shops.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                simpleVerticalModelList = response.body().getRandom_shops();

                simpleVerticalAdapter = new SimpleVerticalAdapter(simpleVerticalModelList,getContext());
                recyclerViewSimple.setAdapter(simpleVerticalAdapter);
                simpleVerticalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });




        ////////////////////////////////Simple Vertical list end//////////////////////////////

        ////////////////////////////////////Great offers model list start////////////////////////////
        greatGreatOffersHorizontal = (RecyclerView) view.findViewById(R.id.recyclerViewgreatOffersHorizontal);
        LinearLayoutManager layoutManagerGreatOffers = new LinearLayoutManager(getContext());
        layoutManagerGreatOffers.setOrientation(RecyclerView.HORIZONTAL);
        greatGreatOffersHorizontal.setLayoutManager(layoutManagerGreatOffers);

        greatOffersModel = new ArrayList<>();
        Call<Users> greatOffersCall = apiInterface.greatOffersShop();
        greatOffersCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                greatOffersModel = response.body().getGreat_offers_shops();

                greatOffersAdapter = new GreatOffersAdapter(greatOffersModel,getContext());
                greatGreatOffersHorizontal.setAdapter(greatOffersAdapter);
                greatOffersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });

        ////////////////////////////////Great offers model list end//////////////////////////////


        ////////////////////////////////////New Great Offers vertical slider start////////////////////////////
        greatOffersRecyclerViewVertical = (RecyclerView) view.findViewById(R.id.greatOffersRecyclerViewVertical);
        LinearLayoutManager layoutManagerVerticalGreatOffers = new LinearLayoutManager(getContext());
        layoutManagerVerticalGreatOffers.setOrientation(RecyclerView.VERTICAL);
        greatOffersRecyclerViewVertical.setLayoutManager(layoutManagerVerticalGreatOffers);

        simpleVerticalModelList = new ArrayList<>();
        Call<Users> greatOffersVerticalCall = apiInterface.greatOffersVerticalShop();
        greatOffersVerticalCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                simpleVerticalModelList = response.body().getGreat_offers_shops_vertical();

                simpleVerticalAdapter = new SimpleVerticalAdapter(simpleVerticalModelList,getContext());
                greatOffersRecyclerViewVertical.setAdapter(simpleVerticalAdapter);
                simpleVerticalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });
        ////////////////////////////////New Great Offers vertical slider end//////////////////////////////


        ////////////////////////////////////New arrival horizontal model list start////////////////////////////
        newArrivalHorizontalRecyclerview = (RecyclerView) view.findViewById(R.id.newArrivalHorizontalRecyclerview);
        LinearLayoutManager layoutManagerHorizontalNewArrival = new LinearLayoutManager(getContext());
        layoutManagerHorizontalNewArrival.setOrientation(RecyclerView.HORIZONTAL);
        newArrivalHorizontalRecyclerview.setLayoutManager(layoutManagerHorizontalNewArrival);

        greatOffersModel = new ArrayList<>();
        Call<Users> arrivalCall = apiInterface.newArrivalsShops();
        arrivalCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                greatOffersModel = response.body().getNew_arrivals_shops();

                greatOffersAdapter = new GreatOffersAdapter(greatOffersModel,getContext());
                newArrivalHorizontalRecyclerview.setAdapter(greatOffersAdapter);
                greatOffersAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });

        ////////////////////////////////New arrival horizontal model list end//////////////////////////////

        ////////////////////////////////////New arrival vertical slider start////////////////////////////
        newArrivalVerticalRecyclerview = (RecyclerView) view.findViewById(R.id.newArrivalVerticalRecyclerview);
        LinearLayoutManager layoutManagerVerticalNewArrival = new LinearLayoutManager(getContext());
        layoutManagerVerticalNewArrival.setOrientation(RecyclerView.VERTICAL);
        newArrivalVerticalRecyclerview.setLayoutManager(layoutManagerVerticalNewArrival);

        simpleVerticalModelList = new ArrayList<>();
        Call<Users> arrivalVerticalCall = apiInterface.newArrivalsVerticalShops();
        arrivalVerticalCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                simpleVerticalModelList = response.body().getNew_arrivals_shops_vertical();

                simpleVerticalAdapter = new SimpleVerticalAdapter(simpleVerticalModelList,getContext());
                newArrivalVerticalRecyclerview.setAdapter(simpleVerticalAdapter);
                simpleVerticalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });


        ////////////////////////////////New arrival vertical slider end//////////////////////////////


        ////////////////////////////////////New arrival horizontal model list start////////////////////////////
        exclusiveHorizontalRecyclerview = (RecyclerView) view.findViewById(R.id.exclusiveHorizontalRecyclerview);
        LinearLayoutManager layoutManagerExclusiveHorizontal = new LinearLayoutManager(getContext());
        layoutManagerExclusiveHorizontal.setOrientation(RecyclerView.HORIZONTAL);
        exclusiveHorizontalRecyclerview.setLayoutManager(layoutManagerExclusiveHorizontal);

        greatOffersModel = new ArrayList<>();
        Call<Users> exclusiveCall = apiInterface.balageruExclusive();
        exclusiveCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                greatOffersModel = response.body().getBalageru_exclusive();

                greatOffersAdapter = new GreatOffersAdapter(greatOffersModel,getContext());
                exclusiveHorizontalRecyclerview.setAdapter(greatOffersAdapter);
                greatOffersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });
        ////////////////////////////////New arrival horizontal model list end//////////////////////////////


        ////////////////////////////////////New arrival vertical slider start////////////////////////////
        exclusiveVerticalRecyclerview = (RecyclerView) view.findViewById(R.id.exclusiveVerticalRecyclerview);
        LinearLayoutManager layoutManagerExclusiveVertical = new LinearLayoutManager(getContext());
        layoutManagerExclusiveVertical.setOrientation(RecyclerView.VERTICAL);
        exclusiveVerticalRecyclerview.setLayoutManager(layoutManagerExclusiveVertical);

        simpleVerticalModelList = new ArrayList<>();
        Call<Users> exclusiveVerticalCall = apiInterface.balageruExclusiveVertical();
        exclusiveVerticalCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                simpleVerticalModelList = response.body().getBalageru_exclusive_vertical();

                simpleVerticalAdapter = new SimpleVerticalAdapter(simpleVerticalModelList,getContext());
                exclusiveVerticalRecyclerview.setAdapter(simpleVerticalAdapter);
                simpleVerticalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });
        ////////////////////////////////New arrival vertical slider end//////////////////////////////


    }

    private void onSetNavigationDrawerEvents() {

        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) view.findViewById(R.id.navigationView);


        navigationBar = (ImageView) view.findViewById(R.id.navigationBar);

        login = (TextView) view.findViewById(R.id.login);
        logout = (TextView) view.findViewById(R.id.logout);
        bookmarks = (RelativeLayout) view.findViewById(R.id.relativeLayout3);
        eightMMGold = (RelativeLayout) view.findViewById(R.id.relativeLayout4);

        your_orders = (TextView) view.findViewById(R.id.your_orders);
        favourite_orders = (TextView) view.findViewById(R.id.favourite_orders);
        address_book = (TextView) view.findViewById(R.id.address_book);
        online_ordering_help = (TextView) view.findViewById(R.id.online_ordering_help);
        send_feedback = (TextView) view.findViewById(R.id.send_feedback);
        report_safety_emergency = (TextView) view.findViewById(R.id.report_safety_emergency);
        rate_playstore = (TextView) view.findViewById(R.id.rate_playstore);



        navigationBar.setOnClickListener(this);
        login.setOnClickListener(this);
        logout.setOnClickListener(this);
        bookmarks.setOnClickListener(this);
        eightMMGold.setOnClickListener(this);

        your_orders.setOnClickListener(this);
        favourite_orders.setOnClickListener(this);
        address_book.setOnClickListener(this);
        online_ordering_help.setOnClickListener(this);
        send_feedback.setOnClickListener(this);
        report_safety_emergency.setOnClickListener(this);
        rate_playstore.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.navigationBar:
                drawerLayout.openDrawer(navigationView, true);
                break;
            case R.id.login:
                Login();
                break;
            case R.id.logout:
                Logout();
                break;
            case R.id.relativeLayout3:
                Toast.makeText(getContext(),"bookmarks", Toast.LENGTH_SHORT).show();
                break;
            case R.id.relativeLayout4:
                Toast.makeText(getContext(),"eightMMGold", Toast.LENGTH_SHORT).show();
                break;
            case R.id.your_orders:
                Toast.makeText(getContext(),"your_orders", Toast.LENGTH_SHORT).show();
                break;
            case R.id.favourite_orders:
                Toast.makeText(getContext(),"favourite_orders", Toast.LENGTH_SHORT).show();
                break;
            case R.id.address_book:
                Toast.makeText(getContext(),"address_book", Toast.LENGTH_SHORT).show();
                break;
            case R.id.online_ordering_help:
                Toast.makeText(getContext(),"online_ordering_help", Toast.LENGTH_SHORT).show();
                break;
            case R.id.send_feedback:
                Toast.makeText(getContext(),"send_feedback", Toast.LENGTH_SHORT).show();
                break;
            case R.id.report_safety_emergency:
                Toast.makeText(getContext(),"report_safety_emergency", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rate_playstore:
                Toast.makeText(getContext(),"rate_playstore", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void Logout() {

        sessionManager.editor.clear();
        sessionManager.editor.commit();

        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
//        Animatoo.animateSwipeRight(getContext());
    }
    private void Login() {

        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
//        Animatoo.animateSwipeRight(getContext());
    }

    @Override
    public void onStart() {
        super.onStart();

        if(sessionManager.isLogin())
        {
            login.setVisibility(View.GONE);
            logout.setVisibility(View.VISIBLE);
        }
    }
}