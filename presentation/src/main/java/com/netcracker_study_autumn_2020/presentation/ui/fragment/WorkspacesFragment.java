package com.netcracker_study_autumn_2020.presentation.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.loopeer.cardstack.CardStackView;
import com.netcracker_study_autumn_2020.data.custom.workspace.WorkspaceEntityStoreFactory;
import com.netcracker_study_autumn_2020.data.executor.JobExecutor;
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
import com.netcracker_study_autumn_2020.presentation.ui.adapter.WorkspaceCardAdapter;

import java.util.ArrayList;
import java.util.List;

public class WorkspacesFragment extends BaseFragment implements CardStackView.ItemExpendListener,
        WorkspacesView {

    //TODO передавть userId во фрагмент
    private WorkspacesPresenter workspacesPresenter;
    private CardStackView cardStackView;
    private WorkspaceCardAdapter workspaceCardAdapter;

    @Override
    void initializePresenter() {
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
        workspacesPresenter = new WorkspacesPresenter(getWorkspacesUseCase,deleteWorkspaceUseCase,
                createWorkspaceUseCase, editWorkspaceUseCase);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workspaces, container, false);

        initCardStackView(root);
        initInteractions(root);
        workspacesPresenter.refreshData(1212);
        return root;
    }

    private void initInteractions(View root) {
    }

    private void initCardStackView(View root) {

        cardStackView = root.findViewById(R.id.workspaces_cards);
        cardStackView.setItemExpendListener(this);
        workspaceCardAdapter = new WorkspaceCardAdapter(getActivity());

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        workspacesPresenter.setWorkspacesView(this);
    }

     @Override
     public void renderWorkspaces(){
        List<WorkspaceModel> workspaceModels = workspacesPresenter.getWorkspaceModels();
        workspaceCardAdapter.updateData(workspaceModels);
        cardStackView.setAdapter(workspaceCardAdapter);
    }

    private void updateWorkspaces(){
        workspacesPresenter.refreshData(111);
    }


    @Override
    public void onItemExpend(boolean expend) {

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
