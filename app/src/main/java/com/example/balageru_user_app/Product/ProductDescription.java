package com.example.balageru_user_app.Product;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.balageru_user_app.HomeActivity;
import com.example.balageru_user_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ProductDescription extends AppCompatActivity {

    TextView prod_Name,prod_Price,seller_Name,prod_Description;
    ImageView prod_Image;
    String productName,productPrice,sellerName,productDescription, sellerId;
    Button open_cart;

    HashMap<String, String> cartToBePosted= new HashMap<>();

    int cartQty=0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        AppCompatImageButton addToCart=findViewById(R.id.add_to_cart);
        AppCompatImageButton deleteFromCart=findViewById(R.id.delete_from_cart);
        TextView quantity = findViewById(R.id.quantity);

        open_cart= findViewById(R.id.open_cart);

        FirebaseFirestore database = FirebaseFirestore.getInstance();


        prod_Name=findViewById(R.id.prod_name);
        prod_Price=findViewById(R.id.prod_price);
        seller_Name=findViewById(R.id.seller_name);
        prod_Description=findViewById(R.id.prod_desc);
        prod_Image=findViewById(R.id.prod_img);
        AppCompatImageButton back=findViewById(R.id.back);
        productName=getIntent().getStringExtra("productName");
        productPrice=getIntent().getStringExtra("productPrice");
        sellerName=getIntent().getStringExtra("sellerName");
        sellerId=getIntent().getStringExtra("sellerId");
        productDescription=getIntent().getStringExtra("productDescription");

        String productImageUrl= String.valueOf(Uri.parse(getIntent().getStringExtra("productImageUrl")));
        Picasso.get().load(productImageUrl).into(prod_Image);
        prod_Price.setText(productPrice);
        prod_Description.setText(productDescription);
        prod_Name.setText(productName);
        seller_Name.setText(sellerName);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProductDescription.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
            });
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity.setText(String.valueOf(++cartQty));


            }
        });
        deleteFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cartQty<=1){
                    cartQty=0;
                }
                quantity.setText(String.valueOf(--cartQty));
            }
        });

        open_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantity.getText().toString().equals("0")){
                    Toast.makeText(ProductDescription.this, "You need to add at least 1 item.", Toast.LENGTH_SHORT).show();
                }else{
                    cartToBePosted.put("ProductName", productName);
                    cartToBePosted.put("ProductPrice", productPrice);
                    cartToBePosted.put("ProductImageUrl", productImageUrl);
                    cartToBePosted.put("SellerId", sellerId);
                    cartToBePosted.put("ProductQuantity", quantity.getText().toString());

                    database.collection("Cart").add(cartToBePosted).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(ProductDescription.this, "Product added to Cart Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }


            }
        });
    }
}
