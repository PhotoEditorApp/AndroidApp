package com.netcracker_study_autumn_2020.presentation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.PhotoViewFragment;

public class PhotoViewActivity extends BaseActivity {

    private static final String CHOSEN_IMAGE_MODEL_JSON = "CHOSEN_IMAGE_MODEL_JSON";
    private static final String CURRENT_WORKSPACE_ID = "CURRENT_WORKSPACE_ID";

    public static Intent getCallingIntent(Context context, ImageModel imageModel, long workspaceId) {
        Intent photoViewIntent = new Intent(context, PhotoViewActivity.class);
        String jsonRepresentation = (new Gson()).toJson(imageModel);
        photoViewIntent.putExtra(CHOSEN_IMAGE_MODEL_JSON, jsonRepresentation);
        photoViewIntent.putExtra(CURRENT_WORKSPACE_ID, workspaceId);

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
        long workspaceId = intent.getLongExtra(CURRENT_WORKSPACE_ID, 0);

        addFragment(R.id.ft_container, new PhotoViewFragment(imageModel, workspaceId));
    }
}
