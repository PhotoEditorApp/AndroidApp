package com.netcracker_study_autumn_2020.data.repository;

import android.graphics.Bitmap;
import android.util.Log;

import com.netcracker_study_autumn_2020.data.custom.image.ImageEntityStore;
import com.netcracker_study_autumn_2020.data.custom.image.ImageEntityStoreFactory;
import com.netcracker_study_autumn_2020.data.entity.ImageEntity;
import com.netcracker_study_autumn_2020.data.mapper.FrameEntityDtoMapper;
import com.netcracker_study_autumn_2020.data.mapper.ImageEntityDtoMapper;
import com.netcracker_study_autumn_2020.domain.dto.ImageDto;
import com.netcracker_study_autumn_2020.domain.repository.ImageRepository;

import java.io.File;
import java.util.List;

public class ImageRepositoryImpl implements ImageRepository {

    private static ImageRepositoryImpl INSTANCE;


    public static synchronized ImageRepositoryImpl getInstance(
            ImageEntityStoreFactory imageEntityStoreFactory,
            ImageEntityDtoMapper imageEntityDtoMapper,
            FrameEntityDtoMapper frameEntityDtoMapper) {
        if (INSTANCE == null) {
            INSTANCE = new ImageRepositoryImpl(imageEntityDtoMapper,
                    frameEntityDtoMapper,
                    imageEntityStoreFactory);
        }
        return INSTANCE;
    }

    private ImageEntityDtoMapper imageEntityDtoMapper;
    private FrameEntityDtoMapper frameEntityDtoMapper;

    private ImageEntityStoreFactory imageEntityStoreFactory;

    public ImageRepositoryImpl(ImageEntityDtoMapper imageEntityDtoMapper,
                               FrameEntityDtoMapper frameEntityDtoMapper,
                               ImageEntityStoreFactory imageEntityStoreFactory) {
        this.imageEntityDtoMapper = imageEntityDtoMapper;
        this.frameEntityDtoMapper = frameEntityDtoMapper;
        this.imageEntityStoreFactory = imageEntityStoreFactory;
    }


    @Override
    public void getUsersFrames(UsersFramesGetCallback callback) {
        ImageEntityStore imageEntityStore = imageEntityStoreFactory.create();

        imageEntityStore.getUsersFrames(new ImageEntityStore.UsersFramesGetCallback() {
            @Override
            public void onUsersFramesLoaded(List<Long> usersFrames) {
                callback.onUsersFramesLoaded(
                        usersFrames);
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }

    @Override
    public void getFramePreview(long frameId, FrameGetPreviewCallback callback) {
        ImageEntityStore imageEntityStore = imageEntityStoreFactory.create();
    }

    @Override
    public void uploadFrame(File sourceFrame, FrameUploadCallback callback) {
        ImageEntityStore imageEntityStore = imageEntityStoreFactory.create();

        imageEntityStore.uploadFrame(sourceFrame, new ImageEntityStore.FrameUploadCallback() {
            @Override
            public void onFrameUploaded() {
                callback.onFrameUploaded();
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }

    @Override
    public void applyFilter(long imageId, String filter, ImageApplyFilterCallback callback) {
        ImageEntityStore imageEntityStore = imageEntityStoreFactory.create();

        imageEntityStore.applyFilter(imageId, filter, new ImageEntityStore.ImageApplyFilterCallback() {
            @Override
            public void onFilterApplied(Bitmap image) {
                callback.onFilterApplied(image);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    public void applyFrame(long imageId, long frameId, ImageApplyFrameCallback callback) {
        ImageEntityStore imageEntityStore = imageEntityStoreFactory.create();

        imageEntityStore.applyFrame(imageId, frameId, new ImageEntityStore.ImageApplyFrameCallback() {
            @Override
            public void onFrameApplied(Bitmap image) {
                callback.onFrameApplied(image);
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }

    @Override
    public void downloadImage(long imageId, ImageDownloadByIdCallback callback) {
        ImageEntityStore imageEntityStore = imageEntityStoreFactory.create();

        imageEntityStore.getImageById(imageId, new ImageEntityStore.ImageDownloadByIdCallback() {
            @Override
            public void onImagesDownloaded(Bitmap image) {
                callback.onImageDownloaded(image);
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }

    @Override
    public void addImage(long userId, long spaceId, File sourceImage, ImageUploadCallback callback) {
        ImageEntityStore imageEntityStore = imageEntityStoreFactory.create();

        imageEntityStore.uploadImage(userId, spaceId, sourceImage, new ImageEntityStore.ImageUploadCallback() {
            @Override
            public void onImagesUploaded() {
                callback.onImagesUploaded();
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }

    @Override
    public void createCollage(List<Long> imageIds, CollageGetCallback callback) {
        ImageEntityStore imageEntityStore = imageEntityStoreFactory.create();

        imageEntityStore.getCollage(imageIds, new ImageEntityStore.CollageCreateCallback() {
            @Override
            public void onCollageCreated() {
                callback.onCollageCreated();
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }

    @Override
    public void getImageBySpaceId(long spaceId, ImageBySpaceIdCallback callback) {
        ImageEntityStore imageEntityStore = imageEntityStoreFactory.create();

        imageEntityStore.getImagesBySpaceId(spaceId, new ImageEntityStore.ImagesBySpaceIdCallback() {
            @Override
            public void onImagesLoaded(List<ImageEntity> imageEntityList) {
                List<ImageDto> imageDtos = imageEntityDtoMapper.map2(imageEntityList);
                for (ImageDto image : imageDtos) {
                    Log.d("IMAGE_NAME", image.getName());
                }
                callback.onImagesLoaded(imageDtos);
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }

    @Override
    public void editImageInfo(ImageDto imageDto, ImageInfoEditCallback callback) {
        ImageEntityStore imageEntityStore = imageEntityStoreFactory.create();
        ImageEntity imageEntity = imageEntityDtoMapper.map1(imageDto);

        imageEntityStore.editImageInfo(imageEntity, new ImageEntityStore.ImageEditCallback() {
            @Override
            public void onImageEdited() {
                callback.onImageInfoEdited();
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }

    @Override
    public void deleteImage(long imageId, ImageDeleteCallback callback) {
        ImageEntityStore imageEntityStore = imageEntityStoreFactory.create();

        imageEntityStore.deleteImage(imageId, new ImageEntityStore.ImageDeleteCallback() {
            @Override
            public void onImageDeleted() {
                callback.onImageDeleted();
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }

    @Override
    public void deleteFrame(long frameId, FrameDeleteCallback callback) {
        ImageEntityStore imageEntityStore = imageEntityStoreFactory.create();

        imageEntityStore.deleteFrame(frameId, new ImageEntityStore.FrameDeleteCallback() {
            @Override
            public void onFrameDeleted() {
                callback.onFrameDeleted();
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }

    @Override
    public void rateImage(long userId, long imageId, int rating, ImageRateCallback callback) {
        ImageEntityStore imageEntityStore = imageEntityStoreFactory.create();

        imageEntityStore.rateImage(userId, imageId, rating, new ImageEntityStore.ImageRateCallback() {
            @Override
            public void onImageRated() {
                callback.onImageRated();
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }
}
