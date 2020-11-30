package com.netcracker_study_autumn_2020.data.custom.image;

import android.util.Log;

import com.netcracker_study_autumn_2020.data.custom.services.ImageService;
import com.netcracker_study_autumn_2020.data.entity.ImageEntity;
import com.netcracker_study_autumn_2020.data.exception.EntityStoreException;
import com.netcracker_study_autumn_2020.data.manager.SessionManager;
import com.netcracker_study_autumn_2020.library.network.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitImageEntityStore implements ImageEntityStore {

    private ImageService imageService;

    public RetrofitImageEntityStore() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.API_ADDRESS)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        imageService = retrofit.create(ImageService.class);
    }

    @Override
    public void uploadImage(long userId, long spaceId, File sourceImage, ImageUploadCallback callback) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), sourceImage);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", sourceImage.getName(),
                requestFile);
        Response<ResponseBody> response;
        Log.d("GETTING_IMAGE", "9");

        try {
            response = imageService.uploadImage(SessionManager.getSessionToken(), userId, spaceId,
                    filePart).execute();
            if (response.code() == 200) {
                callback.onImagesUploaded();
            } else {
                callback.onError(new EntityStoreException("IMAGE_ENTITY_STORE uploadImage: code - " +
                        +response.code()));
            }
        } catch (IOException e) {
            callback.onError(e);
        }
    }

    @Override
    public void getImagesBySpaceId(long spaceId, ImagesBySpaceIdCallback callback) {
        Response<List<ImageEntity>> response;
        try {
            response = imageService.getImagesBySpaceId(SessionManager.getSessionToken(),
                    spaceId).execute();
            if (response.body() == null) {
                callback.onError(new EntityStoreException("IMAGE_ENTITY_STORE getImagesBySpaceId: code - " +
                        +response.code()));
            } else {
                callback.onImagesLoaded(response.body());
            }
        } catch (IOException e) {
            callback.onError(e);
        }
        //callback.onError(new EntityStoreException());
    }

    @Override
    public void editImageInfo(ImageEntity imageEntity, ImageEditCallback callback) {
        Response<ResponseBody> response;
        try {
            response = imageService.editImageInfo(SessionManager.getSessionToken(),
                    imageEntity).execute();
            if (response.body() == null) {
                callback.onError(new EntityStoreException("IMAGE_ENTITY_STORE editImageInfo(): code - " +
                        +response.code()));
            } else {
                callback.onImageEdited();
            }
        } catch (IOException e) {
            callback.onError(e);
        }
        callback.onError(new EntityStoreException());
    }


    @Override
    public void deleteImage(long imageId, ImageDeleteCallback callback) {
        Response<ResponseBody> response;
        try {
            response = imageService.deleteImage(SessionManager.getSessionToken(),
                    imageId).execute();
            if (response.body() == null) {
                callback.onError(new EntityStoreException("IMAGE_ENTITY_STORE deleteImage(): code - " +
                        +response.code()));
            } else {
                callback.onImageDeleted();
            }
        } catch (IOException e) {
            callback.onError(e);
        }
        callback.onError(new EntityStoreException());
    }
}
