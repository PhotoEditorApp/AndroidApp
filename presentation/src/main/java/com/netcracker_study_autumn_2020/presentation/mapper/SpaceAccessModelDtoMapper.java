package com.netcracker_study_autumn_2020.presentation.mapper;

import com.netcracker_study_autumn_2020.domain.dto.SpaceAccessDto;
import com.netcracker_study_autumn_2020.library.data.BaseMapper;
import com.netcracker_study_autumn_2020.presentation.mvp.model.SpaceAccessModel;

public class SpaceAccessModelDtoMapper
        extends BaseMapper<SpaceAccessModel, SpaceAccessDto> {
    @Override
    public SpaceAccessModel map1(SpaceAccessDto o2) {
        SpaceAccessModel spaceAccessModel = new SpaceAccessModel();
        spaceAccessModel.setSpaceId(o2.getSpaceId());
        spaceAccessModel.setUserId(o2.getUserId());
        spaceAccessModel.setType(o2.getType());
        return spaceAccessModel;
    }

    @Override
    public SpaceAccessDto map2(SpaceAccessModel o1) {
        SpaceAccessDto spaceAccessDto = new SpaceAccessDto();
        spaceAccessDto.setSpaceId(o1.getSpaceId());
        spaceAccessDto.setUserId(o1.getUserId());
        spaceAccessDto.setType(o1.getType());
        return spaceAccessDto;
    }
}
