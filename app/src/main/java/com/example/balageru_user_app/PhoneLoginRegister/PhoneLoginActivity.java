package com.example.balageru_user_app.PhoneLoginRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.example.balageru_user_app.EmailLoginRegister.EmailLoginActivity;
import com.example.balageru_user_app.HomeActivity;
import com.example.balageru_user_app.MainActivity;
import com.example.balageru_user_app.OperationRetrofitApi.ApiClient;
import com.example.balageru_user_app.OperationRetrofitApi.ApiInterface;
import com.example.balageru_user_app.OperationRetrofitApi.Users;
import com.example.balageru_user_app.R;
import com.example.balageru_user_app.Sessions.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneLoginActivity extends AppCompatActivity {
    
    private EditText phone, otp;
    private Button btnLogin, btnOtp;
    public static ApiInterface apiInterface;
    String user_id;


    /////////////////phone otp////////////////////
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private FirebaseAuth mAuth;

    ////////////////////////////////////////////
    ImageView dialog;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        ////////hide status bar hide ///////
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ////////hide status bar end///////
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        mAuth = FirebaseAuth.getInstance();
        sessionManager = new SessionManager(this);

        init();
    }
    
    private void init(){
        phone = (EditText) findViewById(R.id.phone);
        otp = (EditText) findViewById(R.id.otp);
        btnLogin = (Button) findViewById(R.id.button2);
        btnOtp = (Button) findViewById(R.id.button3);

        //////////////////////Progress Dialog/////////////////

        dialog = (ImageView) findViewById(R.id.imageView4);
        Glide.with(this).load(R.drawable.loader).into(dialog);
        /////////////////////////////////////////////////////

        ////////////////phone otp callback start////////////////
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e)
            {
                dialog.setVisibility(View.GONE);
                otp.setVisibility(View.GONE);
                phone.setVisibility(View.VISIBLE);
                btnOtp.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);
                Toast.makeText(PhoneLoginActivity.this, "Invalid Phone Number"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token)
            {
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                Toast.makeText(PhoneLoginActivity.this, "Code has been sent, please check and verify ", Toast.LENGTH_SHORT).show();

                otp.setVisibility(View.VISIBLE);
                phone.setVisibility(View.GONE);
                btnOtp.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.GONE);
                dialog.setVisibility(View.GONE);
            }
        };
        ////////////////phone otp callback end////////////////

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user_phone = phone.getText().toString().trim();
                if(TextUtils.isEmpty(user_phone))
                {
                    phone.setError("Phone is required!");
                }
                if(user_phone.length() != 10)
                {
                    phone.setError("Phone should be 10 digits!");
                }
                else {
                    dialog.setVisibility(View.VISIBLE);
                    PhoneAuthOptions options =
                            PhoneAuthOptions.newBuilder(mAuth)
                                    .setPhoneNumber("+251" + user_phone)       // Phone number to verify
                                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                    .setActivity(PhoneLoginActivity.this)                 // Activity (for callback binding)
                                    .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                                    .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
            }
        });

        btnOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otp.getText().toString().equals(""))
                {
                    Toast.makeText(PhoneLoginActivity.this, "Please enter your 6 digit otp", Toast.LENGTH_SHORT).show();
                }
                if(otp.getText().toString().length() != 6)
                {
                    Toast.makeText(PhoneLoginActivity.this, "Invalid otp", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dialog.setVisibility(View.VISIBLE);
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp.getText().toString().trim());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
    }

    private void Login() {

        Call<Users> call = apiInterface.performPhoneLogin(phone.getText().toString());
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                if(response.body().getResponse().equals("ok"))
                {
                    user_id = response.body().getUserId();
                    sessionManager.createSession(user_id);

                    Intent intent = new Intent(PhoneLoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
//                        Animatoo.animateSwipeLeft(PhoneLoginActivity.this)
                    dialog.setVisibility(View.GONE);
                }
                else if(response.body().getResponse().equals("no_account"))
                {
                    Toast.makeText(PhoneLoginActivity.this, "No Account Found!", Toast.LENGTH_SHORT).show();
                    dialog.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(PhoneLoginActivity.this, "Something went wrong, Please try again!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Login();
                        }
                        else
                        {
                            dialog.setVisibility(View.GONE);
                        }
                    }
                });
    }
    public void goToRegister(View view) {
        Intent intent = new Intent(PhoneLoginActivity.this, PhoneRegisterActivity.class);
        startActivity(intent);
//        Animatoo.animateSlideUp(this);
        finish();
    }
    public void backToMainPage(View view) {

        Intent intent = new Intent(PhoneLoginActivity.this, MainActivity.class);
        startActivity(intent);
//        Animatoo.animateSwipeRight(this);
    }
}