package com.netcracker_study_autumn_2020.presentation.ui.viewholder;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.netcracker_study_autumn_2020.data.manager.SessionManager;
import com.netcracker_study_autumn_2020.library.network.NetworkUtils;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;
import com.netcracker_study_autumn_2020.presentation.mvp.view.ImagesView;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.ImagesFragment;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ImageItemViewHolder extends RecyclerView.ViewHolder {

    //View, which represents a fragment
    private ImagesView imagesView;

    private LinearLayout averageColorIndicator;
    private ConstraintLayout previewContainer;
    private ImageView imageView;
    private TextView imageName;
    private TextView rating;

    private boolean isChosen = false;

    public ImageItemViewHolder(@NonNull View itemView,
                               ImagesView imagesView) {
        super(itemView);
        averageColorIndicator = itemView.findViewById(R.id.average_color_indicator);
        imageName = itemView.findViewById(R.id.item_image_name);
        imageView = itemView.findViewById(R.id.item_image);
        previewContainer = itemView.findViewById(R.id.image_preview_container);
        rating = itemView.findViewById(R.id.item_image_rating);


        this.imagesView = imagesView;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void onBind(ImageModel imageModel, Fragment fragment) {
        //We'll show popup menu on a long click at ViewHolder
        // with image preview
        previewContainer.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (!imagesView.isChoosingImagesForCollage()) {
                    v.setBackgroundColor(Color.rgb(255, 255, 255));
                }
                return false;
            } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (!imagesView.isChoosingImagesForCollage()) {
                    v.setBackgroundColor(Color.rgb(177, 247, 246));
                }
                return false;
            }
            return false;
        });
        previewContainer.setOnClickListener(l -> {
            if (!imagesView.isChoosingImagesForCollage()) {
                ((ImagesFragment) fragment).navigateToPhotoView(imageModel);
            } else {
                if (isChosen) {
                    previewContainer
                            .setBackgroundColor(Color.rgb(255, 255, 255));
                    imagesView.removeChosenImage(imageModel.getId());
                    isChosen = false;
                } else {
                    previewContainer
                            .setBackgroundColor(Color.rgb(177, 247, 246));
                    imagesView.chooseImage(imageModel.getId());
                    isChosen = true;
                }
            }
        });
        previewContainer.setOnLongClickListener(v -> {
            imagesView.showImageMenu(imageModel, itemView);
            return true;
        });

        Log.d("IMAGE_COLOR", String.valueOf(imageModel.getAverageColor()));
        averageColorIndicator.getBackground().setColorFilter(imageModel.getAverageColor(),
                PorterDuff.Mode.SRC_IN);

        //TODO do something with that!
        final String previewPath = imageModel.getPreviewPath().replace("\\", "%2F");
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request customImageRequest = chain.request().newBuilder()
                            .addHeader("Authorization", SessionManager.getSessionToken())
                            .build();

                    return chain.proceed(customImageRequest);
                })
                .build();
        Picasso picasso = new Picasso.Builder(fragment.requireContext())
                .downloader(new OkHttp3Downloader(client))
                .build();

        String url = NetworkUtils.API_ADDRESS + NetworkUtils.GET_IMAGE_BY_PATH +
                "?path=" + previewPath;
        Log.d("ONBIND", url);
        picasso.load(url)
                .error(R.drawable.loading_error)
                .placeholder(R.drawable.loading_animation)
                .into(imageView);
        imageName.setText(imageModel.getName());
        rating.setText(String.valueOf(imageModel.getRating()));
    }
}
