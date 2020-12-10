package com.netcracker_study_autumn_2020.domain.interactor.usecases.image;

import java.io.File;

public interface AddFrameUseCase {
    interface Callback {
        void onFrameAdded();

        void onError(Exception e);
    }

    void execute(File sourceFile, AddFrameUseCase.Callback callback);
}
