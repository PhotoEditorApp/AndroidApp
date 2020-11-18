package com.netcracker_study_autumn_2020.domain.repository;

import com.netcracker_study_autumn_2020.domain.dto.SpaceAccessDto;

public interface SpaceAccessRepository {

    interface Error {
        void onError(Exception e);
    }

    interface SpaceAccessCreateCallback extends SpaceAccessRepository.Error {
        void onSpaceAccessCreated();
    }

    interface SpaceAccessDeleteCallback extends SpaceAccessRepository.Error {
        void onSpaceAccessDeleted();
    }

    void createSpaceAccess(SpaceAccessDto spaceAccessDto,
                           SpaceAccessRepository.SpaceAccessCreateCallback callback);

    void deleteSpaceAccess(long spaceAccessId,
                           SpaceAccessRepository.SpaceAccessDeleteCallback callback);
}
