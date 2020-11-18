package com.netcracker_study_autumn_2020.domain.interactor.usecases.space.access.impl;

import com.netcracker_study_autumn_2020.domain.dto.SpaceAccessDto;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.space.access.CreateSpaceAccessUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.space.access.SpaceAccessUseCase;
import com.netcracker_study_autumn_2020.domain.repository.SpaceAccessRepository;

public class CreateSpaceAccessUseCaseImpl extends SpaceAccessUseCase
        implements CreateSpaceAccessUseCase {
    private CreateSpaceAccessUseCase.Callback callback;
    private SpaceAccessDto spaceAccessDto;

    public CreateSpaceAccessUseCaseImpl(SpaceAccessRepository spaceAccessRepository,
                                        PostExecutionThread postExecutionThread,
                                        ThreadExecutor threadExecutor) {
        super(spaceAccessRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void run() {
        this.spaceAccessRepository.createSpaceAccess(spaceAccessDto, spaceAccessCallback);
    }

    @Override
    public void execute(SpaceAccessDto spaceAccessDto,
                        CreateSpaceAccessUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("CreateSpaceAccessUseCase: Invalid callback!");
        }
        super.execute();
        this.spaceAccessDto = spaceAccessDto;
        this.callback = callback;

    }

    private final SpaceAccessRepository.SpaceAccessCreateCallback spaceAccessCallback =
            new SpaceAccessRepository.SpaceAccessCreateCallback() {
                @Override
                public void onSpaceAccessCreated() {
                    notifySpaceAccessCreateSuccess();
                }

                @Override
                public void onError(Exception e) {
                    notifyError(e);
                }
            };

    private void notifySpaceAccessCreateSuccess() {
        this.postExecutionThread.post(() -> callback.onSpaceAccessCreated());
    }

    private void notifyError(Exception e) {
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
