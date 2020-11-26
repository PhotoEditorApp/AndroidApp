package com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl;

import com.netcracker_study_autumn_2020.domain.dto.ImageDto;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.EditImageInfoUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.ImageUseCase;
import com.netcracker_study_autumn_2020.domain.repository.ImageRepository;

public class EditImageInfoUseCaseImpl extends ImageUseCase
        implements EditImageInfoUseCase {
    private EditImageInfoUseCase.Callback callback;
    private ImageDto imageDto;

    public EditImageInfoUseCaseImpl(ImageRepository imageRepository,
                                    PostExecutionThread postExecutionThread,
                                    ThreadExecutor threadExecutor) {
        super(imageRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void run() {
        this.imageRepository.editImageInfo(imageDto, repositoryCallback);
    }

    @Override
    public void execute(ImageDto imageDto, EditImageInfoUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("DeleteImageUseCase: Invalid callback!");
        }
        super.execute();
        this.imageDto = imageDto;
        this.callback = callback;
    }

    private final ImageRepository.ImageInfoEditCallback repositoryCallback =
            new ImageRepository.ImageInfoEditCallback() {
                @Override
                public void onImageInfoEdited() {
                    notifyEditImageInfoSuccess();
                }

                @Override
                public void onError(Exception e) {
                    notifyError(e);
                }
            };


    private void notifyEditImageInfoSuccess() {
        this.postExecutionThread.post(() -> callback.onImageInfoEdited());
    }

    private void notifyError(Exception e) {
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}

