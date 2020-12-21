package com.netcracker_study_autumn_2020.data.custom.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.netcracker_study_autumn_2020.data.custom.services.ImageService;
import com.netcracker_study_autumn_2020.data.entity.ImageEntity;
import com.netcracker_study_autumn_2020.data.exception.EntityStoreException;
import com.netcracker_study_autumn_2020.data.manager.SessionManager;
import com.netcracker_study_autumn_2020.library.network.NetworkUtils;
import com.netcracker_study_autumn_2020.library.network.UnsafeOkHttpClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
                .client(UnsafeOkHttpClient.getUnsafeOkHttpClient())
                .baseUrl(NetworkUtils.API_ADDRESS)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        imageService = retrofit.create(ImageService.class);
    }

    @Override
    public void getImageById(long imageId, ImageDownloadByIdCallback callback) {
        Response<ResponseBody> response;
        try {
            response = imageService.getImageById(SessionManager.getSessionToken(), imageId)
                    .execute();

            if (response.code() == 200) {
                if (response.body() != null) {
                    InputStream imageStream = response.body().byteStream();
                    Bitmap image = BitmapFactory.decodeStream(imageStream);
                    callback.onImagesDownloaded(image);
                }
            } else {
                callback.onError(new EntityStoreException("IMAGE_ENTITY_STORE downloadImage: code - " +
                        +response.code()));
            }
        } catch (IOException e) {
            callback.onError(e);
        }

    }

    @Override
    public void getCollage(List<Long> imageIds, CollageCreateCallback callback) {
        Response<ResponseBody> response;
        try {
            response = imageService.createCollage(SessionManager.getSessionToken(),
                    imageIds).execute();
            if (response.code() == 200) {
                callback.onCollageCreated();
            } else {
                callback.onError(new EntityStoreException("IMAGE_ENTITY_STORE getCollage: code - " +
                        +response.code()));
            }
        } catch (IOException e) {
            callback.onError(e);

        }
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
    public void getUsersFrames(UsersFramesGetCallback callback) {
        Response<List<Long>> response;
        try {
            response = imageService.getUsersFrames(SessionManager.getSessionToken()).execute();
            if (response.body() == null) {
                callback.onError(new EntityStoreException("IMAGE_ENTITY_STORE getUsersFrames: code - " +
                        +response.code()));
            } else {
                callback.onUsersFramesLoaded(response.body());
            }
        } catch (IOException e) {
            callback.onError(e);
        }
    }

    @Override
    public void getFramePreview(long frameId, FrameGetPreviewCallback callback) {
        //TODO we won't need it
    }

    @Override
    public void uploadFrame(File sourceFrame, FrameUploadCallback callback) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), sourceFrame);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("frame", sourceFrame.getName(),
                requestFile);
        Response<ResponseBody> response;

        try {
            response = imageService.uploadFrame(SessionManager.getSessionToken(), filePart)
                    .execute();
            if (response.code() == 200) {
                callback.onFrameUploaded();
            } else {
                callback.onError(new EntityStoreException("IMAGE_ENTITY_STORE uploadFrame: code - " +
                        +response.code()));
            }
        } catch (IOException e) {
            callback.onError(e);
        }
    }

    @Override
    public void applyFilter(long imageId, String filter, ImageApplyFilterCallback callback) {
        Response<ResponseBody> response;
        try {
            response = imageService.applyFilter(SessionManager.getSessionToken(), imageId,
                    filter)
                    .execute();

            if (response.code() == 200) {
                if (response.body() != null) {
                    InputStream imageStream = response.body().byteStream();
                    Bitmap image = BitmapFactory.decodeStream(imageStream);
                    callback.onFilterApplied(image);
                }
            } else {
                callback.onError(new EntityStoreException("IMAGE_ENTITY_STORE applyFilter: code - " +
                        +response.code()));
            }
        } catch (IOException e) {
            callback.onError(e);
        }
    }

    @Override
    public void applyFrame(long imageId, long frameId, ImageApplyFrameCallback callback) {
        Response<ResponseBody> response;
        try {
            response = imageService.applyFrame(SessionManager.getSessionToken(), imageId,
                    frameId)
                    .execute();

            if (response.code() == 200) {
                if (response.body() != null) {
                    InputStream imageStream = response.body().byteStream();
                    Bitmap image = BitmapFactory.decodeStream(imageStream);
                    callback.onFrameApplied(image);
                }
            } else {
                callback.onError(new EntityStoreException("IMAGE_ENTITY_STORE applyFrame: code - " +
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
            Log.d("IMAGE_ID", String.valueOf(imageEntity.getId()));
            response = imageService.editImageInfo(SessionManager.getSessionToken(),
                    imageEntity.getId(), imageEntity.getName()).execute();
            if (response.body() == null) {

                Log.d("EDIT_IMAGE_INFO", response.message());

                callback.onError(new EntityStoreException("IMAGE_ENTITY_STORE editImageInfo(): code - " +
                        +response.code()));
            } else {
                callback.onImageEdited();
            }
        } catch (IOException e) {
            callback.onError(e);
        }
        //callback.onError(new EntityStoreException());
    }

    @Override
    public void rateImage(long userId, long imageId, int ratingNumber, ImageRateCallback callback) {
        Response<ResponseBody> response;
        try {
            response = imageService.rateImage(SessionManager.getSessionToken(),
                    userId, imageId, ratingNumber).execute();
            if (response.body() == null) {
                callback.onError(new EntityStoreException("IMAGE_ENTITY_STORE rateImage(): code - " +
                        +response.code()));
            } else {
                callback.onImageRated();
            }
        } catch (IOException e) {
            callback.onError(e);
        }
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

    @Override
    public void deleteFrame(long frameId, FrameDeleteCallback callback) {
        Response<ResponseBody> response;
        try {
            response = imageService.deleteFrame(SessionManager.getSessionToken(),
                    frameId).execute();
            if (response.body() == null) {
                callback.onError(new EntityStoreException("IMAGE_ENTITY_STORE deleteFrame(): code - " +
                        +response.code()));
            } else {
                callback.onFrameDeleted();
            }
        } catch (IOException e) {
            callback.onError(e);
        }
        callback.onError(new EntityStoreException());
    }
}
