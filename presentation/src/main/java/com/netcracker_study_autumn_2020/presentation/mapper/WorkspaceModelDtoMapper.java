package com.netcracker_study_autumn_2020.presentation.mapper;

import com.netcracker_study_autumn_2020.domain.dto.WorkspaceDto;
import com.netcracker_study_autumn_2020.library.data.BaseMapper;
import com.netcracker_study_autumn_2020.presentation.mvp.model.WorkspaceModel;

public class WorkspaceModelDtoMapper extends BaseMapper<WorkspaceModel, WorkspaceDto> {
    @Override
    public WorkspaceModel map1(WorkspaceDto o2) {
        WorkspaceModel workspaceModel = new WorkspaceModel();
        workspaceModel.setId(o2.getId());
        workspaceModel.setOwnerId(o2.getOwnerId());
        workspaceModel.setName(o2.getName());
        workspaceModel.setDescription(o2.getDescription());
        workspaceModel.setCreationTime(o2.getCreationTime());
        workspaceModel.setModificationTime(o2.getModificationTime());
        workspaceModel.setColor(o2.getColor());

        return workspaceModel;
    }

    @Override
    public WorkspaceDto map2(WorkspaceModel o1) {
        WorkspaceDto workspaceDto = new WorkspaceDto();
        workspaceDto.setId(o1.getId());
        workspaceDto.setOwnerId(o1.getOwnerId());
        workspaceDto.setName(o1.getName());
        workspaceDto.setDescription(o1.getDescription());
        workspaceDto.setCreationTime(o1.getCreationTime());
        workspaceDto.setModificationTime(o1.getModificationTime());
        workspaceDto.setColor(o1.getColor());

        return workspaceDto;
    }
}
