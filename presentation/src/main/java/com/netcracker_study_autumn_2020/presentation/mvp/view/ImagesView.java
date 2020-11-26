package com.netcracker_study_autumn_2020.presentation.mvp.view;

import android.view.View;

import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;

public interface ImagesView {
    void renderImages();

    void showImageMenu(ImageModel imageModel,
                       View targetView);
}
