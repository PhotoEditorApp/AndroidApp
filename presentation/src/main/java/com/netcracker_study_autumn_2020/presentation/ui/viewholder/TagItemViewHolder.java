package com.netcracker_study_autumn_2020.presentation.ui.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.BasePresenter;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.PhotoViewPresenter;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.UserTagsPresenter;
import com.netcracker_study_autumn_2020.presentation.ui.adapter.TagsAdapter;

public class TagItemViewHolder extends RecyclerView.ViewHolder {
    private TagsAdapter.TagsAdapterType tagsAdapterType;

    private TextView tagName;
    private ImageButton deleteTag;
    private LinearLayout itemTagContainer;

    private EditText outerTextField;

    public TagItemViewHolder(@NonNull View itemView,
                             TagsAdapter.TagsAdapterType isImageTag) {
        super(itemView);
        this.tagsAdapterType = isImageTag;
        tagName = itemView.findViewById(R.id.item_tag_name);
        deleteTag = itemView.findViewById(R.id.button_item_tag_delete);
        itemTagContainer = itemView.findViewById(R.id.item_tag_container);

    }

    public TagItemViewHolder(@NonNull View itemView,
                             TagsAdapter.TagsAdapterType isImageTag,
                             EditText outerTextField) {
        super(itemView);
        this.tagsAdapterType = isImageTag;
        this.outerTextField = outerTextField;
        tagName = itemView.findViewById(R.id.item_tag_name);
        deleteTag = itemView.findViewById(R.id.button_item_tag_delete);
        itemTagContainer = itemView.findViewById(R.id.item_tag_container);

    }

    public void onBind(String tag, BasePresenter presenter) {
        tagName.setText(tag);
        if (tagsAdapterType != TagsAdapter.TagsAdapterType.USER_TAGS_DIALOG) {
            deleteTag.setOnClickListener(l -> {
                if (tagsAdapterType == TagsAdapter.TagsAdapterType.IMAGE_TAGS) {
                    ((PhotoViewPresenter) presenter).deleteImageTag(tag);
                } else {
                    ((UserTagsPresenter) presenter).deleteTag(tag);
                }
            });
        } else {
            deleteTag.setVisibility(View.GONE);
            itemTagContainer.setOnClickListener(l -> {
                outerTextField.setText(tag);
            });
        }

        if (tagsAdapterType == TagsAdapter.TagsAdapterType.IMAGE_TAGS) {
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
