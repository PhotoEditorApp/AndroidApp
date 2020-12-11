package com.netcracker_study_autumn_2020.presentation.mvp.presenter;

import android.graphics.Bitmap;

import com.netcracker_study_autumn_2020.data.manager.SessionManager;
import com.netcracker_study_autumn_2020.domain.dto.FrameDto;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.AddFrameUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.AddImageUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.ApplyFilterUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.ApplyFrameUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.GetUsersFramesUseCase;
import com.netcracker_study_autumn_2020.presentation.mapper.FrameModelDtoMapper;
import com.netcracker_study_autumn_2020.presentation.mvp.model.FrameModel;
import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;
import com.netcracker_study_autumn_2020.presentation.mvp.view.PhotoEditView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PhotoEditPresenter extends BasePresenter {

    private PhotoEditView photoEditView;


    private final AddImageUseCase addImageUseCase;
    private ApplyFrameUseCase applyFrameUseCase;
    private ApplyFilterUseCase applyFilterUseCase;
    private GetUsersFramesUseCase getUsersFramesUseCase;
    private AddFrameUseCase addFrameUseCase;

    private List<FrameModel> usersFrameModels;
    private ImageModel imageModel;

    private FrameModelDtoMapper frameModelDtoMapper;

    public PhotoEditPresenter(ApplyFrameUseCase applyFrameUseCase,
                              ApplyFilterUseCase applyFilterUseCase,
                              GetUsersFramesUseCase getUsersFramesUseCase,
                              AddFrameUseCase addFrameUseCase,
                              AddImageUseCase addImageUseCase,
                              ImageModel imageModel) {
        this.applyFrameUseCase = applyFrameUseCase;
        this.applyFilterUseCase = applyFilterUseCase;
        this.getUsersFramesUseCase = getUsersFramesUseCase;
        this.addFrameUseCase = addFrameUseCase;
        this.addImageUseCase = addImageUseCase;
        this.imageModel = imageModel;

        frameModelDtoMapper = new FrameModelDtoMapper();
        usersFrameModels = new ArrayList<>();
    }

    public void addImage(long spaceId, File sourceImage) {
        addImageUseCase.execute(spaceId, SessionManager.getCurrentUserId(),
                sourceImage, new AddImageUseCase.Callback() {
                    @Override
                    public void onImageAdded() {

                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    public void applyFilter(String filterName) {
        photoEditView.showLoading();
        applyFilterUseCase.execute(imageModel.getId(), filterName,
                new ApplyFilterUseCase.Callback() {
                    @Override
                    public void onFilterApplied(Object image) {
                        photoEditView.updateEditableImage((Bitmap) image);
                        photoEditView.hideLoading();
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    public void applyFrame(long frameId) {
        applyFrameUseCase.execute(imageModel.getId(),
                frameId, new ApplyFrameUseCase.Callback() {
                    @Override
                    public void onFrameApplied(Object image) {
                        photoEditView.updateEditableImage((Bitmap) image);
                        photoEditView.hideLoading();
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    public void getUsersFrames() {
        getUsersFramesUseCase.execute(new GetUsersFramesUseCase.Callback() {
            @Override
            public void onUsersFramesLoaded(List<FrameDto> usersFrames) {
                usersFrameModels = frameModelDtoMapper.map1(usersFrames);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void setPhotoEditView(PhotoEditView photoEditView) {
        this.photoEditView = photoEditView;
    }
}
