package com.netcracker_study_autumn_2020.presentation.mvp.presenter;

import android.graphics.Bitmap;

import com.netcracker_study_autumn_2020.domain.interactor.usecases.image.DownloadImageByIdUseCase;
import com.netcracker_study_autumn_2020.presentation.mvp.view.PreviewImageView;

public class PhotoViewPresenter extends BasePresenter {

    private PreviewImageView previewImageView;

    private DownloadImageByIdUseCase downloadImageByIdUseCase;

    private Bitmap downloadedImage;
    private long imageId;


    public Bitmap getDownloadedImage() {
        return downloadedImage;
    }

    public void setDownloadedImage(Bitmap downloadedImage) {
        this.downloadedImage = downloadedImage;
    }

    public PhotoViewPresenter(long imageId,
                              DownloadImageByIdUseCase downloadImageByIdUseCase) {
        this.downloadImageByIdUseCase = downloadImageByIdUseCase;
        this.imageId = imageId;
    }

    public void downloadImage() {
        previewImageView.showLoading();
        downloadImageByIdUseCase.execute(imageId, new DownloadImageByIdUseCase.Callback() {
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
}
