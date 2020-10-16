package com.netcracker_study_autumn_2020.data.auth_manager;

public interface AuthManager {
    interface SignInWithEmailAndPasswordCallback{
        void onSignInFinished(String response);
        void onError(Exception e);
    }

    interface RegisterUserCallback{
        void onRegisterFinished(String response);
        void onError(Exception e);
    }

    interface SignOutCallback{
        void onSignOutFinished(String response);
        void onError(Exception e);
    }

    void registerUser(String login, String password,
                      RegisterUserCallback registerUserCallback);
    void signInWithEmailAndPassword(String login, String password,
                                    SignInWithEmailAndPasswordCallback signIn);
    void signOut(SignOutCallback signOut);
    String getCurrentUserCredentials();
}
