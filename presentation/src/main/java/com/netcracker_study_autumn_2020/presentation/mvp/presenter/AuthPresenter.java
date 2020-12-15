package com.netcracker_study_autumn_2020.presentation.mvp.presenter;

import android.util.Log;

import com.netcracker_study_autumn_2020.data.manager.AuthManager;
import com.netcracker_study_autumn_2020.data.manager.SessionManager;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.view.SignInView;
import com.netcracker_study_autumn_2020.presentation.mvp.view.SignUpView;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.SignInFragment;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.SignUpFragment;

public class AuthPresenter extends BasePresenter {

    private AuthManager authManager;

    private SignInView signInView;
    private SignUpView signUpView;

    public long getUserId() {
        return SessionManager.getCurrentUserId();
    }

    public void setSignInView(SignInView signInView) {
        this.signInView = signInView;
    }

    public void setSignUpView(SignUpView signUpView) {
        this.signUpView = signUpView;
    }

    private AuthManager.RegisterUserCallback registerUserCallback =
            new AuthManager.RegisterUserCallback() {
                @Override
                public void onRegisterFinished(int code) {
                    Log.d("AUTH_PRESENTER_UP", String.valueOf(code));
                    ((SignUpFragment)signUpView).showToastMessage(String.valueOf(code), false);
                    if (code == 200){
                        signUpView.navigateToSignIn();
                    }else{
                        ((SignUpFragment)signUpView).showToastMessage(
                                ((SignUpFragment) signUpView).getString(R.string.sign_up_error),
                                true);
                    }

                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            };

    private AuthManager.SignInWithEmailAndPasswordCallback signInCallback =
            new AuthManager.SignInWithEmailAndPasswordCallback() {
                @Override
                public void onSignInFinished(int code, String sessionToken) {
                    Log.d("AUTH_PRESENTER_IN", String.valueOf(code));
                    Log.d("AUTH_PRESENTER_IN", sessionToken);
                    //((SignInFragment)signInView).showToastMessage(code +
                    //sessionToken, false);
                    if (SessionManager.isSessionOpened()) {
                        signInView.navigateToWorkspaces();
                    } else {
                        ((SignInFragment) signInView).showToastMessage(
                                ((SignInFragment) signInView).getString(
                                        R.string.login_error), true);
                    }

                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            };

    public AuthPresenter(AuthManager authManager){
        this.authManager = authManager;
    }

    public void registerUser(String email, String username, String password){
        authManager.registerUser(email, username, password, registerUserCallback);
    }

    public void signInWithEmailAndPassword(String login, String password){
        authManager.signInWithEmailAndPassword(login, password, signInCallback);
    }


}
