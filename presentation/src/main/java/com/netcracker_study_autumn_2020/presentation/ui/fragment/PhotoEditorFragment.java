package com.netcracker_study_autumn_2020.presentation.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TabHost;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.netcracker_study_autumn_2020.data.custom.image.ImageEntityStoreFactory;
import com.netcracker_study_autumn_2020.data.executor.JobExecutor;
import com.netcracker_study_autumn_2020.data.mapper.FrameEntityDtoMapper;
import com.netcracker_study_autumn_2020.data.mapper.ImageEntityDtoMapper;
import com.netcracker_study_autumn_2020.data.repository.ImageRepositoryImpl;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.AddFrameUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.AddImageUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.ApplyFilterUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.ApplyFrameUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.GetUsersFramesUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl.AddFrameUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl.AddImageUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl.ApplyFilterUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl.ApplyFrameUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl.GetUsersFramesUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.repository.ImageRepository;
import com.netcracker_study_autumn_2020.library.files.FilesUtils;
import com.netcracker_study_autumn_2020.library.image.FilterType;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.executor.UIThread;
import com.netcracker_study_autumn_2020.presentation.mvp.model.EditorItemModel;
import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.PhotoEditPresenter;
import com.netcracker_study_autumn_2020.presentation.mvp.view.PhotoEditView;
import com.netcracker_study_autumn_2020.presentation.ui.adapter.EditorComponentsAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;
import static com.netcracker_study_autumn_2020.presentation.ui.fragment.ImagesFragment.GALLERY_REQUEST;

public class PhotoEditorFragment extends BaseFragment implements PhotoEditView {

    private PhotoEditPresenter photoEditPresenter;

    private ConstraintLayout editPreviewLoadingUI;

    private RecyclerView filtersList;
    private RecyclerView framesList;

    private SubsamplingScaleImageView subsamplingScaleImageView;

    private ImageModel imageModel;
    private long workspaceId;

    private boolean isOriginalImageShown = false;
    private File bufferImageFile;
    private Uri bufferImageUri;
    private Bitmap sourceImage;
    private Bitmap editedImage;

    private String[] galleryPermissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private EditorComponentsAdapter framesAdapter;

    public PhotoEditorFragment(ImageModel imageModel, Bitmap bitmap, long workspaceId) {
        this.imageModel = imageModel;
        this.sourceImage = bitmap;
        this.editedImage = bitmap.copy(bitmap.getConfig(), false);
        this.workspaceId = workspaceId;
    }

    @Override
    void initializePresenter() {

        PostExecutionThread postExecutionThread = UIThread.getInstance();
        ThreadExecutor threadExecutor = JobExecutor.getInstance();

        ImageEntityDtoMapper imageEntityDtoMapper = new ImageEntityDtoMapper();
        FrameEntityDtoMapper frameEntityDtoMapper = new FrameEntityDtoMapper();

        ImageEntityStoreFactory imageEntityStoreFactory = new ImageEntityStoreFactory();
        ImageRepository imageRepository = new ImageRepositoryImpl(imageEntityDtoMapper, frameEntityDtoMapper,
                imageEntityStoreFactory);

        ApplyFilterUseCase applyFilterUseCase = new ApplyFilterUseCaseImpl(imageRepository,
                postExecutionThread, threadExecutor);
        ApplyFrameUseCase applyFrameUseCase = new ApplyFrameUseCaseImpl(imageRepository,
                postExecutionThread, threadExecutor);
        GetUsersFramesUseCase getUsersFramesUseCase = new GetUsersFramesUseCaseImpl(imageRepository,
                postExecutionThread, threadExecutor);
        AddFrameUseCase addFrameUseCase = new AddFrameUseCaseImpl(imageRepository,
                postExecutionThread, threadExecutor);
        AddImageUseCase addImageUseCase = new AddImageUseCaseImpl(imageRepository,
                postExecutionThread, threadExecutor);

        photoEditPresenter = new PhotoEditPresenter(applyFrameUseCase, applyFilterUseCase,
                getUsersFramesUseCase, addFrameUseCase, addImageUseCase, imageModel);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_photo, container, false);
        requireActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initInteractions(root);
        initRecyclerViews(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        photoEditPresenter.setPhotoEditView(this);
        photoEditPresenter.getUsersFrames();
    }

    private void initInteractions(View root) {
        subsamplingScaleImageView = root.findViewById(R.id.edit_preview);
        subsamplingScaleImageView.setImage(ImageSource.bitmap(editedImage));

        editPreviewLoadingUI = root.findViewById(R.id.edit_preview_loading_ui);

        ImageButton saveImage = root.findViewById(R.id.button_save_image);
        ImageButton showOriginal = root.findViewById(R.id.button_show_original_image);
        ImageButton uploadFrame = root.findViewById(R.id.button_upload_frame);
        ImageButton exitEditor = root.findViewById(R.id.button_back_from_photo_editor);

        uploadFrame.setOnClickListener(l -> {
            Log.d("ARE_PERMISSIONS_GRANTED", String.valueOf(
                    EasyPermissions.hasPermissions(requireContext(), galleryPermissions)));
            if (EasyPermissions.hasPermissions(getContext(), galleryPermissions)) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            } else {
                EasyPermissions.requestPermissions(this, "Access for storage",
                        101, galleryPermissions);
            }

        });

