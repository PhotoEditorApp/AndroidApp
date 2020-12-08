package com.netcracker_study_autumn_2020.presentation.mapper;

import com.netcracker_study_autumn_2020.domain.dto.ImageDto;
import com.netcracker_study_autumn_2020.library.data.BaseMapper;
import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;

public class ImageModelDtoMapper extends BaseMapper<ImageModel, ImageDto> {
    @Override
    public ImageModel map1(ImageDto o2) {
        ImageModel imageModel = new ImageModel();
        imageModel.setId(o2.getId());
        imageModel.setName(o2.getName());
        imageModel.setSize(o2.getSize());
        imageModel.setRating(o2.getRating());
        imageModel.setCreateTime(o2.getCreateTime());
        imageModel.setModifiedTime(o2.getModifiedTime());
        imageModel.setUserId(o2.getUserId());
        imageModel.setAverageColor(o2.getAverageColor());
        imageModel.setPath(o2.getPath());
        imageModel.setPreviewPath(o2.getPreviewPath());

        return imageModel;
    }

    @Override
    public ImageDto map2(ImageModel o1) {
        ImageDto imageDto = new ImageDto();
        imageDto.setId(o1.getId());
        imageDto.setName(o1.getName());
        imageDto.setSize(o1.getSize());
        imageDto.setRating(o1.getRating());
        imageDto.setCreateTime(o1.getCreateTime());
        imageDto.setModifiedTime(o1.getModifiedTime());
        imageDto.setUserId(o1.getUserId());
        imageDto.setAverageColor(o1.getAverageColor());
        imageDto.setPath(o1.getPath());
        imageDto.setPreviewPath(o1.getPreviewPath());

        return imageDto;
    }
}
