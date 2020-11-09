package com.netcracker_study_autumn_2020.domain.interactor.usecases.user.impl;

import com.netcracker_study_autumn_2020.domain.dto.UserDto;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.EditUserUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.GetUserByIdUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.UserUseCase;
import com.netcracker_study_autumn_2020.domain.repository.UserRepository;

public class GetUserByIdUseCaseImpl extends UserUseCase
        implements GetUserByIdUseCase {
    private GetUserByIdUseCase.Callback callback;
    private long userId;

    public GetUserByIdUseCaseImpl(UserRepository userRepository,
                               PostExecutionThread postExecutionThread,
                               ThreadExecutor threadExecutor) {
        super(userRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void run() {
        this.userRepository.getUserById(userId, userCallback);
    }

    @Override
    public void execute(long userId, GetUserByIdUseCase.Callback callback) {
        if(callback == null){
            throw new IllegalArgumentException("GetUserByIdUseCase: Invalid callback!");
        }
        super.execute();
        this.userId = userId;
        this.callback = callback;

    }

    private final UserRepository.UserByIdCallback userCallback =
            new UserRepository.UserByIdCallback() {
                @Override
                public void onUserLoaded(UserDto user) {
                    notifyGetUserByIdSuccess(user);
                }

                @Override
                public void onError(Exception e) {
                    notifyError(e);
                }
            };

    private void notifyGetUserByIdSuccess(UserDto user){
        this.postExecutionThread.post(() -> callback.onUserFound(user));
    }

    private  void notifyError(Exception e){
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
