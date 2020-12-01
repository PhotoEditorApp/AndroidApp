package com.netcracker_study_autumn_2020.presentation.ui.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.netcracker_study_autumn_2020.data.custom.image.ImageEntityStoreFactory;
import com.netcracker_study_autumn_2020.data.executor.JobExecutor;
import com.netcracker_study_autumn_2020.data.manager.SessionManager;
import com.netcracker_study_autumn_2020.data.mapper.ImageEntityDtoMapper;
import com.netcracker_study_autumn_2020.data.repository.ImageRepositoryImpl;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.AddImageUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.DeleteImageUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.EditImageInfoUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.GetWorkspaceImagesInfoUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl.AddImageUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl.DeleteImageUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl.EditImageInfoUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl.GetWorkspaceImagesInfoUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.repository.ImageRepository;
import com.netcracker_study_autumn_2020.library.files.FilesUtils;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.executor.UIThread;
import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;
import com.netcracker_study_autumn_2020.presentation.mvp.model.WorkspaceModel;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.ImagesPresenter;
import com.netcracker_study_autumn_2020.presentation.mvp.view.ImagesView;
import com.netcracker_study_autumn_2020.presentation.ui.adapter.ImagesGridRecyclerAdapter;

import java.io.File;

import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

public class ImagesFragment extends BaseFragment implements ImagesView {

    private ConstraintLayout loadingUI;
    private ConstraintLayout mainContainer;

    private ImagesPresenter presenter;
    private RecyclerView recyclerView;
    private ImagesGridRecyclerAdapter imagesGridRecyclerAdapter;
    private LinearLayout buttonPanel;
    private AlertDialog editImageInfoDialog;

    private String[] galleryPermissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    static final int GALLERY_REQUEST = 1;

    private boolean isPanelHide = true;
    private WorkspaceModel workspaceModel;

    public ImagesFragment(WorkspaceModel workspaceModel) {
        this.workspaceModel = workspaceModel;
    }

    @Override
    void initializePresenter() {

        ImageEntityDtoMapper imageEntityDtoMapper = new ImageEntityDtoMapper();
        ImageEntityStoreFactory imageEntityStoreFactory = new ImageEntityStoreFactory();

        ImageRepository imageRepository = ImageRepositoryImpl.getInstance(imageEntityStoreFactory,
                imageEntityDtoMapper);
        PostExecutionThread postExecutionThread = UIThread.getInstance();
        ThreadExecutor threadExecutor = JobExecutor.getInstance();

        AddImageUseCase addImageUseCase = new AddImageUseCaseImpl(imageRepository, postExecutionThread,
                threadExecutor);
        GetWorkspaceImagesInfoUseCase getWorkspaceImagesInfoUseCase = new GetWorkspaceImagesInfoUseCaseImpl(imageRepository,
                postExecutionThread, threadExecutor);
        EditImageInfoUseCase editImageInfoUseCase = new EditImageInfoUseCaseImpl(imageRepository,
                postExecutionThread, threadExecutor);
        DeleteImageUseCase deleteImageUseCase = new DeleteImageUseCaseImpl(imageRepository,
                postExecutionThread, threadExecutor);

        //TODO get spaceId properly
        presenter = new ImagesPresenter(workspaceModel.getId(),
                addImageUseCase, getWorkspaceImagesInfoUseCase, editImageInfoUseCase,
                deleteImageUseCase);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_images, container, false);
        initInteractions(root);
        initRecyclerView(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.setView(this);
        presenter.updateImageList();

    }

