package com.netcracker_study_autumn_2020.presentation.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.netcracker_study_autumn_2020.data.auth_manager.AuthManager;
import com.netcracker_study_autumn_2020.data.auth_manager.impl.CustomBackendAuthManagerImpl;
import com.netcracker_study_autumn_2020.data.executor.JobExecutor;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.executor.UIThread;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.AuthPresenter;
import com.netcracker_study_autumn_2020.presentation.mvp.view.SignUpView;
import com.netcracker_study_autumn_2020.presentation.ui.activity.StartActivity;

public class SignUpFragment  extends BaseFragment implements SignUpView {
    private AuthPresenter authPresenter;

    @Override
    void initializePresenter() {
        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        AuthManager authManager = new CustomBackendAuthManagerImpl(threadExecutor,
                postExecutionThread);
        authPresenter = new AuthPresenter(authManager);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signup, container, false);
        initInteractions(root);
        return root;
    }

    private void initInteractions(View root) {
        StartActivity rootActivity = (StartActivity) getActivity();
        EditText login = root.findViewById(R.id.enter_email);
        EditText password = root.findViewById(R.id.enter_password);

        MaterialButton buttonSignUp = root.findViewById(R.id.button_sign_in);
        MaterialButton backToSignIn = root.findViewById(R.id.button_back_to_sign_in);
        backToSignIn.setOnClickListener(v -> {
            if (rootActivity != null) {
                rootActivity.navigateToSignIn();
            }
        });
        buttonSignUp.setOnClickListener(v -> {
            authPresenter.registerUser(login.getText().toString(),
                    password.getText().toString());
        });


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authPresenter.setSignUpView(this);
    }
}
