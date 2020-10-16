package com.netcracker_study_autumn_2020.data.auth_manager.impl.custom_backend_auth_usecases;

public interface SignInWithEmailAndPasswordUseCase {
    interface Callback {
        void onSignedIn(String response);

        void onError(Exception e);
    }

    void execute(String login, String password,
                 SignInWithEmailAndPasswordUseCase.Callback callback);
}
