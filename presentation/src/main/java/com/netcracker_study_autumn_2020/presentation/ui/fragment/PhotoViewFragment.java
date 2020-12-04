package com.netcracker_study_autumn_2020.presentation.ui.fragment;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.google.android.material.button.MaterialButton;
import com.netcracker_study_autumn_2020.data.custom.image.ImageEntityStoreFactory;
import com.netcracker_study_autumn_2020.data.executor.JobExecutor;
import com.netcracker_study_autumn_2020.data.mapper.ImageEntityDtoMapper;
import com.netcracker_study_autumn_2020.data.repository.ImageRepositoryImpl;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.DownloadImageByIdUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl.DownloadImageByIdUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.repository.ImageRepository;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.executor.UIThread;
import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.PhotoViewPresenter;
import com.netcracker_study_autumn_2020.presentation.mvp.view.PreviewImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class PhotoViewFragment extends BaseFragment
        implements PreviewImageView {

    private ConstraintLayout mainContainer;
    private ConstraintLayout loadingUi;

    private boolean areBarsVisible = true;
    private ConstraintLayout topBar;
    private ConstraintLayout bottomBar;

    private RecyclerView photoTagsList;
    private SubsamplingScaleImageView subsamplingScaleImageView;


    private ImageModel imageModel;
    private long workspaceId;

    private PhotoViewPresenter photoViewPresenter;

    public PhotoViewFragment() {
    }

    public PhotoViewFragment(ImageModel imageModel, long workspaceId) {
        this.imageModel = imageModel;
        this.workspaceId = workspaceId;
    }

    @Override
    void initializePresenter() {

        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        ImageEntityDtoMapper imageEntityDtoMapper = new ImageEntityDtoMapper();
        ImageEntityStoreFactory imageEntityStoreFactory = new ImageEntityStoreFactory();

        ImageRepository imageRepository = ImageRepositoryImpl.getInstance(imageEntityStoreFactory,
                imageEntityDtoMapper);
        DownloadImageByIdUseCase downloadImageByIdUseCase = new DownloadImageByIdUseCaseImpl(imageRepository,
                postExecutionThread, threadExecutor);
        photoViewPresenter = new PhotoViewPresenter(imageModel, downloadImageByIdUseCase);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_photo_view, container, false);
        initInteractions(root);
        return root;
    }

    private void initInteractions(View root) {
        topBar = root.findViewById(R.id.photo_view_top_bar);
        bottomBar = root.findViewById(R.id.photo_view_bottom_bar);

        MaterialButton buttonSave = root.findViewById(R.id.button_save_photo);
        MaterialButton buttonBack = root.findViewById(R.id.button_back_from_photo_view);
        MaterialButton buttonEditPhoto = root.findViewById(R.id.button_edit_photo);

        subsamplingScaleImageView = root.findViewById(R.id.subsampling_scale_image_view);

        subsamplingScaleImageView.setOnClickListener(l -> {
            if (areBarsVisible) {
                topBar.setVisibility(View.INVISIBLE);
                bottomBar.setVisibility(View.INVISIBLE);
                areBarsVisible = false;
            } else {
                topBar.setVisibility(View.VISIBLE);
                bottomBar.setVisibility(View.VISIBLE);
                areBarsVisible = true;
            }

        });

        buttonSave.setOnClickListener(l -> {
            Bitmap imageToSave = photoViewPresenter.getDownloadedImage();
            showLoading();
            saveImage(imageToSave);
            hideLoading();
            showToastMessage("Изображение успешно сохранено в " +
                    "галерею", true);
        });
        buttonEditPhoto.setOnClickListener(l -> {

        });
        buttonBack.setOnClickListener(l -> {

        });
    }

    private void saveImage(Bitmap bitmap) {
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            ContentValues values = contentValues();
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + getString(R.string.app_name));
            values.put(MediaStore.Images.Media.IS_PENDING, true);

            Uri uri = this.getActivity()
                    .getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri != null) {
                try {
                    saveImageToStream(bitmap, getActivity()
                            .getContentResolver().openOutputStream(uri));
                    values.put(MediaStore.Images.Media.IS_PENDING, false);
                    getActivity()
                            .getContentResolver().update(uri, values, null, null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        } else {
            File directory = new File(Environment.getExternalStorageDirectory().toString() + '/' + getString(R.string.app_name));

            if (!directory.exists()) {
                directory.mkdirs();
            }
            String fileName = System.currentTimeMillis() + ".png";
            File file = new File(directory, fileName);
            try {
                saveImageToStream(bitmap, new FileOutputStream(file));
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                this.getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private ContentValues contentValues() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        }
        return values;
    }

    private void saveImageToStream(Bitmap bitmap, OutputStream outputStream) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainContainer = view.findViewById(R.id.main_container);
        loadingUi = view.findViewById(R.id.loading_ui);
        photoViewPresenter.setPreviewImageView(this);
        photoViewPresenter.downloadImage();
    }

    @Override
    public void showLoading() {
        mainContainer.setVisibility(View.INVISIBLE);
        loadingUi.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mainContainer.setVisibility(View.VISIBLE);
        loadingUi.setVisibility(View.GONE);
    }

    @Override
    public void renderImage() {
        Bitmap buf = photoViewPresenter.getDownloadedImage();
        subsamplingScaleImageView.setImage(ImageSource.bitmap(buf));
    }
}
