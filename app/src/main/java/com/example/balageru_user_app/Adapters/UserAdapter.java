package com.example.balageru_user_app.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balageru_user_app.Models.UserModel;
import com.example.balageru_user_app.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.PlateViewHolder>{

    private List<UserModel> userModelList;
    private Context context;

    public UserAdapter(List<UserModel> userModelList, Context context) {
        this.userModelList = userModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserAdapter.PlateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.PlateViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public class PlateViewHolder extends RecyclerView.ViewHolder {
        private TextView user_id, user_name, user_email, user_phone, date_created;
        public PlateViewHolder(@NonNull View itemView) {
            super(itemView);

            user_name = (TextView) itemView.findViewById(R.id.txtName);
            user_email = (TextView) itemView.findViewById(R.id.txtEmail);
            user_phone = (TextView) itemView.findViewById(R.id.txtPhone);
            date_created = (TextView) itemView.findViewById(R.id.txtDate);
        }
    }
}
