package com.example.balageru_user_app;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balageru_user_app.Adapters.OrderAdapter;
import com.example.balageru_user_app.Models.OrderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Order extends AppCompatActivity {

//    AppCompatImageButton back;
    RecyclerView recyclerViewOrdersList;
    ArrayList<OrderModel> orderDataSet= new ArrayList<>();
    private OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        SharedPreferences user_id_stored=getSharedPreferences("USER_ID",MODE_PRIVATE);
//        back = findViewById(R.id.back);
        recyclerViewOrdersList = findViewById(R.id.recyclerViewOrdersList);

        LinearLayoutManager layoutManagerOrder=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewOrdersList.setLayoutManager(layoutManagerOrder);

        FirebaseFirestore orderData = FirebaseFirestore.getInstance();
        orderData.collection("Order").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot snapshot: task.getResult()){
                        if (snapshot.get("SellerId").toString().equals(user_id_stored.getString("userIdStored", null))){
                            OrderModel singleOrder = new OrderModel(snapshot.get("ProductName").toString(),snapshot.get("ProductPrice").toString(),snapshot.get("ProductQuantity").toString(), Uri.parse(snapshot.get("ProductImageUrl").toString()),snapshot.get("SellerId").toString(),snapshot.get("Location").toString(),snapshot.get("Date").toString());
                            orderDataSet.add(singleOrder);

                        }
                    }
                    adapter=new OrderAdapter(orderDataSet);
                    recyclerViewOrdersList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else
                {

                }
            }
        });



//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Order.this, HomeActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }
}