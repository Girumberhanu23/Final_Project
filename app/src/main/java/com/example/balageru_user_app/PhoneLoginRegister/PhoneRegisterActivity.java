package com.example.balageru_user_app.PhoneLoginRegister;

import androidx.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.example.balageru_user_app.EmailLoginRegister.EmailLoginActivity;
import com.example.balageru_user_app.EmailLoginRegister.EmailRegisterActivity;
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

public class PhoneRegisterActivity extends AppCompatActivity {

    private EditText phone, otp;
    private Button btnReg, btnOtp;
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
        setContentView(R.layout.activity_phone_register);

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
        btnOtp = (Button) findViewById(R.id.button3);
        btnReg = (Button) findViewById(R.id.button2);

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
                btnReg.setVisibility(View.VISIBLE);
                Toast.makeText(PhoneRegisterActivity.this, "Invalid Phone Number"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token)
            {
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                Toast.makeText(PhoneRegisterActivity.this, "Code has been sent, please check and verify ", Toast.LENGTH_SHORT).show();

                otp.setVisibility(View.VISIBLE);
                phone.setVisibility(View.GONE);
                btnOtp.setVisibility(View.VISIBLE);
                btnReg.setVisibility(View.GONE);
                dialog.setVisibility(View.GONE);
            }
        };
        ////////////////phone otp callback end////////////////

        btnReg.setOnClickListener(new View.OnClickListener() {
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
                                    .setActivity(PhoneRegisterActivity.this)                 // Activity (for callback binding)
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
                    Toast.makeText(PhoneRegisterActivity.this, "Please enter your 6 digit otp", Toast.LENGTH_SHORT).show();
                }
                if(otp.getText().toString().length() != 6)
                {
                    Toast.makeText(PhoneRegisterActivity.this, "Invalid otp", Toast.LENGTH_SHORT).show();
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

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Register();
                        }
                        else
                        {
                            Toast.makeText(PhoneRegisterActivity.this, "Something went wrong, Please try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void Register() {

            Call<Users> call = apiInterface.performPhoneRegistration(phone.getText().toString());
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    if (response.body().getResponse().equals("ok"))
                    {
                        user_id = response.body().getUserId();
                        sessionManager.createSession(user_id);

                        Intent intent = new Intent(PhoneRegisterActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
//                        Animatoo.animateSwipeLeft(PhoneRegisterActivity.this)
                        dialog.setVisibility(View.GONE);
                    }
                    else if (response.body().getResponse().equals("already"))
                    {
                        Toast.makeText(PhoneRegisterActivity.this, "This phone is already exists, Please try another one.", Toast.LENGTH_SHORT).show();
                        dialog.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {

                    dialog.setVisibility(View.GONE);
                }
            });
        }

    public void goToLogin(View view) {

        Intent intent = new Intent(PhoneRegisterActivity.this, PhoneLoginActivity.class);
        startActivity(intent);
//        Animatoo.animateSlideUp(this);
        finish();
    }
    public void backToMainPage(View view) {

        Intent intent = new Intent(PhoneRegisterActivity.this, MainActivity.class);
        startActivity(intent);
//        Animatoo.animateSwipeRight(this);
    }
}