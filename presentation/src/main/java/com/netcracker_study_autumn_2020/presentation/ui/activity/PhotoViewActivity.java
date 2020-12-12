package com.netcracker_study_autumn_2020.presentation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.netcracker_study_autumn_2020.library.data.SpaceAccessType;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.PhotoEditorFragment;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.PhotoViewFragment;

public class PhotoViewActivity extends BaseActivity {

    private static final String CHOSEN_IMAGE_MODEL_JSON = "CHOSEN_IMAGE_MODEL_JSON";
    private static final String CURRENT_WORKSPACE_ID = "CURRENT_WORKSPACE_ID";
    private static final String CURRENT_USER_SPACE_ACCESS_TYPE = "CURRENT_USER_SPACE_ACCESS_TYPE";

    private Bitmap sourceImage;
    private long workspaceId;

    public static Intent getCallingIntent(Context context, ImageModel imageModel, long workspaceId,
                                          SpaceAccessType currentUserSpaceAccess) {
        Intent photoViewIntent = new Intent(context, PhotoViewActivity.class);
        String jsonRepresentation = (new Gson()).toJson(imageModel);
        photoViewIntent.putExtra(CHOSEN_IMAGE_MODEL_JSON, jsonRepresentation);
        photoViewIntent.putExtra(CURRENT_WORKSPACE_ID, workspaceId);
        photoViewIntent.putExtra(CURRENT_USER_SPACE_ACCESS_TYPE,
                currentUserSpaceAccess.toString());

        return photoViewIntent;
    }


    @Override
    protected void initializeActivity(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        Intent intent = getIntent();
        ImageModel imageModel =
                (new Gson()).fromJson(intent.getStringExtra(CHOSEN_IMAGE_MODEL_JSON),
                        ImageModel.class);
        workspaceId = intent.getLongExtra(CURRENT_WORKSPACE_ID, 0);
        SpaceAccessType currentUserSpaceAccess = SpaceAccessType.valueOf(intent.getStringExtra(
                CURRENT_USER_SPACE_ACCESS_TYPE));

        addFragment(R.id.ft_container, new PhotoViewFragment(imageModel, workspaceId, currentUserSpaceAccess));
    }

    public void setSourceImage(Bitmap sourceImage) {
        this.sourceImage = sourceImage;
    }

    public void navigateToPhotoEditor(ImageModel imageModel) {
        PhotoEditorFragment photoEditorFragment = new PhotoEditorFragment(imageModel, sourceImage, workspaceId);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ft_container, photoEditorFragment)
                .commit();

    }
}
