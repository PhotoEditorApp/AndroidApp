package com.netcracker_study_autumn_2020.data.custom.workspace;

import com.netcracker_study_autumn_2020.data.entity.WorkspaceEntity;

import java.util.List;

public interface WorkspaceEntityStore {

    interface Error{
        void onError(Exception e);
    }

    interface WorkspaceByIdCallback extends Error{
        void onWorkspaceLoaded(WorkspaceEntity workspaceEntity);
    }

    interface WorkspaceListCallback extends Error{
        void onWorkspaceListLoaded(List<WorkspaceEntity> workspaceEntities);
    }

    interface WorkspaceCreateCallback extends Error{
        void onWorkspaceCreated();
    }

    interface WorkspaceDeleteCallback extends Error{
        void onWorkspaceDeleted();
    }

    interface WorkspaceEditCallback extends Error{
        void onWorkspaceEdited();
    }

    void getWorkspace(int userId, int spaceId, WorkspaceByIdCallback callback);
    void allWorkspaces(int userId, WorkspaceListCallback callback);
    void createWorkspace(WorkspaceEntity workspace, WorkspaceCreateCallback callback);
    void deleteWorkspace(int userId, int spaceId, WorkspaceDeleteCallback callback);
    void editWorkspace(WorkspaceEntity workspace, WorkspaceEditCallback callback);
}
