package com.netcracker_study_autumn_2020.presentation.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;
import com.netcracker_study_autumn_2020.presentation.ui.viewholder.ImageItemViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ImagesGridRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ImageModel> imageList;

    public ImagesGridRecyclerAdapter() {
        imageList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ImageItemViewHolder) holder).onBind(imageList.get(position));
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
