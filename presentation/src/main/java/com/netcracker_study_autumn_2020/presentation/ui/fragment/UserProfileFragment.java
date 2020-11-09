package com.netcracker_study_autumn_2020.presentation.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.netcracker_study_autumn_2020.data.executor.JobExecutor;
import com.netcracker_study_autumn_2020.data.manager.AuthManager;
import com.netcracker_study_autumn_2020.data.manager.impl.RetrofitAuthManagerImpl;
import com.netcracker_study_autumn_2020.data.repository.UserRepositoryImpl;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.EditUserUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.GetUserByIdUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.impl.EditUserUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.user.impl.GetUserByIdUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.repository.UserRepository;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.executor.UIThread;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.UserProfilePresenter;
import com.netcracker_study_autumn_2020.presentation.mvp.view.UserProfileView;

public class UserProfileFragment extends BaseFragment implements UserProfileView {

    private UserProfilePresenter userProfilePresenter;
    @Override
    void initializePresenter() {
        AuthManager authManager = new RetrofitAuthManagerImpl();

        UserRepository userRepository = new UserRepositoryImpl();
        PostExecutionThread postExecutionThread = UIThread.getInstance();
        ThreadExecutor threadExecutor = JobExecutor.getInstance();

        GetUserByIdUseCase getUserByIdUseCase = new GetUserByIdUseCaseImpl(
                userRepository, postExecutionThread, threadExecutor
        );
        EditUserUseCase editUserUseCase = new EditUserUseCaseImpl(
                userRepository, postExecutionThread, threadExecutor
        );

        userProfilePresenter = new UserProfilePresenter(authManager,
                getUserByIdUseCase, editUserUseCase);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_profile, container,false);
        initInteractions(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userProfilePresenter.setView(this);
    }

    private void initInteractions(View root) {
        MaterialButton editProfile = root.findViewById(R.id.button_edit_user_profile);
        editProfile.setOnClickListener(l -> {

        });
    }
}
