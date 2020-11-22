package com.netcracker_study_autumn_2020.presentation.mvp.presenter;

import android.util.Log;

import com.netcracker_study_autumn_2020.domain.dto.ImageDto;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.GetWorkspaceImagesInfoUseCase;
import com.netcracker_study_autumn_2020.presentation.mapper.ImageModelDtoMapper;
import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;
import com.netcracker_study_autumn_2020.presentation.mvp.view.ImagesView;

import java.util.List;

import static android.content.ContentValues.TAG;

public class ImagesPresenter extends BasePresenter {
    private ImagesView imagesView;

    private GetWorkspaceImagesInfoUseCase getWorkspaceImagesInfoUseCase;

    private List<ImageModel> imageModels;
    private long spaceId;

    private ImageModelDtoMapper imageModelDtoMapper;

    public ImagesPresenter(long spaceId,
                           GetWorkspaceImagesInfoUseCase getWorkspaceImagesInfoUseCase) {
        this.getWorkspaceImagesInfoUseCase = getWorkspaceImagesInfoUseCase;
        this.spaceId = spaceId;

        imageModelDtoMapper = new ImageModelDtoMapper();
    }

    public void setView(ImagesView imagesView) {
        this.imagesView = imagesView;
    }

    private void getWorkspaceImagesInfo(long spaceId) {
        getWorkspaceImagesInfoUseCase.execute(spaceId, new GetWorkspaceImagesInfoUseCase.Callback() {
            @Override
            public void onImagesLoaded(List<ImageDto> images) {
                List<ImageModel> wModels = imageModelDtoMapper.map1(images);
                imageModels.clear();
                imageModels.addAll(wModels);
                //workspaceModels = wModels;
                Log.d(TAG, "onImagesLoaded: ");

                imagesView.renderImages();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void updateImageList() {
        getWorkspaceImagesInfo(spaceId);
    }

    public List<ImageModel> getImageModels() {
        return imageModels;
    }
}
