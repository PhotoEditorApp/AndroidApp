package com.netcracker_study_autumn_2020.data.manager.impl.auth.impl;

import com.netcracker_study_autumn_2020.data.manager.impl.auth.CustomAuthUseCase;
import com.netcracker_study_autumn_2020.data.manager.impl.auth.SignInWithEmailAndPasswordUseCase;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.library.network.NetworkHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class SignInWithEmailAndPasswordUseCaseImpl extends CustomAuthUseCase implements SignInWithEmailAndPasswordUseCase  {

    private SignInWithEmailAndPasswordUseCase.Callback callback;

    private String userLogin;
    private String userPassword;


    public SignInWithEmailAndPasswordUseCaseImpl(ThreadExecutor threadExecutor,
                                                 PostExecutionThread postExecutionThread){
        super(threadExecutor, postExecutionThread);
    }

    @Override
    public void run() {
        JSONObject currentUser = new JSONObject();

        try {
            currentUser.put("password", userPassword);
            currentUser.put("username", userLogin);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String response = NetworkHelper.requestPOST("/login",
                currentUser, false);
        notifySignInSuccessfully(response);
    }


    private void notifySignInSuccessfully(final String response) {
        this.postExecutionThread.post(() -> callback.onSignedIn(response));
    }

    private void notifyError(Exception e) {
        this.postExecutionThread.post(() -> callback.onError(e));
    }


    @Override
    public void execute(String login, String password, Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Invalid parameter: NULL callback in " +
                    "SignInWithEmailAndPasswordUseCaseImpl");
        }
        super.execute();
        this.callback = callback;
        this.userLogin = login;
        this.userPassword = password;

    }
}
