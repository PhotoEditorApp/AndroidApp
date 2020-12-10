package com.netcracker_study_autumn_2020.presentation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;
import com.netcracker_study_autumn_2020.presentation.ui.fragment.PhotoEditorFragment;

public class PhotoEditorActivity extends BaseActivity {

    private static final String CHOSEN_IMAGE_MODEL_JSON = "CHOSEN_IMAGE_MODEL_JSON";
    private static final String CHOSEN_IMAGE_BITMAP = "CHOSEN_IMAGE_BITMAP";

    public static Intent getCallingIntent(Context context, ImageModel imageModel,
                                          Bitmap bitmap) {
        Intent callingIntent = new Intent(context, PhotoEditorActivity.class);
        String jsonRepresentation = (new Gson()).toJson(imageModel);
        callingIntent.putExtra(CHOSEN_IMAGE_MODEL_JSON, jsonRepresentation);
        callingIntent.putExtra(CHOSEN_IMAGE_BITMAP, bitmap);

        return callingIntent;
    }

    @Override
    protected void initializeActivity(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);

        Intent intent = getIntent();
        ImageModel imageModel =
                (new Gson()).fromJson(intent.getStringExtra(CHOSEN_IMAGE_MODEL_JSON),
                        ImageModel.class);
        Bitmap sourceImage = (Bitmap) intent.getParcelableExtra(CHOSEN_IMAGE_BITMAP);

        addFragment(R.id.ft_container, new PhotoEditorFragment(imageModel, sourceImage));
    }
}
