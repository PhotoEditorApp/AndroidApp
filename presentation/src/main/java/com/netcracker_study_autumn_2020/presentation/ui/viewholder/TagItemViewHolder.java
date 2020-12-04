package com.netcracker_study_autumn_2020.presentation.ui.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.BasePresenter;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.PhotoViewPresenter;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.UserTagsPresenter;

public class TagItemViewHolder extends RecyclerView.ViewHolder {
    private boolean isImageTag;

    private TextView tagName;
    private ImageButton deleteTag;
    private LinearLayout itemTagContainer;

    public TagItemViewHolder(@NonNull View itemView, boolean isImageTag) {
        super(itemView);
        this.isImageTag = isImageTag;
        tagName = itemView.findViewById(R.id.item_tag_name);
        deleteTag = itemView.findViewById(R.id.button_item_tag_delete);
        itemTagContainer = itemView.findViewById(R.id.item_tag_container);

    }

    public void onBind(String tag, BasePresenter presenter) {
        tagName.setText(tag);
        deleteTag.setOnClickListener(l -> {
            if (isImageTag) {
                ((PhotoViewPresenter) presenter).deleteImageTag(tag);
            } else {
                ((UserTagsPresenter) presenter).deleteTag(tag);
            }
        });
        if (isImageTag) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            itemTagContainer.setLayoutParams(layoutParams);
        } else {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            itemTagContainer.setLayoutParams(layoutParams);
        }
    }
}
