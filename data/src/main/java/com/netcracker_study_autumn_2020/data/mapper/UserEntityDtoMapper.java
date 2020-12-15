package com.netcracker_study_autumn_2020.data.mapper;

import com.netcracker_study_autumn_2020.data.entity.UserEntity;
import com.netcracker_study_autumn_2020.domain.dto.UserDto;
import com.netcracker_study_autumn_2020.library.data.BaseMapper;

public class UserEntityDtoMapper extends BaseMapper<UserEntity, UserDto> {
    @Override
    public UserEntity map1(UserDto o2) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(o2.getUser_id());
        userEntity.setEmail(o2.getEmail());
        userEntity.setFirstName(o2.getFirstName());
        userEntity.setLastName(o2.getLastName());
        userEntity.setFullName(o2.getFullName());
        userEntity.setAcceptTermsOfService(o2.isAcceptTermsOfService());
        return userEntity;
    }

    @Override
    public UserDto map2(UserEntity o1) {
        UserDto userDto = new UserDto();
        userDto.setUser_id(o1.getId());
        userDto.setEmail(o1.getEmail());
        userDto.setFirstName(o1.getFirstName() == null ? "FirstName" : o1.getFirstName());
        userDto.setLastName(o1.getLastName() == null ? "LastName" : o1.getLastName());
        userDto.setFullName(o1.getFullName() == null ? "FullName" : o1.getFullName());
        userDto.setAcceptTermsOfService(o1.isAcceptTermsOfService());
        return userDto;
    }
}
