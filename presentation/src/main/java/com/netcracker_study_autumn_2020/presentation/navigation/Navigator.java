package com.netcracker_study_autumn_2020.presentation.navigation;

import android.content.Context;

import com.netcracker_study_autumn_2020.presentation.mvp.model.WorkspaceModel;
import com.netcracker_study_autumn_2020.presentation.ui.activity.CreateWorkspaceActivity;
import com.netcracker_study_autumn_2020.presentation.ui.activity.MainNavigationActivity;

public class Navigator {
    public void navigateToMainNavigationActivity(Context context, int userId){
        context.startActivity(MainNavigationActivity.getCallingIntent(context, "fsdfs"));
    }

    //public void navigateToPhotosActivity(Context context, WorkspaceModel workspaceModel){
        //context.startActivity(ImagesActivity.getCallingIntent(context, workspaceModel));
   // }

    public void navigateToCreateWorkspaceActivity(Context context, int userId) {
        context.startActivity(CreateWorkspaceActivity.getCallingIntent(context, userId));
    }
}
