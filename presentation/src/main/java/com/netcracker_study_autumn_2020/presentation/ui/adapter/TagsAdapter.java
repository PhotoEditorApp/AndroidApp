package com.netcracker_study_autumn_2020.presentation.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.BasePresenter;
import com.netcracker_study_autumn_2020.presentation.ui.viewholder.TagItemViewHolder;

import java.util.ArrayList;
import java.util.List;

public class TagsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public enum TagsAdapterType {
        IMAGE_TAGS,
        USER_TAGS,
        USER_TAGS_DIALOG
    }

    private TagsAdapterType tagsAdapterType;
    private EditText outerTagField;

    private List<String> tagsList;

    private BasePresenter currentPresenter;

    public TagsAdapter(BasePresenter currentPresenter,
                       TagsAdapterType tagsAdapterType) {
        this.tagsList = new ArrayList<>();
        this.tagsAdapterType = tagsAdapterType;
        this.currentPresenter = currentPresenter;
    }

    //Require tagsAdapterType = USER_TAGS_DIALOG
    public TagsAdapter(BasePresenter currentPresenter,
                       TagsAdapterType tagsAdapterType,
                       EditText outerTagField) {
        this.tagsList = new ArrayList<>();
        this.tagsAdapterType = tagsAdapterType;
        this.currentPresenter = currentPresenter;
        this.outerTagField = outerTagField;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TagItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tag, parent, false), tagsAdapterType,
                outerTagField);
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
