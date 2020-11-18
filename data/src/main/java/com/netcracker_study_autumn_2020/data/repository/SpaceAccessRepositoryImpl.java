package com.netcracker_study_autumn_2020.data.repository;

import com.netcracker_study_autumn_2020.data.custom.space.access.SpaceAccessEntityStore;
import com.netcracker_study_autumn_2020.data.custom.space.access.SpaceAccessEntityStoreFactory;
import com.netcracker_study_autumn_2020.data.entity.SpaceAccessEntity;
import com.netcracker_study_autumn_2020.data.mapper.SpaceAccessEntityDtoMapper;
import com.netcracker_study_autumn_2020.domain.dto.SpaceAccessDto;
import com.netcracker_study_autumn_2020.domain.repository.SpaceAccessRepository;

public class SpaceAccessRepositoryImpl implements SpaceAccessRepository {

    private static SpaceAccessRepositoryImpl INSTANCE;

    public static synchronized SpaceAccessRepositoryImpl getInstance(
            SpaceAccessEntityStoreFactory spaceAccessEntityStoreFactory,
            SpaceAccessEntityDtoMapper spaceAccessEntityDtoMapper) {
        if (INSTANCE == null) {
            INSTANCE = new SpaceAccessRepositoryImpl(spaceAccessEntityDtoMapper,
                    spaceAccessEntityStoreFactory);
        }
        return INSTANCE;
    }

    private SpaceAccessEntityDtoMapper spaceAccessEntityDtoMapper;
    private SpaceAccessEntityStoreFactory spaceAccessEntityStoreFactory;

    public SpaceAccessRepositoryImpl(SpaceAccessEntityDtoMapper spaceAccessEntityDtoMapper,
                                     SpaceAccessEntityStoreFactory spaceAccessEntityStoreFactory) {
        this.spaceAccessEntityDtoMapper = spaceAccessEntityDtoMapper;
        this.spaceAccessEntityStoreFactory = spaceAccessEntityStoreFactory;
    }


    @Override
    public void createSpaceAccess(SpaceAccessDto spaceAccessDto, SpaceAccessCreateCallback callback) {
        SpaceAccessEntityStore spaceAccessEntityStore = spaceAccessEntityStoreFactory.create();
        SpaceAccessEntity spaceAccessEntity = spaceAccessEntityDtoMapper.map1(spaceAccessDto);
        spaceAccessEntityStore.createSpaceAccess(spaceAccessEntity,
                new SpaceAccessEntityStore.SpaceAccessCreateCallback() {
                    @Override
                    public void onSpaceAccessCreated() {
                        callback.onSpaceAccessCreated();
                    }

                    @Override
                    public void onError(Exception e) {
                        callback.onError(e);
                    }
                });
    }

    @Override
    public void deleteSpaceAccess(long spaceAccessId, SpaceAccessDeleteCallback callback) {
        SpaceAccessEntityStore spaceAccessEntityStore = spaceAccessEntityStoreFactory.create();
        spaceAccessEntityStore.deleteSpaceAccess(spaceAccessId, new SpaceAccessEntityStore.SpaceAccessDeleteCallback() {
            @Override
            public void onSpaceAccessDeleted() {
                callback.onSpaceAccessDeleted();
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }
}
