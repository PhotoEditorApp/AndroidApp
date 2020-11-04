package com.netcracker_study_autumn_2020.presentation.ui.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.model.WorkspaceModel;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.WorkspacesPresenter;

public class WorkspaceCardAdapter extends StackAdapter<WorkspaceModel> {
    private WorkspacesPresenter workspacesPresenter;

    public WorkspaceCardAdapter(Context context, WorkspacesPresenter workspacesPresenter) {
        super(context);
        this.workspacesPresenter = workspacesPresenter;
    }

    @Override
    public void bindView(WorkspaceModel data, int position, CardStackView.ViewHolder holder) {
        if (holder instanceof ColorItemViewHolder) {
            ColorItemViewHolder h = (ColorItemViewHolder) holder;
            h.onBind(data, position);
            h.buttonOpen.setOnClickListener(l -> {
                workspacesPresenter.navigateToPhotosScreen(data);
            });
            h.buttonDelete.setOnClickListener(l -> {
                workspacesPresenter.deleteWorkspace(data);
            });
        }
    }

    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view;
        view = getLayoutInflater().inflate(R.layout.card_workspace, parent, false);
        return new ColorItemViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.card_workspace;
    }

    static class ColorItemViewHolder extends CardStackView.ViewHolder {
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
            mLayout.getBackground().setColorFilter(data.getColor(), PorterDuff.Mode.SRC_IN);
            cardNumber.setText(String.valueOf(position + 1));
            cardTitle.setText(data.getDescription());
            cardDescription.setText(data.getDescription());
            cardModifiedTime.setText(data.getLastModified().toString());
            cardCreationTime.setText(data.getCreationTime().toString());
        }

    }



}
