package com.netcracker_study_autumn_2020.presentation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.CreateWorkspaceFragment;

public class CreateWorkspaceActivity extends BaseActivity {

    private CreateWorkspaceFragment createWorkspaceFragment;

    public static Intent getCallingIntent(Context context, int userId) {
        Intent intent = new Intent(context, CreateWorkspaceActivity.class);
        intent.putExtra("CURRENT_USER_ID", userId);
        return intent;
    }


    @Override
    protected void initializeActivity(Bundle savedInstanceState) {
        if (createWorkspaceFragment == null){
            createWorkspaceFragment = new CreateWorkspaceFragment();
        }
        replaceFragment(R.id.ft_container, createWorkspaceFragment);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workspace);
    }
}
