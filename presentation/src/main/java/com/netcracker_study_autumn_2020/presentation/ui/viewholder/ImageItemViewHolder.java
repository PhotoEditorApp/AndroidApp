package com.netcracker_study_autumn_2020.presentation.ui.viewholder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.netcracker_study_autumn_2020.data.manager.SessionManager;
import com.netcracker_study_autumn_2020.library.network.NetworkUtils;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;
import com.netcracker_study_autumn_2020.presentation.mvp.view.ImagesView;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ImageItemViewHolder extends RecyclerView.ViewHolder {

    //View, which represents a fragment
    private ImagesView imagesView;

    private ConstraintLayout previewContainer;
    private ImageView imageView;
    private TextView imageName;

    public ImageItemViewHolder(@NonNull View itemView,
                               ImagesView imagesView) {
        super(itemView);
        imageName = itemView.findViewById(R.id.item_image_name);
        imageView = itemView.findViewById(R.id.item_image);
        previewContainer = itemView.findViewById(R.id.image_preview_container);

        this.imagesView = imagesView;
    }

    public void onBind(ImageModel imageModel, Context context) {
        //We'll show popup menu on a long click at ViewHolder
        // with image preview
        previewContainer.setOnLongClickListener(v -> {
            imagesView.showImageMenu(imageModel, itemView);
            return false;
        });


        final long imageId = imageModel.getId();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request customImageRequest = chain.request().newBuilder()
                            .addHeader("Authorization", SessionManager.getSessionToken())
                            .build();

                    return chain.proceed(customImageRequest);
                })
                .build();
        Picasso picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(client))
                .build();
        String url = NetworkUtils.API_ADDRESS + NetworkUtils.GET_IMAGE_BY_ID_ADDRESS +
                "?id=" + imageId;
        Log.d("ONBIND", url);
        picasso.load(url)
                .error(R.drawable.loading_error)
                .placeholder(R.drawable.loading_animation)
                .resize(200, 200)
                .centerCrop()
                .into(imageView);
        imageName.setText(imageModel.getName());
    }
}
