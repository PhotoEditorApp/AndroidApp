package com.netcracker_study_autumn_2020.presentation.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.netcracker_study_autumn_2020.data.custom.user.UserEntityStoreFactory;
import com.netcracker_study_autumn_2020.data.executor.JobExecutor;
import com.netcracker_study_autumn_2020.data.manager.AuthManager;
import com.netcracker_study_autumn_2020.data.manager.impl.RetrofitAuthManagerImpl;
import com.netcracker_study_autumn_2020.data.mapper.UserEntityDtoMapper;
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
import com.netcracker_study_autumn_2020.presentation.mapper.UserModelDtoMapper;
import com.netcracker_study_autumn_2020.presentation.mvp.model.UserModel;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.UserProfilePresenter;
import com.netcracker_study_autumn_2020.presentation.mvp.view.UserProfileView;

public class UserProfileFragment extends BaseFragment implements UserProfileView {

    private UserProfilePresenter userProfilePresenter;

    private TextView userFirstName;
    private TextView userLastName;
    private TextView userId;
    private TextView userEmail;

    @Override
    void initializePresenter() {
        AuthManager authManager = new RetrofitAuthManagerImpl();

        UserEntityDtoMapper userEntityDtoMapper = new UserEntityDtoMapper();
        UserEntityStoreFactory userEntityStoreFactory = new UserEntityStoreFactory();

        UserRepository userRepository = new UserRepositoryImpl(userEntityDtoMapper,
                userEntityStoreFactory);
        PostExecutionThread postExecutionThread = UIThread.getInstance();
        ThreadExecutor threadExecutor = JobExecutor.getInstance();

        GetUserByIdUseCase getUserByIdUseCase = new GetUserByIdUseCaseImpl(
                userRepository, postExecutionThread, threadExecutor
        );
        EditUserUseCase editUserUseCase = new EditUserUseCaseImpl(
                userRepository, postExecutionThread, threadExecutor
        );

        UserModelDtoMapper userModelDtoMapper = new UserModelDtoMapper();
        userProfilePresenter = new UserProfilePresenter(authManager,
                getUserByIdUseCase, editUserUseCase, userModelDtoMapper);
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
        userProfilePresenter.refreshUserProfile();
    }

    private void initInteractions(View root) {
        MaterialButton editProfile = root.findViewById(R.id.button_edit_user_profile);

        userEmail = root.findViewById(R.id.user_profile_email);
        userFirstName = root.findViewById(R.id.user_profile_first_name);
        userLastName = root.findViewById(R.id.user_profile_last_name);
        userId = root.findViewById(R.id.user_profile_id);

        editProfile.setOnClickListener(l -> {
        });
    }

    @Override
    public void renderData() {
        UserModel bufferModel = userProfilePresenter.getUserModel();
        userEmail.setText(bufferModel.getEmail());
        userFirstName.setText(bufferModel.getFirstName());
        userLastName.setText(bufferModel.getLastName());
        userId.setText(String.valueOf(bufferModel.getUser_id()));
    }
}
