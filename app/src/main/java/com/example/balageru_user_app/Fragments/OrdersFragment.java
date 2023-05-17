package com.example.balageru_user_app.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.balageru_user_app.MainActivity;
import com.example.balageru_user_app.R;
import com.example.balageru_user_app.Sessions.SessionManager;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment implements  View.OnClickListener{



    public OrdersFragment() {
        // Required empty public constructor
    }

    DrawerLayout drawerLayout;
    ImageView navigationBar;
    NavigationView navigationView;
    private View view;
    private RelativeLayout bookmarks, eightMMGold;
    private TextView your_orders, favourite_orders, address_book, online_ordering_help, send_feedback, report_safety_emergency, rate_playstore;
    SessionManager sessionManager;
    private TextView login, logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_orders, container, false);
        sessionManager = new SessionManager(getContext());


        onSetNavigationDrawerEvents();
        return view;
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