package com.netcracker_study_autumn_2020.data.auth_manager.impl;

import android.util.Log;

import com.netcracker_study_autumn_2020.data.auth_manager.AuthManager;
import com.netcracker_study_autumn_2020.data.auth_manager.impl.custom_backend_auth_usecases.CustomAuthUseCase;
import com.netcracker_study_autumn_2020.data.auth_manager.impl.custom_backend_auth_usecases.RegisterUserUseCase;
import com.netcracker_study_autumn_2020.data.auth_manager.impl.custom_backend_auth_usecases.SignInWithEmailAndPasswordUseCase;
import com.netcracker_study_autumn_2020.data.auth_manager.impl.custom_backend_auth_usecases.impl.RegisterUserUseCaseImpl;
import com.netcracker_study_autumn_2020.data.auth_manager.impl.custom_backend_auth_usecases.impl.SignInWithEmailAndPasswordUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;

public class CustomBackendAuthManagerImpl implements AuthManager {

    private ThreadExecutor threadExecutor;
    private PostExecutionThread postExecutionThread;

    //private UserCredentials currentUserCredentials;

    public CustomBackendAuthManagerImpl(ThreadExecutor executor,
                                        PostExecutionThread postExecutionThread){
        this.threadExecutor = executor;
        this.postExecutionThread = postExecutionThread;
    }


    @Override
    public void registerUser(String login, String password,
                             final RegisterUserCallback registerUserCallback) {
        RegisterUserUseCase register = new RegisterUserUseCaseImpl(threadExecutor,
                postExecutionThread);
        Log.d("TEST", "registerUser");
        register.execute(login, password, new RegisterUserUseCase.Callback() {
            @Override
            public void onUserRegistered(String response) {
                Log.d("TEST", "onUserRegistered");
                registerUserCallback.onRegisterFinished(response);
            }

            @Override
            public void onError(Exception e) {
                registerUserCallback.onError(e);
            }
        });
    }

    @Override
    public void signInWithEmailAndPassword(String login, String password,
                                           final SignInWithEmailAndPasswordCallback signInCallback) {
        SignInWithEmailAndPasswordUseCase signIn = new SignInWithEmailAndPasswordUseCaseImpl(threadExecutor,
                postExecutionThread);
        Log.d("TEST", "signIn");
        signIn.execute(login, password, new SignInWithEmailAndPasswordUseCase.Callback() {
            @Override
            public void onSignedIn(String response) {
                Log.d("TEST", "onSignedIn");
                signInCallback.onSignInFinished(response);
            }

            @Override
            public void onError(Exception e) {
                signInCallback.onError(e);
            }
        });
    }

    @Override
    public void signOut(SignOutCallback signOut) {

    }

    @Override
    public String getCurrentUserCredentials() {
        return null;
    }
}
