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
    private View mLayout;
    private View mContainerContent;
    private TextView cardTitle;
    private TextView cardDescription;
    private TextView cardCreationTime;
    private TextView cardModifiedTime;
    private TextView cardNumber;
    private MaterialButton buttonShare;
    private MaterialButton buttonOpen;
    private MaterialButton buttonDelete;

    public ColorItemViewHolder(View view) {
        super(view);
        mLayout = view.findViewById(R.id.frame_list_card_item);
        mContainerContent = view.findViewById(R.id.container_list_content);
        cardNumber = view.findViewById(R.id.workspace_card_number);
        cardTitle = view.findViewById(R.id.workspace_card_title);
        cardDescription = view.findViewById(R.id.workspace_card_description);
        cardCreationTime = view.findViewById(R.id.workspace_card_creation_time);
        cardModifiedTime = view.findViewById(R.id.workspace_card_modified_time);
        buttonShare = view.findViewById(R.id.button_share_workspace);
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
        String title = data.getName();
        //if (title.length() > LEN_TO_MAKE_FONT_SMALLER_1) {
        //if (title.length() > LEN_TO_MAKE_FONT_SMALLER_2) {
        // if(title.length() > LEN_TO_MAKE_FONT_SMALLER_3) {
        //cardTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        //} else {
        // cardTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        //}
        // }else {
        //cardTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        //}
        //}
        cardTitle.setText(title);
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

    public MaterialButton getButtonShare() {
        return buttonShare;
    }
}