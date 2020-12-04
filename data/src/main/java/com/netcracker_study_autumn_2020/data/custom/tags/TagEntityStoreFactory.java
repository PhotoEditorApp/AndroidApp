package com.netcracker_study_autumn_2020.data.custom.tags;

public class TagEntityStoreFactory {
    public TagEntityStore create() {
        TagEntityStore tagEntityStore;

        tagEntityStore = new RetrofitTagEntityStore();
        return tagEntityStore;
    }
}
