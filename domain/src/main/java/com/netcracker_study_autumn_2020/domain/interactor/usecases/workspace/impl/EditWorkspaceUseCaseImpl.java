package com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.impl;

import com.netcracker_study_autumn_2020.domain.dto.WorkspaceDto;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.EditWorkspaceUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.GetWorkspacesUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.WorkspaceUseCase;
import com.netcracker_study_autumn_2020.domain.repository.WorkspaceRepository;

import java.util.List;

public class EditWorkspaceUseCaseImpl extends WorkspaceUseCase implements EditWorkspaceUseCase {
    private EditWorkspaceUseCase.Callback callback;
    private WorkspaceDto workspaceDto;

    public EditWorkspaceUseCaseImpl(WorkspaceRepository workspaceRepository,
                                    PostExecutionThread postExecutionThread,
                                    ThreadExecutor threadExecutor) {
        super(workspaceRepository, postExecutionThread, threadExecutor);
    }

    @Override
    public void run() {
        this.workspaceRepository.editWorkspace(workspaceDto, repositoryCallback);
    }

    @Override
    public void execute(WorkspaceDto workspaceDto, EditWorkspaceUseCase.Callback callback) {
        if(callback == null){
            throw new IllegalArgumentException("GetWorkspaceUseCase: Invalid callback!");
        }
        super.execute();
        this.workspaceDto = workspaceDto;
        this.callback = callback;

    }

    private final WorkspaceRepository.WorkspaceEditCallback repositoryCallback =
            new WorkspaceRepository.WorkspaceEditCallback() {
                @Override
                public void onWorkspaceEdited() {
                    notifyEditWorkspaceSuccess();
                }

                @Override
                public void onError(Exception e) {
                    notifyError(e);
                }

            };

    private void notifyEditWorkspaceSuccess(){
        this.postExecutionThread.post(() -> callback.onWorkspaceEdited());
    }

    private  void notifyError(Exception e){
        this.postExecutionThread.post(() -> callback.onError(e));
    }
}
