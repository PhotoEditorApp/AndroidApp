package com.netcracker_study_autumn_2020.data.mapper;

import android.annotation.SuppressLint;
import android.util.Log;

import com.netcracker_study_autumn_2020.data.entity.WorkspaceEntity;
import com.netcracker_study_autumn_2020.domain.dto.WorkspaceDto;
import com.netcracker_study_autumn_2020.library.data.BaseMapper;
import com.netcracker_study_autumn_2020.library.network.NetworkUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class WorkspaceEntityDtoMapper extends BaseMapper<WorkspaceEntity, WorkspaceDto> {
    @Override
    public WorkspaceEntity map1(WorkspaceDto o2) {
        WorkspaceEntity workspaceEntity = new WorkspaceEntity();
        workspaceEntity.setId(o2.getId());
        workspaceEntity.setOwnerId(o2.getOwnerId());
        workspaceEntity.setName(o2.getName());
        workspaceEntity.setDescription(o2.getDescription());
        workspaceEntity.setCreatedTime(o2.getCreationTime().toString());
        workspaceEntity.setModifiedTime(o2.getModificationTime().toString());
        workspaceEntity.setColor(o2.getColor());

        return workspaceEntity;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public WorkspaceDto map2(WorkspaceEntity o1) {
        WorkspaceDto workspaceDto = new WorkspaceDto();
        workspaceDto.setId(o1.getId());
        workspaceDto.setOwnerId(o1.getOwnerId());
        workspaceDto.setName(o1.getName());
        workspaceDto.setDescription(o1.getDescription());
        try {
            Log.d("MAPPER", "map2: " + o1.getCreatedTime());
            workspaceDto.setCreationTime(new SimpleDateFormat(NetworkUtils.DATE_PATTERN)
                    .parse(o1.getCreatedTime()));
            workspaceDto.setModificationTime(new SimpleDateFormat(NetworkUtils.DATE_PATTERN)
                    .parse(o1.getModifiedTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        workspaceDto.setColor(o1.getColor());

        return workspaceDto;
    }
}