        showOriginal.setOnClickListener(l -> {
            editedImage = sourceImage.copy(editedImage.getConfig(), false);
            subsamplingScaleImageView.setImage(ImageSource.bitmap(editedImage));
        });

        saveImage.setOnClickListener(l -> {
            showLoading();
            ImageLoader imageLoaderTask = new ImageLoader();
            imageLoaderTask.execute();

        });

        exitEditor.setOnClickListener(l -> {
            requireActivity().finish();
        });

        TabHost tabHost = root.findViewById(R.id.editor_tab_host);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");

        tabSpec.setContent(R.id.frames_list_tab);
        tabSpec.setIndicator("Рамки");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.filters_list_tab);
        tabSpec.setIndicator("Фильтры");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(1);
    }

    private void initRecyclerViews(View root) {
        framesList = root.findViewById(R.id.frames_list);
        filtersList = root.findViewById(R.id.filters_list);

        framesList.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL,
                false));
        filtersList.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL,
                false));

        framesAdapter = new EditorComponentsAdapter(photoEditPresenter, this);
        EditorComponentsAdapter filtersAdapter = new EditorComponentsAdapter(photoEditPresenter, this);

        framesList.setAdapter(framesAdapter);
        filtersList.setAdapter(filtersAdapter);

        List<EditorItemModel> filters = new ArrayList<>();
        filters.add(new EditorItemModel(FilterType.BLUR.toString(), R.drawable.blur));
        filters.add(new EditorItemModel(FilterType.WB.toString(), R.drawable.wb));
        filters.add(new EditorItemModel(FilterType.SHARP.toString(), R.drawable.sharp));

        List<EditorItemModel> testFrames = new ArrayList<>();
        testFrames.add(new EditorItemModel(304, "Cool frame"));

        filtersAdapter.setEditorItemModels(filters);
        framesAdapter.setEditorItemModels(testFrames);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        requireActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void updateEditableImage(Bitmap image) {
        this.editedImage = image;
        subsamplingScaleImageView.setImage(ImageSource.bitmap(editedImage));

    }

    @Override
    public void showLoading() {
        subsamplingScaleImageView.setVisibility(View.INVISIBLE);
        editPreviewLoadingUI.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        subsamplingScaleImageView.setVisibility(View.VISIBLE);
        editPreviewLoadingUI.setVisibility(View.INVISIBLE);
    }

    @Override
    public void deleteBufferFile() {
        //requireActivity().getContentResolver().delete(bufferImageUri,
        //null, null);
        bufferImageFile.delete();

    }

    @Override
    public void renderFrames() {
        List<Long> framesIds = photoEditPresenter.getUsersFrameIds();
        List<EditorItemModel> editorComponents = new ArrayList<>();
        for (Long fId : framesIds) {
            editorComponents.add(new EditorItemModel(fId, "Frame" + fId));
        }
        framesAdapter.setEditorItemModels(editorComponents);
    }

    @Override
    public void showToast(String s, boolean b) {
        showToastMessage(s, b);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showLoading();

        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String absolutePath = FilesUtils.getPath(getContext(), selectedImage);
                    File sourceFrame;
                    if (absolutePath != null && !absolutePath.isEmpty()) {
                        sourceFrame = new File(absolutePath);
                        photoEditPresenter.addFrame(sourceFrame);
                    } else {
                        Log.d("ABSOLUTE_PATH", "is null");
                    }
                } else {
                    hideLoading();
                }
                break;
            default:
                hideLoading();
        }
        Log.d("GETTING_IMAGE", "3");
        //if (lifeCycleCallBackManager != null) {
        Log.d("GETTING_IMAGE", "4");
        //lifeCycleCallBackManager.onActivityResult(requestCode, resultCode, data);
        //}
        Log.d("GETTING_IMAGE", "5");
        //show loading

    }

    private class ImageLoader extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                //Write file
                String filename = "edited_image_" + System.currentTimeMillis() + ".png";
                FileOutputStream stream = requireActivity().openFileOutput(filename, Context.MODE_PRIVATE);
                editedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                bufferImageUri = Uri.fromFile(requireActivity().getFileStreamPath(filename));

                String absolutePath = FilesUtils.getPath(requireContext(), bufferImageUri);
                if (absolutePath != null && !absolutePath.isEmpty()) {
                    bufferImageFile = new File(absolutePath);
                    photoEditPresenter.addImage(workspaceId, bufferImageFile);
                } else {
                    Log.d("ABSOLUTE_PATH", "is null");
                    hideLoading();
                }
                //Cleanup
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
