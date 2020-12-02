package com.netcracker_study_autumn_2020.presentation.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.material.button.MaterialButton;
import com.netcracker_study_autumn_2020.data.custom.image.ImageEntityStoreFactory;
import com.netcracker_study_autumn_2020.data.executor.JobExecutor;
import com.netcracker_study_autumn_2020.data.mapper.ImageEntityDtoMapper;
import com.netcracker_study_autumn_2020.data.repository.ImageRepositoryImpl;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.DownloadImageByIdUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl.DownloadImageByIdUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.repository.ImageRepository;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.executor.UIThread;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.PhotoViewPresenter;
import com.netcracker_study_autumn_2020.presentation.mvp.view.PreviewImageView;

public class PhotoViewFragment extends BaseFragment
        implements PreviewImageView {

    private ConstraintLayout mainContainer;
    private ConstraintLayout loadingUi;

    private SubsamplingScaleImageView subsamplingScaleImageView;


    private long imageId;
    private long workspaceId;

    private PhotoViewPresenter photoViewPresenter;

    public PhotoViewFragment() {
    }

    public PhotoViewFragment(long imageId, long workspaceId) {
        this.imageId = imageId;
        this.workspaceId = workspaceId;
    }

    @Override
    void initializePresenter() {

        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        ImageEntityDtoMapper imageEntityDtoMapper = new ImageEntityDtoMapper();
        ImageEntityStoreFactory imageEntityStoreFactory = new ImageEntityStoreFactory();

        ImageRepository imageRepository = ImageRepositoryImpl.getInstance(imageEntityStoreFactory,
                imageEntityDtoMapper);
        DownloadImageByIdUseCase downloadImageByIdUseCase = new DownloadImageByIdUseCaseImpl(imageRepository,
                postExecutionThread, threadExecutor);
        photoViewPresenter = new PhotoViewPresenter(imageId, downloadImageByIdUseCase);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_photo_view, container, false);
        initInteractions(root);
        return root;
    }

    private void initInteractions(View root) {
        MaterialButton buttonSave = root.findViewById(R.id.button_save_photo);
        MaterialButton buttonBack = root.findViewById(R.id.button_back_from_photo_view);
        MaterialButton buttonEditPhoto = root.findViewById(R.id.button_edit_photo);

        subsamplingScaleImageView = root.findViewById(R.id.subsampling_scale_image_view);

        buttonSave.setOnClickListener(l -> {

        });
        buttonEditPhoto.setOnClickListener(l -> {

        });
        buttonBack.setOnClickListener(l -> {

        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainContainer = view.findViewById(R.id.main_container);
        loadingUi = view.findViewById(R.id.loading_ui);
        photoViewPresenter.setPreviewImageView(this);
        photoViewPresenter.downloadImage();
    }

    @Override
    public void showLoading() {
        mainContainer.setVisibility(View.INVISIBLE);
        loadingUi.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mainContainer.setVisibility(View.VISIBLE);
        loadingUi.setVisibility(View.GONE);
    }

    @Override
    public void renderImage() {
        Bitmap buf = photoViewPresenter.getDownloadedImage();
        subsamplingScaleImageView.setImage(ImageSource.bitmap(buf));
    }
}
