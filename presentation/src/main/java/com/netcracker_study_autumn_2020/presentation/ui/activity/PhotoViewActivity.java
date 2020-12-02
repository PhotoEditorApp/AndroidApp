package com.netcracker_study_autumn_2020.presentation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.PhotoViewFragment;

public class PhotoViewActivity extends BaseActivity {

    private long imageId;
    private long workspaceId;

    private static final String CHOSEN_IMAGE_ID = "CHOSEN_IMAGE_ID";
    private static final String CURRENT_WORKSPACE_ID = "CURRENT_WORKSPACE_ID";

    public static Intent getCallingIntent(Context context, long imageId, long workspaceId) {
        Intent photoViewIntent = new Intent(context, PhotoViewActivity.class);
        photoViewIntent.putExtra(CHOSEN_IMAGE_ID, imageId);
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
        long imageId = intent.getLongExtra(CHOSEN_IMAGE_ID, 0);
        long workspaceId = intent.getLongExtra(CURRENT_WORKSPACE_ID, 0);

        replaceFragment(R.id.ft_container, new PhotoViewFragment(imageId, workspaceId));
    }
}
