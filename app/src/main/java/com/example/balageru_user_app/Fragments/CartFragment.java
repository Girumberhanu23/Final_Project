package com.example.balageru_user_app.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balageru_user_app.Adapters.CartAdapter;
import com.example.balageru_user_app.Checkout;
import com.example.balageru_user_app.MainActivity;
import com.example.balageru_user_app.Order;
import com.example.balageru_user_app.Product.Cart;
import com.example.balageru_user_app.R;
import com.example.balageru_user_app.Sessions.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment implements  View.OnClickListener{



    public CartFragment() {
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

    private CartAdapter adapter;
    Button checkoutBtn;
    RecyclerView myCart;

    ArrayList<Cart> cartElement=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_cart, container, false);
        sessionManager = new SessionManager(getContext());

        SharedPreferences user_id_stored=getActivity().getSharedPreferences("USER_ID",MODE_PRIVATE);

        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        myCart= view.findViewById(R.id.myCart);
        myCart.setLayoutManager(layoutManager);

        checkoutBtn= view.findViewById(R.id.checkout);


        firebaseFirestore.collection("Cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot snapshot: task.getResult()){
                        if (snapshot.get("SellerId").toString().equals(user_id_stored.getString("userIdStored", null))){
                            Cart singleCart = new Cart(snapshot.get("ProductName").toString(),snapshot.get("ProductPrice").toString(),snapshot.get("ProductQuantity").toString(), Uri.parse(snapshot.get("ProductImageUrl").toString()),snapshot.get("SellerId").toString());
                            cartElement.add(singleCart);
                        }
                    }
                    adapter=new CartAdapter(cartElement);
                    myCart.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    checkoutBtn.setText("Total Price: "+ calculateTotalPrice()+" \t ETB \n Checkout");
                }
                else
                {

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Checkout.class);
                intent.putExtra("TotalPrice", String.valueOf(calculateTotalPrice()));
                startActivity(intent);



            }
        });


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
                Intent intent = new Intent(getActivity(), Order.class);
                startActivity(intent);

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

    private void showToast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
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
    public Double calculateTotalPrice() {
        Double totalPrice = 0.0;

        for (int i = 0; i < cartElement.size(); i++) {
            try {
                Double productPrice = Double.parseDouble(cartElement.get(i).getProductPrice());
                Double productQuantity = Double.parseDouble(cartElement.get(i).getProductQuantity());
                totalPrice += (productPrice * productQuantity);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing price or quantity for cart element at index " + i);
                e.printStackTrace();
                // Handle the error appropriately or skip the element if necessary
            }
        }

        return totalPrice;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter=new CartAdapter(cartElement);
        myCart.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        checkoutBtn.setText("Total Price: "+ calculateTotalPrice()+" \t ETB \n Checkout");
    }

    @Override
    public void onPause() {
        super.onPause();
        cartElement.clear();
    }
}