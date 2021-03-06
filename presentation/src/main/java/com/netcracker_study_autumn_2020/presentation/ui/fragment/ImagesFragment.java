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
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.netcracker_study_autumn_2020.data.custom.image.ImageEntityStoreFactory;
import com.netcracker_study_autumn_2020.data.executor.JobExecutor;
import com.netcracker_study_autumn_2020.data.manager.SessionManager;
import com.netcracker_study_autumn_2020.data.mapper.FrameEntityDtoMapper;
import com.netcracker_study_autumn_2020.data.mapper.ImageEntityDtoMapper;
import com.netcracker_study_autumn_2020.data.repository.ImageRepositoryImpl;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.AddImageUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.CreateCollageUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.DeleteImageUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.EditImageInfoUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.GetWorkspaceImagesInfoUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.RateImageUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl.AddImageUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl.CreateCollageUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl.DeleteImageUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl.EditImageInfoUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl.GetWorkspaceImagesInfoUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl.RateImageUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.repository.ImageRepository;
import com.netcracker_study_autumn_2020.library.data.SpaceAccessType;
import com.netcracker_study_autumn_2020.library.files.FilesUtils;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.executor.UIThread;
import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;
import com.netcracker_study_autumn_2020.presentation.mvp.model.WorkspaceModel;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.ImagesPresenter;
import com.netcracker_study_autumn_2020.presentation.mvp.view.ImagesView;
import com.netcracker_study_autumn_2020.presentation.ui.activity.MainNavigationActivity;
import com.netcracker_study_autumn_2020.presentation.ui.adapter.ImagesGridRecyclerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

public class ImagesFragment extends BaseFragment implements ImagesView {

    private ConstraintLayout emptyUI;
    private ConstraintLayout loadingUI;
    private ConstraintLayout createCollageUI;
    private ConstraintLayout mainContainer;


    private SpaceAccessType currentUserSpaceAccessType;

    private boolean isChoosingImagesForCollage = false;
    private List<Long> chosenImageIds;

    private ImagesPresenter presenter;
    private RecyclerView recyclerView;
    private ImagesGridRecyclerAdapter imagesGridRecyclerAdapter;
    private LinearLayout buttonPanel;
    private ImageButton refreshImageList;
    private AlertDialog editImageInfoDialog;

    private String[] galleryPermissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    static final int GALLERY_REQUEST = 1;

    private boolean isPanelHide = true;
    private WorkspaceModel workspaceModel;

    public ImagesFragment(WorkspaceModel workspaceModel, SpaceAccessType currentUserSpaceAccessType) {
        this.workspaceModel = workspaceModel;
        this.currentUserSpaceAccessType = currentUserSpaceAccessType;
    }

    @Override
    void initializePresenter() {
        FrameEntityDtoMapper frameEntityDtoMapper = new FrameEntityDtoMapper();
        ImageEntityDtoMapper imageEntityDtoMapper = new ImageEntityDtoMapper();
        ImageEntityStoreFactory imageEntityStoreFactory = new ImageEntityStoreFactory();

        ImageRepository imageRepository = ImageRepositoryImpl.getInstance(imageEntityStoreFactory,
                imageEntityDtoMapper, frameEntityDtoMapper);
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
        RateImageUseCase rateImageUseCase = new RateImageUseCaseImpl(imageRepository,
                postExecutionThread, threadExecutor);
        CreateCollageUseCase createCollageUseCase = new CreateCollageUseCaseImpl(imageRepository,
                postExecutionThread, threadExecutor);

        presenter = new ImagesPresenter(workspaceModel.getId(),
                addImageUseCase, getWorkspaceImagesInfoUseCase, editImageInfoUseCase,
                deleteImageUseCase, rateImageUseCase, createCollageUseCase);
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
        chosenImageIds = new ArrayList<>();
        presenter.setView(this);
        presenter.updateImageList();

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.updateImageList();
    }

