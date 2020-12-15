package com.netcracker_study_autumn_2020.domain.interactor.usecases.user.impl;

import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.UploadUserAvatarUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.UserUseCase;
import com.netcracker_study_autumn_2020.domain.repository.UserRepository;

import java.io.File;

public class UploadUserAvatarUseCaseImpl extends UserUseCase
        implements UploadUserAvatarUseCase {
    private UploadUserAvatarUseCase.Callback callback;
    private File sourceFile;

    public UploadUserAvatarUseCaseImpl(UserRepository userRepository,
                                       PostExecutionThread postExecutionThread,
                                       ThreadExecutor threadExecutor) {
        super(userRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void run() {
        this.userRepository.uploadAvatar(sourceFile, repositoryCallback);
    }

    @Override
    public void execute(File sourceImage, UploadUserAvatarUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("UploadUserAvatarUseCase: Invalid callback!");
        }
        super.execute();
        this.sourceFile = sourceImage;
        this.callback = callback;
    }

    private final UserRepository.UserAvatarUploadCallback repositoryCallback = new UserRepository.UserAvatarUploadCallback() {
        @Override
        public void onUserAvatarUploaded() {
            notifyUploadAvatarSuccess();
        }

        @Override
        public void onError(Exception e) {
            notifyError(e);
        }
    };


    private void notifyUploadAvatarSuccess() {
        this.postExecutionThread.post(() -> callback.onUserAvatarUploaded());
    }

    private void notifyError(Exception e) {
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
