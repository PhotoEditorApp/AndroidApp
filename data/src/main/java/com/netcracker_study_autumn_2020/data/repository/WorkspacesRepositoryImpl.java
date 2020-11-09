package com.netcracker_study_autumn_2020.data.repository;

import com.netcracker_study_autumn_2020.data.custom.workspace.WorkspaceEntityStore;
import com.netcracker_study_autumn_2020.data.custom.workspace.WorkspaceEntityStoreFactory;
import com.netcracker_study_autumn_2020.data.entity.WorkspaceEntity;
import com.netcracker_study_autumn_2020.data.exception.RepositoryException;
import com.netcracker_study_autumn_2020.data.mapper.WorkspaceEntityDtoMapper;
import com.netcracker_study_autumn_2020.domain.dto.WorkspaceDto;
import com.netcracker_study_autumn_2020.domain.repository.WorkspaceRepository;

import java.util.List;

public class WorkspacesRepositoryImpl implements WorkspaceRepository {

    private static WorkspacesRepositoryImpl INSTANCE;

    public static synchronized WorkspacesRepositoryImpl getInstance(
            WorkspaceEntityStoreFactory workspaceEntityStoreFactory,
            WorkspaceEntityDtoMapper workspaceEntityDtoMapper ) {
        if (INSTANCE == null){
            INSTANCE = new WorkspacesRepositoryImpl(workspaceEntityDtoMapper,
                    workspaceEntityStoreFactory);
        }
        return INSTANCE;
    }

    WorkspaceEntityDtoMapper workspaceEntityDtoMapper;
    WorkspaceEntityStoreFactory workspaceEntityStoreFactory;

    WorkspacesRepositoryImpl(WorkspaceEntityDtoMapper workspaceEntityDtoMapper,
                             WorkspaceEntityStoreFactory workspaceEntityStoreFactory){
        this.workspaceEntityDtoMapper = workspaceEntityDtoMapper;
        this.workspaceEntityStoreFactory = workspaceEntityStoreFactory;
    }

    @Override
    public void getWorkspace(long spaceId, WorkspaceByIdCallback callback) {


    }

    @Override
    public void allWorkspaces(long userId, WorkspaceListCallback callback) {
        WorkspaceEntityStore workspaceEntityStore = workspaceEntityStoreFactory.create();

        workspaceEntityStore.allWorkspaces(userId, new WorkspaceEntityStore.WorkspaceListCallback() {
            @Override
            public void onWorkspaceListLoaded(List<WorkspaceEntity> workspaceEntities) {
                List<WorkspaceDto> workspaceDtos = workspaceEntityDtoMapper.map2(workspaceEntities);
                if (workspaceDtos == null){
                    callback.onError(new RepositoryException("WorkspaceRepository: got null instead of List"));
                }else {
                    callback.onWorkspaceListLoaded(workspaceDtos);
                }
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }

    @Override
    public void createWorkspace(WorkspaceDto workspace, WorkspaceCreateCallback callback) {

    }

    @Override
    public void deleteWorkspace(long spaceId, WorkspaceDeleteCallback callback) {

    }

    @Override
    public void editWorkspace(WorkspaceDto workspace, WorkspaceEditCallback callback) {

    }
}
