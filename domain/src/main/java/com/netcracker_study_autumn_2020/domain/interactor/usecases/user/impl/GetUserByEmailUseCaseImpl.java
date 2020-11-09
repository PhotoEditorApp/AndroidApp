package com.netcracker_study_autumn_2020.domain.interactor.usecases.user.impl;

import com.netcracker_study_autumn_2020.domain.dto.UserDto;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.EditUserUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.GetUserByEmailUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.UserUseCase;
import com.netcracker_study_autumn_2020.domain.repository.UserRepository;

public class GetUserByEmailUseCaseImpl extends UserUseCase
        implements GetUserByEmailUseCase {
    private GetUserByEmailUseCase.Callback callback;
    private String email;

    public GetUserByEmailUseCaseImpl(UserRepository userRepository,
                               PostExecutionThread postExecutionThread,
                               ThreadExecutor threadExecutor) {
        super(userRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void run() {
        this.userRepository.getUserByEmail(email, userCallback);
    }

    @Override
    public void execute(String email, GetUserByEmailUseCase.Callback callback) {
        if(callback == null){
            throw new IllegalArgumentException("GetUserByEmailUseCase: Invalid callback!");
        }
        super.execute();
        this.email = email;
        this.callback = callback;

    }

    private final UserRepository.UserByEmailCallback userCallback =
            new UserRepository.UserByEmailCallback() {
                @Override
                public void onUserLoaded(UserDto userDto) {
                    notifyGetUserByEmailSuccess(userDto);
                }

                @Override
                public void onError(Exception e) {
                    notifyError(e);
                }
            };

    private void notifyGetUserByEmailSuccess(UserDto user){
        this.postExecutionThread.post(() -> callback.onUserFound(user));
    }

    private  void notifyError(Exception e){
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
