package com.example.balageru_user_app.EmailLoginRegister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.balageru_user_app.MainActivity;
import com.example.balageru_user_app.OperationRetrofitApi.ApiClient;
import com.example.balageru_user_app.OperationRetrofitApi.ApiInterface;
import com.example.balageru_user_app.OperationRetrofitApi.Users;
import com.example.balageru_user_app.R;
import com.example.balageru_user_app.Sessions.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailRegisterActivity extends AppCompatActivity {

    private EditText name, email, registerPhone, password, registerCity, registerSubcity;
    private Button regBtn;
    public static ApiInterface apiInterface;
    String user_id;
    SessionManager sessionManager;
    ProgressDialog dialog;
    ProgressBar spinKit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_register);

        ////////hide status bar hide ///////
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ////////hide status bar end///////
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        sessionManager = new SessionManager(this);

        init();
    }

    private void init() {
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        registerPhone = (EditText) findViewById(R.id.registerPhone);
        password = (EditText) findViewById(R.id.password);
        regBtn =(Button) findViewById(R.id.button2);
        registerCity = (EditText) findViewById(R.id.registerCity);
        registerSubcity = (EditText) findViewById(R.id.registerSubcity);
        spinKit = findViewById(R.id.spinKit);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                Registration();
                registerToFirebase(name.getText().toString(), email.getText().toString(),registerPhone.getText().toString(), password.getText().toString(),registerCity.getText().toString(),registerSubcity.getText().toString());

            }
        });
    }

    private void Registration() {
        String user_name = name.getText().toString().trim();
        String user_email = email.getText().toString().trim();
        String user_password = password.getText().toString().trim();

        if(TextUtils.isEmpty(user_name))
        {
            name.setError("Name is required!");
        }
        else if(TextUtils.isEmpty(user_email))
        {
            email.setError("Email is required!");;
        }
        else if(TextUtils.isEmpty(user_password))
        {
            password.setError("Password is required!");;
        }
        else
        {
//            dialog = new ProgressDialog(this);
//            dialog.setTitle("Registering...");
//            dialog.setMessage("Please wait while we are adding your credentials");
//            dialog.show();
//            dialog.setCanceledOnTouchOutside(false);

            Call<Users> call = apiInterface.performEmailRegistration(user_name, user_email, user_password);
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    if (response.body().getResponse().equals("ok"))
                    {
                        user_id = response.body().getUserId();
                        sessionManager.createSession(user_id);

//                        Intent intent = new Intent(EmailRegisterActivity.this, HomeActivity.class);
//                        startActivity(intent);
//                        finish();
//                        Animatoo.animateSwipeLeft(EmailRegisterActivity.this)

//                        dialog.dismiss();
                    }
                    else if (response.body().getResponse().equals("failed"))
                    {
                        Toast.makeText(EmailRegisterActivity.this, "Something went wrong, Please try again!", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
                    }
                    else if (response.body().getResponse().equals("already"))
                    {
                        Toast.makeText(EmailRegisterActivity.this, "This email is already exists, Please try another one.", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    Toast.makeText(EmailRegisterActivity.this, "Something went wrong, Please try again!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(EmailRegisterActivity.this, EmailLoginActivity.class);
        startActivity(intent);
//        Animatoo.animateSwipeLeft(this);
        finish();
    }
    public void backToMainPage(View view) {

        Intent intent = new Intent(EmailRegisterActivity.this, MainActivity.class);
        startActivity(intent);
//        Animatoo.animateSwipeRight(this);
    }
    public void registerToFirebase(String name, String email, String phone, String password, String city, String subCity){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore userDb = FirebaseFirestore.getInstance();
        HashMap<String, String> user= new HashMap<>();
        user.put("UserName", name);
        user.put("UserPhone", phone);
        user.put("UserEmail", email);
        user.put("UserPassword", password);
        user.put("city", city);
        user.put("subCity", subCity);

        spinKit.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    userDb.collection("User").add(user).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
//                            dialog.dismiss();
                            if(task.isSuccessful()){
                                spinKit.setVisibility(View.GONE);
                                Intent intent = new Intent(EmailRegisterActivity.this, EmailLoginActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(EmailRegisterActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                spinKit.setVisibility(View.GONE);
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EmailRegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}