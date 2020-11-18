package com.netcracker_study_autumn_2020.data.manager;


public interface AuthManager {


    interface SignInWithEmailAndPasswordCallback{
        void onSignInFinished(int code, String sessionToken);
        void onError(Exception e);
    }

    interface RegisterUserCallback{
        void onRegisterFinished(int code);
        void onError(Exception e);
    }

    interface SignOutCallback{
        void onSignOutFinished(String response);
        void onError(Exception e);
    }

    void registerUser(String email, String password, String username,
                      RegisterUserCallback registerUserCallback);
    void signInWithEmailAndPassword(String login, String password,
                                    SignInWithEmailAndPasswordCallback signIn);
    void signOut(SignOutCallback signOut);


}
