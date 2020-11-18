package com.netcracker_study_autumn_2020.data.mapper;

import com.netcracker_study_autumn_2020.data.entity.SpaceAccessEntity;
import com.netcracker_study_autumn_2020.domain.dto.SpaceAccessDto;
import com.netcracker_study_autumn_2020.library.data.BaseMapper;

public class SpaceAccessEntityDtoMapper extends BaseMapper<SpaceAccessEntity,
        SpaceAccessDto> {
    @Override
    public SpaceAccessEntity map1(SpaceAccessDto o2) {
        SpaceAccessEntity spaceAccessEntity = new SpaceAccessEntity();
        spaceAccessEntity.setSpaceId(o2.getSpaceId());
        spaceAccessEntity.setType(o2.getType());
        spaceAccessEntity.setUserId(o2.getUserId());
        return spaceAccessEntity;
    }

    @Override
    public SpaceAccessDto map2(SpaceAccessEntity o1) {
        SpaceAccessDto spaceAccessDto = new SpaceAccessDto();
        spaceAccessDto.setSpaceId(o1.getSpaceId());
        spaceAccessDto.setUserId(o1.getUserId());
        spaceAccessDto.setType(o1.getType());
        return spaceAccessDto;
    }
}
