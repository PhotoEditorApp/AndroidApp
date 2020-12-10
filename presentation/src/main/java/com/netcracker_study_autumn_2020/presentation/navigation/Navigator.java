package com.netcracker_study_autumn_2020.presentation.navigation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;
import com.netcracker_study_autumn_2020.presentation.ui.activity.CreateWorkspaceActivity;
import com.netcracker_study_autumn_2020.presentation.ui.activity.MainNavigationActivity;
import com.netcracker_study_autumn_2020.presentation.ui.activity.PhotoEditorActivity;
import com.netcracker_study_autumn_2020.presentation.ui.activity.PhotoViewActivity;
import com.netcracker_study_autumn_2020.presentation.ui.activity.StartActivity;

public class Navigator {
    public void navigateToMainNavigationActivity(Context context) {
        context.startActivity(MainNavigationActivity.getCallingIntent(context));
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

    public void navigateToStartActivity(Context context) {
        context.startActivity(new Intent(context, StartActivity.class));
    }

    public void navigateToPhotoEditor(Context context,
                                      ImageModel imageModel, Bitmap sourceImage) {
        context.startActivity(PhotoEditorActivity.getCallingIntent(context,
                imageModel, sourceImage));
    }
}
