package com.example.balageru_user_app.Adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.balageru_user_app.Models.GreatOffersModel;
import com.example.balageru_user_app.R;

import java.util.List;

public class GreatOffersAdapter extends RecyclerView.Adapter<GreatOffersAdapter.GreatOfferViewHolder>{

    List<GreatOffersModel> greatOffersModelList;
    Context context;

    public GreatOffersAdapter(List<GreatOffersModel> greatOffersModelList, Context context) {
        this.greatOffersModelList = greatOffersModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public GreatOfferViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_horizontal_great_offers,viewGroup,false);
        return new GreatOfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GreatOfferViewHolder holder, int position) {

        GreatOffersModel greatOffersModel = greatOffersModelList.get(position);

        Glide.with(context).load(greatOffersModel.getShop_image()).placeholder(R.drawable.small_placeholder).into(holder.shopImg);

        holder.shop_name.setText(greatOffersModel.getShop_name());
        holder.discount.setText(greatOffersModel.getDiscount()+"% OFF");
        holder.rating.setText(greatOffersModel.getRating());

    }

    @Override
    public int getItemCount() {
        return greatOffersModelList.size();
    }

    public class GreatOfferViewHolder extends RecyclerView.ViewHolder {

        private ImageView shopImg;
        private TextView shop_name, discount, rating, time;

        public GreatOfferViewHolder(@NonNull View itemView) {
            super(itemView);

            shopImg = (ImageView) itemView.findViewById(R.id.imageView7);
            shop_name = (TextView) itemView.findViewById(R.id.textView9);
            time = (TextView) itemView.findViewById(R.id.textView10);
            discount = (TextView) itemView.findViewById(R.id.textView11);
            rating = (TextView) itemView.findViewById(R.id.textView12);

        }
    }
}
