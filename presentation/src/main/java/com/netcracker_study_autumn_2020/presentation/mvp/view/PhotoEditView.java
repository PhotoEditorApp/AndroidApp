package com.netcracker_study_autumn_2020.presentation.mvp.view;

import android.graphics.Bitmap;

public interface PhotoEditView {
    void updateEditableImage(Bitmap image);

    void showLoading();

    void hideLoading();

    void deleteBufferFile();

    void renderFrames();

    void showToast(String s, boolean b);
}
