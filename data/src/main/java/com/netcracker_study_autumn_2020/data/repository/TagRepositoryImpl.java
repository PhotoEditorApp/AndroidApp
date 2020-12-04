package com.netcracker_study_autumn_2020.data.repository;

import android.util.Log;

import com.netcracker_study_autumn_2020.data.custom.tags.TagEntityStore;
import com.netcracker_study_autumn_2020.data.custom.tags.TagEntityStoreFactory;
import com.netcracker_study_autumn_2020.data.entity.TagEntity;
import com.netcracker_study_autumn_2020.domain.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;

public class TagRepositoryImpl implements TagRepository {

    private static TagRepositoryImpl INSTANCE;


    public static synchronized TagRepositoryImpl getInstance(
            TagEntityStoreFactory tagEntityStoreFactory) {
        if (INSTANCE == null) {
            INSTANCE = new TagRepositoryImpl(tagEntityStoreFactory);
        }
        return INSTANCE;
    }

    private TagEntityStoreFactory tagEntityStoreFactory;

    public TagRepositoryImpl(TagEntityStoreFactory tagEntityStoreFactory) {
        this.tagEntityStoreFactory = tagEntityStoreFactory;
    }

    @Override
    public void getUserTags(long userId, UserTagsGetCallback callback) {
        TagEntityStore tagEntityStore = tagEntityStoreFactory.create();
        Log.d("TAG_REPO", "DEBUG");
        tagEntityStore.getUserTags(userId, new TagEntityStore.UserTagByIdCallback() {
            @Override
            public void onUserTagLoaded(List<TagEntity> userTags) {
                List<String> nameBuffer = new ArrayList<>();
                for (TagEntity t : userTags) {
                    nameBuffer.add(t.getName());
                }
                callback.onUserTagsLoaded(nameBuffer);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void getImageTags(long imageId, ImageTagsGetCallback callback) {
        TagEntityStore tagEntityStore = tagEntityStoreFactory.create();

        tagEntityStore.getImageTags(imageId, new TagEntityStore.ImageTagByIdCallback() {
            @Override
            public void onImageTagLoaded(List<String> imageTags) {
                //List<String> nameBuffer = new ArrayList<>();
                //for (TagEntity t : imageTags) {
                //nameBuffer.add(t.getName());
                //}
                callback.onImageTagsLoaded(imageTags);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void addUserTag(long userId, String tagName, UserTagCreateCallback callback) {
        TagEntityStore tagEntityStore = tagEntityStoreFactory.create();

        tagEntityStore.addUserTag(userId, tagName, new TagEntityStore.UserTagCreateCallback() {
            @Override
            public void onUserTagCreated() {
                callback.onUserTagCreated();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void addImageTag(long userId, long imageId, String tagName, ImageTagCreateCallback callback) {
        TagEntityStore tagEntityStore = tagEntityStoreFactory.create();

        tagEntityStore.addImageTag(userId, imageId, tagName, new TagEntityStore.ImageTagCreateCallback() {
            @Override
            public void onImageTagCreated() {
                callback.onImageTagCreated();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void deleteUserTag(long userId, String tagName, UserTagDeleteCallback callback) {
        TagEntityStore tagEntityStore = tagEntityStoreFactory.create();

        tagEntityStore.deleteUserTag(userId, tagName, new TagEntityStore.UserTagDeleteCallback() {
            @Override
            public void onUserTagDeleted() {
                callback.onUserTagDeleted();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void deleteImageTag(long userId, long imageId, String tagName, ImageTagDeleteCallback callback) {
        TagEntityStore tagEntityStore = tagEntityStoreFactory.create();

        tagEntityStore.deleteImageTag(userId, imageId, tagName, new TagEntityStore.ImageTagDeleteCallback() {
            @Override
            public void onImageTagDeleted() {
                callback.onImageTagDeleted();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }
}
