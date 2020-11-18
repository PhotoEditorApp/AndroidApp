package com.netcracker_study_autumn_2020.data.custom.space.access;

public class SpaceAccessEntityStoreFactory {

    public SpaceAccessEntityStore create() {
        SpaceAccessEntityStore spaceAccessEntityStore;

        spaceAccessEntityStore = new RetrofitSpaceAccessEntityStore();
        return spaceAccessEntityStore;
    }

}
