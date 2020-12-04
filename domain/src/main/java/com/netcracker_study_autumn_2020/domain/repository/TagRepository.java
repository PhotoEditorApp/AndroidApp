package com.netcracker_study_autumn_2020.domain.repository;

import java.util.List;

public interface TagRepository {
    interface Error {
        void onError(Exception e);
    }

    interface ImageTagsGetCallback extends TagRepository.Error {
        void onImageTagsLoaded(List<String> tags);
    }

    interface UserTagsGetCallback extends TagRepository.Error {
        void onUserTagsLoaded(List<String> tags);
    }

    interface UserTagCreateCallback extends TagRepository.Error {
        void onUserTagCreated();
    }

    interface ImageTagCreateCallback extends TagRepository.Error {
        void onImageTagCreated();
    }

    interface UserTagDeleteCallback extends TagRepository.Error {
        void onUserTagDeleted();
    }

    interface ImageTagDeleteCallback extends TagRepository.Error {
        void onImageTagDeleted();
    }

    void getUserTags(long userId, UserTagsGetCallback callback);

    void getImageTags(long imageId, ImageTagsGetCallback callback);

    void addUserTag(long userId, String tagName, UserTagCreateCallback
            callback);

    void addImageTag(long userId, long imageId, String tagName,
                     ImageTagCreateCallback callback);

    void deleteUserTag(long userId, String tagName, UserTagDeleteCallback callback);

    void deleteImageTag(long userId, long imageId, String tagName,
                        ImageTagDeleteCallback callback);


}
