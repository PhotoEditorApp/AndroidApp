package com.netcracker_study_autumn_2020.presentation.mvp.presenter;


import android.util.Log;

import com.netcracker_study_autumn_2020.data.manager.AuthManager;
import com.netcracker_study_autumn_2020.data.manager.SessionManager;
import com.netcracker_study_autumn_2020.domain.dto.WorkspaceDto;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.CreateWorkspaceUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.DeleteWorkspaceUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.EditWorkspaceUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.GetWorkspacesUseCase;
import com.netcracker_study_autumn_2020.presentation.mapper.WorkspaceModelDtoMapper;
import com.netcracker_study_autumn_2020.presentation.mvp.model.WorkspaceModel;
import com.netcracker_study_autumn_2020.presentation.mvp.view.CreateWorkspaceView;
import com.netcracker_study_autumn_2020.presentation.mvp.view.WorkspacesView;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.CreateWorkspaceFragment;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class WorkspacesPresenter extends BasePresenter {

    private AuthManager authManager;

    private WorkspacesView workspacesView;
    private CreateWorkspaceView createWorkspaceView;

    private List<WorkspaceModel> workspaceModels;

    private GetWorkspacesUseCase getWorkspacesUseCase;
    private DeleteWorkspaceUseCase deleteWorkspaceUseCase;
    private CreateWorkspaceUseCase createWorkspaceUseCase;
    private EditWorkspaceUseCase editWorkspaceUseCase;

    private WorkspaceModelDtoMapper workspaceModelDtoMapper;

    public WorkspacesPresenter(AuthManager authManager, GetWorkspacesUseCase getWorkspacesUseCase,
                               DeleteWorkspaceUseCase deleteWorkspaceUseCase,
                               CreateWorkspaceUseCase createWorkspaceUseCase,
                               EditWorkspaceUseCase editWorkspaceUseCase){
        this.getWorkspacesUseCase = getWorkspacesUseCase;
        this.deleteWorkspaceUseCase = deleteWorkspaceUseCase;
        this.createWorkspaceUseCase = createWorkspaceUseCase;
        this.editWorkspaceUseCase = editWorkspaceUseCase;
        this.authManager = authManager;

        workspaceModelDtoMapper = new WorkspaceModelDtoMapper();

        workspaceModels = new ArrayList<>();
    }

    public void setWorkspacesView(WorkspacesView workspacesView) {
        this.workspacesView = workspacesView;
    }

    public void setCreationView(CreateWorkspaceFragment createWorkspaceFragment) {
        this.createWorkspaceView = createWorkspaceFragment;
    }

    public void refreshData() {
        getWorkspacesUseCase.execute(SessionManager.getCurrentUserId(), new GetWorkspacesUseCase.Callback() {
            @Override
            public void onWorkspaceCreated(List<WorkspaceDto> workspaces) {
                List<WorkspaceModel> wModels = workspaceModelDtoMapper.map1(workspaces);
                workspaceModels.clear();
                workspaceModels.addAll(wModels);
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

    public void navigateToPhotosScreen(WorkspaceModel data) {
        workspacesView.navigateToPhotosScreen(data);
    }

    public void navigateToShareSpace(long currentWorkspaceId) {
        workspacesView.navigateToShareWorkspaceScreen(currentWorkspaceId);
    }

    //Этот метод вызывается только в контексте CreateWorkspaceFragment
    public void createWorkspace(WorkspaceModel data) {
        WorkspaceDto workspaceDto = workspaceModelDtoMapper.map2(data);
        createWorkspaceUseCase.execute(workspaceDto, new CreateWorkspaceUseCase.Callback() {
            @Override
            public void onWorkspaceCreated() {
                ((CreateWorkspaceFragment) createWorkspaceView).showToastMessage(
                        "Пространство успешно создано", true
                );

            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void deleteWorkspace(WorkspaceModel data) {
        deleteWorkspaceUseCase.execute(data.getId(), new DeleteWorkspaceUseCase.Callback() {
            @Override
            public void onWorkspaceDeleted() {
                refreshData();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public long getCurrentUserId() {
        return SessionManager.getCurrentUserId();
    }


}
