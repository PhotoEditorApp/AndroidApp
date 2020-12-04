package com.netcracker_study_autumn_2020.presentation.mvp.view;

public interface PreviewImageView {
    void showLoading();

    void hideLoading();

    void renderImage();

    void renderTags();
}
