package com.netcracker_study_autumn_2020.data.manager.impl.auth;

public interface RegisterUserUseCase {
    interface Callback {
        void onUserRegistered(String response);

        void onError(Exception e);
    }

    //TODO передавать объект представляющий пользователя
    void execute(String login, String password,
                 RegisterUserUseCase.Callback callback);
}
