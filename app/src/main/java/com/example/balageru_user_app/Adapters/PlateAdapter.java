package com.example.balageru_user_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.balageru_user_app.Models.BannerModel;
import com.example.balageru_user_app.R;

import java.util.List;

public class PlateAdapter extends RecyclerView.Adapter<PlateAdapter.PlateViewHolder> {

    private List<BannerModel> bannerModelList;
    private Context context;

    public PlateAdapter(List<BannerModel> bannerModelList, Context context) {
        this.bannerModelList = bannerModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_plates,viewGroup,false);
        return new PlateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlateViewHolder holder, int position) {

        BannerModel bannerModel = bannerModelList.get(position);

        Glide.with(context).load(bannerModel.getBanner_image()).placeholder(R.drawable.small_placeholder).into(holder.plateImg);
    }

    @Override
    public int getItemCount() {
        return bannerModelList.size();
    }

    public class PlateViewHolder extends RecyclerView.ViewHolder {

        private ImageView plateImg;

        public PlateViewHolder(@NonNull View itemView) {
            super(itemView);

            plateImg = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
