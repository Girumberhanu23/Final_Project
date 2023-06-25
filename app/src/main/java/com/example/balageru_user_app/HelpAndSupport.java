package com.example.balageru_user_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HelpAndSupport extends AppCompatActivity {

    Button btn_Ok_help_and_support;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_and_support);

        btn_Ok_help_and_support= findViewById(R.id.btn_Ok_help_and_support);

        btn_Ok_help_and_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelpAndSupport.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}