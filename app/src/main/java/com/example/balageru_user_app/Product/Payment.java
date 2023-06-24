package com.example.balageru_user_app.Product;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.balageru_user_app.Checkout;
import com.example.balageru_user_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;

public class Payment extends AppCompatActivity {

    HashMap<String, String> orderToBeOrdered = new HashMap<>();
    LocalDate currentDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        AppCompatImageButton back=findViewById(R.id.back);

        Double totalPrice = Double.parseDouble(getIntent().getStringExtra("TotalPrice"));
        WebView webView= findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                if(request.getUrl().toString().equals("https://chapapayment.onrender.com/pay")){
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    SharedPreferences user_id_stored=getSharedPreferences("USER_ID",MODE_PRIVATE);

                    String location = getIntent().getStringExtra("Location");
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        currentDate = LocalDate.now();
                    }


                    db.collection("Cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){

                                for(DocumentSnapshot snapshot: task.getResult()){
                                    if(user_id_stored.getString("userIdStored", null).equals(snapshot.get("SellerId"))){
                                        orderToBeOrdered.put("ProductName", snapshot.get("ProductName").toString() );
                                        orderToBeOrdered.put("ProductImageUrl", snapshot.get("ProductImageUrl").toString() );
                                        orderToBeOrdered.put("ProductPrice", snapshot.get("ProductPrice").toString() );
                                        orderToBeOrdered.put("ProductQuantity", snapshot.get("ProductQuantity").toString() );
                                        orderToBeOrdered.put("SellerId", snapshot.get("SellerId").toString() );
                                        orderToBeOrdered.put("Location", location);
                                        orderToBeOrdered.put("Date", String.valueOf(currentDate));
                                        
                                        db.collection("Order").add(orderToBeOrdered).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if(task.isSuccessful()){
                                                    DocumentReference reference = snapshot.getReference();
                                                    reference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                        }
                                                    });
                                                }else
                                                {
                                                    Toast.makeText(Payment.this, "Order Failed.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                        
                                    }
                                }
                            }
                        }
                    });

                }
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String token = UUID.randomUUID().toString().replace("-", "");
                String javascript3 = "document.getElementById('inp_amount').value = '" +  totalPrice +  "';";
                String javascript2 = "document.getElementById('tex_ref').value = '" + token + "';";
                String javascript4 = "document.getElementById('inp_amount').readOnly = true;";
                webView.evaluateJavascript(javascript4,null);
                webView.evaluateJavascript(javascript2, null);
                webView.evaluateJavascript(javascript3, null);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Payment.this, Checkout.class);
                startActivity(intent);
                finish();
            }
        });


        webView.loadUrl("https://chapapayment.onrender.com");
    }
}