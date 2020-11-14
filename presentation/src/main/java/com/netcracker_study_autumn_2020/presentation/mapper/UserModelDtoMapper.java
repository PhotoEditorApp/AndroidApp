package com.netcracker_study_autumn_2020.presentation.mapper;

import com.netcracker_study_autumn_2020.domain.dto.UserDto;
import com.netcracker_study_autumn_2020.library.data.BaseMapper;
import com.netcracker_study_autumn_2020.presentation.mvp.model.UserModel;

public class UserModelDtoMapper extends BaseMapper<UserModel, UserDto> {
    @Override
    public UserModel map1(UserDto o2) {

        UserModel userModel = new UserModel();
        userModel.setUser_id(o2.getUser_id());
        userModel.setEmail(o2.getEmail());
        userModel.setFullName(o2.getFullName());
        userModel.setFirstName(o2.getFirstName());
        userModel.setLastName(o2.getLastName());

        return userModel;
    }

    @Override
    public UserDto map2(UserModel o1) {
        UserDto userDto = new UserDto();
        userDto.setUser_id(o1.getUser_id());
        userDto.setEmail(o1.getEmail());
        userDto.setFullName(o1.getFullName());
        userDto.setFirstName(o1.getFirstName());
        userDto.setLastName(o1.getLastName());

        return userDto;
    }
}
