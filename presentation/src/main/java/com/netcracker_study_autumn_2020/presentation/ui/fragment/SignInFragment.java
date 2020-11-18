package com.netcracker_study_autumn_2020.presentation.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.netcracker_study_autumn_2020.data.manager.AuthManager;
import com.netcracker_study_autumn_2020.data.manager.SessionManager;
import com.netcracker_study_autumn_2020.data.manager.impl.RetrofitAuthManagerImpl;
import com.netcracker_study_autumn_2020.library.network.NetworkUtils;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.AuthPresenter;
import com.netcracker_study_autumn_2020.presentation.mvp.view.SignInView;
import com.netcracker_study_autumn_2020.presentation.ui.activity.StartActivity;

public class SignInFragment extends BaseFragment implements SignInView {

    private AuthPresenter authPresenter;

    @Override
    void initializePresenter() {
        AuthManager authManager = new RetrofitAuthManagerImpl();
        authPresenter = new AuthPresenter(authManager);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signin, container, false);
        initInteractions(root);
        return root;
    }



    private void initInteractions(View root) {
        StartActivity rootActivity = (StartActivity) getActivity();
        TextView signUpLink = root.findViewById(R.id.to_register_user_link);
        MaterialButton signIn = root.findViewById(R.id.button_create_workspace);

        EditText login = root.findViewById(R.id.enter_email);
        EditText password = root.findViewById(R.id.enter_password);

        //DEBUG
        EditText apiAddress = root.findViewById(R.id.api_debug);
        String api = apiAddress.getText().toString();


        signIn.setOnClickListener(v -> {
            String username = login.getText().toString();
            String passwordInput = password.getText().toString();
            if (username.isEmpty() || passwordInput.isEmpty()){
                showToastMessage(getString(R.string.empty_login_or_password),
                        true);
            }else {
                //DEBUG
                if (!api.isEmpty()){
                    NetworkUtils.setApiAddress(api);
                    AuthManager authManager = new RetrofitAuthManagerImpl();
                    authPresenter = new AuthPresenter(authManager);
                }
                authPresenter.signInWithEmailAndPassword(
                        username, passwordInput);
            }

        });
        signUpLink.setOnClickListener(v -> {
            if (rootActivity != null) {
                rootActivity.navigateToSignUp();
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authPresenter.setSignInView(this);
    }


    @Override
    public void navigateToWorkspaces() {
        ((StartActivity) getActivity()).navigateToWorkspaces(SessionManager.getCurrentUserId());
    }
}
