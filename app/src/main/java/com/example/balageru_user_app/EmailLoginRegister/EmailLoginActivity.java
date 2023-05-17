package com.example.balageru_user_app.EmailLoginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.balageru_user_app.HomeActivity;
import com.example.balageru_user_app.MainActivity;
import com.example.balageru_user_app.OperationRetrofitApi.ApiClient;
import com.example.balageru_user_app.OperationRetrofitApi.ApiInterface;
import com.example.balageru_user_app.OperationRetrofitApi.Users;
import com.example.balageru_user_app.PhoneLoginRegister.PhoneRegisterActivity;
import com.example.balageru_user_app.R;
import com.example.balageru_user_app.Sessions.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailLoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button btnLogin;
    public static ApiInterface apiInterface;
    String user_id;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        ////////hide status bar hide ///////
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ////////hide status bar end///////
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        sessionManager = new SessionManager(this);

        init();
    }

    private void init() {
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.button2);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });

    }

    private void Login() {
        
        String user_email = email.getText().toString().trim();
        String user_password= password.getText().toString().trim();
        
        if(TextUtils.isEmpty(user_email))
        {
            email.setError("Email is required");
        }else if(TextUtils.isEmpty(user_password))
        {
            password.setError("Password is required");
        }
        else
        {
            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Logging...");
            dialog.setMessage("Please wait while we are checking your credentials");
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);

            Call<Users> call = apiInterface.performEmailLogin(user_email, user_password);
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {

                    if(response.body().getResponse().equals("ok"))
                    {
                        user_id = response.body().getUserId();
                        sessionManager.createSession(user_id);

                        Intent intent = new Intent(EmailLoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
//                        Animatoo.animateSwipeLeft(EmailLoginActivity.this)
                        dialog.dismiss();
                    }
                    else if(response.body().getResponse().equals("no_account"))
                    {
                        Toast.makeText(EmailLoginActivity.this, "No Account Found!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    Toast.makeText(EmailLoginActivity.this, "Something went wrong, Please try again!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(EmailLoginActivity.this, EmailRegisterActivity.class);
        startActivity(intent);
//        Animatoo.animateSwipeLeft(this);
        finish();
    }

    public void backToMainPage(View view) {

        Intent intent = new Intent(EmailLoginActivity.this, MainActivity.class);
        startActivity(intent);
//        Animatoo.animateSwipeRight(this);
    }
}