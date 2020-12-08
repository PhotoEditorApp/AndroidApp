package com.netcracker_study_autumn_2020.presentation.mvp.presenter;

import android.graphics.Bitmap;

import com.netcracker_study_autumn_2020.data.manager.SessionManager;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.DownloadImageByIdUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.AddImageTagUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.DeleteImageTagUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.GetImageTagsUseCase;
import com.netcracker_study_autumn_2020.domain.interactor.usecases.tag.GetUserTagsUseCase;
import com.netcracker_study_autumn_2020.presentation.mvp.model.ImageModel;
import com.netcracker_study_autumn_2020.presentation.mvp.view.PreviewImageView;

import java.util.ArrayList;
import java.util.List;

public class PhotoViewPresenter extends BasePresenter {

    private PreviewImageView previewImageView;

    private DownloadImageByIdUseCase downloadImageByIdUseCase;

    private GetImageTagsUseCase getImageTagsUseCase;
    private GetUserTagsUseCase getUserTagsUseCase;
    private AddImageTagUseCase addImageTagUseCase;
    private DeleteImageTagUseCase deleteImageTagUseCase;

    private Bitmap downloadedImage;
    private ImageModel imageModel;
    private List<String> imageTagsList;
    private List<String> userTagsList;

    public ImageModel getImageModel() {
        return imageModel;
    }

    public Bitmap getDownloadedImage() {
        return downloadedImage;
    }

    public void setDownloadedImage(Bitmap downloadedImage) {
        this.downloadedImage = downloadedImage;
    }

    public PhotoViewPresenter(ImageModel imageModel,
                              DownloadImageByIdUseCase downloadImageByIdUseCase,
                              GetImageTagsUseCase getImageTagsUseCase,
                              GetUserTagsUseCase getUserTagsUseCase,
                              AddImageTagUseCase addImageTagUseCase,
                              DeleteImageTagUseCase deleteImageTagUseCase) {
        this.downloadImageByIdUseCase = downloadImageByIdUseCase;
        this.getImageTagsUseCase = getImageTagsUseCase;
        this.getUserTagsUseCase = getUserTagsUseCase;
        this.addImageTagUseCase = addImageTagUseCase;
        this.deleteImageTagUseCase = deleteImageTagUseCase;
        this.imageModel = imageModel;
        imageTagsList = new ArrayList<>();
    }

    public void getImageTags() {
        getImageTagsUseCase.execute(imageModel.getId(), new GetImageTagsUseCase.Callback() {
            @Override
            public void onImageTagsLoaded(List<String> tags) {
                imageTagsList = tags;
                previewImageView.renderTags();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void deleteImageTag(String tagName) {
        deleteImageTagUseCase.execute(SessionManager.getCurrentUserId(),
                imageModel.getId(), tagName, new DeleteImageTagUseCase.Callback() {
                    @Override
                    public void onImageTagDeleted() {
                        refreshData();
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
    }


    public void addImageTag(String tagName) {
        addImageTagUseCase.execute(SessionManager.getCurrentUserId(), imageModel.getId(),
                tagName, new AddImageTagUseCase.Callback() {
                    @Override
                    public void onImageTagAdded() {
                        refreshData();
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    public void refreshData() {
        getImageTags();
        getUserTags();
    }

    private void getUserTags() {
        getUserTagsUseCase.execute(SessionManager.getCurrentUserId(),
                new GetUserTagsUseCase.Callback() {
                    @Override
                    public void onUserTagsLoaded(List<String> tags) {
                        userTagsList = tags;
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    public void downloadImage() {
        previewImageView.showLoading();
        downloadImageByIdUseCase.execute(imageModel.getId(), new DownloadImageByIdUseCase.Callback() {
            @Override
            public void onImageDownloaded(Object image) {
                downloadedImage = (Bitmap) image;
                previewImageView.renderImage();
                previewImageView.hideLoading();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void setPreviewImageView(PreviewImageView previewImageView) {
        this.previewImageView = previewImageView;
    }

    public List<String> getImageTagsList() {
        return imageTagsList;
    }

    public List<String> getUserTagsList() {
        return userTagsList;
    }
}
