package com.netcracker_study_autumn_2020.presentation.ui.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;
import com.squareup.picasso.Picasso;

public class ImageItemViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView imageName;

    public ImageItemViewHolder(@NonNull View itemView) {
        super(itemView);
        imageName = itemView.findViewById(R.id.item_image_name);
        imageView = itemView.findViewById(R.id.item_image);
    }

    public void onBind(ImageModel imageModel) {
        Picasso.get()
                .load(imageModel.getPreviewPath())
                //.load(NetworkUtils.API_ADDRESS + imageModel.getPreviewPath())
                //.resize(imageView.getWidth(), imageView.getHeight())
                //.centerCrop()
                .into(imageView);
        imageName.setText(imageModel.getName());
    }
}
