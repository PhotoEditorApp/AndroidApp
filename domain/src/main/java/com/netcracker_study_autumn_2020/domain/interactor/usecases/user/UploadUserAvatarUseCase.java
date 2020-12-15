package com.netcracker_study_autumn_2020.domain.interactor.usecases.user;

import java.io.File;

public interface UploadUserAvatarUseCase {
    interface Callback {
        void onUserAvatarUploaded();

        void onError(Exception e);
    }

    void execute(File userAvatar, UploadUserAvatarUseCase.Callback callback);
}
