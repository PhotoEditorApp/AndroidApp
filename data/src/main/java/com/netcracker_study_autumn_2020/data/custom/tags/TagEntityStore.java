package com.netcracker_study_autumn_2020.data.custom.tags;

import com.netcracker_study_autumn_2020.data.entity.TagEntity;

import java.util.List;

public interface TagEntityStore {
    interface Error {
        void onError(Exception e);
    }

    interface UserTagByIdCallback extends TagEntityStore.Error {
        void onUserTagLoaded(List<TagEntity> userTags);
    }

    interface ImageTagByIdCallback extends TagEntityStore.Error {
        void onImageTagLoaded(List<String> imageTags);
    }

    interface ImageTagCreateCallback extends TagEntityStore.Error {
        void onImageTagCreated();
    }

    interface UserTagCreateCallback extends TagEntityStore.Error {
        void onUserTagCreated();
    }

    interface ImageTagDeleteCallback extends TagEntityStore.Error {
        void onImageTagDeleted();
    }

    interface UserTagDeleteCallback extends TagEntityStore.Error {
        void onUserTagDeleted();
    }

    void getUserTags(long userId, UserTagByIdCallback callback);

    void getImageTags(long imageId, ImageTagByIdCallback callback);

    void addUserTag(long userId, String tagName, TagEntityStore.UserTagCreateCallback
            callback);

    void addImageTag(long userId, long imageId, String tagName,
                     ImageTagCreateCallback callback);

    void deleteUserTag(long userId, String tagName, UserTagDeleteCallback callback);

    void deleteImageTag(long userId, long imageId, String tagName,
                        ImageTagDeleteCallback callback);


}
