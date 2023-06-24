package com.example.balageru_user_app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balageru_user_app.Product.Product;
import com.example.balageru_user_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private ArrayList<Product> localDataSet;
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName;
        private TextView productPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage= itemView.findViewById(R.id.productImage);
            productName= itemView.findViewById(R.id.productName);
            productPrice= itemView.findViewById(R.id.productPrice);



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
    }

    public ProductAdapter(ArrayList<Product> dataSet){
        this.localDataSet = dataSet;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card,parent,false);


        return new ProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        holder.getProductName().setText(localDataSet.get(position).getProductName());
        holder.getProductPrice().setText(localDataSet.get(position).getProductPrice());

        Picasso.get().load(localDataSet.get(position).getProductImg()).into(holder.getProductImage());





    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }


}
