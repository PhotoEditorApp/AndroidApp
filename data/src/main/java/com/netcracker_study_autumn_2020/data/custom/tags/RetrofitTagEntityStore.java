package com.netcracker_study_autumn_2020.data.custom.tags;

import android.util.Log;

import com.netcracker_study_autumn_2020.data.custom.services.TagService;
import com.netcracker_study_autumn_2020.data.entity.TagEntity;
import com.netcracker_study_autumn_2020.data.exception.EntityStoreException;
import com.netcracker_study_autumn_2020.data.manager.SessionManager;
import com.netcracker_study_autumn_2020.library.network.NetworkUtils;
import com.netcracker_study_autumn_2020.library.network.UnsafeOkHttpClient;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitTagEntityStore implements TagEntityStore {

    private TagService tagService;

    public RetrofitTagEntityStore() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(UnsafeOkHttpClient.getUnsafeOkHttpClient())
                .baseUrl(NetworkUtils.API_ADDRESS)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        tagService = retrofit.create(TagService.class);
    }

    @Override
    public void getUserTags(long userId, UserTagByIdCallback callback) {
        Response<List<TagEntity>> response;
        try {
            response = tagService.getUserTags(SessionManager.getSessionToken(),
                    userId).execute();
            if (response.body() == null) {
                callback.onError(new EntityStoreException("TAG_ENTITY_STORE getUserTags: code - " +
                        +response.code()));
            } else {
                callback.onUserTagLoaded(response.body());
            }
        } catch (IOException e) {
            callback.onError(e);
        }
        callback.onError(new EntityStoreException());
    }

    @Override
    public void getImageTags(long imageId, ImageTagByIdCallback callback) {
        Response<List<String>> response;
        try {
            response = tagService.getImageTags(SessionManager.getSessionToken(),
                    imageId).execute();
            if (response.body() == null) {
                callback.onError(new EntityStoreException("TAG_ENTITY_STORE getImageTags: code - " +
                        +response.code()));
            } else {
                callback.onImageTagLoaded(response.body());
            }
        } catch (IOException e) {
            callback.onError(e);
        }
        callback.onError(new EntityStoreException());
    }

    @Override
    public void addUserTag(long userId, String tagName, TagEntityStore.UserTagCreateCallback callback) {
        Log.d("ADD_USER_TAG", "from entity store123");
        Response<ResponseBody> response;
        try {
            response = tagService.addUserTag(SessionManager.getSessionToken(),
                    userId, tagName).execute();
            if (response.body() == null) {
                callback.onError(new EntityStoreException("TAG_ENTITY_STORE addUserTags: code - " +
                        +response.code()));
            } else {
                callback.onUserTagCreated();
            }
        } catch (IOException e) {
            callback.onError(e);
        }
        callback.onError(new EntityStoreException());
    }

    @Override
    public void addImageTag(long userId, long imageId, String tagName, ImageTagCreateCallback callback) {
        Response<ResponseBody> response;
        try {
            response = tagService.addImageTag(SessionManager.getSessionToken(),
                    userId, imageId, tagName).execute();
            if (response.body() == null) {
                callback.onError(new EntityStoreException("TAG_ENTITY_STORE addImageTags: code - " +
                        +response.code()));
            } else {
                callback.onImageTagCreated();
            }
        } catch (IOException e) {
            callback.onError(e);
        }
        callback.onError(new EntityStoreException());
    }

    @Override
    public void deleteUserTag(long userId, String tagName, UserTagDeleteCallback callback) {
        Response<ResponseBody> response;
        try {
            response = tagService.deleteUserTag(SessionManager.getSessionToken(),
                    userId, tagName).execute();
            if (response.body() == null) {
                callback.onError(new EntityStoreException("TAG_ENTITY_STORE deleteUserTags: code - " +
                        +response.code()));
            } else {
                callback.onUserTagDeleted();
            }
        } catch (IOException e) {
            callback.onError(e);
        }
        callback.onError(new EntityStoreException());

    }

    @Override
    public void deleteImageTag(long userId, long imageId, String tagName, ImageTagDeleteCallback callback) {
        Response<ResponseBody> response;
        try {
            response = tagService.deleteImageTag(SessionManager.getSessionToken(),
                    userId, imageId, tagName).execute();
            if (response.body() == null) {
                callback.onError(new EntityStoreException("TAG_ENTITY_STORE deleteImageTags: code - " +
                        +response.code()));
            } else {
                callback.onImageTagDeleted();
            }
        } catch (IOException e) {
            callback.onError(e);
        }
        callback.onError(new EntityStoreException());
    }
}
