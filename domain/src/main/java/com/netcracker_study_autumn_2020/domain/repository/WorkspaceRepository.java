package com.netcracker_study_autumn_2020.domain.repository;

import com.netcracker_study_autumn_2020.domain.dto.WorkspaceDto;

import java.util.List;

public interface WorkspaceRepository {

    interface Error{
        void onError(Exception e);
    }

    interface WorkspaceByIdCallback extends Error{
        void onWorkspaceLoaded(WorkspaceDto workspaces);
    }

    interface WorkspaceListCallback extends Error{
        void onWorkspaceListLoaded(List<WorkspaceDto> workspaces);
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

    void getWorkspace(int spaceId, WorkspaceByIdCallback callback);
    void allWorkspaces(int userId, WorkspaceListCallback callback);
    void createWorkspace(WorkspaceDto workspace, WorkspaceCreateCallback callback);
    void deleteWorkspace(int spaceId, WorkspaceDeleteCallback callback);
    void editWorkspace(WorkspaceDto workspace, WorkspaceEditCallback callback);
}
