package com.netcracker_study_autumn_2020.presentation.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.model.WorkspaceModel;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.WorkspacesPresenter;
import com.netcracker_study_autumn_2020.presentation.ui.viewholder.ColorItemViewHolder;

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

            switch (workspacesPresenter.getCurrentTab()) {
                case VIEWER:
                case EDITOR:
                    h.getButtonShare().setVisibility(View.INVISIBLE);
                    h.getButtonDelete().setText("Покинуть");
                    break;
                case CREATOR:
                    h.getButtonShare().setVisibility(View.VISIBLE);
                    h.getButtonDelete().setText("Удалить");
                    break;

            }
            h.getButtonOpen().setOnClickListener(l -> {
                workspacesPresenter.navigateToPhotosScreen(data);
            });
            h.getButtonDelete().setOnClickListener(l -> {
                workspacesPresenter.deleteWorkspace(data);
                workspacesPresenter.refreshData();
            });
            h.getButtonShare().setOnClickListener(l -> {
                workspacesPresenter.navigateToShareSpace(data.getId());
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
}
