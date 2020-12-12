package com.netcracker_study_autumn_2020.presentation.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.model.EditorItemModel;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.PhotoEditPresenter;
import com.netcracker_study_autumn_2020.presentation.ui.viewholder.EditorItemViewHolder;

import java.util.ArrayList;
import java.util.List;

public class EditorComponentsAdapter extends RecyclerView.Adapter<EditorItemViewHolder> {
    private Fragment rootFragment;

    private List<EditorItemModel> editorItemModels;

    private PhotoEditPresenter photoEditPresenter;

    public List<EditorItemModel> getEditorItemModels() {
        return editorItemModels;
    }

    public EditorComponentsAdapter(PhotoEditPresenter photoEditPresenter,
                                   Fragment rootFragment) {
        this.editorItemModels = new ArrayList<>();
        this.photoEditPresenter = photoEditPresenter;
        this.rootFragment = rootFragment;
    }

    @NonNull
    @Override
    public EditorItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EditorItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_effect, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EditorItemViewHolder holder, int position) {
        EditorItemModel modelOnBind = editorItemModels.get(position);
        holder.onBind(modelOnBind, rootFragment);
        holder.itemView.setOnClickListener(l -> {
            switch (modelOnBind.getItemType()) {
                case FILTER:
                    photoEditPresenter.applyFilter(modelOnBind.getTitle());
                    break;
                case FRAME:
                    photoEditPresenter.applyFrame(modelOnBind.getId());
                    break;
            }
        });

    }

    @Override
    public int getItemCount() {
        return editorItemModels.size();
    }

    public void setEditorItemModels(List<EditorItemModel> editorItemModels) {
        this.editorItemModels.clear();
        this.editorItemModels.addAll(editorItemModels);
        notifyDataSetChanged();
    }
}
