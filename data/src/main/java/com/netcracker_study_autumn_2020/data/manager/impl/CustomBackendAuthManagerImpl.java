package com.netcracker_study_autumn_2020.data.manager.impl;

import android.util.Log;

import com.netcracker_study_autumn_2020.data.manager.AuthManager;
import com.netcracker_study_autumn_2020.data.manager.impl.auth.RegisterUserUseCase;
import com.netcracker_study_autumn_2020.data.manager.impl.auth.SignInWithEmailAndPasswordUseCase;
import com.netcracker_study_autumn_2020.data.manager.impl.auth.impl.RegisterUserUseCaseImpl;
import com.netcracker_study_autumn_2020.data.manager.impl.auth.impl.SignInWithEmailAndPasswordUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;

public class CustomBackendAuthManagerImpl {
}
        /*implements AuthManager {

    private ThreadExecutor threadExecutor;
    private PostExecutionThread postExecutionThread;

    //private UserCredentials currentUserCredentials;

    public CustomBackendAuthManagerImpl(ThreadExecutor executor,
                                        PostExecutionThread postExecutionThread){
        this.threadExecutor = executor;
        this.postExecutionThread = postExecutionThread;
    }


    @Override
    public void registerUser(String login, String password, String username,
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
                signInCallback.onSignInFinished(response,response);
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
    public String getSessionToken() {
        return null;
    }

    @Override
    public void closeSession() {

    }

    @Override
    public boolean haveToken() {
        return false;
    }

}
**/