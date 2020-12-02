package com.netcracker_study_autumn_2020.presentation.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;
import com.netcracker_study_autumn_2020.presentation.mvp.view.ImagesView;
import com.netcracker_study_autumn_2020.presentation.ui.viewholder.ImageItemViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ImagesGridRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ImageModel> imageList;
    private Context context;

    private ImagesView imagesView;

    public ImagesGridRecyclerAdapter(ImagesView imagesView) {
        imageList = new ArrayList<>();
        this.imagesView = imagesView;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ImageItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false), imagesView);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ImageItemViewHolder) holder).onBind(imageList.get(position), (Fragment) imagesView);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public void setImageList(List<ImageModel> imageList) {
        this.imageList.clear();
        this.imageList.addAll(imageList);
        notifyDataSetChanged();
    }
}
