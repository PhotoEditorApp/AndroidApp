package com.netcracker_study_autumn_2020.presentation.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;
import com.netcracker_study_autumn_2020.presentation.mvp.model.WorkspaceModel;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.ImagesPresenter;
import com.netcracker_study_autumn_2020.presentation.mvp.view.ImagesView;
import com.netcracker_study_autumn_2020.presentation.ui.adapter.ImagesGridRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ImagesFragment extends BaseFragment implements ImagesView {

    private ImagesPresenter presenter;
    private RecyclerView recyclerView;
    private ImagesGridRecyclerAdapter imagesGridRecyclerAdapter;
    private LinearLayout buttonPanel;

    private boolean isPanelHide = true;

    public ImagesFragment(WorkspaceModel workspaceModel) {

    }

    @Override
    void initializePresenter() {
        presenter = new ImagesPresenter();
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

        //Test
        List<ImageModel> testData = new ArrayList<>();
        testData.add(new ImageModel("https://eskipaper.com/images250_/cool-scenic-wallpaper-1.jpg",
                "Горы11"));
        testData.add(new ImageModel("https://eskipaper.com/images250_/cool-scenic-wallpaper-1.jpg",
                "Горы11"));
        testData.add(new ImageModel("https://eskipaper.com/images250_/cool-scenic-wallpaper-1.jpg",
                "Горы11"));
        testData.add(new ImageModel("https://eskipaper.com/images250_/cool-scenic-wallpaper-1.jpg",
                "Горы11"));
        testData.add(new ImageModel("https://eskipaper.com/images250_/cool-scenic-wallpaper-1.jpg",
                "Горы11"));
        testData.add(new ImageModel("https://eskipaper.com/images250_/cool-scenic-wallpaper-1.jpg",
                "Горы11"));
        testData.add(new ImageModel("https://eskipaper.com/images250_/cool-scenic-wallpaper-1.jpg",
                "Горы11"));
        testData.add(new ImageModel("https://eskipaper.com/images250_/cool-scenic-wallpaper-1.jpg",
                "Горы11"));
        testData.add(new ImageModel("https://eskipaper.com/images250_/cool-scenic-wallpaper-1.jpg",
                "Горы11"));
        testData.add(new ImageModel("https://eskipaper.com/images250_/cool-scenic-wallpaper-1.jpg",
                "Горы11"));
        testData.add(new ImageModel("https://eskipaper.com/images250_/cool-scenic-wallpaper-1.jpg",
                "Горы11"));
        testData.add(new ImageModel("https://eskipaper.com/images250_/cool-scenic-wallpaper-1.jpg",
                "Горы11"));
        testData.add(new ImageModel("https://eskipaper.com/images250_/cool-scenic-wallpaper-1.jpg",
                "Горы11"));
        testData.add(new ImageModel("https://eskipaper.com/images250_/cool-scenic-wallpaper-1.jpg",
                "Горы11"));
        testData.add(new ImageModel("https://eskipaper.com/images250_/cool-scenic-wallpaper-1.jpg",
                "Горы11"));
        testData.add(new ImageModel("https://eskipaper.com/images250_/cool-scenic-wallpaper-1.jpg",
                "Горы11"));
        testData.add(new ImageModel("https://eskipaper.com/images250_/cool-scenic-wallpaper-1.jpg",
                "Горы11"));
        testData.add(new ImageModel("https://eskipaper.com/images250_/cool-scenic-wallpaper-1.jpg",
                "Горы11"));
        testData.add(new ImageModel("https://eskipaper.com/images250_/cool-scenic-wallpaper-1.jpg",
                "Горы11"));
        testData.add(new ImageModel("https://eskipaper.com/images250_/cool-scenic-wallpaper-1.jpg",
                "Горы11"));
        testData.add(new ImageModel("https://eskipaper.com/images250_/cool-scenic-wallpaper-1.jpg",
                "Горы11"));
        testData.add(new ImageModel("https://eskipaper.com/images250_/cool-scenic-wallpaper-1.jpg",
                "Горы11"));
        testData.add(new ImageModel("https://eskipaper.com/images250_/cool-scenic-wallpaper-1.jpg",
                "Горы11"));

        imagesGridRecyclerAdapter = new ImagesGridRecyclerAdapter();
        recyclerView.setAdapter(imagesGridRecyclerAdapter);
        imagesGridRecyclerAdapter.setImageList(testData);
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

    }
}
