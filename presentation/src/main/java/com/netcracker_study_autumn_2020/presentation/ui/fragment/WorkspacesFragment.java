package com.netcracker_study_autumn_2020.presentation.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.loopeer.cardstack.CardStackView;
import com.netcracker_study_autumn_2020.data.custom.workspace.WorkspaceEntityStoreFactory;
import com.netcracker_study_autumn_2020.data.executor.JobExecutor;
import com.netcracker_study_autumn_2020.data.manager.AuthManager;
import com.netcracker_study_autumn_2020.data.manager.SessionManager;
import com.netcracker_study_autumn_2020.data.manager.impl.RetrofitAuthManagerImpl;
import com.netcracker_study_autumn_2020.data.mapper.WorkspaceEntityDtoMapper;
import com.netcracker_study_autumn_2020.data.repository.WorkspacesRepositoryImpl;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.CreateWorkspaceUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.DeleteWorkspaceUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.EditWorkspaceUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.GetWorkspacesUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.impl.CreateWorkspaceUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.impl.DeleteWorkspaceUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.impl.EditWorkspaceUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.workspace.impl.GetWorkspacesUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.repository.WorkspaceRepository;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.executor.UIThread;
import com.netcracker_study_autumn_2020.presentation.mvp.model.WorkspaceModel;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.WorkspacesPresenter;
import com.netcracker_study_autumn_2020.presentation.mvp.view.WorkspacesView;
import com.netcracker_study_autumn_2020.presentation.ui.activity.MainNavigationActivity;
import com.netcracker_study_autumn_2020.presentation.ui.adapter.WorkspaceCardAdapter;

import java.util.List;

public class WorkspacesFragment extends BaseFragment implements CardStackView.ItemExpendListener,
        WorkspacesView {

    private WorkspacesPresenter workspacesPresenter;
    private CardStackView cardStackView;
    private WorkspaceCardAdapter workspaceCardAdapter;

    @Override
    void initializePresenter() {
        AuthManager authManager = new RetrofitAuthManagerImpl();

        WorkspaceEntityStoreFactory workspaceEntityStoreFactory = new WorkspaceEntityStoreFactory();
        WorkspaceEntityDtoMapper workspaceEntityDtoMapper = new WorkspaceEntityDtoMapper();

        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();
        WorkspaceRepository workspaceRepository = WorkspacesRepositoryImpl.getInstance(
                workspaceEntityStoreFactory, workspaceEntityDtoMapper);


        GetWorkspacesUseCase getWorkspacesUseCase = new GetWorkspacesUseCaseImpl(workspaceRepository,
                postExecutionThread,threadExecutor);
        CreateWorkspaceUseCase createWorkspaceUseCase = new CreateWorkspaceUseCaseImpl(workspaceRepository,
                postExecutionThread, threadExecutor);
        DeleteWorkspaceUseCase deleteWorkspaceUseCase = new DeleteWorkspaceUseCaseImpl(workspaceRepository,
                postExecutionThread, threadExecutor);
        EditWorkspaceUseCase editWorkspaceUseCase = new EditWorkspaceUseCaseImpl(workspaceRepository,
                postExecutionThread, threadExecutor);
        workspacesPresenter = new WorkspacesPresenter(authManager, getWorkspacesUseCase,deleteWorkspaceUseCase,
                createWorkspaceUseCase, editWorkspaceUseCase);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workspaces, container, false);

        initCardStackView(root);
        initInteractions(root);
        workspacesPresenter.refreshData();
        return root;
    }

    private void initInteractions(View root) {
        MaterialButton createWorkspace = root.findViewById(R.id.button_add_workspace);
        createWorkspace.setOnClickListener(l -> {
            navigateToCreateWorkspace();
        });
    }

    private void initCardStackView(View root) {

        cardStackView = root.findViewById(R.id.workspaces_cards);
        cardStackView.setItemExpendListener(this);
        workspaceCardAdapter = new WorkspaceCardAdapter(getActivity(), workspacesPresenter);
        cardStackView.setAdapter(workspaceCardAdapter);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        workspacesPresenter.setWorkspacesView(this);
    }

     @Override
     public void renderWorkspaces() {
         List<WorkspaceModel> workspaceModels = workspacesPresenter.getWorkspaceModels();
         workspaceCardAdapter.updateData(workspaceModels);
         //cardStackView.setAdapter(workspaceCardAdapter);
         //cardStackView.invalidate();
     }

    @Override
    public void navigateToPhotosScreen(WorkspaceModel workspaceModel) {
        ((MainNavigationActivity) getActivity()).navigateToPhotoView(workspaceModel);
    }

    @Override
    public void navigateToShareWorkspaceScreen(long currentWorkspaceId) {
        ((MainNavigationActivity) getActivity()).navigateToFindAndShareFragment(
                currentWorkspaceId
        );
    }

    public void navigateToCreateWorkspace() {
        ((MainNavigationActivity) getActivity()).navigateToCreateWorkspace(SessionManager.getCurrentUserId());
    }

    private void updateWorkspaces() {
        workspacesPresenter.refreshData();
    }


    @Override
    public void onItemExpend(boolean expend) {

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
