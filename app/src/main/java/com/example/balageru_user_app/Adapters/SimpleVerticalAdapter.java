package com.example.balageru_user_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.balageru_user_app.Models.SimpleVerticalModel;
import com.example.balageru_user_app.R;

import java.util.List;

public class SimpleVerticalAdapter extends RecyclerView.Adapter<SimpleVerticalAdapter.PlateViewHolder> {

    private List<SimpleVerticalModel> simpleVerticalModelList;
    private Context context;

    public SimpleVerticalAdapter(List<SimpleVerticalModel> simpleVerticalModelList, Context context) {
        this.simpleVerticalModelList = simpleVerticalModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.simple_vertical_slider,viewGroup,false);
        return new PlateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlateViewHolder holder, int position) {

        SimpleVerticalModel simpleVerticalModel = simpleVerticalModelList.get(position);

        Glide.with(context).load(simpleVerticalModel.getShop_image()).into(holder.proImg);
        holder.pro_title.setText(simpleVerticalModel.getShop_name());
        holder.pro_desc.setText(simpleVerticalModel.getLandmark()+","+simpleVerticalModel.getCity()+","+simpleVerticalModel.getState());
        holder.pro_coupon.setText(simpleVerticalModel.getCoupon());
        holder.pro_status.setText(simpleVerticalModel.getDescription());
        holder.pro_rating.setText(simpleVerticalModel.getRating());
    }

    @Override
    public int getItemCount() {
        return simpleVerticalModelList.size();
    }

    public class PlateViewHolder extends RecyclerView.ViewHolder {

        private ImageView proImg;
        private TextView pro_title, pro_desc, pro_coupon, pro_status, pro_rating;

        public PlateViewHolder(@NonNull View itemView) {
            super(itemView);

            proImg = (ImageView) itemView.findViewById(R.id.imageView5);
            pro_title = (TextView) itemView.findViewById(R.id.textView3);
            pro_desc = (TextView) itemView.findViewById(R.id.textView4);
            pro_coupon = (TextView) itemView.findViewById(R.id.textView6);
            pro_status = (TextView) itemView.findViewById(R.id.textView7);
            pro_rating = (TextView) itemView.findViewById(R.id.textView8);
        }
    }
}
