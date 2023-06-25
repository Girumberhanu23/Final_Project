package com.example.balageru_user_app.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.balageru_user_app.HelpAndSupport;
import com.example.balageru_user_app.MainActivity;
import com.example.balageru_user_app.Order;
import com.example.balageru_user_app.R;
import com.example.balageru_user_app.Sessions.SessionManager;
import com.example.balageru_user_app.TermsAndConditions;
import com.google.android.material.navigation.NavigationView;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideosFragment extends Fragment implements  View.OnClickListener {


    public VideosFragment() {
        // Required empty public constructor
    }


    DrawerLayout drawerLayout;
    ImageView navigationBar;
    NavigationView navigationView;
    private View view;
    private TextView your_orders, helpAndSupport, termsAndConditions, callCenter;
    SessionManager sessionManager;
    private TextView login, logout, name, email, phone, city, subCity;
    LinearLayout profileLogout, profileHelpAndSupport, profileTermsAndConditions;


    private String phoneNumber = "0923938609";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_videos, container, false);
        sessionManager = new SessionManager(getContext());

        SharedPreferences user_id_stored= getActivity().getSharedPreferences("USER_ID",MODE_PRIVATE);
        name = view.findViewById(R.id.txtName);
        email = view. findViewById(R.id.txtEmail);
        phone = view. findViewById(R.id.txtPhone);
        city = view. findViewById(R.id.getcity);
        subCity = view. findViewById(R.id.getsubcity);
        name.setText(user_id_stored.getString("userNameStored", null));
        email.setText(user_id_stored.getString("userEmailStored", null));
        phone.setText(user_id_stored.getString("userPhoneStored", null));
        city.setText(user_id_stored.getString("userCity", null));
        subCity.setText(user_id_stored.getString("userSubcity", null));

        profileLogout = view.findViewById(R.id.profileLogout);
        helpAndSupport = view.findViewById(R.id.helpAndSupport);
        termsAndConditions = view.findViewById(R.id.termsAndConditions);

        onSetNavigationDrawerEvents();
        return view;
    }
    private void onSetNavigationDrawerEvents() {

        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) view.findViewById(R.id.navigationView);


        navigationBar = (ImageView) view.findViewById(R.id.navigationBar);

        login = (TextView) view.findViewById(R.id.login);
        logout = (TextView) view.findViewById(R.id.logout);


        your_orders = (TextView) view.findViewById(R.id.your_orders);
        profileHelpAndSupport = (LinearLayout) view.findViewById(R.id.profileHelpAndSupport);
        profileTermsAndConditions = (LinearLayout) view.findViewById(R.id.profileTermsAndConditions);
        callCenter = (TextView) view.findViewById(R.id.callCenter);



        navigationBar.setOnClickListener(this);
        login.setOnClickListener(this);
        logout.setOnClickListener(this);


        your_orders.setOnClickListener(this);
        profileLogout.setOnClickListener(this);
        helpAndSupport.setOnClickListener(this);
        termsAndConditions.setOnClickListener(this);
        profileHelpAndSupport.setOnClickListener(this);
        profileTermsAndConditions.setOnClickListener(this);
        termsAndConditions.setOnClickListener(this);
        callCenter.setOnClickListener(this);

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
            case R.id.profileLogout:
                Logout();
                break;
            case R.id.your_orders:
                Intent intent = new Intent(getActivity(), Order.class);
                startActivity(intent);
                break;
            case R.id.helpAndSupport:
            case R.id.profileHelpAndSupport:
                Intent intent1 = new Intent(getActivity(), HelpAndSupport.class);
                startActivity(intent1);
                break;
            case R.id.termsAndConditions:
            case R.id.profileTermsAndConditions:
                Intent intent2 = new Intent(getActivity(), TermsAndConditions.class);
                startActivity(intent2);
                break;
            case R.id.callCenter:
                dialPhoneNumber(getContext());
                break;

        }
    }

    private void showToast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    private void Logout() {

        sessionManager.editor.clear();
        sessionManager.editor.commit();

        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);

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

    private void dialPhoneNumber(Context context) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}