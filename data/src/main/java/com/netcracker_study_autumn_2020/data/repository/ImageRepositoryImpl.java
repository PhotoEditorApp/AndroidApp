package com.netcracker_study_autumn_2020.data.repository;

import com.netcracker_study_autumn_2020.data.custom.image.ImageEntityStore;
import com.netcracker_study_autumn_2020.data.custom.image.ImageEntityStoreFactory;
import com.netcracker_study_autumn_2020.data.entity.ImageEntity;
import com.netcracker_study_autumn_2020.data.mapper.ImageEntityDtoMapper;
import com.netcracker_study_autumn_2020.domain.dto.ImageDto;
import com.netcracker_study_autumn_2020.domain.repository.ImageRepository;

import java.util.List;

public class ImageRepositoryImpl implements ImageRepository {

    private static ImageRepositoryImpl INSTANCE;


    public static synchronized ImageRepositoryImpl getInstance(
            ImageEntityStoreFactory imageEntityStoreFactory,
            ImageEntityDtoMapper imageEntityDtoMapper) {
        if (INSTANCE == null) {
            INSTANCE = new ImageRepositoryImpl(imageEntityDtoMapper,
                    imageEntityStoreFactory);
        }
        return INSTANCE;
    }

    private ImageEntityDtoMapper imageEntityDtoMapper;
    private ImageEntityStoreFactory imageEntityStoreFactory;

    public ImageRepositoryImpl(ImageEntityDtoMapper imageEntityDtoMapper,
                               ImageEntityStoreFactory imageEntityStoreFactory) {
        this.imageEntityDtoMapper = imageEntityDtoMapper;
        this.imageEntityStoreFactory = imageEntityStoreFactory;
    }


    @Override
    public void getImageBySpaceId(long spaceId, ImageBySpaceIdCallback callback) {
        ImageEntityStore imageEntityStore = imageEntityStoreFactory.create();

        imageEntityStore.getImagesBySpaceId(spaceId, new ImageEntityStore.ImagesBySpaceIdCallback() {
            @Override
            public void onImagesLoaded(List<ImageEntity> imageEntityList) {
                List<ImageDto> imageDtos = imageEntityDtoMapper.map2(imageEntityList);
                callback.onImagesLoaded(imageDtos);
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }
}
