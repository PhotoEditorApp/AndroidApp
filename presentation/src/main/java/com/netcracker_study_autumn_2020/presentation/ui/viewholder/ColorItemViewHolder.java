package com.netcracker_study_autumn_2020.presentation.ui.viewholder;

import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.loopeer.cardstack.CardStackView;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.model.WorkspaceModel;

public class ColorItemViewHolder extends CardStackView.ViewHolder {
    View mLayout;
    View mContainerContent;
    TextView cardTitle;
    TextView cardDescription;
    TextView cardCreationTime;
    TextView cardModifiedTime;
    TextView cardNumber;
    MaterialButton buttonOpen;
    MaterialButton buttonDelete;

    public ColorItemViewHolder(View view) {
        super(view);
        mLayout = view.findViewById(R.id.frame_list_card_item);
        mContainerContent = view.findViewById(R.id.container_list_content);
        cardNumber = view.findViewById(R.id.workspace_card_number);
        cardTitle = view.findViewById(R.id.workspace_card_title);
        cardDescription = view.findViewById(R.id.workspace_card_description);
        cardCreationTime = view.findViewById(R.id.workspace_card_creation_time);
        cardModifiedTime = view.findViewById(R.id.workspace_card_modified_time);
        buttonOpen = view.findViewById(R.id.button_open_workspace);
        buttonDelete = view.findViewById(R.id.button_delete_workspace);
    }

    @Override
    public void onItemExpand(boolean b) {
        mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    public void onBind(WorkspaceModel data, int position) {
        Log.d("COLORITEM", position + data.getName());
        mLayout.getBackground().setColorFilter(data.getColor(), PorterDuff.Mode.SRC_IN);
        cardNumber.setText(String.valueOf(position + 1));
        cardTitle.setText(data.getName());
        cardDescription.setText(data.getDescription());
        cardModifiedTime.setText(data.getModificationTime().toString());
        cardCreationTime.setText(data.getCreationTime().toString());
    }

    public MaterialButton getButtonDelete() {
        return buttonDelete;
    }

    public MaterialButton getButtonOpen() {
        return buttonOpen;
    }
}