package com.netcracker_study_autumn_2020.data.custom.workspace;

import com.netcracker_study_autumn_2020.data.entity.WorkspaceEntity;

public class RetrofitWorkspaceEntityStore implements WorkspaceEntityStore {
    @Override
    public void getWorkspace(int userId, int spaceId, WorkspaceByIdCallback callback) {

    }

    @Override
    public void allWorkspaces(int userId, WorkspaceListCallback callback) {

    }

    @Override
    public void createWorkspace(WorkspaceEntity workspace, WorkspaceCreateCallback callback) {

    }

    @Override
    public void deleteWorkspace(int userId, int spaceId, WorkspaceDeleteCallback callback) {

    }


    @Override
    public void editWorkspace(WorkspaceEntity workspace, WorkspaceEditCallback callback) {

    }
}
