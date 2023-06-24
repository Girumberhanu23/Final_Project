package com.example.balageru_user_app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balageru_user_app.Models.OrderModel;
import com.example.balageru_user_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    ArrayList<OrderModel> orderDataSet;
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName;
        private TextView productPrice;
        private TextView productQty;
        private TextView location;
        private TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage= itemView.findViewById(R.id.cartImage);
            productName= itemView.findViewById(R.id.cartName);
            productPrice= itemView.findViewById(R.id.cartPrice);
            productQty= itemView.findViewById(R.id.cartQuantity);
            location = itemView.findViewById(R.id.orderLocation);
            date = itemView.findViewById(R.id.orderDate);
        }

        public TextView getLocation() {
            return location;
        }

        public TextView getDate() {
            return date;
        }

        public ImageView getProductImage() {
            return productImage;
        }

        public TextView getProductName() {
            return productName;
        }

        public TextView getProductPrice() {
            return productPrice;
        }

        public TextView getProductQty() {
            return productQty;
        }
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_row,parent,false);


        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        Picasso.get().load(orderDataSet.get(position).getProductImage()).into(holder.getProductImage());

        holder.getProductName().setText(orderDataSet.get(position).getProductName());
        holder.getProductPrice().setText(orderDataSet.get(position).getProductPrice());
        holder.getProductQty().setText(orderDataSet.get(position).getProductQuantity());
        holder.getLocation().setText(orderDataSet.get(position).getLocation());
        holder.getDate().setText(orderDataSet.get(position).getDate());

    }

    public OrderAdapter(ArrayList<OrderModel> orderDataSet) {
        this.orderDataSet = orderDataSet;
    }
    @Override
    public int getItemCount() {
        return orderDataSet.size();
    }


}
