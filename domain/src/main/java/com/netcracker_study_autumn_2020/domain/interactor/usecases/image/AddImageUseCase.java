package com.netcracker_study_autumn_2020.domain.interactor.usecases.image;

import java.io.File;

public interface AddImageUseCase {
    interface Callback {
        void onImageAdded();

        void onError(Exception e);
    }

    void execute(long spaceId, long userId, //Bitmap sourceImage,
                 File bufferFile, AddImageUseCase.Callback callback);

}
