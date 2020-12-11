package com.netcracker_study_autumn_2020.presentation.ui.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.model.EditorItemModel;

public class EditorItemViewHolder extends RecyclerView.ViewHolder {

    private ImageView editorItemPreview;
    private TextView editorItemTitle;

    public EditorItemViewHolder(@NonNull View itemView) {
        super(itemView);
        editorItemPreview = itemView.findViewById(R.id.editor_item_preview);
        editorItemTitle = itemView.findViewById(R.id.editor_item_title);
    }

    public void onBind(EditorItemModel editorItemModel) {
        switch (editorItemModel.getItemType()) {
            case FRAME:
                editorItemTitle.setText(editorItemModel.getTitle());
                //Set preview using Picasso
                break;
            case FILTER:
                editorItemTitle.setText(editorItemModel.getTitle());
                editorItemPreview.setImageResource(editorItemModel.getImageResourceId());
                break;
        }
    }
}
