package com.netcracker_study_autumn_2020.presentation.ui.fragment;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.netcracker_study_autumn_2020.data.custom.image.ImageEntityStoreFactory;
import com.netcracker_study_autumn_2020.data.custom.tags.TagEntityStoreFactory;
import com.netcracker_study_autumn_2020.data.executor.JobExecutor;
import com.netcracker_study_autumn_2020.data.mapper.FrameEntityDtoMapper;
import com.netcracker_study_autumn_2020.data.mapper.ImageEntityDtoMapper;
import com.netcracker_study_autumn_2020.data.repository.ImageRepositoryImpl;
import com.netcracker_study_autumn_2020.data.repository.TagRepositoryImpl;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.DownloadImageByIdUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl.DownloadImageByIdUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.AddImageTagUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.DeleteImageTagUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.GetImageTagsUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.GetUserTagsUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.impl.AddImageTagUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.impl.DeleteImageTagUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.impl.GetImageTagsUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.impl.GetUserTagsUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.repository.ImageRepository;
import com.netcracker_study_autumn_2020.domain.repository.TagRepository;
import com.netcracker_study_autumn_2020.library.data.SpaceAccessType;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.executor.UIThread;
import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.PhotoViewPresenter;
import com.netcracker_study_autumn_2020.presentation.mvp.view.PreviewImageView;
import com.netcracker_study_autumn_2020.presentation.ui.activity.PhotoViewActivity;
import com.netcracker_study_autumn_2020.presentation.ui.adapter.TagsAdapter;
import com.netcracker_study_autumn_2020.presentation.ui.viewmodel.PhotoViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class PhotoViewFragment extends BaseFragment
        implements PreviewImageView {

    private ConstraintLayout mainContainer;
    private ConstraintLayout loadingUi;

    private SpaceAccessType currentUserSpaceAccessType;

    private boolean areBarsVisible = true;
    private ConstraintLayout topBar;
    private ConstraintLayout bottomBar;

    private RecyclerView photoTagsList;
    private TagsAdapter tagsAdapter;
    private SubsamplingScaleImageView subsamplingScaleImageView;


    private ImageModel imageModel;
    private long workspaceId;

    private PhotoViewModel stateSaver;

    private PhotoViewPresenter photoViewPresenter;

    public PhotoViewFragment() {
    }

    public PhotoViewFragment(ImageModel imageModel, long workspaceId,
                             SpaceAccessType currentUserSpaceAccessType) {
        this.imageModel = imageModel;
        this.workspaceId = workspaceId;
        this.currentUserSpaceAccessType = currentUserSpaceAccessType;
    }

    @Override
    void initializePresenter() {

        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        FrameEntityDtoMapper frameEntityDtoMapper = new FrameEntityDtoMapper();
        ImageEntityDtoMapper imageEntityDtoMapper = new ImageEntityDtoMapper();
        ImageEntityStoreFactory imageEntityStoreFactory = new ImageEntityStoreFactory();

        TagEntityStoreFactory tagEntityStoreFactory = new TagEntityStoreFactory();

        TagRepository tagRepository = new TagRepositoryImpl(tagEntityStoreFactory);
        ImageRepository imageRepository = ImageRepositoryImpl.getInstance(imageEntityStoreFactory,
                imageEntityDtoMapper, frameEntityDtoMapper);

        GetImageTagsUseCase getImageTagsUseCase = new GetImageTagsUseCaseImpl(tagRepository,
                postExecutionThread, threadExecutor);
        GetUserTagsUseCase getUserTagsUseCase = new GetUserTagsUseCaseImpl(tagRepository,
                postExecutionThread, threadExecutor);
        AddImageTagUseCase addImageTagUseCase = new AddImageTagUseCaseImpl(tagRepository,
                postExecutionThread, threadExecutor);
        DeleteImageTagUseCase deleteImageTagUseCase = new DeleteImageTagUseCaseImpl(tagRepository,
                postExecutionThread, threadExecutor);

        DownloadImageByIdUseCase downloadImageByIdUseCase = new DownloadImageByIdUseCaseImpl(imageRepository,
                postExecutionThread, threadExecutor);
        photoViewPresenter = new PhotoViewPresenter(imageModel, downloadImageByIdUseCase,
                getImageTagsUseCase, getUserTagsUseCase, addImageTagUseCase, deleteImageTagUseCase);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View root = inflater.inflate(R.layout.fragment_photo_view, container, false);
        stateSaver = new ViewModelProvider(this.requireActivity()).get(PhotoViewModel.class);
        initInteractions(root);
        return root;
    }

    @Override
    public void onStop() {
        super.onStop();
        stateSaver.setImageToSave(photoViewPresenter.getDownloadedImage());
    }

    private void initInteractions(View root) {
        topBar = root.findViewById(R.id.photo_view_top_bar);
        bottomBar = root.findViewById(R.id.photo_view_bottom_bar);

        ImageButton buttonSave = root.findViewById(R.id.button_save_photo);
        ImageButton buttonBack = root.findViewById(R.id.button_back_from_photo_view);
        ImageButton buttonEditPhoto = root.findViewById(R.id.button_edit_photo);

        ImageButton addImageTag = root.findViewById(R.id.button_add_image_tag);

        initRecyclerView(root);
        subsamplingScaleImageView = root.findViewById(R.id.subsampling_scale_image_view);

        addImageTag.setOnClickListener(l -> {
            AlertDialog addImageTagDialog = initAddImageTagDialog();
            addImageTagDialog.show();
        });

        subsamplingScaleImageView.setOnClickListener(l -> {
            if (areBarsVisible) {
                topBar.setVisibility(View.INVISIBLE);
                bottomBar.setVisibility(View.INVISIBLE);
                areBarsVisible = false;
                View decorView = requireActivity().getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE;
                decorView.setSystemUiVisibility(uiOptions);
            } else {
                topBar.setVisibility(View.VISIBLE);
                bottomBar.setVisibility(View.VISIBLE);
                areBarsVisible = true;
                View decorView = requireActivity().getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
                decorView.setSystemUiVisibility(uiOptions);
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
            showToastMessage("Открываю редактор изображений...", true);
            showLoading();
            navigateToPhotoEditor();
        });
        buttonBack.setOnClickListener(l -> requireActivity().finish());

        if (currentUserSpaceAccessType == SpaceAccessType.VIEWER) {
            addImageTag.setVisibility(View.GONE);
            buttonEditPhoto.setVisibility(View.GONE);
        }
    }

    private void navigateToPhotoEditor() {
        ((PhotoViewActivity) requireActivity()).setSourceImage(
                photoViewPresenter.getDownloadedImage());
        ((PhotoViewActivity) requireActivity()).navigateToPhotoEditor(imageModel);
    }

    private AlertDialog initAddImageTagDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.dialog_add_image_tag, null);
        alertDialogBuilder.setView(dialogView);

        final EditText dialogNewImageTagName = dialogView.findViewById(R.id.dialog_enter_new_tag_name);
        RecyclerView userTagsList = dialogView.findViewById(R.id.dialog_add_image_tag_user_tags_list);
        userTagsList.setLayoutManager(new LinearLayoutManager(requireContext()));
        TagsAdapter tagsAdapter = new TagsAdapter(photoViewPresenter,
                TagsAdapter.TagsAdapterType.USER_TAGS_DIALOG, dialogNewImageTagName);
        userTagsList.setAdapter(tagsAdapter);
        tagsAdapter.setTagsList(photoViewPresenter.getUserTagsList());
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Добавить",
                        (dialog, which) -> {
                            String tagName = dialogNewImageTagName.getText().toString();
                            photoViewPresenter.addImageTag(tagName);
                        })
                .setNegativeButton("Отмена",
                        (dialog, which) -> dialog.cancel());

        return alertDialogBuilder.create();
    }

    private void initRecyclerView(View root) {
        photoTagsList = root.findViewById(R.id.image_tags_list);
        photoTagsList.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));

        tagsAdapter = new TagsAdapter(photoViewPresenter,
                TagsAdapter.TagsAdapterType.IMAGE_TAGS);
        photoTagsList.setAdapter(tagsAdapter);
        tagsAdapter.setTagsList(new ArrayList<>());

    }

    private void saveImage(Bitmap bitmap) {
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            ContentValues values = contentValues();
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + getString(R.string.app_name));
            values.put(MediaStore.Images.Media.IS_PENDING, true);

            Uri uri = this.requireActivity()
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
        Bitmap savedImage = stateSaver.getImageToSave();
        if (savedImage == null) {
            photoViewPresenter.downloadImage();
        } else {
            subsamplingScaleImageView.setImage(ImageSource.bitmap(savedImage));
            photoViewPresenter.setDownloadedImage(savedImage);
        }
        photoViewPresenter.refreshData();
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

    @Override
    public void renderTags() {
        List<String> buf = photoViewPresenter.getImageTagsList();
        tagsAdapter.setTagsList(buf);

    }
}
