package com.netcracker_study_autumn_2020.presentation.ui.viewholder;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.model.UserModel;
import com.squareup.picasso.Picasso;

public class FindUserPreviewViewHolder extends RecyclerView.ViewHolder {

    private long assignedUserId;
    private ImageView userAvatar;
    private TextView userName;
    private TextView userEmail;
    private TextView userId;
    private ConstraintLayout container;

    private boolean isChosen;

    public FindUserPreviewViewHolder(@NonNull View itemView) {
        super(itemView);
        userAvatar = itemView.findViewById(R.id.find_preview_user_avatar);
        userEmail = itemView.findViewById(R.id.find_preview_user_email);
        userId = itemView.findViewById(R.id.find_preview_user_id);
        userName = itemView.findViewById(R.id.find_preview_user_name);
        container = itemView.findViewById(R.id.find_preview_container);
    }

    public void onBind(UserModel userModel) {
        userId.setText(String.valueOf(userModel.getUser_id()));
        String name = userModel.getLastName().concat(" ").concat(userModel.getFirstName());
        userName.setText(name);
        userEmail.setText(userModel.getEmail());
        Picasso.get()
                .load("https://cdn.onlinewebfonts.com/svg/img_458488.png")
                .into(userAvatar);
    }

    public void onTap() {
        if (isChosen) {
            isChosen = false;
            container.setBackgroundColor(Color.rgb(255, 255, 255));
        } else {
            isChosen = true;
            assignedUserId = Long.parseLong(userId.getText().toString());
            Log.d("FIND_USER_VH", "user_id:" + assignedUserId);
            container.setBackgroundColor(Color.rgb(203, 227, 239));
        }
    }

    public long getAssignedUserId() {
        return assignedUserId;
    }
}
