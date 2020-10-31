package com.netcracker_study_autumn_2020.presentation.navigation;

import android.content.Context;

import com.netcracker_study_autumn_2020.presentation.ui.activity.MainNavigationActivity;

public class Navigator {
    public void navigateToWorkspaces(Context context){
        context.startActivity(MainNavigationActivity.getCallingIntent(context, "fsdfs"));
    }
}
