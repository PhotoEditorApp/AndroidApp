package com.netcracker_study_autumn_2020.presentation.mvp.view;

public interface UserProfileView {
    void renderData();

    void showLoading();

    void hideLoading();

    void renderAvatar();
}
