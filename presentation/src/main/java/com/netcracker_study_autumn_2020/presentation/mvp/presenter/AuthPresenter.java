package com.netcracker_study_autumn_2020.presentation.mvp.presenter;

import android.util.Log;

import com.netcracker_study_autumn_2020.data.auth_manager.AuthManager;
import com.netcracker_study_autumn_2020.data.auth_manager.impl.CustomBackendAuthManagerImpl;
import com.netcracker_study_autumn_2020.presentation.mvp.view.SignInView;
import com.netcracker_study_autumn_2020.presentation.mvp.view.SignUpView;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.SignInFragment;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.SignUpFragment;

public class AuthPresenter extends BasePresenter {

    private AuthManager authManager;

    private SignInView signInView;
    private SignUpView signUpView;

    public void setSignInView(SignInView signInView) {
        this.signInView = signInView;
    }

    public void setSignUpView(SignUpView signUpView) {
        this.signUpView = signUpView;
    }

    private AuthManager.RegisterUserCallback registerUserCallback =
            new AuthManager.RegisterUserCallback() {
                @Override
                public void onRegisterFinished(String response) {
                    //TODO проверка response на null и обработка ошибок
                    Log.d("AUTH_PRESENTER_UP", response);
                    ((SignUpFragment)signUpView).showToastMessage(response, false);
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            };
    private AuthManager.SignInWithEmailAndPasswordCallback signInCallback =
            new AuthManager.SignInWithEmailAndPasswordCallback() {
                @Override
                public void onSignInFinished(String response) {
                    Log.d("AUTH_PRESENTER_IN", response);
                    ((SignInFragment)signInView).showToastMessage(response, false);
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            };

    public AuthPresenter(AuthManager authManager){
        this.authManager = authManager;
    }

    public void registerUser(String login, String password){
        authManager.registerUser(login, password, registerUserCallback);
    }

    public void signInWithEmailAndPassword(String login, String password){
        authManager.signInWithEmailAndPassword(login, password, signInCallback);
    }


}