    private void initRecyclerView(View root) {

        recyclerView = root.findViewById(R.id.photo_preview_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        imagesGridRecyclerAdapter = new ImagesGridRecyclerAdapter(this);
        recyclerView.setAdapter(imagesGridRecyclerAdapter);
        imagesGridRecyclerAdapter.setImageList(presenter.getImageModels());
    }

    private void initInteractions(View root) {
        mainContainer = root.findViewById(R.id.main_container);
        loadingUI = root.findViewById(R.id.loading_ui);
        emptyUI = root.findViewById(R.id.empty_ui);
        createCollageUI = root.findViewById(R.id.create_collage_ui);

        buttonPanel = root.findViewById(R.id.photo_preview_button_panel);
        ImageButton hidePanel = root.findViewById(R.id.hide_panel_button);
        ImageButton addImage = root.findViewById(R.id.button_add_image);
        ImageButton sortImages = root.findViewById(R.id.button_choose_sort);
        ImageButton sortByTags = root.findViewById(R.id.button_sort_by_tags);
        ImageButton createCollage = root.findViewById(R.id.button_choose_images_for_collage);
        refreshImageList = root.findViewById(R.id.button_refresh_images);

        LinearLayout refreshImageListBg = root.findViewById(R.id.button_refresh_workspace_bg);

        ImageButton applyCollage = root.findViewById(R.id.button_create_collage);
        ImageButton cancelCollage = root.findViewById(R.id.button_cancel);

        refreshImageList.setOnClickListener(l -> {
            presenter.updateImageList();
        });

        applyCollage.setOnClickListener(l -> {
            Log.d("APPLY", "check");
            hidePanel.setVisibility(View.VISIBLE);
            buttonPanel.setVisibility(View.VISIBLE);
            createCollageUI.setVisibility(View.GONE);
            isChoosingImagesForCollage = false;
            presenter.createCollage(chosenImageIds);
            showLoading();
        });

        createCollage.setOnClickListener(l -> {
            isPanelHide = true;
            buttonPanel.setVisibility(View.GONE);
            hidePanel.setVisibility(View.GONE);
            createCollageUI.setVisibility(View.VISIBLE);
            isChoosingImagesForCollage = true;
        });

        cancelCollage.setOnClickListener(l -> {
            Log.d("CANCEL", "check");
            hidePanel.setVisibility(View.VISIBLE);
            buttonPanel.setVisibility(View.VISIBLE);
            createCollageUI.setVisibility(View.GONE);
            isChoosingImagesForCollage = false;
        });

        hidePanel.setOnClickListener(l -> {
            if (isPanelHide) {
                buttonPanel.setVisibility(View.VISIBLE);
                refreshImageListBg.setVisibility(View.VISIBLE);
                isPanelHide = false;
            } else {
                buttonPanel.setVisibility(View.GONE);
                refreshImageListBg.setVisibility(View.GONE);
                isPanelHide = true;
            }
        });

        addImage.setOnClickListener(l -> {
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

        sortByTags.setOnClickListener(l -> {
            PopupMenu tagsMenu = new PopupMenu(getContext(), sortImages);
            tagsMenu.inflate(R.menu.unique_tags_menu);
            Set<String> tags = presenter.getUniqueImageTags();
            int i = 1;
            for (String tag : tags) {
                tagsMenu.getMenu().add(1, R.id.menu_tag, i, tag);
                i++;
            }
            tagsMenu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.menu_tag) {
                    presenter.sortByTag(menuItem.getTitle().toString());
                    return true;
                }
                return false;

            });
            tagsMenu.show();
        });
        //initImagePickUtils();

        switch (currentUserSpaceAccessType) {
            case VIEWER:
                addImage.setVisibility(View.GONE);
                createCollage.setVisibility(View.GONE);
                break;
            case EDITOR:
            case CREATOR:
                break;

        }
    }

    @Override
    public boolean isChoosingImagesForCollage() {
        return isChoosingImagesForCollage;
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
                        (dialog, which) -> dialog.cancel());

        return alertDialogBuilder.create();
    }

    private AlertDialog initRateImageDialog(ImageModel imageModel) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.dialog_rate_image, null);
        alertDialogBuilder.setView(dialogView);

        final RatingBar ratingBar = dialogView.findViewById(R.id.rate_image_bar);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Оценить",
                        (dialog, which) -> presenter.rateImage(imageModel.getId(),
                                (int) ratingBar.getRating()))
                .setNegativeButton("Отмена",
                        (dialog, which) -> dialog.cancel());

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
                    File sourceImage;
                    if (absolutePath != null && !absolutePath.isEmpty()) {
                        sourceImage = new File(absolutePath);
                        presenter.addImage(SessionManager.getCurrentUserId(),
                                workspaceModel.getId(), sourceImage);
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


    @Override
    public void renderImages() {
        List<ImageModel> buf = presenter.getImageModels();
        if (buf.isEmpty()) {
            emptyUI.setVisibility(View.VISIBLE);
        } else {
            emptyUI.setVisibility(View.GONE);
        }
        imagesGridRecyclerAdapter.setImageList(buf);

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
                case R.id.menu_item_image_preview_rate:
                    AlertDialog rateDialog = initRateImageDialog(imageModel);
                    rateDialog.show();
                default:
                    return false;
            }
        });
        switch (currentUserSpaceAccessType) {
            case CREATOR:
            case EDITOR:
                imageMenu.show();
                break;
            case VIEWER:
                showToastMessage("Недостаточно прав для " +
                        "оценки, редактирования или удаления изображения!", true);

        }
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

    @Override
    public void chooseImage(long imageId) {
        chosenImageIds.add(imageId);
    }

    @Override
    public void removeChosenImage(long imageId) {
        chosenImageIds.remove(imageId);
    }

    public void navigateToPhotoView(ImageModel imageModel) {
        ((MainNavigationActivity) requireActivity()).navigateToFullSizePhotoView(imageModel,
                workspaceModel.getId(), currentUserSpaceAccessType);
    }
}
