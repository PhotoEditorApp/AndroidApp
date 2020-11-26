package com.netcracker_study_autumn_2020.presentation.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.netcracker_study_autumn_2020.data.custom.image.ImageEntityStoreFactory;
import com.netcracker_study_autumn_2020.data.executor.JobExecutor;
import com.netcracker_study_autumn_2020.data.mapper.ImageEntityDtoMapper;
import com.netcracker_study_autumn_2020.data.repository.ImageRepositoryImpl;
import com.netcracker_study_autumn_2020.domain.executor.PostExecutionThread;
import com.netcracker_study_autumn_2020.domain.executor.ThreadExecutor;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.DeleteImageUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.EditImageInfoUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.GetWorkspaceImagesInfoUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl.DeleteImageUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl.EditImageInfoUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.impl.GetWorkspaceImagesInfoUseCaseImpl;
import com.netcracker_study_autumn_2020.domain.repository.ImageRepository;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.executor.UIThread;
import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;
import com.netcracker_study_autumn_2020.presentation.mvp.model.WorkspaceModel;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.ImagesPresenter;
import com.netcracker_study_autumn_2020.presentation.mvp.view.ImagesView;
import com.netcracker_study_autumn_2020.presentation.ui.adapter.ImagesGridRecyclerAdapter;

public class ImagesFragment extends BaseFragment implements ImagesView {

    private ImagesPresenter presenter;
    private RecyclerView recyclerView;
    private ImagesGridRecyclerAdapter imagesGridRecyclerAdapter;
    private LinearLayout buttonPanel;

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

        GetWorkspaceImagesInfoUseCase getWorkspaceImagesInfoUseCase = new GetWorkspaceImagesInfoUseCaseImpl(imageRepository,
                postExecutionThread, threadExecutor);
        EditImageInfoUseCase editImageInfoUseCase = new EditImageInfoUseCaseImpl(imageRepository,
                postExecutionThread, threadExecutor);
        DeleteImageUseCase deleteImageUseCase = new DeleteImageUseCaseImpl(imageRepository,
                postExecutionThread, threadExecutor);

        //TODO get spaceId properly
        presenter = new ImagesPresenter(workspaceModel.getId()
                , getWorkspaceImagesInfoUseCase, editImageInfoUseCase,
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

    private void initRecyclerView(View root) {
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
            //TODO Переход к фрагменту с загрузкой изображения
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.setView(this);
        presenter.updateImageList();

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
                    presenter.editImageInfo(imageModel);
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
}
