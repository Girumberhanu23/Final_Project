package com.example.balageru_user_app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balageru_user_app.Product.Cart;
import com.example.balageru_user_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{

    ArrayList<Cart> cartDataSet;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName;
        private TextView productPrice;
        private TextView productQty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage= itemView.findViewById(R.id.cartImage);
            productName= itemView.findViewById(R.id.cartName);
            productPrice= itemView.findViewById(R.id.cartPrice);
            productQty= itemView.findViewById(R.id.cartQuantity);
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
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart,parent,false);


        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        Picasso.get().load(cartDataSet.get(position).getProductImage()).into(holder.getProductImage());

        holder.getProductName().setText(cartDataSet.get(position).getProductName());
        holder.getProductPrice().setText(cartDataSet.get(position).getProductPrice());
        holder.getProductQty().setText(cartDataSet.get(position).getProductQuantity());

    }

    public CartAdapter(ArrayList<Cart> cartDataSet) {
        this.cartDataSet = cartDataSet;
    }

    @Override
    public int getItemCount() {
        return cartDataSet.size();
    }
}
