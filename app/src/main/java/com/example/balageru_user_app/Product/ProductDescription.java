package com.example.balageru_user_app.Product;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.balageru_user_app.HomeActivity;
import com.example.balageru_user_app.R;
import com.squareup.picasso.Picasso;

public class ProductDescription extends AppCompatActivity {
    TextView prod_Name,prod_Price,seller_Name,prod_Description;
    ImageView prod_Image;
    String productName,productPrice,sellerName,productDescription;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        prod_Name=findViewById(R.id.prod_name);
        prod_Price=findViewById(R.id.prod_price);
        seller_Name=findViewById(R.id.seller_name);
        prod_Description=findViewById(R.id.prod_desc);
        prod_Image=findViewById(R.id.prod_img);
        AppCompatImageButton back=findViewById(R.id.back);
        productName=getIntent().getStringExtra("productName");
        productPrice=getIntent().getStringExtra("productPrice");
        sellerName=getIntent().getStringExtra("sellerName");
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
    }
}
