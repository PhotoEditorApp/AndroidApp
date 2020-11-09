package com.netcracker_study_autumn_2020.presentation.ui.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;

public class ImageItemViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView imageName;

    public ImageItemViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void onBind(ImageModel imageModel){

    }
}
