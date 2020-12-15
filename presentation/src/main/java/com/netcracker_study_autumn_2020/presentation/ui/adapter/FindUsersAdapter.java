package com.netcracker_study_autumn_2020.presentation.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.model.UserModel;
import com.netcracker_study_autumn_2020.presentation.mvp.presenter.FindAndSharePresenter;
import com.netcracker_study_autumn_2020.presentation.ui.viewholder.FindUserPreviewViewHolder;

import java.util.ArrayList;
import java.util.List;

public class FindUsersAdapter extends RecyclerView.Adapter<FindUserPreviewViewHolder> {

    private List<UserModel> foundUsersList;
    private FindAndSharePresenter presenter;

    public FindUsersAdapter(FindAndSharePresenter findAndSharePresenter) {
        this.presenter = findAndSharePresenter;
        foundUsersList = new ArrayList<>();
    }

    @NonNull
    @Override
    public FindUserPreviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FindUserPreviewViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_find_user_preview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FindUserPreviewViewHolder holder, int position) {
        holder.onBind(foundUsersList.get(position), holder.itemView.getContext());
        holder.itemView.setOnClickListener(l -> {
            holder.onTap();
            presenter.setChosenUserId(holder.getAssignedUserId());
        });
    }

    @Override
    public int getItemCount() {
        return foundUsersList.size();
    }

    public void setFoundUsersList(List<UserModel> foundUsersList) {
        this.foundUsersList.clear();
        this.foundUsersList.addAll(foundUsersList);
        notifyDataSetChanged();
    }
}
