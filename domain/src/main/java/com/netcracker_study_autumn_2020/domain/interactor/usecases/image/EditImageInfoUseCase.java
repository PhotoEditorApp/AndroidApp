package com.netcracker_study_autumn_2020.domain.interactor.usecases.image;

import com.netcracker_study_autumn_2020.domain.dto.ImageDto;

public interface EditImageInfoUseCase {
    interface Callback {
        void onImageInfoEdited();

        void onError(Exception e);
    }

    void execute(ImageDto imageDto, EditImageInfoUseCase.Callback callback);
}
