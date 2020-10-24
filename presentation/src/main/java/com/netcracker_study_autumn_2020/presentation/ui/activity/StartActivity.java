package com.netcracker_study_autumn_2020.presentation.ui.activity;

import android.os.Bundle;

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

    }

    private void initSupportedFragments() {
        signInFragment = new SignInFragment();
        signUpFragment = new SignUpFragment();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public void navigateToWorkspaces() {
        navigator.navigateToWorkspaces(this);
    }
}