    private void initRecyclerView(View root) {
        mainContainer = root.findViewById(R.id.main_container);
        loadingUI = root.findViewById(R.id.loading_ui);
        recyclerView = root.findViewById(R.id.photo_preview_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        imagesGridRecyclerAdapter = new ImagesGridRecyclerAdapter(this);
        recyclerView.setAdapter(imagesGridRecyclerAdapter);
        imagesGridRecyclerAdapter.setImageList(presenter.getImageModels());
    }

    private void initInteractions(View root) {

        buttonPanel = root.findViewById(R.id.photo_preview_button_panel);
        ImageButton hidePanel = root.findViewById(R.id.hide_panel_button);
        ImageButton addImage = root.findViewById(R.id.button_add_image);
        ImageButton sortImages = root.findViewById(R.id.button_choose_sort);



        hidePanel.setOnClickListener(l -> {
            if (isPanelHide) {
                buttonPanel.setVisibility(View.VISIBLE);
                isPanelHide = false;
            } else {
                buttonPanel.setVisibility(View.GONE);
                isPanelHide = true;
            }
        });

        addImage.setOnClickListener(l -> {
            Log.d("ARE_PERMISSIONS_GRANTED", String.valueOf(
                    EasyPermissions.hasPermissions(getContext(), galleryPermissions)));
            if (EasyPermissions.hasPermissions(getContext(), galleryPermissions)) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            } else {
                EasyPermissions.requestPermissions(this, "Access for storage",
                        101, galleryPermissions);
            }
        });

        sortImages.setOnClickListener(l -> {
            PopupMenu sortMenu = new PopupMenu(getContext(), sortImages);
            sortMenu.inflate(R.menu.image_sort_menu);

            sortMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.menu_image_sort_by_average_color:
                        presenter.sortByAverageColor();
                        return true;
                    case R.id.menu_image_sort_by_create_date:
                        presenter.sortByCreateDate();
                        return true;
                    case R.id.menu_image_sort_by_modified_date:
                        presenter.sortByModifiedDate();
                        return true;
                    case R.id.menu_image_sort_by_name:
                        presenter.sortByName();
                        return true;
                    case R.id.menu_image_sort_by_rating:
                        presenter.sortByRating();
                        return true;
                    default:
                        return false;
                }
            });
            sortMenu.show();

        });

        //initImagePickUtils();
    }

    private AlertDialog initEditImageInfoDialog(ImageModel imageModel) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.dialog_edit_image_info, null);
        alertDialogBuilder.setView(dialogView);

        final EditText dialogNewImageName = dialogView.findViewById(R.id.dialog_enter_new_image_name);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Подтвердить",
                        (dialog, which) -> {
                            imageModel.setName(
                                    dialogNewImageName.getText().toString());
                            presenter.editImageInfo(imageModel);
                        })
                .setNegativeButton("Отмена",
                        (dialog, which) -> {
                            dialog.cancel();
                        });

        return alertDialogBuilder.create();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("GETTING_IMAGE", "Rare situation with permissions!");
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
                    File sourceImage = null;
                    if (absolutePath != null) {
                        sourceImage = new File(absolutePath);
                        presenter.addImage(SessionManager.getCurrentUserId(),
                                workspaceModel.getId(), sourceImage);
                    } else {
                        Log.d("ABSOLUTE_PATH", "is null");
                    }
                }
        }
        Log.d("GETTING_IMAGE", "3");
        //if (lifeCycleCallBackManager != null) {
        Log.d("GETTING_IMAGE", "4");
        //lifeCycleCallBackManager.onActivityResult(requestCode, resultCode, data);
        //}
        Log.d("GETTING_IMAGE", "5");
        //show loading

    }


    @Override
    public void renderImages() {
        imagesGridRecyclerAdapter.setImageList(
                presenter.getImageModels()
        );
    }

    @Override
    public void showImageMenu(ImageModel imageModel, View targetView) {
        PopupMenu imageMenu = new PopupMenu(targetView.getContext(), targetView);
        imageMenu.inflate(R.menu.image_preview_menu);

        imageMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.menu_item_image_preview_edit:
                    editImageInfoDialog = initEditImageInfoDialog(imageModel);
                    editImageInfoDialog.show();
                    return true;
                case R.id.menu_item_image_preview_delete:
                    presenter.deleteImage(imageModel.getId());
                    return true;
                default:
                    return false;
            }
        });
        imageMenu.show();
    }

    @Override
    public void showLoading() {
        mainContainer.setVisibility(View.INVISIBLE);
        loadingUI.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mainContainer.setVisibility(View.VISIBLE);
        loadingUI.setVisibility(View.GONE);
    }
}
