package com.netcracker_study_autumn_2020.data.manager.impl.auth.impl;

import com.netcracker_study_autumn_2020.data.manager.impl.auth.CustomAuthUseCase;
import com.netcracker_study_autumn_2020.data.manager.impl.auth.RegisterUserUseCase;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.library.network.NetworkHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterUserUseCaseImpl extends CustomAuthUseCase
                        implements RegisterUserUseCase {
    private RegisterUserUseCase.Callback callback;

    private String userLogin;
    private String userPassword;

    public RegisterUserUseCaseImpl(ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        super(threadExecutor, postExecutionThread);
    }
    @Override
    public void run() {
        JSONObject newUser = new JSONObject();

        try {
            newUser.put("password", userPassword);
            newUser.put("username", userLogin);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String response = NetworkHelper.requestPOST("/users/signup", newUser, false);
        notifyRegisterUserSuccessfully(response);
    }

    private void notifyRegisterUserSuccessfully(final String response) {
        this.postExecutionThread.post(() -> callback.onUserRegistered(response));
    }

    private void notifyError(Exception e) {
        this.postExecutionThread.post(() -> callback.onError(e));
    }

    @Override
    public void execute(String login, String password,
                        RegisterUserUseCase.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Invalid parameter: NULL callback in " +
                    "RegisterUserUseCaseImpl");
        }
        super.execute();
        userLogin = login;
        userPassword = password;
        this.callback = callback;

    }
}
