package com.netcracker_study_autumn_2020.presentation.mvp.presenter;

import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.AddImageUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.GetWorkspaceImagesInfoUseCase;
import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;
import com.netcracker_study_autumn_2020.presentation.mvp.view.ImagesView;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.ImagesFragment;

import java.util.List;

public class ImagesPresenter extends BasePresenter {
    private ImagesView imagesView;

    private GetWorkspaceImagesInfoUseCase getWorkspaceImagesInfoUseCase;
    private AddImageUseCase addImageUseCase;

    private List<ImageModel> imageModels;

    public void setView(ImagesView imagesView) {
        this.imagesView = imagesView;
    }
}
