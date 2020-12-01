package com.netcracker_study_autumn_2020.presentation.mvp.presenter;


import android.util.Log;

import com.netcracker_study_autumn_2020.data.manager.AuthManager;
import com.netcracker_study_autumn_2020.data.manager.SessionManager;
import com.netcracker_study_autumn_2020.domain.dto.WorkspaceDto;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.CreateWorkspaceUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.DeleteWorkspaceUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.EditWorkspaceUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.GetWorkspacesUseCase;
import com.netcracker_study_autumn_2020.library.data.SpaceAccessType;
import com.netcracker_study_autumn_2020.presentation.mapper.WorkspaceModelDtoMapper;
import com.netcracker_study_autumn_2020.presentation.mvp.model.WorkspaceModel;
import com.netcracker_study_autumn_2020.presentation.mvp.view.CreateWorkspaceView;
import com.netcracker_study_autumn_2020.presentation.mvp.view.WorkspacesView;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.CreateWorkspaceFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class WorkspacesPresenter extends BasePresenter {

    private AuthManager authManager;

    private WorkspacesView workspacesView;
    private CreateWorkspaceView createWorkspaceView;

    private List<WorkspaceModel> workspaceModels;
    private SpaceAccessType currentTab = SpaceAccessType.CREATOR;

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

    public SpaceAccessType getCurrentTab() {
        return currentTab;
    }

    public void setCurrentTab(SpaceAccessType currentTab) {
        this.currentTab = currentTab;
        refreshData();
    }

    public void refreshData() {
        getWorkspacesUseCase.execute(SessionManager.getCurrentUserId(), currentTab.toString(),
                new GetWorkspacesUseCase.Callback() {
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


    public void sortByName() {
        workspaceModels.sort((o1, o2) -> {
            String name1 = o1.getName().toLowerCase();
            String name2 = o2.getName().toLowerCase();
            for (int i = 0;
                 i < (Math.min(name1.length(), name2.length())); i++) {
                if (name1.charAt(i) != name2.charAt(i)) {
                    if (name1.charAt(i) > name2.charAt(i)) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
            return 0;
        });
        workspacesView.renderWorkspaces();
    }

    public void sortByCreateDate() {
        workspaceModels.sort((o1, o2) -> {
            Date d1 = o1.getCreationTime();
            Date d2 = o2.getCreationTime();
            if (d1.after(d2)) {
                return 1;
            } else if (d1.before(d2)) {
                return -1;
            }
            return 0;
        });
        workspacesView.renderWorkspaces();
    }

    public void sortByModifiedDate() {
        workspaceModels.sort((o1, o2) -> {
            Date d1 = o1.getModificationTime();
            Date d2 = o2.getModificationTime();
            if (d1.after(d2)) {
                return 1;
            } else if (d1.before(d2)) {
                return -1;
            }
            return 0;
        });
        workspacesView.renderWorkspaces();
    }

    public void sortByColor() {
        workspaceModels.sort((o1, o2) -> {
            if (o1.getColor() > o2.getColor()) {
                return 1;
            } else if (o1.getColor() < o2.getColor()) {
                return -1;
            }
            return 0;
        });
        workspacesView.renderWorkspaces();
    }
}
