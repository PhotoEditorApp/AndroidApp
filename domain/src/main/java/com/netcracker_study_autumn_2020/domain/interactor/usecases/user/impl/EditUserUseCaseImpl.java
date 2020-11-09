package com.netcracker_study_autumn_2020.domain.interactor.usecases.user.impl;

import com.netcracker_study_autumn_2020.domain.dto.UserDto;
import com.netcracker_study_autumn_2020.domain.dto.WorkspaceDto;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.EditUserUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.UserUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.CreateWorkspaceUseCase;
import com.netcracker_study_autumn_2020.domain.repository.UserRepository;
import com.netcracker_study_autumn_2020.domain.repository.WorkspaceRepository;

public class EditUserUseCaseImpl extends UserUseCase
        implements EditUserUseCase {
    private EditUserUseCase.Callback callback;
    private UserDto userDto;

    public EditUserUseCaseImpl(UserRepository userRepository,
                               PostExecutionThread postExecutionThread,
                               ThreadExecutor threadExecutor) {
        super(userRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void run() {
        this.userRepository.editUser(userDto, userCallback);
    }

    @Override
    public void execute(UserDto userDto, EditUserUseCase.Callback callback) {
        if(callback == null){
            throw new IllegalArgumentException("EditUserUseCase: Invalid callback!");
        }
        super.execute();
        this.userDto = userDto;
        this.callback = callback;

    }

    private final UserRepository.UserEditCallback userCallback =
            new UserRepository.UserEditCallback() {
                @Override
                public void onUserEdited() {
                    notifyEditUserSuccess();
                }

                @Override
                public void onError(Exception e) {
                    notifyError(e);
                }
            };

    private void notifyEditUserSuccess(){
        this.postExecutionThread.post(() -> callback.onUserEdited());
    }

    private  void notifyError(Exception e){
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
