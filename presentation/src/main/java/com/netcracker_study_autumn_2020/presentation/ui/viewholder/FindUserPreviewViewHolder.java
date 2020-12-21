package com.netcracker_study_autumn_2020.presentation.ui.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.netcracker_study_autumn_2020.data.manager.SessionManager;
import com.netcracker_study_autumn_2020.library.network.NetworkUtils;
import com.netcracker_study_autumn_2020.library.network.UnsafeOkHttpClient;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.model.UserModel;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;

public class FindUserPreviewViewHolder extends RecyclerView.ViewHolder {

    private long assignedUserId;
    private CircleImageView userAvatar;
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

    public void onBind(UserModel userModel, Context context) {
        userId.setText(String.valueOf(userModel.getUser_id()));
        String name = userModel.getLastName().concat(" ").concat(userModel.getFirstName());
        userName.setText(name);
        userEmail.setText(userModel.getEmail());

        OkHttpClient client = UnsafeOkHttpClient.getPicassoUnsafeOkHttpClient(
                SessionManager.getSessionToken());
        Picasso picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(client))
                .build();

        String url = NetworkUtils.API_ADDRESS + NetworkUtils.GET_AVATAR_BY_ID +
                "?profileId=" + userModel.getUser_id();
        Log.d("ONBIND", url);
        picasso.load(url)
                .error(R.drawable.default_avatar)
                .placeholder(R.drawable.loading_animation)
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
