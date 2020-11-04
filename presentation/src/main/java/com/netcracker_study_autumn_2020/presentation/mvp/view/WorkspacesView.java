package com.netcracker_study_autumn_2020.presentation.mvp.view;

import com.netcracker_study_autumn_2020.presentation.mvp.model.WorkspaceModel;

public interface WorkspacesView {

    void renderWorkspaces();
    void navigateToPhotosScreen(WorkspaceModel workspaceModel);
}
