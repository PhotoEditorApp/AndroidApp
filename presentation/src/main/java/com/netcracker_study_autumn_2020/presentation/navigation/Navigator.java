package com.netcracker_study_autumn_2020.presentation.navigation;

import android.content.Context;

import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;
import com.netcracker_study_autumn_2020.presentation.ui.activity.CreateWorkspaceActivity;
import com.netcracker_study_autumn_2020.presentation.ui.activity.MainNavigationActivity;
import com.netcracker_study_autumn_2020.presentation.ui.activity.PhotoViewActivity;

public class Navigator {
    public void navigateToMainNavigationActivity(Context context, long userId) {
        context.startActivity(MainNavigationActivity.getCallingIntent(context, userId));
    }

    //public void navigateToPhotosActivity(Context context, WorkspaceModel workspaceModel){
    //context.startActivity(ImagesActivity.getCallingIntent(context, workspaceModel));
    // }

    public void navigateToCreateWorkspaceActivity(Context context, long userId) {
        context.startActivity(CreateWorkspaceActivity.getCallingIntent(context, userId));
    }

    public void navigateToPhotoView(Context context, ImageModel imageModel, long workspaceId) {
        context.startActivity(PhotoViewActivity.getCallingIntent(context,
                imageModel, workspaceId));
    }
}
