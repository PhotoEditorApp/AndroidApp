package com.netcracker_study_autumn_2020.data.custom.space.access;

import com.netcracker_study_autumn_2020.data.entity.SpaceAccessEntity;

public interface SpaceAccessEntityStore {
    interface Error {
        void onError(Exception e);
    }

    interface SpaceAccessCreateCallback extends SpaceAccessEntityStore.Error {
        void onSpaceAccessCreated();
    }

    interface SpaceAccessDeleteCallback extends SpaceAccessEntityStore.Error {
        void onSpaceAccessDeleted();
    }

    void createSpaceAccess(SpaceAccessEntity spaceAccessEntity,
                           SpaceAccessEntityStore.SpaceAccessCreateCallback callback);

    void deleteSpaceAccess(long spaceAccessId,
                           SpaceAccessEntityStore.SpaceAccessDeleteCallback callback);
}
