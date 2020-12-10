package com.netcracker_study_autumn_2020.presentation.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.PhotoEditPresenter;
import com.netcracker_study_autumn_2020.presentation.mvp.view.PhotoEditView;

public class PhotoEditorFragment extends BaseFragment implements PhotoEditView {

    private PhotoEditPresenter photoEditPresenter;

    private ImageModel imageModel;
    private Bitmap sourceImage;
    private Bitmap editedImage;

    public PhotoEditorFragment(ImageModel imageModel, Bitmap bitmap) {
        this.imageModel = imageModel;
        this.sourceImage = bitmap;
    }

    @Override
    void initializePresenter() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_photo, container, false);
        initInteractions(root);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initInteractions(View root) {
        SubsamplingScaleImageView subsamplingScaleImageView = root.findViewById(R.id.edit_preview);
        subsamplingScaleImageView.setImage(ImageSource.bitmap(sourceImage));
    }
}
