package com.netcracker_study_autumn_2020.presentation.ui.viewholder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.netcracker_study_autumn_2020.presentation.R;

public class TagItemViewHolder extends RecyclerView.ViewHolder {
    private TextView tagName;
    private ImageButton deleteTag;

    public TagItemViewHolder(@NonNull View itemView) {
        super(itemView);
        tagName = itemView.findViewById(R.id.item_tag_name);
        deleteTag = itemView.findViewById(R.id.button_item_tag_delete);

    }

    public void onBind(String tag) {
        tagName.setText(tag);
        deleteTag.setOnClickListener(l -> {

        });
    }
}
