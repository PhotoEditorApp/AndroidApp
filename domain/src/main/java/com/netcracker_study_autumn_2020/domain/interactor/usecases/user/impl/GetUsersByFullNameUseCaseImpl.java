package com.netcracker_study_autumn_2020.domain.interactor.usecases.user.impl;

import com.netcracker_study_autumn_2020.domain.dto.UserDto;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.GetUserByIdUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.GetUsersByFullNameUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.UserUseCase;
import com.netcracker_study_autumn_2020.domain.repository.UserRepository;

import java.util.List;

public class GetUsersByFullNameUseCaseImpl extends UserUseCase
        implements GetUsersByFullNameUseCase {
    private GetUsersByFullNameUseCase.Callback callback;
    private String fullName;

    public GetUsersByFullNameUseCaseImpl(UserRepository userRepository,
                                  PostExecutionThread postExecutionThread,
                                  ThreadExecutor threadExecutor) {
        super(userRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void run() {
        this.userRepository.getUsersByFullName(fullName, userCallback);
    }

    @Override
    public void execute(String fullName, GetUsersByFullNameUseCase.Callback callback) {
        if(callback == null){
            throw new IllegalArgumentException("GetUserByIdUseCase: Invalid callback!");
        }
        super.execute();
        this.fullName = fullName;
        this.callback = callback;

    }

    private final UserRepository.UsersByFullNameListCallback userCallback =
            new UserRepository.UsersByFullNameListCallback() {
                @Override
                public void onUsersByEmailListLoaded(List<UserDto> users) {
                    notifyGetUsersByFullNameSuccess(users);
                }

                @Override
                public void onError(Exception e) {
                    notifyError(e);
                }
            };

    private void notifyGetUsersByFullNameSuccess(List<UserDto> users){
        this.postExecutionThread.post(() -> callback.onUsersFound(users));
    }

    private  void notifyError(Exception e){
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
