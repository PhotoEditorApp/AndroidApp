package com.netcracker_study_autumn_2020.presentation.ui.viewmodel;

import android.graphics.Bitmap;

import androidx.lifecycle.ViewModel;

public class PhotoViewModel extends ViewModel {
    private Bitmap imageToSave = null;

    public Bitmap getImageToSave() {
        return imageToSave;
    }

    public void setImageToSave(Bitmap imageToSave) {
        this.imageToSave = imageToSave;
    }
}
