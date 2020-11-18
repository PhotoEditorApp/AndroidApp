package com.netcracker_study_autumn_2020.domain.interactor.usecases.space.access.impl;

import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.space.access.DeleteSpaceAccessUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.space.access.SpaceAccessUseCase;
import com.netcracker_study_autumn_2020.domain.repository.SpaceAccessRepository;

public class DeleteSpaceAccessUseCaseImpl extends SpaceAccessUseCase
        implements DeleteSpaceAccessUseCase {
    private DeleteSpaceAccessUseCase.Callback callback;
    private long spaceAccessId;

    public DeleteSpaceAccessUseCaseImpl(SpaceAccessRepository spaceAccessRepository,
                                        PostExecutionThread postExecutionThread,
                                        ThreadExecutor threadExecutor) {
        super(spaceAccessRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void run() {
        this.spaceAccessRepository.deleteSpaceAccess(spaceAccessId, spaceAccessCallback);
    }

    @Override
    public void execute(long spaceAccessId,
                        DeleteSpaceAccessUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("DeleteSpaceAccessUseCase: Invalid callback!");
        }
        super.execute();
        this.spaceAccessId = spaceAccessId;
        this.callback = callback;

    }

    private final SpaceAccessRepository.SpaceAccessDeleteCallback spaceAccessCallback =
            new SpaceAccessRepository.SpaceAccessDeleteCallback() {
                @Override
                public void onSpaceAccessDeleted() {
                    notifySpaceAccessDeleteSuccess();
                }

                @Override
                public void onError(Exception e) {
                    notifyError(e);
                }
            };

    private void notifySpaceAccessDeleteSuccess() {
        this.postExecutionThread.post(() -> callback.onSpaceAccessDeleted());
    }

    private void notifyError(Exception e) {
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
