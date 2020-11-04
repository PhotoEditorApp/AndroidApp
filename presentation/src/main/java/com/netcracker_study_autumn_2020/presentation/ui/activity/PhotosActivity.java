package com.netcracker_study_autumn_2020.presentation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.netcracker_study_autumn_2020.presentation.mvp.model.WorkspaceModel;

public class PhotosActivity extends BaseActivity {


    public static Intent getCallingIntent(Context context, WorkspaceModel workspaceModel){
        Intent intent = new Intent(context, PhotosActivity.class);
        intent.putExtra("CURRENT_WORKSPACE", workspaceModel);
        return intent;
    }

    @Override
    protected void initializeActivity(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
