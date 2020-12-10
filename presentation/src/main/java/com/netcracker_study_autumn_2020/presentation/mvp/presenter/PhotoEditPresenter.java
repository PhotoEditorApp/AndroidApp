package com.netcracker_study_autumn_2020.presentation.mvp.presenter;

import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.AddFrameUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.ApplyFilterUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.ApplyFrameUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.GetUsersFramesUseCase;

public class PhotoEditPresenter extends BasePresenter {
    private ApplyFrameUseCase applyFrameUseCase;
    private ApplyFilterUseCase applyFilterUseCase;
    private GetUsersFramesUseCase getUsersFramesUseCase;
    private AddFrameUseCase addFrameUseCase;

    public PhotoEditPresenter(ApplyFrameUseCase applyFrameUseCase,
                              ApplyFilterUseCase applyFilterUseCase,
                              GetUsersFramesUseCase getUsersFramesUseCase,
                              AddFrameUseCase addFrameUseCase) {
        this.applyFrameUseCase = applyFrameUseCase;
        this.applyFilterUseCase = applyFilterUseCase;
        this.getUsersFramesUseCase = getUsersFramesUseCase;
        this.addFrameUseCase = addFrameUseCase;
    }
}
