package com.example.balageru_user_app.EmailLoginRegister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.balageru_user_app.HomeActivity;
import com.example.balageru_user_app.MainActivity;
import com.example.balageru_user_app.OperationRetrofitApi.ApiClient;
import com.example.balageru_user_app.OperationRetrofitApi.ApiInterface;
import com.example.balageru_user_app.OperationRetrofitApi.Users;
import com.example.balageru_user_app.R;
import com.example.balageru_user_app.Sessions.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailLoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button btnLogin;
    public static ApiInterface apiInterface;
    String user_id, user_name;
    SessionManager sessionManager;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        ////////hide status bar hide ///////
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ////////hide status bar end///////
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        sessionManager = new SessionManager(this);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Logging...");
        dialog.setMessage("Please wait while we are checking your credentials");
        dialog.setCanceledOnTouchOutside(false);
        init();
    }

    private void init() {
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.button2);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Login();
                firebaseLogin(email.getText().toString(), password.getText().toString());
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


            Call<Users> call = apiInterface.performEmailLogin(user_email, user_password);
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {

                    if(response.body().getResponse().equals("ok"))
                    {
                        user_id = response.body().getUserId();
                        user_name = response.body().getUserName();
                        sessionManager.createSession(user_id);
                        sessionManager.createSession(user_name);


//                        Intent intent = new Intent(EmailLoginActivity.this, HomeActivity.class);
//                        startActivity(intent);
//                        finish();
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
    public void firebaseLogin(String email, String password){
        dialog.show();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    db.collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for(DocumentSnapshot snapshot: task.getResult()){
                                if(email.equals(snapshot.get("UserEmail"))){

                                    SharedPreferences user_id_stored= getSharedPreferences("USER_ID",MODE_PRIVATE);
                                    SharedPreferences.Editor editor=user_id_stored.edit();
                                    editor.putString("userNameStored", snapshot.get("UserName").toString());
                                    editor.putString("userIdStored", snapshot.getId());
                                    editor.putString("userEmailStored", snapshot.get("UserEmail").toString());
                                    editor.putString("userPhoneStored", snapshot.get("UserPhone").toString());
                                    editor.putString("userCity", snapshot.get("city").toString());
                                    editor.putString("userSubcity", snapshot.get("subCity").toString());


                                    editor.commit();
                                    dialog.dismiss();
                                    Intent intent = new Intent(EmailLoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }
                    });
                }else{
                    Toast.makeText(EmailLoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }




            }
        });
    }
}