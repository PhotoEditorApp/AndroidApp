package com.netcracker_study_autumn_2020.presentation.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.loopeer.cardstack.CardStackView;
import com.netcracker_study_autumn_2020.presentation.R;
import com.netcracker_study_autumn_2020.presentation.mvp.model.Workspace;
import com.netcracker_study_autumn_2020.presentation.ui.adapter.WorkspaceCardAdapter;

import java.util.ArrayList;
import java.util.List;

public class WorkspacesFragment extends BaseFragment implements CardStackView.ItemExpendListener {
    @Override
    void initializePresenter() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workspaces, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Workspace> workspaces = new ArrayList<>();
        workspaces.add(new Workspace(1));
        workspaces.add(new Workspace(2));
        workspaces.add(new Workspace(3));
        workspaces.add(new Workspace(4));
        workspaces.add(new Workspace(5));
        workspaces.add(new Workspace(6));
        workspaces.add(new Workspace(7));
        workspaces.add(new Workspace(8));
        workspaces.add(new Workspace(9));
        workspaces.add(new Workspace(10));
        workspaces.add(new Workspace(11));
        workspaces.add(new Workspace(12));
        workspaces.add(new Workspace(13));
        workspaces.add(new Workspace(14));
        CardStackView cardStackView = view.findViewById(R.id.workspaces_cards);
        cardStackView.setItemExpendListener(this);

        WorkspaceCardAdapter workspaceCardAdapter = new WorkspaceCardAdapter(getActivity());
        workspaceCardAdapter.updateData(workspaces);
        cardStackView.setAdapter(workspaceCardAdapter);




    }

    @Override
    public void onItemExpend(boolean expend) {

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
