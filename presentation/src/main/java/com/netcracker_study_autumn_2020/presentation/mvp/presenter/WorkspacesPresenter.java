package com.netcracker_study_autumn_2020.presentation.mvp.presenter;


import android.util.Log;

import com.netcracker_study_autumn_2020.domain.dto.WorkspaceDto;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.CreateWorkspaceUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.DeleteWorkspaceUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.EditWorkspaceUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.GetWorkspacesUseCase;
import com.netcracker_study_autumn_2020.presentation.mapper.WorkspaceModelDtoMapper;
import com.netcracker_study_autumn_2020.presentation.mvp.model.WorkspaceModel;
import com.netcracker_study_autumn_2020.presentation.mvp.view.WorkspacesView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class WorkspacesPresenter extends BasePresenter {

    private WorkspacesView workspacesView;

    private List<WorkspaceModel> workspaceModels;

    private GetWorkspacesUseCase getWorkspacesUseCase;
    private DeleteWorkspaceUseCase deleteWorkspaceUseCase;
    private CreateWorkspaceUseCase createWorkspaceUseCase;
    private EditWorkspaceUseCase editWorkspaceUseCase;

    private WorkspaceModelDtoMapper workspaceModelDtoMapper;

    public WorkspacesPresenter(GetWorkspacesUseCase getWorkspacesUseCase,
                               DeleteWorkspaceUseCase deleteWorkspaceUseCase,
                               CreateWorkspaceUseCase createWorkspaceUseCase,
                               EditWorkspaceUseCase editWorkspaceUseCase){
        this.getWorkspacesUseCase = getWorkspacesUseCase;
        this.deleteWorkspaceUseCase = deleteWorkspaceUseCase;
        this.createWorkspaceUseCase = createWorkspaceUseCase;
        this.editWorkspaceUseCase = editWorkspaceUseCase;

        workspaceModelDtoMapper = new WorkspaceModelDtoMapper();

        workspaceModels = new ArrayList<>();

        workspaceModels.add(new WorkspaceModel(1));
        workspaceModels.add(new WorkspaceModel(2));
        workspaceModels.add(new WorkspaceModel(3));
        workspaceModels.add(new WorkspaceModel(4));
        workspaceModels.add(new WorkspaceModel(5));
        workspaceModels.add(new WorkspaceModel(6));
        workspaceModels.add(new WorkspaceModel(7));
        workspaceModels.add(new WorkspaceModel(8));
        workspaceModels.add(new WorkspaceModel(9));
        workspaceModels.add(new WorkspaceModel(10));
        workspaceModels.add(new WorkspaceModel(11));
        workspaceModels.add(new WorkspaceModel(12));
        workspaceModels.add(new WorkspaceModel(13));
        workspaceModels.add(new WorkspaceModel(14));
    }

    public void setWorkspacesView(WorkspacesView workspacesView) {
        this.workspacesView = workspacesView;
    }

    public void refreshData(int userId){
        getWorkspacesUseCase.execute(userId, new GetWorkspacesUseCase.Callback() {
            @Override
            public void onWorkspaceCreated(List<WorkspaceDto> workspaces) {
                //List<WorkspaceModel> wModels = workspaceModelDtoMapper.map1(workspaces);
                //workspaceModels.clear();
                //workspaceModels = wModels;
                Log.d(TAG, "onWorkspaceCreated: ");

                workspacesView.renderWorkspaces();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public List<WorkspaceModel> getWorkspaceModels() {

        return workspaceModels;
    }
}
