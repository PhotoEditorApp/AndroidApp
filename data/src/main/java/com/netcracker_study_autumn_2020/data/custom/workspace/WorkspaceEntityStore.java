package com.netcracker_study_autumn_2020.data.custom.workspace;

import com.netcracker_study_autumn_2020.data.entity.WorkspaceEntity;
import com.netcracker_study_autumn_2020.library.data.SpaceAccessType;

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

    void getWorkspace(long spaceId, WorkspaceByIdCallback callback);

    void allWorkspaces(long userId, SpaceAccessType accessType, WorkspaceListCallback callback);

    void createWorkspace(WorkspaceEntity workspace, WorkspaceCreateCallback callback);
    void deleteWorkspace(long spaceId, WorkspaceDeleteCallback callback);
    void editWorkspace(WorkspaceEntity workspace, WorkspaceEditCallback callback);
}
