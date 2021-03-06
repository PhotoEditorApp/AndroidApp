package com.netcracker_study_autumn_2020.data.mapper;

import com.netcracker_study_autumn_2020.data.entity.ImageEntity;
import com.netcracker_study_autumn_2020.domain.dto.ImageDto;
import com.netcracker_study_autumn_2020.library.data.BaseMapper;
import com.netcracker_study_autumn_2020.library.network.NetworkUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageEntityDtoMapper extends BaseMapper<ImageEntity, ImageDto> {
    @Override
    public ImageEntity map1(ImageDto o2) {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setId(o2.getId());
        imageEntity.setName(o2.getName());
        imageEntity.setSize(o2.getSize());
        imageEntity.setRating(o2.getRating());
        imageEntity.setTags(o2.getTags());
        try {
            Date createDate = new SimpleDateFormat(NetworkUtils.DATE_PATTERN_DB)
                    .parse(o2.getCreateTime().toString());
            Date modifiedDate = new SimpleDateFormat(NetworkUtils.DATE_PATTERN_DB)
                    .parse(o2.getModifiedTime().toString());
            imageEntity.setCreateTime(createDate.toString());
            imageEntity.setModifiedTime(modifiedDate.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        imageEntity.setUserId(o2.getUserId());
        imageEntity.setAverageColor(o2.getAverageColor());
        imageEntity.setPath(o2.getPath());
        imageEntity.setPreviewPath(o2.getPreviewPath());

        return imageEntity;
    }

    @Override
    public ImageDto map2(ImageEntity o1) {
        ImageDto imageDto = new ImageDto();
        imageDto.setId(o1.getId());
        imageDto.setName(o1.getName());
        imageDto.setSize(o1.getSize());
        imageDto.setRating(o1.getRating());
        imageDto.setTags(o1.getTags());
        try {
            imageDto.setCreateTime(new SimpleDateFormat(NetworkUtils.DATE_PATTERN_DB)
                    .parse(o1.getCreateTime()));
            imageDto.setModifiedTime(new SimpleDateFormat(NetworkUtils.DATE_PATTERN_DB)
                    .parse(o1.getModifiedTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        imageDto.setUserId(o1.getUserId());
        imageDto.setAverageColor((int) (o1.getAverageColor()));
        imageDto.setPath(o1.getPath());
        imageDto.setPreviewPath(o1.getPreviewPath());

        return imageDto;
    }
}
