package com.example.balageru_user_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balageru_user_app.Adapters.CartAdapter;
import com.example.balageru_user_app.Product.Cart;
import com.example.balageru_user_app.Product.Payment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Checkout extends AppCompatActivity {

    RecyclerView recyclerViewCheckout;
    RadioButton yes, no;
    EditText editLocation;
    LinearLayout layoutLocation;
    CartAdapter adapter;
    Button confirmOrder;
    TextView checkTotalPrice;

    ArrayList<Cart> checkoutList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        recyclerViewCheckout= findViewById(R.id.recyclerViewCheckout);
        yes= findViewById(R.id.yes);
        no= findViewById(R.id.no);
        editLocation= findViewById(R.id.editLocation);
        layoutLocation= findViewById(R.id.layoutLocation);
        confirmOrder = findViewById(R.id.confirmOrder);
        checkTotalPrice = findViewById(R.id.checkTotalPrice);
        String totalPrice = getIntent().getStringExtra("TotalPrice");
        checkTotalPrice.setText(totalPrice +"\t ETB");

//        back = findViewById(R.id.back);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewCheckout.setLayoutManager(layoutManager);

        Drawable drawable = ContextCompat.getDrawable(this.getApplicationContext(), R.drawable.divider);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewCheckout.getContext(), DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(drawable);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        SharedPreferences user_id_stored=getSharedPreferences("USER_ID",MODE_PRIVATE);
        database.collection("Cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for(DocumentSnapshot snapshot: task.getResult()){
                        if(user_id_stored.getString("userIdStored", null).equals(snapshot.get("SellerId"))){
                            Cart singleCart = new Cart(snapshot.get("ProductName").toString(),snapshot.get("ProductPrice").toString(),snapshot.get("ProductQuantity").toString(), Uri.parse(snapshot.get("ProductImageUrl").toString()),snapshot.get("SellerId").toString());
                            checkoutList.add(singleCart);
                        };
                    }
                    adapter=new CartAdapter(checkoutList);
                    recyclerViewCheckout.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
                else {

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Checkout.this, Payment.class);
                intent.putExtra("TotalPrice",String.valueOf(totalPrice));
                intent.putExtra("Location",editLocation.getText().toString());
                startActivity(intent);
                finish();
            }
        });

        yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    layoutLocation.setVisibility(View.VISIBLE);
                }
            }
        });
        no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    layoutLocation.setVisibility(View.GONE);
                }
            }
        });
    }
}