package com.netcracker_study_autumn_2020.presentation.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.netcracker_study_autumn_2020.data.manager.AuthManager;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.CreateWorkspaceFragment;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.UserProfileFragment;

public class UserProfileActivity extends BaseActivity {

    private UserProfileFragment userProfileFragment;

    @Override
    protected void initializeActivity(Bundle savedInstanceState) {
        if (userProfileFragment == null){
            userProfileFragment = new UserProfileFragment();
        }
        replaceFragment(R.id.ft_container, userProfileFragment);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
    }
}
