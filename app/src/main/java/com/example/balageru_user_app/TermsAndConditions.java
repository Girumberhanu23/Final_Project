package com.example.balageru_user_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class TermsAndConditions extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

        AppCompatButton agreeTerms= findViewById(R.id.agreeTerms);

        agreeTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermsAndConditions.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}