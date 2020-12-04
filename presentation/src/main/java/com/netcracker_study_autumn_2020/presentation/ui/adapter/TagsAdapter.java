package com.netcracker_study_autumn_2020.presentation.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.BasePresenter;
import com.netcracker_study_autumn_2020.presentation.ui.viewholder.TagItemViewHolder;

import java.util.ArrayList;
import java.util.List;

public class TagsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private boolean isImageTag;
    private List<String> tagsList;

    private BasePresenter currentPresenter;

    public TagsAdapter(BasePresenter currentPresenter,
                       boolean isImageTag) {
        this.tagsList = new ArrayList<>();
        this.isImageTag = isImageTag;
        this.currentPresenter = currentPresenter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TagItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tag, parent, false), isImageTag);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TagItemViewHolder) holder).onBind(tagsList.get(position), currentPresenter);
    }

    @Override
    public int getItemCount() {
        return tagsList.size();
    }

    public void setTagsList(List<String> tagsList) {
        this.tagsList.clear();
        this.tagsList.addAll(tagsList);
        notifyDataSetChanged();
    }
}
