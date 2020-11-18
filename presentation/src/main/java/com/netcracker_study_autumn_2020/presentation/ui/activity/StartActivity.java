package com.netcracker_study_autumn_2020.presentation.ui.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.SignInFragment;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.SignUpFragment;

public class StartActivity extends BaseActivity {

    private SignInFragment signInFragment;
    private SignUpFragment signUpFragment;


    @Override
    protected void initializeActivity(Bundle savedInstanceState) {
        initSupportedFragments();
        replaceFragment(R.id.ft_container, signInFragment);
        Log.d("HERE", "initF");
    }

    private void initSupportedFragments() {
        signInFragment = new SignInFragment();
        signUpFragment = new SignUpFragment();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("HERE", "initS0");
        super.onCreate(savedInstanceState);
        Log.d("HERE", "initS1");
        setContentView(R.layout.activity_start);

    }

    public void navigateToSignIn(){
        replaceFragment(R.id.ft_container, signInFragment);
    }

    public void navigateToSignUp(){
        replaceFragment(R.id.ft_container, signUpFragment);
    }

    public void navigateToResetPassword(){

    }

    public void navigateToWorkspaces(long userId) {
        navigator.navigateToMainNavigationActivity(this, userId);
    }
}
