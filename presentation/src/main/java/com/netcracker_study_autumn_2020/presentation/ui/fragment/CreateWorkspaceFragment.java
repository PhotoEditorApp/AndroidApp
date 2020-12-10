package com.netcracker_study_autumn_2020.presentation.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.material.button.MaterialButton;
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
import com.netcracker_study_autumn_2020.presentation.mvp.view.CreateWorkspaceView;

import java.util.Date;

public class CreateWorkspaceFragment extends BaseFragment implements CreateWorkspaceView {

    private WorkspacesPresenter workspacesPresenter;


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
                postExecutionThread, threadExecutor);
        CreateWorkspaceUseCase createWorkspaceUseCase = new CreateWorkspaceUseCaseImpl(workspaceRepository,
                postExecutionThread, threadExecutor);
        DeleteWorkspaceUseCase deleteWorkspaceUseCase = new DeleteWorkspaceUseCaseImpl(workspaceRepository,
                postExecutionThread, threadExecutor);
        EditWorkspaceUseCase editWorkspaceUseCase = new EditWorkspaceUseCaseImpl(workspaceRepository,
                postExecutionThread, threadExecutor);
        workspacesPresenter = new WorkspacesPresenter(authManager, getWorkspacesUseCase, deleteWorkspaceUseCase,
                createWorkspaceUseCase, editWorkspaceUseCase);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_workspace, container, false);
        initInteractions(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        workspacesPresenter.setCreationView(this);
    }

    private void initInteractions(View root) {
        ImageButton choseWorkspaceColor = root.findViewById(R.id.button_chose_workspace_color);
        ImageView marker = root.findViewById(R.id.create_workspace_color_marker);
        final int[] workspaceColor = {Color.argb(255, 155, 155, 155)};
        EditText workspaceName = root.findViewById(R.id.enter_workspace_name);
        EditText workspaceDescription = root.findViewById(R.id.enter_workspace_description);

        MaterialButton createWorkspace = root.findViewById(R.id.button_create_workspace);
        MaterialButton backToWorkspacesList = root.findViewById(R.id.button_back_to_workspaces_list);

        backToWorkspacesList.setOnClickListener(l -> {
            requireActivity().finish();
            //((CreateWorkspaceActivity)requireActivity()).navigateToMainNavigationActivity();
        });

        choseWorkspaceColor.setOnClickListener(l -> {
            ColorPickerDialogBuilder
                    .with(requireActivity())
                    .setTitle("Выберете цвет карточки рабочего пространства:")
                    .initialColor(workspaceColor[0])
                    .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                    .lightnessSliderOnly()
                    .density(12)
                    .setOnColorSelectedListener(selectedColor -> {
                    })
                    .setPositiveButton("Выбрать", (d, lastSelectedColor, allColors) -> {
                        marker.setColorFilter(lastSelectedColor);
                        workspaceColor[0] = lastSelectedColor;
                    })
                    .setNegativeButton("Отмена", (dialog, which) -> {
                    })
                    .build()
                    .show();
        });

        createWorkspace.setOnClickListener(l -> {
            String wN = workspaceName.getText().toString();
            String wD = workspaceDescription.getText().toString();
            if (!wN.isEmpty() && !wD.isEmpty()) {
                Date currentTime = new Date(System.currentTimeMillis());
                workspacesPresenter.createWorkspace(new WorkspaceModel(
                        //workspacesPresenter.getCurrentUserId(),
                        SessionManager.getCurrentUserId(),
                        wN, wD, workspaceColor[0], currentTime, currentTime)
                );
            }
        });
    }
}
